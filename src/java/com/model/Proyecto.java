/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


public class Proyecto implements Serializable {
    
    private Integer idProyecto;
    private String proyecto;
    private String numContrato;
    private String tipoContrato;
    private BigDecimal importeContto;
    private String formaDpago;
    private BigDecimal anticipo;
    private String fechaInicio;
    private String fechaFin;
    private String cliente;
    private BigDecimal pctGarantia;
    private BigDecimal impGarantia;
    private BigDecimal pctFianzaAntcpo;
    private BigDecimal impFianzaAntcpo;
    private BigDecimal pctCumplimiento;
    private BigDecimal impCumplimiento;
    private BigDecimal pctVicios;
    private BigDecimal impVicios;
    private BigDecimal pctRespCivil;
    private BigDecimal impRespCivil;
    private BigDecimal pctTerceros;
    private BigDecimal impTerceros;
    private String otros;
    private Set presupuestos = new HashSet(0);
    private Set almacens = new HashSet(0);
    
    public Proyecto() {
    }
    
    public Proyecto(String proyecto, String numContrato, String tipoContrato, BigDecimal importeContto, String formaDpago, BigDecimal anticipo, String fechaInicio, String fechaFin, String cliente, BigDecimal pctGarantia, BigDecimal impGarantia, BigDecimal pctFianzaAntcpo, BigDecimal impFianzaAntcpo, BigDecimal pctCumplimiento, BigDecimal impCumplimiento, BigDecimal pctVicios, BigDecimal impVicios, BigDecimal pctRespCivil, BigDecimal impRespCivil, BigDecimal pctTerceros, BigDecimal impTerceros, String otros, Set presupuestos, Set almacens) {
       this.proyecto = proyecto;
       this.numContrato = numContrato;
       this.tipoContrato = tipoContrato;
       this.importeContto = importeContto;
       this.formaDpago = formaDpago;
       this.anticipo = anticipo;
       this.fechaInicio = fechaInicio;
       this.fechaFin = fechaFin;
       this.cliente = cliente;
       this.pctGarantia = pctGarantia;
       this.impGarantia = impGarantia;
       this.pctFianzaAntcpo = pctFianzaAntcpo;
       this.impFianzaAntcpo = impFianzaAntcpo;
       this.pctCumplimiento = pctCumplimiento;
       this.impCumplimiento = impCumplimiento;
       this.pctVicios = pctVicios;
       this.impVicios = impVicios;
       this.pctRespCivil = pctRespCivil;
       this.impRespCivil = impRespCivil;
       this.pctTerceros = pctTerceros;
       this.impTerceros = impTerceros;
       this.otros = otros;
       this.presupuestos = presupuestos;
       this.almacens = almacens;
    }

    /**
     * @return the idProyecto
     */
    public Integer getIdProyecto() {
        return idProyecto;
    }

    /**
     * @param idProyecto the idProyecto to set
     */
    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

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
     * @return the tipoContrato
     */
    public String getTipoContrato() {
        return tipoContrato;
    }

    /**
     * @param tipoContrato the tipoContrato to set
     */
    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
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
     * @return the formaDpago
     */
    public String getFormaDpago() {
        return formaDpago;
    }

    /**
     * @param formaDpago the formaDpago to set
     */
    public void setFormaDpago(String formaDpago) {
        this.formaDpago = formaDpago;
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
     * @return the fechaInicio
     */
    public String getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public String getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
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
    public BigDecimal getPctGarantia() {
        return pctGarantia;
    }

    /**
     * @param pctGarantia the pctGarantia to set
     */
    public void setPctGarantia(BigDecimal pctGarantia) {
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
    public BigDecimal getPctFianzaAntcpo() {
        return pctFianzaAntcpo;
    }

    /**
     * @param pctFianzaAntcpo the pctFianzaAntcpo to set
     */
    public void setPctFianzaAntcpo(BigDecimal pctFianzaAntcpo) {
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
    public BigDecimal getPctCumplimiento() {
        return pctCumplimiento;
    }

    /**
     * @param pctCumplimiento the pctCumplimiento to set
     */
    public void setPctCumplimiento(BigDecimal pctCumplimiento) {
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
    public BigDecimal getPctVicios() {
        return pctVicios;
    }

    /**
     * @param pctVicios the pctVicios to set
     */
    public void setPctVicios(BigDecimal pctVicios) {
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
    public BigDecimal getPctRespCivil() {
        return pctRespCivil;
    }

    /**
     * @param pctRespCivil the pctRespCivil to set
     */
    public void setPctRespCivil(BigDecimal pctRespCivil) {
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
    public BigDecimal getPctTerceros() {
        return pctTerceros;
    }

    /**
     * @param pctTerceros the pctTerceros to set
     */
    public void setPctTerceros(BigDecimal pctTerceros) {
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
     * @return the presupuestos
     */
    public Set getPresupuestos() {
        return presupuestos;
    }

    /**
     * @param presupuestos the presupuestos to set
     */
    public void setPresupuestos(Set presupuestos) {
        this.presupuestos = presupuestos;
    }

    /**
     * @return the almacens
     */
    public Set getAlmacens() {
        return almacens;
    }

    /**
     * @param almacens the almacens to set
     */
    public void setAlmacens(Set almacens) {
        this.almacens = almacens;
    }
    
}
