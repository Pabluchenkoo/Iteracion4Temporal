package uniandes.isis2304.SuperAndes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLAlmacenamiento {
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
    public SQLAlmacenamiento(PersistenciaSuperAndes pp) {
        this.pp = pp;
    }

    public void aprovisionarEstante(PersistenceManager pm, long idEstante,double peso, double area)
    {
        String sql = "UPDATE S_ALMACENAMIENTO SET PESOOCUPADO = ?, AREAOCUPADA = ? WHERE ID = ?";
        Query q = pm.newQuery(SQL, sql);
        q.setParameters(peso, area, idEstante);
        q.executeUnique();
    }

    public Object darIdBodega(PersistenceManager pm, long idSucursal)
    {
        String sql = "SELECT ID FROM S_ALMACENAMIENTO WHERE IDSUCURSAL = ? AND TIPO = 'bodega' fetch first 1 rows only";
        Query q = pm.newQuery(SQL, sql);
        q.setParameters(idSucursal);
        return q.executeUnique();
    }
}
