/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: SuperAndes
 * @version 1.0
 * @author da.ramirez14, jf.gutierrez13
 * Octubre de 2018
 * 
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.superAndes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superAndes.negocio.Empresa;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto EMPRESA de Parranderos
 * 
 */
class SQLEmpresa 
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
	public SQLEmpresa (PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una EMPRESA a la base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @return El número de tuplas insertadas
	 */
	public long adicionarEmpresa (PersistenceManager pm, long nit, String nombre, String correo) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaEmpresa () + "(nit, nombre, correo) values (?, ?, ?)");
        q.setParameters(nit, nombre, correo);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar EMPRESAS de la base de datos de SuperAndes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreEmpresa - El nombre dela empresa
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarEmpresaPorNombre (PersistenceManager pm, String nombreEmpresa)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEmpresa () + " WHERE nombre = ?");
        q.setParameters(nombreEmpresa);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UNA EMPRESA de la base de datos de SuperAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param nitEmpresa - El identificador de la empresa
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarEmpresaPorId (PersistenceManager pm, long nitEmpresa)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEmpresa () + " WHERE nit = ?");
        q.setParameters(nitEmpresa);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA EMPRESA de la 
	 * base de datos de SuperAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param nitEmpresa - El identificador de la empresa
	 * @return El objeto EMPRESA que tiene el identificador dado
	 */
	public Empresa darEmpresaPorId (PersistenceManager pm, long nitEmpresa) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEmpresa () + " WHERE nit = ?");
		q.setResultClass(Empresa.class);
		q.setParameters(nitEmpresa);
		return (Empresa) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS EMPRESAS de la 
	 * base de datos de SuperAndes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos BAR que tienen el nombre dado
	 */
	public List<Empresa> darEmpresasPorNombre (PersistenceManager pm, String nombreEmpresa) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEmpresa () + " WHERE nombre = ?");
		q.setResultClass(Empresa.class);
		q.setParameters(nombreEmpresa);
		return (List<Empresa>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS EMPRESAS de la 
	 * base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos EMPRESA
	 */
	public List<Empresa> darEmpresas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEmpresa ());
		q.setResultClass(Empresa.class);
		return (List<Empresa>) q.executeList();
	}
	
}
