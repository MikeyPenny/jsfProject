/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.conexion.ConexionBD;
import com.model.Proyecto;
import com.services.ProyectoBS;
import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Mickey
 */
@ManagedBean
@ViewScoped
public class ProyectosMB implements Serializable {

    private List<Proyecto> listProyectos;
    private Proyecto selectedProyecto;
    
    public ProyectosMB() {
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        ProyectoBS pbs = new ProyectoBS(con);
        listProyectos = pbs.listarProyectos();
        selectedProyecto = new Proyecto();
    }

    /**
     * @return the listProyectos
     */
    public List<Proyecto> getListProyectos() {
        return listProyectos;
    }

    /**
     * @param listProyectos the listProyectos to set
     */
    public void setListProyectos(List<Proyecto> listProyectos) {
        this.listProyectos = listProyectos;
    }

    /**
     * @return the selectedProyecto
     */
    public Proyecto getSelectedProyecto() {
        return selectedProyecto;
    }

    /**
     * @param selectedProyecto the selectedProyecto to set
     */
    public void setSelectedProyecto(Proyecto selectedProyecto) {
        this.selectedProyecto = selectedProyecto;
    }
    
}
