package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los métodos get de BAR.
 * 
 */
public interface VOPersonaNat 
{
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
     /**
	 * @return El id del bar
	 */
	public long getNum_doc();
	
	/**
	 * @return el nombre del bar
	 */
	public String getTipo_doc();
	
	/**
	 * @return el nombre del bar
	 */
	public String getNombre();
	
	/**
	 * @return la ciudad del bar
	 */
	public String getCorreo();
	

	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del bar
	 */
	public String toString();

}
