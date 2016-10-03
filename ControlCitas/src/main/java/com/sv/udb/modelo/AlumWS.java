/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.modelo;

/**
 *
 * @author Kevin
 */
public class AlumWS {
    private String nombAlum;
    private String apelAlum;
    private String codiAlum;
    private String contAlum;

    public AlumWS() {
    }

    public AlumWS(String nombAlum, String apelAlum, String codiAlum, String contAlum) {
        this.nombAlum = nombAlum;
        this.apelAlum = apelAlum;
        this.codiAlum = codiAlum;
        this.contAlum = contAlum;
    }

    public String getNombAlum() {
        return nombAlum;
    }

    public void setNombAlum(String nombAlum) {
        this.nombAlum = nombAlum;
    }

    public String getApelAlum() {
        return apelAlum;
    }

    public void setApelAlum(String apelAlum) {
        this.apelAlum = apelAlum;
    }

    public String getCodiAlum() {
        return codiAlum;
    }

    public void setCodiAlum(String codiAlum) {
        this.codiAlum = codiAlum;
    }

    public String getContAlum() {
        return contAlum;
    }

    public void setContAlum(String contAlum) {
        this.contAlum = contAlum;
    }
    
    
    
}
