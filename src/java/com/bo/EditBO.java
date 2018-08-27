/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo;

import com.bean.Apoderado;
import com.bean.Banco;
import com.bean.ClienteAltaBean;
import com.conexion.ConexionBD;
import com.dao.ClienteDAO;
import java.util.List;


public class EditBO {
    
    public int editarCliente(ClienteAltaBean cl, List<Banco> listBnk, List<Apoderado> listApod, List<Integer> listCancel, List<Integer> listApodCancel) {
        
        int reg;
        
        ConexionBD c = new ConexionBD();
        ClienteDAO cd = new ClienteDAO(c.getConexion());
        reg = cd.editarCliente(cl, listApod, listBnk, listCancel, listApodCancel);
        
        return reg;
        
    }
    
}
