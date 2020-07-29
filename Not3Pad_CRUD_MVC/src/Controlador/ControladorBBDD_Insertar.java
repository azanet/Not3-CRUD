/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.MetodosBBDD_Consultas;
import Vistas.PanelMenuBar;
import Vistas.VistaBBDD_Insertar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author davidf
 * 
 * DESCRIPCION:
 * Este panel "guardará" las opciones en el caso que se cierre la pestaña
 * para ello he implementado metodos y variables para cuando vuelva abrir y se ejecute el controlador
 * cargue una serie de variables que le ayudarán a estar como cuando se cerro la pestaña.
 * Si se cierra el programa si se eliminará el contenido de la ventana.
 * 
 */
public class ControladorBBDD_Insertar {
    
    private VistaBBDD_Insertar vista; 
    private MetodosBBDD_Consultas metodos;
    
    //Estas variables almacenarán los grupos y articulos que existan en la BBDD
    private String[] grupos ;
    private String[] articulos ;
    
    //Estas variables  las utilizaremos para saber 
    public static int grupoSeleccionado=-1;
    public static int articuloSeleccionado=-1;
    private static boolean cargado= false;
 
    
    
    public ControladorBBDD_Insertar(VistaBBDD_Insertar vista) throws SQLException {
        
        this.vista = vista;
        this.metodos = new MetodosBBDD_Consultas();
        
            Iniciar();
            
    }


    
    private void Iniciar() throws SQLException{
        
      //JPOPUPMENU Agregamos a los botones el JPopupMenu su correspondiente Listener
      vista.pop_Rehacer.addActionListener(new OyentePopRehacer());
      vista.pop_Deshacer.addActionListener(new OyentePopDeshacer());        
      vista.pop_Copiar.addActionListener(new OyentePopCopiar());
      vista.pop_Cortar.addActionListener(new OyentePopCortar());
      vista.pop_Pegar.addActionListener(new OyentePopPegar());
      vista.pop_Imprimir.addActionListener(new OyentePopImprimir());
      
      vista.BotonAltaGrupo.addActionListener(new OyenteAltaGrupo());
      vista.BotonAltaArticulo.addActionListener(new OyenteAltaArticulo());
      vista.BotonagregarDescripcion.addActionListener(new OyenteInsertarDescripcion());
      vista.BotonlimpiarPantalla.addActionListener(new OyenteLimpiarPantalla());
      vista.Botonsalir.addActionListener(new OyenteSalir());
      
      vista.comboGrupos.addActionListener(new OyenteComboGrupos());
      vista.comboArticulos.addActionListener(new OyenteComboArticulos());
      
    
      
      
   //Con este método comprobaremos si ya se cargaron los combobox, para no vovler a cargarlos innecesariamente  
  if(cargado==false && grupoSeleccionado==-1){
        RefrescarCombobox();
  }
     
        //Este método comprobará si se produjo la operación de seleccion correctamente para poder pasar al siguiente paso
       DesbloqueoDeOpcionesArticulo();
        DesbloqueoDeOpcionesGrupo();
        
       
        
       
    }//Fin del metodo INICIAR
    
    
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++    
   //Este metodo será utilizado Para Refrescar los comboboxes 
    private void RefrescarCombobox() throws SQLException{
     
        //SETEANDO LOS COMBOBOX
        ///***CREAR CONSULTA PARA RECUPERAR GRPOS Y ARTICULOS Y ALMACENARLOS EN UN OBEJETO   
      
        //ELIMINAMOS TODOS LOS OBJETOS DEL COMBOBOX, PARA RRELLENARLOS DE NUEVO
        vista.comboGrupos.removeAllItems();
             vista.comboGrupos.addItem("------>");
              vista.comboArticulos.removeAllItems();
       vista.comboArticulos.addItem("------>"); 
       
        //Ejecutamos la CONSULTA "LecturaGrupos", la cual nos devolverá un array con los nombre de los grupos existentes
        //Y con esto inicializamos o actualizamos nustras combobox de Grupos
        grupos=metodos.LecturaGrupos();
       
        
        //Rellenando COMBOBOX de GRUPOS con el array recuperado por la consulta     
        for (String grupo : grupos) {
                vista.comboGrupos.addItem(grupo);
             }//Fin del FOR Grupos
        
        
        articulos=new String[2];
         articulos[0]="articulo0";
         articulos[1]="articulo1";
      
        //Rellenando COMBOBOX de ARTICULOS con el array recuperado por la consulta     
        for (String articulo : articulos) {
                vista.comboArticulos.addItem(articulo);
             }//Fin del FOR Articulo
        
        cargado=true;
    }//Fin de REFRESCAR


    
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++        
     //Este metodo será utilizado para saber si DESBLOQUEAR O NO las opciones siguientes para la consulta
    //ACCIONES NECESARIAS PARA IR DESBLOQUEANDO LOS SIGUIENTES COMBOBOXES
        //PARA DE ESTA FORMA, OBLIGAR AL USUARIO A QUE HAGA LA CONSULTA CORRECTAMENTE
    private void DesbloqueoDeOpcionesGrupo(){
        
        
        if(grupoSeleccionado>0){
             //DESBLOQEUAR CAMPO DE ARTICULO
             
//OPCIONAL+++Poner el comboBox de articulo A NINGUN SELECCIONADO
         //   vista.comboArticulos.setSelectedIndex(0);
             vista.tituloGrupoNuevo.setEnabled(false);  
             vista.textFieldGrupoNuevo.setEnabled(false);  
             vista.BotonAltaGrupo.setEnabled(false);  
            vista.tituloComboboxArticulo.setEnabled(true);
            vista.comboArticulos.setEnabled(true);    
            vista.tituloArticuloNuevo.setEnabled(true);
            vista.textFieldArticuloNuevo.setEnabled(true);
            vista.BotonAltaArticulo.setEnabled(true);
            
        }else{

  vista.tituloGrupoNuevo.setEnabled(true);  
   vista.textFieldGrupoNuevo.setEnabled(true); 
   vista.BotonAltaGrupo.setEnabled(true); 
   vista.tituloComboboxArticulo.setEnabled(false);
     vista.comboArticulos.setEnabled(false);
     vista.tituloArticuloNuevo.setEnabled(false);
      vista.textFieldArticuloNuevo.setEnabled(false);
      vista.BotonAltaArticulo.setEnabled(false);
      vista.tituloDescripcion.setEnabled(false);
      vista.BotonagregarDescripcion.setEnabled(false);
      vista.textArea.setEnabled(false);
 
     
        }//Fin del else
      
        }
    
           private void DesbloqueoDeOpcionesArticulo(){
        
        if(articuloSeleccionado>0){
            
                //DESBLOQEUAR CAMPO DE DESCRIPCION
                
              vista.tituloArticuloNuevo.setEnabled(false);  
             vista.textFieldArticuloNuevo.setEnabled(false);  
             vista.BotonAltaArticulo.setEnabled(false);  
            vista.tituloDescripcion.setEnabled(true);
            vista.BotonagregarDescripcion.setEnabled(true);
            vista.textArea.setEnabled(true);
        
        }else{
                //BLOQEUAR CAMPO DE DESCRIPCION
               vista.tituloArticuloNuevo.setEnabled(true);  
             vista.textFieldArticuloNuevo.setEnabled(true);  
             vista.BotonAltaArticulo.setEnabled(true);  
                    vista.tituloDescripcion.setEnabled(false);
      vista.BotonagregarDescripcion.setEnabled(false);
      vista.textArea.setEnabled(false);
                
        }//Fin del else
        
    }//FIN DE DESBLOQUEO-OPCIONES
    
           
    ////////////////////////////////////////////////////////////////////////////////    
//////////// OYENTES DE BOTONES y COMBOBOX de VISTA-INSERTAR//////////   
    //////////////////////////////////////////////////////////////////////////////// 
    
    class OyenteAltaGrupo implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            
            if(vista.textFieldGrupoNuevo.getText().equalsIgnoreCase("")){
                JOptionPane.showMessageDialog(vista,"El campo NO puede estar VACÍO" , "ERROR", JOptionPane.WARNING_MESSAGE);
            }else{
             //   String resultado = metodos.AltaGrupo(vista.textFieldGrupoNuevo.getText());
                JOptionPane.showMessageDialog(vista, metodos.AltaGrupo(vista.textFieldGrupoNuevo.getText()) , "Alta Satisfactoria", JOptionPane.INFORMATION_MESSAGE);
            
            
                try {
                    RefrescarCombobox();
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorBBDD_Insertar.class.getName()).log(Level.SEVERE, null, ex);
                }
                     DesbloqueoDeOpcionesArticulo();
        DesbloqueoDeOpcionesGrupo();
            }
            
           
        }//Fin de la accion sobreescrita

    }//Fin del oyenteAltaGrupo
//=================================================
 
//=================================================
        class OyenteAltaArticulo implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            vista.textArea.setText("WIWI\n");
        
            try {
                RefrescarCombobox();
            } catch (SQLException ex) {
                Logger.getLogger(ControladorBBDD_Insertar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//Fin de la accion sobreescrita

    }//Fin del oyenteAltaArticulo
 //=================================================
 
//=================================================
        class OyenteInsertarDescripcion implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            vista.textArea.setText("pipi\n");
                   vista.textArea.setText("");
            vista.textFieldGrupoNuevo.setText("");
            vista.textFieldArticuloNuevo.setText("");
            vista.comboArticulos.setSelectedIndex(0);
            vista.comboGrupos.setSelectedIndex(0);
        
        }//Fin de la accion sobreescrita

    }//Fin del oyenteInsertarDescripcion  
    
//=================================================
 
//=================================================
        class OyenteLimpiarPantalla implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            vista.textArea.setText("");
            vista.textFieldGrupoNuevo.setText("");
            vista.textFieldArticuloNuevo.setText("");
            vista.comboArticulos.setSelectedIndex(0);
            vista.comboGrupos.setSelectedIndex(0);
        
        }//Fin de la accion sobreescrita

    }//Fin del oyenteInsertarDescripcion  
      
//=================================================
 
//=================================================
        class OyenteSalir implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            
            //Hacemos que se cierre la ventana, SIN DETENER LA EJECUCIÓN DEL PROGRAMA
           vista.dispose();
           //Deseleccionamos el CheckBox correspondiente del MENU-BAR
            PanelMenuBar.insertar.setSelected(false);
           
        }//Fin de la accion sobreescrita

    }//Fin del oyenteInsertarDescripcion  
      
  //=================================================
 
//=================================================
        class OyenteComboGrupos implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
         
         //Recogemos el INDICE donde se encuentra, para luego poder volver a recuperarlo
         grupoSeleccionado = vista.comboGrupos.getSelectedIndex();             
         
               
           
                DesbloqueoDeOpcionesGrupo();
        }//Fin de la accion sobreescrita

    }//Fin del oyenteInsertarDescripcion   
    
//=================================================
 
//=================================================
        class OyenteComboArticulos implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            
        articuloSeleccionado = vista.comboArticulos.getSelectedIndex();
               DesbloqueoDeOpcionesArticulo();    
        }//Fin de la accion sobreescrita

    }//Fin del oyenteInsertarDescripcion   
    
    
       
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}//Fin de la clase CONTROLADOR-INSERTAR
