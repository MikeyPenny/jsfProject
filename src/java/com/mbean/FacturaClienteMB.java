/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.bean.ConceptoEstim;
import com.bean.EstimacionCteBean;
import com.bean.ImportesBean;
import com.bean.PresupuestoBean;
import com.bean.ProyectoCteEst;
import com.bo.CalcularPctBO;
import com.bo.FacturaCteBO;
import com.conexion.ConexionBD;
import com.dao.FacturaClienteDAO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


@ManagedBean(name = "facturaCliente")
@ViewScoped
public class FacturaClienteMB implements Serializable {


    public FacturaClienteMB() {
    }

    private float ivaConfig;
    private int id_proyecto;
    private String proyecto;
    private int id_presupuesto;
    private String presupuesto;

    private String estimacion;

    private List<ProyectoCteEst> listProy = new ArrayList<>();
    private ProyectoCteEst ps;

    private PresupuestoBean pb;
    private ArrayList<PresupuestoBean> listaPres = new ArrayList<>();

    private BigDecimal importeEst;
    private String noFactura;
    private String diaFac;
    private String mesFac;
    private String anFac;
    private String cliente;

    private String concepto;
    private String centCosto;
    private float pctAvance;
    private float pctAnticipo;
    private BigDecimal amortAntcpo;
    private float pctGarantia;
    private BigDecimal amortGarantia;
    private BigDecimal otrasDeductiv;
    private String descDeductivas;
    private BigDecimal subtotal;
    private BigDecimal gtiaDespIva;
    private BigDecimal iva;
    private BigDecimal cargosFinanc;
    private BigDecimal total;
    private BigDecimal totalDespGarant;
    private BigDecimal importeAcum;
    private BigDecimal importeRest;

    private List<EstimacionCteBean> listConc = new ArrayList<>();
    private EstimacionCteBean conc;

    private String tipoFactura = "normal";
    
    private ImportesBean factura;
    
    public void comprobarFactura() {
        
        //System.out.println(getNoFactura()+"-"+conc.getId_cliente());
        
        
        FacturaCteBO fact = new FacturaCteBO();
        //System.out.println(fact.verificarFactura(getNoFactura(), conc.getId_cliente()));
        if(fact.verificarFactura(getNoFactura(), conc.getId_cliente())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El número de factura ya existe para ese cliente"));
            setNoFactura("");
        }
        
    }

    public void registrarFactura() {
        
        factura.setId_estimacion(conc.getId_estimacion());
        factura.setNoFactura(getNoFactura());
        factura.setFechaFact(getDiaFac()+"-"+getMesFac()+"-"+getAnFac());
        factura.setPctIva(getIvaConfig());
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String dateNow = format.format(today);
        factura.setFechaMov(dateNow);
        FacturaCteBO fac = new FacturaCteBO();
        factura.setEstatus(fac.verificarEstatus(getImporteEst(), getImporteRest()));
        ConexionBD c = new ConexionBD();
        FacturaClienteDAO dao = new FacturaClienteDAO(c.getConexion());
        
        if(getNoFactura() != null && ivaConfig > 0) {
            int reg = dao.registrarFactura(factura);
            
            if(reg > 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", "Se ha registrado la factura no. "+factura.getNoFactura()));
            }else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se ha registrado la factura"));
            }
            
        }else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El número de factura no puede ser vacío y el IVA debe ser mayor que cero 0"));
        }
        
        
        
        
    }
    
    public void configurarIVA() {
        System.out.println(getTipoFactura());
        System.out.println(getIvaConfig());
    }

    public void cargarDatos() {

        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String dateNow = format.format(today);
        setDiaFac(dateNow.substring(0, 2));
        setMesFac(dateNow.substring(3, 5));
        setAnFac(dateNow.substring(6, 10));
        FacturaCteBO bo = new FacturaCteBO();
        setCliente(bo.buscarDatosCliente(conc.getId_cliente()).getRazon());
        ProyectoCteEst proyect = bo.cargarDatosProyecto(id_proyecto);
        setCentCosto(proyect.getCentCosto());
        setPctAnticipo(proyect.getPctAntcpo());
        setPctGarantia(proyect.getPctGarantia());
        BigDecimal sumaDeduct = bo.otrasDeductivas(conc.getId_estimacion());
        setOtrasDeductiv(conc.getOtrasDeductivas().subtract(sumaDeduct));
        setDescDeductivas(conc.getDescDeduct());
        setConcepto(conc.getConceptoEstim());
        CalcularPctBO avance = new CalcularPctBO();
        setPctAvance(avance.calcularPct(getId_presupuesto()));
        
        setImporteAcum(bo.sumarFacturas(conc.getId_estimacion()));
        setImporteRest(conc.getImporte().subtract(getImporteAcum()));
        
        setImporteEst(getImporteRest());
        setAmortGarantia(bo.calcularGarantia(proyect.getPctGarantia(), getImporteEst(), getTipoFactura()));
        setAmortAntcpo(bo.calcularAnticipo(proyect.getPctAntcpo(), getImporteEst()));
        setGtiaDespIva(bo.garantiaDespIva(getImporteEst(), proyect.getPctGarantia(), getTipoFactura()));
        setSubtotal(getImporteEst().subtract(getAmortAntcpo()).subtract(getAmortGarantia()).subtract(getOtrasDeductiv()));
        setIva(bo.calcularIVA(getIvaConfig(), getSubtotal(), getTipoFactura()));
        ImportesBean imp = new ImportesBean();
        imp.setImporteEst(getImporteEst());
        imp.setAmortAntcpo(getAmortAntcpo());
        imp.setRetGtia(getAmortGarantia());
        imp.setOtrasDed(getOtrasDeductiv());
        imp.setSubtotal(getSubtotal());
        imp.setGtiDesIVA(getGtiaDespIva());
        imp.setIva(getIva());
        setTotal(bo.sumarTotal(imp, getTipoFactura()));
        imp.setTotal(getTotal());
        setFactura(imp);
        
    }
    
    public void calcularImportes() {
        
        if(getImporteEst().floatValue() < getImporteRest().floatValue()) {
            
            FacturaCteBO bo = new FacturaCteBO();
            //setImporteEst(getImporteRest());
            setAmortGarantia(bo.calcularGarantia(getPctGarantia(), getImporteEst(), getTipoFactura()));
            setAmortAntcpo(bo.calcularAnticipo(getPctAnticipo(), getImporteEst()));
            setGtiaDespIva(bo.garantiaDespIva(getImporteEst(), getPctGarantia(), getTipoFactura()));
            setSubtotal(getImporteEst().subtract(getAmortAntcpo()).subtract(getAmortGarantia()).subtract(getOtrasDeductiv()));
            setIva(bo.calcularIVA(getIvaConfig(), getSubtotal(), getTipoFactura()));
            ImportesBean imp = new ImportesBean();
            imp.setImporteEst(getImporteEst());
            imp.setAmortAntcpo(getAmortAntcpo());
            imp.setRetGtia(getAmortGarantia());
            imp.setOtrasDed(getOtrasDeductiv());
            imp.setSubtotal(getSubtotal());
            imp.setGtiDesIVA(getGtiaDespIva());
            imp.setIva(getIva());
            setTotal(bo.sumarTotal(imp, getTipoFactura()));
            imp.setTotal(getTotal());
            setFactura(imp);
            
        }else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El importe de la factura no puede superar la estimación"));
            setImporteEst(getImporteRest());
        }
        
        
    }

    public void seleccionarEstim() {
        setEstimacion(conc.getFecha());
        setImporteEst(conc.getImporte());
        cargarDatos();
    }

    public void listarEstimaciones() {
        FacturaCteBO bo = new FacturaCteBO();
        setListConc(bo.listarEstimaciones(id_proyecto, id_presupuesto));
    }

    public void seleccionarPresupuesto() {
        setId_presupuesto(pb.getId_presupuesto());
        setPresupuesto(pb.getPresupuesto());
        listarEstimaciones();
    }

    public void listarPresupuesto() {

        if(ps!=null) {
            ConexionBD cnxn = new ConexionBD();
            FacturaClienteDAO dao = new FacturaClienteDAO(cnxn.getConexion());
            setListaPres(dao.listarPresupuesto(id_proyecto));
            //System.out.println(listaPres.size());
        }

    }

    public void seleccionarProyect() {
        setId_proyecto(ps.getId_proyecto());
        setProyecto(ps.getProyecto());
        listarPresupuesto();
    }

    public void listarProyectos() {
        ConexionBD cnxn = new ConexionBD();
        FacturaClienteDAO dao =  new FacturaClienteDAO(cnxn.getConexion());
        setListProy(dao.listarProyecto());
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
     * @return the listProy
     */
    public List<ProyectoCteEst> getListProy() {
        listarProyectos();
        return listProy;
    }

    /**
     * @param listProy the listProy to set
     */
    public void setListProy(List<ProyectoCteEst> listProy) {
        this.listProy = listProy;
    }

    /**
     * @return the ps
     */
    public ProyectoCteEst getPs() {
        return ps;
    }

    /**
     * @param ps the ps to set
     */
    public void setPs(ProyectoCteEst ps) {
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
        return listaPres;
    }

    /**
     * @param listaPres the listaPres to set
     */
    public void setListaPres(ArrayList<PresupuestoBean> listaPres) {
        this.listaPres = listaPres;
    }

    /**
     * @return the estimacion
     */
    public String getEstimacion() {
        return estimacion;
    }

    /**
     * @param estimacion the estimacion to set
     */
    public void setEstimacion(String estimacion) {
        this.estimacion = estimacion;
    }

    /**
     * @return the noFactura
     */
    public String getNoFactura() {
        return noFactura;
    }

    /**
     * @param noFactura the noFactura to set
     */
    public void setNoFactura(String noFactura) {
        this.noFactura = noFactura;
    }

    /**
     * @return the diaFac
     */
    public String getDiaFac() {
        return diaFac;
    }

    /**
     * @param diaFac the diaFac to set
     */
    public void setDiaFac(String diaFac) {
        this.diaFac = diaFac;
    }

    /**
     * @return the mesFac
     */
    public String getMesFac() {
        return mesFac;
    }

    /**
     * @param mesFac the mesFac to set
     */
    public void setMesFac(String mesFac) {
        this.mesFac = mesFac;
    }

    /**
     * @return the anFac
     */
    public String getAnFac() {
        return anFac;
    }

    /**
     * @param anFac the anFac to set
     */
    public void setAnFac(String anFac) {
        this.anFac = anFac;
    }

    /**
     * @return the cliente
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the concepto
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * @param concepto the concepto to set
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     * @return the centCosto
     */
    public String getCentCosto() {
        return centCosto;
    }

    /**
     * @param centCosto the centCosto to set
     */
    public void setCentCosto(String centCosto) {
        this.centCosto = centCosto;
    }

    /**
     * @return the pctAvance
     */
    public float getPctAvance() {
        return pctAvance;
    }

    /**
     * @param pctAvance the pctAvance to set
     */
    public void setPctAvance(float pctAvance) {
        this.pctAvance = pctAvance;
    }

    /**
     * @return the pctAnticipo
     */
    public float getPctAnticipo() {
        return pctAnticipo;
    }

    /**
     * @param pctAnticipo the pctAnticipo to set
     */
    public void setPctAnticipo(float pctAnticipo) {
        this.pctAnticipo = pctAnticipo;
    }

    /**
     * @return the amortAntcpo
     */
    public BigDecimal getAmortAntcpo() {
        return amortAntcpo;
    }

    /**
     * @param amortAntcpo the amortAntcpo to set
     */
    public void setAmortAntcpo(BigDecimal amortAntcpo) {
        this.amortAntcpo = amortAntcpo;
    }

    /**
     * @return the pctGarantia
     */
    public float getPctGarantia() {
        return pctGarantia;
    }

    /**
     * @param pctGarantia the pctGarantia to set
     */
    public void setPctGarantia(float pctGarantia) {
        this.pctGarantia = pctGarantia;
    }

    /**
     * @return the amortGarantia
     */
    public BigDecimal getAmortGarantia() {
        return amortGarantia;
    }

    /**
     * @param amortGarantia the amortGarantia to set
     */
    public void setAmortGarantia(BigDecimal amortGarantia) {
        this.amortGarantia = amortGarantia;
    }

    /**
     * @return the otrasDeductiv
     */
    public BigDecimal getOtrasDeductiv() {
        return otrasDeductiv;
    }

    /**
     * @param otrasDeductiv the otrasDeductiv to set
     */
    public void setOtrasDeductiv(BigDecimal otrasDeductiv) {
        this.otrasDeductiv = otrasDeductiv;
    }

    /**
     * @return the subtotal
     */
    public BigDecimal getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * @return the gtiaDespIva
     */
    public BigDecimal getGtiaDespIva() {
        return gtiaDespIva;
    }

    /**
     * @param gtiaDespIva the gtiaDespIva to set
     */
    public void setGtiaDespIva(BigDecimal gtiaDespIva) {
        this.gtiaDespIva = gtiaDespIva;
    }

    /**
     * @return the iva
     */
    public BigDecimal getIva() {
        return iva;
    }

    /**
     * @param iva the iva to set
     */
    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    /**
     * @return the cargosFinanc
     */
    public BigDecimal getCargosFinanc() {
        return cargosFinanc;
    }

    /**
     * @param cargosFinanc the cargosFinanc to set
     */
    public void setCargosFinanc(BigDecimal cargosFinanc) {
        this.cargosFinanc = cargosFinanc;
    }

    /**
     * @return the total
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * @return the totalDespGarant
     */
    public BigDecimal getTotalDespGarant() {
        return totalDespGarant;
    }

    /**
     * @param totalDespGarant the totalDespGarant to set
     */
    public void setTotalDespGarant(BigDecimal totalDespGarant) {
        this.totalDespGarant = totalDespGarant;
    }

    /**
     * @return the listConc
     */
    public List<EstimacionCteBean> getListConc() {
        return listConc;
    }

    /**
     * @param listConc the listConc to set
     */
    public void setListConc(List<EstimacionCteBean> listConc) {
        this.listConc = listConc;
    }

    /**
     * @return the conc
     */
    public EstimacionCteBean getConc() {
        return conc;
    }

    /**
     * @param conc the conc to set
     */
    public void setConc(EstimacionCteBean conc) {
        this.conc = conc;
    }

    /**
     * @return the importeEst
     */
    public BigDecimal getImporteEst() {
        return importeEst;
    }

    /**
     * @param importeEst the importeEst to set
     */
    public void setImporteEst(BigDecimal importeEst) {
        this.importeEst = importeEst;
    }

    /**
     * @return the descDeductivas
     */
    public String getDescDeductivas() {
        return descDeductivas;
    }

    /**
     * @param descDeductivas the descDeductivas to set
     */
    public void setDescDeductivas(String descDeductivas) {
        this.descDeductivas = descDeductivas;
    }

    /**
     * @return the tipoFactura
     */
    public String getTipoFactura() {
        return tipoFactura;
    }

    /**
     * @param tipoFactura the tipoFactura to set
     */
    public void setTipoFactura(String tipoFactura) {
        this.tipoFactura = tipoFactura;
    }

    /**
     * @return the ivaConfig
     */
    public float getIvaConfig() {
        return ivaConfig;
    }

    /**
     * @param ivaConfig the ivaConfig to set
     */
    public void setIvaConfig(float ivaConfig) {
        this.ivaConfig = ivaConfig;
    }

    /**
     * @return the factura
     */
    public ImportesBean getFactura() {
        return factura;
    }

    /**
     * @param factura the factura to set
     */
    public void setFactura(ImportesBean factura) {
        this.factura = factura;
    }

    /**
     * @return the importeAcum
     */
    public BigDecimal getImporteAcum() {
        return importeAcum;
    }

    /**
     * @param importeAcum the importeAcum to set
     */
    public void setImporteAcum(BigDecimal importeAcum) {
        this.importeAcum = importeAcum;
    }

    /**
     * @return the importeRest
     */
    public BigDecimal getImporteRest() {
        return importeRest;
    }

    /**
     * @param importeRest the importeRest to set
     */
    public void setImporteRest(BigDecimal importeRest) {
        this.importeRest = importeRest;
    }




}
