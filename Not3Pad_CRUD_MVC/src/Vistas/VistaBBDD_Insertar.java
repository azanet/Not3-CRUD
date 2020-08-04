
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
import java.awt.Image;
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
public class VistaBBDD_Insertar extends JFrame{

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
    public JTextField textFieldGrupoNuevo,textFieldArticuloNuevo ;
     //Declarando Etiquetas
   public JLabel tituloComboboxGrupo,tituloGrupoNuevo,tituloComboboxArticulo,tituloArticuloNuevo,tituloDescripcion;
    //Declarando combobox
    public JComboBox comboGrupos,comboArticulos;
   //Declarando Botones
   public JButton BotonAltaGrupo, BotonAltaArticulo, BotonagregarDescripcion, BotonlimpiarPantalla,Botonsalir ;
   
    
   

    
    
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
        panelGrupos.add(Box.createHorizontalGlue()); 
    
        
    //----------------------------------------------------------------------------------------------------------------------------------------    
        //Creamos el PANEL GRUPO-IZQUIERDA, el cual contendrŕa un combobox para escoger grupo y una etiqueta para indicar para que sirve
        panelGrupoIzquierda = new JPanel();
        panelGrupoIzquierda.setLayout(new BoxLayout(panelGrupoIzquierda, BoxLayout.Y_AXIS));//Indicamos qu elos agregue hacia ABAJO
       
        
        //Creamos la ETIQUETA que INDICARÁ que hace nuestro combobox
        tituloComboboxGrupo = new JLabel("Seleccione GRUPO "+((char)9660));
        //Alineamos la eqtiqueta a la IZQUIERDA
        tituloComboboxGrupo.setAlignmentX(LEFT_ALIGNMENT); 
        //Establecemos un borde espaciado VACIO  arriba,izquierda,abajo,derecha 
         tituloComboboxGrupo.setBorder(BorderFactory.createEmptyBorder( 10,0, 5,0)); //Creamos un borde para darle espacio arriba y abajo (por estetica)
        
        //Añadimos el TituloDelCombobox a este PANEL (GRUPO-IZQUIERDA)
         panelGrupoIzquierda.add(tituloComboboxGrupo);
              
        //Procedemos a crear nuestro COMBOBOX de GRUPOS
       comboGrupos = new JComboBox();
        //Le asignamos tamaño 
        comboGrupos.setPreferredSize(new Dimension(350, 30));
        //Le asignamos tamaño Maximo (para que pueda redimensionarse un poco)
        comboGrupos.setMaximumSize(new Dimension(350, 30));
        comboGrupos.setMinimumSize(new Dimension(350, 30));
     
        
       
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
        tituloGrupoNuevo = new JLabel("Nombre para Nuevo GRUPO:");
        //Alineamos la eqtiqueta a la IZQUIERDA
         tituloGrupoNuevo.setAlignmentX(LEFT_ALIGNMENT); 
            tituloGrupoNuevo.setBorder(BorderFactory.createEmptyBorder( 10,0, 0, 0)); //Creamos un borde para darle espacio arriba y abajo (por estetica)
   //     panelGrupoDerecha.add(Box.createHorizontalGlue()); 
        panelGrupoDerecha.add(tituloGrupoNuevo); 
        
         
         textFieldGrupoNuevo = new JTextField(38);
         textFieldGrupoNuevo.setPreferredSize(new Dimension(350, 30));
            //Le asignamos tamaño Maximo (para que pueda redimensionarse un poco)
      textFieldGrupoNuevo.setMaximumSize(new Dimension(350, 30));
         textFieldGrupoNuevo.setAlignmentX(LEFT_ALIGNMENT); 
         panelGrupoDerecha.add(textFieldGrupoNuevo);
               //Agregaremos un AreaRigida de unos cuantros Pixeles, para darle un poco de espacio entre el siguiente elemento que se agregue
         panelGrupoDerecha.add(Box.createRigidArea(new Dimension(0,2)));
         
         //Creamos BOTON PARA ENVIAR GRUPO NUEVO
        BotonAltaGrupo = new JButton("Crear Grupo");
         BotonAltaGrupo.setBackground(new Color(206,255,246));
         panelGrupoDerecha.add(BotonAltaGrupo);
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
         panelArticulos.add(Box.createHorizontalGlue()); 
        
    //----------------------------------------------------------------------------------------------------------------------------------------    
        //Creamos el PANEL ARTICULO-IZQUIERDA, el cual contendrŕa un combobox para escoger grupo y una etiqueta para indicar para que sirve
        panelArticuloIzquierda = new JPanel();
        panelArticuloIzquierda.setLayout(new BoxLayout(panelArticuloIzquierda, BoxLayout.Y_AXIS));//Indicamos qu elos agregue hacia ABAJO
       
        
        //Creamos la ETIQUETA que INDICARÁ que hace nuestro combobox
        tituloComboboxArticulo = new JLabel("Seleccione ARTICULO "+((char)9660));
        //Alineamos la eqtiqueta a la IZQUIERDA
        tituloComboboxArticulo.setAlignmentX(LEFT_ALIGNMENT); 
        //Establecemos un borde espaciado VACIO  arriba,izquierda,abajo,derecha 
         tituloComboboxArticulo.setBorder(BorderFactory.createEmptyBorder( 10,0, 5,0)); //Creamos un borde para darle espacio arriba y abajo (por estetica)
      
        //Añadimos el TituloDelCombobox a este PANEL (ARTICULO-IZQUIERDA)
          panelArticuloIzquierda.add(tituloComboboxArticulo);
              
        //Procedemos a crear nuestro COMBOBOX de GRUPOS
         comboArticulos = new JComboBox();
        //Le asignamos tamaño 
        comboArticulos.setPreferredSize(new Dimension(350, 30));
        //Le asignamos tamaño Maximo (para que pueda redimensionarse un poco)
        comboArticulos.setMaximumSize(new Dimension(350, 30));
        comboArticulos.setMinimumSize(new Dimension(350, 30));
     
        
       
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
         tituloArticuloNuevo = new JLabel("Nombre para Nuevo ARTÍCULO:");
        //Alineamos la eqtiqueta a la IZQUIERDA
         tituloArticuloNuevo.setAlignmentX(LEFT_ALIGNMENT); 
            tituloArticuloNuevo.setBorder(BorderFactory.createEmptyBorder( 10,0, 0, 0)); //Creamos un borde para darle espacio arriba y abajo (por estetica)
   //     panelArticuloDerecha.add(Box.createHorizontalGlue()); 
        panelArticuloDerecha.add(tituloArticuloNuevo); 
        
         
         textFieldArticuloNuevo = new JTextField(38);
         textFieldArticuloNuevo.setPreferredSize(new Dimension(350, 30));
            //Le asignamos tamaño Maximo (para que pueda redimensionarse un poco)
      textFieldArticuloNuevo.setMaximumSize(new Dimension(350, 30));
         textFieldArticuloNuevo.setAlignmentX(LEFT_ALIGNMENT); 
         panelArticuloDerecha.add(textFieldArticuloNuevo);
               //Agregaremos un AreaRigida de unos cuantros Pixeles, para darle un poco de espacio entre el siguiente elemento que se agregue
         panelArticuloDerecha.add(Box.createRigidArea(new Dimension(0,2)));
         
         //Creamos BOTON PARA ENVIAR GRUPO NUEVO
          BotonAltaArticulo = new JButton("Crear Artículo");
         BotonAltaArticulo.setBackground(new Color(206,255,246));
         panelArticuloDerecha.add(BotonAltaArticulo);
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
        panelDescripcion.add(Box.createHorizontalGlue()); 
          
         panelDescripcionSuperior = new JPanel();
         panelDescripcionSuperior.setLayout(new BoxLayout( panelDescripcionSuperior , BoxLayout.X_AXIS));//Indicamos qu elos agregue hacia a la DERECHA
         //Le agregamos PEGAMENTO, para que se estire y quede centrado cuando redimensione
      
         panelDescripcionSuperior.add(Box.createRigidArea(new Dimension(panelDescripcionSuperior.getWidth(),40)));
        panelDescripcionSuperior.add(Box.createRigidArea(new Dimension(10,panelDescripcionSuperior.getHeight())));
        //Inicializando CAMPOS y AREA de Texto (aqui indicaremos lo que queremos que tenga escrito dentro el campo, EN MI CASO ESTARAN VACIOS)
        
        
        
                
        tituloDescripcion = new JLabel("Ingrese Descripción:");
        tituloDescripcion.setBorder(BorderFactory.createEmptyBorder( 30,8, 0, 0));
        panelDescripcionSuperior.add(tituloDescripcion);
        
        //Creamos area rigida para que se mantenga FIJO en la misma posicion aunque redimensionemos
        panelDescripcionSuperior.add(Box.createRigidArea(new Dimension(57,panelDescripcionSuperior.getHeight())));
        //Le agregamos PEGAMENTO, para que se estire y quede centrado cuando redimensione MENOS EL "INGRESE DESCRIPCION"
        panelDescripcionSuperior.add(Box.createHorizontalGlue()); 
        
        BotonagregarDescripcion = new JButton("INSERTAR Descripción");
        BotonagregarDescripcion.setBackground(new Color(184,255,181));
        BotonagregarDescripcion.setBorder(BorderFactory.createEmptyBorder( 15, 8, 15, 8));
        panelDescripcionSuperior.add(BotonagregarDescripcion);
        panelDescripcionSuperior.add(Box.createRigidArea(new Dimension(35,panelDescripcionSuperior.getHeight())));
        
         JSeparator separator5 = new JSeparator(1);
           separator5.setSize(2,0); //El separador siempre hay que iniciarlo con este tamaño, y luego asignarle un tamaño maximo 
          separator5.setMaximumSize(new Dimension(2,600));
        panelDescripcionSuperior.add( separator5);
       panelDescripcionSuperior.add(Box.createRigidArea(new Dimension(165,panelDescripcionSuperior.getHeight())));
      
        
        
        BotonlimpiarPantalla = new JButton("Limpiar Pantalla");
        BotonlimpiarPantalla.setBackground(new Color(255,253,160));
        BotonlimpiarPantalla.setBorder(BorderFactory.createEmptyBorder( 15, 8, 15, 8));
        panelDescripcionSuperior.add(BotonlimpiarPantalla);
        panelDescripcionSuperior.add(Box.createRigidArea(new Dimension(20,panelDescripcionSuperior.getHeight())));
        
        Botonsalir = new JButton("SALIR");
        Botonsalir.setBackground(new Color(255,156,154));
        Botonsalir.setBorder(BorderFactory.createEmptyBorder( 15, 8, 15, 8));
        
        //Agregamos un area fija, para que quede centrado en el sitio que debe ir y le agregamos Pegamento para la redimension
       panelDescripcionSuperior.add(Botonsalir);
       
        panelDescripcionSuperior.add(Box.createRigidArea(new Dimension(120,panelDescripcionSuperior.getHeight())));
        panelDescripcionSuperior.add(Box.createHorizontalGlue());        
        
   //      panelDescripcionSuperior.add(Box.createRigidArea(new Dimension(20,panelGrupos.getHeight())));
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
         scroll.setBorder(BorderFactory.createEmptyBorder( 2,15, 15, 15));
         panelDescripcion.add(scroll); 
                  
  
       
        
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
        
          
                   


        
      //          setResizable(false);
        //Con este método haremos que la pantalla salga JUSTO EN EL CENTRO
       
        //Hacemos visible la vista Principal      
     //   setVisible(true);
     Container contentPane = getContentPane();
       contentPane.add(panelFormularios,BorderLayout.NORTH);
       contentPane.add(panelDescripcion, BorderLayout.CENTER);
               this.setBounds(0,0,600, 600);
               this.setMinimumSize(new Dimension(854, 600));
        // this.setLocationRelativeTo(null);        

    }//Fin de iniciAR

    
    

    
 

    
    
       
  
           
    
    
    

}//Fin de BBDD_Insertar