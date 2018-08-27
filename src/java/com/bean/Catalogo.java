/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;


public class Catalogo {
    
    private int id_subcon;
    private String clave;
    private String definicion;

    /**
     * @return the id_subcon
     */
    public int getId_subcon() {
        return id_subcon;
    }

    /**
     * @param id_subcon the id_subcon to set
     */
    public void setId_subcon(int id_subcon) {
        this.id_subcon = id_subcon;
    }

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * @return the definicion
     */
    public String getDefinicion() {
        return definicion;
    }

    /**
     * @param definicion the definicion to set
     */
    public void setDefinicion(String definicion) {
        this.definicion = definicion;
    }
    
}
