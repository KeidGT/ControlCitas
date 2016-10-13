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

/**
 *
 * @author REGISTRO
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
    }
    
    public void limpForm()
    {
        this.objeHoraDisp = new Horariodisponible();
        this.guardar = true; 
        consTodo();
        consPorUsua();
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
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Consultado a " + 
                    String.format("%s %s", this.objeHoraDisp.getDiaHoraDisp(), this.objeHoraDisp.getHoraInicHoraDisp()) + "')");
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
            if(validar()){
                this.objeHoraDisp.setCodiUsua(LoginBean.getCodiUsuaSesion());
                FCDEHoraDisp.create(this.objeHoraDisp);
                ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos guardados')");
                this.limpForm();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al guardar')");
        }
    }
    
    public void modi()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            if(validar()){
                this.objeHoraDisp.setCodiUsua(LoginBean.getCodiUsuaSesion());
                this.listHoraDisp.remove(this.objeHoraDisp); //Limpia el objeto viejo
                FCDEHoraDisp.edit(this.objeHoraDisp);
                ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Modificados')");
                this.limpForm();
            }
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al modificar ')");
        }
    }
    
    public void elim()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            FCDEHoraDisp.remove(this.objeHoraDisp);
            this.listHoraDisp.remove(this.objeHoraDisp);
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Eliminados')");
            this.limpForm();
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al eliminar')");
        }
    }
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
