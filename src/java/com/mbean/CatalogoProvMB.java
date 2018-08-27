/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.conexion.ConexionBD;
import com.model.Proveedor;
import com.services.ProveedoresBS;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("catProv")
@ViewScoped
public class CatalogoProvMB implements Serializable {
    
    private List<Proveedor> listProv = new ArrayList<>();
    private List<Proveedor> listFiltro = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        ProveedoresBS p = new ProveedoresBS(con);
        setListProv(p.listarProveedores());
        
    }

    /**
     * @return the listProv
     */
    public List<Proveedor> getListProv() {
        return listProv;
    }

    /**
     * @param listProv the listProv to set
     */
    public void setListProv(List<Proveedor> listProv) {
        this.listProv = listProv;
    }

    /**
     * @return the listFiltro
     */
    public List<Proveedor> getListFiltro() {
        return listFiltro;
    }

    /**
     * @param listFiltro the listFiltro to set
     */
    public void setListFiltro(List<Proveedor> listFiltro) {
        this.listFiltro = listFiltro;
    }
    
}
