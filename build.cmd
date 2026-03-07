@echo off
REM Root build script: builds the Forge runtime module and copies the distributable jar to Desktop.
echo === Building Forge runtime module ===
cd /d "%~dp0forge-port"
call gradlew.bat :reiparticles-forge-runtime:build -x test
if errorlevel 1 (
    echo FAILED: reiparticles-forge-runtime
    exit /b 1
)
echo === Copying jar to Desktop ===
copy /y "%~dp0forge-port\reiparticles-forge-runtime\build\libs\reiparticleskill-1.0-SNAPSHOT-forge-port.jar" "%USERPROFILE%\Desktop\"
echo === Done ===
