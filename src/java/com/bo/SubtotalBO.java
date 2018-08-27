/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo;

import com.bean.InsumoExplosion;
import com.bean.SubtotalBean;
import com.conexion.ConexionBD;
import com.dao.PresupuestoDAO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class SubtotalBO {
    
    public List<SubtotalBean> listarMateriales(int id_proy, int id_pres) {
        List<InsumoExplosion> expIns = new ArrayList();
        //InsumoExplosion ins;
        
        List<SubtotalBean> listSubt = new ArrayList();
        //SubtotalBean subtB;
        //System.out.println(id_proy + " "+id_pres);
        ConexionBD conexion = new ConexionBD();
        Connection con = conexion.getConexion();
        PresupuestoDAO pDao = new PresupuestoDAO(con);
        expIns = pDao.explotarInsumos(id_proy, id_pres);
        //System.out.println(expIns.size());
        listSubt = obtenerSubtotales(expIns);
        //obtenerSubtotales(expIns);
        
        return listSubt;
    }    
    
    public List<SubtotalBean> obtenerSubtotales(List<InsumoExplosion> lista) {
        
        List<SubtotalBean> listSubt = new ArrayList<SubtotalBean>();
        SubtotalBean subtB;
        BigDecimal sumaMateriales = BigDecimal.ZERO;
        //System.out.println(lista.size());
        //Sumar Materiales
        for(InsumoExplosion aux: lista) {
            //System.out.println("Sumar Material");
            if(aux.getCodInsumo() != null) {
                //System.out.println(aux.getCodInsumo().substring(0, 3));
                if(!(aux.getCodInsumo().substring(0, 3)).equals("3HM") && !(aux.getCodInsumo().substring(0, 3)).equals("MAQ") && !(aux.getCodInsumo().substring(0, 3)).equals("EQU")) {
                    sumaMateriales = sumaMateriales.add(aux.getSumaCant());
                
                }
            }
            
            
            //System.out.println(aux.getCodInsumo().substring(0, 2));
            
        }
        
        for(InsumoExplosion aux: lista) {
            if(aux.getCodInsumo() == null) {
                sumaMateriales = sumaMateriales.add(aux.getSumaCant());
            }
        }
        
        
        //System.out.println(sumaMateriales);
        
        subtB =  new SubtotalBean();
        subtB.setTipo("MATERIALES");
        subtB.setSubtotal(sumaMateriales);
        listSubt.add(subtB);
        
        for(InsumoExplosion aux: lista) {
            
            if(aux.getCodInsumo() != null) {
                if((aux.getCodInsumo().substring(0, 3)).equals("3HM")) {
                    subtB = new SubtotalBean();
                    subtB.setTipo("HERRAMIENTA MENOR");
                    subtB.setSubtotal(aux.getSumaCant());
                    listSubt.add(subtB);
                }
            }
        }
        
        //Sumar equipo
        BigDecimal sumaEquipo = BigDecimal.ZERO;
        for(InsumoExplosion aux: lista) {
            if(aux.getCodInsumo() != null) {
                if((aux.getCodInsumo().substring(0, 3)).equals("EQU")) {
                    sumaEquipo = sumaEquipo.add(aux.getSumaCant());
                }
            }
            
        }
        
        subtB =  new SubtotalBean();
        subtB.setTipo("EQUIPO");
        subtB.setSubtotal(sumaEquipo);
        listSubt.add(subtB);
        
        //Sumar maquinaria
        BigDecimal sumaMaquina = BigDecimal.ZERO;
        for(InsumoExplosion aux: lista) {
            if(aux.getCodInsumo() != null) {
                if((aux.getCodInsumo().substring(0, 3)).equals("MAQ")) {
                    sumaMaquina = sumaMaquina.add(aux.getSumaCant());
                }
            }
        }
        
        subtB =  new SubtotalBean();
        subtB.setTipo("MAQUINARIA");
        subtB.setSubtotal(sumaMaquina);
        listSubt.add(subtB);
        
        return listSubt;
        
    }
    
    public List<SubtotalBean> listarSubcontratos(int id_proy, int id_pres) {
        List<InsumoExplosion> expIns = new ArrayList<InsumoExplosion>();
        //InsumoExplosion ins;
        
        List<SubtotalBean> listSubt = new ArrayList<SubtotalBean>();
        //SubtotalBean subtB;
        //System.out.println(id_proy + " "+id_pres);
        ConexionBD conexion = new ConexionBD();
        Connection con = conexion.getConexion();
        PresupuestoDAO pDao = new PresupuestoDAO(con);
        expIns = pDao.explotarSubcontratos(id_proy, id_pres);
        //System.out.println(expIns.size());
        listSubt = subtotalSubcontratos(expIns);
        //obtenerSubtotales(expIns);
        
        return listSubt;
    }
    
    public List<SubtotalBean> subtotalSubcontratos(List<InsumoExplosion> lista) {
        
        List<SubtotalBean> listSubt = new ArrayList<SubtotalBean>();
        SubtotalBean subt;
        
        //Sumar andamios
        BigDecimal sumaAndamios = BigDecimal.ZERO;
        for(InsumoExplosion aux: lista) {
            if(aux.getCodInsumo() != null && (aux.getCodInsumo().substring(0, 3)).equals("7AN")) {
                sumaAndamios = sumaAndamios.add(aux.getSumaCant());
            }
        }
        
        subt = new SubtotalBean();
        subt.setTipo("ANDAMIOS");
        subt.setSubtotal(sumaAndamios);
        listSubt.add(subt);
        
        //Sumar fletes y acarreos
        BigDecimal sumaFletes = BigDecimal.ZERO;
        for(InsumoExplosion aux: lista) {
            if(aux.getCodInsumo() != null && (aux.getCodInsumo().substring(0, 3)).equals("7FA")) {
                sumaFletes = sumaFletes.add(aux.getSumaCant());
            }
        }
        
        subt = new SubtotalBean();
        subt.setTipo("FLETES Y ACARREOS");
        subt.setSubtotal(sumaFletes);
        listSubt.add(subt);
        
        //Suma de mano de obra
        BigDecimal sumaMano = BigDecimal.ZERO;
        for(InsumoExplosion aux: lista) {
            if(aux.getCodInsumo() != null && (aux.getCodInsumo().substring(0, 3)).equals("7MO")) {
                sumaMano = sumaMano.add(aux.getSumaCant());
            }
        }
        
        subt = new SubtotalBean();
        subt.setTipo("MANO DE OBRA");
        subt.setSubtotal(sumaMano);
        listSubt.add(subt);
        
        //Suma de Renta de equipo
        BigDecimal sumaRenta = BigDecimal.ZERO;
        for(InsumoExplosion aux: lista) {
            if(aux.getCodInsumo() != null && (aux.getCodInsumo().substring(0, 3)).equals("7RT")) {
                sumaRenta = sumaRenta.add(aux.getSumaCant());
            }
        }
        
        subt = new SubtotalBean();
        subt.setTipo("RENTA DE EQUIPO");
        subt.setSubtotal(sumaRenta);
        listSubt.add(subt);
        
        //Suma de subcontrato completo
        BigDecimal sumaSubc = BigDecimal.ZERO;
        for(InsumoExplosion aux: lista) {
            if(aux.getCodInsumo() != null && (aux.getCodInsumo().substring(0, 3)).equals("7SC")) {
                sumaSubc = sumaSubc.add(aux.getSumaCant());
            }
        }
        
        subt = new SubtotalBean();
        subt.setTipo("SUBCONTRATO COMPLETO");
        subt.setSubtotal(sumaSubc);
        listSubt.add(subt);
        
        //Suma de subcontrato serbvicios
        BigDecimal sumaServ = BigDecimal.ZERO;
        for(InsumoExplosion aux: lista) {
            if(aux.getCodInsumo() != null && (aux.getCodInsumo().substring(0, 3)).equals("7SE")) {
                sumaServ = sumaServ.add(aux.getSumaCant());
            }
        }
        
        subt = new SubtotalBean();
        subt.setTipo("SERVICIOS");
        subt.setSubtotal(sumaServ);
        listSubt.add(subt);
        
        //Suma de subcontrato seguro social
        BigDecimal sumaSeguro = BigDecimal.ZERO;
        for(InsumoExplosion aux: lista) {
            if(aux.getCodInsumo() != null && (aux.getCodInsumo().substring(0, 3)).equals("7SS")) {
                sumaSeguro = sumaSeguro.add(aux.getSumaCant());
            }
        }
        
        subt = new SubtotalBean();
        subt.setTipo("SEGURO SOCIAL");
        subt.setSubtotal(sumaSeguro);
        listSubt.add(subt);
        
        //Suma de subcontrato indirectos
        BigDecimal sumaIndirecto = BigDecimal.ZERO;
        for(InsumoExplosion aux: lista) {
            if(aux.getCodInsumo() != null && (aux.getCodInsumo().substring(0, 3)).equals("7ND")) {
                sumaIndirecto = sumaIndirecto.add(aux.getSumaCant());
            }
        }
        
        subt = new SubtotalBean();
        subt.setTipo("INDIRECTO");
        subt.setSubtotal(sumaIndirecto);
        listSubt.add(subt);
        
        //Suma de subcontrato caja chica
        BigDecimal sumaCaja = BigDecimal.ZERO;
        for(InsumoExplosion aux: lista) {
            if(aux.getCodInsumo() != null && (aux.getCodInsumo().substring(0, 3)).equals("7CH")) {
                sumaCaja = sumaCaja.add(aux.getSumaCant());
            }
        }
        
        subt = new SubtotalBean();
        subt.setTipo("CAJA CHICA");
        subt.setSubtotal(sumaCaja);
        listSubt.add(subt);
        
        return listSubt;
        
    }
    
    public List<InsumoExplosion> buscarInsumosMateriales(String tipo, int id_proy, int id_pres) {
        
        
        List<InsumoExplosion> expIns = new ArrayList<InsumoExplosion>();
        List<InsumoExplosion> filtro = new ArrayList<InsumoExplosion>();

                
        if(tipo.equals("MATERIALES")) {
            
            ConexionBD conexion = new ConexionBD();
            Connection con = conexion.getConexion();
            PresupuestoDAO pDao = new PresupuestoDAO(con);
            expIns = pDao.explotarInsumos(id_proy, id_pres);
            filtro = listarMateriales(expIns, 1);
        }
        if(tipo.equals("HERRAMIENTA MENOR")) {
            
            ConexionBD conexion = new ConexionBD();
            Connection con = conexion.getConexion();
            PresupuestoDAO pDao = new PresupuestoDAO(con);
            expIns = pDao.explotarInsumos(id_proy, id_pres);
            filtro = listarMateriales(expIns, 2);
        }
        if(tipo.equals("EQUIPO")) {
            
            ConexionBD conexion = new ConexionBD();
            Connection con = conexion.getConexion();
            PresupuestoDAO pDao = new PresupuestoDAO(con);
            expIns = pDao.explotarInsumos(id_proy, id_pres);
            filtro = listarMateriales(expIns, 3);
        }
        if(tipo.equals("MAQUINARIA")) {
            
            ConexionBD conexion = new ConexionBD();
            Connection con = conexion.getConexion();
            PresupuestoDAO pDao = new PresupuestoDAO(con);
            expIns = pDao.explotarInsumos(id_proy, id_pres);
            filtro = listarMateriales(expIns, 4);
        }
        
        return filtro;
    }
    
    public List<InsumoExplosion> listarMateriales(List<InsumoExplosion> expIns, int op) {
        
        List<InsumoExplosion> listaMat = new ArrayList<InsumoExplosion>();
        
        switch(op) {
            case 1://MATERIALES
                for(InsumoExplosion aux: expIns) {
                    
                    if(aux.getCodInsumo() == null) {
                        listaMat.add(aux);
                    }else {
                        if(!(aux.getCodInsumo().substring(0, 3)).equals("3HM") && !(aux.getCodInsumo().substring(0, 3)).equals("MAQ") 
                            && !(aux.getCodInsumo().substring(0, 3)).equals("EQU")) {
                            listaMat.add(aux);
                        }
                    }
                    
                }
            break;
                
            case 2:
                for(InsumoExplosion aux: expIns) {
                    if(aux.getCodInsumo() != null) {
                        if((aux.getCodInsumo().substring(0, 3).equals("3HM"))) {

                            listaMat.add(aux);

                        }
                    }
                }
            break;
                
            case 3:
                for(InsumoExplosion aux: expIns) {
                    if(aux.getCodInsumo() != null) {
                        if((aux.getCodInsumo().substring(0, 3).equals("MAQ"))) {

                            listaMat.add(aux);

                        }
                    }
                }
            break;
                
            case 4:
                for(InsumoExplosion aux: expIns) {
                    if(aux.getCodInsumo() != null) {
                        if((aux.getCodInsumo().substring(0, 3).equals("EQU"))) {

                            listaMat.add(aux);

                        }
                    }
                }
            break;
        }
        
        
        return listaMat;
    }
    
    public List<InsumoExplosion> listarInsumoSubcontratos(String tipo, int id_proy, int id_pres) {
        
        List<InsumoExplosion> expIns =  new ArrayList<InsumoExplosion>();
        List<InsumoExplosion> filtro =  new ArrayList<InsumoExplosion>();
        int op = 0;
        
        if(tipo.equals("ANDAMIOS"))
            op=1;
        if(tipo.equals("FLETES Y ACARREOS"))
            op=2;
        if(tipo.equals("MANO DE OBRA"))
            op=3;
        if(tipo.equals("RENTA DE EQUIPO"))
            op=4;
        if(tipo.equals("SUBCONTRATO COMPLETO"))
            op=5;
        if(tipo.equals("SERVICIOS"))
            op=6;
        if(tipo.equals("SEGURO SOCIAL"))
            op=7;
        if(tipo.equals("INDIRECTO"))
            op=8;
        if(tipo.equals("CAJA CHICA"))
            op=9;
        
        ConexionBD conexion = new ConexionBD();
        Connection con = conexion.getConexion();
        PresupuestoDAO pDao = new PresupuestoDAO(con);
        expIns = pDao.explotarSubcontratos(id_proy, id_pres);
        filtro = filtrarSucontratos(expIns, op);
        
        return filtro;
    }
    
    public List<InsumoExplosion> filtrarSucontratos(List<InsumoExplosion> expIns, int op) {
        
        List<InsumoExplosion> filtro = new ArrayList<InsumoExplosion>();
        
        switch(op) {
            
            case 1:
               for(InsumoExplosion aux: expIns) {
                    if(aux.getCodInsumo() != null && (aux.getCodInsumo().substring(0, 3)).equals("7AN")) {
                        filtro.add(aux);
                    }
               } 
            break;
                
            case 2:
               for(InsumoExplosion aux: expIns) {
                    if(aux.getCodInsumo() != null && (aux.getCodInsumo().substring(0, 3)).equals("7FA")) {
                        filtro.add(aux);
                    }
               } 
            break;
                
            case 3:
               for(InsumoExplosion aux: expIns) {
                    if(aux.getCodInsumo() != null && (aux.getCodInsumo().substring(0, 3)).equals("7MO")) {
                        filtro.add(aux);
                    }
               } 
            break;
            
            case 4:
               for(InsumoExplosion aux: expIns) {
                    if(aux.getCodInsumo() != null && (aux.getCodInsumo().substring(0, 3)).equals("7RT")) {
                        filtro.add(aux);
                    }
               } 
            break;
                
            case 5:
               for(InsumoExplosion aux: expIns) {
                    if(aux.getCodInsumo() != null && (aux.getCodInsumo().substring(0, 3)).equals("7SC")) {
                        filtro.add(aux);
                    }
               } 
            break;
                
            case 6:
               for(InsumoExplosion aux: expIns) {
                    if(aux.getCodInsumo() != null && (aux.getCodInsumo().substring(0, 3)).equals("7SE")) {
                        filtro.add(aux);
                    }
               } 
            break;
                
            case 7:
               for(InsumoExplosion aux: expIns) {
                    if(aux.getCodInsumo() != null && (aux.getCodInsumo().substring(0, 3)).equals("7SS")) {
                        filtro.add(aux);
                    }
               } 
            break;
                
            case 8:
               for(InsumoExplosion aux: expIns) {
                    if(aux.getCodInsumo() != null && (aux.getCodInsumo().substring(0, 3)).equals("7ND")) {
                        filtro.add(aux);
                    }
               } 
            break;
                
            case 9:
               for(InsumoExplosion aux: expIns) {
                    if(aux.getCodInsumo() != null && (aux.getCodInsumo().substring(0, 3)).equals("7CH")) {
                        filtro.add(aux);
                    }
               } 
            break;
                
        }
        
        return filtro;
        
    }
    
}
