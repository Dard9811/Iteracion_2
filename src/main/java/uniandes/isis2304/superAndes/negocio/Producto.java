package uniandes.isis2304.superAndes.negocio;

/**
 * Clase para modelar el concepto BEBEDOR del negocio de los Parranderos
 *
 * @author Germán Bravo
 */
public class Producto implements VOProducto
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de la sucursal
	 */
	private String codigo_barras;	
	
	/**
	 * El local de ventas de la sucursal
	 */
	private String nombre;
	
	/**
	 * La segmentación del mercado de la sucursal
	 */
	private String marca;
	
	/**
	 * El local de ventas de la sucursal
	 */
	private String categoria;
	
	/**
	 * La segmentación del mercado de la sucursal
	 */
	private long precio_unitario;
	
	/**
	 * El local de ventas de la sucursal
	 */
	private long precio_medida;
	
	/**
	 * La segmentación del mercado de la sucursal
	 */
	private String presentacion;
	
	/**
	 * El local de ventas de la sucursal
	 */
	private long cantidad_presentacion;

	/**
	 * La segmentación del mercado de la sucursal
	 */
	private String unidad_medida;
	
	/**
	 * El local de ventas de la sucursal
	 */
	private String especificacion_empacado;
		
	/**
	 * El local de ventas de la sucursal
	 */
	private long idProveedor;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Producto() 
	{
		this.codigo_barras = "";
		this.nombre = "";
		this.marca = "";
		this.categoria = "";
		this.precio_unitario = 0;
		this.precio_medida = 0;
		this.presentacion = "";
		this.cantidad_presentacion = 0;
		this.unidad_medida = "";
		this.especificacion_empacado = "";
		this.idProveedor = 0;
		
	}

	/**
	 * Constructor con valores
	 * @param id - El id del sucursal
	 * @param local_ventas - El nombre del sucursal
	 * @param segmentacion_mercado - La sucursal del sucursal
	 */
	public Producto(String codigo_barras, String nombre, String marca, String categoria, long precio_unitario, long precio_medida, String presentacion, long cantidad_presentacion, String unidad_medida, String especificacion_empacado, long idProveedor) 
	{
		this.codigo_barras = codigo_barras;
		this.nombre = nombre;
		this.marca = marca;
		this.categoria = categoria;
		this.precio_unitario = precio_unitario;
		this.precio_medida = precio_medida;
		this.presentacion = presentacion;
		this.cantidad_presentacion = cantidad_presentacion;
		this.unidad_medida = unidad_medida;
		this.especificacion_empacado = especificacion_empacado;
		this.idProveedor = idProveedor;
	}

	public String getCodigo_barras() {
		return codigo_barras;
	}

	public void setCodigo_barras(String codigo_barras) {
		this.codigo_barras = codigo_barras;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public long getPrecio_unitario() {
		return precio_unitario;
	}

	public void setPrecio_unitario(long precio_unitario) {
		this.precio_unitario = precio_unitario;
	}

	public long getPrecio_medida() {
		return precio_medida;
	}

	public void setPrecio_medida(long precio_medida) {
		this.precio_medida = precio_medida;
	}

	public String getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}

	public long getCantidad_presentacion() {
		return cantidad_presentacion;
	}

	public void setCantidad_presentacion(long cantidad_presentacion) {
		this.cantidad_presentacion = cantidad_presentacion;
	}

	public String getUnidad_medida() {
		return unidad_medida;
	}

	public void setUnidad_medida(String unidad_medida) {
		this.unidad_medida = unidad_medida;
	}

	public String getEspecificacion_empacado() {
		return especificacion_empacado;
	}

	public void setEspecificacion_empacado(String especificacion_empacado) {
		this.especificacion_empacado = especificacion_empacado;
	}

	public long getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(long idProveedor) {
		this.idProveedor = idProveedor;
	}

	/**
	 * @return Una cadena de caracteres con la información básica del bebedor
	 */
	@Override
	public String toString() 
	{
		return "Sucursal [id=" + codigo_barras + ", nombre=" + nombre + ", marca=" + marca + ", categoria=" + categoria + ", precio_unitario=" + precio_unitario + ", precio_medida=" + precio_medida + ", presentación=" + presentacion + ", cantidad_presentación="+ cantidad_presentacion + ", unidad_medida=" + unidad_medida + ", especificación_empacado=" + especificacion_empacado + ", bodega=" + ", proveedor=" + idProveedor + "]";
	}


}
