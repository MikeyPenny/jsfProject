/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.io.Serializable;


public class TipoInsumo implements Serializable {
    
    private int id_tipo;
    private String tipoStr;

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
     * @return the tipoStr
     */
    public String getTipoStr() {
        return tipoStr;
    }

    /**
     * @param tipoStr the tipoStr to set
     */
    public void setTipoStr(String tipoStr) {
        this.tipoStr = tipoStr;
    }
    
}
