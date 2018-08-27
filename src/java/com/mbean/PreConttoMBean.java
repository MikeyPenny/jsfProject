/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.bean.CtaSubcontratoBean;
import com.bean.InsumoContrat;
import com.bean.PresupuestoBean;
import com.bean.ProyectoSimple;
import com.bo.ValidaImporte;
import com.conexion.ConexionBD;
import com.dao.ContratistaDAO;
import com.util.FiltrarLista;
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


@ManagedBean(name = "precontrato")
@ViewScoped
public class PreConttoMBean implements Serializable {

    /**
     * Creates a new instance of PreConttoMBean
     */
    public PreConttoMBean() {
        
    }
    
    private int id_proyecto;
    private int id_presup;
    private String proyecto;
    private String presupuesto;
    
    private String tipoContto;
    
    private boolean tipo = false;
    private boolean activo = false;
    
    private List<CtaSubcontratoBean> listaSubcs = new ArrayList<>();
    
    private List<InsumoContrat> listaContrat = new ArrayList<>();
    private InsumoContrat insumoSel;
    
    private List<ProyectoSimple> listProy = new ArrayList<>();
    private ProyectoSimple ps;
    
    private ArrayList<PresupuestoBean> listaPres = new ArrayList<>();
    private PresupuestoBean presB;
    
    private List<InsumoContrat> listaSubc = new ArrayList<>();
    private InsumoContrat insCont;
    
    private BigDecimal sumaContrato;
    
    private List<Integer> idCancel = new ArrayList<>();
    
    public void listarSubcontratos() {
        
        
        FiltrarLista filtro = new FiltrarLista();
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        ContratistaDAO cDAO = new ContratistaDAO(con);
        String proy = "" + getId_proyecto();
        String pres = "" + getId_presup();
//        System.out.println(tipoContto);
//        System.out.println(proy);
//        System.out.println(pres);
        if(getTipoContto().equals("") || pres.equals("0")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Revisar Campos Vacíos"));
        }else {
            setListaSubc(cDAO.listarSbConttos(getId_proyecto(), getId_presup()));
            setListaSubc(filtro.filtrarConceptos(getListaSubc(), getTipoContto()));
            setListaSubc(filtro.elimZero(listaSubc));
        }
        
        //System.out.println(listaSubc.size());
         
    }
    
    public void limpiarFormulario() {
        setProyecto(null);
        setPresupuesto(null);
        setTipoContto(null);
        getListaContrat().clear();
        setSumaContrato(null);
        setTipo(false);
        
    }
    
    public void validarCantidad(CellEditEvent event) {
        //System.out.println(listaContrat.size());
        int row = event.getRowIndex();
        //System.out.println(row);
        InsumoContrat insumo = getListaContrat().get(row);
        if(insumo.getCantContrato().floatValue() > insumo.getCantCtrl().floatValue()) {
            insumo.setCantContrato((BigDecimal)event.getOldValue());
            
        }
        
    }
    
    public void editarImporte() {
        
        for(InsumoContrat aux:getListaContrat()) {
            
            if(aux.getCantContrato() != null) {
                aux.setImporteCont(aux.getPresUnit().multiply(aux.getCantContrato()));
                sumarContrato();
                activarBoton();
            }
                      
        }
        
    }
    
    public void eliminarInsumo() {
        
        //System.out.println(insumoSel.getCodInsumo());
        getIdCancel().add(getInsumoSel().getId_insumo());
        getListaContrat().remove(getInsumoSel());
        editarImporte();
        
    }
    
    public void guardarPreContrato() {
        //System.out.println("Guardar Pre");
        FacesContext msj = FacesContext.getCurrentInstance();
        ConexionBD conexion = new ConexionBD();
        Connection con = conexion.getConexion();
        ContratistaDAO contrato = new ContratistaDAO(con);
        ValidaImporte v = new ValidaImporte();
        
        if(v.validarImportesContrato(getListaContrat())) {
            int registro = contrato.registrarPrecontrato(getListaContrat(), getTipoContto(), getSumaContrato(), getId_proyecto(), getId_presup());
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", "Se ha creado el registro de precontrato no. "+registro));
            msj.addMessage(null, new FacesMessage("Se ha creado el registro de precontrato no. "+registro));
            
        }else {
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Primero calcular los importes"));
            msj.addMessage(null, new FacesMessage("Primero calcular los importes"));
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
    
    public void obtenerProyecto() {
        setId_proyecto(getPs().getId_proyecto());
        setProyecto(getPs().getProyecto());
        buscarPresupuesto();
    }
    
    public void obtenerPresupuesto() {
        setId_presup(getPresB().getId_presupuesto());
        setPresupuesto(getPresB().getPresupuesto());
    }
    
    public void desactivarMenu() {
        setTipo(true);
        //System.out.println(listaContrat.size());
    }
    
    public void sumarContrato() {
        
        sumaContrato = BigDecimal.ZERO;
        
        for(InsumoContrat aux:listaContrat) {
            if(aux.getImporteCont()!= null) {
                //System.out.println(aux.getCantContrato());
                sumaContrato = sumaContrato.add(aux.getImporteCont());
                //System.out.println(sumaContrato);
            }
        }
        
    }
    
    public void activarBoton() {
                
        ValidaImporte v = new ValidaImporte();
        setActivo(v.validarImportesContrato(listaContrat));
        
    }
    
    public void buscarPresupuesto() {
        
        if(ps != null) {
            Connection con;
            ConexionBD conexion = new ConexionBD();
            con = conexion.getConexion();
            ContratistaDAO cDAO = new ContratistaDAO(con);
            
            setListaPres(cDAO.listarPresupuesto(ps.getId_proyecto()));
            //System.out.println(listaPres.size());
        }
        
    }
    
    public void consultarProyecto() {
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        ContratistaDAO ctrDAO = new ContratistaDAO(con);
        setListProy(ctrDAO.listarProyecto());
    }
    
    public void listaSubcontrato() {
        
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        ContratistaDAO cDAO = new ContratistaDAO(con);
        setListaSubcs(cDAO.listarSubcontratos());
        
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

    public String getTipoContto() {
        return tipoContto;
    }

    public void setTipoContto(String tipoContto) {
        this.tipoContto = tipoContto;
    }

    public boolean isTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    public List<CtaSubcontratoBean> getListaSubcs() {
        listaSubcontrato();
        return listaSubcs;
    }

    public void setListaSubcs(List<CtaSubcontratoBean> listaSubcs) {
        this.listaSubcs = listaSubcs;
    }

    public List<InsumoContrat> getListaContrat() {
        return listaContrat;
    }

    public void setListaContrat(List<InsumoContrat> listaContrat) {
        this.listaContrat = listaContrat;
    }

    public InsumoContrat getInsumoSel() {
        return insumoSel;
    }

    public void setInsumoSel(InsumoContrat insumoSel) {
        this.insumoSel = insumoSel;
    }

    public List<ProyectoSimple> getListProy() {
        consultarProyecto();
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

    public ArrayList<PresupuestoBean> getListaPres() {
        buscarPresupuesto();
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

    public List<InsumoContrat> getListaSubc() {
        return listaSubc;
    }

    public void setListaSubc(List<InsumoContrat> listaSubc) {
        this.listaSubc = listaSubc;
    }

    public InsumoContrat getInsCont() {
        return insCont;
    }

    public void setInsCont(InsumoContrat insCont) {
        this.insCont = insCont;
    }

    public BigDecimal getSumaContrato() {
        return sumaContrato;
    }

    public void setSumaContrato(BigDecimal sumaContrato) {
        this.sumaContrato = sumaContrato;
    }

    public List<Integer> getIdCancel() {
        return idCancel;
    }

    public void setIdCancel(List<Integer> idCancel) {
        this.idCancel = idCancel;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
}
