INSERT INTO PRODUCTOS VALUES (1,'leche','lácteos', 10, NULL, 5, 1.20, 4, 1.30, NULL);
INSERT INTO PRODUCTOS VALUES (2,'café','bebidas', 25, NULL, 12, 2.50, 10, 2.80, NULL);
INSERT INTO PRODUCTOS VALUES (3,'pan integral','panadería', 50, NULL, 8, 0.80, 4, 1.00, NULL);
INSERT INTO PRODUCTOS VALUES (4,'arroz','alimentación', 100, NULL, 20, 1.10, 4, 1.40, NULL);
INSERT INTO PRODUCTOS VALUES (5,'aceite de oliva','aceites', 40, NULL, 15, 3.20, 10, 3.60, NULL);
INSERT INTO PRODUCTOS VALUES (6,'manzanas','frutas', 60, NULL, 7, 1.00, 4, 1.20, NULL);



INSERT INTO CLIENTES VALUES (1, 'eufrasio', NULL, NULL, NULL, DEFAULT);
INSERT INTO CLIENTES VALUES (2, 'mariana', '1988-04-12', '654987321', '98765432X', DEFAULT);
INSERT INTO CLIENTES VALUES (3, 'sofia', '1995-10-02', '666333111', '11223344B', DEFAULT);
INSERT INTO CLIENTES VALUES (4, 'miguel', '1979-01-29', '612445566', '44332211C', DEFAULT);
INSERT INTO CLIENTES VALUES (5, 'lucía', '2000-07-15', '677223344', '55667788E', DEFAULT);
INSERT INTO CLIENTES VALUES (6, 'jorge', '1985-12-01', '699112233', '77889900G', DEFAULT);



INSERT INTO USUARIO VALUES (3, 'empleadito1', NULL, NULL, DEFAULT, 'empleadito1@gmail.com', NULL, DEFAULT);
INSERT INTO USUARIO VALUES (4, 'carlos', NULL, '12345678A', DEFAULT, 'carlos@gmail.com', '600123456', 'SI');
INSERT INTO USUARIO VALUES (5, 'laura', 'pérez', '87654321Z', 98765, 'laura@gmail.com', '622554433', DEFAULT);
INSERT INTO USUARIO VALUES (6, 'pedro', 'ruiz', '11221133D', DEFAULT, 'pedro@gmail.com', '699887766', DEFAULT);
INSERT INTO USUARIO VALUES (7, 'andrés', 'carrasco', '33445566F', 256477, 'andres@gmail.com', '688776655', DEFAULT);
INSERT INTO USUARIO VALUES (8, 'marta', 'lozano', '22113344H', DEFAULT, 'marta@gmail.com', '677889900', DEFAULT);



INSERT INTO PROVEEDORES VALUES (1, 'Supermercados El Buen Precio', 'A12345678', '912345678', 'Frutas y Verduras');
INSERT INTO PROVEEDORES VALUES (2, 'Alimentación Natural', 'B98765432', '923456789', 'Alimentos Orgánicos');
INSERT INTO PROVEEDORES VALUES (3, 'Bebidas del Sur', 'C12378945', '934567890', 'Bebidas y Jugos');
INSERT INTO PROVEEDORES VALUES (4, 'Limpieza Total', 'D45612387', '945678901', 'Productos de Limpieza');
INSERT INTO PROVEEDORES VALUES (5, 'Higiene y Cuidado', 'E78965432', '956789012', 'Higiene Personal');



INSERT INTO VENTAS VALUES (1, 1, 6, 3, CURRENT_TIMESTAMP(), 123456789);
INSERT INTO VENTAS VALUES (2, 2, 5, 4, CURRENT_TIMESTAMP(), 987654321);
INSERT INTO VENTAS VALUES (3, 3, 4, 5, CURRENT_TIMESTAMP(), 555666777);
INSERT INTO VENTAS VALUES (4, 4, 3, 8, CURRENT_TIMESTAMP(), 223344556);
INSERT INTO VENTAS VALUES (5, 5, 2, 6, CURRENT_TIMESTAMP(), 889900112);
INSERT INTO VENTAS VALUES (6, 6, 1, 7, CURRENT_TIMESTAMP(), 334455667);



INSERT INTO PROMOCIONES VALUES (1, 'Promo lácteos', '2025-01-01', '2025-01-15', 15, 'Descuento especial en productos lácteos.');
INSERT INTO PROMOCIONES VALUES (2, 'Promo bebidas', '2025-02-01', '2025-02-20', 10, 'Oferta limitada en bebidas.');
INSERT INTO PROMOCIONES VALUES (3, 'Promo panadería', '2025-03-05', '2025-03-25', 20, 'Descuento del 20% en panadería.');
INSERT INTO PROMOCIONES VALUES (4, 'Promo alimentación', '2025-04-10', '2025-04-30', 12, 'Descuentos varios en alimentación básica.');
INSERT INTO PROMOCIONES VALUES (5, 'Promo frutas', '2025-05-01', '2025-05-20', 18, 'Descuento por temporada en frutas frescas.');


INSERT INTO PRODUCTOS_HAS_PROVEEDORES VALUES
(1, 1),  -- leche → proveedor 1
(2, 1),  -- café → proveedor 1
(3, 2),  -- pan integral → proveedor 2
(4, 3),  -- arroz → proveedor 3
(5, 4),  -- aceite → proveedor 4
(6, 2);  -- manzanas → proveedor 2


INSERT INTO PEDIDO (idPedido, Cantidad, Proveedores_idProveedores, Usuario_idEmpleados) VALUES
(1, 50, 1, 3),   -- empleado 3 pide al proveedor 1
(2, 30, 2, 4),
(3, 80, 3, 5),
(4, 40, 4, 6),
(5, 60, 1, 7),
(6, 25, 2, 8);
