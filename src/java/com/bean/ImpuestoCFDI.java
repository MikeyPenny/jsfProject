/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="impuestos")
@XmlType(propOrder = {"totalImpuestosRetenidos","totalImpuestosTrasladados","retenciones","traslados"})
public class ImpuestoCFDI {
    
    private BigDecimal totalImpuestosRetenidos;
    private BigDecimal totalImpuestosTrasladados;
    private List<RetencionCFDI> retenciones = new ArrayList<>();
    private List<TrasladosCFDI> traslados = new ArrayList<>();

    @XmlAttribute(name="totalImpuestosRetenidos")
    public BigDecimal getTotalImpuestosRetenidos() {
        return totalImpuestosRetenidos;
    }

    /**
     * @param totalImpuestosRetenidos the totalImpuestosRetenidos to set
     */
    public void setTotalImpuestosRetenidos(BigDecimal totalImpuestosRetenidos) {
        this.totalImpuestosRetenidos = totalImpuestosRetenidos;
    }

    @XmlAttribute(name="totalImpuestosTrasladados")
    public BigDecimal getTotalImpuestosTrasladados() {
        return totalImpuestosTrasladados;
    }

    /**
     * @param totalImpuestosTrasladados the totalImpuestosTrasladados to set
     */
    public void setTotalImpuestosTrasladados(BigDecimal totalImpuestosTrasladados) {
        this.totalImpuestosTrasladados = totalImpuestosTrasladados;
    }
    
    @XmlElementWrapper(name="Retenciones")
    @XmlElement(name="Retencion", namespace = "http://www.sat.gob.mx/cfd/3")
    public List<RetencionCFDI> getRetenciones() {
        return retenciones;
    }

    /**
     * @param retenciones the retenciones to set
     */
    public void setRetenciones(List<RetencionCFDI> retenciones) {
        this.retenciones = retenciones;
    }

    @XmlElementWrapper(name="Traslados")
    @XmlElement(name="Traslado", namespace = "http://www.sat.gob.mx/cfd/3")
    public List<TrasladosCFDI> getTraslados() {
        return traslados;
    }

    /**
     * @param traslados the traslados to set
     */
    public void setTraslados(List<TrasladosCFDI> traslados) {
        this.traslados = traslados;
    }
    
}
