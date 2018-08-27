/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.conexion.ConexionBD;
import com.dao.InventarioDAO;
import com.model.Presupuesto;
import com.model.Proyecto;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean
@ViewScoped
public class InventarioMB implements Serializable {
    
    public InventarioMB() {
    }
    
    private List<InventarioBean> listaInv;
    private int id_proyecto;
    private String proyecto;
    private int id_presupuesto;
    private String presupuesto;
    
    private List<Proyecto> listProy = new ArrayList<Proyecto>();
    private Proyecto proySel;
    
    private List<Presupuesto> listPres = new ArrayList<Presupuesto>();
    private Presupuesto preSel;
    
    public void listarProyecto() {
        ConexionBD c =  new ConexionBD();
        Connection con = c.getConexion();
        InventarioDAO inv = new InventarioDAO(con);
        setListProy(inv.listarPoryectos());   
    }
    
    public void seleccionarProyecto() {
        setId_proyecto(proySel.getIdProyecto());
        setProyecto(proySel.getProyecto());
        buscarPresupuesto();
    }
    
    public void buscarPresupuesto() {
        if(id_proyecto != 0) {
            ConexionBD c =  new ConexionBD();
            Connection con = c.getConexion();
            InventarioDAO inv = new InventarioDAO(con);
            setListPres(inv.listarPresupestos(getId_proyecto()));
        }
        
    }
    
    public void seleccionarPresupuesto() {
        
        setId_presupuesto(preSel.getIdPresupuesto());
        setPresupuesto(preSel.getPresupuesto());
        
        if(id_proyecto != 0 && id_presupuesto != 0) {
        
            ConexionBD c =  new ConexionBD();
            Connection con = c.getConexion();
            InventarioDAO inv = new InventarioDAO(con);
            setListaInv(inv.obtenerInventario(id_proyecto, id_presupuesto));
            
        }
    }

    /**
     * @return the listaInv
     */
    public List<InventarioBean> getListaInv() {
        return listaInv;
    }

    /**
     * @param listaInv the listaInv to set
     */
    public void setListaInv(List<InventarioBean> listaInv) {
        this.listaInv = listaInv;
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
     * @return the listProy
     */
    public List<Proyecto> getListProy() {
        listarProyecto();
        return listProy;
    }

    /**
     * @param listProy the listProy to set
     */
    public void setListProy(List<Proyecto> listProy) {
        this.listProy = listProy;
    }

    /**
     * @return the proySel
     */
    public Proyecto getProySel() {
        return proySel;
    }

    /**
     * @param proySel the proySel to set
     */
    public void setProySel(Proyecto proySel) {
        this.proySel = proySel;
    }

    /**
     * @return the listPres
     */
    public List<Presupuesto> getListPres() {
        return listPres;
    }

    /**
     * @param listPres the listPres to set
     */
    public void setListPres(List<Presupuesto> listPres) {
        this.listPres = listPres;
    }

    /**
     * @return the preSel
     */
    public Presupuesto getPreSel() {
        return preSel;
    }

    /**
     * @param preSel the preSel to set
     */
    public void setPreSel(Presupuesto preSel) {
        this.preSel = preSel;
    }
    
}
