/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.math.BigDecimal;


public class EstimacionCteBean {
    
    private int id_estimacion;
    private String fecha;
    private BigDecimal otrasDeductivas;
    private String descDeduct;
    private String conceptoEstim;
    private int usuarioID;
    private int id_cliente;
    private int id_presupuesto;
    private BigDecimal importe;

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the otrasDeductivas
     */
    public BigDecimal getOtrasDeductivas() {
        return otrasDeductivas;
    }

    /**
     * @param otrasDeductivas the otrasDeductivas to set
     */
    public void setOtrasDeductivas(BigDecimal otrasDeductivas) {
        this.otrasDeductivas = otrasDeductivas;
    }

    /**
     * @return the descDeduct
     */
    public String getDescDeduct() {
        return descDeduct;
    }

    /**
     * @param descDeduct the descDeduct to set
     */
    public void setDescDeduct(String descDeduct) {
        this.descDeduct = descDeduct;
    }

    /**
     * @return the conceptoEstim
     */
    public String getConceptoEstim() {
        return conceptoEstim;
    }

    /**
     * @param conceptoEstim the conceptoEstim to set
     */
    public void setConceptoEstim(String conceptoEstim) {
        this.conceptoEstim = conceptoEstim;
    }

    /**
     * @return the usuarioID
     */
    public int getUsuarioID() {
        return usuarioID;
    }

    /**
     * @param usuarioID the usuarioID to set
     */
    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

    /**
     * @return the id_cliente
     */
    public int getId_cliente() {
        return id_cliente;
    }

    /**
     * @param id_cliente the id_cliente to set
     */
    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    /**
     * @return the id_presupuesto
     */
    public int getId_presupuesto() {
        return id_presupuesto;
    }

    /**
     * @param id_presupuesto the id_presupuesto to set
     */
    public void setId_presupuesto(int id_presupuesto) {
        this.id_presupuesto = id_presupuesto;
    }

    /**
     * @return the id_estimacion
     */
    public int getId_estimacion() {
        return id_estimacion;
    }

    /**
     * @param id_estimacion the id_estimacion to set
     */
    public void setId_estimacion(int id_estimacion) {
        this.id_estimacion = id_estimacion;
    }

    /**
     * @return the importe
     */
    public BigDecimal getImporte() {
        return importe;
    }

    /**
     * @param importe the importe to set
     */
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }
    
    
    
}
