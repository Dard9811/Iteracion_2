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

import java.sql.Timestamp;

/**
 * Interfaz para los métodos get de PROMOCION.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Germán Bravo
 */
public interface VOPromocion 
{
	/**
	 * @return El id de la promocion
	 */
	public long getId();

	/**
	 * @return La fecha de inicio de la promoción
	 */
	public Timestamp getFechaInic();
	
	/**
	 * @return La fecha de fin de la promoción
	 */
	public Timestamp getFechaFin();
	
	/**
	 * @return El tipo de la promoción 
	 */
	public String getTipoPromo();

	/**
	 * @return El estado de la promoción 
	 */
	public String getEstado();
	
	/**
	 * @return Una cadena con la información básica de la promocion
	 */
	@Override
	public String toString();

}
