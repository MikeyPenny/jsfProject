
package com.dao;

import com.bean.Comprobante;
import com.bean.FacturaBean;
import com.bean.PagoBean;
import com.bean.PresupuestoBean;
import com.bean.ProyectoSimple;
import com.bo.CalculosBO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class FacturaDAO {
    
    Connection con;
    
    public boolean validarUID(Comprobante cfdi) {
        
        boolean valid = false;
        
        try {
            
            String sql = "select id_factura from factura where uuid = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cfdi.getComplemento().getTimbre().getUuid());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                valid = true;
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            valid = true;
        }
         
        return valid;
        
    }
    
    public FacturaDAO(Connection con) {
        this.con = con;
    }
    
    public void cancelarFactura(int id_factura) {
        
        try {
            
            String sql = "Update factura set estatusFact='CANCELADA' WHERE id_factura="+id_factura;
            
            Statement s = con.createStatement();
            s.executeUpdate(sql);
            
            
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    public void cancelarFacturaYActualizarAvance(int id_factura, int id_foreign) {
        
        try {
            
            con.setAutoCommit(false);
            
            String sql = "Update factura set estatusFact='CANCELADA' WHERE id_factura="+id_factura;
            
            Statement s = con.createStatement();
            s.executeUpdate(sql);
            
            String sql2 = "Update avance set estatusAvance='ESTIMADO' WHERE id_avance="+id_foreign;
            s.executeUpdate(sql2);
            
            con.commit();
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
            
            try {
                con.rollback();
            }catch(SQLException e1) {
                e1.printStackTrace();
            }
        }
        
    }
    
    public void cancelarFacturaYActualizarAnticipo(int id_factura, int id_foreign) {
        
        try {
            
            con.setAutoCommit(false);
            
            String sql = "Update factura set estatusFact='CANCELADA' WHERE id_factura="+id_factura;
            
            Statement s = con.createStatement();
            s.executeUpdate(sql);
            
            String sql2 = "Update anticipo set estatusAntcpo='ESTIMADO' WHERE id_contrato="+id_foreign;
            s.executeUpdate(sql2);
            
            con.commit();
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
            
            try {
                con.rollback();
            }catch(SQLException e1) {
                e1.printStackTrace();
            }
        }
        
    }
    
    public boolean buscarFacturaXAvance(int id, String origen) {
        
        boolean existe = false;
        
        try {
            
            String sql = "Select id_factura From factura WHERE id_foraneo="+id+" AND origenFactura='"+origen+"' AND estatusFact!='CANCELADA'";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            if(rs.first()) {
                existe = true;
            }
        
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return existe;
    }
    
    public List<FacturaBean> consultarFacturas(int id_proy, int id_pres) {
        
        List<FacturaBean> listaFact = new ArrayList<>();
        FacturaBean fb;
        
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
                    + "inner join proyecto as K on K.id_proyecto="+id_proy+" and H.id_presupuesto="+id_pres;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                fb = new FacturaBean();
                fb.setId_factura(rs.getInt("id_factura"));
                fb.setNoFactura(rs.getString("noFactura"));
                fb.setImporteFactura(rs.getBigDecimal("importeFactura"));
                fb.setAmortAntcpoFactura(rs.getBigDecimal("antcpoFactura"));
                fb.setContratista(rs.getString("contratista"));
                fb.setOrigenFactura(rs.getString("origenFactura"));
                fb.setTipoFactura(rs.getString("tipoFactura"));
                fb.setIvaFactura(rs.getBigDecimal("ivaFactura"));
                fb.setRetFlete(rs.getBigDecimal("rtnFlete"));
                fb.setRetRenta(rs.getBigDecimal("rtnRenta"));
                fb.setRetFdoGtia(rs.getBigDecimal("rtnGarantia"));
                fb.setImporteTotal(rs.getBigDecimal("totalFactura"));
                fb.setEstatusFact(rs.getString("estatusFact"));
                fb.setFechaFactura(rs.getString("fechaFactura"));
                fb.setId_foraneo(rs.getInt("id_foraneo"));
                fb.setId_solicitud(rs.getInt("id_solicitud"));
                listaFact.add(fb);
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
                    + "inner join proyecto as K on K.id_proyecto="+id_proy+" and P.id_presupuesto="+id_pres;
            
            rs = s.executeQuery(sql2);
            
            while(rs.next()) {
                fb = new FacturaBean();
                fb.setId_factura(rs.getInt("id_factura"));
                fb.setNoFactura(rs.getString("noFactura"));
                fb.setImporteFactura(rs.getBigDecimal("importeFactura"));
                fb.setAmortAntcpoFactura(rs.getBigDecimal("antcpoFactura"));
                fb.setContratista(rs.getString("contratista"));
                fb.setOrigenFactura(rs.getString("origenFactura"));
                fb.setTipoFactura(rs.getString("tipoFactura"));
                fb.setIvaFactura(rs.getBigDecimal("ivaFactura"));
                fb.setRetFlete(rs.getBigDecimal("rtnFlete"));
                fb.setRetRenta(rs.getBigDecimal("rtnRenta"));
                fb.setRetFdoGtia(rs.getBigDecimal("rtnGarantia"));
                fb.setImporteTotal(rs.getBigDecimal("totalFactura"));
                fb.setEstatusFact(rs.getString("estatusFact"));
                fb.setFechaFactura(rs.getString("fechaFactura"));
                fb.setId_foraneo(rs.getInt("id_foraneo"));
                fb.setId_solicitud(rs.getInt("id_solicitud"));
                listaFact.add(fb);
            }
            
            String sql3 = "select fac.id_factura, fac.noFactura, fac.importeFactura, fac.antcpoFactura, fac.contratista, "
                    + "fac.origenFactura, fac.tipoFactura, fac.ivaFactura, fac.rtnFlete, " 
                    + "fac.rtnRenta, fac.rtnGarantia, fac.totalFactura, fac.estatusFact, fac.fechaFactura, "
                    + "fac.id_foraneo, fac.id_solicitud from factura as fac " 
                    + "inner join orden_compra as oc on oc.idorden_compra = fac.id_foraneo and fac.origenFactura = 'antOC' " 
                    + "inner join requisicion as req on req.id_requisicion = oc.id_requisicion " 
                    + "inner join presupuesto as pre on pre.id_presupuesto = req.id_presupuesto " 
                    + "inner join proyecto as pro on pro.id_proyecto ="+id_proy+" and pre.id_presupuesto ="+id_pres;
            
            rs = s.executeQuery(sql3);
            
            while(rs.next()) {
                fb = new FacturaBean();
                fb.setId_factura(rs.getInt("id_factura"));
                fb.setNoFactura(rs.getString("noFactura"));
                fb.setImporteFactura(rs.getBigDecimal("importeFactura"));
                fb.setAmortAntcpoFactura(rs.getBigDecimal("antcpoFactura"));
                fb.setContratista(rs.getString("contratista"));
                fb.setOrigenFactura(rs.getString("origenFactura"));
                fb.setTipoFactura(rs.getString("tipoFactura"));
                fb.setIvaFactura(rs.getBigDecimal("ivaFactura"));
                fb.setRetFlete(rs.getBigDecimal("rtnFlete"));
                fb.setRetRenta(rs.getBigDecimal("rtnRenta"));
                fb.setRetFdoGtia(rs.getBigDecimal("rtnGarantia"));
                fb.setImporteTotal(rs.getBigDecimal("totalFactura"));
                fb.setEstatusFact(rs.getString("estatusFact"));
                fb.setFechaFactura(rs.getString("fechaFactura"));
                fb.setId_foraneo(rs.getInt("id_foraneo"));
                fb.setId_solicitud(rs.getInt("id_solicitud"));
                listaFact.add(fb);
            }
            
            String sql4 = "select fac.id_factura, fac.noFactura, fac.importeFactura, fac.antcpoFactura, fac.contratista, "
                    + "fac.origenFactura, fac.tipoFactura, fac.ivaFactura, fac.rtnFlete, " 
                    + "fac.rtnRenta, fac.rtnGarantia, fac.totalFactura, fac.estatusFact, fac.fechaFactura, "
                    + "fac.id_foraneo, fac.id_solicitud from factura as fac " 
                    + "inner join orden_compra as oc on oc.idorden_compra = fac.id_foraneo and fac.origenFactura = 'antOC' " 
                    + "inner join requisicion as req on req.id_requisicion = oc.id_requisicion " 
                    + "inner join presupuesto as pre on pre.id_presupuesto = req.id_presupuesto " 
                    + "inner join proyecto as pro on pro.id_proyecto ="+id_proy+" and pre.id_presupuesto ="+id_pres;
            
            rs = s.executeQuery(sql4);
            
            while(rs.next()) {
                fb = new FacturaBean();
                fb.setId_factura(rs.getInt("id_factura"));
                fb.setNoFactura(rs.getString("noFactura"));
                fb.setImporteFactura(rs.getBigDecimal("importeFactura"));
                fb.setAmortAntcpoFactura(rs.getBigDecimal("antcpoFactura"));
                fb.setContratista(rs.getString("contratista"));
                fb.setOrigenFactura(rs.getString("origenFactura"));
                fb.setTipoFactura(rs.getString("tipoFactura"));
                fb.setIvaFactura(rs.getBigDecimal("ivaFactura"));
                fb.setRetFlete(rs.getBigDecimal("rtnFlete"));
                fb.setRetRenta(rs.getBigDecimal("rtnRenta"));
                fb.setRetFdoGtia(rs.getBigDecimal("rtnGarantia"));
                fb.setImporteTotal(rs.getBigDecimal("totalFactura"));
                fb.setEstatusFact(rs.getString("estatusFact"));
                fb.setFechaFactura(rs.getString("fechaFactura"));
                fb.setId_foraneo(rs.getInt("id_foraneo"));
                fb.setId_solicitud(rs.getInt("id_solicitud"));
                listaFact.add(fb);
            }
            
            String sql5 = "select fac.id_factura, fac.noFactura, fac.importeFactura, fac.antcpoFactura, fac.contratista, "
                    + "fac.origenFactura, fac.tipoFactura, fac.ivaFactura, fac.rtnFlete, " 
                    + "fac.rtnRenta, fac.rtnGarantia, fac.totalFactura, fac.estatusFact, fac.fechaFactura, "
                    + "fac.id_foraneo, fac.id_solicitud from factura as fac " 
                    + "inner join orden_compra as oc on oc.idorden_compra = fac.id_foraneo and fac.origenFactura = 'compra' " 
                    + "inner join requisicion as req on req.id_requisicion = oc.id_requisicion " 
                    + "inner join presupuesto as pre on pre.id_presupuesto = req.id_presupuesto " 
                    + "inner join proyecto as pro on pro.id_proyecto ="+id_proy+" and pre.id_presupuesto ="+id_pres;
            
            rs = s.executeQuery(sql5);
            
            while(rs.next()) {
                fb = new FacturaBean();
                fb.setId_factura(rs.getInt("id_factura"));
                fb.setNoFactura(rs.getString("noFactura"));
                fb.setImporteFactura(rs.getBigDecimal("importeFactura"));
                fb.setAmortAntcpoFactura(rs.getBigDecimal("antcpoFactura"));
                fb.setContratista(rs.getString("contratista"));
                fb.setOrigenFactura(rs.getString("origenFactura"));
                fb.setTipoFactura(rs.getString("tipoFactura"));
                fb.setIvaFactura(rs.getBigDecimal("ivaFactura"));
                fb.setRetFlete(rs.getBigDecimal("rtnFlete"));
                fb.setRetRenta(rs.getBigDecimal("rtnRenta"));
                fb.setRetFdoGtia(rs.getBigDecimal("rtnGarantia"));
                fb.setImporteTotal(rs.getBigDecimal("totalFactura"));
                fb.setEstatusFact(rs.getString("estatusFact"));
                fb.setFechaFactura(rs.getString("fechaFactura"));
                fb.setId_foraneo(rs.getInt("id_foraneo"));
                fb.setId_solicitud(rs.getInt("id_solicitud"));
                listaFact.add(fb);
            }
            
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        System.out.println(listaFact.size());
        
        return listaFact;
        
    }
    
    public int registrarFacturaAnticipo(PagoBean pago) {
        //ACtiualizar estatus anticipo a facturado y escribir factura
        int id_sol = 0;
        
        try {
            
            con.setAutoCommit(false);
            
            String sql1 = "Select MAX(id_solicitud) as solicitud From factura for update";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql1);
            
            rs.first();
            id_sol = rs.getInt("solicitud");
            
            if(!rs.first()) {
                id_sol = 0;
            }
            
            id_sol = id_sol+1;
            
            //System.out.println(id_sol);
            
            String sql = "Update anticipo set estatusAntcpo=? where id_contrato=?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "FACTURADO");
            ps.setInt(2, pago.getId_pago());
            ps.executeUpdate();
            
            
            String sql2 = "INSERT INTO factura (noFactura, importeFactura, antcpoFactura, contratista, origenFactura, "
                    + "tipoFactura, ivaFactura, rtnFlete, rtnRenta, rtnGarantia, totalFactura, fechaFactura, id_foraneo, id_solicitud)VALUES "
                    + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
            ps = con.prepareStatement(sql2);
            
            ps.setString(1, pago.getNoFactura());
            ps.setBigDecimal(2, pago.getImporteEstimacion());
            ps.setBigDecimal(3, pago.getAnticipoAmort());
            ps.setString(4, pago.getContratista());
            ps.setString(5, pago.getOrigen());
            ps.setString(6, pago.getTipo());
            ps.setBigDecimal(7, pago.getIva());
            ps.setBigDecimal(8, pago.getRetFlete());
            ps.setBigDecimal(9, pago.getRetRenta());
            ps.setBigDecimal(10, pago.getRetFdoGtia());
            ps.setBigDecimal(11, pago.getImporteTotal());
            ps.setString(12, pago.getFecha());
            ps.setInt(13, pago.getId_pago());
            ps.setInt(14, id_sol);
            ps.executeUpdate();
            
            con.commit();
            
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
            
            try {
                
                con.rollback();
                
            }catch(SQLException e1) {
                e1.printStackTrace();
            }
            
        }
        
        return id_sol;
        
    }
    
    public int resgistrarFacturaEstimacion(PagoBean pago) {
        //Actualizar estatus avance a facturado y escribir factura
        int id_sol = 0;
        
        try {
            
            con.setAutoCommit(false);
            
            String sql1 = "Select MAX(id_solicitud) as solicitud From factura for update";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql1);
            
            rs.first();
            id_sol = rs.getInt("solicitud");
            
            if(!rs.first()) {
                id_sol = 0;
            }
            
            id_sol = id_sol+1;
            
            String sql = "Update avance set estatusAvance=? where id_avance=?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "FACTURADO");
            ps.setInt(2, pago.getId_pago());
            ps.executeUpdate();
            
            
            String sql2 = "INSERT INTO factura (noFactura, importeFactura, antcpoFactura, contratista, origenFactura, "
                    + "tipoFactura, ivaFactura, rtnFlete, rtnRenta, rtnGarantia, totalFactura, fechaFactura, id_foraneo, id_solicitud)VALUES "
                    + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
            ps = con.prepareStatement(sql2);
            
            ps.setString(1, pago.getNoFactura());
            ps.setBigDecimal(2, pago.getImporteEstimacion());
            ps.setBigDecimal(3, pago.getAnticipoAmort());
            ps.setString(4, pago.getContratista());
            ps.setString(5, pago.getOrigen());
            ps.setString(6, pago.getTipo());
            ps.setBigDecimal(7, pago.getIva());
            ps.setBigDecimal(8, pago.getRetFlete());
            ps.setBigDecimal(9, pago.getRetRenta());
            ps.setBigDecimal(10, pago.getRetFdoGtia());
            ps.setBigDecimal(11, pago.getImporteTotal());
            ps.setString(12, pago.getFecha());
            ps.setInt(13, pago.getId_pago());
            ps.setInt(14, id_sol);
            ps.executeUpdate();
            
            con.commit();
            
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
            
            try {
                
                con.rollback();
                
            }catch(SQLException e1) {
                e1.printStackTrace();
            }
            
        }
        
        return id_sol;
        
    }
    
    public int resgistrarFacturaOrdenCompra(PagoBean pago) {
        //Actualizar estatus orden a facturado y escribir factura
        int id_sol = 0;
        
        try {
            
            con.setAutoCommit(false);
            
            String sql1 = "Select MAX(id_solicitud) as solicitud From factura for update";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql1);
            
            rs.first();
            id_sol = rs.getInt("solicitud");
            
            if(!rs.first()) {
                id_sol = 0;
            }
            
            id_sol = id_sol+1;
            
            
            
            
            String sql2 = "INSERT INTO factura (noFactura, importeFactura, antcpoFactura, contratista, origenFactura, "
                    + "tipoFactura, ivaFactura, rtnFlete, rtnRenta, rtnGarantia, totalFactura, fechaFactura, id_foraneo, id_solicitud)VALUES "
                    + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement ps = con.prepareStatement(sql2);
            
            ps.setString(1, pago.getNoFactura());
            ps.setBigDecimal(2, pago.getImporteEstimacion());
            ps.setBigDecimal(3, pago.getAnticipoAmort());
            ps.setString(4, pago.getContratista());
            ps.setString(5, pago.getOrigen());
            ps.setString(6, pago.getTipo());
            ps.setBigDecimal(7, pago.getIva());
            ps.setBigDecimal(8, pago.getRetFlete());
            ps.setBigDecimal(9, pago.getRetRenta());
            ps.setBigDecimal(10, pago.getRetFdoGtia());
            ps.setBigDecimal(11, pago.getImporteTotal());
            ps.setString(12, pago.getFecha());
            ps.setInt(13, pago.getId_pago());
            ps.setInt(14, id_sol);
            ps.executeUpdate();
            
            String sql3 = "update orden_compra set estatus='FACTURADO' where idorden_compra="+pago.getId_pago();
            ps = con.prepareStatement(sql3);
            ps.executeUpdate();
            
            String sql4 = "update anticipo set estatusAntcpo='FACTURADO' where id_contrato="+pago.getId_pago();
            ps = con.prepareStatement(sql4);
            ps.executeUpdate();
            
            con.commit();
            
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
            
            try {
                
                con.rollback();
                
            }catch(SQLException e1) {
                e1.printStackTrace();
            }
            
        }
        
        return id_sol;
    }
    
    public boolean validarFactura(String factura, String contratista) {
        
        boolean existe = false;
        
        try {
            
            String sql =  "Select id_factura FROM factura WHERE noFactura='"+factura+"' AND contratista='"+contratista+"'";
            
            Statement s = con.createStatement();
            ResultSet rs =  s.executeQuery(sql);
            
            if(rs.first())
                existe = true;
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return existe;
    }
    
    public List<PagoBean> listarOrdCompra(int id_proy, int id_pres) {
        //Mostrar todas las ordenes de compra excepto canceladas y pendientes x autorizar
        List<PagoBean> list = new ArrayList<PagoBean>();
        PagoBean pb;
        CalculosBO calc = new CalculosBO();
        
        try {
            
            String sql = "SELECT DISTINCT A.id_contrato, A.importe, A.contratista, A.iva, " +
                        "A.total, A.pctAnt FROM anticipo AS A " +
                        "INNER JOIN orden_compra AS B ON B.idorden_compra = A.id_contrato " +
                        "INNER JOIN requisicion AS C ON C.id_requisicion = B.id_requisicion " +
                        "INNER JOIN presupuesto AS G ON G.id_presupuesto = C.id_presupuesto " +
                        "INNER JOIN proyecto AS H ON H.id_proyecto="+id_proy+" AND G.id_presupuesto="+id_pres+
                        " where A.tipo = 'antOC' and estatusAntcpo = 'PENDIENTE'";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            next:
            while(rs.next()) {
                pb = new PagoBean();
                pb.setId_pago(rs.getInt("id_contrato"));
                
                pb.setImporteEstimacion(rs.getBigDecimal("importe"));
                pb.setContratista(rs.getString("contratista"));
                pb.setTipo("Anticipo Orden Compra");
                pb.setIva(rs.getBigDecimal("iva"));
                pb.setImporteTotal(rs.getBigDecimal("total"));
                pb.setPctAmort(rs.getFloat("pctAnt"));
                pb.setRetFdoGtia(BigDecimal.ZERO);
                pb.setRetFlete(BigDecimal.ZERO);
                pb.setRetRenta(BigDecimal.ZERO);
                /*if(comprobarPagos(pb.getId_pago())) {
                    continue next;
                }*/
                //pb.setFacturado(obtenerTotalFacturado(pb.getId_pago()));
                list.add(pb);
            }
            
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    public float obtenerPctAmort(int id) {
        float pct = .00f;
        
        try {
            
            String sql = "select pctAnt from anticipo where id_contrato ="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            pct = rs.getFloat("pctAnt");
            
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return pct;
    }
    
    public boolean comprobarPagos(int id) {
        boolean pago = false;
        BigDecimal facturado = BigDecimal.ZERO;
        
        try {
            String sql = "select sum(importeFactura) as facturado from factura where origenFactura='anticipo' "
                    + "and id_foraneo="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            facturado = rs.getBigDecimal("facturado");
            if(facturado == null || facturado.doubleValue() == 0) {
                pago = true;
            }
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return pago;
    }
    
    public List<PagoBean> listarOrdenesCompra(int id_pres) {
    
        List<PagoBean> listPagos = new ArrayList<PagoBean>();
        PagoBean pago;
        CalculosBO calc = new CalculosBO();
        
        try {
            
            String sql = "SELECT A.id_requisicion, B.idorden_Compra, B.idProveedor, B.iva FROM requisicion AS A, "
                    + "orden_compra AS B WHERE B.id_requisicion = A.id_requisicion AND (B.estatus !='CANCELADO' "
                    + "OR B.estatus !='PENDIENTE POR AUTORIZAR') AND A.id_presupuesto ="+id_pres;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                
                pago = new PagoBean();
                pago.setId_pago(rs.getInt("idorden_compra"));
                int id_req = rs.getInt("id_requisicion");
                int id_prov = rs.getInt("idProveedor");
                pago.setImporteEstimacion(obtenerImporteCompras(id_req));
                pago.setContratista(obtenerProveedor(id_prov));
                pago.setTipo("compra");
                pago.setIva(calc.calcularIva(rs.getInt("iva"), pago.getImporteEstimacion()));
                pago.setImporteTotal(calc.sumarTotal(pago));
                //obtener lo que se ha facturado
                pago.setFacturado(obtenerTotalFacturado(pago.getId_pago()));
                //System.out.println(pago.getId_pago()+" :"+pago.getFacturado());
                listPagos.add(pago);
            }
            
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return listPagos;
    }
    
    public BigDecimal facturadoEstimacion(int id) {
        
        BigDecimal facturado =  BigDecimal.ZERO;
        
        try {
            
            String sql = "select sum(importeFactura) as facturado from factura where origenFactura='estima' "
                    + "and id_foraneo="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            facturado = rs.getBigDecimal("facturado");
            
            if(facturado == null) {
                facturado = BigDecimal.ZERO;
            }
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return facturado;
    }
    
     public BigDecimal obtenerTotalFacturado(int id) {
        
        BigDecimal facturado = BigDecimal.ZERO;
        
        try {
            
            String sql = "select sum(importeFactura) as facturado from factura where (origenFactura='compra' "
                    + "OR origenFactura='antOC') and id_foraneo="+id;
            //System.out.println(origen+" "+id);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            facturado = rs.getBigDecimal("facturado");
            
            if(facturado == null) {
                facturado = BigDecimal.ZERO;
            }
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        
        return facturado;
        
    }
    
    public String obtenerProveedor(int id_prov) {
        
        String proveedor = "";
        
        try {
            
            String sql = "Select razonSocial From proveedor WHERE idProveedor="+id_prov;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            rs.first();
            proveedor = rs.getString("razonSocial");
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return proveedor;
    }
    
    public BigDecimal obtenerImporteCompras(int id_requis) {
        
        BigDecimal importe = BigDecimal.ZERO;
        
        try {
            
            String sql = "Select cantidad, preciounitario FROM insumo_requisicion WHERE id_requisicion="+id_requis;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                BigDecimal aux = rs.getBigDecimal("cantidad").multiply(rs.getBigDecimal("preciounitario"));
                importe = importe.add(aux);
            }
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return importe;
        
    }
    
    public List<PagoBean> listarAvancesEstimados(int id_proy, int id_pres) {
        
        List<PagoBean> listPagos = new ArrayList<PagoBean>();
        PagoBean pb;
        String[] contrtstaTipo = new String[2];
        float[] pcts = new float[2];
        
        try {
            
            String sql = "SELECT DISTINCT A.id_avance, A.importeEstimacion, A.anticipoAmort, A.retFdoGtia, "
                    + "A.subtotal, A.iva, A.retFlete, A.retRenta,A.importeTotal, A.nroEstimacion, A.fecha FROM avance AS A " +
                         "INNER JOIN avance_insumocontto AS B ON B.id_avance = A.id_avance " +
                         "AND A.estatusAvance = 'ESTIMADO' OR A.estatusAvance = 'FACTURADO' " +
                         "INNER JOIN contrato_insumo AS C ON C.idcontrato_insumo = B.idcontrato_insumo " +
                         "INNER JOIN insumo AS D ON D.id_insumo = C.id_insumo " +
                         "INNER JOIN concepto AS E ON E.id_concepto = D.id_concepto " +
                         "INNER JOIN partida AS F ON F.id_partida = E.id_partida " +
                         "INNER JOIN presupuesto AS G ON G.id_presupuesto = F.id_presupuesto " +
                         "INNER JOIN proyecto AS H ON H.id_proyecto = ? AND G.id_presupuesto = ? ORDER BY A.id_avance;";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id_proy);
            ps.setInt(2, id_pres);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                pb = new PagoBean();
                int avn = rs.getInt("id_avance");
                pb.setId_pago(rs.getInt("id_avance"));
                pb.setImporteEstimacion(rs.getBigDecimal("importeEstimacion"));
                pb.setAnticipoAmort(rs.getBigDecimal("anticipoAmort"));
                pb.setRetFdoGtia(rs.getBigDecimal("retFdoGtia"));
                pb.setSubtotal(rs.getBigDecimal("subtotal"));
                pb.setIva(rs.getBigDecimal("iva"));
                pb.setRetFlete(rs.getBigDecimal("retFlete"));
                pb.setRetRenta(rs.getBigDecimal("retRenta"));
                pb.setImporteTotal(rs.getBigDecimal("importeTotal"));
                pb.setNroEstimacion(rs.getInt("nroEstimacion"));
                pb.setFecha(rs.getString("fecha"));
                contrtstaTipo = consultarContratista(avn);
                pb.setTipo(contrtstaTipo[0]);
                pb.setContratista(contrtstaTipo[1]);
                pcts = consultarPctGarantia(avn);
                pb.setFondoGtiaPct(pcts[0]);
                pb.setPctAmort(pcts[1]);
                pb.setFacturado(facturadoEstimacion(pb.getId_pago()));
                //obtener totales
                listPagos.add(pb);
            }
            
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        
        return listPagos;
    }
    
    public float[] consultarPctGarantia(int id_av) {
        
        float[] pct = new float[2];
        
        try {
            
            String sql = "SELECT DISTINCT A.fondoGtiaPct, A.anticipoPct FROM contrato AS A " +
                         "INNER JOIN contrato_insumo AS B ON B.id_contrato = A.id_contrato " +
                         "INNER JOIN avance_insumocontto AS C ON C.idcontrato_insumo = B.idcontrato_insumo " +
                         "INNER JOIN avance AS D ON D.id_avance = C.id_avance " +
                         "AND D.id_avance ="+id_av; 
            
           Statement s = con.createStatement();
           ResultSet rs = s.executeQuery(sql);
            
           rs.first();
           pct[0] = rs.getFloat("fondoGtiaPct");
           pct[1] = rs.getFloat("anticipoPct");
           
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return pct;
    }
    
    
    public String[] consultarContratista(int id_av) {
        
        String[] contrtstaTipo = new String[2];
        
        try {
            
            String sql = "SELECT DISTINCT A.tipo, A.contratista FROM contrato AS A " +
                         "INNER JOIN contrato_insumo AS B ON B.id_contrato = A.id_contrato " +
                         "INNER JOIN avance_insumocontto AS C ON C.idcontrato_insumo = B.idcontrato_insumo " +
                         "INNER JOIN avance AS D ON D.id_avance = C.id_avance " +
                         "AND D.id_avance ="+id_av;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            rs.first();
            contrtstaTipo[0] = rs.getString("tipo");
            contrtstaTipo[1] = rs.getString("contratista");
            
            
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return contrtstaTipo;
    }
    
    public List<PagoBean> listarAnticipos(int id_proy, int id_pres) {
        
        List<PagoBean> listPagos = new ArrayList<>();
        PagoBean pb;
        
        try{
            
            String sql = "SELECT DISTINCT A.id_contrato, A.importe, A.contratista, A.tipo, A.iva, A.rtnRenta, A.rtnFlete, "
                    + "A.total FROM anticipo AS A " +
                    "INNER JOIN contrato AS B ON B.id_contrato = A.id_contrato " +
                    "INNER JOIN contrato_insumo AS C ON C.id_contrato = B.id_contrato " +
                    "INNER JOIN insumo AS D ON D.id_insumo = C.id_insumo " +
                    "INNER JOIN concepto AS E ON E.id_concepto = D.id_concepto " +
                    "INNER JOIN partida AS F ON F.id_partida = E.id_partida " +
                    "INNER JOIN presupuesto AS G ON G.id_presupuesto = F.id_presupuesto " +
                    "INNER JOIN proyecto AS H ON H.id_proyecto="+id_proy+ " AND G.id_presupuesto="+id_pres;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            
            while(rs.next()) {
                pb = new PagoBean();
                pb.setId_pago(rs.getInt("id_contrato"));
                pb.setImporteEstimacion(rs.getBigDecimal("importe"));
                pb.setContratista(rs.getString("contratista"));
                pb.setTipo(rs.getString("tipo"));
                pb.setIva(rs.getBigDecimal("iva"));
                pb.setRetRenta(rs.getBigDecimal("rtnRenta"));
                if(pb.getRetRenta() == null)
                    pb.setRetRenta(BigDecimal.ZERO);
                pb.setRetFlete(rs.getBigDecimal("rtnFlete"));
                if(pb.getRetFlete() == null)
                    pb.setRetFlete(BigDecimal.ZERO);
                pb.setImporteTotal(rs.getBigDecimal("total"));
                pb.setAnticipoAmort(BigDecimal.ZERO);
                pb.setRetFdoGtia(BigDecimal.ZERO);
                pb.setFacturado(obtenerFacturadoAnt(pb.getId_pago()));
                listPagos.add(pb);
            }
            
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return listPagos;
    }
    
    public BigDecimal obtenerFacturadoAnt(int id) {
        BigDecimal facturado = BigDecimal.ZERO;
        
        try {
            
            String sql = "select sum(importeFactura) as facturado from factura where origenFactura='anticipo' "
                    + "and id_foraneo="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            facturado = rs.getBigDecimal("facturado");
            if(facturado == null) 
                facturado = BigDecimal.ZERO;
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return facturado;
    }
    
    public BigDecimal[] sumarAcumulados(int id_pago, String origen) {
        BigDecimal[] importeAcum = new BigDecimal[3];
        
        try {
                
            String sql = "Select SUM(DISTINCT importeFactura) AS suma, SUM(DISTINCT antcpoFactura) as anticipo, "
                    + "SUM(DISTINCT rtnGarantia) as garantia FROM factura WHERE tipoFactura='"+origen+"' AND id_foraneo="+id_pago+" AND estatusFact!='CANCELADA'";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            importeAcum[0] = rs.getBigDecimal("suma");
            importeAcum[1] = rs.getBigDecimal("anticipo");
            importeAcum[2] = rs.getBigDecimal("garantia");
            if(importeAcum[0] == null) {
                importeAcum[0] = BigDecimal.ZERO;
                importeAcum[1] = BigDecimal.ZERO;
                importeAcum[2] = BigDecimal.ZERO;
            }
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return importeAcum;
    }
    
    public ArrayList<PresupuestoBean> listarPresupuesto(int id_proyecto) {
        
        PresupuestoBean pb;
        ArrayList<PresupuestoBean> listaPres = new ArrayList<PresupuestoBean>();
        
        try {
            
            String sql = "Select id_presupuesto, presupuesto from presupuesto WHERE id_proyecto="+id_proyecto;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                pb = new PresupuestoBean();
                pb.setId_presupuesto(rs.getInt("id_presupuesto"));
                pb.setPresupuesto(rs.getString("presupuesto"));
                listaPres.add(pb);
            }
            
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return listaPres;
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
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        
        return listProy;
    }
    
}
