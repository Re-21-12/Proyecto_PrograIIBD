	CREATE DATABASE Fabrica_de_sillas;
	USE Fabrica_de_sillas;
	 CREATE TABLE material (
	  id_material INT NOT NULL AUTO_INCREMENT,
	  nombre VARCHAR(255) NOT NULL,
	  costo DECIMAL(10,2) NOT NULL,
	  cantidad INT	NOT NULL,
	  PRIMARY KEY (id_material)
	); 
	CREATE TABLE silla (
	  id_silla INT NOT NULL AUTO_INCREMENT,
	  nombre VARCHAR(255) NOT NULL,
	  costo DECIMAL(10,2) NOT NULL,
	  precio_venta DECIMAL(10,2) NOT NULL,
	  id_material INT NOT NULL,
	   PRIMARY KEY (id_silla),
	foreign KEY	(id_material) REFERENCES material (id_material)
	 
	);
	--	 SELECT * FROM silla;
	CREATE TABLE silla_material (
	  -- id_silla_material int not null, ?
	  id_silla INT NOT NULL,
	  id_material INT NOT NULL,
	  -- aqui establece las dos como claves priamrias?
	  PRIMARY KEY (id_silla, id_material),
	  FOREIGN KEY (id_silla) REFERENCES silla (id_silla),
	  FOREIGN KEY (id_material) REFERENCES material (id_material)
	);
	USE fabrica_de_sillas;
	SELECT * FROM silla_material;
	CREATE TABLE proveedor (
	  id_proveedor INT NOT NULL AUTO_INCREMENT,
	  id_material INT NOT NULL,
	  nombre VARCHAR(255) NOT NULL,
	  direccion VARCHAR(255) NOT NULL,
	  telefono VARCHAR(255) NOT NULL,
	  correo_electronico VARCHAR(255) NOT NULL,
	  PRIMARY KEY (id_proveedor),
	  FOREIGN KEY (id_material) REFERENCES material (id_material)
	);
	
	SELECT user, host FROM mysql.user;
	 SELECT * FROM proveedor;
	
	 create TABLE distribuidor (
	   id_distribuidor INT NOT NULL AUTO_INCREMENT,
	   id_silla INT NOT NULL,
	   nombre VARCHAR(255) NOT NULL,
	   direccion VARCHAR(255) NOT NULL,
	   telefono VARCHAR(255) NOT NULL,
	   correo_electronico VARCHAR(255) NOT NULL,
	   PRIMARY KEY (id_distribuidor),
	   FOREIGN KEY (id_silla) REFERENCES silla (id_silla)
	 );
	SELECT * FROM distribuidor;
	CREATE DATABASE Fabrica_de_sillas;
	USE Fabrica_de_sillas;
	 CREATE TABLE material (
	  id_material INT NOT NULL AUTO_INCREMENT,
	  nombre VARCHAR(255) NOT NULL,
	  costo DECIMAL(10,2) NOT NULL,
	  cantidad INT	NOT NULL,
	  PRIMARY KEY (id_material)
	); 
	CREATE TABLE silla (
	  id_silla INT NOT NULL AUTO_INCREMENT,
	  nombre VARCHAR(255) NOT NULL,
	  costo DECIMAL(10,2) NOT NULL,
	  precio_venta DECIMAL(10,2) NOT NULL,
	  id_material INT NOT NULL,
	   PRIMARY KEY (id_silla),
	foreign KEY	(id_material) REFERENCES material (id_material)
	 
	);
	SELECT * FROM silla;
	CREATE TABLE silla_material (
	  -- id_silla_material int not null, ?
	  id_silla INT NOT NULL,
	  id_material INT NOT NULL,
	  -- aqui establece las dos como claves priamrias?
	  PRIMARY KEY (id_silla, id_material),
	  FOREIGN KEY (id_silla) REFERENCES silla (id_silla),
	  FOREIGN KEY (id_material) REFERENCES material (id_material)
	);
	USE fabrica_de_sillas;
	SELECT * FROM silla_material;-- 
	CREATE TABLE proveedor_material (
	  id_proveedor_material INT NOT NULL AUTO_INCREMENT,
	  id_material INT NOT NULL,
	  id_proveedor INT	 NOT NULL,
	  PRIMARY KEY (id_proveedor_material),
	  FOREIGN KEY (id_material) REFERENCES material (id_material),
	  FOREIGN KEY (id_proveedor) REFERENCES proveedor (id_proveedor)
	
	);
	SELECT * FROM proveedor_material;
	CREATE TABLE distribuidor_silla (
	  id_distribuidor_silla INT NOT NULL AUTO_INCREMENT,
	  id_silla INT NOT NULL,
	  id_distribuidor INT	 NOT NULL,
	  PRIMARY KEY (id_distribuidor_silla),
	  FOREIGN KEY (id_silla) REFERENCES silla (id_silla),
	  FOREIGN KEY (id_distribuidor) REFERENCES distribuidor (id_distribuidor)
	
	);
	SELECT * FROM distribuidor_silla;
	CREATE TABLE cliente (
	  id_cliente INT NOT NULL AUTO_INCREMENT,
	  nombre VARCHAR(255) NOT NULL,
	  telefono VARCHAR(255) NOT NULL,
	  PRIMARY KEY (id_cliente)
	);
	CREATE TABLE Factura (
	  id_Factura INT NOT NULL AUTO_INCREMENT,
	  fecha DATETIME NOT NULL,
	  telefono VARCHAR(255) NOT NULL,
	  precio_venta Double NOT NULL,
	  PRIMARY KEY (id_Factura)
	);
	CREATE TABLE Factura_cliente (
	  id_Factura_cliente INT NOT NULL AUTO_INCREMENT,
	  id_cliente INT NOT NULL,
	  id_distribuidor INT NOT NULL,
	  id_Factura INT NOT NULL,
	  PRIMARY KEY (id_Factura_cliente),
	   FOREIGN KEY (id_cliente) REFERENCES cliente (id_cliente),
		FOREIGN KEY (id_distribuidor) REFERENCES distribuidor (id_distribuidor),
	   FOREIGN KEY (id_Factura) REFERENCES Factura (id_Factura)
	);
ALTER TABLE cliente
ADD COLUMN id_Factura INT NOT NULL,
ADD CONSTRAINT fk_cliente_factura
FOREIGN KEY (id_Factura) REFERENCES factura(id_Factura);
	ALTER TABLE silla_material
	ADD estado VARCHAR(50);
