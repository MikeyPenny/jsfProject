/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.util.Constantes;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Mickey
 */
@ManagedBean
@ViewScoped
public class FormRequisicionMB implements Serializable {

    private int idProyecto;
    private int idPresupuesto;
    private int idRequisicion;
    private String estatus;
    private String fechaSolicitud;
    private String fechaAutorizacion;
    private int idUsuarioSolicitante;
    private String nombreUsuarioSolicitante;
    private int idUsuarioAutorizacion;
    private String nombreUsuarioAutorizacion;
    
    public FormRequisicionMB() {
        Date fecha = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy H:m:s");
        nombreUsuarioSolicitante = "Hugo F.G.";
        nombreUsuarioAutorizacion = "NO DISPONIBLE";
        fechaAutorizacion = "Sin Fecha de Autorización";
        fechaSolicitud = sdf.format(fecha);
        estatus = Constantes._LBL_PTE_X_AUT;
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
     * @return the nombreUsuarioSolicitante
     */
    public String getNombreUsuarioSolicitante() {
        return nombreUsuarioSolicitante;
    }

    /**
     * @param nombreUsuarioSolicitante the nombreUsuarioSolicitante to set
     */
    public void setNombreUsuarioSolicitante(String nombreUsuarioSolicitante) {
        this.nombreUsuarioSolicitante = nombreUsuarioSolicitante;
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
     * @return the nombreUsuarioAutorizacion
     */
    public String getNombreUsuarioAutorizacion() {
        return nombreUsuarioAutorizacion;
    }

    /**
     * @param nombreUsuarioAutorizacion the nombreUsuarioAutorizacion to set
     */
    public void setNombreUsuarioAutorizacion(String nombreUsuarioAutorizacion) {
        this.nombreUsuarioAutorizacion = nombreUsuarioAutorizacion;
    }
    
}
