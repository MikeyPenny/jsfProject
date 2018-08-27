/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Mickey
 */
public class DireccionProyecto {
    
    Connection con;
    
    public DireccionProyecto() {
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/erp_v2p0", "appl_erp", "erp2015!");
            
        }catch(SQLException|ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public String obtenerDireccion(int id_proy) {
        
        String direccion ="";
        
        try {
            String sql = "Select calle, numExt, colonia, codPostal, ciudad, telefono From almacen Where id_proyecto="+id_proy;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            rs.first();
            direccion = rs.getString("calle")+" No."+rs.getString("numExt")+", "+rs.getString("colonia")+", CP."
                    +rs.getString("codPostal")+", "+rs.getString("ciudad")+", "+rs.getString("telefono");
            con.close();
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return direccion;
    }
    
}
