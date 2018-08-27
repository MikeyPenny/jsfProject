/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.math.BigDecimal;


public class RecepAlmBean {
    
    private int idExpinsumos;
    private String codInsumo;
    private String descripcion;
    private String unidades;
    private BigDecimal cantidad;

    /**
     * @return the idExpinsumos
     */
    public int getIdExpinsumos() {
        return idExpinsumos;
    }

    /**
     * @param idExpinsumos the idExpinsumos to set
     */
    public void setIdExpinsumos(int idExpinsumos) {
        this.idExpinsumos = idExpinsumos;
    }

    /**
     * @return the codInsumo
     */
    public String getCodInsumo() {
        return codInsumo;
    }

    /**
     * @param codInsumo the codInsumo to set
     */
    public void setCodInsumo(String codInsumo) {
        this.codInsumo = codInsumo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
