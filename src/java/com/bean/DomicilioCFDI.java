/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="cfdi:Domicilio")
@XmlType(propOrder = {"calle","noExterior","noInterior","colonia","municipio","estado","pais","codigoPostal"})
public class DomicilioCFDI {
    
    private String calle;
    private String noExterior;
    private String noInterior;
    private String colonia;
    private String municipio;
    private String estado;
    private String pais;
    private String codigoPostal;

    @XmlAttribute(name="calle")
    public String getCalle() {
        return calle;
    }

    /**
     * @param calle the calle to set
     */
    public void setCalle(String calle) {
        this.calle = calle;
    }

    @XmlAttribute(name="noExterior")
    public String getNoExterior() {
        return noExterior;
    }

    /**
     * @param noExterior the noExterior to set
     */
    public void setNoExterior(String noExterior) {
        this.noExterior = noExterior;
    }

    @XmlAttribute(name="noInterior")
    public String getNoInterior() {
        return noInterior;
    }

    /**
     * @param noInterior the noInterior to set
     */
    public void setNoInterior(String noInterior) {
        this.noInterior = noInterior;
    }

    @XmlAttribute(name="colonia")
    public String getColonia() {
        return colonia;
    }

    /**
     * @param colonia the colonia to set
     */
    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    @XmlAttribute(name="municipio")
    public String getMunicipio() {
        return municipio;
    }

    /**
     * @param municipio the municipio to set
     */
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    @XmlAttribute(name="estado")
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlAttribute(name="pais")
    public String getPais() {
        return pais;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    @XmlAttribute(name="codigoPostal")
    public String getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * @param codigoPostal the codigoPostal to set
     */
    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    
    
}
