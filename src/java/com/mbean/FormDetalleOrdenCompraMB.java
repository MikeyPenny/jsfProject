/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean
@ViewScoped
public class FormDetalleOrdenCompraMB {

   private int idOrdenCompra;
    private int idProyecto;
    private String nombreProyecto;
    private int idProveedor;
    private String nombreProvedor;
    private int requisicion;
    
    public FormDetalleOrdenCompraMB() {
    }

    /**
     * @return the idOrdenCompra
     */
    public int getIdOrdenCompra() {
        return idOrdenCompra;
    }

    /**
     * @param idOrdenCompra the idOrdenCompra to set
     */
    public void setIdOrdenCompra(int idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
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
     * @return the idProveedor
     */
    public int getIdProveedor() {
        return idProveedor;
    }

    /**
     * @param idProveedor the idProveedor to set
     */
    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    /**
     * @return the nombreProvedor
     */
    public String getNombreProvedor() {
        return nombreProvedor;
    }

    /**
     * @param nombreProvedor the nombreProvedor to set
     */
    public void setNombreProvedor(String nombreProvedor) {
        this.nombreProvedor = nombreProvedor;
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
}
