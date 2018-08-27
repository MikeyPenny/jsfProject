/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo;

import com.bean.Concepts;
import com.bean.Nivel;
import com.conexion.ConexionBD;
import com.dao.PresupuestoDAO;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class FiltroBO {
    
    public List<Concepts> createFilter(Nivel first, Nivel second) {
        
        int id_first = first.getId_partida();
        
        int id_secLvl = 0;
        List<Concepts> list;
        
        if(second != null) {
            id_secLvl = second.getId_partida();
        }
        
        if(id_secLvl == 0) {
            
            Connection con;
            ConexionBD conexion = new ConexionBD();
            con = conexion.getConexion();
            PresupuestoDAO presD = new PresupuestoDAO(con);
            list = presD.filterFirstLvl(id_first);
            
        }else {
            
            Connection con;
            ConexionBD conexion = new ConexionBD();
            con = conexion.getConexion();
            PresupuestoDAO presD = new PresupuestoDAO(con);
            list = presD.filterSecLvl(id_secLvl);
            
        }
        
        return list;
    }
    
}
