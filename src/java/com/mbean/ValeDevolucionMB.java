/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.conexion.ConexionBD;
import com.model.Proyecto;
import com.services.ProyectoBS;
import com.services.ValeConsumoBS;
import com.services.ValeDevolucionBS;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.text.SimpleDateFormat;
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
public class ValeDevolucionMB implements Serializable {

    private List<Proyecto> listProyectos;
    private Proyecto selectedProyecto;
    
    private String nombreProyecto;
    private int numValeConsumo;
    private int numValeDevolucion;
    private String fechaElaboracion;
    
    private List<FormTablaValeConsumoMB> listaValeConsumo;
    private FormTablaValeConsumoMB selectedValeConsumo;

    private List<FormTablaInsumosValeConsumoMB> listaDetalleInsumo;
    
    public ValeDevolucionMB() {
        
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        ProyectoBS pbs = new ProyectoBS(con);
        listProyectos = pbs.listarProyectos();
        selectedProyecto = new Proyecto();
        Date fecha = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy H:mm:ss");
        fechaElaboracion = sdf.format(fecha);
    }
    
    public void autorizarValeDevolucion() {
        
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        ValeDevolucionBS vdbs = new ValeDevolucionBS(con);

        int idValeDevolucion = vdbs.autorizarValeDevolcion(selectedValeConsumo.getIdValeConsumo(), listaDetalleInsumo, selectedProyecto.getIdProyecto());
        if (idValeDevolucion > 0) {
            setNumValeDevolucion(idValeDevolucion);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación Finalizada ", "Se ha generado correctamente el Vale de Devolución con No.: " + idValeDevolucion));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la Operación ", "No se puede procesar la petición"));
        }

    }
    
    public void listarValesConsumo() {
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        ValeConsumoBS vd = new ValeConsumoBS(con);
        listaValeConsumo = vd.listarValeConsumoPorIdProyecto(getSelectedProyecto().getIdProyecto());
        setNombreProyecto(getSelectedProyecto().getProyecto());
    }
    
    public void listarDetallesConsumo() {
        setNumValeConsumo(selectedValeConsumo.getNoValeConsumo());
        ConexionBD c =  new ConexionBD();
        Connection con = c.getConexion();
        ValeConsumoBS vcbs = new ValeConsumoBS(con);
        listaDetalleInsumo = vcbs.listarInsumosPorIdValeConsumo(selectedValeConsumo.getIdValeConsumo());

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
    public int getNumValeConsumo() {
        return numValeConsumo;
    }

    /**
     * @param numValeConsumo the numValeConsumo to set
     */
    public void setNumValeConsumo(int numValeConsumo) {
        this.numValeConsumo = numValeConsumo;
    }

    /**
     * @return the numValeDevolucion
     */
    public int getNumValeDevolucion() {
        return numValeDevolucion;
    }

    /**
     * @param numValeDevolucion the numValeDevolucion to set
     */
    public void setNumValeDevolucion(int numValeDevolucion) {
        this.numValeDevolucion = numValeDevolucion;
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
     * @return the listaValeConsumo
     */
    public List<FormTablaValeConsumoMB> getListaValeConsumo() {
        return listaValeConsumo;
    }

    /**
     * @param listaValeConsumo the listaValeConsumo to set
     */
    public void setListaValeConsumo(List<FormTablaValeConsumoMB> listaValeConsumo) {
        this.listaValeConsumo = listaValeConsumo;
    }

    /**
     * @return the selectedValeConsumo
     */
    public FormTablaValeConsumoMB getSelectedValeConsumo() {
        return selectedValeConsumo;
    }

    /**
     * @param selectedValeConsumo the selectedValeConsumo to set
     */
    public void setSelectedValeConsumo(FormTablaValeConsumoMB selectedValeConsumo) {
        this.selectedValeConsumo = selectedValeConsumo;
    }

    /**
     * @return the listaDetalleInsumo
     */
    public List<FormTablaInsumosValeConsumoMB> getListaDetalleInsumo() {
        return listaDetalleInsumo;
    }

    /**
     * @param listaDetalleInsumo the listaDetalleInsumo to set
     */
    public void setListaDetalleInsumo(List<FormTablaInsumosValeConsumoMB> listaDetalleInsumo) {
        this.listaDetalleInsumo = listaDetalleInsumo;
    }
    
}
