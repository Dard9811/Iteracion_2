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

package uniandes.isis2304.parranderos.negocio;

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
	 * El identificador del bebedor que gusta de la bebida
	 */
	private long id;

	/**
	 * El identificador de la bebida que gusta al bebedor
	 */
	private long espacio;

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
	}

	/**
	 * Constructor con valores
	 * @param id - El identificador del bebedor. Debe exixtir un bebedor con dicho identificador
	 * @param espacio - El identificador de la bebida. Debe existir una bebida con dicho identificador
	 */
	public Bodega(long idBebedor, long idBebida) 
	{
		this.id = idBebedor;
		this.espacio = idBebida;
	}

	/**
	 * @return El id
	 */
	public long getId() 
	{
		return id;
	}

	/**
	 * @param id - El nuevo id. Debe existir un bebedor con dicho identificador
	 */
	public void setId(long idBebedor) 
	{
		this.id = idBebedor;
	}

	/**
	 * @return El espacio
	 */
	public long getEspacio() 
	{
		return espacio;
	}

	/**
	 * @param espacio - El nuevo identificador de bebida. Debe existir una bebida con dicho identificador
	 */
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
		return "Bodega [id=" + id + ", espacio=" + espacio + "]";
	}
	
}
