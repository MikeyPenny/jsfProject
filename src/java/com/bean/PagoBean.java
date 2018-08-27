
package com.bean;

import java.math.BigDecimal;


public class PagoBean {
    
    private int id_pago;
    private BigDecimal importeEstimacion;
    private BigDecimal anticipoAmort;
    private float pctAmort;
    private BigDecimal retFdoGtia;
    private float fondoGtiaPct;
    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal retFlete;
    private BigDecimal retRenta;
    private BigDecimal importeTotal;
    private String fecha;
    private int nroEstimacion;
    private String contratista;
    private String tipo;
    private String noFactura;
    private String estatusFact;
    private String origen;
    private BigDecimal facturado;
    

    public int getId_pago() {
        return id_pago;
    }

    public void setId_pago(int id_pago) {
        this.id_pago = id_pago;
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

    public int getNroEstimacion() {
        return nroEstimacion;
    }

    public void setNroEstimacion(int nroEstimacion) {
        this.nroEstimacion = nroEstimacion;
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

    public float getFondoGtiaPct() {
        return fondoGtiaPct;
    }

    public void setFondoGtiaPct(float fondoGtiaPct) {
        this.fondoGtiaPct = fondoGtiaPct;
    }

    public float getPctAmort() {
        return pctAmort;
    }

    public void setPctAmort(float pctAmort) {
        this.pctAmort = pctAmort;
    }

    public String getNoFactura() {
        return noFactura;
    }

    public void setNoFactura(String noFactura) {
        this.noFactura = noFactura;
    }

    public String getEstatusFact() {
        return estatusFact;
    }

    public void setEstatusFact(String estatusFact) {
        this.estatusFact = estatusFact;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    /**
     * @return the facturado
     */
    public BigDecimal getFacturado() {
        return facturado;
    }

    /**
     * @param facturado the facturado to set
     */
    public void setFacturado(BigDecimal facturado) {
        this.facturado = facturado;
    }
    
}
