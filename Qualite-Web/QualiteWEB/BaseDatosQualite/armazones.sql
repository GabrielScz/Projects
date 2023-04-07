USE optiqalumnos;

DROP PROCEDURE IF EXISTS insertarArmazon;
DELIMITER $$
CREATE PROCEDURE insertarArmazon(    /* Datos de Producto */
                                    IN    var_nombre          	VARCHAR(65),    --  1
                                    IN    var_marca			  	VARCHAR(65),    --  2
                                    IN    var_modelo			VARCHAR(65),    --  3
                                    IN    var_color			  	VARCHAR(65),    --  4
                                    IN    var_dimensiones	 	VARCHAR(65),    --  5
                                    IN    var_descripcion		VARCHAR(65),    --  6
                                    IN    var_precioCompra    	DOUBLE,     	--  7
                                    IN    var_precioVenta 	  	DOUBLE,      	--  8
                                    IN    var_existencias     	INT,   		    --  9
                                    
                                    /* Datos de Accesorio */
                                    OUT    var_idProducto       INT,          	-- 10
                                    OUT    var_idArmazon        INT,          	-- 11
                                    OUT    var_codigoBarras     VARCHAR(48)   	-- 12	
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
        INSERT INTO armazon (idProducto, modelo, color, dimensiones, descripcion, fotografia)
                    VALUES(var_idProducto, var_modelo, var_color, var_dimensiones, var_descripcion, '21318471JABDAHBDQW14');
                    
        -- Obtenemos el ID de Accesorio que se genero:
        SET var_idArmazon = LAST_INSERT_ID();
        
        -- Generamos el codigo de barras:
        SET var_codigoBarras = CONCAT('OQ-P' , var_idProducto, '-A', var_idArmazon);
UPDATE producto 
SET 
    codigoBarras = var_codigoBarras
WHERE
    idProducto = var_idProducto;
    END $$
DELIMITER ;  
 
DROP PROCEDURE IF EXISTS actualizarArmazon;
DELIMITER $$
CREATE PROCEDURE actualizarArmazon(    

									 /* Datos de Producto */
									IN    var_nombre          	VARCHAR(65),    --  1
                                    IN    var_marca			  	VARCHAR(65),    --  2
                                    IN 	  var_codigoBarras 		VARCHAR(65),    --  3
                                    IN    var_modelo			VARCHAR(65),    --  4
                                    IN    var_color			  	VARCHAR(65),    --  5
                                    IN    var_dimensiones	 	VARCHAR(65),    --  6
                                    IN    var_descripcion		VARCHAR(65),    --  7
                                    IN    var_precioCompra    	DOUBLE,     	--  8
                                    IN    var_precioVenta 	  	DOUBLE,      	--  9
                                    IN    var_existencias     	INT,   		    --  10
                                    IN    var_idProducto      	INT,            --  11
                                    IN	  var_idArmazon			INT				--  12
						
                )      
    BEGIN   
    
        -- Comenzamos Actualizamos los datos del Producto:
        UPDATE producto 
        SET nombre = var_nombre, marca = var_marca, codigoBarras = var_codigoBarras, 
			precioCompra = var_precioCompra, precioVenta = var_precioVenta, existencias = var_existencias
            
            WHERE  idProducto = var_idProducto;
            
        UPDATE armazon
        SET modelo = var_modelo, color = var_color, dimensiones = var_dimensiones, descripcion = var_descripcion
        
        WHERE var_idArmazon = idArmazon;

END $$
DELIMITER ;  

DROP PROCEDURE IF EXISTS activarArmazon;
DELIMITER $$
CREATE PROCEDURE activarArmazon (IN var_idProducto INT)
BEGIN
        UPDATE producto 
        SET estatus = 1
        WHERE var_idProducto = idProducto;
END
$$
DELIMITER ;  

DROP PROCEDURE IF EXISTS eliminarArmazon;
DELIMITER $$
CREATE PROCEDURE eliminarArmazon (IN var_idProducto INT)
BEGIN
        UPDATE producto 
        SET estatus = 0
        WHERE var_idProducto = idProducto;
END
$$
DELIMITER ;

DROP VIEW IF EXISTS v_armazon;
CREATE VIEW v_armazon AS
SELECT  a.modelo, a.color, a.dimensiones, a.descripcion, a.fotografia,  p.*
FROM armazon AS a
INNER JOIN producto AS p ON p.idProducto = a.idProducto;

SELECT * FROM producto;
SELECT * FROM armazon;
SELECT * FROM v_armazon;
SET SQL_SAFE_UPDATES = 0;

SELECT * FROM v_armazon WHERE nombre LIKE '%Gu%' OR marca LIKE '%%' OR precioVenta LIKE '%%' OR precioCompra LIKE '%%' OR existencias LIKE '%%' OR codigoBarras LIKE '%%';