/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.bean.ConceptoEstim;
import com.bean.EstimacionCteBean;
import com.bean.PresupuestoBean;
import com.bean.ProyectoSimple;
import com.bo.CalcularBO;
import com.bo.EstimarBO;
import com.conexion.ConexionBD;
import com.dao.EstimaClienteDAO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;


@ManagedBean(name = "estimaCliente")
@ViewScoped
public class EstimaClienteMB implements Serializable {

    
    public EstimaClienteMB() {
    }
    
    private int id_proy;
    private String proyecto;
    private int id_pres;
    private String presupto;
    private String conceptoFact;
    private BigDecimal otrasDeductivas;
    private String descDeduct;
    private float porcentaje;
    
    private List<ProyectoSimple> listProy = new ArrayList<>();
    private ProyectoSimple ps;
    
    private PresupuestoBean pb;
    private ArrayList<PresupuestoBean> listaPres = new ArrayList<>();
    
    private List<ConceptoEstim> listConc = new ArrayList<>();
    private List<ConceptoEstim> listConSel = new ArrayList<>();
    
    private BigDecimal sumaEstima;
    
    public void guardarEstimacion() {
        
        //System.out.println("Proy:"+getId_proy()+"\nPres: "+getId_pres());
        EstimacionCteBean estim = new EstimacionCteBean();
        estim.setOtrasDeductivas(getOtrasDeductivas());
        estim.setDescDeduct(getDescDeduct());
        estim.setConceptoEstim(getConceptoFact());
        estim.setId_presupuesto(getId_pres());
        
        EstimarBO est = new EstimarBO();
        
        if(est.registrarEstimacion(listConSel, id_proy, estim) > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", "Se ha creado el registro de la estimación"));
        }else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrió un error"));
        }
        
    }
    
    public void sumarEstimacion() {
        
        sumaEstima = BigDecimal.ZERO;
        
        for(ConceptoEstim aux:listConSel) {
            if(aux.getImporte()!= null) {
                //System.out.println(aux.getCantContrato());
                sumaEstima = sumaEstima.add(aux.getImporte());
                //System.out.println(sumaContrato);
            }
        }
        
    }
    
    public void validarCantidad(CellEditEvent event) {
        //System.out.println(listaContrat.size());
        int row = event.getRowIndex();
        //System.out.println(row);
        ConceptoEstim concepto = getListConSel().get(row);
        if(concepto.getCantEstimada().floatValue() > concepto.getPorEstimar().floatValue()) {
            concepto.setCantEstimada(BigDecimal.ZERO);
            
        }
        
    }
    
    public void editarImporte() {
        
        for(ConceptoEstim aux:getListConSel()) {
            
            if(aux.getCantEstimada()!= null && aux.getCantEstimada().floatValue()>0) {
                aux.setImporte(aux.getPresUnitario().multiply(aux.getCantEstimada()));
                sumarEstimacion();
                
            }else {
                aux.setImporte(aux.getPresUnitario().multiply(aux.getPorEstimar()));
            }
                      
        }
        
    }
    
    public void seleccionarProyect() {
        setId_proy(ps.getId_proyecto());
        setProyecto(ps.getProyecto());
        listarPresupuesto();
    }
    
    public void listarPresupuesto() {
        
        if(ps!=null) {
            ConexionBD cnxn = new ConexionBD();
            EstimaClienteDAO dao = new EstimaClienteDAO(cnxn.getConexion());
            setListaPres(dao.listarPresupuesto(id_proy));
            //System.out.println(listaPres.size());
        }
        
    }
    
    public void listarProyectos() {
        ConexionBD cnxn = new ConexionBD();
        EstimaClienteDAO dao =  new EstimaClienteDAO(cnxn.getConexion());
        setListProy(dao.listarProyecto());
    }
    
    public void seleccionarPresupuesto() {
        setId_pres(pb.getId_presupuesto());
        setPresupto(pb.getPresupuesto());
        calcularPct();
    }
    
    public void calcularPct() {
        CalcularBO calc = new CalcularBO();
        setPorcentaje(calc.calcularPct(getId_pres()));
    }
    
    public void mostrarPresVta() {
        
        //System.out.println("Presupuesto venta");
        ConexionBD c = new ConexionBD();
        EstimaClienteDAO dao = new EstimaClienteDAO(c.getConexion());
        //System.out.println(id_pres);
        setListConc(dao.mostrarConceptos(id_pres));
        
    }
    

    /**
     * @return the id_proy
     */
    public int getId_proy() {
        return id_proy;
    }

    /**
     * @param id_proy the id_proy to set
     */
    public void setId_proy(int id_proy) {
        this.id_proy = id_proy;
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
     * @return the id_pres
     */
    public int getId_pres() {
        return id_pres;
    }

    /**
     * @param id_pres the id_pres to set
     */
    public void setId_pres(int id_pres) {
        this.id_pres = id_pres;
    }

    /**
     * @return the presupto
     */
    public String getPresupto() {
        return presupto;
    }

    /**
     * @param presupto the presupto to set
     */
    public void setPresupto(String presupto) {
        this.presupto = presupto;
    }

    /**
     * @return the listProy
     */
    public List<ProyectoSimple> getListProy() {
        listarProyectos();
        return listProy;
    }

    /**
     * @param listProy the listProy to set
     */
    public void setListProy(List<ProyectoSimple> listProy) {
        this.listProy = listProy;
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
    public List<ConceptoEstim> getListConc() {
        return listConc;
    }

    /**
     * @param listConc the listConc to set
     */
    public void setListConc(List<ConceptoEstim> listConc) {
        this.listConc = listConc;
    }

    /**
     * @return the listConSel
     */
    public List<ConceptoEstim> getListConSel() {
        return listConSel;
    }

    /**
     * @param listConSel the listConSel to set
     */
    public void setListConSel(List<ConceptoEstim> listConSel) {
        this.listConSel = listConSel;
    }

    /**
     * @return the sumaEstima
     */
    public BigDecimal getSumaEstima() {
        return sumaEstima;
    }

    /**
     * @param sumaEstima the sumaEstima to set
     */
    public void setSumaEstima(BigDecimal sumaEstima) {
        this.sumaEstima = sumaEstima;
    }

    /**
     * @return the conceptoFact
     */
    public String getConceptoFact() {
        return conceptoFact;
    }

    /**
     * @param conceptoFact the conceptoFact to set
     */
    public void setConceptoFact(String conceptoFact) {
        this.conceptoFact = conceptoFact;
    }

    /**
     * @return the otrasDeductivas
     */
    public BigDecimal getOtrasDeductivas() {
        return otrasDeductivas;
    }

    /**
     * @param otrasDeductivas the otrasDeductivas to set
     */
    public void setOtrasDeductivas(BigDecimal otrasDeductivas) {
        this.otrasDeductivas = otrasDeductivas;
    }

    /**
     * @return the descDeduct
     */
    public String getDescDeduct() {
        return descDeduct;
    }

    /**
     * @param descDeduct the descDeduct to set
     */
    public void setDescDeduct(String descDeduct) {
        this.descDeduct = descDeduct;
    }

    /**
     * @return the porcentaje
     */
    public float getPorcentaje() {
        return porcentaje;
    }

    /**
     * @param porcentaje the porcentaje to set
     */
    public void setPorcentaje(float porcentaje) {
        this.porcentaje = porcentaje;
    }
    
}
