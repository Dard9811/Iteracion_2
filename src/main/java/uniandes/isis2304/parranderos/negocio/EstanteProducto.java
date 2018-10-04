package uniandes.isis2304.parranderos.negocio;

/**
 * Clase para modelar el concepto BEBEDOR del negocio de los Parranderos
 *
 * @author Germán Bravo
 */
public class EstanteProducto implements VOEstanteProducto
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador de la estante
	 */
	private long idEstante;	
	
	/**
	 * El codigo de barras del producto.
	 */
	private String codigoProducto;
	
	/**
	 * La cantidad de producto en el estante
	 */
	private long cantidad;

	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public EstanteProducto() 
	{
		this.idEstante = 0;
		this.codigoProducto = "";
		this.cantidad = 0;		
	}

	/**
	 * Constructor con valores
	 * @param idEstante - El idEstante del sucursal
	 * @param codigoProducto - El nombre del sucursal
	 * @param cantidad - La sucursal del sucursal
	 */
	public EstanteProducto(long id, String codProducto, long cantidad) 
	{
		this.idEstante = id;
		this.codigoProducto = codProducto;
		this.cantidad = cantidad;
	}



	public long getIdEstante() {
		return idEstante;
	}

	public void setIdEstante(long id) {
		this.idEstante = id;
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
		return "Sucursal [idEstante=" + idEstante + ", codigoProducto=" + codigoProducto + ", cantidad=" + cantidad + "]";
	}


}
