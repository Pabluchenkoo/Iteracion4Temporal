package uniandes.isis2304.SuperAndes.persistencia;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;


import uniandes.isis2304.SuperAndes.negocio.Sucursal;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto SUCURSAL de SuperAndes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Pablo Junco 
 */
public class SQLSucursal {
    
    /* ****************************************************************
     *          Constantes
     *****************************************************************/
    /**
     * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
     * Se renombra acá para facilitar la escritura de las sentencias
     */
    
    private final static String SQL = PersistenciaSuperAndes.SQL;
    
    /* ****************************************************************
     *          Atributos
     *****************************************************************/
    /**
     * El manejador de persistencia general de la aplicación
     */
    private PersistenciaSuperAndes pp;
    
    /**
     * Constructor
     * @param pp - El Manejador de persistencia de la aplicación
     */
    public SQLSucursal (PersistenciaSuperAndes pp)
    {
        this.pp = pp;
    }

    /**
     * Crea y ejecuta la sentencia SQL para adicionar una SUCURSAL a la base de datos de SuperAndes
     * @return EL número de tuplas insertadas
     */
    public long adicionarSucursal (PersistenceManager pm, long nitSupermercado, long IdSucursal, String nombre, int tamanio,String ciudad ) 
    {
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaSucursal() + "(nitSupermercado, idSucursal, nombre,tamanio,ciudad) values (?, ?, ?,?,?)");
        q.setParameters(nitSupermercado, IdSucursal, nombre,tamanio,ciudad);
        return (long)q.executeUnique();            
    }
    
    /**
     * Crea y ejecuta la sentencia SQL para eliminar SUCURSALES de la base de datos de Parranderos, por su nombre
     * @return EL número de tuplas eliminadas
     */
    public long eliminarSucursalPorNombre (PersistenceManager pm, String nombre)
    {
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaSucursal () + " WHERE nombre = ?");
        q.setParameters(nombre);
        return (long) q.executeUnique();            
    }
    
    /**
     * Crea y ejecuta la sentencia SQL para eliminar UNA SUCURSAL de la base de datos de SuperAndes, por su identificador
     * @return EL número de tuplas eliminadas
     */
    public long eliminarSucursalPorId (PersistenceManager pm, long idSucursal)
    {
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaSucursal () + " WHERE id = ?");
        q.setParameters(idSucursal);
        return (long) q.executeUnique();            
    }
    
    /**
     * Crea y ejecuta la sentencia SQL para encontrar la información de UNA SUCURSAL de la 
     * base de datos de SuperAndes, por su identificador

     * @return El objeto SUCURSAL que tiene el identificador dado
     */
    public Sucursal darSucursalPorId (PersistenceManager pm, long idSucursal) 
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaSucursal () + " WHERE id = ?");
        q.setResultClass(Sucursal.class);
        q.setParameters(idSucursal);
        return (Sucursal) q.executeUnique();
    }
    
    /**
     * Crea y ejecuta la sentencia SQL para encontrar la información de LAS SUCURSALES de la 
     * base de datos de SuperAndes, por su nombre

     * @return Una lista de objetos SUCURSAL que tienen el nombre dado
     */
    public List<Sucursal> darSucursalesPorNombre (PersistenceManager pm, String nombreSucursal) 
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaSucursal () + " WHERE nombre = ?");
        q.setResultClass(Sucursal.class);
        q.setParameters(nombreSucursal);
        return (List<Sucursal>) q.executeList();
    }
    
    /**
     * Crea y ejecuta la sentencia SQL para mostrar el dinero recolectado por ventas en cada sucursal
     * durante un periodo de tiempo
     * @param pm - El manejador de persistencia
     * @param fechaInicio - Fecha de inicio del periodo
     * @param fechaFin - Fecha de fin del periodo
     * @return Una lista de objetos SUCURSAL
     */
    public List<Object> darDineroRecolectadoSucursalesPeriodo (PersistenceManager pm, String fechaInicio, String fechaFin)
    {
        String sql = "SELECT sucur.nombre, SUM(factura.precio) as ventasSucursal";
        sql += " FROM " + pp.darTablaSucursal() + " sucur";
        sql += " JOIN " + pp.darTablaProductoVendido()+ " producto ON sucur.id = producto.idsucursal";
        sql += " JOIN " + pp.darTablaFactura() + " factura  ON producto.idfactura = factura.id";
        sql += " WHERE factura.fecha BETWEEN TO_DATE(?,'yyyy-mm-dd') AND TO_DATE(?,'yyyy-mm-dd')";
        sql += " GROUP BY sucur.nombre";

        Query q = pm.newQuery(SQL,sql);
        q.setParameters(fechaInicio, fechaFin);
        List<Object> resp = new LinkedList<>();
        List results = (List) q.executeList();
        for (Object o : results)
        {
            Object [] datos = (Object []) o;
            String nombreSucursal = (String) datos [0];
            double ventasSucursal = ((BigDecimal) datos [1]).doubleValue();
            resp.add(new Object [] {nombreSucursal, ventasSucursal});
        }
        return resp;

    }

    /**
     * Crea y ejecuta la sentencia SQL para mostrar el dinero recolectado por ventas en cada sucursal
     * durante un periodo de tiempo
     * @param pm - El manejador de persistencia
     * @param anio - Fecha de inicio del periodo
     * @return Una lista de objetos SUCURSAL
     */
    public List<Object> darDineroRecolectadoSucursalesAnio (PersistenceManager pm, String anio)
    {

        String sql = "SELECT sucur.nombre, SUM(factura.precio) as ventasSucursal";
        sql += " FROM " + pp.darTablaSucursal() + " sucur";
        sql += " JOIN " + pp.darTablaProductoVendido()+ " producto ON sucur.id = producto.idsucursal";
        sql += " JOIN " + pp.darTablaFactura() + " factura  ON producto.idfactura = factura.id";
        sql += " WHERE factura.fecha BETWEEN TO_DATE('"+anio+ "-01-01','yyyy-mm-dd') AND TO_DATE('"+anio+"-12-31','yyyy-mm-dd')";
        sql += " GROUP BY sucur.nombre";

        Query q = pm.newQuery(SQL,sql);

        List<Object> resp = new LinkedList<>();
        List results = (List) q.executeList();
        for (Object o : results)
        {
            Object [] datos = (Object []) o;
            String nombreSucursal = (String) datos [0];
            double ventasSucursal = ((BigDecimal) datos [1]).doubleValue();
            resp.add(new Object [] {nombreSucursal, ventasSucursal});
        }
        return  resp;
    }

    /**
     * Crea y ejecuta la sentencia SQL para mostrar el indice de ocupacion de cada una de las bodegas y estantes
     * @param pm - El manejador de persistencia
     * @return Una lista de objetos SUCURSAL
     */
    public List<Object> darIndiceOcupacionBodegas (PersistenceManager pm)
    {
        String sql = "SELECT sucur.nombre, categoria.nombre, almacenamiento.tipo, " +
                "ROUND((almacenamiento.areaocupada*100)/almacenamiento.volumen, 2) AS porcentajeOcupacionVolumen,\n" +
                "    almacenamiento.volumen AS volumenTotalKg";
        sql += " FROM " + pp.darTablaSucursal() +" sucur";
        sql += " JOIN " + pp.darTablaAlmacenamiento()  +" almacenamiento ON almacenamiento.idsucursal = sucur.id";
        sql += " JOIN " + pp.darTablaCategoria() + " categoria ON categoria.id = almacenamiento.idcategoria";

        Query q = pm.newQuery(SQL,sql);
        List<Object> resp = new LinkedList<>();
        List results = (List) q.executeList();
        for (Object o : results)
        {
            Object [] datos = (Object []) o;
            String nombreSucursal = (String) datos [0];
            String nombreCategoria = (String) datos [1];
            String tipoAlmacenamiento = (String) datos [2];
            BigDecimal porcentajeOcupacion = BigDecimal.valueOf(((BigDecimal) datos [3]).doubleValue());
            BigDecimal volumenTotal = BigDecimal.valueOf(((BigDecimal) datos [4]).doubleValue());
            resp.add(new Object [] {nombreSucursal, nombreCategoria, tipoAlmacenamiento, porcentajeOcupacion, volumenTotal});
        }

        return  q.executeList();
    }

    /**
     * Crea y ejecuta la sentencia SQL para mostrar los productos vendidos mas de x veces
     * @param veces - numero de veces vendido
     * @param pm - El manejador de persistencia
     * @return Una lista de objetos
     */
    public List<Object> darProductosVendidosMasDeX(PersistenceManager pm, int veces)
    {
        String sql = "SELECT prod.nombre, prodvendido.cantidad";
        sql += " FROM " + pp.darTablaSucursal() + " sucur";
        sql += " JOIN " + pp.darTablaProductoVendido()+ " prodvendido ON sucur.id = prodvendido.idsucursal";
        sql += " JOIN " + pp.darTablaProducto() + " prod  ON prodvendido.codigodebarras = prod.codigodebarras";
        sql += " WHERE prodvendido.cantidad > " + veces;

        Query q = pm.newQuery(SQL,sql);
        List<Object> resp = new LinkedList<>();
        List results = (List) q.executeList();
        for (Object o : results)
        {
            Object [] datos = (Object []) o;
            String nombreProducto = (String) datos [0];
            int cantidad = ((BigDecimal) datos [1]).intValue();
            resp.add(new Object [] {nombreProducto, cantidad});
        }
        return  resp;
    }

    /**
     * Crea y ejecuta la sentencia SQL para mostrar los productos comprados a los proveedores
     * @param pm - El manejador de persistencia
     * @return Una lista de objetos
     */
    public List<Object> darComprasAProveedores(PersistenceManager pm)
    {
        String sql = "SELECT  prove.nit,prove.nombre, prove.direccion, ordenped.preciototal";
        sql += " FROM " + pp.darTablaSucursal() + " sucur";
        sql += " JOIN " + pp.darTablaOrdenPedido()+ " ordenped ON sucur.id = ordenped.idsucursal";
        sql += " JOIN " + pp.darTablaProveedor() + " prove  ON prove.id = ordenped.idproveedor";

        Query q = pm.newQuery(SQL,sql);
        List<Object> resp = new LinkedList<>();
        List results = (List) q.executeList();
        for (Object o : results)
        {
            Object [] datos = (Object []) o;
            int nitProveedor = ((BigDecimal) datos [0]).intValue();
            String nombreProveedor = (String) datos [1];
            String direccionProveedor = (String) datos [2];
            double precioTotal = ((BigDecimal) datos [3]).doubleValue();
            resp.add(new Object [] {nitProveedor, nombreProveedor, direccionProveedor, precioTotal});
        }
        return  resp;
    }

    public List<Object> encontrarClientesFrecuentes(PersistenceManager persistenceManager, BigDecimal idSucursal) {

        String sql = "SELECT cliente.nombre,factura.numdoc, count(*) AS numeroDeCompras\n" +
                "FROM S_FACTURA factura\n" +
                "JOIN S_CLIENTE cliente ON cliente.numdoc = factura.numdoc\n" +
                "WHERE factura.IDSUCURSAL = ?\n" +
                "GROUP BY factura.numdoc , cliente.nombre\n" +
                "HAVING COUNT(*) > 2";
        Query q = persistenceManager.newQuery(SQL, sql);
        q.setParameters(idSucursal);
        List<Object> resp = new LinkedList<>();
        List results = (List) q.executeList();
        for (Object o : results) {
            Object[] datos = (Object[]) o;
            String nombreCliente = (String) datos[0];
            BigDecimal numDoc = (BigDecimal) datos[1];
            BigDecimal numeroDeCompras = (BigDecimal) datos[2];
            resp.add(new Object[]{nombreCliente, numDoc, numeroDeCompras});
        }
        return resp;
    }

    public BigDecimal darIdSucursal(PersistenceManager persistenceManager, String sucursal) {

        String sql = "SELECT ID FROM S_SUCURSAL WHERE NOMBRE = ? ";
        Query q = persistenceManager.newQuery(SQL, sql);
        q.setParameters(sucursal);
        return (BigDecimal) q.executeUnique();

    }

    public List<Object[]> mayorDemanda(PersistenceManager persistenceManager, String fechaInicio, String fechaFin, String nombreCategoria) {

        String sql = "SELECT MAX(cantidad), categoriaNombre, sucursalNombre\n" +
                "FROM (SELECT COUNT(producto.nombre) AS cantidad,\n" +
                "             categoria.nombre       AS categoriaNombre,\n" +
                "             factura.fecha,\n" +
                "             sucursal.nombre        AS sucursalNombre\n" +
                "      FROM S_PRODUCTOVENDIDO vendido\n" +
                "               JOIN S_FACTURA factura ON factura.id = vendido.idfactura\n" +
                "               JOIN S_PRODUCTO producto ON producto.codigodebarras = vendido.codigodebarras\n" +
                "               JOIN S_CATEGORIA categoria ON categoria.id = producto.idcategoria\n" +
                "               JOIN S_SUCURSAL sucursal ON sucursal.id = factura.idSucursal\n" +
                "      WHERE categoria.nombre = ?\n" +
                "        AND factura.fecha > to_date(?,'YYYY-MM-DD')\n" +
                "        AND factura.fecha < to_date(?,'YYYY-MM-DD')\n" +
                "      GROUP BY categoria.nombre, factura.fecha, sucursal.nombre)\n" +
                "GROUP BY categoriaNombre, sucursalNombre";
        Query q = persistenceManager.newQuery(SQL, sql);
        q.setParameters(nombreCategoria, fechaInicio, fechaFin);
        List<Object[]> resp = new LinkedList<>();
        List results = (List) q.executeList();
        for (Object o : results) {
            Object[] datos = (Object[]) o;
            BigDecimal cantidad = (BigDecimal) datos[0];
            String categoriaNombre = (String) datos[1];
            String sucursalNombre = (String) datos[2];
            resp.add(new Object[]{cantidad, categoriaNombre, sucursalNombre});
        }
        return resp;
    }

    public List<Object[]> menorDemanda(PersistenceManager persistenceManager, String fechaInicio, String fechaFin, String nombreCategoria) {

        String sql = "SELECT MIN(cantidad), categoriaNombre, sucursalNombre\n" +
                "FROM (SELECT COUNT(producto.nombre) AS cantidad,\n" +
                "             categoria.nombre       AS categoriaNombre,\n" +
                "             factura.fecha,\n" +
                "             sucursal.nombre        AS sucursalNombre\n" +
                "      FROM S_PRODUCTOVENDIDO vendido\n" +
                "               JOIN S_FACTURA factura ON factura.id = vendido.idfactura\n" +
                "               JOIN S_PRODUCTO producto ON producto.codigodebarras = vendido.codigodebarras\n" +
                "               JOIN S_CATEGORIA categoria ON categoria.id = producto.idcategoria\n" +
                "               JOIN S_SUCURSAL sucursal ON sucursal.id = factura.idSucursal\n" +
                "      WHERE categoria.nombre = ?\n" +
                "        AND factura.fecha > to_date(?,'YYYY-MM-DD')\n" +
                "        AND factura.fecha < to_date(?,'YYYY-MM-DD')\n" +
                "      GROUP BY categoria.nombre, factura.fecha, sucursal.nombre)\n" +
                "GROUP BY categoriaNombre, sucursalNombre";
        Query q = persistenceManager.newQuery(SQL, sql);
        q.setParameters(nombreCategoria, fechaInicio, fechaFin);
        List<Object[]> resp = new LinkedList<>();
        List results = (List) q.executeList();
        for (Object o : results) {
            Object[] datos = (Object[]) o;
            BigDecimal cantidad = (BigDecimal) datos[0];
            String categoriaNombre = (String) datos[1];
            String sucursalNombre = (String) datos[2];
            resp.add(new Object[]{cantidad, categoriaNombre, sucursalNombre});
        }
        return resp;
    }
}
