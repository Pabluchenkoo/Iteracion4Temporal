package uniandes.isis2304.SuperAndes.persistencia;

import org.datanucleus.store.rdbms.query.ForwardQueryResult;
import uniandes.isis2304.SuperAndes.negocio.Rol;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLRol {

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
    public SQLRol(PersistenciaSuperAndes pp) {
        this.pp = pp;
    }

    /* ****************************************************************
     *          Métodos
     *****************************************************************/
    public long adicionarRol (PersistenceManager pm, long idRol, String nombre)
    {
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaRol () + "(id,  nombre) values (?, ?)");
        q.setParameters( idRol,  nombre);
        return (long) q.executeUnique();
    }
    public Object[] darRolPorId (PersistenceManager pm, long idRol)
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaRol()  + " WHERE id = ?");
        try {
            Object[] u =(Object[]) ((ForwardQueryResult)q.execute(idRol)).get(0);
            return u;
        } catch (Exception e) {
            return null;
        }
    }






}
