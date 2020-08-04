/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author davidf
 */
public class objetoTablaDescripcion implements Comparable<objetoTablaDescripcion> {

    //Estas variables almacenarán la tabla completa RECUPERA DE LA CONSULTA
    private int ID;
    private String grupo;
    private String articulo;
    private String descripcion;

    //constructor del objeto
    public objetoTablaDescripcion(int ID, String grupo, String articulo, String descripcion) {
        this.ID = ID;
        this.grupo = grupo;
        this.articulo = articulo;
        this.descripcion = descripcion;
    }//Fin del constructor

    //EL ID NO TIENE SETTER, porque no se puede configurar ya que es autoincremental
    public int getID() {
        return ID;
    }

    //El resto tiene getters y setters, para poder luego usar este mismo objeto 
    //cuando queramos MODIFICAR el contenido de alguna entrada o cambiarlo de grupo
    //artículo, etc
    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    //Sobreescribiendo comparador
    //ORDENARÁ POR el ID
    @Override
    public int compareTo(objetoTablaDescripcion t) {

        String aux1 = String.valueOf(this.ID);
        String aux2 = String.valueOf(t.ID);

        return aux1.compareTo(aux2);
    }//Fin del comparador

}//Fin de la clase OBJETO, COMPARABLE

