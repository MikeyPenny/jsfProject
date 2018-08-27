/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class Recepcion implements Serializable {
    
    private Integer idRecepcion;
    private OrdenCompra ordenCompra;
    private String estatus;
    private int noRecepcion;
    private String fechaRecepcion;
    private Set recepcioninsumos = new HashSet(0);
    private Set devolucions = new HashSet(0);
    
    public Recepcion() {
    }

	
    public Recepcion(int noRecepcion, String fechaRecepcion) {
        this.noRecepcion = noRecepcion;
        this.fechaRecepcion = fechaRecepcion;
    }
    public Recepcion(OrdenCompra ordenCompra, String estatus, int noRecepcion, String fechaRecepcion, Set recepcioninsumos, Set devolucions) {
       this.ordenCompra = ordenCompra;
       this.estatus = estatus;
       this.noRecepcion = noRecepcion;
       this.fechaRecepcion = fechaRecepcion;
       this.recepcioninsumos = recepcioninsumos;
       this.devolucions = devolucions;
    }

    /**
     * @return the idRecepcion
     */
    public Integer getIdRecepcion() {
        return idRecepcion;
    }

    /**
     * @param idRecepcion the idRecepcion to set
     */
    public void setIdRecepcion(Integer idRecepcion) {
        this.idRecepcion = idRecepcion;
    }

    /**
     * @return the ordenCompra
     */
    public OrdenCompra getOrdenCompra() {
        return ordenCompra;
    }

    /**
     * @param ordenCompra the ordenCompra to set
     */
    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    /**
     * @return the estatus
     */
    public String getEstatus() {
        return estatus;
    }

    /**
     * @param estatus the estatus to set
     */
    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    /**
     * @return the noRecepcion
     */
    public int getNoRecepcion() {
        return noRecepcion;
    }

    /**
     * @param noRecepcion the noRecepcion to set
     */
    public void setNoRecepcion(int noRecepcion) {
        this.noRecepcion = noRecepcion;
    }

    /**
     * @return the fechaRecepcion
     */
    public String getFechaRecepcion() {
        return fechaRecepcion;
    }

    /**
     * @param fechaRecepcion the fechaRecepcion to set
     */
    public void setFechaRecepcion(String fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    /**
     * @return the recepcioninsumos
     */
    public Set getRecepcioninsumos() {
        return recepcioninsumos;
    }

    /**
     * @param recepcioninsumos the recepcioninsumos to set
     */
    public void setRecepcioninsumos(Set recepcioninsumos) {
        this.recepcioninsumos = recepcioninsumos;
    }

    /**
     * @return the devolucions
     */
    public Set getDevolucions() {
        return devolucions;
    }

    /**
     * @param devolucions the devolucions to set
     */
    public void setDevolucions(Set devolucions) {
        this.devolucions = devolucions;
    }
    
}
