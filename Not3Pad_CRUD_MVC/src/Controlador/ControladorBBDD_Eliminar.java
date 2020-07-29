/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.MetodosBBDD_Consultas;
import Vistas.VistaBBDD_Consultar;
import Vistas.VistaBBDD_Eliminar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import javax.swing.JOptionPane;

/**
 *
 * @author davidf
 */
public class ControladorBBDD_Eliminar {
    
    private VistaBBDD_Eliminar vista; 
    private MetodosBBDD_Consultas metodos;

    public ControladorBBDD_Eliminar(VistaBBDD_Eliminar vista) {
        
        this.vista = vista;
        this.metodos = new MetodosBBDD_Consultas();
        
//            Iniciar();
            
    }


    /*
    
    
    private void Iniciar(){
        
      //JPOPUPMENU Agregamos a los botones el JPopupMenu su correspondiente Listener
      vista.pop_Rehacer.addActionListener(new OyentePopRehacer());
      vista.pop_Deshacer.addActionListener(new OyentePopDeshacer());        
      vista.pop_Copiar.addActionListener(new OyentePopCopiar());
      vista.pop_Cortar.addActionListener(new OyentePopCortar());
      vista.pop_Pegar.addActionListener(new OyentePopPegar());
      vista.pop_Imprimir.addActionListener(new OyentePopImprimir());
    
    
    }//Fin del metodo INICIAR
    
    
       
    ////////////////////////////////////////////////////////////////////////////////    
///////////////////  POP-UP MENU  //////////////////////////////////////////////
////////////CONFIGURARÉ LOS OYENTES DEL JPOPUPMENU EN ESTA MISMA CLASE//////////   
    ////////////////////////////////////////////////////////////////////////////////   
    class OyentePopRehacer implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
           if(vista.manager.canRedo())
               vista.manager.redo();
        }//Fin action performed
    }//Fin del OyenteCOPIAR
    
    
    ////////////////////////////////////////////////////////////////////////////////   
    class OyentePopDeshacer implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            if(vista.manager.canUndo())
                vista.manager.undo();
            
        }//Fin action performed
    }//Fin del OyenteCOPIAR
    
    
    class OyentePopCopiar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            
            vista.textArea.copy();
        }//Fin action performed
    }//Fin del OyenteCOPIAR

////////////////////////////////////////////////////////////////////////////////   
    class OyentePopCortar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ

            vista.textArea.cut();
        }//Fin action performed
    }//Fin del OyenteCOPIAR

////////////////////////////////////////////////////////////////////////////////   
    class OyentePopPegar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            vista.textArea.paste();
        }//Fin action performed
    }//Fin del OyenteCOPIAR



    ////////////////////////////////////////////////////////////////////////////////   
    class OyentePopImprimir implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
                vista.textArea.print();
            } catch (PrinterException ex) {
                 JOptionPane.showMessageDialog(null, "Se produjo un error de impresión", "Error de Impresión", JOptionPane.ERROR_MESSAGE);
            }
        }//Fin action performed
    }//Fin del OyenteCOPIAR
    
    
    
    
    
    
    
    
    
    
    
    */
    
    
    
    
    
    
    
    
}//Fin de la clase CONTROLADOR-INSERTAR
