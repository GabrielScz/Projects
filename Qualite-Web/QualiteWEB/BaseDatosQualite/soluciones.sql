USE optiqalumnos;

DROP PROCEDURE IF EXISTS insertarSolucion;
DELIMITER $$
CREATE PROCEDURE insertarSolucion(    /* Datos de Producto */
                                    IN    var_nombre          	VARCHAR(65),    --  1
                                    IN    var_marca			  	VARCHAR(139),   --  2
                                    IN    var_precioCompra    	DOUBLE,     	--  3
                                    IN    var_precioVenta 	  	DOUBLE,      	--  4
                                    IN    var_existencias     	INT,   		    --  5
                                    
                                    /* Datos de Accesorio */
                                    OUT    var_idProducto       INT,          	-- 6
                                    OUT    var_idSolucion       INT,          	-- 7
                                    OUT    var_codigoBarras     VARCHAR(48)   	-- 8	
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
        INSERT INTO solucion (idProducto)
                    VALUES(var_idProducto);
                    
        -- Obtenemos el ID de Accesorio que se genero:
        SET var_idSolucion = LAST_INSERT_ID();
        
        -- Generamos el codigo de barras:
        SET var_codigoBarras = CONCAT('OQ-P' , var_idProducto, '-S', var_idSolucion);
        UPDATE producto SET codigoBarras = var_codigoBarras WHERE idProducto = var_idProducto;
    END
$$
DELIMITER ;  
 
DROP PROCEDURE IF EXISTS actualizarSolucion;
DELIMITER $$
CREATE PROCEDURE actualizarSolucion(    

									 /* Datos de Producto */
                                    IN    var_nombre          VARCHAR(65),    --  1
                                    IN    var_marca			  VARCHAR(139),   --  2
                                    IN 	  var_codigoBarras 	  VARCHAR(48), 	  --  3
                                    IN    var_precioCompra    DOUBLE,     	  --  4
                                    IN    var_precioVenta 	  DOUBLE,      	  --  5
                                    IN    var_existencias     INT,   		  --  6
                                    IN    var_idProducto      INT             --  7
						
                )      
    BEGIN   
    
        -- Comenzamos Actualizamos los datos del Producto:
        UPDATE producto 
        SET nombre = var_nombre, marca = var_marca, codigoBarras = var_codigoBarras, 
			precioCompra = var_precioCompra, precioVenta = var_precioVenta, existencias = var_existencias
            
            WHERE  idProducto = var_idProducto;
        

END
$$
DELIMITER ;  

DROP PROCEDURE IF EXISTS activarSolucion;
DELIMITER $$
CREATE PROCEDURE activarSolucion (IN var_idProducto INT)
BEGIN
        UPDATE producto 
        SET estatus = 1
        WHERE var_idProducto = idProducto;
END
$$
DELIMITER ;  


DROP PROCEDURE IF EXISTS eliminarSolucion;
DELIMITER $$
CREATE PROCEDURE eliminarSolucion (IN var_idProducto INT)
BEGIN
        UPDATE producto 
        SET estatus = 0
        WHERE var_idProducto = idProducto;
END
$$
DELIMITER ;  


CALL insertarAccesorio('Limpiador', 'EyeCleaner', 1200.50, 4499.99, 12,
                      @out1, @out2, @out3);           -- Parametros de salida
                      
CALL actualizarAccesorio('Porta Lentes', 'OQ-05-02', 'Louis Vuitton', 599.99, 999.90, 8,
					   4);
                        
CALL eliminarAccesorio(4);

CALL activarAccesorio(4);


DROP VIEW IF EXISTS v_solucion;
CREATE VIEW v_solucion AS
SELECT p.idProducto, p.nombre, p.marca, p.codigoBarras, p.precioCompra, p.precioVenta, p.existencias, p.estatus, s.idSolucion 
FROM solucion AS s
INNER JOIN producto AS p ON p.idProducto = s.idProducto;


SELECT * FROM producto;
SELECT * FROM solucion;
SELECT * FROM v_solucion;
SELECT * FROM presupuesto_lentes_tratamientos;
SELECT * FROM tratamiento;
SET SQL_SAFE_UPDATES = 0;


SELECT * FROM v_solucion WHERE nombre LIKE '%Gu%' OR marca LIKE '%%' OR precioVenta LIKE '%%' OR precioCompra LIKE '%%' OR existencias LIKE '%%' OR codigoBarras LIKE '%%';