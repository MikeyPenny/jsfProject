/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 *
 * @author Mickey
 */
public class FormatoString {
    
    public String formatoCantidades(BigDecimal cant) {
        
        DecimalFormat formato = new DecimalFormat("###,###,###.##");
        
        String cad = formato.format(cant);
        
        return cad;
        
    }
    
}
