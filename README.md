# Gestion Campeonato Mundial 2026

## Requisitos
- Java 17
- JavaFX 17 SDK
- Docker

## Configuracion inicial

### 1. Levantar la base de datos
docker compose up -d

### 2. Configurar db.properties
db.server=localhost
db.name=MundialFutbol2026
db.user=sa
db.password=Mundial2026#

### 3. Correr la app
./run.sh

## Usuarios de prueba
admin / Admin123! -> ADMINISTRADOR
valen / 123 -> TRADICIONAL
