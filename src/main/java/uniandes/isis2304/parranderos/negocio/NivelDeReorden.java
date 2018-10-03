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
 * Clase para modelar la relación SIRVEN del negocio de los Parranderos:
 * Cada objeto de esta clase representa el hecho que un bodega sirve una sucursal y viceversa.
 * Se modela mediante los identificadores del bodega y de la sucursal respectivamente
 * Debe existir un bodega con el identificador dado
 * Debe existir una sucursal con el identificador dado 
 * Adicionalmente contiene el cantidadMin (DIURNO, NOCTURNO, TODOS) en el cual el bodega sirve la sucursal
 * 
 * @author Germán Bravo
 */
public class NivelDeReorden implements VONivelDeReorden
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador del bodega que sirve la sucursal
	 */
	private long idBodega;
	
	/**
	 * El identificador de la sucursal que es servida en el bodega
	 */
	private long idSucursal;
	
	/**
	 * El cantidadMin en que sirve la sucursal en el bodega (DIURNO, NOCTURNO, TODOS)
	 */
	private long cantidadMin;
	
	private long cantidadRecompra;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public NivelDeReorden () 
	{
		this.idBodega = 0;
		this.idSucursal = 0;
		this.cantidadMin = 0;
	}

	/**
	 * Constructor con valores
	 * @param idBodega - El identificador del bodega. Debe exixtir un bodega con dicho identificador
	 * @param idSucursal - El identificador de la sucursal. Debe existir una sucursal con dicho identificador
	 * @param cantidadMin - El cantidadMin en el que el bodega sirve la sucursal (DIURNO, NOCTURNO, TODOS)
	 */
	public NivelDeReorden (long idbodega, long idsucursal, long cantidadMin, long cantidadRecompra) 
	{
		this.idBodega = idbodega;
		this.idSucursal = idsucursal;
		this.cantidadMin = cantidadMin;
		this.cantidadRecompra = cantidadRecompra;
	}

	/**
	 * @return El idBodega
	 */
	public long getIdBodega() 
	{
		return idBodega;
	}

	/**
	 * @param idBodega - El nuevo identificador de bodega. Debe existir un bodega con dicho identificador
	 */
	public void setIdBodega(long idbodega) 
	{
		this.idBodega = idbodega;
	}

	/**
	 * @return El idSucursal
	 */
	public long getIdSucursal() 
	{
		return idSucursal;
	}

	/**
	 * @param idSucursal - El nuevo identificador de sucursal. Debe existir una sucursal con dicho identificador
	 */
	public void setIdSucursal(long idsucursal) 
	{
		this.idSucursal = idsucursal;
	}
	
	public long getCantidadMin() 
	{
		return cantidadMin;
	}

	public void setCantidadMin(long cantidadMin) 
	{
		this.cantidadMin = cantidadMin;
	}
	
	public long getCantidadRecompra()
	{
		return cantidadRecompra;
	}
	
	/**
	 * @param cantidadRecompra - El nuevo cantidadMin en que el bodega sirve la sucursal (DIURNO, NOCTURNO, TODOS)
	 */
	public void setCantidadRecompra(long cantidadRecompra) 
	{
		this.cantidadRecompra = cantidadRecompra;
	}

	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString() 
	{
		return "NivelDeReorden [idBodega= " + idBodega + ", idSucursal= " + idSucursal + ", cantidadMin= " + cantidadMin + ",cantidadRecompra= " + cantidadRecompra + " ]";
	}
}
