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
public class InsumoTablaMB implements Serializable {

    private int idInsumoTabla;
    private String clave;
    private String concepto;
    private BigDecimal cantPresupuestada;
    private BigDecimal cantDisponible;
    private BigDecimal cantDisponibleOriginal;
    private String unidades;
    private BigDecimal precioUnitario;
    
    public InsumoTablaMB() {
    }

    /**
     * @return the idInsumoTabla
     */
    public int getIdInsumoTabla() {
        return idInsumoTabla;
    }

    /**
     * @param idInsumoTabla the idInsumoTabla to set
     */
    public void setIdInsumoTabla(int idInsumoTabla) {
        this.idInsumoTabla = idInsumoTabla;
    }

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * @return the concepto
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * @param concepto the concepto to set
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     * @return the cantPresupuestada
     */
    public BigDecimal getCantPresupuestada() {
        return cantPresupuestada;
    }

    /**
     * @param cantPresupuestada the cantPresupuestada to set
     */
    public void setCantPresupuestada(BigDecimal cantPresupuestada) {
        this.cantPresupuestada = cantPresupuestada;
    }

    /**
     * @return the cantDisponible
     */
    public BigDecimal getCantDisponible() {
        return cantDisponible;
    }

    /**
     * @param cantDisponible the cantDisponible to set
     */
    public void setCantDisponible(BigDecimal cantDisponible) {
        this.cantDisponible = cantDisponible;
    }

    /**
     * @return the cantDisponibleOriginal
     */
    public BigDecimal getCantDisponibleOriginal() {
        return cantDisponibleOriginal;
    }

    /**
     * @param cantDisponibleOriginal the cantDisponibleOriginal to set
     */
    public void setCantDisponibleOriginal(BigDecimal cantDisponibleOriginal) {
        this.cantDisponibleOriginal = cantDisponibleOriginal;
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
     * @return the precioUnitario
     */
    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * @param precioUnitario the precioUnitario to set
     */
    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    
}
