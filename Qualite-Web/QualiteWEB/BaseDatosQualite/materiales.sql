USE optiqalumnos;

DROP PROCEDURE IF EXISTS insertarMaterial;
DELIMITER $$
CREATE PROCEDURE insertarMaterial(    /* Datos de Material */
                                    IN    var_nombre          	VARCHAR(65),    --  1
                                    IN    var_precioCompra    	DOUBLE,     	--  2
                                    IN    var_precioVenta 	  	DOUBLE,      	--  3
                                    
                                    /* Valores de retorno */
                                    OUT    var_idMaterial       INT            	--  4	
                )                                    
    BEGIN        
        -- Comenzamos insertando los datos del Material:
        INSERT INTO material (nombre, precioCompra, precioVenta)
		VALUES( var_nombre, var_precioCompra, var_precioVenta);
                            
        -- Obtenemos el ID de Material que se generó:
        SET var_idMaterial = LAST_INSERT_ID();

END $$
DELIMITER ;  
 
DROP PROCEDURE IF EXISTS actualizarMaterial;
DELIMITER $$
CREATE PROCEDURE actualizarMaterial(    

									 /* Datos de Material */
                                    IN    var_nombre          VARCHAR(65),    --  1
                                    IN    var_precioCompra    DOUBLE,     	  --  2
                                    IN    var_precioVenta 	  DOUBLE,      	  --  3
                                    IN    var_idMaterial      INT             --  4
						
                )      
    BEGIN   
        -- Comenzamos Actualizamos los datos del Producto:
        UPDATE material 
        SET nombre = var_nombre, precioCompra = var_precioCompra, precioVenta = var_precioVenta
		WHERE  idMaterial = var_idMaterial;
        
END $$
DELIMITER ;  

CALL insertarMaterial('Carbono', 2999, 4499, @out1); 
                      
CALL actualizarMaterial('Policarbonato',  900, 1200,
					   4);

DROP VIEW IF EXISTS v_material;
CREATE VIEW v_material AS
SELECT m.idMaterial, m.nombre, m.precioCompra, m.precioVenta
FROM material AS m;

SELECT * FROM material;
SELECT * FROM v_material;
SET SQL_SAFE_UPDATES = 0;