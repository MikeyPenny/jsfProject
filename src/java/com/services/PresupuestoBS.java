/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.model.Presupuesto;
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
public class PresupuestoBS {
    
    Connection con;
    
    public PresupuestoBS(Connection con) {
        this.con = con;
    }
    
    public List<Presupuesto> listarPresupuestosPorIDProyecto(int idProyecto){
        
        List<Presupuesto> listPresupuestos = new ArrayList<>();
        Presupuesto pre;
        
        try {
            
            String sql = "select * from presupuesto where id_proyecto="+idProyecto;
            Statement s  = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                pre = new Presupuesto();
                pre.setIdPresupuesto(rs.getInt("id_presupuesto"));
                pre.setPresupuesto(rs.getString("presupuesto"));
                listPresupuestos.add(pre);
            }
            
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return listPresupuestos;
    }
    
}
