package uniandes.isis2304.superAndes.negocio;

public interface VOReqFunCon3 
{
	public String getSucursal();
	
	public long getIdBodega();
	
	public double getIndice_ocupacion_bod();
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del bar
	 */
	public String toString();
}
