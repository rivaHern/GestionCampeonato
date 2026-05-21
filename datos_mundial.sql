USE MundialFutbol2026;
GO

-- Limpiar tablas si hay algo (en orden de FKs)
DELETE FROM Bitacora;
DELETE FROM Partido;
DELETE FROM GrupoEquipo;
DELETE FROM Jugador;
DELETE FROM DirectorTecnico;
DELETE FROM Equipo;
DELETE FROM Estadio;
DELETE FROM Ciudad;
DELETE FROM Pais;
DELETE FROM Usuario;
DELETE FROM Grupo;
DELETE FROM Confederacion;
GO

-- Reiniciar autoincrementables
DBCC CHECKIDENT ('Partido', RESEED, 0);
DBCC CHECKIDENT ('Jugador', RESEED, 0);
DBCC CHECKIDENT ('DirectorTecnico', RESEED, 0);
DBCC CHECKIDENT ('Equipo', RESEED, 0);
DBCC CHECKIDENT ('Estadio', RESEED, 0);
DBCC CHECKIDENT ('Ciudad', RESEED, 0);
DBCC CHECKIDENT ('Pais', RESEED, 0);
DBCC CHECKIDENT ('Usuario', RESEED, 0);
DBCC CHECKIDENT ('Confederacion', RESEED, 0);
DBCC CHECKIDENT ('Grupo', RESEED, 0);
DBCC CHECKIDENT ('Bitacora', RESEED, 0);
GO

-- =============================================
-- CONFIGURACIÓN BÁSICA (Usuarios, Grupos, Confederaciones)
-- =============================================

INSERT INTO Confederacion (nombre, siglas) VALUES
('Union de Asociaciones de Futbol Europeas', 'UEFA'),
('Confederacion Sudamericana de Futbol', 'CONMEBOL'),
('Confederacion de Futbol de America del Norte', 'CONCACAF'),
('Confederacion Africana de Futbol', 'CAF'),
('Confederacion Asiatica de Futbol', 'AFC'),
('Confederacion de Futbol de Oceania', 'OFC');
GO

INSERT INTO Grupo (nombre) VALUES
('A'),('B'),('C'),('D'),('E'),('F'),('G'),('H'),('I'),('J'),('K'),('L');
GO

INSERT INTO Usuario (username, password, rol, activo) VALUES
('admin', 'Admin123!', 'ADMINISTRADOR', 1),
('valen', '123', 'TRADICIONAL', 1),
('esporadico', 'esporadico', 'ESPORADICO', 1);
GO

-- =============================================
-- PAISES (48 Clasificados al Mundial)
-- =============================================
-- UEFA (16 cupos)
INSERT INTO Pais (nombre, id_confederacion) VALUES 
('Francia', 1), ('España', 1), ('Inglaterra', 1), ('Alemania', 1), ('Portugal', 1), 
('Italia', 1), ('Paises Bajos', 1), ('Croacia', 1), ('Belgica', 1), ('Suiza', 1), 
('Dinamarca', 1), ('Serbia', 1), ('Polonia', 1), ('Suecia', 1), ('Austria', 1), ('Escocia', 1);

-- CONMEBOL (6 + 1 cupos) -> Ponemos 7
INSERT INTO Pais (nombre, id_confederacion) VALUES 
('Argentina', 2), ('Brasil', 2), ('Uruguay', 2), ('Colombia', 2), ('Ecuador', 2), ('Venezuela', 2), ('Peru', 2);

-- CONCACAF (6 + 1 cupos) -> Ponemos 7 (Anfitriones incluidos)
INSERT INTO Pais (nombre, id_confederacion) VALUES 
('Estados Unidos', 3), ('México', 3), ('Canadá', 3), ('Costa Rica', 3), ('Panamá', 3), ('Jamaica', 3), ('Honduras', 3);

-- CAF (9 cupos)
INSERT INTO Pais (nombre, id_confederacion) VALUES 
('Marruecos', 4), ('Senegal', 4), ('Egipto', 4), ('Costa de Marfil', 4), ('Nigeria', 4), 
('Camerun', 4), ('Argelia', 4), ('Ghana', 4), ('Tunez', 4);

-- AFC (8 cupos)
INSERT INTO Pais (nombre, id_confederacion) VALUES 
('Japon', 5), ('Iran', 5), ('Corea del Sur', 5), ('Australia', 5), ('Arabia Saudita', 5), 
('Qatar', 5), ('Uzbekistan', 5), ('Emiratos Arabes Unidos', 5);

-- OFC (1 cupo)
INSERT INTO Pais (nombre, id_confederacion) VALUES 
('Nueva Zelanda', 6);
GO

-- =============================================
-- CIUDADES Y ESTADIOS (De los anfitriones)
-- =============================================
DECLARE @idMex INT = (SELECT id_pais FROM Pais WHERE nombre = 'México');
DECLARE @idUSA INT = (SELECT id_pais FROM Pais WHERE nombre = 'Estados Unidos');
DECLARE @idCan INT = (SELECT id_pais FROM Pais WHERE nombre = 'Canadá');

INSERT INTO Ciudad (nombre, id_pais, es_sede) VALUES
('Ciudad de México', @idMex, 1),
('Guadalajara', @idMex, 1),
('Monterrey', @idMex, 1),
('Nueva York', @idUSA, 1),
('Miami', @idUSA, 1),
('Los Angeles', @idUSA, 1),
('Toronto', @idCan, 1),
('Vancouver', @idCan, 1);

INSERT INTO Estadio (nombre, capacidad, id_ciudad) VALUES
('Estadio Azteca', 87000, (SELECT id_ciudad FROM Ciudad WHERE nombre = 'Ciudad de México')),
('Estadio Akron', 48000, (SELECT id_ciudad FROM Ciudad WHERE nombre = 'Guadalajara')),
('Estadio BBVA', 53000, (SELECT id_ciudad FROM Ciudad WHERE nombre = 'Monterrey')),
('MetLife Stadium', 82500, (SELECT id_ciudad FROM Ciudad WHERE nombre = 'Nueva York')),
('Hard Rock Stadium', 65000, (SELECT id_ciudad FROM Ciudad WHERE nombre = 'Miami')),
('SoFi Stadium', 70000, (SELECT id_ciudad FROM Ciudad WHERE nombre = 'Los Angeles')),
('BMO Field', 45000, (SELECT id_ciudad FROM Ciudad WHERE nombre = 'Toronto')),
('BC Place', 54000, (SELECT id_ciudad FROM Ciudad WHERE nombre = 'Vancouver'));
GO

-- =============================================
-- EQUIPOS (48 Equipos)
-- =============================================
INSERT INTO Equipo (nombre, id_confederacion, id_pais, valor_total)
SELECT 'Selección ' + nombre, id_confederacion, id_pais, 0 FROM Pais;
GO

-- =============================================
-- DIRECTORES TÉCNICOS
-- =============================================
-- Asignaremos nombres genéricos a la mayoría y reales a los principales.
INSERT INTO DirectorTecnico (nombre, apellido, nacionalidad, id_equipo)
SELECT 'Director', p.nombre, p.nombre, e.id_equipo 
FROM Equipo e JOIN Pais p ON e.id_pais = p.id_pais;

-- Actualizar algunos reales
UPDATE DirectorTecnico SET nombre = 'Lionel', apellido = 'Scaloni', nacionalidad = 'Argentina' WHERE id_equipo = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección Argentina');
UPDATE DirectorTecnico SET nombre = 'Jaime', apellido = 'Lozano', nacionalidad = 'Mexicana' WHERE id_equipo = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección México');
UPDATE DirectorTecnico SET nombre = 'Gregg', apellido = 'Berhalter', nacionalidad = 'Estadounidense' WHERE id_equipo = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección Estados Unidos');
UPDATE DirectorTecnico SET nombre = 'Nestor', apellido = 'Lorenzo', nacionalidad = 'Argentina' WHERE id_equipo = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección Colombia');
UPDATE DirectorTecnico SET nombre = 'Luis', apellido = 'de la Fuente', nacionalidad = 'Española' WHERE id_equipo = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección España');
UPDATE DirectorTecnico SET nombre = 'Didier', apellido = 'Deschamps', nacionalidad = 'Francesa' WHERE id_equipo = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección Francia');
GO

-- =============================================
-- JUGADORES (Para consultas y reportes)
-- =============================================
DECLARE @idMex INT = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección México');
DECLARE @idUSA INT = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección Estados Unidos');
DECLARE @idCol INT = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección Colombia');
DECLARE @idEsp INT = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección España');
DECLARE @idArg INT = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección Argentina');
DECLARE @idFra INT = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección Francia');
DECLARE @idCan INT = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección Canadá');
DECLARE @idBra INT = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección Brasil');

-- Jugadores México
INSERT INTO Jugador (nombre, apellido, fecha_nacimiento, peso, estatura, posicion, valor, id_equipo) VALUES
('Santiago', 'Gimenez', '2001-04-18', 75.0, 1.83, 'Delantero', 45000000, @idMex),
('Fidel', 'Ambriz', '2008-03-21', 68.0, 1.75, 'Medio', 5000000, @idMex), -- Menor de 21
('Guillermo', 'Ochoa', '1985-07-13', 78.0, 1.85, 'Portero', 1000000, @idMex);

-- Jugadores USA
INSERT INTO Jugador (nombre, apellido, fecha_nacimiento, peso, estatura, posicion, valor, id_equipo) VALUES
('Christian', 'Pulisic', '1998-09-18', 73.0, 1.77, 'Medio', 32000000, @idUSA),
('Gio', 'Reyna', '2006-11-13', 70.0, 1.75, 'Medio', 20000000, @idUSA), -- Menor de 21
('Weston', 'McKennie', '1998-08-28', 84.0, 1.85, 'Medio', 25000000, @idUSA);

-- Jugadores Canadá
INSERT INTO Jugador (nombre, apellido, fecha_nacimiento, peso, estatura, posicion, valor, id_equipo) VALUES
('Alphonso', 'Davies', '2000-11-02', 72.0, 1.83, 'Defensa', 70000000, @idCan),
('Jonathan', 'David', '2000-01-14', 77.0, 1.75, 'Delantero', 50000000, @idCan),
('Ismael', 'Kone', '2006-06-16', 74.0, 1.88, 'Medio', 15000000, @idCan); -- Menor de 21

-- Jugadores Colombia
INSERT INTO Jugador (nombre, apellido, fecha_nacimiento, peso, estatura, posicion, valor, id_equipo) VALUES
('Luis', 'Diaz', '1997-01-13', 73.0, 1.80, 'Delantero', 75000000, @idCol),
('James', 'Rodriguez', '1991-07-12', 75.0, 1.80, 'Medio', 5000000, @idCol),
('Jhon', 'Duran', '2006-12-13', 78.0, 1.85, 'Delantero', 20000000, @idCol); -- Menor de 21

-- Jugadores España
INSERT INTO Jugador (nombre, apellido, fecha_nacimiento, peso, estatura, posicion, valor, id_equipo) VALUES
('Pedri', 'Gonzalez', '2002-11-25', 65.0, 1.74, 'Medio', 80000000, @idEsp),
('Lamine', 'Yamal', '2007-07-13', 65.0, 1.78, 'Delantero', 90000000, @idEsp), -- Menor de 21
('Gavi', 'Paez', '2007-08-05', 68.0, 1.73, 'Medio', 70000000, @idEsp); -- Menor de 21

-- Jugadores Argentina
INSERT INTO Jugador (nombre, apellido, fecha_nacimiento, peso, estatura, posicion, valor, id_equipo) VALUES
('Lionel', 'Messi', '1987-06-24', 72.0, 1.70, 'Delantero', 35000000, @idArg),
('Julian', 'Alvarez', '2000-01-31', 71.0, 1.70, 'Delantero', 90000000, @idArg),
('Alejandro', 'Garnacho', '2006-07-01', 68.0, 1.80, 'Delantero', 40000000, @idArg); -- Menor de 21

-- Jugadores Francia
INSERT INTO Jugador (nombre, apellido, fecha_nacimiento, peso, estatura, posicion, valor, id_equipo) VALUES
('Kylian', 'Mbappe', '1998-12-20', 73.0, 1.78, 'Delantero', 180000000, @idFra),
('Warren', 'Zaire-Emery', '2006-03-08', 75.0, 1.78, 'Medio', 60000000, @idFra), -- Menor de 21
('Antoine', 'Griezmann', '1991-03-21', 72.0, 1.76, 'Delantero', 25000000, @idFra);

-- Jugadores Brasil
INSERT INTO Jugador (nombre, apellido, fecha_nacimiento, peso, estatura, posicion, valor, id_equipo) VALUES
('Vinicius', 'Junior', '2000-07-12', 73.0, 1.76, 'Delantero', 150000000, @idBra),
('Endrick', 'Felipe', '2007-07-21', 70.0, 1.73, 'Delantero', 45000000, @idBra), -- Menor de 21
('Rodrygo', 'Goes', '2001-01-09', 64.0, 1.74, 'Delantero', 100000000, @idBra);

-- Inyectar un par de jugadores genéricos para los otros 40 equipos para evitar que estén totalmente vacíos
INSERT INTO Jugador (nombre, apellido, fecha_nacimiento, peso, estatura, posicion, valor, id_equipo)
SELECT 'Jugador1', p.nombre, '2000-01-01', 70.0, 1.75, 'Medio', 1000000, e.id_equipo 
FROM Equipo e JOIN Pais p ON e.id_pais = p.id_pais 
WHERE e.id_equipo NOT IN (@idMex, @idUSA, @idCol, @idEsp, @idArg, @idFra, @idCan, @idBra);

INSERT INTO Jugador (nombre, apellido, fecha_nacimiento, peso, estatura, posicion, valor, id_equipo)
SELECT 'Jugador2', p.nombre, '2007-01-01', 70.0, 1.75, 'Delantero', 1500000, e.id_equipo 
FROM Equipo e JOIN Pais p ON e.id_pais = p.id_pais 
WHERE e.id_equipo NOT IN (@idMex, @idUSA, @idCol, @idEsp, @idArg, @idFra, @idCan, @idBra);
GO

-- =============================================
-- ACTUALIZAR VALORES DE EQUIPO
-- =============================================
UPDATE Equipo SET valor_total = (SELECT ISNULL(SUM(valor), 0) FROM Jugador WHERE Jugador.id_equipo = Equipo.id_equipo);
GO

-- =============================================
-- ASIGNACIÓN DE GRUPOS (Simulación de Grupos A, B, C, D)
-- =============================================
DECLARE @idMex INT = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección México');
DECLARE @idUSA INT = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección Estados Unidos');
DECLARE @idCan INT = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección Canadá');
DECLARE @idCol INT = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección Colombia');
DECLARE @idArg INT = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección Argentina');
DECLARE @idBra INT = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección Brasil');
DECLARE @idEsp INT = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección España');
DECLARE @idFra INT = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección Francia');

-- Grupo A
INSERT INTO GrupoEquipo (id_grupo, id_equipo) VALUES (1, @idMex), (1, @idCol), (1, @idFra);
-- Grupo B
INSERT INTO GrupoEquipo (id_grupo, id_equipo) VALUES (2, @idUSA), (2, @idEsp), (2, @idBra);
-- Grupo C
INSERT INTO GrupoEquipo (id_grupo, id_equipo) VALUES (3, @idCan), (3, @idArg);
GO

-- =============================================
-- PARTIDOS (Fase de Grupos)
-- =============================================
DECLARE @idMex INT = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección México');
DECLARE @idUSA INT = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección Estados Unidos');
DECLARE @idCan INT = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección Canadá');
DECLARE @idCol INT = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección Colombia');
DECLARE @idArg INT = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección Argentina');
DECLARE @idBra INT = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección Brasil');
DECLARE @idEsp INT = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección España');
DECLARE @idFra INT = (SELECT id_equipo FROM Equipo WHERE nombre = 'Selección Francia');

DECLARE @idEstAzteca INT = (SELECT id_estadio FROM Estadio WHERE nombre = 'Estadio Azteca');
DECLARE @idEstMiami INT = (SELECT id_estadio FROM Estadio WHERE nombre = 'Hard Rock Stadium');
DECLARE @idEstToronto INT = (SELECT id_estadio FROM Estadio WHERE nombre = 'BMO Field');

INSERT INTO Partido (fecha, id_equipo_local, id_equipo_visitante, id_estadio, id_grupo) VALUES
('2026-06-11 20:00:00', @idMex, @idFra, @idEstAzteca, 1),
('2026-06-15 18:00:00', @idCol, @idMex, @idEstAzteca, 1),
('2026-06-12 21:00:00', @idUSA, @idEsp, @idEstMiami, 2),
('2026-06-16 19:00:00', @idBra, @idUSA, @idEstMiami, 2),
('2026-06-13 17:00:00', @idCan, @idArg, @idEstToronto, 3);
GO
