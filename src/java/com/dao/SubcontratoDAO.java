/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.bean.CtaSubcontratoBean;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class SubcontratoDAO {
    
    Connection con;
    
    public SubcontratoDAO(Connection con) {
        this.con = con;
    }
    
    public List<CtaSubcontratoBean> consultarCatalogoSub() {
        
        List<CtaSubcontratoBean> listSubs = new ArrayList<CtaSubcontratoBean>();
        CtaSubcontratoBean csb;
        
        try {
            
            String sql = "Select * From catsubcontratos";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                csb = new CtaSubcontratoBean();
                csb.setId_subcon(rs.getInt("id_subcon"));
                csb.setClave(rs.getString("clave"));
                csb.setDefinicion(rs.getString("definicion"));
                listSubs.add(csb);
            }
            
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return listSubs;
        
    }
    
}
