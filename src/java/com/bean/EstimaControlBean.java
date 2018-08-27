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
public class EstimaControlBean {
    
    private int id_avance;
    private BigDecimal importe;
    private BigDecimal amortizado;
    private BigDecimal pendXAmort;
    private BigDecimal gtiaRetenida;

    public int getId_avance() {
        return id_avance;
    }

    public void setId_avance(int id_avance) {
        this.id_avance = id_avance;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public BigDecimal getAmortizado() {
        return amortizado;
    }

    public void setAmortizado(BigDecimal amortizado) {
        this.amortizado = amortizado;
    }

    public BigDecimal getPendXAmort() {
        return pendXAmort;
    }

    public void setPendXAmort(BigDecimal pendXAmort) {
        this.pendXAmort = pendXAmort;
    }

    public BigDecimal getGtiaRetenida() {
        return gtiaRetenida;
    }

    public void setGtiaRetenida(BigDecimal gtiaRetenida) {
        this.gtiaRetenida = gtiaRetenida;
    }
    
}
