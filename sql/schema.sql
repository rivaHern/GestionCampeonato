-- ============================================================
-- GestionCampeonato Mundial 2026 - Schema completo
-- Ejecutar: sqlcmd -S localhost -U sa -P 'Mundial2026#' -C -i sql/schema.sql
-- ============================================================

CREATE DATABASE MundialFutbol2026;
GO
USE MundialFutbol2026;
GO

CREATE TABLE Confederacion (
    id_confederacion INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    siglas VARCHAR(10) NOT NULL
);

CREATE TABLE Pais (
    id_pais INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    id_confederacion INT NOT NULL,
    FOREIGN KEY (id_confederacion) REFERENCES Confederacion(id_confederacion)
);

CREATE TABLE Ciudad (
    id_ciudad INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    id_pais INT NOT NULL,
    es_sede BIT NOT NULL DEFAULT 0,
    FOREIGN KEY (id_pais) REFERENCES Pais(id_pais)
);

CREATE TABLE Estadio (
    id_estadio INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    capacidad INT NOT NULL,
    id_ciudad INT NOT NULL,
    FOREIGN KEY (id_ciudad) REFERENCES Ciudad(id_ciudad)
);

CREATE TABLE Equipo (
    id_equipo INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    id_confederacion INT NOT NULL,
    id_pais INT NOT NULL,
    valor_total DECIMAL(18,2) NOT NULL DEFAULT 0,
    FOREIGN KEY (id_confederacion) REFERENCES Confederacion(id_confederacion),
    FOREIGN KEY (id_pais) REFERENCES Pais(id_pais)
);

CREATE TABLE DirectorTecnico (
    id_dt INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    nacionalidad VARCHAR(100) NOT NULL,
    id_equipo INT NOT NULL,
    FOREIGN KEY (id_equipo) REFERENCES Equipo(id_equipo)
);

CREATE TABLE Jugador (
    id_jugador INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NULL,
    peso DECIMAL(5,2) NOT NULL,
    estatura DECIMAL(4,2) NOT NULL,
    posicion VARCHAR(50) NOT NULL,
    valor DECIMAL(18,2) NOT NULL,
    id_equipo INT NOT NULL,
    FOREIGN KEY (id_equipo) REFERENCES Equipo(id_equipo)
);

CREATE TABLE Grupo (
    id_grupo INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(10) NOT NULL
);

CREATE TABLE GrupoEquipo (
    id_grupo INT NOT NULL,
    id_equipo INT NOT NULL,
    PRIMARY KEY (id_grupo, id_equipo),
    FOREIGN KEY (id_grupo) REFERENCES Grupo(id_grupo),
    FOREIGN KEY (id_equipo) REFERENCES Equipo(id_equipo)
);

CREATE TABLE Partido (
    id_partido INT IDENTITY(1,1) PRIMARY KEY,
    fecha DATETIME NOT NULL,
    id_equipo_local INT NOT NULL,
    id_equipo_visitante INT NOT NULL,
    id_estadio INT NOT NULL,
    id_grupo INT NOT NULL,
    FOREIGN KEY (id_equipo_local) REFERENCES Equipo(id_equipo),
    FOREIGN KEY (id_equipo_visitante) REFERENCES Equipo(id_equipo),
    FOREIGN KEY (id_estadio) REFERENCES Estadio(id_estadio),
    FOREIGN KEY (id_grupo) REFERENCES Grupo(id_grupo)
);

CREATE TABLE Usuario (
    id_usuario INT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(20) NOT NULL,
    activo BIT NOT NULL DEFAULT 1
);

CREATE TABLE Bitacora (
    id_bitacora INT IDENTITY(1,1) PRIMARY KEY,
    id_usuario INT NOT NULL,
    fecha_entrada DATETIME NOT NULL,
    fecha_salida DATETIME NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);
GO

