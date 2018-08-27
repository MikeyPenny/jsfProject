/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.bean.AcumuladosBean;
import com.bean.AnticipoBean;
import com.bean.AvanceBean;
import com.bean.Contratista;
import com.bean.Contrato;
import com.bean.CtaSubcontratoBean;
import com.bean.EstimaControlBean;
import com.bean.EstimacionBean;
import com.bean.InsumoContrat;
import com.bean.PresupuestoBean;
import com.bean.ProyectoSimple;
import com.util.Cadenas;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mickey
 */
public class ContratistaDAO {
    
    Connection con;
    
    public ContratistaDAO(Connection con) {
        this.con = con;
    }
    
    public boolean eliminarAvance(int id, int id_ctto) {
        
        boolean del;
        String sql;
        boolean cambEstat = false;
        PreparedStatement ps;
        
        try {
            
            con.setAutoCommit(false);
            sql = "delete from avance_insumocontto where id_avance=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            
            sql= "delete from avance where id_avance=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            
            sql = "select idavance_insumoContto from avance_insumocontto as ai " 
                + "inner join contrato_insumo as ci on ai.idcontrato_insumo = ci.idcontrato_insumo " 
                + "inner join contrato as con on con.id_contrato = ci.id_contrato and con.id_contrato = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_ctto);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                cambEstat = true;
            }
            
            if(!cambEstat) {
                sql = "update contrato set estatusContrato='CONTRATO' WHERE id_contrato=?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, id_ctto);
                ps.executeUpdate();
                
            }
            
            con.commit();
            con.close();
            
            del = true;
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            del = false;
        }
        
        return del;
        
    }
    
    public boolean registrarCambioAvance(AvanceBean av, List<InsumoContrat> edit) {
        
        boolean ed;
        String sql;
        PreparedStatement ps;
        try {
            
            con.setAutoCommit(false);
            
            sql = "update avance set importeEstimacion=? where id_avance=?";
            ps = con.prepareStatement(sql);
            ps.setBigDecimal(1, av.getImporteEstimacion());
            ps.setInt(2, av.getId_avance());
            ps.executeUpdate();
            
            sql = "update avance_insumocontto set avance=?, importeAvance=? where idavance_insumoContto=?";
            ps = con.prepareStatement(sql);
            for(InsumoContrat aux:edit) {
                ps.setBigDecimal(1, aux.getAvance());
                ps.setBigDecimal(2, aux.getImporteAvnce());
                ps.setInt(3, aux.getId_avanceInsCont());
                ps.executeUpdate();
            }
            
            ed = true;
            con.commit();
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            ed = false;
        }
        
        return ed;
        
    }
    
    public boolean editarContrato(Contrato cntto, List<InsumoContrat> edit) {
        
        boolean edi;
        
        try {
            
            con.setAutoCommit(false);
            
            String sql = "Update contrato set contratista=?, importeContrato=?, anticipoPct=?,"
                       + "anticipoImp=?, fondoGtiaPct=?, fondoGtiaImp=?, amortAntcpoPct=?, amortAntcpoImp=? WHERE "
                       + "id_contrato =?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cntto.getContratista());
            ps.setBigDecimal(2, cntto.getImporteContrato());
            ps.setFloat(3, cntto.getAnticipoPct());
            ps.setBigDecimal(4, cntto.getAnticipoImp());
            ps.setFloat(5, cntto.getFondoGtiaPct());
            ps.setBigDecimal(6, cntto.getFondoGtiaImp());
            ps.setFloat(7, cntto.getAmortAntcpoPct());
            ps.setBigDecimal(8, cntto.getAmortAntcpoImp());
            ps.setInt(9, cntto.getId_contrato());
            ps.executeUpdate();
            
            
             
            String sql2 = "Update contrato_insumo set cantContrato=?, precioUnitContrat=?, importeContrat=? where idcontrato_insumo=?";
            ps = con.prepareStatement(sql2);
            
            for(InsumoContrat aux:edit) {
                
                ps.setBigDecimal(1, aux.getCantContrato());
                ps.setBigDecimal(2, aux.getPresUnit());
                ps.setBigDecimal(3, aux.getImporteCont());
                ps.setInt(4, aux.getId_insumo());
                ps.executeUpdate();
            }
           
            con.commit();
            con.close();
            
            edi = true;
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            edi = false;
        }
        
        return edi;
        
        
    }
    
    public void estimarAvance(EstimacionBean estim, List<InsumoContrat> lista, int id_avance, int id_proy, int id_pres) {
        
        try {
            
            String sql3 = "SELECT MAX(nroEstimacion ) AS estimacion FROM avance AS A " +
                        "INNER JOIN avance_insumocontto AS B ON B.id_avance = A.id_avance " +
                        "INNER JOIN contrato_insumo AS C ON C.idcontrato_insumo = B.idcontrato_insumo " +
                        "INNER JOIN insumo AS D ON D.id_insumo = C.id_insumo " +
                        "INNER JOIN concepto AS E ON E.id_concepto = D.id_concepto " +
                        "INNER JOIN partida AS F ON F.id_partida = E.id_partida " +
                        "INNER JOIN presupuesto AS G ON G.id_presupuesto = F.id_presupuesto " +
                        "INNER JOIN proyecto AS H ON H.id_proyecto = ? AND G.id_presupuesto = ? FOR UPDATE";
            
            PreparedStatement ps = con.prepareStatement(sql3);
            ps.setInt(1, id_proy);
            ps.setInt(2, id_pres);
            ResultSet rs = ps.executeQuery();
            
            rs.first();
            int nroEstim = rs.getInt("estimacion") + 1;
            
            
            String sql = "UPDATE avance set estatusAvance=?, anticipoAmort=?, retFdoGtia=?, subtotal=?, iva=?, retFlete=?, "
                    + "retRenta=?, importeTotal=?, nroEstimacion=? WHERE id_avance=?";
            
            ps = con.prepareStatement(sql);
            ps.setString(1, estim.getEstatus());
            ps.setBigDecimal(2, estim.getAmortAntcpo());
            ps.setBigDecimal(3, estim.getRtnGarantia());
            ps.setBigDecimal(4, estim.getSubtotal());
            ps.setBigDecimal(5, estim.getIvaEst());
            ps.setBigDecimal(6, estim.getRtnFlete());
            ps.setBigDecimal(7, estim.getRtnRenta());
            ps.setBigDecimal(8, estim.getTotal());
            ps.setInt(9, nroEstim);
            ps.setInt(10, id_avance);
            ps.executeUpdate();
            
            String sql2 = "UPDATE avance_insumocontto SET estatusAvanceIns=? WHERE id_avance=?";
            
            ps = con.prepareStatement(sql2);
            ps.setString(1, "ESTIMADO");
            ps.setInt(2, id_avance);
            ps.executeUpdate();
            
            
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }        
    }
    
    public boolean cancelarEstimacion(int id_av) {
        
        boolean del;
        
        try {
            
            con.setAutoCommit(false);
            
            String sql = "UPDATE avance set estatusAvance=? WHERE id_avance=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "AVANCE");
            ps.setInt(2, id_av);
            ps.executeUpdate();
            
            sql = "UPDATE avance_insumocontto SET estatusAvanceIns=? WHERE id_avance=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, "AVANCE");
            ps.setInt(2, id_av);
            ps.executeUpdate();
            
            con.commit();
            con.close();
            
            del = true;
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            del = false;
        }
        
        return del;
        
    }
    
    public EstimaControlBean buscarUltEstimacion(int id_cont) {
        
        EstimaControlBean ecb = new EstimaControlBean();
        
        try {
            String sql = "SELECT MAX( A.id_avance ) AS id_avance, importeEstimacion AS importe, anticipoAmort AS amortizado, " +
                         "retFdoGtia as garantia FROM avance AS A " +
                         "INNER JOIN avance_insumocontto AS B ON B.id_avance = A.id_avance " +
                         "AND A.estatusAvance =  'ESTIMADO' OR A.estatusAvance =  'FACTURADO' " +
                         "INNER JOIN contrato_insumo AS C ON C.idcontrato_insumo = B.idcontrato_insumo " +
                         "INNER JOIN contrato AS D ON D.id_contrato = C.id_contrato " +
                         "AND D.id_contrato ="+id_cont;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                
                ecb.setId_avance(rs.getInt("id_avance"));
                ecb.setImporte(rs.getBigDecimal("importe"));
                ecb.setAmortizado(rs.getBigDecimal("amortizado"));
                ecb.setGtiaRetenida(rs.getBigDecimal("garantia"));
                
            }
            if(ecb.getImporte()== null) {
                ecb.setImporte(BigDecimal.ZERO);
                ecb.setAmortizado(BigDecimal.ZERO);
                ecb.setPendXAmort(BigDecimal.ZERO);
                ecb.setGtiaRetenida(BigDecimal.ZERO);
            }
            
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return ecb;
    }
    
    public AcumuladosBean acumularImportes(int id_cont) {
        
        AcumuladosBean acumul = new AcumuladosBean();
        
        try {
            
            String sql = "select sum(av.importeEstimacion) as importe, sum(av.anticipoAmort) as amortizado, " 
                    + "sum(av.retFdoGtia) as retenido from avance as av "
                    + "inner join avance_insumocontto as ai on av.id_avance = ai.id_avance "
                    + "inner join contrato_insumo as ci on ai.idcontrato_insumo = ci.idcontrato_insumo "
                    + "inner join contrato as con on con.id_contrato = ci.id_contrato and con.id_contrato = ? "
                    + "and av.estatusAvance = 'ESTIMADO' or av.estatusAvance = 'FACTURADO'";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id_cont);
            ResultSet rs = ps.executeQuery();
            
            
            while(rs.next()) {
                acumul.setAcumImporte(rs.getBigDecimal("importe"));
                acumul.setAcumAmort(rs.getBigDecimal("amortizado"));
                acumul.setAcumGtia(rs.getBigDecimal("retenido"));
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return acumul;
    }
    
    public Contrato buscarDatosContto(int id_avn) {
        
        Contrato cntto = new Contrato();
        
        try {
            
            String sql = "SELECT DISTINCT A.id_contrato, A.importeContrato, A.anticipoImp, A.fondoGtiaImp, A.amortAntcpoPct, " +
                         "A.tipo, A.fondoGtiaPct FROM contrato AS A " +
                         "INNER JOIN contrato_insumo AS B ON B.id_contrato = A.id_contrato " +
                         "INNER JOIN avance_insumocontto AS C ON C.idcontrato_insumo = B.idcontrato_insumo " +
                         "INNER JOIN avance AS D ON D.id_avance = C.id_avance " +
                         "AND D.id_avance ="+id_avn;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                
                cntto.setId_contrato(rs.getInt("id_contrato"));
                cntto.setImporteContrato(rs.getBigDecimal("importeContrato"));
                cntto.setAnticipoImp(rs.getBigDecimal("anticipoImp"));
                cntto.setFondoGtiaImp(rs.getBigDecimal("fondoGtiaImp"));
                cntto.setAmortAntcpoPct(rs.getFloat("amortAntcpoPct"));
                cntto.setTipo(rs.getString("tipo"));
                cntto.setFondoGtiaPct(rs.getFloat("fondoGtiaPct"));
                
            }
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return cntto;
    }
    
    public List<AvanceBean> listarAvances(int id_contrato) {
        
        List<AvanceBean> listAv = new ArrayList<>();
        AvanceBean ab;
        
        try {
            
            String sql = "SELECT DISTINCT A.id_avance, A.importeEstimacion, A.nroAvance, A.fecha, A.estatusAvance FROM avance AS A " +
                         "INNER JOIN avance_insumocontto AS B ON B.id_avance = A.id_avance AND A.estatusAvance != 'CANCELADO' " +
                         "INNER JOIN contrato_insumo AS C ON C.idcontrato_insumo = B.idcontrato_insumo AND " +
                         "C.id_contrato = ?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id_contrato);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                ab = new AvanceBean();
                ab.setId_avance(rs.getInt("id_avance"));
                ab.setImporteEstimacion(rs.getBigDecimal("importeEstimacion"));
                ab.setNroAvance(rs.getInt("nroAvance"));
                ab.setFecha(rs.getString("fecha"));
                ab.setEstatusAvance(rs.getString("estatusAvance"));
                listAv.add(ab);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return listAv;
    }
    
    public List<AvanceBean> listarAvancesToEdit(int id_contrato) {
        
        List<AvanceBean> listAv = new ArrayList<>();
        AvanceBean ab;
        
        try {
            
            String sql = "SELECT DISTINCT A.id_avance, A.importeEstimacion, A.nroAvance, A.fecha, A.estatusAvance FROM avance AS A " +
                         "INNER JOIN avance_insumocontto AS B ON B.id_avance = A.id_avance AND A.estatusAvance = 'AVANCE' " +
                         "INNER JOIN contrato_insumo AS C ON C.idcontrato_insumo = B.idcontrato_insumo AND " +
                         "C.id_contrato = ?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id_contrato);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                ab = new AvanceBean();
                ab.setId_avance(rs.getInt("id_avance"));
                ab.setImporteEstimacion(rs.getBigDecimal("importeEstimacion"));
                ab.setNroAvance(rs.getInt("nroAvance"));
                ab.setFecha(rs.getString("fecha"));
                ab.setEstatusAvance(rs.getString("estatusAvance"));
                listAv.add(ab);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return listAv;
    }
    
    public List<AvanceBean> listarAvancesEstimados(int id_contrato) {
        
        List<AvanceBean> listAv = new ArrayList<>();
        AvanceBean ab;
        
        try {
            
            String sql = "SELECT DISTINCT A.id_avance, A.importeEstimacion, A.nroAvance, A.fecha, A.estatusAvance FROM avance AS A " +
                         "INNER JOIN avance_insumocontto AS B ON B.id_avance = A.id_avance AND A.estatusAvance != 'CANCELADO' " +
                         "and A.estatusAvance = 'ESTIMADO' " +
                         "INNER JOIN contrato_insumo AS C ON C.idcontrato_insumo = B.idcontrato_insumo AND " +
                         "C.id_contrato = ?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id_contrato);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                ab = new AvanceBean();
                ab.setId_avance(rs.getInt("id_avance"));
                ab.setImporteEstimacion(rs.getBigDecimal("importeEstimacion"));
                ab.setNroAvance(rs.getInt("nroAvance"));
                ab.setFecha(rs.getString("fecha"));
                ab.setEstatusAvance(rs.getString("estatusAvance"));
                listAv.add(ab);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return listAv;
    }
    
    public void cancelarAvance(List<InsumoContrat> list, int id_av) {
        
        try {
            
            con.setAutoCommit(false);
            
            String sql = "Update avance set estatusAvance=? WHERE id_avance=?";
            PreparedStatement ps = con.prepareStatement(sql);
            //ps = con.prepareStatement(sql);
            ps.setString(1, "CANCELADO");
            ps.setInt(2, id_av);
            ps.executeUpdate();
            
            String sql2 = "UPDATE avance_insumocontto SET estatusAvanceIns=? WHERE idavance_insumocontto=?";
            
            ps = con.prepareStatement(sql2);
            for(InsumoContrat aux: list) {
                ps.setString(1, "CANCELADO");
                ps.setInt(2, aux.getId_avanceInsCont());
                ps.executeUpdate();
            }
            
            con.commit();
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public void actualizarAvance(int id_av, List<InsumoContrat> list, BigDecimal suma) {
        
        try {
            
            String sql = "Update avance set importeEstimacion=? WHERE id_avance=?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBigDecimal(1, suma);
            ps.setInt(2, id_av);
            ps.executeUpdate();
            
            String sql2 = "Update avance_insumocontto set avance=?, importeAvance=? where idavance_insumocontto=?";
            ps = con.prepareStatement(sql2);
            
            for(InsumoContrat aux: list) {
                ps.setBigDecimal(1, aux.getAvance());
                ps.setBigDecimal(2, aux.getImporteAvnce());
                ps.setInt(3, aux.getId_avanceInsCont());
                ps.executeUpdate();
            }
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public List<InsumoContrat> listarInsumoAvance(int id_av) {
        
        List<InsumoContrat> listInsAv = new ArrayList<>();
        InsumoContrat ic;
        
        try {
            String sql = "SELECT A.idavance_insumocontto, A.codigo, A.descripcion, A.unidad, A.avance, A.precioUnit, "
                    + "A.importeAvance, B.cantContrato, A.idcontrato_insumo FROM avance_insumocontto AS A "
                    + "INNER JOIN contrato_insumo AS B ON B.idcontrato_insumo = A.idcontrato_insumo "
                    + "AND A.estatusAvanceIns != 'CANCELADO' "
                    + "AND id_avance="+id_av;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()){
                ic = new InsumoContrat();
                ic.setId_avanceInsCont(rs.getInt("idavance_insumoContto"));
                int id_contIns = rs.getInt("idcontrato_insumo");
                ic.setCodInsumo(rs.getString("codigo"));
                ic.setDescripcion(rs.getString("descripcion"));
                ic.setUnidad(rs.getString("unidad"));
                ic.setAvance(rs.getBigDecimal("avance"));
                ic.setPresUnit(rs.getBigDecimal("precioUnit"));
                ic.setImporteAvnce(rs.getBigDecimal("importeAvance"));
                ic.setCantContrato(rs.getBigDecimal("cantContrato"));
                ic.setSumaAvance(sumarAvance(id_contIns));
                ic.setCantDispAvn((ic.getCantContrato().subtract(ic.getSumaAvance())).add(ic.getAvance()));
                ic.setId_avance(id_av);
                listInsAv.add(ic);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return listInsAv;
    }
    
    public BigDecimal sumarAvanceInsumo(int id_contIns) {
        
        BigDecimal sumaAvances = BigDecimal.ZERO;
        
        try {
            
            String sql = "Select SUM(avance) AS sumaAvance FROM avance_insumocontto WHERE idcontrato_insumo="+id_contIns;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            if(!rs.first()) {
                sumaAvances = BigDecimal.ZERO;
            }else {
                sumaAvances = rs.getBigDecimal("sumaAvance");
            }
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return sumaAvances;
    }
    
    public List<AvanceBean> buscarAvances(int id_cont) {
        
        List<AvanceBean> listAv = new ArrayList<>();
        AvanceBean ab;
        
        try {
            
            String sql = "SELECT DISTINCT A.id_avance, A.importeEstimacion, A.nroAvance, A.fecha "
                       + "FROM avance AS A " 
                       + "INNER JOIN avance_insumocontto AS B ON B.id_avance = A.id_avance AND A.estatusAvance != 'PAGADO' "
                       + "AND A.estatusAvance != 'CANCELADO' "
                       + "INNER JOIN contrato_insumo AS C ON C.idcontrato_insumo = B.idcontrato_insumo "
                       + "INNER JOIN contrato AS D ON D.id_contrato = C.id_contrato AND C.id_contrato = "+id_cont;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                ab = new AvanceBean();
                ab.setId_avance(rs.getInt("id_avance"));
                ab.setImporteEstimacion(rs.getBigDecimal("importeEstimacion"));
                ab.setNroAvance(rs.getInt("nroAvance"));
                ab.setFecha(rs.getString("fecha"));
                listAv.add(ab);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return listAv;
    }
    
    public void eliminarAntcpo(int id_contto) {
        
        try {
            
            String sql = "DELETE FROM anticipo WHERE id_contrato = "+id_contto;
            
            Statement s = con.createStatement();
            s.executeUpdate(sql);
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public boolean comprobarAnticipo(int id_cont) {
        boolean anticipo = false;
        
        try {
            String sql = "Select id_contrato FROM anticipo WHERE id_contrato="+id_cont;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            if(rs.first()) {
                anticipo = true;
                
            }
                
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return anticipo;
    }
    
    
    public Contrato completarContrato(int id_cont) {
        
        Contrato contto = new Contrato();
        
        try {
            
            String sql = "Select contratista, anticipoPct, anticipoImp, fondoGtiaPct, fondoGtiaImp, amortAntcpoPct, "
                    + "amortAntcpoImp From contrato WHERE id_contrato="+id_cont;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                
                contto.setContratista(rs.getString("contratista"));
                contto.setAnticipoPct(rs.getFloat("anticipoPct"));
                contto.setAnticipoImp(rs.getBigDecimal("anticipoImp"));
                contto.setFondoGtiaPct(rs.getFloat("fondoGtiaPct"));
                contto.setFondoGtiaImp(rs.getBigDecimal("fondoGtiaImp"));
                contto.setAmortAntcpoPct(rs.getFloat("amortAntcpoPct"));
                contto.setAmortAntcpoImp(rs.getBigDecimal("amortAntcpoImp"));
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return contto;
    }
    
    public String consultarContratoEstatus(int id_cont) {
        
        String estatus = "";
        
        try {
            
            String sql = "Select estatusContrato FROM contrato WHERE id_contrato="+id_cont;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            estatus = rs.getString("estatusContrato");
            
            
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
        
        return estatus;
        
    }
    
    public void registrarAnticipo(AnticipoBean antcpo) {



        try {
            String sql = "INSERT INTO anticipo(id_contrato, nroContrato, importe, contratista, tipo, iva, "
                    + "rtnRenta, rtnFlete, total)VALUES (?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, antcpo.getId_contrato());
            ps.setInt(2, antcpo.getNroContrato());
            ps.setBigDecimal(3, antcpo.getImporte());
            ps.setString(4, antcpo.getContratista());
            ps.setString(5, antcpo.getTipo());
            ps.setBigDecimal(6, antcpo.getIva());
            ps.setBigDecimal(7, antcpo.getRtnRenta());
            ps.setBigDecimal(8, antcpo.getRtnFlete());
            ps.setBigDecimal(9, antcpo.getTotal());
            ps.executeUpdate();


        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    
    public void registrarAnticipoActual(AnticipoBean antcpo) {
         


        try {
            String sql = "update anticipo set importe=?, contratista=?, iva=?, rtnRenta=?, rtnFlete=?, total=? "
                    + "where id_contrato=?";

            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setBigDecimal(1, antcpo.getImporte());
            ps.setString(2, antcpo.getContratista());
            ps.setBigDecimal(3, antcpo.getIva());
            ps.setBigDecimal(4, antcpo.getRtnRenta());
            ps.setBigDecimal(5, antcpo.getRtnFlete());
            ps.setBigDecimal(6, antcpo.getTotal());
            ps.setInt(7, antcpo.getId_contrato());
            ps.executeUpdate();


        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
         
    }
    
    public int registrarAvance(AvanceBean avance, int id_cont, List<InsumoContrat> listSelec) {
        
        int nroAvance = 0;
        
        try{
            
            con.setAutoCommit(false);
            
            String sql = "SELECT MAX( A.nroAvance ) AS nroAvance " 
                    + "FROM avance AS A " 
                    + "INNER JOIN avance_insumocontto AS B ON B.id_avance = A.id_avance " 
                    + "INNER JOIN contrato_insumo AS C ON C.idcontrato_insumo = B.idcontrato_insumo " 
                    + "AND C.id_contrato ="+id_cont
                    + " FOR UPDATE";
        
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            if(!rs.first()) {
                nroAvance = 1;
                //System.out.println(nroAvance);
            }else {
                nroAvance = rs.getInt("nroAvance") + 1;
            }
            
            String sql2 = "Insert into avance(estatusAvance, importeEstimacion, fecha, nroAvance)values (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql2);
            ps.setString(1, avance.getEstatusAvance());
            ps.setBigDecimal(2, avance.getImporteEstimacion());
            ps.setString(3, avance.getFecha());
            ps.setInt(4, nroAvance);
            ps.executeUpdate();
            
            String sql3= "Select last_insert_id()";
            rs = s.executeQuery(sql3);
            rs.first();
            int id_av = rs.getInt(1);
            
            String sql4 = "Insert into avance_insumocontto(codigo, descripcion, unidad, avance, precioUnit, importeAvance, "
                    + "id_avance, idcontrato_insumo)values (?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sql4);
            
            for(InsumoContrat aux: listSelec) {
                ps.setString(1, aux.getCodInsumo());
                ps.setString(2, aux.getDescripcion());
                ps.setString(3, aux.getUnidad());
                ps.setBigDecimal(4, aux.getAvance());
                ps.setBigDecimal(5, aux.getPresUnit());
                ps.setBigDecimal(6, aux.getImporteAvnce());
                ps.setInt(7, id_av);
                ps.setInt(8, aux.getId_insumo());
                ps.executeUpdate();
            }
            
            String sql5 = "UPDATE contrato set estatusContrato=? WHERE id_contrato=?";
            ps = con.prepareStatement(sql5);
            ps.setString(1, "AVANCE");
            ps.setInt(2, id_cont);
            ps.executeUpdate();

            con.commit();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
        
        return nroAvance;
    }
    
    public List<InsumoContrat> listarInsumContrato(int id_contto) {
        
        List<InsumoContrat> listInsum = new ArrayList<>();
        InsumoContrat ic;
        
        try {
            
            String sql = "SELECT A.idcontrato_insumo, A.id_insumo, A.codInsumo, A.codConcepto, A.descripcion, "
                    + "A.unidad, A.cantContrato, A.precioUnitContrat, A.importeContrat, C.cantidad FROM " 
                    + "contrato_insumo AS A " 
                    + "INNER JOIN insumo AS B ON B.id_insumo = A.id_insumo AND A.id_contrato = ? "
                    + "AND A.estatusIns =  'CONTRATADO' "
                    + "INNER JOIN concepto AS C ON C.id_concepto = B.id_concepto";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id_contto);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                ic = new InsumoContrat();
                ic.setId_insumo(rs.getInt("idcontrato_insumo"));
                int id_ins = rs.getInt("idcontrato_insumo");
                ic.setCodInsumo(rs.getString("codInsumo"));
                ic.setCodConcepto(rs.getString("codConcepto"));
                ic.setDescripcion(rs.getString("descripcion"));
                ic.setUnidad(rs.getString("unidad"));
                ic.setCantContrato(rs.getBigDecimal("cantContrato"));
                ic.setPresUnit(rs.getBigDecimal("precioUnitContrat"));
                ic.setImporteCont(rs.getBigDecimal("importeContrat"));
                ic.setSumaAvance(sumarAvance(id_ins));
                ic.setCantCtrl(ic.getCantContrato().subtract(ic.getSumaAvance()));
                if(ic.getCantCtrl().floatValue() > 0.0) {
                    listInsum.add(ic);
                }
                
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return listInsum;
    }
    
    public BigDecimal sumarAvance(int id) {
        
        BigDecimal sumaAv = BigDecimal.ZERO;
        
        try {
            String sql = "Select SUM(avance) as avance from avance_insumocontto where idcontrato_insumo="+id+ 
                         " AND estatusAvanceIns != 'CANCELADO'";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            rs.first();
            
            if(rs.getBigDecimal("avance") == null) {
                sumaAv = BigDecimal.ZERO;
            }else {
                sumaAv = rs.getBigDecimal("avance");
            }
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return sumaAv;
    }
    
    public List<Contrato> listarContratos(int id_proy, int id_pres) {
        
        List<Contrato> listPre = new ArrayList<>();
        Contrato c;
        
        try {
            
            String sql = "Select DISTINCT A.numContrato, A.id_contrato, A.tipo, A.contratista, A.importeContrato From contrato AS A  "
                    + "INNER JOIN contrato_insumo AS B ON B.id_contrato = A.id_contrato AND A.estatusContrato != 'CANCELADO' "
                    + "INNER JOIN insumo AS C ON C.id_insumo = B.id_insumo "
                    + "INNER JOIN concepto AS D ON D.id_concepto = C.id_concepto "
                    + "INNER JOIN partida AS E ON E.id_partida = D.id_partida "
                    + "INNER JOIN presupuesto AS F ON F.id_presupuesto = E.id_presupuesto "
                    + "INNER JOIN proyecto AS G ON G.id_proyecto="+id_proy+ " AND F.id_presupuesto="+id_pres+
                    " ORDER BY A.numContrato";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                c = new Contrato();
                c.setId_contrato(rs.getInt("id_contrato"));
                c.setTipo(rs.getString("tipo"));
                c.setContratista(rs.getString("contratista"));
                c.setImporteContrato(rs.getBigDecimal("importeContrato"));
                c.setNumContrato(rs.getInt("numContrato"));
                if(c.getImporteContrato().floatValue() > sumarAvanceXContrato(c.getId_contrato()).floatValue()) {
                    listPre.add(c);
                }
                /*System.out.println(sumarAvanceXContrato(c.getId_contrato()));
                System.out.println(c.getImporteContrato());*/
                
                
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return listPre;
    }
    
    public List<Contrato> listarContratosPorEstimar(int id_proy, int id_pres) {
        
        List<Contrato> listPre = new ArrayList<>();
        Contrato c;
        
        try {
            
            String sql = "Select DISTINCT A.numContrato, A.id_contrato, A.tipo, A.contratista, A.importeContrato From contrato AS A  "
                    + "INNER JOIN contrato_insumo AS B ON B.id_contrato = A.id_contrato AND A.estatusContrato != 'CANCELADO' "
                    + "INNER JOIN insumo AS C ON C.id_insumo = B.id_insumo "
                    + "INNER JOIN concepto AS D ON D.id_concepto = C.id_concepto "
                    + "INNER JOIN partida AS E ON E.id_partida = D.id_partida "
                    + "INNER JOIN presupuesto AS F ON F.id_presupuesto = E.id_presupuesto "
                    + "INNER JOIN proyecto AS G ON G.id_proyecto="+id_proy+ " AND F.id_presupuesto="+id_pres+
                    " ORDER BY A.numContrato";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                c = new Contrato();
                c.setId_contrato(rs.getInt("id_contrato"));
                c.setTipo(rs.getString("tipo"));
                c.setContratista(rs.getString("contratista"));
                c.setImporteContrato(rs.getBigDecimal("importeContrato"));
                c.setNumContrato(rs.getInt("numContrato"));
                listPre.add(c);
                
                /*System.out.println(sumarAvanceXContrato(c.getId_contrato()));
                System.out.println(c.getImporteContrato());*/
                
                
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return listPre;
    }
    
    public List<Contrato> listarContratosEstimados(int id_proy, int id_pres) {
        
        List<Contrato> listPre = new ArrayList<>();
        Contrato c;
        
        try {
            
            String sql = "select con.id_contrato, con.tipo, con.contratista, con.importeContrato, con.numContrato from contrato as con " 
                    + "inner join contrato_insumo as ci on ci.id_contrato = con.id_contrato "
                    + "inner join avance_insumocontto as ai on ai.idcontrato_insumo = ci.idcontrato_insumo " 
                    + "inner join avance as av on av.id_avance = ai.id_avance and av.estatusAvance = 'ESTIMADO' " 
                    + "inner join insumo as ins on ins.id_insumo = ci.id_insumo " 
                    + "inner join concepto as ccp on ccp.id_concepto = ins.id_concepto " 
                    + "inner join partida as part on part.id_partida = ccp.id_partida " 
                    + "inner join presupuesto as pres on pres.id_presupuesto = part.id_presupuesto " 
                    + "inner join proyecto as proy on proy.id_proyecto=? AND pres.id_presupuesto=?"
                    + " ORDER BY con.numContrato";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id_proy);
            ps.setInt(2, id_pres);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                c = new Contrato();
                c.setId_contrato(rs.getInt("id_contrato"));
                c.setTipo(rs.getString("tipo"));
                c.setContratista(rs.getString("contratista"));
                c.setImporteContrato(rs.getBigDecimal("importeContrato"));
                c.setNumContrato(rs.getInt("numContrato")); 
                listPre.add(c);
                
                /*System.out.println(sumarAvanceXContrato(c.getId_contrato()));
                System.out.println(c.getImporteContrato());*/
                
                
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return listPre;
    }
    
    public BigDecimal sumarAvanceXContrato(int id) {
        
        BigDecimal sumaAvance = BigDecimal.ZERO;
        
        try {
            
            String sql = "select sum(importeAvance) as sumaAvance from avance_insumocontto as ai " 
                    + "inner join contrato_insumo as ci on ci.idcontrato_insumo = ai.idcontrato_insumo " 
                    +"inner join contrato as ctto on ctto.id_contrato = ci.id_contrato and ctto.id_contrato = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                sumaAvance = rs.getBigDecimal("sumaAvance");
            }
            
            if(sumaAvance == null) {
                sumaAvance = BigDecimal.ZERO;
            }
            
            //System.out.println("Ctto:"+id+" Avance:"+sumaAvance);
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return sumaAvance;
        
    }
    
    public void registrarContrato(Contrato cntto, List<Integer> listId, List<InsumoContrat> listIns) {
        
        
        
        try {
            
            String sql = "Update contrato set estatusContrato=?, contratista=?, importeContrato=?, anticipoPct=?,"
                       + "anticipoImp=?, fondoGtiaPct=?, fondoGtiaImp=?, amortAntcpoPct=?, amortAntcpoImp=? WHERE "
                       + "id_contrato =?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cntto.getEstatusContrato());
            ps.setString(2, cntto.getContratista());
            ps.setBigDecimal(3, cntto.getImporteContrato());
            ps.setFloat(4, cntto.getAnticipoPct());
            ps.setBigDecimal(5, cntto.getAnticipoImp());
            ps.setFloat(6, cntto.getFondoGtiaPct());
            ps.setBigDecimal(7, cntto.getFondoGtiaImp());
            ps.setFloat(8, cntto.getAmortAntcpoPct());
            ps.setBigDecimal(9, cntto.getAmortAntcpoImp());
            ps.setInt(10, cntto.getId_contrato());
            ps.executeUpdate();
            
            String q = "Update contrato_insumo set estatusIns=? WHERE id_contrato="+cntto.getId_contrato();
            ps = con.prepareStatement(q);
            ps.setString(1, "CONTRATADO");
            ps.executeUpdate();
             
            String sql2 = "Update contrato_insumo set precioUnitCOntrat=? where idcontrato_insumo=?";
            ps = con.prepareStatement(sql2);
            
            for(Integer auxInt: listId) {
                InsumoContrat ic = listIns.get(auxInt);
                ps.setBigDecimal(1, ic.getPresUnit());
                ps.setInt(2, ic.getId_insumo());
                ps.executeUpdate();
            }
           
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
    }
    
    public void editarPreContrato(Contrato c, List<InsumoContrat> listIns) {
        
        try {
            
            String sql = "Update contrato set importeContrato=? Where id_contrato=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBigDecimal(1, c.getImporteContrato());
            ps.setInt(2, c.getId_contrato());
            ps.executeUpdate();
            
            String sql2 = "Update contrato_insumo set cantContrato=?, importeContrat=? Where idcontrato_insumo=?";
            ps = con.prepareStatement(sql2);
            
            for(InsumoContrat aux: listIns) {
                ps.setBigDecimal(1, aux.getCantContrato());
                ps.setBigDecimal(2, aux.getImporteCont());
                ps.setInt(3, aux.getId_insumo());
                ps.executeUpdate();
            }
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public void elimInsumoPreContrato(Contrato c, List<Integer> idList, List<InsumoContrat> listIns) {
        
        try {
            
            String sql = "Update contrato set importeContrato=?  Where id_contrato=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBigDecimal(1, c.getImporteContrato());
            ps.setInt(2, c.getId_contrato());
            ps.executeUpdate();
            
            String sql2 = "Update contrato_insumo set cantContrato=?, importeContrat=? Where idcontrato_insumo=?";
            ps = con.prepareStatement(sql2);
            
            for(InsumoContrat aux: listIns) {
                ps.setBigDecimal(1, aux.getCantContrato());
                ps.setBigDecimal(2, aux.getImporteCont());
                ps.setInt(3, aux.getId_insumo());
                ps.executeUpdate();
            }
            
            String sql3 ="Update contrato_insumo set estatusIns= 'CANCELADO' Where idcontrato_insumo=?";
            ps = con.prepareStatement(sql3);
            
            for(int auxId: idList) {
                ps.setInt(1, auxId);
                ps.executeUpdate();
            }
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public void cancelarPreContrato(int id_cont) {
        
        try {
            
            String sql = "Update contrato set estatusContrato='CANCELADO' Where id_contrato="+id_cont;
            Statement s = con.createStatement();
            s.executeUpdate(sql);
            String q2 = "Update contrato_insumo set estatusIns='CANCELADO' Where id_contrato="+id_cont;
            s.executeUpdate(q2);
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public List<Contratista> listarContratista() {

        List<Contratista> listCtta = new ArrayList<>();
        Contratista ctta;

        try {

            String sql = "SELECT * FROM proveedor WHERE tipo=2 OR tipo=3";

            Statement s = con.createStatement();
            ResultSet rs =  s.executeQuery(sql);

            while(rs.next()) {

                ctta =  new Contratista();
                ctta.setId_contratista(rs.getInt("idProveedor"));
                ctta.setRfc(rs.getString("rfc"));
                ctta.setRazonSocial(rs.getString("razonSocial"));
                listCtta.add(ctta);

            }

        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return listCtta;
    }
    
    public void cancelarContrato(int id_contto) {
        
        try {
            
            con.setAutoCommit(false);
            
            Statement s = con.createStatement();
            
            String sql3 = "DELETE FROM anticipo WHERE id_contrato="+id_contto;
            s.executeUpdate(sql3);
            
            String sql2 = "DELETE FROM contrato_insumo WHERE id_contrato = "+id_contto;           
            s.executeUpdate(sql2);
            
            String sql = "DELETE FROM contrato WHERE id_contrato = "+id_contto;
            s.executeUpdate(sql);
                        
            
            
            con.commit();
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public List<Contrato> listarPreContratos(int id_proy, int id_pres) {
        
        List<Contrato> listPre = new ArrayList<>();
        Contrato c;
        
        try {
            
            String sql = "Select A.id_contrato, A.tipo, A.importeContrato, A.numContrato From contrato AS A  "
                    + "INNER JOIN contrato_insumo AS B ON B.id_contrato = A.id_contrato AND A.estatusContrato = 'PENDIENTE' "
                    + "INNER JOIN insumo AS C ON C.id_insumo = B.id_insumo "
                    + "INNER JOIN concepto AS D ON D.id_concepto = C.id_concepto "
                    + "INNER JOIN partida AS E ON E.id_partida = D.id_partida "
                    + "INNER JOIN presupuesto AS F ON F.id_presupuesto = E.id_presupuesto "
                    + "INNER JOIN proyecto AS G ON G.id_proyecto="+id_proy+ " AND F.id_presupuesto="+id_pres+
                    " ORDER BY A.id_contrato";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                c = new Contrato();
                c.setId_contrato(rs.getInt("id_contrato"));
                c.setTipo(rs.getString("tipo"));
                c.setImporteContrato(rs.getBigDecimal("importeContrato"));
                c.setNumContrato(rs.getInt("numCOntrato"));
                listPre.add(c);
            }    
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        //System.out.println(listPre.size());
        return listPre;
    }
    
    public List<Contrato> listarContratosEdit(int id_proy, int id_pres) {
        
        List<Contrato> listPre = new ArrayList<>();
        Contrato c;
        
        try {
            
            String sql = "Select Distinct A.id_contrato, A.tipo, A.importeContrato, A.numContrato From contrato AS A  "
                    + "INNER JOIN contrato_insumo AS B ON B.id_contrato = A.id_contrato AND A.estatusContrato = 'CONTRATO' "
                    + "INNER JOIN insumo AS C ON C.id_insumo = B.id_insumo "
                    + "INNER JOIN concepto AS D ON D.id_concepto = C.id_concepto "
                    + "INNER JOIN partida AS E ON E.id_partida = D.id_partida "
                    + "INNER JOIN presupuesto AS F ON F.id_presupuesto = E.id_presupuesto "
                    + "INNER JOIN proyecto AS G ON G.id_proyecto="+id_proy+ " AND F.id_presupuesto="+id_pres+
                    " ORDER BY A.id_contrato";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                c = new Contrato();
                c.setId_contrato(rs.getInt("id_contrato"));
                c.setTipo(rs.getString("tipo"));
                c.setImporteContrato(rs.getBigDecimal("importeContrato"));
                c.setNumContrato(rs.getInt("numCOntrato"));
                listPre.add(c);
            }    
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        //System.out.println(listPre.size());
        return listPre;
    }
    
    public List<InsumoContrat> listarInsumos(int id_contto, int id_proy, int id_pres) {
        
        List<InsumoContrat> listInsum = new ArrayList<>();
        InsumoContrat ic;
        //System.out.println("Contto "+id_contto);
        try {
            String sql = "SELECT A.idcontrato_insumo, A.id_insumo, A.codInsumo, A.codConcepto, A.descripcion, "
                    + "A.unidad, A.cantContrato, A.precioUnitContrat, A.importeContrat, C.cantidad, B.cantidadIns, "
                    + "B.cantInsctrl FROM contrato_insumo AS A " 
                    + "INNER JOIN insumo AS B ON B.id_insumo = A.id_insumo AND A.id_contrato = ? "
                    + "AND A.estatusIns !=  'CANCELADO' "
                    + "INNER JOIN concepto AS C ON C.id_concepto = B.id_concepto";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id_contto);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                ic = new InsumoContrat();
                ic.setId_insumo(rs.getInt("idcontrato_insumo"));
                //int id_ins = rs.getInt("id_insumo");
                ic.setCodInsumo(rs.getString("codInsumo"));
                ic.setCodConcepto(rs.getString("codConcepto"));
                ic.setDescripcion(rs.getString("descripcion"));
                ic.setUnidad(rs.getString("unidad"));
                ic.setCantConcepto(rs.getBigDecimal("cantidad"));
                //System.out.println("Cant concepto "+ic.getCantConcepto());
                ic.setCantIns(rs.getBigDecimal("cantidadIns"));
                ic.setCantInsCtrl(rs.getBigDecimal("cantInsctrl"));
                ic.setCantInsTotal(ic.getCantConcepto().multiply(ic.getCantIns()));
                BigDecimal ctrl = explotarInsumo(ic.getCodInsumo(), id_proy, id_pres).subtract(sumarCodInsumo(ic.getCodInsumo(), id_proy, id_pres));
                //System.out.println("Cant Ins Ctrl "+ctrl);
                ic.setCantCtrl(ctrl);
                /*if(sumarCodInsumo(ic.getCodInsumo()).subtract(ic.getCantInsTotal()).floatValue() == 0.0) {
                    ic.setCantCtrl(BigDecimal.ZERO);
                }else {
                    ic.setCantCtrl(sumarCodInsumo(ic.getCodInsumo()).subtract(ic.getCantInsTotal()));
                }*/
                /*if((ic.getCantInsTotal().subtract(sumarCantidadesContratos(id_ins))).floatValue() == 0.0) {
                    ic.setCantCtrl(BigDecimal.ZERO);
                }else {
                    ic.setCantCtrl(ic.getCantInsTotal().subtract(sumarCantidadesContratos(id_ins)));
                }*/
                //ic.setCantCtrl(ic.getCantInsTotal().subtract(sumarCantidadesContratos(id_ins)));
                ic.setCantContrato(rs.getBigDecimal("cantContrato"));
                ic.setPresUnit(rs.getBigDecimal("precioUnitContrat"));
                ic.setImporteCont(rs.getBigDecimal("importeContrat"));
                //System.out.println("Cant Control "+ic.getCantCtrl());
                listInsum.add(ic);
            }
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return listInsum;
    }
    
    public BigDecimal explotarInsumo(String cod, int id_proy, int id_pres) {
        
        BigDecimal explosion = BigDecimal.ZERO;
        
        try {
            
            String sql = "select sum(ins.cantidadIns*cnp.cantidad) as canTotal from insumo ins, "
                    + "concepto cnp, partida pta, presupuesto pre, proyecto pro "
                    + "where ins.id_concepto = cnp.id_concepto and cnp.id_partida = pta.id_partida "
                    + "and pta.id_presupuesto = pre.id_presupuesto and pre.id_proyecto = pro.id_proyecto "
                    + "and ins.codInsumo = ? and pre.id_presupuesto = ? and pro.id_proyecto = ? group by ins.codInsumo";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cod);
            ps.setInt(2, id_pres);
            ps.setInt(3, id_proy);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                explosion = rs.getBigDecimal("canTotal");
            }
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return explosion;
        
    }
    
    public int registrarPrecontrato(List<InsumoContrat> lista, String tipoContto, BigDecimal suma, int id_proy, int id_pres) {
        
        int registro = 0;
        
        try {
            
            con.setAutoCommit(false);
            
            String q = "Select definicion FROM catsubcontratos WHERE clave='"+tipoContto+"'";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(q);
            rs.first();
            String contto = rs.getString("definicion");
            
            String q2 = "Select MAX(A.numContrato) AS numero From contrato AS A "
                        + "INNER JOIN contrato_insumo AS B ON B.id_contrato = A.id_contrato "
                        + "INNER JOIN insumo AS C ON C.id_insumo = B.id_insumo "
                        + "INNER JOIN concepto AS D ON D.id_concepto = C.id_concepto "
                        + "INNER JOIN partida AS E ON E.id_partida = D.id_partida "
                        + "INNER JOIN presupuesto AS F ON F.id_presupuesto = E.id_presupuesto "
                        + "INNER JOIN proyecto AS G ON G.id_proyecto="+id_proy+ " AND F.id_presupuesto="+id_pres
                        + " FOR UPDATE";
            
            rs = s.executeQuery(q2);
            rs.first();
            int num = rs.getInt("numero") + 1;
            
            String sql = "Insert into contrato(tipo, importeContrato, numContrato)VALUES ('"+contto+"',"+suma+","+num+")";
            //System.out.println(sql);
            s.executeUpdate(sql);
            
            String sql2= "Select last_insert_id()";
            rs = s.executeQuery(sql2);
            rs.first();
            int id = rs.getInt(1);
            
            String sql3 = "Insert into contrato_insumo(codInsumo, codConcepto, descripcion, unidad, cantContrato, "
                    + "precioUnitContrat, importeContrat, id_contrato, id_insumo)VALUES (?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement ps = con.prepareStatement(sql3);
            
            for(InsumoContrat aux:lista) {
                ps.setString(1, aux.getCodInsumo());
                ps.setString(2, aux.getCodInsumo());
                ps.setString(3, aux.getDescripcion());
                ps.setString(4, aux.getUnidad());
                ps.setBigDecimal(5, aux.getCantContrato());
                ps.setBigDecimal(6, aux.getPresUnit());
                ps.setBigDecimal(7, aux.getImporteCont());
                ps.setInt(8, id);
                ps.setInt(9, aux.getId_insumo());
                ps.executeUpdate();
            }
            
            String sql4 = "SELECT numContrato FROM contrato where id_contrato="+id;
            rs = s.executeQuery(sql4);
            rs.first();
            registro = rs.getInt("numContrato");
            
            con.commit();
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return registro;
    }
    
    public List<InsumoContrat> listarConceptos(int id_proy, int id_pres) {
        
        List<InsumoContrat> listaSubc = new ArrayList<>();
        InsumoContrat insCont;
        Cadenas c =  new Cadenas();
        
        try {
            
            String sql = "SELECT A.id_insumo, A.codInsumo, A.costoInsCtrl, B.codConcepto, B.descripcion, B.unidad, B.cantidad, " +
                        "A.cantidadIns, A.id_concepto FROM insumo AS A " +
                        "INNER JOIN concepto AS B ON B.id_concepto = A.id_concepto " +
                        "AND A.cuenta = 'SUBCONTRATOS' " +
                        "INNER JOIN partida AS C ON C.id_partida = B.id_partida " +
                        "INNER JOIN presupuesto AS D ON D.id_presupuesto = C.id_presupuesto " +
                        "INNER JOIN proyecto AS E ON E.id_proyecto ="+id_proy+ " AND D.id_presupuesto ="+id_pres+
                        " ORDER BY A.codInsumo";
            
            Statement s = con.createStatement();
            ResultSet rs =  s.executeQuery(sql);
            
            while(rs.next()) {
                insCont = new InsumoContrat();
                insCont.setId_insumo(rs.getInt("id_insumo"));
                int id_ins = insCont.getId_insumo();
                insCont.setCodInsumo(rs.getString("codInsumo"));
                insCont.setCodConcepto(rs.getString("codConcepto"));
                insCont.setDescripcion(c.resumirDescripcion(rs.getString("descripcion")));
                insCont.setUnidad(rs.getString("unidad"));
                insCont.setPresUnit(rs.getBigDecimal("costoInsCtrl"));
                insCont.setCantConcepto(rs.getBigDecimal("cantidad"));
                insCont.setCantInsCtrl(rs.getBigDecimal("cantidadIns"));
                insCont.setId_concepto(rs.getInt("id_concepto"));
                insCont.setCantInsTotal(insCont.getCantConcepto().multiply(insCont.getCantInsCtrl()));
                if((insCont.getCantInsTotal().subtract(sumarCantidadesContratos(id_ins))).floatValue() == 0.0) {
                    insCont.setCantCtrl(BigDecimal.ZERO);
                }else {
                    insCont.setCantCtrl(insCont.getCantInsTotal().subtract(sumarCantidadesContratos(id_ins)));
                }
                
                listaSubc.add(insCont);
            }
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return listaSubc;
    }
    
    public List<InsumoContrat> listarSbConttos(int id_proy, int id_pres) {
        
        List<InsumoContrat> listaSubc = new ArrayList<>();
        InsumoContrat insCont;
        Cadenas c =  new Cadenas();
        
        try {
            
            String sql = "SELECT ins.id_insumo, ins.codInsumo, ins.descripcion, ins.unidadIns, ins.costoIns, "
                    + "sum(ins.cantidadIns*cnp.cantidad) AS canTotal, ins.cuenta,ins.costoInsCtrl, pre.id_presupuesto "
                    + "FROM insumo ins, concepto cnp, partida pta, presupuesto pre, proyecto pro "
                    + "WHERE ins.id_concepto = cnp.id_concepto AND cnp.id_partida = pta.id_partida "
                    + "AND pta.id_presupuesto = pre.id_presupuesto AND pre.id_proyecto = pro.id_proyecto AND pro.id_proyecto="+id_proy
                    + " AND pre.id_presupuesto="+id_pres+" group by ins.codInsumo";
            
            Statement s = con.createStatement();
            ResultSet rs =  s.executeQuery(sql);
            
            while(rs.next()) {
                insCont = new InsumoContrat();
                insCont.setId_insumo(rs.getInt("id_insumo"));
                int id_ins = insCont.getId_insumo();
                insCont.setCodInsumo(rs.getString("codInsumo"));
                
                insCont.setDescripcion(c.resumirDescripcion(rs.getString("descripcion")));
                insCont.setUnidad(rs.getString("unidadIns"));
                insCont.setPresUnit(rs.getBigDecimal("costoIns"));
                insCont.setCantInsTotal(rs.getBigDecimal("canTotal"));
                if((insCont.getCantInsTotal().subtract(sumarCodInsumo(insCont.getCodInsumo(), id_proy, id_pres))).floatValue() == 0.0) {
                    insCont.setCantCtrl(BigDecimal.ZERO);
                }else {
                    insCont.setCantCtrl(insCont.getCantInsTotal().subtract(sumarCodInsumo(insCont.getCodInsumo(), id_proy, id_pres)));
                }
                
                listaSubc.add(insCont);
            }
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return listaSubc;
    }
    
    public BigDecimal sumarCantidadesContratos(int id_ins) {
        
        BigDecimal sumaCant = new BigDecimal(0);
        
        try {
            
            String sql = "Select SUM(cantContrato) AS sumaCantidad From contrato_insumo WHERE id_insumo="+id_ins+ 
                    " AND estatusIns != 'CANCELADO' ";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            rs.first();
            sumaCant = rs.getBigDecimal("sumaCantidad");
            if(sumaCant == null) {
                sumaCant = BigDecimal.ZERO;
            }
            
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return sumaCant;
    }
    
    public BigDecimal sumarCodInsumo(String cod, int id_proy, int id_pres) {
        
        BigDecimal sumaCant = new BigDecimal(0);
        
        try {
            
            String sql = "select sum(cantContrato) as sumaCantidad from contrato_insumo as ci " 
                    + "inner join insumo as ins on ins.id_insumo = ci.id_insumo " 
                    + "inner join concepto as con on con.id_concepto = ins.id_concepto " 
                    + "inner join partida as p on p.id_partida = con.id_partida " 
                    + "inner join presupuesto as pre on pre.id_presupuesto = p.id_presupuesto " 
                    + "inner join proyecto as pro on pro.id_proyecto = pre.id_presupuesto "
                    + "and pro.id_proyecto = ? and pre.id_presupuesto = ? and ins.codInsumo = ?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id_proy);
            ps.setInt(2, id_pres);
            ps.setString(3, cod);
            ResultSet rs = ps.executeQuery();
            
            rs.first();
            sumaCant = rs.getBigDecimal("sumaCantidad");
            if(sumaCant == null) {
                sumaCant = BigDecimal.ZERO;
            }
            
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return sumaCant;
        
    }
            
    
    public List<CtaSubcontratoBean> listarSubcontratos() {
        
        List<CtaSubcontratoBean> listaSubcs = new ArrayList<>();
        CtaSubcontratoBean subc;
        
        try {
            
            String sql = "Select * From catsubcontratos";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                subc = new CtaSubcontratoBean();
                subc.setId_subcon(rs.getInt("id_subcon"));
                subc.setClave(rs.getString("clave"));
                subc.setDefinicion(rs.getString("definicion"));
                listaSubcs.add(subc);
            }
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return listaSubcs;
    }
    
    public ArrayList<PresupuestoBean> listarPresupuesto(int id_proyecto) {
        
        PresupuestoBean pb;
        ArrayList<PresupuestoBean> listaPres = new ArrayList<>();
        
        try {
            
            String sql = "Select id_presupuesto, presupuesto from presupuesto WHERE id_proyecto="+id_proyecto;
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                pb = new PresupuestoBean();
                pb.setId_presupuesto(rs.getInt("id_presupuesto"));
                pb.setPresupuesto(rs.getString("presupuesto"));
                listaPres.add(pb);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return listaPres;
    }
    
    public List<ProyectoSimple> listarProyecto() {
        
        List<ProyectoSimple> listProy = new ArrayList<>();
        ProyectoSimple ps;
        
        try {
            
            String sql = "Select id_proyecto, proyecto From proyecto";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                ps = new ProyectoSimple();
                ps.setId_proyecto(rs.getInt("id_proyecto"));
                ps.setProyecto(rs.getString("proyecto"));
                listProy.add(ps);
            }
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
        return listProy;
    }
    
}
