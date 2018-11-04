package uniandes.isis2304.superAndes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superAndes.negocio.VentaProducto;

public class SQLVentaProducto {
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
	public SQLVentaProducto (PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}
	
	
	public long adicionarVentaProducto (PersistenceManager pm, long idVenta, String codProd, long cantidad)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaVentaProducto () + "(id_venta, cod_producto, cantidad) values (?, ?, ?)");
        q.setParameters(idVenta, codProd, cantidad);
        return (long) q.executeUnique();
	}

	public long eliminarVentaProductoPorIdVenta (PersistenceManager pm, long idVenta)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVentaProducto () + " WHERE id_venta = ?");
        q.setParameters(idVenta);
        return (long) q.executeUnique();            
	}

	public List<VentaProducto> darVentaProductoPorIdVenta (PersistenceManager pm, long idVenta) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVentaProducto () + " WHERE id_venta = ?");
		q.setResultClass(VentaProducto.class);
		q.setParameters(idVenta);
		return (List<VentaProducto>) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS CIUDADES de la 
	 * base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos CIUDAD
	 */
	public List<VentaProducto> darVentaProductos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVentaProducto ());
		q.setResultClass(VentaProducto.class);
		return (List<VentaProducto>) q.executeList();
	}
}
