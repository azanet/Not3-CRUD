/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.MetodosBBDD_Consultas;
import Modelo.objetoTablaDescripcion;
import Vistas.PanelMenuBar;
import Vistas.VistaBBDD_Consultar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.util.TreeSet;
import javax.swing.JOptionPane;

/**
 *
 * @author davidf
 */
public class ControladorBBDD_Consultar {
    
    private final VistaBBDD_Consultar vista; 
    private final MetodosBBDD_Consultas metodos;

    //DefiniendoTreeSet de objetosConsulta, que recibiremos de la BBDD (se ordenarán por ID) ya que el objeto implementa la clase comparable
    TreeSet<objetoTablaDescripcion> ListaObjetosDescripcion = new TreeSet<>();
    


    private String[] grupos;
    private String[] articulos;
    
    int RB_Seleccionado=1;
    public int grupoSeleccionado = 0;
    public int articuloSeleccionado = 0;
    


    public ControladorBBDD_Consultar(VistaBBDD_Consultar vista) {
        
        this.vista = vista;
        this.metodos = new MetodosBBDD_Consultas();
        Iniciar();
            
    }


   
    
    
    private void Iniciar(){
        
      //JPOPUPMENU Agregamos a los botones el JPopupMenu su correspondiente Listener
            //JPOPUPMENU Agregamos a los botones el JPopupMenu su correspondiente Listener
        vista.pop_Rehacer.addActionListener(new OyentePopRehacer());
        vista.pop_Deshacer.addActionListener(new OyentePopDeshacer());
        vista.pop_Copiar.addActionListener(new OyentePopCopiar());
        vista.pop_Cortar.addActionListener(new OyentePopCortar());
        vista.pop_Pegar.addActionListener(new OyentePopPegar());
        vista.pop_Imprimir.addActionListener(new OyentePopImprimir());
        vista.comboGrupos_DUO.addActionListener(new OyenteComboGrupos_DUO());
        vista.comboArticulos_DUO.addActionListener(new OyenteComboArticulos_DUO());
       
        vista.comboGrupos_SOLO.addActionListener(new OyenteComboGrupos_SOLO());
        vista.comboArticulos_SOLO.addActionListener(new OyenteComboArticulos_SOLO());
        
        //Agregando oyentes a radioBotones, según el que se elija, se bloquearán o desbloquearán algunos componentes
        //Tambien se SETEARÁ la variable RB_Seleccionado en 1,2,3 según la elección
        //Dependiendo de esta elección, se realozará una consulta u otra
        //cuando el usuario pulse el boton REALIZAR_CONSULTA
        vista.RB_Selec_DUO.addActionListener(new OyenteRadioBut_DUO());
       vista.RB_Selec_Grupo_SOLO.addActionListener(new OyenteRadioButGrupo_SOLO());
       vista.RB_Selec_Articulo_SOLO.addActionListener(new OyenteRadioButArticulo_SOLO());
     
        
        
       //Abajo del codigo se agfrega un BOTONEXPORTACONSULTAc que esta declarado como estático en la vista proncipal, para poder recuiperar el textArea
      vista.BotonConsultarDescripcion.addActionListener(new OyenteConsultarDescripcion());
       vista.BotonlimpiarPantalla.addActionListener(new OyenteLimpiarPantalla());
        vista.Botonsalir.addActionListener(new OyenteSalir());

      

        //Cargamos un elemento en el COMBOBOX grupo, y el LISTENER se encargará de lanzar el resto de metodos necesarios para ir cargando los comboboxes en orden para evitar errores 
        vista.comboGrupos_DUO.addItem("============>");
        vista.comboArticulos_DUO.addItem("============>");
        vista.comboGrupos_SOLO.addItem("============>");
        vista.comboArticulos_SOLO.addItem("============>");
  
        
           vista.tituloGrupo_DUO.setEnabled(false);
           vista.comboGrupos_DUO.setEnabled(false);
           vista.tituloArticulo_DUO.setEnabled(false);
           vista.comboArticulos_DUO.setEnabled(false);
           vista.tituloArticulo_SOLO.setEnabled(false);
           vista.comboArticulos_SOLO.setEnabled(false);
           vista.tituloGrupo_SOLO.setEnabled(false);
           vista.comboGrupos_SOLO.setEnabled(false);
       
    }//Fin del metodo INICIAR
    
    
    
    private void BorrarPantalla(){
            vista.textArea.setText("");
            vista.comboArticulos_DUO.setSelectedIndex(0);
            vista.comboGrupos_DUO.setSelectedIndex(0);
            vista.comboGrupos_SOLO.setSelectedIndex(0);
            vista.comboArticulos_SOLO.setSelectedIndex(0);
    }
    
    
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++    
    //Este metodo será utilizado Para Refrescar los comboboxes 
    private void RefrescarCombobox_DUO() {

        //SETEANDO LOS COMBOBOX
        ///***CREAR CONSULTA PARA RECUPERAR GRPOS Y ARTICULOS Y ALMACENARLOS EN UN OBEJETO   
       
        grupos = metodos.LecturaGrupos();
        //Ejecutamos la CONSULTA "LecturaGrupos", la cual nos devolverá un array con los nombre de los grupos existentes
        //Y con esto inicializamos o actualizamos nustras combobox de Grupos

        if (grupoSeleccionado == 0) {
            vista.comboGrupos_DUO.removeAllItems();
            for (String grupo : grupos) {
                vista.comboGrupos_DUO.addItem(grupo);
            }//Fin del FOR Grupos
        //Revisaremos se hay alguna opcion elegida en GRUPO, si no la hay, no cargamos el combo de articulos
        
        }
            //Cargamos Articulos con los nombre recibidos de la BBDD
             articulos = metodos.LecturaArticulos((String) vista.comboGrupos_DUO.getSelectedItem());
            vista.comboArticulos_DUO.removeAllItems();
        

              for (String articulo : articulos) {
                vista.comboArticulos_DUO.addItem(articulo);
            }//Fin del FOR Grupos
        
    }//Fin de REFRESCAR ComboboxesDUO
    
//========================================================================  
    
    private void RefrescarComboboxGrupos_SOLO() {
            
            grupos = metodos.LecturaGrupos();
        //Ejecutamos la CONSULTA "LecturaGrupos", la cual nos devolverá un array con los nombre de los grupos existentes
        //Y con esto inicializamos o actualizamos nustras combobox de Grupos
            vista.comboGrupos_SOLO.removeAllItems();
            for (String grupo : grupos) {
                vista.comboGrupos_SOLO.addItem(grupo);
          }//Fin del FOR
    }//Fin de REFRESCAR ComboboxeGRUPO SOLO
    
//========================================================================      
    
    private void RefrescarComboboxArticulos_SOLO() {
    
                    //Cargamos Articulos con los nombre recibidos de la BBDD
             articulos = metodos.LecturaArticulos_SOLO();
            vista.comboArticulos_SOLO.removeAllItems();
        

              for (String articulo : articulos) {
                vista.comboArticulos_SOLO.addItem(articulo);
            }//Fin del FOR Grupos
         
         
    }//Fin de REFRESCAR Combobox ARTICULO SOLO
    
    
    
    
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++        
    //Este metodo será utilizado para saber si DESBLOQUEAR O NO las opciones siguientes para la consulta
    //ACCIONES NECESARIAS PARA IR DESBLOQUEANDO LOS SIGUIENTES COMBOBOXES
    //PARA DE ESTA FORMA, OBLIGAR AL USUARIO A QUE HAGA LA CONSULTA CORRECTAMENTE
   
    private void DesbloqueoDeOpcionesGrupo_DUO() {

        if (grupoSeleccionado > 0) {
            //DESBLOQEUAR CAMPO DE ARTICULO

            vista.tituloArticulo_DUO.setEnabled(true);
            vista.comboArticulos_DUO.setEnabled(true);
     //       vista.tituloArticulo_SOLO.setEnabled(true);

        } else {

            vista.tituloArticulo_DUO.setEnabled(false);
            vista.comboArticulos_DUO.setEnabled(false);
   //         vista.tituloArticulo_SOLO.setEnabled(false);
            vista.BotonExportarConsulta.setEnabled(false);
            vista.tituloDescripcion.setEnabled(false);
            vista.BotonConsultarDescripcion.setEnabled(false);
            vista.textArea.setEnabled(false);

        }//Fin del else

    }//Fin de desbloqueDeOpcionesGrupo_DUO
//==========================================================================
    
    private void DesbloqueoDeOpcionesArticulo_DUO() {

        if (articuloSeleccionado > 0) {
            //DESBLOQEUAR CAMPO DE DESCRIPCION
            vista.tituloDescripcion.setEnabled(true);
            vista.BotonConsultarDescripcion.setEnabled(true);
            vista.textArea.setEnabled(true);
            vista.BotonExportarConsulta.setEnabled(false);
         
         } else if (articuloSeleccionado == 0 && grupoSeleccionado == 0) {
            //BLOQEUAR CAMPO DE DESCRIPCION
            vista.tituloDescripcion.setEnabled(false);
            vista.BotonConsultarDescripcion.setEnabled(false);
            vista.textArea.setEnabled(false);
            vista.BotonExportarConsulta.setEnabled(false);
        }else {
            //BLOQEUAR CAMPO DE DESCRIPCION
             vista.tituloDescripcion.setEnabled(false);
            vista.BotonConsultarDescripcion.setEnabled(false);
            vista.textArea.setEnabled(false);//Fin del else
            vista.BotonExportarConsulta.setEnabled(false);
         }
    }//FIN DE DESBLOQUEO-OPCIONES_ARTUCULO_DUO
    
 //=============================================================================   
        //Aprovechamos esta funcion, para bloquear o desbloquear las opciones 
        //que controlan los COMBOBOXES_SOLO, ya que no afectará al uso del otro combobox
       private void DesbloqueoDeOpcionesCombobox_SOLO() {

        if (grupoSeleccionado > 0 || articuloSeleccionado > 0) {
        
            vista.BotonExportarConsulta.setEnabled(false);
            vista.tituloDescripcion.setEnabled(true);
            vista.BotonConsultarDescripcion.setEnabled(true);
            vista.textArea.setEnabled(true);


        } else {
            vista.BotonExportarConsulta.setEnabled(false);
            vista.tituloDescripcion.setEnabled(false);
            vista.BotonConsultarDescripcion.setEnabled(false);
            vista.textArea.setEnabled(false);

        }//Fin del else

    }//Fin de desbloqueDeOpcionesGrupo_SOLO

    
    
    
    ////////////////////////////////////////////////////////////////////////////////    
//////////// OYENTES DE BOTONES y COMBOBOX de VISTA-INSERTAR//////////   
    //////////////////////////////////////////////////////////////////////////////// 
 
  //////////////////CREANDO OYENTES DE RADIOBOTONES//////////////////////////////        
       
  class OyenteRadioBut_DUO implements ActionListener {    

        @Override
        public void actionPerformed(ActionEvent ae) {
           
           BorrarPantalla();
            
           RB_Seleccionado=1;
           vista.tituloGrupo_DUO.setEnabled(true);
           vista.comboGrupos_DUO.setEnabled(true);
           vista.tituloGrupo_SOLO.setEnabled(false);
           vista.comboGrupos_SOLO.setEnabled(false);
           vista.tituloArticulo_SOLO.setEnabled(false);
           vista.comboArticulos_SOLO.setEnabled(false);

        }
  
  }//Fin Oyente RadioBoton_DUO
    
 //=============================================================      
       
    class OyenteRadioButGrupo_SOLO implements ActionListener {    

        @Override
        public void actionPerformed(ActionEvent ae) {
            
            BorrarPantalla();
            
           RB_Seleccionado=2;
            vista.tituloGrupo_SOLO.setEnabled(true);
           vista.comboGrupos_SOLO.setEnabled(true);
           vista.tituloGrupo_DUO.setEnabled(false);
           vista.comboGrupos_DUO.setEnabled(false);
           vista.tituloArticulo_DUO.setEnabled(false);
           vista.comboArticulos_DUO.setEnabled(false);
           vista.tituloArticulo_SOLO.setEnabled(false);
           vista.comboArticulos_SOLO.setEnabled(false);
           RefrescarComboboxGrupos_SOLO();
        }
  
  }//Fin Oyente RadioBoton_GRUPO_SOLO  
       
 //=============================================================           
           
    class OyenteRadioButArticulo_SOLO implements ActionListener {    

        @Override
        public void actionPerformed(ActionEvent ae) {
            
            BorrarPantalla();
            
           RB_Seleccionado=3;
           vista.tituloArticulo_SOLO.setEnabled(true);
           vista.comboArticulos_SOLO.setEnabled(true);
           vista.tituloGrupo_DUO.setEnabled(false);
           vista.comboGrupos_DUO.setEnabled(false);
           vista.tituloArticulo_DUO.setEnabled(false);
           vista.comboArticulos_DUO.setEnabled(false);
           vista.tituloGrupo_SOLO.setEnabled(false);
           vista.comboGrupos_SOLO.setEnabled(false);
           RefrescarComboboxArticulos_SOLO();
        }
  
  }//Fin Oyente RadioBoton_ARTICULO_SOLO    
 
 //////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////
 //////////////////CREANDO OYENTES DE COMBOBOX////////////////////////////// 
 //////////////////////////////////////////////////////////////////////
//=================================================
    class OyenteComboGrupos_DUO implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            
            //Recogemos el INDICE donde se encuentra, para luego poder volver a recuperarlo
            grupoSeleccionado = vista.comboGrupos_DUO.getSelectedIndex();
            vista.textArea.setText("");
            DesbloqueoDeOpcionesArticulo_DUO();
            DesbloqueoDeOpcionesGrupo_DUO();
      
            
            
            RefrescarCombobox_DUO();
        }//Fin de la accion sobreescrita

    }//Fin del oyenteInsertarDescripcion   

//=================================================
//=================================================
    class OyenteComboArticulos_DUO implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
             
            articuloSeleccionado = vista.comboArticulos_DUO.getSelectedIndex();
            vista.textArea.setText("");
            DesbloqueoDeOpcionesArticulo_DUO();
     
           

        }//Fin de la accion sobreescrita

    }//Fin del oyenteInsertarDescripcion   
    
//=================================================
//=================================================
    class OyenteComboGrupos_SOLO implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            
            grupoSeleccionado = vista.comboGrupos_SOLO.getSelectedIndex();
             vista.textArea.setText("");
            DesbloqueoDeOpcionesCombobox_SOLO();  
        }
    
    }//Find el oyente ComboGRUPOS_SOLO
            
  //=================================================
//=================================================          
            
    class OyenteComboArticulos_SOLO implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            articuloSeleccionado = vista.comboArticulos_SOLO.getSelectedIndex();
             vista.textArea.setText("");
            DesbloqueoDeOpcionesCombobox_SOLO();  
        }
     }//Fin del oyente ComboARTICULOS_SOLO
    
//=================================================

//=================================================
  ////////////////////////////////////////////////////////////////////////////////    
//////////////////Declarando OYENTES DE LOS BOTONES PARA REALIZAR ACCIONES //////////////////////////////////////////////
    class OyenteConsultarDescripcion implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
                
            vista.textArea.setText("");
            //Dependiendo del radioButon elegido, se realizará una consulta u otra 
            //Pero sea cual sea, recogeremos su salida en un treemap y lo imprimiremos en orden
           if(RB_Seleccionado==1){
                                    
                ListaObjetosDescripcion =metodos.LecturaDescripcion_DUO(vista.comboGrupos_DUO.getSelectedItem().toString(),vista.comboArticulos_DUO.getSelectedItem().toString()); //Poner aqui consulta con grupos y articulos 
                 
                vista.textArea.append(vista.comboGrupos_DUO.getSelectedItem().toString().toUpperCase()+"\n########################################################\n");
                vista.textArea.append(vista.comboArticulos_DUO.getSelectedItem().toString().toUpperCase()+"\n========================================================\n\n");
                
            }else if(RB_Seleccionado==2){
                
                 ListaObjetosDescripcion =metodos.LecturaDescripcionGrupo_SOLO(vista.comboGrupos_SOLO.getSelectedItem().toString());//Poner aqui consulta con grupos y articulos //Poner aqui consulta con grupos
                vista.textArea.append(vista.comboGrupos_SOLO.getSelectedItem().toString().toUpperCase()+"\n########################################################\n\n");
                 
            }else if (RB_Seleccionado==3){
                
                ListaObjetosDescripcion =metodos.LecturaDescripcionArticulo_SOLO(vista.comboArticulos_SOLO.getSelectedItem().toString()); //Poner aqui consulta con grupos y articulos //Poner aqui consulta con grupos
                vista.textArea.append(vista.comboArticulos_SOLO.getSelectedItem().toString().toUpperCase()+"\n########################################################\n\n");//Poner aqui consulta con  articulos
                         
            }
         
         //Hacemos que se ordene el TreeSet con el comparador que trae el objeto (Que ordenará según el ID)
         ListaObjetosDescripcion.comparator();
            for (objetoTablaDescripcion obj : ListaObjetosDescripcion) {
                
                if (RB_Seleccionado==2){
                    vista.textArea.append(obj.getArticulo().toUpperCase()+"\n========================================================\n");//Poner aqui consulta con  articulos
                }else if (RB_Seleccionado==3){
                    vista.textArea.append(obj.getGrupo().toUpperCase()+"\n========================================================\n");//Poner aqui consulta con  articulos
         
                }
                
                
                vista.textArea.append(/*obj.getID()+" "+*/obj.getDescripcion());
                
            //AñAdiremos un separador al final de cada entrada recogida
                vista.textArea.append("\n\n-----------------------------------------------------------------\n\n");
            
            }
            
            
            //Se Comprueba el TextArea y si se ha recibido algo, 
            //Desbloqueamos el botón para EXPORTAR LA CONSULTA
            if (!vista.textArea.getText().trim().equalsIgnoreCase("")){
                
                vista.BotonExportarConsulta.setEnabled(true);
            }
            
        }//Fin de la accion sobreescrita

    }//Fin del oyenteInsertarDescripcion  

//=================================================
//=================================================
    class OyenteLimpiarPantalla implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            
            BorrarPantalla();

        }//Fin de la accion sobreescrita

    }//Fin del oyenteInsertarDescripcion  

//=================================================
//=================================================
    class OyenteSalir implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            //Hacemos que se cierre la ventana, SIN DETENER LA EJECUCIÓN DEL PROGRAMA
            vista.dispose();
            //Deseleccionamos el CheckBox correspondiente del MENU-BAR
            PanelMenuBar.consultar.setSelected(false);

        }//Fin de la accion sobreescrita

    }//Fin del oyenteInsertarDescripcion   

    //=================================================
    
   
    
    ////////////////////////////////////////////////////////////////////////////////    
///////////////////  POP-UP MENU  //////////////////////////////////////////////
////////////CONFIGURARÉ LOS OYENTES DEL JPOPUPMENU EN ESTA MISMA CLASE//////////   
    ////////////////////////////////////////////////////////////////////////////////   
    class OyentePopRehacer implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            if (vista.manager.canRedo()) {
                vista.manager.redo();
            }
        }//Fin action performed
    }//Fin del OyenteCOPIAR

    ////////////////////////////////////////////////////////////////////////////////   
    class OyentePopDeshacer implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //ESCRIBIR CÓDIGO DEL BOTÓN AQUÍ
            if (vista.manager.canUndo()) {
                vista.manager.undo();
            }

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



  
}//Fin de la clase CONTROLADOR-CONSULTAR
