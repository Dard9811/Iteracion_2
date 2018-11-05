package uniandes.isis2304.superAndes.negocio;

/**
 * Clase para modelar el concepto BEBEDOR del negocio de los Parranderos
 *
 * @author Germán Bravo
 */
public class Estante implements VOEstante
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
	private long espacio;
	
	/**
	 * El identificador de la sucursal
	 */
	private long idSucursal;
	
	/**
	 * La cantidad minima que acepta el estante
	 */
	private long cantidadMin;

	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Estante() 
	{
		this.id = 0;
		this.espacio = 0;
		this.idSucursal = 0;
		this.cantidadMin = 0;
		
	}

	/**
	 * Constructor con valores
	 * @param id - El id del ciudad
	 * @param nombre - El nombre del ciudad
	 * @param direccion - La ciudad del ciudad
	 */
	public Estante(long id, long espacio, long idBodega, long cantidadMin) 
	{
		this.id = id;
		this.espacio = espacio;
		this.idSucursal = idBodega;
		this.cantidadMin = cantidadMin;
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

	public long getEspacio() {
		return espacio;
	}

	public void setEspacio(long espacio) {
		this.espacio = espacio;
	}

	public long getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal(long idSucursal) {
		this.idSucursal = idSucursal;
	}

	public long getCantidadMin() {
		return cantidadMin;
	}

	public void setCantidadMin(long cantidadMin) {
		this.cantidadMin = cantidadMin;
	}

	/**
	 * @return Una cadena de caracteres con la información básica del bebedor
	 */
	@Override
	public String toString() 
	{
		return "Estante [id=" + id + ", espacio=" + espacio + ", idSucursal=" + idSucursal + ", Cantidad minima=" + cantidadMin + "]";
	}


}
