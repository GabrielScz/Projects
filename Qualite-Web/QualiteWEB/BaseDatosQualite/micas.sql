USE optiqalumnos;

DROP PROCEDURE IF EXISTS insertarMica;
DELIMITER $$
CREATE PROCEDURE insertarMica(    /* Datos de Mica */
                                    IN    var_nombre          	VARCHAR(65),    --  1
                                    IN    var_precioCompra    	DOUBLE,     	--  2
                                    IN    var_precioVenta 	  	DOUBLE,      	--  3
                                    
                                    /* Valores de retorno */
                                    OUT    var_idTipoMica       INT            	--  4	
                )                                    
    BEGIN        
        -- Comenzamos insertando los datos del Mica:
        INSERT INTO tipo_mica (nombre, precioCompra, precioVenta)
		VALUES( var_nombre, var_precioCompra, var_precioVenta);
                            
        -- Obtenemos el ID de Mica que se generó:
        SET var_idTipoMica = LAST_INSERT_ID();

END $$
DELIMITER ;  
 
DROP PROCEDURE IF EXISTS actualizarMica;
DELIMITER $$
CREATE PROCEDURE actualizarMica(    

									 /* Datos de Material */
                                    IN    var_nombre          VARCHAR(65),    --  1
                                    IN    var_precioCompra    DOUBLE,     	  --  2
                                    IN    var_precioVenta 	  DOUBLE,      	  --  3
                                    IN    var_idTipoMica      INT             --  4
						
                )      
    BEGIN   
        -- Comenzamos Actualizamos los datos del Producto:
        UPDATE tipo_mica 
        SET nombre = var_nombre, precioCompra = var_precioCompra, precioVenta = var_precioVenta
		WHERE  idTipoMica = var_idTipoMica;
        
END $$
DELIMITER ;  

DROP VIEW IF EXISTS v_tipomica;
CREATE VIEW v_tipomica AS
SELECT m.idTipoMica, m.nombre, m.precioCompra, m.precioVenta
FROM tipo_mica AS m;

SELECT * FROM tipo_mica;
SELECT * FROM v_tipomica;
SET SQL_SAFE_UPDATES = 0;
