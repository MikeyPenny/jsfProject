/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.conexion.ConexionBD;
import com.model.Proyecto;
import com.services.DevolucionBS;
import com.services.OrdenCompraBS;
import com.services.ProyectoBS;
import com.services.RecepcionBS;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;


@ManagedBean
@ViewScoped
public class DevolucionMB implements Serializable {

    private List<Proyecto> listProyectos;
    private Proyecto selectedProyecto;
    
    private Integer numOrc;
    private int idProvedor;
    private String nombreProveedor;
    private Integer recepcion;
    private int devolucion;
    private String nombreProyecto;
    private String fechaRecepcion;
    private int idRecepcion;
    
    private List<FormDetalleOrdenCompraMB> listOc;
    private FormDetalleOrdenCompraMB selectedOc;

    private List<FormListRecepcionMB> listRecepciones;
    private FormListRecepcionMB selectedRecepcion;

    private List<TablaInsumoOrdenCompraMB> listInsumos;
    private List<TablaInsumoOrdenCompraMB> listInsumosDevolucion;
    
    public DevolucionMB() {
        ConexionBD c =  new ConexionBD();
        Connection con = c.getConexion();
        ProyectoBS pbs = new ProyectoBS(con);
        listProyectos = pbs.listarProyectos();
        selectedProyecto = new Proyecto();
    }
    
    public void autorizarDevolucion() {

        listInsumosDevolucion = new ArrayList<>();

        for (TablaInsumoOrdenCompraMB aux : listInsumos) {
            if (!aux.getCantidad().equals(BigDecimal.ZERO)) {
                listInsumosDevolucion.add(aux);
            }
        }

        Map<String, Object> datosDevolucion = new HashMap<>();
        datosDevolucion.put("idProyecto", getSelectedProyecto().getIdProyecto());
        datosDevolucion.put("idRecepcion", selectedRecepcion.getIdRecepcion());
        System.out.println("Tamaños: " + listInsumosDevolucion.size());
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        DevolucionBS dbs = new DevolucionBS(con);
        int noDevolucion = dbs.guardarDevolucion(listInsumosDevolucion, datosDevolucion);
        if (noDevolucion != -1) {
            setDevolucion(noDevolucion);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación Finalizada ", "Se ha generado correctamente la Devolución con No.: " + noDevolucion));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la Operación ", "No se puede procesar la petición"));
        }

    }
    
    public void listarOrdenesCompra() {
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        OrdenCompraBS ocbs = new OrdenCompraBS(con);
        listOc = ocbs.listarOrdenCompraEstatusAlamacen(getSelectedProyecto().getIdProyecto());
        setNombreProyecto(getSelectedProyecto().getProyecto());
    }
    
    public void listarRecepciones() {
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        RecepcionBS rbs = new RecepcionBS(con);
        listRecepciones = rbs.listarRecepcionesPorIdOrdenCompra(selectedOc.getIdOrdenCompra());
        setNumOrc(selectedOc.getIdOrdenCompra());
        setNombreProveedor(selectedOc.getNombreProvedor());
        setIdProvedor(selectedOc.getIdProveedor());
    }
    
    public void listarInsumos() {
        setIdRecepcion(selectedRecepcion.getIdRecepcion());
        setRecepcion(selectedRecepcion.getNoRecepcion());
        setFechaRecepcion(selectedRecepcion.getFechaRecepcion());
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        RecepcionBS rbs = new RecepcionBS(con);
        listInsumos = rbs.listarInsumosPorIdRecepcion(selectedRecepcion.getIdRecepcion());
    }
    
    public void onCellEdit(CellEditEvent event) {
        DataTable tabla = (DataTable) event.getComponent();
        List<TablaInsumoOrdenCompraMB> lista = (List<TablaInsumoOrdenCompraMB>) tabla.getValue();

        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        
        

        BigDecimal zero = new BigDecimal(0);
        BigDecimal oold = new BigDecimal(oldValue.toString());
        
        if (oold.compareTo(zero) == -1) {
            lista.get(event.getRowIndex()).setCantidad(BigDecimal.ZERO);
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cantidad Insuficiente ", "La cantidad disponible es insuficiente"));
        }
        System.out.println("Antes: " + oldValue + " Despues: " + newValue);
        BigDecimal bOld = new BigDecimal(lista.get(event.getRowIndex()).getCatnCtrl().toString());
        BigDecimal bNew = new BigDecimal(newValue.toString());
        System.out.println("VAL:: " + bOld.compareTo(bNew));
        

        if (bOld.compareTo(bNew) == -1) {
            lista.get(event.getRowIndex()).setCantidad(bOld);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cantidad Insuficiente ", "La cantidad Solicitada es mayor a la Disponible: " + bOld.setScale(3, BigDecimal.ROUND_HALF_DOWN)));
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
     * @return the numOrc
     */
    public Integer getNumOrc() {
        return numOrc;
    }

    /**
     * @param numOrc the numOrc to set
     */
    public void setNumOrc(Integer numOrc) {
        this.numOrc = numOrc;
    }

    /**
     * @return the idProvedor
     */
    public int getIdProvedor() {
        return idProvedor;
    }

    /**
     * @param idProvedor the idProvedor to set
     */
    public void setIdProvedor(int idProvedor) {
        this.idProvedor = idProvedor;
    }

    /**
     * @return the nombreProveedor
     */
    public String getNombreProveedor() {
        return nombreProveedor;
    }

    /**
     * @param nombreProveedor the nombreProveedor to set
     */
    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    /**
     * @return the recepcion
     */
    public Integer getRecepcion() {
        return recepcion;
    }

    /**
     * @param recepcion the recepcion to set
     */
    public void setRecepcion(Integer recepcion) {
        this.recepcion = recepcion;
    }

    /**
     * @return the devolucion
     */
    public int getDevolucion() {
        return devolucion;
    }

    /**
     * @param devolucion the devolucion to set
     */
    public void setDevolucion(int devolucion) {
        this.devolucion = devolucion;
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
     * @return the fechaRecepcion
     */
    public String getFechaRecepcion() {
        return fechaRecepcion;
    }

    /**
     * @param fechaRecepcion the fechaRecepcion to set
     */
    public void setFechaRecepcion(String fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    /**
     * @return the idRecepcion
     */
    public int getIdRecepcion() {
        return idRecepcion;
    }

    /**
     * @param idRecepcion the idRecepcion to set
     */
    public void setIdRecepcion(int idRecepcion) {
        this.idRecepcion = idRecepcion;
    }

    /**
     * @return the listOc
     */
    public List<FormDetalleOrdenCompraMB> getListOc() {
        return listOc;
    }

    /**
     * @param listOc the listOc to set
     */
    public void setListOc(List<FormDetalleOrdenCompraMB> listOc) {
        this.listOc = listOc;
    }

    /**
     * @return the selectedOc
     */
    public FormDetalleOrdenCompraMB getSelectedOc() {
        return selectedOc;
    }

    /**
     * @param selectedOc the selectedOc to set
     */
    public void setSelectedOc(FormDetalleOrdenCompraMB selectedOc) {
        this.selectedOc = selectedOc;
    }

    /**
     * @return the listRecepciones
     */
    public List<FormListRecepcionMB> getListRecepciones() {
        return listRecepciones;
    }

    /**
     * @param listRecepciones the listRecepciones to set
     */
    public void setListRecepciones(List<FormListRecepcionMB> listRecepciones) {
        this.listRecepciones = listRecepciones;
    }

    /**
     * @return the selectedRecepcion
     */
    public FormListRecepcionMB getSelectedRecepcion() {
        return selectedRecepcion;
    }

    /**
     * @param selectedRecepcion the selectedRecepcion to set
     */
    public void setSelectedRecepcion(FormListRecepcionMB selectedRecepcion) {
        this.selectedRecepcion = selectedRecepcion;
    }

    /**
     * @return the listInsumos
     */
    public List<TablaInsumoOrdenCompraMB> getListInsumos() {
        return listInsumos;
    }

    /**
     * @param listInsumos the listInsumos to set
     */
    public void setListInsumos(List<TablaInsumoOrdenCompraMB> listInsumos) {
        this.listInsumos = listInsumos;
    }

    /**
     * @return the listInsumosDevolucion
     */
    public List<TablaInsumoOrdenCompraMB> getListInsumosDevolucion() {
        return listInsumosDevolucion;
    }

    /**
     * @param listInsumosDevolucion the listInsumosDevolucion to set
     */
    public void setListInsumosDevolucion(List<TablaInsumoOrdenCompraMB> listInsumosDevolucion) {
        this.listInsumosDevolucion = listInsumosDevolucion;
    }
    
}
