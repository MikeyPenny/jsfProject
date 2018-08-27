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
import com.bo.Fecha;
import com.conexion.ConexionBD;
import com.dao.ContratistaDAO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;


@ManagedBean(name = "avance")
@ViewScoped
public class AvanceMBean implements Serializable {

    public AvanceMBean() {
        Fecha today = new Fecha();
        setDateNow(today.generarFecha());
        
    }
    
    private int id_proy;
    private String proyecto;
    private int id_pres;
    private String presupto;
    private String dateNow;
    
    private List<ProyectoSimple> listProy = new ArrayList<ProyectoSimple>();
    private ProyectoSimple ps;
    
    private PresupuestoBean pb;
    private ArrayList<PresupuestoBean> listaPres = new ArrayList<>();
    
    private List<Contrato> listPre = new ArrayList<>();
    private Contrato c;
    
    private int id_contrato;
    private String tipoContrat;
    private BigDecimal importeContrato;
    private int numContrato;
    private String contratista;
    
    private List<InsumoContrat> listInsum = new ArrayList<>();
    private InsumoContrat ic;
    
    private List<InsumoContrat> listSelec = new ArrayList<>();
    
    private BigDecimal acumEstimado;
    private BigDecimal porEstimar;
    
    private BigDecimal sumaAvance;
    private int nroAvance;
    
    
    
    private boolean actRegistro = true;
    
    

    
    public void limpiarFormulario() {
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context
         .getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse(); 
    }
    
    public void registrarAvance() {
        
        AvanceBean av = new AvanceBean();
        av.setEstatusAvance("AVANCE");
        av.setImporteEstimacion(getSumaAvance());
        av.setFecha(dateNow);
        
        Cantidades cant = new Cantidades();
        setListSelec(cant.configurarImportesAvances(listSelec));
        
        ConexionBD cnxn = new ConexionBD();
        Connection con = cnxn.getConexion();
        ContratistaDAO cd = new ContratistaDAO(con);
        
        //System.out.println(id_contrato);
        setNroAvance(cd.registrarAvance(av, id_contrato, listSelec));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Revisar Estimaciones", "Avance "+nroAvance+ " Exitoso!!"));
        
        limpiarFormulario();
        
    }
    
    public void validarAvance(CellEditEvent e) {
        FacesContext msj = FacesContext.getCurrentInstance();
        int row = e.getRowIndex();
        InsumoContrat insumo = listSelec.get(row);
        //System.out.println(insumo.getAvance());
        //System.out.println(insumo.getCantContrato().subtract(insumo.getSumaAvance()));
        if(insumo.getAvance().floatValue() <= 0) {
            insumo.setAvance((BigDecimal)e.getOldValue());
            msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",  
                    "El avance no puede ser negativo o nulo."));
        }else {
            if((insumo.getCantContrato().subtract(insumo.getSumaAvance())).floatValue() < insumo.getAvance().floatValue()) {
                insumo.setAvance((BigDecimal)e.getOldValue());
                msj.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",  
                        "El avance no puede ser mayor al total por estimar."));
                //System.out.println(insumo.getCantContrato().subtract(insumo.getSumaAvance()));

            }else {
                setActRegistro(activarBoton());
            }
            
        }
        
        
        Cantidades c =  new Cantidades();
        setSumaAvance(c.sumarAvance(listSelec));
        
        
    }
    
    public boolean activarBoton() {
        
        boolean activ = true; 
        
        for(InsumoContrat aux: listSelec) {
            if(aux.getAvance() != null) {
                activ =false;
            }else {
                activ = true;
                break;
            }
        }
        //System.out.println(activ);
        return activ;
    }
    
    public void actualizarImporte() {
        
        setImporteContrato(BigDecimal.ZERO);
        for(InsumoContrat aux: listInsum) {
            setImporteContrato(importeContrato.add(aux.getImporteCont()));
        }
        
    }
    
    public void agregarInsumos() {
        
        ConexionBD cnxn = new ConexionBD();
        Connection con = cnxn.getConexion();
        ContratistaDAO cd = new ContratistaDAO(con);
        setListInsum(cd.listarInsumContrato(id_contrato));
        
    }
    
    public void seleccionarContrat() {
        setId_contrato(c.getId_contrato());
        setTipoContrat(c.getTipo());
        setNumContrato(c.getNumContrato());
        setContratista(c.getContratista());
        agregarInsumos();
        actualizarImporte();
        Cantidades c = new Cantidades();
        setAcumEstimado(c.calcularEstimado(listInsum));
        setPorEstimar(c.calcularPorEstimar(importeContrato, acumEstimado));
        if(porEstimar.floatValue() == 0) {
            setActRegistro(true);
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
    
    public void seleccionarPresupuesto() {
        setId_pres(pb.getId_presupuesto());
        setPresupto(pb.getPresupuesto());
        listarPreContrato();
    }
    
    public void seleccionarProyect() {
        setId_proy(ps.getId_proyecto());
        setProyecto(ps.getProyecto());
        listarPresupuesto();
    }
    
    public void listarPresupuesto() {
        
        if(ps!=null) {
            ConexionBD cnxn = new ConexionBD();
            Connection con = cnxn.getConexion();
            ContratistaDAO cd = new ContratistaDAO(con);
            setListaPres(cd.listarPresupuesto(id_proy));
            
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

    public String getContratista() {
        return contratista;
    }

    public void setContratista(String contratista) {
        this.contratista = contratista;
    }

    public List<InsumoContrat> getListSelec() {
        return listSelec;
    }

    public void setListSelec(List<InsumoContrat> listSelec) {
        this.listSelec = listSelec;
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

    public String getDateNow() {
        return dateNow;
    }

    public void setDateNow(String dateNow) {
        this.dateNow = dateNow;
    }

    public int getNroAvance() {
        return nroAvance;
    }

    public void setNroAvance(int nroAvance) {
        this.nroAvance = nroAvance;
    }

    

    public boolean isActRegistro() {
        return actRegistro;
    }

    public void setActRegistro(boolean actRegistro) {
        this.actRegistro = actRegistro;
    }
    
}
