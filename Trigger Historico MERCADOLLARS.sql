DELIMITER $$
CREATE TRIGGER TG_HISTORICO_VENTAS
AFTER INSERT ON VENTAS FOR EACH ROW
BEGIN
	INSERT INTO historico_ventas VALUES (NEW.Productos_idProductos, new.Clientes_idClientes, new.productos.nombre, new.productos.grupo/tipo, new.productos.precio_sin_IVA, new.productos.IVA_aplicable, new.productos.precio_final, new.ticket, new.fecha_venta);
END;$$

DELIMITER ;