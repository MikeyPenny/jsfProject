/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo;

import com.bean.ConceptoEstim;
import com.bean.EstimacionCteBean;
import com.conexion.ConexionBD;
import com.dao.EstimaClienteDAO;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class EstimarBO {
    
    public int registrarEstimacion(List<ConceptoEstim> list, int id_proy, EstimacionCteBean estima) {
        
        //Buscar el id del cliente
        ConexionBD c = new ConexionBD();
        EstimaClienteDAO dao = new EstimaClienteDAO(c.getConexion());
        estima.setId_cliente(dao.buscarIdCliente(id_proy, estima.getId_presupuesto()));
        //Fecha de la estimación hoy
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String dateNow = format.format(today);
        estima.setFecha(dateNow);
        int reg = dao.guardarEstimacionCte(estima, list);
        
        return reg;
        
    }
    
    public int buscarIdCliente(int id_proy, int id_pres) {
        
        int id = 0;
        
        ConexionBD c  = new ConexionBD();
        EstimaClienteDAO dao = new EstimaClienteDAO(c.getConexion());
        
        return id;
    
    }
    
}
