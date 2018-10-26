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
	 * La direccion en la ciudad
	 */
	private long idBodega;

	
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
		this.idBodega = 0;
		
	}

	/**
	 * Constructor con valores
	 * @param id - El id del ciudad
	 * @param nombre - El nombre del ciudad
	 * @param direccion - La ciudad del ciudad
	 */
	public Estante(long id, long espacio, long idBodega) 
	{
		this.id = id;
		this.espacio = espacio;
		this.idBodega = idBodega;
		
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

	public long getIdBodega() {
		return idBodega;
	}

	public void setIdBodega(long idBodega) {
		this.idBodega = idBodega;
	}

	/**
	 * @return Una cadena de caracteres con la información básica del bebedor
	 */
	@Override
	public String toString() 
	{
		return "Estante [id=" + id + ", espacio=" + espacio + ", idBodega=" + idBodega + "]";
	}


}
