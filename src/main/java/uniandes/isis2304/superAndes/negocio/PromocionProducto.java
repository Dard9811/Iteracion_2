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
	 * @param idPromocion - La sucursal del sucursal
	 */
	public PromocionProducto(long id, long idPromocion) 
	{
		this.idProducto = id;
		this.idPromocion = idPromocion;
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

	public void setIdPromocion(long idPromocion) {
		this.idPromocion = idPromocion;
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
