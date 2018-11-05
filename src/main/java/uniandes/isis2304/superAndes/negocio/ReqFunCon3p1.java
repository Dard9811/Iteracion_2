package uniandes.isis2304.superAndes.negocio;

public class ReqFunCon3p1 implements VOReqFunCon3p1
{
	private String sucursal;
	
	private long idEstante;
	
	private double indice_ocupacion_est;
	
	public ReqFunCon3p1()
	{
		this.sucursal = "";
		this.idEstante = 0;
		this.indice_ocupacion_est = 0;
	}
	
	public ReqFunCon3p1(String nombre, long idBodega, long indice_ocupacion_est)
	{
		this.sucursal = nombre;
		this.idEstante = idBodega;
		this.indice_ocupacion_est = indice_ocupacion_est;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public long getIdEstante() {
		return idEstante;
	}

	public void setIdEstante(long idEstante) {
		this.idEstante = idEstante;
	}

	public double getIndice_ocupacion_est() {
		return indice_ocupacion_est;
	}

	public void setIndice_ocupacion_est(double indice_ocupacion_est) {
		this.indice_ocupacion_est = indice_ocupacion_est;
	}

	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del bar
	 */
	public String toString() 
	{
		return "RFC3 [Nombre Sucursal=" + sucursal + ", Id Estante=" + idEstante + ", Indice de ocupación " + indice_ocupacion_est*100 +   "% ]";
	}
}
