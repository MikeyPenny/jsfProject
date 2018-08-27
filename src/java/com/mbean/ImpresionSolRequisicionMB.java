/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mickey
 */
@ManagedBean
@RequestScoped
public class ImpresionSolRequisicionMB {

    @ManagedProperty(value = "#{formPrintRequisicion}")
    private FormPrintRequisicion formPrintRequisicion;
    
    private List<InsumoTablaMB> listInsumos;
    
    public ImpresionSolRequisicionMB() {
    }
    
    public void verImpresion(){
         HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
         formPrintRequisicion.setEstatus(session.getAttribute("estatus").toString());
         formPrintRequisicion.setFechaAutorizacion(session.getAttribute("fechaAutorizacion").toString());
         formPrintRequisicion.setFechaSolicitud(session.getAttribute("fechaSolicitud").toString());
         formPrintRequisicion.setNoRequisicion(Integer.parseInt( session.getAttribute("noRequisicion").toString()));
         formPrintRequisicion.setNombreProyecto(session.getAttribute("nombreProyecto").toString());
         formPrintRequisicion.setNombreUsuarioAutorizacion(session.getAttribute("nombreUsuarioAutorizacion").toString());
         formPrintRequisicion.setNombreUsuarioSolicitante(session.getAttribute("nombreUsuarioSolicitante").toString());
         
         listInsumos = (List<InsumoTablaMB>) session.getAttribute("insumos");
         
         System.out.println("Fin de la Session");
         
    }

    public FormPrintRequisicion getFormPrintRequisicion() {
        return formPrintRequisicion;
    }

    public void setFormPrintRequisicion(FormPrintRequisicion formPrintRequisicion) {
        this.formPrintRequisicion = formPrintRequisicion;
    }

    public List<InsumoTablaMB> getListInsumos() {
        return listInsumos;
    }

    public void setListInsumos(List<InsumoTablaMB> listInsumos) {
        this.listInsumos = listInsumos;
    }
}
