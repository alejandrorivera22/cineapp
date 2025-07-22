CREATE TABLE detalles (
  id int NOT NULL AUTO_INCREMENT,
  director varchar(255) DEFAULT NULL,
  actores varchar(255) DEFAULT NULL,
  sinopsis text,
  trailer varchar(255) DEFAULT NULL COMMENT 'URL del video en Youtube\\n',
  PRIMARY KEY (id)
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

  CREATE TABLE peliculas (
    id int NOT NULL AUTO_INCREMENT,
    titulo varchar(255) NOT NULL,
    duracion int NOT NULL,
    clasificacion varchar(255) DEFAULT NULL,
    genero varchar(255) DEFAULT NULL,
    imagen varchar(255) DEFAULT NULL,
    fecha_estreno datetime(6) NOT NULL,
    estatus varchar(255) DEFAULT NULL,
    id_detalle int DEFAULT NULL,
    PRIMARY KEY (id),
    KEY fk_Peliculas_Detalles1_idx (id_detalle),
    CONSTRAINT fk_Peliculas_Detalles1 FOREIGN KEY (id_detalle) REFERENCES detalles (id)
  )  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

  CREATE TABLE banners (
    id int NOT NULL AUTO_INCREMENT,
    titulo varchar(255) NOT NULL,
    fecha datetime(6) DEFAULT NULL,
    archivo varchar(255) DEFAULT NULL,
    estatus varchar(255) NOT NULL,
    PRIMARY KEY (id)
  ) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE horarios (
  id int NOT NULL AUTO_INCREMENT,
  fecha datetime(6) DEFAULT NULL,
  hora varchar(255) NOT NULL,
  sala varchar(255) NOT NULL,
  precio double NOT NULL DEFAULT '0',
  id_pelicula int NOT NULL,
  PRIMARY KEY (id),
  KEY fk_Horarios_Peliculas1_idx (id_pelicula),
  CONSTRAINT fk_Horarios_Peliculas1 FOREIGN KEY (id_pelicula) REFERENCES peliculas (id) ON DELETE CASCADE
)  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE usuarios (
  id int NOT NULL AUTO_INCREMENT,
  cuenta varchar(255) NOT NULL,
  pwd varchar(255) NOT NULL,
  activo int NOT NULL,
  email varchar(255) DEFAULT NULL,
  telefono varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY cuenta_UNIQUE (cuenta)
)  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE perfiles (
  id int NOT NULL AUTO_INCREMENT,
  cuenta varchar(255) NOT NULL,
  perfil varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY authorities_idx_2 (cuenta,perfil),
  UNIQUE KEY cuenta_perfil_UNIQUE (cuenta,perfil),
  CONSTRAINT authorities_ibfk_2 FOREIGN KEY (cuenta) REFERENCES usuarios (cuenta)
)  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE noticias (
  id int NOT NULL AUTO_INCREMENT,
  titulo varchar(50) NOT NULL,
  fecha datetime(6) DEFAULT NULL,
  detalle varchar(5000) NOT NULL,
  estatus varchar(255) NOT NULL,
  PRIMARY KEY (id)
)  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;