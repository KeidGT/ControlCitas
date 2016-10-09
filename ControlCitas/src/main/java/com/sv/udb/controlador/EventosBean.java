/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.ejb.EventoFacadeLocal;
import com.sv.udb.modelo.Evento;
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

/**
 *
 * @author REGISTRO
 */
@Named(value = "eventosBean")
@ViewScoped
public class EventosBean implements Serializable{
   
    
    public EventosBean() {
        
    }
     
    @EJB
    private EventoFacadeLocal FCDEEven;    
    private Evento objeEven;
    private List<Evento> listEven;
    private boolean guardar;
    
    public Evento getObjeEven() {
        return objeEven;
    }

    public void setObjeEven(Evento objeEven) {
        this.objeEven = objeEven;
    }

    public List<Evento> getListEven() {
        return listEven;
    }

    public boolean isGuardar() {
        return guardar;
    }
    
    @PostConstruct
    public void init()
    {
        this.limpForm();
        this.consTodo();
    }
    
    public void limpForm()
    {
        this.objeEven = new Evento();
        this.guardar = true;        
    }
    
    public void consTodo()
    {
        try
        {
            this.listEven = FCDEEven.findAll();
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
            this.objeEven = FCDEEven.find(codi);
            this.guardar = false;
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Consultado a " +this.objeEven.getNombEven()+ "')");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al consultar')");
        }
    }
    
    public void guar()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {            
            if(validar())
            {
                FCDEEven.create(this.objeEven);
                this.listEven.add(this.objeEven);
                ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos guardados')");
                this.limpForm();
            }
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al guardar')");
        }
    }
    
    public void modi()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            if(validar())
            {
                this.listEven.remove(this.objeEven); //Limpia el objeto viejo
                FCDEEven.edit(this.objeEven);
                this.listEven.add(this.objeEven); //Agrega el objeto modificado
                ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Modificados')");
                this.limpForm();
            }
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al modificar ')");
        }
    }
    
    private boolean validar()
    {
        RequestContext ctx = RequestContext.getCurrentInstance();
        DateFormat formatter = new SimpleDateFormat("hh:mm a");
        try
        {
            if(this.objeEven.getFechaFinaEven().after(this.objeEven.getFechaInicEven()) /*|| (this.objeEven.getFechaFinaEven().equals(this.objeEven.getFechaInicEven()).after(formatter.parse(this.objeEven.getHoraInicEven())))*/)
            {
                return true;
            }
            else
            {
                ctx.execute("setMessage('MESS_INFO', 'Atención', 'Fecha Final no debe ser antes de la Inicial'); INIT_OBJE_MODA();");
                return false;
            }
        }
        catch(Exception err)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Horas no válidas')");
            return false;
        }
    }
    
    public void elim()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            FCDEEven.remove(this.objeEven);
            this.listEven.remove(this.objeEven);
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Eliminados')");
            this.limpForm();
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al eliminar')");
        }
    }
}
