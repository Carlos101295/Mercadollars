-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema MERCADOLLARS_DB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema MERCADOLLARS_DB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `MERCADOLLARS_DB` DEFAULT CHARACTER SET utf8 ;
USE `MERCADOLLARS_DB` ;

-- -----------------------------------------------------
-- Table `MERCADOLLARS_DB`.`Clientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MERCADOLLARS_DB`.`Clientes` (
  `idClientes` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `Fecha_nacimiento` DATE NULL,
  `Telefono` VARCHAR(9) NULL,
  `DNI` VARCHAR(10) NULL,
  `Puntos` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`idClientes`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MERCADOLLARS_DB`.`Promociones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MERCADOLLARS_DB`.`Promociones` (
  `idPromociones` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `Fecha_inicio` DATE NOT NULL,
  `Fecha_fin` DATE NOT NULL,
  `Descuento` INT NOT NULL DEFAULT 10,
  `Descripcion` VARCHAR(200) NULL,
  PRIMARY KEY (`idPromociones`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MERCADOLLARS_DB`.`Productos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MERCADOLLARS_DB`.`Productos` (
  `idProductos` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `Grupo_tipo` VARCHAR(45) NOT NULL,
  `Stock` INT NOT NULL,
  `Imagen` VARCHAR(45) NULL,
  `Ratio_aviso` INT NOT NULL,
  `Precio_sin_IVA` DECIMAL(10,2) NOT NULL,
  `IVA_aplicable` INT NOT NULL DEFAULT 21,
  `Precio_final` DECIMAL(10,2) NOT NULL,
  `Promociones_idPromociones` INT NULL,
  PRIMARY KEY (`idProductos`),
  INDEX `fk_Productos_Promociones1_idx` (`Promociones_idPromociones` ASC) VISIBLE,
  CONSTRAINT `fk_Productos_Promociones1`
    FOREIGN KEY (`Promociones_idPromociones`)
    REFERENCES `MERCADOLLARS_DB`.`Promociones` (`idPromociones`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MERCADOLLARS_DB`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MERCADOLLARS_DB`.`Usuario` (
  `idEmpleados` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `Apellidos` VARCHAR(45) NULL,
  `DNI` VARCHAR(10) NULL,
  `Contraseña` VARCHAR(45) NOT NULL DEFAULT '12345',
  `Email` VARCHAR(45) NOT NULL,
  `Telefono` VARCHAR(9) NULL,
  `Admin` ENUM('SI', 'NO') NOT NULL DEFAULT 'NO',
  PRIMARY KEY (`idEmpleados`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MERCADOLLARS_DB`.`Proveedores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MERCADOLLARS_DB`.`Proveedores` (
  `idProveedores` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `CIF` VARCHAR(10) NOT NULL,
  `Telefono` VARCHAR(9) NULL,
  `Tipos_productos` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idProveedores`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MERCADOLLARS_DB`.`Pedido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MERCADOLLARS_DB`.`Pedido` (
  `idPedido` INT NOT NULL AUTO_INCREMENT,
  `Cantidad` INT NOT NULL DEFAULT 1,
  `Proveedores_idProveedores` INT NOT NULL,
  `Usuario_idEmpleados` INT NOT NULL,
  PRIMARY KEY (`idPedido`),
  INDEX `fk_Pedido_Proveedores1_idx` (`Proveedores_idProveedores` ASC) VISIBLE,
  INDEX `fk_Pedido_Usuario1_idx` (`Usuario_idEmpleados` ASC) VISIBLE,
  CONSTRAINT `fk_Pedido_Proveedores1`
    FOREIGN KEY (`Proveedores_idProveedores`)
    REFERENCES `MERCADOLLARS_DB`.`Proveedores` (`idProveedores`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Pedido_Usuario1`
    FOREIGN KEY (`Usuario_idEmpleados`)
    REFERENCES `MERCADOLLARS_DB`.`Usuario` (`idEmpleados`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MERCADOLLARS_DB`.`Productos_has_Proveedores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MERCADOLLARS_DB`.`Productos_has_Proveedores` (
  `Productos_idProductos` INT NOT NULL,
  `Proveedores_idProveedores` INT NOT NULL,
  PRIMARY KEY (`Productos_idProductos`, `Proveedores_idProveedores`),
  INDEX `fk_Productos_has_Proveedores_Proveedores1_idx` (`Proveedores_idProveedores` ASC) VISIBLE,
  INDEX `fk_Productos_has_Proveedores_Productos1_idx` (`Productos_idProductos` ASC) VISIBLE,
  CONSTRAINT `fk_Productos_has_Proveedores_Productos1`
    FOREIGN KEY (`Productos_idProductos`)
    REFERENCES `MERCADOLLARS_DB`.`Productos` (`idProductos`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Productos_has_Proveedores_Proveedores1`
    FOREIGN KEY (`Proveedores_idProveedores`)
    REFERENCES `MERCADOLLARS_DB`.`Proveedores` (`idProveedores`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MERCADOLLARS_DB`.`Ventas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MERCADOLLARS_DB`.`Ventas` (
  `idVentas` INT NOT NULL,
  `Clientes_idClientes` INT NOT NULL,
  `Productos_idProductos` INT NOT NULL,
  `Usuario_idEmpleados` INT NOT NULL,
  `Fecha_venta` DATETIME NOT NULL,
  `Ticket` INT NOT NULL,
  PRIMARY KEY (`idVentas`),
  INDEX `fk_Clientes_has_Productos_Productos1_idx` (`Productos_idProductos` ASC) VISIBLE,
  INDEX `fk_Clientes_has_Productos_Clientes1_idx` (`Clientes_idClientes` ASC) VISIBLE,
  INDEX `fk_Clientes_has_Productos_Usuario1_idx` (`Usuario_idEmpleados` ASC) VISIBLE,
  CONSTRAINT `fk_Clientes_has_Productos_Clientes1`
    FOREIGN KEY (`Clientes_idClientes`)
    REFERENCES `MERCADOLLARS_DB`.`Clientes` (`idClientes`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Clientes_has_Productos_Productos1`
    FOREIGN KEY (`Productos_idProductos`)
    REFERENCES `MERCADOLLARS_DB`.`Productos` (`idProductos`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Clientes_has_Productos_Usuario1`
    FOREIGN KEY (`Usuario_idEmpleados`)
    REFERENCES `MERCADOLLARS_DB`.`Usuario` (`idEmpleados`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MERCADOLLARS_DB`.`Historico_ventas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MERCADOLLARS_DB`.`Historico_ventas` (
  `idProductos` INT NOT NULL,
  `idCliente` INT NOT NULL,
  `Nombre` VARCHAR(45) NOT NULL,
  `Grupo_tipo` VARCHAR(45) NOT NULL,
  `Precio_sin_IVA` DECIMAL(10,2) NOT NULL,
  `IVA_aplicable` INT NOT NULL DEFAULT 21,
  `Precio_final` DECIMAL(10,2) NOT NULL,
  `Ticket_ventas` INT NOT NULL,
  `Fecha_venta` DATETIME NOT NULL)
ENGINE = InnoDB;

USE `MERCADOLLARS_DB`;

DELIMITER $$
USE `MERCADOLLARS_DB`$$
CREATE DEFINER = CURRENT_USER TRIGGER `MERCADOLLARS_DB`.`Ventas_AFTER_INSERT` AFTER INSERT ON `Ventas` FOR EACH ROW
BEGIN
    DECLARE v_nombre VARCHAR(45);
    DECLARE v_grupo_tipo VARCHAR(45);
    DECLARE v_precio_sin_IVA DECIMAL(10,2);
    DECLARE v_IVA_aplicable INT;
    DECLARE v_precio_final DECIMAL(10,2);

    SELECT nombre, grupo_tipo, precio_sin_IVA, IVA_aplicable, precio_final
    INTO v_nombre, v_grupo_tipo, v_precio_sin_IVA, v_IVA_aplicable, v_precio_final
    FROM productos
    WHERE idProductos = NEW.Productos_idProductos;

    INSERT INTO historico_ventas (
        idProductos,
        idCliente,
        Nombre,
        Grupo_tipo,
        Precio_sin_IVA,
        IVA_aplicable,
        Precio_final,
        Ticket_ventas,
        Fecha_venta
    )
    VALUES (
        NEW.Productos_idProductos,
        NEW.Clientes_idClientes,
        v_nombre,
        v_grupo_tipo,
        v_precio_sin_IVA,
        v_IVA_aplicable,
        v_precio_final,
        NEW.ticket,
        NEW.fecha_venta
    );
END;$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
