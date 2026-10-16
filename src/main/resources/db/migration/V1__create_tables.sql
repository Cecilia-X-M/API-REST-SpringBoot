
CREATE TABLE clientes (
                          id BIGINT NOT NULL AUTO_INCREMENT,
                          nombre VARCHAR(255) NOT NULL,
                          correo VARCHAR(255) NOT NULL UNIQUE,
                          password VARCHAR(255) NOT NULL,
                          fecha_creacion DATETIME(6) NOT NULL,
                          fecha_actualizacion DATETIME(6),
                          rol VARCHAR(255) NOT NULL,
                          PRIMARY KEY (id)
);

CREATE TABLE productos (
                           id BIGINT NOT NULL AUTO_INCREMENT,
                           nombre VARCHAR(255) NOT NULL,
                           precio DOUBLE NOT NULL,
                           stock INT,
                           PRIMARY KEY (id)
);

CREATE TABLE compras (
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         fecha DATETIME(6),
                         total DOUBLE,
                         cliente_id BIGINT NOT NULL,
                         PRIMARY KEY (id),
                         CONSTRAINT fk_compras_cliente
                             FOREIGN KEY (cliente_id)
                                 REFERENCES clientes(id)
);

CREATE TABLE detalles_compra (
                                 id BIGINT NOT NULL AUTO_INCREMENT,
                                 cantidad INT NOT NULL,
                                 precio DOUBLE NOT NULL,
                                 compra_id BIGINT NOT NULL,
                                 producto_id BIGINT NOT NULL,
                                 PRIMARY KEY (id),
                                 CONSTRAINT fk_detalles_compra_compra
                                     FOREIGN KEY (compra_id)
                                         REFERENCES compras(id),
                                 CONSTRAINT fk_detalles_compra_producto
                                     FOREIGN KEY (producto_id)
                                         REFERENCES productos(id)
);