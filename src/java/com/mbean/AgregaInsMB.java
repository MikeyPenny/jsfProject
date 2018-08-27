/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.bean.InsumoBean;
import com.bean.MaterialBean;
import com.bean.Unidad;
import com.conexion.ConexionBD;
import com.dao.MaterialDAO;
import com.dao.PresupuestoDAO;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("agregIns")
@ViewScoped
public class AgregaInsMB implements Serializable {
    
    private String cveIns;
    private String descIns;
    private String unidIns;
    private String editCveIns;
    private String editDescIns;
    private String editUnidIns;
    
    private List<Unidad> listUnid = new ArrayList<>();
    private boolean btnAgreg;
    
    private List<MaterialBean> listMat = new ArrayList<>();
    private MaterialBean matSel;
    private MaterialBean matElim;
    
    private String cveInsAux;
    
    @PostConstruct
    public void init() {
    
        ConexionBD c = new ConexionBD();
        PresupuestoDAO pd = new PresupuestoDAO(c.getConexion());
        setListUnid(pd.listaUnidades());
        mostrarMateriales();
        setBtnAgreg(true);
    }
    
    public void agregarInsumo() {
        
        FacesContext msj = FacesContext.getCurrentInstance();
        
        if(validarClaveMat() && !descIns.equals("")) {
            
            MaterialBean mat = new MaterialBean();
            mat.setClaveMat(cveIns);
            mat.setMaterial(descIns);
            mat.setUnidad(unidIns);
            ConexionBD c = new ConexionBD();
            MaterialDAO dao = new MaterialDAO(c.getConexion());
            int reg = dao.agregMaterial(mat);
            
            if(reg > 0) {
                msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", 
                        "Se agregó el material al catálogo"));
            }else {
                msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", 
                        "No se agregó el material"));
            }
        }else {
            
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", 
                        "Error en los datos"));
            
        }
        
        refresh();
        mostrarMateriales();
        
    }
    
    public boolean validarClaveMat() {
    
        FacesContext msj = FacesContext.getCurrentInstance();
        boolean chk = true;
        
        //System.out.println(cveIns.charAt(7));
        if(cveIns.length() != 12) {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "La estructura del código debe ser CVE-Fam-XXXX, revisar la longitud a 12 caracteres"));
            setCveIns(null);
            chk = false;
        }else {
            if(cveIns.charAt(3) != '-') {
                msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "La estructura del código debe ser CVE-Fam-XXXX, la longitud de la CVE no debe ser mayor ni menor a 3 caracteres y debe ser seguida por un guión"));
                char correct = '-';
                setCveIns(cveIns.replace(cveIns.charAt(3), correct));
                chk = false;
            }
            if(cveIns.charAt(7) != '-') {
                msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "La estructura del código debe ser CVE-Fam-XXXX, la longitud de la Fam no debe ser mayor ni menor a 3 caracteres y debe ser seguida por un guión"));
                char correct = '-';
                setCveIns(cveIns.replace(cveIns.charAt(7), correct));
                chk = false;
            }
        }
            
        return chk;
        
    }
    
    public void verificarDuplicado() {
        
        FacesContext msj = FacesContext.getCurrentInstance();
        
        
        
        ConexionBD c = new ConexionBD();
        MaterialDAO dao = new MaterialDAO(c.getConexion());
        if(dao.buscarClaveDuplicada(cveIns)) {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", 
                        "La clave de ese material ya fue registrada"));
            refresh();
        }
        
    }
    
    public void refresh() {
            FacesContext context = FacesContext.getCurrentInstance();
            Application application = context.getApplication();
            ViewHandler viewHandler = application.getViewHandler();
            UIViewRoot viewRoot = viewHandler.createView(context, context
             .getViewRoot().getViewId());
            context.setViewRoot(viewRoot);
            context.renderResponse(); 
    }
    
    public void mostrarMateriales() {
        
        ConexionBD c = new ConexionBD();
        MaterialDAO dao = new MaterialDAO(c.getConexion());
        setListMat(dao.listarCatMaterial());
        
    }
    
    public void seleccionarMaterial() {
        setEditCveIns(matSel.getClaveMat());
        setEditDescIns(matSel.getMaterial());
        setEditUnidIns(matSel.getUnidad());
    }
    
    public void editarMaterial() {
        
        FacesContext msj = FacesContext.getCurrentInstance();
        
        MaterialBean mat = new MaterialBean();
        mat.setId_material(matSel.getId_material());
        mat.setClaveMat(editCveIns);
        mat.setMaterial(editDescIns);
        mat.setUnidad(editUnidIns);
        ConexionBD c = new ConexionBD();
        MaterialDAO dao = new MaterialDAO(c.getConexion());
        int ed = dao.editarMaterial(mat);
        
        if(ed > 0) {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", 
                    "Se editó el material en el catálogo"));
        }else {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", 
                    "No se editó el material"));
        }
        
        refresh();
        
        
    }
    
    public void eliminarMaterial() {
        
        FacesContext msj = FacesContext.getCurrentInstance();
        
        ConexionBD c = new ConexionBD();
        MaterialDAO dao = new MaterialDAO(c.getConexion());
        int del = dao.eliminaMaterial(matElim.getId_material());
        
        if(del > 0) {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", 
                    "Se eliminó el material del catálogo"));
        }else {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", 
                    "No se eliminó el material"));
        }
        
        refresh();
        
    }
    
    
     
    public void turnOnBtn() {
        setBtnAgreg(false);
    }

    /**
     * @return the cveIns
     */
    public String getCveIns() {
        return cveIns;
    }

    /**
     * @param cveIns the cveIns to set
     */
    public void setCveIns(String cveIns) {
        this.cveIns = cveIns;
    }

    /**
     * @return the descIns
     */
    public String getDescIns() {
        return descIns;
    }

    /**
     * @param descIns the descIns to set
     */
    public void setDescIns(String descIns) {
        this.descIns = descIns;
    }

    /**
     * @return the unidIns
     */
    public String getUnidIns() {
        return unidIns;
    }

    /**
     * @param unidIns the unidIns to set
     */
    public void setUnidIns(String unidIns) {
        this.unidIns = unidIns;
    }

    /**
     * @return the listUnid
     */
    public List<Unidad> getListUnid() {
        return listUnid;
    }

    /**
     * @param listUnid the listUnid to set
     */
    public void setListUnid(List<Unidad> listUnid) {
        this.listUnid = listUnid;
    }

    /**
     * @return the btnAgreg
     */
    public boolean isBtnAgreg() {
        return btnAgreg;
    }

    /**
     * @param btnAgreg the btnAgreg to set
     */
    public void setBtnAgreg(boolean btnAgreg) {
        this.btnAgreg = btnAgreg;
    }

    /**
     * @return the listMat
     */
    public List<MaterialBean> getListMat() {
        return listMat;
    }

    /**
     * @param listMat the listMat to set
     */
    public void setListMat(List<MaterialBean> listMat) {
        this.listMat = listMat;
    }

    /**
     * @return the matSel
     */
    public MaterialBean getMatSel() {
        return matSel;
    }

    /**
     * @param matSel the matSel to set
     */
    public void setMatSel(MaterialBean matSel) {
        this.matSel = matSel;
    }

    /**
     * @return the editCveIns
     */
    public String getEditCveIns() {
        return editCveIns;
    }

    /**
     * @param editCveIns the editCveIns to set
     */
    public void setEditCveIns(String editCveIns) {
        this.editCveIns = editCveIns;
    }

    /**
     * @return the editDescIns
     */
    public String getEditDescIns() {
        return editDescIns;
    }

    /**
     * @param editDescIns the editDescIns to set
     */
    public void setEditDescIns(String editDescIns) {
        this.editDescIns = editDescIns;
    }

    /**
     * @return the editUnidIns
     */
    public String getEditUnidIns() {
        return editUnidIns;
    }

    /**
     * @param editUnidIns the editUnidIns to set
     */
    public void setEditUnidIns(String editUnidIns) {
        this.editUnidIns = editUnidIns;
    }

    /**
     * @return the matElim
     */
    public MaterialBean getMatElim() {
        return matElim;
    }

    /**
     * @param matElim the matElim to set
     */
    public void setMatElim(MaterialBean matElim) {
        this.matElim = matElim;
    }

    /**
     * @return the cveInsAux
     */
    public String getCveInsAux() {
        return cveInsAux;
    }

    /**
     * @param cveInsAux the cveInsAux to set
     */
    public void setCveInsAux(String cveInsAux) {
        this.cveInsAux = cveInsAux;
    }
    
}
