/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import javax.swing.JTabbedPane;

/**
 *
 * @author davidf
 * ESTE OBJETO, SERÁ UTILIZADO PARA ALMACENAR  LA CONFIGURACIÓN 
 * QUE HAYA SETEADO EL USUARIO COMO PREDETERMINADO Y GUARDADO: (COLORES, ESTILO DE FUENTE, TAMAÑO, ETC. )
 * 
 */
public class Configuracion implements Serializable {
 //   private JTabbedPane TP_Almacenado;
    private Font Fuente_Almacenada;
    private Color background;
    private Color letra;
    private Color seleccion;
    private Color textoSeleccionado;

    public Configuracion( Font Fuente_Almacenada, Color background, Color letra, Color seleccion, Color textoSeleccionado) {
     //   this.TP_Almacenado = TP_Almacenado;
        this.Fuente_Almacenada = Fuente_Almacenada;
        this.background = background;
        this.letra = letra;
        this.seleccion = seleccion;
        this.textoSeleccionado = textoSeleccionado;
    }//Fin del constructor



    public Font getFuente_Almacenada() {
        return Fuente_Almacenada;
    }

    public void setFuente_Almacenada(Font Fuente_Almacenada) {
        this.Fuente_Almacenada = Fuente_Almacenada;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public Color getLetra() {
        return letra;
    }

    public void setLetra(Color letra) {
        this.letra = letra;
    }

    public Color getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(Color seleccion) {
        this.seleccion = seleccion;
    }

    public Color getTextoSeleccionado() {
        return textoSeleccionado;
    }

    public void setTextoSeleccionado(Color textoSeleccionado) {
        this.textoSeleccionado = textoSeleccionado;
    }
    
    
    
    
    
    
}
