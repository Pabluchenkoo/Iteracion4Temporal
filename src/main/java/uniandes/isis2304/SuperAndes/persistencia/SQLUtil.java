/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.SuperAndes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos de SuperAndes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author
 */
class SQLUtil
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaSuperAndes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaSuperAndes pp;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/

	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLUtil (PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para obtener un nuevo número de secuencia
	 * @param pm - El manejador de persistencia
	 * @return El número de secuencia generado
	 */
	public long nextval (PersistenceManager pm)
	{
        Query q = pm.newQuery(SQL, "SELECT "+ pp.darSeqSuperAndes () + ".nextval FROM DUAL");
        q.setResultClass(Long.class);
        long resp = (long) q.executeUnique();
        return resp;
	}

	/**
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @param pm - El manejador de persistencia
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarSuperAndes(PersistenceManager pm)
	{
		Query qLote = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaLote());
		Query qOferta = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOferta());
		Query qOrdenEntregada = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOrdenEntregada());
		Query qProductoProveedor = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProductoProveedor());
		Query qProductoVendido = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProductoVendido());
		Query qProveedor = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProveedor());
		Query qUsuario = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaUsuario());
		Query qAlmacenamiento = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAlmacenamiento());
		Query qFactura = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaFactura());
		Query qOrdenPedido = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOrdenPedido());
		Query qProducto = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProducto());
		Query qSucursal = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaSucursal());
		Query qSupermercado = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaSupermercado());
		Query qCategoria = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCategoria());
		Query qCliente = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCliente());
		Query qPresentacion = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPresentacion());
		Query qLocalVentas = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaLocalVentas());
		Query qRol = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaRol());

		long loteEliminados = (long) qLote.executeUnique ();
		long ofertaEliminados = (long) qOferta.executeUnique ();
		long ordenEntregadaEliminados = (long) qOrdenEntregada.executeUnique ();
		long productoProveedorEliminados = (long) qProductoProveedor.executeUnique ();
		long productoVendidoEliminados = (long) qProductoVendido.executeUnique ();
		long proveedorEliminados = (long) qProveedor.executeUnique ();
		long usuarioEliminados = (long) qUsuario.executeUnique ();
		long almacenamientoEliminados = (long) qAlmacenamiento.executeUnique ();
		long facturaEliminados = (long) qFactura.executeUnique ();
		long ordenPedidoEliminados = (long) qOrdenPedido.executeUnique ();
		long productoEliminados = (long) qProducto.executeUnique ();
		long sucursalEliminados = (long) qSucursal.executeUnique ();
		long supermercadoEliminados = (long) qSupermercado.executeUnique ();
		long categoriaEliminados = (long) qCategoria.executeUnique ();
		long clienteEliminados = (long) qCliente.executeUnique ();
		long presentacionEliminados = (long) qPresentacion.executeUnique ();
		long localVentasEliminados = (long) qLocalVentas.executeUnique ();
		long rolEliminados = (long) qRol.executeUnique ();

		return new long[] {loteEliminados, ofertaEliminados, ordenEntregadaEliminados, productoProveedorEliminados,
				productoVendidoEliminados, proveedorEliminados, usuarioEliminados, almacenamientoEliminados, facturaEliminados,
				ordenPedidoEliminados, productoEliminados, sucursalEliminados, supermercadoEliminados, categoriaEliminados,
				clienteEliminados, presentacionEliminados, localVentasEliminados, rolEliminados};


	}

}
