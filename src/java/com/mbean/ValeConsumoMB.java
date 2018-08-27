/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.conexion.ConexionBD;
import com.model.Proyecto;
import com.services.ProyectoBS;
import com.services.ValeConsumoBS;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;


@ManagedBean
@ViewScoped
public class ValeConsumoMB {

    private List<Proyecto> listProyectos;
    private Proyecto selectedProyecto;
    
    private String nombreProyecto;
    private Integer numValeConsumo;
    private String fechaElaboracion;
    
    private List<FormTablaInsumosValeConsumoMB> listaInsumos;
    private List<FormTablaInsumosValeConsumoMB> selectedListaInsumos;

    private List<FormTablaInsumosValeConsumoMB> listaInsumosSol;
    private List<FormTablaInsumosValeConsumoMB> selectedListaInsumosSol;
    
    public ValeConsumoMB() {
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        ProyectoBS pbs = new ProyectoBS(con);
        listProyectos = pbs.listarProyectos();
        selectedProyecto = new Proyecto();
        listaInsumosSol = new ArrayList<>();
        Date fecha = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        fechaElaboracion = sdf.format(fecha);
    }
    
    public void listarInsumos() {
        
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        ValeConsumoBS vcbs = new ValeConsumoBS(con);
        listaInsumos = vcbs.listarInsumosEnAlmacenPorIdProyecto(getSelectedProyecto().getIdProyecto());
        System.out.println("Tamños" + listaInsumos.size());
        setNombreProyecto(getSelectedProyecto().getProyecto());
        
    }
    
    public void agregarInsumos() {
        listaInsumosSol.addAll(selectedListaInsumos);
        listaInsumos.removeAll(selectedListaInsumos);
        selectedListaInsumos.clear();
    }
    
    public void eliminarInsumos() {
        listaInsumos.addAll(selectedListaInsumosSol);
        listaInsumosSol.removeAll(selectedListaInsumosSol);
        selectedListaInsumosSol.clear();
    }
    
    public void autorizarValeConsumo() {
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        ValeConsumoBS vcbs = new ValeConsumoBS(con);
        int noValeConsumo = vcbs.autorizarValeConsumo(listaInsumosSol, selectedProyecto.getIdProyecto());
        if (noValeConsumo > 0) {
            setNumValeConsumo(noValeConsumo);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación Finalizada ", "Se ha generado correctamente el Vale de Consumo con No.: " + noValeConsumo));
        }else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la Operación ", "No se puede procesar la petición"));
        }
    }
    
    public void onCellEdit(CellEditEvent event) {
          DataTable tabla = (DataTable) event.getComponent();
        List<FormTablaInsumosValeConsumoMB> lista = (List<FormTablaInsumosValeConsumoMB>) tabla.getValue();
        
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue(); 
        
        BigDecimal zero = new BigDecimal(BigInteger.ZERO);
        BigDecimal oold = new BigDecimal(oldValue.toString());
        
        System.out.println("OLD: " + oold);
        System.out.println("VAL comp: " + zero.compareTo(oold));
        if (zero.compareTo(oold) == 1) {
            lista.get(event.getRowIndex()).setCantidad(BigDecimal.ZERO);
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cantidad Insuficiente ", "La cantidad disponible es insuficiente"));
        }
        
        System.out.println("Antes: " + oldValue + " Despues: " + newValue);
        BigDecimal bOld = new BigDecimal( lista.get(event.getRowIndex()).getCantidadOriginal().toString());
        BigDecimal bNew = new BigDecimal(newValue.toString());
        System.out.println("VAL:: " + bOld.compareTo(bNew));

        if (bOld.compareTo(bNew) == -1) {
            lista.get(event.getRowIndex()).setCantidad(bOld);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cantidad Insuficiente ", "La cantidad Solicitada es mayor a la Disponible: "+ bOld.setScale(3,BigDecimal.ROUND_HALF_DOWN )));
        }
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
     * @return the numValeConsumo
     */
    public Integer getNumValeConsumo() {
        return numValeConsumo;
    }

    /**
     * @param numValeConsumo the numValeConsumo to set
     */
    public void setNumValeConsumo(Integer numValeConsumo) {
        this.numValeConsumo = numValeConsumo;
    }

    /**
     * @return the fechaElaboracion
     */
    public String getFechaElaboracion() {
        return fechaElaboracion;
    }

    /**
     * @param fechaElaboracion the fechaElaboracion to set
     */
    public void setFechaElaboracion(String fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    /**
     * @return the listaInsumos
     */
    public List<FormTablaInsumosValeConsumoMB> getListaInsumos() {
        return listaInsumos;
    }

    /**
     * @param listaInsumos the listaInsumos to set
     */
    public void setListaInsumos(List<FormTablaInsumosValeConsumoMB> listaInsumos) {
        this.listaInsumos = listaInsumos;
    }

    /**
     * @return the selectedListaInsumos
     */
    public List<FormTablaInsumosValeConsumoMB> getSelectedListaInsumos() {
        return selectedListaInsumos;
    }

    /**
     * @param selectedListaInsumos the selectedListaInsumos to set
     */
    public void setSelectedListaInsumos(List<FormTablaInsumosValeConsumoMB> selectedListaInsumos) {
        this.selectedListaInsumos = selectedListaInsumos;
    }

    /**
     * @return the listaInsumosSol
     */
    public List<FormTablaInsumosValeConsumoMB> getListaInsumosSol() {
        return listaInsumosSol;
    }

    /**
     * @param listaInsumosSol the listaInsumosSol to set
     */
    public void setListaInsumosSol(List<FormTablaInsumosValeConsumoMB> listaInsumosSol) {
        this.listaInsumosSol = listaInsumosSol;
    }

    /**
     * @return the selectedListaInsumosSol
     */
    public List<FormTablaInsumosValeConsumoMB> getSelectedListaInsumosSol() {
        return selectedListaInsumosSol;
    }

    /**
     * @param selectedListaInsumosSol the selectedListaInsumosSol to set
     */
    public void setSelectedListaInsumosSol(List<FormTablaInsumosValeConsumoMB> selectedListaInsumosSol) {
        this.selectedListaInsumosSol = selectedListaInsumosSol;
    }
    
}
