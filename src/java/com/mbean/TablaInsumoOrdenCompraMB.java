/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Mickey
 */
@ManagedBean
@ViewScoped
public class TablaInsumoOrdenCompraMB implements Serializable {

    private int idRecepcionInsumo;
    private int idInsumo;
    private String cveInsumo;
    private String descripcion;
    private String unidad;
    private BigDecimal cantidad;
    private BigDecimal cantidadOriginal;
    private BigDecimal catnCtrl;
    private BigDecimal precio;
    private BigDecimal importe;
    private BigDecimal recibido;
    private BigDecimal consumido;
    private BigDecimal devuelto;
    
    public TablaInsumoOrdenCompraMB() {
    }

    /**
     * @return the idRecepcionInsumo
     */
    public int getIdRecepcionInsumo() {
        return idRecepcionInsumo;
    }

    /**
     * @param idRecepcionInsumo the idRecepcionInsumo to set
     */
    public void setIdRecepcionInsumo(int idRecepcionInsumo) {
        this.idRecepcionInsumo = idRecepcionInsumo;
    }

    /**
     * @return the idInsumo
     */
    public int getIdInsumo() {
        return idInsumo;
    }

    /**
     * @param idInsumo the idInsumo to set
     */
    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    /**
     * @return the cveInsumo
     */
    public String getCveInsumo() {
        return cveInsumo;
    }

    /**
     * @param cveInsumo the cveInsumo to set
     */
    public void setCveInsumo(String cveInsumo) {
        this.cveInsumo = cveInsumo;
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
     * @return the importe
     */
    public BigDecimal getImporte() {
        return importe;
    }

    /**
     * @param importe the importe to set
     */
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    /**
     * @return the recibido
     */
    public BigDecimal getRecibido() {
        return recibido;
    }

    /**
     * @param recibido the recibido to set
     */
    public void setRecibido(BigDecimal recibido) {
        this.recibido = recibido;
    }

    /**
     * @return the consumido
     */
    public BigDecimal getConsumido() {
        return consumido;
    }

    /**
     * @param consumido the consumido to set
     */
    public void setConsumido(BigDecimal consumido) {
        this.consumido = consumido;
    }

    /**
     * @return the catnCtrl
     */
    public BigDecimal getCatnCtrl() {
        return catnCtrl;
    }

    /**
     * @param catnCtrl the catnCtrl to set
     */
    public void setCatnCtrl(BigDecimal catnCtrl) {
        this.catnCtrl = catnCtrl;
    }

    /**
     * @return the devuelto
     */
    public BigDecimal getDevuelto() {
        return devuelto;
    }

    /**
     * @param devuelto the devuelto to set
     */
    public void setDevuelto(BigDecimal devuelto) {
        this.devuelto = devuelto;
    }
    
}
