/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.io.Serializable;


public class MaterialBean implements Serializable {
    
    private int id_material;
    private String material;
    private String unidad;
    private String claveMat;
    private String cuenta;

    /**
     * @return the id_material
     */
    public int getId_material() {
        return id_material;
    }

    /**
     * @param id_material the id_material to set
     */
    public void setId_material(int id_material) {
        this.id_material = id_material;
    }

    /**
     * @return the material
     */
    public String getMaterial() {
        return material;
    }

    /**
     * @param material the material to set
     */
    public void setMaterial(String material) {
        this.material = material;
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

    /**
     * @return the claveMat
     */
    public String getClaveMat() {
        return claveMat;
    }

    /**
     * @param claveMat the claveMat to set
     */
    public void setClaveMat(String claveMat) {
        this.claveMat = claveMat;
    }

    /**
     * @return the cuenta
     */
    public String getCuenta() {
        return cuenta;
    }

    /**
     * @param cuenta the cuenta to set
     */
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }
    
}
