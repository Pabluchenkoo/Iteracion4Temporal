package uniandes.isis2304.SuperAndes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.List;

public class SQLFactura {

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
    public SQLFactura(PersistenciaSuperAndes pp) {
        this.pp = pp;
    }

    public void inicializarFactura(PersistenceManager pm, long idFactura, String fecha, long cedula)
    {
        	Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaFactura() + "(id ,fecha ,precio, idsucursal, numdoc, tipodoc) values (? , TO_DATE(?,'yyyy-MM-dd') , 0, 3, ?, 'CC')");
            q.setParameters(idFactura, fecha, cedula);
            q.executeUnique();
    }

    public void actualizarTotalFactura(PersistenceManager pm, long idFactura, double precio)
    {
    	Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaFactura() + " SET precio = ? WHERE id = ?");
        q.setParameters(precio, idFactura);
        q.executeUnique();
    }

    public List<Object[]> masVendido(PersistenceManager persistenceManager, String fecha1, String fecha2) {

        String sql = "SELECT *\n" +
                "FROM (SELECT producto.nombre, SUM(productoVendido.cantidad) AS cantidad\n" +
                "      FROM S_PRODUCTOVENDIDO productoVendido\n" +
                "               JOIN S_FACTURA factura ON factura.id = productoVendido.idFactura\n" +
                "               JOIN S_PRODUCTO producto ON producto.codigodebarras = productoVendido.codigodebarras\n" +
                "      WHERE factura.fecha BETWEEN '"+ fecha1 +"' AND '"+ fecha2 +"'\n" +
                "      GROUP BY(producto.nombre)\n" +
                "      ORDER BY cantidad DESC)\n" +
                "WHERE cantidad = (SELECT MAX(cantidad)\n" +
                "                  FROM (SELECT producto.nombre, SUM(productoVendido.cantidad) AS cantidad\n" +
                "                        FROM S_PRODUCTOVENDIDO productoVendido\n" +
                "                                 JOIN S_FACTURA factura ON factura.id = productoVendido.idFactura\n" +
                "                                 JOIN S_PRODUCTO producto ON producto.codigodebarras = productoVendido.codigodebarras\n" +
                "                        WHERE factura.fecha BETWEEN '"+ fecha1 +"' AND '"+ fecha2 +"'\n" +
                "                        GROUP BY(producto.nombre)\n" +
                "                        ORDER BY cantidad DESC))";
        Query q = persistenceManager.newQuery(SQL, sql);
        return q.executeList();
    }

    public List<Object[]> menosVendido(PersistenceManager persistenceManager, String fecha1, String fecha2) {

        String sql = "SELECT *\n" +
                "FROM (SELECT producto.nombre, SUM(productoVendido.cantidad) AS cantidad\n" +
                "      FROM S_PRODUCTOVENDIDO productoVendido\n" +
                "               JOIN S_FACTURA factura ON factura.id = productoVendido.idFactura\n" +
                "               JOIN S_PRODUCTO producto ON producto.codigodebarras = productoVendido.codigodebarras\n" +
                "      WHERE factura.fecha BETWEEN '"+ fecha1 +"' AND '"+ fecha2 +"'\n" +
                "      GROUP BY(producto.nombre)\n" +
                "      ORDER BY cantidad DESC)\n" +
                "WHERE cantidad = (SELECT MIN(cantidad)\n" +
                "                  FROM (SELECT producto.nombre, SUM(productoVendido.cantidad) AS cantidad\n" +
                "                        FROM S_PRODUCTOVENDIDO productoVendido\n" +
                "                                 JOIN S_FACTURA factura ON factura.id = productoVendido.idFactura\n" +
                "                                 JOIN S_PRODUCTO producto ON producto.codigodebarras = productoVendido.codigodebarras\n" +
                "                        WHERE factura.fecha BETWEEN '"+ fecha1 +"' AND '"+ fecha2 +"'\n" +
                "                        GROUP BY(producto.nombre)\n" +
                "                        ORDER BY cantidad DESC))";
        Query q = persistenceManager.newQuery(SQL, sql);
        return q.executeList();
    }
}
