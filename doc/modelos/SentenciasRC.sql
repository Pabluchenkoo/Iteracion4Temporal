

--darSucursales(nitSupermercado)
SELECT sucur.nombre, sucur.tamanio, sucur.ciudad
	FROM S_SUCURSAL sucur, S_SUPERMERCADO super
	WHERE super.nit = sucur.nitsupermercado;



-- Requerimiento de consulta 1
SELECT sucur.nombre, SUM(factura.precio) as ventasSucursal
FROM S_SUCURSAL sucur
JOIN S_PRODUCTOVENDIDO producto ON sucur.id = producto.idsucursal
JOIN S_FACTURA factura ON producto.idfactura = factura.id
WHERE factura.fecha > TO_DATE('2020-09-11','yyyy-mm-dd') AND factura.fecha < TO_DATE('2023-09-11','yyyy-mm-dd')
GROUP BY sucur.nombre



-- Requerimiento de consulta 1
SELECT sucur.nombre, SUM(factura.precio) as ventasSucursal
FROM S_SUCURSAL sucur
JOIN S_PRODUCTOVENDIDO producto ON sucur.id = producto.idsucursal
JOIN S_FACTURA factura ON producto.idfactura = factura.id
WHERE factura.fecha BETWEEN TO_DATE('?-01-01','yyyy-mm-dd') AND TO_DATE('?-12-31','yyyy-mm-dd')
GROUP BY sucur.nombre





-- Requerimiento de consulta 3
SELECT sucur.nombre, categoria.nombre, almacenamiento.tipo, 
	ROUND((almacenamiento.areaocupada*100)/almacenamiento.volumen, 2) AS porcentajeOcupacionVolumen,
    almacenamiento.volumen AS volumenTotalKg
FROM S_SUCURSAL sucur
JOIN S_ALMACENAMIENTO almacenamiento ON almacenamiento.idsucursal = sucur.id
JOIN S_CATEGORIA categoria ON categoria.id = almacenamiento.idcategoria




-- Requerimiento de consulta 4- PRECIO EN UN RANGO
SELECT producto.nombre AS nombreProdducto, producto.marca, categoria.nombre AS nombreCategoria, presen.precioporunidad, presen.especificacion
FROM S_PRODUCTO producto
JOIN S_PRESENTACION presen ON presen.id = producto.idpresentacion
JOIN S_CATEGORIA categoria ON categoria.id = producto.idcategoria
WHERE presen.precioporunidad BETWEEN ? AND ?


-- Requerimiento de consulta 4- FECHADEVENCIMIENTO
SELECT producto.nombre AS nombreProdducto, producto.marca, categoria.nombre AS nombreCategoria, presen.precioporunidad, presen.especificacion
FROM S_PRODUCTO producto
JOIN S_PRESENTACION presen ON presen.id = producto.idpresentacion
JOIN S_CATEGORIA categoria ON categoria.id = producto.idcategoria
JOIN S_LOTE lote ON lote.id = producto.idlote
WHERE lote.fechavencimiento > TO_DATE(?,'YYYY-MM-DD')


-- Requerimiento de consulta 4- POR PROVEEDOR
SELECT producto.nombre AS nombreProdducto, producto.marca, categoria.nombre AS nombreCategoria, presen.precioporunidad, presen.especificacion
FROM S_PRODUCTO producto
JOIN S_PRESENTACION presen ON presen.id = producto.idpresentacion
JOIN S_CATEGORIA categoria ON categoria.id = producto.idcategoria
JOIN S_PROVEEDOR proveedor ON proveedor.id = producto.idproveedor
WHERE proveedor.nit = ?

-- Requerimiento de consulta 4- DISPONIBLES EN CIUDAD
SELECT producto.nombre AS nombreProdducto, producto.marca, categoria.nombre AS nombreCategoria, presen.precioporunidad, presen.especificacion
FROM S_PRODUCTO producto
JOIN S_PRESENTACION presen ON presen.id = producto.idpresentacion
JOIN S_CATEGORIA categoria ON categoria.id = producto.idcategoria
JOIN S_LOTE lote ON lote.id = producto.idlote
JOIN S_ALMACENAMIENTO alma ON alma.id = lote.idalmacenamiento
JOIN S_SUCURSAL sucur ON sucur.id = alma.idsucursal
WHERE sucur.ciudad = ?


-- Requerimiento de consulta 4- DISPONIBLES EN UNA SUCURSAL
SELECT sucur.nombre AS nombresucursal, sucur.ciudad AS ciudad, 
producto.nombre AS nombreProdducto, producto.marca,
 categoria.nombre AS nombreCategoria, presen.precioporunidad, 
 presen.especificacion
FROM S_PRODUCTO producto
JOIN S_PRESENTACION presen ON presen.id = producto.idpresentacion
JOIN S_CATEGORIA categoria ON categoria.id = producto.idcategoria
JOIN S_LOTE lote ON lote.id = producto.idlote
JOIN S_ALMACENAMIENTO alma ON alma.id = lote.idalmacenamiento
JOIN S_SUCURSAL sucur ON sucur.id = alma.idsucursal
WHERE sucur.nombre = ?


-- Requerimiento de consulta 4- POR CATEGORIA
SELECT producto.nombre AS nombreProdducto, producto.marca,
 categoria.nombre AS nombreCategoria, presen.precioporunidad,
  presen.especificacion
FROM S_PRODUCTO producto
JOIN S_PRESENTACION presen ON presen.id = producto.idpresentacion
JOIN S_CATEGORIA categoria ON categoria.id = producto.idcategoria
WHERE categoria.nombre = ?


-- Requerimiento de consulta 4- VENDIDO MÁS de x VECES
SELECT prod.nombre, prodvendido.cantidad
FROM S_SUCURSAL sucur
JOIN S_PRODUCTOVENDIDO prodvendido ON prodvendido.idsucursal = sucur.id
JOIN S_PRODUCTO prod ON prod.codigodebarras = prodvendido.codigodebarras
WHERE prodvendido.cantidad > 3

--Requerimiento de consulta 5
SELECT  prove.nit,prove.nombre, prove.direccion, ordenped.preciototal
FROM S_SUCURSAL sucur
JOIN S_ORDENPEDIDO ordenped ON sucur.id = ordenped.idSucursal
JOIN S_PROVEEDOR prove ON prove.id = ordenped.idproveedor


--Requerimiento de consulta 6
SELECT cliente.nombre, cliente.numdoc,prodvendido.codigodebarras,prodvendido.cantidad
FROM S_PRODUCTOVENDIDO prodvendido
JOIN S_FACTURA factura ON prodvendido.idfactura = factura.id
JOIN S_CLIENTE cliente ON cliente.numdoc = factura.numdoc
WHERE cliente.numdoc = 1020054991 AND factura.fecha 
    BETWEEN TO_DATE ('2020-02-02','YYYY-MM-DD') AND TO_DATE ('2024-02-02','YYYY-MM-DD')


-- iteracion3 -- Requermientos de consulta
--req7
SELECT MAX(cantidad), categoriaNombre, sucursalNombre
FROM (SELECT COUNT(producto.nombre) AS cantidad,
             categoria.nombre       AS categoriaNombre,
             factura.fecha,
             sucursal.nombre        AS sucursalNombre
      FROM S_PRODUCTOVENDIDO vendido
               JOIN S_FACTURA factura ON factura.id = vendido.idfactura
               JOIN S_PRODUCTO producto ON producto.codigodebarras = vendido.codigodebarras
               JOIN S_CATEGORIA categoria ON categoria.id = producto.idcategoria
               JOIN S_SUCURSAL sucursal ON sucursal.id = factura.idSucursal
      WHERE categoria.nombre = ?
        AND factura.fecha > ?
        AND factura.fecha < ?
      GROUP BY categoria.nombre, factura.fecha, sucursal.nombre)
GROUP BY categoriaNombre, sucursalNombre;

--req7
SELECT MIN(cantidad), categoriaNombre, sucursalNombre
FROM (SELECT COUNT(producto.nombre) AS cantidad,
             categoria.nombre       AS categoriaNombre,
             factura.fecha,
             sucursal.nombre        AS sucursalNombre
      FROM S_PRODUCTOVENDIDO vendido
               JOIN S_FACTURA factura ON factura.id = vendido.idfactura
               JOIN S_PRODUCTO producto ON producto.codigodebarras = vendido.codigodebarras
               JOIN S_CATEGORIA categoria ON categoria.id = producto.idcategoria
               JOIN S_SUCURSAL sucursal ON sucursal.id = factura.idSucursal
      WHERE categoria.nombre = ?
        AND factura.fecha > ?
        AND factura.fecha < ?
      GROUP BY categoria.nombre, factura.fecha, sucursal.nombre)
GROUP BY categoriaNombre, sucursalNombre;


--req 8
SELECT cliente.nombre,factura.numdoc, count(*) AS numeroDeCompras
FROM S_FACTURA factura
         JOIN S_CLIENTE cliente ON cliente.numdoc = factura.numdoc
WHERE factura.IDSUCURSAL = 3
GROUP BY factura.numdoc , cliente.nombre
HAVING COUNT(*) > 2;





--REQCONSULTA 11 IT4
SELECT NUMDOC,TIPODOC,NOMBRE,
       CORREO,MEDIOPAGO,PUNTOS, SUM(cantidades) FROM
    (
        (SELECT  S_CLIENTE.NUMDOC,S_CLIENTE.TIPODOC,S_CLIENTE.NOMBRE,
                 S_CLIENTE.CORREO,S_CLIENTE.MEDIOPAGO,S_CLIENTE.PUNTOS, productoVendido.cantidad AS cantidades FROM S_CLIENTE
                                                                                                                        JOIN S_FACTURA factura ON factura.numdoc = S_CLIENTE.numdoc
                                                                                                                        JOIN S_PRODUCTOVENDIDO productoVendido ON productoVendido.idFactura = factura.id)
            MINUS
            (SELECT S_CLIENTE.NUMDOC,S_CLIENTE.TIPODOC,S_CLIENTE.NOMBRE,
S_CLIENTE.CORREO,S_CLIENTE.MEDIOPAGO,S_CLIENTE.PUNTOS, productoVendido.cantidad FROM S_CLIENTE
JOIN S_FACTURA factura ON factura.numdoc = S_CLIENTE.numdoc
JOIN S_PRODUCTOVENDIDO productoVendido ON productoVendido.idFactura = factura.id
WHERE factura.fecha BETWEEN '22/01/20' AND '30/09/30' AND productoVendido.CODIGODEBARRAS = '9790336344206')
        )
GROUP BY NUMDOC,TIPODOC,NOMBRE,
         CORREO,MEDIOPAGO,PUNTOS
ORDER BY nombre ASC;