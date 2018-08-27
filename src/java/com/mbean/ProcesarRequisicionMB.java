/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.conexion.ConexionBD;
import com.model.ExpInsumos;
import com.model.InsumoRequisicion;
import com.model.Presupuesto;
import com.model.Requisicion;
import com.services.RequisicionBS;
import java.io.Serializable;
import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mickey
 */
@ManagedBean
@ViewScoped
public class ProcesarRequisicionMB implements Serializable {

    @ManagedProperty(value = "#{formRequisicionMB}")
    private FormRequisicionMB formRequisicionMB;

    @ManagedProperty(value = "#{formProyectosMB}")
    private FormProyectosMB formProyectosMB;
    
    public ProcesarRequisicionMB() {
    }
    
    public void guardarRequisicion() {
        Requisicion r = new Requisicion();
        System.out.println("PRRRRR: " +  formProyectosMB.getNombreProyecto());
        r.setEstatus(formRequisicionMB.getEstatus());
        r.setFechaAutorizacion(formRequisicionMB.getFechaAutorizacion());
        r.setFechasolicitud(formRequisicionMB.getFechaSolicitud());
        r.setIdUsuarioAutorizacion(formRequisicionMB.getIdUsuarioAutorizacion());
        r.setIdUsuarioSolicitante(1);

        Presupuesto pre = new Presupuesto();
        pre.setIdPresupuesto(formRequisicionMB.getIdPresupuesto());

        r.setPresupuesto(pre);

        List<InsumoTablaMB> listaInsumos = formProyectosMB.getListInsumosSol();
        Set<InsumoRequisicion> s = new HashSet<>();
        for (InsumoTablaMB aux : listaInsumos) {
            InsumoRequisicion ihr = new InsumoRequisicion();
            ihr.setCantidad(aux.getCantDisponible());
            ihr.setPreciounitario(aux.getPrecioUnitario());
            ExpInsumos expins = new ExpInsumos();
            expins.setIdExpinsumos(aux.getIdInsumoTabla());
                    
            ihr.setExpInsumos(expins);
            s.add(ihr);
        }
        r.setInsumoRequisicions(s);
        
        ConexionBD c =  new ConexionBD();
        Connection con = c.getConexion();
        RequisicionBS rbs = new RequisicionBS(con);
        int idRequisicion = rbs.guardarRequisicion(r);

        if (idRequisicion > 0) {
            formRequisicionMB.setIdRequisicion(idRequisicion);
            formRequisicionMB.setFechaAutorizacion("ESPERANDO FECHA DE AUTORIZACIÓN");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación Finalizada ", "Se ha generado correctamente la Requisición con ID: " + idRequisicion));
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("noRequisicion", new Integer(idRequisicion ));
            session.setAttribute("estatus", new String( formRequisicionMB.getEstatus()));
            session.setAttribute("fechaSolicitud", new String( formRequisicionMB.getFechaSolicitud()));
            session.setAttribute("fechaAutorizacion", new String( formRequisicionMB.getFechaAutorizacion()));
            session.setAttribute("nombreUsuarioSolicitante", new String( formRequisicionMB.getNombreUsuarioSolicitante()));
            session.setAttribute("nombreUsuarioAutorizacion", new String( formRequisicionMB.getNombreUsuarioAutorizacion()));
            session.setAttribute("insumos", listaInsumos);
            session.setAttribute("nombreProyecto", new String (formProyectosMB.getSelectedProyecto().getProyecto()));
            
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la Operación ", "No se puede procesar la petición"));
        }
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
    
}
