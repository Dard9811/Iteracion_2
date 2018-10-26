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
import uniandes.isis2304.superAndes.negocio.EmpresaProducto;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto EMPRESA de Parranderos
 * 
 */
class SQLEmpresaProducto 
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
	public SQLEmpresaProducto (PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una EMPRESA a la base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @return El número de tuplas insertadas
	 */
	public long adicionarEmpresaProducto (PersistenceManager pm, long nit, String codProducto) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaEmpresa () + "(nit_empresa, cod_Producto) values (?, ?, ?)");
        q.setParameters(nit, codProducto);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar EMPRESAS de la base de datos de SuperAndes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param codProducto - El nombre dela empresa
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarEmpresaProductoPorNIT (PersistenceManager pm, long nit)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEmpresaProducto () + " WHERE nit_empresa = ?");
        q.setParameters(nit);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS EMPRESAS de la 
	 * base de datos de SuperAndes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos BAR que tienen el nombre dado
	 */
	public List<EmpresaProducto> darEmpresasProductoPorNit (PersistenceManager pm, long nit) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEmpresa () + " WHERE nit_empresa = ?");
		q.setResultClass(EmpresaProducto.class);
		q.setParameters(nit);
		return (List<EmpresaProducto>) q.executeList();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS EMPRESAS de la 
	 * base de datos de SuperAndes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos BAR que tienen el nombre dado
	 */
	public List<EmpresaProducto> darEmpresasProductoPorCodProducto (PersistenceManager pm, String codProducto) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEmpresa () + " WHERE cod_producto = ?");
		q.setResultClass(EmpresaProducto.class);
		q.setParameters(codProducto);
		return (List<EmpresaProducto>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS EMPRESAS de la 
	 * base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos EMPRESA
	 */
	public List<EmpresaProducto> darEmpresasProducto (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEmpresaProducto ());
		q.setResultClass(EmpresaProducto.class);
		return (List<EmpresaProducto>) q.executeList();
	}
	
}
