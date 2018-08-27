/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.io.Serializable;
import java.math.BigDecimal;


public class MovimientoCteBean implements Serializable {
    
    private int id_movimiento;
    private BigDecimal importe;
    private String fechaMov;
    private int id_factura;

    /**
     * @return the id_movimiento
     */
    public int getId_movimiento() {
        return id_movimiento;
    }

    /**
     * @param id_movimiento the id_movimiento to set
     */
    public void setId_movimiento(int id_movimiento) {
        this.id_movimiento = id_movimiento;
    }

    /**
     * @return the importe
     */
    public BigDecimal getImporte() {
        return importe;
    }

    /**
     * @param importe the importe to set
     */
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
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
