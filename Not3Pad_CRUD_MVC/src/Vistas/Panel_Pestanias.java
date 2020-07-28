/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import java.awt.GridBagConstraints;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


/**
 *
 * @author davidf
 */
public class Panel_Pestanias extends JPanel {

    public static JTabbedPane TP;
    private int i_aux=0;

    
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
         String title=textArea1.fichero.getName();
         TP.addTab(title, textArea1);
        
        /////Incrementamos i_aux para que lleve un contador de las pestaña nuevas que se han creado,
        if (textArea1.getIncrementar()==0){
        i_aux++;
        }else{
            i_aux+=textArea1.getIncrementar(); //este metodo obtiene el incremento que ha tenido que darle el programa para no crear dos archivos con el mismo
        }
        
        //Colocando TITULO y BOTON de CERRAR a la pestaña
        for (int i=0; i < TP.getTabCount(); i++) {
            TP.setTabComponentAt(i, new Cross(TP.getTitleAt(i))); //agrega titulo y boton X.
     
        }// //Fin del for
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
            B.addActionListener(e -> Panel_Pestanias.TP.removeTabAt(Panel_Pestanias.TP.indexOfTab(title)));
     //       B.addActionListener(new ControladorVistaPrincipal.OyenteXX());
         //   B.addActionListener(new OyenteXX());
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
                IMG = IMG.getScaledInstance(size,size , java.awt.Image.SCALE_SMOOTH);
            } catch (Exception e) {
                  
            }
            return new ImageIcon(IMG);
        }
    }


    /*
    
    class OyenteXX implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
      //      TP.removeTabAt(TP.getSelectedIndex());
            System.out.println(TP.getTabPlacement());
         TP.removeTabAt(TP.getTabPlacement());
        }
    }
      
*/
}
