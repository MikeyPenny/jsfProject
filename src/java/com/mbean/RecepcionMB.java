/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.conexion.ConexionBD;
import com.model.InsumoRequisicion;
import com.model.OrdenCompra;
import com.model.Proyecto;
import com.model.Recepcion;
import com.model.Recepcioninsumo;
import com.services.OrdenCompraBS;
import com.services.ProyectoBS;
import com.services.RecepcionBS;
import com.util.Constantes;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class RecepcionMB implements Serializable {

    private int idProyecto;
    private String nombreProyecto;
    private int numOrc;
    private int requisicion;
    private int noRecepcion;
    private String nombreProveedor;
    private String fechaRecepcion;
    
    private List<FormDetalleOrdenCompraMB> listOc;
    private FormDetalleOrdenCompraMB selectedOc;
    
    private List<TablaInsumoOrdenCompraMB> listInsumos;
    
    private List<Proyecto> listProyectos;
    private Proyecto selectedProyecto;
    
    public RecepcionMB() {
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        ProyectoBS pbs = new ProyectoBS(con);
        listProyectos = pbs.listarProyectos();
        selectedProyecto = new Proyecto();
        Date fecha = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy H:m:s");
        fechaRecepcion = sdf.format(fecha);
    }
    
    public void autorizarRecepcion(){
        List<Recepcioninsumo> listRecIns = new ArrayList<>();
        boolean isParcial = false;
        for(TablaInsumoOrdenCompraMB aux :listInsumos){
            System.out.println("Cantidad: " + aux.getIdInsumo());
            if(!aux.getCantidad().equals(BigDecimal.ZERO)){
                Recepcioninsumo item = new Recepcioninsumo();
                item.setCantidad(aux.getCantidad());
                item.setCantidadOriginal(aux.getCantidad());
                item.setPrecio(aux.getPrecio());
                InsumoRequisicion ir = new InsumoRequisicion();
                ir.setIdinsumoRequisicion(aux.getIdInsumo());
                item.setInsumoRequisicion(ir);
                if(!aux.getCantidad().equals(aux.getCantidadOriginal())){
                    isParcial = true;
                }
                listRecIns.add(item);
            }
        }
        
        if(!listRecIns.isEmpty()){
            ConexionBD c = new ConexionBD();
            Connection con = c.getConexion();
            RecepcionBS rcbs = new RecepcionBS(con);
            Recepcion re = new Recepcion();
            re.setEstatus(Constantes._LBL_REC_TOT);
            OrdenCompra oc = new OrdenCompra();
            oc.setIdordenCompra(selectedOc.getIdOrdenCompra());
            re.setOrdenCompra(oc);
            int noRecep = rcbs.autorizarRecepcion(selectedProyecto.getIdProyecto(), re, listRecIns, !isParcial);
            if (noRecep != -1) {
                setNoRecepcion(noRecep);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación Finalizada ", "Se ha generado correctamente la Recepción con No.: " + noRecepcion));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la Operación ", "No se puede procesar la petición"));
            }
        }
    }
    
    public void actualizarRecepcion(){
        setIdProyecto(selectedProyecto.getIdProyecto());
        setNombreProyecto(selectedProyecto.getProyecto());
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        OrdenCompraBS oc = new OrdenCompraBS(con);
        System.out.println("Proyecto: " + getSelectedProyecto().getIdProyecto());
        
        listOc = oc.listarOrdenCompraPorIdProyecto(selectedProyecto.getIdProyecto());
        //formRecepcionMB.setNombreProyecto(proyectosMB.getSelectedProyecto().getProyecto());
    }
    
    public void actualizarDatos(){
        setNumOrc(selectedOc.getIdOrdenCompra());
        setNombreProveedor(selectedOc.getNombreProvedor());
        setRequisicion(selectedOc.getRequisicion());
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        OrdenCompraBS ocbs = new OrdenCompraBS(con);
        listInsumos = ocbs.listarDetalleOrdenCompra(selectedOc.getIdOrdenCompra());
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
        BigDecimal bOld = new BigDecimal( lista.get(event.getRowIndex()).getCantidadOriginal().toString());
        BigDecimal bNew = new BigDecimal(newValue.toString());
        System.out.println("VAL:: " + bOld.compareTo(bNew));

        if (bOld.compareTo(bNew) == -1) {
            lista.get(event.getRowIndex()).setCantidad(bOld);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cantidad Insuficiente ", "La cantidad Solicitada es mayor a la Disponible: "+ bOld.setScale(3,BigDecimal.ROUND_HALF_DOWN )));
        } else {
            BigDecimal pUnitario = lista.get(event.getRowIndex()).getPrecio();
            BigDecimal imp = bNew.multiply(pUnitario).setScale(2,BigDecimal.ROUND_HALF_DOWN );
            listInsumos.get(event.getRowIndex()).setImporte(imp);
            //actualizaTotal();
            System.out.println("Importe: " +  lista.get(event.getRowIndex()).getImporte());
        }
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
     * @return the idProyecto
     */
    public int getIdProyecto() {
        return idProyecto;
    }

    /**
     * @param idProyecto the idProyecto to set
     */
    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
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
     * @return the numOrc
     */
    public int getNumOrc() {
        return numOrc;
    }

    /**
     * @param numOrc the numOrc to set
     */
    public void setNumOrc(int numOrc) {
        this.numOrc = numOrc;
    }

    /**
     * @return the requisicion
     */
    public int getRequisicion() {
        return requisicion;
    }

    /**
     * @param requisicion the requisicion to set
     */
    public void setRequisicion(int requisicion) {
        this.requisicion = requisicion;
    }

    /**
     * @return the noRecepcion
     */
    public int getNoRecepcion() {
        return noRecepcion;
    }

    /**
     * @param noRecepcion the noRecepcion to set
     */
    public void setNoRecepcion(int noRecepcion) {
        this.noRecepcion = noRecepcion;
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
    
}
