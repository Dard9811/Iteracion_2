package uniandes.isis2304.superAndes.persistencia;

import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superAndes.negocio.Promocion;
import uniandes.isis2304.superAndes.negocio.PromocionProducto;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto PROMOCION de SuperAndes
 * 
 */
class SQLPromocionProducto 
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
	public SQLPromocionProducto (PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una PROMOCION a la base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @param idProducto - El identificador de la promo
	 * @param idPromocion - El tiempo de la oferta
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarPromoProd (PersistenceManager pm, long idProducto, long idPromocion) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaPromocionProducto() + "(idProducto, idPromocion) values (?, ?)");
        q.setParameters(idProducto, idPromocion);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar PROMOCIONS de la base de datos de SuperAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idProducto - El identificador de la bebida
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarPromoProdPorIdProd (PersistenceManager pm, long idProducto)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPromocionProducto() + " WHERE idProducto = ?");
        q.setParameters(idProducto);
        return (long) q.executeUnique();            
	}
	
	public long eliminarPromoProdPorIdPromo (PersistenceManager pm, long idPromocion)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPromocionProducto() + " WHERE idPromocion = ?");
        q.setParameters(idPromocion);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA PROMOCION de la 
	 * base de datos de SuperAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idPromo - El identificador de la bebida
	 * @return El objeto PROMOCION que tiene el identificador dado
	 */
	public Promocion darPromoPorIdProd (PersistenceManager pm, long idProducto) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPromocionProducto() + " WHERE idProducto = ?");
		q.setResultClass(Promocion.class);
		q.setParameters(idProducto);
		return (Promocion) q.executeUnique();
	}
	
	public Promocion darPromoPorIdPromocion (PersistenceManager pm, long idPromocion) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPromocionProducto() + " WHERE idPromocion = ?");
		q.setResultClass(Promocion.class);
		q.setParameters(idPromocion);
		return (Promocion) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS PROMOCIONES de la 
	 * base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos PROMOCION
	 */
	public List<PromocionProducto> darPromoProd (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPromocionProducto());
		q.setResultClass(PromocionProducto.class);
		return (List<PromocionProducto>) q.executeList();
	}

}
