
package com.dao;

import com.bean.Apoderado;
import com.bean.Banco;
import com.bean.ClienteAltaBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ClienteDAO {
    
    Connection con;
    
    public ClienteDAO(Connection con) {
        this.con = con;
    }
    
    public void eliminarBanco(int id) {
        
        //System.out.println("Eliminar...");
        
        try {
            
            String sql = "delete from banco where id_banco=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public void agregarBanco(Banco b) {
        
        try {
            
            String sql = "insert into banco(banco, plaza, cuenta, clabe, metodo, nomBenef, aPBenef, aMBenef, fNacBenef, rfcBenef, "
                    + "curpBenef, id_cliente) "
                    + "values(?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, b.getNomBenef());
            ps.setString(2, b.getPlaza());
            ps.setString(3, b.getCta());
            ps.setString(4, b.getClabe());
            ps.setString(5, b.getMetodo());
            ps.setString(6, b.getNomBenef());
            ps.setString(7, b.getApePatBenef());
            ps.setString(8, b.getApeMatBenef());
            ps.setString(9, b.getFechNacBenef());
            ps.setString(10, b.getRfcBenef());
            ps.setString(11, b.getCurpBenef());
            ps.setInt(12, b.getId_cliente());
            ps.executeUpdate();
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public void agregApod(Apoderado ap) {
        
        try {
            
            //System.out.println("agregando");
            
            String sql = "insert into apoderado(nombApod, patApod, matApod, rfcApod, curpApod, fechNacApod, id_cliente)"
                    + "values (?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ap.getNombApod());
            ps.setString(2, ap.getPatApod());
            ps.setString(3, ap.getMatApod());
            ps.setString(4, ap.getRfcApod());
            ps.setString(5, ap.getCurpApod());
            ps.setString(6, ap.getFechaNacApod());
            ps.setInt(7, ap.getId_cliente());
            ps.executeUpdate();
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("falló...");
        }
        
    }
    
    public void eliminarApoderado(int id) {
        
        
        try {
            
            String sql = "delete from apoderado where id_apoderado = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
    }
    
    public int editarCliente(ClienteAltaBean cl, List<Apoderado> listApod, List<Banco> listBco, List<Integer> listCancel, List<Integer> listApodCancel ) {
        
        int reg;
        
        try {
            
            con.setAutoCommit(false);
            
            String sql = "update cliente set razonSocial=?, fechaConst=?, nacionalidad=?, giro=?, rfcCliente=?, calleFisc=?, extFisc=?, "
                    + "intFisc=?, colFisc=?, delFisc=?, cpFisc=?, entFedFisc=?, telFisc=?, extTelFisc=?, emailFisc=?, "
                    + "calleSoc=?, extSoc=?, intSoc=?, colSoc=?, delSoc=?, cpSoc=?, entFedSoc=?, telSoc=?, extTelSoc=?, "
                    + "emailSoc=?, testim=?, cedula=?, compDom=?, testPodLegal=?, idApod=?, fm23=?, constBenef=?, modif=?, "
                    + "modificaciones=? where id_cliente="+cl.getId_cliente();
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cl.getRazon());
            ps.setString(2, cl.getFechConst());
            ps.setString(3, cl.getNacionalidad());
            ps.setString(4, cl.getGiro());
            ps.setString(5, cl.getRfcCliente());
            ps.setString(6, cl.getCalleFisc());
            ps.setString(7, cl.getExtFisc());
            ps.setString(8, cl.getIntFisc());
            ps.setString(9, cl.getColFisc());
            ps.setString(10, cl.getDelFisc());
            ps.setString(11, cl.getCpFisc());
            ps.setString(12, cl.getEntFedFisc());
            if(!cl.getLadaFisc().equals("")) {
                ps.setString(13, cl.getLadaFisc()+"-"+cl.getTelFisc());
                //System.out.println(cl.getLadaFisc());
            }else {
                ps.setString(13, "");
            }
            
            ps.setString(14, cl.getExFisc());
            ps.setString(15, cl.getEmailFisc());
            ps.setString(16, cl.getCalleSoc());
            ps.setString(17, cl.getExtSoc());
            ps.setString(18, cl.getIntSoc());
            ps.setString(19, cl.getColSoc());
            ps.setString(20, cl.getDelSoc());
            ps.setString(21, cl.getCpSoc());
            ps.setString(22, cl.getEntFedSoc());
            ps.setString(23, cl.getTelSoc());
            if(!cl.getLadaSoc().equals("")) {
                ps.setString(24, cl.getLadaSoc()+"-"+cl.getExSoc());
            }else {
                ps.setString(24, "");
            }
            
            ps.setString(25, cl.getEmailSoc());
            ps.setBoolean(26, cl.isTestim());
            ps.setBoolean(27, cl.isCedula());
            ps.setBoolean(28, cl.isCompDom());
            ps.setBoolean(29, cl.isTestPodLegal());
            ps.setBoolean(30, cl.isIdApod());
            ps.setBoolean(31, cl.isFm23());
            ps.setBoolean(32, cl.isConstBenef());
            ps.setBoolean(33, cl.isModif());
            ps.setString(34, cl.getEspModif());
            /*ps.setString(34, cl.getNomBenef());
            ps.setString(35, cl.getaPBenef());
            ps.setString(36, cl.getaMBenef());
            ps.setString(37, cl.getdNacBenef()+"/"+cl.getmNacBenef()+"/"+cl.getyNacBenef());
            ps.setString(38, cl.getRfcBenef());
            ps.setString(39, cl.getCurpBenef());*/
            ps.executeUpdate();
            
            /*String sql2 = "update banco set banco=?, plaza=?, cuenta=?, clabe=?, metodo=?, nomBenef=?, aPBenef=?, aMBenef=?, "
                    + "fNacBenef=?, rfcBenef=?, curpBenef=? where id_cliente="+cl.getId_cliente();
            ps = con.prepareStatement(sql2);
            
            for(Banco aux:listBco) {
                ps.setString(1, aux.getBanco());
                ps.setString(2, aux.getPlaza());
                ps.setString(3, aux.getCta());
                ps.setString(4, aux.getClabe());
                ps.setString(5, aux.getMetodo());
                ps.setString(6, aux.getNomBenef());
                ps.setString(7, aux.getApePatBenef());
                ps.setString(8, aux.getApeMatBenef());
                if(!aux.getDiaNacBenef().equals("")) {
                    ps.setString(9, aux.getDiaNacBenef()+"/"+aux.getMesNacBenef()+"/"+aux.getYearNacBenef());
                }else {
                    ps.setString(9, "");
                }
                
                ps.setString(10, aux.getRfcBenef());
                ps.setString(11, aux.getCurpBenef());
                ps.executeUpdate();
            }
            
            String sql4 = "update apoderado set nombApod=?, patApod=?, matAPod=?, rfcApod=?, curpApod=?, fechNacApod=? "
                    + "where id_cliente="+cl.getId_cliente();
            ps = con.prepareStatement(sql4);
            
            for(Apoderado aux: listApod) {
                ps.setString(1, aux.getNombApod());
                ps.setString(2, aux.getPatApod());
                ps.setString(3, aux.getMatApod());
                ps.setString(4, aux.getRfcApod());
                ps.setString(5, aux.getCurpApod());
                if(!aux.getdNacAPod().equals("")) {
                    ps.setString(6, aux.getdNacAPod()+"/"+aux.getmNacApod()+"/"+aux.getyNacApod());
                }else {
                    ps.setString(6, "");
                }
                
                ps.executeUpdate();
            }
            
            if(listCancel != null) {
                String sql5 = "delete from banco where id_banco=?";
                ps = con.prepareStatement(sql5);

                for(int aux: listCancel) {
                    ps.setInt(1, aux);
                    ps.executeUpdate();
                }
            }
            
            if(listApodCancel != null) {
                String sql6 = "delete from apoderado where id_apoderado=?";
                ps = con.prepareStatement(sql6);

                for(int aux: listApodCancel) {
                    ps.setInt(1, aux);
                    ps.executeUpdate();
                }
            }*/
            
            reg = 1;
            con.commit();
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            //e.printStackTrace();
            reg = -1;
            try {
                con.rollback();
            }catch(SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        return reg;
        
    }
    
    
    public List<Banco> listarBancos(int id) {
        
        List<Banco> list = new ArrayList<>();
        Banco bco;
        
        try {
            
            String sql = "select * from banco where id_cliente="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                bco = new Banco();
                bco.setBanco(rs.getString("banco"));
                bco.setPlaza(rs.getString("plaza"));
                bco.setCta(rs.getString("cuenta"));
                bco.setClabe(rs.getString("clabe"));
                bco.setMetodo(rs.getString("metodo"));
                bco.setNomBenef(rs.getString("nomBenef"));
                bco.setApePatBenef(rs.getString("aPBenef"));
                bco.setApeMatBenef(rs.getString("aMBenef"));
                String fecha = rs.getString("fNacBenef");
                bco.setDiaNacBenef(fecha.substring(0, 2));
                //System.out.println(bco.getDiaNacBenef());
                bco.setMesNacBenef(fecha.substring(3, 5));
                //System.out.println(bco.getMesNacBenef());
                bco.setYearNacBenef(fecha.substring(6, 10));
                //System.out.println(bco.getYearNacBenef());
                bco.setRfcBenef(rs.getString("rfcBenef"));
                bco.setCurpBenef(rs.getString("curpBenef"));
                bco.setId_banco(rs.getInt("id_banco"));
                bco.setId_cliente(rs.getInt("id_cliente"));
                list.add(bco);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            //e.printStackTrace();
        }
        
        return list;
    
    }
    
    public List<Apoderado> listarApoderados(int  id) {
        
        List<Apoderado> list = new ArrayList<>();
        Apoderado ap;
        
        try {
            
            String sql = "select * from apoderado where id_cliente="+id;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                ap = new Apoderado();
                
                ap.setNombApod(rs.getString("nombApod"));
                ap.setPatApod(rs.getString("patApod"));
                ap.setMatApod(rs.getString("matApod"));
                ap.setRfcApod(rs.getString("rfcApod"));
                ap.setCurpApod(rs.getString("curpApod"));
                ap.setFechaNacApod(rs.getString("fechNacApod"));
                String fecha = rs.getString("fechNacApod");
                if(!fecha.equals("")) {
                    ap.setdNacAPod(fecha.substring(0, 2));
                    ap.setmNacApod(fecha.substring(3, 5));
                    ap.setyNacApod(fecha.substring(6, 10));
                }else {
                    ap.setdNacAPod("00");
                    ap.setmNacApod("00");
                    ap.setyNacApod("0000");
                }
                    
                ap.setId_cliente(id);
                ap.setId_apoderado(rs.getInt("id_apoderado"));
                list.add(ap);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
        
    }
    
    public List<ClienteAltaBean> leerClientes() {
        
        List<ClienteAltaBean> list = new ArrayList<>();
        ClienteAltaBean cl;
        
        try {
            
            String sql = "select * from cliente";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                cl = new ClienteAltaBean();
                cl.setId_cliente(rs.getInt("id_cliente"));
                cl.setRazon(rs.getString("razonSocial"));
                cl.setFechConst(rs.getString("fechaConst"));
                cl.setNacionalidad(rs.getString("nacionalidad"));
                cl.setGiro(rs.getString("giro"));
                cl.setRfcCliente(rs.getString("rfcCliente"));
                cl.setCalleFisc(rs.getString("calleFisc"));
                cl.setExtFisc(rs.getString("extFisc"));
                cl.setIntFisc(rs.getString("intFisc"));
                cl.setColFisc(rs.getString("colFisc"));
                cl.setDelFisc(rs.getString("delFisc"));
                cl.setCpFisc(rs.getString("cpFisc"));
                cl.setEntFedFisc(rs.getString("entFedFisc"));
                String tel = rs.getString("telFisc");
                if(!tel.equals("")) {
                    //System.out.println(tel);
                    cl.setLadaFisc(tel.substring(0, 2));
                    cl.setTelFisc(tel.substring(3, 11));
                }else {
                    cl.setLadaFisc("");
                    cl.setTelFisc("");
                }
                
                
                cl.setExFisc(rs.getString("extTelFisc"));
                cl.setEmailFisc(rs.getString("emailFisc"));
                cl.setCalleSoc(rs.getString("calleSoc"));
                cl.setExtSoc(rs.getString("extSoc"));
                cl.setIntSoc(rs.getString("intSoc"));
                cl.setColSoc(rs.getString("colSoc"));
                cl.setDelSoc(rs.getString("delSoc"));
                cl.setCpSoc(rs.getString("cpSoc"));
                cl.setEntFedSoc(rs.getString("entFedSoc"));
                String telSoc = rs.getString("telSoc");
                //System.out.println(telSoc.length());
                //System.out.println(telSoc);
                if(!telSoc.equals("")) {
                    cl.setLadaSoc(telSoc.substring(0, 2));
                    cl.setTelSoc(telSoc.substring(3, 11));
                }else {
                    cl.setLadaSoc("");
                    cl.setTelSoc("");
                }
                
                cl.setEmailSoc(rs.getString("emailSoc"));
                cl.setTestim(rs.getBoolean("testim"));
                cl.setCedula(rs.getBoolean("cedula"));
                cl.setCompDom(rs.getBoolean("compDom"));
                cl.setTestPodLegal(rs.getBoolean("testPodLegal"));
                cl.setIdApod(rs.getBoolean("idApod"));
                cl.setFm23(rs.getBoolean("fm23"));
                cl.setConstBenef(rs.getBoolean("constBenef"));
                cl.setModif(rs.getBoolean("modif"));
                cl.setEspModif(rs.getString("modificaciones"));
                list.add(cl);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
        
    }
    
    public int insertCliente(ClienteAltaBean cl, List<Banco> listBnc, List<Apoderado> lista) {
        
        int reg;
        
        try {
            
            con.setAutoCommit(false);
            
            String sql = "insert into cliente(razonSocial, fechaConst, nacionalidad, giro, rfcCliente, calleFisc, extFisc, intFisc, "
                    + "colFisc, delFisc, cpFisc, entFedFisc, telFisc, extTelFisc, emailFisc, calleSoc, extSoc, intSoc, "
                    + "colSoc, delSoc, cpSoc, entFedSoc, telSoc, extTelSoc, emailSoc, testim, cedula, "
                    + "compDom, testPodLegal, idApod, fm23, constBenef, modif, modificaciones"
                    + ")values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                    + "?,?,?,?)";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cl.getRazon());
            ps.setString(2, cl.getFechConst());
            ps.setString(3, cl.getNacionalidad());
            ps.setString(4, cl.getGiro());
            ps.setString(5, cl.getRfcCliente());
            ps.setString(6, cl.getCalleFisc());
            ps.setString(7, cl.getExtFisc());
            ps.setString(8, cl.getIntFisc());
            ps.setString(9, cl.getColFisc());
            ps.setString(10, cl.getDelFisc());
            ps.setString(11, cl.getCpFisc());
            ps.setString(12, cl.getEntFedFisc());
            if(!cl.getLadaFisc().equals("")) {
                ps.setString(13, cl.getLadaFisc()+"-"+cl.getTelFisc());
            }else {
                ps.setString(13, "");
            }
            ps.setString(14, cl.getExFisc());
            ps.setString(15, cl.getEmailFisc());
            ps.setString(16, cl.getCalleSoc());
            ps.setString(17, cl.getExtSoc());
            ps.setString(18, cl.getIntSoc());
            ps.setString(19, cl.getColSoc());
            ps.setString(20, cl.getDelSoc());
            ps.setString(21, cl.getCpSoc());
            ps.setString(22, cl.getEntFedSoc());
            if(!cl.getLadaSoc().equals("")) {
                ps.setString(23, cl.getLadaSoc()+"-"+cl.getTelSoc());
            }else {
                ps.setString(23, "");
            }
            
            ps.setString(24, cl.getExSoc());
            ps.setString(25, cl.getEmailSoc());
            ps.setBoolean(26, cl.isTestim());
            ps.setBoolean(27, cl.isCedula());
            ps.setBoolean(28, cl.isCompDom());
            ps.setBoolean(29, cl.isTestPodLegal());
            ps.setBoolean(30, cl.isIdApod());
            ps.setBoolean(31, cl.isFm23());
            ps.setBoolean(32, cl.isConstBenef());
            ps.setBoolean(33, cl.isModif());
            ps.setString(34, cl.getEspModif());
            /*ps.setString(34, cl.getNomBenef());
            ps.setString(35, cl.getaPBenef());
            ps.setString(36, cl.getaMBenef());
            ps.setString(37, cl.getdNacBenef()+"/"+cl.getmNacBenef()+"/"+cl.getyNacBenef());
            ps.setString(38, cl.getRfcBenef());
            ps.setString(39, cl.getCurpBenef());*/
            ps.executeUpdate();
            
            String sql2 = "select last_insert_id()";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql2);
            rs.first();
            int id = rs.getInt(1);
            
            String sql3 = "insert into banco(banco, plaza, cuenta, clabe, metodo, nomBenef, aPBenef, aMBenef, fNacBenef, "
                    + "rfcBenef, curpBenef, id_cliente)values (?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sql3);
            for(Banco aux:listBnc) {
                ps.setString(1, aux.getBanco());
                ps.setString(2, aux.getPlaza());
                ps.setString(3, aux.getCta());
                ps.setString(4, aux.getClabe());
                ps.setString(5, aux.getMetodo());
                ps.setString(6, aux.getNomBenef());
                ps.setString(7, aux.getApePatBenef());
                ps.setString(8, aux.getApeMatBenef());
                ps.setString(9, aux.getDiaNacBenef()+"/"+aux.getMesNacBenef()+"/"+aux.getYearNacBenef());
                ps.setString(10, aux.getRfcBenef());
                ps.setString(11, aux.getCurpBenef());
                ps.setInt(12, id);
                ps.executeUpdate();
            }
            
            
            String sql4 = "insert into apoderado(nombApod, patApod, matApod, rfcApod, curpApod, fechNacApod, id_cliente)"
                    + "values (?,?,?,?,?,?,?)";
            
            ps = con.prepareStatement(sql4);
            
            for(Apoderado aux: lista) {
                ps.setString(1, aux.getNombApod());
                ps.setString(2, aux.getPatApod());
                ps.setString(3, aux.getMatApod());
                ps.setString(4, aux.getRfcApod());
                ps.setString(5, aux.getCurpApod());
                if(!aux.getdNacAPod().equals("")) {
                    ps.setString(6, aux.getdNacAPod()+"/"+aux.getmNacApod()+"/"+aux.getyNacApod());
                }else {
                    ps.setString(6, "");
                }
                
                ps.setInt(7, id);
                ps.executeUpdate();
            }
            
            reg = 1;
            
            con.commit();
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            //e.printStackTrace();
            reg = -1;
            try {
                con.rollback();
            }catch(SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        return reg;
        
    }
    
}
