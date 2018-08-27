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
public class FormTablaInsumoOrdenCompraMB implements Serializable {

    private int idControl;
    private int idInsumo;
    private int noRequisicion;
    private String cveInsumo;
    private String concepto;
    private String unidad;
    private BigDecimal cantidad;
    private BigDecimal precioUnitario;
    private int descuento;
    private BigDecimal importe;
    private int insumoRequisicion;
    
    public FormTablaInsumoOrdenCompraMB() {
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

    /**
     * @return the descuento
     */
    public int getDescuento() {
        return descuento;
    }

    /**
     * @param descuento the descuento to set
     */
    public void setDescuento(int descuento) {
        this.descuento = descuento;
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
     * @return the insumoRequisicion
     */
    public int getInsumoRequisicion() {
        return insumoRequisicion;
    }

    /**
     * @param insumoRequisicion the insumoRequisicion to set
     */
    public void setInsumoRequisicion(int insumoRequisicion) {
        this.insumoRequisicion = insumoRequisicion;
    }
    
}
