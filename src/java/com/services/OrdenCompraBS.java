/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.bean.AnticipoBean;
import com.mbean.DetalleOrdenCompraMB;
import com.mbean.FormDetalleOrdenCompraMB;
import com.mbean.FormOrdenCompraMB;
import com.mbean.FormTablaInsumoOrdenCompraMB;
import com.mbean.TablaInsumoOrdenCompraMB;
import com.model.ExpInsumos;
import com.model.InsumoRequisicion;
import com.model.OrdenCompra;
import com.model.Presupuesto;
import com.model.Proveedor;
import com.model.Proyecto;
import com.model.Requisicion;
import com.util.Constantes;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrdenCompraBS {
    
    Connection con;
    
    public OrdenCompraBS(Connection con) {
        this.con = con;
    }
    
    public  List<TablaInsumoOrdenCompraMB> listarDetalleOrdenCompra(int idOrdenCompra) {
        
        List<InsumoRequisicion> listaInsumos = new ArrayList<>();
        InsumoRequisicion insR;
        List<TablaInsumoOrdenCompraMB> listResult = new ArrayList<>();
        
        try {
            
            String sql = "select id_requisicion from orden_compra where idorden_compra="+idOrdenCompra;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            int idRequisicion = rs.getInt("id_requisicion");
            
            String sql2 = "select * from insumo_requisicion as ir where ir.id_requisicion ="+idRequisicion;
            rs = s.executeQuery(sql2);
            
            while(rs.next()) {
                insR = new InsumoRequisicion();
                insR.setIdinsumoRequisicion(rs.getInt("idinsumo_requisicion"));
                insR.setCantidad(rs.getBigDecimal("cantidad"));
                insR.setPreciounitario(rs.getBigDecimal("preciounitario"));
                insR.setRequisicion(agregarRequisicion(idRequisicion));
                insR.setExpInsumos(agregarExpInsumos(insR.getIdinsumoRequisicion()));
                listaInsumos.add(insR);
            }
            
            System.out.println("Tamaño de insumos: " + listaInsumos.size());
            
            
            List<Integer> listIdsRecepciones = new ArrayList<>();
            int id = 0;
             
            String sql3 = "select r.idRecepcion from recepcion as r where idorden_compra="+idOrdenCompra;
            rs = s.executeQuery(sql3);
            while(rs.next()) {
                id = rs.getInt("idRecepcion");
                listIdsRecepciones.add(id);
            }
            
            for(InsumoRequisicion aux : listaInsumos){
                BigDecimal cantidadUsuada = new BigDecimal(BigInteger.ZERO);
                int idInsR = aux.getIdinsumoRequisicion();
                for(Integer tmp : listIdsRecepciones){
                    String sql4 = "select ri.cantidad from recepcioninsumo as ri where idRecepcion ="+tmp+ " and idinsumo_requisicion="+idInsR;
                    rs = s.executeQuery(sql4);
                    
                    
                    if(rs.first()){
                        BigDecimal cnt = rs.getBigDecimal("cantidad");
                        cantidadUsuada = cantidadUsuada.add(cnt);
                    }
                }
                BigDecimal hayInsumo = aux.getCantidad().subtract(cantidadUsuada);
                if(!(hayInsumo.compareTo(BigDecimal.ZERO) == 0)){
                    TablaInsumoOrdenCompraMB item = new TablaInsumoOrdenCompraMB();
                    item.setCantidad(aux.getCantidad().subtract(cantidadUsuada));
                    item.setCveInsumo(aux.getExpInsumos().getCodInsumo());
                    item.setDescripcion(aux.getExpInsumos().getDescripcion());
                    item.setIdInsumo(aux.getIdinsumoRequisicion());
                    item.setImporte(aux.getPreciounitario().multiply(item.getCantidad()));
                    item.setPrecio(aux.getPreciounitario());
                    item.setUnidad(aux.getExpInsumos().getUnidades());
                    item.setCantidadOriginal(aux.getCantidad().subtract(cantidadUsuada));
                    //System.out.println(item);
                    listResult.add(item);
                }

            }
            
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return listResult;
    }
    
    public  List<FormDetalleOrdenCompraMB>  listarOrdenCompraEstatusAlamacen(int idProyecto){
    
        List<FormDetalleOrdenCompraMB> list = new ArrayList<>();
        
        
        try {
            List<OrdenCompra> listOC = new ArrayList<>();
            OrdenCompra ord;
            
            String sql = "select * from orden_compra as oc "
                    + "inner join requisicion as req on req.id_requisicion = oc.id_requisicion "
                    + "inner join presupuesto as pre on pre.id_presupuesto = req.id_presupuesto "
                    + "inner join proyecto as pro on pro.id_proyecto = pre.id_proyecto "
                    + "and pro.id_proyecto="+idProyecto
                    + " where oc.estatus=\'"+Constantes._LBL_REC_PAR+"\' or oc.estatus=\'"+Constantes._LBL_ALM+"\'";
            
            //System.out.println(sql);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);

            while(rs.next()) {
                ord = new OrdenCompra();
                ord.setIdordenCompra(rs.getInt("idorden_compra"));
                ord.setFechaSolicitud(rs.getString("fechaSolicitud"));
                ord.setFechaAutorizacion(rs.getString("fechaAutorizacion"));
                ord.setEmbarque(rs.getString("embarque"));
                ord.setIva(rs.getInt("iva"));
                ord.setPago(rs.getString("pago"));
                ord.setComentarios("comentarios");
                ord.setEstatus("estatus");
                ord.setIdUsuarioSolicitante(rs.getInt("idUsuarioSolicitante"));
                ord.setIdUsuarioAutorizacion(rs.getInt("idUsuarioAutorizacion"));
                ord.setRequisicion(agregarRequisicion(rs.getInt("id_requisicion")));
                ord.setProveedor(agregarProveedor(rs.getInt("idProveedor")));
                listOC.add(ord);
            }
            
            for(OrdenCompra aux: listOC){
                FormDetalleOrdenCompraMB item = new FormDetalleOrdenCompraMB();
                item.setIdOrdenCompra(aux.getIdordenCompra());
                item.setIdProveedor(aux.getProveedor().getIdProveedor());
                item.setIdProyecto(aux.getRequisicion().getPresupuesto().getProyecto().getIdProyecto());
                item.setNombreProvedor(aux.getProveedor().getRazonSocial());
                item.setNombreProyecto(aux.getRequisicion().getPresupuesto().getProyecto().getProyecto());
                item.setRequisicion(aux.getRequisicion().getNoRequisicion());
                list.add(item);
            }

            System.out.println("Tamaño de ordenes de compra para el proyecto " + listOC.size());

            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        
        return list;
    }
    
    public ExpInsumos agregarExpInsumos(int id) {
        ExpInsumos exp = new ExpInsumos();
        int id_exp = 0;
        try {
            String sql = "select * from insumo_requisicion where idinsumo_requisicion="+id;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            id_exp = rs.getInt("idExpinsumos");
            String sql2 = "select * from exp_insumos where idExpinsumos="+id_exp;
            rs = s.executeQuery(sql2);
            rs.first();
            exp.setIdExpinsumos(id_exp);
            exp.setTotal(rs.getBigDecimal("total"));
            exp.setDescripcion(rs.getString("descripcion"));
            exp.setCodInsumo(rs.getString("codInsumo"));
            exp.setUnidades(rs.getString("unidades"));
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return exp;
    }
    
    public List<FormDetalleOrdenCompraMB> listarOrdenCompraPorIdProyecto(int idProyecto) {
        
        List<FormDetalleOrdenCompraMB> list = new ArrayList<>();
        FormDetalleOrdenCompraMB item;
        
        try {
            String sql = "Select * from orden_compra as oc "
                    + "inner join requisicion as req on req.id_requisicion = oc.id_requisicion "
                    + "inner join presupuesto as pre on pre.id_presupuesto = req.id_presupuesto "
                    + "inner join proyecto as pro on pro.id_proyecto = pre.id_proyecto "
                    + "and pro.id_proyecto="+idProyecto+ 
                " where oc.estatus=\'"+Constantes._LBL_AUT+"\'"
                +" or oc.estatus=\'"+Constantes._LBL_REC_PAR+"\'";
            
            
        
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);

            while(rs.next()) {
                item = new FormDetalleOrdenCompraMB();
                item.setIdOrdenCompra(rs.getInt("idorden_compra"));
                item.setIdProveedor(rs.getInt("idProveedor"));
                item.setIdProyecto(idProyecto);
                item.setNombreProvedor(agregarRazon(item.getIdProveedor()));
                item.setNombreProyecto(rs.getString("proyecto"));
                item.setRequisicion(rs.getInt("noRequisicion"));
                list.add(item);
            }
            
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        //System.out.println("Tamaño de ordenes de compra para el proyecto " + list.size());
        
        
        
        return list;
    }
    
    public String agregarRazon(int id) {
        String razon = "";
        
        try {
            
            String sql = "Select razonSocial from proveedor where idProveedor="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            razon = rs.getString("razonSocial");
            
            
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return razon;
    }
    
    public void generarAnticipo(AnticipoBean anticipo) {
        
        try {
            String sql = "insert into anticipo(id_contrato, importe, contratista, tipo, iva, total, pctAnt)"
                    + "values (?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, anticipo.getId_contrato());
            ps.setBigDecimal(2, anticipo.getImporte());
            ps.setString(3, anticipo.getContratista());
            ps.setString(4, anticipo.getTipo());
            ps.setBigDecimal(5, anticipo.getIva());
            ps.setBigDecimal(6, anticipo.getTotal());
            ps.setBigDecimal(7, anticipo.getPctAnt());
            ps.executeUpdate();
            
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    public int generarOrdenCompra(OrdenCompra oc, List<FormTablaInsumoOrdenCompraMB> listInsumos) {
        
        
        Integer idOrdenCompra = -1;
        try {
            
            con.setAutoCommit(false);
            
            Set <InsumoRequisicion> insumos = new HashSet<>();
            
            for(FormTablaInsumoOrdenCompraMB aux:listInsumos ) {
                Integer id = aux.getInsumoRequisicion();
                
                String sql = "select * from insumo_requisicion where idinsumo_requisicion="+id;
                Statement s = con.createStatement();
                ResultSet rs = s.executeQuery(sql);
                rs.first();
                InsumoRequisicion item = new InsumoRequisicion();
                item.setIdinsumoRequisicion(id);
                item.setCantidad(rs.getBigDecimal("cantidad"));
                item.setPreciounitario(aux.getPrecioUnitario());
                item.setRequisicion(agregarRequisicion(rs.getInt("id_requisicion")));
                item.setExpInsumos(agreagarExpInsumos(rs.getInt("idExpinsumos")));
                
                
                insumos.add(item);
                
            }
            
            oc.getRequisicion().setInsumoRequisicions(insumos);
            
            String sql2 = "insert into orden_compra(fechaSolicitud, fechaAutorizacion, embarque, iva, pago, comentarios, "
                    + "estatus, idUsuarioSolicitante, id_requisicion, idProveedor)"
                    + "values(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql2);
            ps.setString(1, oc.getFechaSolicitud());
            ps.setString(2, oc.getFechaAutorizacion());
            ps.setString(3, oc.getEmbarque());
            ps.setInt(4, oc.getIva());
            ps.setString(5, oc.getPago());
            ps.setString(6, oc.getComentarios());
            ps.setString(7, oc.getEstatus());
            ps.setInt(8, oc.getIdUsuarioSolicitante());
            //ps.setInt(9, oc.getIdUsuarioAutorizacion());
            ps.setInt(9, oc.getRequisicion().getIdRequisicion());
            ps.setInt(10, oc.getProveedor().getIdProveedor());
            ps.executeUpdate();
            
            String sql3 = "select last_insert_id()";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql3);
            rs.first();
            idOrdenCompra = rs.getInt(1);
            
            //System.out.println("Uno: " + oc.getIdordenCompra());
            
            
            
            String sql4 = "update requisicion set estatus=\'"+ Constantes._LBL_COM+"\' where id_requisicion="
                    +oc.getRequisicion().getIdRequisicion();
            s.executeUpdate(sql4);
            
            String sql5 = "";
            
            //System.out.println("ID GENERADO: " + idOrdenCompra);
            con.commit();
            
            //System.out.println("EXITO .....");
            con.close();
        } catch(SQLException e) {
            System.out.println("ERROR xxxxx");
            e.printStackTrace();
            try {
                con.rollback();
            }catch(SQLException ex) {
                ex.printStackTrace();
            }
        }
        
        
        return idOrdenCompra;
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
            r.setInsumoRequisicions(agregarInsumoRequisicions(rs.getInt("id_requisicion")));
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return r;
    }
    
    public Set<InsumoRequisicion> agregarInsumoRequisicions(int id) {
        
        Set<InsumoRequisicion> insumos = new HashSet<>();
        InsumoRequisicion ir;
        
        try {
            String sql = "select * from insumo_requisicion where id_requisicion="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                ir = new InsumoRequisicion();
                ir.setIdinsumoRequisicion(rs.getInt("idinsumo_requisicion"));
                ir.setCantidad(rs.getBigDecimal("cantidad"));
                ir.setPreciounitario(rs.getBigDecimal("preciounitario"));
                ir.setExpInsumos(agreagarExpInsumos(rs.getInt("idExpinsumos")));
                insumos.add(ir);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return insumos;
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
    
    public List<FormOrdenCompraMB> listarOrdenesCompra() {
        
        
        List<FormOrdenCompraMB> list = new ArrayList<>();
        List<OrdenCompra> ocList = new ArrayList<>();
        OrdenCompra oc;
        try {
            
            String sql = "select * from orden_compra";
            /*where estatus =\'"+Constantes._LBL_AUT+"\' or estatus =\'"+Constantes._LBL_PTE_X_AUT
                + "\' or estatus =\'"+Constantes._LBL_CAN+"\'*/
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                oc = new OrdenCompra();
                oc.setIdordenCompra(rs.getInt("idorden_compra"));
                oc.setFechaSolicitud(rs.getString("fechaAutorizacion"));
                oc.setFechaSolicitud(rs.getString("fechaSolicitud"));
                oc.setEmbarque(rs.getString("embarque"));
                oc.setIva(rs.getInt("iva"));
                oc.setPago(rs.getString("pago"));
                oc.setComentarios(rs.getString("comentarios"));
                oc.setEstatus(rs.getString("estatus"));
                oc.setIdUsuarioSolicitante(rs.getInt("idUsuarioSolicitante"));
                oc.setRequisicion(agregarRequisicion(rs.getInt("id_requisicion")));
                oc.setProveedor(agregarProveedor(rs.getInt("idProveedor")));
                ocList.add(oc);
            }
            
            
            System.out.println("no de ordenes de compra: " + ocList.size());

            for (OrdenCompra aux : ocList) {
            
                String sql2 = "select nombre from usuarios where idUsuario ="+aux.getIdUsuarioSolicitante();
                rs = s.executeQuery(sql2);
                rs.first();
                String user = rs.getString("nombre");
                
                FormOrdenCompraMB item = new FormOrdenCompraMB();
                item.setDirEmbarque(aux.getEmbarque());
                item.setFechaSolicitud(aux.getFechaSolicitud());
                item.setFormaPago(aux.getPago());
                item.setIdProveedor(aux.getProveedor().getIdProveedor());
                item.setIva(aux.getIva());
                item.setNoOrdenCompra(aux.getIdordenCompra());
                item.setProveedor(aux.getProveedor().getRazonSocial());
                item.setEstatus(aux.getEstatus());
                item.setUsuarioSolicitante(user);
                list.add(item);
            }
            
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    public List<FormOrdenCompraMB> listarOrdenesCompraXPres(int id_pres) {
        
        
        List<FormOrdenCompraMB> list = new ArrayList<>();
        List<OrdenCompra> ocList = new ArrayList<>();
        OrdenCompra oc;
        try {
            
            String sql = "select oc.idorden_compra, oc.fechaSolicitud, oc.fechaAutorizacion, oc.embarque, oc.iva, "
                    + "oc.pago, oc.comentarios, oc.estatus, oc.idUsuarioSolicitante, " 
                    + "oc.idUsuarioAutorizacion, oc.id_requisicion, oc.idProveedor from orden_compra as oc " 
                    + "inner join requisicion as req on req.id_requisicion = oc.id_requisicion " 
                    + "inner join presupuesto as pre on pre.id_presupuesto = req.id_presupuesto " 
                    + "and pre.id_presupuesto = ?";
            /*where estatus =\'"+Constantes._LBL_AUT+"\' or estatus =\'"+Constantes._LBL_PTE_X_AUT
                + "\' or estatus =\'"+Constantes._LBL_CAN+"\'*/
            //System.out.println(sql);
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id_pres);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                oc = new OrdenCompra();
                oc.setIdordenCompra(rs.getInt("idorden_compra"));
                oc.setFechaSolicitud(rs.getString("fechaAutorizacion"));
                oc.setFechaSolicitud(rs.getString("fechaSolicitud"));
                oc.setEmbarque(rs.getString("embarque"));
                oc.setIva(rs.getInt("iva"));
                oc.setPago(rs.getString("pago"));
                oc.setComentarios(rs.getString("comentarios"));
                oc.setEstatus(rs.getString("estatus"));
                oc.setIdUsuarioSolicitante(rs.getInt("idUsuarioSolicitante"));
                oc.setRequisicion(agregarRequisicion(rs.getInt("id_requisicion")));
                oc.setProveedor(agregarProveedor(rs.getInt("idProveedor")));
                ocList.add(oc);
            }
            
            
            //System.out.println("no de ordenes de compra: " + ocList.size());

            for (OrdenCompra aux : ocList) {
            
                String sql2 = "select nombre from usuarios where idUsuario ="+aux.getIdUsuarioSolicitante();
                ps = con.prepareStatement(sql2);
                rs = ps.executeQuery();
                rs.first();
                String user = rs.getString("nombre");
                
                FormOrdenCompraMB item = new FormOrdenCompraMB();
                item.setDirEmbarque(aux.getEmbarque());
                item.setFechaSolicitud(aux.getFechaSolicitud());
                item.setFormaPago(aux.getPago());
                item.setIdProveedor(aux.getProveedor().getIdProveedor());
                item.setIva(aux.getIva());
                item.setNoOrdenCompra(aux.getIdordenCompra());
                item.setProveedor(aux.getProveedor().getRazonSocial());
                item.setEstatus(aux.getEstatus());
                item.setUsuarioSolicitante(user);
                list.add(item);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
    }
    
    public List<FormOrdenCompraMB> listarTodasOrdenesCompra() {
        
        List<FormOrdenCompraMB> list = new ArrayList<>();
        List<OrdenCompra> ocList = new ArrayList<>();
        OrdenCompra oc;
        
        try {
            String sql = "select * from orden_compra";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                oc = new OrdenCompra();
                oc.setIdordenCompra(rs.getInt("idorden_compra"));
                oc.setFechaSolicitud(rs.getString("fechaAutorizacion"));
                oc.setFechaSolicitud(rs.getString("fechaSolicitud"));
                oc.setEmbarque(rs.getString("embarque"));
                oc.setIva(rs.getInt("iva"));
                oc.setPago(rs.getString("pago"));
                oc.setComentarios(rs.getString("comentarios"));
                oc.setEstatus(rs.getString("estatus"));
                oc.setIdUsuarioSolicitante(rs.getInt("idUsuarioSolicitante"));
                oc.setRequisicion(agregarRequisicion(rs.getInt("id_requisicion")));
                oc.setProveedor(agregarProveedor(rs.getInt("idProveedor")));
                ocList.add(oc);
            }
            
            System.out.println("no de ordenes de compra: " + ocList.size());

            for (OrdenCompra aux : ocList) {
                String sql2 = "select nombre from usuarios where idUsuario ="+aux.getIdUsuarioSolicitante();
                rs =  s.executeQuery(sql2);
                rs.first();
                String user = rs.getString("nombre");
                
                String sql3 = "select nombre from usuarios where idUsuario ="+aux.getIdUsuarioSolicitante();
                rs =  s.executeQuery(sql3);
                rs.first();
                String lUser = rs.getString("nombre");

                String user2;
                if(lUser.isEmpty())
                    user2 = "SI USUARIO";
                else
                    user2 = lUser;


                FormOrdenCompraMB item = new FormOrdenCompraMB();
                item.setDirEmbarque(aux.getEmbarque());
                item.setFechaSolicitud(aux.getFechaSolicitud());
                item.setFormaPago(aux.getPago());
                item.setIdProveedor(aux.getProveedor().getIdProveedor());
                item.setIva(aux.getIva());
                item.setNoOrdenCompra(aux.getIdordenCompra());
                item.setProveedor(aux.getProveedor().getRazonSocial());
                item.setEstatus(aux.getEstatus());
                item.setUsuarioSolicitante(user);
                item.setUsuarioAutorizacion(user2);

                list.add(item);
            }
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
        }

        
        return list;
    }
    
    public List<DetalleOrdenCompraMB> detallesOrdenCompra(int idOrdenCompra) {
        
        List<DetalleOrdenCompraMB> dOcInsumosList = new ArrayList<>();
        List<OrdenCompra> list = new ArrayList<>();
        OrdenCompra oc;
        
        try {
            String sql = "select * from orden_compra where idorden_compra ="+idOrdenCompra;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                oc = new OrdenCompra();
                oc.setFechaSolicitud(rs.getString("fechaAutorizacion"));
                oc.setFechaSolicitud(rs.getString("fechaSolicitud"));
                oc.setEmbarque(rs.getString("embarque"));
                oc.setIva(rs.getInt("iva"));
                oc.setPago(rs.getString("pago"));
                oc.setComentarios(rs.getString("comentarios"));
                oc.setEstatus(rs.getString("estatus"));
                oc.setIdUsuarioSolicitante(rs.getInt("idUsuarioSolicitante"));
                oc.setRequisicion(agregarRequisicion(rs.getInt("id_requisicion")));
                oc.setProveedor(agregarProveedor(rs.getInt("idProveedor")));
                
                list.add(oc);
            }
            
            System.out.println("Tamaño: " + list.size());
            int i = 0;
            for (OrdenCompra aux : list) {
                Set<InsumoRequisicion> insumos = aux.getRequisicion().getInsumoRequisicions();
                for (InsumoRequisicion au : insumos) {
                    DetalleOrdenCompraMB item = new DetalleOrdenCompraMB();
                    item.setCantidadSolicitada(au.getCantidad());
                    item.setConcepto(au.getExpInsumos().getDescripcion());
                    item.setIdControl(++i);
                    item.setNoRequisicion(aux.getRequisicion().getNoRequisicion());
                    item.setUnidad(au.getExpInsumos().getUnidades());
                    item.setPrecioUnitario(au.getPreciounitario());
                    dOcInsumosList.add(item);
                }
            }
            
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
        }

        return dOcInsumosList;
    }
    
    public boolean autorizacionOrdenCompra(List<Integer> listaIdOrdenCompra, int idUsuario){
        
        boolean result = true;
        
        try{
            Date fecha = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy H:m:s");
            String fechaAutorizacion = sdf.format(fecha);
            
            for(int aux: listaIdOrdenCompra){
                
                String sql = "update orden_compra set estatus=?, fechaAutorizacion=?, "
                        + "idUsuarioAutorizacion=?  where idorden_compra="+aux;
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, Constantes._LBL_AUT);
                ps.setString(2, fechaAutorizacion);
                ps.setInt(3, idUsuario);
                ps.executeUpdate();
                
                                
                System.out.println("ID OC autorizada: " + aux);
            }
            
            
        } catch (SQLException e) {
            result = false;
            e.printStackTrace();
            try {
                con.rollback();
            }catch(SQLException ex) {
                ex.printStackTrace();
            }
            
            
        }
        
        return result;
     }
     
     public boolean cancelarOrdenCompra(List <Integer>  idOrdenOrdenCompra){
        boolean resultado = false;
        
        try {
            
            con.setAutoCommit(false);
            
            for (int aux : idOrdenOrdenCompra) {
                
                String sql = "update orden_compra set estatus=? where idorden_compra=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, Constantes._LBL_CAN);
                ps.setInt(2, aux);
                ps.executeUpdate();
                
                String sql3 = "select id_requisicion from orden_compra where idorden_compra="+aux;
                Statement s = con.createStatement();
                ResultSet rs = s.executeQuery(sql3);
                rs.first();
                int idReq = rs.getInt("id_requisicion");
                
                String sql2 = "update requisicion set estatus=? where id_requisicion=?";
                ps = con.prepareStatement(sql2);
                ps.setString(1, Constantes._LBL_CAN);
                ps.setInt(2, idReq);
                ps.executeUpdate();
                
            }
            resultado = true;
            con.commit();
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            
            try {
                con.rollback();
            }catch(SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

        return resultado;
    }
    
    public Proveedor agregarProveedor(int id) {
        
        Proveedor prov = new Proveedor();
        
        try {
            String sql = "select * from proveedor where idProveedor="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            prov.setIdProveedor(id);
            prov.setRazonSocial(rs.getString("razonSocial"));
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return prov;
    }
    
}
