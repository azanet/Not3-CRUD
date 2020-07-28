/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.time.LocalTime;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.UndoManager;

/**
 *
 * @author davidf
 */
public class VistaBBDD_Insertar extends JFrame {

    //Establecemos un objeto de "dimension", que le pasaremos al metodo
    //"setMinimunSize() para establecer el tamaño mínimo que podrá tener nuestra aplicación
    
    public JPanel panelFormularios; //Este es el panel que contiene los DISTINTOS PANELES que albergan los formularios y combobox
    
    public JPanel panelGrupos;
    public JPanel panelGrupoIzquierda;  //Renombrar como grupo izquierda
    public JPanel panelGrupoDerecha;//Renombrar como grupo derecha
    
    public JPanel panelArticulos;
    public JPanel panelArticuloIzquierda;//Renombrar como articulo izquierda
    public JPanel panelArticuloDerecha;//Renombrar como articulo derecha
    
    public JPanel panelDescripcion; //Renombrar a panel descripcion
    public JPanel panelDescripcionSuperior ;
    public  JTextArea textArea;
    public JScrollPane scroll;
    
    
      //DECLARANDO TODOS LOS ELEMENTOS QUE COMPONEN NUESTRA VENTANA
    //Declarando UndoManager (para rehacer y deshacer)
      public UndoManager manager; 
      //Declarando popupMenu y sus botones
    public JPopupMenu popMenu;
    public JMenuItem pop_Rehacer;
    public JMenuItem pop_Deshacer;    
    public JMenuItem pop_Copiar;
    public JMenuItem pop_Cortar;
    public JMenuItem pop_Pegar;
    public JMenuItem pop_Imprimir;
    
    //Declarando Campos y Area de Texto 
  
    
    //Declarando SCROLLPane para agregar el TExtArea a este mas abajo
   

    //Declarando Etiquetas
 

    //Declarando Botones

    
    
        
    
    
    
//Creando constructor
    public VistaBBDD_Insertar() {

        // super("Not3Pad");
                this.popMenu = new JPopupMenu();
        this.pop_Rehacer = new JMenuItem("Rehacer");
        this.pop_Deshacer = new JMenuItem("Deshacer");        
       this.pop_Copiar = new JMenuItem("Copiar");
        this.pop_Cortar = new JMenuItem("Cortar");
        this.pop_Pegar = new JMenuItem("Pegar");
       this. pop_Imprimir = new JMenuItem("Imprimir");
            
        
        
        Iniciar();

    }

    private void Iniciar() {
                
        this.popMenu.add(pop_Rehacer);
        this.popMenu.add(pop_Deshacer);
        this.popMenu.addSeparator();
        this.popMenu.add(pop_Copiar);
        this.popMenu.add(pop_Cortar);
        this.popMenu.add(pop_Pegar);
        this.popMenu.addSeparator();
        this.popMenu.add(pop_Imprimir);
                
        //JPOPUPMENU Agregamos a los botones el JPopupMenu su correspondiente Listener
 
        
        //Seteamos TITULO a la ventana principal 
        Image icono = new ImageIcon(getClass().getResource("/Images/LogoKrazyLab.png")).getImage();
        setIconImage(icono);
        setTitle("BBDD-INSERTAR");
        
        //Panel que contendra los paneles de GRUPOS,ARTICULOS
        panelFormularios= new JPanel();
        panelFormularios.setLayout(new BoxLayout(panelFormularios, BoxLayout.Y_AXIS)); //Indicamos qu elos agregue hacia ABAJO
      
        
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
        //Creamos PANEL en el que se alojaran los PANELES QUE CONFORMAN las OPCIONES y textos de GRUPOS
        panelGrupos= new JPanel();
        panelGrupos.setLayout(new BoxLayout(panelGrupos, BoxLayout.X_AXIS));//Indicamos que los agregue hacia LA DERECHA
        //Creamos un area vacía de 20px al principio de este contenedor (en la izquierda), para dejar un espacio (por estetica)
        panelGrupos.add(Box.createRigidArea(new Dimension(20,panelGrupos.getHeight())));
    
        
    //----------------------------------------------------------------------------------------------------------------------------------------    
        //Creamos el PANEL GRUPO-IZQUIERDA, el cual contendrŕa un combobox para escoger grupo y una etiqueta para indicar para que sirve
        panelGrupoIzquierda = new JPanel();
        panelGrupoIzquierda.setLayout(new BoxLayout(panelGrupoIzquierda, BoxLayout.Y_AXIS));//Indicamos qu elos agregue hacia ABAJO
       
        
        //Creamos la ETIQUETA que INDICARÁ que hace nuestro combobox
        JLabel tituloComboboxGrupo = new JLabel("Seleccione GRUPO    ");
        //Alineamos la eqtiqueta a la IZQUIERDA
        tituloComboboxGrupo.setAlignmentX(LEFT_ALIGNMENT); 
        //Establecemos un borde espaciado VACIO  arriba,izquierda,abajo,derecha 
         tituloComboboxGrupo.setBorder(BorderFactory.createEmptyBorder( 10,0, 5, 130)); //Creamos un borde para darle espacio arriba y abajo (por estetica)
        
        //Añadimos el TituloDelCombobox a este PANEL (GRUPO-IZQUIERDA)
         panelGrupoIzquierda.add(tituloComboboxGrupo);
              
        //Procedemos a crear nuestro COMBOBOX de GRUPOS
        JComboBox comboGrupos = new JComboBox();
        //Le asignamos tamaño 
        comboGrupos.setSize(new Dimension(250, 30));
        //Le asignamos tamaño Maximo (para que pueda redimensionarse un poco)
        comboGrupos.setMaximumSize(new Dimension(250, 30));
        comboGrupos.setMinimumSize(new Dimension(250, 30));
     
        
       
        //Le indicamos que se alineará a la izquierda
        comboGrupos.setAlignmentX(LEFT_ALIGNMENT);
        //Añadimos el Combobox a este PANEL (GRUPO-IZQUIERDA)
         panelGrupoIzquierda.add(comboGrupos);
         //Agregaremos un AreaRigida de unos cuantros Pixeles, para darle un poco de espacio entre el siguiente elemento que se agregue
         panelGrupoIzquierda.add(Box.createRigidArea(new Dimension(0,10)));
   
        //Ya completado el GRUPO_IZQUIERDA, procedemos a agregarlo al PANEL-GRUPOS
        panelGrupos.add(panelGrupoIzquierda);
        
        //Creamos un AreaRigida, para que cuando se encoja la pantalla, quede el mismo borde que pusimos al inicio y se vea bonito
        panelGrupos.add(Box.createRigidArea(new Dimension(20,panelGrupos.getHeight())));
        //Agregamos un separador entre los dos paneles GRUPOS poara separarlos y que se vea mejor
        JSeparator separator2 = new JSeparator(1);//Al llamar al separador utilizando 1 en el constructor, le indicamos que el separador estará en VERTICAL
          separator2.setSize(2,0); //El separador siempre hay que iniciarlo con este tamaño, y luego asignarle un tamaño maximo 
          separator2.setMaximumSize(new Dimension(2,600));
          //Agregamos el separador al Panel GRUPOS
          panelGrupos.add(separator2);      
          panelGrupos.add(Box.createRigidArea(new Dimension(20,panelGrupos.getHeight())));
          
               
     //----------------------------------------------------------------------------------------------------------------------------------------    
        //Creamos el PANEL GRUPO-DERECHA, el cual contendrŕa un TextField para insertar un grupo, una etiqueta para indicar para que sirve y un BOTON para realizar el ALTA del nuevo GRUPO        
            
        panelGrupoDerecha = new JPanel();
        panelGrupoDerecha.setLayout(new BoxLayout(panelGrupoDerecha, BoxLayout.Y_AXIS));//Indicamos qu elos agregue hacia ABAJO
       
        //Creamos la ETIQUETA que INDICARÁ que hace nuestro TextField
        JLabel tituloGrupoNuevo = new JLabel("Nombre para Nuevo GRUPO:");
        //Alineamos la eqtiqueta a la IZQUIERDA
         tituloGrupoNuevo.setAlignmentX(LEFT_ALIGNMENT); 
            tituloGrupoNuevo.setBorder(BorderFactory.createEmptyBorder( 10,0, 0, 0)); //Creamos un borde para darle espacio arriba y abajo (por estetica)
   //     panelGrupoDerecha.add(Box.createHorizontalGlue()); 
        panelGrupoDerecha.add(tituloGrupoNuevo); 
        
         
         JTextField textFieldGrupoNuevo = new JTextField(50);
         textFieldGrupoNuevo.setSize(new Dimension(150, 30));
            //Le asignamos tamaño Maximo (para que pueda redimensionarse un poco)
      textFieldGrupoNuevo.setMaximumSize(new Dimension(400, 30));
         textFieldGrupoNuevo.setAlignmentX(LEFT_ALIGNMENT); 
         panelGrupoDerecha.add(textFieldGrupoNuevo);
               //Agregaremos un AreaRigida de unos cuantros Pixeles, para darle un poco de espacio entre el siguiente elemento que se agregue
         panelGrupoDerecha.add(Box.createRigidArea(new Dimension(0,2)));
         
         //Creamos BOTON PARA ENVIAR GRUPO NUEVO
         JButton AltaGrupo = new JButton("Crear Grupo");
         AltaGrupo.setBackground(new Color(206,255,246));
         panelGrupoDerecha.add(AltaGrupo);
   //       panelGrupoDerecha.setBorder(BorderFactory.createEmptyBorder( 10,10, 0, 10));
       panelGrupoDerecha.add(Box.createRigidArea(new Dimension(panelGrupos.getWidth(),10)));
          
          panelGrupos.add(panelGrupoDerecha);
        panelGrupos.add(Box.createRigidArea(new Dimension(20,panelGrupos.getHeight())));
           //Le ponemos "PEGAMENTO" XD, para que se haga un area invisible que permitirá redimensionar la ventana sin que este componente se mueva de su sitio (ya que el area pegamento será la que se estire o encoja según precise)
            panelGrupos.add(Box.createHorizontalGlue()); 
        
        //AÑADIENDO PANEL GRUPOS A EL PANEL "Formularios"
        panelFormularios.add(panelGrupos);
         
          JSeparator separator = new JSeparator();
        panelFormularios.add( separator);
       
  ///////////////////FIN DE PANEL grupos//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        
  
    ///////////////////COMIENZA EL PANEL ARTICULOS,QUE CONTIENE LOS PANELES NECESARIOS PARA trabajar con los articulos///////////////////////////////   
    
    
            panelArticulos= new JPanel();
        panelArticulos.setLayout(new BoxLayout(panelArticulos, BoxLayout.X_AXIS));//Indicamos que los agregue hacia LA DERECHA
        //Creamos un area vacía de 20px al principio de este contenedor (en la izquierda), para dejar un espacio (por estetica)
         panelArticulos.add(Box.createRigidArea(new Dimension(20, panelArticulos.getHeight())));
    
        
    //----------------------------------------------------------------------------------------------------------------------------------------    
        //Creamos el PANEL ARTICULO-IZQUIERDA, el cual contendrŕa un combobox para escoger grupo y una etiqueta para indicar para que sirve
        panelArticuloIzquierda = new JPanel();
        panelArticuloIzquierda.setLayout(new BoxLayout(panelArticuloIzquierda, BoxLayout.Y_AXIS));//Indicamos qu elos agregue hacia ABAJO
       
        
        //Creamos la ETIQUETA que INDICARÁ que hace nuestro combobox
        JLabel tituloComboboxArticulo = new JLabel("Seleccione ARTICULO");
        //Alineamos la eqtiqueta a la IZQUIERDA
        tituloComboboxArticulo.setAlignmentX(LEFT_ALIGNMENT); 
        //Establecemos un borde espaciado VACIO  arriba,izquierda,abajo,derecha 
         tituloComboboxArticulo.setBorder(BorderFactory.createEmptyBorder( 10,0, 5, 131)); //Creamos un borde para darle espacio arriba y abajo (por estetica)
      
        //Añadimos el TituloDelCombobox a este PANEL (ARTICULO-IZQUIERDA)
          panelArticuloIzquierda.add(tituloComboboxArticulo);
              
        //Procedemos a crear nuestro COMBOBOX de GRUPOS
        JComboBox comboArticulos = new JComboBox();
        //Le asignamos tamaño 
        comboArticulos.setSize(new Dimension(250, 30));
        //Le asignamos tamaño Maximo (para que pueda redimensionarse un poco)
        comboArticulos.setMaximumSize(new Dimension(250, 30));
        comboArticulos.setMinimumSize(new Dimension(250, 30));
     
        
       
        //Le indicamos que se alineará a la izquierda
        comboArticulos.setAlignmentX(LEFT_ALIGNMENT);
        //Añadimos el Combobox a este PANEL (GRUPO-IZQUIERDA)
         panelArticuloIzquierda.add(comboArticulos);
         //Agregaremos un AreaRigida de unos cuantros Pixeles, para darle un poco de espacio entre el siguiente elemento que se agregue
         panelArticuloIzquierda.add(Box.createRigidArea(new Dimension(0,10)));
   
        //Ya completado el GRUPO_IZQUIERDA, procedemos a agregarlo al PANEL-GRUPOS
        panelArticulos.add(panelArticuloIzquierda);
        
        //Creamos un AreaRigida, para que cuando se encoja la pantalla, quede el mismo borde que pusimos al inicio y se vea bonito
        panelArticulos.add(Box.createRigidArea(new Dimension(20,panelArticulos.getHeight())));
        //Agregamos un separador entre los dos paneles GRUPOS poara separarlos y que se vea mejor
        JSeparator separator3 = new JSeparator(1);//Al llamar al separador utilizando 1 en el constructor, le indicamos que el separador estará en VERTICAL
          separator3.setSize(2,0); //El separador siempre hay que iniciarlo con este tamaño, y luego asignarle un tamaño maximo 
          separator3.setMaximumSize(new Dimension(2,600));
          //Agregamos el separador al Panel GRUPOS
          panelArticulos.add(separator3);      
          panelArticulos.add(Box.createRigidArea(new Dimension(20,panelArticulos.getHeight())));
          
               
     //----------------------------------------------------------------------------------------------------------------------------------------    
        //Creamos el PANEL ARTICULOS-DERECHA, el cual contendrŕa un TextField para insertar un grupo, una etiqueta para indicar para que sirve y un BOTON para realizar el ALTA del nuevo GRUPO        
            
        panelArticuloDerecha = new JPanel();
        panelArticuloDerecha.setLayout(new BoxLayout(panelArticuloDerecha, BoxLayout.Y_AXIS));//Indicamos qu elos agregue hacia ABAJO
       
        //Creamos la ETIQUETA que INDICARÁ que hace nuestro TextField
        JLabel tituloArticuloNuevo = new JLabel("Nombre para Nuevo ARTÍCULO:");
        //Alineamos la eqtiqueta a la IZQUIERDA
         tituloArticuloNuevo.setAlignmentX(LEFT_ALIGNMENT); 
            tituloArticuloNuevo.setBorder(BorderFactory.createEmptyBorder( 10,0, 0, 0)); //Creamos un borde para darle espacio arriba y abajo (por estetica)
   //     panelArticuloDerecha.add(Box.createHorizontalGlue()); 
        panelArticuloDerecha.add(tituloArticuloNuevo); 
        
         
         JTextField textFieldArticuloNuevo = new JTextField(50);
         textFieldArticuloNuevo.setSize(new Dimension(150, 30));
            //Le asignamos tamaño Maximo (para que pueda redimensionarse un poco)
      textFieldArticuloNuevo.setMaximumSize(new Dimension(400, 30));
         textFieldArticuloNuevo.setAlignmentX(LEFT_ALIGNMENT); 
         panelArticuloDerecha.add(textFieldArticuloNuevo);
               //Agregaremos un AreaRigida de unos cuantros Pixeles, para darle un poco de espacio entre el siguiente elemento que se agregue
         panelArticuloDerecha.add(Box.createRigidArea(new Dimension(0,2)));
         
         //Creamos BOTON PARA ENVIAR GRUPO NUEVO
         JButton AltaArticulo = new JButton("Crear Artículo");
         AltaArticulo.setBackground(new Color(206,255,246));
         panelArticuloDerecha.add(AltaArticulo);
   //       panelArticuloDerecha.setBorder(BorderFactory.createEmptyBorder( 10,10, 0, 10));
       panelArticuloDerecha.add(Box.createRigidArea(new Dimension(panelArticulos.getWidth(),10)));
          
          panelArticulos.add(panelArticuloDerecha);
        panelArticulos.add(Box.createRigidArea(new Dimension(20,panelArticulos.getHeight())));
           panelArticulos.add(Box.createHorizontalGlue()); 
        
        //AÑADIENDO PANEL GRUPOS A EL PANEL "Formularios"
        panelFormularios.add(panelArticulos);
         
          JSeparator separator4 = new JSeparator();
        panelFormularios.add( separator4);
      
    
  ///////////////////FIN DE PANEL ARTICULOS//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
      
    
        
         panelDescripcion = new JPanel();
        panelDescripcion.setLayout(new BoxLayout(panelDescripcion, BoxLayout.Y_AXIS));
        
          
         panelDescripcionSuperior = new JPanel();
         panelDescripcionSuperior.setLayout(new BoxLayout( panelDescripcionSuperior , BoxLayout.X_AXIS));//Indicamos qu elos agregue hacia a la DERECHA
         panelDescripcionSuperior.add(Box.createRigidArea(new Dimension(panelDescripcionSuperior.getWidth(),40)));
        panelDescripcionSuperior.add(Box.createRigidArea(new Dimension(10,panelDescripcionSuperior.getHeight())));
        //Inicializando CAMPOS y AREA de Texto (aqui indicaremos lo que queremos que tenga escrito dentro el campo, EN MI CASO ESTARAN VACIOS)
   
        JLabel tituloDescripcion= new JLabel("Escriba Descripción:");
        tituloDescripcion.setBorder(BorderFactory.createEmptyBorder( 30,0, 0, 0));
        panelDescripcionSuperior.add(tituloDescripcion);
        
        panelDescripcionSuperior.add(Box.createRigidArea(new Dimension(30,panelDescripcionSuperior.getHeight())));
        
        JButton agregarDescripcion = new JButton("INSERTAR Descripción");
        agregarDescripcion.setBackground(new Color(184,255,181));
        agregarDescripcion.setBorder(BorderFactory.createEmptyBorder( 10, 8, 10, 8));
        panelDescripcionSuperior.add(agregarDescripcion);
        panelDescripcionSuperior.add(Box.createRigidArea(new Dimension(35,panelDescripcionSuperior.getHeight())));
        
         JSeparator separator5 = new JSeparator(1);
           separator5.setSize(2,0); //El separador siempre hay que iniciarlo con este tamaño, y luego asignarle un tamaño maximo 
          separator5.setMaximumSize(new Dimension(2,600));
        panelDescripcionSuperior.add( separator5);
        panelDescripcionSuperior.add(Box.createRigidArea(new Dimension(35,panelDescripcionSuperior.getHeight())));
    
        
        
        JButton limpiarPantalla = new JButton("Limpiar Pantalla");
        limpiarPantalla.setBackground(new Color(255,253,160));
        limpiarPantalla.setBorder(BorderFactory.createEmptyBorder( 15, 8, 15, 8));
        panelDescripcionSuperior.add(limpiarPantalla);
        panelDescripcionSuperior.add(Box.createRigidArea(new Dimension(20,panelDescripcionSuperior.getHeight())));
        
        JButton salir = new JButton("SALIR");
        salir.setBackground(new Color(255,156,154));
        salir.setBorder(BorderFactory.createEmptyBorder( 15, 8, 15, 8));
        panelDescripcionSuperior.add(salir);
        
        
        panelDescripcionSuperior.add(Box.createHorizontalGlue());
        
        panelDescripcion.add( panelDescripcionSuperior);
  //      tituloTA.setBorder(BorderFactory.createEmptyBorder( 10,10, 10, 10));
  
        textArea = new JTextArea("");//INicializando al TextArea 
        textArea.setEditable(true);//Haciendo que TextArea NO SEA EDITABLE
        //Agregamos el PopUpMenu al TextArea
        textArea.setComponentPopupMenu(this.popMenu);
        //Con este metodo hacemos que se despliegue el menu al clicar el boton derecho
       textArea.setInheritsPopupMenu(true);
        
        scroll= new JScrollPane(textArea);//Agregando SCROLL a TextArea
          //Establecemos un borde espaciado VACIO  arriba,izquierda,abajo,derecha 
         scroll.setBorder(BorderFactory.createEmptyBorder( 2,10, 10, 10));
        panelDescripcion.add(scroll); 
                
        
        
    //    TextArea_existentes.setLineWrap(true); //Haciendo que se haga salto de linea al llegar al final
       // TextArea_existentes.setWrapStyleWord(true);
        //Agregamos TEXTAREA al Scroll, como textArea está agregado a Scroll, será a este al que AGREGEMOS y ASIGNEMOS el tamaño mas abajo

        //Inicializamos los componentes del PopUp-menu

            
        
          //Menú POP-UP AGREGANDO Botones al PopupMenu


       
        
        //Agregamos el PopUpMenu al TextArea
        textArea.setComponentPopupMenu(popMenu);
        //Con este metodo hacemos que se despliegue el menu al clicar el boton derecho
       textArea.setInheritsPopupMenu(true);
     
        
             //Agregando manager para //REHACER-DESHACER
       manager = new UndoManager();
       Document document = textArea.getDocument();
       document.addUndoableEditListener(new UndoableEditListener() {
           @Override
           public void undoableEditHappened(UndoableEditEvent e) {
               manager.addEdit(e.getEdit());
           }
       });
        
        
        
                
        //DESCOMENTANDO ESTAS LINEAS, PODEMOS ESTABLECER UN ESTILO VISUAL DISTINTO PARA NUESTRA APLICACIÓN UTILIZANDO LA LIBRERIA "JTATTOO"
        //EL ESTILO VISUAL HAY QUE SETEARSE ANTES DE MOSTRAR LA VENTANA, O DARÁ ERROR        
        try {
            //Ponemos primero el nombre del paquete, y seguido de este, la clase LookandFeel del estilo que queremos
            //   UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null, "Error al intentar establecer el estilo de vista\nSe establecerá el estilo visual por defecto");
        }
        
          
                   
        //JPOPUPMENU Agregamos a los botones el JPopupMenu su correspondiente Listener
      pop_Rehacer.addActionListener(new OyentePopRehacer());
      pop_Deshacer.addActionListener(new OyentePopDeshacer());        
      pop_Copiar.addActionListener(new OyentePopCopiar());
      pop_Cortar.addActionListener(new OyentePopCortar());
      pop_Pegar.addActionListener(new OyentePopPegar());
      pop_Imprimir.addActionListener(new OyentePopImprimir());

        
      //          setResizable(false);
        //Con este método haremos que la pantalla salga JUSTO EN EL CENTRO
       
        //Hacemos visible la vista Principal      
     //   setVisible(true);
     Container contentPane = getContentPane();
       contentPane.add(panelFormularios,BorderLayout.NORTH);
       contentPane.add(panelDescripcion, BorderLayout.CENTER);
               this.setBounds(0,0,600, 600);
               this.setMinimumSize(new Dimension(600, 600));
        // this.setLocationRelativeTo(null);        

    }//Fin de iniciAR
    
    
    
    
    ////////////////////////////////////////////////////////////////////////////////    
///////////////////  POP-UP MENU  //////////////////////////////////////////////
////////////CONFIGURARÉ LOS OYENTES DEL JPOPUPMENU EN ESTA MISMA CLASE//////////   
    ////////////////////////////////////////////////////////////////////////////////   
    class OyentePopRehacer implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
           if(manager.canRedo())
                manager.redo();
        }//Fin action performed
    }//Fin del OyenteCOPIAR
    
    
    ////////////////////////////////////////////////////////////////////////////////   
    class OyentePopDeshacer implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            if(manager.canUndo())
                manager.undo();
            
        }//Fin action performed
    }//Fin del OyenteCOPIAR
    
    
    class OyentePopCopiar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            
            textArea.copy();
        }//Fin action performed
    }//Fin del OyenteCOPIAR

////////////////////////////////////////////////////////////////////////////////   
    class OyentePopCortar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ

            textArea.cut();
        }//Fin action performed
    }//Fin del OyenteCOPIAR

////////////////////////////////////////////////////////////////////////////////   
    class OyentePopPegar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            textArea.paste();
        }//Fin action performed
    }//Fin del OyenteCOPIAR



    ////////////////////////////////////////////////////////////////////////////////   
    class OyentePopImprimir implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
                textArea.print();
            } catch (PrinterException ex) {
                 JOptionPane.showMessageDialog(null, "Se produjo un error de impresión", "Error de Impresión", JOptionPane.ERROR_MESSAGE);
            }
        }//Fin action performed
    }//Fin del OyenteCOPIAR
    

    
    
       
  
           
    
    
    

}//Fin de BBDD_Insertar
