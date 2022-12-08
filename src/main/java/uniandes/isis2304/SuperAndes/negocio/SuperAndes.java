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

package uniandes.isis2304.SuperAndes.negocio;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import com.google.gson.JsonObject;
import uniandes.isis2304.SuperAndes.persistencia.PersistenciaSuperAndes;

/**
 * Clase principal del negocio
 * Sarisface todos los requerimientos funcionales del negocio
 *
 * @author Pablo Junco
 */
public class SuperAndes
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(SuperAndes.class.getName());
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaSuperAndes pp;

	private String nombreUsuario;
	private String nombreCliente;
	private long cedula;
	private long rol;
	private long idSucursal;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public SuperAndes()
	{

		pp = PersistenciaSuperAndes.getInstance ();
	}
	
	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public SuperAndes(JsonObject tableConfig)
	{

		pp = PersistenciaSuperAndes.getInstance(tableConfig);
		nombreUsuario = "";
		nombreCliente = "";
		cedula = 0;
		rol = 0;
	}
	
	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pp.cerrarUnidadPersistencia ();
	}
	
	/* ****************************************************************
	 * 			requerimientos funcionales
	 *****************************************************************/
	public Rol adicionarRol(String nombre) {
		log.info ("Adicionando rol: " + nombre);
		Rol rol = pp.adicionarRol(nombre);
		log.info ("Adicionando rol: " + rol);
		return rol;
	}
	public void abandonarCarrito()
	{
		if (nombreCliente != "" ) {
			log.info("Abandonando carrito");
			pp.abandonarCarrito(cedula);
			log.info("Carrito abandonado");
		}
	}
	public void consolidarPedido(long nitProveedor) throws Exception {
		if (nombreUsuario != "" ) {
			log.info("Consolidando pedido");
			pp.consolidarPedido(nitProveedor, idSucursal);
			log.info("Pedido consolidado");
		}
		else {
			throw new Exception("No se puede consolidar un pedido como cliente");
		}
	}

	public void recibirPedidoConsolidado(long idPedido)
	{
		if (nombreUsuario != "" ) {
			log.info("Recibiendo pedido consolidado");
			pp.recibirPedidoConsolidado(idPedido, idSucursal);
			log.info("Pedido consolidado recibido");
		}
	}
	public void recolectarAbandonados() throws Exception {
		System.out.println(rol == 92);
		if (rol != 92)
		{
			log.info("Recolectando abandonados");
			pp.recolectarAbandonados();
			log.info("Abandonados recolectados");
		}
		else{
			log.info("No tiene permisos para recolectar abandonados");
			throw new Exception("No tiene permisos para recolectar abandonados");
		}
	}
	public void pagarCompra(List<Object[]> carrito) throws Exception {
		if (carrito != null && nombreCliente != "" && carrito.size() > 0) {
			log.info ("Pagar compra: " + carrito);
			pp.pagarCompra(carrito, cedula);
			log.info ("Pagar compra: " + carrito);
			}
		else{
			throw new Exception("Esta operacion solo puede ser realizada por un cliente");
		}

		}

	public boolean logCliente(long cedula) {
		Object[] resp = pp.obtenerCliente(cedula);
		if (resp == null) {
			return false;
		}
		else {
			BigDecimal cedulaR = (BigDecimal) resp[1];
			this.cedula = cedulaR.longValue();
			nombreCliente = (String) resp[0];
			return true;
		}
	}
	public List<Object[]> obtenerCarrito(){
		List<Object[]> carrito = pp.obtenerCarrito(cedula);
		return carrito;
	}

	public boolean logUsuario(long cedula, String contrasena) {
		Object[] resp = pp.logUsuario(cedula);
		if (resp == null) {
			return false;
		}
		String palabra = (String) resp[3];
		if (palabra.equals(contrasena)) {
			nombreUsuario = (String) resp[0];
			BigDecimal idRol = (BigDecimal) resp[1];
			long idRolR = idRol.longValue();
			this.rol = idRolR;
			BigDecimal cedulaR = (BigDecimal) resp[2];
			this.cedula = cedulaR.longValue();
			BigDecimal idSucursalR = (BigDecimal) resp[4];
			this.idSucursal = idSucursalR.longValue();
			return true;
		}
		else {
			return false;
		}
	}

	public List<Object[]> consultarConsumoO( long cod,String fecha1,String fecha2, String arg,String orden){
		log.info ("Consultando consumo: " + cod);
		List<Object[]> consumo = pp.consultarConsumoO(cod, fecha1, fecha2, arg, orden);
		log.info ("Consumo consultado: " + consumo);
		return consumo;
	}

	public OfertaDescuento adicionarOfertaDescuento(String codBarras, int unidades, String fecha, double porcentaje){
		log.info ("Adicionando oferta descuento: " + codBarras);
		OfertaDescuento oferta = pp.adicionarOfertaDescuento(codBarras, unidades, fecha, porcentaje);
		log.info ("Adicionando oferta descuento: " + codBarras);
		return oferta;
	}

	public OfertaNxM adicionarNxM(String codBarras, int unidades, String fecha, int n, int m){
		log.info ("Adicionando oferta NxM: " + codBarras);
		OfertaNxM oferta = pp.adicionarOfertaNxM(codBarras, unidades, fecha, n, m);
		log.info ("Adicionando oferta NxM: " + codBarras);
		return oferta;
	}

	public OfertaPagueXLleveY registrarPagueXLLeveY(String codBarras, int unidades, String fecha, int n, int m){
	log.info ("Adicionando oferta NxM: " + codBarras);
	OfertaPagueXLleveY oferta = pp.registrarPagueXLLeveY(codBarras, unidades, fecha, n, m);
	log.info ("Adicionando oferta NxM: " + codBarras);
	return oferta;
}

	public void finalizarPromociones(String fecha){
		log.info ("Finalizando promociones: " + fecha);
		pp.finalizarPromociones(fecha);
		log.info ("Finalizando promociones a la fecha: " + fecha);
	}

	public void aprovisionarEstante(long idAlmacenamiento)
	{
		log.info ("Aprovisionando estante: " + idAlmacenamiento);
		pp.aprovisionarEstante(idAlmacenamiento);
		log.info ("Aprovisionando estante: " + idAlmacenamiento);
	}

	public String realizarCompra(HashMap<String,Integer> productos)
	{
		log.info ("Realizando compra: " + productos);
		String resultado = pp.realizarCompra(productos);
		log.info ("Realizando compra: " + productos);
		return resultado;
	}

	public void recibirPedido(long idPedido)
	{
		log.info ("Recibiendo pedido: " + idPedido);
		pp.recibirPedido(idPedido);
		log.info ("Recibiendo pedido: " + idPedido);
	}

	public String obtenerNombreRol(long idRol)
	{
		log.info ("Obteniendo rol: " + rol);
		Object[] rol = pp.obtenerRol(idRol);
		log.info ("Obteniendo rol: " + rol);
		return (String) rol[1];
	}

	/* ****************************************************************
	 * 			Requerimientos funcionales 15 al 17
	 *			Iteración 3
	 *****************************************************************/

	public String solicitarCarrito() throws Exception {

		log.info("Solicitando carrito al cliente: " + nombreCliente + " con cedula: " + cedula);
		Carrito carrito = pp.solicitarCarrito(cedula);
		long idCarrito = carrito.getIdCarrito();
		String resultado = "Se ha solicitado un carrito con el id: " + idCarrito;
		resultado += "\n bajo la cedula del comprador: " + carrito.getNumDoc();
		return resultado;


	}

	public String[] nombreProductos() {
		log.info("Obteniendo nombre de productos");
		String[] productos = pp.nombreProductos();
		log.info("Obteniendo nombre de productos");
		return productos;
	}

	public String adicionarProductoCarrito(String producto, int cant) {

		log.info("Adicionando producto al carrito: " + producto);
		String resultado = pp.adicionarProductoCarrito(producto, cant, nombreCliente);
		log.info("Adicionando producto al carrito: " + producto);
		return resultado;
	}

	public String[] productosEnCarrito() {

		log.info("Obteniendo productos en carrito");
		String[] productos = pp.productosEnCarrito(nombreCliente);
		log.info("Obteniendo productos en carrito");
		return productos;

	}

	/* ****************************************************************
	 * 			requerimientos de consulta
	 *****************************************************************/

	public List<Object []> darDineroRecolectadoPorSucursalPeriodo( String fechaInicio, String fechaFin) {
		log.info ("Consultando dinero recolectado por sucursal en el periodo: " + fechaInicio + " - " + fechaFin);
		List<Object []> resp = pp.darDineroRecolectadoSucursalesPeriodo(fechaInicio, fechaFin);
		log.info ("Consultando dinero recolectado por sucursal en el periodo: " + fechaInicio + " - " + fechaFin+ resp.size() + " sucursales encontradas");
		return resp;
	}

	public List<Object []> darDineroRecolectadoPorSucursalAnio( String anio) {
		log.info ("Consultando dinero recolectado por sucursal en el anio: " + anio);
		List<Object []> resp = pp.darDineroRecolectadoSucursalesAnio(anio);
		log.info ("Consultando dinero recolectado por sucursal en el anio: " + anio+ resp.size() + " sucursales encontradas");
		return resp;
	}


	public List<Object[]> darIndiceOcupacionSucursales() {
		log.info ("Consultando indice de ocupacion de sucursales: " );
		List<Object []> resp = pp.mostrarIndiceOcupacionSucursales();
		log.info ("Consultando indice de ocupacion de sucursales: ");
		return resp;
	}

	public List<Object []> darProductosPorPrecio(double precioMin, double precioMax) {
		log.info ("Consultando productos por precio: " + precioMin + " - " + precioMax);
		List<Object []> resp = pp.darProductosEnRangoPrecio(precioMin, precioMax);
		log.info ("Consultando productos por precio: " + precioMin + " - " + precioMax+ resp.size() + " productos encontrados");
		return resp;
	}

	public List<Object []> darProductosPorFechaVencimiento(String fecha)
	{
		log.info ("Consultando productos por fecha de vencimiento: " + fecha);
		List<Object []> resp = pp.darProductosPorFechaVencimiento(fecha);
		log.info ("Consultando productos por fecha de vencimiento: " + fecha+ resp.size() + " productos encontrados");
		return resp;
	}

	public List<Object []> darProductosPorProveedor(int nit)
	{
		log.info ("Consultando productos por proveedor: " + nit);
		List<Object []> resp = pp.darProductosPorProveedor(nit);
		log.info ("Consultando productos por proveedor: " + nit+ resp.size() + " productos encontrados");
		return resp;
	}

	public List<Object []> darProductosDisponiblesCiudad(String ciudad)
	{
		log.info ("Consultando productos disponibles en la ciudad: " + ciudad);
		List<Object []> resp = pp.darProductosDisponiblesCiudad(ciudad);
		log.info ("Consultando productos disponibles en la ciudad: " + ciudad+ resp.size() + " productos encontrados");
		return resp;
	}

	public List<Object []> darProductosDisponiblesSucursal(String sucursal)
	{
		log.info ("Consultando productos disponibles en la sucursal: " + sucursal);
		List<Object []> resp = pp.darProductosDisponiblesSucursal(sucursal);
		log.info ("Consultando productos disponibles en la sucursal: " + sucursal+ resp.size() + " productos encontrados");
		return resp;
	}

	public List<Object []> darProductosPorCategoria(String pCategoria)
	{
		log.info ("Consultando productos por categoria: " + pCategoria);
		List<Object []> resp = pp.darProductosPorCategoria(pCategoria);
		log.info ("Consultando productos por categoria: " + pCategoria+ resp.size() + " productos encontrados");
		return resp;
	}

	public List<Object []> darProductoVendidosMasDeXVeces(int pCantidad)
	{
		log.info ("Consultando productos vendidos mas de " + pCantidad + " veces");
		List<Object []> resp = pp.darProductosVendidosMasDeXVeces(pCantidad);
		log.info ("Consultando productos vendidos mas de " + pCantidad + " veces" + resp.size() + " productos encontrados");
		return resp;
	}

	public List<Object []> mostrarComprasRealizadasAProveedores()
	{
		log.info ("Consultando compras realizadas a proveedores");
		List<Object []> resp = pp.mostrarComprasRealizadasAProveedores();
		log.info ("Consultando compras realizadas a proveedores" + resp.size() + " compras encontradas");
		return resp;
	}

	public List<Object []> mostrarVentasAClienteEnRango(int pDoc, String pFechaInicio, String pFechafin)
	{
		log.info ("Consultando ventas a cliente en rango");
		List<Object []> resp = pp.mostrarVentasRealizadasAClienteEnRango(pDoc, pFechaInicio, pFechafin);
		log.info ("Consultando ventas a cliente en rango" + resp.size() + " ventas encontradas");
		return resp;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public long getCedula() {
		return cedula;
	}

	public long getRol() {
		return rol;
	}

	/* ****************************************************************
	 * 			Métodos para administración
	 *****************************************************************/

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Parranderos
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarSuperAndes()
	{
        log.info ("Limpiando la BD de Parranderos");
        long [] borrrados = pp.limpiarSuperAndes();
        log.info ("Limpiando la BD de Parranderos: Listo!");
        return borrrados;
	}


	public List<Object[]> analizarSuperAndes(String fecha1, String fecha2, String producto) {

		log.info ("Analizando SuperAndes");
		List<Object[]> resp = pp.analizarSuperAndes(fecha1, fecha2, producto);
		log.info ("Analizando SuperAndes: Listo!");
		return resp;
	}

	public List<Object[]> encontrarClientesFrecuentes(String sucurcal) throws Exception {

		if(nombreCliente.equals(""))
		{
			log.info ("Encontrando clientes frecuentes");
			List<Object[]> resp = pp.encontrarClientesFrecuentes(sucurcal);
			log.info ("Encontrando clientes frecuentes: Listo!");
			return resp;
		}
		throw new Exception("No tiene permisos para realizar esta operación");
	}

	public String devolverProductoCarrito(String producto) {

		log.info ("Devolver producto carrito");
		String resp = pp.devolverProductoCarrito(producto, nombreCliente);
		log.info ("Devolver producto carrito: Listo!");
		return resp;
	}

    public List<Object[]> analizarSuperAndes2(String fecha1, String fecha2, String producto) {

		log.info ("Analizando SuperAndes");
		List<Object[]> resp = pp.analizarSuperAndes2(fecha1, fecha2, producto);
		log.info ("Analizando SuperAndes: Listo!");
		return resp;

    }

    public List<Object[]> consultarConsumoV2(String codigoDeBarras, String fecha1, String fecha2, String orden) {

		log.info ("Consultando consumo");
		List<Object[]> resp = pp.consultarConsumoV2(codigoDeBarras, fecha1, fecha2, orden);
		log.info ("Consultando consumo: Listo!");
		return resp;
    }
}
