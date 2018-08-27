/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.bean.MaterialBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MaterialDAO {
    
    Connection con;
    
    public MaterialDAO(Connection con) {
        this.con = con;
    }
    
    public boolean buscarClaveDuplicada(String cve) {
        
        boolean dup;
        
        try {
            
            String sql = "select id_material from catmaterial where clave = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cve);
            ResultSet rs = ps.executeQuery();
            dup = rs.next();
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            dup = false;
        }
        
        return dup;
        
    }
    
    public int eliminaMaterial(int id) {
        
        int del;
        
        try {
            
            String sql = "delete from catmaterial where id_material=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            
            del = 1;
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            del = -1;
        }
        
        return del;
        
    }
    
    public int editarMaterial(MaterialBean mat) {
        
        int ed;
        
        try {
            
            String sql = "update catmaterial set clave=?, descripcion=?, unidad=? where id_material=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, mat.getClaveMat());
            ps.setString(2, mat.getMaterial());
            ps.setString(3, mat.getUnidad());
            ps.setInt(4, mat.getId_material());
            ps.executeUpdate();
            
            ed = 1;
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            ed = -1;
        }
        
        return ed;
        
    }
    
    public List<MaterialBean> listarCatMaterial() {
        
        List<MaterialBean> list = new ArrayList<>();
        MaterialBean mat;
        
        try {
            
            String sql = "select * from catmaterial";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                
                mat = new MaterialBean();
                mat.setId_material(rs.getInt("id_material"));
                mat.setClaveMat(rs.getString("clave"));
                mat.setMaterial(rs.getString("descripcion"));
                mat.setUnidad(rs.getString("unidad"));
                list.add(mat);
                
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            
        }
        
        return list;
        
    }
    
    public int agregMaterial(MaterialBean mat) {
        
        int reg;
        
        try {
            
            String sql = "insert into catmaterial(clave, descripcion, unidad) values(?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, mat.getClaveMat());
            ps.setString(2, mat.getMaterial());
            ps.setString(3, mat.getUnidad());
            ps.executeUpdate();
            
            reg = 1;
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            reg = -1;
        }
        
        return reg;
        
    }
    
}
