/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.mbean.FormTablaInsumosValeConsumoMB;
import com.model.ExpInsumos;
import com.model.InsumoValeConsumo;
import com.model.InsumoValeDevolucion;
import com.model.ValeConsumo;
import com.model.ValeDevolucion;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ValeDevolucionBS {
    
    Connection con;
    
    public ValeDevolucionBS(Connection con) {
        this.con = con;
    }
    
    public int autorizarValeDevolcion(Integer idValeConsumo, List<FormTablaInsumosValeConsumoMB> insumos, int id_proy) {
        
        Integer idValeDevolucion = 0;
        try {
            
            con.setAutoCommit(false);
            
            Date fecha = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy H:mm:ss");
            
            String sql = "select * from vale_consumo where id_vale_consumo="+idValeConsumo;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            ValeConsumo vc = new ValeConsumo();
            vc.setIdValeConsumo(idValeConsumo);
            vc.setNoValeConsumo(rs.getInt("no_vale_consumo"));
            vc.setFechaElaboracion(rs.getString("fechaElaboracion"));
            vc.setEstatus(rs.getString("estatus"));
            
            ValeDevolucion vd = new ValeDevolucion();
            vd.setFechaElaboracion(sdf.format(fecha));
            
            vd.setInsumoValeDevolucions(null);
            vd.setNoValeConsumo(vc.getNoValeConsumo());
            
            String sql3= "select max(Distinct noValeDevolucion) as noValeDevolucion from vale_devolucion as vd "
                    + "inner join vale_consumo as vc on vc.id_vale_consumo = vd.id_vale_consumo "
                    + "inner join insumo_vale_consumo as ivc on ivc.id_vale_consumo = vc.id_vale_consumo "
                    + "inner join exp_insumos as exp on exp.idExpinsumos = ivc.idExpinsumos "
                    + "inner join presupuesto as pre on pre.id_presupuesto = exp.id_presupuesto "
                    + "inner join proyecto as pro on pro.id_proyecto = pre.id_proyecto "
                    + "and pro.id_proyecto="+id_proy;
            
            rs = s.executeQuery(sql3);
            rs.first();
            int noDevol = rs.getInt("noValeDevolucion");
            
            if(noDevol == 0) {
                noDevol = 1;
            }else {
                noDevol = noDevol + 1;
            }
            
            vd.setNoValeDevolucion(noDevol);
            vd.setValeConsumo(vc);
            
            String sql2 = "insert into vale_devolucion(id_vale_consumo, fechaElaboracion, noValeConsumo, noValeDevolucion)"
                    + "values (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql2);
            ps.setInt(1, idValeConsumo);
            ps.setString(2, vd.getFechaElaboracion());
            ps.setInt(3, vc.getNoValeConsumo());
            ps.setInt(4, vd.getNoValeDevolucion());
            ps.executeUpdate();
            
            String sql4 ="select last_insert_id()";
            rs = s.executeQuery(sql4);
            rs.first();
            
            idValeDevolucion = rs.getInt(1);
            vd.setIdValeDevolucion(idValeDevolucion);
            System.out.println(idValeDevolucion);
                        
            for (FormTablaInsumosValeConsumoMB aux : insumos) {
                if (!(aux.getCantidad().compareTo(BigDecimal.ZERO) == 0)) {
                    String sql5 = "select * from insumo_vale_consumo as ivc where ivc.idExpinsumos="+aux.getIdInsumoRequisicion()
                                + " and ivc.id_vale_consumo="+idValeConsumo;
                    rs = s.executeQuery(sql5);
                    rs.first();
                    InsumoValeConsumo ivc = new InsumoValeConsumo();
                    ivc.setIdinsumoValeConsumo(rs.getInt("idinsumo_vale_consumo"));
                    ivc.setCantidad(rs.getBigDecimal("cantidad"));
                    ivc.setCantidadOriginal(rs.getBigDecimal("cantidadOriginal"));
                    ivc.setValeConsumo(agregarValeConsumo(rs.getInt("id_vale_consumo")));
                    ivc.setExpInsumos(agregarExpInsumos(rs.getInt("idExpInsumos")));

                    InsumoValeDevolucion item = new InsumoValeDevolucion();
                    item.setCantidad(aux.getCantidad());
                    item.setCantidadOriginal(aux.getCantidadOriginal());
                    item.setInsumoValeConsumo(ivc);
                    item.setValeDevolucion(vd);
                    
                    String sql6 = "insert into insumo_vale_devolucion(id_vale_devolucion, idinsumo_vale_consumo, cantidad, cantidadOriginal)values (?,?,?,?)";
                    ps = con.prepareStatement(sql6);
                    ps.setInt(1, item.getValeDevolucion().getIdValeDevolucion());
                    ps.setInt(2, item.getInsumoValeConsumo().getIdinsumoValeConsumo());
                    ps.setBigDecimal(3, item.getCantidad());
                    ps.setBigDecimal(4, item.getCantidadOriginal());
                    ps.executeUpdate();
                    
                    String sql7= "select last_insert_id()";
                    rs = s.executeQuery(sql7);
                    rs.first();
                    int id = rs.getInt(1);
                    
                    System.out.println("ID de Insumo Devuelto: " + id);
                }

            }
            
            con.commit();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
                idValeDevolucion = -1;
            
            }catch(SQLException ex) {
                ex.printStackTrace();
            }
            
        }

        return idValeDevolucion;

    }
    
    public ValeConsumo agregarValeConsumo(int id) {
        
        ValeConsumo vale = new ValeConsumo();
        
        try {
            String sql = "select * from vale_consumo where id_vale_consumo="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            vale.setIdValeConsumo(id);
            vale.setNoValeConsumo(rs.getInt("no_vale_consumo"));
            vale.setFechaElaboracion(rs.getString("fechaElaboracion"));
            vale.setEstatus(rs.getString("estatus"));
            
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return vale;
    }
    
    public ExpInsumos agregarExpInsumos(int id) {
        ExpInsumos exp = new ExpInsumos();
        
        try {
            String sql = "select * from exp_insumos where idExpinsumos="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            exp.setIdExpinsumos(id);
            exp.setTotal(rs.getBigDecimal("total"));
            exp.setDescripcion(rs.getString("descripcion"));
            exp.setCodInsumo(rs.getString("codInsumo"));
            exp.setUnidades(rs.getString("unidades"));
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return exp;
    }
    
}
