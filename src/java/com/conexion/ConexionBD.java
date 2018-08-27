/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionBD {
    
    Connection con;
    
    public ConexionBD() {
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/erp_v2p0", "root", "");
        }catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public Connection getConexion() {
        return con;
    }
    
    public void closeConexion() {
        try{
            con.close();
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
