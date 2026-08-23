# Parts Generator

This repository contains the source code for the Parts Generator application included with the BF556 Feeder project. It replaces the original Java application, which had compatibility and dependency issues with newer Java versions. The application targets JDK 25 and supports platform-specific packages that include their own Java runtime, so end users do not need to install Java or Maven.

## Requirements

- JDK 25
- Maven
- JavaFX dependencies configured in `pom.xml`
- Git Bash (windows)

## Run from Source

```bash
mvn clean javafx:run
```

## Build a Standalone Application

Make the build script executable:

```bash
chmod +x build
```

Run the platform-detecting build script:

```bash
./build
```

The script detects the current platform and creates:

- macOS: `dist/Parts Generator.app`
- Windows: `dist/Parts Generator.exe`
- Linux: `dist/Parts Generator.deb`

The packaged application includes its own Java runtime. Users do not need Java, Maven, or the JDK installed.

## Project Structure

```text
src/main/java/         Java source files
src/main/resources/    FXML and application resources
pom.xml                Maven configuration
build                  Packaging script
target/                Temporary Maven build output
dist/                  Packaged applications
```


## Troubleshooting

If the application does not start, run it from the project directory:

```bash
mvn clean javafx:run
```

For macOS security warnings, remove the quarantine attribute:

```bash
xattr -dr com.apple.quarantine "dist/Parts Generator.app"
```

OpenSCAD is not included automatically and may need to be installed separately.