
package com.dao;

import com.bean.Catalogo;
import com.bean.Concepto;
import com.bean.Concepts;
import com.bean.FamConcepto;
import com.bean.InsumoBean;
import com.bean.InsumoExplosion;
import com.bean.MaterialBean;
import com.bean.Nivel;
import com.bean.Partida;
import com.bean.PartidaBean;
import com.bean.PartidaCatalogo;
import com.bean.PresupuestoBean;
import com.bean.ProyectoSimple;
import com.bean.TipoPresupto;
import com.bean.Unidad;
import com.util.CadenasCortas;
import com.util.FormatoString;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class PresupuestoDAO {
    
    Connection con; 
    
    public PresupuestoDAO(Connection con) {
        this.con = con;
    }
    
    public boolean editarConcepto(Concepts ccp, List<InsumoBean> add, List<InsumoBean> edit, List<InsumoBean> del) {
        
        boolean ed;
        
        try {
            
            con.setAutoCommit(false);
            
            String sql = "update concepto set descripcion=?, unidad=?, cantidad=?, pUnitario=?, importe=? "
                    + "where id_concepto=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ccp.getDescripcion());
            ps.setString(2, ccp.getUnidad());
            ps.setBigDecimal(3, ccp.getCantidad());
            ps.setBigDecimal(4, ccp.getpUnitario());
            ps.setBigDecimal(5, ccp.getImporte());
            ps.setInt(6, ccp.getId_concepto());
            ps.executeUpdate();
            
            if(add.size() > 0) {
                sql = "insert into insumo(cuenta, codInsumo, descripcion, unidadIns, costoIns, costoInsCtrl, cantidadIns, "
                        + "cantInsCtrl, importeIns, impInsCtrl, id_concepto)values (?,?,?,?,?,?,?,?,?,?,?)";
                ps = con.prepareStatement(sql);
                for(InsumoBean aux:add) {
                    ps.setString(1, aux.getCuenta());
                    ps.setString(2, aux.getCodInsumo());
                    ps.setString(3, aux.getDescripIns());
                    ps.setString(4, aux.getUnidadIns());
                    ps.setBigDecimal(5, aux.getCostoIns());
                    ps.setBigDecimal(6, aux.getCostoIns());
                    ps.setBigDecimal(7, aux.getCantIns());
                    ps.setBigDecimal(8, aux.getCantIns());
                    ps.setBigDecimal(9, aux.getImporteIns());
                    ps.setBigDecimal(10, aux.getImporteIns());
                    ps.setInt(11, ccp.getId_concepto());
                    ps.executeUpdate();
                    
                }
            }
            
            if(edit.size() > 0) {
                sql = "update insumo set unidadIns=?, costoIns=?, cantidadIns=?, "
                        + "importeIns=? where codInsumo=?";
                ps = con.prepareStatement(sql);
                for(InsumoBean aux:edit) {
                    ps.setString(1, aux.getUnidadIns());
                    ps.setBigDecimal(2, aux.getCostoIns());
                    ps.setBigDecimal(3, aux.getCantIns());
                    ps.setBigDecimal(4, aux.getImporteIns());
                    ps.setString(5, aux.getCodInsumo());
                    ps.executeUpdate();
                }
                
                sql  = "select id_concepto from insumo where codInsumo = ?";
                ps = con.prepareStatement(sql);
                List<Integer> listID = new ArrayList<>();
                
                for(InsumoBean aux:edit) { 
                    ps.setString(1, aux.getCodInsumo());
                    ResultSet rs = ps.executeQuery();
                    while(rs.next()) {
                        int n = rs.getInt("id_concepto");
                        listID.add(n);
                    }
                }
                
                BigDecimal impAux;
                for(int n:listID) {
                    sql = "select sum(importeIns) as suma from insumo where id_concepto = ?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, n);
                    ResultSet rs = ps.executeQuery();
                    while(rs.next()) {
                        impAux = rs.getBigDecimal("suma");
                        sql = "update concepto set pUnitario =?, importe = (cantidad * pUnitario) where id_concepto = ?";
                        ps = con.prepareStatement(sql);
                        ps.setBigDecimal(1, impAux);
                        ps.setInt(2, n);
                        ps.executeUpdate();
                    }
                }
                
            }
            
            
            
            if(del.size() > 0) {
                sql = "delete from insumo where id_insumo=?";
                ps = con.prepareStatement(sql);
                for(InsumoBean aux:del) {
                    ps.setInt(1, aux.getId_insumo());
                    ps.executeUpdate();
                }
            }
            
            //con.commit();
            
            //seleccionar los insumos de la matriz editada
            //Select id, cantidad con el cod obtengo los id que se van a actualizar
            //crear concepto
            //actualizar desc, unidad, cantidad, precio e importe en el nuevo objeto y escribirlo en bd
            //actualizar sus insumos igual a la matriz del concepto que se editó
            
            
            
            
            
            List<Concepts> list = new ArrayList<>();
            Concepts concepto;
            
            sql = "select id_concepto, cantidad from concepto where codConcepto = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, ccp.getCodConcepto());
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                concepto = new Concepts();
                concepto.setId_concepto(rs.getInt("id_concepto"));
                concepto.setDescripcion(ccp.getDescripcion());
                concepto.setUnidad(ccp.getUnidad());
                concepto.setCantidad(rs.getBigDecimal("cantidad"));
                concepto.setpUnitario(ccp.getpUnitario());
                concepto.setImporte(concepto.getCantidad().multiply(concepto.getpUnitario()));
                list.add(concepto);
                
            }
            
            /*for(Concepts aux1:list) {
                
                System.out.println(aux1.getId_concepto()+", "+aux1.getDescripcion()+", "+aux1.getUnidad()+", "
                        +aux1.getpUnitario()+", "+aux1.getImporte()+", ");
            }*/
            
            
            for(Concepts aux1:list) {
                
                if(aux1.getId_concepto()!= ccp.getId_concepto()) {
                    sql = "update concepto set descripcion=?, unidad=?, pUnitario=?, importe=? where id_concepto=?";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, aux1.getDescripcion());
                    ps.setString(2, aux1.getUnidad());
                    ps.setBigDecimal(3, aux1.getpUnitario());
                    ps.setBigDecimal(4, aux1.getImporte());
                    ps.setInt(5, aux1.getId_concepto());
                    ps.executeUpdate();



                    if(add.size() > 0) {
                        sql = "insert into insumo(cuenta, codInsumo, descripcion, unidadIns, costoIns, costoInsCtrl, cantidadIns, "
                                + "cantInsCtrl, importeIns, impInsCtrl, id_concepto)values (?,?,?,?,?,?,?,?,?,?,?)";
                        ps = con.prepareStatement(sql);
                        for(InsumoBean aux:add) {
                            ps.setString(1, aux.getCuenta());
                            ps.setString(2, aux.getCodInsumo());
                            ps.setString(3, aux.getDescripIns());
                            ps.setString(4, aux.getUnidadIns());
                            ps.setBigDecimal(5, aux.getCostoIns());
                            ps.setBigDecimal(6, aux.getCostoIns());
                            ps.setBigDecimal(7, aux.getCantIns());
                            ps.setBigDecimal(8, aux.getCantIns());
                            ps.setBigDecimal(9, aux.getImporteIns());
                            ps.setBigDecimal(10, aux.getImporteIns());
                            ps.setInt(11, aux1.getId_concepto());
                            ps.executeUpdate();

                        }
                    }



                    if(del.size() > 0) {
                        sql = "delete from insumo where codInsumo=? and id_concepto=?";
                        ps = con.prepareStatement(sql);
                        for(InsumoBean aux:del) {
                            ps.setString(1, aux.getCodInsumo());
                            ps.setInt(2, aux1.getId_concepto());
                            ps.executeUpdate();
                        }
                    }


                }
            }
                
                    
            
            con.commit();
            con.close();
            ed = true;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            ed = false;
            try {
                con.rollback();
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        return ed;
        
    }
    
    public String buscartipoSubCtto(String cve) {
    
        String cod = "";
        
        try {
            
            String sql = "select definicion from catsubcontratos where clave = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cve);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                cod = rs.getString("definicion");
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return cod;
        
    }
    
    public InsumoBean verifDataSC(int id, String cve) {
        
        List<InsumoBean> list = new ArrayList<>();
        InsumoBean ins;
        InsumoBean insRet = new InsumoBean();
        
        try {
            
            String sql = "select ins.id_insumo, ins.cuenta, ins.codInsumo, ins.costoIns, ins.descripcion, ins.unidadIns "
                    + "from insumo as ins, concepto as con, partida as part, presupuesto as pre "
                    + "where ins.cuenta = 'SUBCONTRATOS' " 
                    + "and ins.id_concepto = con.id_concepto and con.id_partida = part.id_partida "
                    + "and part.id_presupuesto = pre.id_presupuesto and pre.id_presupuesto = ?";
            
            /*sql = "select ins.id_insumo, ins.cuenta, ins.codInsumo, ins.costoIns, ins.descripcion, ins.unidadIns from insumo as ins "
                    + "inner join concepto as con on con.id_concepto = ins.id_concepto "
                    + "inner join partida as part on part.id_partida = con.id_partida "
                    + "inner join presupuesto as pre on pre.id_presupuesto = part.id_presupuesto "
                    + "and pre.id_presupuesto = ? and ins.cuenta = 'SUBCONTRATOS'";*/
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                ins = new InsumoBean();
                ins.setId_insumo(rs.getInt("id_insumo"));
                ins.setCuenta(rs.getString("cuenta"));
                ins.setCodInsumo(rs.getString("codInsumo"));
                ins.setCostoIns(rs.getBigDecimal("costoIns"));
                ins.setDescripIns(rs.getString("descripcion"));
                ins.setUnidadIns(rs.getString("unidadIns"));
                list.add(ins);
            }
            //System.out.println(list.size());
            System.out.println(cve);
            for(InsumoBean aux:list) {
                System.out.println(aux.getCodInsumo());
                if(cve.equals(aux.getCodInsumo())) {
                    insRet.setId_insumo(aux.getId_insumo());
                    insRet.setCuenta(aux.getCuenta());
                    insRet.setCodInsumo(aux.getCodInsumo());
                    insRet.setCostoIns(aux.getCostoIns());
                    insRet.setDescripIns(aux.getDescripIns());
                    insRet.setUnidadIns(aux.getUnidadIns());
                    break;
                }
            }
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return insRet;
        
    }
    
    public InsumoBean verifClaveIns(String cve, int id) {
        
        InsumoBean ins = new InsumoBean();
        
        try {
            
            String sql = "select ins.id_insumo, ins.costoIns from insumo as ins "
                    + "inner join concepto as con on con.id_concepto = ins.id_concepto "
                    + "inner join partida as part on part.id_partida = con.id_partida "
                    + "inner join presupuesto as pre on pre.id_presupuesto = part.id_presupuesto "
                    + "and pre.id_presupuesto = ? and ins.codInsumo = ? ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, cve);
            ResultSet rs  = ps.executeQuery();
            while(rs.next()) {
                ins.setId_insumo(rs.getInt("id_insumo"));
                ins.setCostoIns(rs.getBigDecimal("costoIns"));
                System.out.println(ins.getCostoIns());
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return ins;
        
    }
    
    public void eliminaConcepto(Concepts cncp) {
    
        try {
            
            con.setAutoCommit(false);
            
            String sql = "delete from insumo where id_concepto = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cncp.getId_concepto());
            ps.executeUpdate();
            
            sql = "delete from concepto where id_concepto = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, cncp.getId_concepto());
            ps.executeUpdate();
            
            con.commit();
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public int eliminaPartida(int id_proy, PartidaBean p) {
        
        int del;
        
        try {
            
            con.setAutoCommit(false);
            
            String sql;
            PreparedStatement ps;
            
            //Elimina subpartidas
            
            sql = "delete from partida where esSubNivelDe = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, p.getId_partida());
            ps.executeUpdate();

            sql = "delete from partida where id_partida=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, p.getId_partida());
            ps.executeUpdate();
   
                /*sql = "delete from partida where id_partida=?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, p.getId_partida());
                ps.executeUpdate();*/
            
            del = 1;
  
            con.commit();
            con.close();
            
        }catch(Exception e) {
            System.out.println(e.getMessage());
            del = -1;
            try {
                con.rollback();
            }catch(SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        return del;
        
    }
    
    public int eliminaSubPartida(int id_proy, PartidaBean p) {
        
        int del;
        
        try {
            
            con.setAutoCommit(false);
            
            String sql;
            PreparedStatement ps;
            
            //Elimina subpartidas
            sql = "delete from partida where id_partida=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, p.getId_partida());
            ps.executeUpdate();
            
            del = 1;
            
            con.commit();
            con.close();
            
        }catch(Exception e) {
            System.out.println(e.getMessage());
            del = -1;
            try {
                con.rollback();
            }catch(SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        return del;
        
    }
    
    public void eliminaPres(int id_pres) {
        
        try {
            
            String sql = "delete from presupuesto where id_presupuesto = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id_pres);
            ps.executeUpdate();
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public List<PresupuestoBean> listarPresupuestoVacio(int id_proy) {
        
        List<PresupuestoBean> list = new ArrayList<>();
        PresupuestoBean pres;
        List<PresupuestoBean> listPres = new ArrayList<>();
        
        
        try {
            
            String sql = "select id_presupuesto, presupuesto from presupuesto where id_proyecto=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id_proy);
            ResultSet rs  = ps.executeQuery();
            
            while(rs.next()) {
                pres = new PresupuestoBean();
                pres.setId_presupuesto(rs.getInt("id_presupuesto"));
                pres.setPresupuesto(rs.getString("presupuesto"));
                listPres.add(pres);
            }
            
            //System.out.println("Presupuesto id: "+id_proy);
            //System.out.println(listPres.size());
            /*for(PresupuestoBean p:listPres) {
                System.out.println(p.getPresupuesto());
            }*/
            
            for(PresupuestoBean aux:listPres) {
                sql = "select id_partida from partida where id_presupuesto = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, aux.getId_presupuesto());
                rs = ps.executeQuery();
                //System.out.println(rs.next()+"-"+aux.getId_presupuesto());
                if(!rs.next()) {
                    //System.out.println(aux.getPresupuesto());
                    sql = "select * from presupuesto where id_presupuesto=?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, aux.getId_presupuesto());
                    rs  = ps.executeQuery();
                    while(rs.next()) {
                        pres = new PresupuestoBean();
                        pres.setId_presupuesto(rs.getInt("id_presupuesto"));
                        pres.setPresupuesto(rs.getString("presupuesto"));
                        pres.setTipo(rs.getInt("tipo"));
                        pres.setTipoPres(obtenerTipoPres(pres.getTipo()));
                        list.add(pres);
                    }
                }
            }
            
            con.close();            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
        
    }
    
    public String obtenerTipoPres(int cons) {
        
        String tipo ="";
        
        try {
            
            String sql = "select tipoPres from tipo_presupuesto where consec = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cons);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                tipo = rs.getString("tipoPres");
            }
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return tipo;
        
    }
    
    public List<MaterialBean> listarMateriales() {
        
        List<MaterialBean> list = new ArrayList<>();
        MaterialBean mat;
        
        try {
            
            String sql = "select * from catmaterial";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                mat = new MaterialBean();
                mat.setId_material(rs.getInt("id_material"));
                mat.setClaveMat(rs.getString("clave"));
                mat.setMaterial(rs.getString("descripcion"));
                mat.setUnidad(rs.getString("unidad"));
                mat.setCuenta("MATERIALES");
                list.add(mat);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
        
    }
    
    public int agregarPresupuestoAutoVta(List<PartidaBean> listPart, List<Concepto> listCon, int id_pres) {
        
        int reg = -1;
        
        try {
            
            con.setAutoCommit(false);
            
            //Escribir partida
            String sql = "insert into partida(nivel, esSubNivelDe, descripcion, id_presupuesto)values (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            
            for(PartidaBean aux:listPart) {
                ps.setString(1, aux.getNivel());
                if(aux.getEsSubNivel().equals("0")) {
                    ps.setInt(2, 0);
                }else {
                    ps.setInt(2, ubicarSubNivel(aux.getEsSubNivel(), id_pres));
                }
                ps.setString(3, aux.getDescripcion());
                ps.setInt(4, id_pres);
                ps.executeUpdate();
            }
            
            String sql2 = "insert into concepto(numConcepto, codConcepto, descripcion, unidad, cantidad, cantCtrl, "
                    + "pUnitario, importe, id_partida, partida)values (?,?,?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sql2);
            
            for(Concepto aux:listCon) {
                ps.setString(1, aux.getNumConcepto());
                ps.setString(2, aux.getCodConcepto());
                ps.setString(3, aux.getDescripcion());
                ps.setString(4, aux.getUnidad());
                ps.setBigDecimal(5, aux.getCantidad());
                ps.setBigDecimal(6, aux.getCantidad());
                ps.setBigDecimal(7, aux.getpUnitario());
                ps.setBigDecimal(8, aux.getImporte());
                ps.setInt(9, ubicarPartida(aux.getPartida(), id_pres));
                ps.setString(10, aux.getPartida());
                ps.executeUpdate();
            }
            
            con.commit();
            reg = 1;
            con.close();
            
        }catch(SQLException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
            try {
                con.rollback();
            }catch(SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        return reg;
    }
    
    public int agregarPresupuestoAuto(List<PartidaBean> listPart, List<Concepto> listCon, List<InsumoBean> listIns, int id_pres) {
        
        int reg = -1;
        
        try {
            
            con.setAutoCommit(false);
            
            //Escribir partida
            String sql = "insert into partida(nivel, esSubNivelDe, descripcion, id_presupuesto)values (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            
            for(PartidaBean aux:listPart) {
                ps.setString(1, aux.getNivel());
                if(aux.getEsSubNivel().equals("0")) {
                    ps.setInt(2, 0);
                }else {
                    ps.setInt(2, ubicarSubNivel(aux.getEsSubNivel(), id_pres));
                }
                ps.setString(3, aux.getDescripcion());
                ps.setInt(4, id_pres);
                ps.executeUpdate();
            }
            
            String sql2 = "insert into concepto(numConcepto, codConcepto, descripcion, unidad, cantidad, cantCtrl, "
                    + "pUnitario, importe, id_partida, partida)values (?,?,?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sql2);
            
            for(Concepto aux:listCon) {
                ps.setString(1, aux.getNumConcepto());
                ps.setString(2, aux.getCodConcepto());
                ps.setString(3, aux.getDescripcion());
                ps.setString(4, aux.getUnidad());
                ps.setBigDecimal(5, aux.getCantidad());
                ps.setBigDecimal(6, aux.getCantidad());
                ps.setBigDecimal(7, aux.getpUnitario());
                ps.setBigDecimal(8, aux.getImporte());
                ps.setInt(9, ubicarPartida(aux.getPartida(), id_pres));
                ps.setString(10, aux.getPartida());
                ps.executeUpdate();
            }
            
            String sql3 = "insert into insumo(cuenta, codInsumo, descripcion, unidadIns, costoIns, costoInsCtrl, "
                    + "cantidadIns, cantInsCtrl, importeIns, impInsCtrl, id_concepto)values (?,?,?,?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sql3);
            
            for(InsumoBean aux: listIns) {
                ps.setString(1, aux.getCuenta());
                ps.setString(2, aux.getCodInsumo());
                ps.setString(3, aux.getDescripIns());
                ps.setString(4, aux.getUnidadIns());
                ps.setBigDecimal(5, aux.getCostoIns());
                ps.setBigDecimal(6, aux.getCostoIns());
                ps.setBigDecimal(7, aux.getCantIns());
                ps.setBigDecimal(8, aux.getCantIns());
                ps.setBigDecimal(9, aux.getImporteIns());
                ps.setBigDecimal(10, aux.getImporteIns());
                ps.setInt(11, ubicarConcepto(aux.getNoConcepto(), id_pres));
                ps.executeUpdate();
                
            }
            
            con.commit();
            reg = 1;
            con.close();
            
        }catch(SQLException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
            try {
                con.rollback();
            }catch(SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        return reg;
    }
    
    public int ubicarConcepto(String num, int id_pres) {
        
        int id_concepto = 0;
        
        try {
            String sql = "select id_concepto from concepto as con "
                    + "inner join partida as part on part.id_partida = con.id_partida and part.id_presupuesto="
                    +id_pres+" and con.numConcepto=\'"+num+"\'";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                id_concepto = rs.getInt("id_concepto");
            }
            
            //System.out.println(num+"\t"+id_concepto+"\t"+id_pres);
            
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return id_concepto;
    }
    
    public int ubicarPartida(String part, int id_pres) {
        
        int id_part = 0;
        
        try {
            String sql = "select id_partida from partida where nivel=\'"+part+"\' and id_presupuesto="+id_pres;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                id_part = rs.getInt("id_partida");
            }
            
            
        }catch(SQLException e) {
        
        }
        
        return id_part;
    }
    
    public int guardarPresupuesto(PresupuestoBean pb) {
        
        int id_reg = 0;
        
        try {
            String sql = "insert into presupuesto(presupuesto, tipo, id_proyecto)values (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, pb.getPresupuesto());
            ps.setInt(2, pb.getTipo());
            ps.setInt(3, pb.getId_proyecto());
            ps.executeUpdate();
            
            String sql2 = "select last_insert_id()";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql2);
            while(rs.next()) {
                id_reg = rs.getInt(1);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return id_reg;
    }
    
    public int ubicarSubNivel(String lvl, int id_pres) {
        
        int id_part = 0;
        
        try {
            String sql = "select id_partida from partida where nivel=\'"+lvl+"\' and id_presupuesto="+id_pres;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                id_part = rs.getInt("id_partida");
            }
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return id_part;
    }
    
    public Concepts buscarDuplicados(String cod) {
        
        int dup;
        
        Concepts ccp = new Concepts();
        
        try {
        
            String sql = "SELECT DISTINCT codConcepto, descripcion, unidad, cantidad, pUnitario, importe FROM concepto "
                    + "WHERE codConcepto = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cod);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                ccp.setCodConcepto(rs.getString("codConcepto"));
                ccp.setDescripcion(rs.getString("descripcion"));
                ccp.setUnidad(rs.getString("unidad"));
                ccp.setCantidad(rs.getBigDecimal("cantidad"));
                ccp.setpUnitario(rs.getBigDecimal("pUnitario"));
                ccp.setImporte(rs.getBigDecimal("importe"));
                ccp.setInsumos(buscarInsumosPorConcepto(cod));
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            dup = 0;
        }
        
        return ccp;
        
    }
    
    public List<InsumoBean> buscarInsumosPorConcepto(String cod) {
        
        List<InsumoBean> list = new ArrayList<>();
        InsumoBean ins;
        
        try {
            
            String sql = "select * from insumo where id_concepto = (select min(id_concepto) from concepto where "
                    + "codConcepto = ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cod);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                ins = new InsumoBean();
                ins.setCuenta(rs.getString("cuenta"));
                ins.setCodInsumo(rs.getString("codInsumo"));
                ins.setDescripIns(rs.getString("descripcion"));
                ins.setUnidadIns(rs.getString("unidadIns"));
                ins.setCostoIns(rs.getBigDecimal("costoIns"));
                ins.setCostoInsCtrl(rs.getBigDecimal("costoInsCtrl"));
                ins.setCantIns(rs.getBigDecimal("cantidadIns"));
                ins.setCantInsCtrl(rs.getBigDecimal("cantInsCtrl"));
                ins.setImporteIns(rs.getBigDecimal("importeIns"));
                ins.setImpInsCtrl(rs.getBigDecimal("impInsCtrl"));
                list.add(ins);
            }
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
        
    }
    
    public int guardarConceptoNuevo(Concepto c, List<InsumoBean> list) {
        
        int reg;
        String sql;
        PreparedStatement ps;
        ResultSet rs;
        
        try {
            
            con.setAutoCommit(false);
            
            sql = "insert into concepto(numConcepto, codConcepto, descripcion, unidad, cantidad, cantCtrl, pUnitario, "
                    + "importe, id_partida, partida)values(?,?,?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getNumConcepto());
            ps.setString(2, c.getCodConcepto());
            ps.setString(3, c.getDescripcion());
            ps.setString(4, c.getUnidad());
            ps.setBigDecimal(5, c.getCantidad());
            ps.setBigDecimal(6, c.getCantidad());
            ps.setBigDecimal(7, c.getpUnitario());
            ps.setBigDecimal(8, c.getImporte());
            ps.setInt(9, c.getId_partida());
            ps.setString(10, c.getPartida());
            ps.executeUpdate();
            
            String sql2 = "select last_insert_id()";
            Statement s = con.createStatement();
            rs = s.executeQuery(sql2);
            rs.first();
            int id_conc = rs.getInt(1);
            
            String sql3 = "insert into insumo(cuenta, codInsumo, descripcion, unidadIns, costoIns, costoInsCtrl, cantidadIns, "
                    + "cantInsCtrl, importeIns, impInsCtrl, id_concepto)values (?,?,?,?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sql3);
            
            for(InsumoBean aux:list) {
                ps.setString(1, aux.getCuenta());
                ps.setString(2, aux.getCodInsumo());
                ps.setString(3, aux.getDescripIns());
                ps.setString(4, aux.getUnidadIns());
                ps.setBigDecimal(5, aux.getCostoIns());
                ps.setBigDecimal(6, aux.getCostoInsCtrl());
                ps.setBigDecimal(7, aux.getCantIns());
                ps.setBigDecimal(8, aux.getCantInsCtrl());
                ps.setBigDecimal(9, aux.getImporteIns());
                ps.setBigDecimal(10, aux.getImpInsCtrl());
                ps.setInt(11, id_conc);
                ps.executeUpdate();
            }
            
            reg = 1;
            con.commit();
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            reg = -1;
            try {
                con.rollback();
            }catch(SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        return reg;
        
    }
    
    public void guardarNuevaSubPrtda(PartidaBean p) {
        
        try {
            
            String sql = "insert into partida(nivel, esSubNivelDe, descripcion, id_presupuesto)values (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, p.getNivel());
            ps.setInt(2, p.getEsSubNivelDe());
            ps.setString(3, p.getDescripcion());
            ps.setInt(4, p.getId_presupuesto());
            ps.executeUpdate();
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public void guardarNuevaPartida(PartidaBean p) {
        
        try {
            
            String sql = "insert into partida(nivel, esSubNivelDe, descripcion, id_presupuesto)values (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, p.getNivel());
            ps.setInt(2, 0);
            ps.setString(3, p.getDescripcion());
            ps.setInt(4, p.getId_presupuesto());
            ps.executeUpdate();
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public void guardarNuevoNivel(Nivel nvl) {
        
        try {
            String sql = "insert into partida(nivel, esSubNivelDe, descripcion, id_presupuesto)values (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nvl.getNivel());
            ps.setInt(2, nvl.getEsSubNivelDe());
            ps.setString(3, nvl.getDescripcion());
            ps.setInt(4, nvl.getId_presupuesto());
            ps.executeUpdate();
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public List<PartidaBean> listarSubPartidas(int id_proy, int id_pres, int id_part) {
    
        List<PartidaBean> list = new ArrayList<>();
        PartidaBean pb;
        
        try {
            String sql = "select id_partida, nivel, esSubNivelDe, descripcion from partida as part "
                    + "inner join presupuesto as pre on pre.id_presupuesto =  part.id_presupuesto "
                    + "inner join proyecto as pro on pro.id_proyecto ="+id_proy+" and pre.id_presupuesto="+id_pres
                    +" and esSubNivelDe="+id_part;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                pb = new PartidaBean();
                pb.setId_partida(rs.getInt("id_partida"));
                pb.setNivel(rs.getString("nivel"));
                pb.setEsSubNivelDe(rs.getInt("esSubnivelDe"));
                pb.setDescripcion(rs.getString("descripcion"));
                list.add(pb);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
        
    }
    
    
    public List<Concepto> conceptosConsecutivos(int id_proy, int id_pres) {
        
        List<Concepto> list = new ArrayList<>();
        Concepto c;
        
        
        try {
            
            String sql =  "select con.numConcepto, con.codConcepto, con.descripcion from concepto as con " +
                        "inner join partida as part on part.id_partida = con.id_partida " +
                        "inner join presupuesto as pre on pre.id_presupuesto = part.id_presupuesto " +
                        "inner join proyecto as pro on pro.id_proyecto ="+id_proy+" and pre.id_presupuesto ="+id_pres;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                c = new Concepto();
                c.setNumConcepto(rs.getString("numConcepto"));
                c.setCodConcepto(rs.getString("codConcepto"));
                c.setDescripcion(rs.getString("descripcion"));
                list.add(c);
            }
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
    }
    
    public List<PartidaBean> listarPartidaBean(int id_proy, int id_pres) {
        
        List<PartidaBean> list =  new ArrayList<>();
        PartidaBean pb;
        
        
        try {
            String sql = "select id_partida, nivel, esSubNivelDe, descripcion from partida as part "
                    + "inner join presupuesto as pre on pre.id_presupuesto =  part.id_presupuesto "
                    + "inner join proyecto as pro on pro.id_proyecto ="+id_proy+" and pre.id_presupuesto="+id_pres
                    +" and esSubNivelDe=0";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                pb = new PartidaBean();
                pb.setId_partida(rs.getInt("id_partida"));
                pb.setNivel(rs.getString("nivel"));
                pb.setEsSubNivelDe(rs.getInt("esSubnivelDe"));
                pb.setDescripcion(rs.getString("descripcion"));
                pb.setId_presupuesto(id_pres);
                list.add(pb);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
    }
    
    public List<Nivel> listarNiveles(int id_proy, int id_pres) {
        
        List<Nivel> list =  new ArrayList<>();
        Nivel nvl;
        
        
        try {
            String sql = "select id_partida, nivel, esSubNivelDe, descripcion from partida as part "
                    + "inner join presupuesto as pre on pre.id_presupuesto =  part.id_presupuesto "
                    + "inner join proyecto as pro on pro.id_proyecto ="+id_proy+" and pre.id_presupuesto="+id_pres
                    +" and esSubNivelDe=0";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                nvl = new Nivel();
                nvl.setId_partida(rs.getInt("id_partida"));
                nvl.setNivel(rs.getString("nivel"));
                nvl.setEsSubNivelDe(rs.getInt("esSubnivelDe"));
                nvl.setDescripcion(rs.getString("descripcion"));
                list.add(nvl);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
        
    }
    
    public String busarConsecutivoConcepto(int id_proy, int id_pres) {
        
        String consec = "";
        
        try {
            String sql  = "select max(numConcepto)+1 as numConcepto from concepto as con "
                    + "inner join partida as part on part.id_partida = con.id_partida "
                    + "inner join presupuesto as pre on pre.id_presupuesto = part.id_presupuesto "
                    + "inner join proyecto as pro on pro.id_proyecto ="+id_proy+ " and pre.id_presupuesto="+id_pres;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                consec = rs.getString("numConcepto");
            }
            
            if(consec == null) {
                consec = "1";
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return consec;
        
    }
    
    public List<FamConcepto> listarFamiliasConceptos(int id_proy) {
        
        List<FamConcepto> lista = new ArrayList<>();
        FamConcepto fc;
        
        try {
            String sql = "select id_famConcepto, subfamilia, claveFam from famconcepto where id_proyecto = ?";
            //System.out.println(id_proy);
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id_proy);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                fc = new FamConcepto();
                fc.setId_famConcepto(rs.getInt("id_famConcepto"));
                fc.setSunFamilia(rs.getString("subfamilia"));
                fc.setClaveFam(rs.getString("claveFam"));
                fc.setId_proyecto(id_proy);
                lista.add(fc);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            
        }
        
        
        return lista;
    }
    
    public String buscarConsecutivoSC(String cve) {
        
        String consec = "";
        
        try {
            String sql = "select consecutivo from catsubcontratos where clave=\'"+cve+"\'";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                consec = rs.getString("consecutivo");
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return consec;
    }
    
    public List<Unidad> listarUnidades() {
        List<Unidad> lista = new ArrayList<>();
        Unidad u;
        
        try {
            String sql = "select * from unidad";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                u = new Unidad();
                u.setId_unidad(rs.getInt("id_unidad"));
                u.setUnidad(rs.getString("unidad"));
                lista.add(u);
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
        return lista;
    }
    
    public List<Catalogo> listarCatalogoConceptos() {
        
        List<Catalogo> catalogo = new ArrayList<>();
        Catalogo cat;
        
        try {
           
            String sql = "select id_subcon, clave, definicion from catsubcontratos";

            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                cat = new Catalogo();
                cat.setId_subcon(rs.getInt("id_subcon"));
                cat.setClave(rs.getString("clave"));
                cat.setDefinicion(rs.getString("definicion"));
                catalogo.add(cat);
            }

        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return catalogo; 
         
    }
    
    public List<Catalogo> listarCatalogo(int op) {
        
        List<Catalogo> catalogo = new ArrayList<>();
        Catalogo cat;
        
        switch(op) {
            case 1:
                try {
           
                    String sql = "select id_subcon, clave, definicion from catsubcontratos";

                    Statement s = con.createStatement();
                    ResultSet rs = s.executeQuery(sql);
                    while(rs.next()) {
                        cat = new Catalogo();
                        cat.setId_subcon(rs.getInt("id_subcon"));
                        cat.setClave(rs.getString("clave"));
                        cat.setDefinicion(rs.getString("definicion"));
                        catalogo.add(cat);
                    }

                }catch(SQLException e) {
                    System.out.println(e.getMessage());
                }
                break;
                
            case 2:
                try {
           
                    String sql = "select id_material, familia, descripcion from catmaterial group by familia";

                    Statement s = con.createStatement();
                    ResultSet rs = s.executeQuery(sql);
                    while(rs.next()) {
                        cat = new Catalogo();
                        cat.setId_subcon(rs.getInt("id_material"));
                        cat.setClave(rs.getString("familia"));
                        cat.setDefinicion(rs.getString("descripcion"));
                        catalogo.add(cat);
                    }

                }catch(SQLException e) {
                    System.out.println(e.getMessage());
                }
                break;
        }
        
        return catalogo;
    }
    
    public List<Catalogo> listarClaves(String fam) {
        List<Catalogo> lista = new ArrayList<>();
        Catalogo c;
        
            try {
                String sql = "select clave, insumo from catmaterial where familia=\'"+fam+"\'";
                Statement s = con.createStatement();
                ResultSet rs = s.executeQuery(sql);
                while(rs.next()) {
                    c = new Catalogo();
                    c.setClave(rs.getString("clave"));
                    c.setDefinicion(rs.getString("insumo"));
                    lista.add(c);
                }
                
                System.out.println(lista.size());
                
            }catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        
        return lista;
    }
    
    public boolean cargarExplosion(int id_proy, int id_pres) {
        
        List<InsumoExplosion> listIns = new ArrayList<>();
        InsumoExplosion ins;
        
        boolean success = false;
        
        try {
            
            String sql = "SELECT sum(ins.cantInsCtrl) total, cnp.cantCtrl, sum(ins.cantInsCtrl*cnp.cantCtrl) AS canTotal, "
                    + "ins.cuenta, ins.codInsumo, ins.descripcion, ins.unidadIns, ins.costoIns, ins.costoInsCtrl, pre.id_presupuesto " +
                    "FROM insumo ins, concepto cnp, partida pta, presupuesto pre, proyecto pro " +
                    "WHERE ins.id_concepto = cnp.id_concepto AND cnp.id_partida = pta.id_partida "
                    + "AND pta.id_presupuesto = pre.id_presupuesto AND pre.id_proyecto = pro.id_proyecto "
                    + "AND pro.id_proyecto="+id_proy+" AND pre.id_presupuesto="+id_pres +" AND ins.cuenta = 'MATERIALES' group by ins.codInsumo;";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                ins = new InsumoExplosion();
                ins.setCodInsumo(rs.getString("codInsumo"));
                ins.setDescripcion(rs.getString("descripcion"));
                ins.setUnidadIns(rs.getString("unidadIns"));
                ins.setPrecioUnit(rs.getBigDecimal("costoIns"));
                ins.setImpInsCtrl(rs.getBigDecimal("costoInsCtrl"));
                ins.setCuenta(rs.getString("cuenta"));
                ins.setId_presupuesto(rs.getInt("id_presupuesto"));
                ins.setCantCtrl(rs.getBigDecimal("canTotal"));
                listIns.add(ins);
            }
            
            String sql3 = "SELECT id_presupuesto FROM exp_insumos WHERE id_presupuesto = "+id_pres;
            
            rs = s.executeQuery(sql3);
            
            if(!rs.first()) {
                for(InsumoExplosion aux: listIns) {
                    try {

                        String sql2 = "INSERT INTO exp_insumos(codInsumo, descripcion, unidades, costoIns, costoInsCtrl, cuenta, "
                            + "total, id_presupuesto)VALUES (?,?,?,?,?,?,?,?)";

                        PreparedStatement ps = con.prepareStatement(sql2);
                        ps.setString(1, aux.getCodInsumo());
                        ps.setString(2, aux.getDescripcion());
                        ps.setString(3, aux.getUnidadIns());
                        ps.setBigDecimal(4, aux.getPrecioUnit());
                        ps.setBigDecimal(5, aux.getImpInsCtrl());
                        ps.setString(6, aux.getCuenta());
                        ps.setBigDecimal(7, aux.getCantCtrl());
                        ps.setInt(8, aux.getId_presupuesto());
                        ps.executeUpdate();

                    }catch(SQLException e) {
                        System.out.println(e.getMessage());
                    }

                }
                
                success = true;
            }
            
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return success;
    }
    
    public List<Concepts> filterFirstLvl(int id_part) {
        
        Concepts concept;
        List<Concepts> list = new ArrayList<>();
        FormatoString conv = new FormatoString();
        CadenasCortas shortDesc = new CadenasCortas();
        
        Nivel lvl;
        List<Nivel> listLvl = new ArrayList<>();
        
        try {
            
            String sql = "SELECT id_partida FROM partida WHERE esSubNivelDe="+id_part;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                lvl = new Nivel();
                lvl.setId_partida(rs.getInt("id_partida"));
                listLvl.add(lvl);
            }
            
            
            
            for(Nivel aux: listLvl) {
                String sql2 = " SELECT * FROM concepto WHERE id_partida="+aux.getId_partida();
                rs = s.executeQuery(sql2);
                
                while(rs.next()) {
                    concept = new Concepts();
                    int id = rs.getInt("id_partida");
                    String[] partidas = lookPartida(id);
                    concept.setPartida(partidas[1]);
                    concept.setSubPrtda(partidas[0]);
                    concept.setDescPartida(partidas[2]);
                    concept.setId_concepto(rs.getInt("id_concepto"));
                    concept.setNumConcepto(rs.getInt("numConcepto"));
                    concept.setCodConcepto(rs.getString("codConcepto"));
                    concept.setDescripcion(shortDesc.resumirDescripcion(rs.getString("descripcion")));
                    concept.setUnidad(rs.getString("unidad"));
                    concept.setCantidad(rs.getBigDecimal("cantidad"));
                    concept.setCantCtrl(rs.getBigDecimal("cantCtrl"));
                    concept.setpUnitario(rs.getBigDecimal("pUnitario"));
                    concept.setImporte(rs.getBigDecimal("importe"));
                    concept.setCadCant(conv.formatoCantidades(rs.getBigDecimal("cantidad")));
                    concept.setCadPrecio(conv.formatoCantidades(rs.getBigDecimal("pUnitario")));
                    concept.setCadImp(conv.formatoCantidades(rs.getBigDecimal("importe")));
                    concept.setId_partida(id_part);
                    list.add(concept);
                }
            }
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
        
    }
    
    public List<Concepts> filterSecLvl(int id_part) {
        
        Concepts concept;
        List<Concepts> list = new ArrayList<>();
        FormatoString conv = new FormatoString();
        CadenasCortas shortDesc = new CadenasCortas();
        
        try {
            
            String sql = "SELECT * FROM concepto WHERE id_partida="+id_part;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                concept = new Concepts();
                int id = rs.getInt("id_partida");
                String[] partidas = lookPartida(id);
                concept.setPartida(partidas[1]);
                concept.setSubPrtda(partidas[0]);
                concept.setDescPartida(partidas[2]);
                concept.setId_concepto(rs.getInt("id_concepto"));
                concept.setNumConcepto(rs.getInt("numConcepto"));
                concept.setCodConcepto(rs.getString("codConcepto"));
                concept.setDescripcion(shortDesc.resumirDescripcion(rs.getString("descripcion")));
                concept.setUnidad(rs.getString("unidad"));
                concept.setCantidad(rs.getBigDecimal("cantidad"));
                concept.setCantCtrl(rs.getBigDecimal("cantCtrl"));
                concept.setpUnitario(rs.getBigDecimal("pUnitario"));
                concept.setImporte(rs.getBigDecimal("importe"));
                concept.setCadCant(conv.formatoCantidades(rs.getBigDecimal("cantidad")));
                concept.setCadPrecio(conv.formatoCantidades(rs.getBigDecimal("pUnitario")));
                concept.setCadImp(conv.formatoCantidades(rs.getBigDecimal("importe")));
                concept.setId_partida(id_part);
                list.add(concept);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
        
    }
    
    public List<Nivel> getSecLevel(int subLvl, int id_pres) {
        
        Nivel lvl;
        List<Nivel> levels = new ArrayList<>();
        
        try {
            
            String sql = "SELECT id_partida, nivel, esSubNivelDe, descripcion, id_presupuesto FROM "
                    + "partida WHERE esSubNivelDe="+subLvl+ " AND id_presupuesto="+id_pres;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                lvl = new Nivel();
                lvl.setId_partida(rs.getInt("id_partida"));
                lvl.setNivel(rs.getString("nivel"));
                lvl.setEsSubNivelDe(rs.getInt("esSubNivelDe"));
                lvl.setDescripcion(rs.getString("descripcion"));
                lvl.setId_presupuesto(rs.getInt("id_presupuesto"));
                levels.add(lvl);
                
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
        return levels;
    }
    
    public List<Nivel> readLevel(int id_pres) {
        
        Nivel lvl;
        List<Nivel> levels = new ArrayList<>();
        
        try {
            
            String sql = "SELECT id_partida, nivel, descripcion FROM partida WHERE id_presupuesto="+id_pres +
                    " AND esSubNivelDe=0";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                lvl = new Nivel();
                lvl.setId_partida(rs.getInt("id_partida"));
                lvl.setNivel(rs.getString("nivel"));
                lvl.setDescripcion(rs.getString("descripcion"));
                levels.add(lvl);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return levels;
    }
    
    public String[] nombrarProyecto(int id_proy, int id_pres) {
        
        String[] names = new String[2];
        
        try {
            
            String sql1 = "SELECT proyecto FROM proyecto WHERE id_proyecto="+id_proy;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql1);
            rs.first();
            names[0] = rs.getString("proyecto");
            
            String sql2 = "SELECT presupuesto FROM presupuesto WHERE id_presupuesto="+id_pres;
            rs = s.executeQuery(sql2);
            rs.first();
            names[1] = rs.getString("presupuesto");
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return names;
        
    }
    
    public List<Concepts> listBudget(int id_proy, int id_pres) {
        
        Concepts concept;
        List<Concepts> list = new ArrayList<>();
        FormatoString conv = new FormatoString();
        CadenasCortas shortDesc = new CadenasCortas();
        
        try {
            
            String sql = "SELECT * FROM concepto AS A "
                    + "INNER JOIN partida AS B ON B.id_partida = A.id_partida "
                    + "INNER JOIN presupuesto AS C ON C.id_presupuesto = B.id_presupuesto "
                    + "INNER JOIN proyecto AS D ON  D.id_proyecto ="+id_proy+  " AND C.id_presupuesto ="+id_pres;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                concept = new Concepts();
                int id_part = rs.getInt("id_partida");
                String[] partidas = lookPartida(id_part);
                concept.setPartida(partidas[1]);
                concept.setSubPrtda(partidas[0]);
                concept.setDescPartida(partidas[2]);
                concept.setId_concepto(rs.getInt("id_concepto"));
                concept.setNumConcepto(rs.getInt("numConcepto"));
                concept.setCodConcepto(rs.getString("codConcepto"));
                concept.setDescripcion(shortDesc.resumirDescripcion(rs.getString("descripcion")));
                concept.setUnidad(rs.getString("unidad"));
                concept.setCantidad(rs.getBigDecimal("cantidad"));
                concept.setCantCtrl(rs.getBigDecimal("cantCtrl"));
                concept.setpUnitario(rs.getBigDecimal("pUnitario"));
                concept.setImporte(rs.getBigDecimal("importe"));
                concept.setCadCant(conv.formatoCantidades(rs.getBigDecimal("cantidad")));
                concept.setCadPrecio(conv.formatoCantidades(rs.getBigDecimal("pUnitario")));
                concept.setCadImp(conv.formatoCantidades(rs.getBigDecimal("importe")));
                concept.setId_partida(id_part);
                concept.setInsumos(listarInsXConcep(concept.getId_concepto()));
                list.add(concept);
                
            }
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
    }
    
    public List<Concepts> listaFiltradaDeConcepto(String sql) {
        
        Concepts concept;
        List<Concepts> list = new ArrayList<>();
        FormatoString conv = new FormatoString();
        CadenasCortas shortDesc = new CadenasCortas();
        
        try {
            
            
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                concept = new Concepts();
                int id_part = rs.getInt("id_partida");
                String[] partidas = lookPartida(id_part);
                concept.setPartida(partidas[1]);
                concept.setSubPrtda(partidas[0]);
                concept.setDescPartida(partidas[2]);
                concept.setId_concepto(rs.getInt("id_concepto"));
                concept.setNumConcepto(rs.getInt("numConcepto"));
                concept.setCodConcepto(rs.getString("codConcepto"));
                concept.setDescripcion(shortDesc.resumirDescripcion(rs.getString("descripcion")));
                concept.setUnidad(rs.getString("unidad"));
                concept.setCantidad(rs.getBigDecimal("cantidad"));
                concept.setCantCtrl(rs.getBigDecimal("cantCtrl"));
                concept.setpUnitario(rs.getBigDecimal("pUnitario"));
                concept.setImporte(rs.getBigDecimal("importe"));
                concept.setCadCant(conv.formatoCantidades(rs.getBigDecimal("cantidad")));
                concept.setCadPrecio(conv.formatoCantidades(rs.getBigDecimal("pUnitario")));
                concept.setCadImp(conv.formatoCantidades(rs.getBigDecimal("importe")));
                concept.setId_partida(id_part);
                concept.setInsumos(listarInsXConcep(concept.getId_concepto()));
                list.add(concept);
                
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
    }
    
    public List<InsumoBean> listarInsXConcep(int id) {
        
        List<InsumoBean> list = new ArrayList<>();
        InsumoBean ins;
        
        try {
            
            String sql = "select * from insumo where id_concepto = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                ins = new InsumoBean();
                ins.setId_insumo(rs.getInt("id_insumo"));
                ins.setCuenta(rs.getString("cuenta"));
                ins.setCodInsumo(rs.getString("codInsumo"));
                ins.setDescripIns(rs.getString("descripcion"));
                ins.setUnidadIns(rs.getString("unidadIns"));
                ins.setCostoIns(rs.getBigDecimal("costoIns"));
                ins.setCostoInsCtrl(rs.getBigDecimal("costoInsCtrl"));
                ins.setCantIns(rs.getBigDecimal("cantidadIns"));
                ins.setCantInsCtrl(rs.getBigDecimal("cantInsCtrl"));
                ins.setImporteIns(rs.getBigDecimal("importeIns"));
                ins.setImpInsCtrl(rs.getBigDecimal("impInsCtrl"));
                ins.setId_concepto(id);
                list.add(ins);
            }
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
        
    }
    
    public String[] lookPartida(int id_partida) {
        
        String[] partidas = new String[3];
        
        try {
            
            String sql = "SELECT nivel, esSubNivelDe, descripcion FROM partida WHERE id_partida="+id_partida;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            rs.first();
            partidas[0] = rs.getString("nivel");
            partidas[1] = rs.getString("esSubNivelDe");
            partidas[2] = rs.getString("descripcion");
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return partidas;
        
    }
    
    
    
    public List<InsumoExplosion> explotarInsumos(int id_proy, int id_pres) {
        
        List<InsumoExplosion> expIns = new ArrayList<>();
        InsumoExplosion ins;
        
        try {
            
            String sql = "SELECT A.cuenta, A.codInsumo, A.descripcion, SUM(A.impInsCtrl*B.cantCtrl) AS total FROM insumo AS A, " +
                        "concepto AS B, partida as C, presupuesto as D, proyecto as E " +
                        "where B.id_concepto=A.id_concepto and C.id_partida=B.id_partida and D.id_presupuesto=C.id_presupuesto " +
                        "and D.id_presupuesto="+id_pres+" and E.id_proyecto="+id_proy+" AND A.cuenta='MATERIALES' " +
                        "GROUP BY A.codInsumo";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                ins = new InsumoExplosion();
                ins.setCuenta(rs.getString("cuenta"));
                ins.setCodInsumo(rs.getString("codInsumo"));
                ins.setDescripcion(rs.getString("descripcion"));
                ins.setSumaCant(rs.getBigDecimal("total"));
                
                expIns.add(ins);
            }
            
            //PruebaIns sum = new PruebaIns();
            //sum.sumarIns(expIns);
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return expIns;
    }
    
    public List<InsumoExplosion> explotarSubcontratos(int id_proy, int id_pres) {
        
        List<InsumoExplosion> expIns = new ArrayList<>();
        InsumoExplosion ins;
        
        try {
            
            String sql = "SELECT A.cuenta, A.codInsumo, A.descripcion, SUM(A.impInsCtrl*B.cantCtrl) AS total FROM insumo AS A, " +
                        "concepto AS B, partida as C, presupuesto as D, proyecto as E " +
                        "where B.id_concepto=A.id_concepto and C.id_partida=B.id_partida and D.id_presupuesto=C.id_presupuesto " +
                        "and D.id_presupuesto="+id_pres+" and E.id_proyecto="+id_proy+" AND A.cuenta='SUBCONTRATOS' " +
                        "GROUP BY A.codInsumo";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                ins = new InsumoExplosion();
                ins.setCuenta(rs.getString("cuenta"));
                ins.setCodInsumo(rs.getString("codInsumo"));
                ins.setDescripcion(rs.getString("descripcion"));
                
                ins.setSumaCant(rs.getBigDecimal("total"));
                
                expIns.add(ins);
            }
            
            //PruebaIns sum = new PruebaIns();
            //sum.sumarIns(expIns);
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return expIns;
    }
    
    public void actualizarConcepto(Concepto c) {
        
        try {
            
            String sql = "Update concepto set descripcion=?, cantCtrl=?, importe=? Where numConcepto=?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, c.getDescripcion());
            ps.setBigDecimal(2, c.getCantidad());
            ps.setBigDecimal(3, c.getImporte());
            ps.setString(4, c.getNumConcepto());
            ps.executeUpdate();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public void escribirConcepto(Concepto concep, List<InsumoBean> listaInsTemp) {
        
        int id_concepto;
        
        try {
            
            String sql = "Insert Into concepto (subPartida, cveSubPrtda, numConcepto, codConcepto, descripcion, unidad, cantidad, "
                    + "cantCtrl, pUnitario, importe, carga, id_partida)values (?,?,?,?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, concep.getSubPartida());
            ps.setString(2, concep.getCveSubPrtda());
            ps.setString(3, concep.getNumConcepto());
            ps.setString(4, concep.getCodConcepto());
            ps.setString(5, concep.getDescripcion());
            ps.setString(6, concep.getUnidad());
            ps.setBigDecimal(7, concep.getCantidad());
            ps.setBigDecimal(8, concep.getCantidad());
            ps.setBigDecimal(9, concep.getpUnitario());
            ps.setBigDecimal(10, concep.getImporte());
            ps.setString(11, "MANUAL");
            ps.setInt(12, concep.getId_partida());
            ps.executeUpdate();
            
            String sql2 = "Select id_concepto from concepto where numConcepto="+concep.getNumConcepto();
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql2);
            rs.first();
            id_concepto = rs.getInt("id_concepto");
            
            String sql3 = "Insert Into insumo (cuenta, codInsumo, descripcion, unidadIns, costoIns, costoInsCtrl, "
                    + "cantidadIns, cantInsCtrl, importeIns, impInsCtrl, id_concepto)Values "
                    + "(?,?,?,?,?,?,?,?,?,?,?)";
            
            ps = con.prepareStatement(sql3);
            
            for(InsumoBean aux: listaInsTemp) {
                ps.setString(1, aux.getCuenta());
                ps.setString(2, aux.getCodInsumo());
                ps.setString(3, aux.getDescripIns());
                ps.setString(4, aux.getUnidadIns());
                ps.setBigDecimal(5, aux.getCostoIns());
                ps.setBigDecimal(6, aux.getCostoIns());
                ps.setBigDecimal(7, aux.getCantIns());
                ps.setBigDecimal(8, aux.getCantIns());
                ps.setBigDecimal(9, aux.getImporteIns());
                ps.setBigDecimal(10, aux.getImporteIns());
                ps.setInt(11, id_concepto);
                ps.executeUpdate();
            }
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public int escribirNuevaPartida(String partida, String cve, int id_pres) {
        
        int id_part = 0;
        
        try {
            
            String sql = "Insert Into partida (partida, cvePrtda, id_presupuesto) Values (?,?,?)";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, partida);
            ps.setString(2, cve);
            ps.setInt(3, id_pres);
            ps.executeUpdate();
            
            String sql2 = "Select id_partida From partida Where cvePrtda="+"'"+cve+"'";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql2);
            rs.first();
            id_part = rs.getInt("id_partida");
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return id_part;
    }
    
   
    public int consultarPartida(int id_proy, int id_pres, String cve) {
        int id_part = 0;
        
        try{
            
            String sql = "Select id_partida From partida AS A "
                    + "Inner Join presupuesto AS B ON B.id_presupuesto = A.id_presupuesto "
                    + "Inner Join proyecto AS C on C.id_proyecto ="+id_proy + " AND B.id_presupuesto = "+id_pres+ " "
                    + "AND A.cvePrtda = "+"'"+cve+"'";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            rs.first();
            if(!rs.first()) {
                id_part = 0;
            }else {
                id_part = rs.getInt("id_partida");
            }

            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return id_part;
    }
    
    public boolean consultarConsecutivo(String codigo) {
        
        boolean flag = false;
        
        try {
            
            String sql = "Select codInsumo from insumo Where codInsumo="+"'"+codigo+"'";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            if(rs.first()) {
                flag = true;
            }
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return flag;
        
    }
    
    
    
    public List<Unidad> listaUnidades() {
        
        List<Unidad> listaUnid = new ArrayList<>();
        Unidad un;
        
        try {
            
            String sql = "Select id_unidad, unidad, desc_unidad From unidad";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                un = new Unidad();
                un.setId_unidad(rs.getInt("id_unidad"));
                un.setUnidad(rs.getString("unidad"));
                un.setDesc_unidad(rs.getString("desc_unidad"));
                listaUnid.add(un);
            }
            
            
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
        return listaUnid;
    }
    
    public List<PartidaCatalogo> listaPartida() {
        
        List<PartidaCatalogo> lista = new ArrayList<>();
        PartidaCatalogo p;
        
        try {
            
            String sql = "Select id_catPrtda, catPrtda, clave From catalogopartida";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                p = new PartidaCatalogo();
                p.setId_partida(rs.getInt("id_catPrtda"));
                p.setPartida(rs.getString("catPrtda"));
                p.setCvePartida(rs.getString("clave"));
                lista.add(p);
            }
                        
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
                
        return lista;
    }
    
    public boolean consultaConsecutivo(int consec) {
        
        boolean comprobar = false;
        
        try {
            
            String sql = "Select numConcepto from concepto WHERE numConcepto="+consec;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            if(rs.first()) {
                comprobar = true;
            }
            
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return comprobar;
        
    }
    
    public void actualizarConcepto(BigDecimal precio, BigDecimal importe, int id) {
        
        try {
            
            String sql = "Update concepto set pUnitario=?, importe=? where id_concepto=?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBigDecimal(1, precio);
            ps.setBigDecimal(2, importe);
            ps.setInt(3, id);
            ps.executeUpdate();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public void editarInsumo(InsumoBean ins) {
        
                
        try {
            
            String sql = "Update insumo set unidadIns=?, costoInsCtrl=?, cantInsCtrl=?, "
                    + "impInsCtrl=? WHERE id_insumo=?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ins.getUnidadIns());
            ps.setBigDecimal(2, ins.getCostoInsCtrl());
            ps.setBigDecimal(3, ins.getCantInsCtrl());
            ps.setBigDecimal(4, ins.getImpInsCtrl());
            ps.setInt(5, ins.getId_insumo());
            ps.executeUpdate();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public void editInsumo(InsumoBean ins) {
        
                
        try {
            
            String sql = "Update insumo set descripcion=?, unidadIns=?, costoIns=?, cantidadIns=?, "
                    + "importeIns=? WHERE codInsumo=?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ins.getDescripIns());
            ps.setString(2, ins.getUnidadIns());
            ps.setBigDecimal(3, ins.getCostoInsCtrl());
            ps.setBigDecimal(4, ins.getCantInsCtrl());
            ps.setBigDecimal(5, ins.getImpInsCtrl());
            ps.setString(6, ins.getCodInsumo());
            ps.executeUpdate();
            
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public List<InsumoBean> listarInsumos(int id_con) {
        
        List<InsumoBean> listaIns = new ArrayList<>();
        InsumoBean i;
        
        try {
            
            String sql = "SELECT id_insumo, cuenta, codInsumo, descripcion, unidadIns, costoInsCtrl, cantInsCtrl, impInsCtrl "
                    + "FROM insumo where id_concepto="+id_con;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                
                i = new InsumoBean();
                i.setId_insumo(rs.getInt("id_insumo"));
                i.setCuenta(rs.getString("cuenta"));
                i.setCodInsumo(rs.getString("codInsumo"));
                i.setDescripIns(rs.getString("descripcion"));
                i.setUnidadIns(rs.getString("unidadIns"));
                i.setCostoInsCtrl(rs.getBigDecimal("costoInsCtrl"));
                i.setCantInsCtrl(rs.getBigDecimal("cantInsCtrl"));
                i.setImpInsCtrl(rs.getBigDecimal("impInsCtrl"));
                listaIns.add(i);
            }
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
        return listaIns;
    }
    
    public Concepto buscarConcepto(int concepto) {
        
        Concepto c = new Concepto();
        FormatoString fs = new FormatoString();
        
        try {
            
            String sql = "SELECT id_concepto, subPartida, cveSubPrtda, numConcepto, codConcepto, descripcion, "
                    + "unidad, cantCtrl, pUnitario, "
                    + "importe FROM concepto WHERE numConcepto="+concepto;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            rs.first();
            c.setId_concepto(rs.getInt("id_concepto"));
            c.setSubPartida(rs.getString("subPartida"));
            c.setCveSubPrtda(rs.getString("cveSubPrtda"));
            c.setNumConcepto(rs.getString("numConcepto"));
            c.setCodConcepto(rs.getString("codConcepto"));
            c.setDescripcion(rs.getString("descripcion"));
            c.setUnidad(rs.getString("unidad"));
            c.setCantidad(rs.getBigDecimal("cantCtrl"));
            c.setpUnitario(rs.getBigDecimal("pUnitario"));
            c.setImporte(rs.getBigDecimal("importe"));
            c.setCadCant(fs.formatoCantidades(rs.getBigDecimal("cantCtrl")));
            c.setCadPrecio(fs.formatoCantidades(rs.getBigDecimal("pUnitario")));
            c.setCadImp(fs.formatoCantidades(rs.getBigDecimal("importe")));
                            
            
            
            
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
        return c;
    }
    
    /*public List<Partida> enlistarPresVenta(int id_proy, int id_pres) {
        
        List<Partida> lista = new ArrayList<Partida>();
        Partida p;
        
        
        
        return lista;
    }*/
    
    public List<Partida> enlistar(int id_proy, int id_pres) {
        
        List<Partida> lista = new ArrayList<>();
        Partida p;
        FormatoString fs = new FormatoString();
        
        try {
            
            String sql = "SELECT id_partida, partida, cvePrtda FROM partida AS A " +
                        "INNER JOIN presupuesto AS B ON B.id_presupuesto = A.id_presupuesto " +
                        "INNER JOIN proyecto AS C ON C.id_proyecto ="+id_proy + " AND B.id_presupuesto ="+id_pres;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                
                p = new Partida();
                p.setId_partida(rs.getInt("id_partida"));
                p.setPartida(rs.getString("partida"));
                int id_part = rs.getInt("id_partida");
                p.setListConcepto(enlistarConceptos(id_part));
                p.setTotalesPart(obtenerTotales(id_part));
                p.setTotalesCad(fs.formatoCantidades(obtenerTotales(id_part)));
                lista.add(p);
                
            }
            
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return lista;
        
    }
    
    
    public List<Concepto> enlistarConceptos(int part) {
        
        Concepto concepto;
        List<Concepto> listConcep = new ArrayList<>();
        FormatoString formato = new FormatoString();
        CadenasCortas cadena = new CadenasCortas();
        
        try {
            
            String sql = "SELECT id_concepto, subPartida, cveSubPrtda, numConcepto, codConcepto, descripcion, "
                    + "unidad, cantCtrl, pUnitario, "
                    + "importe FROM concepto WHERE id_partida="+part;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                
                concepto = new Concepto();
                concepto.setId_concepto(rs.getInt("id_concepto"));
                concepto.setSubPartida(rs.getString("subPartida"));
                concepto.setCveSubPrtda(rs.getString("cveSubPrtda"));
                concepto.setNumConcepto(rs.getString("numConcepto"));
                concepto.setCodConcepto(rs.getString("codConcepto"));
                String shortCad = cadena.resumirDescripcion(rs.getString("descripcion"));
                concepto.setDescripcion(shortCad);
                concepto.setUnidad(rs.getString("unidad"));
                concepto.setCantidad(rs.getBigDecimal("cantCtrl"));
                concepto.setpUnitario(rs.getBigDecimal("pUnitario"));
                concepto.setImporte(rs.getBigDecimal("importe"));
                concepto.setCadPrecio(formato.formatoCantidades(rs.getBigDecimal("pUnitario")));
                concepto.setCadImp(formato.formatoCantidades(rs.getBigDecimal("importe")));
                concepto.setCadCant(formato.formatoCantidades(rs.getBigDecimal("cantCtrl")));
                listConcep.add(concepto);
                
            }
            
            //con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
        return listConcep;
    }
    
    public BigDecimal obtenerTotales(int part) {
        
        BigDecimal impTot = new BigDecimal(BigInteger.ONE);
        
        try {
            
            String sql = "SELECT SUM(importe) AS importe FROM concepto WHERE id_partida="+part;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            impTot = rs.getBigDecimal("importe");
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return impTot;
        
    }
    
       
  
    public ArrayList<PresupuestoBean> listarPresupuesto(int id_proyecto, int tipo) {
        
        PresupuestoBean pb;
        ArrayList<PresupuestoBean> listaPres = new ArrayList<>();
        
        try {
            
            String sql = "Select id_presupuesto, presupuesto, tipo from presupuesto WHERE id_proyecto="+id_proyecto
                        +" and tipo="+tipo;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                pb = new PresupuestoBean();
                pb.setId_presupuesto(rs.getInt("id_presupuesto"));
                pb.setPresupuesto(rs.getString("presupuesto"));
                pb.setTipo(rs.getInt("tipo"));
                pb.setListPartidas(llenarDatosPresupuesto(pb.getId_presupuesto()));
                listaPres.add(pb);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return listaPres;
    }
    
    public List<Partida> llenarDatosPresupuesto(int id) {
        
        List<Partida> list = new ArrayList<>();
        Partida partida;
        
        try {
            
            String sql = "select * from partida where id_presupuesto = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                partida = new Partida();
                partida.setId_partida(rs.getInt("id_partida"));
                partida.setPartida(rs.getString("nivel"));
                partida.setSubPartDe(rs.getInt("esSubNivelDe"));
                partida.setPartida(rs.getString("descripcion"));
                partida.setId_presupuesto(id);
                partida.setListConcept(listarConceptosXPart(partida.getId_partida()));
                list.add(partida);
            }
            
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
        
    }
    
    public List<Concepts> listarConceptosXPart(int id_part) {
        
        List<Concepts> list = new ArrayList<>();
        Concepts c;
        
        try {
        
            String sql = "select * from concepto where id_partida = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id_part);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                c = new Concepts();
                c.setId_concepto(rs.getInt("id_concepto"));
                c.setCodConcepto(rs.getString("codConcepto"));
                c.setDescripcion(rs.getString("descripcion"));
                c.setUnidad(rs.getString("unidad"));
                c.setCantidad(rs.getBigDecimal("cantidad"));
                c.setpUnitario(rs.getBigDecimal("pUnitario"));
                c.setImporte(rs.getBigDecimal("importe"));
                c.setId_partida(id_part);
                list.add(c);
            }
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
        
    }
    
    public ArrayList<ProyectoSimple> listarProyecto() {
    
        ArrayList<ProyectoSimple> listaP = new ArrayList<>();
        ProyectoSimple p;
        
        try {
            
            String sql = "Select id_proyecto, proyecto, cod_proy From proyecto";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                p = new ProyectoSimple();
                p.setId_proyecto(rs.getInt("id_proyecto"));
                p.setProyecto(rs.getString("proyecto"));
                p.setCod_proy(rs.getString("cod_proy"));
                listaP.add(p);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return listaP;
        
    }
    
    public List<TipoPresupto> listarTiposPres() {
        
        List<TipoPresupto> list = new ArrayList<>();
        TipoPresupto tp;
        
        try {
            
            String sql  = "select * from tipo_presupuesto";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                
                tp = new TipoPresupto();
                tp.setId_tipoPres(rs.getInt("id_tipoPres"));
                tp.setTipoPres(rs.getString("tipoPres"));
                tp.setConsec(rs.getInt("consec"));
                list.add(tp);
                
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
        
    }
    
}
