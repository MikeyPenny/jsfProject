/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.conexion.ConexionBD;
import com.model.Presupuesto;
import com.model.Proyecto;
import com.services.InsumosBS;
import com.services.PresupuestoBS;
import com.services.ProyectoBS;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Mickey
 */
@ManagedBean
@ViewScoped
public class FormProyectosMB implements Serializable {

    private String nombreProyecto;
    private String noPresupuesto;
    private List<Proyecto> listProyectos; 
    private Proyecto selectedProyecto;
    private List<Presupuesto> listPresupuesto;
    private Presupuesto selectedPresupuesto;
    private List<InsumoTablaMB> listInsumos;
    private List<InsumoTablaMB> listInsumosSol;
    private List<InsumoTablaMB>  selectedInsumo;
    private List<InsumoTablaMB>  selectedInsumoSol;
    private InsumoTablaMB insElim;
    
    @ManagedProperty(value = "#{formRequisicionMB}")
    private  FormRequisicionMB formRequisicionMB;
   
    public FormProyectosMB() {
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        listProyectos = new ProyectoBS(con).listarProyectos();
        listPresupuesto = new ArrayList<>();
        listInsumos = new ArrayList<>();
        selectedInsumo = new ArrayList<>();
        listInsumosSol = new ArrayList<>();
        selectedInsumoSol = new ArrayList<>();
    }
    
    public void buscarPresupuestos(){
        noPresupuesto = "";
        listPresupuesto.clear();
        selectedPresupuesto=null;
        listInsumos.clear();
        selectedInsumo.clear();
        
        nombreProyecto = selectedProyecto.getProyecto();
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        PresupuestoBS psb = new PresupuestoBS(con);
        
        List <Presupuesto> pres = psb.listarPresupuestosPorIDProyecto(selectedProyecto.getIdProyecto());
        Iterator it = pres.iterator();
        listPresupuesto.clear();
        while(it.hasNext()){
            Presupuesto aux = (Presupuesto) it.next();
            listPresupuesto.add(aux);
        }
        
        getFormRequisicionMB().setIdProyecto(selectedProyecto.getIdProyecto());
    }
    
    public void listarInsumos(){
        if(selectedPresupuesto == null)
            return;
        
        noPresupuesto = selectedPresupuesto.getPresupuesto();
        ConexionBD c =  new ConexionBD();
        Connection con =  c.getConexion();
        InsumosBS insbs = new InsumosBS(con);
        getFormRequisicionMB().setIdPresupuesto(selectedPresupuesto.getIdPresupuesto());
        listInsumos = insbs.listarMateriales(selectedPresupuesto.getIdPresupuesto());
        //System.out.println("Tamaño de la Lista de insumos: " + listInsumos.size());
    }
    
    public void agregarInsumos(){
        if(selectedInsumo.isEmpty()){
            return;
        }
        listInsumosSol.addAll(selectedInsumo);
        listInsumos.removeAll(selectedInsumo);
        selectedInsumo.clear();
    }
    
    public void eliminarInsumos(){
        /*if(selectedInsumoSol.isEmpty()){
            return;
        }
        for(InsumoTablaMB aux: selectedInsumoSol){
            aux.setCantDisponible(aux.getCantDisponibleOriginal());
        }
        
        listInsumos.addAll(selectedInsumoSol);
        listInsumosSol.removeAll(selectedInsumoSol);
        selectedInsumoSol.clear();*/
        listInsumosSol.remove(insElim);
        
    }
    
    
    
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        DataTable tabla = (DataTable) event.getComponent();
        List<InsumoTablaMB> lista = (List<InsumoTablaMB>) tabla.getValue();
        
        BigDecimal zero = new BigDecimal(0);
        BigDecimal oold = new BigDecimal(oldValue.toString());
        if (oold.compareTo(zero) == -1) {
                lista.get(event.getRowIndex()).setCantDisponible(BigDecimal.ZERO);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cantidad Insuficiente ", "La cantidad disponible es insuficiente"));
        }
        
        System.out.println("Antes: " + oldValue + " Despues: " + newValue);
        BigDecimal bOld = new BigDecimal( lista.get(event.getRowIndex()).getCantDisponibleOriginal().toString());
        BigDecimal bNew = new BigDecimal(newValue.toString());
        System.out.println("VAL:: " + bOld.compareTo(bNew));
        
        if(bOld.compareTo(bNew) == -1){
            lista.get(event.getRowIndex()).setCantDisponible(bOld);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cantidad Insuficiente ", "La cantidad Solicitada es mayor a la Disponible"));
        } 
    }

    /**
     * @return the nombreProyecto
     */
    public String getNombreProyecto() {
        return nombreProyecto;
    }

    /**
     * @param nombreProyecto the nombreProyecto to set
     */
    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    /**
     * @return the noPresupuesto
     */
    public String getNoPresupuesto() {
        return noPresupuesto;
    }

    /**
     * @param noPresupuesto the noPresupuesto to set
     */
    public void setNoPresupuesto(String noPresupuesto) {
        this.noPresupuesto = noPresupuesto;
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

    /**
     * @return the listPresupuesto
     */
    public List<Presupuesto> getListPresupuesto() {
        return listPresupuesto;
    }

    /**
     * @param listPresupuesto the listPresupuesto to set
     */
    public void setListPresupuesto(List<Presupuesto> listPresupuesto) {
        this.listPresupuesto = listPresupuesto;
    }

    /**
     * @return the selectedPresupuesto
     */
    public Presupuesto getSelectedPresupuesto() {
        return selectedPresupuesto;
    }

    /**
     * @param selectedPresupuesto the selectedPresupuesto to set
     */
    public void setSelectedPresupuesto(Presupuesto selectedPresupuesto) {
        this.selectedPresupuesto = selectedPresupuesto;
    }

    /**
     * @return the listInsumos
     */
    public List<InsumoTablaMB> getListInsumos() {
        return listInsumos;
    }

    /**
     * @param listInsumos the listInsumos to set
     */
    public void setListInsumos(List<InsumoTablaMB> listInsumos) {
        this.listInsumos = listInsumos;
    }

    /**
     * @return the listInsumosSol
     */
    public List<InsumoTablaMB> getListInsumosSol() {
        return listInsumosSol;
    }

    /**
     * @param listInsumosSol the listInsumosSol to set
     */
    public void setListInsumosSol(List<InsumoTablaMB> listInsumosSol) {
        this.listInsumosSol = listInsumosSol;
    }

    /**
     * @return the selectedInsumo
     */
    public List<InsumoTablaMB> getSelectedInsumo() {
        return selectedInsumo;
    }

    /**
     * @param selectedInsumo the selectedInsumo to set
     */
    public void setSelectedInsumo(List<InsumoTablaMB> selectedInsumo) {
        this.selectedInsumo = selectedInsumo;
    }

    /**
     * @return the selectedInsumoSol
     */
    public List<InsumoTablaMB> getSelectedInsumoSol() {
        return selectedInsumoSol;
    }

    /**
     * @param selectedInsumoSol the selectedInsumoSol to set
     */
    public void setSelectedInsumoSol(List<InsumoTablaMB> selectedInsumoSol) {
        this.selectedInsumoSol = selectedInsumoSol;
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
     * @return the insElim
     */
    public InsumoTablaMB getInsElim() {
        return insElim;
    }

    /**
     * @param insElim the insElim to set
     */
    public void setInsElim(InsumoTablaMB insElim) {
        this.insElim = insElim;
    }
    
}
