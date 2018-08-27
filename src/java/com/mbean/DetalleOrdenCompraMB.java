/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import java.math.BigDecimal;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Mickey
 */
@ManagedBean
@ViewScoped
public class DetalleOrdenCompraMB {

    private int idControl;
    private int noRequisicion;
    private String concepto;
    private String unidad;
    private BigDecimal cantidadSolicitada;
    private BigDecimal precioUnitario;
    
    public DetalleOrdenCompraMB() {
    }

    /**
     * @return the idControl
     */
    public int getIdControl() {
        return idControl;
    }

    /**
     * @param idControl the idControl to set
     */
    public void setIdControl(int idControl) {
        this.idControl = idControl;
    }

    /**
     * @return the noRequisicion
     */
    public int getNoRequisicion() {
        return noRequisicion;
    }

    /**
     * @param noRequisicion the noRequisicion to set
     */
    public void setNoRequisicion(int noRequisicion) {
        this.noRequisicion = noRequisicion;
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
     * @return the cantidadSolicitada
     */
    public BigDecimal getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    /**
     * @param cantidadSolicitada the cantidadSolicitada to set
     */
    public void setCantidadSolicitada(BigDecimal cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
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
