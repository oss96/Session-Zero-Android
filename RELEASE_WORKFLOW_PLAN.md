# Release workflow rewrite plan — session-zero-android

## Goal

Switch the release workflow from manual `workflow_dispatch` (auto-bumps MINOR, commits, tags, releases) to **tag-push triggered**, with versioned releases and an auto-changelog. Only one release entry exists at any time; tags accumulate forever for history.

## Current state (`.github/workflows/release.yml`)

- **Trigger:** `workflow_dispatch` (manual button)
- **Behavior on dispatch:**
  1. Reads MAJOR/MINOR from `version.properties`, increments MINOR.
  2. Commits the updated `version.properties` to `main`.
  3. Creates and pushes a tag `v{MAJOR}.{NEW_MINOR}.0`.
  4. Decodes keystore from secrets, builds signed APK via `./gradlew assembleRelease`.
  5. Renames the APK and publishes a GitHub Release.
- **Existing release:** `v0.1.0` with `Session-Zero.apk`.

## Proposed changes

1. **Trigger:** change to `push: tags: ['[0-9]+.[0-9]+.[0-9]+']` — workflow fires when you push a semver tag (e.g. `0.2.0`, no `v` prefix).
2. **Drop the auto-MINOR-bump-and-commit logic entirely.** Tag is the source of truth. Workflow no longer pushes commits back to `main`.
3. **Read version from the tag** (`GITHUB_REF`), validate `X.Y.Z`.
4. **Parse the tag into MAJOR/MINOR/PATCH and sed into `version.properties`** so Gradle picks up the correct `versionCode`/`versionName` for the APK. Local to the runner — not committed.
5. **Build the signed APK** (unchanged — keystore decode + `./gradlew assembleRelease`).
6. **Only on successful build:** delete the most recent existing release (`gh release delete <tag> --yes` without `--cleanup-tag`). The tag stays.
7. **Create the new release** with:
   - Tag = the pushed version (e.g. `0.2.0`)
   - Name = `Session Zero 0.2.0`
   - `generate_release_notes: true`
   - File = `app/build/outputs/apk/release/Session-Zero.apk`

## Proposed full workflow

```yaml
name: Release

on:
  push:
    tags:
      - '[0-9]+.[0-9]+.[0-9]+'

permissions:
  contents: write

jobs:
  release:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v6

      - name: Get version from tag
        id: version
        shell: bash
        run: |
          VERSION="${GITHUB_REF#refs/tags/}"
          if [[ ! "$VERSION" =~ ^[0-9]+\.[0-9]+\.[0-9]+$ ]]; then
            echo "::error::Tag '$VERSION' is not valid semver (expected X.Y.Z)"
            exit 1
          fi
          echo "version=$VERSION" >> "$GITHUB_OUTPUT"

      - name: Set version in version.properties
        run: |
          IFS='.' read -r MAJOR MINOR PATCH <<< "${{ steps.version.outputs.version }}"
          sed -i "s/^MAJOR=.*/MAJOR=${MAJOR}/" version.properties
          sed -i "s/^MINOR=.*/MINOR=${MINOR}/" version.properties
          sed -i "s/^PATCH=.*/PATCH=${PATCH}/" version.properties
          cat version.properties

      - name: Set up JDK 17
        uses: actions/setup-java@v5
        with:
          distribution: temurin
          java-version: 17

      - name: Set up Gradle
        uses: gradle/actions/setup-gradle@v6

      - name: Decode keystore
        env:
          KEYSTORE_BASE64: ${{ secrets.KEYSTORE_BASE64 }}
        run: |
          echo "$KEYSTORE_BASE64" | base64 -d > "$GITHUB_WORKSPACE/release.jks"
          echo "SESSION_ZERO_KEYSTORE_PATH=$GITHUB_WORKSPACE/release.jks" >> "$GITHUB_ENV"

      - name: Build release APK
        env:
          SESSION_ZERO_KEYSTORE_PASSWORD: ${{ secrets.KEYSTORE_PASSWORD }}
          SESSION_ZERO_KEY_ALIAS:         ${{ secrets.KEY_ALIAS }}
          SESSION_ZERO_KEY_PASSWORD:      ${{ secrets.KEY_PASSWORD }}
        run: ./gradlew assembleRelease

      - name: Rename APK
        run: mv app/build/outputs/apk/release/app-release.apk app/build/outputs/apk/release/Session-Zero.apk

      - name: Delete previous release entry (keep tag)
        env:
          GH_TOKEN: ${{ secrets.GITHUB_TOKEN }}
        run: |
          PREV_TAG=$(gh release list --limit 1 --json tagName --jq '.[0].tagName' || true)
          if [ -n "$PREV_TAG" ] && [ "$PREV_TAG" != "${{ steps.version.outputs.version }}" ]; then
            gh release delete "$PREV_TAG" --yes
          fi

      - name: Publish GitHub Release
        uses: softprops/action-gh-release@v3
        with:
          tag_name: ${{ steps.version.outputs.version }}
          name: Session Zero ${{ steps.version.outputs.version }}
          generate_release_notes: true
          files: app/build/outputs/apk/release/Session-Zero.apk
```

## Caveats

- The current `v0.1.0` release will be **deleted** the first time a new tag like `0.2.0` is pushed. The `v0.1.0` tag stays in git history.
- `version.properties` in the repo stays at whatever's committed — sed only affects the runner. Drift accepted per project rule. (Previously the workflow committed the bump back; that's now your responsibility, e.g. as part of the manual release branch flow.)
- Workflow no longer touches `main` — no auto-commits, no auto-pushed tags. The trigger tag must be created/pushed by you.
- Build still requires the same secrets: `KEYSTORE_BASE64`, `KEYSTORE_PASSWORD`, `KEY_ALIAS`, `KEY_PASSWORD`.
- Tag pattern `[0-9]+.[0-9]+.[0-9]+` only matches strict semver `X.Y.Z`.

## Subtlety with versionCode

`version.properties` typically also drives `versionCode` (an integer that must monotonically increase for the Play Store). If your Gradle setup computes `versionCode` from MAJOR/MINOR/PATCH (e.g. `MAJOR*10000 + MINOR*100 + PATCH`), this rewrite preserves it. If `versionCode` is a separate property bumped by the old workflow, that mechanism is now broken — flag this if the existing workflow had any `VERSION_CODE` or `versionCode` handling I missed.
