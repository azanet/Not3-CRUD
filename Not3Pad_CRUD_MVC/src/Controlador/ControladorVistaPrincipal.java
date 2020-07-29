/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Imprimir;
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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import static java.awt.image.ImageObserver.HEIGHT;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.Caret;

/**
 *
 * @author davidf
 */
public class ControladorVistaPrincipal {

    //Declarando OBJETOS de VistaPrincipal(el marco de la aplicación) y los Metodos(modelo)
    private VistaPrincipal vistaPrincipal;
    private final MetodosPrincipal metodosPrincipal;
    //Declarando OBJETOS de PANELES QUE CONFORMA LA VistaPrincipal
    public PanelMenuBar panelMenuBar; //Panel que contiene el JMenuBar
    public static Panel_Pestanias panelPestanias; //Panel que contiene el JTabbedPane
    public PanelTextArea panelTA; //este pane lo utilizaremos para recuperar el PANELTEXTAREA (que está contenida en algunas pestañas) que corresponda a la pestaña que hemos seleccionado
      
    private VistaBBDD_Insertar vistaInsertar;       
    private VistaBBDD_Consultar vistaConsultar;
    private VistaBBDD_Modificar vistaModificar;
    private VistaBBDD_Eliminar vistaEliminar;


    //Declarando objetos y variables necesarias para poder cambiar el estilo y formato de los TextArea
    Font fuente;
    Color color;
    int tamanio_fuente;
    String nombre_fuente;
    String[] fontNames;//Estos métodos rellenarán el Array con los estilos de texto disponibles en nuestro sistema
    Color colorBackground;
    Color colorTexto;
    Color colorSeleccion;
    Color colorTextoSeleccionado;
    int indexPestana; //En esta variable iremos almacenando el INDEX de la pestaña en la que nos encontramos

    public ControladorVistaPrincipal(VistaPrincipal vistaPrincipal, MetodosPrincipal metodosPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
        this.metodosPrincipal = metodosPrincipal;
        this.panelMenuBar = new PanelMenuBar();
        this.panelPestanias = new Panel_Pestanias();
        this.fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(); //Estos métodos rellenarán el Array con los estilos de texto disponibles en nuestro sistema
        vistaInsertar = new VistaBBDD_Insertar();
        
        
               //Confiduramos el layout del panelMenuBar a Border, y agregamos los paneles
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

        //Ejecutamos el método iniciar que iniciará todos los componentes
        Iniciar();
    }

    private void Iniciar() {
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
        
        //Insertando OYENTES DE OBJETOS a los CheckBox
        panelMenuBar.insertar.addItemListener(new OyenteBBDD_Insertar());
        panelMenuBar.consultar.addItemListener(new OyenteBBDD_Consultar());
        panelMenuBar.modificar.addItemListener(new OyenteBBDD_Modificar());
        panelMenuBar.eliminar.addItemListener(new OyenteBBDD_Eliminar());
        //Agregamos OYENTE al JTabbedPane, para saber a qué pestaña ha cambiado (y poder recuperar su componente para trabajar con el o lo que queramos)
        panelPestanias.TP.addChangeListener(new OyenteCambioPestana());

        //Añadiendo y cargando ComboBox de TAMAÑO LETRA
        for (int i = 0; i < 100; i++) {
            panelMenuBar.comboBox.addItem(i);
        }
        // Accion a realizar cuando el JComboBox cambia de item seleccionado.
        panelMenuBar.comboBox.addActionListener(new OyenteComboTamanio());

        ///AÑADIENDO COMBOBOX PARA ESTILO DE LETRA
        //Mostrar un listado con las fuentes DISPONIBLES
        //Recorremos el array de FontNames para rellenar el comboBox con todos los estilos de letra disponibles 
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
     //   panelMenuBar.consultar.setEnabled(false);
        panelMenuBar.comboBox.setEnabled(false);
        panelMenuBar.comboBoxStyle.setEnabled(false);

        
        //Le assignamos el tamaño a la Vista Principal
        vistaPrincipal.setBounds(0, 0, 600, 500);
        vistaPrincipal.setMinimumSize(new Dimension(600, 56));
        //Con este método haremos que la pantalla salga JUSTO EN EL CENTRO
        vistaPrincipal.setLocationRelativeTo(null);
        //Hacemos visible la vista Principal      
        vistaPrincipal.setVisible(true);
        
    }//Fin de iniciar

    
    
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
            panelTA = (PanelTextArea) panelPestanias.TP.getSelectedComponent();
            indexPestana = panelPestanias.TP.getSelectedIndex();
            Recargar();
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

            //Ahora setearemos los comboBox con los datos Extraidos
            panelMenuBar.comboBox.setSelectedItem(tamanio_fuente); //Agregaremos este primer elemento con el valor inicial que tendrá
            panelMenuBar.comboBoxStyle.setSelectedItem(nombre_fuente); //Agregaremos este primer elemento con el valor inicial que tendrá

            //ACTIVAMOS los botones porque no Existirá ninguna pestaña abierta
            panelMenuBar.guardar.setEnabled(true);
            panelMenuBar.guardarComo.setEnabled(true);
            panelMenuBar.renombrar.setEnabled(true);
            panelMenuBar.edicion.setEnabled(true);
            panelMenuBar.imprimir.setEnabled(true);
            panelMenuBar.personalizar.setEnabled(true);
            panelMenuBar.comboBox.setEnabled(true);
            panelMenuBar.comboBoxStyle.setEnabled(true);
             panelMenuBar.consultar.setEnabled(true);
        } else {
            //Desactivamos los botones porque no Existirá ninguna pestaña abierta
            panelMenuBar.guardar.setEnabled(false);
            panelMenuBar.guardarComo.setEnabled(false);
            panelMenuBar.renombrar.setEnabled(false);
            panelMenuBar.edicion.setEnabled(false);
            panelMenuBar.imprimir.setEnabled(false);
            panelMenuBar.personalizar.setEnabled(false);
            panelMenuBar.comboBox.setEnabled(false);
            panelMenuBar.comboBoxStyle.setEnabled(false);
             panelMenuBar.consultar.setEnabled(false);
        }//Find el ifelse

    }//Fin del metodo Recargar

    
    
 
 ////////////////////////////////////////////////////////////////////////////////   
 ////////LISTENERS de los COMBOBOX correspondientes a TAMAÑO y ESTILO////////////   
 ////////////////////////////////////////////////////////////////////////////////  
    //Combobox de tamaño
    class OyenteComboTamanio implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            //Almacenamos los datos del combobox en la variable instanciada, para utilizarla cuando sea necesario
            tamanio_fuente = Integer.parseInt(panelMenuBar.comboBox.getSelectedItem().toString());

            fuente = new Font(nombre_fuente, Font.PLAIN, tamanio_fuente);
            panelTA.textArea.setFont(fuente); // Coneste metodo realizamos el cambio de estilo a nuestro textarea del objeto de PanelTextArea correspondiente a la pestaña

        }//Fin action performed
    }//Fin del OyenteCOPIAR

    
  //Combobox de estilo de fuente
    class OyenteComboStyle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ

            //Creando objeto de PanelTextAREA QUE APUNTA  al PanelTextArea que tenemos en LA PESTAÑA ACTUAL
            //Por lo tanto estaremos actuando directamente en la pestaña seleccionada
            //  PanelTextArea panelTA = (PanelTextArea) panelPestanias.TP.getSelectedComponent();
            //Almacenamos los datos del combobox en la variable instanciada, para utilizarla cuando sea necesario
            nombre_fuente = panelMenuBar.comboBoxStyle.getSelectedItem().toString();

            fuente = new Font(nombre_fuente, Font.PLAIN, tamanio_fuente);
            panelTA.textArea.setFont(fuente); // sólo va a cambiar el tamaño a 12 puntos

        }//Fin action performed
    }//Fin del OyenteCOPIAR
    
 ////////////////////////////////////////////////////////////////////////////////    
 //Fin de los listeners de COMBOBOX   

    

        


 ////////////////////////////////////////////////////////////////////////////////   
 //////// LISTENERS de los JCHECKBOX correspondientes al Menú BBDD   ////////////   
 ////////////////////////////////////////////////////////////////////////////////      
    //Listener de BBDD-INSERTAR
    class OyenteBBDD_Insertar implements ItemListener{

        @Override
        public void itemStateChanged(ItemEvent ie) {  
            if (panelMenuBar.insertar.isSelected()){
                
                
                
                try {
                    ControladorBBDD_Insertar Controlador_Insertar = new ControladorBBDD_Insertar(vistaInsertar);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
                vistaInsertar.setVisible(true);
                vistaInsertar.addComponentListener(new VentanasBBDD_Cerrar(panelMenuBar.insertar));
                 
              
            }else{
                  //Hacemos que se cierre solo ESTA ventana, sin finalizar la ejecución del programa
                vistaInsertar.dispose();
         }//Fin del If_else
         
        }//Fin del ITEM-STATE-CHANGED
   
    }//Fin del Oyente INSERTAR
    

        //Listener de BBDD-CONSULTAR
    class OyenteBBDD_Consultar implements ItemListener{

        @Override
         public void itemStateChanged(ItemEvent ie) {  
            
          if (panelMenuBar.consultar.isSelected()){
                
                vistaConsultar = new VistaBBDD_Consultar();
                 ControladorBBDD_Consultar Controlador_Consultar = new ControladorBBDD_Consultar(vistaConsultar);
               vistaConsultar.setVisible(true);
                vistaConsultar.addComponentListener(new VentanasBBDD_Cerrar(panelMenuBar.consultar));
                
              
            }else{
                //Hacemos que se cierre solo ESTA ventana, sin finalizar la ejecución del programa
                vistaConsultar.dispose();
         }//Fin del If_else
     
 
        }//Fin de ActionPermormed
       
    }//Fin del Oyente CONSULTAR
    
    
        //Listener de BBDD-MODIFICAR
    class OyenteBBDD_Modificar implements ItemListener{

        @Override
         public void itemStateChanged(ItemEvent ie) {  
        
          if (panelMenuBar.modificar.isSelected()){
                
                
                
                vistaModificar = new VistaBBDD_Modificar();
                 ControladorBBDD_Modificar Controlador_Modificar = new ControladorBBDD_Modificar(vistaModificar);
                vistaModificar.setVisible(true);
                vistaModificar.addComponentListener(new VentanasBBDD_Cerrar(panelMenuBar.modificar));
               
              
            }else{
                //Hacemos que se cierre solo ESTA ventana, sin finalizar la ejecución del programa
               vistaModificar.dispose();  
               
         }//Fin del If_else
     
        }//Fin de ActionPermormed
       
    }//Fin del Oyente MODIFICAR
    
    
        //Listener de BBDD-ELIMINAR
    class OyenteBBDD_Eliminar implements ItemListener{

        @Override
         public void itemStateChanged(ItemEvent ie) {  
          
          if (panelMenuBar.eliminar.isSelected()){
                
                 vistaEliminar = new VistaBBDD_Eliminar();
                  ControladorBBDD_Eliminar Controlador_Eliminar = new ControladorBBDD_Eliminar(vistaEliminar); 
                vistaEliminar.setVisible(true);
                vistaEliminar.addComponentListener(new VentanasBBDD_Cerrar(panelMenuBar.eliminar));
                
              
            }else{
                //Hacemos que se cierre solo ESTA ventana, sin finalizar la ejecución del programa
                vistaEliminar.dispose();
                
         }//Fin del If_else
     
        }//Fin de ActionPermormed
       
    }//Fin del Oyente ELIMINAR
    
///////////////////////////////////////////////////////////////////////////////////////////////////
/// Esta clase, será una clase "GENERICA" que funcionará con cualquiera de las opciones del menu BBDD
/// Se encargará de detectar si alguna de las ventanas se ha cerrado, y DESMARCARÁ el CHECKBOX Correspondiente
        class VentanasBBDD_Cerrar implements ComponentListener{
        
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

            panelPestanias = panelPestanias.PestaniaTextoNueva();
            //JPOPUPMENU Agregamos los botones el JPopupMenu y les pasamos su correspondiente Listener
            panelPestanias.TP.setSelectedIndex( panelPestanias.TP.getTabCount()-1);
                  
           
       
        }//Fin action performed
    }//Fin del OyenteCOPIAR

    
////////////////////////////////////////////////////////////////////////////////   
    class OyenteAbrir implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
                //Creamos una pestaña nueva utilizando el método que ESTA INLUCIDO EN LA CLASE Panel_Pestanias
              panelPestanias = panelPestanias.PestaniaTextoNueva();
              panelPestanias.TP.setSelectedIndex( panelPestanias.TP.getTabCount()-1);//Hacemos que se seleccione esta nueva pestaña
              
            try {
            //Capturamos el fichero que seleccionará el usuario,
             //y se lo pasaramos al objeto PanelTextArea correspondiente 
            panelTA.fichero = metodosPrincipal.AbrirArchivo(panelTA.fichero);

            //Reconfiguramos el titulo de la pestaña y le agregamos el boton correspondiente
            String title=panelTA.fichero.getName();
            panelPestanias.TP.setTitleAt(panelPestanias.TP.getTabCount()-1, title);
            panelPestanias.TP.setTabComponentAt(panelPestanias.TP.getTabCount()-1, new Panel_Pestanias.Cross(title)); //agrega titulo y boton X.
            
            //COmprobamos si el archivo contiene algo escrito y si lo hay, lo escribiremos en el TextArea
            if(panelTA.fichero.length()>0){
                try ( //procedemos acrear el flujo y el lector, para leer nuestro fichero seleccionado
                //y poder escribirlo luego en nuestro TextArea
                        FileReader flujo = new FileReader(panelTA.fichero)) {
                    Scanner lector = new Scanner(flujo);
                    
                    while (lector.hasNext()) {
                        panelTA.textArea.append(lector.nextLine() + "\n");
                    }//Fin del WHILE
                }
            }//FIn del IF

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(Panel_Pestanias.TP, "Se produjo un error", "ERROR", JOptionPane.ERROR_MESSAGE);
    }//Find el TryCatch
              
            
        }//Fin action performed
    }//Fin del OyenteABRIR

////////////////////////////////////////////////////////////////////////////////   
    class OyenteGuardar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
             String texto = panelTA.textArea.getText();
            try{
             panelTA.fichero = metodosPrincipal.Guardar(panelTA.fichero, texto);
             JOptionPane.showMessageDialog(panelTA.textArea, "El ARCHIVO fue GUARDADO con Éxito", "Archivo Guardado", JOptionPane.INFORMATION_MESSAGE);
        
            }catch(Exception e){
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
             panelTA.fichero = metodosPrincipal.GuardarComo( panelTA.fichero, texto);
                //Cambiamos el nombre de la pestaña y configuramos para establecerle el icono y el oyente para que cierre correctamente
           panelPestanias.TP.setTitleAt(indexPestana, panelTA.fichero.getName());
            panelPestanias.TP.setTabComponentAt(indexPestana, new Panel_Pestanias.Cross(panelTA.fichero.getName())); //agrega titulo y boton X.
            JOptionPane.showMessageDialog(Panel_Pestanias.TP, "El ARCHIVO fue GUARDADO con Éxito", "Archivo Guardado", JOptionPane.INFORMATION_MESSAGE);
           
             
             
        } catch (IOException ex) {
             JOptionPane.showMessageDialog(panelTA.textArea, "Se produjo un error", "ERROR", JOptionPane.ERROR_MESSAGE);
   
        }catch (ArithmeticException a) {
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
       try{      
        String linea = JOptionPane.showInputDialog(panelTA.textArea, "Introduzca Nº de linea:", "Introduzca Nº Linea", JOptionPane.QUESTION_MESSAGE);
        
        if (linea != null){
        int index = metodosPrincipal.getLineStartIndex(panelTA.textArea, Integer.parseInt(linea));
        panelTA.textArea.setCaretPosition(index);
        
        }else{
             JOptionPane.showMessageDialog(panelTA.textArea, "Operación CANCELADA", "OPERACIÓN CANCELADA", JOptionPane.CANCEL_OPTION);
    
        }
        
        }catch (HeadlessException | NumberFormatException e){
            JOptionPane.showMessageDialog(panelTA.textArea, "Introduzca solo Números", "ERROR de Entrada", JOptionPane.ERROR_MESSAGE);
    
        }catch (Exception e){
             JOptionPane.showMessageDialog(panelTA.textArea, "La LINEA indicada NO EXISTE.", "ERROR de Nº de LINEA ", JOptionPane.ERROR_MESSAGE);
    
        }
        }//Fin action performed
    }//Fin del OyenteCOPIAR

////////////////////////////////////////////////////////////////////////////////   
    class OyenteDeshacer implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            if(panelTA.manager.canUndo())
                panelTA.manager.undo();
        }//Fin action performed
    }//Fin del OyenteCOPIAR

////////////////////////////////////////////////////////////////////////////////   
    class OyenteRehacer implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            if(panelTA.manager.canRedo())
                panelTA.manager.redo();
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
            try{
        String textoInicialDeBusqueda = panelTA.textArea.getSelectedText();
        if (textoInicialDeBusqueda == null) {
            textoInicialDeBusqueda = "";
        }
        String textoABuscar = JOptionPane.showInputDialog( panelTA.textArea, "Texto a buscar", textoInicialDeBusqueda);
        Caret seleccion =  panelTA.textArea.getCaret();

        int posicionInicial = 0;
        if (seleccion.getDot() != seleccion.getMark()) {
            // Hay algo seleccionado
            posicionInicial = seleccion.getDot();
        }

        String textoTotal =  panelTA.textArea.getText();
        int posicion = textoTotal.indexOf(textoABuscar, posicionInicial);
         panelTA.textArea.setCaretPosition(posicion);
         panelTA.textArea.moveCaretPosition(posicion);
            }catch(Exception e){
             JOptionPane.showMessageDialog(panelTA.textArea, "No se encuentra la PALABRA Indicada.", "ERROR de Busqueda ", JOptionPane.ERROR_MESSAGE);
    
            
            }
        }//Fin action performed
    }//Fin del OyenteCOPIAR

////////////////////////////////////////////////////////////////////////////////   
    class OyenteBuscarYReemplazar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
try{
         String textoInicialDeBusqueda = panelTA.textArea.getSelectedText();
        if (textoInicialDeBusqueda == null) {
            textoInicialDeBusqueda = "";
        }
        
        //Solicitamos laas palabras a buscar y reemplazar, en caso de cancelacion saldrá por la excepción
        String textoABuscar = JOptionPane.showInputDialog(panelTA.textArea, "Texto a buscar", textoInicialDeBusqueda);
         if(textoABuscar==null){
            throw new ArithmeticException();
        }
        String textoNuevo = JOptionPane.showInputDialog(panelTA.textArea, "Texto NUEVO", textoInicialDeBusqueda);
        if(textoNuevo == null){
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
           
}catch(ArithmeticException e){
      JOptionPane.showMessageDialog(panelTA.textArea, "Operación Cancelada", "OPERACIÓN CANCELADA ", JOptionPane.CANCEL_OPTION);
    
}catch(Exception e){
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
            try (    //Crea un flujo de caracteres para grabar
                    FileWriter flujo = new FileWriter(fich_print) //Podriamos dejarle true para que seguiera escribiendo debajo de este si existiera, (aunqeu lo estoy eliminando mas arriba pòrque no es el caso)
                     ;PrintWriter escritor = new PrintWriter(flujo)) {
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
             JOptionPane.showMessageDialog(Panel_Pestanias.TP, "Aplicación simple de Bloc de Notas.\n\nRealiza las acciones normales\nque realizaría un Bloc de Notas\nincluyendo sus atajos.\n\n\nAutor: David Freyre Muñoz  2020", "Acerca de", HEIGHT,  new ImageIcon(icono));

        }//Fin action performed
    }//Fin del OyenteCOPIAR

////////////////////////////////////////////////////////////////////////////////   
    class OyenteSalir implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            System.exit(0);
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
}



/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////
//////CODIGOS MODELO PARA UTILIZAR EN EL DESARROLLO///////////////////////
////////////////////////////////////////////////////////////////////////
//   JOptionPane.showMessageDialog(panelPestanias.textArea, "que diseeeee", "dialogo TEST", JOptionPane.INFORMATION_MESSAGE);


//Este codigo nos listará los estilos visuales que tenemos disponibles
/*
       UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels(); 
        for (UIManager.LookAndFeelInfo look : looks) { 
            System.out.println(look.getClassName()); 
        } 
*/