/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.bean.PresupuestoBean;
import com.bean.ProyectoSimple;
import com.bean.TipoPresupto;
import com.conexion.ConexionBD;
import com.dao.PresupuestoDAO;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;


@Named("crear")
@ViewScoped
public class CrearPresMB implements Serializable {
    
    private int id_proyecto;
    private String proyecto;
    
    private String presupuesto;

    private ArrayList<ProyectoSimple> listaProy;
    private ProyectoSimple proySel;
    
    private List<PresupuestoBean> listaPres;
    private PresupuestoBean presupSel;
    
    private List<TipoPresupto> listTP;
    private int tipoPres;
    
    @PostConstruct
    public void init() {
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        PresupuestoDAO presD = new PresupuestoDAO(con);
        setListaProy(presD.listarProyecto());      
        listarTiposDePresupuesto();
        //System.out.println(listaProy.size());
        //System.out.println(listTP.size());
    }
    
    public void eliminarPresupuesto() {
        ConexionBD c = new ConexionBD();
        PresupuestoDAO dao = new PresupuestoDAO(c.getConexion());
        dao.eliminaPres(presupSel.getId_presupuesto());
        //System.out.println(presupSel.getPresupuesto());
        listarPresupuesto();
    }
    
    public void listarTiposDePresupuesto() {
    
        ConexionBD c = new ConexionBD();
        PresupuestoDAO dao = new PresupuestoDAO(c.getConexion());
        setListTP(dao.listarTiposPres());
        
    }
    
    public void crearPresupuesto() {
        
        PresupuestoBean pb = new PresupuestoBean();
        pb.setPresupuesto(getPresupuesto());
        pb.setTipo(getTipoPres());
        pb.setId_proyecto(proySel.getId_proyecto());
        
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        PresupuestoDAO presD = new PresupuestoDAO(con);
        int reg = presD.guardarPresupuesto(pb);
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Correcto!",  "Se registró el presupuesto " + presupuesto + " para el proyecto " + proyecto));
        limpiarFormulario();
        listarPresupuesto();
    }
    
    public void listarPresupuesto() {
        
        ConexionBD c = new ConexionBD();
        PresupuestoDAO dao = new PresupuestoDAO(c.getConexion());
        setListaPres(dao.listarPresupuestoVacio(id_proyecto));
        
    }
    
    public void limpiarFormulario() {
        //setId_proyecto(0);
        //setProyecto(null);
        setPresupuesto(null);
        //setProySel(null);
    }
    
    public void seleccionarProyecto() {
        setId_proyecto(proySel.getId_proyecto());
        setProyecto(proySel.getProyecto());
        listarPresupuesto();
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
     * @return the listaProy
     */
    public ArrayList<ProyectoSimple> getListaProy() {
        return listaProy;
    }

    /**
     * @param listaProy the listaProy to set
     */
    public void setListaProy(ArrayList<ProyectoSimple> listaProy) {
        this.listaProy = listaProy;
    }

    /**
     * @return the proySel
     */
    public ProyectoSimple getProySel() {
        return proySel;
    }

    /**
     * @param proySel the proySel to set
     */
    public void setProySel(ProyectoSimple proySel) {
        this.proySel = proySel;
    }

    /**
     * @return the listTP
     */
    public List<TipoPresupto> getListTP() {
        return listTP;
    }

    /**
     * @param listTP the listTP to set
     */
    public void setListTP(List<TipoPresupto> listTP) {
        this.listTP = listTP;
    }

    /**
     * @return the tipoPres
     */
    public int getTipoPres() {
        return tipoPres;
    }

    /**
     * @param tipoPres the tipoPres to set
     */
    public void setTipoPres(int tipoPres) {
        this.tipoPres = tipoPres;
    }

    /**
     * @return the listaPres
     */
    public List<PresupuestoBean> getListaPres() {
        return listaPres;
    }

    /**
     * @param listaPres the listaPres to set
     */
    public void setListaPres(List<PresupuestoBean> listaPres) {
        this.listaPres = listaPres;
    }

    /**
     * @return the presupSel
     */
    public PresupuestoBean getPresupSel() {
        return presupSel;
    }

    /**
     * @param presupSel the presupSel to set
     */
    public void setPresupSel(PresupuestoBean presupSel) {
        this.presupSel = presupSel;
    }
    
}
