package uniandes.isis2304.SuperAndes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.math.BigDecimal;
import java.util.List;

public class SQLProductoCarrito {
    private final static String SQL = PersistenciaSuperAndes.SQL;
    private PersistenciaSuperAndes pp;
    public SQLProductoCarrito(PersistenciaSuperAndes pp) {
        this.pp = pp;
    }

    public void agregarProductoCarrito(PersistenceManager pm, long idCarrito, String codigoDeBarras, int cantidad)
    {
        String sql = "INSERT INTO S_CARRITO_PRODUCTO (IDCARRITO, CODIGODEBARRAS, CANTIDAD) VALUES (?, ?, ?)";
        Query q = pm.newQuery(SQL, sql);
        q.setParameters(idCarrito, codigoDeBarras, cantidad);
        q.executeUnique();
    }

    public List<Object> obtenerProductosCarrito(PersistenceManager pm, long idCarrito)
    {
        String sql = "SELECT * FROM S_CARRITO_PRODUCTO WHERE ID_CARRITO = ?";
        Query q = pm.newQuery(SQL, sql);
        q.setSerializeRead(true);
        q.setParameters(idCarrito);
        return q.executeList();
    }

    public List<String> darProductosEnCarrito(PersistenceManager pm, BigDecimal idCarrito) {

        String sql = "SELECT CODIGODEBARRAS FROM S_CARRITO_PRODUCTO WHERE IDCARRITO = ?";
        Query q = pm.newQuery(SQL, sql);
        q.setParameters(idCarrito);
        return q.executeList();
    }


    public void devolverProductoCarrito(PersistenceManager pm, BigDecimal idCarrito, String codBarras) {



        String sql2 = "DELETE from S_CARRITO_PRODUCTO where IDCARRITO = ? AND CODIGODEBARRAS = ?";
        Query q2 = pm.newQuery(SQL, sql2);
        q2.setParameters(idCarrito, codBarras);
        q2.executeUnique();



    }
}
