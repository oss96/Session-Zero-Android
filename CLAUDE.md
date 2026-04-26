# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this
repository.

## Project Overview

Session Zero is a native Android app for creating and managing Dungeons & Dragons 2024 characters.
It is a port of the web app at `dnd-builder`, featuring:

- **Dashboard**: List and manage characters
- **Character Creation Wizard**: 8-step guided flow (Class, Species, Background, Ability Scores,
  Skills, Equipment, Details, Review)
- **Character Sheet**: Full D&D character details with ability scores, skills, combat stats,
  equipment, and spells
- **Offline-first**: All data stored locally via Room database
- **Import/Export**: JSON compatibility with the web app

## Build & Development

### Build Commands

```bash
./gradlew assembleDebug          # Build debug APK
./gradlew assembleRelease        # Build release APK
./gradlew test                   # Run unit tests
./gradlew connectedAndroidTest   # Run instrumented tests (device/emulator required)
./gradlew lint                   # Run lint checks
```

### Requirements

- **Android Studio** with **AGP 9.1+** and **Kotlin 2.2+**
- **Min SDK 29** / **Target SDK 36** / **Compile SDK 36**
- **Java 11**
- Gradle daemon enabled (default)

### Key Versions

| Component             | Version    |
|-----------------------|------------|
| Kotlin                | 2.2.10     |
| AGP                   | 9.1.1      |
| Compose BOM           | 2026.02.01 |
| Navigation 3          | 1.0.0      |
| Room                  | 2.7.1      |
| Hilt                  | 2.59.2     |
| kotlinx-serialization | 1.8.1      |

Full catalog in `gradle/libs.versions.toml`.

## Release Process

### Triggering a Release

Releases are produced by the **Release** GitHub Actions workflow
(`.github/workflows/release.yml`). Trigger it from the Actions tab on
github.com (Run workflow button) or via the GitHub CLI:

```bash
gh workflow run Release --ref main
gh run watch                # optional: follow the run live
```

The workflow runs end-to-end on GitHub's runners and:

1. Reads `version.properties`, bumps `MINOR` by 1, commits the change to `main`
2. Creates an annotated tag `vMAJOR.MINOR.0` and pushes both the commit and tag
3. Decodes the keystore from the `KEYSTORE_BASE64` secret onto the runner
4. Builds the signed `app-release.apk` via `./gradlew assembleRelease`
5. Publishes a GitHub Release titled **Session Zero** with the APK attached and
   auto-generated release notes (commits since the previous tag)

A failed run after step 2 will leave a bump commit on `main` and a tag on origin.
Recovery: revert `version.properties` to the previous values and delete the
dangling tag (`gh release delete <tag> --yes` and `git push origin --delete <tag>`)
before retrying.

### Versioning

`versionName` is computed at build time as `MAJOR.MINOR.PATCH`:

- `MAJOR` and `MINOR` are read from `version.properties` at the repo root
- `PATCH` = `git rev-list --count <last v* tag>..HEAD` (resets to 0 on each release)
- `versionCode` = `git rev-list --count HEAD` (total commit count — monotonic, decoupled
  from `versionName` so it never regresses on a major bump)

To bump `MAJOR`, edit `version.properties` manually and commit. The next workflow
dispatch will then produce e.g. `v1.1.0` (the workflow always bumps `MINOR`, so
hand-editing `MAJOR=1, MINOR=0` then dispatching gives `v1.1.0`, not `v1.0.0`).

### Build Variants

Two installable variants, side-by-side on the same device:

| Variant   | applicationId                       | Label                  | Icon  | Signing                   |
|-----------|-------------------------------------|------------------------|-------|---------------------------|
| `debug`   | `com.ossalali.sessionzero.debug`    | "Session Zero (Debug)" | red   | debug keystore (auto)     |
| `release` | `com.ossalali.sessionzero`          | "Session Zero"         | green | release keystore (CI/local opt-in) |

The debug variant overrides `app_name` and `ic_launcher_background` via
`app/src/debug/res/`. Release `versionName` gets a `-debug` suffix when built as
debug.

### Required GitHub Secrets

Set under **Settings → Secrets and variables → Actions**:

- `KEYSTORE_BASE64` — keystore encoded as base64 (use `--body` flag with `gh secret set`,
  not stdin pipe — PowerShell 5.1 corrupts piped values with UTF-16)
- `KEYSTORE_PASSWORD`
- `KEY_ALIAS`
- `KEY_PASSWORD`

### Local Signed Release Builds (Optional)

For `./gradlew assembleRelease` to produce a signed APK on your machine, add to
`~/.gradle/gradle.properties` (never committed):

```properties
SESSION_ZERO_KEYSTORE_PATH=C:/path/to/release.jks
SESSION_ZERO_KEYSTORE_PASSWORD=...
SESSION_ZERO_KEY_ALIAS=...
SESSION_ZERO_KEY_PASSWORD=...
```

Without these, `assembleRelease` still succeeds but produces `app-release-unsigned.apk`
(installable via `adb install` for personal testing).

## Architecture

Clean Architecture with three layers:

### Presentation Layer (ui/)

**ViewModel + Jetpack Compose + Navigation 3**

- **State**: StateFlow for reactive updates
- **Navigation**: 3 main routes via Navigation 3
    - `Dashboard`: Character list
    - `CreateWizard(characterId?)`: 8-step wizard (steps are HorizontalPager pages, not separate
      routes)
    - `CharacterSheet(characterId)`: Character viewer
- **Theme**: `SessionZeroTheme` with Material 3 dynamic colors (Android 12+)
- **Layout**:
    - `dashboard/`: DashboardScreen, DashboardViewModel, CharacterCard, EmptyState
    - `wizard/`: WizardScreen, WizardViewModel, step pages in `steps/`, reusable components in
      `components/`
    - `sheet/`: CharacterSheetScreen, CharacterSheetViewModel, collapsible sections
    - `common/`: Shared composables
    - `theme/`: Color, Type, Theme definitions

**Compose Guidelines**:

- Use `@PreviewLightDark` (not `@Preview`) for light/dark variants
- Keep `dynamicColor = true` in SessionZeroTheme for runtime consistency
- **Always use named parameters** in function calls — formatters reorder parameters, breaking
  positional args

### Domain Layer (domain/)

**Business logic and D&D rules**

- **Models** (model/): Character, ClassDefinition, SpeciesDefinition, Ability, etc. — all immutable
- **Use Cases** (usecase/): One responsibility each
    - SaveCharacter, GetCharacter, GetAllCharacters, DeleteCharacter
    - ExportCharacter, ImportCharacter
    - ComputeDerivedStats (stateless calculations)
- **Rules** (rules/): D&D 2024 game data
    - 12 classes, 10 species, 16 backgrounds
    - Weapons, armor, feats, spells
    - Ability modifiers, spell slot tables
    - All defined in ClassData.kt, SpeciesData.kt, EquipmentData.kt, etc.

### Data Layer (data/)

**Persistence via Room**

- **Database** (local/):
    - AppDatabase: Room DB with 3 entities (Character, EquipmentItem, Weapon)
    - CharacterDao: CRUD queries
    - Converters: TypeConverters for List<String>, Map<String,Int> in Room
    - CharacterWithDetails: @Relation for linked data
- **Repository** (repository/):
    - CharacterRepository (interface) & CharacterRepositoryImpl
    - Methods return Flow<T> for reactive updates
- **Mapper** (mapper/):
    - CharacterMapper: Entity <-> Domain conversion
    - Handles JSON serialization for import/export

### Dependency Injection (Hilt)

- **Entry points**: SessionZeroApp (@HiltAndroidApp), MainActivity (@AndroidEntryPoint)
- **Modules**:
    - DatabaseModule: Provides AppDatabase (singleton) and CharacterDao
    - RepositoryModule: Binds CharacterRepository to CharacterRepositoryImpl
- **Constructor injection**: Hilt auto-provides via @Inject on ViewModel constructors

### Navigation Flow

```
Dashboard
├─ Create → CreateWizard(null)
├─ Edit id → CreateWizard(id)
└─ View id → CharacterSheet(id)
```

Transitions: 350ms slide + fade (in/out).

## Code Patterns

### StateFlow Pattern

```kotlin
private val _state = MutableStateFlow(initialValue)
val state: StateFlow<State> = _state.asStateFlow()

// Update in viewModelScope
viewModelScope.launch {
    _state.update { it.copy(...) }
}
```

### Use Case in ViewModel

```kotlin
@HiltViewModel
class MyViewModel @Inject constructor(
    private val myUseCase: MyUseCase,
) : ViewModel() {
    val data: StateFlow<Data> = myUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), default)
}
```

### Composable Preview

```kotlin
@PreviewLightDark
@Composable
private fun MyComponentPreview() {
    SessionZeroTheme {
        MyComponent(param = "value")  // named parameters required
    }
}
```

### Serialization

- Models: @Serializable annotation
- Encode: `json.encodeToString(obj)`
- Decode: `json.decodeFromString<Type>(str)`
- Logic in use cases (ImportCharacterUseCase, ExportCharacterUseCase)

## Testing

- **Unit tests** (app/src/test/): JUnit 4
- **Instrumented tests** (app/src/androidTest/): AndroidJUnit4 + Espresso

Currently minimal (placeholders). Test priorities:

- Derived stat calculations (ComputeDerivedStatsUseCase)
- ViewModel state updates
- Navigation flows

## Important Project Conventions

### Commits

**Do NOT commit until user approves.** User tests changes on device first. Always:

1. Verify build passes
2. Describe what changed
3. Wait for explicit confirmation ("this change works" or "commit") before git commands

**Commit message style**: Use conventional commits with a scope:

```
feat(UI): Add new button, doing awesome things
fix(Navigation): fix route usage between a and b screen
refactor(Sheet): Extract section composables
chore(Deps): Bump AGP to 9.1.1
```

Format: `<type>(<scope>): <short description>`

### Kotlin

Parameter order varies by formatters (e.g., `modifier` moves to first). **Always use named
parameters** to prevent breakage.

### Previews

Use `@PreviewLightDark` and keep `dynamicColor = true` for preview/runtime consistency.

## File Locations

- Build: build.gradle.kts (root), app/build.gradle.kts (app), gradle/libs.versions.toml (versions)
- App: MainActivity.kt, SessionZeroApp.kt
- Resources: app/src/main/res/
- Manifest: app/src/main/AndroidManifest.xml

## Gradle Tasks

```bash
./gradlew clean                    # Clean build
./gradlew assemble --refresh-deps  # Refresh dependencies
```

## Performance

- **Derived stats**: Stateless, computed on-demand (no caching)
- **Room queries**: Reactive via Flow — UI updates automatically on DB changes
- **Compose**: Efficient recomposition via StateFlow subscriptions

