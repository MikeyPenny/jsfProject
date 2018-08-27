/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.bean.AnticipoBean;
import com.bean.Contratista;
import com.bean.Contrato;
import com.bean.InsumoContrat;
import com.bean.PresupuestoBean;
import com.bean.ProyectoSimple;
import com.bo.AnticipoBO;
import com.bo.BotonesBO;
import com.conexion.ConexionBD;
import com.dao.ContratistaDAO;
import java.io.Serializable;
import java.math.BigDecimal;
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
import org.primefaces.event.CellEditEvent;


@Named("contrato")
@ViewScoped
public class ContratoMBean implements Serializable {
    
    @PostConstruct
    public void init() {
        setActContrato(false);
        setActAnticipo(false);
        setCambio(true);
        buscarContratista();
    }
    
    private int id_proy;
    private String proyecto;
    private int id_pres;
    private String presupto;
    
    private List<ProyectoSimple> listProy = new ArrayList<>();
    private ProyectoSimple ps;
    
    private PresupuestoBean pb;
    private ArrayList<PresupuestoBean> listaPres = new ArrayList<>();
    
    private List<Contrato> listPre = new ArrayList<>();
    private Contrato c;
    
    
    
    private int id_contrato;
    private String tipoContrat;
    private BigDecimal importeContrato;
    private float anticipoPct;
    private BigDecimal anticipoImp;
    private float fondoGtiaPct;
    private BigDecimal fondoGtiaImp;
    private float amortAntcpoPct;
    private BigDecimal amortAntcpoImp;
    private int numContrato;
    private String estatusContto;
    
    private InsumoContrat insSel;
    private String codIns;
    private String unidIns;
    private BigDecimal cantIns;
    private BigDecimal unitIns;
    private BigDecimal auxUnit;
    private BigDecimal impIns;
    private BigDecimal cantidCtrl;
    
    private List<Contratista> listCtta = new ArrayList<>();
    private Contratista ctta;
    private String contratista;
    
    private List<InsumoContrat> listInsum = new ArrayList<>();
    private List<InsumoContrat> listEdit = new ArrayList<>();
    private InsumoContrat ic;
    private BigDecimal presUnitCh;
    
    private List<Integer> listId = new ArrayList<>();
    
    private boolean actContrato = false;
    private boolean actAnticipo = false;
    
    private boolean actContrat = false;
    private boolean cambio;
    
    private BigDecimal iva;
    private BigDecimal rtnRenta;
    private BigDecimal rtnFlete;
    private BigDecimal total;
    
    public void limpiarFormulario() {
        setActContrato(false);
        setActAnticipo(false);
        setProyecto(null);
        setPs(null);
        setPresupto(null);
        setPb(null);
        setNumContrato(0);
        setTipoContrat(null);
        setImporteContrato(null);
        setEstatusContto(null);
        setContratista(null);
        listInsum.clear();
        setAnticipoPct(0);
        setAnticipoImp(null);
        setFondoGtiaPct(0);
        setFondoGtiaImp(null);
        setAmortAntcpoPct(0);
        setAmortAntcpoImp(null);
        setIva(null);
        setRtnFlete(null);
        setRtnRenta(null);
        setTotal(null);
        setActContrat(false);
    }
    
    public void comprobarAnticipo() {
        
        ConexionBD conexion = new ConexionBD();
        Connection con = conexion.getConexion();
        ContratistaDAO cd = new ContratistaDAO(con);
        setActAnticipo(cd.comprobarAnticipo(getId_contrato()));
        //System.out.println(actAnticipo);
    }
    
    public void cancelarAnticipo() {
        ConexionBD conexion = new ConexionBD();
        Connection con = conexion.getConexion();
        ContratistaDAO cd = new ContratistaDAO(con);
        cd.eliminarAntcpo(getId_contrato());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Anticipo Cancelado", "Cancelación Exitosa!!"));
    }
    
    public void generarAnticipo() {
        

        AnticipoBean antcpo = new AnticipoBean();

        antcpo.setId_contrato(getId_contrato());
        antcpo.setNroContrato(getNumContrato());
        antcpo.setImporte(getAnticipoImp());
        antcpo.setContratista(getContratista());
        antcpo.setTipo(getTipoContrat());

        AnticipoBO aBO = new AnticipoBO();
        antcpo = aBO.calcularIVA(antcpo);
        actualizarData(antcpo);
        desactivarSeleccionContratista();
        
        
        
    }
    
    public void actualizarAnticipo() {
        
        AnticipoBean antcpo = new AnticipoBean();

        antcpo.setId_contrato(getId_contrato());
        antcpo.setNroContrato(getNumContrato());
        antcpo.setImporte(getAnticipoImp());
        antcpo.setContratista(getContratista());
        antcpo.setTipo(getTipoContrat());

        AnticipoBO aBO = new AnticipoBO();
        antcpo = aBO.calcularIVAActual(antcpo);
        actualizarData(antcpo);
        desactivarSeleccionContratista();
    
    }
    
    public void actualizarData(AnticipoBean antcpo) {
        setIva(antcpo.getIva());
        setRtnRenta(antcpo.getRtnRenta());
        setRtnFlete(antcpo.getRtnFlete());
        setTotal(antcpo.getTotal());
    }
    
    public void cancelarContrato() {
        
        ConexionBD conexion = new ConexionBD();
        Connection con = conexion.getConexion();
        ContratistaDAO cd = new ContratistaDAO(con);
        cd.cancelarContrato(id_contrato);
        refresh();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cancelado", "Cancelación de contrato exitosa!"));
    }
    
    public void registraContrato() {
        
        if(!getContratista().equals("") && getAnticipoPct() >= 0 && getFondoGtiaPct() >= 0) {
            
            Contrato cntrt = new Contrato();
            cntrt.setId_contrato(getId_contrato());
            cntrt.setContratista(getContratista());
            cntrt.setImporteContrato(getImporteContrato());
            cntrt.setAnticipoPct(getAnticipoPct());
            cntrt.setAnticipoImp(getAnticipoImp());
            cntrt.setFondoGtiaPct(getFondoGtiaPct());
            cntrt.setFondoGtiaImp(getFondoGtiaImp());
            cntrt.setAmortAntcpoPct(getAmortAntcpoPct());
            cntrt.setAmortAntcpoImp(getAmortAntcpoImp());
            cntrt.setEstatusContrato("CONTRATO");
            setEstatusContto("CONTRATO");

            ConexionBD conexion = new ConexionBD();
            Connection con = conexion.getConexion();
            ContratistaDAO cd = new ContratistaDAO(con);
            cd.registrarContrato(cntrt, listId, listInsum);
            setActContrato(true);
            desactivarSeleccionContratista();
            if(getAnticipoPct()>0) {
                generarAnticipo();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Contrato Exitoso!!"));
            
        }else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se asignó contratista"));
        }
        
        
        refresh();
        
    }
    
    public void editaContrato() {
        
        if(!getContratista().equals("") && getAnticipoPct() > 0 && getFondoGtiaPct() > 0) {
            
            Contrato cntrt = new Contrato();
            cntrt.setId_contrato(getId_contrato());
            cntrt.setContratista(getContratista());
            cntrt.setImporteContrato(getImporteContrato());
            cntrt.setAnticipoPct(getAnticipoPct());
            cntrt.setAnticipoImp(getAnticipoImp());
            cntrt.setFondoGtiaPct(getFondoGtiaPct());
            cntrt.setFondoGtiaImp(getFondoGtiaImp());
            cntrt.setAmortAntcpoPct(getAnticipoPct());
            cntrt.setAmortAntcpoImp(getAnticipoImp());
            cntrt.setEstatusContrato("CONTRATO");
            setEstatusContto("CONTRATO");

            ConexionBD conexion = new ConexionBD();
            Connection con = conexion.getConexion();
            ContratistaDAO cd = new ContratistaDAO(con);
            if(cd.editarContrato(cntrt, listEdit)) {
                setActContrato(true);
                desactivarSeleccionContratista();
                actualizarAnticipo();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Contrato Exitoso!!"));
            }else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se editó la info."));
            }
                
            
        }else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se asignó contratista"));
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
    
    public void desactivarSeleccionContratista() {
        setActContrat(true);
    }
    
    
    public void seleccionarInsumo() {
        presUnitCh = ic.getPresUnit();
    }
    
    public void calcularImporteAntcpo() {
        
        
        if(getAnticipoPct()>=0 && getAnticipoPct()<= 100) {
            anticipoImp = BigDecimal.valueOf((getImporteContrato().floatValue())*(getAnticipoPct()/100));
            setAmortAntcpoPct(getAnticipoPct());
            amortAntcpoImp = BigDecimal.valueOf((getImporteContrato().floatValue())*(getAmortAntcpoPct()/100));
            setCambio(false);
        }else {
            setAnticipoPct(0);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Anticipo no mayor al 50%!!"));
        }
        
                
       
    }
    
    public void calcularImporteGtia() {
        
        if(getFondoGtiaPct()>=0 && getFondoGtiaPct() <= 100) {
            fondoGtiaImp = BigDecimal.valueOf((getImporteContrato().floatValue())*(getFondoGtiaPct()/100));
            setCambio(false);
        }else {
            setFondoGtiaPct(0);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Garantía no mayor al 10%!!"));
        }
        
    }
    
    public void validarPrecio(CellEditEvent event) {
        
        InsumoContrat insCont = listInsum.get(event.getRowIndex());
        
        if(Float.parseFloat(event.getOldValue().toString()) <= Float.parseFloat(event.getNewValue().toString())){
            //Enviar msg de Error y config el valor anterior
            //System.out.println(aux);
            //System.out.println(event.getNewValue());
            insCont.setPresUnit(BigDecimal.valueOf(Float.parseFloat(event.getOldValue().toString())));
            
            
        }else {
            
            insCont.setPresUnit(BigDecimal.valueOf(Float.parseFloat(event.getNewValue().toString())));
            insCont.setImporteCont(insCont.getPresUnit().multiply(insCont.getCantContrato()));
            
            listId.add(event.getRowIndex());
            actualizarImporte();
        }
        
        
    }
    
    public void actualizarImporte() {
        
        setImporteContrato(BigDecimal.ZERO);
        for(InsumoContrat aux: listInsum) {
            setImporteContrato(importeContrato.add(aux.getImporteCont()));
        }
        
        
    }
    
        
    public void buscarContratista() {
        
        ConexionBD cnxn = new ConexionBD();
        Connection con = cnxn.getConexion();
        ContratistaDAO cd = new ContratistaDAO(con);
        setListCtta(cd.listarContratista());
    }
    
    public void seleccionarContratista() {
        setContratista(ctta.getRazonSocial());
        setCambio(false);
    }
    
    public void listarPreContrato() {
        
        if(id_proy != 0 && id_pres != 0) {
            ConexionBD cnxn = new ConexionBD();
            Connection con = cnxn.getConexion();
            ContratistaDAO cd = new ContratistaDAO(con);
            setListPre(cd.listarPreContratos(id_proy, id_pres));
        }     
    }
    
    
    
    public void listarCttos() {
        
        if(id_proy != 0 && id_pres != 0) {
            ConexionBD cnxn = new ConexionBD();
            Connection con = cnxn.getConexion();
            ContratistaDAO cd = new ContratistaDAO(con);
            setListPre(cd.listarContratosEdit(id_proy, id_pres));
        }
        
    }
    
    public void btnContrato() {
        BotonesBO btn = new BotonesBO();
        setActContrato(btn.botonContrato(getEstatusContto()));
    }
    
    public void seleccionarContrat() {
        setId_contrato(c.getId_contrato());
        setTipoContrat(c.getTipo());
        setImporteContrato(c.getImporteContrato());
        setNumContrato(c.getNumContrato());
        agregarInsumos();
        actualizarImporte();
        ConexionBD cnxn = new ConexionBD();
        Connection con = cnxn.getConexion();
        ContratistaDAO cd = new ContratistaDAO(con);
        setEstatusContto(cd.consultarContratoEstatus(getId_contrato()));
        if(estatusContto.equals("CONTRATO")) {
            completar(cd.completarContrato(getId_contrato()));
        }
        btnContrato();
    }
    
    public void completar(Contrato cntto) {
        setContratista(cntto.getContratista());
        setAnticipoPct(cntto.getAnticipoPct());
        setAnticipoImp(cntto.getAnticipoImp());
        setFondoGtiaPct(cntto.getFondoGtiaPct());
        setFondoGtiaImp(cntto.getFondoGtiaImp());
        setAmortAntcpoPct(cntto.getAmortAntcpoPct());
        setAmortAntcpoImp(cntto.getAmortAntcpoImp());
    }
    
    public void agregarInsumos() {
        
        ConexionBD cnxn = new ConexionBD();
        Connection con = cnxn.getConexion();
        ContratistaDAO cd = new ContratistaDAO(con);
        setListInsum(cd.listarInsumos(id_contrato, id_proy, id_pres));
        
    }
    
    public void listarPresupuesto() {
        
        if(ps!=null) {
            ConexionBD cnxn = new ConexionBD();
            Connection con = cnxn.getConexion();
            ContratistaDAO cd = new ContratistaDAO(con);
            setListaPres(cd.listarPresupuesto(id_proy));
            
        }
        
    }
    
    public void seleccionarPresCttos() {
        setId_pres(pb.getId_presupuesto());
        setPresupto(pb.getPresupuesto());
        listarCttos();
    }
    
    public void seleccionarPresupuesto() {
        setId_pres(pb.getId_presupuesto());
        setPresupto(pb.getPresupuesto());
        listarPreContrato();
    }
    
    public void listarProyectos() {
        ConexionBD cnxn = new ConexionBD();
        Connection con = cnxn.getConexion();
        ContratistaDAO cd = new ContratistaDAO(con);
        setListProy(cd.listarProyecto());
    }
    
    public void seleccionarProyect() {
        setId_proy(ps.getId_proyecto());
        setProyecto(ps.getProyecto());
        listarPresupuesto();
    }
    
    public void seleccionarInsumoEd() {
        
        setCodIns(insSel.getCodInsumo());
        setUnidIns(insSel.getUnidad());
        setCantIns(insSel.getCantContrato());
        setUnitIns(insSel.getPresUnit());
        setImpIns(insSel.getImporteCont());
        setCantidCtrl(insSel.getCantCtrl());
        setAuxUnit(insSel.getPresUnit());
        
    }
    
    public void ejecutarCambioIns() {
        
        insSel.setCantContrato(getCantIns());
        insSel.setPresUnit(getUnitIns());
        insSel.setImporteCont(getImpIns());
        listInsum.set(listInsum.indexOf(insSel), insSel);
        listEdit.add(insSel);
        setCambio(false);
        sumarContrato();
        calcularImporteAntcpo();
        calcularImporteGtia();
        
    }
    
    public void cambiarCantidadPrecio() {
        
        FacesContext msj = FacesContext.getCurrentInstance();
        
        if(getCantIns().floatValue() <= getCantidCtrl().floatValue() && getUnitIns().floatValue() <= getAuxUnit().floatValue()) {
            setImpIns(getCantIns().multiply(getUnitIns()));
        }else {
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", 
                    "El precio unitario no puede ser mayor al presupuesto "
                    + "y/o la cantidad no puede ser mayor al disponible:"));
            setCantIns(insSel.getCantContrato());
            setUnitIns(getAuxUnit());
        }
        
    }
    
    public void sumarContrato() {
        
        importeContrato = BigDecimal.ZERO;
        
        for(InsumoContrat aux:listInsum) {
            
            importeContrato = importeContrato.add(aux.getImporteCont());
            
        }
        
    }
   
    public int getId_proy() {
        return id_proy;
    }

    
    public void setId_proy(int id_proy) {
        this.id_proy = id_proy;
    }

    
    public String getProyecto() {
        return proyecto;
    }

    
    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    
    public int getId_pres() {
        return id_pres;
    }

    
    public void setId_pres(int id_pres) {
        this.id_pres = id_pres;
    }

    
    public String getPresupto() {
        return presupto;
    }

    
    public void setPresupto(String presupto) {
        this.presupto = presupto;
    }

    
    public List<ProyectoSimple> getListProy() {
        listarProyectos();
        return listProy;
    }

    
    public void setListProy(List<ProyectoSimple> listProy) {
        this.listProy = listProy;
    }

    
    public ProyectoSimple getPs() {
        return ps;
    }

    
    public void setPs(ProyectoSimple ps) {
        this.ps = ps;
    }

    /**
     * @return the pb
     */
    public PresupuestoBean getPb() {
        return pb;
    }

    /**
     * @param pb the pb to set
     */
    public void setPb(PresupuestoBean pb) {
        this.pb = pb;
    }

    /**
     * @return the listaPres
     */
    public ArrayList<PresupuestoBean> getListaPres() {
        listarPresupuesto();
        return listaPres;
    }

    /**
     * @param listaPres the listaPres to set
     */
    public void setListaPres(ArrayList<PresupuestoBean> listaPres) {
        this.listaPres = listaPres;
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
     * @return the tipoContrat
     */
    public String getTipoContrat() {
        return tipoContrat;
    }

    /**
     * @param tipoContrat the tipoContrat to set
     */
    public void setTipoContrat(String tipoContrat) {
        this.tipoContrat = tipoContrat;
    }

    /**
     * @return the listCtta
     */
    public List<Contratista> getListCtta() {
        return listCtta;
    }

    /**
     * @param listCtta the listCtta to set
     */
    public void setListCtta(List<Contratista> listCtta) {
        this.listCtta = listCtta;
    }

    /**
     * @return the ctta
     */
    public Contratista getCtta() {
        return ctta;
    }

    /**
     * @param ctta the ctta to set
     */
    public void setCtta(Contratista ctta) {
        this.ctta = ctta;
    }

    /**
     * @return the contratista
     */
    public String getContratista() {
        return contratista;
    }

    /**
     * @param contratista the contratista to set
     */
    public void setContratista(String contratista) {
        this.contratista = contratista;
    }

    /**
     * @return the importeContrato
     */
    public BigDecimal getImporteContrato() {
        return importeContrato;
    }

    /**
     * @param importeContrato the importeContrato to set
     */
    public void setImporteContrato(BigDecimal importeContrato) {
        this.importeContrato = importeContrato;
    }

    /**
     * @return the anticipoPct
     */
    public float getAnticipoPct() {
        return anticipoPct;
    }

    /**
     * @param anticipoPct the anticipoPct to set
     */
    public void setAnticipoPct(float anticipoPct) {
        this.anticipoPct = anticipoPct;
    }

    /**
     * @return the anticipoImp
     */
    public BigDecimal getAnticipoImp() {
        return anticipoImp;
    }

    /**
     * @param anticipoImp the anticipoImp to set
     */
    public void setAnticipoImp(BigDecimal anticipoImp) {
        this.anticipoImp = anticipoImp;
    }

    /**
     * @return the fondoGtiaPct
     */
    public float getFondoGtiaPct() {
        return fondoGtiaPct;
    }

    /**
     * @param fondoGtiaPct the fondoGtiaPct to set
     */
    public void setFondoGtiaPct(float fondoGtiaPct) {
        this.fondoGtiaPct = fondoGtiaPct;
    }

    /**
     * @return the fondoGtiaImp
     */
    public BigDecimal getFondoGtiaImp() {
        return fondoGtiaImp;
    }

    /**
     * @param fondoGtiaImp the fondoGtiaImp to set
     */
    public void setFondoGtiaImp(BigDecimal fondoGtiaImp) {
        this.fondoGtiaImp = fondoGtiaImp;
    }

    /**
     * @return the amortAntcpoPct
     */
    public float getAmortAntcpoPct() {
        return amortAntcpoPct;
    }

    /**
     * @param amortAntcpoPct the amortAntcpoPct to set
     */
    public void setAmortAntcpoPct(float amortAntcpoPct) {
        this.amortAntcpoPct = amortAntcpoPct;
    }

    /**
     * @return the amortAntcpoImp
     */
    public BigDecimal getAmortAntcpoImp() {
        return amortAntcpoImp;
    }

    /**
     * @param amortAntcpoImp the amortAntcpoImp to set
     */
    public void setAmortAntcpoImp(BigDecimal amortAntcpoImp) {
        this.amortAntcpoImp = amortAntcpoImp;
    }

    /**
     * @return the listInsum
     */
    public List<InsumoContrat> getListInsum() {
        return listInsum;
    }

    /**
     * @param listInsum the listInsum to set
     */
    public void setListInsum(List<InsumoContrat> listInsum) {
        this.listInsum = listInsum;
    }

    /**
     * @return the ic
     */
    public InsumoContrat getIc() {
        return ic;
    }

    /**
     * @param ic the ic to set
     */
    public void setIc(InsumoContrat ic) {
        this.ic = ic;
    }

    /**
     * @return the presUnitCh
     */
    public BigDecimal getPresUnitCh() {
        return presUnitCh;
    }

    /**
     * @param presUnitCh the presUnitCh to set
     */
    public void setPresUnitCh(BigDecimal presUnitCh) {
        this.presUnitCh = presUnitCh;
    }

    /**
     * @return the numContrato
     */
    public int getNumContrato() {
        return numContrato;
    }

    /**
     * @param numContrato the numContrato to set
     */
    public void setNumContrato(int numContrato) {
        this.numContrato = numContrato;
    }

    /**
     * @return the listId
     */
    public List<Integer> getListId() {
        return listId;
    }

    /**
     * @param listId the listId to set
     */
    public void setListId(List<Integer> listId) {
        this.listId = listId;
    }

    public String getEstatusContto() {
        return estatusContto;
    }

    public void setEstatusContto(String estatusContto) {
        this.estatusContto = estatusContto;
    }

    public boolean isActContrato() {
        return actContrato;
    }

    public void setActContrato(boolean actContrato) {
        this.actContrato = actContrato;
    }

    public boolean isActAnticipo() {
        return actAnticipo;
    }

    public void setActAnticipo(boolean actAnticipo) {
        this.actAnticipo = actAnticipo;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getRtnRenta() {
        return rtnRenta;
    }

    public void setRtnRenta(BigDecimal rtnRenta) {
        this.rtnRenta = rtnRenta;
    }

    public BigDecimal getRtnFlete() {
        return rtnFlete;
    }

    public void setRtnFlete(BigDecimal rtnFlete) {
        this.rtnFlete = rtnFlete;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * @return the actContrat
     */
    public boolean isActContrat() {
        return actContrat;
    }

    /**
     * @param actContrat the actContrat to set
     */
    public void setActContrat(boolean actContrat) {
        this.actContrat = actContrat;
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
     * @return the cambio
     */
    public boolean isCambio() {
        return cambio;
    }

    /**
     * @param cambio the cambio to set
     */
    public void setCambio(boolean cambio) {
        this.cambio = cambio;
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
     * @return the listEdit
     */
    public List<InsumoContrat> getListEdit() {
        return listEdit;
    }

    /**
     * @param listEdit the listEdit to set
     */
    public void setListEdit(List<InsumoContrat> listEdit) {
        this.listEdit = listEdit;
    }
    
}
