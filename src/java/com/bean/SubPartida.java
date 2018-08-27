/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.util.List;


public class SubPartida {
    
    private int id_subPartida;
    private String subPartida;
    private String clave;
    private List<Concepto> listaConcep;

    /**
     * @return the id_subPartida
     */
    public int getId_subPartida() {
        return id_subPartida;
    }

    /**
     * @param id_subPartida the id_subPartida to set
     */
    public void setId_subPartida(int id_subPartida) {
        this.id_subPartida = id_subPartida;
    }

    /**
     * @return the subPartida
     */
    public String getSubPartida() {
        return subPartida;
    }

    /**
     * @param subPartida the subPartida to set
     */
    public void setSubPartida(String subPartida) {
        this.subPartida = subPartida;
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
     * @return the listaConcep
     */
    public List<Concepto> getListaConcep() {
        return listaConcep;
    }

    /**
     * @param listaConcep the listaConcep to set
     */
    public void setListaConcep(List<Concepto> listaConcep) {
        this.listaConcep = listaConcep;
    }
    
}
