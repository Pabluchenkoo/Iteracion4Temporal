package uniandes.isis2304.SuperAndes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.math.BigDecimal;
import java.sql.Date;

public class SQLOferta {

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
     *
     * @param pp - El Manejador de persistencia de la aplicación
     */
    public SQLOferta(PersistenciaSuperAndes pp) {
        this.pp = pp;
    }

    public long adicionarOfertaD(PersistenceManager pm, long idOferta, String codBarras, int unidades, String fecha) {
        String activa = "Y";
        double idTipoOferta = 1;
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaOferta() + "(id, codigodeBarras, unidadesdisponibles, fechacaducidad, activa, idtipooferta) values (?, ?, ?, ?, ?, ?)");
        q.setParameters(idOferta, codBarras, unidades, fecha, activa, idTipoOferta);
        return (long) q.executeUnique();
    }

    public long adicionarOfertaN(PersistenceManager pm, long idOferta, String codBarras, int unidades, String fecha) {
        String activa = "Y";
        int idTipoOferta = 2;
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaOferta() + "(id, codigodeBarras, unidadesdisponibles, fechacaducidad, activa, idtipooferta) values (?, ?, ?, ?, ?, ?)");
        q.setParameters(idOferta, codBarras, unidades, fecha, activa, idTipoOferta);
        return (long) q.executeUnique();
    }

    public long adicionarOfertaNxM(PersistenceManager pm, long idOferta,int n, int m) {
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaOfertaNxM() + "(id, n, m) values (?, ?, ?)");
        q.setParameters(idOferta, n, m);
        return (long) q.executeUnique();
    }
    public long adicionarOfertaDescuento(PersistenceManager pm, long idOferta, double porcentaje) {
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaOfertaDescuento() + "(id, descuento) values (?, ?)");
        q.setParameters(idOferta, porcentaje);
        return (long) q.executeUnique();
    }

    public long adicionarOfertaPagueXLleveY(PersistenceManager pm, long idOferta, int x, int y) {
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaOfertaPagueXLleveY() + "(id, x, y) values (?, ?, ?)");
        q.setParameters(idOferta, x, y);
        return (long) q.executeUnique();
    }

    public void finalizarPromociones(PersistenceManager pm, String fecha) {
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaOferta() + " SET activa = 'N' WHERE fechacaducidad < ? or unidadesdisponibles = 0");
        q.setParameters(fecha);
        q.executeUnique();
    }

    public Object ofertaActiva(PersistenceManager pm, String codBarras, String fecha, int cantidad) {
        Query q = pm.newQuery(SQL, "SELECT id, idtipooferta FROM " + pp.darTablaOferta() + " WHERE codigodeBarras = ? and activa = 'Y' and fechacaducidad > TO_DATE(?,'YYYY-MM-DD') AND unidadesdisponibles > ?");
        q.setParameters(codBarras, fecha, cantidad);
        return q.executeUnique();
    }

    public BigDecimal descuento(PersistenceManager pm, long idOferta) {
        Query q = pm.newQuery(SQL, "SELECT descuento FROM " + pp.darTablaOfertaDescuento() + " WHERE id = ?");
        q.setParameters(idOferta);
        return (BigDecimal) q.executeUnique();
    }

    public Object nxm(PersistenceManager pm, long idOferta) {
        Query q = pm.newQuery(SQL, "SELECT n, m FROM " + pp.darTablaOfertaNxM() + " WHERE id = ?");
        q.setParameters(idOferta);
        return q.executeUnique();
    }
}