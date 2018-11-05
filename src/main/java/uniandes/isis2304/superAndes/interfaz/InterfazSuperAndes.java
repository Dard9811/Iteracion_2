/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: superAndes Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.superAndes.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import javafx.geometry.VPos;
import uniandes.isis2304.superAndes.negocio.SuperAndes;
import uniandes.isis2304.superAndes.negocio.VOEmpresa;
import uniandes.isis2304.superAndes.negocio.VOEstante;
import uniandes.isis2304.superAndes.negocio.VOCiudad;
import uniandes.isis2304.superAndes.negocio.VOPromocion;
import uniandes.isis2304.superAndes.negocio.VOProveedor;
import uniandes.isis2304.superAndes.negocio.VOReqFunCon1;
import uniandes.isis2304.superAndes.negocio.VOReqFunCon3;
import uniandes.isis2304.superAndes.negocio.VOReqFunCon3p1;
import uniandes.isis2304.superAndes.negocio.VOSucursal;
import uniandes.isis2304.superAndes.negocio.VOBodega;
import uniandes.isis2304.superAndes.negocio.VOPersonaNat;
import uniandes.isis2304.superAndes.negocio.VOProducto;
import uniandes.isis2304.superAndes.negocio.VOSupermercado;

/**
 * Clase principal de la interfaz
 * 
 * @author Germán Bravo
 */
@SuppressWarnings("serial")

public class InterfazSuperAndes extends JFrame implements ActionListener
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(InterfazSuperAndes.class.getName());
	
	/**
	 * Ruta al archivo de configuración de la interfaz
	 */
	private final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfig.json"; 
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos
	 */
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD_SuperAndes.json"; 
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
     */
    private JsonObject tableConfig;
    
    /**
     * Asociación a la clase principal del negocio.
     */
    private SuperAndes superAndes;
    
	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/
    /**
     * Objeto JSON con la configuración de interfaz de la app.
     */
    private JsonObject guiConfig;
    
    /**
     * Panel de despliegue de interacción para los requerimientos
     */
    private PanelDatos panelDatos;
    
    /**
     * Menú de la aplicación
     */
    private JMenuBar menuBar;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
    /**
     * Construye la ventana principal de la aplicación. <br>
     * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
     */
    public InterfazSuperAndes( )
    {
        // Carga la configuración de la interfaz desde un archivo JSON
        guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);
        
        // Configura la apariencia del frame que contiene la interfaz gráfica
        configurarFrame ( );
        if (guiConfig != null) 	   
        {
     	   crearMenu( guiConfig.getAsJsonArray("menuBar") );
        }
        
        tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
        superAndes = new SuperAndes (tableConfig);
        
    	String path = guiConfig.get("bannerPath").getAsString();
        panelDatos = new PanelDatos ( );

        setLayout (new BorderLayout());
        add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
        add( panelDatos, BorderLayout.CENTER );        
    }
    
	/* ****************************************************************
	 * 			Métodos para la configuración de la interfaz
	 *****************************************************************/
    /**
     * Lee datos de configuración para la aplicación, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param tipo - El tipo de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del tipo especificado
     * 			NULL si hay un error en el archivo.
     */
    private JsonObject openConfig (String tipo, String archConfig)
    {
    	JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración válido: " + tipo);
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "superAndes App", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }
    
    /**
     * Método para configurar el frame principal de la aplicación
     */
    private void configurarFrame(  )
    {
    	int alto = 0;
    	int ancho = 0;
    	String titulo = "";	
    	
    	if ( guiConfig == null )
    	{
    		log.info ( "Se aplica configuración por defecto" );			
			titulo = "superAndes APP Default";
			alto = 300;
			ancho = 500;
    	}
    	else
    	{
			log.info ( "Se aplica configuración indicada en el archivo de configuración" );
    		titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
    	}
    	
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLocation (50,50);
        setResizable( true );
        setBackground( Color.WHITE );

        setTitle( titulo );
		setSize ( ancho, alto);        
    }

    /**
     * Método para crear el menú de la aplicación con base em el objeto JSON leído
     * Genera una barra de menú y los menús con sus respectivas opciones
     * @param jsonMenu - Arreglo Json con los menùs deseados
     */
    private void crearMenu(  JsonArray jsonMenu )
    {    	
    	// Creación de la barra de menús
        menuBar = new JMenuBar();       
        for (JsonElement men : jsonMenu)
        {
        	// Creación de cada uno de los menús
        	JsonObject jom = men.getAsJsonObject(); 

        	String menuTitle = jom.get("menuTitle").getAsString();        	
        	JsonArray opciones = jom.getAsJsonArray("options");
        	
        	JMenu menu = new JMenu( menuTitle);
        	
        	for (JsonElement op : opciones)
        	{       	
        		// Creación de cada una de las opciones del menú
        		JsonObject jo = op.getAsJsonObject(); 
        		String lb =   jo.get("label").getAsString();
        		String event = jo.get("event").getAsString();
        		
        		JMenuItem mItem = new JMenuItem( lb );
        		mItem.addActionListener( this );
        		mItem.setActionCommand(event);
        		
        		menu.add(mItem);
        	}       
        	menuBar.add( menu );
        }        
        setJMenuBar ( menuBar );	
    }
    
	/* ****************************************************************
	 * 			Métodos administrativos
	 *****************************************************************/
	/**
	 * Muestra el log de superAndes
	 */
	public void mostrarLogSuperAndes ()
	{
		mostrarArchivo ("superAndes.log");
	}
	
	/**
	 * Muestra el log de datanucleus
	 */
	public void mostrarLogDatanuecleus ()
	{
		mostrarArchivo ("datanucleus.log");
	}
	
	/**
	 * Limpia el contenido del log de superAndes
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogSuperAndes ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("superAndes.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de superAndes ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia el contenido del log de datanucleus
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogDatanucleus ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("datanucleus.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia todas las tuplas de todas las tablas de la base de datos de superAndes
	 * Muestra en el panel de datos el número de tuplas eliminadas de cada tabla
	 */
	public void limpiarBD ()
	{
		try 
		{
    		// Ejecución de la demo y recolección de los resultados
			long eliminados [] = superAndes.limpiarSuperAndes();
			
			// Generación de la cadena de caracteres con la traza de la ejecución de la demo
			String resultado = "\n\n************ Limpiando la base de datos ************ \n";
			resultado += eliminados [0] + " Bodega eliminados\n";
			resultado += eliminados [1] + " NivelDeReorden eliminados\n";
			resultado += eliminados [2] + " Supermercado eliminados\n";
			resultado += eliminados [3] + " Bebidas eliminadas\n";
			resultado += eliminados [4] + " Tipos de bebida eliminados\n";
			resultado += eliminados [5] + " Ciudades eliminados\n";
			resultado += eliminados [6] + " Bares eliminados\n";
			resultado += "\nLimpieza terminada";
   
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	/**
	 * Muestra la presentación general del proyecto
	 */
	public void mostrarPresentacionGeneral ()
	{
		mostrarArchivo ("data/00-ST-superAndesJDO.pdf");
	}
	
	/**
	 * Muestra el modelo conceptual de superAndes
	 */
	public void mostrarModeloConceptual ()
	{
		mostrarArchivo ("data/Modelo Conceptual superAndes.pdf");
	}
	
	/**
	 * Muestra el esquema de la base de datos de superAndes
	 */
	public void mostrarEsquemaBD ()
	{
		mostrarArchivo ("data/Esquema BD superAndes.pdf");
	}
	
	/**
	 * Muestra el script de creación de la base de datos
	 */
	public void mostrarScriptBD ()
	{
		mostrarArchivo ("data/EsquemasuperAndes.txt");
	}
	
	/**
	 * Muestra la documentación Javadoc del proyectp
	 */
	public void mostrarJavadoc ()
	{
		mostrarArchivo ("doc/index.html");
	}
	
	/* ****************************************************************
	 * 			Metodos de requerimientos
	 *****************************************************************/
	
	public void registrarProveedor()
	{
		long nit = Long.parseLong(JOptionPane.showInputDialog(this,"Ingrese el NIT: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE));
		String nombre = JOptionPane.showInputDialog(this,"Ingrese el nombre: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE);
		long sucursal = Long.parseLong(JOptionPane.showInputDialog(this,"Ingrese la sucursal de la cual es proveedor: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE));

		try 
		{
			VOProveedor nProv = superAndes.adicionarProveedor(nit, nombre, sucursal);
			String resultado = "Se registro el proveedor exitosamente.";
			resultado += "\n" + nProv.toString();
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void registrarProducto()
	{
		String codigo_barras = JOptionPane.showInputDialog(this,"Ingrese el codigo de barras en hexadecimal: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE);
		String nombre = JOptionPane.showInputDialog(this,"Ingrese el nombre del producto: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE);
		String marca = JOptionPane.showInputDialog(this,"Ingrese la marca del producto: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE);
		String categoria = JOptionPane.showInputDialog(this,"Ingrese la categoria del producto: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE);
		long precioUnitario = Long.parseLong(JOptionPane.showInputDialog(this,"Ingrese el precio del producto: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE));
		long precioMedida = Long.parseLong(JOptionPane.showInputDialog(this,"Ingrese el precio por medida del producto: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE));
		String presentacion = JOptionPane.showInputDialog(this,"Ingrese la presentacion del producto: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE);
		long cantidadPresentacion = Long.parseLong(JOptionPane.showInputDialog(this,"Ingrese la cantidad que viene en la presentacion del producto: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE));
		String unidadMedida = JOptionPane.showInputDialog(this,"Ingrese la unidad de medida del producto: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE);
		String especificacionEmpacado = JOptionPane.showInputDialog(this,"Ingrese la especificacion de empacado del producto: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE);
		long proveedor = Long.parseLong(JOptionPane.showInputDialog(this,"Ingrese el nit del proveedor que distribuye el producto: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE));		
		
		try 
		{
			VOProducto nProd = superAndes.adicionarProducto(codigo_barras, nombre, marca, categoria, precioUnitario, precioMedida, presentacion, cantidadPresentacion, unidadMedida, especificacionEmpacado, proveedor);
			String resultado = "Se registro el producto exitosamente.";
			resultado += "\n" + nProd.toString();
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	
	public void registrarPersonaNat()
	{
		long numDoc = Long.parseLong(JOptionPane.showInputDialog(this,"Ingrese el numero de documento: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE));
		String tipoDoc = JOptionPane.showInputDialog(this,"Ingrese las iniciales del tipo de documento en mayusculas: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE);
		String nombre = JOptionPane.showInputDialog(this,"Ingrese el nombre: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE);
		String correo = JOptionPane.showInputDialog(this,"Ingrese el correo: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE);

		try 
		{
			VOPersonaNat nPersonanat = superAndes.adicionarPersonaNat(numDoc, tipoDoc, nombre, correo);
			String resultado = "Se registro la persona natural exitosamente.";
			resultado += "\n" + nPersonanat.toString();
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void registrarSucursal()
	{
		String localVentas = JOptionPane.showInputDialog(this,"Ingrese el nombre del local de ventas: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE);
		String segmentacionMercado = JOptionPane.showInputDialog(this,"Ingrese la segmentacion del mercado ", "SuperAndes", JOptionPane.QUESTION_MESSAGE);
		String tamanioInstalacion = JOptionPane.showInputDialog(this,"Ingrese el tamaño del local de ventas: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE);
		long ciudad = Long.parseLong(JOptionPane.showInputDialog(this,"Ingrese el identificador de la ciudad: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE));
		long supermercado = Long.parseLong(JOptionPane.showInputDialog(this,"Ingrese el identificador del supermercado: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE));

		try 
		{
			VOSucursal nSuc = superAndes.adicionarSucursal( localVentas, segmentacionMercado, tamanioInstalacion, ciudad, supermercado);
			String resultado = "Se registro la persona natural exitosamente.";
			resultado += "\n" + nSuc.toString();
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void registrarBodega()
	{
		long espacio = Long.parseLong(JOptionPane.showInputDialog(this,"Ingrese el espacio de la bodega: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE));
		long idSucursal = Long.parseLong(JOptionPane.showInputDialog(this,"Ingrese el identificador de la sucursal: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE));
		long cantidadMin = Long.parseLong(JOptionPane.showInputDialog(this,"Ingrese la cantidad minima de productos que acepta la bodega: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE));
		
		try 
		{
			VOBodega nBod = superAndes.adicionarBodega(espacio, idSucursal, cantidadMin);
			String resultado = "Se registro la bodega exitosamente.";
			resultado += "\n" + nBod.toString();
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void registrarEstante()
	{
		long espacio = Long.parseLong(JOptionPane.showInputDialog(this,"Ingrese el espacio del estante: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE));
		long idSucursal = Long.parseLong(JOptionPane.showInputDialog(this,"Ingrese el identificador de la sucursal: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE));
		long cantidadMin = Long.parseLong(JOptionPane.showInputDialog(this,"Ingrese la cantidad minima de productos que acepta el estante: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE));
		
		try 
		{
			VOEstante nEst = superAndes.adicionarEstante(espacio, idSucursal, cantidadMin);
			String resultado = "Se registro el estante exitosamente.";
			resultado += "\n" + nEst.toString();
			panelDatos.actualizarInterfaz(resultado);
		}  	
		catch (Exception e) 
		{
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void registrarPromocion()
	{
		Timestamp fechaInic = Timestamp.valueOf(JOptionPane.showInputDialog(this,"Ingrese la fecha de inicio de la promoción: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE));
		Timestamp fechaFin = Timestamp.valueOf(JOptionPane.showInputDialog(this,"Ingrese la fecha de fin de la promoción: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE));
		String tipoPromo = JOptionPane.showInputDialog(this,"Ingrese el tipo de la promoción: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE);
		String estado = JOptionPane.showInputDialog(this,"Ingrese el estado de la promocion: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE);		
		
		try 
		{
			VOPromocion nProm = superAndes.adicionarPromo(fechaInic,fechaFin,tipoPromo, estado);
			String resultado = "Se registro la promoción exitosamente.";
			resultado += "\n" + nProm.toString();
			panelDatos.actualizarInterfaz(resultado);
		}  	
		catch (Exception e) 
		{
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void finalizarPromocion()
	{
		long idPromo = Long.parseLong(JOptionPane.showInputDialog(this,"Ingrese el identificador de la promocion a actualizar: ", "SuperAndes", JOptionPane.QUESTION_MESSAGE));
		
		try 
		{
			VOPromocion nProm = superAndes.actualizarPromo(idPromo);
			String resultado = "Se actualizao la promoción exitosamente.";
			resultado += "\n" + nProm.toString();
			panelDatos.actualizarInterfaz(resultado);
		}  	
		catch (Exception e) 
		{
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void darDineroRecolectado()
	{
		try 
		{
			List<VOReqFunCon1> lista = superAndes.darVoDineroRecolectado();

			String resultado = "El dinero recolectado por sucursal es: ";
			resultado +=  "\n" + listarDineroRecolectado(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void darIndiceDeOcupacionBod()
	{
		try 
		{
			List<VOReqFunCon3> lista = superAndes.darVoIndiceDeOcupacionBod();

			String resultado = "El indice de ocupación de la bodega es: ";
			resultado +=  "\n" + listarIndiceDeOcupacionBod(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void darIndiceDeOcupacionEst()
	{
		try 
		{
			List<VOReqFunCon3p1> lista = superAndes.darVoIndiceDeOcupacionEst();

			String resultado = "El indice de ocupación de los estantes es: ";
			resultado +=  "\n" + listarIndiceDeOcupacionEst(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void listarBodega()
	{
		try 
		{
			List <VOBodega> lista = superAndes.darVOBodega();

			String resultado = "En listarProductos";
			resultado +=  "\n" + listarBodega(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
		catch (Exception e)
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void listarProducto()
	{
		try 
		{
			List <VOProducto> lista = superAndes.darVOProductos();

			String resultado = "En listarProductos";
			resultado +=  "\n" + listarProductos(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
		catch (Exception e)
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void listarEmpresa()
	{
		try 
		{
			List <VOEmpresa> lista = superAndes.darVOEmpresas();

			String resultado = "En listarEmpresas";
			resultado +=  "\n" + listarEmpresas(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
		catch (Exception e)
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void listarEstante()
	{
		try 
		{
			List <VOEstante> lista = superAndes.darVOEstantes();

			String resultado = "En listarEmpresas";
			resultado +=  "\n" + listarEstantes(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
		catch (Exception e)
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void listarCiudades()
	{
    	try 
    	{
			List <VOCiudad> lista = superAndes.darVOCiudades();

			String resultado = "En listarCiudades";
			resultado +=  "\n" + listarCiudades (lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void listarProveedores()
	{
    	try 
    	{
			List <VOProveedor> lista = superAndes.darVOProveedores();

			String resultado = "En listarProveedores";
			resultado +=  "\n" + listarProveedores(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
    	catch (Exception e) 
    	{
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
    

	/* ****************************************************************
	 * 			Métodos privados para la presentación de resultados y otras operaciones
	 *****************************************************************/
    
    private String listarBodega (List<VOBodega> lista) 
    {
    	String resp = "Los Productos existentes son:\n";
    	int i = 1;
        for (VOBodega bod : lista)
        {
        	resp += i++ + ". " + bod.toString() + "\n";
        }
        return resp;
	}
    
    private String listarProductos (List<VOProducto> lista) 
    {
    	String resp = "Los Productos existentes son:\n";
    	int i = 1;
        for (VOProducto prod : lista)
        {
        	resp += i++ + ". " + prod.toString() + "\n";
        }
        return resp;
	}
    
    private String listarEmpresas (List<VOEmpresa> lista) 
    {
    	String resp = "Las Empresas existentes son:\n";
    	int i = 1;
        for (VOEmpresa empr : lista)
        {
        	resp += i++ + ". " + empr.toString() + "\n";
        }
        return resp;
	}
    
    private String listarEstantes (List<VOEstante> lista) 
    {
    	String resp = "Las Empresas existentes son:\n";
    	int i = 1;
        for (VOEstante est : lista)
        {
        	resp += i++ + ". " + est.toString() + "\n";
        }
        return resp;
	}
    
    private String listarProveedores(List<VOProveedor> lista) 
    {
    	String resp = "Los proveedores son:\n";
    	int i = 1;
        for (VOProveedor proveedor : lista)
        {
        	resp += i++ + ". " + proveedor.toString() + "\n";
        }
        return resp;
	}
    
    private String listarDineroRecolectado(List<VOReqFunCon1> lista)
    {
    	String resp = "El dinero recolectado es:\n";
    	int i = 1;
        for (VOReqFunCon1 rfc : lista)
        {
        	resp += i++ + ". " + rfc.toString() + "\n";
        }
        return resp;
    }
    
    private String listarIndiceDeOcupacionBod(List<VOReqFunCon3> lista)
    {
    	String resp = "El indice de ocupacion de la bodega es:\n";
    	int i = 1;
        for (VOReqFunCon3 rfc : lista)
        {
        	resp += i++ + ". " + rfc.toString() + "\n";
        }
        return resp;
    }
    
    private String listarIndiceDeOcupacionEst(List<VOReqFunCon3p1> lista)
    {
    	String resp = "El indice de ocupacion de los estantes es:\n";
    	int i = 1;
        for (VOReqFunCon3p1 rfc : lista)
        {
        	resp += i++ + ". " + rfc.toString() + "\n";
        }
        return resp;
    }
      
    /**
     * Genera una cadena de caracteres con la lista de bebidas recibida: una línea por cada bebida
     * @param lista - La lista con las bebidas
     * @return La cadena con una líea para cada bebida recibida
     */
    private String listarBebidas (List<VOPromocion> lista) 
    {
    	String resp = "Las bebidas existentes son:\n";
    	int i = 1;
        for (VOPromocion beb : lista)
        {
        	resp += i++ + ". " + beb.toString() + "\n";
        }
        return resp;
	}

    /**
     * Genera una cadena de caracteres con la lista de Ciudades recibida: una línea por cada Ciudad
     * @param lista - La lista con los Ciudades
     * @return La cadena con una líea para cada Ciudad recibido
     */
    private String listarCiudades (List<VOCiudad> lista) 
    {
    	String resp = "Las Ciudades existentes son:\n";
    	int i = 1;
        for (VOCiudad ciudad : lista)
        {
        	resp += i++ + ". " + ciudad.toString() + "\n";
        }
        return resp;
	}

    /**
     * Genera una cadena de caracteres con la lista de bares recibida: una línea por cada bar
     * @param lista - La lista con los bares
     * @return La cadena con una líea para cada bar recibido
     */
    private String listarBares (List<VOEmpresa> lista) 
    {
    	String resp = "Los bares existentes son:\n";
    	int i = 1;
        for (VOEmpresa bar : lista)
        {
        	resp += i++ + ". " + bar.toString() + "\n";
        }
        return resp;
	}

    /**
     * Genera una cadena de caracteres con la lista de gustan recibida: una línea por cada gusta
     * @param lista - La lista con los gustan
     * @return La cadena con una líea para cada gustan recibido
     */
    private String listarGustan (List<VOBodega> lista) 
    {
    	String resp = "Los gustan existentes son:\n";
    	int i = 1;
        for (VOBodega serv : lista)
        {
        	resp += i++ + ". " + serv.toString() + "\n";
        }
        return resp;
	}

    /**
     * Genera una cadena de caracteres con la lista de visitan recibida: una línea por cada visitan
     * @param lista - La lista con los visitan
     * @return La cadena con una líea para cada visitan recibido
     */
    private String listarVisitan (List<VOSupermercado> lista) 
    {
    	String resp = "Los visitan existentes son:\n";
    	int i = 1;
        for (VOSupermercado vis : lista)
        {
        	resp += i++ + ". " + vis.toString() + "\n";
        }
        return resp;
	}

    /**
     * Genera una cadena de caracteres con la lista de parejas de números recibida: una línea por cada pareja
     * @param lista - La lista con las pareja
     * @return La cadena con una líea para cada pareja recibido
     */
    private String listarBaresYBebidas (List<long[]> lista) 
    {
    	String resp = "Los bares y el número de bebidas que sirven son:\n";
    	int i = 1;
        for ( long [] tupla : lista)
        {
			long [] datos = tupla;
	        String resp1 = i++ + ". " + "[";
			resp1 += "idBar: " + datos [0] + ", ";
			resp1 += "numBebidas: " + datos [1];
	        resp1 += "]";
	        resp += resp1 + "\n";
        }
        return resp;
	}

    /**
     * Genera una cadena de caracteres con la lista de parejas de objetos recibida: una línea por cada pareja
     * @param lista - La lista con las parejas (Ciudad, numero visitas)
     * @return La cadena con una línea para cada pareja recibido
     */
    private String listarCiudadYNumVisitas (List<Object[]> lista) 
    {
    	String resp = "Los Ciudades y el número visitas realizadas son:\n";
    	int i = 1;
        for (Object [] tupla : lista)
        {
			VOCiudad bdor = (VOCiudad) tupla [0];
			int numVisitas = (int) tupla [1];
	        String resp1 = i++ + ". " + "[";
			resp1 += bdor + ", ";
			resp1 += "numVisitas: " + numVisitas;
	        resp1 += "]";
	        resp += resp1 + "\n";
        }
        return resp;
	}

    /**
     * Genera una cadena de caracteres con la descripción de la excepcion e, haciendo énfasis en las excepcionsde JDO
     * @param e - La excepción recibida
     * @return La descripción de la excepción, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
     */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}

	/**
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicación
	 * @param e - La excepción generada
	 * @return La cadena con la información de la excepción y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecución\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y superAndes.log para más detalles";
		return resultado;
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */
	private boolean limpiarArchivo(String nombreArchivo) 
	{
		BufferedWriter bw;
		try 
		{
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) 
		{
//			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Abre el archivo dado como parámetro con la aplicación por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)
	{
		try
		{
			Desktop.getDesktop().open(new File(nombreArchivo));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/* ****************************************************************
	 * 			Métodos de la Interacción
	 *****************************************************************/
    /**
     * Método para la ejecución de los eventos que enlazan el menú con los métodos de negocio
     * Invoca al método correspondiente según el evento recibido
     * @param pEvento - El evento del usuario
     */
    @Override
	public void actionPerformed(ActionEvent pEvento)
	{
		String evento = pEvento.getActionCommand( );		
        try 
        {
			Method req = InterfazSuperAndes.class.getMethod ( evento );			
			req.invoke ( this );
		} 
        catch (Exception e) 
        {
			e.printStackTrace();
		} 
	}
    
	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
    /**
     * Este método ejecuta la aplicación, creando una nueva interfaz
     * @param args Arreglo de argumentos que se recibe por línea de comandos
     */
    public static void main( String[] args )
    {
        try
        {
        	
            // Unifica la interfaz para Mac y para Windows.
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
            InterfazSuperAndes interfaz = new InterfazSuperAndes( );
            interfaz.setVisible( true );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
    }
}
