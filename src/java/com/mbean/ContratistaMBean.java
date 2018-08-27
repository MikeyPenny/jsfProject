/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.bean.Contrato;
import com.bean.CtaSubcontratoBean;
import com.bean.InsumoContrat;
import com.bean.PresupuestoBean;
import com.bean.ProyectoSimple;
import com.bo.Cantidades;
import com.bo.ValidaImporte;
import com.conexion.ConexionBD;
import com.dao.ContratistaDAO;
import com.util.FiltrarLista;
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


@ManagedBean
@ViewScoped
public class ContratistaMBean implements Serializable {

    private int id_proyecto;
    private int id_presup;
    private String tipoContto;
    
    private List<ProyectoSimple> listProy = new ArrayList<>();
    private ProyectoSimple ps;
    
    private String proyecto;
    private String presupuesto;
    
    private ArrayList<PresupuestoBean> listaPres = new ArrayList<>();
    private PresupuestoBean presB;
    
    private List<CtaSubcontratoBean> listaSubcs = new ArrayList<>();
    
    private List<InsumoContrat> listaSubc = new ArrayList<>();
    private InsumoContrat insCont;
    
    private List<InsumoContrat> listaContrat = new ArrayList<>();
    private InsumoContrat insumoSel;
    
    private BigDecimal sumaContrato;
    
    private boolean activo = false;
    private boolean tipo = false;
    
    private List<Contrato> listPre = new ArrayList<>();
    private Contrato c;
    
    private List<Integer> idCancel = new ArrayList<>();
    
    public ContratistaMBean() {
        
    }
    
    public void eliiminarPreContrato() {
        ConexionBD conexion = new ConexionBD();
        Connection con = conexion.getConexion();
        ContratistaDAO contrato = new ContratistaDAO(con);
        contrato.cancelarPreContrato(c.getId_contrato());
        limpiarFormulario();
    }
    
    public void actualizarPreContrato() {
        ConexionBD conexion = new ConexionBD();
        Connection con = conexion.getConexion();
        ContratistaDAO contrato = new ContratistaDAO(con);
        c.setImporteContrato(sumaContrato);
        if(idCancel != null) {   
            contrato.elimInsumoPreContrato(c, idCancel, listaContrat);
            limpiarFormEdit();
        }else {
            contrato.editarPreContrato(c, listaContrat);
            limpiarFormEdit();
        }
    }
    
    public void eliminarInsumo() {
        
        //System.out.println(insumoSel.getCodInsumo());
        idCancel.add(insumoSel.getId_insumo());
        listaContrat.remove(insumoSel);
        editarImporte();
        
    }
    
    public void limpiarFormulario() {
        setProyecto(null);
        setPresupuesto(null);
        setTipoContto(null);
        listaContrat.clear();
        setSumaContrato(null);
        setTipo(false);
        
    }
    
    public void limpiarFormEdit() {
        setProyecto(null);
        setPresupuesto(null);
        listaContrat.clear();
        setSumaContrato(null);
        setActivo(false);
        idCancel.clear();
    }
    
    public void desactivarMenu() {
        setTipo(true);
        System.out.println(listaContrat.size());
    }
    
    public void validarCantidad(CellEditEvent event) {
        //System.out.println(listaContrat.size());
        int row = event.getRowIndex();
        //System.out.println(row);
        InsumoContrat insumo = listaContrat.get(row);
        if(insumo.getCantContrato().floatValue() > insumo.getCantCtrl().floatValue()) {
            insumo.setCantContrato((BigDecimal)event.getOldValue());
            
        }
        
    }
    
    public void mostrarInfoEdit() {
        ConexionBD conexion = new ConexionBD();
        ContratistaDAO contrato = new ContratistaDAO(conexion.getConexion());
        //int id_cntt = c.getId_contrato();
        setListaContrat(contrato.listarInsumos(c.getId_contrato(),getId_proyecto(),getId_presup()));
        sumarContrato();
        Cantidades cants = new Cantidades();
        setListaContrat(cants.ajustarCantidades(listaContrat));
        
        
    }
    
    /*public void mostrarInfoContrato() {
        ConexionBD conexion = new ConexionBD();
        Connection con = conexion.getConexion();
        ContratistaDAO contrato = new ContratistaDAO(con);
        int id_cntt = c.getId_contrato();
        setListaContrat(contrato.listarInsumos(id_cntt));
        
    }*/
    
    public void listarContratos() {
        ConexionBD conexion = new ConexionBD();
        Connection con = conexion.getConexion();
        ContratistaDAO contrato = new ContratistaDAO(con);
        setListPre(contrato.listarPreContratos(id_proyecto, id_presup));
        
    }
    
    public String guardarPreContrato() {
        ConexionBD conexion = new ConexionBD();
        Connection con = conexion.getConexion();
        ContratistaDAO contrato = new ContratistaDAO(con);
        ValidaImporte v = new ValidaImporte();
        
        if(v.validarImportesContrato(listaContrat)) {
            int registro = contrato.registrarPrecontrato(listaContrat, tipoContto, sumaContrato, id_proyecto, id_presup);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", "Se ha creado el registro de precontrato no. "+registro));
            
        }else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Primero calcular los importes"));
        }
        
        return "index.xhtml";
    }
    
    public void activarBoton() {
                
        ValidaImporte v = new ValidaImporte();
        setActivo(v.validarImportesContrato(listaContrat));
        
    }
    
    public void agregarInsumo() {
        listaContrat.add(insCont);
        activarBoton();
    }
    
    public void editarImporte() {
        
        for(InsumoContrat aux:listaContrat) {
            
            if(aux.getCantContrato() != null) {
                aux.setImporteCont(aux.getPresUnit().multiply(aux.getCantContrato()));
                sumarContrato();
                activarBoton();
            }
                      
        }
        
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
    
    public void revisar() {
        System.out.println(tipoContto);
    }
    
    public void listarSubcontratos() {
        
        FiltrarLista filtro = new FiltrarLista();
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        ContratistaDAO cDAO = new ContratistaDAO(con);
        String proy = "" + id_proyecto;
        String pres = "" + id_presup;
//        System.out.println(tipoContto);
//        System.out.println(proy);
//        System.out.println(pres);
        if(tipoContto.equals("") || pres.equals("0")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Revisar Campos Vacíos"));
        }else {
            setListaSubc(cDAO.listarConceptos(id_proyecto, id_presup));
            setListaSubc(filtro.filtrarConceptos(listaSubc, tipoContto));
        }
         
    }
    
    public void obtenerProyecto() {
        setId_proyecto(ps.getId_proyecto());
        setProyecto(ps.getProyecto());
        buscarPresupuesto();
    }
    
    public void obtenerPresupuesto() {
        setId_presup(presB.getId_presupuesto());
        setPresupuesto(presB.getPresupuesto());
    }
    
    public void listaSubcontrato() {
        
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        ContratistaDAO cDAO = new ContratistaDAO(con);
        setListaSubcs(cDAO.listarSubcontratos());
        
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


    public List<CtaSubcontratoBean> getListaSubcs() {
        listaSubcontrato();
        return listaSubcs;
    }


    public void setListaSubcs(List<CtaSubcontratoBean> listaSubcs) {
        this.listaSubcs = listaSubcs;
    }

    /**
     * @return the listaSubc
     */
    public List<InsumoContrat> getListaSubc() {
        return listaSubc;
    }

    /**
     * @param listaSubc the listaSubc to set
     */
    public void setListaSubc(List<InsumoContrat> listaSubc) {
        this.listaSubc = listaSubc;
    }

    /**
     * @return the insCont
     */
    public InsumoContrat getInsCont() {
        return insCont;
    }

    /**
     * @param insCont the insCont to set
     */
    public void setInsCont(InsumoContrat insCont) {
        this.insCont = insCont;
    }

    /**
     * @return the listaContrat
     */
    public List<InsumoContrat> getListaContrat() {
        return listaContrat;
    }

    /**
     * @param listaContrat the listaContrat to set
     */
    public void setListaContrat(List<InsumoContrat> listaContrat) {
        this.listaContrat = listaContrat;
    }

    /**
     * @return the sumaContrato
     */
    public BigDecimal getSumaContrato() {
        return sumaContrato;
    }

    /**
     * @param sumaContrato the sumaContrato to set
     */
    public void setSumaContrato(BigDecimal sumaContrato) {
        this.sumaContrato = sumaContrato;
    }

    /**
     * @return the activo
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
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
     * @return the tipo
     */
    public boolean isTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the insumoSel
     */
    public InsumoContrat getInsumoSel() {
        return insumoSel;
    }

    /**
     * @param insumoSel the insumoSel to set
     */
    public void setInsumoSel(InsumoContrat insumoSel) {
        this.insumoSel = insumoSel;
    }

    /**
     * @return the idCancel
     */
    public List<Integer> getIdCancel() {
        return idCancel;
    }

    /**
     * @param idCancel the idCancel to set
     */
    public void setIdCancel(List<Integer> idCancel) {
        this.idCancel = idCancel;
    }
    
}
