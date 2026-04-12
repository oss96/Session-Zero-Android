# Session Zero - D&D 2024 Character Builder

A native Android app for creating and managing Dungeons & Dragons 2024 characters.

## Features

- **Character Creation Wizard** — 8-step guided flow: Class, Species, Background, Ability Scores, Skills, Equipment, Details, Review
- **Character Sheet** — Full character sheet with ability scores, skills, combat stats, spells, equipment, and more
- **Import/Export** — Import characters from JSON (compatible with the Session Zero web app), export via share
- **Offline** — All data stored locally via Room database
- **D&D 2024 Content** — 12 classes with subclasses, 10 species with lineage options, 16 backgrounds, complete weapon/armor catalogs, origin feats, spell slot tables

## Tech Stack

- **Kotlin** + **Jetpack Compose** (Material 3)
- **Navigation 3** for screen routing with predictive back
- **Room** for local persistence
- **Hilt** for dependency injection
- **kotlinx-serialization** for JSON import/export

## Building

```bash
./gradlew assembleDebug
```

Requires Android Studio with AGP 9.1+ and Kotlin 2.2+.

## License

All rights reserved.
