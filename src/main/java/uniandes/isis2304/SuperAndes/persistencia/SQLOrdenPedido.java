package uniandes.isis2304.SuperAndes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.List;

public class SQLOrdenPedido {

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
    public SQLOrdenPedido(PersistenciaSuperAndes pp) {
        this.pp = pp;
    }

    public Object darOrdenPedido(PersistenceManager pm, long idOrdenPedido)
    {
    	Query q = pm.newQuery(SQL, "SELECT nombre, fecha FROM " + pp.darTablaOrdenPedido() + " WHERE id = ?");
        q.setParameters(idOrdenPedido);
        return q.executeUnique();
    }

    public Object crearOrdenConsolidada(PersistenceManager pm, long idOrdenConsolidada, long nitProveedor, long idSucursal)
    {
    	Query q = pm.newQuery(SQL, "INSERT INTO S_ORDENCONSOLIDADA (id, idproveedor, idsucursal) VALUES (?, ?, ?)");
        q.setParameters(idOrdenConsolidada, nitProveedor, idSucursal);
        return q.executeUnique();
    }

    public void consolidarPedido(PersistenceManager pm, long idOrdenConsolidada, long idProveedor, long idSucursal)
    {
        	Query q = pm.newQuery(SQL, "UPDATE S_ORDENPEDIDO SET idordenconsolidada = ? WHERE idproveedor = ? AND idsucursal = ? AND idordenconsolidada IS NULL");
            q.setParameters(idOrdenConsolidada, idProveedor, idSucursal);
            q.executeUnique();

    }

    public List<Object[]> ordenesPedidoConsolidado(PersistenceManager pm, long idOrdenConsolidada,long idSucursal)
    {
        	Query q = pm.newQuery(SQL, "SELECT CODIGODEBARRAS, CANTIDAD FROM S_ORDENPEDIDO WHERE idordenconsolidada = ? AND idsucursal = ?");
            q.setParameters(idOrdenConsolidada, idSucursal);
            return q.executeList();
    }

    public void eliminacionOrdenesPedido(PersistenceManager pm, long idOrdenConsolidada, long idSucursal)
    {
        	Query q = pm.newQuery(SQL, "DELETE FROM S_ORDENPEDIDO WHERE idordenconsolidada = ? AND idsucursal = ?");
            q.setParameters(idOrdenConsolidada, idSucursal);
            q.executeUnique();
    }

    public List<Object[]> consultarProveedoresMasSolicitados(PersistenceManager persistenceManager, String fecha1, String fecha2) {
        String sql = "SELECT *\n" +
                "FROM (SELECT proveedor.nombre, SUM(cantidad) AS ordenesRealizadas\n" +
                "      FROM S_ORDENPEDIDO ordenPedido\n" +
                "               JOIN S_PROVEEDOR proveedor ON proveedor.id = ordenPedido.idproveedor\n" +
                "      WHERE fecha BETWEEN '"+ fecha1 +"' AND '"+ fecha2 +"'\n" +
                "      GROUP BY(proveedor.nombre)\n" +
                "      ORDER BY ordenesRealizadas DESC)\n" +
                "WHERE ordenesRealizadas = (SELECT MAX(ordenesRealizadas)\n" +
                "                           FROM (SELECT proveedor.nombre, SUM(cantidad) AS ordenesRealizadas\n" +
                "                                 FROM S_ORDENPEDIDO ordenPedido\n" +
                "                                          JOIN S_PROVEEDOR proveedor ON proveedor.id = ordenPedido.idproveedor\n" +
                "                                 WHERE fecha BETWEEN '"+ fecha1 +"' AND '"+ fecha2 +"'\n" +
                "                                 GROUP BY(proveedor.nombre)\n" +
                "                                 ORDER BY ordenesRealizadas DESC))";
        Query q = persistenceManager.newQuery(SQL, sql);
        return q.executeList();

    }

    public List<Object[]> consultarProveedoresMenosSolicitados(PersistenceManager persistenceManager, String fecha1, String fecha2) {

        String sql= "SELECT *\n" +
                "FROM (SELECT proveedor.nombre, SUM(cantidad) AS ordenesRealizadas\n" +
                "      FROM S_ORDENPEDIDO ordenPedido\n" +
                "               JOIN S_PROVEEDOR proveedor ON proveedor.id = ordenPedido.idproveedor\n" +
                "      WHERE fecha BETWEEN '"+ fecha1 +"' AND '"+ fecha2 +"'\n" +
                "      GROUP BY(proveedor.nombre)\n" +
                "      ORDER BY ordenesRealizadas DESC)\n" +
                "WHERE ordenesRealizadas = (SELECT MIN(ordenesRealizadas)\n" +
                "                           FROM (SELECT proveedor.nombre, SUM(cantidad) AS ordenesRealizadas\n" +
                "                                 FROM S_ORDENPEDIDO ordenPedido\n" +
                "                                          JOIN S_PROVEEDOR proveedor ON proveedor.id = ordenPedido.idproveedor\n" +
                "                                 WHERE fecha BETWEEN '"+ fecha1 +"' AND '"+ fecha2 +"'\n" +
                "                                 GROUP BY(proveedor.nombre)\n" +
                "                                 ORDER BY ordenesRealizadas DESC))";
        Query q = persistenceManager.newQuery(SQL, sql);
        return q.executeList();
    }
}
