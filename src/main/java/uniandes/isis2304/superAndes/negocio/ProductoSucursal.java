
package uniandes.isis2304.superAndes.negocio;

/**
 * Clase para modelar el concepto ProductoSucursal del negocio de los Parranderos
 *
 * @author Germán Bravo
 */
public class ProductoSucursal implements VOProductoSucursal
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador del tipo de bebida
	 */
	private long idSucursal;

	/**
	 * El codProducto del tipo de bebida
	 */
	private String codProducto;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public ProductoSucursal() 
	{
		this.idSucursal = 0;
		this.codProducto = "Default";
	}

	/**
	 * Constructor con valores
	 * @param idSucursal - El identificador del tipo de bebida
	 * @param codProducto - El codProducto del tipo de bebida
	 */
	public ProductoSucursal(long id, String codProducto) 
	{
		this.idSucursal = id;
		this.codProducto = codProducto;
	}

	/**
	 * @return El idSucursal del tipo de bebida
	 */
	public long getIdSucursal() 
	{
		return idSucursal;
	}

	/**
	 * @param idSucursal - El nuevo idSucursal del tipo de bebida
	 */
	public void setIdSucursal(long id) 
	{
		this.idSucursal = id;
	}

	/**
	 * @return El codProducto del tipo de bebida
	 */
	public String getCodProducto() 
	{
		return codProducto;
	}

	/**
	 * @param codProducto - El nuevo codProducto del tipo de bebida
	 */
	public void setCodProducto(String codProducto) 
	{
		this.codProducto = codProducto;
	}


	/**
	 * @return Una cadena de caracteres con la información del tipo de bebida
	 */
	@Override
	public String toString() 
	{
		return "ProductoSucursal [idSucursal=" + idSucursal + ", codProducto=" + codProducto + "]";
	}

//	/**
//	 * @param tipo - El ProductoSucursal a comparar
//	 * @return True si tienen el mismo codProducto
//	 */
//	public boolean equals(Object tipo) 
//	{
//		ProductoSucursal tb = (ProductoSucursal) tipo;
//		return idSucursal == tb.idSucursal && codProducto.equalsIgnoreCase (tb.codProducto);
//	}

}
