/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.util.HashSet;
import java.util.Set;


public class Devolucion {
    
    private Integer idDevolucion;
    private Recepcion recepcion;
    private String fechaDevolucion;
    private Integer noDevolucion;
    private Set devolucionInsumos = new HashSet(0);
    
    public Devolucion() {
    }

    /**
     * @return the idDevolucion
     */
    public Integer getIdDevolucion() {
        return idDevolucion;
    }

    /**
     * @param idDevolucion the idDevolucion to set
     */
    public void setIdDevolucion(Integer idDevolucion) {
        this.idDevolucion = idDevolucion;
    }

    /**
     * @return the recepcion
     */
    public Recepcion getRecepcion() {
        return recepcion;
    }

    /**
     * @param recepcion the recepcion to set
     */
    public void setRecepcion(Recepcion recepcion) {
        this.recepcion = recepcion;
    }

    /**
     * @return the fechaDevolucion
     */
    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    /**
     * @param fechaDevolucion the fechaDevolucion to set
     */
    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    /**
     * @return the noDevolucion
     */
    public Integer getNoDevolucion() {
        return noDevolucion;
    }

    /**
     * @param noDevolucion the noDevolucion to set
     */
    public void setNoDevolucion(Integer noDevolucion) {
        this.noDevolucion = noDevolucion;
    }

    /**
     * @return the devolucionInsumos
     */
    public Set getDevolucionInsumos() {
        return devolucionInsumos;
    }

    /**
     * @param devolucionInsumos the devolucionInsumos to set
     */
    public void setDevolucionInsumos(Set devolucionInsumos) {
        this.devolucionInsumos = devolucionInsumos;
    }
    
}
