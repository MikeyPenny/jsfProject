/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

/**
 *
 * @author Mickey
 */
public class Cadenas {
    
    public String resumirDescripcion(String desc) {

        String resum = "";
        
        if(desc.length() > 40) {
            resum = desc.substring(0, 40);
        }else {
            resum = desc;
        }
               
        
        return resum;
        
    }
    
}
