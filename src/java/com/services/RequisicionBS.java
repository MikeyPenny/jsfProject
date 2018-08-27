/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.mbean.FormProyectosMB;
import com.mbean.FormRequisicionMB;
import com.mbean.FormTablaInsumoOrdenCompraMB;
import com.mbean.FormTablaRequisicionMB;
import com.model.ExpInsumos;
import com.model.InsumoRequisicion;
import com.model.Presupuesto;
import com.model.Proyecto;
import com.model.Requisicion;
import com.model.Usuarios;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Mickey
 */
public class RequisicionBS {
    
    Connection con;
    
    public RequisicionBS(Connection con) {
        this.con = con;
    }
    
    public int guardarRequisicion(Requisicion r) {
        
        Integer noReq = -1;
        
        try {
            
            con.setAutoCommit(false);
            
            String sql = "select max(noRequisicion) as noRequisicion from requisicion where id_presupuesto ="+r.getPresupuesto().getIdPresupuesto();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            noReq = rs.getInt("noRequisicion")+1;
            
            if (noReq == 0) {
                noReq = 1;
            }else {
                r.setNoRequisicion(noReq);
            }
            
            System.out.println("No de Req: " + noReq + " no Pre: " + r.getPresupuesto().getIdPresupuesto());
            
            String sql2 = "Insert into requisicion(noRequisicion, fechaSolicitud, estatus, fechaAutorizacion, "
                    + "idUsuarioAutorizacion, idUsuarioSolicitante, id_presupuesto)values (?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql2);
            ps.setInt(1, r.getNoRequisicion());
            ps.setString(2, r.getFechasolicitud());
            ps.setString(3, r.getEstatus());
            ps.setString(4, r.getFechaAutorizacion());
            ps.setInt(5, r.getIdUsuarioAutorizacion());
            ps.setInt(6, r.getIdUsuarioSolicitante());
            ps.setInt(7, r.getPresupuesto().getIdPresupuesto());
            ps.executeUpdate();
            
            String sql6 = "select last_insert_id()";
            rs = s.executeQuery(sql6);
            rs.first();
            int idReq = rs.getInt(1);
            
            Set<InsumoRequisicion> insumos = r.getInsumoRequisicions();
            System.out.println("Tamaño insumos: " + insumos.size());
            for (InsumoRequisicion aux : insumos) {
                aux.setRequisicion(r);

                String sql3 = "insert into insumo_requisicion(cantidad, preciounitario, id_requisicion, idExpinsumos)"
                        + "values (?,?,?,?)";
                ps = con.prepareStatement(sql3);
                ps.setBigDecimal(1, aux.getCantidad());
                ps.setBigDecimal(2, aux.getPreciounitario());
                ps.setInt(3, idReq);
                ps.setInt(4, aux.getExpInsumos().getIdExpinsumos());
                ps.executeUpdate();

                System.out.println("Se inserto Correctamente el Insumo con id: " + aux.getExpInsumos().getIdExpinsumos());
            }
            System.out.println("ID del Objeto: " + idReq);
            
            con.commit();
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            }catch(SQLException ex) {
                ex.printStackTrace();
            }
        }
        
        return noReq;
    }
    
    public List<FormTablaRequisicionMB> listarRequisiciones(int id_pres) {
        
        List<FormTablaRequisicionMB> listRequisiciones = new ArrayList<>();
        List<Requisicion> list = new ArrayList<>();
        Requisicion r;
        
        try {
            String sql = "select requisicion.id_requisicion, requisicion.noRequisicion, requisicion.fechaSolicitud, "
                    + "requisicion.estatus, requisicion.fechaAutorizacion, requisicion.idUsuarioAutorizacion, requisicion.idUsuarioSolicitante,"
                    + "requisicion.id_presupuesto from requisicion, presupuesto, proyecto "
                    + "where presupuesto.id_presupuesto ="+id_pres;
            Statement s = con.createStatement();
            ResultSet rs  = s.executeQuery(sql);
            while(rs.next()) {
                r = new Requisicion();
                r.setIdRequisicion(rs.getInt("id_requisicion"));
                r.setNoRequisicion(rs.getInt("noRequisicion"));
                r.setFechasolicitud(rs.getString("fechaSolicitud"));
                r.setEstatus(rs.getString("estatus"));
                r.setFechaAutorizacion(rs.getString("fechaAutorizacion"));
                r.setIdUsuarioAutorizacion(rs.getInt("idUsuarioAutorizacion"));
                r.setIdUsuarioSolicitante(rs.getInt("idUsuarioSolicitante"));
                r.setPresupuesto(agregarPresupuesto(rs.getInt("id_presupuesto")));
                list.add(r);
            }
            

            for (Requisicion aux : list) {
                FormTablaRequisicionMB item = new FormTablaRequisicionMB();
                String sql4 = "select nombre from usuarios where idUsuario="+aux.getIdUsuarioSolicitante();
                rs = s.executeQuery(sql4);
                rs.first();
                String user = rs.getString("nombre");
                item.setEstatus(aux.getEstatus());
                item.setNoRequisicion(aux.getNoRequisicion());
                item.setFechaAutorizacion(aux.getFechaAutorizacion());
                item.setFechaSolicitud(aux.getFechasolicitud());
                item.setIdPresupuesto(aux.getPresupuesto().getIdPresupuesto());
                item.setIdRequicision(aux.getIdRequisicion());
                item.setIdUsuarioAutorizacion(0);
                item.setIdUsuarioSolicitante(aux.getIdUsuarioSolicitante());
                item.setNomPresupuesto(aux.getPresupuesto().getPresupuesto());
                item.setUsuarioAutorizacion("");
                item.setUsuarioSolicitante(user);
                item.setNomProyecto(aux.getPresupuesto().getProyecto().getProyecto());
                listRequisiciones.add(item);
            }
            
            con.close();
            
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return listRequisiciones;
    }
    
    public List<FormTablaRequisicionMB> listarTodasRequisiciones() {
        
        List<FormTablaRequisicionMB> listRequisiciones = new ArrayList<FormTablaRequisicionMB>();
        List<Requisicion> list = new ArrayList<>();
        Requisicion r;
        
        try {
            String sql = "select * from requisicion";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                r = new Requisicion();
                r.setIdRequisicion(rs.getInt("id_requisicion"));
                r.setNoRequisicion(rs.getInt("noRequisicion"));
                r.setFechasolicitud(rs.getString("fechaSolicitud"));
                r.setEstatus(rs.getString("estatus"));
                r.setFechaAutorizacion(rs.getString("fechaAutorizacion"));
                r.setIdUsuarioAutorizacion(rs.getInt("idUsuarioAutorizacion"));
                r.setIdUsuarioSolicitante(rs.getInt("idUsuarioSolicitante"));
                r.setPresupuesto(agregarPresupuesto(rs.getInt("id_presupuesto")));
                list.add(r);
            }

            for (Requisicion aux : list) {
                FormTablaRequisicionMB item = new FormTablaRequisicionMB();
                
                String sql2 = "select nombre from usuarios where idUsuario="+aux.getIdUsuarioSolicitante();
                rs = s.executeQuery(sql2);
                rs.first();
                String user = rs.getString("nombre");
                
                String sql3 = "select nombre from usuarios where idUsuario="+aux.getIdUsuarioAutorizacion();
                rs = s.executeQuery(sql3);
                rs.first();
                String user2;
                
                if (rs.first()) {
                    user2 = rs.getString("nombre");
                    
                }else {
                    user2="SIN USUARIO";
                }
                
                item.setEstatus(aux.getEstatus());
                item.setNoRequisicion(aux.getNoRequisicion());
                item.setFechaAutorizacion(aux.getFechaAutorizacion());
                item.setFechaSolicitud(aux.getFechasolicitud());
                item.setIdPresupuesto(aux.getPresupuesto().getIdPresupuesto());
                item.setIdRequicision(aux.getIdRequisicion());
                item.setIdUsuarioAutorizacion(0);
                item.setIdUsuarioSolicitante(0);
                item.setNomPresupuesto(aux.getPresupuesto().getPresupuesto());
                item.setUsuarioAutorizacion(user2);
                item.setUsuarioSolicitante(user);
                item.setNomProyecto(aux.getPresupuesto().getProyecto().getProyecto());
                listRequisiciones.add(item);
            }
            
            con.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listRequisiciones;
    }
    
    public List<InsumoRequisicion> detalleRequisicion(int idRequisicion) {
        
        List<InsumoRequisicion> list = new ArrayList<>();
        InsumoRequisicion ir;
        
        try {
            String sql = "select * from insumo_requisicion where id_requisicion ="+idRequisicion;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                ir = new InsumoRequisicion();
                ir.setIdinsumoRequisicion(rs.getInt("idinsumo_requisicion"));
                ir.setCantidad(rs.getBigDecimal("cantidad"));
                ir.setPreciounitario(rs.getBigDecimal("preciounitario"));
                ir.setRequisicion(agregarRequisicion(rs.getInt("id_requisicion")));
                ir.setExpInsumos(agreagarExpInsumos(rs.getInt("idExpinsumos")));
                list.add(ir);
            }
            

            System.out.println("Tamaño: " + list.size());

            /*for (InsumoRequisicion aux : list) {
                Requisicion r = aux.getRequisicion();
                aux.setRequisicion(r);
                System.out.println("Req: " + r);
                ExpInsumos ei = aux.getExpInsumos();
                aux.setExpInsumos(ei);
                System.out.println("Ei: " + ei);
            }*/
            con.close();

        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        

        return list;
    }
    
    public boolean autorizacionRequisicion(List<Integer> listaIdRequisicion, int idUsuario) {
        
        Requisicion r;
        boolean result = true;
        try {
            Date fecha = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy H:m:s");
            String fechaAutorizacion = sdf.format(fecha);
            for (int aux : listaIdRequisicion) {
                                
                r =  new Requisicion();
                r.setIdRequisicion(aux);
                r.setEstatus(Constantes._LBL_AUT);
                r.setFechaAutorizacion(fechaAutorizacion);
                r.setIdUsuarioAutorizacion(idUsuario);
                
                String sql = "update requisicion set fechaAutorizacion=?, estatus=?, idUsuarioAutorizacion=? "
                        + "where id_requisicion="+aux;
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, r.getFechaAutorizacion());
                ps.setString(2, r.getEstatus());
                ps.setInt(3, r.getIdUsuarioAutorizacion());
                ps.executeUpdate();
                                
                System.out.println("ID Req autorizado: " + r.getIdRequisicion());
            }
        } catch (SQLException e) {
            result = false;
            e.printStackTrace();
        }
        
        return result;
    }
    
    public Map<Integer,Integer> listadoRequisicionesAutorizados(int idPresupuesto){
        
        Map<Integer,Integer> listaRequisiciones = new TreeMap<>();
        List<Requisicion> list = new ArrayList<>();
        Requisicion r;
        
        try {
            
            String sql = "select * from requisicion where id_presupuesto="+idPresupuesto
                    + " and estatus =\'"+Constantes._LBL_AUT+"\'";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                r = new Requisicion();
                r.setIdRequisicion(rs.getInt("id_requisicion"));
                r.setNoRequisicion(rs.getInt("noRequisicion"));
                r.setFechasolicitud(rs.getString("fechaSolicitud"));
                r.setEstatus(rs.getString("estatus"));
                r.setFechaAutorizacion(rs.getString("fechaAutorizacion"));
                r.setIdUsuarioAutorizacion(rs.getInt("idUsuarioAutorizacion"));
                r.setIdUsuarioSolicitante(rs.getInt("idUsuarioSolicitante"));
                r.setPresupuesto(agregarPresupuesto(rs.getInt("id_presupuesto")));
                list.add(r);
            }
            
            con.close();
                
            }catch(SQLException e) {
                e.printStackTrace();
            }
            
            System.out.println("Tam Req: " + list.size());

            for(Requisicion aux: list){
                listaRequisiciones.put(aux.getIdRequisicion(),aux.getNoRequisicion());
            }
   
        
        return listaRequisiciones;
    }
    
    public List<FormTablaInsumoOrdenCompraMB> listarInsumosRequisicionPorId(int idRequisicion){
        List<FormTablaInsumoOrdenCompraMB> listaInsumos = new ArrayList<FormTablaInsumoOrdenCompraMB>();
        List<InsumoRequisicion> insumos = new ArrayList<>();
        InsumoRequisicion ir;
        try {
            String sql = "select * from insumo_requisicion where id_requisicion="+idRequisicion;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                ir = new InsumoRequisicion();
                ir.setIdinsumoRequisicion(rs.getInt("idinsumo_requisicion"));
                ir.setCantidad(rs.getBigDecimal("cantidad"));
                ir.setPreciounitario(rs.getBigDecimal("preciounitario"));
                ir.setRequisicion(agregarRequisicion(rs.getInt("id_requisicion")));
                ir.setExpInsumos(agreagarExpInsumos(rs.getInt("idExpinsumos")));
                insumos.add(ir);
            }
            
            int i = 0;
            for(InsumoRequisicion aux : insumos){
                FormTablaInsumoOrdenCompraMB item = new FormTablaInsumoOrdenCompraMB();
                item.setInsumoRequisicion(aux.getIdinsumoRequisicion());
                item.setCantidad(aux.getCantidad());
                item.setConcepto(aux.getExpInsumos().getDescripcion());
                item.setCveInsumo(aux.getExpInsumos().getCodInsumo());
                item.setDescuento(0);
                item.setIdControl(++i);
                item.setIdInsumo(aux.getExpInsumos().getIdExpinsumos());
                BigDecimal imp = aux.getCantidad().multiply(aux.getExpInsumos().getCostoInsCtrl());
                imp = imp.setScale(2,BigDecimal.ROUND_HALF_DOWN );
                item.setImporte(imp);
                item.setNoRequisicion(aux.getRequisicion().getNoRequisicion());
                item.setPrecioUnitario(aux.getExpInsumos().getCostoInsCtrl().setScale(2,BigDecimal.ROUND_HALF_DOWN ));
                item.setUnidad(aux.getExpInsumos().getUnidades());
                listaInsumos.add(item);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return listaInsumos;
    }
    
    public boolean cancelarRequisicion(List <Integer> idRequisiciones){
        
        boolean resultado = false;
        
        try {
            con.setAutoCommit(false);
            
            for (int aux : idRequisiciones) {
                
                String sql = "update requisicion set estatus=\'"+Constantes._LBL_CAN+"\' where id_requisicion="+aux;
                Statement s = con.createStatement();
                s.executeUpdate(sql);
                
            }
            resultado = true;
            
            con.commit();
            
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
            }catch(SQLException ex) {
                ex.printStackTrace();
            }
        }
        return resultado;
    }
    
    public Map<String, Object> editarRequisicion(int idRequisicion){
        
        Map<String, Object> result = new HashMap<>();
        FormProyectosMB rmb = new FormProyectosMB();
        FormRequisicionMB fmb = new FormRequisicionMB();
        Requisicion r;
        
        try {
            
            String sql = "select * from requisicion where id_requisicion ="+idRequisicion;
            Statement s =  con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            r = new Requisicion();
            r.setIdRequisicion(rs.getInt("id_requisicion"));
            r.setFechasolicitud(rs.getString("fechaSolicitud"));
            r.setEstatus(rs.getString("estatus"));
            r.setFechaAutorizacion(rs.getString("fechaAutorizacion"));
            r.setIdUsuarioAutorizacion(rs.getInt("idUsuarioAutorizacion"));
            r.setIdUsuarioSolicitante(rs.getInt("isUsuarioSolicitante"));
            r.setPresupuesto(agregarPresupuesto(rs.getInt("id_presupuesto")));
            
            rmb.setNoPresupuesto(r.getPresupuesto().getPresupuesto());
            rmb.setNombreProyecto(r.getPresupuesto().getProyecto().getProyecto());

            fmb.setEstatus(r.getEstatus());
            fmb.setFechaAutorizacion(r.getFechaAutorizacion());
            fmb.setIdPresupuesto(r.getPresupuesto().getIdPresupuesto());
            fmb.setIdProyecto(r.getPresupuesto().getProyecto().getIdProyecto());
            fmb.setIdUsuarioAutorizacion(r.getIdUsuarioAutorizacion());
            fmb.setIdUsuarioSolicitante(r.getIdUsuarioSolicitante());

            String sql2 = "select idUsuario, nombre from usuario where idUsuario="+r.getIdUsuarioAutorizacion();
            rs = s.executeQuery(sql2);
            rs.first();
            Usuarios u1 = new Usuarios();
            u1.setIdUsuario(rs.getInt("idUsuario"));
            u1.setNombre(rs.getString("nombre"));
            
            String sql3 = "select idUsuario, nombre from usuario where idUsuario="+r.getIdUsuarioSolicitante();
            rs = s.executeQuery(sql3);
            rs.first();
            Usuarios u2 = new Usuarios();
            u1.setIdUsuario(rs.getInt("idUsuario"));
            u1.setNombre(rs.getString("nombre"));

            fmb.setNombreUsuarioAutorizacion(u1.getNombre());
            fmb.setNombreUsuarioAutorizacion(u2.getNombre());

            

            result.put("FormProyectosMB", rmb);
            result.put("FormRequisicionMB", fmb);
            
            System.out.println("Req: " + r);
            
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return result;
        
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
    
    public Requisicion agregarRequisicion(int id) {
        Requisicion r = new Requisicion();
        
        try {
            String sql = "select * from requisicion where id_requisicion="+id;
            Statement s  = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            r.setIdRequisicion(rs.getInt("id_requisicion"));
            r.setNoRequisicion(rs.getInt("noRequisicion"));
            r.setFechasolicitud(rs.getString("fechaSolicitud"));
            r.setEstatus(rs.getString("estatus"));
            r.setFechaAutorizacion(rs.getString("fechaAutorizacion"));
            r.setIdUsuarioAutorizacion(rs.getInt("idUsuarioAutorizacion"));
            r.setIdUsuarioSolicitante(rs.getInt("idUsuarioSolicitante"));
            r.setPresupuesto(agregarPresupuesto(rs.getInt("id_presupuesto")));
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return r;
    }
    
    public ExpInsumos agreagarExpInsumos(int id) {
        ExpInsumos ei =  new ExpInsumos();
        
        try {
            String sql = "select * from exp_insumos where idExpinsumos="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            ei.setIdExpinsumos(id);
            ei.setPresupuesto(agregarPresupuesto(rs.getInt("id_presupuesto")));
            ei.setCodInsumo(rs.getString("codInsumo"));
            ei.setDescripcion(rs.getString("descripcion"));
            ei.setUnidades(rs.getString("unidades"));
            ei.setCostoIns(rs.getBigDecimal("costoIns"));
            ei.setCostoInsCtrl(rs.getBigDecimal("costoInsCtrl"));
            ei.setCuenta(rs.getString("cuenta"));
            ei.setTotal(rs.getBigDecimal("total"));
            
        }catch(SQLException e) {
            e.printStackTrace();
        }        
        return ei;
    }
    
}
