/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.bean.PresupuestoBean;
import com.bean.ProyectoSimple;
import com.conexion.ConexionBD;
import com.dao.PresupuestoDAO;
import com.model.InsumoRequisicion;
import com.services.RequisicionBS;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Mickey
 */
@ManagedBean
@ViewScoped
public class AutorizacionRequicisionMB implements Serializable {

    @ManagedProperty(value = "#{formProyectosMB}")
    private FormProyectosMB formProyectosMB;
    
    @ManagedProperty(value = "#{formRequisicionMB}")
    private FormRequisicionMB formRequisicionMB;
    
    private List<FormTablaRequisicionMB> selectedListRequisicion;
    private List<FormTablaRequisicionMB> listaRequisicion;
    private List<DetalleRequisicionTablaMB> detallesRequisicion;
    
    private ArrayList<ProyectoSimple> listaProy = new ArrayList<>();
    private ProyectoSimple proySel;
    private String proyecto;
    private int id_proyecto;
    private ArrayList<PresupuestoBean> listaPres = new ArrayList<>();
    private PresupuestoBean presB;
    private int id_presupuesto;
    private String presupuesto;
    
    private int idReqSelected;
    
    @PostConstruct
    public void init() {
        buscarProyecto();
        detallesRequisicion = new ArrayList<>();
        selectedListRequisicion = new ArrayList<>();
    }

    public void buscarProyecto() {
        
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        PresupuestoDAO presD = new PresupuestoDAO(con);
        setListaProy(presD.listarProyecto());      
    }
    
    public void obtenerProyecto() {
        setId_proyecto(getProySel().getId_proyecto());
        setProyecto(getProySel().getProyecto());
        //setCod_proy(getProySel().getCod_proy());
        buscarPresupuesto();
        //listarFamilias();
        //setBtnFam(false);
        //setBtnPart(false);
    }
    
    public void buscarPresupuesto() {
        
        if(proySel != null) {
            Connection con;
            ConexionBD conexion = new ConexionBD();
            con = conexion.getConexion();
            PresupuestoDAO presD = new PresupuestoDAO(con);
            
            setListaPres(presD.listarPresupuesto(proySel.getId_proyecto(), 2));
            //System.out.println(listaPres.size());
        }
        
    }
    
    public void obtenerPresupuesto() {
        setId_presupuesto(presB.getId_presupuesto());
        setPresupuesto(presB.getPresupuesto());
        
        
    }
    
    public void listarReq() {
        
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        RequisicionBS rbs = new RequisicionBS(con);
        listaRequisicion = rbs.listarRequisiciones(id_presupuesto);
        //detallesRequisicion = new ArrayList<>();
        //selectedListRequisicion = new ArrayList<>();
        
    }
    
    public void listarDetallesRequisicion(int idRequisicion) {
        
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        RequisicionBS rbs = new RequisicionBS(con);
        List<InsumoRequisicion> list = rbs.detalleRequisicion(idRequisicion);
        detallesRequisicion.clear();
        System.out.println("Tamaño de insumos: " + list.size());
        for (InsumoRequisicion aux : list) {
            DetalleRequisicionTablaMB detalleInsumo = new DetalleRequisicionTablaMB();
            detalleInsumo.setCantidadSolicitada(aux.getCantidad());
            detalleInsumo.setIdInsumo(aux.getIdinsumoRequisicion());
            detalleInsumo.setIdRequisicion(aux.getRequisicion().getNoRequisicion());
            detalleInsumo.setNombreInsumo(aux.getExpInsumos().getDescripcion());
            detalleInsumo.setUnidades(aux.getExpInsumos().getUnidades());
            detalleInsumo.setClaveInsumo( aux.getExpInsumos().getCodInsumo());
            detallesRequisicion.add(detalleInsumo);
            idReqSelected = aux.getRequisicion().getNoRequisicion();
        }
        
    }
    
    public void autorizarRequisiciones() {
        List<Integer> list = new ArrayList<>();
        System.out.println("Requisiciones Seleccionadas: " + selectedListRequisicion.size());
        for (FormTablaRequisicionMB aux : selectedListRequisicion) {
            list.add(aux.getIdRequicision());
        }
        
        if(list.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "AVISO", "Debe seleccionar al menos una Requisicion"));
            return;
        }
        
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        RequisicionBS rbs = new RequisicionBS(con);
        boolean resultado = rbs.autorizacionRequisicion(list, 2);
        if (resultado) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación Finalizada ", "Se han Autorizado correctamente las Requisiciones seleccionadas"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error del Sistema ", "No se puede procesar la petición"));
        }
        
        listaRequisicion = rbs.listarRequisiciones(id_presupuesto);
        selectedListRequisicion.clear();
    }
    
    public void cancelarRequisiciones() {
        List<Integer> list = new ArrayList<>();
        System.out.println("Requisiciones Seleccionadas: " + selectedListRequisicion.size());
        for (FormTablaRequisicionMB aux : selectedListRequisicion) {
                list.add(aux.getIdRequicision());
        }
        
        if(list.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "AVISO", "Debe seleccionar al menos una Requisicion"));
            return;
        }
        
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        RequisicionBS rbs = new RequisicionBS(con);
        boolean resultado = rbs.cancelarRequisicion(list);
        if (resultado) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación Finalizada ", "Se han Cancelado correctamente las Requisiciones seleccionadas"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error del Sistema ", "No se puede procesar la petición"));
        }
        listaRequisicion = rbs.listarRequisiciones(id_presupuesto);
        selectedListRequisicion.clear();
        
    }

    /**
     * @return the formProyectosMB
     */
    public FormProyectosMB getFormProyectosMB() {
        return formProyectosMB;
    }

    /**
     * @param formProyectosMB the formProyectosMB to set
     */
    public void setFormProyectosMB(FormProyectosMB formProyectosMB) {
        this.formProyectosMB = formProyectosMB;
    }

    /**
     * @return the formRequisicionMB
     */
    public FormRequisicionMB getFormRequisicionMB() {
        return formRequisicionMB;
    }

    /**
     * @param formRequisicionMB the formRequisicionMB to set
     */
    public void setFormRequisicionMB(FormRequisicionMB formRequisicionMB) {
        this.formRequisicionMB = formRequisicionMB;
    }

    /**
     * @return the selectedListRequisicion
     */
    public List<FormTablaRequisicionMB> getSelectedListRequisicion() {
        return selectedListRequisicion;
    }

    /**
     * @param selectedListRequisicion the selectedListRequisicion to set
     */
    public void setSelectedListRequisicion(List<FormTablaRequisicionMB> selectedListRequisicion) {
        this.selectedListRequisicion = selectedListRequisicion;
    }

    /**
     * @return the listaRequisicion
     */
    public List<FormTablaRequisicionMB> getListaRequisicion() {
        return listaRequisicion;
    }

    /**
     * @param listaRequisicion the listaRequisicion to set
     */
    public void setListaRequisicion(List<FormTablaRequisicionMB> listaRequisicion) {
        this.listaRequisicion = listaRequisicion;
    }

    /**
     * @return the detallesRequisicion
     */
    public List<DetalleRequisicionTablaMB> getDetallesRequisicion() {
        return detallesRequisicion;
    }

    /**
     * @param detallesRequisicion the detallesRequisicion to set
     */
    public void setDetallesRequisicion(List<DetalleRequisicionTablaMB> detallesRequisicion) {
        this.detallesRequisicion = detallesRequisicion;
    }

    /**
     * @return the idReqSelected
     */
    public int getIdReqSelected() {
        return idReqSelected;
    }

    /**
     * @param idReqSelected the idReqSelected to set
     */
    public void setIdReqSelected(int idReqSelected) {
        this.idReqSelected = idReqSelected;
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
     * @return the listaPres
     */
    public ArrayList<PresupuestoBean> getListaPres() {
        return listaPres;
    }

    /**
     * @param listaPres the listaPres to set
     */
    public void setListaPres(ArrayList<PresupuestoBean> listaPres) {
        this.listaPres = listaPres;
    }

    /**
     * @return the presB
     */
    public PresupuestoBean getPresB() {
        return presB;
    }

    /**
     * @param presB the presB to set
     */
    public void setPresB(PresupuestoBean presB) {
        this.presB = presB;
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
    
}
