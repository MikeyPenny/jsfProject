/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="timbreFiscalDigital")
@XmlType(propOrder = {"xsi","schemaLocation","version","uuid","fechaTimbrado","selloCFD","noCertificado",
    "selloSAT","tfd"})
public class TimbreFiscalDigitalCFDI {
    
    private String xsi;
    private String schemaLocation;
    private String version;
    private String uuid;
    private String fechaTimbrado;
    private String selloCFD;
    private String noCertificado;
    private String selloSAT;
    private String tfd;

    @XmlAttribute(name="xsi")
    public String getXsi() {
        return xsi;
    }

    /**
     * @param xsi the xsi to set
     */
    public void setXsi(String xsi) {
        this.xsi = xsi;
    }

    @XmlAttribute(name="schemaLocation")
    public String getSchemaLocation() {
        return schemaLocation;
    }

    /**
     * @param schemaLocation the schemaLocation to set
     */
    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    @XmlAttribute(name="version")
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    @XmlAttribute(name="UUID")
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @XmlAttribute(name="FechaTimbrado")
    public String getFechaTimbrado() {
        return fechaTimbrado;
    }

    /**
     * @param fechaTimbrado the fechaTimbrado to set
     */
    public void setFechaTimbrado(String fechaTimbrado) {
        this.fechaTimbrado = fechaTimbrado;
    }

    @XmlAttribute(name="selloCFD")
    public String getSelloCFD() {
        return selloCFD;
    }

    /**
     * @param selloCFD the selloCFD to set
     */
    public void setSelloCFD(String selloCFD) {
        this.selloCFD = selloCFD;
    }

    @XmlAttribute(name="noCertificadoSAT")
    public String getNoCertificado() {
        return noCertificado;
    }

    /**
     * @param noCertificado the noCertificado to set
     */
    public void setNoCertificado(String noCertificado) {
        this.noCertificado = noCertificado;
    }

    @XmlAttribute(name="selloSAT")
    public String getSelloSAT() {
        return selloSAT;
    }

    /**
     * @param selloSAT the selloSAT to set
     */
    public void setSelloSAT(String selloSAT) {
        this.selloSAT = selloSAT;
    }

    @XmlAttribute(name="tfd")
    public String getTfd() {
        return tfd;
    }

    /**
     * @param tfd the tfd to set
     */
    public void setTfd(String tfd) {
        this.tfd = tfd;
    }
    
}
