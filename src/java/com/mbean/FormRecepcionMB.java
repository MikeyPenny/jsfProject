/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean
@ViewScoped
public class FormRecepcionMB implements Serializable {

    private int idProyecto;
    private String nombreProyecto;
    private int numOrc;
    private int requisicion;
    private int noRecepcion;
    private String nombreProveedor;
    private String fechaRecepcion;
    
    public FormRecepcionMB() {
        Date fecha = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy H:m:s");
        fechaRecepcion = sdf.format(fecha);
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
     * @return the numOrc
     */
    public int getNumOrc() {
        return numOrc;
    }

    /**
     * @param numOrc the numOrc to set
     */
    public void setNumOrc(int numOrc) {
        this.numOrc = numOrc;
    }

    /**
     * @return the requisicion
     */
    public int getRequisicion() {
        return requisicion;
    }

    /**
     * @param requisicion the requisicion to set
     */
    public void setRequisicion(int requisicion) {
        this.requisicion = requisicion;
    }

    /**
     * @return the noRecepcion
     */
    public int getNoRecepcion() {
        return noRecepcion;
    }

    /**
     * @param noRecepcion the noRecepcion to set
     */
    public void setNoRecepcion(int noRecepcion) {
        this.noRecepcion = noRecepcion;
    }

    /**
     * @return the nombreProveedor
     */
    public String getNombreProveedor() {
        return nombreProveedor;
    }

    /**
     * @param nombreProveedor the nombreProveedor to set
     */
    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    /**
     * @return the fechaRecepcion
     */
    public String getFechaRecepcion() {
        return fechaRecepcion;
    }

    /**
     * @param fechaRecepcion the fechaRecepcion to set
     */
    public void setFechaRecepcion(String fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }
}
