	package uniandes.isis2304.superAndes.negocio;

import java.sql.Time;
import java.sql.Timestamp;

/**
	 * Clase para modelar el concepto BEBEDOR del negocio de los Parranderos
	 *
	 * @author Germán Bravo
	 */
	public class Venta implements VOVenta
	{
		/* ****************************************************************
		 * 			Atributos
		 *****************************************************************/
		/**
		 * El identificador ÚNICO de la sucursal
		 */
		private long id;	
		
		/**
		 * El valor de la venta
		 */
		private long valor;
		
		/**
		 * La fecha de la venta;
		 */
		private Timestamp fecha;
		
		/* ****************************************************************
		 * 			Métodos
		 *****************************************************************/
		/**
		 * Constructor por defecto
		 */
		public Venta() 
		{
			this.id = 0;
			this.valor = 0;	
			this.fecha = new Timestamp(0) ;
		}
	
		/**
		 * Constructor con valores
		 * @param id - El id del sucursal
		 * @param valor - El nombre del sucursal
		 * @param segmentacion_mercado - La sucursal del sucursal
		 */
		public Venta (long id, long valor, Timestamp fecha)
		{
			this.id = id;
			this.valor = valor;
			this.fecha = fecha;
		}
	
		@Override
		public long getId() {
			return id;
		}
	
		public void setId(long id) {
			this.id = id;
		}
		
		@Override
		public long getValor() {
			return valor;
		}
	
		public void setValor(long valor) {
			this.valor = valor;
		}
		
		@Override
		public Timestamp getFecha() {
			return fecha;
		}

		public void setFecha(Timestamp fecha) {
			this.fecha = fecha;
		}

		/**
		 * @return Una cadena de caracteres con la información básica del bebedor
		 */
		@Override
		public String toString() 
		{
			return "Sucursal [id=" + id + ", valor=" + valor + ", fecha=" + fecha + "]";
		}
	
	
	}
