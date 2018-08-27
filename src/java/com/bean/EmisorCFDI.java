
package com.bean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="emisor")
@XmlType(propOrder={"rfc","nombre","domicilioFiscal","regimenFiscal"})
public class EmisorCFDI {
    
    private String rfc;
    private String nombre;
    private DomicilioFiscalCFDI domicilioFiscal;
    private RegimenFiscalCFDI regimenFiscal;

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

    @XmlElement(name="DomicilioFiscal")
    public DomicilioFiscalCFDI getDomicilioFiscal() {
        return domicilioFiscal;
    }

    /**
     * @param domicilioFiscal the domicilioFiscal to set
     */
    public void setDomicilioFiscal(DomicilioFiscalCFDI domicilioFiscal) {
        this.domicilioFiscal = domicilioFiscal;
    }

    @XmlElement(name="RegimenFiscal")
    public RegimenFiscalCFDI getRegimenFiscal() {
        return regimenFiscal;
    }

    /**
     * @param regimenFiscal the regimenFiscal to set
     */
    public void setRegimenFiscal(RegimenFiscalCFDI regimenFiscal) {
        this.regimenFiscal = regimenFiscal;
    }
    
}
