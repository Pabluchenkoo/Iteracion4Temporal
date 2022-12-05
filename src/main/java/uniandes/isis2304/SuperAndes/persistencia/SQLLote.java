package uniandes.isis2304.SuperAndes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class SQLLote {
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
    public SQLLote(PersistenciaSuperAndes pp) {
        this.pp = pp;
    }

    public Object  loteProvisionar(PersistenceManager pm, long idEstante){
        String sql = "SELECT * FROM S_ALMACENAMIENTO WHERE ID = ?" ;
        Query q = pm.newQuery(SQL, sql);
        q.setParameters(idEstante);
        Object resp = q.executeUnique();
        return resp;
    }

    public Object maxLote(PersistenceManager pm, double peso, double area, long idEstante)
    {
        String sql = "SELECT * FROM S_LOTE WHERE PESO <= ? AND AREA <= ? AND IDALMACENAMIENTO != ? order by peso desc, area desc fetch first 1 rows only";
        Query q = pm.newQuery(SQL, sql);
        q.setParameters(peso, area, idEstante);
        Object resp = q.executeUnique();
        return resp;
    }

    public void asignarEstante(PersistenceManager pm, long idLote, long idEstante)
    {
        String sql = "UPDATE S_LOTE SET IDALMACENAMIENTO = ? WHERE ID = ?";
        Query q = pm.newQuery(SQL, sql);
        q.setParameters(idEstante, idLote);
        q.executeUnique();
    }

    public void actualizarCantidad(PersistenceManager pm, long idLote, int cantidad)
    {
        String sql1 = "SELECT UNIDADES FROM S_LOTE WHERE ID = ?";
        Query q1 = pm.newQuery(SQL, sql1);
        q1.setParameters(idLote);
        BigDecimal cant = (BigDecimal) q1.executeUnique();
        int newCant = cant.intValue() - cantidad;
        String sql = "UPDATE S_LOTE SET UNIDADES = ? WHERE ID = ?";
        Query q = pm.newQuery(SQL, sql);
        q.setParameters(newCant, idLote);
        q.executeUnique();
    }

    public void crearLote(PersistenceManager pm, long idLote, long idBodega, String codBarras, int cantidad){
        String sql = "INSERT INTO S_LOTE (ID, IDALMACENAMIENTO, CODIGODEBARRAS, UNIDADES) VALUES (?, ?, ?, ?)";
        Query q = pm.newQuery(SQL, sql);
        q.setParameters(idLote, idBodega, codBarras, cantidad);
        q.executeUnique();
    }
}
