/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.math.BigDecimal;


public class InsumoBean {
    
    private int id_insumo;
    private String cuenta;
    private String codInsumo;
    private String descripIns;
    private String unidadIns;
    private BigDecimal costoIns;
    private BigDecimal cantIns;
    private BigDecimal importeIns;
    private BigDecimal costoInsCtrl;
    private BigDecimal cantInsCtrl;
    private BigDecimal impInsCtrl;
    private String noConcepto;
    private int id_concepto;

    /**
     * @return the id_insumo
     */
    public int getId_insumo() {
        return id_insumo;
    }

    /**
     * @param id_insumo the id_insumo to set
     */
    public void setId_insumo(int id_insumo) {
        this.id_insumo = id_insumo;
    }

    /**
     * @return the cuenta
     */
    public String getCuenta() {
        return cuenta;
    }

    /**
     * @param cuenta the cuenta to set
     */
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    /**
     * @return the codInsumo
     */
    public String getCodInsumo() {
        return codInsumo;
    }

    /**
     * @param codInsumo the codInsumo to set
     */
    public void setCodInsumo(String codInsumo) {
        this.codInsumo = codInsumo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripIns() {
        return descripIns;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripIns(String descripIns) {
        this.descripIns = descripIns;
    }

    /**
     * @return the unidadIns
     */
    public String getUnidadIns() {
        return unidadIns;
    }

    /**
     * @param unidadIns the unidadIns to set
     */
    public void setUnidadIns(String unidadIns) {
        this.unidadIns = unidadIns;
    }

    /**
     * @return the costoIns
     */
    public BigDecimal getCostoIns() {
        return costoIns;
    }

    /**
     * @param costoIns the costoIns to set
     */
    public void setCostoIns(BigDecimal costoIns) {
        this.costoIns = costoIns;
    }

    /**
     * @return the cantIns
     */
    public BigDecimal getCantIns() {
        return cantIns;
    }

    /**
     * @param cantIns the cantIns to set
     */
    public void setCantIns(BigDecimal cantIns) {
        this.cantIns = cantIns;
    }

    /**
     * @return the importeIns
     */
    public BigDecimal getImporteIns() {
        return importeIns;
    }

    /**
     * @param importeIns the importeIns to set
     */
    public void setImporteIns(BigDecimal importeIns) {
        this.importeIns = importeIns;
    }

    /**
     * @return the costoInsCtrl
     */
    public BigDecimal getCostoInsCtrl() {
        return costoInsCtrl;
    }

    /**
     * @param costoInsCtrl the costoInsCtrl to set
     */
    public void setCostoInsCtrl(BigDecimal costoInsCtrl) {
        this.costoInsCtrl = costoInsCtrl;
    }

    /**
     * @return the cantInsCtrl
     */
    public BigDecimal getCantInsCtrl() {
        return cantInsCtrl;
    }

    /**
     * @param cantInsCtrl the cantInsCtrl to set
     */
    public void setCantInsCtrl(BigDecimal cantInsCtrl) {
        this.cantInsCtrl = cantInsCtrl;
    }

    /**
     * @return the impInsCtrl
     */
    public BigDecimal getImpInsCtrl() {
        return impInsCtrl;
    }

    /**
     * @param impInsCtrl the impInsCtrl to set
     */
    public void setImpInsCtrl(BigDecimal impInsCtrl) {
        this.impInsCtrl = impInsCtrl;
    }

    /**
     * @return the noConcepto
     */
    public String getNoConcepto() {
        return noConcepto;
    }

    /**
     * @param noConcepto the noConcepto to set
     */
    public void setNoConcepto(String noConcepto) {
        this.noConcepto = noConcepto;
    }

    /**
     * @return the id_concepto
     */
    public int getId_concepto() {
        return id_concepto;
    }

    /**
     * @param id_concepto the id_concepto to set
     */
    public void setId_concepto(int id_concepto) {
        this.id_concepto = id_concepto;
    }
    
}
