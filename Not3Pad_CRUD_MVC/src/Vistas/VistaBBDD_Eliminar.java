/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Component.LEFT_ALIGNMENT;
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
public class VistaBBDD_Eliminar extends JFrame {

  public JPanel panelFormularios; //Este es el panel que contiene los DISTINTOS PANELES que albergan los formularios y combobox

    //Declarando los RADIOCHECK BUTTON

    public JRadioButton RB_Selec_Descripcion; //RadioBut. para seleccionar DESCRIPCION
    public JRadioButton RB_Selec_Grupo; //RadioBut. para seleccionar GRUPO
    public JRadioButton RB_Selec_Articulo; //RadioBut. para seleccionar ARTICULO

    public ButtonGroup grupoRB = new ButtonGroup();

  
  
    public JPanel panelRadioButtons;
    public JPanel panelGrupos;
    public JPanel panelGrupoIzquierda; 
    public JPanel panelGrupoDerecha;

    public JPanel panelArticulosDescripcion;
    public JPanel panelElegirDescripcion;
    public JPanel panelArticuloDerecha;

    public JPanel panelModificarDescripcion;
    public JPanel panelModificarDescripcionSuperior;
    public JTextArea textArea;
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
    public JLabel tituloGrupo_DUO, tituloArticulo_DUO, tituloElegirDescripcion, tituloDescripcion, tituloOpcion;
   
    //Declarando combobox
    public JComboBox comboGrupos_DUO, comboArticulos_DUO, comboElegirDescripcion;
    //Declarando Botones
    public JButton BotonEliminarSeleccion,BotonObtenerDescripcion, BotonlimpiarPantalla, Botonsalir;

//Creando constructor
    public VistaBBDD_Eliminar() {

        this.popMenu = new JPopupMenu();
        this.pop_Rehacer = new JMenuItem("Rehacer");
        this.pop_Deshacer = new JMenuItem("Deshacer");
        this.pop_Copiar = new JMenuItem("Copiar");
        this.pop_Cortar = new JMenuItem("Cortar");
        this.pop_Pegar = new JMenuItem("Pegar");
        this.pop_Imprimir = new JMenuItem("Imprimir");

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
        setTitle("BBDD-ELIMINAR");

        //Panel que contendra los paneles de GRUPOS,ARTICULOS
        panelFormularios = new JPanel();
        panelFormularios.setLayout(new BoxLayout(panelFormularios, BoxLayout.Y_AXIS)); //Indicamos qu elos agregue hacia ABAJO

        
        
        
        
        
        
        
               ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
        //Creamos PANEL en el que se alojaran los PANELES QUE CONFORMAN las OPCIONES y textos de GRUPOS
        panelRadioButtons = new JPanel();
        panelRadioButtons.setLayout(new BoxLayout(panelRadioButtons, BoxLayout.X_AXIS));//Indicamos que los agregue hacia LA DERECHA
        //Creamos un area vacía de 20px al principio de este contenedor (en la izquierda), para dejar un espacio (por estetica)

        panelRadioButtons.add(Box.createHorizontalGlue());

        JSeparator separator2 = new JSeparator(1);//Al llamar al separador utilizando 1 en el constructor, le indicamos que el separador estará en VERTICAL
        separator2.setSize(2, 0); //El separador siempre hay que iniciarlo con este tamaño, y luego asignarle un tamaño maximo 
        separator2.setMaximumSize(new Dimension(2, 600));
        //Agregamos el separador al Panel GRUPOS
        panelRadioButtons.add(separator2);

       panelRadioButtons.add(Box.createRigidArea(new Dimension(40, panelRadioButtons.getHeight())));

        //Creamos la ETIQUETA que INDICARÁ que hace nuestro combobox
        tituloOpcion = new JLabel("SELECCIONE OPCIÓN:");
        //Alineamos la eqtiqueta a la IZQUIERDA
        tituloOpcion.setAlignmentX(LEFT_ALIGNMENT);
        //Establecemos un borde espaciado VACIO  arriba,izquierda,abajo,derecha 
        tituloOpcion.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0)); //Creamos un borde para darle espacio arriba y abajo (por estetica)
        //Añadimos el TituloDelCombobox a este PANEL (GRUPO-IZQUIERDA)
        panelRadioButtons.add(tituloOpcion);
       panelRadioButtons.add(Box.createRigidArea(new Dimension(40, panelRadioButtons.getHeight())));
       
        //Al pertenecer a un GRUPO, solo se puede elegir UNO
        RB_Selec_Grupo = new JRadioButton("ELIMINAR \"GRUPO\"");
        //RB_Selec_Grupo.setSelected(true);
        RB_Selec_Grupo.setAlignmentX(LEFT_ALIGNMENT);
        RB_Selec_Grupo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0)); 
        //Agregamos el Radioboton al PANEL GRUPO IZQUIERDA
        panelRadioButtons.add(RB_Selec_Grupo);
        panelRadioButtons.add(Box.createRigidArea(new Dimension(40, panelRadioButtons.getHeight())));
        
        RB_Selec_Articulo = new JRadioButton("ELIMINAR \"ARTÍCULO\"");
        //RB_Selec_Articulo.setSelected(true);
        RB_Selec_Articulo.setAlignmentX(LEFT_ALIGNMENT);
        RB_Selec_Articulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0)); 
        //Agregamos el Radioboton al PANEL GRUPO IZQUIERDA
        panelRadioButtons.add(RB_Selec_Articulo);
        panelRadioButtons.add(Box.createRigidArea(new Dimension(40, panelRadioButtons.getHeight())));
      
        RB_Selec_Descripcion = new JRadioButton("ELIMINAR \"DESCRIPCIÓN\"");
        //RB_Selec_Descripcion.setSelected(true);
        RB_Selec_Descripcion.setAlignmentX(LEFT_ALIGNMENT);
        RB_Selec_Descripcion.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0)); 
        //Agregamos el Radioboton al PANEL GRUPO IZQUIERDA
        panelRadioButtons.add(RB_Selec_Descripcion);
        
        
        
          ///////////////////FIN DE PANEL RADIO BUTONS//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////AGREGANDO RADIOBOTONES A UN MISMO GRUPO//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        grupoRB.add(RB_Selec_Grupo);
        grupoRB.add(RB_Selec_Articulo);
        grupoRB.add(RB_Selec_Descripcion);
        ///////////////////FIN AGREGANDO RADIOBOTONES A UN MISMO GRUPO//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        
       

        panelRadioButtons.add(Box.createRigidArea(new Dimension(40, panelRadioButtons.getHeight())));

        JSeparator separator7 = new JSeparator(1);//Al llamar al separador utilizando 1 en el constructor, le indicamos que el separador estará en VERTICAL
        separator7.setSize(2, 0); //El separador siempre hay que iniciarlo con este tamaño, y luego asignarle un tamaño maximo 
        separator7.setMaximumSize(new Dimension(2, 600));
        //Agregamos el separador al Panel GRUPOS
       panelRadioButtons.add(separator7);

        //Le ponemos "PEGAMENTO" XD, para que se haga un area invisible que permitirá redimensionar la ventana sin que este componente se mueva de su sitio (ya que el area pegamento será la que se estire o encoja según precise)
        panelRadioButtons.add(Box.createHorizontalGlue());

   
        
        
        //AÑADIENDO PANEL GRUPOS A EL PANEL "Formularios"
        panelFormularios.add(panelRadioButtons);
        
        JSeparator separator13 = new JSeparator();
        panelFormularios.add(separator13);
         
         
        
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
        //Creamos PANEL en el que se alojaran los PANELES QUE CONFORMAN las OPCIONES y textos de GRUPOS
        panelGrupos = new JPanel();
        panelGrupos.setLayout(new BoxLayout(panelGrupos, BoxLayout.X_AXIS));//Indicamos que los agregue hacia LA DERECHA
        //Creamos un area vacía de 20px al principio de este contenedor (en la izquierda), para dejar un espacio (por estetica)

        panelGrupos.add(Box.createHorizontalGlue());

        JSeparator separator11 = new JSeparator(1);//Al llamar al separador utilizando 1 en el constructor, le indicamos que el separador estará en VERTICAL
        separator11.setSize(2, 0); //El separador siempre hay que iniciarlo con este tamaño, y luego asignarle un tamaño maximo 
        separator11.setMaximumSize(new Dimension(2, 600));
        //Agregamos el separador al Panel GRUPOS
        panelGrupos.add(separator11);

        panelGrupos.add(Box.createRigidArea(new Dimension(40, panelGrupos.getHeight())));

        //----------------------------------------------------------------------------------------------------------------------------------------    
        //Creamos el PANEL GRUPO-IZQUIERDA, el cual contendrŕa un combobox para escoger grupo y una etiqueta para indicar para que sirve
        panelGrupoIzquierda = new JPanel();
        panelGrupoIzquierda.setLayout(new BoxLayout(panelGrupoIzquierda, BoxLayout.Y_AXIS));//Indicamos qu elos agregue hacia ABAJO

        panelGrupoIzquierda.add(Box.createRigidArea(new Dimension(panelGrupoIzquierda.getWidth(), 10)));

        //Creamos la ETIQUETA que INDICARÁ que hace nuestro combobox
        tituloGrupo_DUO = new JLabel("Seleccione GRUPO " + ((char) 9660));
        //Alineamos la eqtiqueta a la IZQUIERDA
        tituloGrupo_DUO.setAlignmentX(LEFT_ALIGNMENT);
        //Establecemos un borde espaciado VACIO  arriba,izquierda,abajo,derecha 
        tituloGrupo_DUO.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0)); //Creamos un borde para darle espacio arriba y abajo (por estetica)

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
        panelGrupoIzquierda.add(Box.createRigidArea(new Dimension(0, 10)));

        //Ya completado el GRUPO_IZQUIERDA, procedemos a agregarlo al PANEL-GRUPOS
        panelGrupos.add(panelGrupoIzquierda);

        //Creamos un Area Rígida, en el panel GRUPOS, para darle separación a los dos Paneles superiores (de grupo)
        panelGrupos.add(Box.createRigidArea(new Dimension(60, panelGrupos.getHeight())));

        //----------------------------------------------------------------------------------------------------------------------------------------    
        //Creamos el PANEL GRUPO-DERECHA, el cual contendrŕa un TextField para insertar un grupo, una etiqueta para indicar para que sirve y un BOTON para realizar el ALTA del nuevo GRUPO        
        panelGrupoDerecha = new JPanel();
        panelGrupoDerecha.setLayout(new BoxLayout(panelGrupoDerecha, BoxLayout.Y_AXIS));//Indicamos qu elos agregue hacia ABAJO
        panelGrupoDerecha.add(Box.createRigidArea(new Dimension(panelGrupoIzquierda.getWidth(), 10)));

      
        //Creamos la ETIQUETA que INDICARÁ que hace nuestro combobox
        tituloArticulo_DUO = new JLabel("Seleccione ARTÍCULO " + ((char) 9660));
        //Alineamos la eqtiqueta a la IZQUIERDA
        tituloArticulo_DUO.setAlignmentX(LEFT_ALIGNMENT);
        //Establecemos un borde espaciado VACIO  arriba,izquierda,abajo,derecha 
        tituloArticulo_DUO.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0)); //Creamos un borde para darle espacio arriba y abajo (por estetica)

        //Añadimos el TituloDelCombobox a este PANEL (ARTICULO-IZQUIERDA)
        panelGrupoDerecha.add(tituloArticulo_DUO);

        //Procedemos a crear nuestro COMBOBOX de GRUPOS
        comboArticulos_DUO = new JComboBox();
        //Le asignamos tamaño 
        comboArticulos_DUO.setPreferredSize(new Dimension(350, 30));
        //Le asignamos tamaño Maximo (para que pueda redimensionarse un poco)
        comboArticulos_DUO.setMaximumSize(new Dimension(350, 30));
        comboArticulos_DUO.setMinimumSize(new Dimension(350, 30));

        //Le indicamos que se alineará a la izquierda
        comboArticulos_DUO.setAlignmentX(LEFT_ALIGNMENT);
        //Añadimos el Combobox a este PANEL (GRUPO-IZQUIERDA)
        panelGrupoDerecha.add(comboArticulos_DUO);
        //Agregaremos un AreaRigida de unos cuantros Pixeles, para darle un poco de espacio entre el siguiente elemento que se agregue
        panelGrupoDerecha.add(Box.createRigidArea(new Dimension(0, 10)));

        panelGrupos.add(panelGrupoDerecha);

        panelGrupos.add(Box.createRigidArea(new Dimension(40, panelGrupos.getHeight())));

        JSeparator separator12 = new JSeparator(1);//Al llamar al separador utilizando 1 en el constructor, le indicamos que el separador estará en VERTICAL
        separator12.setSize(2, 0); //El separador siempre hay que iniciarlo con este tamaño, y luego asignarle un tamaño maximo 
        separator12.setMaximumSize(new Dimension(2, 600));
        //Agregamos el separador al Panel GRUPOS
        panelGrupos.add(separator12);

        //Le ponemos "PEGAMENTO" XD, para que se haga un area invisible que permitirá redimensionar la ventana sin que este componente se mueva de su sitio (ya que el area pegamento será la que se estire o encoja según precise)
        panelGrupos.add(Box.createHorizontalGlue());

        //AÑADIENDO PANEL GRUPOS A EL PANEL "Formularios"
        panelFormularios.add(panelGrupos);

        //Añadimos separador HORIZONTAL
        JSeparator separator = new JSeparator();
        panelFormularios.add(separator);

        ///////////////////FIN DE PANEL grupos//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////COMIENZA EL PANEL ARTICULOS,QUE CONTIENE LOS PANELES NECESARIOS PARA trabajar con los articulos///////////////////////////////   
        panelArticulosDescripcion = new JPanel();
        panelArticulosDescripcion.setLayout(new BoxLayout(panelArticulosDescripcion, BoxLayout.X_AXIS));//Indicamos que los agregue hacia LA DERECHA
        //Creamos un area vacía de 20px al principio de este contenedor (en la izquierda), para dejar un espacio (por estetica)
        panelArticulosDescripcion.add(Box.createHorizontalGlue());

        JSeparator separator3 = new JSeparator(1);//Al llamar al separador utilizando 1 en el constructor, le indicamos que el separador estará en VERTICAL
        separator3.setSize(2, 0); //El separador siempre hay que iniciarlo con este tamaño, y luego asignarle un tamaño maximo 
        separator3.setMaximumSize(new Dimension(2, 600));
        panelArticulosDescripcion.add(separator3);

        panelArticulosDescripcion.add(Box.createRigidArea(new Dimension(40, panelArticulosDescripcion.getHeight())));
        //----------------------------------------------------------------------------------------------------------------------------------------    
        //Creamos el PANEL ARTICULO-IZQUIERDA, el cual contendrŕa un combobox para escoger grupo y una etiqueta para indicar para que sirve
        panelElegirDescripcion = new JPanel();
        panelElegirDescripcion.setLayout(new BoxLayout(panelElegirDescripcion, BoxLayout.Y_AXIS));//Indicamos qu elos agregue hacia ABAJO


        panelElegirDescripcion.add(Box.createRigidArea(new Dimension(panelElegirDescripcion.getWidth(), 10)));

        //Creamos la ETIQUETA que INDICARÁ que hace nuestro combobox
        tituloElegirDescripcion = new JLabel("Seleccione DESCRIPCIÓN " + ((char) 9660));
        //Alineamos la eqtiqueta a la IZQUIERDA
        tituloElegirDescripcion.setAlignmentX(LEFT_ALIGNMENT);
        //Establecemos un borde espaciado VACIO  arriba,izquierda,abajo,derecha 
        tituloElegirDescripcion.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0)); //Creamos un borde para darle espacio arriba y abajo (por estetica)

        //Añadimos el TituloDelCombobox a este PANEL (GRUPO-IZQUIERDA)
        panelElegirDescripcion.add(tituloElegirDescripcion);

        //Procedemos a crear nuestro COMBOBOX de GRUPOS
        comboElegirDescripcion = new JComboBox();

        //Le asignamos tamaño con SETPREFERED SIZE
        comboElegirDescripcion.setPreferredSize(new Dimension(764, 30));

        comboElegirDescripcion.setMaximumSize(new Dimension(764, 30));
        comboElegirDescripcion.setMinimumSize(new Dimension(764, 30));

        //Le indicamos que se alineará a la izquierda
        comboElegirDescripcion.setAlignmentX(LEFT_ALIGNMENT);
        //Añadimos el Combobox a este PANEL (GRUPO-IZQUIERDA)
        panelElegirDescripcion.add(comboElegirDescripcion);
        //Agregaremos un AreaRigida de unos cuantros Pixeles, para darle un poco de espacio entre el siguiente elemento que se agregue
        panelElegirDescripcion.add(Box.createRigidArea(new Dimension(0, 10)));

        //Ya completado el panel elegir descripcion, procedemos a agregarlo al PANEL-Descripcion
        panelArticulosDescripcion.add(panelElegirDescripcion);

        

        panelArticulosDescripcion.add(Box.createRigidArea(new Dimension(36, panelArticulosDescripcion.getHeight())));

        JSeparator separator1 = new JSeparator(1);//Al llamar al separador utilizando 1 en el constructor, le indicamos que el separador estará en VERTICAL
        separator1.setSize(2, 0); //El separador siempre hay que iniciarlo con este tamaño, y luego asignarle un tamaño maximo 
        separator1.setMaximumSize(new Dimension(2, 600));
        //Agregamos el separador al Panel GRUPOS
        panelArticulosDescripcion.add(separator1);

        panelArticulosDescripcion.add(Box.createHorizontalGlue());

        //AÑADIENDO PANEL GRUPOS A EL PANEL "Formularios"
        panelFormularios.add(panelArticulosDescripcion);

        JSeparator separator4 = new JSeparator();
        panelFormularios.add(separator4);


        ///////////////////FIN AGREGANDO RADIOBOTONES A UN MISMO GRUPO//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ///////////////////SETEANDO Y AGREGANDO PANEL DESCRIPCION //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        panelModificarDescripcion = new JPanel();
        panelModificarDescripcion.setLayout(new BoxLayout(panelModificarDescripcion, BoxLayout.Y_AXIS));
        panelModificarDescripcion.add(Box.createHorizontalGlue());

        panelModificarDescripcionSuperior = new JPanel();
        panelModificarDescripcionSuperior.setLayout(new BoxLayout(panelModificarDescripcionSuperior, BoxLayout.X_AXIS));//Indicamos qu elos agregue hacia a la DERECHA
        //Le agregamos PEGAMENTO, para que se estire y quede centrado cuando redimensione

        panelModificarDescripcionSuperior.add(Box.createRigidArea(new Dimension(panelModificarDescripcionSuperior.getWidth(), 40)));
        panelModificarDescripcionSuperior.add(Box.createRigidArea(new Dimension(10, panelModificarDescripcionSuperior.getHeight())));
        //Inicializando CAMPOS y AREA de Texto (aqui indicaremos lo que queremos que tenga escrito dentro el campo, EN MI CASO ESTARAN VACIOS)

        tituloDescripcion = new JLabel("Modifique Consulta:");
        tituloDescripcion.setBorder(BorderFactory.createEmptyBorder(30, 8, 0, 0));
        panelModificarDescripcionSuperior.add(tituloDescripcion);

        //Creamos area rigida para que se mantenga FIJO en la misma posicion aunque redimensionemos
        panelModificarDescripcionSuperior.add(Box.createRigidArea(new Dimension(75, panelModificarDescripcionSuperior.getHeight())));
        //Le agregamos PEGAMENTO, para que se estire y quede centrado cuando redimensione MENOS EL "INGRESE DESCRIPCION"
        panelModificarDescripcionSuperior.add(Box.createHorizontalGlue());

        BotonEliminarSeleccion = new JButton("ELIMINAR REGISTRO");
        BotonEliminarSeleccion.setBackground(new Color(184, 255, 181));
        BotonEliminarSeleccion.setBorder(BorderFactory.createEmptyBorder(15, 8, 15, 8));
        panelModificarDescripcionSuperior.add(BotonEliminarSeleccion);
        panelModificarDescripcionSuperior.add(Box.createRigidArea(new Dimension(35, panelModificarDescripcionSuperior.getHeight())));

        JSeparator separator5 = new JSeparator(1);
        separator5.setSize(2, 0); //El separador siempre hay que iniciarlo con este tamaño, y luego asignarle un tamaño maximo 
        separator5.setMaximumSize(new Dimension(2, 600));
        panelModificarDescripcionSuperior.add(separator5);
        panelModificarDescripcionSuperior.add(Box.createRigidArea(new Dimension(24, panelModificarDescripcionSuperior.getHeight())));

        //LE AGREGAMOS EL BOTÓN "ObtenerDescripcion" por si el usuario quiere enviar la consulta a la pestaña que esté seleccionada en el momento
        BotonObtenerDescripcion = new JButton("OBTENER Descripción");
        BotonObtenerDescripcion.setBackground(new Color(131, 196, 255));
        BotonObtenerDescripcion.setBorder(BorderFactory.createEmptyBorder(15, 8, 15, 8));
        panelModificarDescripcionSuperior.add(BotonObtenerDescripcion);
        panelModificarDescripcionSuperior.add(Box.createRigidArea(new Dimension(16, panelModificarDescripcionSuperior.getHeight())));
        

        BotonlimpiarPantalla = new JButton("Limpiar Pantalla");
        BotonlimpiarPantalla.setBackground(new Color(255, 253, 160));
        BotonlimpiarPantalla.setBorder(BorderFactory.createEmptyBorder(15, 8, 15, 8));
        panelModificarDescripcionSuperior.add(BotonlimpiarPantalla);
        panelModificarDescripcionSuperior.add(Box.createRigidArea(new Dimension(16, panelModificarDescripcionSuperior.getHeight())));

        Botonsalir = new JButton("SALIR");
        Botonsalir.setBackground(new Color(255, 156, 154));
        Botonsalir.setBorder(BorderFactory.createEmptyBorder(15, 8, 15, 8));
        panelModificarDescripcionSuperior.add(Botonsalir);
        //Agregamos un area fija, para que quede centrado en el sitio que debe ir y le agregamos Pegamento para la redimension
        panelModificarDescripcionSuperior.add(Box.createRigidArea(new Dimension(10, panelModificarDescripcionSuperior.getHeight())));
        panelModificarDescripcionSuperior.add(Box.createHorizontalGlue());

        panelModificarDescripcion.add(panelModificarDescripcionSuperior);
        //      tituloTA.setBorder(BorderFactory.createEmptyBorder( 10,10, 10, 10));

        //LE PASAMOS EL AREA DE TEXTO ESTATICA, PARA poder exportar la consulta a la pestaña si queremos
        textArea = new JTextArea("");//INicializando al TextArea 
        textArea.setEditable(true);//Haciendo que TextArea NO SEA EDITABLE
        
        //Agregamos el PopUpMenu al TextArea
        textArea.setComponentPopupMenu(this.popMenu);
        //Con este metodo hacemos que se despliegue el menu al clicar el boton derecho
        textArea.setInheritsPopupMenu(true);

        scroll = new JScrollPane(textArea);//Agregando SCROLL a TextArea
        //Establecemos un borde espaciado VACIO  arriba,izquierda,abajo,derecha 
        scroll.setBorder(BorderFactory.createEmptyBorder(2, 15, 15, 15));
        panelModificarDescripcion.add(scroll);

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
        contentPane.add(panelFormularios, BorderLayout.NORTH);
        contentPane.add(panelModificarDescripcion, BorderLayout.CENTER);
        this.setBounds(300, 300, 600, 600);
        this.setMinimumSize(new Dimension(854, 600));
        // this.setLocationRelativeTo(null);        

    }//Fin de iniciAR
}//Fin de BBDD_Eliminar
