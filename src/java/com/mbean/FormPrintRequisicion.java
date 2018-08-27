
package com.mbean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean
@ViewScoped
public class FormPrintRequisicion implements Serializable {

    private int noRequisicion;
    private String nombreProyecto;
    private String estatus;
    private String fechaSolicitud;
    private String fechaAutorizacion;
    private String nombreUsuarioSolicitante;
    private String nombreUsuarioAutorizacion;
    
    public FormPrintRequisicion() {
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
     * @return the nombreProyecto
     */
    public String getNombreProyecto() {
        return nombreProyecto;
    }

    /**
     * @param nombreProyecto the nombreProyecto to set
     */
    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
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
