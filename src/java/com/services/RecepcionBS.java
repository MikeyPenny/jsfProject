/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.mbean.FormListRecepcionMB;
import com.mbean.TablaInsumoOrdenCompraMB;
import com.mbean.TablaInsumosRecepcion;
import com.model.ExpInsumos;
import com.model.InsumoRequisicion;
import com.model.OrdenCompra;
import com.model.Presupuesto;
import com.model.Proveedor;
import com.model.Proyecto;
import com.model.Recepcion;
import com.model.Recepcioninsumo;
import com.model.Requisicion;
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


public class RecepcionBS {
    
    Connection con;
    
    public RecepcionBS(Connection con) {
        this.con = con;
    }
    
    public int autorizarRecepcion(int idProyecto, Recepcion recepcion, List<Recepcioninsumo> listaInsumos, boolean isTotal) {
        
        Integer idRecepcion = -1;
        Integer noRecepcion = -1;
        
        try {
           
           con.setAutoCommit(false);
            
           //Obtener el siguiente número de recepción por proyecto 
           String sql = "select max(noRecepcion) as noRecepcion from recepcion as r " +
                    "inner join orden_compra as ord on ord.idorden_compra = r.idorden_compra " +
                    "inner join requisicion as req on req.id_requisicion = ord.id_requisicion " +
                    "inner join presupuesto as pre on  pre.id_presupuesto = req.id_presupuesto " +
                    "inner join proyecto as pro on pro.id_proyecto = pre.id_proyecto " +
                    "and pro.id_proyecto ="+idProyecto+ " for update";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            int result = rs.getInt("noRecepcion");
            System.out.println("RECEPCION: "+result);
            
            if (result == 0) {
                noRecepcion = 1;
            } else {
                noRecepcion = result+1;
            }
            Date fecha = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy H:m:s");
            recepcion.setFechaRecepcion(sdf.format(fecha));
            recepcion.setNoRecepcion(noRecepcion);
            
            String sql5= "Insert into recepcion(estatus, noRecepcion, fechaRecepcion, idorden_compra)values (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql5);
            ps.setString(1, recepcion.getEstatus());
            ps.setInt(2, recepcion.getNoRecepcion());
            ps.setString(3, recepcion.getFechaRecepcion());
            ps.setInt(4, recepcion.getOrdenCompra().getIdordenCompra());
            ps.executeUpdate();
            
            String sql6 = "Select last_insert_id()";
            s = con.createStatement();
            rs = s.executeQuery(sql6);
            rs.first();
            
            idRecepcion = rs.getInt(1);
            System.out.println("ID recepcion: "+idRecepcion);
            
            if (isTotal) {
                String sql2 = "Update orden_compra set estatus=\'"+Constantes._LBL_ALM+"\' where idorden_compra="+recepcion.getOrdenCompra().getIdordenCompra();
                s.executeUpdate(sql2);
            } else {
                String sql3 = "Update orden_compra set estatus=\'"+Constantes._LBL_REC_PAR+"\' where idorden_compra="+recepcion.getOrdenCompra().getIdordenCompra();
                s.executeUpdate(sql3);
            }
            
            String sql4 = "Insert into recepcioninsumo(cantidad, precio, idinsumo_requisicion, idRecepcion, cantidadOriginal)"
                    + "values (?,?,?,?,?)";
            ps = con.prepareStatement(sql4);
            
            for(Recepcioninsumo aux : listaInsumos) {
                ps.setBigDecimal(1, aux.getCantidad());
                ps.setBigDecimal(2, aux.getPrecio());
                ps.setInt(3, aux.getInsumoRequisicion().getIdinsumoRequisicion());
                ps.setInt(4, idRecepcion);
                ps.setBigDecimal(5, aux.getCantidadOriginal());
                ps.executeUpdate();
            } 
            
            con.commit();
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            }catch(SQLException er) {
                er.printStackTrace();
            }
            
        }
        
        if (idRecepcion != -1) {
            return noRecepcion;
        }else {
            return -1;
        }
        
    }
    
    public List<FormListRecepcionMB> listarRecepcionesPorIdOrdenCompra(int idOrdenCompra) {
        
        List<FormListRecepcionMB> listaRecepciones = new ArrayList<>();
        FormListRecepcionMB rec;
        List<Recepcion> list = new ArrayList<>();
        Recepcion recep;
        try {
            
            String sql = "Select * from recepcion where idorden_compra ="+idOrdenCompra;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                recep = new Recepcion();
                recep.setIdRecepcion(rs.getInt("idRecepcion"));
                recep.setEstatus(rs.getString("estatus"));
                recep.setNoRecepcion(rs.getInt("noRecepcion"));
                recep.setFechaRecepcion(rs.getString("fechaRecepcion"));
                recep.setOrdenCompra(agregarOrdenCompra(rs.getInt("idorden_compra")));
                list.add(recep);
            }
            
            for (Recepcion aux : list) {
                FormListRecepcionMB item = new FormListRecepcionMB();
                item.setEstatus(aux.getEstatus());
                item.setFechaRecepcion(aux.getFechaRecepcion());
                item.setIdOrdenCompra(aux.getOrdenCompra().getIdordenCompra());
                item.setNoRecepcion(aux.getNoRecepcion());
                item.setIdRecepcion(aux.getIdRecepcion());
                listaRecepciones.add(item);
            }
            
            
        }catch(SQLException e) {
            e.printStackTrace();
        }

        return listaRecepciones;

    }
    
    public List<TablaInsumoOrdenCompraMB> listarInsumosPorIdRecepcion(int idRecepcion) {
        
        List<Recepcioninsumo> list = new ArrayList<>();
        Recepcioninsumo recIns;
        List<TablaInsumoOrdenCompraMB> listInsumos = new ArrayList<>();
        
        
        try {
            String sql = "select ri.idrecepcionInsumo, ri.idRecepcion, ri.idinsumo_requisicion, ri.cantidad, ri.precio, "
                    + "ri.cantidadOriginal, exp.codInsumo, exp.descripcion, exp.idExpinsumos, exp.unidades "
                    + "from recepcioninsumo as ri, insumo_requisicion as ir, exp_insumos as exp where ri.idRecepcion="+idRecepcion
                    +" and ri.idinsumo_requisicion = ir.idinsumo_requisicion and ir.idExpinsumos = exp.idExpInsumos";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                recIns =  new Recepcioninsumo();
                recIns.setIdrecepcionInsumo(rs.getInt("idrecepcionInsumo"));
                recIns.setRecepcion(agregarRecepcion(rs.getInt("idRecepcion")));
                //recIns.setInsumoRequisicion(agregarInsRequisicion(rs.getInt("idinsumo_requisicion")));
                recIns.setCantidad(rs.getBigDecimal("cantidad").subtract(sumarDevoluciones(recIns.getIdrecepcionInsumo())));
                recIns.setDevolucion(sumarDevoluciones(recIns.getIdrecepcionInsumo()));
                recIns.setPrecio(rs.getBigDecimal("precio"));
                recIns.setCantidadOriginal(rs.getBigDecimal("cantidadOriginal"));
                recIns.setClaveIns(rs.getString("codInsumo"));
                recIns.setDescripcion(rs.getString("descripcion"));
                recIns.setIdExpIns(rs.getInt("idExpInsumos"));
                recIns.setUnidad(rs.getString("unidades"));
                list.add(recIns);
            }
            
            for (Recepcioninsumo aux : list) {
                if (aux.getCantidad().compareTo(BigDecimal.ZERO) > 0){
                    TablaInsumoOrdenCompraMB item = new TablaInsumoOrdenCompraMB();
                    item.setIdRecepcionInsumo(aux.getIdrecepcionInsumo());
                    //item.setCantidad(aux.getCantidad());
                    item.setCantidadOriginal(aux.getCantidadOriginal());
                    item.setCveInsumo(aux.getClaveIns());
                    item.setDescripcion(aux.getDescripcion());
                    item.setIdInsumo(aux.getIdExpIns());
                    item.setImporte(BigDecimal.ZERO);
                    item.setPrecio(aux.getPrecio());
                    item.setUnidad(aux.getUnidad());
                    item.setRecibido(agregarRecibido(aux.getIdExpIns()));
                    item.setConsumido(agregarConsumido(aux.getIdExpIns()));
                    item.setDevuelto(aux.getDevolucion());
                    BigDecimal inv = item.getRecibido().subtract(item.getConsumido());
                    if(aux.getCantidad().floatValue() > inv.floatValue()) {
                        item.setCantidad(inv);
                        item.setCatnCtrl(inv);
                    }else {
                        item.setCantidad(aux.getCantidad());
                        item.setCatnCtrl(aux.getCantidad());
                    }
                    listInsumos.add(item);
                }   
            }
        
        }catch(SQLException e) {
            e.printStackTrace();
        }

        return listInsumos;
    }
    
    public BigDecimal sumarDevoluciones(int id) {
        
        BigDecimal cant = BigDecimal.ZERO;
        
        try {
            String sql = "select sum(cantidad) as cantidad from devolucion_insumo where idrecepcionInsumo="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                cant = rs.getBigDecimal("cantidad");
            }
            
            if(cant == null) {
                cant = BigDecimal.ZERO;
            }
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
            
        return cant;
    }
    
    public BigDecimal agregarRecibido(int id) {
        
        BigDecimal recibido = BigDecimal.ZERO;
        
        try {
            
            String sql = "select sum(ri.cantidad) as recibido from recepcioninsumo ri " +
                        "inner join  insumo_requisicion as ir on ir.idinsumo_requisicion = ri.idinsumo_requisicion " +
                        "inner join exp_insumos as exp on exp.idExpinsumos = ir.idExpinsumos and exp.idExpinsumos ="+id;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            
            recibido = rs.getBigDecimal("recibido");
            
            if(recibido == null) {
                recibido = BigDecimal.ZERO;
            }
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return recibido;
        
    }
    
    public BigDecimal agregarConsumido(int id) {
        BigDecimal consumo = BigDecimal.ZERO;
        
        try {
            String sql = "select sum(ivc.cantidad) as consumo from insumo_vale_consumo as ivc where idExpinsumos="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            consumo = rs.getBigDecimal("consumo");
            
            if(consumo == null)
                consumo = BigDecimal.ZERO;
            
            
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return consumo;
    }
    
    public InsumoRequisicion agregarInsRequisicion(int id) {
        InsumoRequisicion insR = new InsumoRequisicion();
        
        try {
            
            String sql = "select * from insumo_requisicion where idinsumo_requisicion="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            insR.setIdinsumoRequisicion(rs.getInt("idinsumo_requisicion"));
            insR.setExpInsumos(agregarExpInsumos(id));
            insR.setRequisicion(agregarRequisicion(rs.getInt("id_requisicion")));
            insR.setCantidad(rs.getBigDecimal("cantidad"));
            insR.setPreciounitario(rs.getBigDecimal("preciounitario"));
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return insR;
    }
    
    public ExpInsumos agregarExpInsumos(int id) {
        ExpInsumos exp = new ExpInsumos();
        
        try {
            
            String sql2 = "select * from exp_insumos where idExpinsumos="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql2);
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
    
    public Recepcion agregarRecepcion(int id) {
        Recepcion rec =  new Recepcion();
        
        try  {
            
            String sql = "select * from recepcion where idRecepcion="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            rec.setIdRecepcion(id);
            rec.setOrdenCompra(agregarOrdenCompra(rs.getInt("idorden_compra")));
            rec.setEstatus(rs.getString("estatus"));
            rec.setNoRecepcion(rs.getInt("noRecepcion"));
            rec.setFechaRecepcion(rs.getString("fechaRecepcion"));
            
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return rec;
    }
    
    public OrdenCompra agregarOrdenCompra(int id) {
        OrdenCompra oc = new OrdenCompra();
        try {
            String sql = "select * from orden_compra where idorden_compra="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            oc.setIdordenCompra(rs.getInt("idorden_compra"));
            oc.setProveedor(agregarProveedor(rs.getInt("idProveedor")));
            oc.setRequisicion(agregarRequisicion(rs.getInt("id_requisicion")));
            oc.setFechaSolicitud(rs.getString("fechaSolicitud"));
            oc.setFechaAutorizacion(rs.getString("fechaAutorizacion"));
            oc.setEmbarque(rs.getString("embarque"));
            oc.setIva(rs.getInt("iva"));
            oc.setPago(rs.getString("pago"));
            oc.setComentarios(rs.getString("comentarios"));
            oc.setEstatus(rs.getString("estatus"));
            oc.setIdUsuarioSolicitante(rs.getInt("idUsuarioSolicitante"));
            oc.setIdUsuarioAutorizacion(rs.getInt("idUsuarioAutorizacion"));
            
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return oc;
    }
    
    public Requisicion agregarRequisicion(int id_req) {
        
        Requisicion req = new Requisicion();
        
        try {
            String sql2 = "select id_presupuesto from requisicion where id_requisicion="+id_req;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql2);
            rs.first();
            int id_pres = rs.getInt("id_presupuesto");
            
            String sql = "select * from requisicion where id_requisicion="+id_req;
            rs = s.executeQuery(sql);
            rs.first();
            req.setIdRequisicion(rs.getInt("id_requisicion"));
            req.setPresupuesto(agregarPresupuesto(id_pres));
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return req;
    }
    
    public Presupuesto agregarPresupuesto(int id) {
        
        Presupuesto pres = new Presupuesto();
        
        try {
            
            String sql2 = "select id_proyecto from presupuesto where id_presupuesto="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql2);
            rs.first();
            int id_proy = rs.getInt("id_proyecto");
            
            String sql = "select * from presupuesto where id_presupuesto="+id;
            s = con.createStatement();
            rs = s.executeQuery(sql);
            rs.first();
            
            pres.setIdPresupuesto(rs.getInt("id_presupuesto"));
            pres.setPresupuesto(rs.getString("presupuesto"));
            pres.setProyecto(agregarProyecto(id_proy));
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return pres;
    }
    
    public Proyecto agregarProyecto(int id) {
        
        Proyecto proy = new Proyecto();;
        
        try {
            String sql = "select * from proyecto where id_proyecto="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            
            proy.setIdProyecto(rs.getInt("id_proyecto"));
            proy.setProyecto(rs.getString("proyecto"));
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return proy;
    }
    
    public Proveedor agregarProveedor(int id) {
        Proveedor pro = new Proveedor();
        
        try {
            String sql = "Select * from proveedor where idProveedor="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                
                pro.setIdProveedor(id);
                pro.setRazonSocial(rs.getString("razonSocial"));
                pro.setDirFiscal(rs.getString("dirFiscal"));
                pro.setRfc(rs.getString("rfc"));
                pro.setTelefono(rs.getString("telefono"));
                pro.setEmail(rs.getString("email"));
                pro.setDiasCredito(rs.getInt("diasCredito"));
                pro.setNombreContacto(rs.getString("nombreContacto"));
                pro.setNombreBanco(rs.getString("nombreBanco"));
                pro.setEstatus(rs.getInt("estatus"));
                
            }
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return pro;
    }
    
    public List<FormListRecepcionMB> listarRecepciones() {
        
        List<FormListRecepcionMB> listaRecepciones = new ArrayList<FormListRecepcionMB>();
        List<Recepcion> list = new ArrayList<>();
        Recepcion r;
        
        try {
            
            String sql = "select * from recepcion";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                r = new Recepcion();
                r.setIdRecepcion(rs.getInt("idRecepcion"));
                r.setEstatus(rs.getString("estatus"));
                r.setNoRecepcion(rs.getInt("noRecepcion"));
                r.setFechaRecepcion(rs.getString("fechaRecepcion"));
                r.setOrdenCompra(agregarOrdenCompra(rs.getInt("idorden_compra")));
                list.add(r);
            }
            
            for (Recepcion aux : list) {
                FormListRecepcionMB item = new FormListRecepcionMB();
                item.setEstatus(aux.getOrdenCompra().getEstatus());
                item.setFechaRecepcion(aux.getFechaRecepcion());
                item.setIdOrdenCompra(aux.getOrdenCompra().getIdordenCompra());
                item.setNoRecepcion(aux.getNoRecepcion());
                item.setIdRecepcion(aux.getIdRecepcion());
                item.setProveedor(aux.getOrdenCompra().getProveedor().getRazonSocial());
                listaRecepciones.add(item);
            }

            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
        }

        return listaRecepciones;

    }
    
    public  List<TablaInsumosRecepcion> listarDetallesRecepcionPorIdRecepcion(int idRecepcion){
        
        List<TablaInsumosRecepcion> result = new ArrayList<>();
        TablaInsumosRecepcion item;
        
        try {
            
            String sql = "select * from recepcioninsumo where idRecepcion="+idRecepcion;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                item =  new TablaInsumosRecepcion();
                item.setCantidadRecepcionada(rs.getBigDecimal("cantidad"));
                item.setIdInsumoRecepcion(rs.getInt("idrecepcionInsumo"));
                String[] d = nombreInsumo(rs.getInt("idinsumo_requisicion"));
                item.setNombreInsumo(d[0]);
                item.setUnidad(d[1]);
                result.add(item);
                
            }
            
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        
        return  result;
    }
    
    public String[] nombreInsumo(int id) {
        String[] dat = new String[2];
        
        try {
            
            String sql = "select idExpinsumos from insumo_requisicion where idinsumo_requisicion="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            int idExp = rs.getInt("idExpinsumos");
            
            String sql2 = "select descripcion, unidades from exp_insumos where idExpinsumos="+idExp;
            rs = s.executeQuery(sql2);
            rs.first();
            dat[0] = rs.getString("descripcion");
            dat[1] = rs.getString("unidades");
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return dat;
    }
    
}
