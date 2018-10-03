package uniandes.isis2304.parranderos.negocio;


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
	 * @return El id del bebedor
	 */
	public long getId();

	/**
	 * @return El nombre del bebedor
	 */
	public int getEspacio();

	/**
	 * @return La ciudad del bebedor
	 */
	public long getIdBodega();

	/**
	 * @return Una cadena de caracteres con la información básica del bebedor
	 */
	@Override
	public String toString();

}
