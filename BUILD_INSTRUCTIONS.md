# Build the updated app

Open this directory in Android Studio and use its bundled JDK 21 for Gradle.
On Windows, run `build-debug.bat` from this directory to build the debug APK.
The first build requires Internet access for Google Maven and Maven Central dependencies.
The APK is written to `app/build/outputs/apk/debug/app-debug.apk`.

Build fixes:
- Use a short project-local Java Unix-domain socket path for Gradle communication on Windows.
- Remove the unused Foojay toolchain download plugin. The app uses the installed JDK.
- Include a Windows build helper that selects Android Studio's JDK and a project-local Gradle cache.

Verified: assembleDebug completed successfully. Original Kotlin files and existing layout view IDs remain unchanged.
This confirms compilation and packaging; device interaction testing has not been performed.
