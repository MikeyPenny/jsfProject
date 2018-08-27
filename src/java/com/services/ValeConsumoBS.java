/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.mbean.FormListValeConsumo;
import com.mbean.FormTablaInsumoValeConsumo;
import com.mbean.FormTablaInsumosValeConsumoMB;
import com.mbean.FormTablaValeConsumoMB;
import com.model.ExpInsumos;
import com.model.InsumoValeConsumo;
import com.model.InsumoValeDevolucion;
import com.model.RecepAlmBean;
import com.model.ValeConsumo;
import com.util.Constantes;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ValeConsumoBS {
    
    Connection con;
    
    public ValeConsumoBS(Connection con) {
        this.con = con;
    }
    
    public List<FormTablaInsumosValeConsumoMB> listarInsumosPorIdValeConsumo(int idValeConsumo) {
        
        
        List<FormTablaInsumosValeConsumoMB> result = new ArrayList<>();
        List<InsumoValeConsumo> list = new ArrayList<>();
        InsumoValeConsumo vale;
        List<InsumoValeDevolucion> listDev = new ArrayList<>();
        InsumoValeDevolucion id;
        
        try {
            
            String sql = "select * from insumo_vale_consumo  where id_vale_consumo="+idValeConsumo;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                vale = new InsumoValeConsumo();
                vale.setIdinsumoValeConsumo(rs.getInt("idinsumo_vale_consumo"));
                vale.setCantidad(rs.getBigDecimal("cantidad"));
                vale.setCantidadOriginal(rs.getBigDecimal("cantidadOriginal"));
                vale.setValeConsumo(agregarValeConsumo(rs.getInt("id_vale_consumo")));
                vale.setExpInsumos(agregarExpInsumos(rs.getInt("idExpinsumos")));
                list.add(vale);
            }
            
            String sql2 = "select id_insumo_vale_devolucion, idinsumo_vale_consumo, cantidadOriginal-sum(cantidadOriginal-cantidad) as xDevolver "
                        + "from insumo_vale_devolucion group by idinsumo_vale_consumo";
            rs = s.executeQuery(sql2);
            while(rs.next()) {
                id = new InsumoValeDevolucion();
                id.setIdInsumoValeDevolucion(rs.getInt("id_insumo_vale_devolucion"));
                vale = new InsumoValeConsumo();
                vale.setIdinsumoValeConsumo(rs.getInt("idinsumo_vale_consumo"));
                id.setInsumoValeConsumo(vale);
                id.setCantidad(rs.getBigDecimal("xDevolver"));
                listDev.add(id);
            }
            
            for(InsumoValeDevolucion aux: listDev) {
                
                for(InsumoValeConsumo aux2: list) {
                    if(aux.getInsumoValeConsumo().getIdinsumoValeConsumo() == aux2.getIdinsumoValeConsumo()) {
                        int ind = list.indexOf(aux2);
                        aux2.setCantidad(aux.getCantidad());
                        list.set(ind, aux2);
                        
                    }
                }
                
            }
            
            for (InsumoValeConsumo aux : list) {
                FormTablaInsumosValeConsumoMB item = new FormTablaInsumosValeConsumoMB();

                item.setCantidad(aux.getCantidad());
                item.setCantidadOriginal(aux.getCantidad());
                item.setClaveInsumo(aux.getExpInsumos().getCodInsumo());
                item.setIdInsumoRequisicion(aux.getExpInsumos().getIdExpinsumos());
                item.setNombreInsumo(aux.getExpInsumos().getDescripcion());
                item.setUnidades(aux.getExpInsumos().getUnidades());
                result.add(item);
            }
            
            System.out.println("Tamaño: " + list.size());
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public List<FormTablaValeConsumoMB> listarValeConsumoPorIdProyecto(int idProyecto) {
        
        List<FormTablaValeConsumoMB> idValeConsumos = new ArrayList<>();
        FormTablaValeConsumoMB formVale;
        
        
        try {
            
            String sql = "select distinct vc.id_vale_consumo, vc.no_vale_consumo, vc.fechaElaboracion from vale_consumo as vc "
                + "inner join insumo_vale_consumo as ivc on ivc.id_vale_consumo = vc.id_vale_consumo "
                + "inner join exp_insumos as exp on exp.idExpinsumos = ivc.idExpinsumos "
                + "inner join presupuesto as pre on pre.id_presupuesto = exp.id_presupuesto "
                + "inner join proyecto as pro on pro.id_proyecto = pre.id_proyecto "
                + "and pro.id_proyecto ="+idProyecto;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                formVale = new FormTablaValeConsumoMB();
                formVale.setIdValeConsumo(rs.getInt("id_vale_consumo"));
                formVale.setNoValeConsumo(rs.getInt("no_vale_consumo"));
                formVale.setFechaSolicutud(rs.getString("fechaElaboracion"));
                idValeConsumos.add(formVale);
            }
            
            //Buscar si ya hubo devoluciones y si son totales o parciales 
            List<Integer> listId = new ArrayList<>();
            int id = 0;
            String sql2 = "select vd.id_vale_consumo, sum(ivd.cantidad) as cantidad, ivd.cantidadOriginal "
                    + "from vale_devolucion as vd "
                    + "inner join insumo_vale_devolucion as ivd on ivd.id_vale_devolucion = vd.id_vale_devolucion "
                    + "inner join insumo_vale_consumo as ivc on ivc.idinsumo_vale_consumo = ivd.idinsumo_vale_consumo "
                    + "inner join exp_insumos as exp on exp.idExpinsumos = ivc.idExpinsumos "
                    + "inner join presupuesto as pre on pre.id_presupuesto = exp.id_presupuesto "
                    + "inner join proyecto as pro on pro.id_proyecto = pre.id_proyecto and pro.id_proyecto="+idProyecto
                    +" group by vd.id_vale_consumo";
            rs = s.executeQuery(sql2);
            while(rs.next()) {
                id = rs.getInt("id_vale_consumo");
                BigDecimal cant = rs.getBigDecimal("cantidad");
                BigDecimal cantOr = rs.getBigDecimal("cantidadOriginal");
                if(cant.floatValue() == cantOr.floatValue()) {
                    listId.add(id);
                }
                
            }
            
            //System.out.println(listId.size());
            //System.out.println(idValeConsumos.size());
            
            
            //Quitar los consumos que ya fueron devueltos en su totalidad
            for(int aux1: listId) {
                //System.out.println(aux1);
                for(FormTablaValeConsumoMB aux2: idValeConsumos) {
                    if(aux1 == aux2.getIdValeConsumo()) {
                        //int ind = idValeConsumos.indexOf(aux2);
                        //System.out.println(ind);
                        idValeConsumos.remove(aux2);
                        break;
                    }
                }
                
            }
            
            
            
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
        }

        return idValeConsumos;
    }
    
    public List<FormTablaInsumosValeConsumoMB> listarInsumosEnAlmacenPorIdProyecto(int idProyecto) {
        
        List<FormTablaInsumosValeConsumoMB> result = new ArrayList<>();
        List<RecepAlmBean> list = new ArrayList<>();
        RecepAlmBean rec;

        try {
            String sql = "SELECT ei.idExpinsumos, ei.codinsumo, ei.descripcion, ei.unidades, SUM( ri.cantidad ) as cantidad "
                + "FROM recepcioninsumo ri, insumo_requisicion ir, exp_insumos ei, presupuesto pre, proyecto pro "
                + "WHERE ri.idinsumo_requisicion = ir.idinsumo_requisicion "
                + "AND ir.idExpinsumos = ei.idExpinsumos "
                + "AND ei.id_presupuesto = pre.id_presupuesto "
                + "and pre.id_proyecto = pro.id_proyecto "
                + "and pro.id_proyecto ="+idProyecto
                + " GROUP BY ei.idExpinsumos";
        
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                rec = new RecepAlmBean();
                rec.setIdExpinsumos(rs.getInt("idExpinsumos"));
                rec.setCodInsumo(rs.getString("codInsumo"));
                rec.setDescripcion(rs.getString("descripcion"));
                rec.setUnidades(rs.getString("unidades"));
                rec.setCantidad(rs.getBigDecimal("cantidad"));
                list.add(rec);
            }
            
            siguiente:
            for (RecepAlmBean aux : list) {
                //Object it[] = (Object[]) aux;

                String sql1 = "select sum(ivc.cantidad) as cantidad from insumo_vale_consumo as ivc where ivc.idExpinsumos ="+aux.getIdExpinsumos();
                rs = s.executeQuery(sql1);
                rs.first();
                BigDecimal res = rs.getBigDecimal("cantidad");
                
                if (res == null) {   
                    res = BigDecimal.ZERO;
                }
                
                String sql2 = "select sum(ivd.cantidad) as cantidad from insumo_vale_devolucion as ivd, insumo_vale_consumo as ivc, "
                        + "exp_insumos as exp "
                        + "where ivd.idinsumo_vale_consumo = ivc.idinsumo_vale_consumo "
                        + "and ivc.idExpinsumos = exp.idExpinsumos "
                        + "and exp.idExpinsumos="+aux.getIdExpinsumos();
                rs = s.executeQuery(sql2);
                rs.first();
                
                BigDecimal res1 = rs.getBigDecimal("cantidad");
                if (res1 == null) {
                    res1 = BigDecimal.ZERO;
                }
                
                //System.out.println(res);
                BigDecimal cantidadRetirada = res;
                BigDecimal cantidadDevuelta = res1;
                //System.out.println(aux.getCantidad());
                BigDecimal cantidadRecepcionada = aux.getCantidad();
                BigDecimal cantidadDisponibleSinDevuelta = cantidadRecepcionada.subtract(cantidadRetirada);
                BigDecimal cantidadDisponible = cantidadDisponibleSinDevuelta.add(cantidadDevuelta);

                //System.out.println("idInsumo: " + aux.getIdExpinsumos() + " Recep: " + cantidadRecepcionada + " + Retirada: " + cantidadRetirada + " - Devuelta: " + cantidadDevuelta + " Disponible: " + cantidadDisponible);

                if (cantidadDisponible.compareTo(BigDecimal.ZERO) == 0) {
                    continue siguiente;
                }

                FormTablaInsumosValeConsumoMB item = new FormTablaInsumosValeConsumoMB();
                item.setCantidad(cantidadDisponible);
                item.setCantidadOriginal(cantidadDisponible);
                item.setClaveInsumo(aux.getCodInsumo());
                item.setEstatus("");
                item.setIdInsumoRequisicion(aux.getIdExpinsumos()); //Revisar, es un id de expInsumos y pide insRequisicion
                item.setNombreInsumo(aux.getDescripcion());
                item.setUnidades(aux.getUnidades());

                result.add(item);
            }
            
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public int autorizarValeConsumo(List<FormTablaInsumosValeConsumoMB> insumos, int id_proy) {
        
        int noValeConsumo;
        try {
            
            con.setAutoCommit(false);
            String sql = "select max(vc.no_vale_consumo) as noVale from vale_consumo as vc, insumo_vale_consumo as ivc, "
                    + "exp_insumos as exp, presupuesto as pre, proyecto as pro "
                    + "where vc.id_vale_consumo = ivc.id_vale_consumo "
                    + "and ivc.idExpInsumos = exp.idExpInsumos "
                    + "and exp.id_presupuesto = pre.id_presupuesto "
                    + "and pre.id_proyecto = pro.id_proyecto "
                    + "and pro.id_proyecto="+id_proy;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            noValeConsumo = rs.getInt("noVale");
            
            if (noValeConsumo == 0) {
                noValeConsumo = 1;
            } else {
                noValeConsumo = noValeConsumo + 1;
            }

            Date fecha = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy H:mm:ss");

            ValeConsumo vc = new ValeConsumo();
            vc.setEstatus(Constantes._LBL_AUT);
            vc.setFechaElaboracion(sdf.format(fecha));
            vc.setNoValeConsumo(noValeConsumo);
            
            String sql2 = "insert into vale_consumo(no_vale_consumo, fechaElaboracion, estatus)values (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql2);
            ps.setInt(1, vc.getNoValeConsumo());
            ps.setString(2, vc.getFechaElaboracion());
            ps.setString(3, vc.getEstatus());
            ps.executeUpdate();
            
            String sql3 = "select last_insert_id()";
            rs = s.executeQuery(sql3);
            rs.first();
            Integer idValeConsumo = rs.getInt(1);
            System.out.println("No de Vale: " + idValeConsumo);
            
            for (FormTablaInsumosValeConsumoMB aux : insumos) {
                InsumoValeConsumo item = new InsumoValeConsumo();
                item.setCantidad(aux.getCantidad());
                item.setCantidadOriginal(aux.getCantidadOriginal());
                item.setValeConsumo(vc);
                ExpInsumos ins = new ExpInsumos();
                ins.setIdExpinsumos(aux.getIdInsumoRequisicion());
                item.setExpInsumos(ins);
                
                String sql4 = "insert into insumo_vale_consumo(cantidad, cantidadOriginal, id_vale_consumo, idExpinsumos)"
                        + "values (?,?,?,?)";
                ps = con.prepareStatement(sql4);
                ps.setBigDecimal(1, item.getCantidad());
                ps.setBigDecimal(2, item.getCantidadOriginal());
                ps.setInt(3, idValeConsumo);
                ps.setInt(4, item.getExpInsumos().getIdExpinsumos());
                ps.executeUpdate();
                
                String sql5 = "select last_insert_id()";
                rs = s.executeQuery(sql5);
                rs.first();
                item.setIdinsumoValeConsumo(rs.getInt(1));
                //InsumoValeConsumo idInsumoValeConsumo = (InsumoValeConsumo) session.merge(item);
                System.out.println("\t idInsumoValeConsumo: " + item.getIdinsumoValeConsumo());
            }

            con.commit();
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
            noValeConsumo = -1;
            try {
                con.rollback();
            }catch(SQLException ex) {
                ex.printStackTrace();
            }
        }

        return noValeConsumo;
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
    
    public List<FormListValeConsumo> listarValeConsumo() {
        

        List<FormListValeConsumo> resultado = new ArrayList<>();
        List<ValeConsumo> list = new ArrayList<>();
        ValeConsumo vc;
        
        try {
            String sql = "select * from vale_consumo";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                vc =  new ValeConsumo();
                vc.setIdValeConsumo(rs.getInt("id_vale_consumo"));
                vc.setNoValeConsumo(rs.getInt("no_vale_consumo"));
                vc.setFechaElaboracion(rs.getString("fechaElaboracion"));
                vc.setEstatus(rs.getString("estatus"));
                list.add(vc);
            }
            
            for (ValeConsumo aux : list) {
                FormListValeConsumo item = new FormListValeConsumo();
                item.setEstatus(aux.getEstatus());
                item.setFechaElaboracion(aux.getFechaElaboracion());
                item.setIdValeConsumo(aux.getIdValeConsumo());
                item.setNoValeConsumo(aux.getNoValeConsumo());

                resultado.add(item);

            }
            
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return resultado;

    }
    
    public List<FormTablaInsumoValeConsumo>  listarDetallesValeConsumo(int idValeConsumo){
        
        List<FormTablaInsumoValeConsumo> resultado = new ArrayList<>();
        FormTablaInsumoValeConsumo item;
        try {
            String sql = "select * from insumo_vale_consumo where id_vale_consumo="+idValeConsumo;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                item = new FormTablaInsumoValeConsumo();
                item.setCantidad(rs.getBigDecimal("cantidad"));
                item.setIdinsumoValeConsumo(rs.getInt("idinsumo_vale_consumo"));
                String[] d = nombreInsumo(rs.getInt("idExpinsumos"));
                item.setNombreInsumo(d[0]);
                item.setUnidades(d[1]);
                resultado.add(item);
            }
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
       
        return resultado;
    }
    
    public String[] nombreInsumo(int id) {
        String[] dat = new String[2];
        
        try {
                                    
            String sql2 = "select descripcion, unidades from exp_insumos where idExpinsumos="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql2);
            rs.first();
            dat[0] = rs.getString("descripcion");
            dat[1] = rs.getString("unidades");
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return dat;
    }
    
}
