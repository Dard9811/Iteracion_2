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

package uniandes.isis2304.superAndes.negocio;

import java.sql.Timestamp;

/**
 * Clase para modelar la relación SIRVEN del negocio de los Parranderos:
 * Cada objeto de esta clase representa el hecho que un SuperMercado sirve una sucursal y viceversa.
 * Se modela mediante los identificadores del SuperMercado y de la sucursal respectivamente
 * Debe existir un SuperMercado con el identificador dado
 * Debe existir una sucursal con el identificador dado 
 * Adicionalmente contiene el cantidadMin (DIURNO, NOCTURNO, TODOS) en el cual el SuperMercado sirve la sucursal
 * 
 * @author Germán Bravo
 */
public class OrdenDePedido implements VOOrdenDePedido
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador del SuperMercado que sirve la sucursal
	 */
	private long idSupermercado;
	
	/**
	 * El identificador de la sucursal que es servida en el SuperMercado
	 */
	private long idSucursal;
	
	/**
	 * El cantidadMin en que sirve la sucursal en el SuperMercado (DIURNO, NOCTURNO, TODOS)
	 */
	private String nomProducto;
	
	private long cantidad;
	
	private Timestamp fechaEntrega;
	
	private String estado;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public OrdenDePedido () 
	{
		this.idSupermercado = 0;
		this.idSucursal = 0;
		this.setNomProducto("");
		this.setCantidad(0);
		this.setFechaEntrega(new Timestamp(0));
		this.setEstado("");
	}

	/**
	 * Constructor con valores
	 * @param idSupermercado - El identificador del SuperMercado. Debe exixtir un SuperMercado con dicho identificador
	 * @param idSucursal - El identificador de la sucursal. Debe existir una sucursal con dicho identificador
	 * @param nomProducto - El cantidadMin en el que el SuperMercado sirve la sucursal (DIURNO, NOCTURNO, TODOS)
	 */
	public OrdenDePedido (long idSupermercado, long idsucursal, String nomProducto, long cantidad, Timestamp fechaEntrega, String estado) 
	{
		this.idSupermercado = idSupermercado;
		this.idSucursal = idsucursal;
		this.setNomProducto(nomProducto);
		this.setCantidad(cantidad);
		this.setFechaEntrega(fechaEntrega);
		this.setEstado(estado);
	}

	/**
	 * @return El idSupermercado
	 */
	public long getIdSuperMercado() 
	{
		return idSupermercado;
	}

	/**
	 * @param idSupermercado - El nuevo identificador de SuperMercado. Debe existir un SuperMercado con dicho identificador
	 */
	public void setIdSuperMercado(long idSuperMercado) 
	{
		this.idSupermercado = idSuperMercado;
	}

	/**
	 * @return El idSucursal
	 */
	public long getIdSucursal() 
	{
		return idSucursal;
	}

	/**
	 * @param idSucursal - El nuevo identificador de sucursal. Debe existir una sucursal con dicho identificador
	 */
	public void setIdSucursal(long idsucursal) 
	{
		this.idSucursal = idsucursal;
	}

	public String getNomProducto() {
		return nomProducto;
	}

	public void setNomProducto(String nomProducto) {
		this.nomProducto = nomProducto;
	}

	public long getCantidad() {
		return cantidad;
	}

	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}

	public Timestamp getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Timestamp fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString() 
	{
		return "NivelDeReorden [idSupermercado= " + idSupermercado + ", idSucursal= " + idSucursal + ", nomProducto= " + nomProducto + ", cantidad= " + cantidad + ", fechaEntrega= " + fechaEntrega + ", estado= " + estado + " ]";
	}
}
