DROP DATABASE IF EXISTS examenDiagnostico;
CREATE DATABASE examenDiagnostico;

USE examenDiagnostico;

-- ------------- TABLA PERSONA -------------- --
CREATE TABLE persona (
	idPersona 			INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nombre 				VARCHAR(50) NOT NULL,
	apellidos 			VARCHAR(50) NOT NULL,
	fechaRegistro 		INT NOT NULL
);

-- ------------- TABLA PARTICIPANTE -------------- --
CREATE TABLE participante (
    idParticipante			INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    idPersona				INT NOT NULL,
    edad         			INT,
    estado             		VARCHAR(50) NOT NULL,
    CONSTRAINT fk_participante_persona FOREIGN KEY (idPersona) 
                REFERENCES persona(idPersona) 
);

-- ------------- TABLA ASESOR -------------- --
CREATE TABLE asesor (
    idAsesor			INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    idPersona			INT NOT NULL,
    codigoAsesor        INT NOT NULL,
    departamento        VARCHAR(65),
    CONSTRAINT fk_asesor_persona FOREIGN KEY (idPersona) 
                REFERENCES persona(idPersona)
);

-- ------------- TABLA PROYECTO -------------- --
CREATE TABLE proyecto (
	idProyecto			INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    idAsesor			INT NOT NULL,
    idParticipante		INT NOT NULL,
    costo        		INT NOT NULL,
    nombre	        	VARCHAR(65),
    CONSTRAINT fk_proyecto_asesor FOREIGN KEY (idAsesor) 
                REFERENCES asesor(idAsesor),
	CONSTRAINT fk_proyecto_participante FOREIGN KEY (idParticipante) 
                REFERENCES participante(idParticipante)
);

INSERT INTO persona (nombre, apellidos, fechaRegistro) VALUES ('Carlos', 'Sanchez Palomino', 2003);
INSERT INTO persona (nombre, apellidos, fechaRegistro) VALUES ('Cristian', 'Leyva Collazo', 2003);
INSERT INTO persona (nombre, apellidos, fechaRegistro) VALUES ('Ximena', 'Rocha Trujillo', 2004);
INSERT INTO persona (nombre, apellidos, fechaRegistro) VALUES ('Jonathan', 'Zendejas Gutierrez', 2002);

SELECT * FROM persona;

INSERT INTO participante (idPersona, edad, estado) VALUES (1, 19, 'Guanajuato');
INSERT INTO participante (idPersona, edad, estado) VALUES (2, 19, 'Oaxaca');
INSERT INTO participante (idPersona, edad, estado) VALUES (3, 18, 'Yucatan');
INSERT INTO participante (idPersona, edad, estado) VALUES (4, 22, 'Jalisco');

SELECT * FROM participante;

INSERT INTO asesor (idPersona, codigoAsesor, departamento) VALUES (1, 1232, 'Gerencia');
INSERT INTO asesor (idPersona, codigoAsesor, departamento) VALUES (2, 3423, 'Desarrollo');
INSERT INTO asesor (idPersona, codigoAsesor, departamento) VALUES (3, 2353, 'Desarrollo');
INSERT INTO asesor (idPersona, codigoAsesor, departamento) VALUES (4, 9743, 'Ventas');

SELECT * FROM asesor;

INSERT INTO proyecto (idAsesor, idParticipante, costo, nombre) VALUES (1, 1, 220000, 'Jugueteria Mahuitlia');
INSERT INTO proyecto (idAsesor, idParticipante, costo, nombre) VALUES (1, 2, 1354000, 'Optica Qualite');
INSERT INTO proyecto (idAsesor, idParticipante, costo, nombre) VALUES (1, 3, 3550000, 'Ducati Bikes');
INSERT INTO proyecto (idAsesor, idParticipante, costo, nombre) VALUES (4, 3, 100000, 'Mariscos Don Camaron');

SELECT * FROM proyecto;



-- PARTE 1 --
SELECT nombre FROM persona WHERE idPersona = (SELECT idPersona FROM asesor WHERE idAsesor = (SELECT idAsesor FROM proyecto WHERE costo = (SELECT MAX(costo) FROM proyecto)));

SELECT nombre FROM persona WHERE idPersona = (SELECT idPersona FROM participante WHERE edad = (SELECT MIN(edad) FROM participante));

SELECT nombre FROM persona WHERE idPersona = (SELECT idPersona FROM participante WHERE idParticipante = (SELECT idParticipante FROM proyecto WHERE costo = (SELECT MIN(costo) FROM proyecto)));

SELECT nombre FROM persona WHERE idPersona = (SELECT idPersona FROM asesor WHERE idAsesor = (SELECT idAsesor FROM proyecto WHERE nombre = 'Optica Qualite'));

SELECT idParticipante FROM participante WHERE idPersona = (SELECT idPersona FROM persona WHERE fechaRegistro BETWEEN 2004 AND 2005);

