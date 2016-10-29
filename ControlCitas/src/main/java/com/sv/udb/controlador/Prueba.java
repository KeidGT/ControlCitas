/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Kevin
 */
@Named(value = "prueba")
@Dependent
public class Prueba {

    /**
     * Creates a new instance of Prueba
     */
    public Prueba() {
       
    }
   public void prueba(){
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar ahorita =  Calendar.getInstance();
        String ahora = df.format(ahorita.getTime());
        ahorita.setTime(new Date());
        ahorita.add(Calendar.DAY_OF_YEAR, -15);
        String menos15 = df.format(ahorita.getTime());
        ahorita.add(Calendar.DAY_OF_YEAR, +30);
        String mas15 = df.format(ahorita.getTime());
        ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Ahorita: "+ahora+", Menos15: "+menos15+", Mas15: "+mas15+"');");
        
    }
}
