/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.io.Serializable;


public class TipoPresupto implements Serializable {
    
    private int id_tipoPres;
    private String tipoPres;
    private int consec;

    /**
     * @return the id_tipoPres
     */
    public int getId_tipoPres() {
        return id_tipoPres;
    }

    /**
     * @param id_tipoPres the id_tipoPres to set
     */
    public void setId_tipoPres(int id_tipoPres) {
        this.id_tipoPres = id_tipoPres;
    }

    /**
     * @return the tipoPres
     */
    public String getTipoPres() {
        return tipoPres;
    }

    /**
     * @param tipoPres the tipoPres to set
     */
    public void setTipoPres(String tipoPres) {
        this.tipoPres = tipoPres;
    }

    /**
     * @return the consec
     */
    public int getConsec() {
        return consec;
    }

    /**
     * @param consec the consec to set
     */
    public void setConsec(int consec) {
        this.consec = consec;
    }
    
}
