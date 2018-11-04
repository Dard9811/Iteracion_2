package uniandes.isis2304.superAndes.negocio;

/**
 * Clase para modelar el concepto BEBEDOR del negocio de los Parranderos
 *
 * @author Germán Bravo
 */
public class VentaEmpresa implements VOVentaEmpresa
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador de la Venta
	 */
	private long idVenta;	
	
	/**
	 * El nit de la empresa.
	 */
	private long nit;

	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public VentaEmpresa() 
	{
		this.idVenta = 0;
		this.nit = 0;	
	}

	/**
	 * Constructor con valores
	 * @param idVenta - El idVenta del sucursal
	 * @param nit - El nombre del sucursal
	 * @param segmentacion_mercado - La sucursal del sucursal
	 */
	public VentaEmpresa(long id, long nit) 
	{
		this.idVenta = id;
		this.nit = nit;
	}

	@Override
	public long getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(long idVenta) {
		this.idVenta = idVenta;
	}
	
	@Override
	public long getNitEmpresa() {
		return nit;
	}

	public void setNitEmpresa(long nit) {
		this.nit = nit;
	}

	/**
	 * @return Una cadena de caracteres con la información básica del bebedor
	 */
	@Override
	public String toString() 
	{
		return "Sucursal [idVenta=" + idVenta + ", nit=" + nit + "]";
	}


}
