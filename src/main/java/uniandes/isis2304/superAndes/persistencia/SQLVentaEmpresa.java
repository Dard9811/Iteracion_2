package uniandes.isis2304.superAndes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superAndes.negocio.VentaEmpresa;

public class SQLVentaEmpresa {
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
	public SQLVentaEmpresa (PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}
	
	public long adicionarVentaEmpresa (PersistenceManager pm, long nit, long idVenta) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaVentaEmpresa () + "(nit_empresa, id_venta) values (?, ?)");
        q.setParameters(nit, idVenta);
        return (long) q.executeUnique();
	}

	public long eliminarVentaEmpresaPorIdVenta (PersistenceManager pm, long idVenta)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVentaEmpresa () + " WHERE id_Venta = ?");
        q.setParameters(idVenta);
        return (long) q.executeUnique();            
	}

	public List<VentaEmpresa> darVentaEmpresaPorIdVenta (PersistenceManager pm, long idVenta) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVentaEmpresa () + " WHERE id_venta = ?");
		q.setResultClass(VentaEmpresa.class);
		q.setParameters(idVenta);
		return (List<VentaEmpresa>) q.executeUnique();
	}
	
	public List<VentaEmpresa> darVentaEmpresaPorNitEmpresa (PersistenceManager pm, long nit) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVentaEmpresa () + " WHERE nit_empresa = ?");
		q.setResultClass(VentaEmpresa.class);
		q.setParameters(nit);
		return (List<VentaEmpresa>) q.executeUnique();
	}

	public List<VentaEmpresa> darVentaEmpresas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVentaEmpresa ());
		q.setResultClass(VentaEmpresa.class);
		return (List<VentaEmpresa>) q.executeList();
	}
}
