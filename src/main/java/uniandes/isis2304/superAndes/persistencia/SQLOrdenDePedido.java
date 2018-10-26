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

import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superAndes.negocio.OrdenDePedido;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto OrdenDePedido de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLOrdenDePedido 
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
	public SQLOrdenDePedido (PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un OrdenDePedido a la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param idSupermercado - El identificador del bar
	 * @param idSucursal - El identificador de la bebida
	 * @param horario - El horario en que el bar sirve la bebida (DIURNO, NOCTURNO, TDOOS)
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarOrdenDePedido (PersistenceManager pm, long idSupermercado, long idSucursal, String nomProducto, long cantidad, Timestamp fechaEntrega, String estado) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaOrdenDePedido () + "(idsupermercado, idsucursal, nomproducto, cantidad, fecha_entrega, estado) values (?, ?, ?, ?, ?, ?)");
        q.setParameters(idSupermercado, idSucursal, nomProducto, cantidad, fechaEntrega, estado);
        return (long)q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN OrdenDePedido de la base de datos de Parranderos, por sus identificador
	 * @param pm - El manejador de persistencia
	 * @param idSupermercado - El identificador del bar
	 * @param idSucursal - El identificador de la bebida
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarOrdenDePedido (PersistenceManager pm, long idSupermercado, long idSucursal) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOrdenDePedido () + " WHERE idsupermercado = ? AND idsucursal = ?");
        q.setParameters(idSupermercado, idSucursal);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de los OrdenDePedido de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos OrdenDePedido
	 */
	public List<OrdenDePedido> darOrdenDePedido (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaOrdenDePedido ());
		q.setResultClass(OrdenDePedido.class);
		return (List<OrdenDePedido>) q.execute();
	}
	
//	/**
//	 * Crea y ejecuta la sentencia SQL para encontrar el identificador y el número de bebidas que OrdenDePedido los bares de la 
//	 * base de datos de Parranderos
//	 * @param pm - El manejador de persistencia
//	 * @return Una lista de parejas de objetos, el primer elemento de cada pareja representa el identificador de un bar,
//	 * 	el segundo elemento representa el número de bebidas que sirve (Una bebida que se sirve en dos horarios cuenta dos veces)
//	 */
//	public List<Object []> darBaresYCantidadBebidasOrdenDePedido (PersistenceManager pm)
//	{
//        String sql = "SELECT idBar, count (*) as numBebidas";
//        sql += " FROM " + pp.darTablaOrdenDePedido ();
//       	sql	+= " GROUP BY idBar";
//		Query q = pm.newQuery(SQL, sql);
//		return q.executeList();
//	}

}
