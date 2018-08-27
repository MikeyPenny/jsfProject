/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Mickey
 */
public class ValeDevolucion {
    
    private Integer idValeDevolucion;
    private ValeConsumo valeConsumo;
    private String fechaElaboracion;
    private Integer noValeConsumo;
    private Integer noValeDevolucion;
    private Set insumoValeDevolucions = new HashSet(0);

    public ValeDevolucion() {
    }
    
    public Integer getIdValeDevolucion() {
        return idValeDevolucion;
    }

    /**
     * @param idValeDevolucion the idValeDevolucion to set
     */
    public void setIdValeDevolucion(Integer idValeDevolucion) {
        this.idValeDevolucion = idValeDevolucion;
    }

    /**
     * @return the valeConsumo
     */
    public ValeConsumo getValeConsumo() {
        return valeConsumo;
    }

    /**
     * @param valeConsumo the valeConsumo to set
     */
    public void setValeConsumo(ValeConsumo valeConsumo) {
        this.valeConsumo = valeConsumo;
    }

    /**
     * @return the fechaElaboracion
     */
    public String getFechaElaboracion() {
        return fechaElaboracion;
    }

    /**
     * @param fechaElaboracion the fechaElaboracion to set
     */
    public void setFechaElaboracion(String fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    /**
     * @return the noValeConsumo
     */
    public Integer getNoValeConsumo() {
        return noValeConsumo;
    }

    /**
     * @param noValeConsumo the noValeConsumo to set
     */
    public void setNoValeConsumo(Integer noValeConsumo) {
        this.noValeConsumo = noValeConsumo;
    }

    /**
     * @return the noValeDevolucion
     */
    public Integer getNoValeDevolucion() {
        return noValeDevolucion;
    }

    /**
     * @param noValeDevolucion the noValeDevolucion to set
     */
    public void setNoValeDevolucion(Integer noValeDevolucion) {
        this.noValeDevolucion = noValeDevolucion;
    }

    /**
     * @return the insumoValeDevolucions
     */
    public Set getInsumoValeDevolucions() {
        return insumoValeDevolucions;
    }

    /**
     * @param insumoValeDevolucions the insumoValeDevolucions to set
     */
    public void setInsumoValeDevolucions(Set insumoValeDevolucions) {
        this.insumoValeDevolucions = insumoValeDevolucions;
    }
    
}
