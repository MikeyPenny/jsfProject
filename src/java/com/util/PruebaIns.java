/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.bean.InsumoExplosion;
import java.math.BigDecimal;
import java.util.List;


public class PruebaIns {
    
    public void sumarIns(List<InsumoExplosion> expIns) {
        
        BigDecimal sumaTotal = BigDecimal.ZERO;
        for(InsumoExplosion aux: expIns) {
            BigDecimal sub = aux.getPrecioUnit().multiply(aux.getCantConcepto());
            
            sumaTotal = sumaTotal.add(sub);
            
        }
        
        System.out.println(sumaTotal);
    }
    
}
