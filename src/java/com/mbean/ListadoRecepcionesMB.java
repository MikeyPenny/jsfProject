/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.conexion.ConexionBD;
import com.services.RecepcionBS;
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
public class ListadoRecepcionesMB implements Serializable {

    private List<TablaInsumosRecepcion> listaInsumos;
    private List<FormListRecepcionMB> listRecepciones;
    
    
    public ListadoRecepcionesMB() {
        ConexionBD c =  new ConexionBD();
        Connection con = c.getConexion();
        RecepcionBS rbs = new RecepcionBS(con);
        listRecepciones = rbs.listarRecepciones();
    }
    
    public void listarDetallesRecepcion(int idRecepcion){
        ConexionBD c =  new ConexionBD();
        Connection con = c.getConexion();
        RecepcionBS rbs = new RecepcionBS(con);
        listaInsumos = rbs.listarDetallesRecepcionPorIdRecepcion(idRecepcion);
    
    }

    /**
     * @return the listaInsumos
     */
    public List<TablaInsumosRecepcion> getListaInsumos() {
        return listaInsumos;
    }

    /**
     * @param listaInsumos the listaInsumos to set
     */
    public void setListaInsumos(List<TablaInsumosRecepcion> listaInsumos) {
        this.listaInsumos = listaInsumos;
    }

    /**
     * @return the listRecepciones
     */
    public List<FormListRecepcionMB> getListRecepciones() {
        return listRecepciones;
    }

    /**
     * @param listRecepciones the listRecepciones to set
     */
    public void setListRecepciones(List<FormListRecepcionMB> listRecepciones) {
        this.listRecepciones = listRecepciones;
    }
}
