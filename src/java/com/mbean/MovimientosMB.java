/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.bean.EstimacionCteBean;
import com.bean.ImportesBean;
import com.bean.MovimientoCteBean;
import com.bean.PresupuestoBean;
import com.bean.ProyectoCteEst;
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


@ManagedBean
@ViewScoped
public class MovimientosMB implements Serializable {

    private int id_proyecto;
    private String proyecto;
    private int id_presupuesto;
    private String presupuesto;
    private String estimacion;
    private int id_estimacion;
    
    private BigDecimal importeEst;
    private BigDecimal amortAntcpo;
    private BigDecimal retGtia;
    private BigDecimal otrasDed;
    private BigDecimal subtotal;
    private BigDecimal gtiDesIVA;
    private BigDecimal iva;
    private BigDecimal total;
    private String noFactura;
    private String fechaFact;
    private String fechaMov;
    private int estatus;
    private float pctIva;
    private int id_factura;
    
    private BigDecimal pagoAplicado;
    
    private List<ProyectoCteEst> listProy = new ArrayList<>();
    private ProyectoCteEst ps;

    private PresupuestoBean pb;
    private ArrayList<PresupuestoBean> listaPres = new ArrayList<>();
    
    private List<EstimacionCteBean> listConc = new ArrayList<>();
    private EstimacionCteBean conc;
    
    private List<ImportesBean> listFact = new ArrayList<>();
    private ImportesBean factSel;
    
    private BigDecimal sumaMovs;
    
    private List<MovimientoCteBean> listMovs = new ArrayList<>();
    private MovimientoCteBean movSel;
    
    private BigDecimal restoPorPagar;
    
    public MovimientosMB() {
        
    }
    
    public void listarProyectos() {
        ConexionBD cnxn = new ConexionBD();
        FacturaClienteDAO dao =  new FacturaClienteDAO(cnxn.getConexion());
        setListProy(dao.listarProyecto());
    }
    
    public void seleccionarProyect() {
        setId_proyecto(ps.getId_proyecto());
        setProyecto(ps.getProyecto());
        listarPresupuesto();
    }
    
    public void listarPresupuesto() {

        if(ps!=null) {
            ConexionBD cnxn = new ConexionBD();
            FacturaClienteDAO dao = new FacturaClienteDAO(cnxn.getConexion());
            setListaPres(dao.listarPresupuesto(id_proyecto));
            //System.out.println(listaPres.size());
        }

    }
    
    public void seleccionarPresupuesto() {
        setId_presupuesto(pb.getId_presupuesto());
        setPresupuesto(pb.getPresupuesto());
        listarEstimaciones();
    }
    
    public void listarEstimaciones() {
        FacturaCteBO bo = new FacturaCteBO();
        setListConc(bo.listarEstimacionesFac(id_proyecto, id_presupuesto));
    }
    
    public void seleccionarEstim() {
        setEstimacion(conc.getFecha());
        setId_estimacion(conc.getId_estimacion());
        listarFacturas();
    }
    
    public void listarFacturas() {
        ConexionBD c = new ConexionBD();
        FacturaClienteDAO dao = new FacturaClienteDAO(c.getConexion());
        setListFact(dao.listarFacturasPorEstimacion(getId_estimacion()));
    }
    
    public void seleccionarFactura() {
        
        ConexionBD c = new ConexionBD();
        //FacturaClienteDAO dao = new FacturaClienteDAO(c.getConexion());
        //setSumaMovs(dao.sumarMovsFactura(factSel.getId_factura()));
        setId_factura(factSel.getId_factura());
        setId_estimacion(factSel.getId_estimacion());
        setNoFactura(factSel.getNoFactura());
        setFechaFact(factSel.getFechaFact());
        setImporteEst(factSel.getImporteEst());
        setOtrasDed(factSel.getOtrasDed());
        setAmortAntcpo(factSel.getAmortAntcpo());
        setGtiDesIVA(factSel.getGtiDesIVA());
        setRetGtia(factSel.getRetGtia());
        setPctIva(factSel.getPctIva());
        setSubtotal(factSel.getSubtotal());
        setIva(factSel.getIva());
        setTotal(factSel.getTotal());
        
        Date d = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
        String dateNow = sd.format(d);
        setFechaMov(dateNow);
        setPagoAplicado(factSel.getTotal());
        //setRestoPorPagar(factSel.getTotal());
    }
    
    public void listarMovimientos() {
        ConexionBD c = new ConexionBD();
        FacturaClienteDAO dao = new FacturaClienteDAO(c.getConexion());
        setListMovs(dao.listarMovimientosPorFactura(factSel.getId_factura()));
    }
    
    public void validarImporte() {
        
        if(pagoAplicado.floatValue() > restoPorPagar.floatValue()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El pago por aplicar no puede ser mayor al importe de la factura, revisar movimientos."));
            setPagoAplicado(getRestoPorPagar());
        }
        
    }
    
    public void aplicarMovimiento() {
        
        MovimientoCteBean mov = new MovimientoCteBean();
        mov.setImporte(getPagoAplicado());
        mov.setFechaMov(getFechaMov());
        mov.setId_factura(factSel.getId_factura());
        
        ConexionBD c = new ConexionBD();
        FacturaClienteDAO dao = new FacturaClienteDAO(c.getConexion());
        if(dao.aplicarMovimiento(mov) > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", "El pago se registro por un importe de: "+mov.getImporte()));
        }else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El pago no se ha registrado."));
        }
        
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
     * @return the id_estimacion
     */
    public int getId_estimacion() {
        return id_estimacion;
    }

    /**
     * @param id_estimacion the id_estimacion to set
     */
    public void setId_estimacion(int id_estimacion) {
        this.id_estimacion = id_estimacion;
    }

    /**
     * @return the listFact
     */
    public List<ImportesBean> getListFact() {
        return listFact;
    }

    /**
     * @param listFact the listFact to set
     */
    public void setListFact(List<ImportesBean> listFact) {
        this.listFact = listFact;
    }

    /**
     * @return the factSel
     */
    public ImportesBean getFactSel() {
        return factSel;
    }

    /**
     * @param factSel the factSel to set
     */
    public void setFactSel(ImportesBean factSel) {
        this.factSel = factSel;
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
     * @return the retGtia
     */
    public BigDecimal getRetGtia() {
        return retGtia;
    }

    /**
     * @param retGtia the retGtia to set
     */
    public void setRetGtia(BigDecimal retGtia) {
        this.retGtia = retGtia;
    }

    /**
     * @return the otrasDed
     */
    public BigDecimal getOtrasDed() {
        return otrasDed;
    }

    /**
     * @param otrasDed the otrasDed to set
     */
    public void setOtrasDed(BigDecimal otrasDed) {
        this.otrasDed = otrasDed;
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
     * @return the gtiDesIVA
     */
    public BigDecimal getGtiDesIVA() {
        return gtiDesIVA;
    }

    /**
     * @param gtiDesIVA the gtiDesIVA to set
     */
    public void setGtiDesIVA(BigDecimal gtiDesIVA) {
        this.gtiDesIVA = gtiDesIVA;
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
     * @return the fechaFact
     */
    public String getFechaFact() {
        return fechaFact;
    }

    /**
     * @param fechaFact the fechaFact to set
     */
    public void setFechaFact(String fechaFact) {
        this.fechaFact = fechaFact;
    }

    /**
     * @return the fechaMov
     */
    public String getFechaMov() {
        return fechaMov;
    }

    /**
     * @param fechaMov the fechaMov to set
     */
    public void setFechaMov(String fechaMov) {
        this.fechaMov = fechaMov;
    }

    /**
     * @return the estatus
     */
    public int getEstatus() {
        return estatus;
    }

    /**
     * @param estatus the estatus to set
     */
    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    /**
     * @return the pctIva
     */
    public float getPctIva() {
        return pctIva;
    }

    /**
     * @param pctIva the pctIva to set
     */
    public void setPctIva(float pctIva) {
        this.pctIva = pctIva;
    }

    /**
     * @return the id_factura
     */
    public int getId_factura() {
        return id_factura;
    }

    /**
     * @param id_factura the id_factura to set
     */
    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    /**
     * @return the pagoAplicado
     */
    public BigDecimal getPagoAplicado() {
        return pagoAplicado;
    }

    /**
     * @param pagoAplicado the pagoAplicado to set
     */
    public void setPagoAplicado(BigDecimal pagoAplicado) {
        this.pagoAplicado = pagoAplicado;
    }

    /**
     * @return the sumaMovs
     */
    public BigDecimal getSumaMovs() {
        return sumaMovs;
    }

    /**
     * @param sumaMovs the sumaMovs to set
     */
    public void setSumaMovs(BigDecimal sumaMovs) {
        this.sumaMovs = sumaMovs;
    }

    /**
     * @return the listMovs
     */
    public List<MovimientoCteBean> getListMovs() {
        return listMovs;
    }

    /**
     * @param listMovs the listMovs to set
     */
    public void setListMovs(List<MovimientoCteBean> listMovs) {
        this.listMovs = listMovs;
    }

    /**
     * @return the movSel
     */
    public MovimientoCteBean getMovSel() {
        return movSel;
    }

    /**
     * @param movSel the movSel to set
     */
    public void setMovSel(MovimientoCteBean movSel) {
        this.movSel = movSel;
    }

    /**
     * @return the restoPorPagar
     */
    public BigDecimal getRestoPorPagar() {
        return restoPorPagar;
    }

    /**
     * @param restoPorPagar the restoPorPagar to set
     */
    public void setRestoPorPagar(BigDecimal restoPorPagar) {
        this.restoPorPagar = restoPorPagar;
    }
}
