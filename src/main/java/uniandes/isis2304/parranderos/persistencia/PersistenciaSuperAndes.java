package uniandes.isis2304.parranderos.persistencia;


import java.math.BigDecimal;
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
import uniandes.isis2304.parranderos.negocio.Cuidad;
import uniandes.isis2304.parranderos.negocio.Promocion;
import uniandes.isis2304.parranderos.negocio.Proveedor;
import uniandes.isis2304.parranderos.negocio.Empresa;
import uniandes.isis2304.parranderos.negocio.Estante;
import uniandes.isis2304.parranderos.negocio.Bodega;
import uniandes.isis2304.parranderos.negocio.PersonaNat;
import uniandes.isis2304.parranderos.negocio.Producto;
import uniandes.isis2304.parranderos.negocio.NivelDeReorden;
import uniandes.isis2304.parranderos.negocio.Sucursal;
import uniandes.isis2304.parranderos.negocio.ProductoSucursal;
import uniandes.isis2304.parranderos.negocio.Supermercado;

/**
 * Clase para el manejador de persistencia del proyecto SuperAndes
 * Traduce la información entre objetos Java y tuplas de la base de datos, en ambos sentidos
 * Sigue un patrón SINGLETON (Sólo puede haber UN objeto de esta clase) para comunicarse de manera correcta
 * con la base de datos
 * Se apoya en las clases SQLBar, SQLBebedor, SQLBebida, SQLGustan, SQLSirven, SQLTipoBebida y SQLVisitan, que son 
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
	 * Secuenciador, tipoBebida, bebida, bar, bebedor, gustan, sirven y visitan
	 */
	private List <String> tablas;
	
	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaParranderos
	 */
	private SQLUtil sqlUtil;
	
	/**
	 * Atributo para el acceso a la tabla TIPOBEBIDA de la base de datos
	 */
	private SQLTipoBebida sqlTipoBebida;
	
	/**
	 * Atributo para el acceso a la tabla PROMOCION de la base de datos
	 */
	private SQLPromocion sqlPromo;
	
	/**
	 * Atributo para el acceso a la tabla SUCURSAL de la base de datos
	 */
	private SQLSucursal sqlSucursal;

	/**
	 * Atributo para el acceso a la tabla SUCURSAL de la base de datos
	 */
	private SQLEstante sqlEstante;
	
	/**
	 * Atributo para el acceso a la tabla EMPRESA de la base de datos
	 */
	private SQLEmpresa sqlEmpresa;
	
	/**
	 * Atributo para el acceso a la tabla EMPRESA de la base de datos
	 */
	private SQLProveedor sqlProveedor;
	
	/**
	 * Atributo para el acceso a la tabla EMPRESA de la base de datos
	 */
	private SQLPersonaNat sqlPersonaNat;
	
	/**
	 * Atributo para el acceso a la tabla CIUDAD de la base de datos
	 */
	private SQLCiudad sqlCiudad;
	
	/**
	 * Atributo para el acceso a la tabla GUSTAN de la base de datos
	 */
	private SQLGustan sqlGustan;
	
	/**
	 * Atributo para el acceso a la tabla SIRVEN de la base de datos
	 */
	private SQLSirven sqlSirven;
	
	/**
	 * Atributo para el acceso a la tabla VISITAN de la base de datos
	 */
	private SQLVisitan sqlVisitan;
	
	/**
	 * Atributo para el acceso a la tabla VISITAN de la base de datos
	 */
	private SQLProducto sqlProducto;
	
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
		tablas.add ("Parranderos_sequence");
		tablas.add ("TIPOBEBIDA");
		tablas.add ("PROMOCION");
		tablas.add ("EMPRESA");
		tablas.add ("CIUDAD");
		tablas.add ("GUSTAN");
		tablas.add ("SIRVEN");
		tablas.add ("VISITAN");
		tablas.add ("SUCURSAL");
		tablas.add ("ESTANTE");
		tablas.add ("PROVEEDOR");
		tablas.add ("PERSONANAT");
		tablas.add ("PRODUCTO");
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
		sqlTipoBebida = new SQLTipoBebida(this);
		sqlPromo = new SQLPromocion(this);
		sqlEmpresa = new SQLEmpresa(this);
		sqlProveedor = new SQLProveedor(this);
		sqlCiudad = new SQLCiudad(this);
		sqlGustan = new SQLGustan(this);
		sqlSirven = new SQLSirven (this);
		sqlVisitan = new SQLVisitan(this);		
		sqlUtil = new SQLUtil(this);
		sqlSucursal = new SQLSucursal(this);
		sqlEstante = new SQLEstante(this);
		sqlPersonaNat = new SQLPersonaNat(this);
		sqlProducto = new SQLProducto(this);
	}

	/**
	 * @return La cadena de caracteres con el nombre del secuenciador de parranderos
	 */
	public String darSeqParranderos ()
	{
		return tablas.get (0);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de ProductoSucursal de parranderos
	 */
	public String darTablaTipoBebida ()
	{
		return tablas.get (1);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bebida de parranderos
	 */
	public String darTablaPromo ()
	{
		return tablas.get (2);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bar de parranderos
	 */
	public String darTablaEmpresa ()
	{
		return tablas.get (3);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bebedor de parranderos
	 */
	public String darTablaCiudad ()
	{
		return tablas.get (4);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bodega de parranderos
	 */
	public String darTablaGustan ()
	{
		return tablas.get (5);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de NivelDeReorden de parranderos
	 */
	public String darTablaSirven ()
	{
		return tablas.get (6);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Supermercado de parranderos
	 */
	public String darTablaVisitan ()
	{
		return tablas.get (7);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Supermercado de parranderos
	 */
	public String darTablaSucursal ()
	{
		return tablas.get (8);
	}
	
	public String darTablaEstante ()
	{
		return tablas.get(9);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bar de parranderos
	 */
	public String darTablaProveedor ()
	{
		return tablas.get (10);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bar de parranderos
	 */
	public String darTablaPersonaNat ()
	{
		return tablas.get (11);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bar de parranderos
	 */
	public String darTablaProducto ()
	{
		return tablas.get (12);
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
	 * 			Métodos para manejar los TIPOS DE BEBIDA
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla ProductoSucursal
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto ProductoSucursal adicionado. null si ocurre alguna Excepción
	 */
	public ProductoSucursal adicionarTipoBebida(String nombre)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idTipoBebida = nextval ();
            long tuplasInsertadas = sqlTipoBebida.adicionarTipoBebida(pm, idTipoBebida, nombre);
            tx.commit();
            
            log.trace ("Inserción de tipo de bebida: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new ProductoSucursal (idTipoBebida, nombre);
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
	public long eliminarTipoBebidaPorNombre (String nombre) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlTipoBebida.eliminarTipoBebidaPorNombre(pm, nombre);
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
	 * @param idTipoBebida - El identificador del tipo de bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarTipoBebidaPorId (long idTipoBebida) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlTipoBebida.eliminarTipoBebidaPorId(pm, idTipoBebida);
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
	 * @return La lista de objetos ProductoSucursal, construidos con base en las tuplas de la tabla TIPOBEBIDA
	 */
	public List<ProductoSucursal> darTiposBebida ()
	{
		return sqlTipoBebida.darTiposBebida (pmf.getPersistenceManager());
	}
 
	/**
	 * Método que consulta todas las tuplas en la tabla ProductoSucursal que tienen el nombre dado
	 * @param nombre - El nombre del tipo de bebida
	 * @return La lista de objetos ProductoSucursal, construidos con base en las tuplas de la tabla TIPOBEBIDA
	 */
	public List<ProductoSucursal> darTipoBebidaPorNombre (String nombre)
	{
		return sqlTipoBebida.darTiposBebidaPorNombre (pmf.getPersistenceManager(), nombre);
	}
 
	/**
	 * Método que consulta todas las tuplas en la tabla ProductoSucursal con un identificador dado
	 * @param idTipoBebida - El identificador del tipo de bebida
	 * @return El objeto ProductoSucursal, construido con base en las tuplas de la tabla TIPOBEBIDA con el identificador dado
	 */
	public ProductoSucursal darTipoBebidaPorId (long idTipoBebida)
	{
		return sqlTipoBebida.darTipoBebidaPorId (pmf.getPersistenceManager(), idTipoBebida);
	}
 
	/* ****************************************************************
	 * 			Métodos para manejar las BEBIDAS
	 *****************************************************************/
	
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla Bebida
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre de la bebida
	 * @param idTipoBebida - El identificador del tipo de bebida (Debe existir en la tabla ProductoSucursal)
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
            long tuplasInsertadas = sqlPromo.adicionarPromo(pm, idPromo, tiempo_oferta);
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
            long resp = sqlPromo.eliminarPromoPorId (pm, idPromo);
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
		return sqlPromo.darPromo (pmf.getPersistenceManager());
	}
 
	/* ****************************************************************
	 * 			Métodos para manejar los BEBEDORES
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
	 * 			Métodos para manejar la relación GUSTAN
	 *****************************************************************/
	
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla GUSTAN
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor - Debe haber un bebedor con ese identificador
	 * @param idBebida - El identificador de la bebida - Debe haber una bebida con ese identificador
	 * @return Un objeto GUSTAN con la información dada. Null si ocurre alguna Excepción
	 */
	public Bodega adicionarGustan(long idBebedor, long idBebida) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlGustan.adicionarGustan (pm, idBebedor, idBebida);
            tx.commit();

            log.trace ("Inserción de gustan: [" + idBebedor + ", " + idBebida + "]. " + tuplasInsertadas + " tuplas insertadas");

            return new Bodega (idBebedor, idBebida);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla GUSTAN, dados los identificadores de bebedor y bebida
	 * @param idBebedor - El identificador del bebedor
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarGustan(long idBebedor, long idBebida) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlGustan.eliminarGustan(pm, idBebedor, idBebida);           
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
	 * Método que consulta todas las tuplas en la tabla GUSTAN
	 * @return La lista de objetos GUSTAN, construidos con base en las tuplas de la tabla GUSTAN
	 */
	public List<Bodega> darGustan ()
	{
		return sqlGustan.darGustan (pmf.getPersistenceManager());
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
	 * 			Métodos para manejar la relación SIRVEN
	 *****************************************************************/
	
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla SIRVEN
	 * Adiciona entradas al log de la aplicación
	 * @param idBar - El identificador del bar - Debe haber un bar con ese identificador
	 * @param idBebida - El identificador de la bebida - Debe haber una bebida con ese identificador
	 * @param horario - El hororio en que se sirve (DIURNO, NOCTURNO, TODOS)
	 * @return Un objeto SIRVEN con la información dada. Null si ocurre alguna Excepción
	 */
	public NivelDeReorden adicionarSirven (long idBar, long idBebida, String horario) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlSirven.adicionarSirven (pmf.getPersistenceManager(), idBar, idBebida, horario);
    		tx.commit();

            log.trace ("Inserción de gustan: [" + idBar + ", " + idBebida + "]. " + tuplasInsertadas + " tuplas insertadas");

            return new NivelDeReorden (idBar, idBebida, horario);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla SIRVEN, dados los identificadores de bar y bebida
	 * @param idBar - El identificador del bar
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarSirven (long idBar, long idBebida) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
	        Transaction tx=pm.currentTransaction();
	        try
	        {
	            tx.begin();
	            long resp = sqlSirven.eliminarSirven (pm, idBar, idBebida);	            
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
	 * Método que consulta todas las tuplas en la tabla SIRVEN
	 * @return La lista de objetos SIRVEN, construidos con base en las tuplas de la tabla SIRVEN
	 */
	public List<NivelDeReorden> darSirven ()
	{
		return sqlSirven.darSirven (pmf.getPersistenceManager());
	}
 
	/**
	 * Método que encuentra el identificador de los bares y cuántas bebidas sirve cada uno de ellos. Si una bebida se sirve en diferentes horarios,
	 * cuenta múltiples veces
	 * @return Una lista de arreglos de 2 números. El primero corresponde al identificador del bar, el segundo corresponde al nombre del tipo de bebida
	 */
	public List<long []> darBaresYCantidadBebidasSirven ()
	{
		List<long []> resp = new LinkedList<long []> ();
		List<Object []> tuplas =  sqlSirven.darBaresYCantidadBebidasSirven (pmf.getPersistenceManager());
        for ( Object [] tupla : tuplas)
        {
			long [] datosResp = new long [2];
			
			datosResp [0] = ((BigDecimal) tupla [0]).longValue ();
			datosResp [1] = ((BigDecimal) tupla [1]).longValue ();
			resp.add (datosResp);
        }
        return resp;
	}
 
	/* ****************************************************************
	 * 			Métodos para manejar la relación VISITAN
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla VISITAN
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor - Debe haber un bebedor con ese identificador
	 * @param idBar - El identificador del bar - Debe haber un bar con ese identificador
	 * @param fecha - La fecha en que se realizó la visita
	 * @param horario - El hororio en que se sirve (DIURNO, NOCTURNO, TODOS)
	 * @return Un objeto VISITAN con la información dada. Null si ocurre alguna Excepción
	 */	
	public Supermercado adicionarVisitan (long idBebedor, long idBar, Timestamp fecha, String horario) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlVisitan.adicionarVisitan(pm, idBebedor, idBar, fecha, horario);
            tx.commit();

            log.trace ("Inserción de gustan: [" + idBebedor + ", " + idBar + "]. " + tuplasInsertadas + " tuplas insertadas");

            return new Supermercado (idBebedor, idBar, fecha, horario);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla VISITAN, dados los identificadores de bebedor y bar
	 * @param idBebedor - El identificador del bebedor
	 * @param idBar - El identificador del bar
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarVisitan (long idBebedor, long idBar) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlVisitan.eliminarVisitan(pm, idBebedor, idBar);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla VISITAN, dados el identificador del bebedor
	 * @param idBebedor - El identificador del bebedor
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarVisitanPorIdBebedor (long idBebedor) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long visitasEliminadas = sqlVisitan.eliminarVisitanPorIdBebedor (pm, idBebedor);
            tx.commit();

            return visitasEliminadas;
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla VISITAN, dados el identificador del bar
	 * @param idBar - El identificador del bar
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarVisitanPorIdBar (long idBar) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long visitasEliminadas = sqlVisitan.eliminarVisitanPorIdBar (pm, idBar);
            tx.commit();

            return visitasEliminadas;
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
	 * Método que consulta todas las tuplas en la tabla VISITAN
	 * @return La lista de objetos VISITAN, construidos con base en las tuplas de la tabla VISITAN
	 */
	public List<Supermercado> darVisitan ()
	{
		return sqlVisitan.darVisitan (pmf.getPersistenceManager());
	}	

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Parranderos
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarSuperAndes ()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long [] resp = sqlUtil.limpiarParranderos (pm);
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
