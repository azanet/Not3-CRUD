/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;


import Controlador.ControladorVistaPrincipal;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
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
public class VistaBBDD_Consultar extends JFrame {

    public JPanel panelFormularios; //Este es el panel que contiene los DISTINTOS PANELES que albergan los formularios y combobox

    
    //Declarando los RADIOCHECK BUTTON
    public JRadioButton RB_Selec_DUO; //RadioBut. para seleccionar busqueda con los 2 los comboboxes DUO
    public JRadioButton RB_Selec_Grupo_SOLO; //RadioBut. para seleccionar busqueda conel combobox Grupo_solo
    public JRadioButton RB_Selec_Articulo_SOLO; //RadioBut. para seleccionar busqueda conel combobox Articulo_solo
    
    public ButtonGroup grupoRB = new ButtonGroup();
    
    
    public JPanel panelGrupos;
    public JPanel panelGrupoIzquierda;  //Renombrar como grupo izquierda
    public JPanel panelGrupoDerecha;//Renombrar como grupo derecha
    
    public JPanel panelArticulos;
    public JPanel panelArticuloIzquierda;//Renombrar como articulo izquierda
    public JPanel panelArticuloDerecha;//Renombrar como articulo derecha
    
    public JPanel panelDescripcion; //Renombrar a panel descripcion
    public JPanel panelDescripcionSuperior ;
    public  static JTextArea textArea;
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
        //Declarando Etiquetas
   public JLabel tituloGrupo_DUO,tituloArticulo_DUO, tituloGrupo_SOLO,tituloArticulo_SOLO,tituloDescripcion;
   public JLabel tituloRB_DUO, tituloRB_Grupo_SOLO, tituloRB_Articulo_SOLO;
    //Declarando combobox
    public JComboBox comboGrupos_DUO,comboArticulos_DUO, comboGrupos_SOLO,comboArticulos_SOLO;
   //Declarando Botones
   public JButton BotonConsultarDescripcion, BotonlimpiarPantalla,Botonsalir ;
   
    
   

    
    
    
//Creando constructor
    public VistaBBDD_Consultar() {

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
        setTitle("BBDD-CONSULTAR");
        
        //Panel que contendra los paneles de GRUPOS,ARTICULOS
        panelFormularios= new JPanel();
        panelFormularios.setLayout(new BoxLayout(panelFormularios, BoxLayout.Y_AXIS)); //Indicamos qu elos agregue hacia ABAJO
         
         
  
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
        //Creamos PANEL en el que se alojaran los PANELES QUE CONFORMAN las OPCIONES y textos de GRUPOS
        panelGrupos= new JPanel();
        panelGrupos.setLayout(new BoxLayout(panelGrupos, BoxLayout.X_AXIS));//Indicamos que los agregue hacia LA DERECHA
        //Creamos un area vacía de 20px al principio de este contenedor (en la izquierda), para dejar un espacio (por estetica)
    //    panelGrupos.add(Box.createRigidArea(new Dimension(20,panelGrupos.getHeight())));
        panelGrupos.add(Box.createHorizontalGlue()); 
    
             JSeparator separator2 = new JSeparator(1);//Al llamar al separador utilizando 1 en el constructor, le indicamos que el separador estará en VERTICAL
          separator2.setSize(2,0); //El separador siempre hay que iniciarlo con este tamaño, y luego asignarle un tamaño maximo 
          separator2.setMaximumSize(new Dimension(2,600));
          //Agregamos el separador al Panel GRUPOS
          panelGrupos.add(separator2);      
    
         panelGrupos.add(Box.createRigidArea(new Dimension(40,panelGrupos.getHeight())));
          
    //----------------------------------------------------------------------------------------------------------------------------------------    
        //Creamos el PANEL GRUPO-IZQUIERDA, el cual contendrŕa un combobox para escoger grupo y una etiqueta para indicar para que sirve
        panelGrupoIzquierda = new JPanel();
        panelGrupoIzquierda.setLayout(new BoxLayout(panelGrupoIzquierda, BoxLayout.Y_AXIS));//Indicamos qu elos agregue hacia ABAJO
    
        panelGrupoIzquierda.add(Box.createRigidArea(new Dimension(panelGrupoIzquierda.getWidth(),5)));
        //Inicialozamos el RADIOBUTTON y le indicamos desde aquí que será el botón por defecto
        //Recordamos que el boton pertenece a un grupo junto con los demas radioBotones
        //Para que solo se pueda elegir una opción a la vez
          RB_Selec_DUO= new JRadioButton("FILTRAR POR \"GRUPO y ARTÍCULO\"");
          RB_Selec_DUO.setSelected(true);
          RB_Selec_DUO.setAlignmentX(LEFT_ALIGNMENT); 
                RB_Selec_DUO.setBackground(new Color(195,201,223));

        
          RB_Selec_DUO.setOpaque(true);
          
         //Agregamos el Radioboton al PANEL GRUPO IZQUIERDA
          panelGrupoIzquierda.add(RB_Selec_DUO);
    
       panelGrupoIzquierda.add(Box.createRigidArea(new Dimension(panelGrupoIzquierda.getWidth(),10)));
 
      
        //Creamos la ETIQUETA que INDICARÁ que hace nuestro combobox
        tituloGrupo_DUO = new JLabel("Seleccione GRUPO "+((char)9660));
        //Alineamos la eqtiqueta a la IZQUIERDA
        tituloGrupo_DUO.setAlignmentX(LEFT_ALIGNMENT); 
        //Establecemos un borde espaciado VACIO  arriba,izquierda,abajo,derecha 
         tituloGrupo_DUO.setBorder(BorderFactory.createEmptyBorder( 10,0, 5, 0)); //Creamos un borde para darle espacio arriba y abajo (por estetica)
        
        //Añadimos el TituloDelCombobox a este PANEL (GRUPO-IZQUIERDA)
         panelGrupoIzquierda.add(tituloGrupo_DUO);
              
        //Procedemos a crear nuestro COMBOBOX de GRUPOS
       comboGrupos_DUO = new JComboBox();
       
        //Le asignamos tamaño con SETPREFERED SIZE
        comboGrupos_DUO.setPreferredSize(new Dimension(350, 30));
       
        comboGrupos_DUO.setMaximumSize(new Dimension(350, 30));
        comboGrupos_DUO.setMinimumSize(new Dimension(350, 30));
       
        
       
        //Le indicamos que se alineará a la izquierda
        comboGrupos_DUO.setAlignmentX(LEFT_ALIGNMENT);
        //Añadimos el Combobox a este PANEL (GRUPO-IZQUIERDA)
         panelGrupoIzquierda.add(comboGrupos_DUO);
         //Agregaremos un AreaRigida de unos cuantros Pixeles, para darle un poco de espacio entre el siguiente elemento que se agregue
         panelGrupoIzquierda.add(Box.createRigidArea(new Dimension(0,10)));
   
        //Ya completado el GRUPO_IZQUIERDA, procedemos a agregarlo al PANEL-GRUPOS
        panelGrupos.add(panelGrupoIzquierda);
        
        //Creamos un Area Rígida, en el panel GRUPOS, para darle separación a los dos Paneles superiores (de grupo)
        panelGrupos.add(Box.createRigidArea(new Dimension(60,panelGrupos.getHeight())));
          
               
     //----------------------------------------------------------------------------------------------------------------------------------------    
        //Creamos el PANEL GRUPO-DERECHA, el cual contendrŕa un TextField para insertar un grupo, una etiqueta para indicar para que sirve y un BOTON para realizar el ALTA del nuevo GRUPO        
            
        panelGrupoDerecha = new JPanel();
        panelGrupoDerecha.setLayout(new BoxLayout(panelGrupoDerecha, BoxLayout.Y_AXIS));//Indicamos qu elos agregue hacia ABAJO
              
        panelGrupoDerecha.add(Box.createRigidArea(new Dimension(0,33)));// Creamos este area rígida para igualarlo en altura a los objetos del otro panel
        //Creamos la ETIQUETA que INDICARÁ que hace nuestro combobox
        tituloArticulo_DUO = new JLabel("Seleccione ARTÍCULO "+((char)9660));
        //Alineamos la eqtiqueta a la IZQUIERDA
        tituloArticulo_DUO.setAlignmentX(LEFT_ALIGNMENT); 
        //Establecemos un borde espaciado VACIO  arriba,izquierda,abajo,derecha 
         tituloArticulo_DUO.setBorder(BorderFactory.createEmptyBorder( 10,0, 5, 0)); //Creamos un borde para darle espacio arriba y abajo (por estetica)
      
        //Añadimos el TituloDelCombobox a este PANEL (ARTICULO-IZQUIERDA)
          panelGrupoDerecha.add(tituloArticulo_DUO);
              
        //Procedemos a crear nuestro COMBOBOX de GRUPOS
         comboArticulos_DUO = new JComboBox();
        //Le asignamos tamaño 
        comboArticulos_DUO.setPreferredSize(new Dimension(350, 30));
        //Le asignamos tamaño Maximo (para que pueda redimensionarse un poco)
        comboArticulos_DUO.setMaximumSize(new Dimension(350, 30));
        comboArticulos_DUO.setMinimumSize(new Dimension(350, 30));
       // comboArticulos_DUO.setBorder(BorderFactory.createEmptyBorder(7,0, 7, 0)); //Creamos un borde para darle espacio arriba y abajo (por estetica)
        
       
        //Le indicamos que se alineará a la izquierda
        comboArticulos_DUO.setAlignmentX(LEFT_ALIGNMENT);
        //Añadimos el Combobox a este PANEL (GRUPO-IZQUIERDA)
        panelGrupoDerecha.add(comboArticulos_DUO);
         //Agregaremos un AreaRigida de unos cuantros Pixeles, para darle un poco de espacio entre el siguiente elemento que se agregue
        panelGrupoDerecha.add(Box.createRigidArea(new Dimension(0,10)));
            
          panelGrupos.add(panelGrupoDerecha);
          
        panelGrupos.add(Box.createRigidArea(new Dimension(40,panelGrupos.getHeight())));
        
          JSeparator separator7 = new JSeparator(1);//Al llamar al separador utilizando 1 en el constructor, le indicamos que el separador estará en VERTICAL
          separator7.setSize(2,0); //El separador siempre hay que iniciarlo con este tamaño, y luego asignarle un tamaño maximo 
          separator7.setMaximumSize(new Dimension(2,600));
          //Agregamos el separador al Panel GRUPOS
          panelGrupos.add(separator7);      
          
           //Le ponemos "PEGAMENTO" XD, para que se haga un area invisible que permitirá redimensionar la ventana sin que este componente se mueva de su sitio (ya que el area pegamento será la que se estire o encoja según precise)
            panelGrupos.add(Box.createHorizontalGlue()); 
        
        //AÑADIENDO PANEL GRUPOS A EL PANEL "Formularios"
        panelFormularios.add(panelGrupos);
        
        
        
        
         //Añadimos separador HORIZONTAL
          JSeparator separator = new JSeparator();
        panelFormularios.add( separator);
       
  ///////////////////FIN DE PANEL grupos//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        
  
    ///////////////////COMIENZA EL PANEL ARTICULOS,QUE CONTIENE LOS PANELES NECESARIOS PARA trabajar con los articulos///////////////////////////////   
    
    
            panelArticulos= new JPanel();
        panelArticulos.setLayout(new BoxLayout(panelArticulos, BoxLayout.X_AXIS));//Indicamos que los agregue hacia LA DERECHA
        //Creamos un area vacía de 20px al principio de este contenedor (en la izquierda), para dejar un espacio (por estetica)
        panelArticulos.add(Box.createHorizontalGlue()); 
        
          JSeparator separator3 = new JSeparator(1);//Al llamar al separador utilizando 1 en el constructor, le indicamos que el separador estará en VERTICAL
          separator3.setSize(2,0); //El separador siempre hay que iniciarlo con este tamaño, y luego asignarle un tamaño maximo 
          separator3.setMaximumSize(new Dimension(2,600));
          panelArticulos.add(separator3);  
          
          panelArticulos.add(Box.createRigidArea(new Dimension(40, panelArticulos.getHeight())));
    //----------------------------------------------------------------------------------------------------------------------------------------    
        //Creamos el PANEL ARTICULO-IZQUIERDA, el cual contendrŕa un combobox para escoger grupo y una etiqueta para indicar para que sirve
       panelArticuloIzquierda = new JPanel();
        panelArticuloIzquierda.setLayout(new BoxLayout(panelArticuloIzquierda, BoxLayout.Y_AXIS));//Indicamos qu elos agregue hacia ABAJO
    
     
        panelArticuloIzquierda.add(Box.createRigidArea(new Dimension(panelArticuloIzquierda.getWidth(),5)));
        //Inicialozamos el RADIOBUTTON y le indicamos desde aquí que será el botón por defecto
        //Recordamos que el boton pertenece a un grupo junto con los demas radioBotones
        //Para que solo se pueda elegir una opción a la vez
          RB_Selec_Grupo_SOLO= new JRadioButton("FILTRAR SOLO POR \"GRUPO\"");
   //       RB_Selec_Grupo_SOLO.setSelected(true);
          RB_Selec_Grupo_SOLO.setAlignmentX(LEFT_ALIGNMENT); 
                RB_Selec_Grupo_SOLO.setBackground(new Color(195,201,223));

        
          RB_Selec_Grupo_SOLO.setOpaque(true);
          
         //Agregamos el Radioboton al PANEL GRUPO IZQUIERDA
          panelArticuloIzquierda.add(RB_Selec_Grupo_SOLO);
    
       panelArticuloIzquierda.add(Box.createRigidArea(new Dimension(panelArticuloIzquierda.getWidth(),10)));
 
      
        //Creamos la ETIQUETA que INDICARÁ que hace nuestro combobox
        tituloGrupo_SOLO = new JLabel("Seleccione GRUPO "+((char)9660));
        //Alineamos la eqtiqueta a la IZQUIERDA
        tituloGrupo_SOLO.setAlignmentX(LEFT_ALIGNMENT); 
        //Establecemos un borde espaciado VACIO  arriba,izquierda,abajo,derecha 
         tituloGrupo_SOLO.setBorder(BorderFactory.createEmptyBorder( 10,0, 5, 0)); //Creamos un borde para darle espacio arriba y abajo (por estetica)
        
        //Añadimos el TituloDelCombobox a este PANEL (GRUPO-IZQUIERDA)
         panelArticuloIzquierda.add(tituloGrupo_SOLO);
              
        //Procedemos a crear nuestro COMBOBOX de GRUPOS
       comboGrupos_SOLO = new JComboBox();
       
        //Le asignamos tamaño con SETPREFERED SIZE
        comboGrupos_SOLO.setPreferredSize(new Dimension(350, 30));
       
        comboGrupos_SOLO.setMaximumSize(new Dimension(350, 30));
        comboGrupos_SOLO.setMinimumSize(new Dimension(350, 30));
       
        
       
        //Le indicamos que se alineará a la izquierda
        comboGrupos_SOLO.setAlignmentX(LEFT_ALIGNMENT);
        //Añadimos el Combobox a este PANEL (GRUPO-IZQUIERDA)
         panelArticuloIzquierda.add(comboGrupos_SOLO);
         //Agregaremos un AreaRigida de unos cuantros Pixeles, para darle un poco de espacio entre el siguiente elemento que se agregue
         panelArticuloIzquierda.add(Box.createRigidArea(new Dimension(0,10)));
   
        //Ya completado el GRUPO_IZQUIERDA, procedemos a agregarlo al PANEL-GRUPOS
        panelArticulos.add(panelArticuloIzquierda);
        
        //Creamos un Area Rígida, en el panel GRUPOS, para darle separación a los dos Paneles superiores (de grupo)
        panelArticulos.add(Box.createRigidArea(new Dimension(31,panelArticulos.getHeight())));
        
         JSeparator separator6 = new JSeparator(1);//Al llamar al separador utilizando 1 en el constructor, le indicamos que el separador estará en VERTICAL
          separator6.setSize(2,0); //El separador siempre hay que iniciarlo con este tamaño, y luego asignarle un tamaño maximo 
          separator6.setMaximumSize(new Dimension(2,600));
          //Agregamos el separador al Panel GRUPOS
          panelArticulos.add(separator6);      
        
        
        panelArticulos.add(Box.createRigidArea(new Dimension(32,panelArticulos.getHeight())));
         
     //----------------------------------------------------------------------------------------------------------------------------------------    
        //Creamos el PANEL ARTICULOS-DERECHA, el cual contendrŕa un TextField para insertar un grupo, una etiqueta para indicar para que sirve y un BOTON para realizar el ALTA del nuevo GRUPO        
            
        panelArticuloDerecha = new JPanel();
        panelArticuloDerecha.setLayout(new BoxLayout(panelArticuloDerecha, BoxLayout.Y_AXIS));//Indicamos qu elos agregue hacia ABAJO
       
        panelArticuloDerecha.add(Box.createRigidArea(new Dimension(panelArticuloDerecha.getWidth(),5)));
        //Inicialozamos el RADIOBUTTON y le indicamos desde aquí que será el botón por defecto
        //Recordamos que el boton pertenece a un grupo junto con los demas radioBotones
        //Para que solo se pueda elegir una opción a la vez
          RB_Selec_Articulo_SOLO= new JRadioButton("FILTRAR SOLO POR \"ARTÍCULO\"");
   //       RB_Selec_Grupo_SOLO.setSelected(true);
          RB_Selec_Articulo_SOLO.setAlignmentX(LEFT_ALIGNMENT); 
                RB_Selec_Articulo_SOLO.setBackground(new Color(195,201,223));

        
          RB_Selec_Articulo_SOLO.setOpaque(true);
          
         //Agregamos el Radioboton al PANEL GRUPO IZQUIERDA
          panelArticuloDerecha.add(RB_Selec_Articulo_SOLO);
    
       panelArticuloDerecha.add(Box.createRigidArea(new Dimension(panelArticuloDerecha.getWidth(),10)));
 
      
        //Creamos la ETIQUETA que INDICARÁ que hace nuestro combobox
        tituloArticulo_SOLO = new JLabel("Seleccione ARTÍCULO "+((char)9660));
        //Alineamos la eqtiqueta a la IZQUIERDA
        tituloArticulo_SOLO.setAlignmentX(LEFT_ALIGNMENT); 
        //Establecemos un borde espaciado VACIO  arriba,izquierda,abajo,derecha 
         tituloArticulo_SOLO.setBorder(BorderFactory.createEmptyBorder( 10,0, 5, 0)); //Creamos un borde para darle espacio arriba y abajo (por estetica)
        
        //Añadimos el TituloDelCombobox a este PANEL (GRUPO-IZQUIERDA)
         panelArticuloDerecha.add(tituloArticulo_SOLO);
              
        //Procedemos a crear nuestro COMBOBOX de GRUPOS
       comboArticulos_SOLO = new JComboBox();
       
        //Le asignamos tamaño con SETPREFERED SIZE
        comboArticulos_SOLO.setPreferredSize(new Dimension(350, 30));
       
        comboArticulos_SOLO.setMaximumSize(new Dimension(350, 30));
        comboArticulos_SOLO.setMinimumSize(new Dimension(350, 30));
       
        
       
        //Le indicamos que se alineará a la izquierda
        comboArticulos_SOLO.setAlignmentX(LEFT_ALIGNMENT);
        //Añadimos el Combobox a este PANEL (GRUPO-IZQUIERDA)
         panelArticuloDerecha.add(comboArticulos_SOLO);
         //Agregaremos un AreaRigida de unos cuantros Pixeles, para darle un poco de espacio entre el siguiente elemento que se agregue
         panelArticuloDerecha.add(Box.createRigidArea(new Dimension(0,10)));
   
        //Ya completado el GRUPO_IZQUIERDA, procedemos a agregarlo al PANEL-GRUPOS
        panelArticulos.add(panelArticuloDerecha);
            
        panelArticulos.add(Box.createRigidArea(new Dimension(35,panelArticulos.getHeight())));
        
                 JSeparator separator1 = new JSeparator(1);//Al llamar al separador utilizando 1 en el constructor, le indicamos que el separador estará en VERTICAL
          separator1.setSize(2,0); //El separador siempre hay que iniciarlo con este tamaño, y luego asignarle un tamaño maximo 
          separator1.setMaximumSize(new Dimension(2,600));
          //Agregamos el separador al Panel GRUPOS
          panelArticulos.add(separator1);      
        
        
        
        
           panelArticulos.add(Box.createHorizontalGlue()); 
        
        //AÑADIENDO PANEL GRUPOS A EL PANEL "Formularios"
        panelFormularios.add(panelArticulos);
         
          JSeparator separator4 = new JSeparator();
        panelFormularios.add( separator4);
      
    
  ///////////////////FIN DE PANEL ARTICULOS//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
  
  ///////////////////AGREGANDO RADIOBOTONES A UN MISMO GRUPO//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  grupoRB.add(RB_Selec_DUO);
grupoRB.add(RB_Selec_Grupo_SOLO);
grupoRB.add(RB_Selec_Articulo_SOLO);
  ///////////////////FIN AGREGANDO RADIOBOTONES A UN MISMO GRUPO//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  
  
  
   ///////////////////SETEANDO Y AGREGANDO PANEL DESCRIPCION //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
        
         panelDescripcion = new JPanel();
        panelDescripcion.setLayout(new BoxLayout(panelDescripcion, BoxLayout.Y_AXIS));
        panelDescripcion.add(Box.createHorizontalGlue()); 
          
         panelDescripcionSuperior = new JPanel();
         panelDescripcionSuperior.setLayout(new BoxLayout( panelDescripcionSuperior , BoxLayout.X_AXIS));//Indicamos qu elos agregue hacia a la DERECHA
         //Le agregamos PEGAMENTO, para que se estire y quede centrado cuando redimensione
      
         panelDescripcionSuperior.add(Box.createRigidArea(new Dimension(panelDescripcionSuperior.getWidth(),40)));
        panelDescripcionSuperior.add(Box.createRigidArea(new Dimension(10,panelDescripcionSuperior.getHeight())));
        //Inicializando CAMPOS y AREA de Texto (aqui indicaremos lo que queremos que tenga escrito dentro el campo, EN MI CASO ESTARAN VACIOS)
        
        
        
                
        tituloDescripcion = new JLabel("Resultado Consulta:");
        tituloDescripcion.setBorder(BorderFactory.createEmptyBorder( 30,8, 0, 0));
        panelDescripcionSuperior.add(tituloDescripcion);
        
        //Creamos area rigida para que se mantenga FIJO en la misma posicion aunque redimensionemos
        panelDescripcionSuperior.add(Box.createRigidArea(new Dimension(75,panelDescripcionSuperior.getHeight())));
        //Le agregamos PEGAMENTO, para que se estire y quede centrado cuando redimensione MENOS EL "INGRESE DESCRIPCION"
        panelDescripcionSuperior.add(Box.createHorizontalGlue()); 
        
        BotonConsultarDescripcion = new JButton("REALIZAR CONSULTA");
        BotonConsultarDescripcion.setBackground(new Color(184,255,181));
        BotonConsultarDescripcion.setBorder(BorderFactory.createEmptyBorder( 15, 8, 15, 8));
        panelDescripcionSuperior.add(BotonConsultarDescripcion);
        panelDescripcionSuperior.add(Box.createRigidArea(new Dimension(35,panelDescripcionSuperior.getHeight())));
        
         JSeparator separator5 = new JSeparator(1);
           separator5.setSize(2,0); //El separador siempre hay que iniciarlo con este tamaño, y luego asignarle un tamaño maximo 
          separator5.setMaximumSize(new Dimension(2,600));
        panelDescripcionSuperior.add( separator5);
        panelDescripcionSuperior.add(Box.createRigidArea(new Dimension(24,panelDescripcionSuperior.getHeight())));
    
        //LE AGREGAMOS EL BOTÓN "ExportarConsultaaPestaña" por si el usuario quiere enviar la consulta a la pestaña que esté seleccionada en el momento
        panelDescripcionSuperior.add(ControladorVistaPrincipal.ExportarConsultaAPestania);
         ControladorVistaPrincipal.ExportarConsultaAPestania.setBackground(new Color(131,196,255));
        ControladorVistaPrincipal.ExportarConsultaAPestania.setBorder(BorderFactory.createEmptyBorder( 15, 8, 15, 8));
         panelDescripcionSuperior.add(Box.createRigidArea(new Dimension(40,panelDescripcionSuperior.getHeight())));
         
         
        BotonlimpiarPantalla = new JButton("Limpiar Pantalla");
        BotonlimpiarPantalla.setBackground(new Color(255,253,160));
        BotonlimpiarPantalla.setBorder(BorderFactory.createEmptyBorder( 15, 8, 15, 8));
        panelDescripcionSuperior.add(BotonlimpiarPantalla);
        panelDescripcionSuperior.add(Box.createRigidArea(new Dimension(16,panelDescripcionSuperior.getHeight())));
        
        Botonsalir = new JButton("SALIR");
        Botonsalir.setBackground(new Color(255,156,154));
        Botonsalir.setBorder(BorderFactory.createEmptyBorder( 15, 8, 15, 8));
        panelDescripcionSuperior.add(Botonsalir);
        //Agregamos un area fija, para que quede centrado en el sitio que debe ir y le agregamos Pegamento para la redimension
        panelDescripcionSuperior.add(Box.createRigidArea(new Dimension(10,panelDescripcionSuperior.getHeight())));
        panelDescripcionSuperior.add(Box.createHorizontalGlue());
        
        panelDescripcion.add( panelDescripcionSuperior);
  //      tituloTA.setBorder(BorderFactory.createEmptyBorder( 10,10, 10, 10));
  
  //LE PASAMOS EL AREA DE TEXTO ESTATICA, PARA poder exportar la consulta a la pestaña si queremos
        textArea = ControladorVistaPrincipal.texAreaExportar;//INicializando al TextArea 
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
               this.setBounds(100,100,600, 600);
               this.setMinimumSize(new Dimension(854, 600));
        // this.setLocationRelativeTo(null);        


 
    
                
       
    }//Fin de iniciAR

    
    

}