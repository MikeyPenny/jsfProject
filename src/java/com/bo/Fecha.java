/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Mickey
 */
public class Fecha {
    
    public String generarFecha() {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String dateNow = format.format(today);
        
        return dateNow;
    }
    
}
