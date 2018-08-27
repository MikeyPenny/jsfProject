/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


public class PresupuestoBean implements Serializable {
    
    private int id_presupuesto;
    private String presupuesto;
    private int tipo;
    private String tipoPres;
    private int id_proyecto;
    private List<Partida> listPartidas;
    private BigDecimal sumaPres;

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
     * @return the tipoPres
     */
    public String getTipoPres() {
        return tipoPres;
    }

    /**
     * @param tipoPres the tipoPres to set
     */
    public void setTipoPres(String tipoPres) {
        this.tipoPres = tipoPres;
    }

    /**
     * @return the listPartidas
     */
    public List<Partida> getListPartidas() {
        return listPartidas;
    }

    /**
     * @param listPartidas the listPartidas to set
     */
    public void setListPartidas(List<Partida> listPartidas) {
        this.listPartidas = listPartidas;
    }

    /**
     * @return the sumaPres
     */
    public BigDecimal getSumaPres() {
        return sumaPres;
    }

    /**
     * @param sumaPres the sumaPres to set
     */
    public void setSumaPres(BigDecimal sumaPres) {
        this.sumaPres = sumaPres;
    }
    
}
