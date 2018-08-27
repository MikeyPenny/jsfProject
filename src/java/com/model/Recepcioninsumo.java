/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


public class Recepcioninsumo implements Serializable {
    
    private Integer idrecepcionInsumo;
    private Recepcion recepcion;
    private InsumoRequisicion insumoRequisicion;
    private BigDecimal cantidad;
    private BigDecimal precio;
    private BigDecimal cantidadOriginal;
    private String claveIns;
    private String Descripcion;
    private int idExpIns;
    private String unidad;
    private BigDecimal devolucion;
    
    private Set devolucionInsumos = new HashSet(0);
    
    public Recepcioninsumo() {
    }
    
    public Recepcioninsumo(Recepcion recepcion, BigDecimal cantidad, BigDecimal precio) {
        this.recepcion = recepcion;
        this.cantidad = cantidad;
        this.precio = precio;
    }
    
    public Recepcioninsumo(Recepcion recepcion, InsumoRequisicion insumoRequisicion, BigDecimal cantidad, BigDecimal precio, BigDecimal cantidadOriginal, Set devolucionInsumos) {
       this.recepcion = recepcion;
       this.insumoRequisicion = insumoRequisicion;
       this.cantidad = cantidad;
       this.precio = precio;
       this.cantidadOriginal = cantidadOriginal;
       this.devolucionInsumos = devolucionInsumos;
    }

    /**
     * @return the idrecepcionInsumo
     */
    public Integer getIdrecepcionInsumo() {
        return idrecepcionInsumo;
    }

    /**
     * @param idrecepcionInsumo the idrecepcionInsumo to set
     */
    public void setIdrecepcionInsumo(Integer idrecepcionInsumo) {
        this.idrecepcionInsumo = idrecepcionInsumo;
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
     * @return the insumoRequisicion
     */
    public InsumoRequisicion getInsumoRequisicion() {
        return insumoRequisicion;
    }

    /**
     * @param insumoRequisicion the insumoRequisicion to set
     */
    public void setInsumoRequisicion(InsumoRequisicion insumoRequisicion) {
        this.insumoRequisicion = insumoRequisicion;
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
     * @return the precio
     */
    public BigDecimal getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
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

    /**
     * @return the claveIns
     */
    public String getClaveIns() {
        return claveIns;
    }

    /**
     * @param claveIns the claveIns to set
     */
    public void setClaveIns(String claveIns) {
        this.claveIns = claveIns;
    }

    /**
     * @return the Descripcion
     */
    public String getDescripcion() {
        return Descripcion;
    }

    /**
     * @param Descripcion the Descripcion to set
     */
    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    /**
     * @return the idExpIns
     */
    public int getIdExpIns() {
        return idExpIns;
    }

    /**
     * @param idExpIns the idExpIns to set
     */
    public void setIdExpIns(int idExpIns) {
        this.idExpIns = idExpIns;
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

    /**
     * @return the devolucion
     */
    public BigDecimal getDevolucion() {
        return devolucion;
    }

    /**
     * @param devolucion the devolucion to set
     */
    public void setDevolucion(BigDecimal devolucion) {
        this.devolucion = devolucion;
    }
    
}
