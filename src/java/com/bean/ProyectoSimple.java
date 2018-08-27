/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.io.Serializable;

/**
 *
 * @author Mickey
 */
public class ProyectoSimple implements Serializable {
    
    private int id_proyecto;
    private String proyecto;
    private String cod_proy;

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
     * @return the cod_proy
     */
    public String getCod_proy() {
        return cod_proy;
    }

    /**
     * @param cod_proy the cod_proy to set
     */
    public void setCod_proy(String cod_proy) {
        this.cod_proy = cod_proy;
    }
    
}
