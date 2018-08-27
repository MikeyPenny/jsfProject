/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.bean.AvanceBean;
import com.bean.Contrato;
import com.bean.InsumoContrat;
import com.bean.PresupuestoBean;
import com.bean.ProyectoSimple;
import com.conexion.ConexionBD;
import com.dao.ContratistaDAO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

@Named("editaEst")
@ViewScoped
public class EditaEstMB implements Serializable {
    
    private int id_proyecto;
    private int id_presup;
    private String tipoContto;    
    private String proyecto;
    private String presupuesto;
    
    private int id_avance;
    private int nroAvance;
    private String estatusAv;
    private String dateNow;
    
    private BigDecimal sumaAvance;
    
    private int id_contrato;
    private int nroContto;
    private BigDecimal impCntto;
    private BigDecimal impAntcpo;
    private BigDecimal impGtia;
    private float pctAmort;
    
    private List<ProyectoSimple> listaProy = new ArrayList<>();
    private ProyectoSimple ps;
    
    private ArrayList<PresupuestoBean> listaPres = new ArrayList<>();
    private PresupuestoBean presB;
    
    private List<AvanceBean> listAv = new ArrayList<>();
    private AvanceBean ab;
    
    private List<InsumoContrat> listInsAv = new ArrayList<>();
    private List<Contrato> listPre = new ArrayList<>();
    
    private Contrato c;
    
    
    @PostConstruct
    public void init() {
        consultarProyecto();
    }
    
    public void consultarProyecto() {
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        ContratistaDAO ctrDAO = new ContratistaDAO(con);
        setListaProy(ctrDAO.listarProyecto());
    }
    
    public void obtenerProyecto() {
        setId_proyecto(ps.getId_proyecto());
        setProyecto(ps.getProyecto());
        buscarPresupuesto();
    }
    
    public void buscarPresupuesto() {
        
        if(ps != null) {
            Connection con;
            ConexionBD conexion = new ConexionBD();
            con = conexion.getConexion();
            ContratistaDAO cDAO = new ContratistaDAO(con);
            
            setListaPres(cDAO.listarPresupuesto(ps.getId_proyecto()));
            
        }
        
    }
    
    public void obtenerPresupuesto() {
        setId_presup(presB.getId_presupuesto());
        setPresupuesto(presB.getPresupuesto());
        listarPreContrato();
    }
    
    public void listarPreContrato() {
        
        if(id_proyecto != 0 && id_presup != 0) {
            ConexionBD cnxn = new ConexionBD();
            Connection con = cnxn.getConexion();
            ContratistaDAO cd = new ContratistaDAO(con);
            setListPre(cd.listarContratosEstimados(id_proyecto, id_presup));
        }     
    }
    
    public void seleccionarContrat() {
        setId_contrato(getC().getId_contrato());
        setNroContto(getC().getNumContrato());
        buscarAvances();
    }
    
    public void buscarAvances() {
        
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        ContratistaDAO cDAO = new ContratistaDAO(con);
        setListAv(cDAO.listarAvancesEstimados(id_contrato));
        
    }
    
    public void seleccionarAvance() {
        
        setNroAvance(ab.getNroAvance());
        setId_avance(ab.getId_avance());
        setEstatusAv(ab.getEstatusAvance());
        setDateNow(getAb().getFecha());
        
        ConexionBD cnxn = new ConexionBD();
        Connection con = cnxn.getConexion();
        ContratistaDAO cd = new ContratistaDAO(con);
        setListInsAv(cd.listarInsumoAvance(ab.getId_avance()));
        sumarAvance();
        
        /*consultarDatosContto();
        configurarImporteAntcpoAuto();
        buscarDatosPago();
        buscarControlAmort();
        restarDiferencias();
        calcularRetenciones();
        activarBtnAutorizar();*/
        //buscarControlAmort();
    }
    
    public void sumarAvance() {
        
        BigDecimal importe = BigDecimal.ZERO;
        
        for(InsumoContrat aux: listInsAv) {
            importe = importe.add(aux.getImporteAvnce());
            importe = importe.setScale(2, RoundingMode.DOWN);
        }
        setSumaAvance(importe);
        
    }
    
    public void eliminarEstimacion() {
        
        FacesContext msj = FacesContext.getCurrentInstance();
        
        ConexionBD c = new ConexionBD();
        ContratistaDAO dao = new ContratistaDAO(c.getConexion());
        if(dao.cancelarEstimacion(id_avance)) {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", "Se eliminó la estimación!"));
        }else {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se eliminó la estimación!"));
        }
        
        refresh();
        
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
     * @return the id_presup
     */
    public int getId_presup() {
        return id_presup;
    }

    /**
     * @param id_presup the id_presup to set
     */
    public void setId_presup(int id_presup) {
        this.id_presup = id_presup;
    }

    /**
     * @return the tipoContto
     */
    public String getTipoContto() {
        return tipoContto;
    }

    /**
     * @param tipoContto the tipoContto to set
     */
    public void setTipoContto(String tipoContto) {
        this.tipoContto = tipoContto;
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
     * @return the id_avance
     */
    public int getId_avance() {
        return id_avance;
    }

    /**
     * @param id_avance the id_avance to set
     */
    public void setId_avance(int id_avance) {
        this.id_avance = id_avance;
    }

    /**
     * @return the nroAvance
     */
    public int getNroAvance() {
        return nroAvance;
    }

    /**
     * @param nroAvance the nroAvance to set
     */
    public void setNroAvance(int nroAvance) {
        this.nroAvance = nroAvance;
    }

    /**
     * @return the estatusAv
     */
    public String getEstatusAv() {
        return estatusAv;
    }

    /**
     * @param estatusAv the estatusAv to set
     */
    public void setEstatusAv(String estatusAv) {
        this.estatusAv = estatusAv;
    }

    /**
     * @return the dateNow
     */
    public String getDateNow() {
        return dateNow;
    }

    /**
     * @param dateNow the dateNow to set
     */
    public void setDateNow(String dateNow) {
        this.dateNow = dateNow;
    }

    /**
     * @return the sumaAvance
     */
    public BigDecimal getSumaAvance() {
        return sumaAvance;
    }

    /**
     * @param sumaAvance the sumaAvance to set
     */
    public void setSumaAvance(BigDecimal sumaAvance) {
        this.sumaAvance = sumaAvance;
    }

    /**
     * @return the id_contrato
     */
    public int getId_contrato() {
        return id_contrato;
    }

    /**
     * @param id_contrato the id_contrato to set
     */
    public void setId_contrato(int id_contrato) {
        this.id_contrato = id_contrato;
    }

    /**
     * @return the nroContto
     */
    public int getNroContto() {
        return nroContto;
    }

    /**
     * @param nroContto the nroContto to set
     */
    public void setNroContto(int nroContto) {
        this.nroContto = nroContto;
    }

    /**
     * @return the impCntto
     */
    public BigDecimal getImpCntto() {
        return impCntto;
    }

    /**
     * @param impCntto the impCntto to set
     */
    public void setImpCntto(BigDecimal impCntto) {
        this.impCntto = impCntto;
    }

    /**
     * @return the impAntcpo
     */
    public BigDecimal getImpAntcpo() {
        return impAntcpo;
    }

    /**
     * @param impAntcpo the impAntcpo to set
     */
    public void setImpAntcpo(BigDecimal impAntcpo) {
        this.impAntcpo = impAntcpo;
    }

    /**
     * @return the impGtia
     */
    public BigDecimal getImpGtia() {
        return impGtia;
    }

    /**
     * @param impGtia the impGtia to set
     */
    public void setImpGtia(BigDecimal impGtia) {
        this.impGtia = impGtia;
    }

    /**
     * @return the pctAmort
     */
    public float getPctAmort() {
        return pctAmort;
    }

    /**
     * @param pctAmort the pctAmort to set
     */
    public void setPctAmort(float pctAmort) {
        this.pctAmort = pctAmort;
    }

    /**
     * @return the listaProy
     */
    public List<ProyectoSimple> getListaProy() {
        return listaProy;
    }

    /**
     * @param listaProy the listaProy to set
     */
    public void setListaProy(List<ProyectoSimple> listaProy) {
        this.listaProy = listaProy;
    }

    /**
     * @return the ps
     */
    public ProyectoSimple getPs() {
        return ps;
    }

    /**
     * @param ps the ps to set
     */
    public void setPs(ProyectoSimple ps) {
        this.ps = ps;
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
     * @return the listAv
     */
    public List<AvanceBean> getListAv() {
        return listAv;
    }

    /**
     * @param listAv the listAv to set
     */
    public void setListAv(List<AvanceBean> listAv) {
        this.listAv = listAv;
    }

    /**
     * @return the ab
     */
    public AvanceBean getAb() {
        return ab;
    }

    /**
     * @param ab the ab to set
     */
    public void setAb(AvanceBean ab) {
        this.ab = ab;
    }

    /**
     * @return the listInsAv
     */
    public List<InsumoContrat> getListInsAv() {
        return listInsAv;
    }

    /**
     * @param listInsAv the listInsAv to set
     */
    public void setListInsAv(List<InsumoContrat> listInsAv) {
        this.listInsAv = listInsAv;
    }

    /**
     * @return the listPre
     */
    public List<Contrato> getListPre() {
        return listPre;
    }

    /**
     * @param listPre the listPre to set
     */
    public void setListPre(List<Contrato> listPre) {
        this.listPre = listPre;
    }

    /**
     * @return the c
     */
    public Contrato getC() {
        return c;
    }

    /**
     * @param c the c to set
     */
    public void setC(Contrato c) {
        this.c = c;
    }
    
}
