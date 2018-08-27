/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.bean.ConceptoEstim;
import com.bean.EstimacionCteBean;
import com.bean.PresupuestoBean;

import com.bean.ProyectoSimple;
import com.util.Cadenas;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class EstimaClienteDAO {
    
    Connection con;
    
    public EstimaClienteDAO(Connection con) {
        this.con = con;
    }
    
    
    public int guardarEstimacionCte(EstimacionCteBean estima, List<ConceptoEstim> list) {
        
        int reg = 0;
        
        try {
            
            con.setAutoCommit(false);
            
            String sql = "insert into estimacion(fecha, otrasDeductivas, descDeduct, conceptoEstim, id_cliente, id_presupuesto)values (?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, estima.getFecha());
            ps.setBigDecimal(2, estima.getOtrasDeductivas());
            ps.setString(3, estima.getDescDeduct());
            ps.setString(4, estima.getConceptoEstim());
            ps.setInt(5, estima.getId_cliente());
            ps.setInt(6, estima.getId_presupuesto());
            ps.executeUpdate();
            
            String sql2 = "select last_insert_id()";
            ps = con.prepareStatement(sql2);
            ResultSet rs = ps.executeQuery();
            rs.first();
            int id_est = rs.getInt(1);
            
            String sql3 = "insert into estima_concepto(precio, cantidad, importe, id_concepto, id_estimacion)values (?,?,?,?,?)";
            ps = con.prepareStatement(sql3);
            
            for(ConceptoEstim aux: list) {
                ps.setBigDecimal(1, aux.getPresUnitario());
                ps.setBigDecimal(2, aux.getCantEstimada());
                ps.setBigDecimal(3, aux.getImporte());
                ps.setInt(4, aux.getId_concepto());
                ps.setInt(5, id_est);
                ps.executeUpdate();
            }
            
            reg = 1;
            
            con.commit();
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            reg = -1;
            try {
                con.rollback();
            }catch(SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        return reg;
    
    }
    
    
    
    public int buscarIdCliente(int id_proy, int id_pres) {
        
        int id = 0;
        
        try {
            
            String sql = "select id_cliente from cliente_proyecto cp, proyecto as pro, presupuesto as pre "
                    + "where pre.id_presupuesto="+id_pres
                    +" and pro.id_proyecto="+id_proy+" and cp.id_proyecto = pro.id_proyecto";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                id = rs.getInt("id_cliente");
            }
            
            
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return id;
    }
    
    public List<ProyectoSimple> listarProyecto() {
        
        List<ProyectoSimple> listProy = new ArrayList<>();
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
    
    public List<ConceptoEstim> mostrarConceptos(int id_pres) {
        
        List<ConceptoEstim> list = new ArrayList<>();
        ConceptoEstim c;
        Cadenas cad = new Cadenas();
        
        try {
            
            String sql = "select con.id_concepto, con.numConcepto, con.codConcepto, con.descripcion, con.unidad, con.cantCtrl, "
                    + "con.pUnitario, con.importe from concepto as con, partida as part, presupuesto as pres where "
                    + "con.id_partida = part.id_partida and part.id_presupuesto =  pres.id_presupuesto and pres.id_presupuesto ="+id_pres;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                c = new ConceptoEstim();
                c.setId_concepto(rs.getInt("id_concepto"));
                int id = c.getId_concepto();
                c.setNumConcepto(rs.getString("numConcepto"));
                c.setCodConcepto(rs.getString("codConcepto"));
                c.setDescripcion(cad.resumirDescripcion(rs.getString("descripcion")));
                c.setUnidad(rs.getString("unidad"));
                c.setCantidad(rs.getBigDecimal("cantCtrl"));
                c.setPresUnitario(rs.getBigDecimal("pUnitario"));
                c.setSumaEstimado(obtenerEstimados(id));
                c.setPorEstimar(c.getCantidad().subtract(c.getSumaEstimado()));
                c.setImporte(c.getPresUnitario().multiply(c.getPorEstimar()));
                list.add(c);
            }
            
            
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
        
    }
    
    public BigDecimal obtenerEstimados(int id) {
        
        BigDecimal suma = BigDecimal.ZERO;
        
        try {
            
            String sql = "select sum(cantidad) as suma from estima_concepto where id_concepto="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                suma = rs.getBigDecimal("suma");
            }
            
            if(suma == null) {
                suma = BigDecimal.ZERO;
            }
            
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return suma;
        
    }
    
}
