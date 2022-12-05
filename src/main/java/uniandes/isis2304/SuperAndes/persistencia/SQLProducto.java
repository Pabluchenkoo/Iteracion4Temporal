package uniandes.isis2304.SuperAndes.persistencia;

import uniandes.isis2304.SuperAndes.negocio.Producto;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class SQLProducto {

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
    public SQLProducto(PersistenciaSuperAndes pp) {
        this.pp = pp;
    }


    /**
     * Crea y ejecuta la sentencia SQL para mostrar los productos en un rango de precios
     * @param precioMin - precio minimo
     * @param precioMax - precio maximo
     * @param pm - El manejador de persistencia
     * @return Una lista de objetos
     */
    public List<Object> darProductosPorPrecio (PersistenceManager pm, double precioMin, double precioMax)
    {
        String sql = "SELECT producto.nombre AS nombreProdducto, producto.marca, categoria.nombre AS nombreCategoria, presen.precioporunidad, presen.especificacion";
        sql += " FROM " + pp.darTablaProducto() + " producto" ;
        sql += " JOIN " + pp.darTablaPresentacion() + " presen ON presen.id = producto.idpresentacion";
        sql += " JOIN " + pp.darTablaCategoria() + " categoria ON categoria.id = producto.idcategoria";
        sql += " WHERE presen.precioporunidad BETWEEN ? AND ?  ";

        Query q = pm.newQuery(SQL, sql);
        q.setParameters(precioMin, precioMax);
        List<Object> resp = new LinkedList<>();
        List results = q.executeList();
        for (Object o : results) {
            Object[] tupla = (Object[]) o;
            String nombreProducto = (String) tupla[0];
            String marca = (String) tupla[1];
            String nombreCategoria = (String) tupla[2];
            double precio = ((BigDecimal) tupla[3]).doubleValue();
            String especificacion = (String) tupla[4];
            resp.add(new Object []{nombreProducto, marca, nombreCategoria, precio, especificacion});
        }
        return resp;
    }

    public Object darProducto(PersistenceManager pm, String codBarras)
    {
        String sql = "select precioporunidad, idlote, nombre from s_producto\n" +
                "inner join s_presentacion on idpresentacion = s_presentacion.id\n";
        sql += "where codigodebarras = ?";
        Query q = pm.newQuery(SQL, sql);
        q.setParameters(codBarras);
        return q.executeUnique();
    }

    /**
     * Crea y ejecuta la sentencia SQL para mostrar los productos que tengan una fecha de vencimiento posterior a la fecha dada
     * @param fechaVencimiento - fecha de vencimiento
     * @param pm - El manejador de persistencia
     * @return Una lista de objetos
     */
    public List<Object> darProductosPorFechaVencimiento (PersistenceManager pm, String fechaVencimiento)
    {
        String sql = "SELECT producto.nombre AS nombreProdducto, producto.marca, categoria.nombre " +
                "AS nombreCategoria, presen.precioporunidad, presen.especificacion, lote.fechavencimiento";
        sql += " FROM " + pp.darTablaProducto() + " producto" ;
        sql += " JOIN " + pp.darTablaPresentacion() + " presen ON presen.id = producto.idpresentacion";
        sql += " JOIN " + pp.darTablaCategoria() + " categoria ON categoria.id = producto.idcategoria";
        sql += " JOIN " + pp.darTablaLote() + " lote ON lote.id = producto.idlote";
        sql += " WHERE lote.fechavencimiento > TO_DATE(?,'YYYY-MM-DD')  ";

        Query q = pm.newQuery(SQL, sql);
        q.setParameters(fechaVencimiento);

        List<Object> resp = new LinkedList<>();
        List results = q.executeList();
        for (Object o : results) {
            Object[] tupla = (Object[]) o;
            String nombreProducto = (String) tupla[0];
            String marca = (String) tupla[1];
            String nombreCategoria = (String) tupla[2];
            double precio = ((BigDecimal) tupla[3]).doubleValue();
            String especificacion = (String) tupla[4];
            Timestamp fechaVen = (Timestamp) tupla[5];
            resp.add(new Object []{nombreProducto, marca, nombreCategoria, precio, especificacion, fechaVen});
        }
        return resp;
    }

    /**
     * Crea y ejecuta la sentencia SQL para mostrar los productos por proveedor
     * @param nitProveedor - nit del proveedor
     * @param pm - El manejador de persistencia
     * @return Una lista de objetos
     */
    public List<Object> darProductosPorProveedor (PersistenceManager pm, int nitProveedor)
    {
        String sql = "SELECT producto.nombre AS nombreProdducto, producto.marca, categoria.nombre AS nombreCategoria, " +
                "presen.precioporunidad, presen.especificacion, proveedor.nombre\n";
        sql += " FROM " + pp.darTablaProducto() + " producto" ;
        sql += " JOIN " + pp.darTablaPresentacion() + " presen ON presen.id = producto.idpresentacion";
        sql += " JOIN " + pp.darTablaCategoria() + " categoria ON categoria.id = producto.idcategoria";
        sql += " JOIN " + pp.darTablaProveedor() + " proveedor ON proveedor.id = producto.idproveedor";
        sql += " WHERE proveedor.nit = ?  ";

        Query q = pm.newQuery(SQL, sql);
        q.setParameters(nitProveedor);

        List<Object> resp = new LinkedList<>();
        List results = q.executeList();
        for (Object o : results) {
            Object[] tupla = (Object[]) o;
            String nombreProducto = (String) tupla[0];
            String marca = (String) tupla[1];
            String nombreCategoria = (String) tupla[2];
            double precio = ((BigDecimal) tupla[3]).doubleValue();
            String especificacion = (String) tupla[4];
            String nombreProveedor = (String) tupla[5];
            resp.add(new Object []{nombreProducto, marca, nombreCategoria, precio, especificacion, nombreProveedor});
        }
        return resp;
    }

    /**
     * Crea y ejecuta la sentencia SQL para mostrar los productos disponibles en una ciudad
     * @param nombreCiudad - nombre de la ciudad
     * @param pm - El manejador de persistencia
     * @return Una lista de objetos
     */
    public List<Object> darProductosPorCiudad (PersistenceManager pm, String nombreCiudad)
    {
        String sql = "SELECT producto.nombre AS nombreProdducto, producto.marca, categoria.nombre AS nombreCategoria," +
            " presen.precioporunidad, presen.especificacion, sucur.ciudad\n";
        sql += " FROM " + pp.darTablaProducto() + " producto" ;
        sql += " JOIN " + pp.darTablaPresentacion() + " presen ON presen.id = producto.idpresentacion";
        sql += " JOIN " + pp.darTablaCategoria() + " categoria ON categoria.id = producto.idcategoria";
        sql += " JOIN " + pp.darTablaLote() + " lote ON lote.id = producto.idlote";
        sql += " JOIN " + pp.darTablaAlmacenamiento() + " alma ON alma.id = lote.idalmacenamiento";
        sql += " JOIN " + pp.darTablaSucursal() + " sucur ON sucur.id = alma.idsucursal";

        sql += " WHERE sucur.ciudad = ?  ";

        Query q = pm.newQuery(SQL, sql);
        q.setParameters(nombreCiudad);
        List<Object> resp = new LinkedList<>();
        List results = q.executeList();
        for (Object o : results) {
            Object[] tupla = (Object[]) o;
            String nombreProducto = (String) tupla[0];
            String marca = (String) tupla[1];
            String nombreCategoria = (String) tupla[2];
            double precio = ((BigDecimal) tupla[3]).doubleValue();
            String especificacion = (String) tupla[4];
            String ciudad = (String) tupla[5];
            resp.add(new Object []{nombreProducto, marca, nombreCategoria, precio, especificacion, ciudad});
        }
        return resp;
    }

    /**
     * Crea y ejecuta la sentencia SQL para mostrar los productos disponibles en una Sucursal
     * @param sucursal - nombre de la sucursal
     * @param pm - El manejador de persistencia
     * @return Una lista de objetos
     */
    public List<Object> darProductosPorSucursal(PersistenceManager pm, String sucursal)
    {
        String sql = "SELECT sucur.nombre AS nombresucursal, sucur.ciudad AS ciudad, producto.nombre AS nombreProdducto, producto.marca, categoria.nombre AS nombreCategoria, presen.precioporunidad, presen.especificacion\n";
        sql += " FROM " + pp.darTablaProducto() + " producto" ;
        sql += " JOIN " + pp.darTablaPresentacion() + " presen ON presen.id = producto.idpresentacion";
        sql += " JOIN " + pp.darTablaCategoria() + " categoria ON categoria.id = producto.idcategoria";
        sql += " JOIN " + pp.darTablaLote() + " lote ON lote.id = producto.idlote";
        sql += " JOIN " + pp.darTablaAlmacenamiento() + " alma ON alma.id = lote.idalmacenamiento";
        sql += " JOIN " + pp.darTablaSucursal() + " sucur ON sucur.id = alma.idsucursal";
        sql += " WHERE sucur.nombre = ?  ";

        Query q = pm.newQuery(SQL, sql);
        q.setParameters(sucursal);

        List<Object> resp = new LinkedList<>();
        List results = q.executeList();
        for (Object o : results) {
            Object[] tupla = (Object[]) o;
            String nombreSucursal = (String) tupla[0];
            String ciudad = (String) tupla[1];
            String nombreProducto = (String) tupla[2];
            String marca = (String) tupla[3];
            String nombreCategoria = (String) tupla[4];
            double precio = ((BigDecimal) tupla[5]).doubleValue();
            String especificacion = (String) tupla[6];
            resp.add(new Object []{nombreSucursal, ciudad, nombreProducto, marca, nombreCategoria, precio, especificacion});
        }
        return resp;

    }

    /**
     * Crea y ejecuta la sentencia SQL para mostrar los productos por categoria
     * @param categoria - nombre de la categoría
     * @param pm - El manejador de persistencia
     * @return Una lista de objetos
     */
    public List<Object> darProductosPorCategoria(PersistenceManager pm, String categoria)
    {
        String sql= "SELECT producto.nombre AS nombreProdducto, producto.marca, categoria.nombre AS nombreCategoria," +
                " presen.precioporunidad, presen.especificacion\n";
        sql += " FROM " + pp.darTablaProducto() + " producto" ;
        sql += " JOIN " + pp.darTablaPresentacion() + " presen ON presen.id = producto.idpresentacion";
        sql += " JOIN " + pp.darTablaCategoria() + " categoria ON categoria.id = producto.idcategoria";
        sql += " WHERE categoria.nombre = ?  ";

        Query q = pm.newQuery(SQL, sql);
        q.setParameters(categoria);
        List<Object> resp = new LinkedList<>();
        List results = q.executeList();
        for (Object o : results) {
            Object[] tupla = (Object[]) o;
            String nombreProducto = (String) tupla[0];
            String marca = (String) tupla[1];
            String nombreCategoria = (String) tupla[2];
            double precio = ((BigDecimal) tupla[3]).doubleValue();
            String especificacion = (String) tupla[4];
            resp.add(new Object []{nombreProducto, marca, nombreCategoria, precio, especificacion});
        }
        return resp;
    }


    public List <String> darNombreProductos(PersistenceManager pm)
    {
        String sql = "SELECT nombre FROM " + pp.darTablaProducto();
        Query q = pm.newQuery(SQL, sql);
        List<String> resp = new LinkedList<>();
        List results = q.executeList();
        for (Object o : results) {
            String nombre = (String) o;
            resp.add(nombre);
        }
        return resp;
    }

    public String darCodBarras(PersistenceManager pm, String nombreProducto) {

        String sql = "SELECT codigodebarras FROM " + pp.darTablaProducto() + " WHERE nombre = ?";
        Query q = pm.newQuery(SQL, sql);
        q.setParameters(nombreProducto);
        String resp = (String) q.executeUnique();
        return resp;
    }

    public List<String> nombreProductosPorCodigo(PersistenceManager pm, List<String> codigo)
    {
        String sql = "SELECT nombre FROM " + pp.darTablaProducto() + " WHERE codigodebarras = ?";
        Query q = pm.newQuery(SQL, sql);
        List<String> resp = new LinkedList<>();
        for (String cod : codigo) {
            q.setParameters(cod);
            String nombre = (String) q.executeUnique();
            resp.add(nombre);
        }
        return resp;

    }


}
