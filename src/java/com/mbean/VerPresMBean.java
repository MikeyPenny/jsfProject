/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.bean.VistaPresBean;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Mickey
 */
@ManagedBean
@SessionScoped
public class VerPresMBean implements Serializable {

    private ArrayList<VistaPresBean> listaConcpto = new ArrayList<VistaPresBean>();
    private VistaPresBean conceptSel;

    /**
     * Creates a new instance of VerPresMBean
     */
    public VerPresMBean() {
    }
    
    public void buscarPresupuesto(int id_proy, int id_pres) {
        
    }

    /**
     * @return the listaConcpto
     */
    public ArrayList<VistaPresBean> getListaConcpto() {
        return listaConcpto;
    }

    /**
     * @param listaConcpto the listaConcpto to set
     */
    public void setListaConcpto(ArrayList<VistaPresBean> listaConcpto) {
        this.listaConcpto = listaConcpto;
    }

    /**
     * @return the conceptSel
     */
    public VistaPresBean getConceptSel() {
        return conceptSel;
    }

    /**
     * @param conceptSel the conceptSel to set
     */
    public void setConceptSel(VistaPresBean conceptSel) {
        this.conceptSel = conceptSel;
    }
    
}
