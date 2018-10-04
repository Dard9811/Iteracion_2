package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Supermercado;

public class SQLSupermercado {
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
	 * Crea y ejecuta la sentencia SQL para adicionar un CIUDAD a la base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarSupermercado (PersistenceManager pm, long id, String nombre) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaSupermercado () + "(id, nombre) values (?, ?)");
        q.setParameters(id, nombre);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN Supermercado de la base de datos de SuperAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del Supermercado
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarSupermercadoPorId (PersistenceManager pm, long id)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaSupermercado () + " WHERE id = ?");
        q.setParameters(id);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN CIUDAD de la 
	 * base de datos de SuperAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del ciudad
	 * @return El objeto CIUDAD que tiene el identificador dado
	 */
	public Supermercado darSupermercadoPorId (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaSupermercado () + " WHERE id = ?");
		q.setResultClass(Supermercado.class);
		q.setParameters(id);
		return (Supermercado) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS CIUDADES de la 
	 * base de datos de SuperAndes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreSupermercado - El nombre de ciudad buscado
	 * @return Una lista de objetos CIUDAD que tienen el nombre dado
	 */
	public List<Supermercado> darSupermercadosPorNombre (PersistenceManager pm, String nombre) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaSupermercado () + " WHERE nombre = ?");
		q.setResultClass(Supermercado.class);
		q.setParameters(nombre);
		return (List<Supermercado>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS CIUDADES de la 
	 * base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos CIUDAD
	 */
	public List<Supermercado> darSupermercados (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaSupermercado ());
		q.setResultClass(Supermercado.class);
		return (List<Supermercado>) q.executeList();
	}
}
