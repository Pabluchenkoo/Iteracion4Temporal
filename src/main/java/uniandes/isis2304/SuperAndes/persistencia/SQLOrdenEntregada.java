package uniandes.isis2304.SuperAndes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLOrdenEntregada {

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
    public SQLOrdenEntregada(PersistenciaSuperAndes pp) {
        this.pp = pp;
    }

    public void recibirPedido(PersistenceManager pm, long idOrdenPedido, String fecha, String nombre)
    {
    	Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaOrdenEntregada() + "(idordenpedido ,fecha ,nombre) values (? , TO_DATE(?,'yyyy-MM-dd') , ?)");
        q.setParameters(idOrdenPedido, fecha, nombre);
        q.executeUnique();
    }

    public void crearOrdenEntregada(PersistenceManager pm,long  idOrdenE,String fechaString,int cantidad, String codBarras)
    {
        	Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaOrdenEntregada() + "(idordenEntregada ,fecha ,cantidad, codigodebarras) values (? , TO_DATE(?,'yyyy-MM-dd') , ?, ?)");
            q.setParameters(idOrdenE, fechaString, cantidad, codBarras);
            q.executeUnique();
    }
}
