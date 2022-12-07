/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: SuperAndes Uniandes
 * @version 1.0
 * @author
 * 2022-2
 * 

 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.SuperAndes.interfazApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.swing.*;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.SuperAndes.negocio.*;

/**
 * Clase principal de la interfaz
 * @author
 */
@SuppressWarnings("serial")

public class InterfazSuperAndesApp extends JFrame implements ActionListener
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(InterfazSuperAndesApp.class.getName());
	
	/**
	 * Ruta al archivo de configuración de la interfaz
	 */
	private static final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigApp.json"; 
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos
	 */
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD_A.json"; 
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
     */
    private JsonObject tableConfig;
    
    /**
     * Asociación a la clase principal del negocio.
     */
    private SuperAndes superAndes;
    
	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/
    /**
     * Objeto JSON con la configuración de interfaz de la app.
     */
    private JsonObject guiConfig;
    
    /**
     * Panel de despliegue de interacción para los requerimientos
     */
    private PanelDatos panelDatos;
    
    /**
     * Menú de la aplicación
     */
    private JMenuBar menuBar;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
    /**
     * Construye la ventana principal de la aplicación. <br>
     * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
     */
    public InterfazSuperAndesApp( )
    {
        // Carga la configuración de la interfaz desde un archivo JSON
        guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);
        
        // Configura la apariencia del frame que contiene la interfaz gráfica
        configurarFrame ( );
        if (guiConfig != null) 	   
        {
     	   crearMenu( guiConfig.getAsJsonArray("menuBar") );
        }
        
        tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
        superAndes = new SuperAndes(tableConfig);
        
    	String path = guiConfig.get("bannerPath").getAsString();
        panelDatos = new PanelDatos ( );

        setLayout (new BorderLayout());
        add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );
		iniciarSesion();
		JPanel panelNombre = new JPanel ( );
		String nombreUsuario = superAndes.getNombreUsuario();
		String nombreCliente = superAndes.getNombreCliente();
		if(nombreUsuario!="")
		{
			panelNombre.add (new JLabel ("Usuario: " + nombreUsuario+"  Rol: " + superAndes.getRol()));
		}
		if(nombreCliente!="")
		{
			panelNombre.add(new JLabel("Cliente: " + nombreCliente));
		}
		add(panelDatos, BorderLayout.CENTER);
		add(panelNombre, BorderLayout.SOUTH);
    }
    
	/* ****************************************************************
	 * 			Métodos de configuración de la interfaz
	 *****************************************************************/
    /**
     * Lee datos de configuración para la aplicació, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param tipo - El tipo de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del tipo especificado
     * 			NULL si hay un error en el archivo.
     */
    private JsonObject openConfig (String tipo, String archConfig)
    {
    	JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración válido: " + tipo);
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "SuperAndes App", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }
    
    /**
     * Método para configurar el frame principal de la aplicación
     */
    private void configurarFrame(  )
    {
    	int alto = 0;
    	int ancho = 0;
    	String titulo = "";	
    	
    	if ( guiConfig == null )
    	{
    		log.info ( "Se aplica configuración por defecto" );			
			titulo = "SuperAndes APP Default";
			alto = 300;
			ancho = 600;
    	}
    	else
    	{
			log.info ( "Se aplica configuración indicada en el archivo de configuración" );
    		titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
    	}
    	
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLocation (50,50);
        setResizable( true );
        setBackground( Color.WHITE );

        setTitle( titulo );
		setSize ( ancho, alto);        
    }

    /**
     * Método para crear el menú de la aplicación con base em el objeto JSON leído
     * Genera una barra de menú y los menús con sus respectivas opciones
     * @param jsonMenu - Arreglo Json con los menùs deseados
     */
    private void crearMenu(  JsonArray jsonMenu )
    {    	
    	// Creación de la barra de menús
        menuBar = new JMenuBar();       
        for (JsonElement men : jsonMenu)
        {
        	// Creación de cada uno de los menús
        	JsonObject jom = men.getAsJsonObject(); 

        	String menuTitle = jom.get("menuTitle").getAsString();        	
        	JsonArray opciones = jom.getAsJsonArray("options");
        	
        	JMenu menu = new JMenu( menuTitle);
        	
        	for (JsonElement op : opciones)
        	{       	
        		// Creación de cada una de las opciones del menú
        		JsonObject jo = op.getAsJsonObject(); 
        		String lb =   jo.get("label").getAsString();
        		String event = jo.get("event").getAsString();
        		
        		JMenuItem mItem = new JMenuItem( lb );
        		mItem.addActionListener( this );
        		mItem.setActionCommand(event);
        		
        		menu.add(mItem);
        	}       
        	menuBar.add( menu );
        }        
        setJMenuBar ( menuBar );	
    }
    
	/* ****************************************************************
	 * 			Requerimientos funcionales
	 *****************************************************************/
	public void iniciarSesion() {
	boolean valido = false;
		while (!valido) {
			int tipo = JOptionPane.showOptionDialog(this, "Seleccione login", "Iniciar Sesion", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Cliente", "Empleado"}, "Cliente");
			if(tipo == 0)
			{
				String cedulaS = JOptionPane.showInputDialog(this, "Introduzca su cedula para iniciar sesion", "SuperAndes", JOptionPane.INFORMATION_MESSAGE);
				try {
					if (cedulaS == null) {
						System.exit(0);
					}
					long cedula = Long.parseLong(cedulaS);

					valido = superAndes.logCliente(cedula);
					if (!valido) {
						JOptionPane.showMessageDialog(this, "La cedula introducida no esta registrada.", "SuperAndes", JOptionPane.ERROR_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(this, "Bienvenido, " + superAndes.getNombreCliente(), "SuperAndes", JOptionPane.PLAIN_MESSAGE);
					}
				}
				catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(this, "Ingrese un valor vï¿½lido.", "Vacu-Andes", JOptionPane.ERROR_MESSAGE);
				}
			}
			if(tipo == 1)
			{
				String cedulaS = JOptionPane.showInputDialog(this, "Introduzca su cedula para iniciar sesion", "SuperAndes", JOptionPane.INFORMATION_MESSAGE);
				String palabraClave = JOptionPane.showInputDialog(this, "Introduzca su palabra clave para iniciar sesion", "SuperAndes", JOptionPane.INFORMATION_MESSAGE);
				try {
					if (cedulaS == null) {
						System.exit(0);
					}
					long cedula = Long.parseLong(cedulaS);

					valido = superAndes.logUsuario(cedula, palabraClave);
					if (!valido) {
						JOptionPane.showMessageDialog(this, "La cedula introducida no esta registrada o se ingresó una palabra clave inválida.", "SuperAndes", JOptionPane.ERROR_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(this, "Bienvenido, " + superAndes.getNombreUsuario(), "SuperAndes", JOptionPane.PLAIN_MESSAGE);
					}
				}
				catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(this, "Ingrese un valor válido.", "SuperAndes", JOptionPane.ERROR_MESSAGE);
				}			}


}

}

	public void  pagarCompra() {
		try
		{
			List<Object[]> carrito = superAndes.obtenerCarrito();
			superAndes.pagarCompra(carrito);
		}
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "No se ha encontrado un carrito asociado con este cliente", "SuperAndes", JOptionPane.ERROR_MESSAGE);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Solo los clientes pueden realizar esta operacion", "SuperAndes", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void recibirPedidoConsolidado()
	{
		try
		{
			String idPedido = JOptionPane.showInputDialog(this, "Introduzca el id del pedido recibido", "SuperAndes", JOptionPane.INFORMATION_MESSAGE);
			if (idPedido != null) {
				long id = Long.parseLong(idPedido);
				superAndes.recibirPedidoConsolidado(id);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		}

		catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Solo los operadores pueden realizar esta operacion", "SuperAndes", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void mostrarCarrito() {
		List<Object[]> carrito = superAndes.obtenerCarrito();
		String rest = "Nombre" + "		" + "Cantidad" + "		" + "Precio Unitario"+ "		" + "Subtotal"+"\n\n";
		double total = 0;
		for ( Object[] producto:carrito)
		{
			String nombre = (String) producto[5];
			BigDecimal cantidadB = (BigDecimal) producto[3];
			int cantidad = cantidadB.intValue();
			BigDecimal precioB = (BigDecimal) producto[4];
			int precioU = precioB.intValue();
			int subtotal = cantidad*precioU;
			rest += nombre + "		" + cantidad + "		" + precioU + "		"+ subtotal + "\n";
			total += subtotal;
		}
		rest+= "\nTotal: " + total;
		panelDatos.actualizarInterfaz(rest);
	}

	public void recolectarAbandonados()
	{
		try
		{
			superAndes.recolectarAbandonados();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(this, "No se puede realizar esta operacion", "SuperAndes", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void consolidarPedido()
	{
		try
		{
			String NITProveedor = JOptionPane.showInputDialog (this, "Proveedor?", "Consolidar Pedido", JOptionPane.QUESTION_MESSAGE);
			if (NITProveedor != null)
			{
				long nit = Long.parseLong(NITProveedor);
				superAndes.consolidarPedido(nit);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void registrarRol( )
	{
		try
		{
			String nombre = JOptionPane.showInputDialog (this, "Nombre del rol?", "Adicionar rol", JOptionPane.QUESTION_MESSAGE);
			if (nombre != null)
			{
				VORol tb = superAndes.adicionarRol(nombre) ;
				if (tb == null)
				{
					throw new Exception ("No se pudo crear un rol con nombre: " + nombre);
				}
				String resultado = "En adicionarRol\n\n";
				resultado += "Rol adicionado exitosamente: " + tb;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/* ****************************************************************
	 * 			Requerimientos funcionales 15 al 17
	 *			Iteración 3
	 *****************************************************************/

	public void consultarConsumoO()
	{
		try{

			String codigo = JOptionPane.showInputDialog (this, "Codigo del producto?", "Consultar consumo de un producto", JOptionPane.QUESTION_MESSAGE);
			String fecha1 = JOptionPane.showInputDialog (this, "Fecha inicial? - YYYY-MM-DD",
					"Consultar consumo de un producto", JOptionPane.QUESTION_MESSAGE);

			String fecha2 = JOptionPane.showInputDialog (this, "Fecha final? - YYYY-MM-DD",
					"Consultar consumo de un producto", JOptionPane.QUESTION_MESSAGE);
			String arg = (String) JOptionPane.showInputDialog(this, "Seleccione el parametro de ordenamiento", "Input", JOptionPane.QUESTION_MESSAGE,
					null, new Object[] {"Nombre", "Correo","Documento"} , "Nombre");
			String orden = (String) JOptionPane.showInputDialog(this, "En que orden?", "Input", JOptionPane.QUESTION_MESSAGE,
					null, new Object[] {"Ascendente","Descendente"} , "Descendente");
			if (codigo != null && fecha1 != null && fecha2 != null)
			{
				long cod = Long.parseLong(codigo);
				List<Object[]> consumo = superAndes.consultarConsumoO(cod, fecha1, fecha2, arg, orden);
				String rest = "Documento" + "		" + "Nombre" + "		" + "Correo"+"\n\n";
				for ( Object[] cliente:consumo)
				{
					String nombre = (String) cliente[1];
					String correo = (String) cliente[2];
					BigDecimal documentoB = (BigDecimal) cliente[0];
					int documento = documentoB.intValue();
					rest += documento + "		" + nombre + "		" + correo + "\n";
				}
				panelDatos.actualizarInterfaz(rest);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		}
		catch(Exception e){
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}


	}

	public void solicitarCarrito()
	{
		try{
			String carrito = superAndes.solicitarCarrito();
			panelDatos.actualizarInterfaz(carrito);
		}
		catch(Exception e){
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}


	}

	public void consultarConsumoA() {
		try {

			String codigo = JOptionPane.showInputDialog(this, "Codigo del producto?", "Consultar consumo de un producto", JOptionPane.QUESTION_MESSAGE);
			String fecha1 = JOptionPane.showInputDialog(this, "Fecha inicial? - YYYY-MM-DD",
					"Consultar consumo de un producto", JOptionPane.QUESTION_MESSAGE);

			String fecha2 = JOptionPane.showInputDialog(this, "Fecha final? - YYYY-MM-DD",
					"Consultar consumo de un producto", JOptionPane.QUESTION_MESSAGE);
			String arg = (String) JOptionPane.showInputDialog(this, "Seleccione el parametro de ordenamiento", "Input", JOptionPane.QUESTION_MESSAGE,
					null, new Object[]{"Nombre", "Correo", "Documento", "Cantidad"}, "Nombre");
			String orden = (String) JOptionPane.showInputDialog(this, "En que orden?", "Input", JOptionPane.QUESTION_MESSAGE,
					null, new Object[]{"Ascendente", "Descendente"}, "Descendente");
			if (codigo != null && fecha1 != null && fecha2 != null) {
				long cod = Long.parseLong(codigo);
				List<Object[]> consumo = superAndes.consultarConsumoA(cod, fecha1, fecha2, arg, orden);
				String rest = "Documento" + "		" + "Nombre" + "		" + "Correo" + "		" + "Cantidad" +"\n\n";
				for (Object[] cliente : consumo) {
					String nombre = (String) cliente[1];
					String correo = (String) cliente[2];
					BigDecimal documentoB = (BigDecimal) cliente[0];
					int documento = documentoB.intValue();
					BigDecimal cantidadB = (BigDecimal) cliente[3];
					int cantidad = cantidadB.intValue();
					rest += documento + "		" + nombre + "		" + correo + "		" + cantidad+"\n";
				}
				panelDatos.actualizarInterfaz(rest);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	public void adicionarProductoCarrito()
	{
		try{

			String[] choices = superAndes.nombreProductos();
			String producto = (String) JOptionPane.showInputDialog(this, "Pick a product", "Input", JOptionPane.QUESTION_MESSAGE,
					null, choices, "Titan");
			String respuesta = "El producto seleccionado es: " + producto;
			panelDatos.actualizarInterfaz(respuesta);
			String cantidad = JOptionPane.showInputDialog (this, "Cantidad?", "Adicionar producto a carrito", JOptionPane.QUESTION_MESSAGE);
			int cant = Integer.parseInt(cantidad);
			respuesta+= "\nLa cantidad seleccionada es: " + cant;
			panelDatos.actualizarInterfaz(respuesta);
			String carrito = superAndes.adicionarProductoCarrito(producto, cant);
			respuesta += "\n" + carrito;
			panelDatos.actualizarInterfaz(respuesta);

		}
		catch(Exception e){
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void buenosClientes()
	{
		try
		{
			List<Object[]> clientes = superAndes.buenosClientes();
			String rest = "Clientes que compraron productos mas caros a $100000" + "\n\n";
			 rest += "Documento" + "		" + "Nombre" + "		" + "Correo" + "		" + "\n\n";
			for (Object[] cliente : clientes) {
				String nombre = (String) cliente[1];
				String correo = (String) cliente[2];
				BigDecimal documentoB = (BigDecimal) cliente[0];
				int documento = documentoB.intValue();
				rest += documento + "		" + nombre + "		" + correo + "		" + "\n";

			}
			rest += "Clientes que compraron productos electronicos o herramientas" + "\n\n";
			List<Object[]> clientes2 = superAndes.buenosClientes2();
			rest += "Documento" + "		" + "Nombre" + "		" + "Correo" + "		" + "\n\n";
			for (Object[] cliente : clientes2) {
				String nombre = (String) cliente[1];
				String correo = (String) cliente[2];
				BigDecimal documentoB = (BigDecimal) cliente[0];
				int documento = documentoB.intValue();
				rest += documento + "		" + nombre + "		" + correo + "		" + "\n";}
			panelDatos.actualizarInterfaz(rest);
		}

		catch(Exception e)
		{
		e.printStackTrace();
		String resultado = generarMensajeError(e);
		panelDatos.actualizarInterfaz(resultado);
	}
	}
	public void devolverProductoCarrito()
	{
		String[] choices = superAndes.productosEnCarrito();
		String producto = (String) JOptionPane.showInputDialog(this, "Pick a product", "Input", JOptionPane.QUESTION_MESSAGE,
				null, choices, "Titan");
		String carrito = superAndes.devolverProductoCarrito(producto);
		panelDatos.actualizarInterfaz(carrito);



	}


	/* ****************************************************************
	 * 			Requerimientos de consulta
	 *			Iteración 3
	 *****************************************************************/

	public void analizarSuperAndes()
	{
		String fecha1 = JOptionPane.showInputDialog (this, "Fecha inicial? - YYYY-MM-DD",
				"Analizar SuperAndes en un periodo de tiempo", JOptionPane.QUESTION_MESSAGE);

		String fecha2 = JOptionPane.showInputDialog (this, "Fecha final? - YYYY-MM-DD",
				"Analizar SuperAndes en un periodo de tiempo", JOptionPane.QUESTION_MESSAGE);

		String producto = JOptionPane.showInputDialog (this, "Categoria?",
				"Analizar SuperAndes en un periodo de tiempo", JOptionPane.QUESTION_MESSAGE);

		List<Object[]> respuesta = superAndes.analizarSuperAndes(fecha1, fecha2, producto);
		String resultado = "Mayor demanda en SuperAndes: \n\n";
		for (Object[] tupla : respuesta)
		{
			resultado += "Cantidad vendida: " + tupla[0] + " - Producto: " + tupla[1] + " - Sucursal: "
		+ tupla[2] +"\n";
		}
		resultado += "\n Operación terminada";
		panelDatos.actualizarInterfaz(resultado);

		List<Object[]> respuesta2 = superAndes.analizarSuperAndes2(fecha1,fecha2,producto);
		resultado += "\n\nMenor demanda en SuperAndes: \n\n";
		for (Object[] tupla : respuesta2) {
			resultado += "Cantidad vendida: " + tupla[0] + " - Producto: " + tupla[1] + " - Sucursal: "
					+ tupla[2] + "\n";
		}
		resultado += "\n Operación terminada";
		panelDatos.actualizarInterfaz(resultado);


	}

	public void encontrarClientesFrecuentes()
	{
		try{
			String sucurcal = JOptionPane.showInputDialog (this, "Nombre sucursal?",
					"Nombre sucursal", JOptionPane.QUESTION_MESSAGE);
			List<Object[]> respuesta = superAndes.encontrarClientesFrecuentes(sucurcal);
			String resultado = "En encontrarClientesFrecuentes\n\n";
			resultado += "Los clientes frecuentes son: \n";
			int i = 1;
			resultado += "  nombre" + "                 cedula" + "                 numeroCompras"  + "\n";
			for (Object [] tupla : respuesta)
			{
				String nombre = (String) tupla [0];
				String cedula = (String) tupla [1];
				String numeroCompras = (String) tupla [2];

				resultado += i++ + ". " + nombre + "        -       " + cedula + "        -       " + numeroCompras + "\n";
			}
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		}
		catch(Exception e){
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}


	/* ****************************************************************
	 * 			Requerimientos de consulta
	 *****************************************************************/
	/*
	* Mostrar el dinero recolectado por cada sucursal en un rango de fechas
	 */
	public void registrarPromocion()
	{
		try
		{
			int tipo = JOptionPane.showOptionDialog(this, "Seleccione tipo de oferta", "Registrar Oferta", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Descuento", "NxM","PagueXLLeveY", "Descuento2Unidad", "Paquete"}, "Descuento");
			if(tipo == 0)
			{
				registrarDescuento();
			}
			if(tipo == 1)
			{
				registrarNxM();
			}

			if(tipo == 2)
			{
				registrarPagueXLLeveY();
			}
			/**
			if(tipo == 3)
			{
				registrarDescuento2Unidad();
			}
			if(tipo == 4)
			{
				registrarPaquete();
			}**/
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void registrarNxM()
	{
		try{
			String codBarras = JOptionPane.showInputDialog (this, "Codigo De Barras?", "Adicionar Descuento", JOptionPane.QUESTION_MESSAGE);
			String unidadesDisponibles = JOptionPane.showInputDialog (this, "Unidades Disponibles?", "Adicionar Descuento", JOptionPane.QUESTION_MESSAGE);
			String fechaCaducidad = JOptionPane.showInputDialog (this, "Fecha de caducidad?", "Adicionar Descuento", JOptionPane.QUESTION_MESSAGE);
			String N = JOptionPane.showInputDialog (this, "N?", "Adicionar Descuento", JOptionPane.QUESTION_MESSAGE);
			String M = JOptionPane.showInputDialog (this, "M?", "Adicionar Descuento", JOptionPane.QUESTION_MESSAGE);
			if (codBarras != null || unidadesDisponibles != null || fechaCaducidad != null || N != null || M != null)
			{
				int n = Integer.parseInt(N);
				int m = Integer.parseInt(M);
				int unidades = Integer.parseInt(unidadesDisponibles);
				OfertaNxM tb = superAndes.adicionarNxM(codBarras, unidades, fechaCaducidad, n, m) ;
				if (tb == null)
				{
					throw new Exception ("No se pudo crear un descuento con nombre: " + codBarras);
				}
				String resultado = "En adicionarDescuento\n\n";
				resultado += "Descuento adicionado exitosamente: " + tb;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void abandonarCarrito()
	{
		try
		{
			superAndes.abandonarCarrito();
			String resultado = "En abandonarCarrito\n\n";
			resultado += "Carrito abandonado exitosamente";
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void registrarPagueXLLeveY()
	{
		try{
			String codBarras = JOptionPane.showInputDialog (this, "Codigo De Barras?", "Adicionar Descuento", JOptionPane.QUESTION_MESSAGE);
			String unidadesDisponibles = JOptionPane.showInputDialog (this, "Unidades Disponibles?", "Adicionar Descuento", JOptionPane.QUESTION_MESSAGE);
			String fechaCaducidad = JOptionPane.showInputDialog (this, "Fecha de caducidad?", "Adicionar Descuento", JOptionPane.QUESTION_MESSAGE);
			String x = JOptionPane.showInputDialog (this, "N?", "Adicionar Descuento", JOptionPane.QUESTION_MESSAGE);
			String y = JOptionPane.showInputDialog (this, "M?", "Adicionar Descuento", JOptionPane.QUESTION_MESSAGE);
			if (codBarras != null || unidadesDisponibles != null || fechaCaducidad != null || x != null || y != null)
			{
				int n = Integer.parseInt(x);
				int m = Integer.parseInt(y);
				int unidades = Integer.parseInt(unidadesDisponibles);
				OfertaPagueXLleveY tb = superAndes.registrarPagueXLLeveY(codBarras, unidades, fechaCaducidad, n, m) ;
				if (tb == null)
				{
					throw new Exception ("No se pudo crear un descuento con nombre: " + codBarras);
				}
				String resultado = "En adicionarDescuento\n\n";
				resultado += "Descuento adicionado exitosamente: " + tb;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	public void registrarDescuento()
	{
		try{
			String codBarras = JOptionPane.showInputDialog (this, "Codigo De Barras?", "Adicionar Descuento", JOptionPane.QUESTION_MESSAGE);
			String unidadesDisponibles = JOptionPane.showInputDialog (this, "Unidades Disponibles?", "Adicionar Descuento", JOptionPane.QUESTION_MESSAGE);
			String fechaCaducidad = JOptionPane.showInputDialog (this, "Fecha de caducidad?", "Adicionar Descuento", JOptionPane.QUESTION_MESSAGE);
			String porcentajeDescuento = JOptionPane.showInputDialog (this, "Porcentaje de descuento?", "Adicionar Descuento", JOptionPane.QUESTION_MESSAGE);
			if (codBarras != null || unidadesDisponibles != null ||fechaCaducidad != null || porcentajeDescuento != null)
			{
				int unidades = Integer.parseInt(unidadesDisponibles);
				Double porcentaje = Double.parseDouble(porcentajeDescuento);
				OfertaDescuento oferta = superAndes.adicionarOfertaDescuento(codBarras, unidades, fechaCaducidad, porcentaje);
				if (oferta == null)
				{
					throw new Exception ("No se pudo crear la oferta");
				}
				String resultado = "En adicionar ofertaDescuento\n\n";
				resultado += "OfertaDescuento adicionada exitosamente: " + oferta;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void finalizarPromociones()
	{
		try{
			String fechaCaducidad = JOptionPane.showInputDialog (this, "Fecha actual?", "Finalizar Promociones", JOptionPane.QUESTION_MESSAGE);
			if(fechaCaducidad != null)
			{
				superAndes.finalizarPromociones(fechaCaducidad);
				String resultado = "En finalizarPromociones\n\n";
				resultado += "Promociones finalizadas exitosamente";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void dineroRecolectadoTiempo( )
	{
		try
		{
			String fechaInicio = JOptionPane.showInputDialog (this, "Fecha de inicio? - YYYY-MM-DD", "Mostrar dinero recolectado por sucursal", JOptionPane.QUESTION_MESSAGE);
			String fechaFin = JOptionPane.showInputDialog (this, "Fecha de fin? - YYYY-MM-DD", "Mostrar dinero recolectado por sucursal", JOptionPane.QUESTION_MESSAGE);
			if (fechaInicio != null && fechaFin != null)
			{
				List <Object []> lista = superAndes.darDineroRecolectadoPorSucursalPeriodo(fechaInicio, fechaFin);
				String resultado = "Para el requerimiento funcional 1:\n\n";
				resultado += "Dinero recolectado por sucursal: " + lista.size() + " sucursales encontradas";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
				String resp = "Sucursal\tDinero recolectado\n";
				for (Object [] tb : lista)
				{
					String nombreSucursal = (String) tb [0];
					double dineroRecolectado = (double) tb [1];
					resp += nombreSucursal + "\t" + dineroRecolectado + "\n";
				}
				panelDatos.actualizarInterfaz(resp);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void dineroRecolectadoAnio()
	{
		try
		{
			String anio = JOptionPane.showInputDialog (this, "Año? - YYYY", "Mostrar dinero recolectado por sucursal", JOptionPane.QUESTION_MESSAGE);
			if (anio != null)
			{
				List <Object []> lista = superAndes.darDineroRecolectadoPorSucursalAnio(anio);
				String resultado = "Para el requerimiento funcional 2:\n\n";
				resultado += "Dinero recolectado por sucursal: " + lista.size() + " sucursales encontradas";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
				String resp = "Sucursal\tDinero recolectado\n";
				for (Object [] tb : lista)
				{
					String nombreSucursal = (String) tb [0];
					double dineroRecolectado = (double) tb [1];
					resp += nombreSucursal + "\t" + dineroRecolectado + "\n";
				}
				panelDatos.actualizarInterfaz(resp);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void indiceOcupacion()
	{
		try
		{
			List<Object []> lista = superAndes.darIndiceOcupacionSucursales();
			String resultado = "Para el requerimiento funcional 3:\n\n";
			resultado += "Indice de ocupación de las sucursales: " + lista.size() + " sucursales encontradas";
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);

			String resp = "Sucursal\t                         Categoria\tTipo\tIndiceOcupacion\tCapacidad\n";
			for (Object [] tb : lista)
			{
				String nombreSucursal = (String) tb [0];
				String nombreCategoria = (String) tb [1];
				String tipoAlmacenamiento = (String) tb [2];
				BigDecimal indiceOcupacion = (BigDecimal) tb [3];
				BigDecimal capacidad = (BigDecimal) tb [4];
				resp += nombreSucursal + "\t" + nombreCategoria + "\t"+ tipoAlmacenamiento +"\t"+ indiceOcupacion+ "\t                          "+ capacidad + "kg" + "\n";
			}
			panelDatos.actualizarInterfaz(resp);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}

	public void aprovisionarEstante()
	{
		String idAlmacenamiento = JOptionPane.showInputDialog (this, "Id del estante?", "Aprovisionar estante", JOptionPane.QUESTION_MESSAGE);
		if (idAlmacenamiento != null)
		{
			try
			{
				long id = Long.parseLong(idAlmacenamiento);
				superAndes.aprovisionarEstante(id);
				panelDatos.actualizarInterfaz("Estante aprovisionado exitosamente");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			}
		}
		else
		{
			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
		}
	}
	public void productosPorPrecio()
	{
		try
		{
			double precioMin = Double.valueOf(JOptionPane.showInputDialog (this, "Precio mínimo? - XXXX", "Mostrar productos por precio", JOptionPane.QUESTION_MESSAGE));
			double precioMax = Double.parseDouble(JOptionPane.showInputDialog (this, "Precio máximo? - XXXX", "Mostrar productos por precio", JOptionPane.QUESTION_MESSAGE));
			if (precioMin != -1 && precioMax != -1)
			{
				List <Object []> lista = superAndes.darProductosPorPrecio(precioMin, precioMax);
				String resultado = "Para el requerimiento funcional 4:\n\n";
				resultado += "Productos por precio: " + lista.size() + " productos encontrados";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
				String resp = "Nombre\tMarca\tCategoria\tPrecio\tEspecificación\n";
				for (Object [] tb : lista)
				{
					String nombreProducto = (String) tb [0];
					String marca = (String) tb [1];
					String categoria = (String) tb [2];
					double precio = (double) tb [3];
					String especificacion = (String) tb [4];
					resp += nombreProducto + "\t" + marca + "\t"+ categoria +"\t"+ precio+ "\t"+ especificacion + "\n";
				}
				panelDatos.actualizarInterfaz(resp);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void productosPorFechaVencimiento()
	{
		try
		{
			String fecha = JOptionPane.showInputDialog (this, "Fecha de vencimiento? - YYYY-MM-DD", "Mostrar productos por fecha de vencimiento", JOptionPane.QUESTION_MESSAGE);
			if (fecha != null)
			{
				List <Object []> lista = superAndes.darProductosPorFechaVencimiento(fecha);
				String resultado = "Para el requerimiento funcional 4:\n\n";
				resultado += "Productos por fecha de vencimiento: " + lista.size() + " productos encontrados";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
				String resp = "Nombre\tMarca\tCategoria\tPrecio\tEspecificación\t                                  FechaVencimiento\n";
				for (Object [] tb : lista)
				{
					String nombreProducto = (String) tb [0];
					String marca = (String) tb [1];
					String categoria = (String) tb [2];
					double precio = (double) tb [3];
					String especificacion = (String) tb [4];
					Timestamp fechaVencimiento = (Timestamp) tb [5];
					resp += nombreProducto + "\t" + marca + "\t"+ categoria +"\t"+ precio+ "\t"+ especificacion + "\t"+ fechaVencimiento + "\n";
				}
				panelDatos.actualizarInterfaz(resp);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void productosPorProveedor()
	{
		try
		{
			String nitProveedor = JOptionPane.showInputDialog (this, "Nit del proveedor? - XXXXXXXXX", "Mostrar productos por proveedor", JOptionPane.QUESTION_MESSAGE);

			if (nitProveedor != null)
			{
				int nit = Integer.parseInt(nitProveedor);
				List <Object []> lista = superAndes.darProductosPorProveedor(nit);
				String resultado = "Para el requerimiento funcional 4:\n\n";
				resultado += "Productos por proveedor: " + lista.size() + " productos encontrados";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
				String resp = "Nombre\tMarca\tCategoria\tPrecio\tEspecificación\t                           Proveedor\n";
				for (Object [] tb : lista)
				{
					String nombreProducto = (String) tb [0];
					String marca = (String) tb [1];
					String categoria = (String) tb [2];
					double precio = (double) tb [3];
					String especificacion = (String) tb [4];
					String proveedor = (String) tb [5];
					resp += nombreProducto + "\t" + marca + "\t"+ categoria +"\t"+ precio+ "\t"+ especificacion + "\t" + proveedor + "\n";
				}
				panelDatos.actualizarInterfaz(resp);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void productosDisponiblesCiudad()
	{
		try
		{
			String ciudad = JOptionPane.showInputDialog (this, "Ciudad?", "Mostrar productos disponibles en una ciudad", JOptionPane.QUESTION_MESSAGE);

			if (ciudad != null)
			{
				List <Object []> lista = superAndes.darProductosDisponiblesCiudad(ciudad);
				String resultado = "Para el requerimiento funcional 4:\n\n";
				resultado += "Productos disponibles en una ciudad: " + lista.size() + " productos encontrados";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
				String resp = "Nombre\tMarca\tCategoria\tPrecio\tEspecificación\t                     Ciudad\n";
				for (Object [] tb : lista)
				{
					String nombreProducto = (String) tb [0];
					String marca = (String) tb [1];
					String categoria = (String) tb [2];
					double precio = (double) tb [3];
					String especificacion = (String) tb [4];
					String ciudadProducto = (String) tb [5];
					resp += nombreProducto + "\t" + marca + "\t"+ categoria +"\t"+ precio+ "\t"+ especificacion + "\t" + ciudadProducto + "\n";
				}
				panelDatos.actualizarInterfaz(resp);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void  recibirPedido()
	{
		try
		{
			String idPedido = JOptionPane.showInputDialog (this, "Id del pedido?", "Recibir pedido", JOptionPane.QUESTION_MESSAGE);

			if (idPedido != null)
			{
				long id = Long.parseLong(idPedido);
				superAndes.recibirPedido(id);
				String resultado = "Para el requerimiento funcional 5:\n\n";
				resultado += "Pedido recibido";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	public void productosDisponiblesSucursal()
	{
		try
		{
			String sucursal = JOptionPane.showInputDialog (this, "Sucursal?", "Mostrar productos disponibles en una sucursal", JOptionPane.QUESTION_MESSAGE);

			if (sucursal != null)
			{
				List <Object []> lista = superAndes.darProductosDisponiblesSucursal(sucursal);
				String resultado = "Para el requerimiento funcional 4:\n\n";
				resultado += "Productos disponibles en una sucursal: " + lista.size() + " productos encontrados";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
				String resp = "Sucursal\t                        Ciudad\tNombre\tMarca\tCategoria\tPrecio\tEspecificación\n";
				for (Object [] tb : lista)
				{
					String nombreSucursal = (String) tb [0];
					String ciudad = (String) tb [1];
					String nombreProducto = (String) tb [2];
					String marca = (String) tb [3];
					String categoria = (String) tb [4];
					double precio = (double) tb [5];
					String especificacion = (String) tb [6];

					resp += nombreSucursal + "\t" + ciudad + "\t" + nombreProducto + "\t" + marca + "\t"+ categoria +"\t"+ precio+ "\t"+ especificacion +  "\n";

				}
				panelDatos.actualizarInterfaz(resp);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void productosPorCategoria()
	{
		try
		{
			String categoria = JOptionPane.showInputDialog (this, "Categoria?", "Mostrar productos por categoria", JOptionPane.QUESTION_MESSAGE);

			if (categoria != null)
			{
				List <Object []> lista = superAndes.darProductosPorCategoria(categoria);
				String resultado = "Para el requerimiento funcional 4:\n\n";
				resultado += "Productos por categoria: " + lista.size() + " productos encontrados";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
				String resp = "Nombre\tMarca\tCategoria\tPrecio\tEspecificación\n";
				for (Object [] tb : lista)
				{
					String nombreProducto = (String) tb [0];
					String marca = (String) tb [1];
					String categoriaProducto = (String) tb [2];
					double precio = (double) tb [3];
					String especificacion = (String) tb [4];

					resp += nombreProducto + "\t" + marca + "\t"+ categoriaProducto +"\t"+ precio+ "\t"+ especificacion + "\n";
				}
				panelDatos.actualizarInterfaz(resp);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void productosVendidosMasDeXVeces()
	{
		try
		{
			String cantidad = JOptionPane.showInputDialog (this, "Cantidad?", "Mostrar productos vendidos mas de X veces", JOptionPane.QUESTION_MESSAGE);

			if (cantidad != null)
			{
				List <Object []> lista = superAndes.darProductoVendidosMasDeXVeces(Integer.parseInt(cantidad));
				String resultado = "Para el requerimiento funcional 4:\n\n";
				resultado += "Productos vendidos mas de X veces: " + lista.size() + " productos encontrados";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
				String resp = "Nombre\tCantidad\n";
				for (Object [] tb : lista)
				{
					String nombreProducto = (String) tb [0];
					int cantidadVentas = (int) tb [1];

					resp += nombreProducto + "\t" + cantidadVentas + "\n";
				}
				panelDatos.actualizarInterfaz(resp);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}


	public void comprasHechasProveedor()
	{
		try
		{
			List<Object []> lista = superAndes.mostrarComprasRealizadasAProveedores();
			String resultado = "Para el requerimiento funcional 5:\n\n";
			resultado += "Compras realizadas a proveedores: " + lista.size() + " compras encontradas";
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
			String resp = "Nit\t         Nombre\t                 Direccion\t                       PrecioTotal\n";
			for (Object [] tb : lista)
			{
				int nit = (int) tb [0];
				String nombre = (String) tb [1];
				String direccion = (String) tb [2];
				double precioTotal = (double) tb [3];

				resp += nit + "\t" + nombre + "\t"+ direccion +"\t"+ precioTotal + "\n";
			}
			panelDatos.actualizarInterfaz(resp);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void realizarCompra()
	{
		HashMap<String, Integer> productos = new HashMap<String, Integer>();
		boolean terminado = true;
		int tipo = JOptionPane.showOptionDialog(this, "Tipo de documento?", "Realizar compra", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"C.C", "NIT","T.I", "C.E", "No registrado"}, "C.C");
		if(tipo != 4) {
			String cedula = JOptionPane.showInputDialog(this, "Número de documento?", "Realizar compra", JOptionPane.QUESTION_MESSAGE);
			try {
				while (terminado) {
					String producto = JOptionPane.showInputDialog(this, "Producto?", "Realizar compra", JOptionPane.QUESTION_MESSAGE);
					String cantidad = JOptionPane.showInputDialog(this, "Cantidad?", "Realizar compra", JOptionPane.QUESTION_MESSAGE);
					if (producto != null && cantidad != null) {
						productos.put(producto, Integer.parseInt(cantidad));
					} else {
						terminado = false;
					}
					int continuar = JOptionPane.showOptionDialog(this, "Desea agregar otro producto", "Realizar compra", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Agregar Producto", "Pagar"}, "Pagar");
					if (continuar == 1) {
						terminado = false;
					}
				}
				//String resultado = superAndes.realizarCompraDoc(tipo, cedula, productos);

			} catch (Exception e) {
				e.printStackTrace();
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			}
		}
			else{
			try {
				while (terminado) {
					String producto = JOptionPane.showInputDialog(this, "Producto?", "Realizar compra", JOptionPane.QUESTION_MESSAGE);
					String cantidad = JOptionPane.showInputDialog(this, "Cantidad?", "Realizar compra", JOptionPane.QUESTION_MESSAGE);
					if (producto != null && cantidad != null) {
						productos.put(producto, Integer.parseInt(cantidad));
					} else {
						terminado = false;
					}
					int continuar = JOptionPane.showOptionDialog(this, "Desea agregar otro producto", "Realizar compra", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Agregar Producto", "Pagar"}, "Pagar");
					if (continuar == 1) {
						terminado = false;
					}
				}
				String resultado = superAndes.realizarCompra(productos);
				panelDatos.actualizarInterfaz(resultado);


			} catch (Exception e) {
				e.printStackTrace();
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			}
			}
	}


	public void comprasClientePeriodo()
	{
		try
		{
			String idCliente = JOptionPane.showInputDialog (this, "Id del cliente?", "Mostrar compras de un cliente en un periodo", JOptionPane.QUESTION_MESSAGE);
			String fechaInicial = JOptionPane.showInputDialog (this, "Fecha inicial? - YYYY-MM-DD", "Mostrar compras de un cliente en un periodo", JOptionPane.QUESTION_MESSAGE);
			String fechaFinal = JOptionPane.showInputDialog (this, "Fecha final? - YYYY-MM-DD", "Mostrar compras de un cliente en un periodo", JOptionPane.QUESTION_MESSAGE);

			if (idCliente != null && fechaInicial != null && fechaFinal != null)
			{
				List <Object []> lista = superAndes.mostrarVentasAClienteEnRango(Integer.parseInt(idCliente), fechaInicial, fechaFinal);
				String resultado = "Para el requerimiento funcional 6:\n\n";
				resultado += "Compras de un cliente en un periodo: " + lista.size() + " compras encontradas";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
				String resp = "Nombre\tDocumento\tCodigoDeBarras\tCantidad\n";
				for (Object [] tb : lista)
				{
					String nombreProducto = (String) tb [0];
					int documento = (int) tb [1];
					String codigoDeBarras = (String) tb [2];
					int cantidad = (int) tb [3];

					resp += nombreProducto + "\t" + documento + "\t  "+ codigoDeBarras +"\t                                 "+ cantidad + "\n";
				}
				panelDatos.actualizarInterfaz(resp);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/* ****************************************************************
	 * 			Métodos administrativos
	 *****************************************************************/
	/**
	 * Muestra el log de SuperAndes
	 */
	public void mostrarLogSuperAndes()
	{
		mostrarArchivo ("SuperAndes.log");
	}
	
	/**
	 * Muestra el log de datanucleus
	 */
	public void mostrarLogDatanuecleus ()
	{
		mostrarArchivo ("datanucleus.log");
	}
	
	/**
	 * Limpia el contenido del log de SuperAndes
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogSuperAndes()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("SuperAndes.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de SuperAndes ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia el contenido del log de datanucleus
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogDatanucleus ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("datanucleus.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia todas las tuplas de todas las tablas de la base de datos de SuperAndes
	 * Muestra en el panel de datos el número de tuplas eliminadas de cada tabla
	 */
	public void limpiarBD ()
	{
		try 
		{
    		// Ejecución de la demo y recolección de los resultados
			long eliminados [] = superAndes.limpiarSuperAndes();
			
			// Generación de la cadena de caracteres con la traza de la ejecución de la demo
			String resultado = "\n\n************ Limpiando la base de datos ************ \n";
			resultado += eliminados [0] + " Gustan eliminados\n";
			resultado += eliminados [1] + " Sirven eliminados\n";
			resultado += eliminados [2] + " Visitan eliminados\n";
			resultado += eliminados [3] + " Bebidas eliminadas\n";
			resultado += eliminados [4] + " Tipos de bebida eliminados\n";
			resultado += eliminados [5] + " Bebedores eliminados\n";
			resultado += eliminados [6] + " Bares eliminados\n";
			resultado += "\nLimpieza terminada";
   
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	/**
	 * Muestra la presentación general del proyecto
	 */
	public void mostrarPresentacionGeneral ()
	{
		mostrarArchivo ("data/00-ST-ParranderosJDO.pdf");
	}
	
	/**
	 * Muestra el modelo conceptual de Parranderos
	 */
	public void mostrarModeloConceptual ()
	{
		mostrarArchivo ("data/Modelo Conceptual Parranderos.pdf");
	}
	
	/**
	 * Muestra el esquema de la base de datos de Parranderos
	 */
	public void mostrarEsquemaBD ()
	{
		mostrarArchivo ("data/Esquema BD Parranderos.pdf");
	}
	
	/**
	 * Muestra el script de creación de la base de datos
	 */
	public void mostrarScriptBD ()
	{
		mostrarArchivo ("data/EsquemaParranderos.sql");
	}
	
	/**
	 * Muestra la arquitectura de referencia para Parranderos
	 */
	public void mostrarArqRef ()
	{
		mostrarArchivo ("data/ArquitecturaReferencia.pdf");
	}
	
	/**
	 * Muestra la documentación Javadoc del proyectp
	 */
	public void mostrarJavadoc ()
	{
		mostrarArchivo ("doc/index.html");
	}
	
	/**
     * Muestra la información acerca del desarrollo de esta apicación
     */
    public void acercaDe ()
    {
		String resultado = "\n\n ************************************\n\n";
		resultado += " * Universidad	de	los	Andes	(Bogotá	- Colombia)\n";
		resultado += " * Departamento	de	Ingeniería	de	Sistemas	y	Computación\n";
		resultado += " * Licenciado	bajo	el	esquema	Academic Free License versión 2.1\n";
		resultado += " * \n";		
		resultado += " * Curso: isis2304 - Sistemas Transaccionales\n";
		resultado += " * Proyecto: SuperAndes\n";
		resultado += " * @version 1.0\n";
		resultado += " * @author Pablo Junco - Juan Montenegro\n";
		resultado += " * Julio de 2018\n";
		resultado += " * \n";
		resultado += "\n ************************************\n\n";

		panelDatos.actualizarInterfaz(resultado);		
    }
    

	/* ****************************************************************
	 * 			Métodos privados para la presentación de resultados y otras operaciones
	 *****************************************************************/
    /**
     * Genera una cadena de caracteres con la lista de los tipos de bebida recibida: una línea por cada tipo de bebida
     * @param lista - La lista con los tipos de bebida
     * @return La cadena con una líea para cada tipo de bebida recibido
     */
    private String listarTiposBebida(List<VOTipoBebida> lista) 
    {
    	String resp = "Los tipos de bebida existentes son:\n";
    	int i = 1;
        for (VOTipoBebida tb : lista)
        {
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}

    /**
     * Genera una cadena de caracteres con la descripción de la excepcion e, haciendo énfasis en las excepcionsde JDO
     * @param e - La excepción recibida
     * @return La descripción de la excepción, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
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
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicación
	 * @param e - La excepción generada
	 * @return La cadena con la información de la excepción y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecución\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y SuperAndes.log para más detalles";
		return resultado;
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */
	private boolean limpiarArchivo(String nombreArchivo) 
	{
		BufferedWriter bw;
		try 
		{
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) 
		{
//			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Abre el archivo dado como parámetro con la aplicación por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)
	{
		try
		{
			Desktop.getDesktop().open(new File(nombreArchivo));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* ****************************************************************
	 * 			Métodos de la Interacción
	 *****************************************************************/
    /**
     * Método para la ejecución de los eventos que enlazan el menú con los métodos de negocio
     * Invoca al método correspondiente según el evento recibido
     * @param pEvento - El evento del usuario
     */
    @Override
	public void actionPerformed(ActionEvent pEvento)
	{
		String evento = pEvento.getActionCommand( );		
        try 
        {
			Method req = InterfazSuperAndesApp.class.getMethod ( evento );
			req.invoke ( this );
		} 
        catch (Exception e) 
        {
			e.printStackTrace();
		} 
	}
    
	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
    /**
     * Este método ejecuta la aplicación, creando una nueva interfaz
     * @param args Arreglo de argumentos que se recibe por línea de comandos
     */
    public static void main( String[] args )
    {
        try
        {
        	
            // Unifica la interfaz para Mac y para Windows.
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
            InterfazSuperAndesApp interfaz = new InterfazSuperAndesApp( );
            interfaz.setVisible( true );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
    }
}
