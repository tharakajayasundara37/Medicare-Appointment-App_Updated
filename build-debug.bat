@echo off
setlocal
cd /d "%~dp0"
if exist "%ProgramFiles%\Android\Android Studio\jbr\bin\java.exe" set "JAVA_HOME=%ProgramFiles%\Android\Android Studio\jbr"
set "GRADLE_USER_HOME=%~dp0.gradle-local"
call gradlew.bat :app:assembleDebug --no-daemon
exit /b %ERRORLEVEL%
