package uniandes.isis2304.superAndes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superAndes.negocio.Supermercado;
import uniandes.isis2304.superAndes.negocio.Venta;

public class SQLVenta {
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
	public SQLVenta (PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un CIUDAD a la base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarVenta (PersistenceManager pm, long id) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaVenta() + "(id) values (?)");
        q.setParameters(id);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN Supermercado de la base de datos de SuperAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del Supermercado
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarVentaPorId (PersistenceManager pm, long id)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVenta() + " WHERE id = ?");
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
	public Venta darVentaPorId (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVenta() + " WHERE id = ?");
		q.setResultClass(Venta.class);
		q.setParameters(id);
		return (Venta) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS CIUDADES de la 
	 * base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos CIUDAD
	 */
	public List<Venta> darVentas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVenta());
		q.setResultClass(Venta.class);
		return (List<Venta>) q.executeList();
	}
}
