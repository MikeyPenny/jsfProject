/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import java.math.BigDecimal;


public class TablaInsumosRecepcion {
    
    public int idInsumoRecepcion;
    public String nombreInsumo;
    public BigDecimal cantidadRecepcionada;
    public String unidad;
    
    public TablaInsumosRecepcion() {
    }
    
    public int getIdInsumoRecepcion() {
        return idInsumoRecepcion;
    }

    public void setIdInsumoRecepcion(int idInsumoRecepcion) {
        this.idInsumoRecepcion = idInsumoRecepcion;
    }

    public String getNombreInsumo() {
        return nombreInsumo;
    }

    public void setNombreInsumo(String nombreInsumo) {
        this.nombreInsumo = nombreInsumo;
    }

    public BigDecimal getCantidadRecepcionada() {
        return cantidadRecepcionada;
    }

    public void setCantidadRecepcionada(BigDecimal cantidadRecepcionada) {
        this.cantidadRecepcionada = cantidadRecepcionada;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
    
}
