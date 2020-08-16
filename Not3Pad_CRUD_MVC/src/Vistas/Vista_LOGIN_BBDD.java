/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author davidf
 */
public class Vista_LOGIN_BBDD extends JDialog {

    private JPanel panelLogin;
    private JLabel TituloLogin, LabUrl, LabPuerto, LabNameBBDD, LabUsuario, LabPass;
    private JTextField EntradaUrl, EntradaPuerto, EntradaNameBBDD, EntradaUsuario;
    private JPasswordField EntradaPass;
    public JButton BotonAceptar, BotonCancelar;

    private String url;
    private String puerto;
    private String nameBBDD;
    private String usuario;
    private String pass;

    //Constructor del login, NO DEJARÁ HACER OTRA ACCION HASTA SALIR DE ESTA VENTANA
    public Vista_LOGIN_BBDD(Frame owner, boolean modal) {
        super(owner, modal);

        this.url = Controlador.ControladorVistaPrincipal.BBDDurl;
        this.puerto = Controlador.ControladorVistaPrincipal.BBDDpuerto;
        this.nameBBDD = Controlador.ControladorVistaPrincipal.BBDDname;
        this.usuario = Controlador.ControladorVistaPrincipal.BBDDusuario;
        this.pass = Controlador.ControladorVistaPrincipal.BBDDpass;

        Iniciar();

    }//Fin del constructor

    private void Iniciar() {

        panelLogin = new JPanel();
        panelLogin.setLayout(null);

        TituloLogin = new JLabel("--Introduzca DATOS para CONEXIÓN a la BBDD--");
        TituloLogin.setBounds(65, 10, 290, 30);
        panelLogin.add(TituloLogin);

        LabUrl = new JLabel("URL:");
        LabUrl.setBounds(20, 70, 50, 30);
        panelLogin.add(LabUrl);
        EntradaUrl = new JTextField(url);
        EntradaUrl.setBounds(55, 70, 340, 30);
        panelLogin.add(EntradaUrl);

        LabPuerto = new JLabel("PUERTO:");
        LabPuerto.setBounds(10, 120, 60, 30);
        panelLogin.add(LabPuerto);
        EntradaPuerto = new JTextField(puerto);
        EntradaPuerto.setBounds(65, 120, 70, 30);
        panelLogin.add(EntradaPuerto);

        LabNameBBDD = new JLabel("Nombre BBDD:");
        LabNameBBDD.setBounds(150, 120, 100, 30);
        panelLogin.add(LabNameBBDD);

        EntradaNameBBDD = new JTextField(nameBBDD);
        EntradaNameBBDD.setBounds(245, 120, 160, 30);
        panelLogin.add(EntradaNameBBDD);

        LabUsuario = new JLabel("USUARIO:");
        LabUsuario.setBounds(85, 200, 70, 30);
        panelLogin.add(LabUsuario);

        EntradaUsuario = new JTextField(usuario);
        EntradaUsuario.setBounds(150, 200, 200, 30);
        panelLogin.add(EntradaUsuario);

        LabPass = new JLabel("CONTRASEÑA:");
        LabPass.setBounds(60, 250, 100, 30);
        panelLogin.add(LabPass);

        EntradaPass = new JPasswordField(pass);
        EntradaPass.setBounds(150, 250, 200, 30);
        panelLogin.add(EntradaPass);

        BotonAceptar = new JButton("Aceptar");
        BotonAceptar.setBounds(90, 300, 100, 30);
        panelLogin.add(BotonAceptar);

        BotonCancelar = new JButton("Cancelar");
        BotonCancelar.setBounds(230, 300, 100, 30);
        panelLogin.add(BotonCancelar);

        //Agregamos aquí los OYENTES DE LOS BOTONES "ACEPTAR" y "CANCELAR"
        BotonAceptar.addActionListener(new OyenteAceptar());
        BotonCancelar.addActionListener(new OyenteCancelar());

        Container contentPane = getContentPane();
        contentPane.add(panelLogin, BorderLayout.CENTER);

        this.setMinimumSize(new Dimension(415, 380));
        setResizable(false);
        this.setLocationRelativeTo(null);
        setVisible(true);

    }//Fin del metodo Iniciar

    private class OyenteAceptar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Controlador.ControladorVistaPrincipal.BBDDurl = EntradaUrl.getText();
            Controlador.ControladorVistaPrincipal.BBDDpuerto = EntradaPuerto.getText();
            Controlador.ControladorVistaPrincipal.BBDDname = EntradaNameBBDD.getText();
            Controlador.ControladorVistaPrincipal.BBDDusuario = EntradaUsuario.getText();
            Controlador.ControladorVistaPrincipal.BBDDpass = EntradaPass.getText();
            Controlador.ControladorVistaPrincipal.BBDDaceptar = true;
            dispose();

        }

    }//Fin Oyente ACEPTAR

    private class OyenteCancelar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Controlador.ControladorVistaPrincipal.BBDDaceptar = false;
            dispose();
        }

    }//Fin oyente CANCELAR

}//Fin de la clase Vista LOGIN
