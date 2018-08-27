/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import java.math.BigDecimal;


public class FormTablaInsumoValeConsumo {
    
    private int idinsumoValeConsumo;
    private String nombreInsumo;
    private BigDecimal cantidad;
    private String unidades;

    public FormTablaInsumoValeConsumo() {
    }

    /**
     * @return the idinsumoValeConsumo
     */
    public int getIdinsumoValeConsumo() {
        return idinsumoValeConsumo;
    }

    /**
     * @param idinsumoValeConsumo the idinsumoValeConsumo to set
     */
    public void setIdinsumoValeConsumo(int idinsumoValeConsumo) {
        this.idinsumoValeConsumo = idinsumoValeConsumo;
    }

    /**
     * @return the nombreInsumo
     */
    public String getNombreInsumo() {
        return nombreInsumo;
    }

    /**
     * @param nombreInsumo the nombreInsumo to set
     */
    public void setNombreInsumo(String nombreInsumo) {
        this.nombreInsumo = nombreInsumo;
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
     * @return the unidades
     */
    public String getUnidades() {
        return unidades;
    }

    /**
     * @param unidades the unidades to set
     */
    public void setUnidades(String unidades) {
        this.unidades = unidades;
    }
    
}
