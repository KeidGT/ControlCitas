/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

/**
 *
 * @author Alvin
 */
public class UsuarioBean {
    private static int codiUsua=1;

    public static int getCodiUsua() {
        return codiUsua;
    }

    public static void setCodiUsua(int codiUsua) {
        UsuarioBean.codiUsua = codiUsua;
    }
    
}
