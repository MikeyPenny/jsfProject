
package com.bean;

import java.math.BigDecimal;


public class AnticipoFacturaBean {
    
    private int id_pago;
    private BigDecimal importe;
    private BigDecimal amorAntcpo;
    private String contratista;
    private String tipo;
    private BigDecimal iva;
    private BigDecimal rtnRenta;
    private BigDecimal rtnFlete;
    private BigDecimal total;

    public int getId_pago() {
        return id_pago;
    }

    public void setId_pago(int id_pago) {
        this.id_pago = id_pago;
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

    public BigDecimal getAmorAntcpo() {
        return amorAntcpo;
    }

    public void setAmorAntcpo(BigDecimal amorAntcpo) {
        this.amorAntcpo = amorAntcpo;
    }
    
}
