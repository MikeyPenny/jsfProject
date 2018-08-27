
package com.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="complemento")
@XmlType(propOrder = {"timbre"})
public class ComplementoCFDI {
    
    private TimbreFiscalDigitalCFDI  timbre;

    @XmlElement(name="TimbreFiscalDigital")
    public TimbreFiscalDigitalCFDI getTimbre() {
        return timbre;
    }

    /**
     * @param timbre the timbre to set
     */
    public void setTimbre(TimbreFiscalDigitalCFDI timbre) {
        this.timbre = timbre;
    }
    
}
