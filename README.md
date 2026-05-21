# Gestion Campeonato Mundial 2026

## Integrantes
- Valentina Gonzalez Diaz
- Juan Felipe Hurtado Londoño
- Jose Luis Rivadeneira Hernandez

## Requisitos
- Java 17 o superior
- JavaFX 17 SDK (o superior)
- Docker (para la base de datos SQL Server)

## Configuración inicial

### 1. Levantar la base de datos
Abre una terminal en la raíz del proyecto y ejecuta:
```bash
docker compose up -d
```
*Nota: La base de datos ya contiene un script inicial (`schema.sql`) que se debe ejecutar para crear las tablas y datos básicos.*

### 2. Configurar la conexión (Opcional)
El archivo `src/db.properties` contiene las credenciales por defecto:
```properties
db.server=localhost
db.name=MundialFutbol2026
db.user=sa
db.password=Mundial2026#
```

### 3. Correr la aplicación
Dependiendo de tu sistema operativo, ejecuta uno de los siguientes archivos para compilar y arrancar la aplicación automáticamente:

- **En Windows:**
  Haz doble clic en el archivo `run.bat` o ejecútalo desde la consola:
  ```cmd
  .\run.bat
  ```

- **En Linux o macOS:**
  Abre una terminal y ejecuta el script de shell:
  ```bash
  ./run.sh
  ```

*(Estos scripts detectarán automáticamente tu instalación de Java y JavaFX, compilarán el código fuente y abrirán la ventana de inicio de sesión).*

## Usuarios de prueba
- **Administrador:** `admin` / `Admin123!` -> Acceso total, creación de usuarios y vista de bitácora.
- **Tradicional:** `valen` / `123` -> Permite gestionar CRUD de datos (Equipos, Jugadores, Partidos).
- **Esporádico:** `esporadico` / `esporadico` -> Acceso restringido únicamente a Consultas.

## Funcionalidades Principales
- **Base de Datos Completa:** Información de 48 equipos, jugadores, sedes, estadios y partidos.
- **Bitácora de Sesiones:** Registro de entrada y salida automática para los usuarios.
- **Consultas Personalizadas:** Búsqueda por confederación, partidos por estadio, jugadores menores de 21, etc.
- **Reportes en PDF:** Generación de reportes PDF usando *iTextPDF* para exportar métricas e información del sistema.
