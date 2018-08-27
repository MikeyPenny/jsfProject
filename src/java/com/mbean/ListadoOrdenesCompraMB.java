/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.conexion.ConexionBD;
import com.services.OrdenCompraBS;
import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Mickey
 */
@ManagedBean
@ViewScoped
public class ListadoOrdenesCompraMB implements Serializable {

    private List<FormOrdenCompraMB> listOrdenCompra;
    
    public ListadoOrdenesCompraMB() {
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        listOrdenCompra = new OrdenCompraBS(con).listarTodasOrdenesCompra();
    }

    /**
     * @return the listOrdenCompra
     */
    public List<FormOrdenCompraMB> getListOrdenCompra() {
        return listOrdenCompra;
    }

    /**
     * @param listOrdenCompra the listOrdenCompra to set
     */
    public void setListOrdenCompra(List<FormOrdenCompraMB> listOrdenCompra) {
        this.listOrdenCompra = listOrdenCompra;
    }
    
}
