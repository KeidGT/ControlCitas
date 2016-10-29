/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.utils;

import com.sv.udb.controlador.*;
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
@Named(value = "customFunction")
@Dependent
public class CustomFunction {

    /**
     * Creates a new instance of Prueba
     */
    public CustomFunction() {
       
    }
   public Date sumarDias(Date fecha, int dias){
       DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
       Calendar now =  Calendar.getInstance();
       now.setTime(fecha);
       now.add(Calendar.DAY_OF_YEAR, dias);
       
       return now.getTime();
   }
}
