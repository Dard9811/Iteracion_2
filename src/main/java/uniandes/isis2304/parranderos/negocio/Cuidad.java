package uniandes.isis2304.parranderos.negocio;

/**
 * Clase para modelar el concepto BEBEDOR del negocio de los Parranderos
 *
 * @author Germán Bravo
 */
public class Cuidad implements VOCiudad
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de la ciudad
	 */
	private long id;	
	
	/**
	 * El nombre de la ciudad
	 */
	private String nombre;
	
	/**
	 * La direccion en la ciudad
	 */
	private String direccion;

	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Cuidad() 
	{
		this.id = 0;
		this.nombre = "";
		this.direccion = "";
		
	}

	/**
	 * Constructor con valores
	 * @param id - El id del ciudad
	 * @param nombre - El nombre del ciudad
	 * @param direccion - La ciudad del ciudad
	 */
	public Cuidad(long id, String nombre, String direccion) 
	{
		this.id = id;
		this.nombre = nombre;
		this.direccion = direccion;
		
	}

	/**
	 * @return El id del ciudad
	 */
	public long getId() 
	{
		return id;
	}

	/**
	 * @param id - El nuevo id del ciudad
	 */
	public void setId(long id) 
	{
		this.id = id;
	}

	/**
	 * @return El nombre del ciudad
	 */
	public String getNombre() 
	{
		return nombre;
	}

	/**
	 * @param nombre - El nuevo nombre del ciudad
	 */
	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	/**
	 * @return La direccion en la ciudad
	 */
	public String getDireccion() 
	{
		return direccion;
	}

	/**
	 * @param direccion - La nueva direccion en la ciudad
	 */
	public void setDireccion(String direccion) 
	{
		this.direccion = direccion;
	}

	/**
	 * @return Una cadena de caracteres con la información básica del bebedor
	 */
	@Override
	public String toString() 
	{
		return "Ciudad [id=" + id + ", nombre=" + nombre + ", direccion=" + direccion + "]";
	}


}
