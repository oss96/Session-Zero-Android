# Session Zero - D&D 2024 Character Builder: Android Implementation Plan

> **How to use**: Give this file to Claude in a new session with:
> "Read `SESSION_ZERO_ANDROID_PLAN.md` and implement it phase by phase."

---

## Overview

Port the web app at `C:\Users\Oss\Projects\dnd-builder` (a D&D 2024 character builder) to a native Android app. The web app has a dashboard, 8-step character creation wizard, and character sheet viewer. All data is client-side.

**Android project**: `C:\Users\Oss\Projects\sessionzero` (renamed from dnd-builder-android)
**Package**: `com.ossalali.sessionzero`

---

## Existing Project State

The Android project is a fresh Compose project created from the Empty Compose Activity template:
- **AGP 9.1.0**, **Kotlin 2.2.10**, **Compose BOM 2026.02.01**
- **Min SDK 29** / **Target SDK 36** / **Java 11**
- Has: `MainActivity.kt` (with boilerplate Greeting), MD3 theme with dynamic colors (`ui/theme/`)
- Has: `build.gradle.kts` with Compose + Material3 deps only
- Missing: Navigation, DI, database, serialization, any app logic

---

## Tech Stack (to add)

| Concern | Library |
|---------|---------|
| Navigation | Navigation 3 (`androidx.navigation3:navigation3-runtime` + `navigation3-ui`) |
| ViewModel scoping | `androidx.lifecycle:lifecycle-viewmodel-navigation3` |
| ViewModel Compose | `androidx.lifecycle:lifecycle-viewmodel-compose` |
| Persistence | Room (`room-runtime`, `room-ktx`, `room-compiler` via KSP) |
| DI | Hilt (`hilt-android`, `hilt-android-compiler` via KSP, `hilt-navigation-compose`) |
| Serialization | `kotlinx-serialization-core` + `kotlinx-serialization-json` |
| Icons | `androidx.compose.material:material-icons-extended` |
| Pager | `androidx.compose.foundation` (already via BOM) |

**Gradle plugins to add**: `kotlin-serialization`, `ksp`, `hilt`

---

## Navigation (Nav3) -- 3 Routes Only

```kotlin
@Serializable data object Dashboard : NavKey
@Serializable data class CreateWizard(val characterId: String? = null) : NavKey
@Serializable data class CharacterSheet(val characterId: String) : NavKey
```

The wizard's 8 steps use `HorizontalPager` inside `WizardScreen` -- NOT separate Nav3 routes. This keeps system back stack clean and scopes one `WizardViewModel` to the wizard.

Nav3 setup pattern:
```kotlin
val backStack = rememberNavBackStack(Dashboard)
NavDisplay(
    backStack = backStack,
    onBack = { backStack.removeLastOrNull() },
    entryDecorators = listOf(
        rememberSaveableStateHolderNavEntryDecorator(),
        rememberViewModelStoreNavEntryDecorator()
    ),
    entryProvider = entryProvider {
        entry<Dashboard> { DashboardScreen(...) }
        entry<CreateWizard> { key -> WizardScreen(characterId = key.characterId, ...) }
        entry<CharacterSheet> { key -> CharacterSheetScreen(characterId = key.characterId, ...) }
    }
)
```

---

## Package Structure

All under `com.ossalali.sessionzero`:

```
├── SessionZeroApp.kt                   // @HiltAndroidApp
├── MainActivity.kt                     // @AndroidEntryPoint (update existing)
│
├── navigation/
│   ├── Routes.kt                       // @Serializable NavKey definitions
│   └── AppNavigation.kt               // NavDisplay + entryProvider
│
├── data/
│   ├── local/
│   │   ├── AppDatabase.kt
│   │   ├── dao/CharacterDao.kt
│   │   ├── entity/
│   │   │   ├── CharacterEntity.kt
│   │   │   ├── EquipmentItemEntity.kt
│   │   │   └── WeaponEntity.kt
│   │   ├── converter/Converters.kt     // JSON TypeConverters for List<String>, Map<String,Int>
│   │   └── relation/CharacterWithDetails.kt
│   ├── repository/
│   │   ├── CharacterRepository.kt      // Interface
│   │   └── CharacterRepositoryImpl.kt
│   └── mapper/CharacterMapper.kt       // Entity <-> Domain
│
├── domain/
│   ├── model/
│   │   ├── Character.kt               // Main domain model (~40 fields)
│   │   ├── DerivedStats.kt            // Computed stats
│   │   ├── Enums.kt                   // AbilityName, SkillName, ClassName, SpeciesName, BackgroundName, Alignment
│   │   ├── AbilityScores.kt           // Data class + SKILL_ABILITY_MAP, ABILITIES, ABILITY_LABELS, SKILLS_BY_ABILITY
│   │   ├── Equipment.kt               // EquipmentItem, Coins, EquipmentPackage
│   │   ├── Appearance.kt              // 8-field data class
│   │   ├── ClassDefinition.kt         // ClassDefinition, ClassFeature, SubclassDefinition, SpellcastingProfile
│   │   ├── SpeciesDefinition.kt       // SpeciesDefinition, SpeciesTrait, LineageOption
│   │   └── BackgroundDefinition.kt    // BackgroundDefinition
│   ├── usecase/
│   │   ├── ComputeDerivedStatsUseCase.kt
│   │   ├── SaveCharacterUseCase.kt
│   │   ├── DeleteCharacterUseCase.kt
│   │   ├── GetCharacterUseCase.kt
│   │   ├── GetAllCharactersUseCase.kt
│   │   ├── ExportCharacterUseCase.kt  // Character -> JSON string
│   │   └── ImportCharacterUseCase.kt  // JSON -> validated Character
│   └── rules/                          // Static D&D data (Kotlin objects, NOT in DB)
│       ├── GameRules.kt               // abilityModifier(), proficiencyBonus(), calculateMaxHP(), calculateAC()
│       ├── ClassData.kt               // 12 ClassDefinition objects
│       ├── SpeciesData.kt             // 10 SpeciesDefinition objects
│       ├── BackgroundData.kt          // 16 BackgroundDefinition objects
│       ├── EquipmentData.kt           // Weapons + armor catalogs
│       ├── FeatData.kt                // Origin feats
│       ├── SpellSlotTables.kt         // full/half/third/pact slot tables
│       └── AbilityRules.kt            // Point buy costs, standard array [15,14,13,12,10,8]
│
├── ui/
│   ├── theme/                          // Already exists -- update Color.kt
│   │   ├── Theme.kt
│   │   ├── Color.kt
│   │   └── Type.kt
│   ├── common/
│   │   ├── DndCard.kt                 // Selection card with selected state
│   │   ├── StepIndicator.kt           // Horizontal step dots with labels
│   │   ├── SectionHeader.kt
│   │   ├── SelectionGrid.kt           // LazyVerticalGrid for class/species/background
│   │   ├── AbilityScoreBox.kt         // Score + modifier display box
│   │   └── ConfirmDialog.kt           // Delete confirmation
│   ├── dashboard/
│   │   ├── DashboardScreen.kt
│   │   ├── DashboardViewModel.kt
│   │   ├── CharacterCard.kt
│   │   └── EmptyState.kt
│   ├── wizard/
│   │   ├── WizardScreen.kt            // HorizontalPager + StepIndicator + Prev/Next
│   │   ├── WizardViewModel.kt         // Character state + mutation methods
│   │   ├── steps/
│   │   │   ├── ClassStep.kt           // Class grid + level slider + subclass dropdown
│   │   │   ├── SpeciesStep.kt         // Species grid + lineage picker
│   │   │   ├── BackgroundStep.kt      // Background grid + ability bonus allocator
│   │   │   ├── AbilityScoresStep.kt   // 4 methods: Point Buy, Standard Array, Roll, Manual
│   │   │   ├── SkillsStep.kt          // Proficiency checkboxes grouped by ability
│   │   │   ├── EquipmentStep.kt       // Package vs gold + inventory editor
│   │   │   ├── DetailsStep.kt         // Name, appearance, personality, feats, spells, weapons, portrait, backstory
│   │   │   └── ReviewStep.kt          // Summary + Save button
│   │   └── components/
│   │       ├── PointBuyPanel.kt
│   │       ├── DiceRoller.kt
│   │       ├── EquipmentEditor.kt
│   │       └── WeaponEditor.kt
│   └── sheet/
│       ├── CharacterSheetScreen.kt
│       ├── CharacterSheetViewModel.kt
│       ├── SheetPage1.kt              // Abilities, skills, combat, weapons
│       └── SheetPage2.kt              // Equipment, spells, features, backstory
│
└── di/
    ├── DatabaseModule.kt               // Room DB + DAO
    └── RepositoryModule.kt             // Binds CharacterRepository
```

---

## Room Database Schema

### Table: `characters` (CharacterEntity)

~35 scalar columns. Small bounded lists stored as JSON via TypeConverters (feats, languages, cantrips, spells, skills, abilityScoreBonuses, appearance).

| Column | Type | Notes |
|--------|------|-------|
| `id` | TEXT PK | UUID string |
| `name` | TEXT | |
| `pronouns` | TEXT | |
| `level` | INTEGER | 1-20 |
| `xp` | INTEGER | |
| `alignment` | TEXT | enum name or "" |
| `className` | TEXT? | ClassName enum name |
| `subclass` | TEXT? | |
| `species` | TEXT? | SpeciesName enum name |
| `speciesLineage` | TEXT? | |
| `background` | TEXT? | BackgroundName enum name |
| `abilityScoreBonuses` | TEXT | JSON `{"STR":2,"DEX":1}` |
| `originFeat` | TEXT? | |
| `baseStr..baseCha` | INTEGER x6 | |
| `abilityScoreMethod` | TEXT? | "pointBuy"/"standardArray"/"rolled"/"manual" |
| `skillProficiencies` | TEXT | JSON `["Athletics","Perception"]` |
| `equipmentChoice` | TEXT? | "package"/"gold" |
| `coinsCp..coinsPp` | INTEGER x4 | |
| `personalityTraits` | TEXT | |
| `ideals` | TEXT | |
| `bonds` | TEXT | |
| `flaws` | TEXT | |
| `appearance` | TEXT | JSON 8-field object |
| `backstory` | TEXT | |
| `alliesAndOrganizations` | TEXT | |
| `additionalNotes` | TEXT | |
| `portraitUrl` | TEXT | base64 data URL or "" |
| `feats` | TEXT | JSON array |
| `languages` | TEXT | JSON array |
| `knownCantrips` | TEXT | JSON array |
| `preparedSpells` | TEXT | JSON array |
| `hpOverride` | INTEGER? | null = use calculated |
| `acOverride` | INTEGER? | |
| `spellSaveDCOverride` | INTEGER? | |
| `createdAt` | TEXT | ISO 8601 |
| `updatedAt` | TEXT | |

### Table: `equipment_items` (EquipmentItemEntity)
FK to `characters.id` with CASCADE delete.

| Column | Type |
|--------|------|
| `id` | INTEGER PK (auto) |
| `characterId` | TEXT FK |
| `name` | TEXT |
| `quantity` | INTEGER |
| `description` | TEXT? |
| `sortOrder` | INTEGER |

### Table: `weapons` (WeaponEntity)
FK to `characters.id` with CASCADE delete.

| Column | Type |
|--------|------|
| `id` | INTEGER PK (auto) |
| `characterId` | TEXT FK |
| `name` | TEXT |
| `attackBonus` | TEXT |
| `damage` | TEXT |
| `sortOrder` | INTEGER |

### DAO Key Operations
```kotlin
@Transaction @Query("SELECT * FROM characters ORDER BY updatedAt DESC")
fun getAllCharactersWithDetails(): Flow<List<CharacterWithDetails>>

@Transaction @Query("SELECT * FROM characters WHERE id = :id")
fun getCharacterWithDetails(id: String): Flow<CharacterWithDetails?>

@Transaction
suspend fun saveCharacterFull(character, equipment, weapons)  // upsert + delete/reinsert children

suspend fun deleteCharacter(id: String)
```

---

## Web Source Files to Port

Reference these files in `C:\Users\Oss\Projects\dnd-builder\src\` when implementing:

| Web File | What It Contains | Port To |
|----------|-----------------|---------|
| `types/character.ts` | Character interface (40 fields), DerivedStats interface | `domain/model/Character.kt`, `DerivedStats.kt` |
| `types/abilities.ts` | AbilityName, SkillName, Alignment types + SKILL_ABILITY_MAP, ABILITIES, ABILITY_LABELS, SKILLS_BY_ABILITY | `domain/model/Enums.kt`, `AbilityScores.kt` |
| `types/class.ts` | ClassDefinition, ClassFeature, SubclassDefinition, SpellcastingProfile | `domain/model/ClassDefinition.kt` |
| `types/species.ts` | SpeciesDefinition, SpeciesTrait, LineageOption | `domain/model/SpeciesDefinition.kt` |
| `types/background.ts` | BackgroundDefinition | `domain/model/BackgroundDefinition.kt` |
| `types/equipment.ts` | EquipmentItem, Coins, Weapon, Armor, EquipmentPackage | `domain/model/Equipment.kt` |
| `types/spells.ts` | ClassName enum, Spell interface | `domain/model/Enums.kt` |
| `hooks/useDerivedStats.ts` | Derived stats computation (104 lines) -- port VERBATIM | `domain/usecase/ComputeDerivedStatsUseCase.kt` |
| `utils/abilityModifier.ts` | `floor((score - 10) / 2)` | `domain/rules/GameRules.kt` |
| `utils/proficiencyBonus.ts` | Level-based lookup (+2 at 1-4, +3 at 5-8, etc.) | `domain/rules/GameRules.kt` |
| `utils/hpCalculator.ts` | `hitDie + (level-1) * (hitDie/2 + 1 + conMod)` | `domain/rules/GameRules.kt` |
| `state/characterReducer.ts` | 22 action types + initialCharacter state | `WizardViewModel.kt` methods |
| `data/classes/*.ts` (12 files) | Full class definitions with features, subclasses, spellcasting | `domain/rules/ClassData.kt` |
| `data/species/*.ts` (10 files) | Species with traits + lineage options | `domain/rules/SpeciesData.kt` |
| `data/backgrounds/backgrounds.ts` | 16 backgrounds | `domain/rules/BackgroundData.kt` |
| `data/equipment/weapons.ts` | Weapons catalog | `domain/rules/EquipmentData.kt` |
| `data/equipment/armor.ts` | Armor catalog | `domain/rules/EquipmentData.kt` |
| `data/feats/originFeats.ts` | Origin feats | `domain/rules/FeatData.kt` |
| `data/rules/abilityScores.ts` | Point buy costs (8=0..15=9), standard array [15,14,13,12,10,8] | `domain/rules/AbilityRules.kt` |

---

## Key Architectural Decisions

1. **Wizard = 1 Nav3 route + HorizontalPager** -- 8 steps share a single `WizardViewModel`. System back exits wizard entirely.

2. **Direct ViewModel methods, not sealed-class reducer** -- The web's 22 reducer actions become 22 methods on `WizardViewModel`, each calling `_character.update { it.copy(...) }`. More idiomatic Kotlin.

3. **Static game data as Kotlin objects** -- Classes, species, backgrounds, equipment, feats, rules all live in `domain/rules/` as object singletons. Not in Room DB.

4. **JSON TypeConverters for small lists, separate tables for equipment/weapons** -- Equipment and weapons are variable-length and individually editable. Feats, languages, skills, spells are atomic.

5. **Hilt for DI** -- DB -> DAO -> Repository -> UseCase -> ViewModel.

6. **WizardViewModel mutation methods** map 1:1 from the web reducer. When class changes, reset dependent fields (skills, equipment, spells). When level drops below 3, clear subclass. Port exact same logic from `characterReducer.ts`.

---

## Implementation Phases

### Phase 1: Gradle + Scaffolding
1. Update `gradle/libs.versions.toml` -- add versions and libraries for Nav3, Room, Hilt, kotlinx-serialization, material-icons-extended, lifecycle-viewmodel-compose
2. Update root `build.gradle.kts` -- add `kotlin-serialization`, `ksp`, `hilt` plugins (apply false)
3. Update `app/build.gradle.kts` -- apply plugins, add all new dependencies
4. Create `SessionZeroApp.kt` (@HiltAndroidApp)
5. Update `AndroidManifest.xml` -- set `android:name=".SessionZeroApp"`
6. Update `MainActivity.kt` -- add `@AndroidEntryPoint`, replace Greeting boilerplate with `AppNavigation()`
7. Create package directory structure
8. Verify build

### Phase 2: Domain Models + Game Rules
1. Create enums in `Enums.kt`: `AbilityName`, `SkillName`, `ClassName`, `SpeciesName`, `BackgroundName`, `Alignment`
2. Create `AbilityScores.kt` with data class + companion constants (SKILL_ABILITY_MAP, etc.)
3. Create `Equipment.kt`: `EquipmentItem`, `Coins`, `EquipmentPackage`
4. Create `Appearance.kt`: 8-field data class
5. Create `ClassDefinition.kt`: `ClassDefinition`, `ClassFeature`, `SubclassDefinition`, `SpellcastingProfile`
6. Create `SpeciesDefinition.kt`: `SpeciesDefinition`, `SpeciesTrait`, `LineageOption`
7. Create `BackgroundDefinition.kt`
8. Create `Character.kt` (main domain model with all ~40 fields + `empty()` factory)
9. Create `DerivedStats.kt`
10. Create `GameRules.kt` -- port `abilityModifier()`, `proficiencyBonus()`, `calculateMaxHP()`, `calculateAC()`
11. Create `AbilityRules.kt` -- point buy cost table, standard array
12. Create `SpellSlotTables.kt` -- full/half/third/pact tables
13. Create `ComputeDerivedStatsUseCase.kt` -- port `useDerivedStats.ts` verbatim

### Phase 3: Static Game Data
1. Port all 12 class definitions from `data/classes/*.ts` -> `ClassData.kt`
2. Port all 10 species from `data/species/*.ts` -> `SpeciesData.kt`
3. Port all 16 backgrounds from `data/backgrounds/backgrounds.ts` -> `BackgroundData.kt`
4. Port weapons + armor catalogs -> `EquipmentData.kt`
5. Port origin feats -> `FeatData.kt`

### Phase 4: Data Layer (Room + Hilt)
1. Create `CharacterEntity.kt`, `EquipmentItemEntity.kt`, `WeaponEntity.kt`
2. Create `Converters.kt` (JSON TypeConverters)
3. Create `CharacterDao.kt`
4. Create `AppDatabase.kt`
5. Create `CharacterWithDetails.kt` (@Relation)
6. Create `CharacterMapper.kt` (entity <-> domain)
7. Create `CharacterRepository.kt` interface + `CharacterRepositoryImpl.kt`
8. Create `DatabaseModule.kt` + `RepositoryModule.kt` (Hilt)
9. Create all use cases (Save, Delete, Get, GetAll, Export, Import)

### Phase 5: Navigation + Dashboard
1. Create `Routes.kt` (3 Nav3 routes)
2. Create `AppNavigation.kt` (NavDisplay + entryProvider)
3. Build common UI: `DndCard`, `StepIndicator`, `SectionHeader`, `SelectionGrid`, `ConfirmDialog`
4. Create `DashboardViewModel.kt`
5. Build `DashboardScreen.kt` -- TopAppBar, FAB, character grid, empty state
6. Build `CharacterCard.kt` -- level badge, name, class/species/background, action buttons
7. Build `EmptyState.kt`
8. Wire import (file picker for JSON) + export (share intent)
9. Wire delete with `ConfirmDialog`

### Phase 6: Wizard Steps 1-4
1. Create `WizardViewModel.kt` -- character StateFlow, currentStep, all mutation methods (port from `characterReducer.ts`)
2. Build `WizardScreen.kt` -- Scaffold + StepIndicator + HorizontalPager(userScrollEnabled=false) + Prev/Next bottom bar
3. Build `ClassStep.kt` -- class selection grid, level slider (1-20), subclass dropdown (shown when level >= 3)
4. Build `SpeciesStep.kt` -- species grid, lineage picker (segmented buttons)
5. Build `BackgroundStep.kt` -- background grid, ability bonus allocator (+2/+1 or +1/+1/+1)
6. Build `AbilityScoresStep.kt` -- segmented tab row for method, Point Buy (+/- with costs), Standard Array (dropdowns), Roll (4d6 drop lowest), Manual (text fields)

### Phase 7: Wizard Steps 5-8
1. Build `SkillsStep.kt` -- checkboxes grouped by ability, background skills locked, class limit enforced
2. Build `EquipmentStep.kt` -- package/gold toggle, items list, inventory editor, coin inputs
3. Build `DetailsStep.kt` -- name/pronouns, alignment, appearance (8 fields), personality (4 textareas), feats (chips + add), languages (chips + add), spells (if class has spellcasting), weapons (editable rows), portrait (image picker), backstory/allies/notes
4. Build `ReviewStep.kt` -- full read-only summary with derived stats, Save button
5. Wire save: ViewModel -> UseCase -> Repository -> Room -> navigate back to dashboard

### Phase 8: Character Sheet
1. Create `CharacterSheetViewModel.kt` -- load character + compute derived stats
2. Build `CharacterSheetScreen.kt` -- TopAppBar with back, scrollable content
3. Build `SheetPage1.kt` -- header, 6 ability boxes, saving throws, skills, combat stats (AC/HP/initiative/speed/hit dice/prof), weapons, passives
4. Build `SheetPage2.kt` -- personality, backstory, equipment + coins, spellcasting (DC, attack, cantrips, spells, slots), features & traits, allies, notes, portrait

### Phase 9: Polish
1. Loading states + error handling
2. Input validation (name required, point buy total, skill count limits)
3. Dark theme verification
4. Portrait image compression/storage
5. Wizard step transition animations
6. Accessibility (content descriptions, touch targets)

---

## Verification Checklist

- [ ] `./gradlew assembleDebug` builds clean
- [ ] Dashboard: empty state shown on first launch
- [ ] Create: complete all 8 wizard steps -> Save -> card appears on dashboard
- [ ] Edit: tap Edit on card -> wizard pre-populated -> change values -> Save updates
- [ ] Delete: tap Delete -> confirmation -> character removed
- [ ] Sheet: tap View Sheet -> all stats computed correctly
- [ ] Export: tap Export -> share JSON file
- [ ] Import: tap Import -> pick JSON -> character appears
- [ ] Spot check: Level 5 Fighter, STR 16, DEX 14, CON 15 -> Prof +3, HP = 44, AC = 12, Init = +2
- [ ] Dark theme: all screens render correctly
- [ ] Back navigation: system back from wizard -> dashboard, from sheet -> dashboard
