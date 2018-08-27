/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.conexion.ConexionBD;
import com.services.DevolucionBS;
import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Mickey
 */
@ManagedBean
@RequestScoped
public class ListadoDevolucionesMB implements Serializable {

    private List<FormListDevolucion> listaDevoluciones;
    
    private List<TablaInsumoDevolucion> listaInsumos;
    
    public ListadoDevolucionesMB() {
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        DevolucionBS dbs = new DevolucionBS(con);
        listaDevoluciones = dbs.listarDevoluciones(); 
    }
    
    public void listarDetallesDevolucion(int idDevolucion){
        
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        DevolucionBS dbs = new DevolucionBS(con);
        setListaInsumos(dbs.listarDetallesDevolucionPorIdDevolucion(idDevolucion));
        
    
    }

    /**
     * @return the listaDevoluciones
     */
    public List<FormListDevolucion> getListaDevoluciones() {
        return listaDevoluciones;
    }

    /**
     * @param listaDevoluciones the listaDevoluciones to set
     */
    public void setListaDevoluciones(List<FormListDevolucion> listaDevoluciones) {
        this.listaDevoluciones = listaDevoluciones;
    }

    /**
     * @return the listaInsumos
     */
    public List<TablaInsumoDevolucion> getListaInsumos() {
        return listaInsumos;
    }

    /**
     * @param listaInsumos the listaInsumos to set
     */
    public void setListaInsumos(List<TablaInsumoDevolucion> listaInsumos) {
        this.listaInsumos = listaInsumos;
    }
    
}
