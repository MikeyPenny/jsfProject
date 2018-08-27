/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;


public class SelectBean {
    
    private String beneficiario;
    private String fechaDe;
    private String fechaA;
    private int id_proyecto;
    private String centroCosto;
    private String factura;
    private String estatusFactura;
    private String cuenta;
    private int id_solpago;

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public String getFechaDe() {
        return fechaDe;
    }

    public void setFechaDe(String fechaDe) {
        this.fechaDe = fechaDe;
    }

    public String getFechaA() {
        return fechaA;
    }

    public void setFechaA(String fechaA) {
        this.fechaA = fechaA;
    }

    public int getId_proyecto() {
        return id_proyecto;
    }

    public void setId_proyecto(int id_proyecto) {
        this.id_proyecto = id_proyecto;
    }

    public String getCentroCosto() {
        return centroCosto;
    }

    public void setCentroCosto(String centroCosto) {
        this.centroCosto = centroCosto;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getEstatusFactura() {
        return estatusFactura;
    }

    public void setEstatusFactura(String estatusFactura) {
        this.estatusFactura = estatusFactura;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public int getId_solpago() {
        return id_solpago;
    }

    public void setId_solpago(int id_solpago) {
        this.id_solpago = id_solpago;
    }
    
}
