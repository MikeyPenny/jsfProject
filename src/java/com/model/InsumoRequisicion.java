/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


public class InsumoRequisicion implements Serializable {
    
    private Integer idinsumoRequisicion;
    private ExpInsumos expInsumos;
    private Requisicion requisicion;
    private BigDecimal cantidad;
    private BigDecimal preciounitario;
    private Set recepcioninsumos = new HashSet(0);
    
    public InsumoRequisicion() {
    }

    public InsumoRequisicion(ExpInsumos expInsumos, Requisicion requisicion, BigDecimal cantidad, BigDecimal preciounitario, Set recepcioninsumos) {
       this.expInsumos = expInsumos;
       this.requisicion = requisicion;
       this.cantidad = cantidad;
       this.preciounitario = preciounitario;
       this.recepcioninsumos = recepcioninsumos;
    }

    /**
     * @return the idinsumoRequisicion
     */
    public Integer getIdinsumoRequisicion() {
        return idinsumoRequisicion;
    }

    /**
     * @param idinsumoRequisicion the idinsumoRequisicion to set
     */
    public void setIdinsumoRequisicion(Integer idinsumoRequisicion) {
        this.idinsumoRequisicion = idinsumoRequisicion;
    }

    /**
     * @return the expInsumos
     */
    public ExpInsumos getExpInsumos() {
        return expInsumos;
    }

    /**
     * @param expInsumos the expInsumos to set
     */
    public void setExpInsumos(ExpInsumos expInsumos) {
        this.expInsumos = expInsumos;
    }

    /**
     * @return the requisicion
     */
    public Requisicion getRequisicion() {
        return requisicion;
    }

    /**
     * @param requisicion the requisicion to set
     */
    public void setRequisicion(Requisicion requisicion) {
        this.requisicion = requisicion;
    }

    /**
     * @return the cantidad
     */
    public BigDecimal getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the preciounitario
     */
    public BigDecimal getPreciounitario() {
        return preciounitario;
    }

    /**
     * @param preciounitario the preciounitario to set
     */
    public void setPreciounitario(BigDecimal preciounitario) {
        this.preciounitario = preciounitario;
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
    
}
