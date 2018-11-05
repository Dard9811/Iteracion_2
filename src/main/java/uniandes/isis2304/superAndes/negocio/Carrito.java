package uniandes.isis2304.superAndes.negocio;

import java.util.ArrayList;
import java.util.List;

public class Carrito implements VOCarrito
{
	private List<Producto> productos;
	
	public Carrito()
	{
		productos = new ArrayList<Producto>();
	}
	
	public Carrito(List<Producto> productos)
	{
		this.productos = productos;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	
	public void agregarProducto(Producto prod)
	{
		productos.add(prod);
	}
	
}
