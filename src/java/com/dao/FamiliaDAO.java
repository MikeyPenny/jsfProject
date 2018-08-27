/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.bean.FamiliaMat;
import com.bean.ProyectoBean;
import com.bean.TipoInsumo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class FamiliaDAO {
    
    Connection con;
    
    public FamiliaDAO(Connection con) {
        this.con = con;
    }
    
    
    
    public int editarFamilia(FamiliaMat fam) {
        
        int reg;
        
        try {
            
            String sql = "update famconcepto set subfamilia=?, claveFam=? where id_famConcepto=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, fam.getFamilia());
            ps.setString(2, fam.getClave());
            ps.setInt(3, fam.getId_familia());
            ps.executeUpdate();
            
            reg = 1;
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            reg = -1;
        }
        
        return reg;
        
    }
    
    public void eliminarFamilia(int id) {
        
        try {
            
            String sql = "delete from famconcepto where id_famConcepto = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public List<FamiliaMat> listarFamPorPoryecto(int id) {
        
        List<FamiliaMat> list = new ArrayList<>();
        FamiliaMat fam;
        
        try {
            
            String sql = "select * from famconcepto where id_proyecto = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                fam = new FamiliaMat();
                fam.setId_familia(rs.getInt("id_famConcepto"));
                fam.setFamilia(rs.getString("subfamilia"));
                fam.setClave(rs.getString("claveFam"));
                fam.setId_proyecto(rs.getInt("id_proyecto"));
                list.add(fam);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
        
    }
    
    public int registrarFamilia(FamiliaMat fam) {
        
        int reg;
        
        try {
            
            String sql = "insert into famconcepto(subfamilia, claveFam, id_proyecto) values(?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, fam.getFamilia());
            ps.setString(2, fam.getClave());
            ps.setInt(3, fam.getId_proyecto());
            ps.executeUpdate();
            
            reg = 1;
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            reg = -1;
        }
        
        
        return reg;
    
    }
    
    public List<TipoInsumo> leerTiposInsumo() {
        
        List<TipoInsumo> list = new ArrayList<>();
        TipoInsumo typ;
        
        
        try {
            
            String sql = "select * from tipo";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                typ = new TipoInsumo();
                typ.setId_tipo(rs.getInt("id_tipo"));
                typ.setTipoStr(rs.getString("nombre"));
                list.add(typ);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
        
    }
    
    public List<ProyectoBean> leerProyectos() {
        
        List<ProyectoBean>  list = new ArrayList<>();
        ProyectoBean pb;
        
        try {
            
            String sql = "select * from proyecto";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                pb = new ProyectoBean();
                pb.setId_proyecto(rs.getInt("id_proyecto"));
                pb.setProyecto(rs.getString("proyecto"));
                list.add(pb);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
        
    }
    
}
