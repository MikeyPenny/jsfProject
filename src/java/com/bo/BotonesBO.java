/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo;

/**
 *
 * @author Mickey
 */
public class BotonesBO {
    
    public boolean botonContrato(String status) {
        boolean contrato = false;
        
        if(status.equals("CONTRATO")) {
            contrato = true;
        }
        
        return contrato;
    }
    
}
