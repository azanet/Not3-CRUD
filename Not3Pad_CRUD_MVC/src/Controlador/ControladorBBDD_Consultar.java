/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.MetodosBBDD_Consultas;
import Vistas.PanelMenuBar;
import Vistas.VistaBBDD_Consultar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import javax.swing.JOptionPane;

/**
 *
 * @author davidf
 */
public class ControladorBBDD_Consultar {
    
    private final VistaBBDD_Consultar vista; 
    private final MetodosBBDD_Consultas metodos;

     private String[] grupos;
    private String[] articulos;
    
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

      vista.BotonConsultarDescripcion.addActionListener(new OyenteConsultarDescripcion());

        vista.BotonlimpiarPantalla.addActionListener(new OyenteLimpiarPantalla());
        vista.Botonsalir.addActionListener(new OyenteSalir());

        vista.comboGrupos_DUO.addActionListener(new OyenteComboGrupos());
        vista.comboArticulos_DUO.addActionListener(new OyenteComboArticulos());

        //Cargamos un elemento en el COMBOBOX grupo, y el LISTENER se encargará de lanzar el resto de metodos necesarios para ir cargando los comboboxes en orden para evitar errores 
        vista.comboArticulos_DUO.addItem("============>");
        vista.comboGrupos_DUO.addItem("============>");
        vista.comboGrupos_SOLO.addItem("============>");
        vista.comboArticulos_SOLO.addItem("============>");
  
    }//Fin del metodo INICIAR
    
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
        

    
    }//Fin de REFRESCAR

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++        
    //Este metodo será utilizado para saber si DESBLOQUEAR O NO las opciones siguientes para la consulta
    //ACCIONES NECESARIAS PARA IR DESBLOQUEANDO LOS SIGUIENTES COMBOBOXES
    //PARA DE ESTA FORMA, OBLIGAR AL USUARIO A QUE HAGA LA CONSULTA CORRECTAMENTE
   
    private void DesbloqueoDeOpcionesGrupo() {

        if (grupoSeleccionado > 0) {
            //DESBLOQEUAR CAMPO DE ARTICULO

//OPCIONAL+++Poner el comboBox de articulo A NINGUN SELECCIONADO
 
            vista.tituloArticulo_DUO.setEnabled(true);
            vista.comboArticulos_DUO.setEnabled(true);
     //       vista.tituloArticulo_SOLO.setEnabled(true);

        } else {

            vista.tituloArticulo_DUO.setEnabled(false);
            vista.comboArticulos_DUO.setEnabled(false);
   //         vista.tituloArticulo_SOLO.setEnabled(false);

            vista.tituloDescripcion.setEnabled(false);
            vista.BotonConsultarDescripcion.setEnabled(false);
            vista.textArea.setEnabled(false);

        }//Fin del else

    }

    private void DesbloqueoDeOpcionesArticulo() {

        if (articuloSeleccionado > 0) {

            //DESBLOQEUAR CAMPO DE DESCRIPCION
    //        vista.tituloArticulo_SOLO.setEnabled(false);
            vista.tituloDescripcion.setEnabled(true);
            vista.BotonConsultarDescripcion.setEnabled(true);
            vista.textArea.setEnabled(true);

         
         } else if (articuloSeleccionado == 0 && grupoSeleccionado == 0) {
            //BLOQEUAR CAMPO DE DESCRIPCION
      //      vista.tituloArticulo_SOLO.setEnabled(false);
            vista.tituloDescripcion.setEnabled(false);
            vista.BotonConsultarDescripcion.setEnabled(false);
            vista.textArea.setEnabled(false);
        }else {
            //BLOQEUAR CAMPO DE DESCRIPCION
  //          vista.tituloArticulo_SOLO.setEnabled(true);
            vista.tituloDescripcion.setEnabled(false);
            vista.BotonConsultarDescripcion.setEnabled(false);
            vista.textArea.setEnabled(false);//Fin del else
         }
    }//FIN DE DESBLOQUEO-OPCIONES

    ////////////////////////////////////////////////////////////////////////////////    
//////////// OYENTES DE BOTONES y COMBOBOX de VISTA-INSERTAR//////////   
    //////////////////////////////////////////////////////////////////////////////// 
 
    //=================================================

//=================================================
    class OyenteConsultarDescripcion implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            if (vista.textArea.getText().trim().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(vista, "El campo NO puede estar VACÍO", "ERROR", JOptionPane.WARNING_MESSAGE);
            } else {
                // Realizamos la CONSULTA, enviando el texto del TextArea, y recuperando el NOMBRE del ONJETO SELECCIONADO EN LOS COMBOBOXes
                String resultado = metodos.AltaDescripcion(vista.comboGrupos_DUO.getSelectedItem().toString(), vista.comboArticulos_DUO.getSelectedItem().toString(), vista.textArea.getText());
                //Comprobamos que lo que hemos recogido de la función, y si está vacío sabemos que es un error(lo programe yo)
                if (resultado.equals("")) {
                    JOptionPane.showMessageDialog(vista, "Error en la consulta", "ERROR", JOptionPane.ERROR_MESSAGE);

                } else {

                    JOptionPane.showMessageDialog(vista, resultado, "Artículo dado de Alta", JOptionPane.INFORMATION_MESSAGE);

                }

                vista.textArea.setText("");
 //               vista.comboArticulos_DUO.setSelectedIndex(0);
  //              vista.comboGrupos_DUO.setSelectedIndex(0);
            }//Fin del IF-ELSE

        }//Fin de la accion sobreescrita

    }//Fin del oyenteInsertarDescripcion  

//=================================================
//=================================================
    class OyenteLimpiarPantalla implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            vista.textArea.setText("");
            vista.comboArticulos_DUO.setSelectedIndex(0);
            vista.comboGrupos_DUO.setSelectedIndex(0);
            vista.comboGrupos_SOLO.setSelectedIndex(0);
            vista.comboArticulos_DUO.setSelectedIndex(0);

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
//=================================================
    class OyenteComboGrupos implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            //Recogemos el INDICE donde se encuentra, para luego poder volver a recuperarlo
            grupoSeleccionado = vista.comboGrupos_DUO.getSelectedIndex();
           
            DesbloqueoDeOpcionesArticulo();
            DesbloqueoDeOpcionesGrupo();
          //  DesbloqueoDeOpcionesArticulo();
            
            
            RefrescarCombobox_DUO();
        }//Fin de la accion sobreescrita

    }//Fin del oyenteInsertarDescripcion   

//=================================================
//=================================================
    class OyenteComboArticulos implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            articuloSeleccionado = vista.comboArticulos_DUO.getSelectedIndex();
            
            DesbloqueoDeOpcionesArticulo();
          //  DesbloqueoDeOpcionesGrupo();
           

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
