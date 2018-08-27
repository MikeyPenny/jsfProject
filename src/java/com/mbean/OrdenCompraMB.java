/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.bean.AnticipoBean;
import com.conexion.ConexionBD;
import com.model.OrdenCompra;
import com.model.Presupuesto;
import com.model.Proveedor;
import com.model.Proyecto;
import com.model.Requisicion;
import com.services.OrdenCompraBS;
import com.services.PresupuestoBS;
import com.services.ProveedoresBS;
import com.services.ProyectoBS;
import com.services.RequisicionBS;
import com.util.DireccionProyecto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Mickey
 */
@ManagedBean
@ViewScoped
public class OrdenCompraMB implements Serializable {

    @ManagedProperty(value = "#{formOrdenCompraMB}")
    private FormOrdenCompraMB formOrdenCompraMB;

    private List<Proyecto> listProyectos;
    private Proyecto selectedProyecto;

    private List<Presupuesto> listPresupuesto;
    private Presupuesto selectedPresupuesto;

    private List<FormTablaInsumoOrdenCompraMB> listInsumos;

    private List<FormProveedorMB> listProveedores;
    private FormProveedorMB selectedProveedor;

    private String nombreProyecto;
    private String noPresupuesto;

    private List<SelectItem> noRequisiciones;
    private String selectedIdRequisiciones;
    
    private BigDecimal totalOc;
    private String direccion;
    
    private boolean anticipo;
    private BigDecimal pctAnt;

    public OrdenCompraMB() {
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        listProyectos = new ProyectoBS(con).listarProyectos();
        listPresupuesto = new ArrayList<>();
        listInsumos = new ArrayList<>();
        c = new ConexionBD();
        con = c.getConexion();
        listProveedores = new ProveedoresBS(con).listarProveedoresForm();
        noRequisiciones = new ArrayList<>();
        totalOc = new BigDecimal(0.0);
    }
    
    public void guardarOrdenCompra() {
        System.out.println(formOrdenCompraMB.getIdProveedor());
        
        int noOrdenCompra = 0;
        
        if(formOrdenCompraMB.getIdProveedor() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la Operación ", "No se puede procesar la petición"));
        }else {
            OrdenCompra oc = new OrdenCompra();
            oc.setComentarios(" SIN COMENTARIOS ");
            oc.setEmbarque(formOrdenCompraMB.getDirEmbarque());
            oc.setFechaSolicitud(formOrdenCompraMB.getFechaSolicitud());
            oc.setIdUsuarioSolicitante(1);
            oc.setEstatus(formOrdenCompraMB.getEstatus());
            oc.setIva(formOrdenCompraMB.getIva());
            oc.setPago(formOrdenCompraMB.getFormaPago());
            oc.setEmbarque(getDireccion());

            Proveedor p = new Proveedor();
            p.setIdProveedor(formOrdenCompraMB.getIdProveedor());

            Requisicion r = new Requisicion();
            r.setIdRequisicion(Integer.parseInt(selectedIdRequisiciones));

            oc.setProveedor(p);
            oc.setRequisicion(r);
            
            if(anticipo) {
                if(pctAnt.floatValue() <= 0.00){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El porcentaje no puede ser cero o menor a cero"));
                }else {
                    ConexionBD c = new ConexionBD();
                    Connection con = c.getConexion();
                    OrdenCompraBS ocbs = new OrdenCompraBS(con);
                    noOrdenCompra = ocbs.generarOrdenCompra(oc,listInsumos);
                    if(noOrdenCompra > 0)
                        generarAnticipo(noOrdenCompra);
                    System.out.println("IDOC: " + noOrdenCompra);
                }
            }else {
                ConexionBD c = new ConexionBD();
                Connection con = c.getConexion();
                OrdenCompraBS ocbs = new OrdenCompraBS(con);
                noOrdenCompra = ocbs.generarOrdenCompra(oc,listInsumos);
                System.out.println("IDOC: " + noOrdenCompra);
            }

            
            if (noOrdenCompra > 0) {
                formOrdenCompraMB.setNoOrdenCompra(noOrdenCompra);
                listarRequisiciones();
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación Finalizada ", "Se ha generado correctamente la Orden de Compra con No.: " + noOrdenCompra));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la Operación ", "No se puede procesar la petición"));
            }
        }
        
    }
    
    public void generarAnticipo(int id) {
        
        float pct = getPctAnt().floatValue()/100;
        
        AnticipoBean ant = new AnticipoBean();
        ant.setId_contrato(id);
        ant.setImporte(getTotalOc().multiply(BigDecimal.valueOf(pct)));
        ant.setContratista(formOrdenCompraMB.getProveedor());
        ant.setTipo("antOC");
        ant.setIva(ant.getImporte().multiply(BigDecimal.valueOf(.16)));
        ant.setTotal(ant.getImporte().add(ant.getIva()));
        ant.setPctAnt(getPctAnt());
        
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        OrdenCompraBS ocbs = new OrdenCompraBS(con);
        ocbs.generarAnticipo(ant);
        
    }
    
    public void listarRequisiciones() {
        noPresupuesto = selectedPresupuesto.getPresupuesto();
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        RequisicionBS rbs = new RequisicionBS(con);
        noRequisiciones.clear();
        Map<Integer, Integer> listaRequ = rbs.listadoRequisicionesAutorizados(selectedPresupuesto.getIdPresupuesto());
        Set<Integer> ids = listaRequ.keySet();
        for (Integer aux : ids) {
            System.out.println("Aux: "+ aux);
            SelectItem s = new SelectItem(aux, listaRequ.get(aux).toString());
            noRequisiciones.add(s);
        }

    }
    
    public void onCellEdit(CellEditEvent event) {
        DataTable tabla = (DataTable) event.getComponent();
        List<FormTablaInsumoOrdenCompraMB> lista = (List<FormTablaInsumoOrdenCompraMB>) tabla.getValue();
        
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        

        if (oldValue.toString().equals("0")) {

            lista.get(event.getRowIndex()).setCantidad(BigDecimal.ZERO);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cantidad Insuficiente ", "La cantidad disponible es insuficiente"));
        }
        System.out.println("Antes: " + oldValue + " Despues: " + newValue);
        BigDecimal bOld = new BigDecimal(oldValue.toString());
        BigDecimal bNew = new BigDecimal(newValue.toString());
        System.out.println("VAL:: " + bOld.compareTo(bNew));

        if (bOld.compareTo(bNew) == -1) {
            lista.get(event.getRowIndex()).setPrecioUnitario(bOld);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cantidad Insuficiente ", "La cantidad Solicitada es mayor a la Disponible"));
        } else {
            BigDecimal cantidad = lista.get(event.getRowIndex()).getCantidad();
            BigDecimal imp = cantidad.multiply(bNew).setScale(2,BigDecimal.ROUND_HALF_DOWN );
            listInsumos.get(event.getRowIndex()).setImporte(imp);
            actualizaTotal();
            System.out.println("Importe: " +  lista.get(event.getRowIndex()).getImporte());
        }
    }
    
    private void actualizaTotal(){
        totalOc = totalOc.multiply(BigDecimal.ZERO);
        for (FormTablaInsumoOrdenCompraMB aux : listInsumos) {
            totalOc = totalOc.add(aux.getImporte());
        }
       totalOc=  totalOc.setScale(2,BigDecimal.ROUND_HALF_DOWN );
    }
    
    public void seleccionarProveedor() {
        System.out.println("Selección: " + selectedProveedor);
        formOrdenCompraMB.setDirEmbarque("   ");
        formOrdenCompraMB.setProveedor(selectedProveedor.getRazonSocial());
        formOrdenCompraMB.setIdProveedor(selectedProveedor.getIdProveedor());
        selectedProveedor = null;
    }
    
    public void buscarPresupuestos() {

        noPresupuesto = "";
        listPresupuesto.clear();
        selectedPresupuesto = null;
        listInsumos.clear();

        nombreProyecto = selectedProyecto.getProyecto();
        
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        PresupuestoBS psb = new PresupuestoBS(con);

        List<Presupuesto> pres = psb.listarPresupuestosPorIDProyecto(selectedProyecto.getIdProyecto());

        Iterator it = pres.iterator();
        listPresupuesto.clear();
        while (it.hasNext()) {
            Presupuesto aux = (Presupuesto) it.next();
            listPresupuesto.add(aux);
        }
        
        DireccionProyecto dir = new DireccionProyecto();
        setDireccion(dir.obtenerDireccion(selectedProyecto.getIdProyecto()));
    }
    
    public void listarInsumosRequicisiones() {
        System.out.println("SELECIONADO: " + selectedIdRequisiciones);
        noPresupuesto = selectedPresupuesto.getPresupuesto();
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        RequisicionBS rbs = new RequisicionBS(con);
        listInsumos = rbs.listarInsumosRequisicionPorId(Integer.parseInt(selectedIdRequisiciones));
        System.out.println("**** Tamaño: " + listInsumos.size());
        
        actualizaTotal();
    }
    
    
    

    /**
     * @return the formOrdenCompraMB
     */
    public FormOrdenCompraMB getFormOrdenCompraMB() {
        return formOrdenCompraMB;
    }

    /**
     * @param formOrdenCompraMB the formOrdenCompraMB to set
     */
    public void setFormOrdenCompraMB(FormOrdenCompraMB formOrdenCompraMB) {
        this.formOrdenCompraMB = formOrdenCompraMB;
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
    public List<FormTablaInsumoOrdenCompraMB> getListInsumos() {
        return listInsumos;
    }

    /**
     * @param listInsumos the listInsumos to set
     */
    public void setListInsumos(List<FormTablaInsumoOrdenCompraMB> listInsumos) {
        this.listInsumos = listInsumos;
    }

    /**
     * @return the listProveedores
     */
    public List<FormProveedorMB> getListProveedores() {
        return listProveedores;
    }

    /**
     * @param listProveedores the listProveedores to set
     */
    public void setListProveedores(List<FormProveedorMB> listProveedores) {
        this.listProveedores = listProveedores;
    }

    /**
     * @return the selectedProveedor
     */
    public FormProveedorMB getSelectedProveedor() {
        return selectedProveedor;
    }

    /**
     * @param selectedProveedor the selectedProveedor to set
     */
    public void setSelectedProveedor(FormProveedorMB selectedProveedor) {
        this.selectedProveedor = selectedProveedor;
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
     * @return the noRequisiciones
     */
    public List<SelectItem> getNoRequisiciones() {
        return noRequisiciones;
    }

    /**
     * @param noRequisiciones the noRequisiciones to set
     */
    public void setNoRequisiciones(List<SelectItem> noRequisiciones) {
        this.noRequisiciones = noRequisiciones;
    }

    /**
     * @return the selectedIdRequisiciones
     */
    public String getSelectedIdRequisiciones() {
        return selectedIdRequisiciones;
    }

    /**
     * @param selectedIdRequisiciones the selectedIdRequisiciones to set
     */
    public void setSelectedIdRequisiciones(String selectedIdRequisiciones) {
        this.selectedIdRequisiciones = selectedIdRequisiciones;
    }

    /**
     * @return the totalOc
     */
    public BigDecimal getTotalOc() {
        return totalOc;
    }

    /**
     * @param totalOc the totalOc to set
     */
    public void setTotalOc(BigDecimal totalOc) {
        this.totalOc = totalOc;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the anticipo
     */
    public boolean isAnticipo() {
        return anticipo;
    }

    /**
     * @param anticipo the anticipo to set
     */
    public void setAnticipo(boolean anticipo) {
        this.anticipo = anticipo;
    }

    /**
     * @return the pctAnt
     */
    public BigDecimal getPctAnt() {
        return pctAnt;
    }

    /**
     * @param pctAnt the pctAnt to set
     */
    public void setPctAnt(BigDecimal pctAnt) {
        this.pctAnt = pctAnt;
    }
    
}
