USE optiqalumnos;

DROP PROCEDURE IF EXISTS insertarTratamiento;
DELIMITER $$
CREATE PROCEDURE insertarTratamiento(    /* Datos de Tratamiento */
                                    IN    var_nombre          	VARCHAR(65),    --  1
                                    IN    var_primerApellido    	VARCHAR(50),     	--  2
                                    IN    var_p 	  	VARCHAR(50),      	--  3
                                    
                                    /* Valores de retorno */
                                    OUT    var_idTratamiento       INT            	--  4	
                )                                    
    BEGIN        
        -- Comenzamos insertando los datos del Tratamiento:
        INSERT INTO tratamiento (nombre, precioCompra, precioVenta, estatus)
		VALUES( var_nombre, var_precioCompra, var_precioVenta, 1);
                            
        -- Obtenemos el ID de Tratamiento que se generó:
        SET var_idTratamiento = LAST_INSERT_ID();

END $$
DELIMITER ;  
 
DROP PROCEDURE IF EXISTS actualizarTratamiento;
DELIMITER $$
CREATE PROCEDURE actualizarTratamiento(    

									 /* Datos de Material */
                                    IN    var_nombre          VARCHAR(65),    --  1
                                    IN    var_precioCompra    DOUBLE,     	  --  2
                                    IN    var_precioVenta 	  DOUBLE,      	  --  3
                                    IN    var_idTratamiento      INT          --  4
						
                )      
    BEGIN   
        -- Comenzamos Actualizamos los datos del Producto:
        UPDATE tratamiento 
        SET nombre = var_nombre, precioCompra = var_precioCompra, precioVenta = var_precioVenta
		WHERE  idTratamiento = var_idTratamiento;
        
END $$
DELIMITER ;  

DROP PROCEDURE IF EXISTS activarTratamiento;
DELIMITER $$
CREATE PROCEDURE activarTratamiento (IN var_idTratamiento INT)
BEGIN
        UPDATE tratamiento 
        SET estatus = 1
        WHERE var_idTratamiento = idTratamiento;
END $$
DELIMITER ;  

DROP PROCEDURE IF EXISTS eliminarTratamiento;
DELIMITER $$
CREATE PROCEDURE eliminarTratamiento (IN var_idTratamiento INT)
BEGIN
        UPDATE tratamiento 
        SET estatus = 0
        WHERE var_idTratamiento = idTratamiento;
END $$
DELIMITER ;  

CALL insertarAccesorio('Limpiador', 'EyeCleaner', 1200.50, 4499.99, 12,
                      @out1, @out2, @out3);           -- Parametros de salida
                      
CALL actualizarTratamiento('Fotocromatico',  1200, 1900, 4);

DROP VIEW IF EXISTS v_tratamiento;
CREATE VIEW v_tratamiento AS
SELECT t.idTratamiento, t.nombre, t.precioCompra, t.precioVenta, t.estatus
FROM tratamiento AS t;

SELECT * FROM tratamiento;
SELECT * FROM v_tratamiento;
SET SQL_SAFE_UPDATES = 0;
