/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.math.BigDecimal;


public class InsumoExplosion {
    
    private int id_insumo;
    private String cuenta;
    private String codInsumo;
    private String descripcion;
    private String unidadIns;
    private BigDecimal precioUnit;
    private BigDecimal impInsCtrl;
    private BigDecimal cantInsCtrl; //Cantidad en la matriz
    private BigDecimal cantCtrl; //Cantidad en el concepto
    private int id_concepto;
    private BigDecimal total;
    private BigDecimal sumaCant;
    private BigDecimal cantConcepto;
    private int id_presupuesto;
    
    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getCodInsumo() {
        return codInsumo;
    }

    public void setCodInsumo(String codInsumo) {
        this.codInsumo = codInsumo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecioUnit() {
        return precioUnit;
    }

    public void setPrecioUnit(BigDecimal precioUnit) {
        this.precioUnit = precioUnit;
    }

    public BigDecimal getSumaCant() {
        return sumaCant;
    }

    public void setSumaCant(BigDecimal sumaCant) {
        this.sumaCant = sumaCant;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getCantConcepto() {
        return cantConcepto;
    }

    public void setCantConcepto(BigDecimal cantConcepto) {
        this.cantConcepto = cantConcepto;
    }

    public int getId_insumo() {
        return id_insumo;
    }

    public void setId_insumo(int id_insumo) {
        this.id_insumo = id_insumo;
    }

    public BigDecimal getImpInsCtrl() {
        return impInsCtrl;
    }

    public void setImpInsCtrl(BigDecimal impInsCtrl) {
        this.impInsCtrl = impInsCtrl;
    }

    public BigDecimal getCantCtrl() {
        return cantCtrl;
    }

    public void setCantCtrl(BigDecimal cantCtrl) {
        this.cantCtrl = cantCtrl;
    }

    public int getId_concepto() {
        return id_concepto;
    }

    public void setId_concepto(int id_concepto) {
        this.id_concepto = id_concepto;
    }

    public String getUnidadIns() {
        return unidadIns;
    }

    public void setUnidadIns(String unidadIns) {
        this.unidadIns = unidadIns;
    }

    public BigDecimal getCantInsCtrl() {
        return cantInsCtrl;
    }

    public void setCantInsCtrl(BigDecimal cantInsCtrl) {
        this.cantInsCtrl = cantInsCtrl;
    }

    /**
     * @return the id_presupuesto
     */
    public int getId_presupuesto() {
        return id_presupuesto;
    }

    /**
     * @param id_presupuesto the id_presupuesto to set
     */
    public void setId_presupuesto(int id_presupuesto) {
        this.id_presupuesto = id_presupuesto;
    }
    
}
