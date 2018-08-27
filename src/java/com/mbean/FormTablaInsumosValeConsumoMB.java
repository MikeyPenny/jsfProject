/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


@ManagedBean
@RequestScoped
public class FormTablaInsumosValeConsumoMB implements Serializable {
    
    private int idInsumoRequisicion;
    private BigDecimal cantidad;
    private BigDecimal cantidadOriginal;
    private String claveInsumo;
    private String nombreInsumo;
    private String estatus;
    private String unidades;
    
    public FormTablaInsumosValeConsumoMB() {
    }

    /**
     * @return the idInsumoRequisicion
     */
    public int getIdInsumoRequisicion() {
        return idInsumoRequisicion;
    }

    /**
     * @param idInsumoRequisicion the idInsumoRequisicion to set
     */
    public void setIdInsumoRequisicion(int idInsumoRequisicion) {
        this.idInsumoRequisicion = idInsumoRequisicion;
    }

    /**
     * @return the cantidad
     */
    public BigDecimal getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the cantidadOriginal
     */
    public BigDecimal getCantidadOriginal() {
        return cantidadOriginal;
    }

    /**
     * @param cantidadOriginal the cantidadOriginal to set
     */
    public void setCantidadOriginal(BigDecimal cantidadOriginal) {
        this.cantidadOriginal = cantidadOriginal;
    }

    /**
     * @return the claveInsumo
     */
    public String getClaveInsumo() {
        return claveInsumo;
    }

    /**
     * @param claveInsumo the claveInsumo to set
     */
    public void setClaveInsumo(String claveInsumo) {
        this.claveInsumo = claveInsumo;
    }

    /**
     * @return the nombreInsumo
     */
    public String getNombreInsumo() {
        return nombreInsumo;
    }

    /**
     * @param nombreInsumo the nombreInsumo to set
     */
    public void setNombreInsumo(String nombreInsumo) {
        this.nombreInsumo = nombreInsumo;
    }

    /**
     * @return the estatus
     */
    public String getEstatus() {
        return estatus;
    }

    /**
     * @param estatus the estatus to set
     */
    public void setEstatus(String estatus) {
        this.estatus = estatus;
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
    
}
