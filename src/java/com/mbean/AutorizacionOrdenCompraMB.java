/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.bean.PresupuestoBean;
import com.bean.ProyectoSimple;
import com.bo.Cantidades;
import com.conexion.ConexionBD;
import com.dao.PresupuestoDAO;
import com.services.OrdenCompraBS;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Mickey
 */
@ManagedBean
@ViewScoped
public class AutorizacionOrdenCompraMB implements Serializable {

    private List<FormOrdenCompraMB> listOrdenCompra;
    private List<FormOrdenCompraMB> selectedOrdenCompra;
    private List<DetalleOrdenCompraMB> detallesOrdenCompra;
    private BigDecimal sumaDet;
    
    private ArrayList<ProyectoSimple> listaProy = new ArrayList<>();
    private ProyectoSimple proySel;
    private String proyecto;
    private int id_proyecto;
    private ArrayList<PresupuestoBean> listaPres = new ArrayList<>();
    private PresupuestoBean presB;
    private int id_presupuesto;
    private String presupuesto;
    
    @PostConstruct
    public void init() {
        buscarProyecto();
        listOrdenCompra = new ArrayList<>();
        detallesOrdenCompra = new ArrayList<>();
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
    
    public void listarOrdenCompra() {
        
        ConexionBD c = new ConexionBD();
        OrdenCompraBS ocbs = new OrdenCompraBS(c.getConexion());
        listOrdenCompra = ocbs.listarOrdenesCompraXPres(id_presupuesto);
        
    }
    
    /*public AutorizacionOrdenCompraMB() {
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        listOrdenCompra = new OrdenCompraBS(con).listarOrdenesCompra();
        detallesOrdenCompra = new ArrayList<>();
    }*/
    
    public void listarDetalleOrdenCompra(int idOrdenCompra){
        detallesOrdenCompra.clear();
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        OrdenCompraBS ocbs = new OrdenCompraBS(con);
        detallesOrdenCompra =  ocbs.detallesOrdenCompra(idOrdenCompra);
        Cantidades cant = new Cantidades();
        setSumaDet(cant.sumarDetalleOrdenCompra(detallesOrdenCompra));
        System.out.println("Tamaño del detalle OC: " + detallesOrdenCompra.size());
    }
    
    public void autorizarOrdenCompra(){
       
        List <Integer> list = new ArrayList<>();
        System.out.println("Ordenes de compra seleccionadas: " + selectedOrdenCompra.size());
        for(FormOrdenCompraMB aux: selectedOrdenCompra){
            list.add(aux.getNoOrdenCompra());
        }
        if(list.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "AVISO", "Debe seleccionar al menos una Orden de Compra"));
            return;
        }
        
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        OrdenCompraBS ocbs = new OrdenCompraBS(con);
        boolean resultado = ocbs.autorizacionOrdenCompra(list, 2);
        if (resultado) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación Finalizada ", "Se han Autorizado correctamente las Ordenes de compra seleccionadas"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error del Sistema ", "No se puede procesar la petición"));
        }
        
        listOrdenCompra = ocbs.listarOrdenesCompra();
        selectedOrdenCompra.clear();
    }
    
    public void cancelarOrdenCompra() {
        
        if(!selectedOrdenCompra.isEmpty()) {
            
            List<Integer> listCancel = new ArrayList<>();
            
            for(FormOrdenCompraMB aux: selectedOrdenCompra) {
                listCancel.add(aux.getNoOrdenCompra());
            }
            
            ConexionBD c = new ConexionBD();
            OrdenCompraBS bs = new OrdenCompraBS(c.getConexion());
            if(bs.cancelarOrdenCompra(listCancel)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación Finalizada ", "Se han Cancelado correctamente las Ordenes de compra seleccionadas"));
            }else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se realizó la cancelación"));
            }
            
        }else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "AVISO", "Debe seleccionar al menos una Orden de Compra"));
        }
        
    }

    /**
     * @return the listOrdenCompra
     */
    public List<FormOrdenCompraMB> getListOrdenCompra() {
        return listOrdenCompra;
    }

    /**
     * @param listOrdenCompra the listOrdenCompra to set
     */
    public void setListOrdenCompra(List<FormOrdenCompraMB> listOrdenCompra) {
        this.listOrdenCompra = listOrdenCompra;
    }

    /**
     * @return the selectedOrdenCompra
     */
    public List<FormOrdenCompraMB> getSelectedOrdenCompra() {
        return selectedOrdenCompra;
    }

    /**
     * @param selectedOrdenCompra the selectedOrdenCompra to set
     */
    public void setSelectedOrdenCompra(List<FormOrdenCompraMB> selectedOrdenCompra) {
        this.selectedOrdenCompra = selectedOrdenCompra;
    }

    /**
     * @return the detallesOrdenCompra
     */
    public List<DetalleOrdenCompraMB> getDetallesOrdenCompra() {
        return detallesOrdenCompra;
    }

    /**
     * @param detallesOrdenCompra the detallesOrdenCompra to set
     */
    public void setDetallesOrdenCompra(List<DetalleOrdenCompraMB> detallesOrdenCompra) {
        this.detallesOrdenCompra = detallesOrdenCompra;
    }

    /**
     * @return the sumaDet
     */
    public BigDecimal getSumaDet() {
        return sumaDet;
    }

    /**
     * @param sumaDet the sumaDet to set
     */
    public void setSumaDet(BigDecimal sumaDet) {
        this.sumaDet = sumaDet;
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
