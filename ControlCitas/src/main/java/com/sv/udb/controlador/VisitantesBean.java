/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.ejb.VisitanteFacadeLocal;
import com.sv.udb.modelo.Visitante;
import java.io.Serializable;
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
@Named(value = "visitantesBean")
@ViewScoped
public class VisitantesBean implements Serializable{
   
    
    public VisitantesBean() {
        
    }
     
    @EJB
    private VisitanteFacadeLocal FCDEVisi;    
    private Visitante objeVisi;
    private List<Visitante> listVisi;
    private boolean guardar;
    
    public Visitante getObjeVisi() {
        return objeVisi;
    }

    public void setObjeVisi(Visitante objeVisi) {
        this.objeVisi = objeVisi;
    }

    public List<Visitante> getListVisi() {
        return listVisi;
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
        this.objeVisi = new Visitante();
        this.guardar = true;        
    }
    
    public void consTodo()
    {
        try
        {
            this.listVisi = FCDEVisi.findAll();
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
            this.objeVisi = FCDEVisi.find(codi);
            this.guardar = false;
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Consultado a " + 
                    String.format("%s %s", this.objeVisi.getNombVisi(), this.objeVisi.getApelVisi()) + "')");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al consultar')");
        }
        finally
        {
            
        }
    }
    
    public void guar()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            FCDEVisi.create(this.objeVisi);
            this.listVisi.add(this.objeVisi);
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos guardados')");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al guardar')");
        }
        finally
        {
            
        }
    }
    
    public void modi()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            this.listVisi.remove(this.objeVisi); //Limpia el objeto viejo
            FCDEVisi.edit(this.objeVisi);
            this.listVisi.add(this.objeVisi); //Agrega el objeto modificado
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Modificados')");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al modificar ')");
        }
        finally
        {
            
        }
    }
    
    public void elim()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            FCDEVisi.remove(this.objeVisi);
            this.listVisi.remove(this.objeVisi);
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Eliminados')");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al eliminar')");
        }
        finally
        {
            
        }
    }
}
