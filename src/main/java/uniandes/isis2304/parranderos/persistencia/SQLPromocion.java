package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Promocion;
import uniandes.isis2304.parranderos.negocio.ProductoSucursal;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto PROMOCION de SuperAndes
 * 
 */
class SQLPromocion 
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
	public SQLPromocion (PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una PROMOCION a la base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @param idPromo - El identificador de la promo
	 * @param tiempo_oferta - El tiempo de la oferta
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarPromo (PersistenceManager pm, long idPromo, Timestamp tiempo_oferta) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaPromo () + "(id, tiempo_oferta) values (?, ?)");
        q.setParameters(idPromo, tiempo_oferta);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar PROMOCIONS de la base de datos de SuperAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idPromo - El identificador de la bebida
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarPromoPorId (PersistenceManager pm, long idPromo)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPromo () + " WHERE id = ?");
        q.setParameters(idPromo);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA PROMOCION de la 
	 * base de datos de SuperAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idPromo - El identificador de la bebida
	 * @return El objeto PROMOCION que tiene el identificador dado
	 */
	public Promocion darPromoPorId (PersistenceManager pm, long idPromo) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPromo () + " WHERE id = ?");
		q.setResultClass(ProductoSucursal.class);
		q.setParameters(idPromo);
		return (Promocion) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS PROMOCIONES de la 
	 * base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos PROMOCION
	 */
	public List<Promocion> darPromo (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPromo ());
		q.setResultClass(Promocion.class);
		return (List<Promocion>) q.executeList();
	}

}
