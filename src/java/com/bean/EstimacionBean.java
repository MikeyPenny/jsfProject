/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.math.BigDecimal;

/**
 *
 * @author Mickey
 */
public class EstimacionBean {
    
    private BigDecimal estimacionImp;
    private BigDecimal amortAntcpo;
    private BigDecimal ivaEst;
    private BigDecimal retencion;
    private BigDecimal rtnRenta;
    private BigDecimal rtnFlete;
    private BigDecimal rtnGarantia;
    private BigDecimal subtotal;
    private BigDecimal total;
    private String estatus;
    private BigDecimal pendXAmort;

    public BigDecimal getEstimacionImp() {
        return estimacionImp;
    }

    public void setEstimacionImp(BigDecimal estimacionImp) {
        this.estimacionImp = estimacionImp;
    }

    public BigDecimal getAmortAntcpo() {
        return amortAntcpo;
    }

    public void setAmortAntcpo(BigDecimal amortAntcpo) {
        this.amortAntcpo = amortAntcpo;
    }

    public BigDecimal getIvaEst() {
        return ivaEst;
    }

    public void setIvaEst(BigDecimal ivaEst) {
        this.ivaEst = ivaEst;
    }

    public BigDecimal getRetencion() {
        return retencion;
    }

    public void setRetencion(BigDecimal retencion) {
        this.retencion = retencion;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public BigDecimal getRtnGarantia() {
        return rtnGarantia;
    }

    public void setRtnGarantia(BigDecimal rtnGarantia) {
        this.rtnGarantia = rtnGarantia;
    }

    public BigDecimal getPendXAmort() {
        return pendXAmort;
    }

    public void setPendXAmort(BigDecimal pendXAmort) {
        this.pendXAmort = pendXAmort;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getRtnRenta() {
        return rtnRenta;
    }

    public void setRtnRenta(BigDecimal rtnRenta) {
        this.rtnRenta = rtnRenta;
    }

    public BigDecimal getRtnFlete() {
        return rtnFlete;
    }

    public void setRtnFlete(BigDecimal rtnFlete) {
        this.rtnFlete = rtnFlete;
    }
    
}
