package uniandes.isis2304.SuperAndes.persistencia;

import org.datanucleus.store.rdbms.query.ForwardQueryResult;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLUsuario {

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
    public SQLUsuario(PersistenciaSuperAndes pp) {
        this.pp = pp;
    }

    public Object[] darUsuarioPorId (PersistenceManager pm, long idUsuario)
    {
        Query q = pm.newQuery(SQL, "SELECT NOMBRE, IDROL, CEDULA, PALABRACLAVE, IDSUCURSAL FROM " + pp.darTablaUsuario()  + " WHERE CEDULA = ?");
        try {
            Object[] u =(Object[]) ((ForwardQueryResult)q.execute(idUsuario)).get(0);
            return u;
        } catch (Exception e) {
            return null;
        }    }
}
