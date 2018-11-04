/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: SuperAndes Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.superAndes.negocio;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import com.google.gson.JsonObject;

import uniandes.isis2304.superAndes.persistencia.PersistenciaSuperAndes;

/**
 * Clase principal del negocio
 * Sarisface todos los requerimientos funcionales del negocio
 *
 * @author Germán Bravo
 */
public class SuperAndes 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(SuperAndes.class.getName());

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaSuperAndes pp;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public SuperAndes ()
	{
		pp = PersistenciaSuperAndes.getInstance ();
	}

	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public SuperAndes (JsonObject tableConfig)
	{
		pp = PersistenciaSuperAndes.getInstance (tableConfig);
	}

	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pp.cerrarUnidadPersistencia ();
	}

	/* ****************************************************************
	 * 			Métodos para manejar las BODEGA
	 *****************************************************************/

	public Bodega adicionarBodega(long espacio, long idSucursal, long cantidadMin)
	{
		log.info ("Adicionando bodega: " + espacio);
		Bodega bodega = pp.adicionarBodega(espacio, idSucursal, cantidadMin);		
		log.info ("Adicionando bodega: " + bodega);
		return bodega;
	}

	public List<Bodega> darBodegas ()
	{
		log.info ("Consultando BODEGAS");
		List<Bodega> bodega = pp.darBodega();	
		log.info ("Consultando BODEGA: " + bodega.size() + " existentes");
		return bodega;
	}

	public List<VOBodega> darVOBodega()
	{
		log.info ("Generando los VO de BODEGAL");        
		List<VOBodega> voBodega = new LinkedList<VOBodega> ();
		for (Bodega tb : pp.darBodega())
		{
			voBodega.add (tb);
		}
		log.info ("Generando los VO de BODEGA: " + voBodega.size() + " existentes");
		return voBodega;
	}

	public long eliminarBodega(long id)
	{
		log.info ("Eliminando bodega por id: " + id);
		long resp = pp.eliminarBodega(id);
		log.info ("Eliminando bodega por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	/* ****************************************************************
	 * 			Métodos para manejar la BODEGA PRODUCTO
	 *****************************************************************/

	public BodegaProducto adicionarBodegaProducto(String codProducto, long cantidad)
	{
		log.info ("Adicionando bodega producto: " + cantidad);
		BodegaProducto bodegaP = pp.adicionarBodegaProducto(codProducto, cantidad);		
		log.info ("Adicionando bodega producto: " + bodegaP);
		return bodegaP;
	}

	public List<BodegaProducto> darBodegasProductos()
	{
		log.info ("Consultando BODEGAS PRODUCTO");
		List<BodegaProducto> bodegaP = pp.darBodegaProducto();	
		log.info ("Consultando BODEGA PRODUCTO: " + bodegaP.size() + " existentes");
		return bodegaP;
	}

	public List<VOBodegaProducto> darVOBodegaProducto()
	{
		log.info ("Generando los VO de BODEGA PRODUCTO");        
		List<VOBodegaProducto> voBodegaP = new LinkedList<VOBodegaProducto> ();
		for (BodegaProducto tb : pp.darBodegaProducto())
		{
			voBodegaP.add(tb);
		}
		log.info ("Generando los VO de BODEGA PRODUCTO: " + voBodegaP.size() + " existentes");
		return voBodegaP;
	}

	public long eliminarBodegaProdcuto(long id)
	{
		log.info ("Eliminando bodega producto por id: " + id );
		long resp = pp.eliminarBodegaProducto(id);
		log.info ("Eliminando bodega producto por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	/* ****************************************************************
	 * 			Métodos para manejar las CIUDADADES
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente un ciudad 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del ciudad
	 * @return El objeto CIUDAD adicionado. null si ocurre alguna Excepción
	 */
	public Cuidad adicionarCiudad (String nombre, String direccion)
	{
		log.info ("Adicionando ciudad: " + nombre);
		Cuidad ciudad = pp.adicionarCiudad (nombre, direccion);
		log.info ("Adicionando ciudad: " + ciudad);
		return ciudad;
	}

	/**
	 * Elimina un ciudad por su nombre
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del ciudad a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarCiudadPorNombre (String nombre)
	{
		log.info ("Eliminando ciudad por nombre: " + nombre);
		long resp = pp.eliminarCiudadPorNombre (nombre);
		log.info ("Eliminando ciudad por nombre: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Elimina un ciudad por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idCiudad - El identificador del ciudad a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarCiudadPorId (long idCiudad)
	{
		log.info ("Eliminando ciudad por id: " + idCiudad);
		long resp = pp.eliminarCiudadPorId (idCiudad);
		log.info ("Eliminando ciudad por Id: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Encuentra un ciudad y su información básica, según su identificador
	 * @param idCiudad - El identificador del ciudad buscado
	 * @return Un objeto Ciudad que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un ciudad con dicho identificador no existe
	 */
	public Cuidad darCiudadPorId (long idCiudad)
	{
		log.info ("Dar información de una ciudad por id: " + idCiudad);
		Cuidad ciudad = pp.darCiudadPorId (idCiudad);
		log.info ("Buscando ciudad por Id: " + ciudad != null ? ciudad : "NO EXISTE");
		return ciudad;
	}

	/**
	 * Encuentra la información básica de los ciudades, según su nombre
	 * @param nombre - El nombre de ciudad a buscar
	 * @return Una lista de Bebedores con su información básica, donde todos tienen el nombre buscado.
	 * 	La lista vacía indica que no existen ciudades con ese nombre
	 */
	public List<Cuidad> darCiudadesPorNombre (String nombre)
	{
		log.info ("Dar información de ciudades por nombre: " + nombre);
		List<Cuidad> ciudades = pp.darCiudadesPorNombre (nombre);
		log.info ("Dar información de Ciudades por nombre: " + ciudades.size() + " ciudades con ese nombre existentes");
		return ciudades;
	}

	/**
	 * Encuentra la información básica de los ciudades, según su nombre y los devuelve como VO
	 * @param nombre - El nombre de ciudad a buscar
	 * @return Una lista de Bebedores con su información básica, donde todos tienen el nombre buscado.
	 * 	La lista vacía indica que no existen ciudades con ese nombre
	 */
	public List<VOCiudad> darVOCiudadesPorNombre (String nombre)
	{
		log.info ("Generando VO de ciudades por nombre: " + nombre);
		List<VOCiudad> voCiudades = new LinkedList<VOCiudad> ();
		for (Cuidad ciu : pp.darCiudadesPorNombre (nombre))
		{
			voCiudades.add (ciu);
		}
		log.info ("Generando los VO de Ciudades: " + voCiudades.size() + " bebedores existentes");
		return voCiudades;
	}

	/**
	 * Encuentra todos los bebedores en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<Cuidad> darCiudades ()
	{
		log.info ("Listando Ciudades");
		List<Cuidad> ciudades = pp.darCiudades ();	
		log.info ("Listando Ciudades: " + ciudades.size() + " ciudades existentes");
		return ciudades;
	}

	/**
	 * Encuentra todos los bebedores en SuperAndes y los devuelve como VOBebedor
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOBebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<VOCiudad> darVOCiudades ()
	{
		log.info ("Generando los VO de Ciudades");
		List<VOCiudad> voCiudades = new LinkedList<VOCiudad> ();
		for (Cuidad ciu : pp.darCiudades ())
		{
			voCiudades.add (ciu);
		}
		log.info ("Generando los VO de Ciudades: " + voCiudades.size() + " ciudades existentes");
		return voCiudades;
	}

	/* ****************************************************************
	 * 			Métodos para manejar los EMPRESA
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente una empresa 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bar
	 * @return El objeto Empresa adicionado. null si ocurre alguna Excepción
	 */
	public Empresa adicionarEmpresa (long nit, String nombre, String correo)
	{
		log.info ("Adicionando empresa: " + nombre);
		Empresa emp = pp.adicionarEmpresa (nit, nombre, correo);
		log.info ("Adicionando empresa: " + emp);
		return emp;
	}

	/**
	 * Elimina una empresa por su nombre
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre de la empresa a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarEmpresaPorNombre (String nombre)
	{
		log.info ("Eliminando empresa por nombre: " + nombre);
		long resp = pp.eliminarEmpresaPorNombre (nombre);
		log.info ("Eliminando empresa: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Elimina un bebedor por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param nit - El identificador del empresa a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarEmpresaPorNit (long nit)
	{
		log.info ("Eliminando empresa por id: " + nit);
		long resp = pp.eliminarEmpresaPorNit (nit);
		log.info ("Eliminando empresa: " + resp);
		return resp;
	}

	/**
	 * Encuentra todos los empresaes en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bar con todos las empresaes que conoce la aplicación, llenos con su información básica
	 */
	public List<Empresa> darEmpresas ()
	{
		log.info ("Listando Empresas");
		List<Empresa> empresas = pp.darEmpresas ();	
		log.info ("Listando Empresas: " + empresas.size() + " empresas existentes");
		return empresas;
	}

	/**
	 * Encuentra todos los empresaes en SuperAndes y los devuelce como VO
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bar con todos las empresaes que conoce la aplicación, llenos con su información básica
	 */
	public List<VOEmpresa> darVOEmpresas ()
	{
		log.info ("Generando los VO de Empresas");
		List<VOEmpresa> voEmpresas = new LinkedList<VOEmpresa> ();
		for (Empresa empresa: pp.darEmpresas ())
		{
			voEmpresas.add (empresa);
		}
		log.info ("Generando los VO de Empresas: " + voEmpresas.size () + " empresas existentes");
		return voEmpresas;
	}

	/* ****************************************************************
	 * 			Métodos para manejar los ESTANTE
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente un ciudad 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del ciudad
	 * @return El objeto CIUDAD adicionado. null si ocurre alguna Excepción
	 */
	public Estante adicionarEstante (long espacio, long idBodega)
	{
		log.info ("Adicionando estante: " + idBodega);
		Estante estante = pp.adicionarEstante (espacio, idBodega);
		log.info ("Adicionando estante: " + estante);
		return estante;
	}

	/**
	 * Elimina un ciudad por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idCiudad - El identificador del ciudad a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarEstantePorId (long idEstante)
	{
		log.info ("Eliminando Estante por id: " + idEstante);
		long resp = pp.eliminarCiudadPorId (idEstante);
		log.info ("Eliminando Estante por Id: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Encuentra un ciudad y su información básica, según su identificador
	 * @param idCiudad - El identificador del ciudad buscado
	 * @return Un objeto Ciudad que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un ciudad con dicho identificador no existe
	 */
	public Estante darEstantePorId (long idEstante)
	{
		log.info ("Dar información de una Estante por id: " + idEstante);
		Estante estante = pp.darEstantePorId (idEstante);
		log.info ("Buscando Estante por Id: " + estante != null ? estante : "NO EXISTE");
		return estante;
	}

	/**
	 * Encuentra todos los bebedores en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<Estante> darEstantes ()
	{
		log.info ("Listando Estante");
		List<Estante> estantes = pp.darEstantes();	
		log.info ("Listando Estante: " + estantes.size() + " Estantes existentes");
		return estantes;
	}

	/**
	 * Encuentra todos los bebedores en SuperAndes y los devuelve como VOBebedor
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOBebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<VOEstante> darVOEstantes ()
	{
		log.info ("Generando los VO de Estantes");
		List<VOEstante> voEstantes = new LinkedList<VOEstante> ();
		for (Estante est : pp.darEstantes ())
		{
			voEstantes.add (est);
		}
		log.info ("Generando los VO de Ciudades: " + voEstantes.size() + " ciudades existentes");
		return voEstantes;
	}

	/* ****************************************************************
	 * 			Métodos para manejar los PERSONA NAT
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente una empresa 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bar
	 * @return El objeto Empresa adicionado. null si ocurre alguna Excepción
	 */
	public PersonaNat adicionarPersonaNat (long num_doc, String tipo_doc, String nombre, String correo)
	{
		log.info ("Adicionando persona natural: " + nombre);
		PersonaNat emp = pp.adicionarPersonaNat (num_doc, tipo_doc, nombre, correo);
		log.info ("Adicionando persona natural: " + emp);
		return emp;
	}

	/**
	 * Elimina una empresa por su nombre
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre de la empresa a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarPersonaNatPorNombre (String nombre)
	{
		log.info ("Eliminando persona natural por nombre: " + nombre);
		long resp = pp.eliminarPersonaNatPorNombre (nombre);
		log.info ("Eliminando persona natural: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Elimina un bebedor por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param nit - El identificador del empresa a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarPersonaNatPorDocumento (long num_doc, String tipo_doc)
	{
		log.info ("Eliminando persona natural por id: " + tipo_doc + num_doc);
		long resp = pp.eliminarPersonaNatPorDocumento (num_doc, tipo_doc);
		log.info ("Eliminando persona natural: " + resp);
		return resp;
	}

	/**
	 * Encuentra todos los empresaes en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bar con todos las empresaes que conoce la aplicación, llenos con su información básica
	 */
	public List<PersonaNat> darPersonaNats ()
	{
		log.info ("Listando Personas naturales");
		List<PersonaNat> personas = pp.darPersonaNats ();	
		log.info ("Listando Personas naturales: " + personas.size() + " personas naturales existentes");
		return personas;
	}

	/**
	 * Encuentra todos los empresaes en SuperAndes y los devuelce como VO
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bar con todos las empresaes que conoce la aplicación, llenos con su información básica
	 */
	public List<VOPersonaNat> darVOPersonaNats ()
	{
		log.info ("Generando los VO de PersonaNats");
		List<VOPersonaNat> voPersonaNats = new LinkedList<VOPersonaNat> ();
		for (PersonaNat per: pp.darPersonaNats ())
		{
			voPersonaNats.add (per);
		}
		log.info ("Generando los VO de PersonaNats: " + voPersonaNats.size () + " personas existentes");
		return voPersonaNats;
	}

	/* ****************************************************************
	 * 			Métodos para manejar los PRODUCTOS
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente un ciudad 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del ciudad
	 * @return El objeto CIUDAD adicionado. null si ocurre alguna Excepción
	 */
	public Producto adicionarProducto (String codigo_barras, String nombre, String marca, String categoria, long precio_unitario, long precio_medida, String presentacion, long cantidad_presentacion, String unidad_medida, String especificacion_empacado, long idProveedor)
	{
		log.info ("Adicionando producto: " + codigo_barras);
		Producto producto = pp.adicionarProducto (codigo_barras, nombre, marca, categoria, precio_unitario, precio_medida, presentacion, cantidad_presentacion, unidad_medida, especificacion_empacado, idProveedor);
		log.info ("Adicionando producto: " + producto);
		return producto;
	}

	/**
	 * Elimina un ciudad por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param codBarras - El identificador del ciudad a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarProductoPorCodBarras (String codBarras)
	{
		log.info ("Eliminando producto por id: " + codBarras);
		long resp = pp.eliminarProductoPorCodBarras (codBarras);
		log.info ("Eliminando producto por Id: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Encuentra un ciudad y su información básica, según su identificador
	 * @param codBarras - El identificador del ciudad buscado
	 * @return Un objeto Ciudad que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un ciudad con dicho identificador no existe
	 */
	public Producto darProductoPorCodBarras (String codBarras)
	{
		log.info ("Dar información de una producto por id: " + codBarras);
		Producto producto = pp.darProductoPorCodBarras (codBarras);
		log.info ("Buscando producto por Id: " + producto != null ? producto : "NO EXISTE");
		return producto;
	}

	/**
	 * Encuentra la información básica de los ciudades, según su nombre
	 * @param nombre - El nombre de ciudad a buscar
	 * @return Una lista de Bebedores con su información básica, donde todos tienen el nombre buscado.
	 * 	La lista vacía indica que no existen ciudades con ese nombre
	 */
	public List<Producto> darProductosPorBodega (long idBodega)
	{
		log.info ("Dar información de productos por ciudad: " + idBodega);
		List<Producto> productos = pp.darProductosPorBodega (idBodega);
		log.info ("Dar información de productos por ciudad: " + productos.size() + " productos de esa bodega existentes");
		return productos;
	}

	/**
	 * Encuentra la información básica de los ciudades, según su nombre
	 * @param nombre - El nombre de ciudad a buscar
	 * @return Una lista de Bebedores con su información básica, donde todos tienen el nombre buscado.
	 * 	La lista vacía indica que no existen ciudades con ese nombre
	 */
	public List<Producto> darProductoesPorEstante (long idEstante)
	{
		log.info ("Dar información de productoes por Estante: " + idEstante);
		List<Producto> productos = pp.darProductosPorEstante (idEstante);
		log.info ("Dar información de productoes por Estante: " + productos.size() + " productos de ese Estante existentes");
		return productos;
	}

	/**
	 * Encuentra la información básica de los ciudades, según su nombre
	 * @param nombre - El nombre de ciudad a buscar
	 * @return Una lista de Bebedores con su información básica, donde todos tienen el nombre buscado.
	 * 	La lista vacía indica que no existen ciudades con ese nombre
	 */
	public List<Producto> darProductoesPorProveedor (long idProveedor)
	{
		log.info ("Dar información de productoes por Proveedor: " + idProveedor);
		List<Producto> productos = pp.darProductosPorProveedor (idProveedor);
		log.info ("Dar información de productoes por Proveedor: " + productos.size() + " productos de ese Proveedor existentes");
		return productos;
	}

	/**
	 * Encuentra todos los bebedores en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<Producto> darProductos ()
	{
		log.info ("Listando Productos");
		List<Producto> productos = pp.darProductos ();	
		log.info ("Listando Productos: " + productos.size() + " productos existentes");
		return productos;
	}

	/**
	 * Encuentra todos los bebedores en SuperAndes y los devuelve como VOBebedor
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOBebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<VOProducto> darVOProductos ()
	{
		log.info ("Generando los VO de Productos");
		List<VOProducto> voProductos = new LinkedList<VOProducto> ();
		for (Producto prod : pp.darProductos ())
		{
			voProductos.add (prod);
		}
		log.info ("Generando los VO de Productos: " + voProductos.size() + " productos existentes");
		return voProductos;
	}

	/* ****************************************************************
	 * 			Métodos para manejar las PROMOCIONES
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente una bebida 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre la bebida
	 * @param idTipoBebida - El identificador del producto sucursal de la bebida - Debe existir un TIPOBEBIDA con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la bebida (Mayor que 0)
	 * @return El objeto Bebida adicionado. null si ocurre alguna Excepción
	 */
	public Promocion adicionarPromo (Timestamp tiempo_oferta)
	{
		log.info ("Adicionando promo ");
		Promocion promo = pp.adicionarPromo (tiempo_oferta);
		log.info ("Adicionando promo: " + promo);
		return promo;
	}

	/**
	 * Elimina una bebida por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idPromo - El identificador de la bebida a eliminar
	 * @return El número de tuplas eliminadas (1 o 0)
	 */
	public long eliminarPromoPorId (long idPromo)
	{
		log.info ("Eliminando promo por id: " + idPromo);
		long resp = pp.eliminarPromoPorId (idPromo);
		log.info ("Eliminando promo por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Encuentra todas las bebida en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bebida con todos las bebidas que conoce la aplicación, llenos con su información básica
	 */
	public List<Promocion> darPromos ()
	{
		log.info ("Consultando promociones");
		List<Promocion> promos = pp.darPromos ();	
		log.info ("Consultando promociones: " + promos.size() + " promociones existentes");
		return promos;
	}

	/**
	 * Encuentra todos los PRODUCTO SUCURSAL en SuperAndes y los devuelve como una lista de VOProductoSucursal
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOBebida con todos las bebidas que conoce la aplicación, llenos con su información básica
	 */
	public List<VOPromocion> darVOPromociones ()
	{
		log.info ("Generando los VO de las promociones");       
		List<VOPromocion> voPromociones = new LinkedList<VOPromocion> ();
		for (Promocion promo : pp.darPromos ())
		{
			voPromociones.add (promo);
		}
		log.info ("Generando los VO de las promociones: " + voPromociones.size() + " existentes");
		return voPromociones;
	}

	/* ****************************************************************
	 * 			Métodos para manejar las PROMOCIONES-PRODUCTO
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente una bebida 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre la bebida
	 * @param idTipoBebida - El identificador del producto sucursal de la bebida - Debe existir un TIPOBEBIDA con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la bebida (Mayor que 0)
	 * @return El objeto Bebida adicionado. null si ocurre alguna Excepción
	 */
	public PromocionProducto adicionarPromoProd (long idProd, long idPromo)
	{
		log.info ("Adicionando promoProd ");
		PromocionProducto promoProd = pp.adicionarPromoProd(idProd, idPromo);
		log.info ("Adicionando promoProd: " + promoProd);
		return promoProd;
	}

	/**
	 * Elimina una bebida por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idPromo - El identificador de la bebida a eliminar
	 * @return El número de tuplas eliminadas (1 o 0)
	 */
	public long eliminarPromoProdPorId (long idProd)
	{
		log.info ("Eliminando promo por id: " + idProd);
		long resp = pp.eliminarPromoProdPorIdProducto(idProd);
		log.info ("Eliminando promo por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Encuentra todas las bebida en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bebida con todos las bebidas que conoce la aplicación, llenos con su información básica
	 */
	public List<PromocionProducto> darPromoProductos ()
	{
		log.info ("Consultando promociones");
		List<PromocionProducto> promoProds = pp.darPromoProds();	
		log.info ("Consultando promociones: " + promoProds.size() + " promociones existentes");
		return promoProds;
	}

	/**
	 * Encuentra todos los PRODUCTO SUCURSAL en SuperAndes y los devuelve como una lista de VOProductoSucursal
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOBebida con todos las bebidas que conoce la aplicación, llenos con su información básica
	 */
	public List<VOPromocionProducto> darVOPromocionesProductos ()
	{
		log.info ("Generando los VO de las promociones");       
		List<VOPromocionProducto> voPromocionesProductos = new LinkedList<VOPromocionProducto> ();
		for (PromocionProducto promoProd : pp.darPromoProds())
		{
			voPromocionesProductos.add (promoProd);
		}
		log.info ("Generando los VO de las promociones: " + voPromocionesProductos.size() + " existentes");
		return voPromocionesProductos;
	}

	/* ****************************************************************
	 * 			Métodos para manejar las PROVEEDOR
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente una empresa 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bar
	 * @return El objeto Empresa adicionado. null si ocurre alguna Excepción
	 */
	public Proveedor adicionarProveedor (long nit, String nombre, long idSucursal)
	{
		log.info ("Adicionando proveedor: " + nombre);
		Proveedor emp = pp.adicionarProveedor (nit, nombre, idSucursal);
		log.info ("Adicionando proveedor: " + emp);
		return emp;
	}

	/**
	 * Elimina una empresa por su nombre
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre de la empresa a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarProveedorPorNombre (String nombre)
	{
		log.info ("Eliminando proveedor por nombre: " + nombre);
		long resp = pp.eliminarProveedorPorNombre (nombre);
		log.info ("Eliminando proveedor: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Elimina un bebedor por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param nit - El identificador del empresa a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarProveedorPorNit (long nit)
	{
		log.info ("Eliminando proveedor por id: " + nit);
		long resp = pp.eliminarProveedorPorNit (nit);
		log.info ("Eliminando proveedor: " + resp);
		return resp;
	}

	/**
	 * Encuentra todos los empresaes en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bar con todos las empresaes que conoce la aplicación, llenos con su información básica
	 */
	public List<Proveedor> darProveedores ()
	{
		log.info ("Listando Proveedores");
		List<Proveedor> proveedores = pp.darProveedores ();	
		log.info ("Listando Proveedores: " + proveedores.size() + " proveedores existentes");
		return proveedores;
	}

	/**
	 * Encuentra todos los empresaes en SuperAndes y los devuelce como VO
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bar con todos las empresaes que conoce la aplicación, llenos con su información básica
	 */
	public List<VOProveedor> darVOProveedores ()
	{
		log.info ("Generando los VO de Proveedores");
		List<VOProveedor> voProveedores = new LinkedList<VOProveedor> ();
		for (Proveedor proveedor: pp.darProveedores ())
		{
			voProveedores.add (proveedor);
		}
		log.info ("Generando los VO de Proveedores: " + voProveedores.size () + " proveedores existentes");
		return voProveedores;
	}

	public VOProveedor darVOProveedoresPorNit (long nit)
	{
		log.info ("Generando los VO de Proveedor con nit: " + nit);
		VOProveedor voProveedor = pp.darProveedorPorNit(nit);
		log.info ("Se encontro el VO de proveedor con el nit indicado");
		return voProveedor;
	}

	/* ****************************************************************
	 * 			Métodos para manejar las SUCURSAL
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente un ciudad 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del ciudad
	 * @return El objeto CIUDAD adicionado. null si ocurre alguna Excepción
	 */
	public Sucursal adicionarSucursal (String local_ventas, String segmentacion_mercado, String tamanio_instalacion, long idCiudad, long idSupermercado)
	{
		log.info ("Adicionando sucursal: " + local_ventas);
		Sucursal sucursal = pp.adicionarSucursal (local_ventas,segmentacion_mercado, tamanio_instalacion, idCiudad, idSupermercado);
		log.info ("Adicionando sucursal: " + sucursal);
		return sucursal;
	}

	/**
	 * Elimina un ciudad por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idSucursal - El identificador del ciudad a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarSucursalPorId (long idSucursal)
	{
		log.info ("Eliminando sucursal por id: " + idSucursal);
		long resp = pp.eliminarSucursalPorId (idSucursal);
		log.info ("Eliminando sucursal por Id: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Encuentra un ciudad y su información básica, según su identificador
	 * @param idSucursal - El identificador del ciudad buscado
	 * @return Un objeto Ciudad que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un ciudad con dicho identificador no existe
	 */
	public Sucursal darSucursalPorId (long idSucursal)
	{
		log.info ("Dar información de una sucursal por id: " + idSucursal);
		Sucursal sucursal = pp.darSucursalPorId (idSucursal);
		log.info ("Buscando sucursal por Id: " + sucursal != null ? sucursal : "NO EXISTE");
		return sucursal;
	}

	/**
	 * Encuentra la información básica de los ciudades, según su nombre
	 * @param nombre - El nombre de ciudad a buscar
	 * @return Una lista de Bebedores con su información básica, donde todos tienen el nombre buscado.
	 * 	La lista vacía indica que no existen ciudades con ese nombre
	 */
	public List<Sucursal> darSucursalesPorCiudad (long idCiudad)
	{
		log.info ("Dar información de sucursales por ciudad: " + idCiudad);
		List<Sucursal> sucursales = pp.darSucursalesPorCiudad (idCiudad);
		log.info ("Dar información de sucursales por ciudad: " + sucursales.size() + " sucursales de esa ciudad existentes");
		return sucursales;
	}

	/**
	 * Encuentra la información básica de los ciudades, según su nombre
	 * @param nombre - El nombre de ciudad a buscar
	 * @return Una lista de Bebedores con su información básica, donde todos tienen el nombre buscado.
	 * 	La lista vacía indica que no existen ciudades con ese nombre
	 */
	public List<Sucursal> darSucursalesPorSupermercado (long idSupermercado)
	{
		log.info ("Dar información de sucursales por Supermercado: " + idSupermercado);
		List<Sucursal> sucursales = pp.darSucursalesPorSupermercado (idSupermercado);
		log.info ("Dar información de sucursales por Supermercado: " + sucursales.size() + " sucursales de ese Supermercado existentes");
		return sucursales;
	}

	/**
	 * Encuentra todos los bebedores en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<Sucursal> darSucursales ()
	{
		log.info ("Listando Sucursales");
		List<Sucursal> Sucursales = pp.darSucursales ();	
		log.info ("Listando Sucursales: " + Sucursales.size() + " ciudades existentes");
		return Sucursales;
	}

	/**
	 * Encuentra todos los bebedores en SuperAndes y los devuelve como VOBebedor
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOBebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<VOSucursal> darVOSucursales ()
	{
		log.info ("Generando los VO de Sucursales");
		List<VOSucursal> voSucursales = new LinkedList<VOSucursal> ();
		for (Sucursal suc : pp.darSucursales ())
		{
			voSucursales.add (suc);
		}
		log.info ("Generando los VO de Sucursales: " + voSucursales.size() + " sucursales existentes");
		return voSucursales;
	}

	/* ****************************************************************
	 * 			Métodos para manejar las VENTAS
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente un ciudad 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del ciudad
	 * @return El objeto CIUDAD adicionado. null si ocurre alguna Excepción
	 */
	public Venta adicionarVenta (long valor, Timestamp fecha)
	{
		log.info ("Adicionando venta: " + valor);
		Venta venta = pp.adicionarVenta(valor, fecha);
		log.info ("Adicionando venta: " + venta);
		return venta;
	}

	/**
	 * Elimina un ciudad por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idSucursal - El identificador del ciudad a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarVentaPorId (long id)
	{
		log.info ("Eliminando venta por id: " + id);
		long resp = pp.eliminarVentaPorId(id);
		log.info ("Eliminando venta por Id: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Encuentra todos los bebedores en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<Venta> darVentas ()
	{
		log.info ("Listando Ventas");
		List<Venta> ventas = pp.darVentas();	
		log.info ("Listando ventas: " + ventas.size() + " ventas existentes");
		return ventas;
	}

	/**
	 * Encuentra todos los bebedores en SuperAndes y los devuelve como VOBebedor
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOBebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<VOVenta> darVOVentas ()
	{
		log.info ("Generando los VO de Ventas");
		List<VOVenta> voVenta = new LinkedList<VOVenta> ();
		for (Venta venta : pp.darVentas())
		{
			voVenta.add (venta);
		}
		log.info ("Generando los VO de Ventas: " + voVenta.size() + " ventas existentes");
		return voVenta;
	}

	//Aqui voy

	/* ****************************************************************
	 * 			Métodos para manejar los RFC
	 *****************************************************************/

	public List<ReqFunCon1> darDineroRecolectado()
	{
		log.info ("Generando el RFC1");
		List<ReqFunCon1> rfc = pp.darDineroRecolectado();
		log.info ("Generando el RFC1: " + rfc.size() + " tuplas existentes");
		return rfc;
	}
	
	public List<VOReqFunCon1> darVoDineroRecolectado()
	{
		log.info ("Generando el RFC1");
		List<VOReqFunCon1> voRfc = new LinkedList<VOReqFunCon1> ();
		for (ReqFunCon1 reqFunCon1 : pp.darDineroRecolectado()) 
		{
			voRfc.add(reqFunCon1);
		}
		log.info ("Generando el RFC1: " + voRfc.size() + " tuplas existentes");
		return voRfc;
	}
	
	public List<ReqFunCon3> darIndiceDeOcupacionBod()
	{
		log.info ("Generando el RFC3");
		List<ReqFunCon3> rfc = pp.darIndiceDeOcupacionBod();
		log.info ("Generando el RFC3: " + rfc.size() + " tuplas existentes");
		return rfc;
	}
	
	public List<VOReqFunCon3> darVoIndiceDeOcupacionBod()
	{
		log.info ("Generando el RFC3");
		List<VOReqFunCon3> voRfc = new LinkedList<VOReqFunCon3> ();
		for (ReqFunCon3 reqFunCon3 : pp.darIndiceDeOcupacionBod()) 
		{
			voRfc.add(reqFunCon3);
		}
		log.info ("Generando el RFC3: " + voRfc.size() + " tuplas existentes");
		return voRfc;
	}
	
	public List<ReqFunCon3p1> darIndiceDeOcupacionEst()
	{
		log.info ("Generando el RFC3p1");
		List<ReqFunCon3p1> rfc = pp.darIndiceDeOcupacionEst();
		log.info ("Generando el RFC3p1: " + rfc.size() + " tuplas existentes");
		return rfc;
	}
	
	public List<VOReqFunCon3p1> darVoIndiceDeOcupacionEst()
	{
		log.info ("Generando el RFC3p1");
		List<VOReqFunCon3p1> voRfc = new LinkedList<VOReqFunCon3p1> ();
		for (ReqFunCon3p1 reqFunCon3p1 : pp.darIndiceDeOcupacionEst()) 
		{
			voRfc.add(reqFunCon3p1);
		}
		log.info ("Generando el RFC3p1: " + voRfc.size() + " tuplas existentes");
		return voRfc;
	}


	/* ****************************************************************
	 * 			Métodos para administración
	 *****************************************************************/

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de SuperAndes
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarSuperAndes ()
	{
		log.info ("Limpiando la BD de SuperAndes");
		long [] borrrados = pp.limpiarSuperAndes();	
		log.info ("Limpiando la BD de SuperAndes: Listo!");
		return borrrados;
	}
}
