/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="receptor")
@XmlType(propOrder = {"rfc","nombre","domicilio"})
public class ReceptorCFDI {
    
    private String rfc;
    private String nombre;
    private DomicilioCFDI domicilio;

    @XmlAttribute(name="rfc")
    public String getRfc() {
        return rfc;
    }

    /**
     * @param rfc the rfc to set
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    @XmlAttribute(name="nombre")
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlElement(name="Domicilio")
    public DomicilioCFDI getDomicilio() {
        return domicilio;
    }

    /**
     * @param domicilio the domicilio to set
     */
    public void setDomicilio(DomicilioCFDI domicilio) {
        this.domicilio = domicilio;
    }
    
}
