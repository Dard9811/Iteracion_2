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
public class PersonaNat implements VOPersonaNat
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de los empresas
	 */
	private long num_doc;
	
	/**
	 * El nombre del empresa
	 */
	private String tipo_doc;
	
	/**
	 * El nombre del empresa
	 */
	private String nombre;

	/**
	 * La ciudad donde se encuentra el empresa
	 */
	private String correo;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public PersonaNat() 
    {
    	this.num_doc = 0;
    	this.tipo_doc = "";
		this.nombre = "";
		this.correo = "";

	}

	/**
	 * Constructor con valores
	 * @param id - El id del bart
	 * @param nombre - El nombre del bar
	 * @param ciudad - La ciudad del bar
	 * @param presupuesto - El presupuesto del bar (ALTO, MEDIO, BAJO)
	 * @param cantSedes - Las sedes del bar (Mayor que 0)
	 */
    public PersonaNat(long num_doc, String tipo_doc, String nombre, String correo) 
    {
    	this.num_doc = num_doc;
    	this.tipo_doc = tipo_doc;
		this.nombre = nombre;
		this.correo = correo;
	}

    /**
	 * @return El nit de la empresa
	 */
	public long getNum_doc() 
	{
		return num_doc;
	}
	
	/**
	 * @param id - El nuevo nit dela empresa
	 */
	public void setNum_doc(long num_doc) 
	{
		this.num_doc = num_doc;
	}
	
	
	
	public String getTipo_doc() {
		return tipo_doc;
	}

	public void setTipo_doc(String tipo_doc) {
		this.tipo_doc = tipo_doc;
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
	public String getCorreo() 
	{
		return correo;
	}
	
	/**
	 * @param ciudad - El nuevo correo de la empresa
	 */
	public void setCorreo(String correo) 
	{
		this.correo =  correo;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del bar
	 */
	public String toString() 
	{
		return "Empresa [num_doc=" + num_doc + ", tipo_doc=" + tipo_doc + ", nombre=" + nombre + ", correo=" + correo + "]";
	}
	

}
