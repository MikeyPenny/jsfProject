/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.math.BigDecimal;


public class VistaPresBean {
    
    private int id_concepto;
    private String partidaCve;
    private String partidaNombre;
    private String subPartidaCve;
    private String subPartidaNombre;
    private String concepto;
    private String cveConcepto;
    private String descripcion;
    private String unidad;
    private BigDecimal cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal importe;
    private int id_archivo;

    /**
     * @return the id_concepto
     */
    public int getId_concepto() {
        return id_concepto;
    }

    /**
     * @param id_concepto the id_concepto to set
     */
    public void setId_concepto(int id_concepto) {
        this.id_concepto = id_concepto;
    }

    /**
     * @return the partidaCve
     */
    public String getPartidaCve() {
        return partidaCve;
    }

    /**
     * @param partidaCve the partidaCve to set
     */
    public void setPartidaCve(String partidaCve) {
        this.partidaCve = partidaCve;
    }

    /**
     * @return the partidaNombre
     */
    public String getPartidaNombre() {
        return partidaNombre;
    }

    /**
     * @param partidaNombre the partidaNombre to set
     */
    public void setPartidaNombre(String partidaNombre) {
        this.partidaNombre = partidaNombre;
    }

    /**
     * @return the subPartidaCve
     */
    public String getSubPartidaCve() {
        return subPartidaCve;
    }

    /**
     * @param subPartidaCve the subPartidaCve to set
     */
    public void setSubPartidaCve(String subPartidaCve) {
        this.subPartidaCve = subPartidaCve;
    }

    /**
     * @return the subPartidaNombre
     */
    public String getSubPartidaNombre() {
        return subPartidaNombre;
    }

    /**
     * @param subPartidaNombre the subPartidaNombre to set
     */
    public void setSubPartidaNombre(String subPartidaNombre) {
        this.subPartidaNombre = subPartidaNombre;
    }

    /**
     * @return the concepto
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * @param concepto the concepto to set
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     * @return the cveConcepto
     */
    public String getCveConcepto() {
        return cveConcepto;
    }

    /**
     * @param cveConcepto the cveConcepto to set
     */
    public void setCveConcepto(String cveConcepto) {
        this.cveConcepto = cveConcepto;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the unidad
     */
    public String getUnidad() {
        return unidad;
    }

    /**
     * @param unidad the unidad to set
     */
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    /**
     * @return the cantidad
     */
    public BigDecimal getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the precioUnitario
     */
    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * @param precioUnitario the precioUnitario to set
     */
    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    /**
     * @return the importe
     */
    public BigDecimal getImporte() {
        return importe;
    }

    /**
     * @param importe the importe to set
     */
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    /**
     * @return the id_archivo
     */
    public int getId_archivo() {
        return id_archivo;
    }

    /**
     * @param id_archivo the id_archivo to set
     */
    public void setId_archivo(int id_archivo) {
        this.id_archivo = id_archivo;
    }
    
}
