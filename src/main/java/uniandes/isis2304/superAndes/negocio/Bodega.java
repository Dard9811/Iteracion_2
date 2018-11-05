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

/**
 * Clase para modelar la relación GUSTAN del negocio de los Parranderos:
 * Cada objeto de esta clase representa el hecho que un bebedor gusta de una bebida y viceversa.
 * Se modela mediante los identificadores del bebedor y de la bebida respectivamente.
 * Debe existir un bebedor con el identificador dado
 * Debe existir una bebida con el identificador dado 
 * 
 * @author Germán Bravo
 */
public class Bodega implements VOBodega
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador de la bodega
	 */
	private long id;

	/**
	 * El espacio de la bodega
	 */
	private long espacio;
	
	private long idSucursal;
	
	private long cantidadMin;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Bodega() 
	{
		this.id = 0;
		this.espacio = 0;
		this.idSucursal = 0;
		this.cantidadMin = 0;
	}

	/**
	 * Constructor con valores
	 * @param id - El identificador del bebedor. Debe exixtir un bebedor con dicho identificador
	 * @param espacio - El identificador de la bebida. Debe existir una bebida con dicho identificador
	 */
	public Bodega(long id, long espacio, long idSucursal, long cantidadMin) 
	{
		this.id = id;
		this.espacio = espacio;
		this.idSucursal = idSucursal;
		this.cantidadMin = cantidadMin;
	}

	public long getId() 
	{
		return id;
	}

	public void setId(long idBebedor) 
	{
		this.id = idBebedor;
	}

	public long getEspacio() 
	{
		return espacio;
	}

	public void setEspacio(long idBebida) 
	{
		this.espacio = idBebida;
	}
	
	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString() 
	{
		return "Bodega [id=" + id + ", espacio=" + espacio + ", id sucursal=" + idSucursal + ", cantidad minima=" + cantidadMin + "]";
	}
	
}
