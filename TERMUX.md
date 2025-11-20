# Termux Support

This document describes Apktool's support for running on Termux, the Android terminal emulator and Linux environment app.

## Overview

Apktool has been modified to support running natively on Termux by utilizing the Termux `PREFIX` environment variable to locate system binaries like `aapt2`.

## Features Added

### 1. AAPT Optimization Support
- Added command-line options for aapt2 optimization:
  - `--shorten-res-paths` (`-srp`): Shortens the paths of resources inside the APK
  - `--enable-sparse-encoding` (`-ese`): Enables encoding of sparse entries using a binary search tree
  - `--collapse-res-names` (`-crn`): Collapses resource names to a single value in the key string pool

### 2. DEX Container Format Support
- Enhanced support for DEX container formats
- Improved handling of multiple DEX files within APK archives

### 3. Termux PREFIX Environment Variable Support
- Apktool now checks for the `PREFIX` environment variable (typically `/data/data/com.termux/files/usr` in Termux)
- When `PREFIX` is set, Apktool will look for `aapt2` in `$PREFIX/bin/aapt2`
- Falls back to embedded binaries if Termux PREFIX is not set or aapt2 is not found

## Prerequisites for Termux

To use Apktool on Termux, you need:

1. **Java Development Kit (JDK)**:
   ```bash
   pkg install openjdk-21
   ```

2. **AAPT2** (Android Asset Packaging Tool 2) - **REQUIRED**:
   ```bash
   pkg install aapt2
   ```
   
   **Important**: AAPT2 is mandatory for Termux. The embedded aapt2 binary in Apktool is compiled for x86-64 and will not work on Termux's aarch64 (ARM64) architecture. You must install aapt2 from Termux packages.

3. **Gradle** (for building from source):
   ```bash
   pkg install gradle
   ```

## Building on Termux

To build Apktool on Termux:

```bash
# Navigate to the Apktool directory
cd Apktool

# Build the project
./gradlew build shadowJar

# The built JAR will be in brut.apktool/apktool-cli/build/libs/
```

## Usage on Termux

Using Apktool on Termux is the same as on other platforms. The tool will automatically detect the Termux environment and use the system `aapt2` binary:

```bash
# Decode an APK
apktool d app.apk

# Build an APK with optimization
apktool b app -srp -ese -crn
```

## Environment Variables

- `PREFIX`: The Termux installation prefix (automatically set by Termux, typically `/data/data/com.termux/files/usr`)
- `JAVA_HOME`: Path to Java installation (set by Termux when installing JDK)

## Troubleshooting

### aapt2 not found
If you encounter errors about aapt2 not being found:
1. Ensure aapt2 is installed: `pkg install aapt2`
2. Verify the PREFIX variable is set: `echo $PREFIX`
3. Check that aapt2 is executable: `ls -la $PREFIX/bin/aapt2`

### Test failures during build
Some tests may fail on Termux due to resource limitations or missing dependencies. You can skip tests during build:
```bash
./gradlew build shadowJar -x test
```

## Implementation Details

The Termux support is implemented in `AaptManager.java`:
- The `getBinaryFile()` method first checks for the `PREFIX` environment variable
- If found, it looks for aapt2 in `$PREFIX/bin/aapt2`
- If the Termux aapt2 is found and executable, it returns that path
- **If PREFIX is set but aapt2 is not found, it throws an error** - this is because:
  - Termux runs on aarch64 (ARM64) architecture
  - The embedded aapt2 binaries in Apktool are compiled for x86-64
  - x86-64 binaries cannot run on ARM64 Termux
- On non-Termux environments, it falls back to extracting and using the embedded aapt2 binary

This approach ensures compatibility with both Termux and standard environments while preventing architecture mismatches.
