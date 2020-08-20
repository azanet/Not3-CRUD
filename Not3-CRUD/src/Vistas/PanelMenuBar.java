/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 *
 * @author davidf
 *
 *
 * Este panel contendrá el menú botones ETC correspondientes a la "ventana
 * principal"
 *
 */
public class PanelMenuBar extends JPanel {

    // public JPanel panelPrincipal;
    private JMenuBar menuBar;
    public JMenu archivo, edicion, imprimir, bbdd, ayuda, personalizar;
    public JMenuItem nuevo;
    public JMenuItem abrir, guardar, guardarComo, renombrar, ir_A, deshacer, rehacer, copiar, pegar, cortar, insertarFecha, buscar, buscarYreemplazar, buscarYreemplazarTodo,imprimir_configurando, imprimir_directo, salir, acercaDe;
    //Haremos estos CheckBox estáticos, para poder utilizarlo en las distintas ventanas correspondientes y poder cambiar su valor si el usuario elige salir de estas
    public JMenuItem conectar_desconectar;
    public static JCheckBox insertar, consultar, modificar, eliminar;

    public JMenuItem colorBackground, colorFuente, colorSeleccion, colorTextoSeleccionado, guardarConfiguracion;
    public JComboBox comboBoxTamanio, comboBoxStyle, comboBoxTipo;

    public PanelMenuBar() {

        Iniciar();
    }//Find el constructor

    private void Iniciar() {

        //   panelPrincipal= new JPanel();
        this.menuBar = new JMenuBar();
        this.archivo = new JMenu(" Archivo");
        this.edicion = new JMenu(" Edición");
        this.imprimir = new JMenu(" Imprimir");
        this.bbdd = new JMenu(" BBDD");
        this.personalizar = new JMenu(" Personalizar");
        this.ayuda = new JMenu(" Ayuda");

        this.nuevo = new JMenuItem("Nuevo");
        this.nuevo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        this.abrir = new JMenuItem("Abrir");
        this.abrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        this.guardar = new JMenuItem("Guardar");
        this.guardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        this.guardarComo = new JMenuItem("Guardar como...");
        this.guardarComo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_MASK));
        this.renombrar = new JMenuItem("Renombrar");
        this.renombrar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
        this.ir_A = new JMenuItem("Ir a...");
        this.ir_A.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
        this.deshacer = new JMenuItem("Deshacer");
        this.deshacer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
        this.rehacer = new JMenuItem("Rehacer");
        this.rehacer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
        this.copiar = new JMenuItem("Copiar");
        this.copiar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        this.pegar = new JMenuItem("Pegar");
        this.pegar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
        this.cortar = new JMenuItem("Cortar");
        this.cortar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        this.insertarFecha = new JMenuItem("Insertar Fecha/Hora");
        this.insertarFecha.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, InputEvent.CTRL_MASK));
        this.buscar = new JMenuItem("Buscar");
        this.buscar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
        this.buscarYreemplazar = new JMenuItem("Buscar y Reemplazar");
        this.buscarYreemplazar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
        this.buscarYreemplazarTodo = new JMenuItem("Buscar y Reemplazar TODO");
        this.buscarYreemplazarTodo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F8, InputEvent.CTRL_MASK));
        this.imprimir_configurando = new JMenuItem("Imprimir configurando");
        this.imprimir_configurando.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
        this.imprimir_directo = new JMenuItem("Imprimir directo");
        this.imprimir_directo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        this.salir = new JMenuItem("Salir");
        this.salir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, InputEvent.CTRL_MASK));
        this.acercaDe = new JMenuItem("Acerca de...");
        this.acercaDe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));

        this.conectar_desconectar = new JMenuItem("CONECTAR");
        this.conectar_desconectar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.ALT_MASK));
        this.insertar = new JCheckBox("Insertar", false);
        this.consultar = new JCheckBox("Consultar", false);
        this.modificar = new JCheckBox("Modificar", false);
        this.eliminar = new JCheckBox("Eliminar", false);

        this.colorBackground = new JMenuItem("Color de Fondo");
        this.colorFuente = new JMenuItem("Color de Fuente");
        this.colorSeleccion = new JMenuItem("Color de Selección");
        this.colorTextoSeleccionado = new JMenuItem("Color de Texto Seleccionado");
        this.guardarConfiguracion = new JMenuItem("Guardar \"TEMA\" como Predeterminado");

        this.comboBoxTamanio = new JComboBox();
        this.comboBoxTipo = new JComboBox();
        this.comboBoxStyle = new JComboBox();

//Utilizaremos Layout para situar los componentes sin tener que estar pasando las coordenadas de posición
        //Los iremos colocando en orden para que vaya agregandolos
        //pONEMOS EL LAYOUT DE ESTE PANEL COMO grid PARA QUE SE OCUPE TODO EL AREA DISPONIBLE al agregar el MenuBar
        this.setLayout(new GridLayout());

        this.setBackground(Color.red);//Esto es para ver el color de fondo de este panel por si sale por algun sitio

        //Agregamos la MenuBar al panel
        //Configuraremos el LAYOUT del MENUBAR a mano, para que los botones queden bién y no se redimensionen
        menuBar.setLayout(null);

        //Agregamos botones al JMenuBar
        archivo.setBounds(0, 0, 60, 20);
        archivo.setMnemonic('A'); //Si se pulsa "Alt+CARACTER" se podrá acceder al menú y sus elementos y movernos a través de la barra de menú
        menuBar.add(archivo);

        edicion.setBounds(60, 0, 60, 20);
        edicion.setMnemonic('E');//Si se pulsa "Alt+CARACTER" se podrá acceder al menú y sus elementos y movernos a través de la barra de menú
        menuBar.add(edicion);

        imprimir.setBounds(120, 0, 65, 20);
        imprimir.setMnemonic('I');//Si se pulsa "Alt+CARACTER" se podrá acceder al menú y sus elementos y movernos a través de la barra de menú
        menuBar.add(imprimir);

        bbdd.setBounds(185, 0, 50, 20);
        bbdd.setMnemonic('B');//Si se pulsa "Alt+CARACTER" se podrá acceder al menú y sus elementos y movernos a través de la barra de menú
        menuBar.add(bbdd);

        personalizar.setBounds(235, 0, 90, 20);
        personalizar.setMnemonic('P');//Si se pulsa "Alt+CARACTER" se podrá acceder al menú y sus elementos y movernos a través de la barra de menú
        menuBar.add(personalizar);

        ayuda.setBounds(325, 0, 52, 20);
        ayuda.setMnemonic('Y');//Si se pulsa "Alt+CARACTER" se podrá acceder al menú y sus elementos y movernos a través de la barra de menú
        menuBar.add(ayuda);

        comboBoxTipo.setBounds(377, 0, 77, 20);
        menuBar.add(comboBoxTipo);

        comboBoxTamanio.setBounds(454, 0, 52, 20);
        menuBar.add(comboBoxTamanio);

        //        setPreferredSize(new Dimension(410, 50));
        comboBoxStyle.setBounds(506, 0, 148, 20);
        menuBar.add(comboBoxStyle);

        //Agregamos CADA BOTÓN a su "Menú" Correspondiente
        //Menú ARCHIVO
        archivo.add(nuevo);
        archivo.add(abrir);
        archivo.add(guardar);
        archivo.add(guardarComo);
        archivo.add(renombrar);
        archivo.addSeparator();
        archivo.add(salir);
        //Menú EDICIÓN
        edicion.add(deshacer);
        edicion.add(rehacer);
        edicion.addSeparator();
        edicion.add(copiar);
        edicion.add(pegar);
        edicion.add(cortar);
        edicion.addSeparator();
        edicion.add(ir_A);
        edicion.add(buscar);
        edicion.add(buscarYreemplazar);
        edicion.add(buscarYreemplazarTodo);
        edicion.addSeparator();
        edicion.add(insertarFecha);
        //Menú Imprimir
        imprimir.add(imprimir_configurando);
        imprimir.add(imprimir_directo);
        //Menú MODE

        bbdd.add(conectar_desconectar);
        bbdd.addSeparator();
        bbdd.add(insertar);
        bbdd.addSeparator();
        bbdd.add(consultar);
        bbdd.addSeparator();
        bbdd.add(modificar);
        bbdd.addSeparator();
        bbdd.add(eliminar);

        //Menú Personalizar
        personalizar.add(colorBackground);
        personalizar.add(colorFuente);
        personalizar.addSeparator();
        personalizar.add(colorSeleccion);
        personalizar.add(colorTextoSeleccionado);
        personalizar.add(guardarConfiguracion);

        //Menú Ayuda
        ayuda.add(acercaDe);

        this.add(menuBar);

    }//Fin del metodo INICIAR

    //NO SE ASIGNARÁ TAMAÑO AL PANEL, PARA QUE SE ADAPTE AL JFRAME
}
