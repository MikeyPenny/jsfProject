/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean
@ViewScoped
public class FormTablaValeConsumoMB implements Serializable {
    
    private int idValeConsumo;
    private int noValeConsumo;
    private String fechaSolicutud;
    
    public FormTablaValeConsumoMB() {
    }

    /**
     * @return the idValeConsumo
     */
    public int getIdValeConsumo() {
        return idValeConsumo;
    }

    /**
     * @param idValeConsumo the idValeConsumo to set
     */
    public void setIdValeConsumo(int idValeConsumo) {
        this.idValeConsumo = idValeConsumo;
    }

    /**
     * @return the noValeConsumo
     */
    public int getNoValeConsumo() {
        return noValeConsumo;
    }

    /**
     * @param noValeConsumo the noValeConsumo to set
     */
    public void setNoValeConsumo(int noValeConsumo) {
        this.noValeConsumo = noValeConsumo;
    }

    /**
     * @return the fechaSolicutud
     */
    public String getFechaSolicutud() {
        return fechaSolicutud;
    }

    /**
     * @param fechaSolicutud the fechaSolicutud to set
     */
    public void setFechaSolicutud(String fechaSolicutud) {
        this.fechaSolicutud = fechaSolicutud;
    }
    
}
