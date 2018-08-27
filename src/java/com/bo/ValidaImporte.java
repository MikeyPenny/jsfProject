/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo;

import com.bean.InsumoContrat;
import java.util.List;

/**
 *
 * @author Mickey
 */
public class ValidaImporte {
    
    public boolean validarImportesContrato(List<InsumoContrat> lista) {
        
        boolean flag = true;
        
        for(InsumoContrat aux: lista) {
            if(aux.getImporteCont() == null) {
                flag = false;
                //break;
            }
        }
        
        return flag;
    }
    
}
