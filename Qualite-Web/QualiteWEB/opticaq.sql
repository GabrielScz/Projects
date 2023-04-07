-- ---------------------------------------------------------------- --
-- Archivo: 04_DatosBase_Optica.sql                                 -- 
-- Version: 1.0                                                     --
-- Autor:   Miguel Angel Gil Rios   								--
-- Email:   angel.grios@gmail.com / mgil@utleon.edu.mx              --
-- Fecha de elaboracion: 29-12-2021                                 --
-- ---------------------------------------------------------------- --

USE optiqalumnos;
                      
-- Insercion de Catalogos Base
INSERT INTO tipo_mica(idTipoMica, nombre, precioCompra, precioVenta) 
             VALUES (1, 'Bifocal', 0.00, 0.00),
                    (2, 'Monofocal', 0.00, 0.00),
                    (3, 'Progresivo', 0.00, 0.00);
                    
INSERT INTO material(idMaterial, nombre, precioCompra, precioVenta)
            VALUES  (1, 'Cr', 0.00, 0.00),
                    (2, 'Cristal', 0.00, 0.00),
                    (3, 'Polarizado', 0.00, 0.00),
                    (4, 'Policarbonato', 0.00, 0.00);
                    
INSERT INTO tratamiento (idTratamiento, nombre, precioCompra, precioVenta, estatus)
            VALUES      (1, 'Antireflejante básico', 0.00, 0.00, 1),
                        (2, 'Blue free', 0.00, 0.00, 1),
                        (3, 'Entintado', 0.00, 0.00, 1),
                        (4, 'Fotocromático', 0.00, 0.00, 1);
                        
DROP PROCEDURE IF EXISTS insertarEmpleado;
DELIMITER $$
CREATE PROCEDURE insertarEmpleado(    /* Datos Personales */
                                    IN    var_nombre          VARCHAR(64),    --  1
                                    IN    var_apellidoPaterno VARCHAR(64),    --  2
                                    IN    var_apellidoMaterno VARCHAR(64),    --  3
                                    IN    var_genero          VARCHAR(2),     --  4
                                    IN    var_fechaNacimiento VARCHAR(11),    --  5
                                    IN    var_calle           VARCHAR(129),   --  6
                                    IN    var_numero          VARCHAR(20),    --  7
                                    IN    var_colonia         VARCHAR(40),    --  8
                                    IN    var_cp              VARCHAR(25),    --  9
                                    IN    var_ciudad          VARCHAR(40),    -- 10
                                    IN    var_estado          VARCHAR(40),    -- 11
                                    IN    var_telcasa         VARCHAR(20),    -- 12
                                    IN    var_telmovil        VARCHAR(20),    -- 13
                                    IN    var_email           VARCHAR(129),   -- 14
                                    
                                    /* Datos de Usuario */
                                    IN    var_nombreUsuario   VARCHAR(129),   -- 15
                                    IN    var_contrasenia     VARCHAR(129),   -- 16
                                    IN    var_rol             VARCHAR(25),    -- 17                                    
                                    
                                    /* Valores de Retorno */
                                    OUT    var_idPersona       INT,            -- 18
                                    OUT    var_idUsuario       INT,            -- 19
                                    OUT    var_idEmpleado      INT,            -- 20
                                    OUT    var_numeroUnico     VARCHAR(65),    -- 21
                                    OUT    var_lastToken       VARCHAR(65)     -- 22
                )                                    
    BEGIN        
        -- Comenzamos insertando los datos de la Persona:
        INSERT INTO persona (nombre, apellidoPaterno, apellidoMaterno, genero,
                             fechaNacimiento, calle, numero, colonia, cp, ciudad,
                             estado, telcasa, telmovil, email)
                    VALUES( var_nombre, var_apellidoPaterno, var_apellidoMaterno, 
                            var_genero, STR_TO_DATE(var_fechaNacimiento, '%d/%m/%Y'), 
                            var_calle, var_numero, var_colonia, var_cp, var_ciudad,
                            var_estado, var_telcasa, var_telmovil, var_email);
        -- Obtenemos el ID de Persona que se generó:
        SET var_idPersona = LAST_INSERT_ID();

 

        -- Insertamos los datos de seguridad del Empleado:
        INSERT INTO usuario ( nombreUsuario, contrasenia, rol) 
                    VALUES( var_nombreUsuario, var_contrasenia, var_rol);
        -- Obtenemos el ID de Usuario que se generó:
        SET var_idUsuario = LAST_INSERT_ID();

 

        --  Generamos el numero unico de empleado.        
        SET var_numeroUnico = '';
        --  Agregamos la primera letra del apellidoPaterno:
        IF  LENGTH(var_apellidoPaterno) >= 1 THEN
            SET var_numeroUnico = SUBSTRING(var_apellidoPaterno, 1, 1);
        ELSE
            SET var_numeroUnico = 'X';
        END IF;
        --  Agregamos la segunda letra del apellidoPaterno:
        IF  LENGTH(var_apellidoPaterno) >= 2 THEN
            SET var_numeroUnico = CONCAT(var_numeroUnico, SUBSTRING(var_apellidoPaterno, 2, 1));
        ELSE
            SET var_numeroUnico = CONCAT(var_numeroUnico, 'X');
        END IF;        
        --  Agregamos el timestamp:
        SET var_numeroUnico = CONCAT(var_numeroUnico, CAST(UNIX_TIMESTAMP() AS CHAR));
        -- Codificamos el numero unico generado:
        SET var_numeroUnico = MD5(var_numeroUnico);

 

        -- Finalmente, insertamos en la tabla Empleado:
        INSERT INTO empleado (idPersona, idUsuario, numeroUnico, estatus)
                    VALUES(var_idPersona, var_idUsuario, var_numeroUnico, 1);
        -- Obtenemos el ID del Empleado que se genero:
        SET var_idEmpleado = LAST_INSERT_ID();
    END
$$
DELIMITER ;  
 
DROP PROCEDURE IF EXISTS actualizarEmpleado;
DELIMITER $$
CREATE PROCEDURE actualizarEmpleado(    

									 /* Datos de Persona */
									IN    var_nombre          VARCHAR(64),    --  1
                                    IN    var_apellidoPaterno VARCHAR(64),    --  2
                                    IN    var_apellidoMaterno VARCHAR(64),    --  3
                                    IN    var_genero          VARCHAR(2),     --  4
                                    IN    var_fechaNacimiento VARCHAR(11),    --  5
                                    IN    var_calle           VARCHAR(129),   --  6
                                    IN    var_numero          VARCHAR(20),    --  7
                                    IN    var_colonia         VARCHAR(40),    --  8
                                    IN    var_cp              VARCHAR(25),    --  9
                                    IN    var_ciudad          VARCHAR(40),    -- 10
                                    IN    var_estado          VARCHAR(40),    -- 11
                                    IN    var_telcasa         VARCHAR(20),    -- 12
                                    IN    var_telmovil        VARCHAR(20),    -- 13
                                    IN    var_email           VARCHAR(129),   -- 14
                                    IN	  var_idPersona	  	  INT,			  -- 15
                                    
                                    /* Datos de Usuario */
                                    IN    var_nombreUsuario   VARCHAR(129),   -- 16
                                    IN    var_contrasenia     VARCHAR(129),   -- 17
                                    IN    var_rol             VARCHAR(25),    -- 18  
                                    IN	  var_idUsuario		  INT, 			  -- 19
                                    
                                    /* Valores de Empleado */
                                    IN    var_estatus		  INT    	      -- 21
                                    
                                    
                )      
    BEGIN   
    
        -- Comenzamos Actualizamos los datos de la Persona:
        UPDATE persona 
        SET nombre = var_nombre, apellidoPaterno = var_apellidoPaterno ,apellidoMaterno = var_apellidoMaterno, 
			genero = var_genero, fechaNacimiento = STR_TO_DATE(var_fechaNacimiento, '%d/%m/%Y'), calle = var_calle, numero = var_numero, colonia = var_colonia, cp = var_cp, 
			ciudad = var_ciudad, estado = var_estado, telcasa = var_telcasa, telmovil = var_telmovil, email = var_email
            
            WHERE var_idPersona = idPersona;
				
    
        -- Actualizamos los datos del Usuario:
        UPDATE usuario
        SET nombreUsuario = var_nombreUsuario, contrasenia = var_contrasenia, rol = var_rol
        
        WHERE var_idUsuario = idUsuario;


        -- Finalmente, Actualizamos en la tabla Empleado:
        UPDATE empleado 
        SET estatus = var_estatus
        
        WHERE var_idPersona = idPersona;
        

END
$$
DELIMITER ;  

DROP PROCEDURE IF EXISTS activarEmpleado;
DELIMITER $$
CREATE PROCEDURE activarEmpleado (IN var_idEmpleado INT)
BEGIN
        UPDATE empleado 
        SET estatus = 1
        WHERE var_idEmpleado = idEmpleado;
END
$$
DELIMITER ;  


DROP PROCEDURE IF EXISTS eliminarEmpleado;
DELIMITER $$
CREATE PROCEDURE eliminarEmpleado (IN var_idEmpleado INT)
BEGIN
        UPDATE empleado 
        SET estatus = 0
        WHERE var_idEmpleado = idEmpleado;
END
$$
DELIMITER ;  


CALL insertarEmpleado('Carlos', 'Sanchez', 'Palomino', 'M', '29/05/2003',
					  'Bosques Reales', '113', 'Bosques Reales', '37536', 'Leon', 'Guanajuato', '4773922823', '4775120091', 'gab@gmail.com',
					  'Gabriel29', 'RainRTX2070s', 'Administrador', 
                      @out1, @out2, @out3, @out4, @out5);           -- Parametros de salida
                      
CALL actualizarEmpleado('Carlos', 'Sanchez', 'Palomino', 'M', '29/05/2003',
						'Bosques Reales', '113', 'Pradera del Bosque', '37536', 'Leon', 'Guanajuato', '4773922823', '4775120091', 'gab@gmail.com',
                        1,
                        'Gabriel29', 'RainRTX2070s', 'Administrador', 
                        1,
                        1);
                        
                        
CALL eliminarEmpleado(1);

CALL activarEmpleado(1);


DROP VIEW IF EXISTS v_empleado;
CREATE VIEW v_empleado AS
SELECT p.idPersona, p.nombre, p.apellidoPaterno, p.apellidoMaterno, p.genero, p.fechaNacimiento, p.calle, p.numero, p.colonia, p.cp, p.ciudad, p.estado, p.telcasa, p.telmovil, p.email, u.idUsuario, u.nombreUsuario, u.contrasenia, u.lastToken, u.dateLastToken, u.rol, e.idEmpleado, e.numeroUnico, e.estatus 
FROM persona AS p 
INNER JOIN usuario AS u ON p.idPersona = u.idUsuario
INNER JOIN empleado AS e ON u.idUsuario = e.idEmpleado;

SELECT * FROM v_persona WHERE nombre LIKE '%Car%' OR apellidoPaterno LIKE '%%' OR apellidoMaterno LIKE '%%' OR ciudad LIKE '%%' OR cp LIKE '%%' OR estado LIKE '%%';

SELECT * FROM persona;
SELECT * FROM empleado;
SELECT * FROM usuario;
SELECT * FROM v_empleado;
SET SQL_SAFE_UPDATES = 0;
                 