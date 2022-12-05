package uniandes.isis2304.SuperAndes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class SQLProductoVendido {

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
    public SQLProductoVendido(PersistenciaSuperAndes pp) {
        this.pp = pp;
    }


    /* ****************************************************************
     * 			Métodos
     *****************************************************************/

    /**
     * Crea y ejecuta la sentencia SQL para mostrar los productos vendidos a un cliente en un rango de fechas
     * @param numdoc - numero de documento del cliente
     * @param fecha1 - fecha inicial
     * @param fecha2 - fecha final
     * @param pm - El manejador de persistencia
     * @return Una lista de objetos
     */
    public List<Object> mostrarProductosVendidosClienteRangoFechas (PersistenceManager pm, int numdoc, String fecha1, String fecha2)
    {
        String sql = "SELECT cliente.nombre, cliente.numdoc,prodvendido.codigodebarras,prodvendido.cantidad\n";
        sql += " FROM " + pp.darTablaProductoVendido() + " prodvendido";
        sql += " JOIN " + pp.darTablaFactura() + " factura ON prodvendido.idfactura = factura.id";
        sql += " JOIN " + pp.darTablaCliente() + " cliente ON cliente.numdoc = factura.numdoc";
        sql += " WHERE cliente.numdoc = " + numdoc + " AND factura.fecha BETWEEN "
                + "TO_DATE ('"+ fecha1 +"','YYYY-MM-DD')" + " AND " + "TO_DATE ('"+ fecha2 +"','YYYY-MM-DD')" ;

        Query q = pm.newQuery(SQL, sql);
        List<Object> resp = new LinkedList<>();
        List tuplas = (List) q.executeList();
        for (Object tupla : tuplas)
        {
            Object[] datos = (Object[]) tupla;
            String nombre = (String) datos[0];
            int numdocu = ((BigDecimal) datos[1]).intValue();
            String codigodebarras = (String) datos[2];
            int cantidad = ((BigDecimal) datos[3]).intValue();
            resp.add(new Object [] {nombre, numdocu, codigodebarras, cantidad});

        }
        return resp;
    }

    public void agregarProductoVendido(PersistenceManager pm,long idFactura,String codigodebarras,long idProductoVendido,int cantidad)
    {
        	Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaProductoVendido() + "(idfactura ,codigodebarras ,id, cantidad) values (? , ? , ?, ?)");
            q.setParameters(idFactura, codigodebarras, idProductoVendido, cantidad);
            q.executeUnique();
    }

    public List<Object[]> consultarConsumoO(PersistenceManager pm, long cod,String fecha1,String fecha2, String arg,String orden){
        if(arg=="Nombre")
        {
            arg="nombre";
        }
        if(arg=="Documento")
        {
            arg="numdoc";
        }
        if(orden=="Ascendente")
        {
            orden="ASC";
        }
        if(orden=="Descendente")
        {
            orden="DESC";
        }
        {
            arg="nombre";
        }
        String sql = "select NUMDOC, NOMBRE, CORREO from ";
        sql += "(select DISTINCT NUMDOC AS CLIENTE from S_FACTURA ";
        sql += "inner join S_PRODUCTOVENDIDO  ON ";
        sql += "S_FACTURA.ID = S_PRODUCTOVENDIDO.IDFACTURA ";
        sql += " WHERE CODIGODEBARRAS = '"+cod+"' AND   FECHA BETWEEN "+ "TO_DATE ('"+ fecha1 +"','YYYY-MM-DD') " + "AND " + "TO_DATE ('"+ fecha2 +"','YYYY-MM-DD'))";
        sql += " INNER JOIN S_CLIENTE ON S_CLIENTE.NUMDOC = CLIENTE" ;
        sql += " ORDER BY "+arg+" "+orden;
        Query q = pm.newQuery(SQL, sql);
        return q.executeList();
    }


}
