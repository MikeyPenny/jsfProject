/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo;

import com.bean.AnticipoBean;
import com.conexion.ConexionBD;
import com.dao.ContratistaDAO;
import java.math.BigDecimal;
import java.sql.Connection;

/**
 *
 * @author Mickey
 */
public class AnticipoBO {
    
    public AnticipoBean calcularIVA(AnticipoBean antcpo) {
        BigDecimal ivaAntcpo = antcpo.getImporte().multiply(BigDecimal.valueOf(.16));
        antcpo.setIva(ivaAntcpo);
        if(antcpo.getTipo().equals("FLETES Y ACARREOS")) {
            BigDecimal rtnFlete = calcularRetFlete(ivaAntcpo);
            antcpo.setRtnFlete(rtnFlete);
            antcpo.setTotal(sumarAnticipo(antcpo.getImporte(), ivaAntcpo, rtnFlete));
        }
        if(antcpo.getTipo().equals("RENTA DE EQUIPO")) {
            BigDecimal rtnRenta = calcularRtnRenta(ivaAntcpo);
            antcpo.setRtnRenta(rtnRenta);
            antcpo.setTotal(sumarAnticipo(antcpo.getImporte(), ivaAntcpo, rtnRenta));
            
        }
        if (!antcpo.getTipo().equals("RENTA DE EQUIPO") && !antcpo.getTipo().equals("FLETES Y ACARREOS")) {
            antcpo.setTotal(sumarAnticipo(antcpo.getImporte(), ivaAntcpo, BigDecimal.ZERO));
        }
        
                
        ConexionBD conexion = new ConexionBD();
        Connection con = conexion.getConexion();
        ContratistaDAO cd = new ContratistaDAO(con);
        cd.registrarAnticipo(antcpo);
        
        return antcpo;
    }
    
    public AnticipoBean calcularIVAActual(AnticipoBean antcpo) {
        BigDecimal ivaAntcpo = antcpo.getImporte().multiply(BigDecimal.valueOf(.16));
        antcpo.setIva(ivaAntcpo);
        if(antcpo.getTipo().equals("FLETES Y ACARREOS")) {
            BigDecimal rtnFlete = calcularRetFlete(ivaAntcpo);
            antcpo.setRtnFlete(rtnFlete);
            antcpo.setTotal(sumarAnticipo(antcpo.getImporte(), ivaAntcpo, rtnFlete));
        }
        if(antcpo.getTipo().equals("RENTA DE EQUIPO")) {
            BigDecimal rtnRenta = calcularRtnRenta(ivaAntcpo);
            antcpo.setRtnRenta(rtnRenta);
            antcpo.setTotal(sumarAnticipo(antcpo.getImporte(), ivaAntcpo, rtnRenta));
            
        }
        if (!antcpo.getTipo().equals("RENTA DE EQUIPO") && !antcpo.getTipo().equals("FLETES Y ACARREOS")) {
            antcpo.setTotal(sumarAnticipo(antcpo.getImporte(), ivaAntcpo, BigDecimal.ZERO));
        }
        
                
        ConexionBD conexion = new ConexionBD();
        Connection con = conexion.getConexion();
        ContratistaDAO cd = new ContratistaDAO(con);
        cd.registrarAnticipoActual(antcpo);
        
        return antcpo;
    }
    
    public BigDecimal calcularRetFlete(BigDecimal iva) {
        
        BigDecimal rtnFlete;
        rtnFlete = iva.multiply(BigDecimal.valueOf(.04));
        
        return rtnFlete;
    }
    
    public BigDecimal calcularRtnRenta(BigDecimal iva) {
        BigDecimal rtnRenta = BigDecimal.ZERO;
        float div = iva.floatValue()/3;
        float mult = div * 2;
        rtnRenta = BigDecimal.valueOf(mult);
        
        return rtnRenta;
    }
    
    public BigDecimal sumarAnticipo(BigDecimal importe, BigDecimal iva, BigDecimal rtn) {
        BigDecimal sumaTotal = BigDecimal.ZERO;
        
        sumaTotal = (importe.add(iva)).subtract(rtn);
        
        return sumaTotal;
    }
    
}
