package uniandes.isis2304.superAndes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superAndes.negocio.Sucursal;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto CIUDAD de SuperAndes
 * 
 */
class SQLSucursal 
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
	public SQLSucursal (PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un CIUDAD a la base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarSucursal (PersistenceManager pm, long idSucursal, String local_ventas, String segmentacion_mercado, String productos_ofrecidos, String tamanio_instalacion, long idCiudad, long idSupermercado) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaSucursal () + "(id, local_ventas, segmentacion_mercado, productos_ofrecidos, tamanio_instalacion, ciudad, supermercado) values (?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(idSucursal, local_ventas, segmentacion_mercado, local_ventas, segmentacion_mercado, productos_ofrecidos, tamanio_instalacion, idCiudad, idSupermercado);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN SUCURSAL de la base de datos de SuperAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idSucursal - El identificador del sucursal
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarSucursalPorId (PersistenceManager pm, long idSucursal)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaSucursal () + " WHERE id = ?");
        q.setParameters(idSucursal);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN CIUDAD de la 
	 * base de datos de SuperAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idSucursal - El identificador del ciudad
	 * @return El objeto CIUDAD que tiene el identificador dado
	 */
	public Sucursal darSucursalPorId (PersistenceManager pm, long idSucursal) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaSucursal () + " WHERE id = ?");
		q.setResultClass(Sucursal.class);
		q.setParameters(idSucursal);
		return (Sucursal) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS CIUDADES de la 
	 * base de datos de SuperAndes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreSucursal - El nombre de ciudad buscado
	 * @return Una lista de objetos CIUDAD que tienen el nombre dado
	 */
	public List<Sucursal> darSucursalesPorSupermercado (PersistenceManager pm, long idSupermercado) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaSucursal () + " WHERE supermercado = ?");
		q.setResultClass(Sucursal.class);
		q.setParameters(idSupermercado);
		return (List<Sucursal>) q.executeList();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS CIUDADES de la 
	 * base de datos de SuperAndes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreSucursal - El nombre de ciudad buscado
	 * @return Una lista de objetos CIUDAD que tienen el nombre dado
	 */
	public List<Sucursal> darSucursalesPorCiudad (PersistenceManager pm, long idCiudad) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaSucursal () + " WHERE ciudad = ?");
		q.setResultClass(Sucursal.class);
		q.setParameters(idCiudad);
		return (List<Sucursal>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS CIUDADES de la 
	 * base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos CIUDAD
	 */
	public List<Sucursal> darSucursales (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaSucursal ());
		q.setResultClass(Sucursal.class);
		return (List<Sucursal>) q.executeList();
	}
	
}
