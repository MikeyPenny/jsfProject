/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.bean.FamiliaMat;
import com.bean.ProyectoBean;
import com.bean.TipoInsumo;
import com.conexion.ConexionBD;
import com.dao.FamiliaDAO;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;



import javax.inject.Named;

@Named("familia")
@ViewScoped
public class CatalogoFamMB implements Serializable {
        
    private List<ProyectoBean> listProy;
    private int id_proyecto;
    private String proyecto;
    private ProyectoBean proySel;
    
    private List<TipoInsumo> listTipos;
    private int id_tipo;
    private String tipoStr;
    
    private String familiaStr;
    private String familiaCve;
    
    private List<FamiliaMat> listFam;
    private FamiliaMat elimFam;
    
    @PostConstruct
    public void init() {
        
        ConexionBD c = new ConexionBD();
        FamiliaDAO dao = new FamiliaDAO(c.getConexion());
        setListProy(dao.leerProyectos());
        c = new ConexionBD();
        dao = new FamiliaDAO(c.getConexion());
        setListTipos(dao.leerTiposInsumo());
        
    }
    
    public void eliminarFamilia() {
        
        ConexionBD c = new ConexionBD();
        FamiliaDAO dao = new FamiliaDAO(c.getConexion());
        dao.eliminarFamilia(elimFam.getId_familia());
        listarFamilias();
        
    }
    
    public void listarFamilias() {
        
        ConexionBD c = new ConexionBD();
        FamiliaDAO dao = new FamiliaDAO(c.getConexion());
        setListFam(dao.listarFamPorPoryecto(id_proyecto));
        
    }
    
    public void registrarFamilia() {
        
        FamiliaMat fam = new FamiliaMat();
        fam.setFamilia(familiaStr);
        fam.setClave(familiaCve);
        fam.setId_proyecto(id_proyecto);
        
        ConexionBD c = new ConexionBD();
        FamiliaDAO dao = new FamiliaDAO(c.getConexion());
        int reg = dao.registrarFamilia(fam);
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        if(reg>0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", "Se agregó una nueva "
                    + "familia"));
        }else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se agregó la información"));
        }
        
        listarFamilias();
        resetFields();
        
    }
    
    public void resetFields() {
        
        setFamiliaCve(null);
        setFamiliaStr(null);
        
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

    /**
     * @return the listProy
     */
    public List<ProyectoBean> getListProy() {
        return listProy;
    }

    /**
     * @param listProy the listProy to set
     */
    public void setListProy(List<ProyectoBean> listProy) {
        this.listProy = listProy;
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
     * @return the proySel
     */
    public ProyectoBean getProySel() {
        return proySel;
    }

    /**
     * @param proySel the proySel to set
     */
    public void setProySel(ProyectoBean proySel) {
        this.proySel = proySel;
    }

    /**
     * @return the listTipos
     */
    public List<TipoInsumo> getListTipos() {
        return listTipos;
    }

    /**
     * @param listTipos the listTipos to set
     */
    public void setListTipos(List<TipoInsumo> listTipos) {
        this.listTipos = listTipos;
    }

    /**
     * @return the id_tipo
     */
    public int getId_tipo() {
        return id_tipo;
    }

    /**
     * @param id_tipo the id_tipo to set
     */
    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }

    /**
     * @return the tipoStr
     */
    public String getTipoStr() {
        return tipoStr;
    }

    /**
     * @param tipoStr the tipoStr to set
     */
    public void setTipoStr(String tipoStr) {
        this.tipoStr = tipoStr;
    }

    /**
     * @return the familiaStr
     */
    public String getFamiliaStr() {
        return familiaStr;
    }

    /**
     * @param familiaStr the familiaStr to set
     */
    public void setFamiliaStr(String familiaStr) {
        this.familiaStr = familiaStr;
    }

    /**
     * @return the familiaCve
     */
    public String getFamiliaCve() {
        return familiaCve;
    }

    /**
     * @param familiaCve the familiaCve to set
     */
    public void setFamiliaCve(String familiaCve) {
        this.familiaCve = familiaCve;
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
     * @return the listFam
     */
    public List<FamiliaMat> getListFam() {
        return listFam;
    }

    /**
     * @param listFam the listFam to set
     */
    public void setListFam(List<FamiliaMat> listFam) {
        this.listFam = listFam;
    }

    /**
     * @return the elimFam
     */
    public FamiliaMat getElimFam() {
        return elimFam;
    }

    /**
     * @param elimFam the elimFam to set
     */
    public void setElimFam(FamiliaMat elimFam) {
        this.elimFam = elimFam;
    }
    
    
    
}
