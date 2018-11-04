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

package uniandes.isis2304.superAndes.negocio;

/**
 * @author Daniel Ramirez - Juan Gutierrez
 */
public interface VOVentaPersonaNat 
{
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * @return El número de documento de una persona natural
	 */
	public long getNumDoc();

	/**
	 * @return El identificador de la venta
	 */
	public long getIdVenta();
	
	/**
	 * @return El tipo de documento de una persona natural.
	 */
	public String getTipoDoc();

	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString();

}
