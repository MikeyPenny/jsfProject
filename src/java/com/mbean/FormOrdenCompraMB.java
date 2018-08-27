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
public class FormOrdenCompraMB implements Serializable {

    private int noOrdenCompra;
    private String fechaSolicitud;
    private int iva;
    private String formaPago;
    private int idProveedor;
    private String proveedor;
    private String dirEmbarque;
    private int idUsuarioSolicitante;
    private String usuarioSolicitante;
    private int idUsuarioAutorizacion;
    private String usuarioAutorizacion;
    private String estatus;
    
    public FormOrdenCompraMB() {
        iva = 16;
        estatus = Constantes._LBL_PTE_X_AUT;
        formaPago = "EFECTIVO";
        Date fecha = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy H:m:s");
        fechaSolicitud=sdf.format(fecha);
    }

    /**
     * @return the noOrdenCompra
     */
    public int getNoOrdenCompra() {
        return noOrdenCompra;
    }

    /**
     * @param noOrdenCompra the noOrdenCompra to set
     */
    public void setNoOrdenCompra(int noOrdenCompra) {
        this.noOrdenCompra = noOrdenCompra;
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
     * @return the iva
     */
    public int getIva() {
        return iva;
    }

    /**
     * @param iva the iva to set
     */
    public void setIva(int iva) {
        this.iva = iva;
    }

    /**
     * @return the formaPago
     */
    public String getFormaPago() {
        return formaPago;
    }

    /**
     * @param formaPago the formaPago to set
     */
    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
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

    /**
     * @return the dirEmbarque
     */
    public String getDirEmbarque() {
        return dirEmbarque;
    }

    /**
     * @param dirEmbarque the dirEmbarque to set
     */
    public void setDirEmbarque(String dirEmbarque) {
        this.dirEmbarque = dirEmbarque;
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
    
}
