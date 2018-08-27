/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.conexion.ConexionBD;
import com.services.RequisicionBS;
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
public class ListadoRequisicionesMB implements Serializable {

    private List<FormTablaRequisicionMB> listaRequisicion;
    
    public ListadoRequisicionesMB() {
        ConexionBD c =  new ConexionBD();
        Connection con = c.getConexion();
        RequisicionBS rbs = new RequisicionBS(con);
        listaRequisicion = rbs.listarTodasRequisiciones();
    }

    /**
     * @return the listaRequisicion
     */
    public List<FormTablaRequisicionMB> getListaRequisicion() {
        return listaRequisicion;
    }

    /**
     * @param listaRequisicion the listaRequisicion to set
     */
    public void setListaRequisicion(List<FormTablaRequisicionMB> listaRequisicion) {
        this.listaRequisicion = listaRequisicion;
    }
    
}
