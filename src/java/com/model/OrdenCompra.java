/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Mickey
 */
public class OrdenCompra implements Serializable {
    
    private Integer idordenCompra;
    private Proveedor proveedor;
    private Requisicion requisicion;
    private String fechaSolicitud;
    private String fechaAutorizacion;
    private String embarque;
    private Integer iva;
    private String pago;
    private String comentarios;
    private String estatus;
    private Integer idUsuarioSolicitante;
    private Integer idUsuarioAutorizacion;
    private Set recepcions = new HashSet(0);
    
    public OrdenCompra() {
    }

	
    public OrdenCompra(Proveedor proveedor, Requisicion requisicion) {
        this.proveedor = proveedor;
        this.requisicion = requisicion;
    }
    public OrdenCompra(Proveedor proveedor, Requisicion requisicion, String fechaSolicitud, String fechaAutorizacion, String embarque, Integer iva, String pago, String comentarios, String estatus, Integer idUsuarioSolicitante, Integer idUsuarioAutorizacion, Set recepcions) {
       this.proveedor = proveedor;
       this.requisicion = requisicion;
       this.fechaSolicitud = fechaSolicitud;
       this.fechaAutorizacion = fechaAutorizacion;
       this.embarque = embarque;
       this.iva = iva;
       this.pago = pago;
       this.comentarios = comentarios;
       this.estatus = estatus;
       this.idUsuarioSolicitante = idUsuarioSolicitante;
       this.idUsuarioAutorizacion = idUsuarioAutorizacion;
       this.recepcions = recepcions;
    }

    /**
     * @return the idordenCompra
     */
    public Integer getIdordenCompra() {
        return idordenCompra;
    }

    /**
     * @param idordenCompra the idordenCompra to set
     */
    public void setIdordenCompra(Integer idordenCompra) {
        this.idordenCompra = idordenCompra;
    }

    /**
     * @return the proveedor
     */
    public Proveedor getProveedor() {
        return proveedor;
    }

    /**
     * @param proveedor the proveedor to set
     */
    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * @return the requisicion
     */
    public Requisicion getRequisicion() {
        return requisicion;
    }

    /**
     * @param requisicion the requisicion to set
     */
    public void setRequisicion(Requisicion requisicion) {
        this.requisicion = requisicion;
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
     * @return the embarque
     */
    public String getEmbarque() {
        return embarque;
    }

    /**
     * @param embarque the embarque to set
     */
    public void setEmbarque(String embarque) {
        this.embarque = embarque;
    }

    /**
     * @return the iva
     */
    public Integer getIva() {
        return iva;
    }

    /**
     * @param iva the iva to set
     */
    public void setIva(Integer iva) {
        this.iva = iva;
    }

    /**
     * @return the pago
     */
    public String getPago() {
        return pago;
    }

    /**
     * @param pago the pago to set
     */
    public void setPago(String pago) {
        this.pago = pago;
    }

    /**
     * @return the comentarios
     */
    public String getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
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
     * @return the recepcions
     */
    public Set getRecepcions() {
        return recepcions;
    }

    /**
     * @param recepcions the recepcions to set
     */
    public void setRecepcions(Set recepcions) {
        this.recepcions = recepcions;
    }
    
}
