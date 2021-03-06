 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.ejb.HorariodisponibleFacadeLocal;
import com.sv.udb.modelo.Horariodisponible;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;
import org.apache.log4j.Logger;

/**
 *
 * @author Sistema de citas
 * @version 1.0 13 de Octubre de 2016
 */
@Named(value = "horarioDisponibleBean")
@ViewScoped
public class HorarioDisponibleBean implements Serializable{
   
    
    public HorarioDisponibleBean() {
        
    }
     
    @EJB
    private HorariodisponibleFacadeLocal FCDEHoraDisp;    
    private Horariodisponible objeHoraDisp;
    private List<Horariodisponible> listHoraDisp;
    private List<Horariodisponible> listHoraDispTodo;
    private boolean guardar;
    private Logger logger = Logger.getLogger(HorarioDisponibleBean.class);

    public List<Horariodisponible> getListHoraDispTodo() {
        return listHoraDispTodo;
    }

    public void setListHoraDispTodo(List<Horariodisponible> listHoraDispTodo) {
        this.listHoraDispTodo = listHoraDispTodo;
    }

    public Horariodisponible getObjeHoraDisp() {
        return objeHoraDisp;
    }

    public void setObjeHoraDisp(Horariodisponible objeHoraDisp) {
        this.objeHoraDisp = objeHoraDisp;
    }

    public List<Horariodisponible> getListHoraDisp() {
        return listHoraDisp;
    }

    public boolean isGuardar() {
        return guardar;
    }
    
    @PostConstruct
    public void init()
    {
        this.limpForm();
        this.consPorUsua();
        this.consTodo();
        consTodo();
        consPorUsua();
    }
    //Limpiar el formulario
    public void limpForm()
    {
        this.objeHoraDisp = new Horariodisponible();
        this.guardar = true; 
        
    }
    
    public void consPorUsua()
    {
        try
        {
            this.listHoraDisp = FCDEHoraDisp.findByCodiUsua(LoginBean.getCodiUsuaSesion());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void consTodo()
    {
        try
        {
            this.listHoraDispTodo = FCDEHoraDisp.findAll();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void cons()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        int codi = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codiObjePara"));
        try
        {
            this.objeHoraDisp = FCDEHoraDisp.find(codi);
            this.guardar = false;
            logger.info("Se ha consultado un horario: " + this.objeHoraDisp.getDiaHoraDisp()+" "+this.objeHoraDisp.getHoraInicHoraDisp() + " "+this.objeHoraDisp.getAnioHoraDisp());
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Consultado a " + 
                    String.format("%s %s", this.objeHoraDisp.getDiaHoraDisp(), this.objeHoraDisp.getHoraInicHoraDisp()) + "')");
        }
        catch(Exception ex)
        {
            logger.error("Error al consultar registro",ex);
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al consultar')");
        }
    }
     /**
     * Método que guarda un objeto del tipo horario disponible en la base de datos
     * @exception Error al realizar la operacion         
     * @since incluido desde la version 1.0
     */ 
    public void guar()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            if(validar()){
                this.objeHoraDisp.setCodiUsua(LoginBean.getCodiUsuaSesion());
                objeHoraDisp.setEstaHoraDisp(1);
                FCDEHoraDisp.create(this.objeHoraDisp);
                logger.info("Se ha guardado un horario: " + this.objeHoraDisp.getDiaHoraDisp() + " " + this.objeHoraDisp.getHoraInicHoraDisp() + " " + this.objeHoraDisp.getAnioHoraDisp() );
                ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos guardados')");
                this.listHoraDisp.add(objeHoraDisp);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            logger.error("Error al guardar: ", ex);
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al guardar')");
        }
    }
     /**
     * Método que modifica un objeto del tipo horario disponible en la base de datos
     * objeHoraDisp Objeto del tipo horario disponible
     * @exception Error al realizar la operacion         
     * @since incluido desde la version 1.0
     */     
    public void modi()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            if(validar()){
                this.objeHoraDisp.setCodiUsua(LoginBean.getCodiUsuaSesion());
                this.listHoraDisp.remove(this.objeHoraDisp); //Limpia el objeto viejo
                FCDEHoraDisp.edit(this.objeHoraDisp);
                logger.info("Se ha modificado un horario: " + this.objeHoraDisp.getDiaHoraDisp() + " " + this.objeHoraDisp.getHoraInicHoraDisp() + " a " + this.objeHoraDisp.getHoraFinaHoraDisp() + " " + this.objeHoraDisp.getAnioHoraDisp() );
                ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Modificados')");
                this.listHoraDisp.add(objeHoraDisp);
            }
        }
        catch(Exception ex)
        {
            logger.error("Error al modificar: ",ex);
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al modificar ')");
        }
    }
     /**
     * Método que elimina un objeto del tipo horario disponible en la base de datos
     *  Objeto del tipo horario disponible
     * @exception Error al realizar la operacion         
     * @since incluido desde la version 1.0
     */ 
    public void elim()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            objeHoraDisp.setEstaHoraDisp(0);
            FCDEHoraDisp.edit(this.objeHoraDisp);
            this.listHoraDisp.remove(this.objeHoraDisp);
            logger.info("Se ha eliminado un horario: " + this.objeHoraDisp.getDiaHoraDisp() + " " + this.objeHoraDisp.getHoraInicHoraDisp() + " " + this.objeHoraDisp.getHoraFinaHoraDisp() + " " + this.objeHoraDisp.getAnioHoraDisp() );
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Eliminados')");
        }
        catch(Exception ex)
        {
            logger.error("Error al eliminar: ", ex);
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al eliminar')");
        }
    }
         /**
     * Método que valida la fecha y hora del tipo horario disponible en la base de datos
     * objeHoraDisp Objeto del tipo horario disponible para obtener fechas
     * @exception Error al realizar la operacion         
     * @since incluido desde la version 1.0
     */ 
    private boolean validar(){
        boolean val = false;
        RequestContext ctx = RequestContext.getCurrentInstance();
        DateFormat formatter = new SimpleDateFormat("hh:mm a");
        try
        {
          
            if(formatter.parse(this.objeHoraDisp.getHoraFinaHoraDisp()).after(formatter.parse(this.objeHoraDisp.getHoraInicHoraDisp()))){
                val = true;
            }
            else
            {
                ctx.execute("setMessage('MESS_INFO', 'Atención', 'La hora Final no puede ser antes de la Inicial');");
            }
        }
        catch(Exception err)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Horas no válidas')");
            return false;
        }
        return val;
    }
}
