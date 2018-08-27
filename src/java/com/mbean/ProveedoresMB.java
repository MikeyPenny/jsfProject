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
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;


@Named
@ViewScoped
public class ProveedoresMB implements Serializable {

    @ManagedProperty(value = "#{formProveedorMB}")
    private FormProveedorMB formProveedor;
    
    private Integer idProveedor;
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
    private int tipoProv;
    private List<TipoProveedor> listProv;
    
    @PostConstruct
    public void init() {
        
        ConexionBD c = new ConexionBD();
        ProveedoresBS bs = new ProveedoresBS(c.getConexion());
        setListProv(bs.listarTipoProv());
        
    }
    
    public void nuevoProveedor() {
        ConexionBD c = new ConexionBD();
        ProveedoresBS pbs = new ProveedoresBS(c.getConexion());
        if(!pbs.buscarDuplicado(rfc)) {
            Proveedor p = new Proveedor();
            p.setCiudad(getCiudad());
            //System.out.println(getCiudad());
            p.setClabe(getClabe());
            p.setDiasCredito(getDiasCredito());
            p.setDirFiscal(getDirFiscal());
            p.setEmail(getEmail());
            //System.out.println(getEmail());
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
            p.setTipo(getTipoProv());
            c = new ConexionBD();
            pbs = new ProveedoresBS(c.getConexion());
            int idPro = pbs.insertarProveedor(p);
            if (idPro != 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OPERACIÓN CORRECTA ", "Se ha insertado Correctamente el Proveedor. "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "No se puede procesar la petición intente más tarde. "));
            }
        }else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "El RFC ya existe, favor de revisar."));
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

    public String editarProveedor(int idProveedor) {
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        ProveedoresBS p = new ProveedoresBS(con);
        Proveedor proveedor = p.listaProveedorPorID(idProveedor);
        System.out.println("Pro: " + proveedor);

        formProveedor.setCiudad(proveedor.getCiudad());
        formProveedor.setClabe(proveedor.getClabe());
        formProveedor.setDiasCredito(proveedor.getDiasCredito());
        formProveedor.setDirFiscal(proveedor.getDirFiscal());
        formProveedor.setEmail(proveedor.getEmail());
        formProveedor.setEstatus(proveedor.getEstatus());
        formProveedor.setFax(proveedor.getFax());
        formProveedor.setIdProveedor(proveedor.getIdProveedor());
        formProveedor.setNoAba(proveedor.getNoAba());
        formProveedor.setNoCuenta(proveedor.getNoCuenta());
        formProveedor.setNoCuentaInterbancaria(proveedor.getNoCuentaInterbancaria());
        formProveedor.setNombreBanco(proveedor.getNombreBanco());
        formProveedor.setNombreContacto(proveedor.getNombreContacto());
        formProveedor.setNumNomPlaza(proveedor.getNumNomPlaza());
        formProveedor.setNumNomSucursal(proveedor.getNumNomSucursal());
        formProveedor.setRazonSocial(proveedor.getRazonSocial());
        formProveedor.setRfc(proveedor.getRfc().toUpperCase());
        formProveedor.setTelefono(proveedor.getTelefono());

        return "editarProveedor";

    }

    public void actualizar() {
        System.out.println("Entro-.......");
        System.out.println("desde MB id: " + formProveedor.getIdProveedor());

        System.out.println("desde MB RFC: " + formProveedor.getRfc());
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        ProveedoresBS pbs = new ProveedoresBS(con);
        Proveedor p = new Proveedor();
        p.setCiudad(formProveedor.getCiudad());
        p.setIdProveedor(formProveedor.getIdProveedor());
        p.setClabe(formProveedor.getClabe());
        p.setDiasCredito(formProveedor.getDiasCredito());
        p.setDirFiscal(formProveedor.getDirFiscal());
        p.setEmail(formProveedor.getEmail());
        p.setEstatus(1);
        p.setFax(formProveedor.getFax());
        p.setNoAba(formProveedor.getNoAba());
        p.setNoCuenta(formProveedor.getNoCuenta());
        p.setNoCuentaInterbancaria(formProveedor.getNoCuentaInterbancaria());
        p.setNombreBanco(formProveedor.getNombreBanco());
        p.setNombreContacto(formProveedor.getNombreContacto());
        p.setNumNomPlaza(formProveedor.getNumNomPlaza());
        p.setNumNomSucursal(formProveedor.getNumNomSucursal());
        p.setRazonSocial(formProveedor.getRazonSocial());
        p.setRfc(formProveedor.getRfc());
        p.setTelefono(formProveedor.getTelefono());

        System.out.println("idMNPROV: " + p.getIdProveedor());
        System.out.println("RazZOO: " + p.getRazonSocial());
        boolean res = pbs.actualizarProveedor(p);
        if (res) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OPERACIÓN CORRECTA ", "Se ha Actualizado Correctamente el Proveedor."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "No se puede procesar la petición intente más tarde. "));
        }
    }

    /**
     * @return the formProveedor
     */
    public FormProveedorMB getFormProveedor() {
        return formProveedor;
    }

    /**
     * @param formProveedor the formProveedor to set
     */
    public void setFormProveedor(FormProveedorMB formProveedor) {
        this.formProveedor = formProveedor;
    }

    /**
     * @return the idProveedor
     */
    public Integer getIdProveedor() {
        return idProveedor;
    }

    /**
     * @param idProveedor the idProveedor to set
     */
    public void setIdProveedor(Integer idProveedor) {
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
     * @return the tipoProv
     */
    public int getTipoProv() {
        return tipoProv;
    }

    /**
     * @param tipoProv the tipoProv to set
     */
    public void setTipoProv(int tipoProv) {
        this.tipoProv = tipoProv;
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
