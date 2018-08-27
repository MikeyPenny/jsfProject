/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.math.BigDecimal;

/**
 *
 * @author Mickey
 */
public class Contrato {
    
    private int id_contrato;
    private String tipo;
    private String estatusContrato;
    private String contratista;
    private BigDecimal importeContrato;
    private float anticipoPct;
    private BigDecimal anticipoImp;
    private float fondoGtiaPct;
    private BigDecimal fondoGtiaImp;
    private float amortAntcpoPct;
    private BigDecimal amortAntcpoImp;
    private int numContrato;

    /**
     * @return the id_contrato
     */
    public int getId_contrato() {
        return id_contrato;
    }

    /**
     * @param id_contrato the id_contrato to set
     */
    public void setId_contrato(int id_contrato) {
        this.id_contrato = id_contrato;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the estatusContrato
     */
    public String getEstatusContrato() {
        return estatusContrato;
    }

    /**
     * @param estatusContrato the estatusContrato to set
     */
    public void setEstatusContrato(String estatusContrato) {
        this.estatusContrato = estatusContrato;
    }

    /**
     * @return the contratista
     */
    public String getContratista() {
        return contratista;
    }

    /**
     * @param contratista the contratista to set
     */
    public void setContratista(String contratista) {
        this.contratista = contratista;
    }

    /**
     * @return the anticipoPct
     */
    public float getAnticipoPct() {
        return anticipoPct;
    }

    /**
     * @param anticipoPct the anticipoPct to set
     */
    public void setAnticipoPct(float anticipoPct) {
        this.anticipoPct = anticipoPct;
    }

    /**
     * @return the anticipoImp
     */
    public BigDecimal getAnticipoImp() {
        return anticipoImp;
    }

    /**
     * @param anticipoImp the anticipoImp to set
     */
    public void setAnticipoImp(BigDecimal anticipoImp) {
        this.anticipoImp = anticipoImp;
    }

    /**
     * @return the fondoGtiaPct
     */
    public float getFondoGtiaPct() {
        return fondoGtiaPct;
    }

    /**
     * @param fondoGtiaPct the fondoGtiaPct to set
     */
    public void setFondoGtiaPct(float fondoGtiaPct) {
        this.fondoGtiaPct = fondoGtiaPct;
    }

    /**
     * @return the fondoGtiaImp
     */
    public BigDecimal getFondoGtiaImp() {
        return fondoGtiaImp;
    }

    /**
     * @param fondoGtiaImp the fondoGtiaImp to set
     */
    public void setFondoGtiaImp(BigDecimal fondoGtiaImp) {
        this.fondoGtiaImp = fondoGtiaImp;
    }

    /**
     * @return the amortAntcpoPct
     */
    public float getAmortAntcpoPct() {
        return amortAntcpoPct;
    }

    /**
     * @param amortAntcpoPct the amortAntcpoPct to set
     */
    public void setAmortAntcpoPct(float amortAntcpoPct) {
        this.amortAntcpoPct = amortAntcpoPct;
    }

    /**
     * @return the amortAntcpoImp
     */
    public BigDecimal getAmortAntcpoImp() {
        return amortAntcpoImp;
    }

    /**
     * @param amortAntcpoImp the amortAntcpoImp to set
     */
    public void setAmortAntcpoImp(BigDecimal amortAntcpoImp) {
        this.amortAntcpoImp = amortAntcpoImp;
    }

    /**
     * @return the importeContrato
     */
    public BigDecimal getImporteContrato() {
        return importeContrato;
    }

    /**
     * @param importeContrato the importeContrato to set
     */
    public void setImporteContrato(BigDecimal importeContrato) {
        this.importeContrato = importeContrato;
    }

    /**
     * @return the numContrato
     */
    public int getNumContrato() {
        return numContrato;
    }

    /**
     * @param numContrato the numContrato to set
     */
    public void setNumContrato(int numContrato) {
        this.numContrato = numContrato;
    }
    
}
