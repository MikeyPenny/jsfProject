/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;


public class CadenasCortas {
    
    public String resumirDescripcion(String desc) {
        String shortDesc = "";
        
        if(desc.length() < 120) {
            shortDesc = desc;
        }else {
            shortDesc = desc.substring(0, 120);
        }
        
        return shortDesc;
    }
    
}
