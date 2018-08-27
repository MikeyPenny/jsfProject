/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Mickey
 */
@ManagedBean
@ViewScoped
public class DetalleRequisicionTablaMB implements Serializable {

    private int idInsumo;
    private int idRequisicion;
    private String nombreInsumo;
    private BigDecimal cantidadSolicitada;
    private String unidades;
    private String claveInsumo;
    
    public DetalleRequisicionTablaMB() {
    }

    /**
     * @return the idInsumo
     */
    public int getIdInsumo() {
        return idInsumo;
    }

    /**
     * @param idInsumo the idInsumo to set
     */
    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    /**
     * @return the idRequisicion
     */
    public int getIdRequisicion() {
        return idRequisicion;
    }

    /**
     * @param idRequisicion the idRequisicion to set
     */
    public void setIdRequisicion(int idRequisicion) {
        this.idRequisicion = idRequisicion;
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
     * @return the cantidadSolicitada
     */
    public BigDecimal getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    /**
     * @param cantidadSolicitada the cantidadSolicitada to set
     */
    public void setCantidadSolicitada(BigDecimal cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
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
    
}
