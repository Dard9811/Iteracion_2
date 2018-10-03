
package uniandes.isis2304.parranderos.negocio;

/**
 * Clase para modelar la relación VISITAN del negocio de los Parranderos:
 * Cada objeto de esta clase representa el hecho que un bebedor visitó un bar y viceversa.
 * Se modela mediante los identificadores del bebedor y del bar respectivamente
 * Debe existir un bebedor con el identificador dado
 * Debe existir un bar con el identificador dado 
 * Adicionalmente contiene la fecha y el horario (DIURNO, NOCTURNO, TODOS) en el cual el bebedor visitó el bar
 * 
 * @author Germán Bravo
 */
public class Supermercado implements VOSupermercado
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador del bebedor que realiza la visita
	 */
	private long id;
	
	/**
	 * El identificador del bar visitado
	 */
	private String nombre;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Supermercado() 
	{
		this.id = 0;
		this.nombre = "";
	}

	/**
	 * Constructor con valores
	 * @param id - El identificador del b ebedor. Debe existir un bebedor con dicho identificador
	 * @param nombre - El identificador del bar. Debe exixtir un bar con dicho identificador
	 * @param fechaVisita - La fecha en la cual se realiza la visita
	 * @param horario - El horario en el que el bebedor vista el bar (DIURNO, NOCTURNO, TODOS)
	 */
	public Supermercado(long id, String nombre) 
	{
		this.id = id;
		this.nombre = nombre;
	}

	/**
	 * @return El id
	 */
	public long getId() 
	{
		return id;
	}

	/**
	 * @param id - El nuevo id. Debe existir un bebedor con dicho identificador
	 */
	public void setId(long id) 
	{
		this.id = id;
	}

	/**
	 * @return El nombre
	 */
	public String getNombre() 
	{
		return nombre;
	}

	public void setIdBar(String nombre) 
	{
		this.nombre = nombre;
	}

	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString() 
	{
		return "Supermercado [id=" + id + ", nombre=" + nombre +"]";
	}
}
