/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Vistas.Panel_Pestanias;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.swing.JFileChooser;
import static javax.swing.JFileChooser.FILES_ONLY;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

/**
 *
 * @author davidf
 */
public class MetodosPrincipal {

    public MetodosPrincipal() {
    }

    //Metodo Abrir
    public File AbrirArchivo(File archivo) throws IOException {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(FILES_ONLY);
        try {
            if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
                archivo = fileChooser.getSelectedFile();

                return archivo;

            }
        } catch (HeadlessException e) {
            e.getMessage();
        }
        return archivo;

    }//FFin del metodo ABRIR

    ////////////////////////////////////////////////////////////////////////////////  
    public File Guardar(File fichero, String texto) {

        //Crea un flujo de caracteres para grabar
        try {
            FileWriter flujo = new FileWriter(fichero); //Podriamos dejarle true para que seguiera escribiendo debajo de este si existiera, (aunqeu lo estoy eliminando mas arriba pòrque no es el caso)
            try (PrintWriter escritor = new PrintWriter(flujo)) {
                escritor.println(texto);
                escritor.close();
                flujo.close();

                return fichero;

            }
        } catch (IOException ex) {
            ex.getMessage();
        }
        return fichero;
    }//Fin del metodo GUARDAR

    ////////////////////////////////////////////////////////////////////////////////     
    public File GuardarComo(File archivo, String texto) throws IOException {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(FILES_ONLY);

        try {
            if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {

                archivo = fileChooser.getSelectedFile();

                try (FileWriter escritor = new FileWriter(archivo)) {
                    escritor.write(texto);
                }

            } else {
                throw new ArithmeticException();
            }
        } catch (IOException e) {
            e.getMessage();
            //   escritor.close();

        }

        return archivo;

    }//FFin del metodo guardar como

////////////////////////////////////////////////////////////////////////////////    
    public String Renombrar(String texto) {
        File fichero_rename = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(FILES_ONLY);
        //       nombre_fichero = fileChooser.getSelectedFile();

        //Utilizaremos un DIALOGO, un poco personalizado, en el que se puede cambiar el nombre del Botón y Título
        if (fileChooser.showDialog(fileChooser, "Renombrar") == JFileChooser.APPROVE_OPTION) {
            //creamos el objeto File, con el nombre   que queremos ponerle a nuestro Archivo. y luego renombraremos nuestro archivo al nombre de este que hemos creado   
            fichero_rename = fileChooser.getSelectedFile();
            fichero_rename = Guardar(fichero_rename, texto); //Y creamos el archivo con el nombre nuevo pasandole el TEXTOcontenido del archivo que existia antes

            //Este metodo. integrado en el objeto PanelTextArea, elimina el archivo existente y abre el archivo creado anteriormente  
        } else {
            throw new ArithmeticException();
        }
        return fichero_rename.getPath();
    }

////////////////////////////////////////////////////////////////////////////////   
    //Metodo que utiliza IR A, para determinar a donde se tiene que desplazar el puntero
    public int getLineStartIndex(JTextComponent textComp, int lineNumber) {
        if (lineNumber == 0) {
            return 0;
        }

// Gets the current line number start index value for
// the supplied text line.
        try {
            JTextArea jta = (JTextArea) textComp;
            return jta.getLineStartOffset(lineNumber - 1);
        } catch (BadLocationException ex) {
            return -1;
        }
    }

////////////////////////////////////////////////////////////////////////////////   
    public LocalDateTime InsertarFecha() {
        LocalDate hoy = LocalDate.now();
        LocalTime ahora = LocalTime.now();

        LocalDateTime fecha = LocalDateTime.of(hoy, ahora);
        return fecha;
    }

////////////////////////////////////////////////////////////////////////////////         
}//Fin de METODOS PRINCIPAL
