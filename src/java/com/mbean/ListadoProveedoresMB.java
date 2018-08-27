/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.bean.TipoProveedor;
import com.conexion.ConexionBD;
import com.model.Proveedor;
import com.services.ProveedoresBS;
import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Mickey
 */
@Named("listadoProveedoresMB")
@ViewScoped
public class ListadoProveedoresMB implements Serializable {

    private List<Proveedor> listaProveedores;
    
    private Proveedor provSel;
    private Proveedor provElim;
    
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
    private List<TipoProveedor> listProv;
    
    
    @PostConstruct
    public void init() {
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        ProveedoresBS p = new ProveedoresBS(con);
        listaProveedores = p.listarProveedores();
        c = new ConexionBD();
        ProveedoresBS bs = new ProveedoresBS(c.getConexion());
        setListProv(bs.listarTipoProv());
    }
    
    public void editarProveedor() {
        
        setIdProveedor(provSel.getIdProveedor());
        setRazonSocial(provSel.getRazonSocial());
        setDirFiscal(provSel.getDirFiscal());
        setRfc(provSel.getRfc());
        setTelefono(provSel.getTelefono());
        setEmail(provSel.getEmail());
        setDiasCredito(provSel.getDiasCredito());
        setFax(provSel.getFax());
        setNombreContacto(provSel.getNombreContacto());
        setNombreBanco(provSel.getNombreBanco());
        setNumNomPlaza(provSel.getNumNomPlaza());
        setNoCuenta(provSel.getNoCuenta());
        setNumNomSucursal(provSel.getNumNomSucursal());
        setNoCuentaInterbancaria(provSel.getNoCuentaInterbancaria());
        setClabe(provSel.getClabe());
        setCiudad(provSel.getCiudad());
        setNoAba(provSel.getNoAba());
        setEstatus(provSel.getEstatus());
        setTipo(provSel.getTipo());
        
        

        //System.out.println(getIdProveedor());

    }
    
    public void actualizar() {
        
        //System.out.println(provSel.getIdProveedor());
        //System.out.println(idProveedor);
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        ProveedoresBS pbs = new ProveedoresBS(con);
        Proveedor p = new Proveedor();
        p.setCiudad(getCiudad());
        p.setIdProveedor(getIdProveedor());
        //System.out.println(p.getIdProveedor());
        p.setClabe(getClabe());
        //System.out.println(p.getClabe());
        p.setDiasCredito(getDiasCredito());
        p.setDirFiscal(getDirFiscal());
        p.setEmail(getEmail());
        p.setEstatus(1);
        p.setFax(getFax());
        p.setNoAba(getNoAba());
        p.setNoCuenta(getNoCuenta());
        p.setNoCuentaInterbancaria(getNoCuentaInterbancaria());
        p.setNombreBanco(getNombreBanco());
        p.setNombreContacto(getNombreContacto());
        p.setNumNomPlaza(getNumNomPlaza());
        p.setNumNomSucursal(getNumNomSucursal());
        p.setRazonSocial(getRazonSocial());
        p.setRfc(getRfc());
        p.setTelefono(getTelefono());
        p.setTipo(getTipo());

        
        boolean res = pbs.actualizarProveedor(p);
        if (res) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OPERACIÓN CORRECTA ", "Se ha Actualizado Correctamente el Proveedor."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "No se puede procesar la petición intente más tarde. "));
        }
        
        refresh();
        
    }
    
    public void refresh() {
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context
         .getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse(); 
    }
    
    public void eliminaProveedor() {
        
        System.out.println(provSel.getIdProveedor());
        
        ConexionBD c  = new ConexionBD();
        ProveedoresBS pbs = new ProveedoresBS(c.getConexion());
        pbs.eliminarProveedor(provSel.getIdProveedor());
        
        refresh();
        
    }

    /**
     * @return the listaProveedores
     */
    public List<Proveedor> getListaProveedores() {
        return listaProveedores;
    }

    /**
     * @param listaProveedores the listaProveedores to set
     */
    public void setListaProveedores(List<Proveedor> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

    /**
     * @return the provSel
     */
    public Proveedor getProvSel() {
        return provSel;
    }

    /**
     * @param provSel the provSel to set
     */
    public void setProvSel(Proveedor provSel) {
        this.provSel = provSel;
    }

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
     * @return the provElim
     */
    public Proveedor getProvElim() {
        return provElim;
    }

    /**
     * @param provElim the provElim to set
     */
    public void setProvElim(Proveedor provElim) {
        this.provElim = provElim;
    }

    /**
     * @return the listProv
     */
    public List<TipoProveedor> getListProv() {
        return listProv;
    }

    /**
     * @param listProv the listProv to set
     */
    public void setListProv(List<TipoProveedor> listProv) {
        this.listProv = listProv;
    }

    
    
}
