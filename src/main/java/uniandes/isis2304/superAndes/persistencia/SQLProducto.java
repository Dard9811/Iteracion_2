package uniandes.isis2304.superAndes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superAndes.negocio.Producto;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto CIUDAD de SuperAndes
 * 
 */
class SQLProducto 
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
	public SQLProducto (PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un CIUDAD a la base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarProducto (PersistenceManager pm, String codigo_barras, String nombre, String marca, String categoria, long precio_unitario, long precio_medida, String presentacion, long cantidad_presentacion, String unidad_medida, String especificacion_empacado, long idProveedor) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaProducto () + "(codigo_barras, nombre, marca, categoria, precio_unitario, precio_medida, presentacion, cantidad_presentacion, unidad_medida, especificacion_empacado, proveedor) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(codigo_barras, nombre, marca, categoria, precio_unitario, precio_medida, presentacion, cantidad_presentacion, unidad_medida, especificacion_empacado, idProveedor);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN SUCURSAL de la base de datos de SuperAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idProducto - El identificador del sucursal
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarProductoPorCodBarras (PersistenceManager pm, String codigo_barras)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProducto () + " WHERE codigo_barras = ?");
        q.setParameters(codigo_barras);
        return (long) q.executeUnique();            
	}

	public Producto darProductoPorCodBarras (PersistenceManager pm, String codigo_barras) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProducto () + " WHERE codigo_barras = ?");
		q.setResultClass(Producto.class);
		q.setParameters(codigo_barras);
		return (Producto) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS CIUDADES de la 
	 * base de datos de SuperAndes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreProducto - El nombre de ciudad buscado
	 * @return Una lista de objetos CIUDAD que tienen el nombre dado
	 */
	public List<Producto> darProductosPorBodega (PersistenceManager pm, long idBodega) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProducto () + " WHERE bodega = ?");
		q.setResultClass(Producto.class);
		q.setParameters(idBodega);
		return (List<Producto>) q.executeList();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS CIUDADES de la 
	 * base de datos de SuperAndes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreProducto - El nombre de ciudad buscado
	 * @return Una lista de objetos CIUDAD que tienen el nombre dado
	 */
	public List<Producto> darProductosPorEstante (PersistenceManager pm, long idEstante) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProducto () + " WHERE estante = ?");
		q.setResultClass(Producto.class);
		q.setParameters(idEstante);
		return (List<Producto>) q.executeList();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS CIUDADES de la 
	 * base de datos de SuperAndes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreProducto - El nombre de ciudad buscado
	 * @return Una lista de objetos CIUDAD que tienen el nombre dado
	 */
	public List<Producto> darProductosPorProveedor (PersistenceManager pm, long idProveedor) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProducto () + " WHERE proveedor = ?");
		q.setResultClass(Producto.class);
		q.setParameters(idProveedor);
		return (List<Producto>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS CIUDADES de la 
	 * base de datos de SuperAndes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos CIUDAD
	 */
	public List<Producto> darProductos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProducto ());
		q.setResultClass(Producto.class);
		return (List<Producto>) q.executeList();
	}
	
}
