package uniandes.isis2304.SuperAndes.persistencia;


import uniandes.isis2304.SuperAndes.negocio.Supermercado;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.List;

class SQLSupermercado {

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
	public SQLSupermercado (PersistenciaSuperAndes pp)
	{

		this.pp = pp;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un SUPERMERCADO a la base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @param nit - El identificador del supermercado
	 * @param nombre - El nombre del supermercado
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarSupermercado(PersistenceManager pm, long nit, String nombre)
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaSupermercado () +
				"(nit, nombre) values (?, ?)");
		q.setParameters(nit, nombre );
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar SUPERMERCADOS de la base de datos de SuperAndes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombre - El nombre del supermercado
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarSupermercadoPorNombre(PersistenceManager pm, String nombre)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaSupermercado () + " WHERE nombre = ?");
		q.setParameters(nombre);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN SUPERMERCADO de la base de datos de SuperAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param nitSupermercado - El identificador del supermercado
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarSupermercadoPorNit(PersistenceManager pm, long nitSupermercado)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaSupermercado () + " WHERE id = ?");
		q.setParameters(nitSupermercado);
		return (long) q.executeUnique();
	}




	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS SUCURSALES Y SU INFORMACION DEL SUPERMERCADO de la
	 * base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @param nitSupermercado-- Creación de la tabla S_ORDENENTREGADA con sus restricciones
	 */
	public List<Object []> darSucursales(PersistenceManager pm, long nitSupermercado)
	{
		String sql = "SELECT sucur.nombre, sucur.tamanio, sucur.ciudad";
		sql += " FROM ";
		sql += pp.darTablaSucursal () + " sucur, ";
		sql += pp.darTablaSupermercado () + " super ";
		sql	+= " WHERE ";
		sql += "super.nit = ?";
		sql += " AND super.nit = sucur.nitsupermercado";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(nitSupermercado);
		return q.executeList();
	}


}
