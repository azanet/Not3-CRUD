/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author davidf
 */
public class MetodosBBDD_Consultas {
    //Declaramos OBJETO DE CLASE CONECTOR
    private Connection con;
    private ResultSet rs;
   //PreparedStatement s;
    
    
   //INICIALIZAMOS EL OBJETO EN EL CONSTRUCTOR (a travez de la llamada a una funcion privada,por seguridad)
     public MetodosBBDD_Consultas() {
          Conexion1();
    }
     

    private void Conexion1() {
        try {
            //LE INDICAMOS CUAL ES EL DRIVER
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Con este objeto de Connection y utilizando "getConnection" que lleva 3 parametros, le indicamos la bbdd a conectar, usuario y contraseña
            String url = "jdbc:mysql://LOCALHOST:3306/NOMBRE_DE_LA_BBDD";//?verifyServerCertificate=false&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            this.con = DriverManager.getConnection(url, "usuario", "contraseña");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, ("Error " + e), "Failure", JOptionPane.ERROR_MESSAGE);
        }//Fin del try catch

    }//Fin del CONSTRUCTOR
    

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    
  //METODO BOTON ALTA GRUPO (publico y privado)  
    public String AltaGrupo (String nom_Grupo){
              
            return  AltaGrupoH(nom_Grupo);
       }//Fin alta artículo
    
    
    private String AltaGrupoH(String nom_Grupo) {
        // Preparamos la consulta
        String resultado = "";
        try {

            PreparedStatement s1 = this.con.prepareStatement("INSERT INTO `Grupos` (`Nom_Grupo`) VALUES (?)");               //Agregando parametros a la posicion correspondiente de la consulta
            s1.setString(1, nom_Grupo);
         

            if (s1.executeUpdate() == 0) {
                JOptionPane.showMessageDialog(null, "No se pudo realizar el ALTA, compruebe que los datos están correctos", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                resultado = "GRUPO DADO DE ALTA con éxito:\n\nNombre Grupo: " + nom_Grupo;
             }
            //CERRAMOS CONEXION
            s1.close();
            
        } catch (Exception e) {
            resultado = "Error, " + e.getMessage();
        }//Fin del try catch

        return resultado;

    }//Fin metodo AltaGrupoH
    
    
    
         
       public String[] LecturaGrupos ()throws SQLException {
       
     
        return  LecturaGruposH();
   }
  
    
  
  private String[] LecturaGruposH() throws SQLException {

        //Recolectaremos los datos en un ARRAYLIST porque es mas facil y limpio para recolectar los datos
        //antes de retornar la lista, la pasaremos a ARRAY y la almacenaremos en su Array correspondiente en el controlador
        ArrayList<String> lista = new ArrayList<>();
        

        try {
            PreparedStatement s1 = this.con.prepareStatement("SELECT * FROM `Grupos`"); //Agregando parametros a la posicion correspondiente de la consulta
                rs = s1.executeQuery();
               
                
         
                while (rs.next()) {
                    //Creamos objeto Albarán DETALLE
                 //   art = new Albaran(rs.getString("numero_albaran"), rs.getString("codigo_articulo"), rs.getString("fecha_venta"), rs.getInt("cantidad"), rs.getDouble("precio"), rs.getFloat("descuento"));
                       lista.add(rs.getString("Nom_Grupo"));     
                     
                }
                
                //Retornamos la lista recolectada, pero antes la transformaremos a ARRAY
                String[] retorno= new String[lista.size()];
                lista.toArray(retorno);
                return retorno;
                
            
            

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Se produjo un error al realizar la consulta", "Error", JOptionPane.ERROR_MESSAGE);
            //Retornamos la lista recolectada, pero antes la transformaremos a ARRAY
            String[] retorno= new String[lista.size()];
            lista.toArray(retorno);
            return retorno;
        }//Fin del try catch

 
    }
    

    
    
    
    
    
    
    
    
    
}//Fin clase METODO_CONSULTAS
