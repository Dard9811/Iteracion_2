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
        Query qBodegaProducto = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaBodegaProducto ());   
        Query qCiudad = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCiudad());
        Query qEmpresa = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEmpresa());
        Query qEstante = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEstante());
        Query qEstanteProducto = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEstanteProducto ());   
        Query qOrdenDePedido = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOrdenDePedido());
        Query qPersonaNat = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPersonaNat());
        Query qProducto = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProducto());
        Query qPromocion = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPromocion());
        Query qPromocionProducto = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPromocionProducto());    
        Query qProveedor = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProveedor());
        Query qSucursal = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaSucursal());
        Query qSupermercado = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaSupermercado());
        Query qVenta = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVenta());
        Query qVentaEmpresa = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVentaEmpresa());
        Query qVentaPersonaNat = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVentaPersonaNat());
        Query qVentaProducto = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVentaProducto());



        		
        long BodegaEliminados = (long) qBodega.executeUnique ();
        long BodegaProductoEliminados = (long) qBodegaProducto.executeUnique ();
        long CiudadEliminados = (long) qCiudad.executeUnique ();
        long EmpresaEliminados = (long) qEmpresa.executeUnique ();
        long EstanteEliminados = (long) qEstante.executeUnique ();
        long EstanteProductoEliminados = (long) qEstanteProducto.executeUnique ();
        long OrdenDePedidoEliminados = (long) qOrdenDePedido.executeUnique ();
        long PersonaNatEliminados = (long) qPersonaNat.executeUnique ();
        long ProductoEliminados = (long) qProducto.executeUnique ();
        long PromocionEliminados = (long) qPromocion.executeUnique ();
        long PromocionProductoEliminados = (long) qPromocionProducto.executeUnique ();
        long ProveedorEliminados = (long) qProveedor.executeUnique ();
        long SucursalEliminados = (long) qSucursal.executeUnique ();
        long SupermercadoEliminados = (long) qSupermercado.executeUnique ();
        long VentaEliminados = (long) qVenta.executeUnique ();
        long VentaEmpresaEliminados = (long) qVentaEmpresa.executeUnique ();
        long VentaPersonaNatEliminados = (long) qVentaPersonaNat.executeUnique ();
        long VentaProductoEliminados = (long) qVentaProducto.executeUnique ();
        return new long[] {BodegaEliminados, CiudadEliminados, EmpresaEliminados,
        		EstanteEliminados, OrdenDePedidoEliminados, PersonaNatEliminados,
        		ProductoEliminados, PromocionEliminados, PromocionProductoEliminados,
        		ProveedorEliminados, SucursalEliminados,SupermercadoEliminados,
        		VentaEliminados, VentaEmpresaEliminados, VentaPersonaNatEliminados,
        		VentaProductoEliminados};
	}

}
