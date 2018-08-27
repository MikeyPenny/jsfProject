/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Mickey
 */
public class AvanceBean implements Serializable {
    
    private int id_avance;
    private String estatusAvance;
    private BigDecimal importeEstimacion;
    private BigDecimal anticipoAmort;
    private BigDecimal retFdoGtia;
    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal importeTotal;
    private String fecha;
    private int nroAvance;
    private int nroEstimacion;

    public String getEstatusAvance() {
        return estatusAvance;
    }

    public void setEstatusAvance(String estatusAvance) {
        this.estatusAvance = estatusAvance;
    }

    public BigDecimal getImporteEstimacion() {
        return importeEstimacion;
    }

    public void setImporteEstimacion(BigDecimal importeEstimacion) {
        this.importeEstimacion = importeEstimacion;
    }

    public BigDecimal getAnticipoAmort() {
        return anticipoAmort;
    }

    public void setAnticipoAmort(BigDecimal anticipoAmort) {
        this.anticipoAmort = anticipoAmort;
    }

    public BigDecimal getRetFdoGtia() {
        return retFdoGtia;
    }

    public void setRetFdoGtia(BigDecimal retFdoGtia) {
        this.retFdoGtia = retFdoGtia;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId_avance() {
        return id_avance;
    }

    public void setId_avance(int id_avance) {
        this.id_avance = id_avance;
    }

    public int getNroAvance() {
        return nroAvance;
    }

    public void setNroAvance(int nroAvance) {
        this.nroAvance = nroAvance;
    }

    /**
     * @return the nroEstimacion
     */
    public int getNroEstimacion() {
        return nroEstimacion;
    }

    /**
     * @param nroEstimacion the nroEstimacion to set
     */
    public void setNroEstimacion(int nroEstimacion) {
        this.nroEstimacion = nroEstimacion;
    }
    
}
