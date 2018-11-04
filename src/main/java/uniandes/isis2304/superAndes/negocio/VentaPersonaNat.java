package uniandes.isis2304.superAndes.negocio;


public class VentaPersonaNat implements VOVentaPersonaNat
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador de la Venta
	 */
	private long idVenta;	
	
	/**
	 * El numero de documento de una persona
	 */
	private long numDoc;
	
	/**
	 * El tipo de documento de una persona
	 */
	private String tipo;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public VentaPersonaNat() 
	{
		this.idVenta = 0;
		this.numDoc = 0;	
		this.tipo = "";
	}

	/**
	 * Constructor con valores
	 * @param idVenta - El idVenta del sucursal
	 * @param numDoc - El nombre del sucursal
	 * @param segmentacion_mercado - La sucursal del sucursal
	 */
	public VentaPersonaNat(long id, long numDoc, String tipo) 
	{
		this.idVenta = id;
		this.numDoc = numDoc;
		this.tipo = tipo;
	}

	@Override
	public long getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(long idVenta) {
		this.idVenta = idVenta;
	}
	
	@Override
	public long getNumDoc() {
		return numDoc;
	}

	public void setNumDoc(long numDoc) {
		this.numDoc = numDoc;
	}
	
	@Override
	public String getTipoDoc() {
		return tipo;
	}

	public void setTipoDoc(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return Una cadena de caracteres con la información básica del bebedor
	 */
	@Override
	public String toString() 
	{
		return "Venta-PersonaNat [ NumDoc=" + numDoc + ", tipoDoc =" + tipo + ", idVenta=" + idVenta  + "]";
	}
}