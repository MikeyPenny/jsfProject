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

@Named("edAvan")
@ViewScoped
public class EditarAvMB implements Serializable {
    
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
    private InsumoContrat avn;
    private List<InsumoContrat> listAvEdit = new ArrayList<>();
    
    private Contrato cntto;
    
    private List<Contrato> listPre = new ArrayList<>();
    private Contrato c;
    
    private InsumoContrat insSel;
    private String codIns;
    private String unidIns;
    private BigDecimal cantIns;
    private BigDecimal cantInsAux;
    private BigDecimal unitIns;
    private BigDecimal auxUnit;
    private BigDecimal impIns;
    private BigDecimal cantidCtrl;
    private BigDecimal sumaAvn;
    
    private boolean btnEdit;
    
    @PostConstruct
    public void init() {
        
        consultarProyecto();
        setBtnEdit(true);
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
            setListPre(cd.listarContratos(id_proyecto, id_presup));
        }     
    }
    
    
    public void seleccionarContrat() {
        setId_contrato(c.getId_contrato());
        setNroContto(c.getNumContrato());
        buscarAvances();
    }
    
    public void buscarAvances() {
        
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        ContratistaDAO cDAO = new ContratistaDAO(con);
        setListAv(cDAO.listarAvancesToEdit(id_contrato));
        
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
        activarBtnAutorizar();
        //buscarControlAmort();*/
        
    }
    
    public void sumarAvance() {
        
        BigDecimal importe = BigDecimal.ZERO;
        
        for(InsumoContrat aux: listInsAv) {
            importe = importe.add(aux.getImporteAvnce());
            importe = importe.setScale(2, RoundingMode.DOWN);
        }
        setSumaAvance(importe);
        
    }
    
    public void cambiarCantidadPrecio() {
        
        FacesContext msj = FacesContext.getCurrentInstance();
        
        if(getCantIns().floatValue()  <= getCantidCtrl().floatValue()) {
            setImpIns(getCantIns().multiply(getUnitIns()));
            setBtnEdit(false);
        }else {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", 
                    "La cantidad no puede ser mayor al disponible:"));
            setCantIns(getCantInsAux());
            
        }
        
    }
    
    public void elegirInsAvance() {
        
        setCodIns(insSel.getCodInsumo());
        setUnidIns(insSel.getUnidad());
        setCantIns(insSel.getAvance());
        setCantInsAux(insSel.getAvance());
        setUnitIns(insSel.getPresUnit());
        setImpIns(insSel.getImporteAvnce());
        setCantidCtrl((insSel.getCantContrato().subtract(insSel.getSumaAvance())).add(insSel.getAvance()));
        setAuxUnit(insSel.getPresUnit());
        
    }
    
    public void cambiarAvance() {
        
        insSel.setAvance(getCantIns());
        insSel.setImporteAvnce(getImpIns());
        listInsAv.set(listInsAv.indexOf(insSel), insSel);
        listAvEdit.add(insSel);
        sumarAvance();
        
    }
    
    public void registrarCambio() {
        
        FacesContext msj = FacesContext.getCurrentInstance();
        
        AvanceBean avance = new AvanceBean();
        avance.setId_avance(id_avance);
        avance.setImporteEstimacion(sumaAvance);
        
        ConexionBD c = new ConexionBD();
        ContratistaDAO dao = new ContratistaDAO(c.getConexion());
        if(dao.registrarCambioAvance(avance, listAvEdit)) {
            
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", 
                    "Se ha actualizado el avance."));
            
            
        }else {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", 
                    "No se actualizó el avance."));
            
        }
        
        refresh();
        
    }
    
    public void eliminarAvance() {
        
        FacesContext msj = FacesContext.getCurrentInstance();
        
        ConexionBD c = new ConexionBD();
        ContratistaDAO dao = new ContratistaDAO(c.getConexion());
        if(dao.eliminarAvance(id_avance, id_contrato)) {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", "Se eliminó el avance!"));
        }else {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se eliminó el avance!"));
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
     * @return the cntto
     */
    public Contrato getCntto() {
        return cntto;
    }

    /**
     * @param cntto the cntto to set
     */
    public void setCntto(Contrato cntto) {
        this.cntto = cntto;
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

    /**
     * @return the avn
     */
    public InsumoContrat getAvn() {
        return avn;
    }

    /**
     * @param avn the avn to set
     */
    public void setAvn(InsumoContrat avn) {
        this.avn = avn;
    }

    /**
     * @return the insSel
     */
    public InsumoContrat getInsSel() {
        return insSel;
    }

    /**
     * @param insSel the insSel to set
     */
    public void setInsSel(InsumoContrat insSel) {
        this.insSel = insSel;
    }

    /**
     * @return the codIns
     */
    public String getCodIns() {
        return codIns;
    }

    /**
     * @param codIns the codIns to set
     */
    public void setCodIns(String codIns) {
        this.codIns = codIns;
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
     * @return the cantIns
     */
    public BigDecimal getCantIns() {
        return cantIns;
    }

    /**
     * @param cantIns the cantIns to set
     */
    public void setCantIns(BigDecimal cantIns) {
        this.cantIns = cantIns;
    }

    /**
     * @return the unitIns
     */
    public BigDecimal getUnitIns() {
        return unitIns;
    }

    /**
     * @param unitIns the unitIns to set
     */
    public void setUnitIns(BigDecimal unitIns) {
        this.unitIns = unitIns;
    }

    /**
     * @return the auxUnit
     */
    public BigDecimal getAuxUnit() {
        return auxUnit;
    }

    /**
     * @param auxUnit the auxUnit to set
     */
    public void setAuxUnit(BigDecimal auxUnit) {
        this.auxUnit = auxUnit;
    }

    /**
     * @return the impIns
     */
    public BigDecimal getImpIns() {
        return impIns;
    }

    /**
     * @param impIns the impIns to set
     */
    public void setImpIns(BigDecimal impIns) {
        this.impIns = impIns;
    }

    /**
     * @return the cantidCtrl
     */
    public BigDecimal getCantidCtrl() {
        return cantidCtrl;
    }

    /**
     * @param cantidCtrl the cantidCtrl to set
     */
    public void setCantidCtrl(BigDecimal cantidCtrl) {
        this.cantidCtrl = cantidCtrl;
    }

    /**
     * @return the listAvEdit
     */
    public List<InsumoContrat> getListAvEdit() {
        return listAvEdit;
    }

    /**
     * @param listAvEdit the listAvEdit to set
     */
    public void setListAvEdit(List<InsumoContrat> listAvEdit) {
        this.listAvEdit = listAvEdit;
    }

    /**
     * @return the sumaAvn
     */
    public BigDecimal getSumaAvn() {
        return sumaAvn;
    }

    /**
     * @param sumaAvn the sumaAvn to set
     */
    public void setSumaAvn(BigDecimal sumaAvn) {
        this.sumaAvn = sumaAvn;
    }

    /**
     * @return the cantInsAux
     */
    public BigDecimal getCantInsAux() {
        return cantInsAux;
    }

    /**
     * @param cantInsAux the cantInsAux to set
     */
    public void setCantInsAux(BigDecimal cantInsAux) {
        this.cantInsAux = cantInsAux;
    }

    /**
     * @return the btnEdit
     */
    public boolean isBtnEdit() {
        return btnEdit;
    }

    /**
     * @param btnEdit the btnEdit to set
     */
    public void setBtnEdit(boolean btnEdit) {
        this.btnEdit = btnEdit;
    }
    
}
