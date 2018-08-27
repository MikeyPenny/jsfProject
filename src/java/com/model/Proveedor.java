/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Mickey
 */
public class Proveedor implements Serializable {
    
    private int idProveedor;
    private String razonSocial;
    private String dirFiscal;
    private String rfc;
    private String telefono;
    private String email;
    private int diasCredito;
    private String fax;
    private String nombreContacto;
    private String nombreBanco;
    private String numNomPlaza;
    private String noCuenta;
    private String numNomSucursal;
    private String noCuentaInterbancaria;
    private String clabe;
    private String ciudad;
    private String noAba;
    private int estatus;
    private int tipo;
    private Set ordenCompras = new HashSet(0);
     
    

    /**
     * @return the idProveedor
     */
    public int getIdProveedor() {
        return idProveedor;
    }

    /**
     * @param idProveedor the idProveedor to set
     */
    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    /**
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * @param razonSocial the razonSocial to set
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * @return the dirFiscal
     */
    public String getDirFiscal() {
        return dirFiscal;
    }

    /**
     * @param dirFiscal the dirFiscal to set
     */
    public void setDirFiscal(String dirFiscal) {
        this.dirFiscal = dirFiscal;
    }

    /**
     * @return the rfc
     */
    public String getRfc() {
        return rfc;
    }

    /**
     * @param rfc the rfc to set
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the diasCredito
     */
    public int getDiasCredito() {
        return diasCredito;
    }

    /**
     * @param diasCredito the diasCredito to set
     */
    public void setDiasCredito(int diasCredito) {
        this.diasCredito = diasCredito;
    }

    /**
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * @return the nombreContacto
     */
    public String getNombreContacto() {
        return nombreContacto;
    }

    /**
     * @param nombreContacto the nombreContacto to set
     */
    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    /**
     * @return the nombreBanco
     */
    public String getNombreBanco() {
        return nombreBanco;
    }

    /**
     * @param nombreBanco the nombreBanco to set
     */
    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    /**
     * @return the numNomPlaza
     */
    public String getNumNomPlaza() {
        return numNomPlaza;
    }

    /**
     * @param numNomPlaza the numNomPlaza to set
     */
    public void setNumNomPlaza(String numNomPlaza) {
        this.numNomPlaza = numNomPlaza;
    }

    /**
     * @return the noCuenta
     */
    public String getNoCuenta() {
        return noCuenta;
    }

    /**
     * @param noCuenta the noCuenta to set
     */
    public void setNoCuenta(String noCuenta) {
        this.noCuenta = noCuenta;
    }

    /**
     * @return the numNomSucursal
     */
    public String getNumNomSucursal() {
        return numNomSucursal;
    }

    /**
     * @param numNomSucursal the numNomSucursal to set
     */
    public void setNumNomSucursal(String numNomSucursal) {
        this.numNomSucursal = numNomSucursal;
    }

    /**
     * @return the noCuentaInterbancaria
     */
    public String getNoCuentaInterbancaria() {
        return noCuentaInterbancaria;
    }

    /**
     * @param noCuentaInterbancaria the noCuentaInterbancaria to set
     */
    public void setNoCuentaInterbancaria(String noCuentaInterbancaria) {
        this.noCuentaInterbancaria = noCuentaInterbancaria;
    }

    /**
     * @return the clabe
     */
    public String getClabe() {
        return clabe;
    }

    /**
     * @param clabe the clabe to set
     */
    public void setClabe(String clabe) {
        this.clabe = clabe;
    }

    /**
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @param ciudad the ciudad to set
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * @return the noAba
     */
    public String getNoAba() {
        return noAba;
    }

    /**
     * @param noAba the noAba to set
     */
    public void setNoAba(String noAba) {
        this.noAba = noAba;
    }

    /**
     * @return the estatus
     */
    public int getEstatus() {
        return estatus;
    }

    /**
     * @param estatus the estatus to set
     */
    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    /**
     * @return the tipo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the ordenCompras
     */
    public Set getOrdenCompras() {
        return ordenCompras;
    }

    /**
     * @param ordenCompras the ordenCompras to set
     */
    public void setOrdenCompras(Set ordenCompras) {
        this.ordenCompras = ordenCompras;
    }
    
}
