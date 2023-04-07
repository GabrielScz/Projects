USE optiqalumnos;

DROP VIEW IF EXISTS v_ventaProducto;
CREATE VIEW v_ventaProducto AS
SELECT vp.idVenta, vp.idProducto, vp.cantidad, vp.precioUnitario, vp.descuento
FROM venta_producto AS vp;

SELECT * FROM v_ventaProducto;

DELIMITER //
CREATE TRIGGER actualizar_existencias
AFTER INSERT ON venta_producto
FOR EACH ROW
BEGIN
    UPDATE producto 
    SET existencias = existencias - NEW.cantidad
    WHERE idProducto = NEW.idProducto;
END //
DELIMITER ;

SELECT * FROM examen_vista;
SELECT * FROM graduacion;

INSERT INTO graduacion VALUES (5, 9.8, 9.7, 9.1, 9.1, 8, 8, 10);
INSERT INTO examen_vista VALUES (5, 23425219, 2, 2, 5, current_date());

-- Vista examen de vista

DROP VIEW IF EXISTS v_examenVista;
CREATE VIEW v_examenVista AS
SELECT ev.idExamenVista, ev.idEmpleado, ev.idCliente, ev.idGraduacion, ev.clave
FROM examen_vista AS ev;

SELECT * FROM v_examenVista;

SELECT * FROM venta;
SELECT * FROM venta_presupuesto;
SELECT * FROM presupuesto_lentescontacto;
SELECT * FROM presupuesto;
SELECT * FROM presupuesto_lentes;

DELIMITER //
CREATE TRIGGER actualizar_existencias_lente_contacto
AFTER INSERT ON presupuesto_lentescontacto
FOR EACH ROW
BEGIN
    DECLARE producto_id INT;
    SELECT idProducto INTO producto_id FROM lente_contacto WHERE idLenteContacto = NEW.idLenteContacto;
    UPDATE producto 
    SET existencias = existencias - NEW.cantidad
    WHERE idProducto = producto_id;
END //
DELIMITER ;

-- Venta Lentes --

-- Vista tipo mica -- 
DROP VIEW IF EXISTS v_tipoMica;
CREATE VIEW v_tipoMica AS
SELECT tm.idTipoMica, tm.nombre, tm.precioCompra, tm.precioVenta
FROM tipo_mica AS tm;

SELECT * FROM v_tipoMica;

-- Vista armazon -- 
DROP VIEW IF EXISTS v_armazon;
CREATE VIEW v_armazon AS
SELECT a.idArmazon, a.idProducto, a.modelo, a.color, a.dimensiones, a.descripcion, p.*
FROM armazon AS a INNER JOIN producto AS p ON a.idProducto = p.idProducto;

SELECT * FROM v_armazon;
SELECT * FROM armazon;


SELECT * FROM v_tratamiento;
SELECT * FROM tratamiento;
SELECT * FROM tipo_mica;
SELECT * FROM material;

SELECT * FROM presupuesto_lentes;