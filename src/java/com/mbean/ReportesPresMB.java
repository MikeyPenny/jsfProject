
package com.mbean;

import com.bean.Direccion;
import com.bean.Partida;
import com.bean.PresupuestoBean;
import com.bean.ProyectoSimple;
import com.conexion.ConexionBD;
import com.dao.AltaDAO;
import com.dao.PresupuestoDAO;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;


import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;



@Named("repPres")
@ViewScoped
public class ReportesPresMB implements Serializable {

    private int id_proyecto;
    private String proyecto;
    private int id_presupuesto;
    private String presupuesto;
    private ArrayList<ProyectoSimple> listaProy = new ArrayList<>();
    private ProyectoSimple proySel;
    private ArrayList<PresupuestoBean> listaPres = new ArrayList<>();
    private PresupuestoBean presB;
    private List<Partida> listPartidas;
    
    private String direccion;
       
    @PostConstruct
    public void init() {
        buscarProyecto();
    }
    
    public void buscarProyecto() {
        
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        PresupuestoDAO presD = new PresupuestoDAO(con);
        setListaProy(presD.listarProyecto());      
    }
    
    public void obtenerDireccion() {
        
        ConexionBD c = new ConexionBD();
        AltaDAO dao = new AltaDAO(c.getConexion());
        Direccion dir = dao.buscarDireccion(id_proyecto);
        setDireccion(dir.getCalle()+", "+dir.getNum()+", "+dir.getColonia()+", "+dir.getCodPost());
    }
    
    public void obtenerProyecto() {
        setId_proyecto(getProySel().getId_proyecto());
        setProyecto(getProySel().getProyecto());
        buscarPresupuesto();
        //listarFamilias();
        
    }
    
    public void buscarPresupuesto() {
        
        if(getProySel() != null) {
            Connection con;
            ConexionBD conexion = new ConexionBD();
            con = conexion.getConexion();
            PresupuestoDAO presD = new PresupuestoDAO(con);
            
            setListaPres(presD.listarPresupuesto(getProySel().getId_proyecto(), 2));
            //System.out.println(listaPres.size());
        }
        
    }
    
    public void obtenerPresupuesto() {
        setId_presupuesto(presB.getId_presupuesto());
        setPresupuesto(presB.getPresupuesto());
        setListPartidas(presB.getListPartidas());
        //buscarPartida();
        //setBtnPart(false);
        
    }
    
    public void exportarPDF(ActionEvent actionEvent) throws JRException, IOException {
        
        
        
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("txtProyecto", proyecto);
        parametros.put("txtDireccion", direccion);
        
        
            
        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/presPrueb.jasper"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), null, new JRBeanCollectionDataSource(this.listPartidas));

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        ServletOutputStream stream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

        stream.flush();
        stream.close();

        FacesContext.getCurrentInstance().responseComplete();

        System.out.println("Exportando...");
        //System.out.println(listPartidas.size());
        
            
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
     * @return the listaProy
     */
    public ArrayList<ProyectoSimple> getListaProy() {
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
     * @return the listPartidas
     */
    public List<Partida> getListPartidas() {
        return listPartidas;
    }

    /**
     * @param listPartidas the listPartidas to set
     */
    public void setListPartidas(List<Partida> listPartidas) {
        this.listPartidas = listPartidas;
    }
    
}
