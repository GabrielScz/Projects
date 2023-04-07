-- DDL HELP DESK V.1
-- AUTOR: CARLOS GABRIEL SANCHEZ PALOMINO
-- FECHA: 14-02-2023

DROP DATABASE IF EXISTS HelpDesk;
CREATE DATABASE HelpDesk;
USE HelpDesk;

DROP TABLE IF EXISTS usuario;
CREATE TABLE usuario(
	idUsuario INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombreUsuario VARCHAR(40) NOT NULL,
    contrasenia VARCHAR(20) NOT NULL,
    rol VARCHAR(20) NOT NULL,
    ultimaConexion VARCHAR(20)
);

DROP TABLE IF EXISTS departamento;
CREATE TABLE departamento(
	idDepartamento INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombreDepartamento VARCHAR(40) NOT NULL,
    sucursal VARCHAR(30) NOT NULL
);

DROP TABLE IF EXISTS empleado;
CREATE TABLE empleado(
	idEmpleado INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombreEmpleado VARCHAR(40) NOT NULL,
    primerApellido VARCHAR(40) NOT NULL,
    segundoApellido VARCHAR(40),
    rfc VARCHAR(15),
    email VARCHAR(40),
    telefono VARCHAR(20),
    fotografia LONGTEXT,
    
    idUsuario INT NOT NULL,
    idDepartamento INT NOT NULL,
    
    FOREIGN KEY (idUsuario) REFERENCES usuario(idUsuario),
    FOREIGN KEY (idDepartamento) REFERENCES departamento(idDepartamento)
);

DROP TABLE IF EXISTS ticket;
CREATE TABLE ticket(
	idTicket INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    dispositivo VARCHAR(30) NOT NULL,
    descripcion VARCHAR(200) NOT NULL,
    fechaHora DATETIME NOT NULL,
    estatus INT NOT NULL, -- Estatus: 'Registrado', 'Pendiente', 'Atendido' --
    
    idEmpleado INT NOT NULL,
    
    FOREIGN KEY (idEmpleado) REFERENCES empleado(idEmpleado)
);