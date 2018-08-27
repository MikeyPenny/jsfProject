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
import com.bo.Cantidades;
import com.conexion.ConexionBD;
import com.dao.ContratistaDAO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;


@ManagedBean(name = "corregir")
@ViewScoped
public class CorregirMBean implements Serializable {

    public CorregirMBean() {
    }
    
    private int id_proy;
    private String proyecto;
    private int id_pres;
    private String presupto;
    private String dateNow;
    private int id_contrato;
    private String tipoContrat;
    private BigDecimal importeContrato;
    private int numContrato;
    private String contratista;
    private BigDecimal acumEstimado;
    private BigDecimal porEstimar;
    private int id_avance;
    
    private BigDecimal sumaAvance;
    private int nroAvance;
    
    private List<InsumoContrat> listInsAv = new ArrayList<InsumoContrat>();
    
    private List<ProyectoSimple> listProy = new ArrayList<ProyectoSimple>();
    private ProyectoSimple ps;
    
    private PresupuestoBean pb;
    private ArrayList<PresupuestoBean> listaPres = new ArrayList<PresupuestoBean>();
    
    private List<Contrato> listPre = new ArrayList<Contrato>();
    private Contrato c;
    
    private List<AvanceBean> listAv = new ArrayList<AvanceBean>();
    private AvanceBean ab;
    
    private List<InsumoContrat> listInsum = new ArrayList<InsumoContrat>();
    private InsumoContrat ic;
    
    public void cancelarAvance() {
        
        ConexionBD cnxn = new ConexionBD();
        Connection con = cnxn.getConexion();
        ContratistaDAO cd = new ContratistaDAO(con);
        
        cd.cancelarAvance(listInsAv, id_avance);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso!", "Avance cancelado"));
    }
    
    public void limpiarForma() {
        setProyecto(null);
        setPresupto(null);
        setNumContrato(0);
        setTipoContrat(null);
        setContratista(null);
        setImporteContrato(null);
        setAcumEstimado(null);
        setPorEstimar(null);
        setDateNow(null);
        setSumaAvance(null);
        setNroAvance(0);
        listInsAv.clear();
        listAv.clear();
        listInsum.clear();
        
    }
    
    public void actualizarAvance() {
        
        ConexionBD cnxn = new ConexionBD();
        Connection con = cnxn.getConexion();
        ContratistaDAO cd = new ContratistaDAO(con);
        cd.actualizarAvance(id_avance, listInsAv, sumaAvance);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Listo!", "Avance actualizado"));
    }
    
    public void sumarAvance() {
        
        BigDecimal importe = BigDecimal.ZERO;
        
        for(InsumoContrat aux: listInsAv) {
            importe = importe.add(aux.getImporteAvnce());
        }
        setSumaAvance(importe);
    }
    
    public void corregirAvance(CellEditEvent event) {
        //System.out.println("Editar");
        int row = event.getRowIndex();
        InsumoContrat insCont = getListInsAv().get(row);
        
        if(insCont.getAvance().floatValue() <= insCont.getCantDispAvn().floatValue()) {
            insCont.setImporteAvnce(insCont.getAvance().multiply(insCont.getPresUnit()));
        }else {
            insCont.setAvance((BigDecimal)event.getOldValue());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Avance no puede ser mayor al disponible"));
        }
        sumarAvance();
    }
    
    public void seleccionarProyect() {
        setId_proy(getPs().getId_proyecto());
        setProyecto(getPs().getProyecto());
        listarPresupuesto();
    }
    
    public void seleccionarPresupuesto() {
        setId_pres(getPb().getId_presupuesto());
        setPresupto(getPb().getPresupuesto());
        listarPreContrato();
    }
    
    public void seleccionarContratEdit() {
        setId_contrato(getC().getId_contrato());
        setTipoContrat(getC().getTipo());
        setNumContrato(getC().getNumContrato());
        setContratista(getC().getContratista());
        agregarInsumos();
        actualizarImporte();
        Cantidades c = new Cantidades();
        setAcumEstimado(c.calcularEstimado(getListInsum()));
        setPorEstimar(c.calcularPorEstimar(getImporteContrato(), getAcumEstimado()));
        
                
        ConexionBD cnxn = new ConexionBD();
        Connection con = cnxn.getConexion();
        ContratistaDAO cd = new ContratistaDAO(con);
        setListAv(cd.buscarAvances(getId_contrato()));
    }
    
    public void seleccionarAvance() {
        
        setNroAvance(ab.getNroAvance());
        setId_avance(ab.getId_avance());
        setDateNow(getAb().getFecha());
        ConexionBD cnxn = new ConexionBD();
        Connection con = cnxn.getConexion();
        ContratistaDAO cd = new ContratistaDAO(con);
        setListInsAv(cd.listarInsumoAvance(ab.getId_avance()));
        sumarAvance();
    }
    
    public void listarPresupuesto() {
        
        if(ps!=null) {
            ConexionBD cnxn = new ConexionBD();
            Connection con = cnxn.getConexion();
            ContratistaDAO cd = new ContratistaDAO(con);
            setListaPres(cd.listarPresupuesto(id_proy));
            
        }
        
    }
    
    public void listarPreContrato() {
        
        if(id_proy != 0 && id_pres != 0) {
            ConexionBD cnxn = new ConexionBD();
            Connection con = cnxn.getConexion();
            ContratistaDAO cd = new ContratistaDAO(con);
            setListPre(cd.listarContratos(id_proy, id_pres));
        }     
    }
    
    public void agregarInsumos() {
        
        ConexionBD cnxn = new ConexionBD();
        Connection con = cnxn.getConexion();
        ContratistaDAO cd = new ContratistaDAO(con);
        setListInsum(cd.listarInsumContrato(id_contrato));
        
    }
    
    public void actualizarImporte() {
        
        setImporteContrato(BigDecimal.ZERO);
        for(InsumoContrat aux: getListInsum()) {
            setImporteContrato(importeContrato.add(aux.getImporteCont()));
        }
        
    }
    
    public void listarProyectos() {
        ConexionBD cnxn = new ConexionBD();
        Connection con = cnxn.getConexion();
        ContratistaDAO cd = new ContratistaDAO(con);
        setListProy(cd.listarProyecto());
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

    public String getDateNow() {
        return dateNow;
    }

    public void setDateNow(String dateNow) {
        this.dateNow = dateNow;
    }

    public int getId_contrato() {
        return id_contrato;
    }

    public void setId_contrato(int id_contrato) {
        this.id_contrato = id_contrato;
    }

    public String getTipoContrat() {
        return tipoContrat;
    }

    public void setTipoContrat(String tipoContrat) {
        this.tipoContrat = tipoContrat;
    }

    public BigDecimal getImporteContrato() {
        return importeContrato;
    }

    public void setImporteContrato(BigDecimal importeContrato) {
        this.importeContrato = importeContrato;
    }

    public int getNumContrato() {
        return numContrato;
    }

    public void setNumContrato(int numContrato) {
        this.numContrato = numContrato;
    }

    public String getContratista() {
        return contratista;
    }

    public void setContratista(String contratista) {
        this.contratista = contratista;
    }

    public BigDecimal getAcumEstimado() {
        return acumEstimado;
    }

    public void setAcumEstimado(BigDecimal acumEstimado) {
        this.acumEstimado = acumEstimado;
    }

    public BigDecimal getPorEstimar() {
        return porEstimar;
    }

    public void setPorEstimar(BigDecimal porEstimar) {
        this.porEstimar = porEstimar;
    }

    public BigDecimal getSumaAvance() {
        return sumaAvance;
    }

    public void setSumaAvance(BigDecimal sumaAvance) {
        this.sumaAvance = sumaAvance;
    }

    public int getNroAvance() {
        return nroAvance;
    }

    public void setNroAvance(int nroAvance) {
        this.nroAvance = nroAvance;
    }

    public List<InsumoContrat> getListInsAv() {
        return listInsAv;
    }

    public void setListInsAv(List<InsumoContrat> listInsAv) {
        this.listInsAv = listInsAv;
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

    public PresupuestoBean getPb() {
        return pb;
    }

    public void setPb(PresupuestoBean pb) {
        this.pb = pb;
    }

    public ArrayList<PresupuestoBean> getListaPres() {
        return listaPres;
    }

    public void setListaPres(ArrayList<PresupuestoBean> listaPres) {
        this.listaPres = listaPres;
    }

    public List<Contrato> getListPre() {
        return listPre;
    }

    public void setListPre(List<Contrato> listPre) {
        this.listPre = listPre;
    }

    public Contrato getC() {
        return c;
    }

    public void setC(Contrato c) {
        this.c = c;
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

    public List<InsumoContrat> getListInsum() {
        return listInsum;
    }

    public void setListInsum(List<InsumoContrat> listInsum) {
        this.listInsum = listInsum;
    }

    public InsumoContrat getIc() {
        return ic;
    }

    public void setIc(InsumoContrat ic) {
        this.ic = ic;
    }

    public int getId_avance() {
        return id_avance;
    }

    public void setId_avance(int id_avance) {
        this.id_avance = id_avance;
    }
    
}
