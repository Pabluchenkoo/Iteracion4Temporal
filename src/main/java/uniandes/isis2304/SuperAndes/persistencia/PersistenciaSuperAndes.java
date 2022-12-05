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


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import uniandes.isis2304.SuperAndes.negocio.*;

/**
 * Clase para el manejador de persistencia del proyecto SuperAndes
 * Traduce la información entre objetos Java y tuplas de la base de datos, en ambos sentidos
 * Sigue un patrón SINGLETON (Sólo puede haber UN objeto de esta clase) para comunicarse de manera correcta
 * con la base de datos

 * 
 * @author
 */
public class PersistenciaSuperAndes
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(PersistenciaSuperAndes.class.getName());
	
	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
	 */
	private static PersistenciaSuperAndes instance;
	
	/**
	 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;
	
	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador, supermercado, sucursal, categoria, almacenamiento,lote,presentacion
	 * producto, oferta, cliente, factura, productoVendido, localVentas, Rol,
	 * usuario, proveedor, ordenPedido, productoProveedor, ordenEntregada, ofertaNxM
	 * ofertaDescuento, ofertaPagueXLleveY,ofertaMenorQueSuma2.
	 */
	private List <String> tablas;
	
	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaSuperAndes
	 */
	private SQLUtil sqlUtil;
	


	/* ****************************************************************
	 * 			Atributos SuperAndes
	 *****************************************************************/

	/**
	 * Atributo para el acceso a la tabla SUPERMERCADO de la base de datos
	 */
	private SQLSupermercado sqlSupermercado;

	/**
	 * Atributo para el acceso a la tabla SUCURSAL de la base de datos
	 */
	private SQLSucursal sqlSucursal;

	/**
	 * Atributo para el acceso a la tabla CATEGORIA de la base de datos
	 */
	private SQLCategoria sqlCategoria;

	/**
	 * Atributo para el acceso a la tabla ALMACENAMIENTO de la base de datos
	 */
	private SQLAlmacenamiento sqlAlmacenamiento;

	/**
	 * Atributo para el acceso a la tabla LOTE de la base de datos
	 */
	private SQLLote sqlLote;

	/**
	 * Atributo para el acceso a la tabla PRESENTACION de la base de datos
	 */
	private SQLPresentacion sqlPresentacion;

	/**
	 * Atributo para el acceso a la tabla PRODUCTO de la base de datos
	 */
	private SQLProducto sqlProducto;

	/**
	 * Atributo para el acceso a la tabla OFERTA de la base de datos
	 */
	private SQLOferta sqlOferta;

	/**
	 * Atributo para el acceso a la tabla CLIENTE de la base de datos
	 */
	private SQLCliente sqlCliente;

	/**
	 * Atributo para el acceso a la tabla FACTURA de la base de datos
	 */
	private SQLFactura sqlFactura;

	/**
	 * Atributo para el acceso a la tabla PRODUCTOVENDIDO de la base de datos
	 */
	private SQLProductoVendido sqlProductoVendido;

	/**
	 * Atributo para el acceso a la tabla LOCALVENTAS de la base de datos
	 */
	private SQLLocalVentas sqlLocalVentas;

	/**
	 * Atributo para el acceso a la tabla ROL de la base de datos
	 */
	private SQLRol sqlRol;

	/**
	 * Atributo para el acceso a la tabla USUARIO de la base de datos
	 */
	private SQLUsuario sqlUsuario;

	/**
	 * Atributo para el acceso a la tabla PROVEEDOR de la base de datos
	 */
	private SQLProveedor sqlProveedor;

	/**
	 * Atributo para el acceso a la tabla ORDENPEDIDO de la base de datos
	 */
	private SQLOrdenPedido sqlOrdenPedido;

	/**
	 * Atributo para el acceso a la tabla PRODUCTOPROVEEDOR de la base de datos
	 */
	private SQLProductoProveedor sqlProductoProveedor;

	/**
	 * Atributo para el acceso a la tabla ORDENENTREGADA de la base de datos
	 */
	private SQLOrdenEntregada sqlOrdenEntregada;
	private SQLCarrito	sqlCarrito;

	private SQLProductoCarrito sqlProductoCarrito;







	/* ****************************************************************
	 * 			Métodos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/

	/**
	 * Constructor privado con valores por defecto - Patrón SINGLETON
	 */
	private PersistenciaSuperAndes()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("Parranderos");		
		crearClasesSQL ();
		
		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("SuperAndes_sequence");
		tablas.add ("S_SUPERMERCADO");
		tablas.add ("S_SUCURSAL");
		tablas.add ("S_CATEGORIA");
		tablas.add ("S_ALMACENAMIENTO");
		tablas.add ("S_LOTE");
		tablas.add ("S_PRESENTACION");
		tablas.add ("S_PRODUCTO");
		tablas.add ("S_OFERTA");
		tablas.add ("S_CLIENTE");
		tablas.add ("S_FACTURA");
		tablas.add ("S_PRODUCTOVENDIDO");
		tablas.add ("S_LOCALVENTAS");
		tablas.add ("S_ROL");
		tablas.add ("S_USUARIO");
		tablas.add ("S_PROVEEDOR");
		tablas.add ("S_ORDENPEDIDO");
		tablas.add ("S_PRODUCTOPROVEEDOR");
		tablas.add ("S_ORDENENTREGADA");
		tablas.add ("S_CARRO");
		tablas.add ("S_PRODUCTOCARRITO");


}

	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaSuperAndes(JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);
		
		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}

	/**
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaSuperAndes getInstance ()
	{
		System.out.println(instance.toString());
		if (instance == null)
		{
			instance = new PersistenciaSuperAndes();
		}
		return instance;
	}
	
	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaSuperAndes getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaSuperAndes(tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}
	
	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}
		
		return resp;
	}
	
	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{
		sqlSupermercado = new SQLSupermercado(this);
		sqlSucursal = new SQLSucursal(this);
		sqlCategoria = new SQLCategoria(this);
		sqlAlmacenamiento = new SQLAlmacenamiento(this);
		sqlLote = new SQLLote(this);
		sqlPresentacion = new SQLPresentacion(this);
		sqlProducto = new SQLProducto(this);
		sqlOferta = new SQLOferta(this);
		sqlCliente = new SQLCliente(this);
		sqlFactura = new SQLFactura(this);
		sqlProductoVendido = new SQLProductoVendido(this);
		sqlLocalVentas = new SQLLocalVentas(this);
		sqlRol = new SQLRol(this);
		sqlUsuario = new SQLUsuario(this);
		sqlProveedor = new SQLProveedor(this);
		sqlOrdenPedido = new SQLOrdenPedido(this);
		sqlProductoProveedor = new SQLProductoProveedor(this);
		sqlOrdenEntregada = new SQLOrdenEntregada(this);
		sqlUtil = new SQLUtil(this);
		sqlCarrito = new SQLCarrito(this);
		sqlProductoCarrito = new SQLProductoCarrito(this);

	}

	/**
	 * @return La cadena de caracteres con el nombre del secuenciador de SuperAndes
	 */
	public String darSeqSuperAndes ()
	{

		return tablas.get (0);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Supermercado de SuperAndes
	 */
	public String darTablaSupermercado ()
	{
		return tablas.get (1);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Sucursal de SuperAndes
	 */
	public String darTablaSucursal ()
	{
		return tablas.get (2);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Categoria de SuperAndes
	 */
	public String darTablaCategoria ()
	{
		return tablas.get (3);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Almacenamiento de SuperAndes
	 */
	public String darTablaAlmacenamiento ()
	{
		return tablas.get (4);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Lote de SuperAndes
	 */
	public String darTablaLote ()
	{
		return tablas.get (5);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Presentacion de SuperAndes
	 */
	public String darTablaPresentacion ()
	{
		return tablas.get (6);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Producto de SuperAndes
	 */
	public String darTablaProducto ()
	{
		return tablas.get (7);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Oferta de SuperAndes
	 */
	public String darTablaOferta ()
	{
		return tablas.get (8);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Cliente de SuperAndes
	 */
	public String darTablaCliente ()
	{
		return tablas.get (9);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Factura de SuperAndes
	 */
	public String darTablaFactura ()
	{
		return tablas.get (10);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de ProductoVendido de SuperAndes
	 */
	public String darTablaProductoVendido ()
	{
		return tablas.get (11);
	}

/**
	 * @return La cadena de caracteres con el nombre de la tabla de LocalVentas de SuperAndes
	 */
	public String darTablaLocalVentas ()
	{
		return tablas.get (12);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Rol de SuperAndes
	 */
	public String darTablaRol ()
	{
		return tablas.get (13);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Usuario de SuperAndes
	 */
	public String darTablaUsuario ()
	{
		return tablas.get (14);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Proveedor de SuperAndes
	 */
	public String darTablaProveedor ()
	{
		return tablas.get (15);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de OrdenPedido de SuperAndes
	 */
	public String darTablaOrdenPedido ()
	{
		return tablas.get (16);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de ProductoProveedor de SuperAndes
	 */
	public String darTablaProductoProveedor ()
	{
		return tablas.get (17);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de OrdenEntregada de SuperAndes
	 */
	public String darTablaOrdenEntregada ()
	{
		return tablas.get (18);
	}

	public String darTablaOfertaNxM () { return tablas.get (19);}

	public String darTablaOfertaDescuento () { return tablas.get (20);}

	public String darTablaOfertaPagueXLleveY () { return tablas.get (21);}
	public String darTablaCarrito () { return tablas.get (22);}



	/* ****************************************************************
	 * 			Requerimientos Funcionales
	 *****************************************************************/
	public Rol adicionarRol(String nombre)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long idRol = nextval ();
			long tuplasInsertadas = sqlRol.adicionarRol(pm, idRol, nombre);
			tx.commit();

			log.trace ("Inserción rol: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
			return new Rol (idRol, nombre);
		}
		catch (Exception e)
		{
//	          e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public List<Object[]> consultarConsumoO( long cod,String fecha1,String fecha2, String arg,String orden){
		return sqlProductoVendido.consultarConsumoO(pmf.getPersistenceManager(), cod, fecha1, fecha2, arg, orden);
	}

	public OfertaNxM adicionarOfertaNxM( String codBarras, int unidades, String fecha, int n, int m)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			long idOferta = nextval ();
			tx.begin();
			sqlOferta.adicionarOfertaN(pm, idOferta, codBarras, unidades, fecha);
			tx.commit();
			tx.begin();
			long tuplasInsertadas = sqlOferta.adicionarOfertaNxM(pm, idOferta, n,m);
			tx.commit();

			log.trace ("Inserción oferta: " + idOferta + ": " + tuplasInsertadas + " tuplas insertadas");
			return new OfertaNxM (idOferta, n, m);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public Object[] obtenerRol(long idRol)
	{
		return sqlRol.darRolPorId(pmf.getPersistenceManager(), idRol);
	}
	public List<Object[]> obtenerCarrito(long cedula)
	{
		return sqlCarrito.darCarritoPorId(pmf.getPersistenceManager(), cedula);
	}
	public Object[] obtenerCliente(long idCliente)
	{
		Object[] cliente = sqlCliente.darClientePorId(pmf.getPersistenceManager(), idCliente);
		return cliente;
	}

	public Object[] logUsuario(long cedula)
	{
		Object[] usuario = sqlUsuario.darUsuarioPorId(pmf.getPersistenceManager(), cedula);
		return usuario;
	}

	public OfertaPagueXLleveY registrarPagueXLLeveY( String codBarras, int unidades, String fecha, int x, int y)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			long idOferta = nextval ();
			tx.begin();
			sqlOferta.adicionarOfertaN(pm, idOferta, codBarras, unidades, fecha);
			tx.commit();
			tx.begin();
			long tuplasInsertadas = sqlOferta.adicionarOfertaPagueXLleveY(pm, idOferta, x,y);
			tx.commit();

			log.trace ("Inserción oferta: " + idOferta + ": " + tuplasInsertadas + " tuplas insertadas");
			return new OfertaPagueXLleveY (idOferta, x, y);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public void finalizarPromociones(String fecha){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			sqlOferta.finalizarPromociones(pm, fecha);
			tx.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public void aprovisionarEstante(long idEstante)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{

			Object lote = sqlLote.loteProvisionar(pm, idEstante);
			Object[] tupla = (Object[]) lote;
			BigDecimal peso = (BigDecimal) tupla[5];
			BigDecimal area = (BigDecimal) tupla[4];
			BigDecimal pesoOcupado = (BigDecimal) tupla[6];
			BigDecimal areaOcupada = (BigDecimal) tupla[7];
			Double pesoDisponible = peso.doubleValue() - pesoOcupado.doubleValue();
			Double areaDisponible = area.doubleValue() - areaOcupada.doubleValue();
			Object maxlote= sqlLote.maxLote(pm, pesoDisponible, areaDisponible, idEstante);
			Object[] tupla2 = (Object[]) maxlote;
			BigDecimal idLote = (BigDecimal) tupla2[1];
			BigDecimal pesoLote = (BigDecimal) tupla2[4];
			BigDecimal areaLote = (BigDecimal) tupla2[5];
			long idLote2 = idLote.longValue();
			tx.begin();
			sqlLote.asignarEstante(pm, idLote2, idEstante);
			tx.commit();
			Double newPeso = pesoOcupado.doubleValue() + pesoLote.doubleValue();
			Double newArea = areaOcupada.doubleValue() + areaLote.doubleValue();
			tx.begin();
			sqlAlmacenamiento.aprovisionarEstante(pm, idEstante, newPeso, newArea);
			tx.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public void recibirPedido(long idPedido)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			Object orden = sqlOrdenPedido.darOrdenPedido(pm, idPedido);
			Object[] tupla = (Object[]) orden;
			String nombre = (String) tupla[0];
			Timestamp fecha = (Timestamp) tupla[1];
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String fechaString = fecha.toLocalDateTime().format(formatter);
			tx.begin();
			sqlOrdenEntregada.recibirPedido(pm, idPedido, nombre, fechaString);
			tx.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public String realizarCompra(HashMap<String, Integer> productos ) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		long idFactura = nextval();
		Double total = 0.0;
		String rest = "";
		rest = "		Factura No " + idFactura + "		"+"\n\n";
		rest+= "nombre" + "		" + "cantidad" + "		" + "subtotal"+"\n\n";
		try {
			Timestamp fecha = new Timestamp(System.currentTimeMillis());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String fechaString = fecha.toLocalDateTime().format(formatter);
			tx.begin();
			//sqlFactura.inicializarFactura(pm, idFactura, fechaString);
			tx.commit();
			for (String codBarras : productos.keySet()) {
				int cantidad = productos.get(codBarras);
				Object oferta = sqlOferta.ofertaActiva(pm, codBarras, fechaString, cantidad);
				if (oferta != null)
				{

					Object[] tupla = (Object[]) oferta;
					BigDecimal m = (BigDecimal) tupla[0];
					long idOferta = m.longValue();
					BigDecimal n = (BigDecimal) tupla[1];
					long idTipo = n.longValue();
					if(idTipo==1)
					{
						BigDecimal descuento = sqlOferta.descuento(pm, idOferta);
						Object producto = sqlProducto.darProducto(pm, codBarras);
						Object[] tupla2 = (Object[]) producto;
						BigDecimal precio = (BigDecimal) tupla2[0];
						Double precioDouble = precio.doubleValue()*((100-descuento.doubleValue())/100);
						Double precioTotal = precio.doubleValue()*cantidad;
						double dcto = (precio.doubleValue()*(descuento.doubleValue()/100)) * cantidad;
						Double subtotal = precioDouble * cantidad;
						total += subtotal;
						BigDecimal idLote = (BigDecimal) tupla2[1];
						long idLote2 = idLote.longValue();
						String nombre = (String) tupla2[2];
						tx.begin();
						sqlLote.actualizarCantidad(pm, idLote2 , cantidad);
						tx.commit();
						rest+= nombre + "		" + cantidad + "		" + precioTotal+"\n\n";
						rest+= "Descuento: 	" + descuento + "%			"+"-"+dcto+"\n\n";
					}
					if(idTipo == 2)
					{
						Object promo = sqlOferta.nxm(pm, idOferta);
						Object[] tupla3 = (Object[]) promo;
						n = (BigDecimal) tupla3[0];
						m = (BigDecimal) tupla3[1];
						double totalGratis = 0;
						int mI = m.intValue();
						if(cantidad>n.intValue())
						{
							double residuo = cantidad % n.intValue();
							double entero = (cantidad/2)- residuo;
							int gratis = n.intValue() - mI;
							totalGratis = gratis * entero;
							rest += "Unidades gratis :	" + totalGratis + "\n\n";
						}
						Object producto = sqlProducto.darProducto(pm, codBarras);
						Object[] tupla4 = (Object[]) producto;
						BigDecimal precio = (BigDecimal) tupla4[0];
						Double precioDouble = precio.doubleValue();
						Double subtotal = precioDouble * (cantidad-totalGratis);
						total += subtotal;
						BigDecimal idLote = (BigDecimal) tupla4[1];
						long idLote2 = idLote.longValue();
						String nombre = (String) tupla4[2];
						tx.begin();
						sqlLote.actualizarCantidad(pm, idLote2 , cantidad);
						tx.commit();
						rest+= nombre + "		" + cantidad + "		" + subtotal+"\n";
					}

				}
				else {
					Object producto = sqlProducto.darProducto(pm, codBarras);
					Object[] tupla = (Object[]) producto;
					BigDecimal precio = (BigDecimal) tupla[0];
					Double precioDouble = precio.doubleValue();
					Double subtotal = precioDouble * cantidad;
					total += subtotal;
					BigDecimal idLote = (BigDecimal) tupla[1];
					long idLote2 = idLote.longValue();
					String nombre = (String) tupla[2];
					tx.begin();
					sqlLote.actualizarCantidad(pm, idLote2 , cantidad);
					tx.commit();
					rest+= nombre + "		" + cantidad + "		" + subtotal+"\n";
				}
			}
			tx.begin();
			sqlFactura.actualizarTotalFactura(pm, idFactura, total);
			rest+= "		Total: " + total;
			tx.commit();


		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
			return rest;
		}
	}

	public void consolidarPedido(long nit, long idSucursal)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			long idOrden = nextval();
			sqlOrdenPedido.crearOrdenConsolidada(pm, idOrden, nit, idSucursal);
			sqlOrdenPedido.consolidarPedido(pm, idOrden, nit, idSucursal);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	public void recibirPedidoConsolidado(long idOrden, long idSucursal)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			List<Object[]> ordenes = sqlOrdenPedido.ordenesPedidoConsolidado(pm, idOrden, idSucursal);
			sqlOrdenPedido.eliminacionOrdenesPedido(pm, idOrden, idSucursal);
			for (Object[] orden:ordenes)
			{
				long idOrdenE = nextval();
				long idLote = nextval();
				String codBarras = (String) orden[0];
				int cantidad = ((BigDecimal) orden[1]).intValue();
				Object Bodega = sqlAlmacenamiento.darIdBodega(pm, idSucursal);
				long idBodega = ((BigDecimal) Bodega).longValue();
				Timestamp fecha = new Timestamp(System.currentTimeMillis());
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				String fechaString = fecha.toLocalDateTime().format(formatter);
				sqlLote.crearLote(pm, idLote, idBodega, codBarras, cantidad);
				sqlOrdenEntregada.crearOrdenEntregada(pm, idOrdenE,fechaString, cantidad, codBarras);
			}
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	public void abandonarCarrito(long cedula)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			sqlCarrito.abandonarCarrito(pm, cedula);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
	public void pagarCompra(List<Object[]> carrito,long cedula)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			long idFactura = nextval();
			Timestamp fecha = new Timestamp(System.currentTimeMillis());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String fechaString = fecha.toLocalDateTime().format(formatter);
			sqlFactura.inicializarFactura(pm, idFactura, fechaString, cedula);
			double total = 0;
			BigDecimal idCarritoB = (BigDecimal) (carrito.get(0)[0]);
			long idCarrito = idCarritoB.longValue();
			sqlCarrito.limpiarCarrito(pm, idCarrito);
			sqlCarrito.liberarCarrito(pm, idCarrito);
			for (Object[] producto:carrito)
			{
				long idProductoVendido = nextval();
				BigDecimal idLoteB = (BigDecimal) producto[1];
				BigDecimal cantidadB = (BigDecimal) producto[3];
				long idLote = idLoteB.longValue();
				int cantidad = cantidadB.intValue();
				sqlLote.actualizarCantidad(pm, idLote , cantidad);
				BigDecimal precioB = (BigDecimal) producto[4];
				Double precio = precioB.doubleValue();
				total += precio * cantidad;
				String codigodebarras = (String) producto[6];
				sqlProductoVendido.agregarProductoVendido(pm, idFactura, codigodebarras, idProductoVendido, cantidad);
			}
			sqlFactura.actualizarTotalFactura(pm, idFactura, total);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
	public OfertaDescuento adicionarOfertaDescuento(String codBarras, int unidades, String fecha, double porcentaje)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			long idOferta = nextval ();
			tx.begin();
			sqlOferta.adicionarOfertaD(pm, idOferta, codBarras, unidades, fecha);
			tx.commit();
			tx.begin();
			long tuplasInsertadas = sqlOferta.adicionarOfertaDescuento(pm, idOferta, porcentaje);
			tx.commit();


			log.trace ("Inserción oferta: " + codBarras + ": " + tuplasInsertadas + " tuplas insertadas");
			return new OfertaDescuento(idOferta, porcentaje);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/* ****************************************************************
	 * 			Requerimientos funcionales 15 al 17
	 *			Iteración 3
	 *****************************************************************/

	public Carrito solicitarCarrito(long cedula) throws Exception {

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		long idCarrito = nextval();
		tx.begin();
		sqlCarrito.solicitarCarrito(pm, idCarrito, cedula);
		tx.commit();

		int cedulaInt = (int) cedula;
		log.info("Se creo el carrito con id: " + idCarrito);


		if (tx.isActive()) {
			tx.rollback();
		}
		pm.close();

		return new Carrito(idCarrito,1, cedulaInt);


	}

	public String adicionarProductoCarrito(String nombreProducto, int cant, String nombreCliente) {

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{

			String codBarras = sqlProducto.darCodBarras(pm, nombreProducto);
			BigDecimal cedula = sqlCliente.darCedula(pm, nombreCliente);
			BigDecimal idCarrito = sqlCarrito.darIdCarrito(pm, cedula);

			long idCarritoL = idCarrito.longValue();

			tx.begin();
			sqlProductoCarrito.agregarProductoCarrito(pm,idCarritoL, codBarras, cant );
			tx.commit();


			return "Producto agregado al carrito: " + nombreProducto;
		}
		catch (Exception e)
		{
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return "Exception : " + e.getMessage() + "\n" + darDetalleException(e);
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}

	public String[] nombreProductos() {

		List<String> nombres = sqlProducto.darNombreProductos(pmf.getPersistenceManager());
		String[] resp = new String[nombres.size()];
		for(int i = 0; i < nombres.size(); i++)
		{
			resp[i] = nombres.get(i);
		}
		return resp;
	}


	public String[] productosEnCarrito(String nombreCliente) {

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();

		try{
			BigDecimal cedula = sqlCliente.darCedula(pm, nombreCliente);
			BigDecimal idCarrito = sqlCarrito.darIdCarrito(pm, cedula);

			List<String> productos = sqlProductoCarrito.darProductosEnCarrito(pm, idCarrito);
			List<String> nombresProductos = sqlProducto.nombreProductosPorCodigo(pm, productos);

			String[] resp = new String[nombresProductos.size()];
			for(int i = 0; i < nombresProductos.size(); i++)
			{
				resp[i] = nombresProductos.get(i) ;
			}
			return resp;
		}
		catch(Exception e)
		{
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}

	public String devolverProductoCarrito(String nombreProducto, String nombreCliente) {

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			String codBarras = sqlProducto.darCodBarras(pm, nombreProducto);
			BigDecimal cedula = sqlCliente.darCedula(pm, nombreCliente);
			BigDecimal idCarrito = sqlCarrito.darIdCarrito(pm, cedula);

			tx.begin();
			sqlProductoCarrito.devolverProductoCarrito(pm, idCarrito, codBarras);
			tx.commit();

			return "Producto devuelto del carrito al estante correspondiente: " + nombreProducto;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/* ****************************************************************
	 * 			Requerimientos Consulta
	 * 			Iteración 3
	 *****************************************************************/


	public List<Object []> analizarSuperAndes(String fecha1, String fecha2, String producto) {

		PersistenceManager pm = pmf.getPersistenceManager();
		List<Object []> resp = new ArrayList<Object []> ();
		List<Object []> tuplas = sqlSucursal.mayorDemanda(pm, fecha1, fecha2, producto);
		for (Object [] tupla : tuplas)
		{
			int cantidad = ((BigDecimal) tupla [0]).intValue();
			String nombreCategoria = (String) tupla [1];
			String nombreSucursal = (String) tupla [2];

			Object [] datos = new Object [3];
			datos [0] = cantidad;
			datos [1] = nombreCategoria;
			datos [2] = nombreSucursal;
			resp.add (datos);


		}
		return resp;

	}

	public List<Object[]> analizarSuperAndes2(String fecha1, String fecha2, String producto) {

		PersistenceManager pm = pmf.getPersistenceManager();
		List<Object []> resp = new ArrayList<Object []> ();
		List<Object []> tuplas = sqlSucursal.menorDemanda(pm, fecha1, fecha2, producto);
		for (Object [] tupla : tuplas)
		{
			int cantidad = ((BigDecimal) tupla [0]).intValue();
			String nombreCategoria = (String) tupla [1];
			String nombreSucursal = (String) tupla [2];

			Object [] datos = new Object [3];
			datos [0] = cantidad;
			datos [1] = nombreCategoria;
			datos [2] = nombreSucursal;
			resp.add (datos);


		}
		return resp;

	}


	public List<Object[]> encontrarClientesFrecuentes(String sucursal) {

		List<Object[]> resp = new LinkedList<Object[]>();
		BigDecimal idSucursal = sqlSucursal.darIdSucursal(pmf.getPersistenceManager(), sucursal);

		List<Object> tuplas = sqlSucursal.encontrarClientesFrecuentes(pmf.getPersistenceManager(), idSucursal);
		for(Object tupla : tuplas)
		{
			Object [] datos = (Object []) tupla;
			String nombre = (String) datos[0];
			BigDecimal numdoc = (BigDecimal) datos[1];
			BigDecimal numeroCompras = (BigDecimal) datos[2];

			String [] respTupla = new String[4];
			respTupla[0] = nombre;
			respTupla[1] = numdoc.toString();
			respTupla[2] = numeroCompras.toString();

			resp.add(respTupla);
		}
		return resp;

	}

	/* ****************************************************************
	 * 			Requerimientos Consulta
	 *****************************************************************/
	public List<Object []> darDineroRecolectadoSucursalesPeriodo (String fechaInicio, String fechaFin)
	{
		List<Object []> resp = new LinkedList<Object []>();
		List<Object> tuplas = sqlSucursal.darDineroRecolectadoSucursalesPeriodo(pmf.getPersistenceManager(), fechaInicio, fechaFin);
		for (Object tupla : tuplas)
		{
			Object [] datos = (Object []) tupla;
			String nombreSucursal = (String) datos [0];
			double dineroRecolectado = (double) datos[1];
			Object [] respTupla = new Object [2];
			respTupla [0] = nombreSucursal;
			respTupla [1] = dineroRecolectado;
			resp.add(respTupla);
		}
		return resp;
	}


	public List<Object []> darDineroRecolectadoSucursalesAnio (String anio)
	{
		List<Object []> resp = new LinkedList<Object []>();
		List<Object> tuplas = sqlSucursal.darDineroRecolectadoSucursalesAnio(pmf.getPersistenceManager(), anio);
		for (Object tupla : tuplas)
		{
			Object [] datos = (Object []) tupla;
			String nombreSucursal = (String) datos [0];
			double dineroRecolectado = (double) datos [1];
			Object [] respTupla = new Object [2];
			respTupla [0] = nombreSucursal;
			respTupla [1] = dineroRecolectado;
			resp.add(respTupla);
		}
		return resp;
	}

	public List<Object []> mostrarIndiceOcupacionSucursales ()
	{
		List<Object []> resp = new LinkedList<Object []>();
		List<Object> tuplas = sqlSucursal.darIndiceOcupacionBodegas(pmf.getPersistenceManager());
		for (Object tupla : tuplas)
		{
			Object [] datos = (Object []) tupla;
			String nombreSucursal = (String) datos [0];
			String nombreCategoria = (String) datos [1];
			String tipoAlmacenamiento = (String) datos [2];
			BigDecimal porcentajeOcupacion = (BigDecimal) datos [3];
			BigDecimal capacidad = (BigDecimal) datos [4];
			Object [] respTupla = new Object [5];
			respTupla [0] = nombreSucursal;
			respTupla [1] = nombreCategoria;
			respTupla [2] = tipoAlmacenamiento;
			respTupla [3] = porcentajeOcupacion;
			respTupla [4] = capacidad;
			resp.add(respTupla);
		}
		return resp;
	}

	public List<Object []> darProductosEnRangoPrecio (double precioMin, double precioMax)
	{
		List<Object []> resp = new LinkedList<Object []>();
		List<Object> tuplas = sqlProducto.darProductosPorPrecio(pmf.getPersistenceManager(), precioMin, precioMax);
		for (Object tupla : tuplas)
		{
			Object [] datos = (Object []) tupla;
			String nombreProducto = (String) datos [0];
			String marca = (String) datos [1];
			String nombreCategoria = (String) datos [2];
			double precio = (double) datos [3];
			String especificacionEmpaque = (String) datos [4];

			Object [] respTupla = new Object [5];
			respTupla [0] = nombreProducto;
			respTupla [1] = marca;
			respTupla [2] = nombreCategoria;
			respTupla [3] = precio;
			respTupla [4] = especificacionEmpaque;
			resp.add(respTupla);
		}
		return resp;
	}

	public List<Object []> darProductosPorFechaVencimiento(String fechaVencimiento)
	{
		List<Object []> resp = new LinkedList<Object []>();
		List<Object> tuplas = sqlProducto.darProductosPorFechaVencimiento(pmf.getPersistenceManager(), fechaVencimiento);
		for (Object tupla : tuplas)
		{
			Object [] datos = (Object []) tupla;
			String nombreProducto = (String) datos [0];
			String marca = (String) datos [1];
			String nombreCategoria = (String) datos [2];
			double precio = (double) datos [3];
			String especificacionEmpaque = (String) datos [4];
			Timestamp fechaVencimientoProducto = (Timestamp) datos [5];

			Object [] respTupla = new Object [6];
			respTupla [0] = nombreProducto;
			respTupla [1] = marca;
			respTupla [2] = nombreCategoria;
			respTupla [3] = precio;
			respTupla [4] = especificacionEmpaque;
			respTupla [5] = fechaVencimientoProducto;
			resp.add(respTupla);
		}
		return resp;
	}

	public List<Object []> darProductosPorProveedor(int nitProveedor)
	{
		List<Object []> resp = new LinkedList<Object []>();
		List<Object> tuplas = sqlProducto.darProductosPorProveedor(pmf.getPersistenceManager(), nitProveedor);
		for (Object tupla : tuplas)
		{
			Object [] datos = (Object []) tupla;
			String nombreProducto = (String) datos [0];
			String marca = (String) datos [1];
			String nombreCategoria = (String) datos [2];
			double precio = (double) datos [3];
			String especificacionEmpaque = (String) datos [4];
			String nombreProveedor = (String) datos [5];

			Object [] respTupla = new Object [6];
			respTupla [0] = nombreProducto;
			respTupla [1] = marca;
			respTupla [2] = nombreCategoria;
			respTupla [3] = precio;
			respTupla [4] = especificacionEmpaque;
			respTupla [5] = nombreProveedor;
			resp.add(respTupla);
		}
		return resp;
	}

	public List<Object []> darProductosDisponiblesCiudad(String ciudad)
	{
		List<Object []> resp = new LinkedList<Object []>();
		List<Object> tuplas = sqlProducto.darProductosPorCiudad(pmf.getPersistenceManager(), ciudad);
		for (Object tupla : tuplas)
		{
			Object [] datos = (Object []) tupla;
			String nombreProducto = (String) datos [0];
			String marca = (String) datos [1];
			String nombreCategoria = (String) datos [2];
			double precio = (double) datos [3];
			String especificacionEmpaque = (String) datos [4];
			String ciudadSucursal = (String) datos [5];

			Object [] respTupla = new Object [6];
			respTupla [0] = nombreProducto;
			respTupla [1] = marca;
			respTupla [2] = nombreCategoria;
			respTupla [3] = precio;
			respTupla [4] = especificacionEmpaque;
			respTupla [5] = ciudadSucursal;
			resp.add(respTupla);
		}
		return resp;
	}

	public List<Object []> darProductosDisponiblesSucursal(String nombreSucursal)
	{
		List<Object []> resp = new LinkedList<Object []>();
		List<Object> tuplas = sqlProducto.darProductosPorSucursal(pmf.getPersistenceManager(), nombreSucursal);
		for (Object tupla : tuplas)
		{
			Object [] datos = (Object []) tupla;
			String nombreSucur = (String) datos [0];
			String ciudad = (String) datos [1];
			String nombreProducto = (String) datos [2];
			String marca = (String) datos [3];
			String categoria = (String) datos [4];
			double precio = (double) datos [5];
			String especificacion = (String) datos [6];

			Object [] respTupla = new Object [7];
			respTupla [0] = nombreSucur;
			respTupla [1] = ciudad;
			respTupla [2] = nombreProducto;
			respTupla [3] = marca;
			respTupla [4] = categoria;
			respTupla [5] = precio;
			respTupla [6] = especificacion;
			resp.add(respTupla);
		}
		return resp;
	}

	public List<Object []> darProductosPorCategoria(String pCategoria)
	{
		List<Object []> resp = new LinkedList<Object []>();
		List<Object> tuplas = sqlProducto.darProductosPorCategoria(pmf.getPersistenceManager(), pCategoria);
		for (Object tupla : tuplas)
		{
			Object [] datos = (Object []) tupla;
			String nombreProducto = (String) datos [0];
			String marca = (String) datos [1];
			String nombreCategoria = (String) datos [2];
			double precio = (double) datos [3];
			String especificacionEmpaque = (String) datos [4];

			Object [] respTupla = new Object [5];
			respTupla [0] = nombreProducto;
			respTupla [1] = marca;
			respTupla [2] = nombreCategoria;
			respTupla [3] = precio;
			respTupla [4] = especificacionEmpaque;
			resp.add(respTupla);
		}
		return resp;
	}

	public List<Object[]> darProductosVendidosMasDeXVeces(int pVeces)
	{
		List<Object []> resp = new LinkedList<Object []>();
		List<Object> tuplas = sqlSucursal.darProductosVendidosMasDeX(pmf.getPersistenceManager(), pVeces);
		for (Object tupla : tuplas)
		{
			Object [] datos = (Object []) tupla;
			String nombreProducto = (String) datos [0];
			int cantidad = (int) datos [1];

			Object [] respTupla = new Object [2];
			respTupla [0] = nombreProducto;
			respTupla [1] = cantidad;
			resp.add(respTupla);
		}
		return resp;
	}


	public List<Object []> mostrarComprasRealizadasAProveedores()
	{
		List<Object []> resp = new LinkedList<Object []>();
		List<Object> tuplas = sqlSucursal.darComprasAProveedores(pmf.getPersistenceManager());
		for (Object tupla : tuplas)
		{
			Object [] datos = (Object []) tupla;
			int nitProveedor = (int) datos [0];
			String nombreProveedor = (String) datos [1];
			String direccionProveedor = (String) datos [2];
			double precioTotal = ((double) datos [3]);

			Object [] respTupla = new Object [4];
			respTupla [0] = nitProveedor;
			respTupla [1] = nombreProveedor;
			respTupla [2] = direccionProveedor;
			respTupla [3] = precioTotal;
			resp.add(respTupla);
		}
		return resp;
	}

	public List<Object []> mostrarVentasRealizadasAClienteEnRango(int pDoc, String pFechaInicio, String pFechaFin)
	{
		List<Object []> resp = new LinkedList<Object []>();
		List<Object> tuplas = sqlProductoVendido.mostrarProductosVendidosClienteRangoFechas(pmf.getPersistenceManager(), pDoc, pFechaInicio, pFechaFin);
		for (Object tupla : tuplas)
		{
			Object [] datos = (Object []) tupla;
			String nombre = (String) datos[0];
			int numdocu = (int) datos[1];
			String codigodebarras = (String) datos[2];
			int cantidad = (int) datos[3];

			Object [] respTupla = new Object [4];
			respTupla [0] = nombre;
			respTupla [1] = numdocu;
			respTupla [2] = codigodebarras;
			respTupla [3] = cantidad;
			resp.add(respTupla);

		}
		return resp;
	}

	public void recolectarAbandonados()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			List<Object[]> abandonados = sqlCarrito.carritosAbandonados(pmf.getPersistenceManager());
			for (Object[] carrito:abandonados)
			{
				BigDecimal idCarritoB = (BigDecimal) carrito[0];
				long idCarrito = idCarritoB.longValue();
				sqlCarrito.limpiarCarrito(pm, idCarrito);
			}
			tx.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}

	/**
	 * Transacción para el generador de secuencia de SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return El siguiente número del secuenciador de SuperAndes
	 */
	private long nextval ()
	{
        long resp = sqlUtil.nextval (pmf.getPersistenceManager());
        log.trace ("Generando secuencia: " + resp);
        return resp;
    }
	
	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
	 */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}


	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Parranderos
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarSuperAndes()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long [] resp = sqlUtil.limpiarSuperAndes(pm);
            tx.commit ();
            log.info ("Borrada la base de datos");
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return new long[] {-1, -1, -1, -1, -1, -1, -1};
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
		
	}



}
