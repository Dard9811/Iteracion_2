package uniandes.isis2304.superAndes.negocio;

public interface VOReqFunCon3p1 
{
	public String getSucursal();
	
	public long getIdEstante() ;
	
	public double getIndice_ocupacion_est();
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del bar
	 */
	public String toString();
}
