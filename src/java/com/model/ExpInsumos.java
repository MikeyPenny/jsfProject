/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Mickey
 */
public class ExpInsumos {
    
    private Integer idExpinsumos;
    private Presupuesto presupuesto;
    private String codInsumo;
    private String descripcion;
    private String unidades;
    private BigDecimal costoIns;
    private BigDecimal costoInsCtrl;
    private String cuenta;
    private BigDecimal total;
    private Set insumoRequisicions = new HashSet(0);
    private Set insumoValeConsumos = new HashSet(0);

    /**
     * @return the idExpinsumos
     */
    public Integer getIdExpinsumos() {
        return idExpinsumos;
    }

    /**
     * @param idExpinsumos the idExpinsumos to set
     */
    public void setIdExpinsumos(Integer idExpinsumos) {
        this.idExpinsumos = idExpinsumos;
    }

    /**
     * @return the presupuesto
     */
    public Presupuesto getPresupuesto() {
        return presupuesto;
    }

    /**
     * @param presupuesto the presupuesto to set
     */
    public void setPresupuesto(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;
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
     * @return the unidades
     */
    public String getUnidades() {
        return unidades;
    }

    /**
     * @param unidades the unidades to set
     */
    public void setUnidades(String unidades) {
        this.unidades = unidades;
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
     * @return the total
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * @return the insumoRequisicions
     */
    public Set getInsumoRequisicions() {
        return insumoRequisicions;
    }

    /**
     * @param insumoRequisicions the insumoRequisicions to set
     */
    public void setInsumoRequisicions(Set insumoRequisicions) {
        this.insumoRequisicions = insumoRequisicions;
    }

    /**
     * @return the insumoValeConsumos
     */
    public Set getInsumoValeConsumos() {
        return insumoValeConsumos;
    }

    /**
     * @param insumoValeConsumos the insumoValeConsumos to set
     */
    public void setInsumoValeConsumos(Set insumoValeConsumos) {
        this.insumoValeConsumos = insumoValeConsumos;
    }
    
}
