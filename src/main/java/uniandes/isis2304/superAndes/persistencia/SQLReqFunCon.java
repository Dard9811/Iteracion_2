package uniandes.isis2304.superAndes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superAndes.negocio.ReqFunCon1;
import uniandes.isis2304.superAndes.negocio.ReqFunCon3;
import uniandes.isis2304.superAndes.negocio.ReqFunCon3p1;

public class SQLReqFunCon 
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

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/

	public SQLReqFunCon(PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}


	public List<ReqFunCon1> RFC1(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT suc.LOCAL_VENTAS AS nombre, sum(venta.VALOR) AS valor "
				+  "FROM " + pp.darTablaVentaSucursal() + " ventaSuc "
				+  "INNER JOIN " + pp.darTablaVenta() + " venta "
				+  " ON  venta.ID = ventaSuc.IDVENTA"
				+  " INNER JOIN " + pp.darTablaSucursal() + " suc"
				+  " ON suc.ID = ventaSuc.IDSUCURSAL "
				+  "GROUP BY suc.LOCAL_VENTAS");
		q.setResultClass(ReqFunCon1.class);
		return (List<ReqFunCon1>) q.executeList();
	}

	public List<ReqFunCon3> RFC3(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT SUC.LOCAL_VENTAS AS SUCURSAL, BOD.ID AS IDBODEGA, AVG(BODPROD.CANTIDAD/BOD.ESPACIO) AS INDICE_OCUPACION_BOD "
				+  "FROM " + pp.darTablaBodega() + " BOD "
				+  "INNER JOIN " + pp.darTablaBodegaProducto() + " BODPROD "
				+  " ON  BOD.ID = BODPROD.IDBODEGA"
				+  " INNER JOIN " + pp.darTablaSucursal() + " SUC"
				+  " ON SUC.ID = BOD.IDSUCURSAL "
				+  "GROUP BY SUC.LOCAL_VENTAS, BOD.ID");
		q.setResultClass(ReqFunCon3.class);
		return (List<ReqFunCon3>) q.executeList();
	}
	
	public List<ReqFunCon3p1> RFC3p1(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT SUC.LOCAL_VENTAS AS SUCURSAL, EST.ID AS IDESTANTE, AVG(ESTPROD.CANTIDAD/EST.ESPACIO) AS INDICE_OCUPACION_EST "
				+  "FROM " + pp.darTablaEstante() + " EST "
				+  "INNER JOIN " + pp.darTablaEstanteProducto() + " ESTPROD "
				+  " ON  EST.ID = ESTPROD.IDESTANTE"
				+  " INNER JOIN " + pp.darTablaSucursal() + " SUC"
				+  " ON SUC.ID = EST.IDSUCURSAL "
				+  "GROUP BY SUC.LOCAL_VENTAS, EST.ID");
		q.setResultClass(ReqFunCon3p1.class);
		return (List<ReqFunCon3p1>) q.executeList();
	}
	
	
}
