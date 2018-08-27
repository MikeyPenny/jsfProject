/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import java.math.BigDecimal;


public class TablaInsumoDevolucion {
    
    private int idInsumoDevolucion;
    private String nombreInsumo;
    private BigDecimal cantidadDevuelta;
    private String unidad;

    public TablaInsumoDevolucion() {
    }

    /**
     * @return the idInsumoDevolucion
     */
    public int getIdInsumoDevolucion() {
        return idInsumoDevolucion;
    }

    /**
     * @param idInsumoDevolucion the idInsumoDevolucion to set
     */
    public void setIdInsumoDevolucion(int idInsumoDevolucion) {
        this.idInsumoDevolucion = idInsumoDevolucion;
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
     * @return the cantidadDevuelta
     */
    public BigDecimal getCantidadDevuelta() {
        return cantidadDevuelta;
    }

    /**
     * @param cantidadDevuelta the cantidadDevuelta to set
     */
    public void setCantidadDevuelta(BigDecimal cantidadDevuelta) {
        this.cantidadDevuelta = cantidadDevuelta;
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
    
}
