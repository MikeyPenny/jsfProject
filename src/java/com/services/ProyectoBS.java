/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.model.Proyecto;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mickey
 */
public class ProyectoBS {
    
    Connection con;
    
    public ProyectoBS(Connection con) {
        this.con = con;
    }
    
    public List<Proyecto> listarProyectos(){
        
        List<Proyecto> listProyectos  = new ArrayList<>();
        Proyecto p;
        
        try{
            String sql = "select * from proyecto";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                p = new Proyecto();
                p.setIdProyecto(rs.getInt("id_proyecto"));
                p.setProyecto(rs.getString("proyecto"));
                listProyectos.add(p);
            }
            
            con.close();
            System.out.println("Tamaño lista: " + listProyectos.size());
        }catch(SQLException e){
            e.printStackTrace();
            
        }
        
        
        return listProyectos;
    }
    
}
