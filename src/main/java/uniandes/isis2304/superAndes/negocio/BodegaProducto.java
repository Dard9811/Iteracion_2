package uniandes.isis2304.superAndes.negocio;

/**
 * Clase para modelar el concepto BEBEDOR del negocio de los Parranderos
 *
 * @author Germán Bravo
 */
public class BodegaProducto implements VOBodegaProducto
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador de la Bodega
	 */
	private long idBodega;	
	
	/**
	 * El codigo de barras del producto.
	 */
	private String codigoProducto;
	
	/**
	 * La cantidad de producto en la bodega
	 */
	private long cantidad;

	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public BodegaProducto() 
	{
		this.idBodega = 0;
		this.codigoProducto = "";
		this.cantidad = 0;		
	}

	/**
	 * Constructor con valores
	 * @param idBodega - El idBodega del sucursal
	 * @param codigoProducto - El nombre del sucursal
	 * @param cantidad - La sucursal del sucursal
	 */
	public BodegaProducto(long id, String codProducto, long cantidad) 
	{
		this.idBodega = id;
		this.codigoProducto = codProducto;
		this.cantidad = cantidad;
	}



	public long getIdBodega() {
		return idBodega;
	}

	public void setIdBodega(long id) {
		this.idBodega = id;
	}

	public String getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(String codProducto) {
		this.codigoProducto = codProducto;
	}

	public long getCantidad() {
		return cantidad;
	}

	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return Una cadena de caracteres con la información básica del bebedor
	 */
	@Override
	public String toString() 
	{
		return "Sucursal [idBodega=" + idBodega + ", codigoProducto=" + codigoProducto + ", cantidad=" + cantidad + "]";
	}


}
