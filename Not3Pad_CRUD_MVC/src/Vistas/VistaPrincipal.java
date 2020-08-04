/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author davidf
 */
public class VistaPrincipal extends JFrame {

    //Establecemos un objeto de "dimension", que le pasaremos al metodo
    //"setMinimunSize() para establecer el tamaño mínimo que podrá tener nuestra aplicación
    public static JPanel panelBase;

//Creando constructor
    public VistaPrincipal() {

        // super("Not3Pad");
        Iniciar();

    }

    private void Iniciar() {
        //Seteamos TITULO a la ventana principal 
        Image icono = new ImageIcon(getClass().getResource("/Images/LogoKrazyLab.png")).getImage();
        setIconImage(icono);
        setTitle("Not3Pad");
        panelBase = new JPanel();

        //DESCOMENTANDO ESTAS LINEAS, PODEMOS ESTABLECER UN ESTILO VISUAL DISTINTO PARA NUESTRA APLICACIÓN UTILIZANDO LA LIBRERIA "JTATTOO"
        //EL ESTILO VISUAL HAY QUE SETEARSE ANTES DE MOSTRAR LA VENTANA, O DARÁ ERROR        
        try {
            //Ponemos primero el nombre del paquete, y seguido de este, la clase LookandFeel del estilo que queremos
            //   UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null, "Error al intentar establecer el estilo de vista\nSe establecerá el estilo visual por defecto");
        }

    }

}//Fin de la vista principal

