package uniandes.isis2304.superAndes.negocio;


/**
 * Interfaz para los métodos get de SUCURSAL.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 */
public interface VOProducto 
{
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * @return El id del bebedor
	 */
	public String getCodigo_barras();

	/**
	 * @return El nombre del bebedor
	 */
	public String getNombre();

	/**
	 * @return La ciudad del bebedor
	 */
	public String getMarca();

	public String getCategoria();
	
	public long getPrecio_unitario();
	
	public long getPrecio_medida();
	
	public String getPresentacion();
	
	public long getCantidad_presentacion();
	
	public String getUnidad_medida();
	
	public String getEspecificacion_empacado();
	
	public long getIdBodega();
	
	public long getIdEstante();
	
	public long getIdProveedor();
	
	/**
	 * @return Una cadena de caracteres con la información básica del bebedor
	 */
	@Override
	public String toString();

}
