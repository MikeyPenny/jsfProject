/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo;

import com.conexion.ConexionBD;
import com.dao.EstimaClienteDAO;
import java.math.BigDecimal;


public class CalcularBO {
    
    public float calcularPct(int id_pres) {
        
        ConexionBD c = new ConexionBD();
        EstimaClienteDAO dao = new EstimaClienteDAO(c.getConexion());
        BigDecimal sumaEst = dao.sumarEstAlCliente(id_pres);
        float sum = sumaEst.floatValue();
        
        c = new ConexionBD();
        dao = new EstimaClienteDAO(c.getConexion());
        BigDecimal presupuesto = dao.sumarPres(id_pres);
        float pres = presupuesto.floatValue();
        
        float pct = sum*100;
        pct = pct/pres;
        
        //System.out.println(pct);
        //System.out.println(sum);
        //System.out.println(pres);
        
        return pct;
        
    }
    
}
