USE optiqalumnos;

DROP PROCEDURE IF EXISTS insertarLenteContacto;
DELIMITER $$
CREATE PROCEDURE insertarLenteContacto(    /* Datos de Producto */
                                    IN    var_nombre          	VARCHAR(65),    --  1
                                    IN    var_marca			  	VARCHAR(139),   --  2
                                    IN    var_precioCompra    	DOUBLE,     	--  3
                                    IN    var_precioVenta 	  	DOUBLE,      	--  4
                                    IN    var_existencias     	INT,   		    --  5
                                    IN	  var_keratometria		INT, 			--  6
                                    IN    var_fotografia 		LONGTEXT,       --  7
                                    
                                    /* Datos de Accesorio */
                                    OUT    var_idProducto       INT,          	--  8
                                    OUT    var_idLenteContacto  INT,        	--  9
                                    OUT    var_codigoBarras     VARCHAR(48)   	--  10	
                )                                    
    BEGIN        
        -- Comenzamos insertando los datos del Producto:
        INSERT INTO producto (nombre, marca, precioCompra,
                             precioVenta, existencias, estatus)
                    VALUES( var_nombre, var_marca, var_precioCompra, 
							var_precioVenta, var_existencias, 1);
        -- Obtenemos el ID de Producto que se generó:
        SET var_idProducto = LAST_INSERT_ID();

        -- Finalmente, insertamos en la tabla Accesorio:
        INSERT INTO lente_contacto (idProducto, keratometria, fotografia)
                    VALUES(var_idProducto, var_keratometria, var_fotografia);
        -- Obtenemos el ID de Accesorio que se genero:
        SET var_idLenteContacto = LAST_INSERT_ID();
        
        -- Generamos el codigo de barras:
        SET var_codigoBarras = CONCAT('OQ-P' , var_idProducto, '-LC', var_idLenteContacto);
        UPDATE producto SET codigoBarras = var_codigoBarras WHERE idProducto = var_idProducto;
    END
$$
DELIMITER ;  
 
DROP PROCEDURE IF EXISTS actualizarLenteContacto;
DELIMITER $$
CREATE PROCEDURE actualizarLenteContacto(    

									 /* Datos de Producto */
                                    IN    var_nombre          VARCHAR(65),    --  1
                                    IN    var_marca			  VARCHAR(139),   --  2
                                    IN 	  var_codigoBarras 	  VARCHAR(48), 	  --  3
                                    IN    var_precioCompra    DOUBLE,     	  --  4
                                    IN    var_precioVenta 	  DOUBLE,      	  --  5
                                    IN    var_existencias     INT,   		  --  6
                                    IN 	  var_keratometria	  INT, 			  --  7
                                    IN    var_fotografia 	  LONGTEXT,       --  8
                                    IN    var_idProducto      INT,            --  9
                                    IN    var_idLenteContacto INT			  --  10
						
                )      
    BEGIN   
    
        -- Comenzamos Actualizamos los datos del Producto:
        UPDATE producto 
        SET nombre = var_nombre, marca = var_marca, codigoBarras = var_codigoBarras, 
			precioCompra = var_precioCompra, precioVenta = var_precioVenta, existencias = var_existencias 
		
            WHERE  idProducto = var_idProducto;
            
		UPDATE lente_contacto
        SET keratometria = var_keratometria, fotografia = var_fotografia
        WHERE idLenteContacto = var_idLenteContacto;
END
$$
DELIMITER ;  

DROP PROCEDURE IF EXISTS activarLenteContacto;
DELIMITER $$
CREATE PROCEDURE activarLenteContacto (IN var_idProducto INT)
BEGIN
        UPDATE producto 
        SET estatus = 1
        WHERE var_idProducto = idProducto;
END
$$
DELIMITER ;  


DROP PROCEDURE IF EXISTS eliminarLenteContacto;
DELIMITER $$
CREATE PROCEDURE eliminarLenteContacto (IN var_idProducto INT)
BEGIN
        UPDATE producto 
        SET estatus = 0
        WHERE var_idProducto = idProducto;
END
$$
DELIMITER ;  


 CALL insertarLenteContacto('LenteContacto', 'Qualite', 1200, 1500, 12, 132, 124235253,
                       @out1, @out2, @out3);           -- Parametros de salida
                      
-- CALL actualizarAccesorio('Porta Lentes', 'OQ-05-02', 'Louis Vuitton', 599.99, 999.90, 8,
-- 					   4);
                        
-- CALL eliminarAccesorio(4);

-- CALL activarAccesorio(4);


DROP VIEW IF EXISTS v_lenteContacto;
CREATE VIEW v_lenteContacto AS
SELECT p.idProducto, lc.idLenteContacto, p.nombre, p.marca, p.codigoBarras, p.precioVenta, p.precioCompra, p.estatus, p.existencias, lc.keratometria, lc.fotografia
FROM lente_contacto AS lc
INNER JOIN producto AS p ON p.idProducto = lc.idProducto;


SELECT * FROM producto;
SELECT * FROM lente_contacto;
SELECT * FROM v_lenteContacto;
SET SQL_SAFE_UPDATES = 0;

DELETE FROM material WHERE idMaterial = 1;


SELECT * FROM v_accesorio WHERE nombre LIKE '%Gu%' OR marca LIKE '%%' OR precioVenta LIKE '%%' OR precioCompra LIKE '%%' OR existencias LIKE '%%' OR codigoBarras LIKE '%%';