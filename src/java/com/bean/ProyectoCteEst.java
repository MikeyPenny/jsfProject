/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.io.Serializable;


public class ProyectoCteEst implements Serializable {
    
    private int id_proyecto;
    private String proyecto;
    
    private String cliente;
    private String centCosto;
    private float pctAntcpo;
    private float pctGarantia;
    
    
    

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
     * @return the centCosto
     */
    public String getCentCosto() {
        return centCosto;
    }

    /**
     * @param centCosto the centCosto to set
     */
    public void setCentCosto(String centCosto) {
        this.centCosto = centCosto;
    }

    /**
     * @return the pctAntcpo
     */
    public float getPctAntcpo() {
        return pctAntcpo;
    }

    /**
     * @param pctAntcpo the pctAntcpo to set
     */
    public void setPctAntcpo(float pctAntcpo) {
        this.pctAntcpo = pctAntcpo;
    }

    /**
     * @return the pctGarantia
     */
    public float getPctGarantia() {
        return pctGarantia;
    }

    /**
     * @param pctGarantia the pctGarantia to set
     */
    public void setPctGarantia(float pctGarantia) {
        this.pctGarantia = pctGarantia;
    }
    
}
