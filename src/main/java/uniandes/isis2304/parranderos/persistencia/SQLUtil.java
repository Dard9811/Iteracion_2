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

package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto BAR de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLUtil
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
	public SQLUtil (PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para obtener un nuevo número de secuencia
	 * @param pm - El manejador de persistencia
	 * @return El número de secuencia generado
	 */
	public long nextval (PersistenceManager pm)
	{
        Query q = pm.newQuery(SQL, "SELECT "+ pp.darSeqSuperAndes () + ".nextval FROM DUAL");
        q.setResultClass(Long.class);
        long resp = (long) q.executeUnique();
        return resp;
	}

	/**
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @param pm - El manejador de persistencia
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas Bodega, NivelDeReorden, ProductoSucursal, BEBIDA,
	 * PersonanatProducto, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarSuperAndes (PersistenceManager pm)
	{
        Query qBodega = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaBodega ());          
        Query qCiudad = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCiudad());
        Query qEmpresa = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEmpresa());
        Query qEmpresaProducto = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEmpresaProducto());
        Query qEstante = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEstante());
        Query qNivelDeReorden = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaNivelDeReorden());
        Query qOrdenDePedido = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOrdenDePedido());
        Query qPersonaNat = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPersonaNat());
        Query qPersonanatProducto = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPersonanatProducto());
        Query qProducto = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProducto());
        Query qProductoSucursal = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProductoSucursal());
        Query qPromocion = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPromocion());
        Query qProveedor = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProveedor());
        Query qSucursal = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaSucursal());
        Query qSupermercado = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaSupermercado());
        		
        		
        long BodegaEliminados = (long) qBodega.executeUnique ();
        long CiudadEliminados = (long) qCiudad.executeUnique ();
        long EmpresaEliminados = (long) qEmpresa.executeUnique ();
        long EmpresaProductoEliminados = (long) qEmpresaProducto.executeUnique ();
        long EstanteEliminados = (long) qEstante.executeUnique ();
        long NivelDeReordenEliminados = (long) qNivelDeReorden.executeUnique ();
        long OrdenDePedidoEliminados = (long) qOrdenDePedido.executeUnique ();
        long PersonaNatEliminados = (long) qPersonaNat.executeUnique ();
        long PersonanatProductoEliminados = (long) qPersonanatProducto.executeUnique ();
        long ProductoEliminados = (long) qProducto.executeUnique ();
        long ProductoSucursalEliminadas = (long) qProductoSucursal.executeUnique ();
        long PromocionEliminados = (long) qPromocion.executeUnique ();
        long ProveedorEliminados = (long) qProveedor.executeUnique ();
        long SucursalEliminados = (long) qSucursal.executeUnique ();
        long SupermercadoEliminados = (long) qSupermercado.executeUnique ();
        return new long[] {BodegaEliminados, CiudadEliminados, EmpresaEliminados, EmpresaProductoEliminados,
        		EstanteEliminados, NivelDeReordenEliminados, OrdenDePedidoEliminados, PersonaNatEliminados,
        		PersonanatProductoEliminados, ProductoEliminados, ProductoSucursalEliminadas, PromocionEliminados,
        		ProveedorEliminados, SucursalEliminados,SupermercadoEliminados};
	}

}
