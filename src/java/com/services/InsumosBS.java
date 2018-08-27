/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.mbean.InsumoTablaMB;
import com.model.ExpInsumos;
import com.model.Presupuesto;
import com.model.Proyecto;
import com.util.CantDevuelta;
import com.util.Constantes;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class InsumosBS {
    
    Connection con;
    
    public InsumosBS(Connection con) {
        this.con = con;
    }
    
    public List<InsumoTablaMB> listarMateriales(int idPresupuesto) {
        
        List<InsumoTablaMB> listaInsumos = new ArrayList<>();
        List<ExpInsumos> list = new ArrayList<>();
        ExpInsumos exp;
        
        try {
            String sql = "select * from exp_insumos where id_presupuesto ="+idPresupuesto+ 
                        " and cuenta=\'"+Constantes._LBL_MAT+"\'";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                exp = new ExpInsumos();
                exp.setIdExpinsumos(rs.getInt("idExpinsumos"));
                exp.setCodInsumo(rs.getString("codInsumo"));
                exp.setDescripcion(rs.getString("descripcion"));
                exp.setUnidades(rs.getString("unidades"));
                exp.setCostoIns(rs.getBigDecimal("costoIns"));
                exp.setCostoInsCtrl(rs.getBigDecimal("costoInsCtrl"));
                exp.setCuenta(rs.getString("cuenta"));
                exp.setTotal(rs.getBigDecimal("total"));
                exp.setPresupuesto(agregarPresupuesto(rs.getInt("id_presupuesto")));
                list.add(exp);
                
            }
            
            //System.out.println("Tamaño de la lista: " + list.size());
            siguiente:
            for (ExpInsumos aux : list) {
                String sql2 = "SELECT sum(ir.cantidad) as cantidad FROM insumo_requisicion as ir "
                        + "inner join requisicion as r on r.id_requisicion = ir.id_requisicion "
                        + "and ir.idExpInsumos ="+aux.getIdExpinsumos()+ " and r.estatus !=\'"+Constantes._LBL_CAN+"\'";
                rs = s.executeQuery(sql2);
                rs.first();
                                
                BigDecimal cantidadOcupada = rs.getBigDecimal("cantidad");
                BigDecimal cantidadTotal = aux.getTotal();
                if (cantidadOcupada == null) {
                    cantidadOcupada = new BigDecimal(0);
                }
                
                BigDecimal cantDevuelta = new CantDevuelta().obtenerDevoluciones(aux.getIdExpinsumos());
                
                
                
                BigDecimal resta = cantidadOcupada.subtract(cantDevuelta);
                
                //Cambie cantidadOcupada por resta en el parametro del subtract
                if (new BigDecimal(0.0).compareTo(cantidadTotal.subtract(resta)) == 0) {
                    continue siguiente;
                }
                
                //System.out.println(cantidadOcupada);
                
                
                InsumoTablaMB auxIns = new InsumoTablaMB();
                auxIns.setCantDisponible(cantidadTotal.subtract(resta));
                
                //auxIns.setCantDisponible(cantidadTotal.subtract(cantidadOcupada));
                auxIns.setCantPresupuestada(cantidadTotal);
                auxIns.setCantDisponibleOriginal(cantidadTotal.subtract(cantidadOcupada));
                auxIns.setClave(aux.getCodInsumo());
                auxIns.setConcepto(aux.getDescripcion());
                auxIns.setIdInsumoTabla(aux.getIdExpinsumos());
                auxIns.setUnidades(aux.getUnidades());
                auxIns.setPrecioUnitario(aux.getCostoInsCtrl());
                listaInsumos.add(auxIns);
            }
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return listaInsumos;
    }
    
    public Presupuesto agregarPresupuesto(int id) {
        Presupuesto pre = new Presupuesto();
        
        try {
            String sql = "select * from presupuesto where id_presupuesto="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            pre.setIdPresupuesto(rs.getInt("id_presupuesto"));
            pre.setPresupuesto(rs.getString("presupuesto"));
            pre.setProyecto(agregarProyecto(rs.getInt("id_proyecto")));
            
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return pre;
    }
    
    public Proyecto agregarProyecto(int id) {
        Proyecto pro = new Proyecto();
        
        try {
            String sql = "select * from proyecto where id_proyecto="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            pro.setIdProyecto(rs.getInt("id_proyecto"));
            pro.setProyecto(rs.getString("proyecto"));
            
            
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return pro;
    }
    
}
