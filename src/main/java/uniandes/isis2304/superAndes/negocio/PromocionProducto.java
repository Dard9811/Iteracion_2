package uniandes.isis2304.superAndes.negocio;

/**
 * Clase para modelar el concepto BEBEDOR del negocio de los Parranderos
 *
 * @author Germán Bravo
 */
public class PromocionProducto implements VOPromocionProducto
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador del producto
	 */
	private long idProducto;	
	
	/**
	 * El identificador de la promoción
	 */
	private long idPromocion;

	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public PromocionProducto() 
	{
		this.idProducto = 0;
		this.idPromocion = 0;		
	}

	/**
	 * Constructor con valores
	 * @param idBodega - El idBodega del sucursal
	 * @param codigoProducto - El nombre del sucursal
	 * @param idPromoción - La sucursal del sucursal
	 */
	public PromocionProducto(long id, long idPromoción) 
	{
		this.idProducto = id;
		this.idPromocion = idPromoción;
	}
	
	@Override
	public long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}
	
	@Override
	public long getIdPromocion() {
		return idPromocion;
	}

	public void setIdPromoción(long idPromoción) {
		this.idPromocion = idPromoción;
	}

	/**
	 * @return Una cadena de caracteres con la información básica del bebedor
	 */
	@Override
	public String toString() 
	{
		return "Promoción-Producto [idProducto=" + idProducto  + ", idPromoción=" + idPromocion + "]";
	}


}
