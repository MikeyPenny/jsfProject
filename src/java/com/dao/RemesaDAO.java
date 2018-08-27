/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.bean.FacturaBean;
import com.bean.MovimientoBean;
import com.bean.ProyectoSimple;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class RemesaDAO {
    
    Connection con;
    
    public RemesaDAO(Connection con) {
        this.con = con;
    }
    
    public void registrarMovs(List<MovimientoBean> movs) {
        
        try {
            
            String sql = "Insert into movimiento (fechaPago, importe, referencias, id_factura)VALUES (?,?,?,?)";
            
            PreparedStatement ps =  con.prepareStatement(sql);
            
            for(MovimientoBean aux: movs) {
                ps.setString(1, aux.getFechaPago());
                ps.setBigDecimal(2, aux.getImporteMov());
                ps.setString(3, aux.getReferencias());
                ps.setInt(4, aux.getId_factura());
                ps.executeUpdate();
            }
            
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    public void validarSolicitud(List<FacturaBean> validados) {
        
        try {
            
            String sql = "Update factura set estatusFact='VALIDADA' Where id_solicitud=?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            
            for(FacturaBean aux: validados) {
                ps.setInt(1, aux.getId_solicitud());
                ps.executeUpdate();
            }
            
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    public List<MovimientoBean> listarMovimientos(int id_factura) {
        
        List<MovimientoBean> listMovs = new ArrayList<MovimientoBean>();
        MovimientoBean mov;
        
        try {
            
            String sql = "SELECT id_movimiento, fechaPago, importe, referencias, id_factura from movimiento WHERE "
                    + "id_factura="+id_factura;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                mov =  new MovimientoBean();
                mov.setId_movimiento(rs.getInt("id_movimiento"));
                mov.setFechaPago(rs.getString("fechaPago"));
                mov.setImporteMov(rs.getBigDecimal("importe"));
                mov.setReferencias(rs.getString("referencias"));
                mov.setId_factura(rs.getInt("id_factura"));
                listMovs.add(mov);
            }
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return listMovs;
    }
    
    public List<ProyectoSimple> listarProyecto() {
        
        List<ProyectoSimple> listProy = new ArrayList<ProyectoSimple>();
        ProyectoSimple ps;
        
        try {
            
            String sql = "Select id_proyecto, proyecto From proyecto";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                ps = new ProyectoSimple();
                ps.setId_proyecto(rs.getInt("id_proyecto"));
                ps.setProyecto(rs.getString("proyecto"));
                listProy.add(ps);
            }
            
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        
        return listProy;
    }
    
    public List<FacturaBean> filtrarBusqueda(String q1, String q2, String q3, String q4) {
        
        List<FacturaBean> facturas = new ArrayList<FacturaBean>();
        FacturaBean factura;
        int id_fact = 0;
        
        try {
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(q1);
            
            while(rs.next()) {
                factura = new FacturaBean();
                factura.setId_factura(rs.getInt("id_factura"));
                id_fact = rs.getInt("id_factura");
                factura.setNoFactura(rs.getString("noFactura"));
                factura.setImporteFactura(rs.getBigDecimal("importeFactura"));
                factura.setAmortAntcpo(rs.getBigDecimal("antcpoFactura"));
                factura.setContratista(rs.getString("contratista"));
                factura.setOrigenFactura(rs.getString("origenFactura"));
                factura.setTipoFactura(rs.getString("tipoFactura"));
                factura.setIva(rs.getBigDecimal("ivaFactura"));
                factura.setRtnFlete(rs.getBigDecimal("rtnFlete"));
                factura.setRtnRenta(rs.getBigDecimal("rtnRenta"));
                factura.setRtnGarantia(rs.getBigDecimal("rtnGarantia"));
                factura.setImporteTotal(rs.getBigDecimal("totalFactura"));
                factura.setEstatusFact(rs.getString("estatusFact"));
                factura.setFechaFactura(rs.getString("fechaFactura"));
                factura.setId_foraneo(rs.getInt("id_foraneo"));
                factura.setId_solicitud(rs.getInt("id_solicitud"));
                factura.setSumaMovs(sumarMovimientos(id_fact));
                factura.setPasivo(factura.getImporteTotal().subtract(factura.getSumaMovs()));
                facturas.add(factura);
            }
            
            rs = s.executeQuery(q2);
            
            while(rs.next()) {
                factura = new FacturaBean();
                factura.setId_factura(rs.getInt("id_factura"));
                id_fact = rs.getInt("id_factura");
                factura.setNoFactura(rs.getString("noFactura"));
                factura.setImporteFactura(rs.getBigDecimal("importeFactura"));
                factura.setAmortAntcpo(rs.getBigDecimal("antcpoFactura"));
                factura.setContratista(rs.getString("contratista"));
                factura.setOrigenFactura(rs.getString("origenFactura"));
                factura.setTipoFactura(rs.getString("tipoFactura"));
                factura.setIva(rs.getBigDecimal("ivaFactura"));
                factura.setRtnFlete(rs.getBigDecimal("rtnFlete"));
                factura.setRtnRenta(rs.getBigDecimal("rtnRenta"));
                factura.setRtnGarantia(rs.getBigDecimal("rtnGarantia"));
                factura.setImporteTotal(rs.getBigDecimal("totalFactura"));
                factura.setEstatusFact(rs.getString("estatusFact"));
                factura.setFechaFactura(rs.getString("fechaFactura"));
                factura.setId_foraneo(rs.getInt("id_foraneo"));
                factura.setId_solicitud(rs.getInt("id_solicitud"));
                factura.setSumaMovs(sumarMovimientos(id_fact));
                factura.setPasivo(factura.getImporteTotal().subtract(factura.getSumaMovs()));
                facturas.add(factura);
            }
            
            rs = s.executeQuery(q3);
            
            while(rs.next()) {
                factura = new FacturaBean();
                factura.setId_factura(rs.getInt("id_factura"));
                id_fact = rs.getInt("id_factura");
                factura.setNoFactura(rs.getString("noFactura"));
                factura.setImporteFactura(rs.getBigDecimal("importeFactura"));
                factura.setAmortAntcpo(rs.getBigDecimal("antcpoFactura"));
                factura.setContratista(rs.getString("contratista"));
                factura.setOrigenFactura(rs.getString("origenFactura"));
                factura.setTipoFactura(rs.getString("tipoFactura"));
                factura.setIva(rs.getBigDecimal("ivaFactura"));
                factura.setRtnFlete(rs.getBigDecimal("rtnFlete"));
                factura.setRtnRenta(rs.getBigDecimal("rtnRenta"));
                factura.setRtnGarantia(rs.getBigDecimal("rtnGarantia"));
                factura.setImporteTotal(rs.getBigDecimal("totalFactura"));
                factura.setEstatusFact(rs.getString("estatusFact"));
                factura.setFechaFactura(rs.getString("fechaFactura"));
                factura.setId_foraneo(rs.getInt("id_foraneo"));
                factura.setId_solicitud(rs.getInt("id_solicitud"));
                factura.setSumaMovs(sumarMovimientos(id_fact));
                factura.setPasivo(factura.getImporteTotal().subtract(factura.getSumaMovs()));
                facturas.add(factura);
            }
            
            rs = s.executeQuery(q4);
            
            while(rs.next()) {
                factura = new FacturaBean();
                factura.setId_factura(rs.getInt("id_factura"));
                id_fact = rs.getInt("id_factura");
                factura.setNoFactura(rs.getString("noFactura"));
                factura.setImporteFactura(rs.getBigDecimal("importeFactura"));
                factura.setAmortAntcpo(rs.getBigDecimal("antcpoFactura"));
                factura.setContratista(rs.getString("contratista"));
                factura.setOrigenFactura(rs.getString("origenFactura"));
                factura.setTipoFactura(rs.getString("tipoFactura"));
                factura.setIva(rs.getBigDecimal("ivaFactura"));
                factura.setRtnFlete(rs.getBigDecimal("rtnFlete"));
                factura.setRtnRenta(rs.getBigDecimal("rtnRenta"));
                factura.setRtnGarantia(rs.getBigDecimal("rtnGarantia"));
                factura.setImporteTotal(rs.getBigDecimal("totalFactura"));
                factura.setEstatusFact(rs.getString("estatusFact"));
                factura.setFechaFactura(rs.getString("fechaFactura"));
                factura.setId_foraneo(rs.getInt("id_foraneo"));
                factura.setId_solicitud(rs.getInt("id_solicitud"));
                factura.setSumaMovs(sumarMovimientos(id_fact));
                factura.setPasivo(factura.getImporteTotal().subtract(factura.getSumaMovs()));
                facturas.add(factura);
            }
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return facturas;
    }
    
    public BigDecimal sumarMovimientos(int id_fact) {
        BigDecimal sumaMovs =  BigDecimal.ZERO;
        
        try {
            
            String sql = "Select SUM(importe) as sumaMov from movimiento Where id_factura="+id_fact;
            
            Statement s = con.createStatement();
            ResultSet rs =  s.executeQuery(sql);
            
            rs.first();
            sumaMovs = rs.getBigDecimal("sumaMov");
            if(sumaMovs == null)
                sumaMovs = BigDecimal.ZERO;
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return sumaMovs;
    }
    
    public List<FacturaBean> listarFacturas(int id_proy) {
        
        List<FacturaBean> facturas = new ArrayList<FacturaBean>();
        FacturaBean factura;
        int id_fact = 0;
        
        try {
            
            String sql = "SELECT  DISTINCT A.id_factura, A.noFactura, A.importeFactura, A.antcpoFactura, A.contratista, "
                    + "A.origenFactura, A.tipoFactura, A.ivaFactura, " 
                    + "A.rtnFlete, A.rtnRenta, A.rtnGarantia, A.totalFactura, A.estatusFact, A.fechaFactura, "
                    + "A.id_foraneo, A.id_solicitud from factura AS A " 
                    + "inner join avance as B on B.id_avance = A.id_foraneo AND A.origenFactura = 'estima' " 
                    + "inner join avance_insumocontto as C on C.id_avance = B.id_avance " 
                    + "inner join contrato_insumo as D on D.idcontrato_insumo = C.idcontrato_insumo " 
                    + "inner join insumo as E on E.id_insumo = D.id_insumo " 
                    + "inner join concepto as F on F.id_concepto = E.id_concepto " 
                    + "inner join partida as G on G.id_partida = F.id_partida " 
                    + "inner join presupuesto as H on H.id_presupuesto = G.id_presupuesto " 
                    + "inner join proyecto as K on K.id_proyecto="+id_proy;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                factura = new FacturaBean();
                factura.setId_factura(rs.getInt("id_factura"));
                id_fact = rs.getInt("id_factura");
                factura.setNoFactura(rs.getString("noFactura"));
                factura.setImporteFactura(rs.getBigDecimal("importeFactura"));
                factura.setAmortAntcpo(rs.getBigDecimal("antcpoFactura"));
                factura.setContratista(rs.getString("contratista"));
                factura.setOrigenFactura(rs.getString("origenFactura"));
                factura.setTipoFactura(rs.getString("tipoFactura"));
                factura.setIva(rs.getBigDecimal("ivaFactura"));
                factura.setRtnFlete(rs.getBigDecimal("rtnFlete"));
                factura.setRtnRenta(rs.getBigDecimal("rtnRenta"));
                factura.setRtnGarantia(rs.getBigDecimal("rtnGarantia"));
                factura.setImporteTotal(rs.getBigDecimal("totalFactura"));
                factura.setEstatusFact(rs.getString("estatusFact"));
                factura.setFechaFactura(rs.getString("fechaFactura"));
                factura.setId_foraneo(rs.getInt("id_foraneo"));
                factura.setId_solicitud(rs.getInt("id_solicitud"));
                factura.setSumaMovs(sumarMovimientos(id_fact));
                //System.out.println(factura.getImporteFactura().subtract(factura.getSumaMovs()));
                factura.setPasivo(factura.getImporteFactura().subtract(factura.getSumaMovs()));
                facturas.add(factura);
            }
            
            String sql2 = "SELECT  DISTINCT A.id_factura, A.noFactura, A.importeFactura, A.antcpoFactura, A.contratista, "
                    + "A.origenFactura, A.tipoFactura, A.ivaFactura, " 
                    + "A.rtnFlete, A.rtnRenta, A.rtnGarantia, A.totalFactura, A.estatusFact, A.fechaFactura, "
                    + "A.id_foraneo, A.id_solicitud from factura AS A " 
                    + "inner join anticipo as I on I.id_contrato = A.id_foraneo AND A.origenFactura = 'anticipo' " 
                    + "inner join contrato as J on J.id_contrato = I.id_contrato " 
                    + "inner join contrato_insumo as L on L.id_contrato = J.id_contrato " 
                    + "inner join insumo as M on M.id_insumo = L.id_insumo " 
                    + "inner join concepto as N on N.id_concepto = M.id_concepto " 
                    + "inner join partida as O on O.id_partida = N.id_partida " 
                    + "inner join presupuesto as P on P.id_presupuesto = O.id_presupuesto " 
                    + "inner join proyecto as K on K.id_proyecto="+id_proy;
            
            rs = s.executeQuery(sql2);
            
            while(rs.next()) {
                factura = new FacturaBean();
                factura.setId_factura(rs.getInt("id_factura"));
                id_fact = rs.getInt("id_factura");
                factura.setNoFactura(rs.getString("noFactura"));
                factura.setImporteFactura(rs.getBigDecimal("importeFactura"));
                factura.setAmortAntcpo(rs.getBigDecimal("antcpoFactura"));
                factura.setContratista(rs.getString("contratista"));
                factura.setOrigenFactura(rs.getString("origenFactura"));
                factura.setTipoFactura(rs.getString("tipoFactura"));
                factura.setIva(rs.getBigDecimal("ivaFactura"));
                factura.setRtnFlete(rs.getBigDecimal("rtnFlete"));
                factura.setRtnRenta(rs.getBigDecimal("rtnRenta"));
                factura.setRtnGarantia(rs.getBigDecimal("rtnGarantia"));
                factura.setImporteTotal(rs.getBigDecimal("totalFactura"));
                factura.setEstatusFact(rs.getString("estatusFact"));
                factura.setFechaFactura(rs.getString("fechaFactura"));
                factura.setId_foraneo(rs.getInt("id_foraneo"));
                factura.setId_solicitud(rs.getInt("id_solicitud"));
                factura.setSumaMovs(sumarMovimientos(id_fact));
                //System.out.println(factura.getImporteFactura().subtract(factura.getSumaMovs()));
                factura.setPasivo(factura.getImporteFactura().subtract(factura.getSumaMovs()));
                facturas.add(factura);
            }
            
        }catch(SQLException e) {
            e.printStackTrace();
        } 
        
        return facturas;
    }
    
}
