/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.io.Serializable;
import java.math.BigDecimal;


public class ImportesBean implements Serializable {
    
    private BigDecimal importeEst;
    private BigDecimal amortAntcpo;
    private BigDecimal retGtia;
    private BigDecimal otrasDed;
    private BigDecimal subtotal;
    private BigDecimal gtiDesIVA;
    private BigDecimal iva;
    private BigDecimal total;
    private String noFactura;
    private String fechaFact;
    private String fechaMov;
    private int id_estimacion;
    private int estatus;
    private float pctIva;
    private int id_factura;

    /**
     * @return the importeEst
     */
    public BigDecimal getImporteEst() {
        return importeEst;
    }

    /**
     * @param importeEst the importeEst to set
     */
    public void setImporteEst(BigDecimal importeEst) {
        this.importeEst = importeEst;
    }

    /**
     * @return the amortAntcpo
     */
    public BigDecimal getAmortAntcpo() {
        return amortAntcpo;
    }

    /**
     * @param amortAntcpo the amortAntcpo to set
     */
    public void setAmortAntcpo(BigDecimal amortAntcpo) {
        this.amortAntcpo = amortAntcpo;
    }

    /**
     * @return the retGtia
     */
    public BigDecimal getRetGtia() {
        return retGtia;
    }

    /**
     * @param retGtia the retGtia to set
     */
    public void setRetGtia(BigDecimal retGtia) {
        this.retGtia = retGtia;
    }

    /**
     * @return the otrasDed
     */
    public BigDecimal getOtrasDed() {
        return otrasDed;
    }

    /**
     * @param otrasDed the otrasDed to set
     */
    public void setOtrasDed(BigDecimal otrasDed) {
        this.otrasDed = otrasDed;
    }

    /**
     * @return the gtiDesIVA
     */
    public BigDecimal getGtiDesIVA() {
        return gtiDesIVA;
    }

    /**
     * @param gtiDesIVA the gtiDesIVA to set
     */
    public void setGtiDesIVA(BigDecimal gtiDesIVA) {
        this.gtiDesIVA = gtiDesIVA;
    }

    /**
     * @return the iva
     */
    public BigDecimal getIva() {
        return iva;
    }

    /**
     * @param iva the iva to set
     */
    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    /**
     * @return the total
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * @return the subtotal
     */
    public BigDecimal getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * @return the noFactura
     */
    public String getNoFactura() {
        return noFactura;
    }

    /**
     * @param noFactura the noFactura to set
     */
    public void setNoFactura(String noFactura) {
        this.noFactura = noFactura;
    }

    /**
     * @return the fechaFact
     */
    public String getFechaFact() {
        return fechaFact;
    }

    /**
     * @param fechaFact the fechaFact to set
     */
    public void setFechaFact(String fechaFact) {
        this.fechaFact = fechaFact;
    }

    /**
     * @return the fechaMov
     */
    public String getFechaMov() {
        return fechaMov;
    }

    /**
     * @param fechaMov the fechaMov to set
     */
    public void setFechaMov(String fechaMov) {
        this.fechaMov = fechaMov;
    }

    /**
     * @return the id_estimacion
     */
    public int getId_estimacion() {
        return id_estimacion;
    }

    /**
     * @param id_estimacion the id_estimacion to set
     */
    public void setId_estimacion(int id_estimacion) {
        this.id_estimacion = id_estimacion;
    }

    /**
     * @return the estatus
     */
    public int getEstatus() {
        return estatus;
    }

    /**
     * @param estatus the estatus to set
     */
    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    /**
     * @return the pctIva
     */
    public float getPctIva() {
        return pctIva;
    }

    /**
     * @param pctIva the pctIva to set
     */
    public void setPctIva(float pctIva) {
        this.pctIva = pctIva;
    }

    /**
     * @return the id_factura
     */
    public int getId_factura() {
        return id_factura;
    }

    /**
     * @param id_factura the id_factura to set
     */
    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }
    
}
