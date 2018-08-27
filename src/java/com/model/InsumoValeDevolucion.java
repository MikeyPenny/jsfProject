/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.math.BigDecimal;

/**
 *
 * @author Mickey
 */
public class InsumoValeDevolucion {
    
    private Integer idInsumoValeDevolucion;
    private InsumoValeConsumo insumoValeConsumo;
    private ValeDevolucion valeDevolucion;
    private BigDecimal cantidad;
    private BigDecimal cantidadOriginal;

    /**
     * @return the idInsumoValeDevolucion
     */
    public Integer getIdInsumoValeDevolucion() {
        return idInsumoValeDevolucion;
    }

    /**
     * @param idInsumoValeDevolucion the idInsumoValeDevolucion to set
     */
    public void setIdInsumoValeDevolucion(Integer idInsumoValeDevolucion) {
        this.idInsumoValeDevolucion = idInsumoValeDevolucion;
    }

    /**
     * @return the insumoValeConsumo
     */
    public InsumoValeConsumo getInsumoValeConsumo() {
        return insumoValeConsumo;
    }

    /**
     * @param insumoValeConsumo the insumoValeConsumo to set
     */
    public void setInsumoValeConsumo(InsumoValeConsumo insumoValeConsumo) {
        this.insumoValeConsumo = insumoValeConsumo;
    }

    /**
     * @return the valeDevolucion
     */
    public ValeDevolucion getValeDevolucion() {
        return valeDevolucion;
    }

    /**
     * @param valeDevolucion the valeDevolucion to set
     */
    public void setValeDevolucion(ValeDevolucion valeDevolucion) {
        this.valeDevolucion = valeDevolucion;
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
     * @return the cantidadOriginal
     */
    public BigDecimal getCantidadOriginal() {
        return cantidadOriginal;
    }

    /**
     * @param cantidadOriginal the cantidadOriginal to set
     */
    public void setCantidadOriginal(BigDecimal cantidadOriginal) {
        this.cantidadOriginal = cantidadOriginal;
    }
    
}
