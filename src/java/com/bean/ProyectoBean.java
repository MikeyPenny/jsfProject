/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Mickey
 */
public class ProyectoBean {
    
    private int id_proyecto;
    private String proyecto;
    private String numContrato;
    private Date fechInicio;
    private Date fechFin;
    private BigDecimal importeContto;
    private String cliente;

    /**
     * @return the proyecto
     */
    public String getProyecto() {
        return proyecto;
    }

    /**
     * @param proyecto the proyecto to set
     */
    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    /**
     * @return the contrato
     */
    public String getNumContrato() {
        return numContrato;
    }

    /**
     * @param contrato the contrato to set
     */
    public void setNumContrato(String numContrato) {
        this.numContrato = numContrato;
    }

    /**
     * @return the fechInicio
     */
    public Date getFechInicio() {
        return fechInicio;
    }

    /**
     * @param fechInicio the fechInicio to set
     */
    public void setFechInicio(Date fechInicio) {
        this.fechInicio = fechInicio;
    }

    /**
     * @return the fechFin
     */
    public Date getFechFin() {
        return fechFin;
    }

    /**
     * @param fechFin the fechFin to set
     */
    public void setFechFin(Date fechFin) {
        this.fechFin = fechFin;
    }

    /**
     * @return the importeContto
     */
    public BigDecimal getImporteContto() {
        return importeContto;
    }

    /**
     * @param importeContto the importeContto to set
     */
    public void setImporteContto(BigDecimal importeContto) {
        this.importeContto = importeContto;
    }

    /**
     * @return the cliente
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the id_proyecto
     */
    public int getId_proyecto() {
        return id_proyecto;
    }

    /**
     * @param id_proyecto the id_proyecto to set
     */
    public void setId_proyecto(int id_proyecto) {
        this.id_proyecto = id_proyecto;
    }
    
}
