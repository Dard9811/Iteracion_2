package uniandes.isis2304.superAndes.negocio;

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
	 * La fecha de inicio de la promoción
	 */
	private Timestamp fechaInic;
	
	/**
	 * La fecha de fin de la promoción
	 */
	private Timestamp fechaFin;
	
	/**
	 * El tipo de promoción
	 */
	private String tipoPromo;
	
	private String estado;


	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Promocion() 
	{
		this.id = 0;
		this.fechaInic = new Timestamp(0);
		this.fechaFin = new Timestamp(0);
		this.tipoPromo = "";
		this.estado = "";
	}

	/**
	 * Constructor con valores
	 * @param id - El id de la bebida
	 */
	public Promocion(long id, Timestamp fechaInic, Timestamp fechaFin, String tipoPromo, String estado) 
	{
		this.id = id;
		this.fechaInic = fechaInic;
		this.fechaFin = fechaFin;
		this.tipoPromo = tipoPromo;
		this.estado = estado;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getFechaInic() {
		return fechaInic;
	}

	public void setFechaInic(Timestamp fechaInic) {
		this.fechaInic = fechaInic;
	}

	public Timestamp getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getTipoPromo() {
		return tipoPromo;
	}

	public void setTipoPromo(String tipoPromo) {
		this.tipoPromo = tipoPromo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return Una cadena con la información básica de la bebida
	 */
	@Override
	public String toString() 
	{
		return "Promocion [id=" + id + ", fecha de inicio=" + fechaInic + ", fecha de fin=" + fechaFin + ", tipo de prmoción=" + tipoPromo + ", estado=" + estado + "]";
	}

}
