USE optiqalumnos;

DROP PROCEDURE IF EXISTS insertarCliente;
DELIMITER $$
CREATE PROCEDURE insertarCliente(    /* Datos Personales */
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
                                    
                                    /* Datos de Cliente */
                                    OUT    var_idPersona       INT,            -- 15
                                    OUT    var_idCliente       INT,            -- 16
                                    OUT    var_numeroUnico     VARCHAR(65)     -- 17
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



        --  Generamos el numero unico de cliente.        
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

 

        -- Finalmente, insertamos en la tabla cliente:
        INSERT INTO cliente (idPersona, numeroUnico, estatus)
                    VALUES(var_idPersona, var_numeroUnico, 1);
        -- Obtenemos el ID del Cliente que se genero:
        SET var_idCliente = LAST_INSERT_ID();
    END
$$
DELIMITER ;  
 
DROP PROCEDURE IF EXISTS actualizarCliente;
DELIMITER $$
CREATE PROCEDURE actualizarCliente(    

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
                                    IN    var_estatus		  INT,    	      -- 15
                                    IN	  var_idPersona	  	  INT,			  -- 16
                                    IN 	  var_idCliente 	  INT 			  -- 17
                                    
                                    
							
                )      
    BEGIN   
    
        -- Comenzamos Actualizamos los datos de la Persona:
        UPDATE persona 
        SET nombre = var_nombre, apellidoPaterno = var_apellidoPaterno ,apellidoMaterno = var_apellidoMaterno, 
			genero = var_genero, fechaNacimiento = STR_TO_DATE(var_fechaNacimiento, '%d/%m/%Y'), calle = var_calle, numero = var_numero, colonia = var_colonia, cp = var_cp, 
			ciudad = var_ciudad, estado = var_estado, telcasa = var_telcasa, telmovil = var_telmovil, email = var_email
            
            WHERE var_idPersona = idPersona;
				
                
        -- Finalmente, Actualizamos en la tabla Cliente:
        UPDATE cliente 
        SET estatus = var_estatus
        
        WHERE var_idPersona = idPersona;
        

END
$$
DELIMITER ;  

DROP PROCEDURE IF EXISTS activarCliente;
DELIMITER $$
CREATE PROCEDURE activarCliente (IN var_idCliente INT)
BEGIN
        UPDATE cliente 
        SET estatus = 1
        WHERE var_idCliente = idCliente;
END
$$
DELIMITER ;  


DROP PROCEDURE IF EXISTS eliminarCliente;
DELIMITER $$
CREATE PROCEDURE activarCliente (IN var_idCliente INT)
BEGIN
        UPDATE cliente 
        SET estatus = 0
        WHERE var_idCliente = idCliente;
END
$$
DELIMITER ;  


CALL insertarCliente('Carlos', 'Sanchez', 'Palomino', 'M', '29/05/2003',
					  'Bosques Reales', '113', 'Bosques Reales', '37536', 'Leon', 'Guanajuato', '4773922823', '4775120091', 'gab@gmail.com',
                      @out1, @out2, @out3);           -- Parametros de salida
                      
CALL actualizarCliente('Carlos', 'Sanchez', 'Palomino', 'M', '29/05/2003',
						'Bosques Reales', '113', 'Pradera del Bosque', '37536', 'Leon', 'Guanajuato', '4773922823', '4775120091', 'gab@gmail.com',
                        1, 1, 1);
                        
                        
CALL eliminarCliente(1);

CALL activarCliente(1);


DROP VIEW IF EXISTS v_cliente;
CREATE VIEW v_cliente AS
SELECT p.idPersona, p.nombre, p.apellidoPaterno, p.apellidoMaterno, p.genero, p.fechaNacimiento, p.calle, p.numero, p.colonia, p.cp, p.ciudad, p.estado, p.telcasa, p.telmovil, p.email, c.idCliente, c.numeroUnico, c.estatus 
FROM persona AS p 
INNER JOIN cliente AS c ON p.idPersona = c.idCliente;

SELECT * FROM v_cliente WHERE nombre LIKE '%Car%' OR apellidoPaterno LIKE '%%' OR apellidoMaterno LIKE '%%' OR ciudad LIKE '%%' OR cp LIKE '%%' OR estado LIKE '%%';

SELECT * FROM persona;
SELECT * FROM cliente;
SELECT * FROM v_cliente;
SET SQL_SAFE_UPDATES = 0;