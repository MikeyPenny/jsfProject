/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.mbean.FormListDevolucion;
import com.mbean.TablaInsumoDevolucion;
import com.mbean.TablaInsumoOrdenCompraMB;
import com.model.Devolucion;
import com.model.DevolucionInsumo;
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
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class DevolucionBS {
    
    Connection con;
    
    public DevolucionBS(Connection con) {
        this.con = con;
    }
    
    public int guardarDevolucion(List<TablaInsumoOrdenCompraMB> listInsumosDevolucion, Map<String, Object> datosDevolucion) {
        
        
        int noDevolucion = -1;
        int result = 0;
        try {
            con.setAutoCommit(false);
            int idProyecto = Integer.parseInt(datosDevolucion.get("idProyecto").toString());
            String sql = "select max(noDevolucion) as noDevolucion from devolucion as d " +
                        "inner join recepcion as rec on rec.idRecepcion = d.idRecepcion " +
                        "inner join orden_compra as ord on ord.idorden_compra = rec.idorden_compra " +
                        "inner join requisicion as req on req.id_requisicion = ord.id_requisicion " +
                        "inner join presupuesto as pre on pre.id_presupuesto = req.id_presupuesto " +
                        "inner join proyecto as pro on pro.id_proyecto = pre.id_proyecto " +
                        "and pro.id_proyecto ="+idProyecto;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            result = rs.getInt(1);

            if (result == 0) {
                noDevolucion = 1;
            } else {
                noDevolucion = result + 1;
            }

            Integer idRecepcion = Integer.parseInt(datosDevolucion.get("idRecepcion").toString());
            System.out.println("IdRECEPCION: " + idRecepcion);
            //Recepcion r = (Recepcion) session.load(Recepcion.class, idRecepcion);
            Recepcion r = new Recepcion();
            String sql2 = "Select * from recepcion where idRecepcion="+idRecepcion;
            rs = s.executeQuery(sql2);
            rs.first();
            r.setIdRecepcion(idRecepcion);
            r.setEstatus(rs.getString("estatus"));
            r.setNoRecepcion(rs.getInt("noRecepcion"));
            r.setFechaRecepcion(rs.getString("fechaRecepcion"));
            r.setOrdenCompra(agregarOrdenCompra(rs.getInt("idorden_compra")));
            
            System.out.println("Rece: "+ r.getIdRecepcion());
            Devolucion devolucion = new Devolucion();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy H:m:s");
            Date fecha = new Date();
            devolucion.setFechaDevolucion(sdf.format(fecha));
            devolucion.setNoDevolucion(noDevolucion);
            devolucion.setRecepcion(r);
            
            String sql3 = "Insert into devolucion(fechaDevolucion, noDevolucion, idRecepcion)values (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql3);
            ps.setString(1, devolucion.getFechaDevolucion());
            ps.setInt(2, devolucion.getNoDevolucion());
            ps.setInt(3, devolucion.getRecepcion().getIdRecepcion());
            ps.executeUpdate();
            
            String sql4 = "select max(id_devolucion) from devolucion";
            rs = s.executeQuery(sql4);
            rs.first();
            
            Integer idDevolucion = rs.getInt(1);
            
            System.out.println("ID Devolucion: " + idDevolucion);
            
            BigDecimal vacio = new BigDecimal(BigInteger.ZERO);
            for (TablaInsumoOrdenCompraMB aux : listInsumosDevolucion) {
                String sql5 = "select * from recepcioninsumo where idrecepcionInsumo="+aux.getIdRecepcionInsumo();
                rs = s.executeQuery(sql5);
                rs.first();

                Recepcioninsumo insumo = new Recepcioninsumo();
                insumo.setCantidad(rs.getBigDecimal("cantidad"));
                insumo.setPrecio(rs.getBigDecimal("precio"));
                insumo.setInsumoRequisicion(agregarInsRequisicion(rs.getInt("idinsumo_requisicion")));
                System.out.println("idRecInsu: " + insumo.getIdrecepcionInsumo() +  "IDInsumos: " + aux.getIdInsumo() + ", Cantidad: " + insumo.getCantidad() + " - " + aux.getCantidad());

                BigDecimal cantidad = new BigDecimal(insumo.getCantidad().toString());
                BigDecimal cantidadActual = cantidad.subtract(aux.getCantidad());

                insumo.setCantidad(cantidadActual);
                //session.merge(insumo);
                vacio = insumo.getCantidad().add(vacio);
                DevolucionInsumo item = new DevolucionInsumo();
                item.setCantidad(aux.getCantidad());
                item.setDevolucion(devolucion);
                item.setRecepcioninsumo(insumo);
                
                String sql6 = "Insert into devolucion_insumo(cantidad, id_devolucion, idrecepcionInsumo)values (?,?,?)";
                ps = con.prepareStatement(sql6);
                ps.setBigDecimal(1, item.getCantidad());
                ps.setInt(2, idDevolucion);
                ps.setInt(3, aux.getIdRecepcionInsumo());
                ps.executeUpdate();
                
            }
            
            if(vacio.compareTo(BigDecimal.ZERO) == 0){
                r.setEstatus(Constantes._LBL_DEV_TOT);
                String sql7 = "Update recepcion set estatus=\'"+r.getEstatus()+"\'";
                s.executeUpdate(sql7);
            }
            
            con.commit();
            con.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            }catch(SQLException ex) {
                ex.printStackTrace();
            }
        }
        
        return noDevolucion;
    }
    
    public InsumoRequisicion agregarInsRequisicion(int id) {
        InsumoRequisicion ir = new InsumoRequisicion();
        
        try {
            
            String sql = "select * from insumo_requisicion where idinsumo_requisicion="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            ir.setCantidad(rs.getBigDecimal("cantidad"));
            ir.setPreciounitario(rs.getBigDecimal("preciounitario"));
            ir.setRequisicion(agregarRequisicion(rs.getInt("id_requisicion")));
            ir.setExpInsumos(agregarExpInsumos(rs.getInt("idExpinsumos")));
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        
        
        return ir;
    }
    
    public ExpInsumos agregarExpInsumos(int id) {
        ExpInsumos exp = new ExpInsumos();
        int id_exp = 0;
        try {
            
            String sql2 = "select * from exp_insumos where idExpinsumos="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql2);
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
    
    public OrdenCompra agregarOrdenCompra(int id) {
        OrdenCompra orden = new OrdenCompra();
        
        try {
            String sql = "Select * from orden_compra where idorden_compra="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            orden.setIdordenCompra(id);
            orden.setFechaSolicitud(rs.getString("fechaSolicitud"));
            orden.setFechaAutorizacion(rs.getString("fechaAutorizacion"));
            orden.setEmbarque(rs.getString("embarque"));
            orden.setIva(rs.getInt("iva"));
            orden.setPago(rs.getString("pago"));
            orden.setComentarios(rs.getString("comentarios"));
            orden.setEstatus(rs.getString("estatus"));
            orden.setIdUsuarioSolicitante(rs.getInt("idUsuarioSolicitante"));
            orden.setIdUsuarioAutorizacion(rs.getInt("idUsuarioAutorizacion"));
            orden.setRequisicion(agregarRequisicion(rs.getInt("id_requisicion")));
            orden.setProveedor(agregarProveedor(rs.getInt("idProveedor")));
            
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return orden;
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
    
    public List<FormListDevolucion> listarDevoluciones(){
        
                   
         List<FormListDevolucion> listaDevolucion = new ArrayList<>();
         List<Devolucion> list = new ArrayList<>();
         Devolucion dev;
         
         try {
             String sql = "select * from devolucion";
             Statement s = con.createStatement();
             ResultSet rs = s.executeQuery(sql);
             while(rs.next()) {
                dev = new Devolucion();
                dev.setIdDevolucion(rs.getInt("id_devolucion"));
                dev.setFechaDevolucion(rs.getString("fechaDevolucion"));
                dev.setNoDevolucion(rs.getInt("noDevolucion"));
                dev.setRecepcion(agregaRecepcion(rs.getInt("idRecepcion")));
                list.add(dev);
             }
             
             for(Devolucion aux: list){
                FormListDevolucion item = new FormListDevolucion();
                item.setFechaDevolucion(aux.getFechaDevolucion());
                item.setIdDevolucion(aux.getIdDevolucion());
                item.setNoDevolucion(aux.getNoDevolucion());
                item.setNombreProveedor(aux.getRecepcion().getOrdenCompra().getProveedor().getRazonSocial());

                listaDevolucion.add(item);
            }
             
             con.close();
             
         }catch(SQLException e) {
             e.printStackTrace();
         }
         
         return listaDevolucion;
         
    }
    
    public Recepcion agregaRecepcion(int id) {
        
        Recepcion r = new Recepcion();
        
        try {
            
            String sql = "select * from recepcion where idRecepcion="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            r.setIdRecepcion(rs.getInt("idRecepcion"));
            r.setEstatus(rs.getString("estatus"));
            r.setNoRecepcion(rs.getInt("noRecepcion"));
            r.setFechaRecepcion(rs.getString("fechaRecepcion"));
            r.setOrdenCompra(agregarOrdenCompra(rs.getInt("idorden_compra")));
            
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return r;
    }
    
    public List<TablaInsumoDevolucion> listarDetallesDevolucionPorIdDevolucion(int idDevolucion){
        
        List<TablaInsumoDevolucion> lista = new ArrayList<>();
        TablaInsumoDevolucion tab;
        
        try {
            
            String sql = "select * from devolucion_insumo where id_devolucion="+idDevolucion;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                tab =  new TablaInsumoDevolucion();
                tab.setIdInsumoDevolucion(rs.getInt("id_devolucion_insumo"));
                tab.setCantidadDevuelta(rs.getBigDecimal("cantidad"));
                String[] d = nombreInsumo(rs.getInt("id_devolucion_insumo"));
                tab.setNombreInsumo(d[0]);
                tab.setUnidad(d[1]);
                lista.add(tab);
            }
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
                
        return lista;
    
    }
    
    public String[] nombreInsumo(int id) {
        
        String[] dat = new String[2];
        
        try {
            
            String sql = "select distinct * from exp_insumos as exp, insumo_requisicion as ir, recepcioninsumo as ri, "
                    + "devolucion_insumo as di where exp.idExpinsumos = ir.idExpInsumos " 
                    + "and ir.idinsumo_requisicion = ri.idinsumo_requisicion " 
                    + "and ri.idrecepcionInsumo = di.idrecepcionInsumo " 
                    + "and di.id_devolucion_insumo="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            dat[0] = rs.getString("descripcion");
            dat[1] = rs.getString("unidades");
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return dat;
    }
    
}
