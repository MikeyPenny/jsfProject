/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.io.Serializable;
import java.math.BigDecimal;


public class InsumoContrat implements Serializable {
    
    private int id_insumo;
    private String codInsumo;
    private String codConcepto;
    private String descripcion;
    private String unidad;
    private BigDecimal cantCtrl;
    private BigDecimal cantIns;
    private BigDecimal cantInsCtrl;
    private BigDecimal cantInsTotal;
    private BigDecimal cantConcepto;
    private BigDecimal presUnit;
    private BigDecimal cantContrato;
    private BigDecimal importeCont;
    private BigDecimal sumaAvance;
    private BigDecimal avance = BigDecimal.ZERO;
    private BigDecimal importeAvnce;
    private String estatusIns;
    private int id_avanceInsCont;
    private BigDecimal cantDispAvn;
    private int id_concepto;
    private int id_avance;
    private int idcontrato_insumo;

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
     * @return the codConcepto
     */
    public String getCodConcepto() {
        return codConcepto;
    }

    /**
     * @param codConcepto the codConcepto to set
     */
    public void setCodConcepto(String codConcepto) {
        this.codConcepto = codConcepto;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the unidad
     */
    public String getUnidad() {
        return unidad;
    }

    /**
     * @param unidad the unidad to set
     */
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    /**
     * @return the cantCtrl
     */
    public BigDecimal getCantCtrl() {
        return cantCtrl;
    }

    /**
     * @param cantCtrl the cantCtrl to set
     */
    public void setCantCtrl(BigDecimal cantCtrl) {
        this.cantCtrl = cantCtrl;
    }

    /**
     * @return the pUnitario
     */
    public BigDecimal getPresUnit() {
        return presUnit;
    }

    /**
     * @param pUnitario the pUnitario to set
     */
    public void setPresUnit(BigDecimal presUnit) {
        this.presUnit = presUnit;
    }

    /**
     * @return the cantContrato
     */
    public BigDecimal getCantContrato() {
        return cantContrato;
    }

    /**
     * @param cantContrato the cantContrato to set
     */
    public void setCantContrato(BigDecimal cantContrato) {
        this.cantContrato = cantContrato;
    }

    /**
     * @return the importeCont
     */
    public BigDecimal getImporteCont() {
        return importeCont;
    }

    /**
     * @param importeCont the importeCont to set
     */
    public void setImporteCont(BigDecimal importeCont) {
        this.importeCont = importeCont;
    }

    public BigDecimal getSumaAvance() {
        return sumaAvance;
    }

    public void setSumaAvance(BigDecimal sumaAvance) {
        this.sumaAvance = sumaAvance;
    }

    public BigDecimal getAvance() {
        return avance;
    }

    public void setAvance(BigDecimal avance) {
        this.avance = avance;
    }

    public BigDecimal getImporteAvnce() {
        return importeAvnce;
    }

    public void setImporteAvnce(BigDecimal importeAvnce) {
        this.importeAvnce = importeAvnce;
    }

    public String getEstatusIns() {
        return estatusIns;
    }

    public void setEstatusIns(String estatusIns) {
        this.estatusIns = estatusIns;
    }

    public int getId_avanceInsCont() {
        return id_avanceInsCont;
    }

    public void setId_avanceInsCont(int id_avanceInsCont) {
        this.id_avanceInsCont = id_avanceInsCont;
    }

    public BigDecimal getCantInsCtrl() {
        return cantInsCtrl;
    }

    public void setCantInsCtrl(BigDecimal cantInsCtrl) {
        this.cantInsCtrl = cantInsCtrl;
    }

    public BigDecimal getCantInsTotal() {
        return cantInsTotal;
    }

    public void setCantInsTotal(BigDecimal cantInsTotal) {
        this.cantInsTotal = cantInsTotal;
    }

    public BigDecimal getCantConcepto() {
        return cantConcepto;
    }

    public void setCantConcepto(BigDecimal cantConcepto) {
        this.cantConcepto = cantConcepto;
    }

    public BigDecimal getCantDispAvn() {
        return cantDispAvn;
    }

    public void setCantDispAvn(BigDecimal cantDispAvn) {
        this.cantDispAvn = cantDispAvn;
    }

    public int getId_concepto() {
        return id_concepto;
    }

    public void setId_concepto(int id_concepto) {
        this.id_concepto = id_concepto;
    }

    /**
     * @return the id_avance
     */
    public int getId_avance() {
        return id_avance;
    }

    /**
     * @param id_avance the id_avance to set
     */
    public void setId_avance(int id_avance) {
        this.id_avance = id_avance;
    }

    /**
     * @return the idcontrato_insumo
     */
    public int getIdcontrato_insumo() {
        return idcontrato_insumo;
    }

    /**
     * @param idcontrato_insumo the idcontrato_insumo to set
     */
    public void setIdcontrato_insumo(int idcontrato_insumo) {
        this.idcontrato_insumo = idcontrato_insumo;
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
    
}
