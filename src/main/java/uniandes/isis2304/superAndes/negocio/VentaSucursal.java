package uniandes.isis2304.superAndes.negocio;

/**
 * Clase para modelar el concepto BEBEDOR del negocio de los Parranderos
 *
 * @author Germán Bravo
 */
public class VentaSucursal implements VOVentaSucursal
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de la venta
	 */
	private long idVenta;	
	
	/**
	 * El identificador Unico de la sucursal
	 */
	private long idSucursal;
	
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public VentaSucursal() 
	{
		this.idVenta = 0;
		this.idSucursal = 0;
	}

	/**
	 * Constructor con valores
	 * @param idVenta - El idVenta del sucursal
	 * @param codProd - El nombre del sucursal
	 * @param segmentacion_mercado - La sucursal del sucursal
	 */
	public VentaSucursal(long idVenta, long idSucursal) 
	{
		this.idVenta = idVenta;
		this.idSucursal = idSucursal;
	}

	@Override
	public long getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(long idVenta) {
		this.idVenta = idVenta;
	}
	
	@Override
	public long getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal(long idSucursal) {
		this.idSucursal = idSucursal;
	}

	/**
	 * @return Una cadena de caracteres con la información básica del bebedor
	 */
	@Override
	public String toString() 
	{
		return "Venta-Producto [idVenta=" + idVenta + ", idSucursal=" + idSucursal + "]";
	}
}
