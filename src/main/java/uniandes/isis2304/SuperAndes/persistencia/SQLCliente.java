package uniandes.isis2304.SuperAndes.persistencia;

import uniandes.isis2304.SuperAndes.negocio.Cliente;

import org.datanucleus.store.rdbms.query.ForwardQueryResult;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.math.BigDecimal;

public class SQLCliente {

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
    public SQLCliente(PersistenciaSuperAndes pp) {
        this.pp = pp;
    }
    public Object[] darClientePorId (PersistenceManager pm, long idCliente)
    {
        Query q = pm.newQuery(SQL, "SELECT NOMBRE, NUMDOC FROM " + pp.darTablaCliente()  + " WHERE NUMDOC = ?");
        try {
            Object[] u =(Object[]) ((ForwardQueryResult)q.execute(idCliente)).get(0);
            return u;
        } catch (Exception e) {
            return null;
        }    }

    public BigDecimal darCedula(PersistenceManager pm, String nombreCliente) {
        Query q = pm.newQuery(SQL, "SELECT NUMDOC FROM " + pp.darTablaCliente() + " WHERE NOMBRE = ?");
        q.setParameters(nombreCliente);
        return (BigDecimal) q.executeUnique();
    }
}
