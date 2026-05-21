@echo off
setlocal enabledelayedexpansion
REM ============================================================
REM GestionCampeonato - Script de ejecución para Windows
REM ============================================================

set PROJECT_DIR=%~dp0
cd /d "%PROJECT_DIR%"

REM Detectar Java instalado
where java >nul 2>&1
if errorlevel 1 (
    echo ❌ Java no está instalado o no está en PATH.
    echo    Descárgalo desde: https://www.oracle.com/java/technologies/downloads/
    echo    Asegúrate de que esté en PATH
    pause
    exit /b 1
)
echo ✅ Java encontrado

REM Detectar JavaFX automáticamente
set JAVAFX_PATH=
for %%p in (
    "%PROJECT_DIR%javafx-sdk-21.0.5\lib"
    "%USERPROFILE%\javafx-sdk-21.0.5\lib"
    "%USERPROFILE%\javafx-sdk-17.0.13\lib"
    "%USERPROFILE%\javafx-sdk-17\lib"
    "C:\Program Files\JavaFX\lib"
    "C:\Program Files\Java\javafx-sdk-21.0.3\lib"
) do (
    if exist "%%~p\javafx.controls.jar" (
        set JAVAFX_PATH=%%~p
        goto :found_javafx
    )
)

:found_javafx
if "%JAVAFX_PATH%"=="" (
    echo ❌ No se encontró JavaFX.
    echo    Descárgalo desde: https://gluonhq.com/products/javafx/
    echo    Extráelo en: %USERPROFILE%\javafx-sdk-21.0.5\
    pause
    exit /b 1
)

echo ✅ JavaFX encontrado en: %JAVAFX_PATH%

REM Librerías
set LIBS=lib\mssql-jdbc.jar;lib\itextpdf-5.5.13.3.jar

REM Compilar
echo Compilando...
powershell -Command "Get-ChildItem -Path src -Filter *.java -Recurse | ForEach-Object { '\"' + ($_.FullName -replace '\\', '/') + '\"' } | Out-File -FilePath sources.txt -Encoding ascii"
javac --module-path "%JAVAFX_PATH%" ^
      --add-modules javafx.controls,javafx.fxml ^
      -cp "%LIBS%" ^
      -d bin ^
      @sources.txt

if errorlevel 1 (
    echo ❌ Error de compilación.
    pause
    exit /b 1
)

REM Copiar recursos
echo Copiando recursos...
xcopy /e /i /y src\views bin\views >nul 2>&1
copy src\db.properties bin\application\ >nul 2>&1
copy src\db.properties bin\ >nul 2>&1
for /r src %%f in (*.css) do (
    set "dest=%%f"
    set "dest=!dest:%PROJECT_DIR%src\=%PROJECT_DIR%bin\!"
    mkdir "!dest!\.." 2>nul
    copy "%%f" "!dest!" >nul
)
for /r src %%f in (*.png *.jpg *.gif) do (
    set "dest=%%f"
    set "dest=!dest:%PROJECT_DIR%src\=%PROJECT_DIR%bin\!"
    mkdir "!dest!\.." 2>nul
    copy "%%f" "!dest!" >nul
)

REM Ejecutar
echo Ejecutando...
java --module-path "%JAVAFX_PATH%" ^
     --add-modules javafx.controls,javafx.fxml ^
     -Djava.library.path="%JAVAFX_PATH%" ^
     -cp "bin;%LIBS%" ^
     application.Main

pause