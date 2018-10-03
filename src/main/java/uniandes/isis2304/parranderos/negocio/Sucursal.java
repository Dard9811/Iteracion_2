package uniandes.isis2304.parranderos.negocio;

/**
 * Clase para modelar el concepto BEBEDOR del negocio de los Parranderos
 *
 * @author Germán Bravo
 */
public class Sucursal implements VOSucursal
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de la sucursal
	 */
	private long id;	
	
	/**
	 * El local de ventas de la sucursal
	 */
	private String local_ventas;
	
	/**
	 * La segmentación del mercado de la sucursal
	 */
	private String segmentacion_mercado;
	
	/**
	 * El local de ventas de la sucursal
	 */
	private String productos_ofrecidos;
	
	/**
	 * La segmentación del mercado de la sucursal
	 */
	private String tamanio_instalacion;
	
	/**
	 * El local de ventas de la sucursal
	 */
	private long idCiudad;
	
	/**
	 * La segmentación del mercado de la sucursal
	 */
	private long idSupermercado;

	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Sucursal() 
	{
		this.id = 0;
		this.local_ventas = "";
		this.segmentacion_mercado = "";
		this.productos_ofrecidos = "";
		this.tamanio_instalacion = "";
		this.idCiudad = 0;
		this.idSupermercado = 0;
		
	}

	/**
	 * Constructor con valores
	 * @param id - El id del sucursal
	 * @param local_ventas - El nombre del sucursal
	 * @param segmentacion_mercado - La sucursal del sucursal
	 */
	public Sucursal(long id, String local_ventas, String segmentacion_mercado, String productos_ofrecidos, String tamanio_instalacion, long idCiudad, long idSupermercado) 
	{
		this.id = id;
		this.local_ventas = local_ventas;
		this.segmentacion_mercado = segmentacion_mercado;
		this.productos_ofrecidos = productos_ofrecidos;
		this.tamanio_instalacion = tamanio_instalacion;
		this.idCiudad = idCiudad;
		this.idSupermercado = idSupermercado;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLocal_ventas() {
		return local_ventas;
	}

	public void setLocal_ventas(String local_ventas) {
		this.local_ventas = local_ventas;
	}

	public String getSegmentacion_mercado() {
		return segmentacion_mercado;
	}

	public void setSegmentacion_mercado(String segmentacion_mercado) {
		this.segmentacion_mercado = segmentacion_mercado;
	}

	public String getProductos_ofrecidos() {
		return productos_ofrecidos;
	}

	public void setProductos_ofrecidos(String productos_ofrecidos) {
		this.productos_ofrecidos = productos_ofrecidos;
	}

	public String getTamanio_instalacion() {
		return tamanio_instalacion;
	}

	public void setTamanio_instalacion(String tamanio_instalacion) {
		this.tamanio_instalacion = tamanio_instalacion;
	}

	public long getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(long idCiudad) {
		this.idCiudad = idCiudad;
	}

	public long getIdSupermercado() {
		return idSupermercado;
	}

	public void setIdSupermercado(long idSupermercado) {
		this.idSupermercado = idSupermercado;
	}

	/**
	 * @return Una cadena de caracteres con la información básica del bebedor
	 */
	@Override
	public String toString() 
	{
		return "Sucursal [id=" + id + ", local_ventas=" + local_ventas + ", segmentacion_mercado=" + segmentacion_mercado + ", productos_ofrecidos=" + productos_ofrecidos + ", tamanio_instalacion=" + tamanio_instalacion + ", idCiudad=" + idCiudad + ", idSupermercado=" + idSupermercado + "]";
	}


}
