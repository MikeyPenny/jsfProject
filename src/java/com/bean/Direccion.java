/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;


public class Direccion {
    
    private String direccion;
    private String calle;
    private String num;
    private String colonia;
    private String codPost;
    private String ciudad;
    private String telefono;
    
    public Direccion() {
    
    }
    
    public Direccion(String calle, String num, String colonia, String codPost, 
            String ciudad, String telefono) {
        direccion = calle+" "+num+", "+colonia+", CP. "+codPost;
        this.calle = calle;
        this.num = num;
        this.colonia = colonia;
        this.codPost = codPost;
        this.ciudad = ciudad;
        this.telefono = telefono;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @param ciudad the ciudad to set
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the calle
     */
    public String getCalle() {
        return calle;
    }

    /**
     * @param calle the calle to set
     */
    public void setCalle(String calle) {
        this.calle = calle;
    }

    /**
     * @return the num
     */
    public String getNum() {
        return num;
    }

    /**
     * @param num the num to set
     */
    public void setNum(String num) {
        this.num = num;
    }

    /**
     * @return the colonia
     */
    public String getColonia() {
        return colonia;
    }

    /**
     * @param colonia the colonia to set
     */
    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    /**
     * @return the codPost
     */
    public String getCodPost() {
        return codPost;
    }

    /**
     * @param codPost the codPost to set
     */
    public void setCodPost(String codPost) {
        this.codPost = codPost;
    }
    
}
