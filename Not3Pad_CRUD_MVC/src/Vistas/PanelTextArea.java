/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.File;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.UndoManager;

/**
 *
 * @author davidf
 */
public class PanelTextArea extends JPanel{
    
    public JTextArea textArea;
    //Declarando objeto de UndoManager, que nos permitirá Deshacer y Rehacer    
    public UndoManager manager; 
    protected JScrollPane scrollPane;
    public JPopupMenu popMenu;
    public JMenuItem pop_Rehacer;
    public JMenuItem pop_Deshacer;    
    public JMenuItem pop_Copiar;
    public JMenuItem pop_Cortar;
    public JMenuItem pop_Pegar;
    public JMenuItem pop_Imprimir;
    //Declarando FICHERO/ARCHIVo
    public File fichero;
    private int incrementar;
    
    
    
    public  PanelTextArea(int contador)  {

        //Se utilizará BORDERLAYOUT, para situar el TextArea en este panel
        this.setLayout(new BorderLayout());

        //Instanciamos el TextArea y lo agregamos al ScrollPane
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);
        //Instanciamos el Popup y los menus
        popMenu = new JPopupMenu();
        pop_Rehacer = new JMenuItem("Rehacer");
        pop_Deshacer = new JMenuItem("Deshacer");        
        pop_Copiar = new JMenuItem("Copiar");
        pop_Cortar = new JMenuItem("Cortar");
        pop_Pegar = new JMenuItem("Pegar");
        pop_Imprimir = new JMenuItem("Imprimir");
 
        //Activamos el textArea para que se muestren los colores
        textArea.setOpaque(true);
        
        //Agregamos TextArea a la capa.
        add(scrollPane, BorderLayout.CENTER);

        //Menú POP-UP AGREGANDO Botones al PopupMenu
        
        popMenu.add(pop_Rehacer);
        popMenu.add(pop_Deshacer);
        popMenu.addSeparator();
        popMenu.add(pop_Copiar);
        popMenu.add(pop_Cortar);
        popMenu.add(pop_Pegar);
        popMenu.addSeparator();
        popMenu.add(pop_Imprimir);

       
        
        //Agregamos el PopUpMenu al TextArea
        textArea.setComponentPopupMenu(popMenu);
        //Con este metodo hacemos que se despliegue el menu al clicar el boton derecho
        textArea.setInheritsPopupMenu(true);
     
        Iniciar(contador);
    }

    private void Iniciar(int contador) {
        
        incrementar=0;
        String nombre_fic;
        
        //Comprobando nombre del fichero, para que no se asignen dos nombres iguales
        boolean exist = false;
        int i = contador;
     
        //Inicializamos el FICHERO/ARCHIVO
        fichero = new File("Archivo_nuevo(" + i + ").txt");
         do {
            if (fichero.exists()) {
                nombre_fic = "Archivo_nuevo(" + i + ").txt";
                fichero = new File(nombre_fic);
                exist = true;
                i++;
                incrementar++;
            } else {
               exist = false; 
            }
        } while (exist);  

                  
      //Agregando manager para //REHACER-DESHACER
       manager = new UndoManager();
       Document document = textArea.getDocument();
       document.addUndoableEditListener(new UndoableEditListener() {
           @Override
           public void undoableEditHappened(UndoableEditEvent e) {
               manager.addEdit(e.getEdit());
           }
       });
        
        
        
        //JPOPUPMENU Agregamos a los botones el JPopupMenu su correspondiente Listener
      pop_Rehacer.addActionListener(new OyentePopRehacer());
      pop_Deshacer.addActionListener(new OyentePopDeshacer());        
      pop_Copiar.addActionListener(new OyentePopCopiar());
      pop_Cortar.addActionListener(new OyentePopCortar());
      pop_Pegar.addActionListener(new OyentePopPegar());
      pop_Imprimir.addActionListener(new OyentePopImprimir());
   
     }//Find e INICIar
    
    
////////////////////////////////////////////////////////////////////////////////    
/////////////////// GETTERS  //////////////////////////////////////////////    
    public int getIncrementar(){
        return incrementar;
    }
    
       
    
    public void cambiarNombre(String rutaArchivo){
        if (fichero.exists()){
        fichero.delete();
        System.gc();
        fichero= new File(rutaArchivo);
        }else{
        fichero= new File(rutaArchivo);
        }        
    }
    
    
    

    
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
    

    
}
