/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.io.Serializable;

/**
 *
 * @author Mickey
 */
public class Contratista implements Serializable {
    
    private int id_contratista;
    private String rfc;
    private String razonSocial;

    /**
     * @return the id_contratista
     */
    public int getId_contratista() {
        return id_contratista;
    }

    /**
     * @param id_contratista the id_contratista to set
     */
    public void setId_contratista(int id_contratista) {
        this.id_contratista = id_contratista;
    }

    /**
     * @return the rfc
     */
    public String getRfc() {
        return rfc;
    }

    /**
     * @param rfc the rfc to set
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * @param razonSocial the razonSocial to set
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
    
}
