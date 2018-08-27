/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.math.BigDecimal;


public class InsumoValeConsumo {
    private Integer idinsumoValeConsumo;
    private ExpInsumos expInsumos;
    private ValeConsumo valeConsumo;
    private BigDecimal cantidad;
    private BigDecimal cantidadOriginal;

    /**
     * @return the idinsumoValeConsumo
     */
    public Integer getIdinsumoValeConsumo() {
        return idinsumoValeConsumo;
    }

    /**
     * @param idinsumoValeConsumo the idinsumoValeConsumo to set
     */
    public void setIdinsumoValeConsumo(Integer idinsumoValeConsumo) {
        this.idinsumoValeConsumo = idinsumoValeConsumo;
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
