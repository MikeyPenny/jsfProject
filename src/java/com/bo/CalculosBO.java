/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo;

import com.bean.PagoBean;
import java.math.BigDecimal;


public class CalculosBO {
    
    public BigDecimal calcularIva(int iva, BigDecimal imp) {
        
        BigDecimal ivaImp = BigDecimal.ZERO;
        float aux = iva/100;
        
        ivaImp = imp.multiply(BigDecimal.valueOf(aux));
        
        return ivaImp;
        
    }
    
    public BigDecimal sumarTotal(PagoBean pago) {
        
        BigDecimal suma = pago.getImporteEstimacion().add(pago.getIva());
        
        return suma;
    }
    
}
