package uniandes.isis2304.superAndes.negocio;

/**
 * Clase para modelar el concepto BEBEDOR del negocio de los Parranderos
 *
 * @author Germán Bravo
 */
public class VentaProducto implements VOVentaProducto
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de la venta
	 */
	private long idVenta;	
	
	/**
	 * El codigo del producto
	 */
	private String codProd;
	
	/**
	 * La cantidad del producto en la venta
	 */
	private long cantidad;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public VentaProducto() 
	{
		this.idVenta = 0;
		this.codProd = "";
		this.cantidad = 0;
	}

	/**
	 * Constructor con valores
	 * @param idVenta - El idVenta del sucursal
	 * @param codProd - El nombre del sucursal
	 * @param segmentacion_mercado - La sucursal del sucursal
	 */
	public VentaProducto(long id, String codProd, long cantidad) 
	{
		this.idVenta = id;
		this.codProd = codProd;
		this.cantidad = cantidad;
	}

	@Override
	public long getIdVenta() {
		return idVenta;
	}

	public void setId(long id) {
		this.idVenta = id;
	}
	
	@Override
	public String getCodProd() {
		return codProd;
	}

	public void setCodProd(String codProd) {
		this.codProd = codProd;
	}
	
	@Override
	public long getCantidad() {
		return cantidad;
	}

	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}

	public void setIdVenta(long idVenta) {
		this.idVenta = idVenta;
	}

	/**
	 * @return Una cadena de caracteres con la información básica del bebedor
	 */
	@Override
	public String toString() 
	{
		return "Venta-Producto [idVenta=" + idVenta + ", codProducto=" + codProd + ", cantidad=" + cantidad + "]";
	}
}
