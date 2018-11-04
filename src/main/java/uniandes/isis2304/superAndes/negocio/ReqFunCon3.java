package uniandes.isis2304.superAndes.negocio;

public class ReqFunCon3 implements VOReqFunCon3
{
	private String sucursal;
	
	private long idBodega;
	
	private double indice_ocupacion_bod;
	
	public ReqFunCon3()
	{
		this.sucursal = "";
		this.idBodega = 0;
		this.indice_ocupacion_bod = 0;
	}
	
	public ReqFunCon3(String nombre, long idBodega, double valor)
	{
		this.sucursal = nombre;
		this.idBodega = idBodega;
		this.indice_ocupacion_bod = valor;
	}
	
	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public long getIdBodega() {
		return idBodega;
	}

	public void setIdBodega(long idBodega) {
		this.idBodega = idBodega;
	}

	public double getIndice_ocupacion_bod() {
		return indice_ocupacion_bod;
	}

	public void setIndice_ocupacion_bod(double indice_ocupacion_bod) {
		this.indice_ocupacion_bod = indice_ocupacion_bod;
	}

	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del bar
	 */
	public String toString() 
	{
		return "RFC3 [Nombre Sucursal=" + sucursal + ", Id Bodega=" + idBodega + ", Indice de ocupación " + indice_ocupacion_bod*100 +   "% ]";
	}
}
