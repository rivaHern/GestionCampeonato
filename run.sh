#!/bin/bash

# ============================================================
# GestionCampeonato - Script de ejecución universal
# Compatible con Linux y macOS
# ============================================================

PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$PROJECT_DIR"

# Detectar JavaFX automáticamente
find_javafx() {
    for path in \
        "$HOME/javafx-sdk-17.0.13/lib" \
        "$HOME/javafx-sdk-17/lib" \
        "/usr/lib/jvm/openjfx" \
        "/usr/share/openjfx/lib" \
        "/opt/javafx-sdk-17/lib" \
        "/opt/javafx-sdk/lib"; do
        if [ -f "$path/javafx.controls.jar" ]; then
            echo "$path"
            return
        fi
    done
    echo ""
}

JAVAFX_PATH=$(find_javafx)

if [ -z "$JAVAFX_PATH" ]; then
    echo "❌ No se encontró JavaFX."
    echo "   Descárgalo desde: https://gluonhq.com/products/javafx/"
    echo "   Extráelo en: $HOME/javafx-sdk-17.0.13/"
    exit 1
fi

echo "✅ JavaFX encontrado en: $JAVAFX_PATH"

# Librerias
LIBS="lib/mssql-jdbc.jar:lib/itextpdf-5.5.13.3.jar"

# Compilar
echo "Compilando..."
find src -name "*.java" > sources.txt
javac --module-path "$JAVAFX_PATH" \
      --add-modules javafx.controls,javafx.fxml \
      -cp "$LIBS" \
      -d bin \
      @sources.txt

if [ $? -ne 0 ]; then
    echo "❌ Error de compilación."
    exit 1
fi

# Copiar recursos
echo "Copiando recursos..."
cp -r src/views bin/ 2>/dev/null
cp src/db.properties bin/application/ 2>/dev/null
cp src/db.properties bin/ 2>/dev/null
find src -name "*.css" | while read f; do
    dest="bin/${f#src/}"
    mkdir -p "$(dirname "$dest")"
    cp "$f" "$dest"
done
find src -name "*.png" -o -name "*.jpg" -o -name "*.gif" | while read f; do
    dest="bin/${f#src/}"
    mkdir -p "$(dirname "$dest")"
    cp "$f" "$dest"
done

# Ejecutar
echo "Ejecutando..."
java --module-path "$JAVAFX_PATH" \
     --add-modules javafx.controls,javafx.fxml \
     -Djava.library.path="$JAVAFX_PATH" \
     -cp "bin:$LIBS" \
     application.Main
