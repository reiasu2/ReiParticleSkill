@echo off
REM Root build script: builds both modules in correct order and copies jars to Desktop.
echo === Building forge-port-api ===
cd /d "%~dp0forge-port-api"
call gradlew.bat build -x test
if errorlevel 1 (
    echo FAILED: forge-port-api
    exit /b 1
)
echo === Building forge-port ===
cd /d "%~dp0forge-port"
call gradlew.bat build -x test
if errorlevel 1 (
    echo FAILED: forge-port
    exit /b 1
)
echo === Copying jars to Desktop ===
copy /y "%~dp0forge-port-api\build\libs\reiparticlesapi-1.0-SNAPSHOT-forge-port.jar" "%USERPROFILE%\Desktop\"
copy /y "%~dp0forge-port\build\libs\reiparticleskill-1.0-SNAPSHOT-forge-port.jar" "%USERPROFILE%\Desktop\"
echo === Done ===
