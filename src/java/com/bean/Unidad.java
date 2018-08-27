/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.io.Serializable;


public class Unidad implements Serializable {
    
    private int id_unidad;
    private String unidad;
    private String desc_unidad;

    /**
     * @return the id_unidad
     */
    public int getId_unidad() {
        return id_unidad;
    }

    /**
     * @param id_unidad the id_unidad to set
     */
    public void setId_unidad(int id_unidad) {
        this.id_unidad = id_unidad;
    }

    /**
     * @return the unidad
     */
    public String getUnidad() {
        return unidad;
    }

    /**
     * @param unidad the unidad to set
     */
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    @Override
    public String toString() {
        return unidad;
    }

    /**
     * @return the desc_unidad
     */
    public String getDesc_unidad() {
        return desc_unidad;
    }

    /**
     * @param desc_unidad the desc_unidad to set
     */
    public void setDesc_unidad(String desc_unidad) {
        this.desc_unidad = desc_unidad;
    }
    
}
