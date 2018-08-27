/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;


public class PartidaCatalogo {
    
    private int id_partida;
    private String partida;
    private String cvePartida;

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
     * @return the cvePartida
     */
    public String getCvePartida() {
        return cvePartida;
    }

    /**
     * @param cvePartida the cvePartida to set
     */
    public void setCvePartida(String cvePartida) {
        this.cvePartida = cvePartida;
    }

    @Override
    public String toString() {
        return partida + "-" + cvePartida ;
    }
    
}
