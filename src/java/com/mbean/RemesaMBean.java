/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.bean.FacturaBean;
import com.bean.MovimientoBean;
import com.bean.ProyectoSimple;
import com.bean.SelectBean;
import com.bo.CreaQuery;
import com.conexion.ConexionBD;
import com.dao.RemesaDAO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;


@ManagedBean(name = "remesas")
@ViewScoped
public class RemesaMBean implements Serializable {

    private int id_proyecto;
    private String proyecto;
    
    private List<FacturaBean> facturas = new ArrayList<FacturaBean>();
    private FacturaBean factura;
    
    private List<FacturaBean> facturasFiltro = new ArrayList<FacturaBean>();
    
    private List<ProyectoSimple> listProy = new ArrayList<ProyectoSimple>();
    private ProyectoSimple ps;
    
    //Filtros
    private String beneficiario;
    private String fechaDe;
    private String fechaA;
    private String centroCosto;
    private String facturaSol;
    private String estatusFactura;
    private String cuenta;
    private int id_solicitud;
    
    //Lista moviemientos y clase moviemiento
    private List<MovimientoBean> listMovs = new ArrayList<MovimientoBean>();
    private MovimientoBean mov;
    
    //Movimientos nuevos
    private List<MovimientoBean> listAgreg = new ArrayList<MovimientoBean>();
    private MovimientoBean nuevoMov;
    
    public RemesaMBean() {
    }
    
    public void registrarMovimiento() {
        //System.out.println(listAgreg.size());
        Connection con;
        ConexionBD c = new ConexionBD();
        con = c.getConexion();
        RemesaDAO rem = new RemesaDAO(con);
        rem.registrarMovs(listAgreg);
        crearQuery();
    }
    
    public void agregarMovimiento() {
        nuevoMov = new MovimientoBean();
        SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String fechaActual = sd.format(date);
        nuevoMov.setFechaPago(fechaActual);
        nuevoMov.setImporteMov(factura.getImpAutorizado());
        nuevoMov.setReferencias(factura.getReferencias());
        nuevoMov.setId_factura(factura.getId_factura());
        listAgreg.add(nuevoMov);
        
    }
    
    public void validarCantidad(CellEditEvent event) {
        
        int row = event.getRowIndex();
        FacturaBean factB = facturas.get(row);
        
        if(factB.getImpAutorizado().floatValue()> factB.getPasivo().floatValue()) {
            factB.setImpAutorizado((BigDecimal)event.getOldValue());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "La suma de los movimientos es mayor al importe de la factura."));
        }
        
        
    }
    
    public void validarSolicitud() {
        //System.out.println(facturasFiltro.size());
        Connection con;
        ConexionBD c = new ConexionBD();
        con = c.getConexion();
        RemesaDAO rem = new RemesaDAO(con);
        rem.validarSolicitud(facturasFiltro);
        listarFactura();
    }
    
    public void mostrarDetalle() {
        int id_factura = factura.getId_factura();
        Connection con;
        ConexionBD c = new ConexionBD();
        con = c.getConexion();
        RemesaDAO rem = new RemesaDAO(con);
        setListMovs(rem.listarMovimientos(id_factura));
    }
    
    public void crearQuery() {
        
        SelectBean select = new SelectBean();
        select.setId_proyecto(id_proyecto);
        select.setBeneficiario(beneficiario);
        select.setFechaDe(fechaDe);
        select.setFechaA(fechaA);
        select.setCentroCosto(centroCosto);
        select.setFactura(facturaSol);
        select.setEstatusFactura(estatusFactura);
        select.setCuenta(cuenta);
        select.setId_solpago(id_solicitud);
        CreaQuery q = new CreaQuery();
        setFacturas(q.crearSelect(select));
        
    }
    
    public void listarFactura() {
        
        Connection con;
        ConexionBD c = new ConexionBD();
        con = c.getConexion();
        RemesaDAO rem = new RemesaDAO(con);
        setFacturas(rem.listarFacturas(id_proyecto));
        //System.out.println(facturas.size());
    }
    
    public void obtenerProyecto() {
        setId_proyecto(ps.getId_proyecto());
        setProyecto(ps.getProyecto());
        //listarFactura();
    }
    
    public void consultarProyecto() {
        Connection con;
        ConexionBD c = new ConexionBD();
        con = c.getConexion();
        RemesaDAO rem = new RemesaDAO(con);
        setListProy(rem.listarProyecto());
    }
    
    public void consultarFactura() {
        Connection con;
        ConexionBD c = new ConexionBD();
        con = c.getConexion();
        RemesaDAO rem = new RemesaDAO(con);
        setFacturas(rem.listarFacturas(id_proyecto));
    }

    public List<FacturaBean> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<FacturaBean> facturas) {
        this.facturas = facturas;
    }

    public FacturaBean getFactura() {
        return factura;
    }

    public void setFactura(FacturaBean factura) {
        this.factura = factura;
    }

    public int getId_proyecto() {
        return id_proyecto;
    }

    public void setId_proyecto(int id_proyecto) {
        this.id_proyecto = id_proyecto;
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

    public List<FacturaBean> getFacturasFiltro() {
        return facturasFiltro;
    }

    public void setFacturasFiltro(List<FacturaBean> facturasFiltro) {
        this.facturasFiltro = facturasFiltro;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public String getFechaDe() {
        return fechaDe;
    }

    public void setFechaDe(String fechaDe) {
        this.fechaDe = fechaDe;
    }

    public String getFechaA() {
        return fechaA;
    }

    public void setFechaA(String fechaA) {
        this.fechaA = fechaA;
    }

    public String getCentroCosto() {
        return centroCosto;
    }

    public void setCentroCosto(String centroCosto) {
        this.centroCosto = centroCosto;
    }

    public String getFacturaSol() {
        return facturaSol;
    }

    public void setFacturaSol(String facturaSol) {
        this.facturaSol = facturaSol;
    }

    public String getEstatusFactura() {
        return estatusFactura;
    }

    public void setEstatusFactura(String estatusFactura) {
        this.estatusFactura = estatusFactura;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public int getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(int id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public List<MovimientoBean> getListMovs() {
        return listMovs;
    }

    public void setListMovs(List<MovimientoBean> listMovs) {
        this.listMovs = listMovs;
    }

    public MovimientoBean getMov() {
        return mov;
    }

    public void setMov(MovimientoBean mov) {
        this.mov = mov;
    }

    public List<MovimientoBean> getListAgreg() {
        return listAgreg;
    }

    public void setListAgreg(List<MovimientoBean> listAgreg) {
        this.listAgreg = listAgreg;
    }

    public MovimientoBean getNuevoMov() {
        return nuevoMov;
    }

    public void setNuevoMov(MovimientoBean nuevoMov) {
        this.nuevoMov = nuevoMov;
    }
    
}
