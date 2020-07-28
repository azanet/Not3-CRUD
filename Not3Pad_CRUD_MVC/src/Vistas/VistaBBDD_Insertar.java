/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.time.LocalTime;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author davidf
 */
public class VistaBBDD_Insertar extends JFrame {

    //Establecemos un objeto de "dimension", que le pasaremos al metodo
    //"setMinimunSize() para establecer el tamaño mínimo que podrá tener nuestra aplicación
    public JPanel panelBotones;
    public JPanel panelTextArea;

    
      //DECLARANDO TODOS LOS ELEMENTOS QUE COMPONEN NUESTRA VENTANA
    //Declarando Campos y Area de Texto 
  
    
    //Declarando SCROLLPane para agregar el TExtArea a este mas abajo
    public  JTextArea textArea;
    JScrollPane scroll;

    //Declarando Etiquetas
 

    //Declarando Botones

    
    
    
    
    
    
//Creando constructor
    public VistaBBDD_Insertar() {

        // super("Not3Pad");
        Iniciar();

    }

    private void Iniciar() {
        //Seteamos TITULO a la ventana principal 
        Image icono = new ImageIcon(getClass().getResource("/Images/LogoKrazyLab.png")).getImage();
        setIconImage(icono);
        setTitle("BBDD-INSERTAR");
        
     
        panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
        JLabel titulo = new JLabel("INSERTAR");
        panelBotones.add(Box.createRigidArea(new Dimension(10,0)));
        panelBotones.add(titulo);
      //  panelBotones.add(Box.createRigidArea(new Dimension(0,100)));
        
        
         panelTextArea = new JPanel();
        panelTextArea.setLayout(new BoxLayout(panelTextArea, BoxLayout.X_AXIS));
        

     
        //Inicializando CAMPOS y AREA de Texto (aqui indicaremos lo que queremos que tenga escrito dentro el campo, EN MI CASO ESTARAN VACIOS)
   
        

        textArea = new JTextArea("");//INicializando al TextArea 
        textArea.setEditable(true);//Haciendo que TextArea NO SEA EDITABLE
         scroll= new JScrollPane(textArea);//Agregando SCROLL a TextArea
        panelTextArea.add(scroll); 
        
        
    //    TextArea_existentes.setLineWrap(true); //Haciendo que se haga salto de linea al llegar al final
       // TextArea_existentes.setWrapStyleWord(true);
        //Agregamos TEXTAREA al Scroll, como textArea está agregado a Scroll, será a este al que AGREGEMOS y ASIGNEMOS el tamaño mas abajo

        
        
        
        
        
        
        
        
        
                
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
       contentPane.add(panelBotones,BorderLayout.NORTH);
       contentPane.add(panelTextArea, BorderLayout.CENTER);
               this.setBounds(0,0,1000, 1000);
         this.setLocationRelativeTo(null);        
  //   this.pack();
    }
    

    
    
       
  
           
    
    
    

}//Fin de BBDD_Insertar
