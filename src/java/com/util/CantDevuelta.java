/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Mickey
 */
public class CantDevuelta {
    
    Connection con;
    
    
        public CantDevuelta() {
            try {

             Class.forName("com.mysql.jdbc.Driver");
             con = DriverManager.getConnection("jdbc:mysql://localhost/erp_v2p0", "root", "");

             }catch(ClassNotFoundException|SQLException e) {
                 System.out.println(e.getMessage());
             }
        }

        public BigDecimal obtenerDevoluciones(int idExp) {
            BigDecimal devol = BigDecimal.ZERO;

            try {
                String sql = "Select Sum(dev.cantidad) as cantidad From devolucion_insumo as dev "
                             + "Inner Join recepcioninsumo as rec on rec.idrecepcionInsumo = dev.idrecepcionInsumo "
                             + "Inner Join insumo_requisicion as insr on insr.idinsumo_requisicion = rec.idinsumo_requisicion "
                             + "Inner Join exp_insumos as exp on exp.idExpinsumos = insr.idExpinsumos and exp.idExpinsumos ="+idExp;
                Statement s = con.createStatement();
                ResultSet rs = s.executeQuery(sql);
                rs.first();

                devol = rs.getBigDecimal("cantidad");

                if(devol == null) {
                    devol = BigDecimal.ZERO;
                }

                con.close();

            }catch(SQLException e) {
                e.printStackTrace();
            }

            return devol;
        }
    
}
