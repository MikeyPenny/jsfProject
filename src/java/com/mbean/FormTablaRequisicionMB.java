/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Mickey
 */
@ManagedBean
@ViewScoped
public class FormTablaRequisicionMB implements Serializable {

    private int idRequicision;
    private int noRequisicion;
    private String fechaSolicitud;
    private String estatus;
    private String fechaAutorizacion;
    private int idUsuarioAutorizacion;
    private String usuarioAutorizacion;
    private int idUsuarioSolicitante;
    private String usuarioSolicitante;
    private String nomProyecto;
    private int idPresupuesto;
    private String nomPresupuesto;
    
    public FormTablaRequisicionMB() {
    }

    /**
     * @return the idRequicision
     */
    public int getIdRequicision() {
        return idRequicision;
    }

    /**
     * @param idRequicision the idRequicision to set
     */
    public void setIdRequicision(int idRequicision) {
        this.idRequicision = idRequicision;
    }

    /**
     * @return the noRequisicion
     */
    public int getNoRequisicion() {
        return noRequisicion;
    }

    /**
     * @param noRequisicion the noRequisicion to set
     */
    public void setNoRequisicion(int noRequisicion) {
        this.noRequisicion = noRequisicion;
    }

    /**
     * @return the fechaSolicitud
     */
    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    /**
     * @param fechaSolicitud the fechaSolicitud to set
     */
    public void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
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
    public int getIdUsuarioAutorizacion() {
        return idUsuarioAutorizacion;
    }

    /**
     * @param idUsuarioAutorizacion the idUsuarioAutorizacion to set
     */
    public void setIdUsuarioAutorizacion(int idUsuarioAutorizacion) {
        this.idUsuarioAutorizacion = idUsuarioAutorizacion;
    }

    /**
     * @return the usuarioAutorizacion
     */
    public String getUsuarioAutorizacion() {
        return usuarioAutorizacion;
    }

    /**
     * @param usuarioAutorizacion the usuarioAutorizacion to set
     */
    public void setUsuarioAutorizacion(String usuarioAutorizacion) {
        this.usuarioAutorizacion = usuarioAutorizacion;
    }

    /**
     * @return the idUsuarioSolicitante
     */
    public int getIdUsuarioSolicitante() {
        return idUsuarioSolicitante;
    }

    /**
     * @param idUsuarioSolicitante the idUsuarioSolicitante to set
     */
    public void setIdUsuarioSolicitante(int idUsuarioSolicitante) {
        this.idUsuarioSolicitante = idUsuarioSolicitante;
    }

    /**
     * @return the usuarioSolicitante
     */
    public String getUsuarioSolicitante() {
        return usuarioSolicitante;
    }

    /**
     * @param usuarioSolicitante the usuarioSolicitante to set
     */
    public void setUsuarioSolicitante(String usuarioSolicitante) {
        this.usuarioSolicitante = usuarioSolicitante;
    }

    /**
     * @return the nomProyecto
     */
    public String getNomProyecto() {
        return nomProyecto;
    }

    /**
     * @param nomProyecto the nomProyecto to set
     */
    public void setNomProyecto(String nomProyecto) {
        this.nomProyecto = nomProyecto;
    }

    /**
     * @return the idPresupuesto
     */
    public int getIdPresupuesto() {
        return idPresupuesto;
    }

    /**
     * @param idPresupuesto the idPresupuesto to set
     */
    public void setIdPresupuesto(int idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
    }

    /**
     * @return the nomPresupuesto
     */
    public String getNomPresupuesto() {
        return nomPresupuesto;
    }

    /**
     * @param nomPresupuesto the nomPresupuesto to set
     */
    public void setNomPresupuesto(String nomPresupuesto) {
        this.nomPresupuesto = nomPresupuesto;
    }
    
}
