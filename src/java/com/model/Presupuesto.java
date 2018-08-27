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
public class Presupuesto implements Serializable {
    
    private Integer idPresupuesto;
    private Proyecto proyecto;
    private String presupuesto;
    private int tipo;
    private Set expInsumoses = new HashSet(0);
    private Set partidas = new HashSet(0);
    private Set requisicions = new HashSet(0);
    
    public Presupuesto() {
    }
    
    public Presupuesto(int tipo) {
        this.tipo = tipo;
    }
    public Presupuesto(Proyecto proyecto, String presupuesto, int tipo, Set expInsumoses, Set partidas, Set requisicions) {
       this.proyecto = proyecto;
       this.presupuesto = presupuesto;
       this.tipo = tipo;
       this.expInsumoses = expInsumoses;
       this.partidas = partidas;
       this.requisicions = requisicions;
    }

    /**
     * @return the idPresupuesto
     */
    public Integer getIdPresupuesto() {
        return idPresupuesto;
    }

    /**
     * @param idPresupuesto the idPresupuesto to set
     */
    public void setIdPresupuesto(Integer idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
    }

    /**
     * @return the proyecto
     */
    public Proyecto getProyecto() {
        return proyecto;
    }

    /**
     * @param proyecto the proyecto to set
     */
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    /**
     * @return the presupuesto
     */
    public String getPresupuesto() {
        return presupuesto;
    }

    /**
     * @param presupuesto the presupuesto to set
     */
    public void setPresupuesto(String presupuesto) {
        this.presupuesto = presupuesto;
    }

    /**
     * @return the tipo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the expInsumoses
     */
    public Set getExpInsumoses() {
        return expInsumoses;
    }

    /**
     * @param expInsumoses the expInsumoses to set
     */
    public void setExpInsumoses(Set expInsumoses) {
        this.expInsumoses = expInsumoses;
    }

    /**
     * @return the partidas
     */
    public Set getPartidas() {
        return partidas;
    }

    /**
     * @param partidas the partidas to set
     */
    public void setPartidas(Set partidas) {
        this.partidas = partidas;
    }

    /**
     * @return the requisicions
     */
    public Set getRequisicions() {
        return requisicions;
    }

    /**
     * @param requisicions the requisicions to set
     */
    public void setRequisicions(Set requisicions) {
        this.requisicions = requisicions;
    }
    
}
