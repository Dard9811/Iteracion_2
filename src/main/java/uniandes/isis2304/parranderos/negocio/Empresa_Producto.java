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

package uniandes.isis2304.parranderos.negocio;

/**
 * Clase para modelar el concepto BAR del negocio de los Parranderos
 *
 * @author Germán Bravo
 */
public class Empresa_Producto implements VOEmpresa_Producto
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de los empresas
	 */
	private long nit;
	
	/**
	 * El codProducto del empresa
	 */
	private String codProducto;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public Empresa_Producto() 
    {
    	this.nit = 0;
		this.codProducto = "";

	}

	/**
	 * Constructor con valores
	 * @param id - El id del bart
	 * @param codProducto - El codProducto del bar
	 * @param ciudad - La ciudad del bar
	 * @param presupuesto - El presupuesto del bar (ALTO, MEDIO, BAJO)
	 * @param cantSedes - Las sedes del bar (Mayor que 0)
	 */
    public Empresa_Producto(long nit, String nombre, String correo) 
    {
    	this.nit = nit;
		this.codProducto = nombre;

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
	 * @return el codProducto de la empresa
	 */
	public String getCodProducto() 
	{
		return codProducto;
	}
	
	/**
	 * @param codProducto El nuevo codProducto 
	 */
	public void setCodProducto(String nombre) 
	{
		this.codProducto = nombre;
	}
	
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del bar
	 */
	public String toString() 
	{
		return "Empresa [nit=" + nit + ", codProducto=" + codProducto + "]";
	}
	

}
