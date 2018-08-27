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
public class AcumuladosBean {
    
    private BigDecimal acumImporte;
    private BigDecimal acumAmort;
    private BigDecimal acumGtia;

    public BigDecimal getAcumImporte() {
        return acumImporte;
    }

    public void setAcumImporte(BigDecimal acumImporte) {
        this.acumImporte = acumImporte;
    }

    public BigDecimal getAcumAmort() {
        return acumAmort;
    }

    public void setAcumAmort(BigDecimal acumAmort) {
        this.acumAmort = acumAmort;
    }

    public BigDecimal getAcumGtia() {
        return acumGtia;
    }

    public void setAcumGtia(BigDecimal acumGtia) {
        this.acumGtia = acumGtia;
    }
    
}
