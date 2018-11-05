package uniandes.isis2304.superAndes.negocio;


/**
 * Interfaz para los métodos get de BEBEDOR.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Germán Bravo
 */
public interface VOEstante 
{
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * @return El id del estante
	 */
	public long getId();

	/**
	 * @return El espacio del estante
	 */
	public long getEspacio();

	/**
	 * @return El estante de la sucursal
	 */
	public long getIdSucursal();
	
	/**
	 * @return La cantidad minima que acepta el estante
	 */
	public long getCantidadMin();

	/**
	 * @return Una cadena de caracteres con la información básica del bebedor
	 */
	@Override
	public String toString();

}
