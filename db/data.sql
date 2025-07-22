INSERT INTO banners VALUES
(1, 'Ya llegó - Dune: Parte Dos', '2024-03-01', 'dune_slide.jpg', 'Activo'),
(2, 'Prepárate - Godzilla x Kong: The New Empire', '2024-03-29', 'godzilla_kong_slide2.jpg', 'Activo'),
(3, 'Muy pronto - Deadpool y Wolverine', '2024-07-26', 'deadpool_wol_slide.jpg', 'Activo'),
(4, 'Ya en cartelera - Inside Out 2', '2024-06-14', 'inside_slide.jpg', 'Activo');

INSERT INTO detalles (id, director, actores, sinopsis, trailer) VALUES
(1, 'Denis Villeneuve', 'Timothée Chalamet, Zendaya, Rebecca Ferguson, Javier Bardem',
'Dune: Parte Dos retoma la historia de Paul Atreides mientras une fuerzas con los Fremen para vengar a su familia y salvar Arrakis.',
'https://www.youtube.com/embed/Way9Dexny3w'),
(2, 'Adam Wingard', 'Rebecca Hall, Dan Stevens, Brian Tyree Henry, Kaylee Hottle',
'Godzilla y Kong se enfrentan a una colosal amenaza oculta dentro de nuestro mundo que desafía su existencia misma.',
'https://www.youtube.com/embed/qqrpMRDuPfc'),
(3, 'Shawn Levy', 'Ryan Reynolds, Hugh Jackman, Emma Corrin, Morena Baccarin',
'Deadpool forma una alianza con Wolverine en una misión inesperada que mezcla acción, humor y viajes interdimensionales.',
'https://www.youtube.com/embed/73_1biulkYk'),
(4, 'Kelsey Mann', 'Amy Poehler, Maya Hawke, Phyllis Smith, Lewis Black',
'En la secuela de Intensamente, Riley enfrenta nuevas emociones mientras entra en la adolescencia.',
'https://www.youtube.com/embed/LEjhY15eCx0'),
(5, 'George Miller', 'Anya Taylor-Joy, Chris Hemsworth, Tom Burke',
'Furiosa narra la historia previa de la valiente guerrera de Mad Max, revelando su lucha por sobrevivir en un mundo postapocalíptico.',
'https://www.youtube.com/embed/XJMuhwVlca4'),
(6, 'Wes Ball', 'Owen Teague, Freya Allan, Kevin Durand',
'En un futuro dominado por simios, un joven simio emprende una travesía que redefinirá el destino de su especie.',
'https://www.youtube.com/embed/4kmKoj4RS8w');

INSERT INTO peliculas (id, titulo, duracion, clasificacion, genero, imagen, fecha_estreno, estatus, id_detalle) VALUES
(1, 'Dune: Parte Dos', 165, 'PG-13', 'Ciencia Ficción', 'dune2.jpg', '2024-03-01 00:00:00', 'Activa', 1),
(2, 'Godzilla x Kong: The New Empire', 115, 'PG-13', 'Acción', 'godzilla_kong.jpg', '2024-03-29 00:00:00', 'Activa', 2),
(3, 'Deadpool & Wolverine', 130, 'R', 'Acción / Comedia', 'deadpool_wolverine.jpg', '2024-07-26 00:00:00', 'Activa', 3),
(4, 'Inside Out 2', 100, 'PG', 'Animación / Familiar', 'inside_out_2.jpg', '2024-06-14 00:00:00', 'Activa', 4),
(5, 'Furiosa: A Mad Max Saga', 148, 'R', 'Acción / Aventura', 'furiosa.jpg', '2024-05-24 00:00:00', 'Activa', 5),
(6, 'Kingdom of the Planet of the Apes', 145, 'PG-13', 'Ciencia Ficción / Aventura', 'planet_apes.jpg', '2024-05-10 00:00:00', 'Activa', 6);


INSERT INTO horarios (id, fecha, hora, sala, precio, id_pelicula) VALUES
 -- Dune: Parte Dos (id=1)
 (1, '2024-03-02', '18:00', 'Sala 1', 75.00, 1),
 (2, '2024-03-02', '21:00', 'Sala 1', 75.00, 1),
 (3, '2024-03-03', '19:00', 'Sala 2', 70.00, 1),

 -- Godzilla x Kong (id=2)
 (4, '2024-03-30', '16:00', 'Sala 1', 70.00, 2),
 (5, '2024-03-30', '19:00', 'Sala 2', 70.00, 2),

 -- Deadpool & Wolverine (id=3)
 (6, '2024-07-26', '17:30', 'Sala 1', 80.00, 3),
 (7, '2024-07-26', '20:30', 'Sala 1', 80.00, 3),
 (8, '2024-07-27', '21:00', 'Sala 2', 75.00, 3),

 -- Inside Out 2 (id=4)
 (9, '2024-06-15', '14:00', 'Sala 3', 60.00, 4),
 (10, '2024-06-15', '16:30', 'Sala 3', 60.00, 4),
 (11, '2024-06-16', '18:00', 'Sala 2', 65.00, 4),

 -- Furiosa: A Mad Max Saga (id=5)
 (12, '2024-05-24', '18:45', 'Sala 2', 75.00, 5),
 (13, '2024-05-24', '21:45', 'Sala 2', 75.00, 5),
 (14, '2024-05-25', '22:00', 'Sala 1', 70.00, 5),

 -- Kingdom of the Planet of the Apes (id=6)
 (15, '2024-05-11', '15:00', 'Sala 1', 70.00, 6),
 (16, '2024-05-11', '18:00', 'Sala 3', 70.00, 6),
 (17, '2024-05-12', '20:30', 'Sala 3', 70.00, 6);


-- Noticias de Dune: Parte Dos (id_pelicula = 1)
INSERT INTO noticias (id, titulo, fecha, detalle, estatus) VALUES
(1, 'Dune Parte Dos rompe récords de taquilla', '2024-03-02 00:00:00', 'La esperada secuela de Denis Villeneuve supera expectativas en su primer fin de semana.', 'Activa'),
(2, 'Zendaya habla sobre su rol en Dune 2', '2024-02-20 00:00:00', 'La actriz profundiza sobre el desarrollo de su personaje Chani en esta segunda parte.', 'Activa');

-- Noticias de Godzilla x Kong (id_pelicula = 2)
INSERT INTO noticias (id, titulo, fecha, detalle, estatus) VALUES
(3, 'Godzilla y Kong arrasan en cines', '2024-03-30 00:00:00', 'La nueva entrega del MonsterVerse se convierte en tendencia global.', 'Activa'),
(4, 'Revelan escenas eliminadas de Godzilla x Kong', '2024-04-01 00:00:00', 'Fans reaccionan a las nuevas tomas no incluidas en la versión de cines.', 'Activa');

-- Noticias de Deadpool & Wolverine (id_pelicula = 3)
INSERT INTO noticias (id, titulo, fecha, detalle, estatus) VALUES
(5, 'Deadpool y Wolverine: la dupla más esperada', '2024-07-10 00:00:00', 'Ryan Reynolds y Hugh Jackman presentan su nueva aventura con humor y acción desenfrenada.', 'Activa'),
(6, 'Cameos sorpresa en Deadpool & Wolverine', '2024-07-27 00:00:00', 'Los fans quedan impactados con las apariciones especiales en la película.', 'Activa');

-- Noticias de Inside Out 2 (id_pelicula = 4)
INSERT INTO noticias (id, titulo, fecha, detalle, estatus) VALUES
(7, 'Inside Out 2 emociona al público', '2024-06-14 00:00:00', 'La secuela de Pixar conmueve a grandes y pequeños con nuevas emociones.', 'Activa'),
(8, 'La adolescencia llega a Riley en Inside Out 2', '2024-06-10 00:00:00', 'La película explora los desafíos del crecimiento emocional.', 'Activa');

-- Noticias de Furiosa (id_pelicula = 5)
INSERT INTO noticias (id, titulo, fecha, detalle, estatus) VALUES
(9, 'Furiosa deslumbra con su acción explosiva', '2024-05-25 00:00:00', 'George Miller vuelve con una precuela feroz y espectacular.', 'Activa'),
(10, 'Anya Taylor-Joy se consolida como Furiosa', '2024-05-20 00:00:00', 'La crítica aplaude su poderosa interpretación en el universo Mad Max.', 'Activa');

-- Noticias de Planet of the Apes (id_pelicula = 6)
INSERT INTO noticias (id, titulo, fecha, detalle, estatus) VALUES
(11, 'Nuevo capítulo de El Planeta de los Simios', '2024-05-11 00:00:00', 'La saga evoluciona con un enfoque más profundo y visualmente impactante.', 'Activa'),
(12, 'Wes Ball habla sobre su visión para los simios', '2024-05-05 00:00:00', 'El director detalla cómo construyó este nuevo mundo post-apocalíptico.', 'Activa');

INSERT INTO `usuarios` VALUES (1, 'luis','$2a$10$tb9tB6Ap3DlRRJjJsfogkelg2D7WNrgKfmz1w2VNJW/fMqxIOtB/W',1,'luis@test.com','9856523'); -- luis123
INSERT INTO `usuarios` VALUES (2, 'marisol','$2a$10$17tS8E9jzpZLSpU/fs9IOOwjkAjVOZV41AKxrRUiE/Tl74yRHd5FK',1,'marisol@example.com','9856482'); -- mari123

-- Insertamos (asignamos roles) a nuestros usuarios.
INSERT INTO `perfiles` VALUES (1, 'luis','EDITOR');
INSERT INTO `perfiles` VALUES (2, 'marisol','GERENTE');