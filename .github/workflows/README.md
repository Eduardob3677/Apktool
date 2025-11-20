# GitHub Actions Workflows

This directory contains GitHub Actions workflows for the Apktool project.

## build-and-release.yml

This workflow automates the process of building Apktool and creating releases.

### Features

1. **Automatic Build**: Compiles Apktool using Gradle with shadowJar and proguard tasks
2. **Artifact Upload**: Uploads the built JAR to GitHub Actions with 90-day retention
3. **Release Creation**: Automatically creates GitHub releases when version tags are pushed
4. **AAPT2 Version Tracking**: Extracts and includes AAPT2 version in release information

### Triggers

The workflow runs on:
- **Push to main branch**: Builds and uploads artifacts to GitHub Actions
- **Version tags (v*)**: Builds, uploads artifacts, and creates a GitHub release  
- **Manual dispatch**: Can be triggered manually from the Actions tab

### Usage

#### Creating a Release

To create a new release:

1. Ensure your code is ready for release on the main branch
2. Create and push a version tag:
   ```bash
   git tag v3.0.0
   git push origin v3.0.0
   ```
3. The workflow will automatically:
   - Build Apktool
   - Extract AAPT2 version
   - Create a GitHub release with the tag name
   - Upload the built JAR as a release asset
   - Include AAPT2 version in the release title and notes

#### Manual Workflow Run

To manually trigger the build:

1. Go to the Actions tab in GitHub
2. Select "Build and Release Apktool" workflow
3. Click "Run workflow"
4. Select the branch and click "Run workflow"

### Outputs

- **Artifact**: `apktool-<version>.jar` uploaded to GitHub Actions (accessible for 90 days)
- **Release** (only on version tags): GitHub release with the built JAR attached

### Release Information

Each release includes:
- Release title with Apktool version and AAPT2 version
- Build date and commit SHA
- Downloadable JAR file

### Example Release Title

```
Apktool 3.0.0-SNAPSHOT (AAPT2 2.20-eng.202511)
```

### Requirements

- Java 17 (Zulu distribution)
- Gradle 8.7
- Ubuntu latest runner
