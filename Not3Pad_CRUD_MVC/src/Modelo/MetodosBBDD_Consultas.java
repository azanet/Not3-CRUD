/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    //Crearemos esta variable statica, para comprobar desde cualquier parte del codigo si EXISTE UN ERROR EN LA CONEXION a la BBDD y si es así NOI PERMITIR realizar ninguna consulta
    static boolean BBDD_Error = false;

    //INICIALIZAMOS EL OBJETO EN EL CONSTRUCTOR (a travez de la llamada a una funcion privada,por seguridad)
    public MetodosBBDD_Consultas() {
        Conexion1();

    }

    private void Conexion1() {
        if (BBDD_Error == false) {
            try {
                //LE INDICAMOS CUAL ES EL DRIVER
                Class.forName("com.mysql.cj.jdbc.Driver");
                //Con este objeto de Connection y utilizando "getConnection" que lleva 3 parametros, le indicamos la bbdd a conectar, usuario y contraseña
                String url = "jdbc:mysql://localhost:3306/Not3Pad?verifyServerCertificate=false&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                 this.con = DriverManager.getConnection(url, "not3pad", "admin");//Nombre de Uuario(not3pad) y contraseña(admin) asignadas a la BBDD 
                BBDD_Error = false;

            } catch (ClassNotFoundException | SQLException e) {
                BBDD_Error = true;
                JOptionPane.showMessageDialog(null, ("Error " + e), "Failure", JOptionPane.ERROR_MESSAGE);
            }//Fin del try catch

        }
    }//Fin del CONSTRUCTOR

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //METODO BOTON ALTA GRUPO (publico y privado)  
    public String AltaGrupo(String nom_Grupo) {

        if (!BBDD_Error) {
            return AltaGrupoH(nom_Grupo);
        } else {
            return "";
        }

    }//Fin alta artículo

    private String AltaGrupoH(String nom_Grupo) {
        // Preparamos la consulta
        String resultado = "";
        try {

            try (PreparedStatement s1 = this.con.prepareStatement("INSERT INTO `Grupos` (`Nom_Grupo`) VALUES (?)") //Agregando parametros a la posicion correspondiente de la consulta
                    ) {
                s1.setString(1, nom_Grupo);
                if (s1.executeUpdate() == 0) {
                    JOptionPane.showMessageDialog(null, "No se pudo realizar el ALTA, compruebe que los datos están correctos", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    resultado = "GRUPO DADO DE ALTA con éxito:\n\nNombre Grupo: " + nom_Grupo;
                    return resultado;
                }
                //CERRAMOS CONEXION
            }

        } catch (HeadlessException | SQLException e) {

            JOptionPane.showMessageDialog(null, ("Error " + e), "Failure", JOptionPane.ERROR_MESSAGE);

        }//Fin del try catch

        return resultado;

    }//Fin metodo AltaGrupoH

    //////////////////////////////////////////////////////////////////////////////////  
    //METODO LECTURA GRUPOS (publico y privado)     
    public String[] LecturaGrupos() {

        if (!BBDD_Error) {
            return LecturaGruposH();
        } else {
            String[] lista_aux = {"============>"};
            return lista_aux;

        }

    }

    private String[] LecturaGruposH() {

        //Recolectaremos los datos en un ARRAYLIST porque es mas facil y limpio para recolectar los datos
        //antes de retornar la lista, la pasaremos a ARRAY y la almacenaremos en su Array correspondiente en el controlador
        ArrayList<String> lista = new ArrayList<>();
        lista.add("============>");

        try {
            try (PreparedStatement s1 = this.con.prepareStatement("SELECT * FROM `Grupos`") //Agregando parametros a la posicion correspondiente de la consulta
                    ) {
                rs = s1.executeQuery();

                while (rs.next()) {

                    lista.add(rs.getString("Nom_Grupo"));

                }
            }

            //Retornamos la lista recolectada, pero antes la transformaremos a ARRAY
            String[] retorno = new String[lista.size()];
            lista.toArray(retorno);
            return retorno;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Se produjo un error al realizar la consulta", "Error", JOptionPane.ERROR_MESSAGE);
            //Retornamos la lista recolectada, pero antes la transformaremos a ARRAY
            String[] retorno = new String[lista.size()];
            lista.toArray(retorno);
            return retorno;

        }//Fin del try catch

    }

//////////////////////////////////////////////////////////////////////////////////  
    //METODO BOTON ALTA ARTICULO (publico y privado)  
    public String AltaArticulo(String nom_Grupo, String nom_Articulo) {

        if (!BBDD_Error) {
            return AltaArticuloH(nom_Grupo, nom_Articulo);
        } else {
            return "";
        }

    }//Fin alta artículo

    private String AltaArticuloH(String nom_Grupo, String nom_Articulo) {
        // Preparamos la consulta
        String resultado = "";
        try {

            try (PreparedStatement s1 = this.con.prepareStatement("INSERT INTO `Articulos` (`Nom_Grupo`, `Nom_Articulo`) VALUES (?,?)") //Agregando parametros a la posicion correspondiente de la consulta
                    ) {
                s1.setString(2, nom_Articulo);
                s1.setString(1, nom_Grupo);
                if (s1.executeUpdate() == 0) {
                    JOptionPane.showMessageDialog(null, "No se pudo realizar el ALTA, compruebe que los datos están correctos", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    resultado = "Artículo: " + nom_Articulo + "\nDADO DE ALTA con éxito \nEn el Grupo: " + nom_Grupo;
                    return resultado;
                }
                //CERRAMOS CONEXION
            }

        } catch (HeadlessException | SQLException e) {

            JOptionPane.showMessageDialog(null, ("Error " + e), "Failure", JOptionPane.ERROR_MESSAGE);

        }//Fin del try catch

        return resultado;

    }//Fin metodo AltaGrupoH

//////////////////////////////////////////////////////////////////////////////////    
    //METODO LECTURA ARTICULOS (publico y privado)     
    public String[] LecturaArticulos(String nom_Grupo) {

        if (!BBDD_Error) {
            return LecturaArticulosH(nom_Grupo);
        } else {
            String[] lista_aux = {"============>"};
            return lista_aux;

        }

    }

    private String[] LecturaArticulosH(String nom_Grupo) {

        //Recolectaremos los datos en un ARRAYLIST porque es mas facil y limpio para recolectar los datos
        //antes de retornar la lista, la pasaremos a ARRAY y la almacenaremos en su Array correspondiente en el controlador
        ArrayList<String> lista = new ArrayList<>();
        lista.add("============>");

        try {
            try (PreparedStatement s1 = this.con.prepareStatement("SELECT Articulos.Nom_Articulo FROM Articulos WHERE Articulos.Nom_Grupo= ? ") //Agregando parametros a la posicion correspondiente de la consulta
                    ) {
                s1.setString(1, nom_Grupo);
                rs = s1.executeQuery();

                while (rs.next()) {

                    lista.add(rs.getString("Nom_Articulo"));
                }
            }
            //Retornamos la lista recolectada, pero antes la transformaremos a ARRAY
            String[] retorno = new String[lista.size()];
            lista.toArray(retorno);
            return retorno;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Se produjo un error al realizar la consulta", "Error", JOptionPane.ERROR_MESSAGE);
            //Retornamos la lista recolectada, pero antes la transformaremos a ARRAY
            String[] retorno = new String[lista.size()];
            lista.toArray(retorno);
            return retorno;
        }//Fin del try catch

    }//Fin de LECTURO ARTICULOS

//////////////////////////////////////////////////////////////////////////////////    
    //METODO BOTON ALTA DESCRIPCION (publico y privado)  
    public String AltaDescripcion(String nom_Grupo, String nom_Articulo, String descripcion) {

        if (!BBDD_Error) {
            return AltaDescripcionH(nom_Grupo, nom_Articulo, descripcion);
        } else {
            return "";
        }

    }//Fin alta artículo

    private String AltaDescripcionH(String nom_Grupo, String nom_Articulo, String descripcion) {
        // Preparamos la consulta
        String resultado = "";
        try {

            try (PreparedStatement s1 = this.con.prepareStatement("INSERT INTO `Descripcion` (`id`, `Nom_Grupo`, `Nom_Articulo`, `Texto`) VALUES (NULL, ?, ?, ?)") //Agregando parametros a la posicion correspondiente de la consulta
                    ) {
                s1.setString(3, descripcion);
                s1.setString(2, nom_Articulo);
                s1.setString(1, nom_Grupo);
                if (s1.executeUpdate() == 0) {
                    JOptionPane.showMessageDialog(null, "No se pudo realizar el ALTA, compruebe que los datos están correctos", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (descripcion.length() > 21) {
                        descripcion = descripcion.substring(0, 20);
                    }
                    resultado = "AGREGADA Descripción: " + descripcion + "..." + "\nEn el Articulo" + nom_Articulo + "\nDentro del Grupo: " + nom_Grupo;
                }
                //CERRAMOS CONEXION
            }

        } catch (HeadlessException | SQLException e) {

            JOptionPane.showMessageDialog(null, ("Error " + e), "Failure", JOptionPane.ERROR_MESSAGE);

        }//Fin del try catch

        return resultado;

    }//Fin metodo AltaGrupoH

//////////////////////////////////////////////////////////////////////////////////    
    //METODO LECTURA ARTICULOS (publico y privado)     
    public String[] LecturaArticulos_SOLO() {

        if (!BBDD_Error) {
            return LecturaArticulos_SOLO_H();
        } else {
            String[] lista_aux = {"============>"};
            return lista_aux;
        }
    }

    private String[] LecturaArticulos_SOLO_H() {

        //Recolectaremos los datos en un ARRAYLIST porque es mas facil y limpio para recolectar los datos
        //antes de retornar la lista, la pasaremos a ARRAY y la almacenaremos en su Array correspondiente en el controlador
        ArrayList<String> lista = new ArrayList<>();
        lista.add("============>");

        try {
            try (PreparedStatement s1 = this.con.prepareStatement("SELECT Articulos.Nom_Articulo FROM Articulos") //Agregando parametros a la posicion correspondiente de la consulta
                    ) {
                rs = s1.executeQuery();

                while (rs.next()) {

                    lista.add(rs.getString("Nom_Articulo"));
                }
            }
            //Retornamos la lista recolectada, pero antes la transformaremos a ARRAY
            String[] retorno = new String[lista.size()];
            lista.toArray(retorno);
            return retorno;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Se produjo un error al realizar la consulta", "Error", JOptionPane.ERROR_MESSAGE);
            //Retornamos la lista recolectada, pero antes la transformaremos a ARRAY
            String[] retorno = new String[lista.size()];
            lista.toArray(retorno);
            return retorno;
        }//Fin del try catch

    }//Fin de LECTURO ARTICULOS

////////////////////////////////////////////////////////////////////////////////// 
//METODO LECTURA TABLA DESCRIPCION por GRUPO y ARTICULO (publico y privado)     
    public TreeSet<objetoTablaDescripcion> LecturaDescripcion_DUO(String nomGrupo, String nomArticulo) {

        if (!BBDD_Error) {
            return LecturaDescripcion_DUO_H(nomGrupo, nomArticulo);
        } else {
            TreeSet<objetoTablaDescripcion> lista = new TreeSet<>();
            return lista;
        }
    }

    private TreeSet<objetoTablaDescripcion> LecturaDescripcion_DUO_H(String nomGrupo, String nomArticulo) {

        //Recolectaremos los datos en un TreeSet para que las consulñtas salgan ordenadas por el id
        //Crearemos un objetto de tablaDescripcion en el que almacenaremos los datos y luego 
        //almacenaremos a este en el TreeSet, el TreeSet lo retornaremos y en el controlador lo recogeremos y trabajaremos con el
        TreeSet<objetoTablaDescripcion> lista = new TreeSet<>();
        objetoTablaDescripcion tablaDescripcion;

        try {
            try (PreparedStatement s1 = this.con.prepareStatement("SELECT * FROM Descripcion WHERE Descripcion.Nom_Grupo= ? && Descripcion.Nom_Articulo= ?") //Agregando parametros a la posicion correspondiente de la consulta
                    ) {

                s1.setString(1, nomGrupo);
                s1.setString(2, nomArticulo);
                rs = s1.executeQuery();

                while (rs.next()) {
                    //Creamos objeto tabla Descripcion
                    tablaDescripcion = new objetoTablaDescripcion(rs.getInt("ID"), rs.getString("Nom_Grupo"), rs.getString("Nom_Articulo"), rs.getString("Texto"));
                    //Agregamos objeto al TreeSet
                    lista.add(tablaDescripcion);

                }
            }

            //Retornamos la lista recolectada, pero antes la transformaremos a ARRAY
            return lista;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            //Retornamos la lista recolectada, pero antes la transformaremos a ARRAY

            return lista;

        }//Fin del try catch

    }

////////////////////////////////////////////////////////////////////////////////// 
//METODO LECTURA TABLA DESCRIPCION por GRUPO (publico y privado)     
    public TreeSet<objetoTablaDescripcion> LecturaDescripcionGrupo_SOLO(String nomGrupo) {

        if (!BBDD_Error) {
            return LecturaDescripcionGrupo_SOLO_H(nomGrupo);
        } else {
            TreeSet<objetoTablaDescripcion> lista = new TreeSet<>();
            return lista;
        }
    }

    private TreeSet<objetoTablaDescripcion> LecturaDescripcionGrupo_SOLO_H(String nomGrupo) {

        //Recolectaremos los datos en un TreeSet para que las consulñtas salgan ordenadas por el id
        //Crearemos un objetto de tablaDescripcion en el que almacenaremos los datos y luego 
        //almacenaremos a este en el TreeSet, el TreeSet lo retornaremos y en el controlador lo recogeremos y trabajaremos con el
        TreeSet<objetoTablaDescripcion> lista = new TreeSet<>();
        objetoTablaDescripcion tablaDescripcion;

        try {
            try (PreparedStatement s1 = this.con.prepareStatement("SELECT * FROM Descripcion WHERE Descripcion.Nom_Grupo= ? ") //Agregando parametros a la posicion correspondiente de la consulta
                    ) {

                s1.setString(1, nomGrupo);

                rs = s1.executeQuery();

                while (rs.next()) {
                    //Creamos objeto tabla Descripcion
                    tablaDescripcion = new objetoTablaDescripcion(rs.getInt("ID"), rs.getString("Nom_Grupo"), rs.getString("Nom_Articulo"), rs.getString("Texto"));
                    //Agregamos objeto al TreeSet
                    lista.add(tablaDescripcion);

                }
            }

            //Retornamos la lista recolectada, pero antes la transformaremos a ARRAY
            return lista;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            //Retornamos la lista recolectada, pero antes la transformaremos a ARRAY

            return lista;

        }//Fin del try catch

    }

////////////////////////////////////////////////////////////////////////////////// 
//METODO LECTURA TABLA DESCRIPCION por  ARTICULO (publico y privado)     
    public TreeSet<objetoTablaDescripcion> LecturaDescripcionArticulo_SOLO(String nomArticulo) {

        if (!BBDD_Error) {
            return LecturaDescripcionArticulo_SOLO_H(nomArticulo);
        } else {
            TreeSet<objetoTablaDescripcion> lista = new TreeSet<>();
            return lista;
        }
    }

    private TreeSet<objetoTablaDescripcion> LecturaDescripcionArticulo_SOLO_H(String nomArticulo) {

        //Recolectaremos los datos en un TreeSet para que las consulñtas salgan ordenadas por el id
        //Crearemos un objetto de tablaDescripcion en el que almacenaremos los datos y luego 
        //almacenaremos a este en el TreeSet, el TreeSet lo retornaremos y en el controlador lo recogeremos y trabajaremos con el
        TreeSet<objetoTablaDescripcion> lista = new TreeSet<>();
        objetoTablaDescripcion tablaDescripcion;

        try {
            try (PreparedStatement s1 = this.con.prepareStatement("SELECT * FROM Descripcion WHERE Descripcion.Nom_Articulo= ? ") //Agregando parametros a la posicion correspondiente de la consulta
                    ) {

                s1.setString(1, nomArticulo);

                rs = s1.executeQuery();

                while (rs.next()) {
                    //Creamos objeto tabla Descripcion
                    tablaDescripcion = new objetoTablaDescripcion(rs.getInt("ID"), rs.getString("Nom_Grupo"), rs.getString("Nom_Articulo"), rs.getString("Texto"));
                    //Agregamos objeto al TreeSet
                    lista.add(tablaDescripcion);

                }
            }

            //Retornamos la lista recolectada, pero antes la transformaremos a ARRAY
            return lista;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            //Retornamos la lista recolectada, pero antes la transformaremos a ARRAY

            return lista;

        }//Fin del try catch

    }

    //////////////////////////////////////////////////////////////////////////////////    
    //METODO BOTON MODOFOCAR DESCRIPCION (publico y privado)  
    public boolean ModificarDescripcion(Integer id, String descripcion) {

        if (!BBDD_Error) {
            return ModificarDescripcionH(id, descripcion);
        } else {
            return false;
        }

    }//Fin 

    private boolean ModificarDescripcionH(Integer id, String descripcion) {
        // Preparamos la consulta
        try {

            try (PreparedStatement s1 = this.con.prepareStatement("UPDATE Descripcion SET Descripcion.Texto = ? WHERE Descripcion.id = ?") //Agregando parametros a la posicion correspondiente de la consulta
                    ) {
                s1.setString(1, descripcion);
                s1.setInt(2, id);
                if (s1.executeUpdate() == 0) {
                    return false;
                } else {
                    return true;
                }
                //CERRAMOS CONEXION

            }

        } catch (HeadlessException | SQLException e) {

            JOptionPane.showMessageDialog(null, ("Error " + e), "Failure", JOptionPane.ERROR_MESSAGE);

        }//Fin del try catch

        return false;

    }//Fin metodo AltaGrupoH

    //////////////////////////////////////////////////////////////////////////////////    
    //METODO BOTON ALTA DESCRIPCION (publico y privado)  
    public String ObtenerDescripcion(Integer id) {

        if (!BBDD_Error) {
            return ObtenerDescripcionH(id);
        } else {
            return "";
        }

    }//Fin alta artículo

    private String ObtenerDescripcionH(Integer id) {
        // Preparamos la consulta
        String ObtenerDescripcion;

        try {

          
           
              PreparedStatement s1 = this.con.prepareStatement("SELECT Descripcion.Texto FROM Descripcion WHERE Descripcion.id= ? "); //Agregando parametros a la posicion correspondiente de la consulta
                s1.setInt(1, id);
            
            rs = s1.executeQuery();

            while (rs.next()) {
                //Creamos objeto tabla Descripcion
                ObtenerDescripcion = rs.getString("Texto");
                //Agregamos objeto al TreeSet
                return ObtenerDescripcion;
            }
           
            } catch (SQLException ex) {
                Logger.getLogger(MetodosBBDD_Consultas.class.getName()).log(Level.SEVERE, null, ex);
            }
        return "";

    }//Fin metodo AltaGrupoH

}//Fin clase METODO_CONSULTAS
