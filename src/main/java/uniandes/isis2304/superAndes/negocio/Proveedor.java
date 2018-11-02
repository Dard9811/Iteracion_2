/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.superAndes.negocio;

/**
 * Clase para modelar el concepto BAR del negocio de los Parranderos
 *
 * @author Germán Bravo
 */
public class Proveedor implements VOProveedor
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de los empresas
	 */
	private long nit;
	
	/**
	 * El nombre del empresa
	 */
	private String nombre;

	/**
	 * La ciudad donde se encuentra el empresa
	 */
	private long idSucursal;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public Proveedor() 
    {
    	this.nit = 0;
		this.nombre = "";
		this.idSucursal = 0;

	}

	/**
	 * Constructor con valores
	 * @param id - El id del bart
	 * @param nombre - El nombre del bar
	 * @param ciudad - La ciudad del bar
	 * @param presupuesto - El presupuesto del bar (ALTO, MEDIO, BAJO)
	 * @param cantSedes - Las sedes del bar (Mayor que 0)
	 */
    public Proveedor(long nit, String nombre, long idSucursal) 
    {
    	this.nit = nit;
		this.nombre = nombre;
		this.idSucursal = idSucursal;
	}

    /**
	 * @return El nit de la empresa
	 */
	public long getNit() 
	{
		return nit;
	}
	
	/**
	 * @param id - El nuevo nit dela empresa
	 */
	public void setNit(long nit) 
	{
		this.nit = nit;
	}
	
	/**
	 * @return el nombre de la empresa
	 */
	public String getNombre() 
	{
		return nombre;
	}
	
	/**
	 * @param nombre El nuevo nombre de la empresa
	 */
	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}
	
	/**
	 * @return el correo de la empresa
	 */
	public long getIdSucursal() 
	{
		return idSucursal;
	}
	
	/**
	 * @param ciudad - El nuevo correo de la empresa
	 */
	public void setIdSucursal(long idSucursal) 
	{
		this.idSucursal =  idSucursal;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del bar
	 */
	public String toString() 
	{
		return "Proveedor [nit=" + nit + ", nombre=" + nombre + ", idSucursal=" + idSucursal + "]";
	}
	

}
