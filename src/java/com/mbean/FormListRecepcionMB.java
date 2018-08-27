/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean
@ViewScoped
public class FormListRecepcionMB implements Serializable {

    private int idRecepcion;
    private int noRecepcion;
    private String estatus;
    private String fechaRecepcion;
    private int idOrdenCompra;
    private String proveedor;
    
    public FormListRecepcionMB() {
    }

    /**
     * @return the idRecepcion
     */
    public int getIdRecepcion() {
        return idRecepcion;
    }

    /**
     * @param idRecepcion the idRecepcion to set
     */
    public void setIdRecepcion(int idRecepcion) {
        this.idRecepcion = idRecepcion;
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
     * @return the proveedor
     */
    public String getProveedor() {
        return proveedor;
    }

    /**
     * @param proveedor the proveedor to set
     */
    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }
    
}
