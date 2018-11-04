package uniandes.isis2304.superAndes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superAndes.negocio.VentaPersonaNat;

public class SQLVentaPersonaNat {
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
	public SQLVentaPersonaNat (PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}
	
	public long adicionarVentaPersonaNat (PersistenceManager pm, long numDoc, long idVenta, String tipoDoc) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaVentaPersonaNat () + "(num_doc_personanat, id_venta, tipo_doc_personanat) values (?, ?, ?)");
        q.setParameters(numDoc, idVenta, tipoDoc);
        return (long) q.executeUnique();
	}

	public long eliminarVentaPersonaNatPorIdVenta (PersistenceManager pm, long idVenta)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVentaPersonaNat () + " WHERE id_venta = ?");
        q.setParameters(idVenta);
        return (long) q.executeUnique();            
	}

	public VentaPersonaNat darVentaPersonaNatPorIdVenta (PersistenceManager pm, long idVenta) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVentaPersonaNat () + " WHERE id_venta = ?");
		q.setResultClass(VentaPersonaNat.class);
		q.setParameters(idVenta);
		return (VentaPersonaNat) q.executeUnique();
	}
	
	public List<VentaPersonaNat> darVentaPersonaNats (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVentaPersonaNat ());
		q.setResultClass(VentaPersonaNat.class);
		return (List<VentaPersonaNat>) q.executeList();
	}
}
