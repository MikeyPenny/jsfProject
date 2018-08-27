/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.mbean.InventarioBean;
import com.model.Presupuesto;
import com.model.Proyecto;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class InventarioDAO {
    
    Connection con;
    
    public InventarioDAO(Connection con) {
        this.con = con;
    }
    
    
    public List<InventarioBean> obtenerInventario(int id_proy, int id_pres) {
        
        List<InventarioBean> listaInv = new ArrayList<InventarioBean>();
        InventarioBean ib;
        
        try {
            
            String sql = "select recIn.idinsumo_requisicion, sum(recIn.cantidad) as cantidad, "
                    + "max(rec.fechaRecepcion) as fechaRecepcion, exp.descripcion, exp.codInsumo, exp.unidades " +
                        "from recepcioninsumo as recIn " +
                        "inner join recepcion as rec on rec.idRecepcion = recIn.idRecepcion " +
                        "inner join insumo_requisicion as insr on insr.idinsumo_requisicion = recIn.idinsumo_requisicion " +
                        "inner join exp_insumos as exp on exp.idExpinsumos = insr.idExpinsumos " +
                        "inner join presupuesto as pre on pre.id_presupuesto = exp.id_presupuesto and pre.id_presupuesto ="+id_pres+
                        " inner join proyecto as pro on pro.id_proyecto ="+id_proy+
                        " group by exp.codInsumo";
            
            Statement s = con.createStatement();
                ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                ib = new InventarioBean();
                ib.setId_insumoreq(rs.getInt("idinsumo_requisicion"));
                ib.setCantidad(rs.getBigDecimal("cantidad"));
                ib.setFecha(rs.getString("fechaRecepcion"));
                ib.setDescripcion(rs.getString("descripcion"));
                ib.setCodInsumo(rs.getString("codInsumo"));
                ib.setUnidades(rs.getString("unidades"));
                listaInv.add(ib);
            }
            
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return listaInv;
         
    }
    
    public List<Proyecto> listarPoryectos() {
        List<Proyecto> listaProy = new ArrayList<Proyecto>();
        Proyecto proy;
        
        try {
            String sql = "Select * from proyecto";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                proy = new Proyecto();
                proy.setIdProyecto(rs.getInt("id_proyecto"));
                proy.setProyecto(rs.getString("proyecto"));
                listaProy.add(proy);
            }
            
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return listaProy;
    }
    
    public List<Presupuesto> listarPresupestos(int id_proy) {
        List<Presupuesto> listPres = new ArrayList<Presupuesto>();
        Presupuesto pres;
        
        try { 
            String sql = "Select * from presupuesto where id_proyecto="+id_proy;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                pres = new Presupuesto();
                pres.setIdPresupuesto(rs.getInt("id_presupuesto"));
                pres.setPresupuesto(rs.getString("presupuesto"));
                listPres.add(pres);
            }
            
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return listPres;
    }
    
}
