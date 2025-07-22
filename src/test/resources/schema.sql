CREATE TABLE detalles (
  id INT AUTO_INCREMENT PRIMARY KEY,
  director VARCHAR(255),
  actores VARCHAR(255),
  sinopsis TEXT,
  trailer VARCHAR(255)
);

CREATE TABLE peliculas (
  id INT AUTO_INCREMENT PRIMARY KEY,
  titulo VARCHAR(255) NOT NULL,
  duracion INT NOT NULL,
  clasificacion VARCHAR(255),
  genero VARCHAR(255),
  imagen VARCHAR(255),
  fecha_estreno TIMESTAMP NOT NULL,
  estatus VARCHAR(255),
  id_detalle INT,
  FOREIGN KEY (id_detalle) REFERENCES detalles(id)
);

CREATE TABLE banners (
  id INT AUTO_INCREMENT PRIMARY KEY,
  titulo VARCHAR(255) NOT NULL,
  fecha TIMESTAMP,
  archivo VARCHAR(255),
  estatus VARCHAR(255) NOT NULL
);

CREATE TABLE horarios (
  id INT AUTO_INCREMENT PRIMARY KEY,
  fecha TIMESTAMP,
  hora VARCHAR(255) NOT NULL,
  sala VARCHAR(255) NOT NULL,
  precio DOUBLE PRECISION NOT NULL DEFAULT 0,
  id_pelicula INT NOT NULL,
  FOREIGN KEY (id_pelicula) REFERENCES peliculas(id) ON DELETE CASCADE
);

CREATE TABLE usuarios (
  id INT AUTO_INCREMENT PRIMARY KEY,
  cuenta VARCHAR(255) NOT NULL UNIQUE,
  pwd VARCHAR(255) NOT NULL,
  activo INT NOT NULL,
  email VARCHAR(255),
  telefono VARCHAR(255) NOT NULL
);

CREATE UNIQUE INDEX idx_unique_cuenta ON usuarios(cuenta);

CREATE TABLE perfiles (
  id INT AUTO_INCREMENT PRIMARY KEY,
  cuenta VARCHAR(255) NOT NULL,
  perfil VARCHAR(255),
  FOREIGN KEY (cuenta) REFERENCES usuarios(cuenta)
);

CREATE UNIQUE INDEX idx_unique_perfil ON perfiles(cuenta, perfil);

CREATE TABLE noticias (
  id INT AUTO_INCREMENT PRIMARY KEY,
  titulo VARCHAR(50) NOT NULL,
  fecha TIMESTAMP,
  detalle VARCHAR(5000) NOT NULL,
  estatus VARCHAR(255) NOT NULL
);
