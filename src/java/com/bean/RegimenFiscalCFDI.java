
package com.bean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="regimenFiscal")
@XmlType(propOrder = {"regimen"})
public class RegimenFiscalCFDI {
    
    private String regimen;

    @XmlAttribute(name="Regimen")
    public String getRegimen() {
        return regimen;
    }

    /**
     * @param Regimen the Regimen to set
     */
    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }
    
}
