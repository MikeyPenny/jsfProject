/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.conexion.ConexionBD;
import com.services.ValeConsumoBS;
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
public class ListarValesConsumo implements Serializable {

    private List<FormListValeConsumo> listaValeConsumo;
    private List<FormTablaInsumoValeConsumo> listaInsumos;
    
    public ListarValesConsumo() {
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        ValeConsumoBS vcbs = new ValeConsumoBS(con);
        listaValeConsumo = vcbs.listarValeConsumo();
    }
    
    public void listarDetallesValeConsumo(int idValeConsumo){
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        ValeConsumoBS vcbs = new ValeConsumoBS(con);
        setListaInsumos(vcbs.listarDetallesValeConsumo(idValeConsumo));
    }

    /**
     * @return the listaValeConsumo
     */
    public List<FormListValeConsumo> getListaValeConsumo() {
        return listaValeConsumo;
    }

    /**
     * @param listaValeConsumo the listaValeConsumo to set
     */
    public void setListaValeConsumo(List<FormListValeConsumo> listaValeConsumo) {
        this.listaValeConsumo = listaValeConsumo;
    }

    /**
     * @return the listaInsumos
     */
    public List<FormTablaInsumoValeConsumo> getListaInsumos() {
        return listaInsumos;
    }

    /**
     * @param listaInsumos the listaInsumos to set
     */
    public void setListaInsumos(List<FormTablaInsumoValeConsumo> listaInsumos) {
        this.listaInsumos = listaInsumos;
    }
}
