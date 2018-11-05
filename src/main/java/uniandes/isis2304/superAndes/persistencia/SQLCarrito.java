package uniandes.isis2304.superAndes.persistencia;

import java.sql.Timestamp;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superAndes.negocio.Carrito;
import uniandes.isis2304.superAndes.negocio.Producto;
import uniandes.isis2304.superAndes.negocio.Venta;

public class SQLCarrito 
{
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaSuperAndes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaSuperAndes pp;
	
	private Carrito carrito;
	
	
	public SQLCarrito (PersistenciaSuperAndes pp)
	{
		this.pp = pp;
		carrito = null;
	}
	
	public Carrito agregarAlCarrito (PersistenceManager pm, Carrito carrito, String codProd)
	{
        this.carrito = carrito;
		Query q = pm.newQuery(SQL, "SET TRANSACTION ISOLATION LEVEL SERIALIZABLE");
        Query r = pm.newQuery(SQL, "SET AUTOCOMMIT = 0");
        q.executeUnique();
        r.executeUnique();
//        for (int i = 0; i < carrito.getProductos().size(); i++) {
        	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        	Query x = pm.newQuery(SQL, "SELECT PRECIO_UNITARIO FROM " + pp.darTablaProducto() + " WHERE CODIGO_BARRAS = ?");
    		x.setResultClass(Producto.class);
    		x.setParameters(codProd);
    		int y = (int) x.executeUnique();
        	
        	Query s = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEstanteProducto() + " WHERE COD_PROD = '" + codProd + "'");
        	Query t = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaVenta() + "(VALOR, FECHA) VALUES(?, ?)");
        	t.setParameters(y ,timestamp);
        	
        	s.executeUnique();
        	t.executeUnique();
        	
        	Query a = pm.newQuery(SQL, "SELECT ID FROM " + pp.darTablaVenta() + " WHERE VALOR = ? AND FECHA = ?");
    		a.setResultClass(Venta.class);
    		a.setParameters(y,timestamp);
    		long b = (long) a.executeUnique();
    		
        	Query u = pm.newQuery(SQL, "INSERT INTO" + pp.darTablaVentaProducto() + "(ID_VENTA, COD_PRODUCTO) VALUES (?, ?)");
        	u.setParameters(b, codProd);
        	u.executeUnique();
        	
        	       	
//		}
        return carrito;       
        
	}
	
	public void comprar (PersistenceManager pm, String tipoDoc, long numDoc, long idSucursal)
	{
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		for (int i = 0; i < carrito.getProductos().size(); i++) {
			Query x = pm.newQuery(SQL, "SELECT PRECIO_UNITARIO FROM " + pp.darTablaProducto() + " WHERE CODIGO_BARRAS = ?");
    		x.setResultClass(Producto.class);
    		x.setParameters(carrito.getProductos().get(i).getCodigo_barras());
    		int y = (int) x.executeUnique();
    		
    		Query a = pm.newQuery(SQL, "SELECT ID FROM " + pp.darTablaVenta() + " WHERE VALOR = ? AND FECHA = ?");
    		a.setResultClass(Venta.class);
    		a.setParameters(y,timestamp);
    		long b = (long) a.executeUnique();
			
			Query u = pm.newQuery(SQL, "INSERT INTO" + pp.darTablaVentaPersonaNat() + "(ID_VENTA, TIPO_DOC_PERSONANAT, NUM_DOC_PERSONANAT) VALUES (?, ?, ?)");
	    	u.setParameters(b, tipoDoc, numDoc);
	    	u.executeUnique();
	    	
	    	Query t = pm.newQuery(SQL, "INSERT INTO" + pp.darTablaVentaSucursal() + "(ID_VENTA, ID_SUCURSAL) VALUES (?, ?)");
	    	t.setParameters(b, idSucursal);
	    	t.executeUnique();
		}
		
		Query q = pm.newQuery(SQL, "COMMIT");
		q.executeUnique();
	}
	
	public void abandonarCarrito (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "ROLLBACK");
		q.executeUnique();
	}
	
}
