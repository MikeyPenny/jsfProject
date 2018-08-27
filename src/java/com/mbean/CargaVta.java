/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.bo.CargarProformaBO;
import com.bean.PartidaBean;
import com.bean.PresupuestoBean;
import com.bean.ProyectoSimple;
import com.conexion.ConexionBD;
import com.dao.PresupuestoDAO;
import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;


@ManagedBean
@ViewScoped
public class CargaVta implements Serializable {


    public CargaVta() {
    }
    
    private int id_proyecto;
    private String proyecto;
    private int id_presupuesto;
    private String presupuesto;
    private File file2;
    private Part file;
    
    private UploadedFile upload;
    
    private List<PartidaBean> listaPart;
    
    private ArrayList<ProyectoSimple> listaProy = new ArrayList<ProyectoSimple>();
    private ProyectoSimple proySel;
    
    private ArrayList<PresupuestoBean> listaPres = new ArrayList<PresupuestoBean>();
    private PresupuestoBean presB;
    private int res;
    
    public void handleFileUploadListener(FileUploadEvent event) {
        upload = event.getFile();
        
        if(id_presupuesto != 0) {
            CargarProformaBO carga = new CargarProformaBO();
            setRes(carga.cargarArchivoVta(upload, id_presupuesto));
            
        }else {
            setRes(-1);
        }
        onComplete();
    }
    
    public void cargarArchivo() {
        
        CargarProformaBO carga = new CargarProformaBO();
        setRes(carga.cargarArchivo(upload, id_presupuesto));
        
    }
    
    public void onComplete() {
        
        if(res > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
         
            context.addMessage(null, new FacesMessage("Correcto!",  "El presupuesto " + id_presupuesto + " se cargó con éxito") );
            
        }else {
            FacesContext context = FacesContext.getCurrentInstance();
         
            context.addMessage(null, new FacesMessage("Error!",  "Hubo un problema con la carga"));
        }
    }
    
    public void buscarProyecto() {
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        PresupuestoDAO presD = new PresupuestoDAO(con);
        setListaProy(presD.listarProyecto());      
    }
    
    public void obtenerProyecto() {
        setId_proyecto(proySel.getId_proyecto());
        setProyecto(proySel.getProyecto());
        buscarPresupuesto();
    }
    
    public void buscarPresupuesto() {
        
        if(proySel != null) {
            Connection con;
            ConexionBD conexion = new ConexionBD();
            con = conexion.getConexion();
            PresupuestoDAO presD = new PresupuestoDAO(con);
            
            setListaPres(presD.listarPresupuesto(proySel.getId_proyecto(), 1));
            //System.out.println(listaPres.size());
        }
        
    }
    
    public void obtenerPresupuesto() {
        setId_presupuesto(presB.getId_presupuesto());
        setPresupuesto(presB.getPresupuesto());
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
     * @return the id_presupuesto
     */
    public int getId_presupuesto() {
        return id_presupuesto;
    }

    /**
     * @param id_presupuesto the id_presupuesto to set
     */
    public void setId_presupuesto(int id_presupuesto) {
        this.id_presupuesto = id_presupuesto;
    }

    /**
     * @return the presupuesto
     */
    public String getPresupuesto() {
        return presupuesto;
    }

    /**
     * @param presupuesto the presupuesto to set
     */
    public void setPresupuesto(String presupuesto) {
        this.presupuesto = presupuesto;
    }

    /**
     * @return the file2
     */
    public File getFile2() {
        return file2;
    }

    /**
     * @param file2 the file2 to set
     */
    public void setFile2(File file2) {
        this.file2 = file2;
    }

    /**
     * @return the file
     */
    public Part getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(Part file) {
        this.file = file;
    }

    /**
     * @return the upload
     */
    public UploadedFile getUpload() {
        return upload;
    }

    /**
     * @param upload the upload to set
     */
    public void setUpload(UploadedFile upload) {
        this.upload = upload;
    }

    /**
     * @return the listaPart
     */
    public List<PartidaBean> getListaPart() {
        return listaPart;
    }

    /**
     * @param listaPart the listaPart to set
     */
    public void setListaPart(List<PartidaBean> listaPart) {
        this.listaPart = listaPart;
    }

    /**
     * @return the listaProy
     */
    public ArrayList<ProyectoSimple> getListaProy() {
        buscarProyecto();
        return listaProy;
    }

    /**
     * @param listaProy the listaProy to set
     */
    public void setListaProy(ArrayList<ProyectoSimple> listaProy) {
        this.listaProy = listaProy;
    }

    /**
     * @return the proySel
     */
    public ProyectoSimple getProySel() {
        return proySel;
    }

    /**
     * @param proySel the proySel to set
     */
    public void setProySel(ProyectoSimple proySel) {
        this.proySel = proySel;
    }

    /**
     * @return the listaPres
     */
    public ArrayList<PresupuestoBean> getListaPres() {
        return listaPres;
    }

    /**
     * @param listaPres the listaPres to set
     */
    public void setListaPres(ArrayList<PresupuestoBean> listaPres) {
        this.listaPres = listaPres;
    }

    /**
     * @return the presB
     */
    public PresupuestoBean getPresB() {
        return presB;
    }

    /**
     * @param presB the presB to set
     */
    public void setPresB(PresupuestoBean presB) {
        this.presB = presB;
    }

    /**
     * @return the res
     */
    public int getRes() {
        return res;
    }

    /**
     * @param res the res to set
     */
    public void setRes(int res) {
        this.res = res;
    }
    
}
