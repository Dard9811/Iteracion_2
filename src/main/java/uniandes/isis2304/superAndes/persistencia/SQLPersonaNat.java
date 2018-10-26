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

import uniandes.isis2304.superAndes.negocio.PersonaNat;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto EMPRESA de Parranderos
 * 
 */
class SQLPersonaNat 
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
	public SQLPersonaNat (PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una EMPRESA a la base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @return El número de tuplas insertadas
	 */
	public long adicionarPersonaNat (PersistenceManager pm, long num_doc, String tipo_doc, String nombre, String correo) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaPersonaNat () + "(num_doc, tipo_doc, nombre, correo) values (?, ?, ?, ?)");
        q.setParameters(num_doc, tipo_doc, nombre, correo);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar EMPRESAS de la base de datos de SuperAndes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombrePersonaNat - El nombre dela empresa
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarPersonaNatPorNombre (PersistenceManager pm, String nombrePersonaNat)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPersonaNat () + " WHERE nombre = ?");
        q.setParameters(nombrePersonaNat);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UNA EMPRESA de la base de datos de SuperAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param nitPersonaNat - El identificador de la empresa
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarPersonaNatPorId (PersistenceManager pm, long num_doc, String tipo_doc)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPersonaNat () + " WHERE num_doc = ? AND tipo_doc = ?");
        q.setParameters(num_doc, tipo_doc);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA EMPRESA de la 
	 * base de datos de SuperAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param nitPersonaNat - El identificador de la empresa
	 * @return El objeto EMPRESA que tiene el identificador dado
	 */
	public PersonaNat darPersonaNatPorId (PersistenceManager pm, long num_doc, String tipo_doc) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPersonaNat () + " WHERE num_doc = ? AND tipo_doc = ?");
		q.setResultClass(PersonaNat.class);
		q.setParameters(num_doc, tipo_doc);
		return (PersonaNat) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS EMPRESAS de la 
	 * base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos EMPRESA
	 */
	public List<PersonaNat> darPersonaNats (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPersonaNat ());
		q.setResultClass(PersonaNat.class);
		return (List<PersonaNat>) q.executeList();
	}
	
}
