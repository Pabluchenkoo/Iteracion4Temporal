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

    public List<Object[]> consultarConsumoA(PersistenceManager pm, long cod,String fecha1,String fecha2, String arg,String orden){
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
        String sql = "select NUMDOC, NOMBRE, CORREO, CANTIDAD from ";
        sql += "(select NUMDOC AS CLIENTE  , SUM(CANTIDAD) AS CANTIDAD from S_FACTURA ";
        sql += "inner join S_PRODUCTOVENDIDO  ON ";
        sql += "S_FACTURA.ID = S_PRODUCTOVENDIDO.IDFACTURA ";
        sql += " WHERE CODIGODEBARRAS = '"+cod+"' AND   FECHA BETWEEN "+ "TO_DATE ('"+ fecha1 +"','YYYY-MM-DD') " + "AND " + "TO_DATE ('"+ fecha2 +"','YYYY-MM-DD') GROUP BY NUMDOC)";
        sql += " INNER JOIN S_CLIENTE ON S_CLIENTE.NUMDOC = CLIENTE" ;
        sql += " ORDER BY "+arg+" "+orden;
        Query q = pm.newQuery(SQL, sql);
        return q.executeList();
    }
    public List<Object[]> buenosClientes(PersistenceManager pm){
        String sql = "select distinct s_cliente.NUMDOC, s_cliente.NOMBRE, s_cliente.CORREO from s_productovendido ";
        sql += "inner join (select codigodebarras as cb from  ";
        sql += "s_producto inner join s_presentacion on s_presentacion.id=s_producto.idpresentacion where precioporunidad>=100000) ";
        sql += "on cb = s_productovendido.codigodebarras inner join s_factura on s_factura.id = idfactura ";
        sql += "inner join s_cliente on s_cliente.numdoc = s_factura.numdoc";
        Query q = pm.newQuery(SQL, sql);
        return q.executeList();
    }


    public List<Object[]> buenosClientes2(PersistenceManager pm){
        String sql = "select distinct s_cliente.NUMDOC, s_cliente.NOMBRE, s_cliente.CORREO from s_productovendido ";
        sql += "inner join (select codigodebarras as cb from  ";
        sql += "s_producto inner join s_categoria on s_categoria.id=s_producto.idcategoria where s_categoria.nombre='Tools' or s_categoria.nombre='Electronics')  ";
        sql += "on cb = s_productovendido.codigodebarras inner join s_factura on s_factura.id = idfactura ";
        sql += "inner join s_cliente on s_cliente.numdoc = s_factura.numdoc";
        Query q = pm.newQuery(SQL, sql);
        return q.executeList();
    }

    }

    public List<Object[]> consultarConsumoV2(PersistenceManager persistenceManager, String codigoDeBarras, String fecha1, String fecha2, String orden) {
        if(orden.equals("nombre"))
        {
            orden="NOMBRE";
        }
        if(orden.equals("cantidad"))
        {
            orden="CANTIDAD";
        }
        if(orden.equals("puntos"))
        {
            orden="PUNTOS";
        }

        String sql = "SELECT NUMDOC, TIPODOC, NOMBRE, CORREO, MEDIOPAGO, PUNTOS, SUM(cantidades) AS cantidad ";
        sql += "FROM ( (SELECT S_CLIENTE.NUMDOC, S_CLIENTE.TIPODOC, S_CLIENTE.NOMBRE,S_CLIENTE.CORREO, ";
        sql += "S_CLIENTE.MEDIOPAGO, S_CLIENTE.PUNTOS, productoVendido.cantidad AS cantidades ";
        sql += "FROM S_CLIENTE ";
        sql += "JOIN S_FACTURA factura ON factura.numdoc = S_CLIENTE.numdoc ";
        sql += "JOIN S_PRODUCTOVENDIDO productoVendido ON productoVendido.idFactura = factura.id) ";
        sql += "MINUS ";
        sql += "(SELECT S_CLIENTE.NUMDOC, S_CLIENTE.TIPODOC,S_CLIENTE.NOMBRE, S_CLIENTE.CORREO, ";
        sql += "S_CLIENTE.MEDIOPAGO,S_CLIENTE.PUNTOS, productoVendido.cantidad AS cantidades ";
        sql += "FROM S_CLIENTE ";
        sql += "JOIN S_FACTURA factura ON factura.numdoc = S_CLIENTE.numdoc ";
        sql += "JOIN S_PRODUCTOVENDIDO productoVendido ON productoVendido.idFactura = factura.id ";
        sql += "WHERE factura.fecha BETWEEN TO_DATE('" + fecha1 + "','DD-MM-YYYY')  AND TO_DATE('" + fecha2 + "','DD-MM-YYYY') AND productoVendido.CODIGODEBARRAS = '" + codigoDeBarras + "')) ";
        sql += "GROUP BY NUMDOC, TIPODOC, NOMBRE, CORREO, MEDIOPAGO, PUNTOS ";
        sql += "ORDER BY " + orden + " ASC";

        Query q = persistenceManager.newQuery(SQL, sql);
        return q.executeList();
    }
}


