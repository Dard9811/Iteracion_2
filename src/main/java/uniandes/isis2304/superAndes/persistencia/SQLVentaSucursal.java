package uniandes.isis2304.superAndes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superAndes.negocio.VentaSucursal;

public class SQLVentaSucursal {
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
	public SQLVentaSucursal (PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}
	
	
	public long adicionarVentaSucursal (PersistenceManager pm, long idVenta, long idSucursal)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaVentaSucursal () + "(idventa, idsucursal) values (?, ?)");
        q.setParameters(idVenta, idSucursal);
        return (long) q.executeUnique();
	}

	public long eliminarVentaSucursalPorIdVenta (PersistenceManager pm, long idVenta)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVentaSucursal () + " WHERE idventa = ?");
        q.setParameters(idVenta);
        return (long) q.executeUnique();            
	}

	public List<VentaSucursal> darVentaSucursalPorIdVenta (PersistenceManager pm, long idVenta) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVentaSucursal () + " WHERE idventa = ?");
		q.setResultClass(VentaSucursal.class);
		q.setParameters(idVenta);
		return (List<VentaSucursal>) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS CIUDADES de la 
	 * base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos CIUDAD
	 */
	public List<VentaSucursal> darVentaSucursales (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVentaSucursal ());
		q.setResultClass(VentaSucursal.class);
		return (List<VentaSucursal>) q.executeList();
	}
}
