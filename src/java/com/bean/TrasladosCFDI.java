
package com.bean;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="traslado")
@XmlType(propOrder = {"impuesto","tasa","importe"})
public class TrasladosCFDI {
    
    private String impuesto;
    private BigDecimal tasa;
    private BigDecimal importe;

    @XmlAttribute(name="impuesto")
    public String getImpuesto() {
        return impuesto;
    }

    /**
     * @param impuesto the impuesto to set
     */
    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    @XmlAttribute(name="tasa")
    public BigDecimal getTasa() {
        return tasa;
    }

    /**
     * @param tasa the tasa to set
     */
    public void setTasa(BigDecimal tasa) {
        this.tasa = tasa;
    }

    @XmlAttribute(name="importe")
    public BigDecimal getImporte() {
        return importe;
    }

    /**
     * @param importe the importe to set
     */
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }
    
}
