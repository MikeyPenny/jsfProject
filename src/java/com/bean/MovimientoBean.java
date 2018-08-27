/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.math.BigDecimal;


public class MovimientoBean {
    
    private int id_movimiento;
    private String fechaPago;
    private BigDecimal importeMov;
    private String referencias;
    private int id_factura;

    public int getId_movimiento() {
        return id_movimiento;
    }

    public void setId_movimiento(int id_movimiento) {
        this.id_movimiento = id_movimiento;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public BigDecimal getImporteMov() {
        return importeMov;
    }

    public void setImporteMov(BigDecimal importeMov) {
        this.importeMov = importeMov;
    }

    public String getReferencias() {
        return referencias;
    }

    public void setReferencias(String referencias) {
        this.referencias = referencias;
    }

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }
    
}
