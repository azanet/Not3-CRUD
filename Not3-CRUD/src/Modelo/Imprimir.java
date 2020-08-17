/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;


/**
 *
 * @author davidf
 */
public class Imprimir {

    
    
    public Imprimir(String nombre_archivo) throws IOException{
    
    
    
    
FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(nombre_archivo);
        } catch (FileNotFoundException e) {
        }
        if (inputStream == null) {
            return;
        }
 
        DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
        Doc document = new SimpleDoc(inputStream, docFormat, null);
 
        PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();
 
        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
 
  
 
        if (defaultPrintService != null) {
            DocPrintJob printJob = defaultPrintService.createPrintJob();
            try {
                printJob.print(document, attributeSet);
 
            } catch (PrintException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.err.println("No existen impresoras instaladas");
        }
 
        inputStream.close();
    
    
    
    }
}

