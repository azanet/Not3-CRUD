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
import javax.swing.JOptionPane;

/**
 *
 * @author davidf
 *
 * DESCRIPCION: Este panel "guardará" las opciones en el caso que se cierre la
 * pestaña para ello he implementado metodos y variables para cuando vuelva
 * abrir y se ejecute el controlador cargue una serie de variables que le
 * ayudarán a estar como cuando se cerro la pestaña. Si se cierra el programa si
 * se eliminará el contenido de la ventana.
 *
 */
public class ControladorBBDD_Insertar {

    private VistaBBDD_Insertar vista;
    private MetodosBBDD_Consultas metodos;

    //Estas variables almacenarán los grupos y articulos que existan en la BBDD
    private String[] grupos;
    private String[] articulos;

    public int grupoSeleccionado = 0;
    public int articuloSeleccionado = 0;

    public ControladorBBDD_Insertar(VistaBBDD_Insertar vista) {

        this.vista = vista;
        this.metodos = new MetodosBBDD_Consultas();

        Iniciar();

    }

    private void Iniciar() {

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

        //Cargamos un elemento en el COMBOBOX grupo, y el LISTENER se encargará de lanzar el resto de metodos necesarios para ir cargando los comboboxes en orden para evitar errores 
        vista.comboArticulos.addItem("============>");
        vista.comboGrupos.addItem("============>");
  
    }//Fin del metodo INICIAR

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++    
    //Este metodo será utilizado Para Refrescar los comboboxes 
    private void RefrescarCombobox() {

        //SETEANDO LOS COMBOBOX
        ///***CREAR CONSULTA PARA RECUPERAR GRPOS Y ARTICULOS Y ALMACENARLOS EN UN OBEJETO   
       
        grupos = metodos.LecturaGrupos();
        //Ejecutamos la CONSULTA "LecturaGrupos", la cual nos devolverá un array con los nombre de los grupos existentes
        //Y con esto inicializamos o actualizamos nustras combobox de Grupos

        if (grupoSeleccionado == 0) {
            vista.comboGrupos.removeAllItems();
            for (String grupo : grupos) {
                vista.comboGrupos.addItem(grupo);
            }//Fin del FOR Grupos
        //Revisaremos se hay alguna opcion elegida en GRUPO, si no la hay, no cargamos el combo de articulos
        
        }
        
             articulos = metodos.LecturaArticulos((String) vista.comboGrupos.getSelectedItem());
            vista.comboArticulos.removeAllItems();
        

              for (String articulo : articulos) {
                vista.comboArticulos.addItem(articulo);
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
            vista.tituloGrupoNuevo.setEnabled(false);
            vista.textFieldGrupoNuevo.setEnabled(false);
            vista.BotonAltaGrupo.setEnabled(false);
            vista.tituloComboboxArticulo.setEnabled(true);
            vista.comboArticulos.setEnabled(true);
            vista.tituloArticuloNuevo.setEnabled(true);
            vista.textFieldArticuloNuevo.setEnabled(true);
            vista.BotonAltaArticulo.setEnabled(true);

        } else {

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

    private void DesbloqueoDeOpcionesArticulo() {

        if (articuloSeleccionado > 0) {

            //DESBLOQEUAR CAMPO DE DESCRIPCION
            vista.tituloArticuloNuevo.setEnabled(false);
            vista.textFieldArticuloNuevo.setEnabled(false);
            vista.BotonAltaArticulo.setEnabled(false);
            vista.tituloDescripcion.setEnabled(true);
            vista.BotonagregarDescripcion.setEnabled(true);
            vista.textArea.setEnabled(true);

         
         } else if (articuloSeleccionado == 0 && grupoSeleccionado == 0) {
            //BLOQEUAR CAMPO DE DESCRIPCION
            vista.tituloArticuloNuevo.setEnabled(false);
            vista.textFieldArticuloNuevo.setEnabled(false);
            vista.BotonAltaArticulo.setEnabled(false);
            vista.tituloDescripcion.setEnabled(false);
            vista.BotonagregarDescripcion.setEnabled(false);
            vista.textArea.setEnabled(false);
        }else {
            //BLOQEUAR CAMPO DE DESCRIPCION
            vista.tituloArticuloNuevo.setEnabled(true);
            vista.textFieldArticuloNuevo.setEnabled(true);
            vista.BotonAltaArticulo.setEnabled(true);
            vista.tituloDescripcion.setEnabled(false);
            vista.BotonagregarDescripcion.setEnabled(false);
            vista.textArea.setEnabled(false);//Fin del else
         }
    }//FIN DE DESBLOQUEO-OPCIONES

    ////////////////////////////////////////////////////////////////////////////////    
//////////// OYENTES DE BOTONES y COMBOBOX de VISTA-INSERTAR//////////   
    //////////////////////////////////////////////////////////////////////////////// 
    class OyenteAltaGrupo implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            if (vista.textFieldGrupoNuevo.getText().trim().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(vista, "El campo NO puede estar VACÍO", "ERROR", JOptionPane.WARNING_MESSAGE);
            } else {
                //   String resultado = metodos.AltaGrupo(vista.textFieldGrupoNuevo.getText());
                String resultado = metodos.AltaGrupo(vista.textFieldGrupoNuevo.getText());
                //Comprobamos que lo que hemos recogido de la función, y si está vacío sabemos que es un error(lo programe yo)
                if (resultado.equals("")) {
                    JOptionPane.showMessageDialog(vista, "Error en la consulta", "ERROR", JOptionPane.ERROR_MESSAGE);

                } else {

                    JOptionPane.showMessageDialog(vista, resultado, "Grupo dado de Alta", JOptionPane.INFORMATION_MESSAGE);

                }
                RefrescarCombobox();
                vista.textArea.setText("");
                vista.textFieldGrupoNuevo.setText("");
                vista.textFieldArticuloNuevo.setText("");
                vista.comboArticulos.setSelectedIndex(0);
                vista.comboGrupos.setSelectedIndex(0);

            }//Fin del IF-ELSE

        }//Fin de la accion sobreescrita

    }//Fin del oyenteAltaGrupo
//=================================================

//=================================================
    class OyenteAltaArticulo implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            if (vista.textFieldArticuloNuevo.getText().trim().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(vista, "El campo NO puede estar VACÍO", "ERROR", JOptionPane.WARNING_MESSAGE);
            } else {

                String resultado = metodos.AltaArticulo(vista.comboGrupos.getSelectedItem().toString(), vista.textFieldArticuloNuevo.getText());
                //Comprobamos que lo que hemos recogido de la función, y si está vacío sabemos que es un error(lo programe yo)
                if (resultado.equals("")) {
                    JOptionPane.showMessageDialog(vista, "Error en la consulta", "ERROR", JOptionPane.ERROR_MESSAGE);

                } else {

                    JOptionPane.showMessageDialog(vista, resultado, "Artículo dado de Alta", JOptionPane.INFORMATION_MESSAGE);

                }

                RefrescarCombobox();
                vista.textArea.setText("");
                vista.textFieldGrupoNuevo.setText("");
                vista.textFieldArticuloNuevo.setText("");
                vista.comboArticulos.setSelectedIndex(0);
                vista.comboGrupos.setSelectedIndex(0);
            }//Fin el if-else

        }//Fin de la accion sobreescrita

    }//Fin del oyenteAltaArticulo
    //=================================================

//=================================================
    class OyenteInsertarDescripcion implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            if (vista.textArea.getText().trim().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(vista, "El campo NO puede estar VACÍO", "ERROR", JOptionPane.WARNING_MESSAGE);
            } else {
                // Realizamos la CONSULTA, enviando el texto del TextArea, y recuperando el NOMBRE del ONJETO SELECCIONADO EN LOS COMBOBOXes
                String resultado = metodos.AltaDescripcion(vista.comboGrupos.getSelectedItem().toString(), vista.comboArticulos.getSelectedItem().toString(), vista.textArea.getText());
                //Comprobamos que lo que hemos recogido de la función, y si está vacío sabemos que es un error(lo programe yo)
                if (resultado.equals("")) {
                    JOptionPane.showMessageDialog(vista, "Error en la consulta", "ERROR", JOptionPane.ERROR_MESSAGE);

                } else {

                    JOptionPane.showMessageDialog(vista, resultado, "Artículo dado de Alta", JOptionPane.INFORMATION_MESSAGE);

                }

                vista.textArea.setText("");
                vista.textFieldGrupoNuevo.setText("");
                vista.textFieldArticuloNuevo.setText("");
                vista.comboArticulos.setSelectedIndex(0);
                vista.comboGrupos.setSelectedIndex(0);
            }//Fin del IF-ELSE

        }//Fin de la accion sobreescrita

    }//Fin del oyenteInsertarDescripcion  

//=================================================
//=================================================
    class OyenteLimpiarPantalla implements ActionListener {

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
    class OyenteSalir implements ActionListener {

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
    class OyenteComboGrupos implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            //Recogemos el INDICE donde se encuentra, para luego poder volver a recuperarlo
            grupoSeleccionado = vista.comboGrupos.getSelectedIndex();
           
            DesbloqueoDeOpcionesArticulo();
            DesbloqueoDeOpcionesGrupo();
          //  DesbloqueoDeOpcionesArticulo();
            
            
            RefrescarCombobox();
        }//Fin de la accion sobreescrita

    }//Fin del oyenteInsertarDescripcion   

//=================================================
//=================================================
    class OyenteComboArticulos implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            articuloSeleccionado = vista.comboArticulos.getSelectedIndex();
            
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

}//Fin de la clase CONTROLADOR-INSERTAR
