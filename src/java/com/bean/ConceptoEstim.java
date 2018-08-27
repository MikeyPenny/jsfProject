/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.io.Serializable;
import java.math.BigDecimal;


public class ConceptoEstim implements Serializable {
    
    private int id_concepto;
    private String subPartida;
    private String cveSubPrtda;
    private String numConcepto;
    private String codConcepto;
    private String descripcion;
    private String unidad;
    private BigDecimal cantidad;
    private BigDecimal presUnitario;
    private BigDecimal importe;
    private String cadCant;
    private String cadPrecio;
    private String cadImp;
    private BigDecimal sumaImporte;
    private int id_partida;
    private String partida;
    private BigDecimal cantEstimada;
    private BigDecimal sumaEstimado;
    private BigDecimal porEstimar;

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
     * @return the subPartida
     */
    public String getSubPartida() {
        return subPartida;
    }

    /**
     * @param subPartida the subPartida to set
     */
    public void setSubPartida(String subPartida) {
        this.subPartida = subPartida;
    }

    /**
     * @return the cveSubPrtda
     */
    public String getCveSubPrtda() {
        return cveSubPrtda;
    }

    /**
     * @param cveSubPrtda the cveSubPrtda to set
     */
    public void setCveSubPrtda(String cveSubPrtda) {
        this.cveSubPrtda = cveSubPrtda;
    }

    /**
     * @return the numConcepto
     */
    public String getNumConcepto() {
        return numConcepto;
    }

    /**
     * @param numConcepto the numConcepto to set
     */
    public void setNumConcepto(String numConcepto) {
        this.numConcepto = numConcepto;
    }

    /**
     * @return the codConcepto
     */
    public String getCodConcepto() {
        return codConcepto;
    }

    /**
     * @param codConcepto the codConcepto to set
     */
    public void setCodConcepto(String codConcepto) {
        this.codConcepto = codConcepto;
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
     * @return the pUnitario
     */
    public BigDecimal getPresUnitario() {
        return presUnitario;
    }

    /**
     * @param pUnitario the pUnitario to set
     */
    public void setPresUnitario(BigDecimal presUnitario) {
        this.presUnitario = presUnitario;
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
     * @return the cadCant
     */
    public String getCadCant() {
        return cadCant;
    }

    /**
     * @param cadCant the cadCant to set
     */
    public void setCadCant(String cadCant) {
        this.cadCant = cadCant;
    }

    /**
     * @return the cadPrecio
     */
    public String getCadPrecio() {
        return cadPrecio;
    }

    /**
     * @param cadPrecio the cadPrecio to set
     */
    public void setCadPrecio(String cadPrecio) {
        this.cadPrecio = cadPrecio;
    }

    /**
     * @return the cadImp
     */
    public String getCadImp() {
        return cadImp;
    }

    /**
     * @param cadImp the cadImp to set
     */
    public void setCadImp(String cadImp) {
        this.cadImp = cadImp;
    }

    /**
     * @return the sumaImporte
     */
    public BigDecimal getSumaImporte() {
        return sumaImporte;
    }

    /**
     * @param sumaImporte the sumaImporte to set
     */
    public void setSumaImporte(BigDecimal sumaImporte) {
        this.sumaImporte = sumaImporte;
    }

    /**
     * @return the id_partida
     */
    public int getId_partida() {
        return id_partida;
    }

    /**
     * @param id_partida the id_partida to set
     */
    public void setId_partida(int id_partida) {
        this.id_partida = id_partida;
    }

    /**
     * @return the partida
     */
    public String getPartida() {
        return partida;
    }

    /**
     * @param partida the partida to set
     */
    public void setPartida(String partida) {
        this.partida = partida;
    }

    /**
     * @return the cantEstimada
     */
    public BigDecimal getCantEstimada() {
        return cantEstimada;
    }

    /**
     * @param cantEstimada the cantEstimada to set
     */
    public void setCantEstimada(BigDecimal cantEstimada) {
        this.cantEstimada = cantEstimada;
    }

    /**
     * @return the sumaEstimado
     */
    public BigDecimal getSumaEstimado() {
        return sumaEstimado;
    }

    /**
     * @param sumaEstimado the sumaEstimado to set
     */
    public void setSumaEstimado(BigDecimal sumaEstimado) {
        this.sumaEstimado = sumaEstimado;
    }

    /**
     * @return the porEstimar
     */
    public BigDecimal getPorEstimar() {
        return porEstimar;
    }

    /**
     * @param porEstimar the porEstimar to set
     */
    public void setPorEstimar(BigDecimal porEstimar) {
        this.porEstimar = porEstimar;
    }
    
}
