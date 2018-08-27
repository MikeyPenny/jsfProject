/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.io.Serializable;


public class PartidaBean implements Serializable {
    
    private int id_partida;
    private String nivel;
    private int esSubNivelDe;
    private String esSubNivel;
    private String descripcion;
    private int id_presupuesto;

    /**
     * @return the id_partida
     */
    public int getId_partida() {
        return id_partida;
    }

    /**
     * @param id_partida the id_partida to set
     */
    public void setId_partida(int id_partida) {
        this.id_partida = id_partida;
    }

    /**
     * @return the nivel
     */
    public String getNivel() {
        return nivel;
    }

    /**
     * @param nivel the nivel to set
     */
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    /**
     * @return the esSubNivelDe
     */
    public int getEsSubNivelDe() {
        return esSubNivelDe;
    }

    /**
     * @param esSubNivelDe the esSubNivelDe to set
     */
    public void setEsSubNivelDe(int esSubNivelDe) {
        this.esSubNivelDe = esSubNivelDe;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
     * @return the esSubNivel
     */
    public String getEsSubNivel() {
        return esSubNivel;
    }

    /**
     * @param esSubNivel the esSubNivel to set
     */
    public void setEsSubNivel(String esSubNivel) {
        this.esSubNivel = esSubNivel;
    }
    
}
