/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.bean.Cliente;
import com.bean.ConceptoEstim;
import com.bean.EstimacionCteBean;
import com.bean.ImportesBean;
import com.bean.MovimientoCteBean;
import com.bean.PresupuestoBean;
import com.bean.ProyectoCteEst;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class FacturaClienteDAO {
    
    Connection con;
    
    public FacturaClienteDAO(Connection con) {
        this.con = con;
    }
    
    public int aplicarMovimiento(MovimientoCteBean mov) {
        
        int upd = 0;
        boolean ok = true;
        int id_est = 0;
        
        try {
            
            con.setAutoCommit(false);
            
            String sql = "update factura_cliente set estatusFact=?, fechaMov=? where id_factura=?";
            System.out.println(sql);
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "Pagada");
            ps.setString(2, mov.getFechaMov());
            ps.setInt(3, mov.getId_factura());
            ps.executeUpdate();
            
            Statement s = con.createStatement();
            
            
            sql = "select id_estimacion from factura_cliente where id_factura="+mov.getId_factura();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                id_est = rs.getInt("id_estimacion");
            }
            
            sql = "select estatusFact from factura_cliente where id_estimacion="+id_est;
            rs = s.executeQuery(sql);
            List<String> list = new ArrayList<>();
            while(rs.next()) {
                list.add(rs.getString("estatusFact"));
            }
            
            for(String aux: list) {
                if(aux.equals("PagoPendiente")) {
                    ok = false;
                    break;
                }
            }
            
            if(ok) {
                sql = "update estimacion set estatusEst=5 where id_estimacion="+id_est;
                s = con.createStatement();
                s.executeUpdate(sql);
            }
            
            upd = 1;
            con.commit();
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            upd = -1;
            
            try {
                con.rollback();
            }catch(SQLException ex) {
                System.out.println(ex.getMessage());
            }
            
        }
        
        return upd;
        
    }
    
    public List<MovimientoCteBean> listarMovimientosPorFactura(int id_fact) {
        
        List<MovimientoCteBean> list = new ArrayList<>();
        MovimientoCteBean mov;
        
        try {
            String sql = "select * from movimiento_cliente where id_factura="+id_fact;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                mov = new MovimientoCteBean();
                mov.setId_movimiento(rs.getInt("id_movimiento"));
                mov.setImporte(rs.getBigDecimal("importe"));
                mov.setFechaMov(rs.getString("fechaMov"));
                mov.setId_factura(id_fact);
                list.add(mov);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
        
    }
    
    public BigDecimal sumarMovsFactura(int id_fact) {
        
        BigDecimal suma = BigDecimal.ZERO;
        
        try {
            String sql = "select sum(importe) as suma from movimiento_cliente where id_factura="+id_fact;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                suma = rs.getBigDecimal("suma");
            }
            
            if(suma == null) {
                suma = BigDecimal.ZERO;
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return suma;
        
    }
    
    public List<ImportesBean> listarFacturasPorEstimacion(int id_est) {
        
        List<ImportesBean> list = new ArrayList<>();
        ImportesBean fact;
        
        try {
            String sql = "select * from factura_cliente where estatusFact='PagoPendiente' and id_estimacion="+id_est;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                fact = new ImportesBean();
                fact.setId_factura(rs.getInt("id_factura"));
                fact.setNoFactura(rs.getString("noFactura"));
                fact.setFechaFact(rs.getString("fechaFact"));
                fact.setFechaMov(rs.getString("fechaMov"));
                fact.setImporteEst(rs.getBigDecimal("importeEst"));
                fact.setAmortAntcpo(rs.getBigDecimal("amortAntcpo"));
                fact.setRetGtia(rs.getBigDecimal("retGtia"));
                fact.setOtrasDed(rs.getBigDecimal("otrasDed"));
                fact.setSubtotal(rs.getBigDecimal("subtotal"));
                fact.setGtiDesIVA(rs.getBigDecimal("gtiaDespIVA"));
                fact.setIva(rs.getBigDecimal("iva"));
                fact.setTotal(rs.getBigDecimal("total"));
                fact.setPctIva(rs.getFloat("pctIva"));
                fact.setId_estimacion(rs.getInt("id_estimacion"));
                list.add(fact);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
        
    }
    
    public BigDecimal sumarDeductivasporFactura(int id_est) {
        
        BigDecimal suma = BigDecimal.ZERO;
        
        try {
            
            String sql = "select sum(otrasDed) as suma from factura_cliente where id_estimacion="+id_est;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                suma = rs.getBigDecimal("suma");
            }
            
            if(suma == null) {
                suma = BigDecimal.ZERO;
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return suma;
        
    }
    
    public int verificarFactura(String fact, int id_cliente) {
        
        int check = 0;
        
        try {
            
            String sql = "select noFactura from factura_cliente as fc "
                    + "inner join estimacion as est on est.id_estimacion = fc.id_estimacion "
                    + "and est.id_cliente="+id_cliente+" and fc.noFactura=\'"+fact+"\'";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            if(rs.first())
                check = 1;
            
            //System.out.println(sql);
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        //System.out.println("El check: "+check);
        return check;
        
    }
            
    
    public int registrarFactura(ImportesBean factura) {
        
        int registro = 0;
        
        try {
            
            con.setAutoCommit(false);
            
            String sql = "insert into factura_cliente(noFactura, fechaFact, fechaMov, importeEst, amortAntcpo, retGtia, "
                    + "otrasDed, subtotal, gtiaDespIVA, iva, total, pctIva, id_estimacion)values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, factura.getNoFactura());
            ps.setString(2, factura.getFechaFact());
            ps.setString(3, factura.getFechaMov());
            ps.setBigDecimal(4, factura.getImporteEst());
            ps.setBigDecimal(5, factura.getAmortAntcpo());
            ps.setBigDecimal(6, factura.getRetGtia());
            ps.setBigDecimal(7, factura.getOtrasDed());
            ps.setBigDecimal(8, factura.getSubtotal());
            ps.setBigDecimal(9, factura.getGtiDesIVA());
            ps.setBigDecimal(10, factura.getIva());
            ps.setBigDecimal(11, factura.getTotal());
            ps.setFloat(12, factura.getPctIva());
            ps.setInt(13, factura.getId_estimacion());
            ps.executeUpdate();
            
            String sql2 = "update estimacion set estatusEst="+factura.getEstatus()+" where id_estimacion="+factura.getId_estimacion();
            ps = con.prepareStatement(sql2);
            ps.executeUpdate();
            
            registro = 1;
            
            con.commit();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            registro = -1;
            try {
                con.rollback();
            }catch(SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        return registro;
        
    }
    
    public List<ProyectoCteEst> listarProyecto() {
        
        List<ProyectoCteEst> listProy = new ArrayList<>();
        ProyectoCteEst ps;
        
        try {
            
            String sql = "Select id_proyecto, proyecto, cliente, centroCostos, pctAnt, pctGarantia From proyecto";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                ps = new ProyectoCteEst();
                ps.setId_proyecto(rs.getInt("id_proyecto"));
                ps.setProyecto(rs.getString("proyecto"));
                ps.setCliente(rs.getString("cliente"));
                ps.setCentCosto(rs.getString("centroCostos"));
                ps.setPctAntcpo(rs.getFloat("pctAnt"));
                ps.setPctAntcpo(rs.getFloat("pctGarantia"));
                listProy.add(ps);
            }
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
        return listProy;
    }
    
    public ArrayList<PresupuestoBean> listarPresupuesto(int id_proyecto) {
        
        PresupuestoBean pb;
        ArrayList<PresupuestoBean> listaPres = new ArrayList<>();
        
        try {
            
            String sql = "Select id_presupuesto, presupuesto from presupuesto WHERE id_proyecto="+id_proyecto+
                    " and tipo="+1;
            
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
            System.out.println(e.getMessage());
        }
        
        return listaPres;
    }
    
    public int buscarIdCliente(int id_proy, int id_pres) {
    
        int id = 0;
        
        try {
            String sql = "select id_cliente from cliente_proyecto cp, proyecto as pro where pro.id_presupuesto=? " 
                    + "and pro.id_proyecto=6 and cp.id_proyecto = pro.id_proyecto";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id_proy);
            ps.setInt(2, id_pres);
            ResultSet rs = ps.executeQuery();
            rs.first();
            id = rs.getInt(1);
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return id;
    
    }
    
    public List<ConceptoEstim> listarEstima(int[] ids) {
        
        List<ConceptoEstim> list = new ArrayList<>();
        ConceptoEstim conc;
        
        try {
            
            String sql = "select est.id_concepto con.descripcion, est.precio, est.cantidad, est.importe from estima_concepto as est " 
                    +"inner join concepto as con on con.id_concepto = est.id_concepto " 
                    +"inner join partida as part on part.id_partida = con.id_partida " 
                    +"inner join presupuesto as pre on pre.id_presupuesto = part.id_presupuesto " 
                    +"inner join proyecto as pro on pro.id_proyecto = pre.id_proyecto " 
                    +"inner join cliente_proyecto as cte on cte.id_proyecto = pro.id_proyecto " 
                    +"and pro.id_proyecto = ? and pre.id_presupuesto = ? and  cte.id_cliente = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, ids[0]);
            ps.setInt(1, ids[1]);
            ps.setInt(1, ids[2]);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                conc = new ConceptoEstim();
                conc.setId_concepto(rs.getInt("id_concepto"));
                conc.setDescripcion(rs.getString("descripcion"));
                conc.setPresUnitario(rs.getBigDecimal("precio"));
                conc.setCantidad(rs.getBigDecimal("cantidad"));
                conc.setImporte(rs.getBigDecimal("importe"));
                list.add(conc);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
        
    }
    
    public List<EstimacionCteBean> listEstimaciones(int id_pres) {
        
        List<EstimacionCteBean> list = new ArrayList<>();
        EstimacionCteBean est;
        
        try {
            
            String sql  = "select id_estimacion, fecha, otrasDeductivas, descDeduct, conceptoEstim,  id_cliente from estimacion where id_presupuesto="+id_pres
                    +" and estatusEst=1 or estatusEst=2";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                est =  new EstimacionCteBean();
                est.setId_estimacion(rs.getInt("id_estimacion"));
                est.setFecha(rs.getString("fecha"));
                est.setOtrasDeductivas(rs.getBigDecimal("otrasDeductivas"));
                if(est.getOtrasDeductivas() == null) {
                    est.setOtrasDeductivas(BigDecimal.ZERO);
                }
                est.setDescDeduct(rs.getString("descDeduct"));
                est.setConceptoEstim(rs.getString("conceptoEstim"));
                est.setId_cliente(rs.getInt("id_cliente"));
                est.setImporte(sumarEstimacion(est.getId_estimacion()));
                list.add(est);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
        
    }
    
    public List<EstimacionCteBean> listEstimacionesFacturadas(int id_pres) {
        
        List<EstimacionCteBean> list = new ArrayList<>();
        EstimacionCteBean est;
        
        try {
            
            String sql  = "select id_estimacion, fecha, otrasDeductivas, descDeduct, conceptoEstim,  id_cliente from estimacion where id_presupuesto="+id_pres
                    +" and estatusEst=2 or estatusEst=3";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                est =  new EstimacionCteBean();
                est.setId_estimacion(rs.getInt("id_estimacion"));
                est.setFecha(rs.getString("fecha"));
                est.setOtrasDeductivas(rs.getBigDecimal("otrasDeductivas"));
                if(est.getOtrasDeductivas() == null) {
                    est.setOtrasDeductivas(BigDecimal.ZERO);
                }
                est.setDescDeduct(rs.getString("descDeduct"));
                est.setConceptoEstim(rs.getString("conceptoEstim"));
                est.setId_cliente(rs.getInt("id_cliente"));
                est.setImporte(sumarEstimacion(est.getId_estimacion()));
                list.add(est);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
        
    }
    
    public BigDecimal sumarPres(int id_pres) {
        BigDecimal suma = BigDecimal.ZERO;
        
        try {
            
            String sql = "select sum(con.importe) as suma from concepto as con, partida as part, presupuesto as pres where "
                    + "con.id_partida = part.id_partida and part.id_presupuesto =  pres.id_presupuesto "
                    + "and pres.id_presupuesto ="+id_pres;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                suma = rs.getBigDecimal("suma");
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return suma;
    }
    
    public BigDecimal sumarFacturado(int id_est) {
    
        BigDecimal suma = BigDecimal.ZERO;
        
        try {
            
            String sql = "select sum(importeEst) as suma from factura_cliente where id_estimacion="+id_est;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                suma = rs.getBigDecimal("suma");
            }
            
            if(suma == null) {
                suma = BigDecimal.ZERO;
            }
            
            System.out.println("id "+id_est);
            System.out.println(suma);
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return suma;
    }
    
    public BigDecimal sumarEstAlCliente(int id_pres) {
        
        BigDecimal sumaEst = BigDecimal.ZERO;
        
        try {
            String sql = "select sum(ec.importe) as sumaEst from estima_concepto as ec, estimacion as est, presupuesto as pres "
                    + "where ec.id_estimacion = est.id_estimacion and est.id_presupuesto = pres.id_presupuesto "
                    + "and pres.id_presupuesto="+id_pres;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                sumaEst = rs.getBigDecimal("sumaEst");
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return sumaEst;
    }
    
    public BigDecimal sumarEstimacion(int id_est) {
        
        BigDecimal suma = BigDecimal.ZERO;
        
        try {
            
            String sql = "select sum(importe) as suma from estima_concepto where id_estimacion="+id_est;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            suma = rs.getBigDecimal(1);
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return suma;
        
    }
    
    public Cliente buscarClienteXId(int id_cte) {
        
        Cliente cte = new Cliente();
        
        try {
            String sql = "select razonSocial from cliente where id_cliente="+id_cte;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                cte.setRazon(rs.getString("razonSocial"));
                
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return cte;
        
    }
    
    public ProyectoCteEst buscarProyectoPorId(int id) {
        
        ProyectoCteEst proy = new ProyectoCteEst();
        
        try {
            String sql = "select centroCostos, pctGarantia, pctAnt from proyecto where id_proyecto="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                proy.setCentCosto(rs.getString("centroCostos"));
                proy.setPctGarantia(rs.getFloat("pctGarantia"));
                proy.setPctAntcpo(rs.getFloat("pctAnt"));
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
        return proy;
        
    }
    
}
