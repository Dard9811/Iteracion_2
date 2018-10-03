package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

/**
 * Clase para modelar el concepto PROMOCION del negocio de los SuperAndes
 *
 */
public class Promocion implements VOPromocion
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de la promocion
	 */
	private long id;
	
	/**
	 * El tiempo de la oferta
	 */
	private Timestamp tiempo_oferta;


	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Promocion() 
	{
		this.id = 0;
		this.tiempo_oferta = new Timestamp(0);
	}

	/**
	 * Constructor con valores
	 * @param id - El id de la bebida
	 */
	public Promocion(long id, Timestamp tiempo_oferta) 
	{
		this.id = id;
		this.tiempo_oferta = tiempo_oferta;

	}

	/**
	 * @return El id de la bebida
	 */
	public long getId() 
	{
		return id;
	}

	/**
	 * @param id - El nuevo id de la bebida 
	 */
	public void setId(long id) 
	{
		this.id = id;
	}

	/**
	 * @return El nombre de la bebida
	 */
	public Timestamp getTiempoOferta() 
	{
		return tiempo_oferta;
	}

	/**
	 * @param nombre - El nuevo nombre de la bebida
	 */
	public void setTiempoOferta(Timestamp tiempo_oferta) 
	{
		this.tiempo_oferta = tiempo_oferta;
	}

	/**
	 * @return Una cadena con la información básica de la bebida
	 */
	@Override
	public String toString() 
	{
		return "Promocion [id=" + id + ", tiempo_oferta=" + tiempo_oferta + "]";
	}

}
