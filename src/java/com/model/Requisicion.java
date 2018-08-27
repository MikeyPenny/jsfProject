/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Mickey
 */
public class Requisicion {
    
    private Integer idRequisicion;
    private Presupuesto presupuesto;
    private Integer noRequisicion;
    private String fechasolicitud;
    private String estatus;
    private String fechaAutorizacion;
    private Integer idUsuarioAutorizacion;
    private Integer idUsuarioSolicitante;
    private Set ordenCompras = new HashSet(0);
    private Set insumoRequisicions = new HashSet(0);
    
    public Requisicion() {
    }

    /**
     * @return the idRequisicion
     */
    public Integer getIdRequisicion() {
        return idRequisicion;
    }
    
    public Requisicion(Presupuesto presupuesto, Integer noRequisicion, String fechasolicitud, String estatus, String fechaAutorizacion, Integer idUsuarioAutorizacion, Integer idUsuarioSolicitante, Set ordenCompras, Set insumoRequisicions) {
       this.presupuesto = presupuesto;
       this.noRequisicion = noRequisicion;
       this.fechasolicitud = fechasolicitud;
       this.estatus = estatus;
       this.fechaAutorizacion = fechaAutorizacion;
       this.idUsuarioAutorizacion = idUsuarioAutorizacion;
       this.idUsuarioSolicitante = idUsuarioSolicitante;
       this.ordenCompras = ordenCompras;
       this.insumoRequisicions = insumoRequisicions;
    }

    /**
     * @param idRequisicion the idRequisicion to set
     */
    public void setIdRequisicion(Integer idRequisicion) {
        this.idRequisicion = idRequisicion;
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
     * @return the noRequisicion
     */
    public Integer getNoRequisicion() {
        return noRequisicion;
    }

    /**
     * @param noRequisicion the noRequisicion to set
     */
    public void setNoRequisicion(Integer noRequisicion) {
        this.noRequisicion = noRequisicion;
    }

    /**
     * @return the fechasolicitud
     */
    public String getFechasolicitud() {
        return fechasolicitud;
    }

    /**
     * @param fechasolicitud the fechasolicitud to set
     */
    public void setFechasolicitud(String fechasolicitud) {
        this.fechasolicitud = fechasolicitud;
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
     * @return the fechaAutorizacion
     */
    public String getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    /**
     * @param fechaAutorizacion the fechaAutorizacion to set
     */
    public void setFechaAutorizacion(String fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    /**
     * @return the idUsuarioAutorizacion
     */
    public Integer getIdUsuarioAutorizacion() {
        return idUsuarioAutorizacion;
    }

    /**
     * @param idUsuarioAutorizacion the idUsuarioAutorizacion to set
     */
    public void setIdUsuarioAutorizacion(Integer idUsuarioAutorizacion) {
        this.idUsuarioAutorizacion = idUsuarioAutorizacion;
    }

    /**
     * @return the idUsuarioSolicitante
     */
    public Integer getIdUsuarioSolicitante() {
        return idUsuarioSolicitante;
    }

    /**
     * @param idUsuarioSolicitante the idUsuarioSolicitante to set
     */
    public void setIdUsuarioSolicitante(Integer idUsuarioSolicitante) {
        this.idUsuarioSolicitante = idUsuarioSolicitante;
    }

    /**
     * @return the ordenCompras
     */
    public Set getOrdenCompras() {
        return ordenCompras;
    }

    /**
     * @param ordenCompras the ordenCompras to set
     */
    public void setOrdenCompras(Set ordenCompras) {
        this.ordenCompras = ordenCompras;
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
    
}
