
package com.bean;

import java.math.BigDecimal;


public class FacturaBean {
    
    private int id_factura;
    private String noFactura;
    private BigDecimal importeFactura;
    private BigDecimal amortAntcpo;
    private BigDecimal amortAntcpoFactura;
    private String contratista;
    private String origenFactura;
    private String tipoFactura;
    private BigDecimal iva;
    private BigDecimal ivaFactura;
    private BigDecimal rtnFlete;
    private BigDecimal rtnRenta;
    private BigDecimal rtnGarantia;
    private BigDecimal retFlete;
    private BigDecimal retRenta;
    private BigDecimal retFdoGtia;
    private BigDecimal subtotal;
    private BigDecimal importeTotal;
    private String estatusFact;
    private String fechaFactura;
    private int id_foraneo;
    private int id_solicitud;
    private BigDecimal sumaMovs;
    private BigDecimal pasivo;
    private BigDecimal impAutorizado;
    private String referencias;

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public String getNoFactura() {
        return noFactura;
    }

    public void setNoFactura(String noFactura) {
        this.noFactura = noFactura;
    }

    public BigDecimal getImporteFactura() {
        return importeFactura;
    }

    public void setImporteFactura(BigDecimal importeFactura) {
        this.importeFactura = importeFactura;
    }

    public BigDecimal getAmortAntcpoFactura() {
        return amortAntcpoFactura;
    }

    public void setAmortAntcpoFactura(BigDecimal amortAntcpoFactura) {
        this.amortAntcpoFactura = amortAntcpoFactura;
    }

    public String getContratista() {
        return contratista;
    }

    public void setContratista(String contratista) {
        this.contratista = contratista;
    }

    public String getOrigenFactura() {
        return origenFactura;
    }

    public void setOrigenFactura(String origenFactura) {
        this.origenFactura = origenFactura;
    }

    public String getTipoFactura() {
        return tipoFactura;
    }

    public void setTipoFactura(String tipoFactura) {
        this.tipoFactura = tipoFactura;
    }

    public BigDecimal getIvaFactura() {
        return ivaFactura;
    }

    public void setIvaFactura(BigDecimal ivaFactura) {
        this.ivaFactura = ivaFactura;
    }

    public BigDecimal getRetFlete() {
        return retFlete;
    }

    public void setRetFlete(BigDecimal retFlete) {
        this.retFlete = retFlete;
    }

    public BigDecimal getRetRenta() {
        return retRenta;
    }

    public void setRetRenta(BigDecimal retRenta) {
        this.retRenta = retRenta;
    }

    public BigDecimal getRetFdoGtia() {
        return retFdoGtia;
    }

    public void setRetFdoGtia(BigDecimal retFdoGtia) {
        this.retFdoGtia = retFdoGtia;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public String getEstatusFact() {
        return estatusFact;
    }

    public void setEstatusFact(String estatusFact) {
        this.estatusFact = estatusFact;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public int getId_foraneo() {
        return id_foraneo;
    }

    public void setId_foraneo(int id_foraneo) {
        this.id_foraneo = id_foraneo;
    }

    public int getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(int id_solicitud) {
        this.id_solicitud = id_solicitud;
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
     * @return the rtnFlete
     */
    public BigDecimal getRtnFlete() {
        return rtnFlete;
    }

    /**
     * @param rtnFlete the rtnFlete to set
     */
    public void setRtnFlete(BigDecimal rtnFlete) {
        this.rtnFlete = rtnFlete;
    }

    /**
     * @return the rtnRenta
     */
    public BigDecimal getRtnRenta() {
        return rtnRenta;
    }

    /**
     * @param rtnRenta the rtnRenta to set
     */
    public void setRtnRenta(BigDecimal rtnRenta) {
        this.rtnRenta = rtnRenta;
    }

    /**
     * @return the rtnGarantia
     */
    public BigDecimal getRtnGarantia() {
        return rtnGarantia;
    }

    /**
     * @param rtnGarantia the rtnGarantia to set
     */
    public void setRtnGarantia(BigDecimal rtnGarantia) {
        this.rtnGarantia = rtnGarantia;
    }

    /**
     * @return the sumaMovs
     */
    public BigDecimal getSumaMovs() {
        return sumaMovs;
    }

    /**
     * @param sumaMovs the sumaMovs to set
     */
    public void setSumaMovs(BigDecimal sumaMovs) {
        this.sumaMovs = sumaMovs;
    }

    /**
     * @return the pasivo
     */
    public BigDecimal getPasivo() {
        return pasivo;
    }

    /**
     * @param pasivo the pasivo to set
     */
    public void setPasivo(BigDecimal pasivo) {
        this.pasivo = pasivo;
    }

    /**
     * @return the impAutorizado
     */
    public BigDecimal getImpAutorizado() {
        return impAutorizado;
    }

    /**
     * @param impAutorizado the impAutorizado to set
     */
    public void setImpAutorizado(BigDecimal impAutorizado) {
        this.impAutorizado = impAutorizado;
    }

    /**
     * @return the referencias
     */
    public String getReferencias() {
        return referencias;
    }

    /**
     * @param referencias the referencias to set
     */
    public void setReferencias(String referencias) {
        this.referencias = referencias;
    }
    
}
