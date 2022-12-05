package uniandes.isis2304.SuperAndes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

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
}
