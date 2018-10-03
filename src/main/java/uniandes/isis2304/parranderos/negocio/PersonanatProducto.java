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
public class PersonanatProducto implements VOPersonanatProducto
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de los empresas
	 */
	private long personaNatNumDoc;
	
	/**
	 * El identificador ÚNICO de los empresas
	 */
	private long personaNatTipoDoc;
	
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
	public PersonanatProducto() 
    {
    	this.personaNatNumDoc = 0;
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
    public PersonanatProducto(long nit, String nombre, String correo) 
    {
    	this.personaNatNumDoc = nit;
		this.codProducto = nombre;

	}

	public long getPersonaNatNumDoc() {
		return personaNatNumDoc;
	}

	public void setPersonaNatNumDoc(long personaNatNumDoc) {
		this.personaNatNumDoc = personaNatNumDoc;
	}
	
	public long getPersonaNatTipoDoc() {
		return personaNatTipoDoc;
	}

	public void setPersonaNatTipoDoc(long personaNatTipoDoc) {
		this.personaNatTipoDoc = personaNatTipoDoc;
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
		return "Empresa [personaNatNumDoc=" + personaNatNumDoc + ", codProducto=" + codProducto + "]";
	}

	

}
