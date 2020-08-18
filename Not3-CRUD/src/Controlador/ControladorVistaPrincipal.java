/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Configuracion;
import Modelo.Imprimir;
import Modelo.MetodosBBDD_Consultas;
import Modelo.MetodosPrincipal;
import Vistas.PanelMenuBar;
import Vistas.PanelTextArea;
import Vistas.Panel_Pestanias;
import Vistas.VistaBBDD_Consultar;
import Vistas.VistaBBDD_Eliminar;
import Vistas.VistaBBDD_Insertar;
import Vistas.VistaBBDD_Modificar;
import Vistas.VistaPrincipal;
import static Vistas.VistaPrincipal.panelBase;
import Vistas.Vista_LOGIN_BBDD;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static java.awt.image.ImageObserver.HEIGHT;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Delayed;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.TransferHandler;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.Caret;

/**
 *
 * @author davidf
 */
public class ControladorVistaPrincipal {

    //Declarando OBJETOS de VistaPrincipal(el marco de la aplicación) y los Metodos(modelo)
    private final VistaPrincipal vistaPrincipal;
    private final MetodosPrincipal metodosPrincipal;
    //Declarando OBJETOS de PANELES QUE CONFORMA LA VistaPrincipal
    public PanelMenuBar panelMenuBar; //Panel que contiene el JMenuBar
    public static Panel_Pestanias panelPestanias; //Panel que contiene el JTabbedPane
    public PanelTextArea panelTA; //este pane lo utilizaremos para recuperar el PANELTEXTAREA (que está contenida en algunas pestañas) que corresponda a la pestaña que hemos seleccionado
    public static JTextArea texAreaExportar; //este AREA, lse usará para recuperar el contenido de la consulta realizada, y que pueda recogerse para insertarla en la pestaña que este vigente, si se desea
    public static JButton ExportarConsultaAPestania; //este Boton, lse usará para recuperar el contenido de la consulta realizada, y que pueda recogerse para insertarla en la pestaña que este vigente, si se desea

    private VistaBBDD_Insertar vistaInsertar;
    private VistaBBDD_Consultar vistaConsultar;
    private VistaBBDD_Modificar vistaModificar;
    private VistaBBDD_Eliminar vistaEliminar;
    private MetodosBBDD_Consultas metodos;

    private ControladorBBDD_Insertar Controlador_Insertar;

    private ControladorBBDD_Consultar Controlador_Consultar;

    private ControladorBBDD_Eliminar Controlador_Eliminar;

    private ControladorBBDD_Modificar Controlador_Modificar;

    //Declarando objetos y variables necesarias para poder cambiar el estilo y formato de los TextArea
    Font fuente;
    Color color;
    int tamanio_fuente;
    int tipo_fuente;
    String nombre_fuente;
    String[] fontNames;//Estos métodos rellenarán el Array con los estilos de texto disponibles en nuestro sistema
    Color colorBackground;
    Color colorTexto;
    Color colorSeleccion;
    Color colorTextoSeleccionado;
    int indexPestana; //En esta variable iremos almacenando el INDEX de la pestaña en la que nos encontramos

    //variable que recibirá la ruta del archivo en caso de querer abrir el programa directamente con este
    private String rutaArchivo = "";

    //Declarando variables para GUARDAR las "OPCIONES VISUALES" del usuario (color texto etc..)
    //Variables para Realizar el LOGIN (rescataremos esta info si existe guardada en el fichero de configuracion)
    public static String BBDDurl = "localhost";
    public static String BBDDpuerto = "3306";
    public static String BBDDname = "Not3Pad";
    public static String BBDDusuario = "not3pad";
    public static String BBDDpass = "admin";
    public static boolean BBDDaceptar = false; //Con esta variable, determinaremos si el usuario le da a aceptar o a cancelar en el LOGIN

    //CONSTRUCTOR NORMAL
    public ControladorVistaPrincipal(VistaPrincipal vistaPrincipal, MetodosPrincipal metodosPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
        this.metodosPrincipal = metodosPrincipal;

        //Ejecutamos el método iniciar que iniciará todos los componentes
        Iniciar();
    }//FIN DEL CONSTRUCTOR NORMAL

    //CONSTRUCTOR RECIBIENDO RUTA DE ARCHIVO
    public ControladorVistaPrincipal(VistaPrincipal vistaPrincipal, MetodosPrincipal metodosPrincipal, String rutaArchivo) {
        this.vistaPrincipal = vistaPrincipal;
        this.metodosPrincipal = metodosPrincipal;
        this.rutaArchivo = rutaArchivo;

        //Ejecutamos el método iniciar que iniciará todos los componentes
        Iniciar();
    }

    private void Iniciar() {

        this.panelMenuBar = new PanelMenuBar();
        this.panelPestanias = new Panel_Pestanias();
        this.fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(); //Estos métodos rellenarán el Array con los estilos de texto disponibles en nuestro sistema
        this.ExportarConsultaAPestania = new JButton("Exportar a Pestaña");
        this.texAreaExportar = new JTextArea("");

        /////// FIM DE CONTROLADORES Y VISTAS DE LA BBDD////////////////////////////////////////
        //Configuramos el layout del panelMenuBar a Border, y agregamos los paneles
        vistaPrincipal.panelBase.setLayout(new BorderLayout());

//Seteamos el tamaño del panel que contiene el MENU (ya que si no no se muestra correctamente el componente)
        //, dandole de ancho el mismo que tenga de X el panelBase 
        panelMenuBar.setPreferredSize(new Dimension(panelBase.getX(), 20));
        //Agregamos el panel que contiene el MENU
        vistaPrincipal.panelBase.add(panelMenuBar, BorderLayout.PAGE_START);

//Agregamos el panel que contiene el TextArea
        vistaPrincipal.panelBase.add(panelPestanias.TP, BorderLayout.CENTER);
        //Agregamos a vista principal su propio panelBase

        vistaPrincipal.getContentPane().add(vistaPrincipal.panelBase);

        vistaPrincipal.pack();

        //Implementando OYENTE para detectar Drag&DROP
        vistaPrincipal.setTransferHandler(new FileDropHandler());

        //Agregando LISTENERS de todos los botones existentes en el PanelMENUBAR
        panelMenuBar.nuevo.addActionListener(new OyenteNuevo());
        panelMenuBar.abrir.addActionListener(new OyenteAbrir());
        panelMenuBar.guardar.addActionListener(new OyenteGuardar());
        panelMenuBar.guardarComo.addActionListener(new OyenteGuardarComo());
        panelMenuBar.renombrar.addActionListener(new OyenteRenombrar());
        panelMenuBar.ir_A.addActionListener(new OyenteIrA());
        panelMenuBar.deshacer.addActionListener(new OyenteDeshacer());
        panelMenuBar.rehacer.addActionListener(new OyenteRehacer());
        panelMenuBar.copiar.addActionListener(new OyenteCopiar());
        panelMenuBar.pegar.addActionListener(new OyentePegar());
        panelMenuBar.cortar.addActionListener(new OyenteCortar());
        panelMenuBar.insertarFecha.addActionListener(new OyenteInsertarFecha());
        panelMenuBar.buscar.addActionListener(new OyenteBuscar());
        panelMenuBar.buscarYreemplazar.addActionListener(new OyenteBuscarYReemplazar());
        panelMenuBar.imprimir_configurando.addActionListener(new OyenteImprimirConfigurando());
        panelMenuBar.imprimir_directo.addActionListener(new OyenteImprimirDirecto());
        panelMenuBar.acercaDe.addActionListener(new OyenteAcercaDe());
        panelMenuBar.salir.addActionListener(new OyenteSalir());
        panelMenuBar.colorBackground.addActionListener(new OyenteColorBackground());
        panelMenuBar.colorFuente.addActionListener(new OyenteColorFuente());
        panelMenuBar.colorSeleccion.addActionListener(new OyenteColorSeleccion());
        panelMenuBar.colorTextoSeleccionado.addActionListener(new OyenteColorTextoSeleccionado());
        panelMenuBar.guardarConfiguracion.addActionListener(new OyenteGuardarConfiguracion());

        //Insertando OYENTES DE OBJETOS a los CheckBox
        panelMenuBar.conectar_desconectar.addActionListener(new OyenteBBDD_Conectar_Desconectar());
        panelMenuBar.insertar.addItemListener(new OyenteBBDD_Insertar());
        panelMenuBar.consultar.addItemListener(new OyenteBBDD_Consultar());
        panelMenuBar.modificar.addItemListener(new OyenteBBDD_Modificar());
        panelMenuBar.eliminar.addItemListener(new OyenteBBDD_Eliminar());
        //Agregamos OYENTE al JTabbedPane, para saber a qué pestaña ha cambiado (y poder recuperar su componente para trabajar con el o lo que queramos)
        panelPestanias.TP.addChangeListener(new OyenteCambioPestana());

        ExportarConsultaAPestania.addActionListener(new OyenteExportarConsulta());

        //RELLENANDO COMBOBOXES
        panelMenuBar.comboBoxTipo.addItem("Normal");
        panelMenuBar.comboBoxTipo.addItem("Bold");
        panelMenuBar.comboBoxTipo.addItem("Italic");
        panelMenuBar.comboBoxTipo.addItem("B+I");
        // Accion a realizar cuando el JComboBox cambia de item seleccionado.
        panelMenuBar.comboBoxTipo.addActionListener(new OyenteComboTipo());

        //Añadiendo y cargando ComboBox de TAMAÑO LETRA
        for (int i = 0; i < 100; i++) {
            panelMenuBar.comboBoxTamanio.addItem(i);
        }
        // Accion a realizar cuando el JComboBox cambia de item seleccionado.
        panelMenuBar.comboBoxTamanio.addActionListener(new OyenteComboTamanio());

        ///AÑADIENDO COMBOBOX PARA ESTILO DE LETRA
        //Mostrar un listado con las fuentes DISPONIBLES
        //Recorremos el array de FontNames para rellenar el comboBoxTamanio con todos los estilos de letra disponibles 
        for (String fontName : fontNames) {
            panelMenuBar.comboBoxStyle.addItem(fontName);
        }
        panelMenuBar.comboBoxStyle.addActionListener(new OyenteComboStyle());

        //Desactivamos los botones porque no Existirá ninguna pestaña abierta
        panelMenuBar.guardar.setEnabled(false);
        panelMenuBar.guardarComo.setEnabled(false);
        panelMenuBar.renombrar.setEnabled(false);
        panelMenuBar.edicion.setEnabled(false);
        panelMenuBar.imprimir.setEnabled(false);
        panelMenuBar.personalizar.setEnabled(false);
        panelMenuBar.comboBoxTamanio.setEnabled(false);
        panelMenuBar.comboBoxTipo.setEnabled(false);
        panelMenuBar.comboBoxStyle.setEnabled(false);

        //DESHABILITANDO BOTONES DE BBDD
        panelMenuBar.insertar.setEnabled(false);
        panelMenuBar.consultar.setEnabled(false);
        panelMenuBar.modificar.setEnabled(false);
        panelMenuBar.eliminar.setEnabled(false);

        //COMPROBAMOS SI SE HA PASADO UN FICHERO PARA INICIAR EL PROGRAMA O NO
        //DEPENDIENDO DE ESTO, ABRIREMOS UNA PESTAÑA VACÍA O ABRIREMOS LE FICHERO
        //Si no se ha recibido ruta, abrimos una pestaña nueva,
        if (rutaArchivo.equals("")) {
            panelPestanias = panelPestanias.PestaniaTextoNueva();
            Recargar();
            //Si se ha recibido ruta, abrimos una pestaña nueva cargandole el archivo
        } else {
            //Creamos una pestaña nueva utilizando el método que ESTA INLUCIDO EN LA CLASE Panel_Pestanias
                File file = new File(rutaArchivo);
                AbrirArchivo(file);
                
        }
        //Agregando METODO para CERRAR VENTANA DEPENDIENDO SI HAY ALGO ABIERTO, no, o sin guardar, etc
        cerrar();
        Recargar();

    }//Fin de iniciar

    public void cerrar() {

        try {
            vistaPrincipal.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            vistaPrincipal.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    AlertarSalida();
                }
            });
            vistaPrincipal.setVisible(true);

        } catch (Exception e) {
            e.getMessage();
        }

    }//Fin del metodo CERRAR.

    public void AlertarSalida() {

        //Esta variable controlará si existe algún archivo sin guardar y cuantos, servirá para ir darle un indice a los objetos en el ARRAY, para saber si lanzar la notificación
        //y para incluir los titulos luego en la notificación
        int ficherosSinGuardar = 0;
        //en este ARRAY, almacenaremos los nombres de los archivos que están sin guardar, para mostrarselos al usuario
        String[] titulosArchivosNoGuardados = new String[panelPestanias.TP.getTabCount()];//La dimension se la daremos según el contador de pestañas, para no quedartnos cortos y no pasarnos mucho

        for (int i = 0; i < panelPestanias.TP.getTabCount(); i++) {

            String textoFichero = "";//Esta variable almacenará el contenido del fichero para poder compararlo con el del TextArea

            //Recojemos el componente "panelTextArea" de la pestaña seleccionada
            PanelTextArea panelTAaux = (PanelTextArea) panelPestanias.TP.getComponentAt(i);

            //    System.out.println(panelTAaux.fichero.toString());
            //Comprobamos si el archivo almacenado es distinto al contenido de nuestro textarea, en este caso,
            //Ofreceremos guardar el documento a nuestro usuario
            if (panelTAaux.fichero.length() > 0) {
                try (
                        //procedemos a crear el flujo y el lector, para leer nuestro fichero seleccionado
                        //y poder almacenarlo para COMPARARLO   CON LO QUE HAY ESCRITO EN NUESTRO TEXTAREA
                        FileReader flujo = new FileReader(panelTAaux.fichero)) {
                    try (Scanner lector = new Scanner(flujo)) {

                        int cont = 0;//esta variable es para imprimir o no los saltos de linea, para que no altere el archivo original
                        while (lector.hasNext()) {

                            if (cont == 0) {
                                textoFichero += (lector.nextLine());
                            } else {
                                textoFichero += ("\n" + lector.nextLine());
                            }
                            cont++;
                        }//Fin del WHILE
                        lector.close();
                        flujo.close();
                    }

                } catch (IOException ex) {
                    Logger.getLogger(Panel_Pestanias.class.getName()).log(Level.SEVERE, null, ex);
                }
            }//FIn del IF

            //Si el contenido del fichero es distinto al contenido del TextArea de su misma pestaña, sabremos que el archivo está sin guardar
            //Por lo que almacenaremos el nombre del archivo e incrementaremos un contador para saber que no está guardado
            if (!textoFichero.equals(panelTAaux.textArea.getText())) {

                titulosArchivosNoGuardados[ficherosSinGuardar] = panelTAaux.fichero.getName();//Almacenando el nombre del archivo en el array
                ficherosSinGuardar++;

            }//Fin del iF-EQUALS

        }//FIN DEL FOR

        String titulosTotal = ""; //En esta variable almacenaremos cada nombre de archivo sin guardar, con un salto de linea, para que salgan a lo largo (si existe alguno)

        for (int i = 0; i < ficherosSinGuardar; i++) {

            titulosTotal += "--> " + titulosArchivosNoGuardados[i] + "\n";

        }

        //Se comprobará si existe el fichero de configuración, si no existe, se creará uno por primera vez.
        File fichero = new File("./$Not3CRUD_Config.conf");
        if (!fichero.exists()) {
            JOptionPane.showMessageDialog(Panel_Pestanias.TP, "No existe ningun archivo de configuración.\n\nSe utilizará la configuración de la pestaña actual\ncomo \"TEMA\" predeterminado ", "Creando Configuración", JOptionPane.INFORMATION_MESSAGE);

            //Ejecutando al metodo guardar configuracion
            if (GuardarConfiguración()) {
                JOptionPane.showMessageDialog(Panel_Pestanias.TP, "Configuración GUARDADA con Éxito", "GUARDADO", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(Panel_Pestanias.TP, "Configuración NO GUARDADA", "ERROR", JOptionPane.ERROR_MESSAGE);
            }//Fin del If-Else

        }//Fin de comrpobar si existe fichero de configuración

        //Si existe algun fichero sin guardar, se notificará al usuario para que los guarde manualmente
        //Se le indicará de los nombres de cada uno de ellos.
        if (ficherosSinGuardar > 0) {

            int valor = JOptionPane.showConfirmDialog(Panel_Pestanias.TP, "¿Desea SALIR sin GUARDAR?\n\nHay " + ficherosSinGuardar + " Archivo SIN GUARDAR:\n" + titulosTotal, "¿CERRAR PROGRAMA?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (valor == JOptionPane.YES_OPTION) {
                //SI el usuario eligio ACEPTAR, GUARDAREMOS EL "TP" EN EL ARCHIVO y  otras opciones y SALDREMOS DEL PROGRAMA

                System.exit(0);

            }//Fin del if valor==YES_OPTION

            //Si el usuario elige CANCELAR, no se cerrará el programa
        } else {

            System.exit(0);
        }//Fin del IF-EQUALS

    }//Fin de confirmar salida    

    private boolean GuardarConfiguración() {

        //Declaramos objeto de configuración, segun si el archivo es nuevo o no se seteará este objeto
        Configuracion config = null;

        try {

            File fichero = new File("./$Not3CRUD_Config.conf");

            if (!fichero.exists()) {
                fichero.createNewFile();
                config = new Configuracion(fuente, Color.WHITE, colorTexto, colorSeleccion, colorTextoSeleccionado);
            } else {
                fichero.delete();
                System.gc();
                fichero.createNewFile();
                config = new Configuracion(fuente, colorBackground, colorTexto, colorSeleccion, colorTextoSeleccionado);

            }

            ObjectOutputStream escritor;
            //Si NO tiene registros, creamos "objeto" escritor con la Clase NORMAL. para escribir la cabecera del objeto.
            try (FileOutputStream flujo = new FileOutputStream(fichero)) {
                //Si NO tiene registros, creamos "objeto" escritor con la Clase NORMAL. para escribir la cabecera del objeto.
                escritor = new ObjectOutputStream(flujo);
                escritor.writeObject(config);
                //        System.out.println("guardado");
                //Cerramos flujo y escritor
                flujo.close();
            }
            escritor.close();

            //En este metodo no controlaremos el EOF, porque solo vamos a escribir y los va a ir añadiendo directamente al final 
        } catch (IOException ioe) {
            System.out.println("Error de Fichero");
        } catch (Exception e) {
            System.out.println(e.initCause(e));
        }//Fin del TRY_CATCH

        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * ESTE ES EL Oyente para DETECTAR en QUÉ PESTAÑA se encuentra nuestro
     * "puntero" cada vez que detecte un cambio de pestaña, recuperará el
     * TextArea de la pestaña correspondiente para poder trabajar en este y
     * aplicarle su configuración correspondiente a los combobox, etc.
     */
    class OyenteCambioPestana implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent ce) {
            //Creando objeto de PanelTextAREA QUE APUNTA  al PanelTextArea que tenemos en LA PESTAÑA ACTUAL
            //Por lo tanto estaremos actuando directamente en la pestaña seleccionada
            try {
                panelTA = (PanelTextArea) panelPestanias.TP.getSelectedComponent();
                indexPestana = panelPestanias.TP.getSelectedIndex();
                Recargar();
            } catch (Exception e) {
                // System.out.println(e.getMessage());
            }//Fin del tryCatch

        }

    }//Fin oyenteCambioPestaña

    /**
     * Este metodo, se ejecutará cada vez que el OyentePestana detecte un cambio
     * en este, se comprobará si existe alguna pestaña en caso que no existe, se
     * limitarán algunos botones en caso de que exista almenos una, se
     * desbloquearan los botones y se cargará en PanelTA de esta clase pasandole
     * el PanelTexArea de la pestaña que está seleccionada actualmente para
     * poder trabajar con esta, con todas sus propiedades etc..
     */
    private void Recargar() {

        //Comprobamos si existe alguna pestaña
        if (Panel_Pestanias.TP.getTabCount() > 0) {

            //Recuperando FICHERO/ARCHIVO correspondiente
            //Recuperamos LOS COLORES de Texto,Background,seleccion, texto seleccionado Correspondientes al TextArea
            //Color del Background
            Color recuperando_color;//Variable en la que obtendremos el RGB y lo transformaremos a COLOR

            recuperando_color = new Color(panelTA.textArea.getBackground().getRGB());
            colorBackground = recuperando_color;
            //Color del Foreground
            recuperando_color = new Color(panelTA.textArea.getForeground().getRGB());
            colorTexto = recuperando_color;
            //Color del TextoSeleccionado
            recuperando_color = new Color(panelTA.textArea.getSelectedTextColor().getRGB());
            colorTextoSeleccionado = recuperando_color;
            //Color dela Seleccion
            recuperando_color = new Color(panelTA.textArea.getSelectionColor().getRGB());
            colorSeleccion = recuperando_color;

            //Recuperamos la fuente correspondiente al TextArea de nuestro objeto PanelTextArea
            fuente = panelTA.textArea.getFont();

            //Extraemos el nombre de la fuente y lo almacenamos en la variable que tenemos instanciada
            nombre_fuente = fuente.getName();
            //Y extraemos el tamaño de la fuente y lo almacenamos en la variable que tenemos instanciada
            tamanio_fuente = fuente.getSize();
            //Extraemos el TIPO de la fuente y lo almacenamos.
            tipo_fuente = fuente.getStyle();

            //Ahora setearemos los comboBoxTamanio con los datos Extraidos
            panelMenuBar.comboBoxTamanio.setSelectedItem(tamanio_fuente); //Agregaremos este primer elemento con el valor inicial que tendrá
            panelMenuBar.comboBoxStyle.setSelectedItem(nombre_fuente); //Agregaremos este primer elemento con el valor inicial que tendrá
            panelMenuBar.comboBoxTipo.setSelectedIndex(tipo_fuente);//Le indicaremos que se seleccione el INDEX, ya que el Orden del index pertenece a los numeros correspondientes a los nombres del estilo de la letra
            //ACTIVAMOS los botones porque no Existirá ninguna pestaña abierta
            panelMenuBar.guardar.setEnabled(true);
            panelMenuBar.guardarComo.setEnabled(true);
            panelMenuBar.renombrar.setEnabled(true);
            panelMenuBar.edicion.setEnabled(true);
            panelMenuBar.imprimir.setEnabled(true);
            panelMenuBar.personalizar.setEnabled(true);
            panelMenuBar.comboBoxTamanio.setEnabled(true);
            panelMenuBar.comboBoxTipo.setEnabled(true);
            panelMenuBar.comboBoxStyle.setEnabled(true);

        } else {
            //Desactivamos los botones porque no Existirá ninguna pestaña abierta
            panelMenuBar.guardar.setEnabled(false);
            panelMenuBar.guardarComo.setEnabled(false);
            panelMenuBar.renombrar.setEnabled(false);
            panelMenuBar.edicion.setEnabled(false);
            panelMenuBar.imprimir.setEnabled(false);
            panelMenuBar.personalizar.setEnabled(false);
            panelMenuBar.comboBoxTamanio.setEnabled(false);
            panelMenuBar.comboBoxTipo.setEnabled(false);
            panelMenuBar.comboBoxStyle.setEnabled(false);

        }//Find el ifelse

    }//Fin del metodo Recargar

    ////////////////////////////////////////////////////////////////////////////////   
    ////////LISTENERS de los COMBOBOX correspondientes a TIPO, TAMAÑO y ESTILO////////////   
    ////////////////////////////////////////////////////////////////////////////////  
    //Combobox de tamaño
    class OyenteComboTipo implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            //Almacenamos los datos del combobox en la variable instanciada, para utilizarla cuando sea necesario
            tipo_fuente = panelMenuBar.comboBoxTipo.getSelectedIndex();

            fuente = new Font(nombre_fuente, tipo_fuente, tamanio_fuente);
            panelTA.textArea.setFont(fuente); // Coneste metodo realizamos el cambio de estilo a nuestro textarea del objeto de PanelTextArea correspondiente a la pestaña

        }//Fin action performed
    }//Fin del Oyente

    //Combobox de tamaño
    class OyenteComboTamanio implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            //Almacenamos los datos del combobox en la variable instanciada, para utilizarla cuando sea necesario
            tamanio_fuente = Integer.parseInt(panelMenuBar.comboBoxTamanio.getSelectedItem().toString());

            fuente = new Font(nombre_fuente, tipo_fuente, tamanio_fuente);
            panelTA.textArea.setFont(fuente); // Coneste metodo realizamos el cambio de estilo a nuestro textarea del objeto de PanelTextArea correspondiente a la pestaña

        }//Fin action performed
    }//Fin del Oyente

    //Combobox de estilo de fuente
    class OyenteComboStyle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ

            //Recogemos el estilo que el usuario elija en el panel, para SETEAR nuestro textArea con esa opción
            nombre_fuente = panelMenuBar.comboBoxStyle.getSelectedItem().toString();

            fuente = new Font(nombre_fuente, tipo_fuente, tamanio_fuente);
            panelTA.textArea.setFont(fuente); // sólo va a cambiar el tamaño a 12 puntos

        }//Fin action performed
    }//Fin del Oyente

    ////////////////////////////////////////////////////////////////////////////////    
    //Fin de los listeners de COMBOBOX   
    ////////////////////////////////////////////////////////////////////////////////   
    //////// LISTENERS de los JCHECKBOX correspondientes al Menú BBDD   ////////////   
    ////////////////////////////////////////////////////////////////////////////////   
    //Listener de BBDD-CONECTAR
    class OyenteBBDD_Conectar_Desconectar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            //INICIALIZANDO VISTAS Y CONTROLADORES DE LAS CONSULTAS, PASAR A UNA FUNCION SOLA, QUE SEA LLAMADA UNA SOLA VEZ
            //SE UTILIZARÁ UNA VARIABLE BOOLEANA PARA DETECTAR SI YA SE HA INICIADO o NO y respecto a esta, se modificará el botón y sus funciones
            if (!MetodosBBDD_Consultas.BBDD_Conectado) {

                //Recuperamos los datos de la conexión almacenada en el archivo de configuración en caso de que exista
                File fichero = new File("./$Not3CRUD_BBDD_Config.conf");
                //Si el fichero existe, trataremos de leer todos los datos que deben existir y si todo está correcto, cambiaremos el valor de las variables BBDD que tenemos instanciada por las de nuestro archivo de configuración.
                if (fichero.exists()) {

                    try {
                        FileReader flujo = new FileReader(fichero);//Creamos el objeto de filereader
                        Scanner lector = new Scanner(flujo); //y creamos el objeto scanner con el que leeremos la salida de nuestro fichero

                        //Si el fichero existe, leeremos de seguido todos los datos, en caso de error en el fichero, lo tomaremos como inexistente
                        lector.reset();//Reseteamos el (contador de lineas)

                        String BBDDurl_aux = lector.nextLine();
                        String BBDDpuerto_aux = lector.nextLine();
                        String BBDDname_aux = lector.nextLine();
                        String BBDDusuario_aux = lector.nextLine();
                        String BBDDpass_aux = lector.nextLine();

                        //Cerramos el archivo
                        lector.close();

                        //Encaso de que todo haya ido bien, Setearemos los datos instanciados de la conexion con los datos Almacenados en nuestro archivo
                        BBDDurl = BBDDurl_aux;
                        BBDDpuerto = BBDDpuerto_aux;
                        BBDDname = BBDDname_aux;
                        BBDDusuario = BBDDusuario_aux;
                        BBDDpass = BBDDpass_aux;

                        //EN CASO DE PRODUCIRSE UN ERROR TOMAREMOS EL ARCHIVO COMO INVÄLIDO  
                    } catch (IOException ioe) {
                        ioe.getMessage();
                    } catch (Exception e) {
                        e.getMessage();
                    }//Fin del Try-Catch

                    //Si no existe el archivo, comprenderemos que existe un cambio   
                }

                //////////////////////////////////////////////////////////////////////////////      
                Vista_LOGIN_BBDD login = new Vista_LOGIN_BBDD(vistaPrincipal, true);
                login = null;
                if (BBDDaceptar == true) {
                    metodos = new MetodosBBDD_Consultas();
                }
            }

            //Para ejecutarse esta primera parte, la conexion debe ser satisfactoria, no haber pulsado a cancelar y el boton debe marcar "CONECTAR"
            if (BBDDaceptar == true && MetodosBBDD_Consultas.BBDD_Conectado && panelMenuBar.conectar_desconectar.getText().equals("CONECTAR")) {
                vistaInsertar = new VistaBBDD_Insertar();
                Controlador_Insertar = new ControladorBBDD_Insertar(vistaInsertar, metodos);
                vistaConsultar = new VistaBBDD_Consultar();
                Controlador_Consultar = new ControladorBBDD_Consultar(vistaConsultar, metodos);
                vistaEliminar = new VistaBBDD_Eliminar();
                Controlador_Eliminar = new ControladorBBDD_Eliminar(vistaEliminar, metodos);
                vistaModificar = new VistaBBDD_Modificar();
                Controlador_Modificar = new ControladorBBDD_Modificar(vistaModificar, metodos);
                panelMenuBar.conectar_desconectar.setText("DESCONECTAR");

                //HABILITANDO BOTONES DE BBDD
                panelMenuBar.insertar.setEnabled(true);
                panelMenuBar.consultar.setEnabled(true);
                panelMenuBar.modificar.setEnabled(true);
                panelMenuBar.eliminar.setEnabled(true);

                JOptionPane.showMessageDialog(panelMenuBar.conectar_desconectar, "Conectado CORRECTAMENTE a la BBDD", "Conexión Satisfactoria", JOptionPane.INFORMATION_MESSAGE);

                //Comprobaremos si loS datos de la conexión "son nuevos", y si lo son, ofreceremos almacenarlos en un Archivo (BBDD_Config.conf)
                boolean cambios = false; //Variable que controlará siexiste un cambio
                File fichero = new File("./$Not3CRUD_BBDD_Config.conf");//Creamos objeto de File (con la ruta del fichero de configuración que debería existir)

                if (fichero.exists()) {

                    try {

                        FileReader flujo = new FileReader(fichero);
                        Scanner lector = new Scanner(flujo);

                        //Si el fichero existe, leeremos de seguido todos los datos, en caso de error en el fichero, lo tomaremos como inexistente
                        lector.reset();//Reseteamos el (contador de lineas)
                        //Comprobaremos si los datos almacenados son iguales a los ultimos que ha introducido el usuario, en caso de ser distintos pondremos la VARIABLE "cambios" a TRUE
                        if (!BBDDurl.equals(lector.nextLine())) {
                            cambios = true;
                        }
                        if (!BBDDpuerto.equals(lector.nextLine())) {
                            cambios = true;
                        }
                        if (!BBDDname.equals(lector.nextLine())) {
                            cambios = true;
                        }
                        if (!BBDDusuario.equals(lector.nextLine())) {
                            cambios = true;
                        }
                        if (!BBDDpass.equals(lector.nextLine())) {
                            cambios = true;
                        }

                        //Cerramos el archivo
                        lector.close();

                        //EN CASO DE PRODUCIRSE UN ERROR TOMAREMOS EL ARCHIVO COMO INVÄLIDO  
                    } catch (IOException ioe) {
                        cambios = true;
                    } catch (Exception e) {
                        cambios = true;
                    }//Fin del Try-Catch

                    //Si no existe el archivo, comprenderemos que existe un cambio   
                } else {
                    cambios = true;
                }

                //Comprobamos se existen cambios respecto a la conexión y en caso de existir ofreceremos la opción de guardar la configuración como predeterminada
                if (cambios == true) {
                    //Preguntamos al usuario si desea almacenar los datos de la nueva conexion en caso de ser diferente a las almacenadas, o no existir archivo de configuración
                    int almacenar = JOptionPane.showConfirmDialog(panelMenuBar.conectar_desconectar, "¿Desea GUARDAR estos datos de conexión como PREDETERMINADOS?", "¿Establecer como predeterminado?", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    if (almacenar == JOptionPane.YES_OPTION) {
                        try {
                            //Crea un objeto File asociado al fichero fichSec.txt
                            File fichero_guardar = new File("./$Not3CRUD_BBDD_Config.conf");
                            //Crea un flujo de caracteres para grabar
                            FileWriter flujo_guardar = new FileWriter(fichero_guardar);//Si existe el archivo, reescribirá su contenido
                            // Escribe los campos cada uno en una linea
                            try (
                                    //Enlaza el flujo de salida con la clase PrintWrite que tiene el metodo println
                                    PrintWriter escritor = new PrintWriter(flujo_guardar)) {
                                // Escribe los campos cada uno en una linea
                                //   String lineaAlta=matricula+" "+marca+" "+modelo+" "+dniTitular+" "+nombreTitular;
                                escritor.println(BBDDurl);
                                escritor.println(BBDDpuerto);
                                escritor.println(BBDDname);
                                escritor.println(BBDDusuario);
                                escritor.println(BBDDpass);

                                JOptionPane.showMessageDialog(panelMenuBar.conectar_desconectar, "Los Nuevos DATOS fueron GUARDADOS correctamente", "Datos Guardados", JOptionPane.INFORMATION_MESSAGE);

                                escritor.close();
                                flujo_guardar.close();

                            }//Fin del Try anidado

                        } catch (IOException ioe) {
                            JOptionPane.showMessageDialog(panelMenuBar.conectar_desconectar, "Los DATOS NO fueron GUARDADOS", "ERROR", JOptionPane.WARNING_MESSAGE);

                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(panelMenuBar.conectar_desconectar, "Los DATOS NO fueron GUARDADOS", "ERROR", JOptionPane.WARNING_MESSAGE);

                        }//Fin del try catch 

                    }//Fin de If almacenar==YES

                }//Find el IF-Cambios

                //Si la conxion es erronea o ya se habia iniciado sesion, procedemos a desconectarnos y cambiar el nombre del boton a "conectar"
            } else if (BBDDaceptar == true && MetodosBBDD_Consultas.BBDD_Conectado && panelMenuBar.conectar_desconectar.getText().equals("DESCONECTAR")) {

                //ELIMINAMOS TODOS LOS OBJETOS QUE SE HUBIESEN CREADO.
                MetodosBBDD_Consultas.BBDD_Conectado = false;
                panelMenuBar.conectar_desconectar.setText("CONECTAR");
                metodos = null;
                vistaInsertar = null;
                vistaConsultar = null;
                vistaEliminar = null;
                vistaModificar = null;
                Controlador_Insertar = null;
                Controlador_Consultar = null;
                Controlador_Modificar = null;
                Controlador_Eliminar = null;
                //DESHABILITANDO BOTONES DE BBDD
                panelMenuBar.insertar.setEnabled(false);
                panelMenuBar.consultar.setEnabled(false);
                panelMenuBar.modificar.setEnabled(false);
                panelMenuBar.eliminar.setEnabled(false);

                JOptionPane.showMessageDialog(panelMenuBar.conectar_desconectar, "Desconectado de la BBDD", "Desconexión Satisfactoria", JOptionPane.INFORMATION_MESSAGE);
            }//Fin del ELSE

        }//Fin del actionPerformed

    }//Fin del Oyente CONECTAR

    //Listener de BBDD-INSERTAR
    class OyenteBBDD_Insertar implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent ie) {

            if (panelMenuBar.insertar.isSelected()) {

                vistaInsertar.setVisible(true);
                vistaInsertar.addComponentListener(new VentanasBBDD_Cerrar(panelMenuBar.insertar));

            } else {
                //Hacemos que se "cierre" solo ESTA ventana, sin finalizar la ejecución del programa (la ventana conservará su estado al salir de esta))
                vistaInsertar.dispose();
            }//Fin del If_else

        }//Fin del ITEM-STATE-CHANGED

    }//Fin del Oyente INSERTAR

    //Listener de BBDD-CONSULTAR
    class OyenteBBDD_Consultar implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent ie) {

            if (panelMenuBar.consultar.isSelected()) {

                vistaConsultar.setVisible(true);
                vistaConsultar.addComponentListener(new VentanasBBDD_Cerrar(panelMenuBar.consultar));

            } else {
                //Hacemos que se cierre solo ESTA ventana, sin finalizar la ejecución del programa
                vistaConsultar.dispose();
            }//Fin del If_else

        }//Fin de ActionPermormed

    }//Fin del Oyente CONSULTAR

    //Listener de BBDD-MODIFICAR
    class OyenteBBDD_Modificar implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent ie) {

            if (panelMenuBar.modificar.isSelected()) {

                vistaModificar.setVisible(true);
                vistaModificar.addComponentListener(new VentanasBBDD_Cerrar(panelMenuBar.modificar));

            } else {
                //Hacemos que se cierre solo ESTA ventana, sin finalizar la ejecución del programa
                vistaModificar.dispose();

            }//Fin del If_else

        }//Fin de ActionPermormed

    }//Fin del Oyente MODIFICAR

    //Listener de BBDD-ELIMINAR
    class OyenteBBDD_Eliminar implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent ie) {

            if (panelMenuBar.eliminar.isSelected()) {

                vistaEliminar.setVisible(true);
                vistaEliminar.addComponentListener(new VentanasBBDD_Cerrar(panelMenuBar.eliminar));

            } else {
                //Hacemos que se cierre solo ESTA ventana, sin finalizar la ejecución del programa
                vistaEliminar.dispose();

            }//Fin del If_else

        }//Fin de ActionPermormed

    }//Fin del Oyente ELIMINAR

///////////////////////////////////////////////////////////////////////////////////////////////////
/// Esta clase, será una clase "GENERICA" que funcionará con cualquiera de las opciones del menu BBDD
/// Se encargará de detectar si alguna de las ventanas se ha cerrado, y DESMARCARÁ el CHECKBOX Correspondiente
    class VentanasBBDD_Cerrar implements ComponentListener {

        //Creamos un objeto de CheckBox, en el cual almacenaremos el CHECKBOX correspondiente en el momento, para actuar con el
        JCheckBox comodin;

        //Constructor de esta  clase, la cual recibe un Componente JCheckBox, que modificará desde esta clase
        public VentanasBBDD_Cerrar(JCheckBox BBDD_Opcion) {
            comodin = BBDD_Opcion;
        }//Find el constructor

        @Override
        public void componentResized(ComponentEvent ce) {
        }

        @Override
        public void componentMoved(ComponentEvent ce) {
        }

        @Override
        public void componentShown(ComponentEvent ce) {
        }

        //Este metodo DETECTA cuando se CIERRA la VENTANA
        @Override
        public void componentHidden(ComponentEvent ce) {
            //DESMARCAREMOS EL CHECKBOX CORRESPONDIENTE
            comodin.setSelected(false);
        }

    }//Fin de VentanasBBDD_Cerrar

    ////////////////////////////////////////////////////////////////////////////////    
    //Fin de los listeners de BBDD-JCHECKBOX
////////////////////////////////////////////////////////////////////////////////   
    class OyenteNuevo implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ

            //Ejecutamos el metodo pestaña nueva y RECIBIMOS un OBJETO de panelPestanias, que sustituirá al existente
            panelPestanias = panelPestanias.PestaniaTextoNueva();
            //JPOPUPMENU Agregamos los botones el JPopupMenu y les pasamos su correspondiente Listener
            panelPestanias.TP.setSelectedIndex(panelPestanias.TP.getTabCount() - 1);
            Recargar();
        }//Fin action performed
    }//Fin del OyenteCOPIAR

////////////////////////////////////////////////////////////////////////////////   
    class OyenteAbrir implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            //Creamos una pestaña nueva utilizando el método que ESTA INLUCIDO EN LA CLASE Panel_Pestanias
            
            File file;
            try {
                file= metodosPrincipal.AbrirArchivo(panelTA.fichero);
                AbrirArchivo(file);
            } catch (IOException ex) {
                Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }//Fin action performed
    }//Fin del OyenteABRIR

////////////////////////////////////////////////////////////////////////////////   
    class OyenteGuardar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            String texto = panelTA.textArea.getText();

            try {
                panelTA.fichero = metodosPrincipal.Guardar(panelTA.fichero, texto);
                JOptionPane.showMessageDialog(panelTA.textArea, "El ARCHIVO fue GUARDADO con Éxito", "Archivo Guardado", JOptionPane.INFORMATION_MESSAGE);

            } catch (HeadlessException e) {
                JOptionPane.showMessageDialog(panelTA.textArea, "El ARCHIVO NO fue guardado ", "ERROR", JOptionPane.INFORMATION_MESSAGE);

            }

        }//Fin action performed
    }//Fin del OyenteGUARDAR

////////////////////////////////////////////////////////////////////////////////   
    class OyenteGuardarComo implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            String texto = panelTA.textArea.getText();

            try {
                panelTA.fichero = metodosPrincipal.GuardarComo(panelTA.fichero, texto);
                //Cambiamos el nombre de la pestaña y configuramos para establecerle el icono y el oyente para que cierre correctamente
                panelPestanias.TP.setTitleAt(indexPestana, panelTA.fichero.getName());
                panelPestanias.TP.setTabComponentAt(indexPestana, new Panel_Pestanias.Cross(panelTA.fichero.getName())); //agrega titulo y boton X.
                JOptionPane.showMessageDialog(Panel_Pestanias.TP, "El ARCHIVO fue GUARDADO con Éxito", "Archivo Guardado", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(panelTA.textArea, "Se produjo un error", "ERROR", JOptionPane.ERROR_MESSAGE);

            } catch (ArithmeticException a) {
                JOptionPane.showMessageDialog(panelTA.textArea, "El ARCHIVO NO fue guardado ", "Archivo NO Guardado", JOptionPane.CANCEL_OPTION);

            }

        }//Fin action performed
    }//Fin del OyenteGUARDAR_COMO

    ////////////////////////////////////////////////////////////////////////////////   
    public class OyenteRenombrar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ

            try {

                String texto = panelTA.textArea.getText(); //Recogemos el texto del archivo que hay en el textarea

                //Este metodo. integrado en el objeto PanelTextArea, elimina el archivo existente y abre el archivo creado anteriormente  
                panelTA.cambiarNombre(metodosPrincipal.Renombrar(texto)); //Renombramos el fichero (mas bien vamos a crear uno nuevo)
                //Cambiamos el nombre de la pestaña y configuramos para establecerle el icono y el oyente para que cierre correctamente
                panelPestanias.TP.setTitleAt(indexPestana, panelTA.fichero.getName());
                panelPestanias.TP.setTabComponentAt(indexPestana, new Panel_Pestanias.Cross(panelTA.fichero.getName())); //agrega titulo y boton X.
                JOptionPane.showMessageDialog(panelTA.textArea, "El ARCHIVO fue RENOMBRADO con Éxito", "Archivo Renombrado", JOptionPane.INFORMATION_MESSAGE);

            } catch (HeadlessException e) {
                e.getMessage();
            } catch (ArithmeticException e) {
                JOptionPane.showMessageDialog(panelTA.textArea, "Operación CANCELADA", "OPERACIÓN CANCELADA", JOptionPane.CANCEL_OPTION);
            } //Fin del try-Catch

        }//Fin action performed
    }//Fin del OyenteRENOMBRAR

////////////////////////////////////////////////////////////////////////////////   
    class OyenteIrA implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            try {
                String linea = JOptionPane.showInputDialog(panelTA.textArea, "Introduzca Nº de linea:", "Introduzca Nº Linea", JOptionPane.QUESTION_MESSAGE);

                if (linea != null) {
                    int index = metodosPrincipal.getLineStartIndex(panelTA.textArea, Integer.parseInt(linea));
                    panelTA.textArea.setCaretPosition(index);

                } else {
                    JOptionPane.showMessageDialog(panelTA.textArea, "Operación CANCELADA", "OPERACIÓN CANCELADA", JOptionPane.CANCEL_OPTION);

                }

            } catch (HeadlessException | NumberFormatException e) {
                JOptionPane.showMessageDialog(panelTA.textArea, "Introduzca solo Números", "ERROR de Entrada", JOptionPane.ERROR_MESSAGE);

            }
        }//Fin action performed
    }//Fin del OyenteCOPIAR

////////////////////////////////////////////////////////////////////////////////   
    class OyenteDeshacer implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            if (panelTA.manager.canUndo()) {
                panelTA.manager.undo();
            }
        }//Fin action performed
    }//Fin del OyenteCOPIAR

////////////////////////////////////////////////////////////////////////////////   
    class OyenteRehacer implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            if (panelTA.manager.canRedo()) {
                panelTA.manager.redo();
            }
        }//Fin action performed
    }//Fin del OyenteCOPIAR

////////////////////////////////////////////////////////////////////////////////   
    class OyenteCopiar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            panelTA.textArea.copy();
        }//Fin action performed
    }//Fin del OyenteCOPIAR

////////////////////////////////////////////////////////////////////////////////   
    class OyentePegar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            panelTA.textArea.paste();
        }//Fin action performed
    }//Fin del OyenteCOPIAR

////////////////////////////////////////////////////////////////////////////////   
    class OyenteCortar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            panelTA.textArea.cut();
        }//Fin action performed
    }//Fin del OyenteCOPIAR

////////////////////////////////////////////////////////////////////////////////   
    class OyenteInsertarFecha implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ

            panelTA.textArea.append(String.valueOf(metodosPrincipal.InsertarFecha()));
        }//Fin action performed
    }//Fin del OyenteCOPIAR

////////////////////////////////////////////////////////////////////////////////   
    class OyenteBuscar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            try {
                String textoInicialDeBusqueda = panelTA.textArea.getSelectedText();
                if (textoInicialDeBusqueda == null) {
                    textoInicialDeBusqueda = "";
                }
                String textoABuscar = JOptionPane.showInputDialog(panelTA.textArea, "Texto a buscar", textoInicialDeBusqueda);
                Caret seleccion = panelTA.textArea.getCaret();

                int posicionInicial = 0;
                if (seleccion.getDot() != seleccion.getMark()) {
                    // Hay algo seleccionado
                    posicionInicial = seleccion.getDot();
                }

                String textoTotal = panelTA.textArea.getText();
                int posicion = textoTotal.indexOf(textoABuscar, posicionInicial);
                panelTA.textArea.setCaretPosition(posicion);
                panelTA.textArea.moveCaretPosition(posicion);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(panelTA.textArea, "No se encuentra la PALABRA Indicada.", "ERROR de Busqueda ", JOptionPane.ERROR_MESSAGE);

            }
        }//Fin action performed
    }//Fin del OyenteCOPIAR

////////////////////////////////////////////////////////////////////////////////   
    class OyenteBuscarYReemplazar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            try {
                String textoInicialDeBusqueda = panelTA.textArea.getSelectedText();
                if (textoInicialDeBusqueda == null) {
                    textoInicialDeBusqueda = "";
                }

                //Solicitamos laas palabras a buscar y reemplazar, en caso de cancelacion saldrá por la excepción
                String textoABuscar = JOptionPane.showInputDialog(panelTA.textArea, "Texto a buscar", textoInicialDeBusqueda);
                if (textoABuscar == null) {
                    throw new ArithmeticException();
                }
                String textoNuevo = JOptionPane.showInputDialog(panelTA.textArea, "Texto NUEVO", textoInicialDeBusqueda);
                if (textoNuevo == null) {
                    throw new ArithmeticException();
                }

                Caret seleccion = panelTA.textArea.getCaret();

                int posicionInicial = 0;
                if (seleccion.getDot() != seleccion.getMark()) {
                    // Hay algo seleccionado
                    posicionInicial = seleccion.getDot();
                }

                String textoTotal = panelTA.textArea.getText();
                int posicion = textoTotal.indexOf(textoABuscar, posicionInicial);

                panelTA.textArea.replaceRange(textoNuevo, posicion, posicion + textoABuscar.length());

            } catch (ArithmeticException e) {
                JOptionPane.showMessageDialog(panelTA.textArea, "Operación Cancelada", "OPERACIÓN CANCELADA ", JOptionPane.CANCEL_OPTION);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(panelTA.textArea, "No se encuentra la palabra buscada", "ERROR ", JOptionPane.CANCEL_OPTION);

            }
        }//Fin action performed
    }//Fin del OyenteCOPIAR

////////////////////////////////////////////////////////////////////////////////   
    class OyenteImprimirConfigurando implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
                panelTA.textArea.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(panelTA.textArea, "Se ha producido un error con la Impresión.", "ERROR de Impresión ", JOptionPane.ERROR_MESSAGE);

            }
        }//Fin action performed
    }//Fin del OyenteCOPIAR

////////////////////////////////////////////////////////////////////////////////   
    class OyenteImprimirDirecto implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ

            try {

                String nombre_fichero = "TMP_PRINT";
                //Creando Fichero TEMPORAL para mandar a Imprimir
                File fich_print = new File(nombre_fichero);

                fich_print.delete();
                System.gc();
                fich_print.createNewFile();
                try ( //Crea un flujo de caracteres para grabar
                        FileWriter flujo = new FileWriter(fich_print) //Podriamos dejarle true para que seguiera escribiendo debajo de este si existiera, (aunqeu lo estoy eliminando mas arriba pòrque no es el caso)
                        ;
                         PrintWriter escritor = new PrintWriter(flujo)) {
                    //Grabamos el archivo temporal con el contenido del JTextArea
                    escritor.println(panelTA.textArea.getText());
                }
                //imprimir es un objeto de Imprimir y está ya declarado en esta clase
                Imprimir imprimir = new Imprimir(nombre_fichero);//Inicializamos el objeto enviandole el nombre del archivo, y se irá a la cola de impresión
                //Borramos el archivo Temporal de Impresión
                fich_print.delete();
                System.gc();

            } catch (IOException e) {

            }

        }//Fin action performed
    }//Fin del OyenteCOPIAR

////////////////////////////////////////////////////////////////////////////////   
    class OyenteAcercaDe implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            Image icono = new ImageIcon(getClass().getResource("/Images/LogoKrazyLab.png")).getImage();
            JOptionPane.showMessageDialog(Panel_Pestanias.TP, "Aplicación NotePad con Pestañas y Conexión a BBDD.\n\nRealiza las operaciones a esperar por un Bloc de Notas.\nSu diseño es minimalista \"a gusto del Programador\".\n\nTiene la capacidad de conectarse a una BBDD\n(Entregada junto al programa, lista para importar).\n\nEn la Base de datos se podrán realizar las acciones:\n-Insertar\n-Consultar\n-Modificar\n-Eliminar\n\n\nAutor: David Freyre Muñoz  2020", "Acerca de", HEIGHT, new ImageIcon(icono));

        }//Fin action performed
    }//Fin del OyenteCOPIAR

////////////////////////////////////////////////////////////////////////////////   
    class OyenteSalir implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            AlertarSalida();
        }//Fin action performed
    }//Fin del OyenteCOPIAR

////////////////////////////////////////////////////////////////////////////////   
    class OyenteColorBackground implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ

            //Solicitamos que se seleccione el color
            color = JColorChooser.showDialog(panelTA.textArea, "Seleccione color para FONDO", colorBackground);

            //Comprobamos si se SELECCIONÓ algúncolor
            if (color != null) {
                colorBackground = new Color(color.getRGB());
                panelTA.textArea.setBackground(colorBackground);
            }//Find el if

        }//Fin action performed
    }//Fin del OyenteCOPIAR

////////////////////////////////////////////////////////////////////////////////   
    class OyenteColorFuente implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ

            //Creando objeto de PanelTextAREA QUE APUNTA  al PanelTextArea que tenemos en LA PESTAÑA ACTUAL
            //Por lo tanto estaremos actuando directamente en la pestaña seleccionada
            //CAMBIAR EL COLOR DE LA "LETRA"
            color = JColorChooser.showDialog(panelTA.textArea, "Seleccione color para TEXTO", colorTexto);

            //Comprobamos si se SELECCIONÓ algúncolor
            if (color != null) {
                colorTexto = new Color(color.getRGB());
                //Seteando COLOR de TEXTO   
                panelTA.textArea.setForeground(colorTexto);
                //Color del "caret" (puntero)
                panelTA.textArea.setCaretColor(colorTexto);
            }//Find el if

        }//Fin action performed
    }//Fin del OyenteCOPIAR

    ////////////////////////////////////////////////////////////////////////////////   
    class OyenteColorSeleccion implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ

            //CAMBIAR EL COLOR DE LA "LETRA"
            color = JColorChooser.showDialog(panelTA.textArea, "Seleccione color para SELECCIÓN", colorSeleccion);

            if (color != null) {
                colorSeleccion = new Color(color.getRGB());
                //Color de SLECCION
                panelTA.textArea.setSelectionColor(colorSeleccion);
            }

        }//Fin action performed
    }//Fin del OyenteCOPIAR

    ////////////////////////////////////////////////////////////////////////////////   
    class OyenteColorTextoSeleccionado implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            //CAMBIAR EL COLOR DE LA "LETRA"
            color = JColorChooser.showDialog(panelTA.textArea, "Seleccione color para TEXTO Seleccionado", colorTextoSeleccionado);
            if (color != null) {
                colorTextoSeleccionado = new Color(color.getRGB());
                //Color de TEXTO Seleccionado
                panelTA.textArea.setSelectedTextColor(colorTextoSeleccionado);
            }

        }//Fin action performed
    }//Fin del OyenteCOPIAR

    class OyenteGuardarConfiguracion implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            //Ejecutando al metodo guardar configuracion
            if (GuardarConfiguración()) {
                JOptionPane.showMessageDialog(Panel_Pestanias.TP, "Configuración GUARDADA con Éxito", "GUARDADO", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(Panel_Pestanias.TP, "Configuración NO GUARDADA", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        }//Find el actionPerformed

    }//Fin de Oyente GUARDAR CONFIDURACION    

    //BOTON ESTATICO PARA TRAER LA CONSULTA RECUPERADA EN "BBDD_CONSULTAR" A LA PESTAÑA SELECCIONADA
    class OyenteExportarConsulta implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            try {
                //Agregamos a nuestra pestaña actual el area de texto recogida en el TextArea Estático (que solo se encuentra en CONSULTA)
                panelTA.textArea.append(texAreaExportar.getText());
            } catch (Exception e) {
                //Controlamos si existe alguna pestaña abierta, si no existe, la abriremos
                panelPestanias = panelPestanias.PestaniaTextoNueva();
                panelTA.textArea.append(texAreaExportar.getText());

            }//Fin del Try catch

        }//Fin del actionPerformed

    }//Fin de exportar consulta

    ///////////////////////////////////////////////////////////////////////////////////
//Implementando DRAG&DROP, si un archivo es aarastrado, se abrira en una nueva pestaña
    class FileDropHandler extends TransferHandler {

        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            for (DataFlavor flavor : support.getDataFlavors()) {
                if (flavor.isFlavorJavaFileListType()) {

                    ArrayList<File> files;

                    try {
                        files = (ArrayList<File>) support.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    } catch (Exception ex) {
                        // should never happen (or JDK is buggy)
                        return false;
                    }

                    for (File file : files) {
                        //ABRIMOS PESTAÑA NUEVA PASANDOLE EL ARCHIVO
                        AbrirArchivo(file);
                        
                    }
                    return true;

                }
            }
            return false;
        }

        @Override
        //     @SuppressWarnings("unchecked")
        public boolean importData(TransferHandler.TransferSupport support) {

            return true;
        }
    }//FIN DE FILE-DROP-HANDLER

///////////////////////////////////////////////////////////////////////////////
    
    
    
    
    private void AbrirArchivo (File file){
    
     try {
                 panelPestanias = panelPestanias.PestaniaTextoNueva();
                 panelPestanias.TP.setSelectedIndex(panelPestanias.TP.getTabCount() - 1);//Hacemos que se seleccione esta nueva pestaña
     
                //Capturamos el fichero que seleccionará el usuario,
                //y se lo pasaramos al objeto PanelTextArea correspondiente 
                
                panelTA.fichero = file;

                //Reconfiguramos el titulo de la pestaña y le agregamos el boton correspondiente
                String title = panelTA.fichero.getName();
                panelPestanias.TP.setTitleAt(panelPestanias.TP.getTabCount() - 1, title);
                //Le pasamos el metodo SETTABCOMPONENT que nos permite modificar la pestaña, y utilizamos el metodo estatico contenido en Panel_pestanias,"Cross" que lo que hará será añadir un icono a la pestaña, dotandola con la propiedad de poder cerrarla
                panelPestanias.TP.setTabComponentAt(panelPestanias.TP.getTabCount() - 1, new Panel_Pestanias.Cross(title)); //agrega titulo y boton X.

                   //COmprobamos si el archivo contiene algo escrito y si lo hay, lo escribiremos en el TextArea
                if (panelTA.fichero.length() > 0) {
                    try ( //procedemos acrear el flujo y el lector, para leer nuestro fichero seleccionado
                            //y poder escribirlo luego en nuestro TextArea
                            FileReader flujo = new FileReader(panelTA.fichero)) {
                        try (Scanner lector = new Scanner(flujo)) {
                            long cont = 0;//esta variable es para imprimir o no los saltos de linea, para que no altere el archivo original
                            while (lector.hasNext()) {
                                
                                if (cont == 0) {
                                    panelTA.textArea.append(lector.nextLine());
                                } else {
                                    panelTA.textArea.append("\n" + lector.nextLine());
                                }
                                cont++;
                                                      
                            }//Fin del WHILE
                        lector.close();
                        } 
                        flujo.close();
                    }
                }//FIn del IF fichero.lenght >0
                    
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(Panel_Pestanias.TP, "Se produjo un error", "ERROR", JOptionPane.ERROR_MESSAGE);
            }//Find el TryCatch
            
            Recargar();
    
    }//Fin de AbrirArchivo
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}//Fin del controlador principal

