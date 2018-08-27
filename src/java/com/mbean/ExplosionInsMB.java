/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.bean.InsumoExplosion;
import com.bean.PresupuestoBean;
import com.bean.ProyectoSimple;
import com.bean.SubtotalBean;
import com.bo.SubtotalBO;
import com.conexion.ConexionBD;
import com.dao.PresupuestoDAO;
import com.util.FormatoString;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


@ManagedBean(name = "explosion")
@ViewScoped
public class ExplosionInsMB implements Serializable {

    public ExplosionInsMB() {
    }
    
    private String proyecto;
    private int id_proyecto;
    private int id_presupuesto;
    private String presupuesto;
    
    private ArrayList<ProyectoSimple> listaProy = new ArrayList<ProyectoSimple>();
    private ProyectoSimple proySel;
    
    private ArrayList<PresupuestoBean> listaPres = new ArrayList<PresupuestoBean>();
    private PresupuestoBean presB;
    
    private List<InsumoExplosion> expIns = new ArrayList<InsumoExplosion>();
    private InsumoExplosion ins;
    
    private List<SubtotalBean> listSubt = new ArrayList<SubtotalBean>();
    private SubtotalBean subtB;
    
    private List<SubtotalBean> listSubt2 = new ArrayList<SubtotalBean>();
    private SubtotalBean subtB2;
    
    private BigDecimal sumaMaterial;
    private BigDecimal sumaSubcontrato;
    
    private String sumaMatStr;
    private String sumaSubcStr;
    
    public void cargarTablaExplosion() {
        
        ConexionBD conexion = new ConexionBD();
        Connection con = conexion.getConexion();
        PresupuestoDAO pDao = new PresupuestoDAO(con);
        if(pDao.cargarExplosion(id_proyecto, id_presupuesto)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Carga Exitosa", "Se ha cargado la explosión de insumos!!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Revisar", "La carga se realizó previamente!!"));
        }
        
    }
    
    public void mostrarInsumosSubcontratos() {
        SubtotalBO subt = new SubtotalBO();
        setExpIns(subt.listarInsumoSubcontratos(subtB2.getTipo(), id_proyecto, id_presupuesto));
    }
    
    public void mostrarInsumosMateriales() {
        
        SubtotalBO subt = new SubtotalBO();
        setExpIns(subt.buscarInsumosMateriales(subtB.getTipo(), id_proyecto, id_presupuesto));
        
    }
    
    public BigDecimal sumar(String cuenta) {
        
        BigDecimal suma = BigDecimal.ZERO;
        
        
        if(cuenta.equals("MATERIALES")) {
            for(InsumoExplosion aux: expIns) {
                if(aux.getCuenta().equals(cuenta)) {
                    suma = suma.add(aux.getSumaCant());
                }
            }
        } 
        if(cuenta.equals("SUBCONTRATOS")) {
            for(InsumoExplosion aux: expIns) {
                if(aux.getCuenta().equals(cuenta)) {
                    suma = suma.add(aux.getSumaCant());
                }
            }
        }
        if(cuenta.equals("MANO DE OBRA")) {
            for(InsumoExplosion aux: expIns) {
                if(aux.getCuenta().equals(cuenta)) {
                    suma = suma.add(aux.getSumaCant());
                }
            }
        }
        if(cuenta.equals("MAQ Y EQUIPO")) {
            for(InsumoExplosion aux: expIns) {
                if(aux.getCuenta().equals(cuenta)) {
                    suma = suma.add(aux.getSumaCant());
                }
            }
        }
        
        
        return suma;
    }
    
    public void sumarTotales() {
        
        setSumaMaterial(BigDecimal.ZERO);
        for(SubtotalBean aux: getListSubt()) {
            
            sumaMaterial = sumaMaterial.add(aux.getSubtotal()).setScale(2, RoundingMode.HALF_EVEN);
        }
        
        FormatoString format = new FormatoString();
        setSumaMatStr(format.formatoCantidades(sumaMaterial));
        
        setSumaSubcontrato(BigDecimal.ZERO);
        for(SubtotalBean aux: getListSubt2()) {
            sumaSubcontrato = sumaSubcontrato.add(aux.getSubtotal().setScale(2, RoundingMode.HALF_EVEN));
        }
        setSumaSubcStr(format.formatoCantidades(sumaSubcontrato));
        
    }
    
    public void obtenerSubtotales() {
        SubtotalBO sBO = new SubtotalBO();
        setListSubt(sBO.listarMateriales(id_proyecto, id_presupuesto));
        setListSubt2(sBO.listarSubcontratos(id_proyecto, id_presupuesto));
        sumarTotales();
    }
    
    public void listarInsumos() {
        ConexionBD conexion = new ConexionBD();
        Connection con = conexion.getConexion();
        PresupuestoDAO pDao = new PresupuestoDAO(con);
        setExpIns(pDao.explotarInsumos(id_proyecto, id_presupuesto));
    }
    
    public void obtenerPresupuesto() {
        setId_presupuesto(presB.getId_presupuesto());
        setPresupuesto(presB.getPresupuesto());
    }
    
    public void buscarPresupuesto() {
        
        if(proySel != null) {
            Connection con;
            ConexionBD conexion = new ConexionBD();
            con = conexion.getConexion();
            PresupuestoDAO presD = new PresupuestoDAO(con);
            
            setListaPres(presD.listarPresupuesto(proySel.getId_proyecto(), 2));
            //System.out.println(listaPres.size());
        }
        
    }
    
    public void obtenerProyecto() {
        setId_proyecto(proySel.getId_proyecto());
        setProyecto(proySel.getProyecto());
        buscarPresupuesto();
    }
    
    public void buscarProyecto() {
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        PresupuestoDAO presD = new PresupuestoDAO(con);
        setListaProy(presD.listarProyecto());      
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public int getId_proyecto() {
        return id_proyecto;
    }

    public void setId_proyecto(int id_proyecto) {
        this.id_proyecto = id_proyecto;
    }

    public int getId_presupuesto() {
        return id_presupuesto;
    }

    public void setId_presupuesto(int id_presupuesto) {
        this.id_presupuesto = id_presupuesto;
    }

    public String getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(String presupuesto) {
        this.presupuesto = presupuesto;
    }

    public ArrayList<ProyectoSimple> getListaProy() {
        buscarProyecto();
        return listaProy;
    }

    public void setListaProy(ArrayList<ProyectoSimple> listaProy) {
        this.listaProy = listaProy;
    }

    public ProyectoSimple getProySel() {
        return proySel;
    }

    public void setProySel(ProyectoSimple proySel) {
        this.proySel = proySel;
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

    public List<InsumoExplosion> getExpIns() {
        return expIns;
    }

    public void setExpIns(List<InsumoExplosion> expIns) {
        this.expIns = expIns;
    }

    public InsumoExplosion getIns() {
        return ins;
    }

    public void setIns(InsumoExplosion ins) {
        this.ins = ins;
    }

    public List<SubtotalBean> getListSubt() {
        return listSubt;
    }

    public void setListSubt(List<SubtotalBean> listSubt) {
        this.listSubt = listSubt;
    }

    public SubtotalBean getSubtB() {
        return subtB;
    }

    public void setSubtB(SubtotalBean subtB) {
        this.subtB = subtB;
    }

    public List<SubtotalBean> getListSubt2() {
        return listSubt2;
    }

    public void setListSubt2(List<SubtotalBean> listSubt2) {
        this.listSubt2 = listSubt2;
    }

    public SubtotalBean getSubtB2() {
        return subtB2;
    }

    public void setSubtB2(SubtotalBean subtB2) {
        this.subtB2 = subtB2;
    }

    public BigDecimal getSumaMaterial() {
        return sumaMaterial;
    }

    public void setSumaMaterial(BigDecimal sumaMaterial) {
        this.sumaMaterial = sumaMaterial;
    }

    public BigDecimal getSumaSubcontrato() {
        return sumaSubcontrato;
    }

    public void setSumaSubcontrato(BigDecimal sumaSubcontrato) {
        this.sumaSubcontrato = sumaSubcontrato;
    }

    public String getSumaMatStr() {
        return sumaMatStr;
    }

    public void setSumaMatStr(String sumaMatStr) {
        this.sumaMatStr = sumaMatStr;
    }

    public String getSumaSubcStr() {
        return sumaSubcStr;
    }

    public void setSumaSubcStr(String sumaSubcStr) {
        this.sumaSubcStr = sumaSubcStr;
    }
}
