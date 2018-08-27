/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.io.Serializable;


public class Nivel implements Serializable {
    
    private int id_partida;
    private String nivel;
    private int esSubNivelDe;
    private String descripcion;
    private int id_presupuesto;
    
    public int getId_partida() {
        return id_partida;
    }

    public void setId_partida(int id_partida) {
        this.id_partida = id_partida;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public int getEsSubNivelDe() {
        return esSubNivelDe;
    }

    public void setEsSubNivelDe(int esSubNivelDe) {
        this.esSubNivelDe = esSubNivelDe;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId_presupuesto() {
        return id_presupuesto;
    }

    public void setId_presupuesto(int id_presupuesto) {
        this.id_presupuesto = id_presupuesto;
    }
    
}
