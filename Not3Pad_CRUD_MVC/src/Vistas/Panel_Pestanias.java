/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Modelo.Configuracion;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author davidf
 */
public class Panel_Pestanias extends JPanel {

    public static JTabbedPane TP;
    private int i_aux = 0;

    public Panel_Pestanias() {

        TP = new JTabbedPane();

    }

    public int getI_aux() {
        return i_aux;
    }

    ////////Metodo para crear pestañas NUEVAS con TextArea
    public Panel_Pestanias PestaniaTextoNueva() {

        //Creando objeto de PanelTextArea para pasarselo a la PESTAÑA
        PanelTextArea textArea1 = new PanelTextArea(i_aux);

        //Poniendole titulo a la pestaña y agregandola a la pestaña pasandole el textarea tambien
        String title = textArea1.fichero.getName();
        TP.addTab(title, textArea1);

        /////Incrementamos i_aux para que lleve un contador de las pestaña nuevas que se han creado,
        if (textArea1.getIncrementar() == 0) {
            i_aux++;
        } else {
            i_aux += textArea1.getIncrementar(); //este metodo obtiene el incremento que ha tenido que darle el programa para no crear dos archivos con el mismo
        }

        //Colocando TITULO y BOTON de CERRAR a la pestaña
        for (int i = 0; i < TP.getTabCount(); i++) {
            TP.setTabComponentAt(i, new Cross(TP.getTitleAt(i))); //agrega titulo y boton X.

        }// //Fin del for

        //CARGANDO COLORES, ESTILO, ETC. (En caso de que exista fichero de "configuración" guardado)
        //Cargamos el fichero de configuración
        File fichero = new File("./$Not3pad_Config.conf");
        //Comprobando si el fichero existe
        if (fichero.exists()) {

            try {
                FileInputStream flujo = new FileInputStream(fichero);
                ObjectInputStream lector = new ObjectInputStream(flujo);

                //Como sabemos que solo va a contener UN SOLO OBJETO, comprobaremos si hay algo escrito
                //Y si existe lo recuperaremos y setearemos esta pestaña con la configuración almacenada
                if (fichero.length() > 0) {
                    //RECUPERANDO LOS DATOS
                    //Leemos el objeto configuración y recogoremeos Font y colores
                    Configuracion config = (Configuracion) lector.readObject();
                    textArea1.textArea.setBackground(config.getBackground());
                    textArea1.textArea.setForeground(config.getLetra());
                    textArea1.textArea.setCaretColor(config.getLetra());
                    textArea1.textArea.setSelectionColor(config.getSeleccion());
                    textArea1.textArea.setSelectedTextColor(config.getTextoSeleccionado());
                    textArea1.textArea.setFont(config.getFuente_Almacenada());

                }
                flujo.close();
                lector.close();

                //Agregamos la Excepcion EOF que indicará que se llegó al final del archivo, Y MOSTRAREMOS QUE LLEGAMOS AL FIN D ESTE      
            } catch (EOFException fnfe) {
                //Find el archivo
            } catch (FileNotFoundException fnfe) {
                // System.out.println("Fichero no encontrado");
            } catch (IOException ioe) {
                // System.out.println("Error de fichero");
            } catch (Exception e) {
                // System.out.println("Error de fichero");
            }//Fin del Try-Catch

        }//Fin de IF-FICHERO-EXIST

        return this;
    }//Fin del metodo crear pestaña nueva

    /////////////clase que coloca el TITULO y el BOTON DE CERRAR a la pestaña
    public static class Cross extends JPanel {

        private JLabel L;
        private JButton B;
        private int size = 12;

        public Cross(String title) {

            setOpaque(false);
            setLayout(new java.awt.GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1;
            L = new JLabel(title + " ");
            B = new JButton();
            B.setPreferredSize(new java.awt.Dimension(10, 10));
            B.setIcon(getImage());
            //Listener para cierre de tabs con acceso estatico al `JTabbedPane`
            B.addActionListener(new OyenteCerrarPestania(title));
            add(L, gbc);
            gbc.gridx++;
            gbc.weightx = 0;
            add(B, gbc);
        }

        //Creando y escalando el ICONO de CERRAR la PEsTAÑA
        private ImageIcon getImage() {
            java.awt.Image IMG = null;
            try {
                IMG = new ImageIcon(getClass().getResource("/Images/crossRed.png")).getImage();
                IMG = IMG.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
            } catch (Exception e) {

            }
            return new ImageIcon(IMG);
        }//FinGetImage
    }//Fin de la clase Cross

    static public class OyenteCerrarPestania implements ActionListener {

        String title;

        public OyenteCerrarPestania(String titulo) {
            this.title = titulo;
        }

        //e -> Panel_Pestanias.TP.removeTabAt(Panel_Pestanias.TP.indexOfTab(title))
        @Override
        public void actionPerformed(ActionEvent ae) {

            String textoFichero = "";
            //Hacemos que se seleccione la pestaña a la que se le ha pulsado el boton
            TP.setSelectedIndex(Panel_Pestanias.TP.indexOfTab(title));
            //Recojemos el componente "panelTextArea" de la pestaña seleccionada
            PanelTextArea panelTAaux = (PanelTextArea) TP.getSelectedComponent();

            //     System.out.println(panelTAaux.fichero.toString());
            //Comprobamos si el archivo almacenado es distinto al contenido de nuestro textarea, en este caso,
            //Ofreceremos guardar el documento a nuestro usuario
            if (panelTAaux.fichero.length() > 0) {
                try ( //procedemos acrear el flujo y el lector, para leer nuestro fichero seleccionado
                        //y poder almacenarlo para COMPARARLO   CONLO QUE HAY ESCRITYO EN NUESTRO TEXTAREA
                        FileReader flujo = new FileReader(panelTAaux.fichero)) {
                    try (Scanner lector = new Scanner(flujo)) {
                        while (lector.hasNext()) {
                            textoFichero = (lector.nextLine() + "\n");
                        }//Fin del WHILE
                        lector.close();
                        flujo.close();
                    }

                } catch (IOException ex) {
                    Logger.getLogger(Panel_Pestanias.class.getName()).log(Level.SEVERE, null, ex);
                }
            }//FIn del IF

            if (!textoFichero.equals(panelTAaux.textArea.getText())) {

                int valor = JOptionPane.showConfirmDialog(Panel_Pestanias.TP, "¿Desea guardar antes de Cerrar?", "¿GUARDAR?", JOptionPane.YES_NO_OPTION, JOptionPane.OK_OPTION);

                if (valor == JOptionPane.YES_OPTION) {
                    //             System.out.println("SALVANDO");
                    //            System.out.println(panelTAaux.textArea.getText());

                    try {
                        FileWriter flujo = new FileWriter(panelTAaux.fichero); //Podriamos dejarle true para que seguiera escribiendo debajo de este si existiera, (aunqeu lo estoy eliminando mas arriba pòrque no es el caso)
                        try (PrintWriter escritor = new PrintWriter(flujo)) {
                            escritor.println(panelTAaux.textArea.getText());
                            escritor.close();
                            flujo.close();

                        }
                    } catch (IOException ex) {
                        ex.getMessage();
                    }

                }//Fin del if valor==YES_OPTION

            }//Fin del IF-EQUALS

            //         System.out.println("SALiendo");
            //       System.out.println(panelTAaux.textArea.getText());
            Panel_Pestanias.TP.removeTabAt(Panel_Pestanias.TP.indexOfTab(title));

        }//Fin del actionPerformed

    }//Fin de oyente cerrar pestaña

}//Final de la clase OanelPestanias
