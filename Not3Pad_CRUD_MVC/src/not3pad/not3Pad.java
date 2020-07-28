/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package not3pad;

import Controlador.ControladorVistaPrincipal;
import Modelo.MetodosPrincipal;
import Vistas.VistaPrincipal;
import javax.swing.JFrame;

/**
 *
 * @author davidf
 */
public class not3Pad {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        VistaPrincipal vistaPrincipal = new VistaPrincipal();
        MetodosPrincipal metodosPrincipal =new MetodosPrincipal();
        ControladorVistaPrincipal controladorPrincipal;
        controladorPrincipal= new ControladorVistaPrincipal(vistaPrincipal, metodosPrincipal);
        vistaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }//Fin del bloque Main
    
}//Fin de la clase principal
