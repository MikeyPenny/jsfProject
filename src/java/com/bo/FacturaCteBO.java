/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo;

import com.bean.Cliente;
import com.bean.EstimacionCteBean;
import com.bean.ImportesBean;
import com.bean.ProyectoCteEst;
import com.conexion.ConexionBD;
import com.dao.FacturaClienteDAO;
import java.math.BigDecimal;
import java.util.List;


public class FacturaCteBO {
    
    public int buscarId(int id_proy, int id_pres) {
        
        int id = 0;
        
        ConexionBD c = new ConexionBD();
        FacturaClienteDAO dao = new FacturaClienteDAO(c.getConexion());
        id = dao.buscarIdCliente(id_proy, id_pres);
                
        return id;
        
    }
    
    public List<EstimacionCteBean> listarEstimaciones(int id_proy, int id_pres) {
        
        /*int[] ids = new int[3];
        ids[0] = id_proy;
        ids[1] = id_pres;
        ids[3] = buscarId(id_proy, id_pres);*/
        
        List<EstimacionCteBean> list;
        
        
        ConexionBD c = new ConexionBD();
        FacturaClienteDAO dao = new FacturaClienteDAO(c.getConexion());
        list = dao.listEstimaciones(id_pres);
        
        
        return list;
        
    }
    
    public List<EstimacionCteBean> listarEstimacionesFac(int id_proy, int id_pres) {
        
        /*int[] ids = new int[3];
        ids[0] = id_proy;
        ids[1] = id_pres;
        ids[3] = buscarId(id_proy, id_pres);*/
        
        List<EstimacionCteBean> list;
        
        
        ConexionBD c = new ConexionBD();
        FacturaClienteDAO dao = new FacturaClienteDAO(c.getConexion());
        list = dao.listEstimacionesFacturadas(id_pres);
        
        
        return list;
        
    }
    
    public Cliente buscarDatosCliente(int id_cte) {
        
        ConexionBD c = new ConexionBD();
        FacturaClienteDAO dao = new FacturaClienteDAO(c.getConexion());
        Cliente cte = dao.buscarClienteXId(id_cte);
                
        return cte;
        
    }
    
    public ProyectoCteEst cargarDatosProyecto(int id_proy) {
        
        ConexionBD c  = new ConexionBD();
        FacturaClienteDAO dao = new FacturaClienteDAO(c.getConexion());
        ProyectoCteEst proyecto = dao.buscarProyectoPorId(id_proy);
                
        return proyecto;
        
    }
    
    public BigDecimal calcularGarantia(float pct, BigDecimal importe, String tipo) {
        
        BigDecimal garantia = BigDecimal.ZERO;
        
        if(tipo.equals("normal") || tipo.equals("noIva")) {
            float aux = pct/100;
            garantia = importe.multiply(BigDecimal.valueOf(aux));
        }
        
        
        
        return garantia;
    }
    
    public BigDecimal calcularAnticipo(float pct, BigDecimal importe) {
        
        float aux = pct/100;
        BigDecimal anticipo = importe.multiply(BigDecimal.valueOf(aux));
                
        return anticipo;
        
    }
    
    public BigDecimal garantiaDespIva(BigDecimal importe, float pct, String tipo) {
        
        BigDecimal garantia = BigDecimal.ZERO;
        
        if(tipo.equals("after")) {
            
            System.out.println(tipo);
            float aux = pct/100;
            garantia = importe.multiply(BigDecimal.valueOf(aux));
            
        }
        
        
        
        return garantia;
        
    }
    
    public BigDecimal calcularIVA(float ivaVal, BigDecimal importe, String tipo) {
        
        BigDecimal iva = BigDecimal.ZERO;
        
        if(!tipo.equals("noIva")) {
            //System.out.println(ivaVal);
            ivaVal = ivaVal/100;
            iva = importe.multiply(BigDecimal.valueOf(ivaVal));
        }
        
        return iva;
        
    }
    
    public void sumarSubtotal() {
        
        
        
    }
    
    public BigDecimal sumarTotal(ImportesBean importes, String tipo) {
        
        BigDecimal total = BigDecimal.ZERO;
        
        if(tipo.equals("normal") || tipo.equals("noIva")) {
            total = importes.getSubtotal().add(importes.getIva());
        }
        if(tipo.equals("after")) {
            total = importes.getSubtotal().add(importes.getIva()).subtract(importes.getGtiDesIVA());
        }
       
        return total;
        
    }
    
    public BigDecimal sumarFacturas(int id_est) {
        
        ConexionBD c = new ConexionBD();
        FacturaClienteDAO dao = new FacturaClienteDAO(c.getConexion());
        BigDecimal suma = dao.sumarFacturado(id_est);
        
        return suma;
        
    }
    
    public int verificarEstatus(BigDecimal importe, BigDecimal resto) {
        
        int estatus = 0;
        
        if(resto.subtract(importe).floatValue() <= 0.00) {
            estatus = 3;
            
        }else {
            estatus = 2;
        }
        
        return estatus;
        
    }
    
    public boolean verificarFactura(String factura, int id_cliente) {
        
        ConexionBD c = new ConexionBD();
        FacturaClienteDAO dao = new FacturaClienteDAO(c.getConexion());
        boolean existe = false;
        
        if(dao.verificarFactura(factura, id_cliente) == 1) {
            existe = true;
        }
        
        return existe;
        
    }
    
    public BigDecimal otrasDeductivas(int id_est) {
        
        ConexionBD c = new ConexionBD();
        FacturaClienteDAO dao = new FacturaClienteDAO(c.getConexion());
        BigDecimal suma = dao.sumarDeductivasporFactura(id_est);
        
        
        return suma;
        
    }
    
}
