--PRUEBAS DE UNICIDAD DE TUPLAS.

--Poblar tabla S_SUPERMERCADO
INSERT INTO S_SUPERMERCADO(nit, nombre) VALUES (8901227710, 'Jumbo');
INSERT INTO S_SUPERMERCADO(nit, nombre) VALUES (8901227710, 'Exito');

--Poblar tabla S_SUCURSAL 
INSERT INTO S_SUCURSAL(nitsupermercado, id, nombre, tamanio, ciudad) VALUES(8901227710,1,'NicolasDeFederman',50.5,'Bogotá');
INSERT INTO S_SUCURSAL(nitsupermercado, id, nombre, tamanio, ciudad) VALUES(8901227710,1,'FloridaBlanca',40.5,'Bucaramanga');

--Poblar tabla S_CATEGORIA 
INSERT INTO S_CATEGORIA(id, nombre) VALUES(10,'Bebidas');
INSERT INTO S_CATEGORIA(id, nombre) VALUES(10,'Charcuteria');

--Poblar tabla S_ALMACENAMIENTO
INSERT INTO S_ALMACENAMIENTO(idsucursal, id, idcategoria, tipo, volumen, peso, areaocupada, pesoocupado) VALUES(1,20,12,'bodega',40.5,50.5,20.2,25.1);
INSERT INTO S_ALMACENAMIENTO(idsucursal, id, idcategoria, tipo, volumen, peso, areaocupada, pesoocupado) VALUES(1,20,11,'estanteria',40.5,50.5,20.2,25.1);

--Poblar tabla S_PRESENTACION
INSERT INTO S_PRESENTACION(id, precioporunidad, unidaddemedida,especificacion) VALUES(30,15.000,'gr','paquete de 170gr');
INSERT INTO S_PRESENTACION(id, precioporunidad, unidaddemedida,especificacion) VALUES(30,33.100,'kg','paquete de 1kg');

--Poblar tabla S_LOTE
INSERT INTO S_LOTE(idalmacenamiento,id, unidades, fechavencimiento, peso, area) VALUES(21,40,100,to_date('2023-12-12','yyyy-mm-dd'), 11.2 , 11.2);
INSERT INTO S_LOTE(idalmacenamiento,id, unidades, fechavencimiento, peso, area) VALUES(20,40,100,to_date('2023-12-12','yyyy-mm-dd'), 10.0 , 10.0);

--Poblar tabla S_PROVEEDOR
INSERT INTO S_PROVEEDOR(idsucursal, id, nit, nombre, telefono, direccion) VALUES(1,101,840298774,'DelChiras',3224560,'calle 3 # 10-11');
INSERT INTO S_PROVEEDOR(idsucursal, id, nit, nombre, telefono, direccion) VALUES(2,101,948484959,'CarnicosDelNorte',9898987,'calle 10 # 77-11');

--Poblar tabla S_ORDENPEDIDO
INSERT INTO S_ORDENPEDIDO(idsucursal,idproveedor,id,nombre,fecha,fechaentregaesperada, preciototal) VALUES(2,102,110,'100kg de punta de anca',to_date('2022-9-10','yyyy-mm-dd'),to_date('2022-9-20','yyyy-mm-dd'),5000000.00);
INSERT INTO S_ORDENPEDIDO(idsucursal,idproveedor,id,nombre,fecha,fechaentregaesperada, preciototal) VALUES(2,101,110,'1000 paquetes de tostacos',to_date('2022-9-10','yyyy-mm-dd'),to_date('2022-9-20','yyyy-mm-dd'),5000000.00);

--Poblar tabla S_PRODUCTO
INSERT INTO S_PRODUCTO(idPresentacion, idcategoria, idproveedor, idordenpedido, idlote, codigodebarras,nombre,marca) VALUES(30,12,101,111,41,'fa00fc0fd','Tostacos','DelChiras');
INSERT INTO S_PRODUCTO(idPresentacion, idcategoria, idproveedor, idordenpedido, idlote, codigodebarras,nombre,marca) VALUES(31,11,102,110,40,'fa00fc0fd','Punta de anca','DelBeef');

--Poblar tabla S_OFERTA
INSERT INTO S_TIPOOFERTA(ID,NOMBRE) VALUES (1,'DESCUENTO');
INSERT INTO S_TIPOOFERTA(ID,NOMBRE) VALUES (1,'NXM');

--Poblar tabla S_CLIENTE
INSERT INTO S_CLIENTE(numdoc, tipodoc, nombre, correo, mediopago, puntos) VALUES(1020054991,'C.C','Juan','juanp@hotmail.com','Tarjeta',20);
INSERT INTO S_CLIENTE(numdoc, tipodoc, nombre, correo, mediopago, puntos) VALUES(1020054991,'NIT','Friko','friko@friko.com','Tarjeta',20);

--Poblar tabla S_FACTURA
INSERT INTO S_FACTURA(numdoc, id, fecha, precio) VALUES(1020054991,60,to_date('2022-9-11','yyyy-mm-dd'),90000.00);
INSERT INTO S_FACTURA(numdoc, id, fecha, precio) VALUES(5012244331,60,to_date('2022-9-11','yyyy-mm-dd'),120000.00);

--Poblar tabla S_PRODUCTOVENDIDO
INSERT INTO S_PRODUCTOVENDIDO(idfactura, idsucursal, codigodebarras,id,cantidad) VALUES(60,2,'fa00fc0fd',70,3);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, idsucursal, codigodebarras,id,cantidad) VALUES(61,3,'fa00fc0fd',70,7);

--Poblar tabla S_ROL
INSERT INTO S_ROL(id, nombre) VALUES(90,'Administrador');
INSERT INTO S_ROL(id, nombre) VALUES(90,'Gerente Sucursal');

--Poblar tabla S_USUARIO 
INSERT INTO S_USUARIO(idrol, cedula, idsucursal, nitsupermercado, nombre, correo, palabraclave) VALUES(90,322323233,1,8901227710,'John','john@jumbo.com','banana');
INSERT INTO S_USUARIO(idrol, cedula, idsucursal, nitsupermercado, nombre, correo, palabraclave) VALUES(91,322323233,2,8901227710,'Miguel','miguel@jumbo.com','melon');

--Poblar tabla S_ORDENENTREGADA
INSERT INTO S_ORDENENTREGADA(idordenpedido, nombre, fecha) VALUES(110, '100kg de punta de anca recibidos', to_date('2022-9-20','yyyy-mm-dd'));
INSERT INTO S_ORDENENTREGADA(idordenpedido, nombre, fecha) VALUES(110, '99kg de punta de anca recibidos', to_date('2022-9-20','yyyy-mm-dd'));

------------------------------------------------------------------------------------------------------------------------------------------------------
--PRUEBAS DE INTEGRIDAD FK
------------------------------------------------------------------------------------------------------------------------------------------------------

--Poblar tabla S_SUPERMERCADO
INSERT INTO S_SUPERMERCADO(nit, nombre) VALUES (8901227710, 'Jumbo');
INSERT INTO S_SUPERMERCADO(nit, nombre) VALUES (8901227710, 'Exito');

--Poblar tabla S_SUCURSAL 
INSERT INTO S_SUCURSAL(nitsupermercado, id, nombre, tamanio, ciudad) VALUES(8901227710,1,'NicolasDeFederman',50.5,'Bogotá');
INSERT INTO S_SUCURSAL(nitsupermercado, id, nombre, tamanio, ciudad) VALUES(2920292920,2,'FloridaBlanca',40.5,'Bucaramanga');

--Poblar tabla S_CATEGORIA 
INSERT INTO S_CATEGORIA(id, nombre) VALUES(10,'Bebidas');
INSERT INTO S_CATEGORIA(id, nombre) VALUES(11,'Charcuteria');

--Poblar tabla S_ALMACENAMIENTO
INSERT INTO S_ALMACENAMIENTO(idsucursal, id, idcategoria, tipo, volumen, peso, areaocupada, pesoocupado) VALUES(1,20,12,'bodega',40.5,50.5,20.2,25.1);
INSERT INTO S_ALMACENAMIENTO(idsucursal, id, idcategoria, tipo, volumen, peso, areaocupada, pesoocupado) VALUES(5,21,17,'estanteria',40.5,50.5,20.2,25.1);


--Poblar tabla S_PRESENTACION
INSERT INTO S_PRESENTACION(id, precioporunidad, unidaddemedida,especificacion) VALUES(30,15.000,'gr','paquete de 170gr');
INSERT INTO S_PRESENTACION(id, precioporunidad, unidaddemedida,especificacion) VALUES(31,33.100,'kg','paquete de 1kg');


--Poblar tabla S_LOTE
INSERT INTO S_LOTE(idalmacenamiento,id, unidades, fechavencimiento, peso, area) VALUES(21,40,100,to_date('2023-12-12','yyyy-mm-dd'), 11.2 , 11.2);
INSERT INTO S_LOTE(idalmacenamiento,id, unidades, fechavencimiento, peso, area) VALUES(25,41,100,to_date('2023-12-12','yyyy-mm-dd'), 10.0 , 10.0);

--Poblar tabla S_PROVEEDOR
INSERT INTO S_PROVEEDOR(idsucursal, id, nit, nombre, telefono, direccion) VALUES(1,101,840298774,'DelChiras',3224560,'calle 3 # 10-11');
INSERT INTO S_PROVEEDOR(idsucursal, id, nit, nombre, telefono, direccion) VALUES(9,102,948484959,'CarnicosDelNorte',9898987,'calle 10 # 77-11');


--Poblar tabla S_ORDENPEDIDO
INSERT INTO S_ORDENPEDIDO(idsucursal,idproveedor,id,nombre,fecha,fechaentregaesperada, preciototal) VALUES(2,102,110,'100kg de punta de anca',to_date('2022-9-10','yyyy-mm-dd'),to_date('2022-9-20','yyyy-mm-dd'),5000000.00);
INSERT INTO S_ORDENPEDIDO(idsucursal,idproveedor,id,nombre,fecha,fechaentregaesperada, preciototal) VALUES(9,109,111,'1000 paquetes de tostacos',to_date('2022-9-10','yyyy-mm-dd'),to_date('2022-9-20','yyyy-mm-dd'),5000000.00);


--Poblar tabla S_PRODUCTO
INSERT INTO S_PRODUCTO(idPresentacion, idcategoria, idproveedor, idordenpedido, idlote, codigodebarras,nombre,marca) VALUES(30,12,101,111,41,'fa00fc0fd','Tostacos','DelChiras');
INSERT INTO S_PRODUCTO(idPresentacion, idcategoria, idproveedor, idordenpedido, idlote, codigodebarras,nombre,marca) VALUES(37,19,107,118,40,'fa32ft0dd','Punta de anca','DelBeef');



--Poblar tabla S_OFERTA
INSERT INTO S_TIPOOFERTA(ID,NOMBRE) VALUES (1,'DESCUENTO');
INSERT INTO S_TIPOOFERTA(ID,NOMBRE) VALUES (2,'NXM');
INSERT INTO S_TIPOOFERTA(ID,NOMBRE) VALUES (3,'XxY');
INSERT INTO S_TIPOOFERTA(ID,NOMBRE) VALUES (4,'2UNIDAD');
INSERT INTO S_TIPOOFERTA(ID,NOMBRE) VALUES (5,'PAQUETE');


--Poblar tabla S_CLIENTE
INSERT INTO S_CLIENTE(numdoc, tipodoc, nombre, correo, mediopago, puntos) VALUES(1020054991,'C.C','Juan','juanp@hotmail.com','Tarjeta',20);
INSERT INTO S_CLIENTE(numdoc, tipodoc, nombre, correo, mediopago, puntos) VALUES(5012244331,'NIT','Friko','friko@friko.com','Tarjeta',20);

--Poblar tabla S_FACTURA
INSERT INTO S_FACTURA(numdoc, id, fecha, precio) VALUES(1020054991,60,to_date('2022-9-11','yyyy-mm-dd'),90000.00);
INSERT INTO S_FACTURA(numdoc, id, fecha, precio) VALUES(5012244331,61,to_date('2022-9-11','yyyy-mm-dd'),120000.00);

--Poblar tabla S_PRODUCTOVENDIDO
INSERT INTO S_PRODUCTOVENDIDO(idfactura, idsucursal, codigodebarras,id,cantidad) VALUES(60,2,'fa00fc0fd',70,3);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, idsucursal, codigodebarras,id,cantidad) VALUES(63,9,'ASFDSFSDF',71,7);



--Poblar tabla S_ROL
INSERT INTO S_ROL(id, nombre) VALUES(90,'Administrador');
INSERT INTO S_ROL(id, nombre) VALUES(91,'Gerente Sucursal');



--Poblar tabla S_USUARIO 
INSERT INTO S_USUARIO(idrol, cedula, idsucursal, nitsupermercado, nombre, correo, palabraclave) VALUES(90,322323233,1,8901227710,'John','john@jumbo.com','banana');
INSERT INTO S_USUARIO(idrol, cedula, idsucursal, nitsupermercado, nombre, correo, palabraclave) VALUES(99,234343434,9,8901227710,'Miguel','miguel@jumbo.com','melon');


--Poblar tabla S_ORDENENTREGADA
INSERT INTO S_ORDENENTREGADA(idordenpedido, nombre, fecha) VALUES(119, '100kg de punta de anca recibidos', to_date('2022-9-20','yyyy-mm-dd'));


------------------------------------------------------------------------------------------------------------------------------------------------------
--PRUEBAS DE CHEQUEO
------------------------------------------------------------------------------------------------------------------------------------------------------

--Poblar tabla S_CATEGORIA 
INSERT INTO S_CATEGORIA(id, nombre) VALUES(10,'Bebidas');
INSERT INTO S_CATEGORIA(id, nombre) VALUES(11,'Charcuteria');
INSERT INTO S_CATEGORIA(id, nombre) VALUES(12,'Abarrotes');
INSERT INTO S_CATEGORIA(id, nombre) VALUES(13,'yogur');
INSERT INTO S_CATEGORIA(id, nombre) VALUES(14,'hogar');

--Poblar tabla S_ALMACENAMIENTO
INSERT INTO S_ALMACENAMIENTO(idsucursal, id, idcategoria, tipo, volumen, peso, areaocupada, pesoocupado) VALUES(1,20,12,'bodega',40.5,50.5,20.2,25.1);
INSERT INTO S_ALMACENAMIENTO(idsucursal, id, idcategoria, tipo, volumen, peso, areaocupada, pesoocupado) VALUES(1,21,11,'estanteria',40.5,50.5,20.2,25.1);
INSERT INTO S_ALMACENAMIENTO(idsucursal, id, idcategoria, tipo, volumen, peso, areaocupada, pesoocupado) VALUES(1,22,11,'almacen',40.5,50.5,20.2,25.1);

--Poblar tabla S_PRESENTACION
INSERT INTO S_PRESENTACION(id, precioporunidad, unidaddemedida,especificacion) VALUES(30,15.000,'gr','paquete de 170gr');
INSERT INTO S_PRESENTACION(id, precioporunidad, unidaddemedida,especificacion) VALUES(31,33.100,'kg','paquete de 1kg');
INSERT INTO S_PRESENTACION(id, precioporunidad, unidaddemedida,especificacion) VALUES(32,33.100,'lg','paquete de 1kg');

--Poblar tabla S_CLIENTE
INSERT INTO S_CLIENTE(numdoc, tipodoc, nombre, correo, mediopago, puntos) VALUES(1020054991,'C.C','Juan','juanp@hotmail.com','Tarjeta',20);
INSERT INTO S_CLIENTE(numdoc, tipodoc, nombre, correo, mediopago, puntos) VALUES(5012244331,'NIT','Friko','friko@friko.com','Tarjeta',20);
INSERT INTO S_CLIENTE(numdoc, tipodoc, nombre, correo, mediopago, puntos) VALUES(2199399393,'T.I','Friko','friko@friko.com','Tarjeta',20);

--Poblar tabla S_ROL
INSERT INTO S_ROL(id, nombre) VALUES(90,'Administrador');
INSERT INTO S_ROL(id, nombre) VALUES(91,'Gerente Sucursal');
INSERT INTO S_ROL(id, nombre) VALUES(92,'Operador');
INSERT INTO S_ROL(id, nombre) VALUES(93,'Gerente General');
INSERT INTO S_ROL(id, nombre) VALUES(94,'Cajero');
INSERT INTO S_ROL(id, nombre) VALUES(95,'operario');
INSERT INTO S_ROL(id, nombre) VALUES(96,'empleado');





