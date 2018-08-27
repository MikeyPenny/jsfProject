/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class AltaBean implements Serializable {
    
    private int idProyecto;
    private String proyecto;
    private String codProy;
    private String direccion;
    private String contrato;
    private String numContrato;
    private BigDecimal importeContto;
    private String formaDPago;
    private float pctAntcpo;
    private BigDecimal anticipo;
    private Date fechInicio;
    private String fechaIniCad;
    private Date fechFin;
    private String fechaFinCad;
    private String cliente;
    private float pctGarantia;
    private BigDecimal impGarantia;
    private float pctFianzaAntcpo;
    private BigDecimal impFianzaAntcpo;
    private float pctCumplimiento;
    private BigDecimal impCumplimiento;
    private float pctVicios;
    private BigDecimal impVicios;
    private float pctRespCivil;
    private BigDecimal impRespCivil;
    private float pctTerceros;
    private BigDecimal impTerceros;
    private String otros;
    private BigDecimal otrosRiesg;
    private String cCostos;
    private int id_cliente;
    
    /**
     * @return the proyecto
     */
    public String getProyecto() {
        return proyecto;
    }

    /**
     * @param proyecto the proyecto to set
     */
    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    /**
     * @return the contrato
     */
    public String getContrato() {
        return contrato;
    }

    /**
     * @param contrato the contrato to set
     */
    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    /**
     * @return the numContrato
     */
    public String getNumContrato() {
        return numContrato;
    }

    /**
     * @param numContrato the numContrato to set
     */
    public void setNumContrato(String numContrato) {
        this.numContrato = numContrato;
    }

    /**
     * @return the importeContto
     */
    public BigDecimal getImporteContto() {
        return importeContto;
    }

    /**
     * @param importeContto the importeContto to set
     */
    public void setImporteContto(BigDecimal importeContto) {
        this.importeContto = importeContto;
    }

    /**
     * @return the formaDPago
     */
    public String getFormaDPago() {
        return formaDPago;
    }

    /**
     * @param formaDPago the formaDPago to set
     */
    public void setFormaDPago(String formaDPago) {
        this.formaDPago = formaDPago;
    }

    /**
     * @return the anticipo
     */
    public BigDecimal getAnticipo() {
        return anticipo;
    }

    /**
     * @param anticipo the anticipo to set
     */
    public void setAnticipo(BigDecimal anticipo) {
        this.anticipo = anticipo;
    }

    /**
     * @return the cliente
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the pctGarantia
     */
    public float getPctGarantia() {
        return pctGarantia;
    }

    /**
     * @param pctGarantia the pctGarantia to set
     */
    public void setPctGarantia(float pctGarantia) {
        this.pctGarantia = pctGarantia;
    }

    /**
     * @return the impGarantia
     */
    public BigDecimal getImpGarantia() {
        return impGarantia;
    }

    /**
     * @param impGarantia the impGarantia to set
     */
    public void setImpGarantia(BigDecimal impGarantia) {
        this.impGarantia = impGarantia;
    }

    /**
     * @return the pctFianzaAntcpo
     */
    public float getPctFianzaAntcpo() {
        return pctFianzaAntcpo;
    }

    /**
     * @param pctFianzaAntcpo the pctFianzaAntcpo to set
     */
    public void setPctFianzaAntcpo(float pctFianzaAntcpo) {
        this.pctFianzaAntcpo = pctFianzaAntcpo;
    }

    /**
     * @return the impFianzaAntcpo
     */
    public BigDecimal getImpFianzaAntcpo() {
        return impFianzaAntcpo;
    }

    /**
     * @param impFianzaAntcpo the impFianzaAntcpo to set
     */
    public void setImpFianzaAntcpo(BigDecimal impFianzaAntcpo) {
        this.impFianzaAntcpo = impFianzaAntcpo;
    }

    /**
     * @return the pctCumplimiento
     */
    public float getPctCumplimiento() {
        return pctCumplimiento;
    }

    /**
     * @param pctCumplimiento the pctCumplimiento to set
     */
    public void setPctCumplimiento(float pctCumplimiento) {
        this.pctCumplimiento = pctCumplimiento;
    }

    /**
     * @return the impCumplimiento
     */
    public BigDecimal getImpCumplimiento() {
        return impCumplimiento;
    }

    /**
     * @param impCumplimiento the impCumplimiento to set
     */
    public void setImpCumplimiento(BigDecimal impCumplimiento) {
        this.impCumplimiento = impCumplimiento;
    }

    /**
     * @return the pctVicios
     */
    public float getPctVicios() {
        return pctVicios;
    }

    /**
     * @param pctVicios the pctVicios to set
     */
    public void setPctVicios(float pctVicios) {
        this.pctVicios = pctVicios;
    }

    /**
     * @return the impVicios
     */
    public BigDecimal getImpVicios() {
        return impVicios;
    }

    /**
     * @param impVicios the impVicios to set
     */
    public void setImpVicios(BigDecimal impVicios) {
        this.impVicios = impVicios;
    }

    /**
     * @return the pctRespCivil
     */
    public float getPctRespCivil() {
        return pctRespCivil;
    }

    /**
     * @param pctRespCivil the pctRespCivil to set
     */
    public void setPctRespCivil(float pctRespCivil) {
        this.pctRespCivil = pctRespCivil;
    }

    /**
     * @return the impRespCivil
     */
    public BigDecimal getImpRespCivil() {
        return impRespCivil;
    }

    /**
     * @param impRespCivil the impRespCivil to set
     */
    public void setImpRespCivil(BigDecimal impRespCivil) {
        this.impRespCivil = impRespCivil;
    }

    /**
     * @return the pctTerceros
     */
    public float getPctTerceros() {
        return pctTerceros;
    }

    /**
     * @param pctTerceros the pctTerceros to set
     */
    public void setPctTerceros(float pctTerceros) {
        this.pctTerceros = pctTerceros;
    }

    /**
     * @return the impTerceros
     */
    public BigDecimal getImpTerceros() {
        return impTerceros;
    }

    /**
     * @param impTerceros the impTerceros to set
     */
    public void setImpTerceros(BigDecimal impTerceros) {
        this.impTerceros = impTerceros;
    }

    /**
     * @return the otros
     */
    public String getOtros() {
        return otros;
    }

    /**
     * @param otros the otros to set
     */
    public void setOtros(String otros) {
        this.otros = otros;
    }

    /**
     * @return the fechInicio
     */
    public Date getFechInicio() {
        return fechInicio;
    }

    /**
     * @param fechInicio the fechInicio to set
     */
    public void setFechInicio(Date fechInicio) {
        this.fechInicio = fechInicio;
    }

    /**
     * @return the fechFin
     */
    public Date getFechFin() {
        return fechFin;
    }

    /**
     * @param fechFin the fechFin to set
     */
    public void setFechFin(Date fechFin) {
        this.fechFin = fechFin;
    }

    /**
     * @return the idProyecto
     */
    public int getIdProyecto() {
        return idProyecto;
    }

    /**
     * @param idProyecto the idProyecto to set
     */
    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    /**
     * @return the pctAntcpo
     */
    public float getPctAntcpo() {
        return pctAntcpo;
    }

    /**
     * @param pctAntcpo the pctAntcpo to set
     */
    public void setPctAntcpo(float pctAntcpo) {
        this.pctAntcpo = pctAntcpo;
    }

    /**
     * @return the fechaIniCad
     */
    public String getFechaIniCad() {
        return fechaIniCad;
    }

    /**
     * @param fechaIniCad the fechaIniCad to set
     */
    public void setFechaIniCad(String fechaIniCad) {
        this.fechaIniCad = fechaIniCad;
    }

    /**
     * @return the fechaFinCad
     */
    public String getFechaFinCad() {
        return fechaFinCad;
    }

    /**
     * @param fechaFinCad the fechaFinCad to set
     */
    public void setFechaFinCad(String fechaFinCad) {
        this.fechaFinCad = fechaFinCad;
    }

    public BigDecimal getOtrosRiesg() {
        return otrosRiesg;
    }

    public void setOtrosRiesg(BigDecimal otrosRiesg) {
        this.otrosRiesg = otrosRiesg;
    }

    public String getcCostos() {
        return cCostos;
    }

    public void setcCostos(String cCostos) {
        this.cCostos = cCostos;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the id_cliente
     */
    public int getId_cliente() {
        return id_cliente;
    }

    /**
     * @param id_cliente the id_cliente to set
     */
    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    /**
     * @return the codProy
     */
    public String getCodProy() {
        return codProy;
    }

    /**
     * @param codProy the codProy to set
     */
    public void setCodProy(String codProy) {
        this.codProy = codProy;
    }
    
}
