/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


public class Concepts implements Serializable {
    
    private String partida;
    private String subPrtda;
    private String descPartida;
    private int id_concepto;
    private int numConcepto;
    private String codConcepto;
    private String descripcion;
    private String unidad;
    private BigDecimal cantidad;
    private BigDecimal cantCtrl;
    private BigDecimal pUnitario;
    private BigDecimal importe;
    private String cadCant;
    private String cadPrecio;
    private String cadImp;
    private BigDecimal sumaImporte;
    private int id_partida;
    private List<InsumoBean> insumos;

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public String getSubPrtda() {
        return subPrtda;
    }

    public void setSubPrtda(String subPrtda) {
        this.subPrtda = subPrtda;
    }

    public int getId_concepto() {
        return id_concepto;
    }

    public void setId_concepto(int id_concepto) {
        this.id_concepto = id_concepto;
    }

    public int getNumConcepto() {
        return numConcepto;
    }

    public void setNumConcepto(int numConcepto) {
        this.numConcepto = numConcepto;
    }

    public String getCodConcepto() {
        return codConcepto;
    }

    public void setCodConcepto(String codConcepto) {
        this.codConcepto = codConcepto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getCantCtrl() {
        return cantCtrl;
    }

    public void setCantCtrl(BigDecimal cantCtrl) {
        this.cantCtrl = cantCtrl;
    }

    public BigDecimal getpUnitario() {
        return pUnitario;
    }

    public void setpUnitario(BigDecimal pUnitario) {
        this.pUnitario = pUnitario;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getCadCant() {
        return cadCant;
    }

    public void setCadCant(String cadCant) {
        this.cadCant = cadCant;
    }

    public String getCadPrecio() {
        return cadPrecio;
    }

    public void setCadPrecio(String cadPrecio) {
        this.cadPrecio = cadPrecio;
    }

    public String getCadImp() {
        return cadImp;
    }

    public void setCadImp(String cadImp) {
        this.cadImp = cadImp;
    }

    public BigDecimal getSumaImporte() {
        return sumaImporte;
    }

    public void setSumaImporte(BigDecimal sumaImporte) {
        this.sumaImporte = sumaImporte;
    }

    public int getId_partida() {
        return id_partida;
    }

    public void setId_partida(int id_partida) {
        this.id_partida = id_partida;
    }

    public String getDescPartida() {
        return descPartida;
    }

    public void setDescPartida(String descPartida) {
        this.descPartida = descPartida;
    }

    /**
     * @return the insumos
     */
    public List<InsumoBean> getInsumos() {
        return insumos;
    }

    /**
     * @param insumos the insumos to set
     */
    public void setInsumos(List<InsumoBean> insumos) {
        this.insumos = insumos;
    }
    
}
