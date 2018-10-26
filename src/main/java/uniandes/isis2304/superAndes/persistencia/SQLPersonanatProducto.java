/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.superAndes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superAndes.negocio.ProductoSucursal;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto TIPO DE BEBIDA de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLPersonanatProducto 
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
	public SQLPersonanatProducto (PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un PersonanatProducto a la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param idPersonanatProducto - El identificador del tipo de bebida
	 * @param nombre - El nombre del tipo de bebida
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarPersonanatProducto (PersistenceManager pm, long personanatNumDoc, String personanatTipoDoc, String codProducto) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaPersonanatProducto  () + "(personanat_num_doc, personanat_tipo_doc, cod_producto) values (?, ?)");
        q.setParameters(personanatNumDoc,personanatTipoDoc, codProducto);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar TIPOS DE BEBIDA de la base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombrePersonanatProducto - El nombre del tipo de bebida
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarPersonanatProductoPorNombre (PersistenceManager pm, String nombrePersonanatProducto)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPersonanatProducto  () + " WHERE nombre = ?");
        q.setParameters(nombrePersonanatProducto);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar TIPOS DE BEBIDA de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idPersonanatProducto - El identificador del tipo de bebida
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarPersonanatProductoPorId (PersistenceManager pm, long idPersonanatProducto)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPersonanatProducto  () + " WHERE id = ?");
        q.setParameters(idPersonanatProducto);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN TIPO DE BEBIDA de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idPersonanatProducto - El identificador del tipo de bebida
	 * @return El objeto PersonanatProducto que tiene el identificador dado
	 */
	public ProductoSucursal darPersonanatProductoPorId (PersistenceManager pm, long idPersonanatProducto) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPersonanatProducto  () + " WHERE id = ?");
		q.setResultClass(ProductoSucursal.class);
		q.setParameters(idPersonanatProducto);
		return (ProductoSucursal) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN TIPO DE BEBIDA de la 
	 * base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombrePersonanatProducto - El nombre del tipo de bebida
	 * @return El objeto PersonanatProducto que tiene el nombre dado
	 */
	public List<ProductoSucursal> darTiposBebidaPorNombre (PersistenceManager pm, String nombrePersonanatProducto) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPersonanatProducto  () + " WHERE nombre = ?");
		q.setResultClass(ProductoSucursal.class);
		q.setParameters(nombrePersonanatProducto);
		return (List<ProductoSucursal>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS TIPOS DE BEBIDA de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos PersonanatProducto
	 */
	public List<ProductoSucursal> darTiposBebida (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPersonanatProducto  ());
		q.setResultClass(ProductoSucursal.class);
		return (List<ProductoSucursal>) q.executeList();
	}

}
