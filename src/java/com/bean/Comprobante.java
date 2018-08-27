
package com.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="Comprobante", namespace = "http://www.sat.gob.mx/cfd/3")
@XmlType(propOrder = {"xsi","cfdi","schemaLocation","version","folio","fecha","formaDePago","noCertificado","numCtaPago",
    "moneda","subtotal","total","tipoDeComprobante","metodoDePago","lugarExpedicion","sello","certificado","emisor","receptor",
    "conceptos","impuestos","complemento"})
public class Comprobante {
    
    private String xsi;
    private String cfdi;
    private String schemaLocation;
    private String version;
    private String folio;
    private String fecha;
    private String formaDePago;
    private String noCertificado;
    private String numCtaPago;
    private String moneda;
    private BigDecimal subtotal;
    private BigDecimal total;
    private String tipoDeComprobante;
    private String metodoDePago;
    private String lugarExpedicion;
    private String sello;
    private String certificado;
    
    private EmisorCFDI emisor;
    private ReceptorCFDI receptor;
    private List<ConceptoCFDI> conceptos= new ArrayList<>();
    private ImpuestoCFDI impuestos;
    private ComplementoCFDI complemento;
    
    @XmlAttribute(name="xsi")
    public String getXsi() {
        return xsi;
    }

    public void setXsi(String xsi) {
        this.xsi = xsi;
    }
    
    @XmlAttribute(name="xmlns:cfdi")
    public String getCfdi() {
        return cfdi;
    }

    public void setCfdi(String cfdi) {
        this.cfdi = cfdi;
    }

    @XmlAttribute(name="xsi:schemaLocation")
    public String getSchemaLocation() {
        return schemaLocation;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }
    
    @XmlAttribute(name="version")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    
    @XmlAttribute(name="folio")
    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }
    
    @XmlAttribute(name="fecha")
    public String getFecha() {
        return fecha;
    }
    
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    @XmlAttribute(name="formaDePago")
    public String getFormaDePago() {
        return formaDePago;
    }

    public void setFormaDePago(String formaDePago) {
        this.formaDePago = formaDePago;
    }

    @XmlAttribute(name="noCertificado")
    public String getNoCertificado() {
        return noCertificado;
    }

    public void setNoCertificado(String noCertificado) {
        this.noCertificado = noCertificado;
    }
    
    @XmlAttribute(name="NumCtaPago")
    public String getNumCtaPago() {
        return numCtaPago;
    }

    public void setNumCtaPago(String numCtaPago) {
        this.numCtaPago = numCtaPago;
    }

    @XmlAttribute(name="Moneda")
    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    @XmlAttribute(name="subTotal")
    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    @XmlAttribute(name="total")
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @XmlAttribute(name="tipoDeComprobante")
    public String getTipoDeComprobante() {
        return tipoDeComprobante;
    }

    public void setTipoDeComprobante(String tipoDeComprobante) {
        this.tipoDeComprobante = tipoDeComprobante;
    }
    
    @XmlAttribute(name="metodoDePago")
    public String getMetodoDePago() {
        return metodoDePago;
    }

    public void setMetodoDePago(String metodoDePago) {
        this.metodoDePago = metodoDePago;
    }

    @XmlAttribute(name="LugarExpedicion")
    public String getLugarExpedicion() {
        return lugarExpedicion;
    }

    public void setLugarExpedicion(String lugarExpedicion) {
        this.lugarExpedicion = lugarExpedicion;
    }
    
    @XmlAttribute(name="sello")
    public String getSello() {
        return sello;
    }

    public void setSello(String sello) {
        this.sello = sello;
    }

    @XmlAttribute(name="certificado")
    public String getCertificado() {
        return certificado;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }
    
    @XmlElement(name="Emisor")
    public EmisorCFDI getEmisor() {
        return emisor;
    }

    public void setEmisor(EmisorCFDI emisor) {
        this.emisor = emisor;
    }

    @XmlElement(name="Receptor")
    public ReceptorCFDI getReceptor() {
        return receptor;
    }

    /**
     * @param receptor the receptor to set
     */
    public void setReceptor(ReceptorCFDI receptor) {
        this.receptor = receptor;
    }

    @XmlElementWrapper(name="Conceptos")
    @XmlElement(name="Concepto", namespace = "http://www.sat.gob.mx/cfd/3")
    public List<ConceptoCFDI> getConceptos() {
        return conceptos;
    }

    /**
     * @param conceptos the conceptos to set
     */
    public void setConceptos(List<ConceptoCFDI> conceptos) {
        this.conceptos = conceptos;
    }

    @XmlElement(name="Impuestos")
    public ImpuestoCFDI getImpuestos() {
        return impuestos;
    }

    /**
     * @param impuestos the impuestos to set
     */
    public void setImpuestos(ImpuestoCFDI impuestos) {
        this.impuestos = impuestos;
    }

    @XmlElement(name="Complemento")
    public ComplementoCFDI getComplemento() {
        return complemento;
    }

    /**
     * @param complemento the complemento to set
     */
    public void setComplemento(ComplementoCFDI complemento) {
        this.complemento = complemento;
    }

    
    
}
