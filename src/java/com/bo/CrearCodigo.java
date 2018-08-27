/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo;

import com.conexion.ConexionBD;
import com.dao.PresupuestoDAO;
import java.sql.Connection;


public class CrearCodigo {
    
    public String codificarConcepto(String codProy, String clave, int id_pres, String capCons) {
        
        String cod = "";
        
        cod = cod+codProy;
        
        
        //String ch = ""+clave.charAt(ind1);
        
        cod = cod+"-"+clave+"-";
        //System.out.println(cod);
        
        cod = cod+capCons;
        
        //System.out.println(cod);
        //System.out.println(cod);
        //System.out.println(lng);
        return cod;
    }
    
    public String crearConsecutivo(int id_proy, int id_pres) {
        
        ConexionBD c =  new ConexionBD();
        Connection con = c.getConexion();
        PresupuestoDAO pd = new PresupuestoDAO(con);
        String consecutivo = pd.busarConsecutivoConcepto(id_proy, id_pres);
        String conAux = "";
        for(int i = consecutivo.length(); i<4; i++) {
            conAux = conAux+"0";
        }
        conAux = conAux + consecutivo;
        
        
        return conAux;
        
    }
    
}
