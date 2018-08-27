/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo;

import com.bean.EstimacionBean;
import com.bean.InsumoContrat;
import com.conexion.ConexionBD;
import com.dao.ContratistaDAO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author Mickey
 */
public class EstimacionBO {
    
    public void registrarEstimacion(EstimacionBean estim, List<InsumoContrat> lista, int id_avance, int id_proy, int id_pres) {
        
        estim.setSubtotal(calcularSubtotal(estim));
        BigDecimal iva = BigDecimal.valueOf(.16);
        estim.setIvaEst(estim.getSubtotal().multiply(iva));
        estim.setEstatus("ESTIMADO");
        estim.setTotal(calcularTotal(estim));
        ConexionBD cnxn = new ConexionBD();
        Connection con = cnxn.getConexion();
        ContratistaDAO cd = new ContratistaDAO(con);
        cd.estimarAvance(estim, lista, id_avance, id_proy, id_pres);
        
    }
    
    public BigDecimal calcularSubtotal(EstimacionBean estim) {
        
        BigDecimal subtotal = (estim.getEstimacionImp().subtract(estim.getAmortAntcpo()).subtract(estim.getRtnGarantia()));
        
        
        return subtotal;
    }
    
    public BigDecimal calcularTotal(EstimacionBean estim) {
        
        BigDecimal total = estim.getSubtotal().add(estim.getIvaEst()).subtract(estim.getRtnFlete().subtract(estim.getRtnRenta()));
        
        return total;
    }
    
}
