package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Cuidad;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto CIUDAD de SuperAndes
 * 
 */
class SQLCiudad 
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
	public SQLCiudad (PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un CIUDAD a la base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarCiudad (PersistenceManager pm, long idCiudad, String nombre, String direccion) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaCiudad () + "(id, nombre, direccion) values (?, ?, ?)");
        q.setParameters(idCiudad, nombre, direccion);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar CIUDADES de la base de datos de SuperAndes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombre - El nombre de la ciudad
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarCiudadPorNombre (PersistenceManager pm, String nombre)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCiudad () + " WHERE nombre = ?");
        q.setParameters(nombre);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN CIUDAD de la base de datos de SuperAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idCiudad - El identificador del ciudad
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarCiudadPorId (PersistenceManager pm, long idCiudad)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCiudad () + " WHERE id = ?");
        q.setParameters(idCiudad);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN CIUDAD de la 
	 * base de datos de SuperAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idCiudad - El identificador del ciudad
	 * @return El objeto CIUDAD que tiene el identificador dado
	 */
	public Cuidad darCiudadPorId (PersistenceManager pm, long idCiudad) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCiudad () + " WHERE id = ?");
		q.setResultClass(Cuidad.class);
		q.setParameters(idCiudad);
		return (Cuidad) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS CIUDADES de la 
	 * base de datos de SuperAndes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreCiudad - El nombre de ciudad buscado
	 * @return Una lista de objetos CIUDAD que tienen el nombre dado
	 */
	public List<Cuidad> darCiudadesPorNombre (PersistenceManager pm, String nombreCiudad) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCiudad () + " WHERE nombre = ?");
		q.setResultClass(Cuidad.class);
		q.setParameters(nombreCiudad);
		return (List<Cuidad>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS CIUDADES de la 
	 * base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos CIUDAD
	 */
	public List<Cuidad> darCiudades (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCiudad ());
		q.setResultClass(Cuidad.class);
		return (List<Cuidad>) q.executeList();
	}
	
}
