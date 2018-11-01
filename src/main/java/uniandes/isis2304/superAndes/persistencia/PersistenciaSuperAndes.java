	package uniandes.isis2304.superAndes.persistencia;
	
	
	import java.sql.Timestamp;
	import java.util.LinkedList;
	import java.util.List;
	
	import javax.jdo.JDODataStoreException;
	import javax.jdo.JDOHelper;
	import javax.jdo.PersistenceManager;
	import javax.jdo.PersistenceManagerFactory;
	import javax.jdo.Transaction;
	
	import org.apache.log4j.Logger;
	import com.google.gson.JsonArray;
	import com.google.gson.JsonElement;
	import com.google.gson.JsonObject;
	import com.sun.org.apache.xpath.internal.operations.Or;

import uniandes.isis2304.superAndes.negocio.Bodega;
import uniandes.isis2304.superAndes.negocio.BodegaProducto;
import uniandes.isis2304.superAndes.negocio.Cuidad;
import uniandes.isis2304.superAndes.negocio.Empresa;
import uniandes.isis2304.superAndes.negocio.EmpresaProducto;
import uniandes.isis2304.superAndes.negocio.Estante;
import uniandes.isis2304.superAndes.negocio.EstanteProducto;
import uniandes.isis2304.superAndes.negocio.NivelDeReorden;
import uniandes.isis2304.superAndes.negocio.OrdenDePedido;
import uniandes.isis2304.superAndes.negocio.PersonaNat;
import uniandes.isis2304.superAndes.negocio.PersonanatProducto;
import uniandes.isis2304.superAndes.negocio.Producto;
import uniandes.isis2304.superAndes.negocio.ProductoSucursal;
import uniandes.isis2304.superAndes.negocio.Promocion;
import uniandes.isis2304.superAndes.negocio.Proveedor;
import uniandes.isis2304.superAndes.negocio.Sucursal;
import uniandes.isis2304.superAndes.negocio.Supermercado;
	
	/**
	 * Clase para el manejador de persistencia del proyecto SuperAndes
	 * Traduce la información entre objetos Java y tuplas de la base de datos, en ambos sentidos
	 * Sigue un patrón SINGLETON (Sólo puede haber UN objeto de esta clase) para comunicarse de manera correcta
	 * con la base de datos
	 * Se apoya en las clases SQLBar, SQLBebedor, SQLBebida, SQLBodega, SQLNivelDeReorden, SQLPersonanatProducto y SQLSupermercado, que son 
	 * las que realizan el acceso a la base de datos
	 * 
	 */
	public class PersistenciaSuperAndes 
	{
		/* ****************************************************************
		 * 			Constantes
		 *****************************************************************/
		/**
		 * Logger para escribir la traza de la ejecución
		 */
		private static Logger log = Logger.getLogger(PersistenciaSuperAndes.class.getName());
	
		/**
		 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
		 */
		public final static String SQL = "javax.jdo.query.SQL";
	
		/* ****************************************************************
		 * 			Atributos
		 *****************************************************************/
		/**
		 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
		 */
		private static PersistenciaSuperAndes instance;
	
		/**
		 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
		 */
		private PersistenceManagerFactory pmf;
	
		/**
		 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
		 * Secuenciador, PersonanatProducto, bebida, bar, bebedor, Bodega, NivelDeReorden y Supermercado
		 */
		private List <String> tablas;
	
		/**
		 * Atributo para el acceso a las sentencias SQL propias a PersistenciaParranderos
		 */
		private SQLUtil sqlUtil;
	
		/**
		 * Atributo para el acceso a la tabla BODEGA de la base de datos
		 */
		private SQLBodega sqlBodega;
	
		/**
		 * Atributo para el acceso a la tabla BODEGA PRODUCTO de la base de datos
		 */
		private SQLBodegaProducto sqlBodegaProducto;
	
		/**
		 * Atributo para el acceso a la tabla CIUDAD de la base de datos
		 */
		private SQLCiudad sqlCiudad;
	
		/**
		 * Atributo para el acceso a la tabla EMPRESA de la base de datos
		 */
		private SQLEmpresa sqlEmpresa;
	
		/**
		 * Atributo para el acceso a la tabla EMPRESA PRODUCTO de la base de datos
		 */
		private SQLEmpresaProducto sqlEmpresaProducto;
	
		/**
		 * Atributo para el acceso a la tabla ESTANTE de la base de datos
		 */
		private SQLEstante sqlEstante;
	
		/**
		 * Atributo para el acceso a la tabla ESTANTE de la base de datos
		 */
		private SQLEstanteProducto sqlEstanteProducto;
	
		/**
		 * Atributo para el acceso a la tabla NIVEL DE REORDEN de la base de datos
		 */
		private SQLNivelDeReorden sqlNivelDeReorden;
	
		/**
		 * Atributo para el acceso a la tabla ORDEN DE PEDIDO de la base de datos
		 */
		private SQLOrdenDePedido sqlOrdenDePedido;
	
		/**
		 * Atributo para el acceso a la tabla PERSONA NAT de la base de datos
		 */
		private SQLPersonaNat sqlPersonaNat;
	
		/**
		 * Atributo para el acceso a la tabla PERSONANAT PRODUCTO de la base de datos
		 */
		private SQLPersonanatProducto sqlPersonanatProducto;
	
		/**
		 * Atributo para el acceso a la tabla PRODUCTO de la base de datos
		 */
		private SQLProducto sqlProducto;
	
		/**
		 * Atributo para el acceso a la tabla PRODUCTO SUCURSAL de la base de datos
		 */
		private SQLProductoSucursal sqlProductoSucursal;
	
		/**
		 * Atributo para el acceso a la tabla PROMOCION de la base de datos
		 */
		private SQLPromocion sqlPromocion;
	
		/**
		 * Atributo para el acceso a la tabla PROVEEDOR de la base de datos
		 */
		private SQLProveedor sqlProveedor;
	
		/**
		 * Atributo para el acceso a la tabla SUCURSAL de la base de datos
		 */
		private SQLSucursal sqlSucursal;
	
		/**
		 * Atributo para el acceso a la tabla SUPERMERCADO de la base de datos
		 */
		private SQLSupermercado sqlSupermercado;
	
		/* ****************************************************************
		 * 			Métodos del MANEJADOR DE PERSISTENCIA
		 *****************************************************************/
	
		/**
		 * Constructor privado con valores por defecto - Patrón SINGLETON
		 */
		private PersistenciaSuperAndes ()
		{
			pmf = JDOHelper.getPersistenceManagerFactory("SuperAndes");		
			crearClasesSQL ();
	
			// Define los nombres por defecto de las tablas de la base de datos
			tablas = new LinkedList<String> ();
			tablas.add("SuperAndes_sequence");
			tablas.add ("BODEGA");
			tablas.add("BODEGA_PRODUCTO");
			tablas.add ("CIUDAD");
			tablas.add ("EMPRESA");
			tablas.add ("EMPRESA_PRODUCTO");
			tablas.add ("ESTANTE");
			tablas.add("ESTANTE_PRODUCTO");
			tablas.add ("NIVEL_DE_REORDEN");
			tablas.add ("ORDEN_DE_PEDIDO");
			tablas.add ("PERSONANAT");
			tablas.add ("PERSONANAT_PRODUCTO");
			tablas.add ("PRODUCTO");
			tablas.add ("PRODUCTO_SUCURSAL");
			tablas.add ("PROMOCION");
			tablas.add ("PROVEEDOR");
			tablas.add("SUCURSAL");
			tablas.add("SUPERMERCADO");
		}
	
		/**
		 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
		 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
		 */
		private PersistenciaSuperAndes (JsonObject tableConfig)
		{
			crearClasesSQL ();
			tablas = leerNombresTablas (tableConfig);
	
			String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
			log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
			pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
		}
	
		/**
		 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
		 */
		public static PersistenciaSuperAndes getInstance ()
		{
			if (instance == null)
			{
				instance = new PersistenciaSuperAndes ();
			}
			return instance;
		}
	
		/**
		 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
		 * @param tableConfig - El objeto JSON con los nombres de las tablas
		 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
		 */
		public static PersistenciaSuperAndes getInstance (JsonObject tableConfig)
		{
			if (instance == null)
			{
				instance = new PersistenciaSuperAndes (tableConfig);
			}
			return instance;
		}
	
		/**
		 * Cierra la conexión con la base de datos
		 */
		public void cerrarUnidadPersistencia ()
		{
			pmf.close ();
			instance = null;
		}
	
		/**
		 * Genera una lista con los nombres de las tablas de la base de datos
		 * @param tableConfig - El objeto Json con los nombres de las tablas
		 * @return La lista con los nombres del secuenciador y de las tablas
		 */
		private List <String> leerNombresTablas (JsonObject tableConfig)
		{
			JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;
	
			List <String> resp = new LinkedList <String> ();
			for (JsonElement nom : nombres)
			{
				resp.add (nom.getAsString ());
			}
	
			return resp;
		}
	
		/**
		 * Crea los atributos de clases de apoyo SQL
		 */
		private void crearClasesSQL ()
		{
			sqlUtil = new SQLUtil(this);
			sqlBodega = new SQLBodega(this);
			sqlBodegaProducto = new SQLBodegaProducto(this);
			sqlCiudad = new SQLCiudad(this);
			sqlEmpresa = new SQLEmpresa(this);
			sqlEmpresaProducto = new SQLEmpresaProducto(this);
			sqlEstante= new SQLEstante(this);
			sqlEstanteProducto = new SQLEstanteProducto(this);
			sqlNivelDeReorden = new SQLNivelDeReorden(this);
			sqlOrdenDePedido = new SQLOrdenDePedido(this);
			sqlPersonaNat = new SQLPersonaNat(this);		
			sqlPersonanatProducto = new SQLPersonanatProducto(this);
			sqlProducto = new SQLProducto(this);
			sqlProductoSucursal = new SQLProductoSucursal(this);
			sqlPromocion = new SQLPromocion(this);
			sqlProveedor = new SQLProveedor(this);
			sqlSucursal = new SQLSucursal(this);
			sqlSupermercado = new SQLSupermercado(this);
		}
	
		public String darTablaBodega()
		{
			return tablas.get(1);
		}
	
		public String darTablaBodegaProducto()
		{
			return tablas.get(2);
		}
	
		public String darTablaCiudad()
		{
			return tablas.get(3);
		}
	
		public String darTablaEmpresa()
		{
			return tablas.get(4);
		}
	
		public String darTablaEmpresaProducto()
		{
			return tablas.get(5);
		}
	
		public String darTablaEstante()
		{
			return tablas.get(6);
		}
	
		public String darTablaEstanteProducto()
		{
			return tablas.get(7);
		}
	
		public String darTablaNivelDeReorden()
		{
			return tablas.get(8);
		}
	
		public String darTablaOrdenDePedido()
		{
			return tablas.get(9);
		}
	
		public String darTablaPersonaNat()
		{
			return tablas.get(10);
		}
	
		public String darTablaPersonanatProducto()
		{
			return tablas.get(11);
		}
	
		public String darTablaProducto()
		{
			return tablas.get(12);
		}
	
		public String darTablaProductoSucursal()
		{
			return tablas.get(13);
		}
	
		public String darTablaPromocion()
		{
			return tablas.get(14);
		}
	
		public String darTablaProveedor()
		{
			return tablas.get(15);
		}
	
		public String darTablaSucursal()
		{
			return tablas.get(16);
		}
	
		public String darTablaSupermercado()
		{
			return tablas.get(17);
		}
	
		public String darSeqSuperAndes()
		{
			return tablas.get(0);
		}
	
		/**
		 * Transacción para el generador de secuencia de Parranderos
		 * Adiciona entradas al log de la aplicación
		 * @return El siguiente número del secuenciador de Parranderos
		 */
		private long nextval ()
		{
			long resp = sqlUtil.nextval (pmf.getPersistenceManager());
			log.trace ("Generando secuencia: " + resp);
			return resp;
		}
	
		/**
		 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
		 * @param e - La excepción que ocurrio
		 * @return El mensaje de la excepción JDO
		 */
		private String darDetalleException(Exception e) 
		{
			String resp = "";
			if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
			{
				JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
				return je.getNestedExceptions() [0].getMessage();
			}
			return resp;
		}
	
		/* ****************************************************************
		 * 			Métodos para manejar la relación BODEGA
		 *****************************************************************/
	
		/**
		 * Método que inserta, de manera transaccional, una tupla en la tabla Bodega
		 * Adiciona entradas al log de la aplicación
		 * @param id - El identificador de la bodega 
		 * @param espacio - El espacio de la bodega 
		 * @return Un objeto Bodega con la información dada. Null si ocurre alguna Excepción
		 */
		public Bodega adicionarBodega(long id, long espacio) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long tuplasInsertadas = sqlBodega.adicionarBodega (pm, id, espacio);
				tx.commit();
	
				log.trace ("Inserción de Bodega: [" + id + ", " + espacio + "]. " + tuplasInsertadas + " tuplas insertadas");
	
				return new Bodega (id, espacio);
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return null;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que elimina, de manera transaccional, una tupla en la tabla Bodega, dados los identificadores de bebedor y bebida
		 * @param id - El identificador del bebedor
		 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
		 */
		public long eliminarBodega(long id) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long resp = sqlBodega.eliminarBodega(pm, id);           
				tx.commit();
	
				return resp;
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return -1;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla Bodega
		 * @return La lista de objetos Bodega, construidos con base en las tuplas de la tabla Bodega
		 */
		public List<Bodega> darBodega ()
		{
			return sqlBodega.darBodega (pmf.getPersistenceManager());
		}
	
	
		/* ****************************************************************
		 * 			Métodos para manejar la relación BODEGA PRODUCTO
		 *****************************************************************/
	
		/**
		 * Método que inserta, de manera transaccional, una tupla en la tabla Bodega
		 * Adiciona entradas al log de la aplicación
		 * @param codProducto - El identificador del producto 
		 * @param cantidad - cantidad de productosen bodega
		 * @return Un objeto Bodega Producto con la información dada. Null si ocurre alguna Excepción
		 */
		public BodegaProducto adicionarBodegaProducto( String codProducto, long cantidad) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long idBodega = nextval();
				long tuplasInsertadas = sqlBodegaProducto.adicionarBodegaProducto (pm, idBodega, codProducto, cantidad);
				tx.commit();
	
				log.trace ("Inserción de Bodega: [" + idBodega + ", " + codProducto + "]. " + tuplasInsertadas + " tuplas insertadas");
	
				return new BodegaProducto(idBodega, codProducto, cantidad);
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return null;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que elimina, de manera transaccional, una tupla en la tabla Bodega, dados los identificadores de bebedor y bebida
		 * @param idBebedor - El identificador del bebedor
		 * @param idBebida - El identificador de la bebida
		 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
		 */
		public long eliminarBodegaProducto(long idBodega) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long resp = sqlBodega.eliminarBodega(pm, idBodega);           
				tx.commit();
	
				return resp;
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return -1;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla Bodega
		 * @return La lista de objetos Bodega, construidos con base en las tuplas de la tabla Bodega
		 */
		public List<BodegaProducto> darBodegaProducto ()
		{
			return sqlBodegaProducto.darBodegaProducto(pmf.getPersistenceManager());
		}
	
		/* ****************************************************************
		 * 			Métodos para manejar la CIUDAD
		 *****************************************************************/
	
		/**
		 * Método que inserta, de manera transaccional, una tupla en la tabla CIUDAD
		 * Adiciona entradas al log de la aplicación
		 * @param nombre - El nombre del bebedor
		 * @return El objeto CIUDAD adicionado. null si ocurre alguna Excepción
		 */
		public Cuidad adicionarCiudad(String nombre, String direccion) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long idCiudad = nextval ();
				long tuplasInsertadas = sqlCiudad.adicionarCiudad(pmf.getPersistenceManager(), idCiudad, nombre, direccion);
				tx.commit();
	
				log.trace ("Inserción de ciudad: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
	
				return new Cuidad (idCiudad, nombre, direccion);
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return null;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que elimina, de manera transaccional, una tupla en la tabla CIUDAD, dado el nombre del ciudad
		 * Adiciona entradas al log de la aplicación
		 * @param nombre - El nombre del ciudad
		 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
		 */
		public long eliminarCiudadPorNombre(String nombre) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long resp = sqlCiudad.eliminarCiudadPorNombre (pm, nombre);
				tx.commit();
				return resp;
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return -1;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que elimina, de manera transaccional, una tupla en la tabla CIUDAD, dado el identificador del ciudad
		 * Adiciona entradas al log de la aplicación
		 * @param idCiudad - El identificador del ciudad
		 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
		 */
		public long eliminarCiudadPorId (long idCiudad) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long resp = sqlCiudad.eliminarCiudadPorId (pm, idCiudad);
				tx.commit();
				return resp;
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return -1;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla CIUDAD que tienen el nombre dado
		 * @param nombreCiudad - El nombre del ciudad
		 * @return La lista de objetos CIUDAD, construidos con base en las tuplas de la tabla CIUDAD
		 */
		public List<Cuidad> darCiudadesPorNombre (String nombreCiudad) 
		{
			return sqlCiudad.darCiudadesPorNombre (pmf.getPersistenceManager(), nombreCiudad);
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla BEBEDOR que tienen el identificador dado
		 * @param idBebedor - El identificador del bebedor
		 * @return El objeto BEBEDOR, construido con base en la tuplas de la tabla BEBEDOR, que tiene el identificador dado
		 */
		public Cuidad darCiudadPorId (long idCiudad) 
		{
			return (Cuidad) sqlCiudad.darCiudadPorId (pmf.getPersistenceManager(), idCiudad);
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla BEBEDOR
		 * @return La lista de objetos BEBEDOR, construidos con base en las tuplas de la tabla BEBEDOR
		 */
		public List<Cuidad> darCiudades ()
		{
			return sqlCiudad.darCiudades (pmf.getPersistenceManager());
		}
	
		/* ****************************************************************
		 * 			Métodos para manejar las EMPRESAS
		 *****************************************************************/
	
		/**
		 * Método que inserta, de manera transaccional, una tupla en la tabla EMPRESA
		 * Adiciona entradas al log de la aplicación
		 * @param nombre - El nombre de la empresa
		 * @return El objeto Empresa adicionado. null si ocurre alguna Excepción
		 */
		public Empresa adicionarEmpresa(long nit, String nombre, String correo) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long tuplasInsertadas = sqlEmpresa.adicionarEmpresa(pm, nit, nombre, correo);
				tx.commit();
	
				log.trace ("Inserción de Empresa: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
	
				return new Empresa (nit, nombre, correo);
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return null;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que elimina, de manera transaccional, una tupla en la tabla EMPRESA, dado el nombre de la empresa
		 * Adiciona entradas al log de la aplicación
		 * @param nombreBar - El nombre del bar
		 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
		 */
		public long eliminarEmpresaPorNombre (String nombreEmpresa) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long resp = sqlEmpresa.eliminarEmpresaPorNombre(pm, nombreEmpresa);
				tx.commit();
	
				return resp;
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return -1;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que elimina, de manera transaccional, una tupla en la tabla EMPRESA, dado el nit de la empresa
		 * Adiciona entradas al log de la aplicación
		 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
		 */
		public long eliminarEmpresaPorNit (long nit) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long resp = sqlEmpresa.eliminarEmpresaPorId (pm, nit);
				tx.commit();
	
				return resp;
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return -1;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla BAR
		 * @return La lista de objetos BAR, construidos con base en las tuplas de la tabla BAR
		 */
		public List<Empresa> darEmpresas ()
		{
			return sqlEmpresa.darEmpresas (pmf.getPersistenceManager());
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla BAR que tienen el nombre dado
		 * @param nombreBar - El nombre del bar
		 * @return La lista de objetos BAR, construidos con base en las tuplas de la tabla BAR
		 */
		public List<Empresa> darEmpresasPorNombre (String nombreEmpresa)
		{
			return sqlEmpresa.darEmpresasPorNombre (pmf.getPersistenceManager(), nombreEmpresa);
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla BAR que tienen el identificador dado
		 * @param idBar - El identificador del bar
		 * @return El objeto BAR, construido con base en la tuplas de la tabla BAR, que tiene el identificador dado
		 */
		public Empresa darEmpresaPorNit (long nit)
		{
			return sqlEmpresa.darEmpresaPorId (pmf.getPersistenceManager(), nit);
		}
	
	
		/* ****************************************************************
		 * 			Métodos para manejar las EMPRESAS PRODUCTO
		 *****************************************************************/
	
		/**
		 * Método que inserta, de manera transaccional, una tupla en la tabla EMPRESA
		 * Adiciona entradas al log de la aplicación
		 * @param codProducto - El nombre de la empresa
		 * @return El objeto Empresa adicionado. null si ocurre alguna Excepción
		 */
		public EmpresaProducto adicionarEmpresaProducto(long nitEmpresa, String codProducto) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long tuplasInsertadas = sqlEmpresaProducto.adicionarEmpresaProducto(pm, nitEmpresa, codProducto);
				tx.commit();
	
				log.trace ("Inserción de Empresa: " + codProducto + ": " + tuplasInsertadas + " tuplas insertadas");
	
				return new EmpresaProducto(nitEmpresa, codProducto);
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return null;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que elimina, de manera transaccional, una tupla en la tabla EMPRESA, dado el nombre de la empresa
		 * Adiciona entradas al log de la aplicación
		 * @param nombreBar - El nombre del bar
		 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
		 */
		public long eliminarEmpresaProductoPorNIT (long nit) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long resp = sqlEmpresaProducto.eliminarEmpresaProductoPorNIT(pm, nit);
				tx.commit();
	
				return resp;
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return -1;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla BAR
		 * @return La lista de objetos BAR, construidos con base en las tuplas de la tabla BAR
		 */
		public List<EmpresaProducto> darEmpresasProducto ()
		{
			return sqlEmpresaProducto.darEmpresasProducto(pmf.getPersistenceManager());
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla BAR que tienen el nombre dado
		 * @param nombreBar - El nombre del bar
		 * @return La lista de objetos BAR, construidos con base en las tuplas de la tabla BAR
		 */
		public List<EmpresaProducto> darEmpresasProductoPorNIT (long nit)
		{
			return sqlEmpresaProducto.darEmpresasProductoPorNit(pmf.getPersistenceManager(), nit);
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla BAR que tienen el identificador dado
		 * @param idBar - El identificador del bar
		 * @return El objeto BAR, construido con base en la tuplas de la tabla BAR, que tiene el identificador dado
		 */
		public List<EmpresaProducto> darEmpresaProductoPorCodProducto (String codProducto)
		{
			return sqlEmpresaProducto.darEmpresasProductoPorCodProducto(pmf.getPersistenceManager(), codProducto);
		}
	
		/* ****************************************************************
		 * 			Métodos para manejar la relación ESTANTE
		 *****************************************************************/
	
		/**
		 * Método que inserta, de manera transaccional, una tupla en la tabla CIUDAD
		 * Adiciona entradas al log de la aplicación
		 * @param nombre - El nombre del bebedor
		 * @return El objeto CIUDAD adicionado. null si ocurre alguna Excepción
		 */
		public Estante adicionarEstante(int espacio, long idBodega) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long idEstante = nextval ();
				long tuplasInsertadas = sqlEstante.adicionarEstante(pmf.getPersistenceManager(), idEstante, espacio, idBodega);
				tx.commit();
	
				log.trace ("Inserción de estante: " + idEstante + ": " + tuplasInsertadas + " tuplas insertadas");
	
				return new Estante (idEstante, espacio, idBodega);
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return null;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que elimina, de manera transaccional, una tupla en la tabla CIUDAD, dado el identificador del ciudad
		 * Adiciona entradas al log de la aplicación
		 * @param idEstante - El identificador del ciudad
		 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
		 */
		public long eliminarEstantePorId (long idEstante) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long resp = sqlEstante.eliminarEstantePorId (pm, idEstante);
				tx.commit();
				return resp;
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return -1;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla BEBEDOR que tienen el identificador dado
		 * @param idBebedor - El identificador del bebedor
		 * @return El objeto BEBEDOR, construido con base en la tuplas de la tabla BEBEDOR, que tiene el identificador dado
		 */
		public Estante darEstantePorId (long idEstante) 
		{
			return (Estante) sqlEstante.darEstantePorId (pmf.getPersistenceManager(), idEstante);
		}
	
		public List<Estante> darEstantes ()
		{
			return sqlEstante.darEstantes (pmf.getPersistenceManager());
		}
	
		/* ****************************************************************
		 * 			Métodos para manejar la relación ESTANTE PRODUCTO
		 *****************************************************************/
	
		/**
		 * Método que inserta, de manera transaccional, una tupla en la tabla CIUDAD
		 * Adiciona entradas al log de la aplicación
		 * @param nombre - El nombre del bebedor
		 * @return El objeto CIUDAD adicionado. null si ocurre alguna Excepción
		 */
		public EstanteProducto adicionarEstanteProducot(String codProducto, long cantidad) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long idEstante = nextval ();
				long tuplasInsertadas = sqlEstanteProducto.adicionarEstanteProducto(pmf.getPersistenceManager(), idEstante, codProducto, cantidad);
				tx.commit();
	
				log.trace ("Inserción de estante: " + idEstante + ": " + tuplasInsertadas + " tuplas insertadas");
	
				return new EstanteProducto(idEstante, codProducto, cantidad);
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return null;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que elimina, de manera transaccional, una tupla en la tabla CIUDAD, dado el identificador del ciudad
		 * Adiciona entradas al log de la aplicación
		 * @param idEstante - El identificador del ciudad
		 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
		 */
		public long eliminarEstanteProducto (long idEstante, String codProducto) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long resp = sqlEstanteProducto.eliminarEstanteProducto(pm, idEstante, codProducto);
				tx.commit();
				return resp;
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return -1;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		public List<EstanteProducto> darEstantesProductos ()
		{
			return sqlEstanteProducto.darEstantesProductos(pmf.getPersistenceManager());
		}
	
		/* ****************************************************************
		 * 			Métodos para manejar la relación NIVEL DE REORDEN
		 *****************************************************************/
	
		/**
		 * Método que inserta, de manera transaccional, una tupla en la tabla NivelDeReorden
		 * Adiciona entradas al log de la aplicación
		 * @param idBodega - El identificador del bar - Debe haber un bar con ese identificador
		 * @param idSucursal - El identificador de la bebida - Debe haber una bebida con ese identificador
		 * @param cantidadMin - El hororio en que se sirve (DIURNO, NOCTURNO, TODOS)
		 * @return Un objeto NivelDeReorden con la información dada. Null si ocurre alguna Excepción
		 */
		public NivelDeReorden adicionarNivelDeReorden (long idBodega, long idSucursal, long cantidadMin, long cantidadRecompra) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long tuplasInsertadas = sqlNivelDeReorden.adicionarNivelDeReorden (pmf.getPersistenceManager(), idBodega, idSucursal, cantidadMin, cantidadRecompra);
				tx.commit();
	
				log.trace ("Inserción de Bodega: [" + idBodega + ", " + idSucursal + "]. " + tuplasInsertadas + " tuplas insertadas");
	
				return new NivelDeReorden (idBodega, idSucursal, cantidadMin, cantidadRecompra);
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return null;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que elimina, de manera transaccional, una tupla en la tabla NivelDeReorden, dados los identificadores de bar y bebida
		 * @param idBar - El identificador del bar
		 * @param idBebida - El identificador de la bebida
		 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
		 */
		public long eliminarNivelDeReorden (long idBar, long idBebida) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long resp = sqlNivelDeReorden.eliminarNivelDeReorden (pm, idBar, idBebida);	            
				tx.commit();
	
				return resp;
			}
			catch (Exception e)
			{
				//	        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return -1;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla NivelDeReorden
		 * @return La lista de objetos NivelDeReorden, construidos con base en las tuplas de la tabla NivelDeReorden
		 */
		public List<NivelDeReorden> darNivelDeReorden ()
		{
			return sqlNivelDeReorden.darNivelDeReorden (pmf.getPersistenceManager());
		}
	
		/* ****************************************************************
		 * 			Métodos para manejar la relación ORDEN DE PEDIDO
		 *****************************************************************/
	
		/**
		 * Método que inserta, de manera transaccional, una tupla en la tabla NivelDeReorden
		 * Adiciona entradas al log de la aplicación
		 * @param idBodega - El identificador del bar - Debe haber un bar con ese identificador
		 * @param idSucursal - El identificador de la bebida - Debe haber una bebida con ese identificador
		 * @param cantidadMin - El hororio en que se sirve (DIURNO, NOCTURNO, TODOS)
		 * @return Un objeto NivelDeReorden con la información dada. Null si ocurre alguna Excepción
		 */
		public OrdenDePedido adicionarOrdenDePedido (long idSupermercado, long idSucursal, String nomProducto, long cantidad, Timestamp fechaEntrega, String estado) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long tuplasInsertadas = sqlOrdenDePedido.adicionarOrdenDePedido(pmf.getPersistenceManager(), idSupermercado, idSucursal, nomProducto, cantidad, fechaEntrega, estado);
				tx.commit();
	
				log.trace ("Inserción de Bodega: [" + idSupermercado + ", " + idSucursal + "]. " + tuplasInsertadas + " tuplas insertadas");
	
				return new OrdenDePedido(idSupermercado, idSucursal, nomProducto, cantidad, fechaEntrega, estado);
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return null;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que elimina, de manera transaccional, una tupla en la tabla NivelDeReorden, dados los identificadores de bar y bebida
		 * @param idSupermercado - El identificador del bar
		 * @param idSucursal - El identificador de la bebida
		 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
		 */
		public long eliminarOrdenDePedido (long idSupermercado, long idSucursal) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long resp = sqlOrdenDePedido.eliminarOrdenDePedido(pm, idSupermercado, idSucursal);	            
				tx.commit();
	
				return resp;
			}
			catch (Exception e)
			{
				//	        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return -1;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla NivelDeReorden
		 * @return La lista de objetos NivelDeReorden, construidos con base en las tuplas de la tabla NivelDeReorden
		 */
		public List<OrdenDePedido> darOrdenDePedido ()
		{
			return sqlOrdenDePedido.darOrdenDePedido(pmf.getPersistenceManager());
		}
	
		/* ****************************************************************
		 * 			Métodos para manejar los PERSONANAT
		 *****************************************************************/
	
		/**
		 * Método que elimina, de manera transaccional, una tupla en la tabla EMPRESA, dado el nombre de la empresa
		 * Adiciona entradas al log de la aplicación
		 * @param nombreBar - El nombre del bar
		 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
		 */
		public long eliminarPersonaNatPorNombre (String nombrePersonaNat) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long resp = sqlPersonaNat.eliminarPersonaNatPorNombre(pm, nombrePersonaNat);
				tx.commit();
	
				return resp;
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return -1;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que elimina, de manera transaccional, una tupla en la tabla EMPRESA, dado el nit de la empresa
		 * Adiciona entradas al log de la aplicación
		 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
		 */
		public long eliminarPersonaNatPorDocumento (long num_doc, String tipo_doc) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long resp = sqlPersonaNat.eliminarPersonaNatPorId (pm, num_doc, tipo_doc);
				tx.commit();
	
				return resp;
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return -1;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
	
		/**
		 * Método que inserta, de manera transaccional, una tupla en la tabla EMPRESA
		 * Adiciona entradas al log de la aplicación
		 * @param nombre - El nombre de la empresa
		 * @return El objeto Empresa adicionado. null si ocurre alguna Excepción
		 */
		public PersonaNat adicionarPersonaNat(long num_doc, String tipo_doc, String nombre, String correo) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long tuplasInsertadas = sqlPersonaNat.adicionarPersonaNat(pm, num_doc, tipo_doc, nombre, correo);
				tx.commit();
	
				log.trace ("Inserción de PersonaNat: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
	
				return new PersonaNat (num_doc, tipo_doc, nombre, correo);
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return null;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla BAR
		 * @return La lista de objetos BAR, construidos con base en las tuplas de la tabla BAR
		 */
		public List<PersonaNat> darPersonaNats ()
		{
			return sqlPersonaNat.darPersonaNats (pmf.getPersistenceManager());
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla BAR que tienen el identificador dado
		 * @param idBar - El identificador del bar
		 * @return El objeto BAR, construido con base en la tuplas de la tabla BAR, que tiene el identificador dado
		 */
		public PersonaNat darPersonaNatPorDocumento (long num_doc, String tipo_doc)
		{
			return sqlPersonaNat.darPersonaNatPorId (pmf.getPersistenceManager(), num_doc, tipo_doc);
		}
	
		/* ****************************************************************
		 * 			Métodos para manejar los PERSONANAT PRODUCTO
		 *****************************************************************/
	
		/**
		 * Método que inserta, de manera transaccional, una tupla en la tabla ProductoSucursal
		 * Adiciona entradas al log de la aplicación
		 * @param nombre - El nombre del tipo de bebida
		 * @return El objeto ProductoSucursal adicionado. null si ocurre alguna Excepción
		 */
		public PersonanatProducto adicionarPersonanatProducto(long personanatNumDoc, String personanatTipoDoc, String codProducto)
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long tuplasInsertadas = sqlPersonanatProducto.adicionarPersonanatProducto(pm, personanatNumDoc, personanatTipoDoc, codProducto);
				tx.commit();
	
				log.trace ("Inserción de prodcuto sucursal: " + codProducto + ": " + tuplasInsertadas + " tuplas insertadas");
	
				return new PersonanatProducto(personanatNumDoc, personanatTipoDoc, codProducto);
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return null;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que elimina, de manera transaccional, una tupla en la tabla ProductoSucursal, dado el nombre del tipo de bebida
		 * Adiciona entradas al log de la aplicación
		 * @param nombre - El nombre del tipo de bebida
		 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
		 */
		public long eliminarPersonanatProductoPorNombre (String nombre) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long resp = sqlPersonanatProducto.eliminarPersonanatProductoPorNombre(pm, nombre);
				tx.commit();
				return resp;
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return -1;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que elimina, de manera transaccional, una tupla en la tabla ProductoSucursal, dado el identificador del tipo de bebida
		 * Adiciona entradas al log de la aplicación
		 * @param idPersonanatProducto - El identificador del tipo de bebida
		 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
		 */
		public long eliminarPersonanatProductoPorId (long idPersonanatProducto) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long resp = sqlPersonanatProducto.eliminarPersonanatProductoPorId(pm, idPersonanatProducto);
				tx.commit();
				return resp;
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return -1;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla ProductoSucursal
		 * @return La lista de objetos ProductoSucursal, construidos con base en las tuplas de la tabla PersonanatProducto
		 */
		public List<ProductoSucursal> darTiposBebida ()
		{
			return sqlPersonanatProducto.darTiposBebida (pmf.getPersistenceManager());
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla ProductoSucursal que tienen el nombre dado
		 * @param nombre - El nombre del tipo de bebida
		 * @return La lista de objetos ProductoSucursal, construidos con base en las tuplas de la tabla PersonanatProducto
		 */
		public List<ProductoSucursal> darPersonanatProductoPorNombre (String nombre)
		{
			return sqlPersonanatProducto.darTiposBebidaPorNombre (pmf.getPersistenceManager(), nombre);
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla ProductoSucursal con un identificador dado
		 * @param idPersonanatProducto - El identificador del tipo de bebida
		 * @return El objeto ProductoSucursal, construido con base en las tuplas de la tabla PersonanatProducto con el identificador dado
		 */
		public ProductoSucursal darPersonanatProductoPorId (long idPersonanatProducto)
		{
			return sqlPersonanatProducto.darPersonanatProductoPorId (pmf.getPersistenceManager(), idPersonanatProducto);
		}
	
		/* ****************************************************************
		 * 			Métodos para manejar los PRODUCTOS
		 *****************************************************************/
	
		/**
		 * Método que inserta, de manera transaccional, una tupla en la tabla CIUDAD
		 * Adiciona entradas al log de la aplicación
		 * @param nombre - El nombre del bebedor
		 * @return El objeto CIUDAD adicionado. null si ocurre alguna Excepción
		 */
		public Producto adicionarProducto(String codigo_barras, String nombre, String marca, String categoria, int precio_unitario, int precio_medida, String presentacion, int cantidad_presentacion, String unidad_medida, String especificacion_empacado, long idBodega, long idEstante, long idProveedor) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long tuplasInsertadas = sqlProducto.adicionarProducto(pmf.getPersistenceManager(), codigo_barras, nombre, marca, categoria, precio_unitario, precio_medida, presentacion, cantidad_presentacion, unidad_medida, especificacion_empacado, idBodega, idEstante, idProveedor);
				tx.commit();
	
				log.trace ("Inserción de producto: " + codigo_barras + ": " + tuplasInsertadas + " tuplas insertadas");
	
				return new Producto (codigo_barras, nombre, marca, categoria, precio_unitario, precio_medida, presentacion, cantidad_presentacion, unidad_medida, especificacion_empacado, idBodega, idEstante, idProveedor);
			}
			catch (Exception e)
			{
				//	        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return null;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que elimina, de manera transaccional, una tupla en la tabla CIUDAD, dado el identificador del ciudad
		 * Adiciona entradas al log de la aplicación
		 * @param idProducto - El identificador del ciudad
		 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
		 */
		public long eliminarProductoPorCodBarras (String codBarras) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long resp = sqlProducto.eliminarProductoPorCodBarras (pm, codBarras);
				tx.commit();
				return resp;
			}
			catch (Exception e)
			{
				//	        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return -1;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla CIUDAD que tienen el nombre dado
		 * @param nombreCiudad - El nombre del ciudad
		 * @return La lista de objetos CIUDAD, construidos con base en las tuplas de la tabla CIUDAD
		 */
		public List<Producto> darProductosPorBodega (long idBodega) 
		{
			return sqlProducto.darProductosPorBodega (pmf.getPersistenceManager(), idBodega);
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla CIUDAD que tienen el nombre dado
		 * @param nombreCiudad - El nombre del ciudad
		 * @return La lista de objetos CIUDAD, construidos con base en las tuplas de la tabla CIUDAD
		 */
		public List<Producto> darProductosPorEstante (long idEstante) 
		{
			return sqlProducto.darProductosPorEstante (pmf.getPersistenceManager(), idEstante);
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla CIUDAD que tienen el nombre dado
		 * @param nombreCiudad - El nombre del ciudad
		 * @return La lista de objetos CIUDAD, construidos con base en las tuplas de la tabla CIUDAD
		 */
		public List<Producto> darProductosPorProveedor (long idProveedor) 
		{
			return sqlProducto.darProductosPorEstante (pmf.getPersistenceManager(), idProveedor);
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla BEBEDOR que tienen el identificador dado
		 * @param idBebedor - El identificador del bebedor
		 * @return El objeto BEBEDOR, construido con base en la tuplas de la tabla BEBEDOR, que tiene el identificador dado
		 */
		public Producto darProductoPorCodBarras (String codBarras) 
		{
			return (Producto) sqlProducto.darProductoPorCodBarras (pmf.getPersistenceManager(), codBarras);
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla BEBEDOR
		 * @return La lista de objetos BEBEDOR, construidos con base en las tuplas de la tabla BEBEDOR
		 */
		public List<Producto> darProductos ()
		{
			return sqlProducto.darProductos (pmf.getPersistenceManager());
		}
		
		/* ****************************************************************
		 * 			Métodos para manejar los PRODUCTO SUCURSAL
		 *****************************************************************/
	
		/**
		 * Método que inserta, de manera transaccional, una tupla en la tabla CIUDAD
		 * Adiciona entradas al log de la aplicación
		 * @param codProducto - El nombre del bebedor
		 * @return El objeto CIUDAD adicionado. null si ocurre alguna Excepción
		 */
		public ProductoSucursal adicionarProductoSucursal(long idSucursal, String codProducto) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long tuplasInsertadas = sqlProductoSucursal.adicionarProductoSucursal(pmf.getPersistenceManager(), idSucursal, codProducto);
				tx.commit();
	
				log.trace ("Inserción de producto: " + idSucursal + ": " + tuplasInsertadas + " tuplas insertadas");
	
				return new ProductoSucursal(idSucursal, codProducto);
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return null;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que elimina, de manera transaccional, una tupla en la tabla CIUDAD, dado el identificador del ciudad
		 * Adiciona entradas al log de la aplicación
		 * @param idProducto - El identificador del ciudad
		 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
		 */
		public long eliminarProductoSucursal(long idSucursal, String codBarras) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long resp = sqlProductoSucursal.eliminarProductoSucursal(pm,idSucursal, codBarras);
				tx.commit();
				return resp;
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return -1;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		public List<ProductoSucursal> darProductosSucursales () 
		{
			return sqlProductoSucursal.darProductoSucursal(pmf.getPersistenceManager());
		}
		
		/* ****************************************************************
		 * 			Métodos para manejar las PROOCIONES
		 *****************************************************************/
	
		/**
		 * Método que inserta, de manera transaccional, una tupla en la tabla Bebida
		 * Adiciona entradas al log de la aplicación
		 * @param nombre - El nombre de la bebida
		 * @param idPersonanatProducto - El identificador del tipo de bebida (Debe existir en la tabla ProductoSucursal)
		 * @param gradoAlcohol - El grado de alcohol de la bebida (mayor que 0)
		 * @return El objeto Bebida adicionado. null si ocurre alguna Excepción
		 */
		public Promocion adicionarPromo(Timestamp tiempo_oferta) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();            
				long idPromo = nextval ();
				long tuplasInsertadas = sqlPromocion.adicionarPromo(pm, idPromo, tiempo_oferta);
				tx.commit();
	
				log.trace ("Inserción promoción: " + idPromo + ": " + tuplasInsertadas + " tuplas insertadas");
				return new Promocion (idPromo, tiempo_oferta);
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return null;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que elimina, de manera transaccional, una tupla en la tabla Bebida, dado el identificador de la bebida
		 * Adiciona entradas al log de la aplicación
		 * @param idPromo - El identificador de la bebida
		 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
		 */
		public long eliminarPromoPorId (long idPromo) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long resp = sqlPromocion.eliminarPromoPorId (pm, idPromo);
				tx.commit();
	
				return resp;
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return -1;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla Promocion
		 * @return La lista de objetos Bebida, construidos con base en las tuplas de la tabla PROMOCION
		 */
		public List<Promocion> darPromos ()
		{
			return sqlPromocion.darPromo (pmf.getPersistenceManager());
		}
	
		/* ****************************************************************
		 * 			Métodos para manejar los PROVEEDORES
		 *****************************************************************/
		
		/**
		 * Método que inserta, de manera transaccional, una tupla en la tabla EMPRESA
		 * Adiciona entradas al log de la aplicación
		 * @param nombre - El nombre de la empresa
		 * @return El objeto Empresa adicionado. null si ocurre alguna Excepción
		 */
		public Proveedor adicionarProveedor(long nit, String nombre, long idSucursal) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long tuplasInsertadas = sqlProveedor.adicionarProveedor(pm, nit, nombre, idSucursal);
				tx.commit();
	
				log.trace ("Inserción de Proveedor: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
	
				return new Proveedor (nit, nombre, idSucursal);
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return null;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que elimina, de manera transaccional, una tupla en la tabla EMPRESA, dado el nombre de la empresa
		 * Adiciona entradas al log de la aplicación
		 * @param nombreBar - El nombre del bar
		 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
		 */
		public long eliminarProveedorPorNombre (String nombreProveedor) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long resp = sqlProveedor.eliminarProveedorPorNombre(pm, nombreProveedor);
				tx.commit();
	
				return resp;
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return -1;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que elimina, de manera transaccional, una tupla en la tabla EMPRESA, dado el nit de la empresa
		 * Adiciona entradas al log de la aplicación
		 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
		 */
		public long eliminarProveedorPorNit (long nit) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long resp = sqlProveedor.eliminarProveedorPorId (pm, nit);
				tx.commit();
	
				return resp;
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return -1;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla BAR
		 * @return La lista de objetos BAR, construidos con base en las tuplas de la tabla BAR
		 */
		public List<Proveedor> darProveedores ()
		{
			return sqlProveedor.darProveedores (pmf.getPersistenceManager());
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla BAR que tienen el nombre dado
		 * @param nombreBar - El nombre del bar
		 * @return La lista de objetos BAR, construidos con base en las tuplas de la tabla BAR
		 */
		public List<Proveedor> darProveedoresPorNombre (String nombreProveedor)
		{
			return sqlProveedor.darProveedoresPorNombre (pmf.getPersistenceManager(), nombreProveedor);
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla BAR que tienen el identificador dado
		 * @param idBar - El identificador del bar
		 * @return El objeto BAR, construido con base en la tuplas de la tabla BAR, que tiene el identificador dado
		 */
		public Proveedor darProveedorPorNit (long nit)
		{
			return sqlProveedor.darProveedorPorId (pmf.getPersistenceManager(), nit);
		}
	
		/* ****************************************************************
		 * 			Métodos para manejar las SUCURSALES
		 *****************************************************************/
		
		/**
		 * Método que inserta, de manera transaccional, una tupla en la tabla CIUDAD
		 * Adiciona entradas al log de la aplicación
		 * @param nombre - El nombre del bebedor
		 * @return El objeto CIUDAD adicionado. null si ocurre alguna Excepción
		 */
		public Sucursal adicionarSucursal(String local_ventas, String segmentacion_mercado, String productos_ofrecidos, String tamanio_instalacion, long idCiudad, long idSupermercado) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long idSucursal = nextval ();
				long tuplasInsertadas = sqlSucursal.adicionarSucursal(pmf.getPersistenceManager(), idSucursal, local_ventas, segmentacion_mercado, productos_ofrecidos, tamanio_instalacion, idCiudad, idSupermercado);
				tx.commit();
	
				log.trace ("Inserción de sucursal: " + idSucursal + ": " + tuplasInsertadas + " tuplas insertadas");
	
				return new Sucursal (idSucursal, local_ventas, segmentacion_mercado, productos_ofrecidos, tamanio_instalacion, idCiudad, idSupermercado);
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return null;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que elimina, de manera transaccional, una tupla en la tabla CIUDAD, dado el identificador del ciudad
		 * Adiciona entradas al log de la aplicación
		 * @param idSucursal - El identificador del ciudad
		 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
		 */
		public long eliminarSucursalPorId (long idSucursal) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long resp = sqlSucursal.eliminarSucursalPorId (pm, idSucursal);
				tx.commit();
				return resp;
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return -1;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla CIUDAD que tienen el nombre dado
		 * @param nombreCiudad - El nombre del ciudad
		 * @return La lista de objetos CIUDAD, construidos con base en las tuplas de la tabla CIUDAD
		 */
		public List<Sucursal> darSucursalesPorCiudad (long idCiudad) 
		{
			return sqlSucursal.darSucursalesPorCiudad (pmf.getPersistenceManager(), idCiudad);
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla CIUDAD que tienen el nombre dado
		 * @param nombreCiudad - El nombre del ciudad
		 * @return La lista de objetos CIUDAD, construidos con base en las tuplas de la tabla CIUDAD
		 */
		public List<Sucursal> darSucursalesPorSupermercado (long idSupermercado) 
		{
			return sqlSucursal.darSucursalesPorSupermercado (pmf.getPersistenceManager(), idSupermercado);
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla BEBEDOR que tienen el identificador dado
		 * @param idBebedor - El identificador del bebedor
		 * @return El objeto BEBEDOR, construido con base en la tuplas de la tabla BEBEDOR, que tiene el identificador dado
		 */
		public Sucursal darSucursalPorId (long idSucursal) 
		{
			return (Sucursal) sqlSucursal.darSucursalPorId (pmf.getPersistenceManager(), idSucursal);
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla BEBEDOR
		 * @return La lista de objetos BEBEDOR, construidos con base en las tuplas de la tabla BEBEDOR
		 */
		public List<Sucursal> darSucursales ()
		{
			return sqlSucursal.darSucursales (pmf.getPersistenceManager());
		}
	
		/* ****************************************************************
		 * 			Métodos para manejar la relación SUPERMERCADO
		 *****************************************************************/
	
		/**
		 * Método que inserta, de manera transaccional, una tupla en la tabla Supermercado
		 * Adiciona entradas al log de la aplicación
		 * @param id - El identificador del bebedor - Debe haber un bebedor con ese identificador
		 * @param idBar - El identificador del bar - Debe haber un bar con ese identificador
		 * @param fecha - La fecha en que se realizó la visita
		 * @param nombre - El hororio en que se sirve (DIURNO, NOCTURNO, TODOS)
		 * @return Un objeto Supermercado con la información dada. Null si ocurre alguna Excepción
		 */	
		public Supermercado adicionarSupermercado (long id, String nombre) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long tuplasInsertadas = sqlSupermercado.adicionarSupermercado(pm, id, nombre);
				tx.commit();
	
				log.trace ("Inserción de Bodega: [" + id + "]. " + tuplasInsertadas + " tuplas insertadas");
	
				return new Supermercado (id, nombre);
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return null;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
	
		/**
		 * Método que elimina, de manera transaccional, una tupla en la tabla Supermercado, dados los identificadores de bebedor y bar
		 * @param id
		 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
		 */
		public long eliminarSupermercadoPorId (long id) 
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long resp = sqlSupermercado.eliminarSupermercadoPorId(pm, id);
				tx.commit();
	
				return resp;
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return -1;
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
		}
	
		/**
		 * Método que consulta todas las tuplas en la tabla Supermercado
		 * @return La lista de objetos Supermercado, construidos con base en las tuplas de la tabla Supermercado
		 */
		public List<Supermercado> darSupermercado ()
		{
			return sqlSupermercado.darSupermercados (pmf.getPersistenceManager());
		}	
	
		/**
		 * Elimina todas las tuplas de todas las tablas de la base de datos de Parranderos
		 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
		 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas Bodega, NivelDeReorden, Supermercado, BEBIDA,
		 * PersonanatProducto, BEBEDOR y BAR, respectivamente
		 */
		public long [] limpiarSuperAndes ()
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			try
			{
				tx.begin();
				long [] resp = sqlUtil.limpiarSuperAndes(pm);
				tx.commit ();
				log.info ("Borrada la base de datos");
				return resp;
			}
			catch (Exception e)
			{
				//        	e.printStackTrace();
				log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
				return new long[] {-1, -1, -1, -1, -1, -1, -1};
			}
			finally
			{
				if (tx.isActive())
				{
					tx.rollback();
				}
				pm.close();
			}
	
		}
	
	}
