package uniandes.isis2304.SuperAndes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.math.BigDecimal;
import java.util.List;

public class SQLCarrito {
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
    public SQLCarrito(PersistenciaSuperAndes pp) {
        this.pp = pp;
    }


    public void agregarProductoCarrito(PersistenceManager pm, long idCarrito, long idProducto, int cantidad)
    {
        String sql = "INSERT INTO S_CARRITO_PRODUCTO (ID_CARRITO, ID_PRODUCTO, CANTIDAD) VALUES (?, ?, ?)";
        Query q = pm.newQuery(SQL, sql);
        q.setParameters(idCarrito, idProducto, cantidad);
        q.executeUnique();
    }

    public List<Object[]> darCarritoPorId(PersistenceManager pm, long cedula)
    {
        String sql = "SELECT IDCARRITO,IDLOTE, CODIGO, CANTIDAD, PRECIOPORUNIDAD, NOMBRE, CODIGODEBARRAS FROM  " ;
        sql += "(SELECT CODIGODEBARRAS,s_carrito.IDCARRITO, CLIENTENUMDOC, CANTIDAD  FROM S_CARRITO_PRODUCTO ";
        sql += "INNER JOIN S_CARRITO ON S_CARRITO_PRODUCTO.IDCARRITO = s_carrito.idcarrito) ";
        sql += "INNER JOIN (select s_producto.CODIGODEBARRAS AS CODIGO, PRECIOPORUNIDAD , IDLOTE, NOMBRE from s_producto ";
        sql += "INNER JOIN S_PRESENTACION ON s_producto.IDPRESENTACION = S_PRESENTACION.ID ";
        sql += "INNER JOIN (select s_producto.CODIGODEBARRAS AS COD, S_LOTE.ID as IDLOTE from s_producto ";
        sql += "INNER JOIN S_LOTE ON s_producto.CODIGODEBARRAS = S_LOTE.CODIGODEBARRAS) ";
        sql += "ON CODIGODEBARRAS = COD) ON CODIGODEBARRAS = CODIGO WHERE CLIENTENUMDOC = ? " ;
        Query q = pm.newQuery(SQL, sql);
        q.setParameters(cedula);
        return (List<Object[]>) q.executeList();
    }

    public void limpiarCarrito(PersistenceManager pm, long idCarrito)
    {
        String sql = "DELETE FROM S_CARRITO_PRODUCTO WHERE IDCARRITO = ?";
        Query q = pm.newQuery(SQL, sql);
        q.setParameters(idCarrito);
        q.executeUnique();
    }
    public void liberarCarrito(PersistenceManager pm, long idCarrito)
    {
        String sql = "UPDATE S_CARRITO SET ASIGNADO = 'N', CLIENTENUMDOC = NULL WHERE IDCARRITO = ?";
        Query q = pm.newQuery(SQL, sql);
        q.setParameters(idCarrito);
        q.executeUnique();
    }

    public void abandonarCarrito(PersistenceManager pm, long cedula)
    {
        String sql = "UPDATE S_CARRITO SET ASIGNADO = 'N', CLIENTENUMDOC = NULL WHERE CLIENTENUMDOC = ?";
        Query q = pm.newQuery(SQL, sql);
        q.setParameters(cedula);
        q.executeUnique();
    }

    public List<Object[]> carritosAbandonados(PersistenceManager pm)
    {
        String sql = "SELECT IDCARRITO, ASIGNADO FROM S_CARRITO WHERE ASIGNADO = 'N'";
        Query q = pm.newQuery(SQL, sql);
        return (List<Object[]>) q.executeList();
    }

    public Object solicitarCarrito(PersistenceManager pm, long idCarrito, long cedula) throws Exception {

        try{
            String sql = "INSERT INTO S_CARRITO(IDCARRITO, ASIGNADO, CLIENTENUMDOC) VALUES (?, ?, ?)";
            Query q = pm.newQuery(SQL, sql);
            q.setParameters(idCarrito, 1, cedula);
            return  q.executeUnique();
        }
        catch (Exception e){

            throw new Exception( "El cliente ya tiene un carrito asignado");

        }

    }

    public BigDecimal darIdCarrito(PersistenceManager pm, BigDecimal cedula) {
        String sql = "SELECT IDCARRITO FROM S_CARRITO WHERE CLIENTENUMDOC = ? AND ASIGNADO = 'Y'";
        Query q = pm.newQuery(SQL, sql);
        q.setParameters(cedula);
        return (BigDecimal) q.executeUnique();
    }
}
