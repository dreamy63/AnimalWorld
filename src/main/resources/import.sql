/* Populate tabla usuarios */
INSERT INTO usuarios (id_usuario, usu_nombre, usu_apellido, usu_dni, usu_fecha_nac, usu_telefono, usu_celular, usu_email, usu_usuario, usu_clave, usu_rol, usu_foto) values (1,'Karina Rocio', 'Alvarez Hancco', '46844618', '1992-03-06', '5755378', '931579099', 'alvarez.hancco@gmail.com', '46844618', '12345', '','');

/* Populate tabla productos */
INSERT INTO productos (id_producto,nombre,descripcion,precio_unitario,stock,recomendaciones) values (1,'juguete hueso','Juguete para perros en forma de hueso',7,30,'Perros mayores de 1 mes');
INSERT INTO productos (id_producto,nombre,descripcion,precio_unitario,stock,recomendaciones) values (2,'juguete pluma','Juguete para gatos en forma de pluma',5,18,'Gatos mayores de 1 mes');