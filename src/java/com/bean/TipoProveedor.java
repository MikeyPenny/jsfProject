/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.io.Serializable;


public class TipoProveedor implements Serializable {
    
    private int id_tipo;
    private String tipoProveedor;
    private int cve;

    /**
     * @return the id_tipo
     */
    public int getId_tipo() {
        return id_tipo;
    }

    /**
     * @param id_tipo the id_tipo to set
     */
    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }

    /**
     * @return the tipoProveedor
     */
    public String getTipoProveedor() {
        return tipoProveedor;
    }

    /**
     * @param tipoProveedor the tipoProveedor to set
     */
    public void setTipoProveedor(String tipoProveedor) {
        this.tipoProveedor = tipoProveedor;
    }

    /**
     * @return the cve
     */
    public int getCve() {
        return cve;
    }

    /**
     * @param cve the cve to set
     */
    public void setCve(int cve) {
        this.cve = cve;
    }
    
}
