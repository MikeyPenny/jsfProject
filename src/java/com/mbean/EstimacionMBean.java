/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.bean.AcumuladosBean;
import com.bean.AvanceBean;
import com.bean.Contrato;
import com.bean.EstimaControlBean;
import com.bean.EstimacionBean;
import com.bean.InsumoContrat;
import com.bean.PresupuestoBean;
import com.bean.ProyectoSimple;
import com.bo.Cantidades;
import com.bo.EstimacionBO;
import com.conexion.ConexionBD;
import com.dao.ContratistaDAO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;


@Named("estimar")
@ViewScoped
public class EstimacionMBean implements Serializable {

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
    
    private Contrato cntto;    
    private boolean autMan; //Cambiar el disable del radio
    private BigDecimal amortAntcpoImp;
    
    private BigDecimal acumImporte;
    private BigDecimal acumAmort;
    private BigDecimal acumGtia;
    private BigDecimal difPago;
    private BigDecimal difAmortizar;
    private BigDecimal difGarantia;
    
    private BigDecimal pendXAMort;
    
    private BigDecimal impAmortManual;
    
    private BigDecimal rtnFlete;
    private BigDecimal rtnRenta;
    private BigDecimal rtnGtia;
    
    private BigDecimal pendAmortMan;
    
    private BigDecimal estimAnt;
    private BigDecimal amortAnt;
    private BigDecimal gtiaAntRet;
    
    private List<Contrato> listPre = new ArrayList<>();
    private Contrato c;
    
    private boolean actAutoriza = false;
    
    public EstimacionMBean() {}
    
    public void resetFormulario() {
        setProyecto(null);
        setPresupuesto(null);
        setNroAvance(0);
        listInsAv.clear();
        setEstimAnt(null);
        setAmortAnt(null);
        setGtiaAntRet(null);
        setSumaAvance(null);
        setAmortAntcpoImp(null);
        setRtnGtia(null);
        setAcumImporte(acumImporte);
        setAcumAmort(null);
        setAcumGtia(null);
        setDifPago(null);
        setDifAmortizar(null);
        setDifGarantia(null);
        setAutMan(false);
        setPctAmort(0);
        setRtnFlete(null);
        setRtnRenta(null);
        setRtnGtia(null);
        setActAutoriza(false);
        
    }
    
    public void obtenerPendienteManual() {
        
        if(impAmortManual.floatValue() > sumaAvance.floatValue()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Amortización incorrecta", "Amortización no debe ser mayor al importe de estimación"));
        }else {
            if(impAmortManual.add(pendXAMort).add(acumAmort).floatValue() > impAntcpo.floatValue()) {
                setPendAmortMan(amortAntcpoImp.subtract(impAmortManual));
                setImpAmortManual(impAmortManual.add(pendXAMort).add(pendAmortMan));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Amortización incorrecta", "Amortización debe ser por $ "+impAmortManual));
            }else {
                setPendAmortMan(amortAntcpoImp.subtract(impAmortManual)); 
                //System.out.println(pendAmortMan);
            }
        }
        
        
        
        
    }
    
    public void comprobarAmortizacion() {
        
        //System.out.println(autMan);
        if(autMan) {
            //Comprobar amort manual correcta
            if(impAmortManual.floatValue() > difAmortizar.floatValue() || (sumaAvance.add(acumImporte)).floatValue() == impCntto.floatValue()) {
                setImpAmortManual(difAmortizar);
                //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Amortización errónea", "Correcta "+difAmortizar));
            }
            if(tipoContto.equals("SUBCONTRATO COMPLETO") || tipoContto.equals("MANO DE OBRA")) {
                EstimacionBean estBean = new EstimacionBean();
                estBean.setEstimacionImp(sumaAvance);
                estBean.setAmortAntcpo(impAmortManual);
                estBean.setRtnRenta(BigDecimal.ZERO);
                estBean.setRtnFlete(BigDecimal.ZERO);
                estBean.setRtnGarantia(rtnGtia);
                EstimacionBO estBO = new EstimacionBO();
                estBO.registrarEstimacion(estBean, listInsAv, id_avance, id_proyecto, id_presup);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", "Estimación Realizada"));
            }
            if(tipoContto.equals("RENTA DE EQUIPO")) {
                EstimacionBean estBean = new EstimacionBean();
                estBean.setEstimacionImp(sumaAvance);
                estBean.setAmortAntcpo(impAmortManual);
                estBean.setRtnRenta(rtnRenta);
                estBean.setRtnFlete(BigDecimal.ZERO);
                estBean.setRtnGarantia(rtnGtia);
                EstimacionBO estBO = new EstimacionBO();
                estBO.registrarEstimacion(estBean, listInsAv, id_avance, id_proyecto, id_presup);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", "Estimación Realizada"));
            }
            if(tipoContto.equals("FLETES Y ACARREOS")) {
                EstimacionBean estBean = new EstimacionBean();
                estBean.setEstimacionImp(sumaAvance);
                estBean.setAmortAntcpo(impAmortManual);
                estBean.setRtnRenta(BigDecimal.ZERO);
                estBean.setRtnFlete(rtnFlete);
                estBean.setRtnGarantia(rtnGtia);
                EstimacionBO estBO = new EstimacionBO();
                estBO.registrarEstimacion(estBean, listInsAv, id_avance, id_proyecto, id_presup);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", "Estimación Realizada"));
            }
            
            
        }else {
            //Comprobar amort auto
            
            if(amortAntcpoImp.floatValue() > difAmortizar.floatValue()) {
                //|| (sumaAvance.add(acumImporte)).floatValue() == impCntto.floatValue()
                setImpAmortManual(difAmortizar);
                autMan = true;
                
                System.out.println(sumaAvance.add(acumImporte)+" \t"+impCntto);
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Amortización mayor al anticipo", "Realizar amortización manual"));
            }else {
                
                if(tipoContto.equals("SUBCONTRATO COMPLETO") || tipoContto.equals("MANO DE OBRA")) {
                    EstimacionBean estBean = new EstimacionBean();
                    estBean.setEstimacionImp(sumaAvance);
                    estBean.setAmortAntcpo(amortAntcpoImp);
                    estBean.setRtnRenta(BigDecimal.ZERO);
                    estBean.setRtnFlete(BigDecimal.ZERO);
                    estBean.setRtnGarantia(rtnGtia);
                    EstimacionBO estBO = new EstimacionBO();
                    estBO.registrarEstimacion(estBean, listInsAv, id_avance, id_proyecto, id_presup);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", "Estimación Realizada"));
                }
                if(tipoContto.equals("RENTA DE EQUIPO")) {
                    EstimacionBean estBean = new EstimacionBean();
                    estBean.setEstimacionImp(sumaAvance);
                    estBean.setAmortAntcpo(amortAntcpoImp);
                    estBean.setRtnRenta(rtnRenta);
                    estBean.setRtnFlete(BigDecimal.ZERO);
                    estBean.setRtnGarantia(rtnGtia);
                    EstimacionBO estBO = new EstimacionBO();
                    estBO.registrarEstimacion(estBean, listInsAv, id_avance, id_proyecto, id_presup);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", "Estimación Realizada"));
                }
                if(tipoContto.equals("FLETES Y ACARREOS")) {
                    EstimacionBean estBean = new EstimacionBean();
                    estBean.setEstimacionImp(sumaAvance);
                    estBean.setAmortAntcpo(amortAntcpoImp);
                    estBean.setRtnRenta(BigDecimal.ZERO);
                    estBean.setRtnFlete(rtnFlete);
                    estBean.setRtnGarantia(rtnGtia);
                    EstimacionBO estBO = new EstimacionBO();
                    estBO.registrarEstimacion(estBean, listInsAv, id_avance, id_proyecto, id_presup);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", "Estimación Realizada"));
                }
                
            }
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
    
    public void buscarControlAmort() {
        
        ConexionBD cnxn = new ConexionBD();
        Connection con = cnxn.getConexion();
        ContratistaDAO cd = new ContratistaDAO(con);
        EstimaControlBean ecb = cd.buscarUltEstimacion(id_contrato);
        
        if(ecb.getPendXAmort() == null) {
            setPendXAMort(BigDecimal.ZERO);
        }else {
            setPendXAMort(ecb.getPendXAmort().setScale(2, RoundingMode.CEILING));
        }
        
        cargarDatosUltEstimacion(ecb);
        
    }
    
    public void cargarDatosUltEstimacion(EstimaControlBean ecb) {
        setEstimAnt(ecb.getImporte().setScale(2, RoundingMode.UP));
        setAmortAnt(ecb.getAmortizado().setScale(2, RoundingMode.UP));
        setGtiaAntRet(ecb.getGtiaRetenida().setScale(2, RoundingMode.UP));
    }
    
    public void calcularRetenciones() {
        
        if(tipoContto.equals("SUBCONTRATO COMPLETO") || tipoContto.equals("MANO DE OBRA")) {
            setRtnFlete(BigDecimal.ZERO);
            setRtnRenta(BigDecimal.ZERO);
            
        }
        if(tipoContto.equals("RENTA DE EQUIPO")) {
            setRtnFlete(BigDecimal.ZERO);
            Cantidades cant = new Cantidades();
            setRtnRenta(cant.calcularRenta(sumaAvance).setScale(2, RoundingMode.CEILING));
        }
        if(tipoContto.equals("FLETES Y ACARREOS")) {
            setRtnRenta(BigDecimal.ZERO);
            Cantidades cant = new Cantidades();
            setRtnFlete(cant.calcularFlete(sumaAvance).setScale(2, RoundingMode.CEILING));
        }
        
    }
    
    public void restarDiferencias() {
        
        setDifPago((impCntto.subtract(acumImporte)).setScale(2, RoundingMode.CEILING));
        setDifAmortizar((impAntcpo.subtract(acumAmort)).setScale(2, RoundingMode.CEILING));
        setDifGarantia((impGtia.subtract(acumGtia)).setScale(2, RoundingMode.CEILING));
    }
    
    public void buscarDatosPago() {
        
        ConexionBD cnxn = new ConexionBD();
        Connection con = cnxn.getConexion();
        ContratistaDAO cd = new ContratistaDAO(con);
        //acumul = new AcumuladosBean();
        Cantidades cant = new Cantidades();
        AcumuladosBean acumul = cant.evitarNulos(cd.acumularImportes(id_contrato));
        mostrarAcumulados(acumul);
        
    }
    
    public void mostrarAcumulados(AcumuladosBean acumul) {
        setAcumImporte(acumul.getAcumImporte().setScale(2, RoundingMode.CEILING));
        setAcumAmort(acumul.getAcumAmort().setScale(2, RoundingMode.CEILING));
        setAcumGtia(acumul.getAcumGtia().setScale(2, RoundingMode.CEILING));
        
    }
    
    public void configurarImporteAntcpoAuto() {
        float pct = pctAmort/100;
        buscarControlAmort();
        setAmortAntcpoImp(((sumaAvance.multiply(BigDecimal.valueOf(pct))).setScale(2, RoundingMode.DOWN)).add(pendXAMort));
        if(amortAntcpoImp.floatValue() < 0) {
            setAmortAntcpoImp(BigDecimal.ZERO);
        }
    }
    
    public void consultarDatosContto() {
        
        ConexionBD cnxn = new ConexionBD();
        Connection con = cnxn.getConexion();
        ContratistaDAO cd = new ContratistaDAO(con);
        actulizarDatosContto(cd.buscarDatosContto(id_avance));
    }
    
    public void actulizarDatosContto(Contrato cntto) {
        
        setId_contrato(cntto.getId_contrato());
        setImpCntto(cntto.getImporteContrato().setScale(2, RoundingMode.CEILING));
        setImpAntcpo(cntto.getAnticipoImp().setScale(2, RoundingMode.CEILING));
        setImpGtia(cntto.getFondoGtiaImp().setScale(2, RoundingMode.CEILING));
        setPctAmort(cntto.getAmortAntcpoPct());
        setTipoContto(cntto.getTipo());
        setRtnGtia((sumaAvance.multiply(BigDecimal.valueOf(cntto.getFondoGtiaPct()/100))).setScale(2, RoundingMode.CEILING));
        
    }
    
    public void sumarAvance() {
        
        BigDecimal importe = BigDecimal.ZERO;
        
        for(InsumoContrat aux: listInsAv) {
            importe = importe.add(aux.getImporteAvnce());
            importe = importe.setScale(2, RoundingMode.DOWN);
        }
        setSumaAvance(importe);
        
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
        
        consultarDatosContto();
        configurarImporteAntcpoAuto();
        buscarDatosPago();
        buscarControlAmort();
        restarDiferencias();
        calcularRetenciones();
        activarBtnAutorizar();
        //buscarControlAmort();
    }
    
    public void activarBtnAutorizar() {
        if(estatusAv.equals("AVANCE")) {
            setActAutoriza(false);
        }else {
            setActAutoriza(true);
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
        setListAv(cDAO.listarAvances(id_contrato));
        
    }
    
    public void rechazarAvance() {
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        ContratistaDAO cDAO = new ContratistaDAO(con);
        cDAO.cancelarAvance(listInsAv, id_avance);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", "Avance cancelado!"));
        
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
            setListPre(cd.listarContratosPorEstimar(id_proyecto, id_presup));
        }     
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
    
    public void obtenerProyecto() {
        setId_proyecto(ps.getId_proyecto());
        setProyecto(ps.getProyecto());
        buscarPresupuesto();
    }
    
    public void consultarProyecto() {
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        ContratistaDAO ctrDAO = new ContratistaDAO(con);
        setListaProy(ctrDAO.listarProyecto());
    }

    public int getId_proyecto() {
        return id_proyecto;
    }

    public void setId_proyecto(int id_proyecto) {
        this.id_proyecto = id_proyecto;
    }

    public int getId_presup() {
        return id_presup;
    }

    public void setId_presup(int id_presup) {
        this.id_presup = id_presup;
    }

    public String getTipoContto() {
        return tipoContto;
    }

    public void setTipoContto(String tipoContto) {
        this.tipoContto = tipoContto;
    }
   

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public String getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(String presupuesto) {
        this.presupuesto = presupuesto;
    }

    public ArrayList<PresupuestoBean> getListaPres() {
        return listaPres;
    }

    public void setListaPres(ArrayList<PresupuestoBean> listaPres) {
        this.listaPres = listaPres;
    }

    public PresupuestoBean getPresB() {
        return presB;
    }

    public void setPresB(PresupuestoBean presB) {
        this.presB = presB;
    }

    public List<ProyectoSimple> getListaProy() {
        consultarProyecto();
        return listaProy;
    }

    public void setListaProy(List<ProyectoSimple> listaProy) {
        this.listaProy = listaProy;
    }

    public ProyectoSimple getPs() {
        return ps;
    }

    public void setPs(ProyectoSimple ps) {
        this.ps = ps;
    }

    public List<AvanceBean> getListAv() {
        return listAv;
    }

    public void setListAv(List<AvanceBean> listAv) {
        this.listAv = listAv;
    }

    public AvanceBean getAb() {
        return ab;
    }

    public void setAb(AvanceBean ab) {
        this.ab = ab;
    }

    public int getId_avance() {
        return id_avance;
    }

    public void setId_avance(int id_avance) {
        this.id_avance = id_avance;
    }

    public int getNroAvance() {
        return nroAvance;
    }

    public void setNroAvance(int nroAvance) {
        this.nroAvance = nroAvance;
    }

    public String getDateNow() {
        return dateNow;
    }

    public void setDateNow(String dateNow) {
        this.dateNow = dateNow;
    }

    public List<InsumoContrat> getListInsAv() {
        return listInsAv;
    }

    public void setListInsAv(List<InsumoContrat> listInsAv) {
        this.listInsAv = listInsAv;
    }

    public BigDecimal getSumaAvance() {
        return sumaAvance;
    }

    public void setSumaAvance(BigDecimal sumaAvance) {
        this.sumaAvance = sumaAvance;
    }

    public Contrato getCntto() {
        return cntto;
    }

    public void setCntto(Contrato cntto) {
        this.cntto = cntto;
    }

    public BigDecimal getImpCntto() {
        return impCntto;
    }

    public void setImpCntto(BigDecimal impCntto) {
        this.impCntto = impCntto;
    }

    public BigDecimal getImpAntcpo() {
        return impAntcpo;
    }

    public void setImpAntcpo(BigDecimal impAntcpo) {
        this.impAntcpo = impAntcpo;
    }

    public BigDecimal getImpGtia() {
        return impGtia;
    }

    public void setImpGtia(BigDecimal impGtia) {
        this.impGtia = impGtia;
    }

    public float getPctAmort() {
        return pctAmort;
    }

    public void setPctAmort(float pctAmort) {
        this.pctAmort = pctAmort;
    }

    public boolean isAutMan() {
        return autMan;
    }

    public void setAutMan(boolean autMan) {
        this.autMan = autMan;
    }

    public BigDecimal getAmortAntcpoImp() {
        return amortAntcpoImp;
    }

    public void setAmortAntcpoImp(BigDecimal amortAntcpoImp) {
        this.amortAntcpoImp = amortAntcpoImp;
    }

    public int getId_contrato() {
        return id_contrato;
    }

    public void setId_contrato(int id_contrato) {
        this.id_contrato = id_contrato;
    }

    public BigDecimal getAcumImporte() {
        return acumImporte;
    }

    public void setAcumImporte(BigDecimal acumImporte) {
        this.acumImporte = acumImporte;
    }

    public BigDecimal getAcumAmort() {
        return acumAmort;
    }

    public void setAcumAmort(BigDecimal acumAmort) {
        this.acumAmort = acumAmort;
    }

    public BigDecimal getDifPago() {
        return difPago;
    }

    public void setDifPago(BigDecimal difPago) {
        this.difPago = difPago;
    }

    public BigDecimal getDifAmortizar() {
        return difAmortizar;
    }

    public void setDifAmortizar(BigDecimal difAmortizar) {
        this.difAmortizar = difAmortizar;
    }

    public BigDecimal getPendXAMort() {
        return pendXAMort;
    }

    public void setPendXAMort(BigDecimal pendXAMort) {
        this.pendXAMort = pendXAMort;
    }

    public BigDecimal getImpAmortManual() {
        return impAmortManual;
    }

    public void setImpAmortManual(BigDecimal impAmortManual) {
        this.impAmortManual = impAmortManual;
    }

    public BigDecimal getRtnFlete() {
        return rtnFlete;
    }

    public void setRtnFlete(BigDecimal rtnFlete) {
        this.rtnFlete = rtnFlete;
    }

    public BigDecimal getRtnRenta() {
        return rtnRenta;
    }

    public void setRtnRenta(BigDecimal rtnRenta) {
        this.rtnRenta = rtnRenta;
    }

    public BigDecimal getRtnGtia() {
        return rtnGtia;
    }

    public void setRtnGtia(BigDecimal rtnGtia) {
        this.rtnGtia = rtnGtia;
    }

    public BigDecimal getPendAmortMan() {
        return pendAmortMan;
    }

    public void setPendAmortMan(BigDecimal pendAmortMan) {
        this.pendAmortMan = pendAmortMan;
    }

    public BigDecimal getEstimAnt() {
        return estimAnt;
    }

    public void setEstimAnt(BigDecimal estimAnt) {
        this.estimAnt = estimAnt;
    }

    public BigDecimal getAmortAnt() {
        return amortAnt;
    }

    public void setAmortAnt(BigDecimal amortAnt) {
        this.amortAnt = amortAnt;
    }

    public BigDecimal getGtiaAntRet() {
        return gtiaAntRet;
    }

    public void setGtiaAntRet(BigDecimal gtiaAntRet) {
        this.gtiaAntRet = gtiaAntRet;
    }

    public BigDecimal getAcumGtia() {
        return acumGtia;
    }

    public void setAcumGtia(BigDecimal acumGtia) {
        this.acumGtia = acumGtia;
    }

    public BigDecimal getDifGarantia() {
        return difGarantia;
    }

    public void setDifGarantia(BigDecimal difGarantia) {
        this.difGarantia = difGarantia;
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
     * @return the actAutoriza
     */
    public boolean isActAutoriza() {
        return actAutoriza;
    }

    /**
     * @param actAutoriza the actAutoriza to set
     */
    public void setActAutoriza(boolean actAutoriza) {
        this.actAutoriza = actAutoriza;
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
    
}
