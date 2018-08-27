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
public class AnticipoBean {
    
    private int id_contrato;
    private int nroContrato;
    private BigDecimal importe;
    private String contratista;
    private String tipo;
    private BigDecimal iva;
    private BigDecimal rtnRenta;
    private BigDecimal rtnFlete;
    private BigDecimal total;
    private BigDecimal pctAnt;

    public int getId_contrato() {
        return id_contrato;
    }

    public void setId_contrato(int id_contrato) {
        this.id_contrato = id_contrato;
    }

    public int getNroContrato() {
        return nroContrato;
    }

    public void setNroContrato(int nroContrato) {
        this.nroContrato = nroContrato;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getContratista() {
        return contratista;
    }

    public void setContratista(String contratista) {
        this.contratista = contratista;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * @return the pctAnt
     */
    public BigDecimal getPctAnt() {
        return pctAnt;
    }

    /**
     * @param pctAnt the pctAnt to set
     */
    public void setPctAnt(BigDecimal pctAnt) {
        this.pctAnt = pctAnt;
    }
    
}
