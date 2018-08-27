/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;


public class FormListDevolucion {
    
    private Integer idDevolucion;
    private String fechaDevolucion;
    private Integer noDevolucion;
    private String nombreProveedor;
    
    public FormListDevolucion() {
    }

    /**
     * @return the idDevolucion
     */
    public Integer getIdDevolucion() {
        return idDevolucion;
    }

    /**
     * @param idDevolucion the idDevolucion to set
     */
    public void setIdDevolucion(Integer idDevolucion) {
        this.idDevolucion = idDevolucion;
    }

    /**
     * @return the fechaDevolucion
     */
    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    /**
     * @param fechaDevolucion the fechaDevolucion to set
     */
    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    /**
     * @return the noDevolucion
     */
    public Integer getNoDevolucion() {
        return noDevolucion;
    }

    /**
     * @param noDevolucion the noDevolucion to set
     */
    public void setNoDevolucion(Integer noDevolucion) {
        this.noDevolucion = noDevolucion;
    }

    /**
     * @return the nombreProveedor
     */
    public String getNombreProveedor() {
        return nombreProveedor;
    }

    /**
     * @param nombreProveedor the nombreProveedor to set
     */
    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }
    
}
