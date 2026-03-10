@echo off
REM Root build script: runs the aggregate Forge workspace build, including all tests/checks, and copies the distributable jar to Desktop.
echo === Building Forge workspace with full verification ===
cd /d "%~dp0forge-port"
call gradlew.bat build
if errorlevel 1 (
    echo FAILED: forge-port aggregate build
    exit /b 1
)
echo === Copying jar to Desktop ===
copy /y "%~dp0forge-port\reiparticles-forge-runtime\build\libs\reiparticleskill-1.0-SNAPSHOT-forge-port.jar" "%USERPROFILE%\Desktop\"
echo === Done ===
