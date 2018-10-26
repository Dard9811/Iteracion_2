package uniandes.isis2304.superAndes.negocio;


/**
 * Interfaz para los métodos get de SUCURSAL.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 */
public interface VOSucursal 
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
	public String getLocal_ventas();

	/**
	 * @return La ciudad del bebedor
	 */
	public String getSegmentacion_mercado();

	public String getProductos_ofrecidos();
	
	public String getTamanio_instalacion();
	
	public long getIdCiudad();
	
	public long getIdSupermercado();
	
	/**
	 * @return Una cadena de caracteres con la información básica del bebedor
	 */
	@Override
	public String toString();

}
