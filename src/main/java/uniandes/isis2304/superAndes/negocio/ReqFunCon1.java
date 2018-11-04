package uniandes.isis2304.superAndes.negocio;

public class ReqFunCon1 implements VOReqFunCon1
{
	private String nombre;
	
	private long valor;
	
	public ReqFunCon1()
	{
		this.nombre = "";
		this.valor = 0;
	}
	
	public ReqFunCon1(String nombre, long valor)
	{
		this.nombre = nombre;
		this.valor = valor;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getValor() {
		return valor;
	}

	public void setValor(long valor) {
		this.valor = valor;
	}

	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del bar
	 */
	public String toString() 
	{
		return "RFC1 [Nombre Sucursal=" + nombre + ", Dinero Recolectado= $" + valor + "]";
	}
}
