
package com.mbean;

import com.bean.FacturaBean;
import com.bean.PresupuestoBean;
import com.bean.ProyectoSimple;
import com.conexion.ConexionBD;
import com.dao.FacturaDAO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean(name = "tablaFact")
@ViewScoped
public class ListaFacturasMBean implements Serializable {

    public ListaFacturasMBean() {
    }
    
    private int id_proyecto;
    private String proyecto;
    private int id_presupuesto;
    private String presupuesto;
    
    private List<ProyectoSimple> listProy = new ArrayList<ProyectoSimple>();
    private ProyectoSimple ps;
    
    private ArrayList<PresupuestoBean> listaPres = new ArrayList<PresupuestoBean>();
    private PresupuestoBean presB;
    
    private int id_factura;
    private String noFactura;
    private BigDecimal importeFactura;
    private BigDecimal amortAntcpoFactura;
    private String contratista;
    private String origenFactura;
    private String tipoFactura;
    private BigDecimal ivaFactura;
    private BigDecimal retFlete;
    private BigDecimal retRenta;
    private BigDecimal retFdoGtia;
    private BigDecimal importeTotal;
    private String estatusFact;
    private String fechaFactura;
    private int id_foraneo;
    
    private List<FacturaBean> listaFact = new ArrayList<FacturaBean>();
    private FacturaBean fb;
    
    
    
    public void eliminarFactura() {
        
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        FacturaDAO fd = new FacturaDAO(con);
        fd.cancelarFactura(id_factura);
        boolean hayFacturas = fd.buscarFacturaXAvance(id_foraneo, origenFactura);
        
        if(!hayFacturas) {
            if(origenFactura.equals("anticipo"))
                fd.cancelarFacturaYActualizarAnticipo(id_factura, id_foraneo);
            if(origenFactura.equals("estima"))
                fd.cancelarFacturaYActualizarAvance(id_factura, id_foraneo);
        }
        
        
    }
    
    
    
    public void seleccionarFactura() {
    
        setId_factura(fb.getId_factura());
        setNoFactura(fb.getNoFactura());
        setImporteFactura(fb.getImporteFactura().setScale(2, RoundingMode.UP));
        setAmortAntcpoFactura(fb.getAmortAntcpoFactura().setScale(2, RoundingMode.UP));
        setContratista(fb.getContratista());
        setOrigenFactura(fb.getOrigenFactura());
        setTipoFactura(fb.getTipoFactura());
        setIvaFactura(fb.getIvaFactura().setScale(2, RoundingMode.UP));
        setRetFlete(fb.getRetFlete().setScale(2, RoundingMode.UP));
        setRetRenta(fb.getRetRenta().setScale(2, RoundingMode.UP));
        setRetFdoGtia(fb.getRetFdoGtia().setScale(2, RoundingMode.UP));
        setImporteTotal(fb.getImporteTotal().setScale(2, RoundingMode.UP));
        setEstatusFact(fb.getEstatusFact());
        setFechaFactura(fb.getFechaFactura());
        setId_foraneo(fb.getId_foraneo());
        
    }
    
    public void listarFacturas() {
        
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        FacturaDAO fd = new FacturaDAO(con);
        setListaFact(fd.consultarFacturas(id_proyecto, id_presupuesto));
    }
    
    public void obtenerPresupuesto() {
        setId_presupuesto(presB.getId_presupuesto());
        setPresupuesto(presB.getPresupuesto());    
    }
    
    public void buscarPresupuesto() {
        
        if(ps != null) {
            Connection con;
            ConexionBD conexion = new ConexionBD();
            con = conexion.getConexion();
            FacturaDAO fd = new FacturaDAO(con);
            
            setListaPres(fd.listarPresupuesto(ps.getId_proyecto()));
            //System.out.println(listaPres.size());
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
        FacturaDAO fd = new FacturaDAO(con);
        setListProy(fd.listarProyecto());
    }

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public String getNoFactura() {
        return noFactura;
    }

    public void setNoFactura(String noFactura) {
        this.noFactura = noFactura;
    }

    public BigDecimal getImporteFactura() {
        return importeFactura;
    }

    public void setImporteFactura(BigDecimal importeFactura) {
        this.importeFactura = importeFactura;
    }

    public BigDecimal getAmortAntcpoFactura() {
        return amortAntcpoFactura;
    }

    public void setAmortAntcpoFactura(BigDecimal amortAntcpoFactura) {
        this.amortAntcpoFactura = amortAntcpoFactura;
    }

    public String getContratista() {
        return contratista;
    }

    public void setContratista(String contratista) {
        this.contratista = contratista;
    }

    public String getOrigenFactura() {
        return origenFactura;
    }

    public void setOrigenFactura(String origenFactura) {
        this.origenFactura = origenFactura;
    }

    public String getTipoFactura() {
        return tipoFactura;
    }

    public void setTipoFactura(String tipoFactura) {
        this.tipoFactura = tipoFactura;
    }

    public BigDecimal getIvaFactura() {
        return ivaFactura;
    }

    public void setIvaFactura(BigDecimal ivaFactura) {
        this.ivaFactura = ivaFactura;
    }

    public BigDecimal getRetFlete() {
        return retFlete;
    }

    public void setRetFlete(BigDecimal retFlete) {
        this.retFlete = retFlete;
    }

    public BigDecimal getRetRenta() {
        return retRenta;
    }

    public void setRetRenta(BigDecimal retRenta) {
        this.retRenta = retRenta;
    }

    public BigDecimal getRetFdoGtia() {
        return retFdoGtia;
    }

    public void setRetFdoGtia(BigDecimal retFdoGtia) {
        this.retFdoGtia = retFdoGtia;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public String getEstatusFact() {
        return estatusFact;
    }

    public void setEstatusFact(String estatusFact) {
        this.estatusFact = estatusFact;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public int getId_foraneo() {
        return id_foraneo;
    }

    public void setId_foraneo(int id_foraneo) {
        this.id_foraneo = id_foraneo;
    }

    public List<FacturaBean> getListaFact() {
        return listaFact;
    }

    public void setListaFact(List<FacturaBean> listaFact) {
        this.listaFact = listaFact;
    }

    public FacturaBean getFb() {
        return fb;
    }

    public void setFb(FacturaBean fb) {
        this.fb = fb;
    }

    public int getId_proyecto() {
        return id_proyecto;
    }

    public void setId_proyecto(int id_proyecto) {
        this.id_proyecto = id_proyecto;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
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
    
}
