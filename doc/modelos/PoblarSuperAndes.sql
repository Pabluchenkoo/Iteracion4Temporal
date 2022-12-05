-- Poblar tablas del esquema de Supermercados SuperAndes

--Poblar tabla S_SUPERMERCADO
INSERT INTO S_SUPERMERCADO(nit, nombre) VALUES (8901227710, 'Jumbo');

INSERT INTO S_SUPERMERCADO(nit, nombre) VALUES (9251577812, 'Carulla');

--Poblar tabla S_SUCURSAL
INSERT INTO S_SUCURSAL(nitsupermercado, id, nombre, tamanio, ciudad) VALUES(8901227710,1,'NicolasDeFederman',50.5,'Bogotá');
INSERT INTO S_SUCURSAL(nitsupermercado, id, nombre, tamanio, ciudad) VALUES(8901227710,2,'FloridaBlanca',40.5,'Bucaramanga');

INSERT INTO S_SUCURSAL(nitsupermercado, id, nombre, tamanio, ciudad) VALUES(9251577812,3,'ElPoblado',100.5,'Medellín');
INSERT INTO S_SUCURSAL(nitsupermercado, id, nombre, tamanio, ciudad) VALUES(9251577812,4,'cedritos',50.5,'Bogotá');

--Poblar tabla S_CATEGORIA
INSERT INTO S_CATEGORIA(id, nombre) VALUES(10,'Bebidas');
INSERT INTO S_CATEGORIA(id, nombre) VALUES(11,'Charcuteria');
INSERT INTO S_CATEGORIA(id, nombre) VALUES(12,'Abarrotes');

--Poblar tabla S_ALMACENAMIENTO
INSERT INTO S_ALMACENAMIENTO(idsucursal, id, idcategoria, tipo, volumen, peso, areaocupada, pesoocupado) VALUES(1,20,12,'bodega',40.5,50.5,20.2,25.1);
INSERT INTO S_ALMACENAMIENTO(idsucursal, id, idcategoria, tipo, volumen, peso, areaocupada, pesoocupado) VALUES(1,21,11,'estanteria',40.5,50.5,20.2,25.1);


--Poblar tabla S_PRESENTACION
INSERT INTO S_PRESENTACION(id, precioporunidad, unidaddemedida,especificacion) VALUES(30,15.000,'gr','paquete de 170gr');
INSERT INTO S_PRESENTACION(id, precioporunidad, unidaddemedida,especificacion) VALUES(31,33.100,'kg','paquete de 1kg');


--Poblar tabla S_PROVEEDOR
INSERT INTO S_PROVEEDOR(idsucursal, id, nit, nombre, telefono, direccion) VALUES(1,101,840298774,'DelChiras',3224560,'calle 3 # 10-11');
INSERT INTO S_PROVEEDOR(idsucursal, id, nit, nombre, telefono, direccion) VALUES(2,102,948484959,'CarnicosDelNorte',9898987,'calle 10 # 77-11');



--Poblar tabla S_PRODUCTO
INSERT INTO S_PRODUCTO(idPresentacion, idcategoria, idproveedor,  codigodebarras,nombre,marca) VALUES(30,12,101,'fa00fc0fd','Tostacos','DelChiras');
INSERT INTO S_PRODUCTO(idPresentacion, idcategoria, idproveedor,  codigodebarras,nombre,marca) VALUES(31,11,102,'fa32ft0dd','Punta de anca','DelBeef');

--Poblar tabla S_ORDENPEDIDO
INSERT INTO S_ORDENPEDIDO(idsucursal,idproveedor,id,fecha,fechaentregaesperada, preciototal, codigodebarras, cantidad) VALUES(2,102,110,to_date('2022-9-10','yyyy-mm-dd'),to_date('2022-9-20','yyyy-mm-dd'),5000000.00,'fa00fc0fd',300);
INSERT INTO S_ORDENPEDIDO(idsucursal,idproveedor,id,fecha,fechaentregaesperada, preciototal, codigodebarras,cantidad) VALUES(2,101,111,to_date('2022-9-10','yyyy-mm-dd'),to_date('2022-9-20','yyyy-mm-dd'),5000000.00,'fa32ft0dd',1000);

--Poblar tabla S_LOTE
INSERT INTO S_LOTE(idalmacenamiento,id, codigodebarras, unidades, fechavencimiento, peso, area) VALUES(21,40,'fa00fc0fd',100,to_date('2023-12-12','yyyy-mm-dd'), 11.2 , 11.2);
INSERT INTO S_LOTE(idalmacenamiento,id, codigodebarras,unidades, fechavencimiento, peso, area) VALUES(20,41,'fa32ft0dd',100,to_date('2023-12-12','yyyy-mm-dd'), 10.0 , 10.0);


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
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991,60,to_date('2022-9-11','yyyy-mm-dd'),90000.00,3);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331,61,to_date('2022-9-11','yyyy-mm-dd'),120000.00,3);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331,62,to_date('2022-9-11','yyyy-mm-dd'),90000.00,3);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331,63,to_date('2022-9-11','yyyy-mm-dd'),120000.00,3);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331,64,to_date('2022-9-11','yyyy-mm-dd'),90000.00,2);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331,65,to_date('2022-9-11','yyyy-mm-dd'),120000.00,3);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991,66,to_date('2022-9-11','yyyy-mm-dd'),90000.00,3);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331,67,to_date('2022-9-11','yyyy-mm-dd'),120000.00,3);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331,68,to_date('2022-9-11','yyyy-mm-dd'),120000.00,2);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331,69,to_date('2022-9-11','yyyy-mm-dd'),90000.00,3);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331,70,to_date('2022-9-11','yyyy-mm-dd'),120000.00,1);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991,71,to_date('2022-9-11','yyyy-mm-dd'),90000.00,1);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331,72,to_date('2022-9-11','yyyy-mm-dd'),120000.00,3);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10001, to_date('2017-01-31','yyyy-mm-dd'), 340573,2);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10002, to_date('2007-12-14','yyyy-mm-dd'), 695238,4);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10003, to_date('2024-04-01','yyyy-mm-dd'), 186643,1);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10004, to_date('2003-04-30','yyyy-mm-dd'), 974204,2);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10005, to_date('2007-12-01','yyyy-mm-dd'), 908270,3);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10006, to_date('2011-12-11','yyyy-mm-dd'), 496679,1);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10007, to_date('2019-04-06','yyyy-mm-dd'), 220887,2);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10008, to_date('2018-01-03','yyyy-mm-dd'), 142094,4);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10009, to_date('2011-02-23','yyyy-mm-dd'), 800890,2);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10010, to_date('2017-05-23','yyyy-mm-dd'), 337831,4);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10011, to_date('2026-07-21','yyyy-mm-dd'), 943732,2);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10012, to_date('2013-03-06','yyyy-mm-dd'), 483314,1);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10013, to_date('2025-05-21','yyyy-mm-dd'), 161181,2);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10014, to_date('2018-10-22','yyyy-mm-dd'), 843699,1);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10015, to_date('2027-08-04','yyyy-mm-dd'), 527994,4);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331, 10016, to_date('2022-07-24','yyyy-mm-dd'), 651351,3);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331, 10017, to_date('2013-07-17','yyyy-mm-dd'), 580209,2);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331, 10018, to_date('2016-09-06','yyyy-mm-dd'), 834349,1);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331, 10019, to_date('2005-03-23','yyyy-mm-dd'), 540930,3);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331, 10020, to_date('2018-10-19','yyyy-mm-dd'), 908490,4);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331, 10021, to_date('2012-05-04','yyyy-mm-dd'), 779919,1);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331, 10022, to_date('2018-04-22','yyyy-mm-dd'), 167721,3);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331, 10023, to_date('2009-06-16','yyyy-mm-dd'), 33617,4);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331, 10024, to_date('2008-07-01','yyyy-mm-dd'), 518895,1);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331, 10025, to_date('2021-03-29','yyyy-mm-dd'), 742959,2);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331, 10026, to_date('2002-02-06','yyyy-mm-dd'), 121462,2);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331, 10027, to_date('2008-09-08','yyyy-mm-dd'), 721976,1);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331, 10028, to_date('2002-02-07','yyyy-mm-dd'), 784550,3);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331, 10029, to_date('2004-08-04','yyyy-mm-dd'), 465296,1);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331, 10030, to_date('2012-05-14','yyyy-mm-dd'), 536724,2);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331, 10031, to_date('2011-09-16','yyyy-mm-dd'), 404877,4);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331, 10032, to_date('2016-04-27','yyyy-mm-dd'), 415280,1);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331, 10033, to_date('2008-01-07','yyyy-mm-dd'), 79340,4);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331, 10034, to_date('2020-05-03','yyyy-mm-dd'), 674505,2);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331, 10035, to_date('2025-05-18','yyyy-mm-dd'), 12764,1);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331, 10036, to_date('2017-04-05','yyyy-mm-dd'), 51869,1);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331, 10037, to_date('2018-03-30','yyyy-mm-dd'), 787061,4);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',5012244331, 10038, to_date('2027-04-25','yyyy-mm-dd'), 804999,4);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10039, to_date('2024-01-13','yyyy-mm-dd'), 114085,1);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10040, to_date('2012-02-03','yyyy-mm-dd'), 867433,3);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10041, to_date('2023-11-10','yyyy-mm-dd'), 717820,1);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10042, to_date('2021-03-07','yyyy-mm-dd'), 282553,2);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10043, to_date('2020-09-06','yyyy-mm-dd'), 990554,3);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10044, to_date('2016-12-24','yyyy-mm-dd'), 942658,3);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10045, to_date('2006-11-11','yyyy-mm-dd'), 763169,1);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10046, to_date('2023-12-13','yyyy-mm-dd'), 837470,1);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10047, to_date('2016-01-06','yyyy-mm-dd'), 201143,2);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10048, to_date('2015-12-11','yyyy-mm-dd'), 773406,3);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10049, to_date('2009-01-01','yyyy-mm-dd'), 870236,4);
INSERT INTO S_FACTURA(tipodoc,numdoc, id, fecha, precio, idsucursal) VALUES('CC',1020054991, 10050, to_date('2000-04-06','yyyy-mm-dd'), 398746,2);



--Poblar tabla S_ROL
INSERT INTO S_ROL(id, nombre) VALUES(90,'Administrador');
INSERT INTO S_ROL(id, nombre) VALUES(91,'Gerente Sucursal');
INSERT INTO S_ROL(id, nombre) VALUES(92,'Operador');
INSERT INTO S_ROL(id, nombre) VALUES(93,'Gerente General');
INSERT INTO S_ROL(id, nombre) VALUES(94,'Cajero');


--Poblar tabla S_USUARIO
INSERT INTO S_USUARIO(idrol, cedula, idsucursal, nitsupermercado, nombre, correo, palabraclave) VALUES(90,322323233,1,8901227710,'John','john@jumbo.com','banana');
INSERT INTO S_USUARIO(idrol, cedula, idsucursal, nitsupermercado, nombre, correo, palabraclave) VALUES(91,234343434,2,8901227710,'Miguel','miguel@jumbo.com','melon');
INSERT INTO S_USUARIO(idrol, cedula, idsucursal, nitsupermercado, nombre, correo, palabraclave) VALUES(92,8759487984,2,8901227710,'Juan Pablo Junco','jp@jumbo.com','sistrans');



--Poblar tabla S_ORDENENTREGADA
INSERT INTO S_ORDENENTREGADA(idordenentregada, fecha, codigodebarras, cantidad) VALUES (110, to_date('2022-9-20','yyyy-mm-dd'), 'fa32ft0dd', 1000);


--Poblar tabla S_PRODUCTOVENDIDO
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(60,'fa00fc0fd',70,3);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(61,'fa00fc0fd',71,7);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(61,'fa00fc0fd',72,8);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(60,'fa00fc0fd',73,9);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(61,'fa00fc0fd',74,13);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(60,'fa32ft0dd',75,3);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(61,'fa32ft0dd',76,7);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(61,'fa32ft0dd',77,8);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(60,'fa32ft0dd',78,9);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(61,'fa32ft0dd',79,13);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10001,'fa00fc0fd',1467,57);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10002,'fa00fc0fd',1125,19);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10003,'fa00fc0fd',1499,10);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10004,'fa00fc0fd',1341,14);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10005,'fa00fc0fd',1081,76);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10006,'fa00fc0fd',1052,58);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10007,'fa00fc0fd',1295,25);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10008,'fa00fc0fd',1218,22);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10009,'fa00fc0fd',1411,27);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10010,'fa00fc0fd',1050,46);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10011,'fa00fc0fd',1283,6);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10012,'fa00fc0fd',1372,41);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10013,'fa00fc0fd',1185,88);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10014,'fa00fc0fd',1141,2);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10015,'fa00fc0fd',1124,45);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10016,'fa00fc0fd',1134,11);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10017,'fa00fc0fd',1295,33);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10018,'fa00fc0fd',1358,19);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10019,'fa00fc0fd',1135,13);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10020,'fa00fc0fd',1482,38);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10021,'fa00fc0fd',1045,63);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10022,'fa00fc0fd',1147,44);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10023,'fa00fc0fd',1353,83);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10024,'fa00fc0fd',1337,35);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10025,'fa00fc0fd',1158,56);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10026,'fa00fc0fd',1068,61);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10027,'fa00fc0fd',1099,26);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10028,'fa00fc0fd',1357,79);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10029,'fa00fc0fd',1396,32);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10030,'fa00fc0fd',1338,42);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10031,'fa00fc0fd',1421,83);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10032,'fa00fc0fd',1439,2);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10033,'fa00fc0fd',1193,51);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10034,'fa00fc0fd',1386,17);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10035,'fa00fc0fd',1130,77);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10036,'fa00fc0fd',1455,78);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10037,'fa00fc0fd',1459,1);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10038,'fa00fc0fd',1275,43);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10039,'fa00fc0fd',1244,78);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10040,'fa00fc0fd',1424,39);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10041,'fa00fc0fd',1144,74);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10042,'fa00fc0fd',1236,3);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10043,'fa00fc0fd',1298,52);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10044,'fa00fc0fd',1014,33);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10045,'fa00fc0fd',1443,34);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10046,'fa00fc0fd',1270,12);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10047,'fa00fc0fd',1220,68);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10048,'fa00fc0fd',1206,38);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10049,'fa00fc0fd',1162,37);
INSERT INTO S_PRODUCTOVENDIDO(idfactura, codigodebarras,id,cantidad) VALUES(10050,'fa00fc0fd',1288,39);





INSERT INTO S_CARRITO(IDCARRITO, ASIGNADO, CLIENTENUMDOC) VALUES (1000, 'Y', 1020054991);
INSERT INTO S_CARRITO_PRODUCTO(IDCARRITO, CODIGODEBARRAS, CANTIDAD) VALUES (1000, 'fa32ft0dd', 2);
INSERT INTO S_CARRITO_PRODUCTO(IDCARRITO, CODIGODEBARRAS, CANTIDAD) VALUES (1000, 'fa00fc0fd', 3);


commit;





