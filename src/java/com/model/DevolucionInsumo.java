/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.math.BigDecimal;


public class DevolucionInsumo {
    
    private Integer idDevolucionInsumo;
    private Devolucion devolucion;
    private Recepcioninsumo recepcioninsumo;
    private BigDecimal cantidad;
    
    public DevolucionInsumo() {
    
    }

    /**
     * @return the idDevolucionInsumo
     */
    public Integer getIdDevolucionInsumo() {
        return idDevolucionInsumo;
    }

    /**
     * @param idDevolucionInsumo the idDevolucionInsumo to set
     */
    public void setIdDevolucionInsumo(Integer idDevolucionInsumo) {
        this.idDevolucionInsumo = idDevolucionInsumo;
    }

    /**
     * @return the devolucion
     */
    public Devolucion getDevolucion() {
        return devolucion;
    }

    /**
     * @param devolucion the devolucion to set
     */
    public void setDevolucion(Devolucion devolucion) {
        this.devolucion = devolucion;
    }

    /**
     * @return the recepcioninsumo
     */
    public Recepcioninsumo getRecepcioninsumo() {
        return recepcioninsumo;
    }

    /**
     * @param recepcioninsumo the recepcioninsumo to set
     */
    public void setRecepcioninsumo(Recepcioninsumo recepcioninsumo) {
        this.recepcioninsumo = recepcioninsumo;
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
    
}
