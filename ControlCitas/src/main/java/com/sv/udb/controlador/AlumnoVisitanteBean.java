/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.ejb.AlumnovisitanteFacadeLocal;
import com.sv.udb.modelo.Alumnovisitante;
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
@Named(value = "alumnoVisitanteBean")
@ViewScoped
public class AlumnoVisitanteBean implements Serializable{
   
    
    public AlumnoVisitanteBean() {
        
    }
     
    @EJB
    private AlumnovisitanteFacadeLocal FCDEAlumVisi;    
    private Alumnovisitante objeAlumVisi;
    private List<Alumnovisitante> listAlumVisi;
    private boolean guardar;
    
    public Alumnovisitante getObjeAlumVisi() {
        return objeAlumVisi;
    }

    public void setObjeAlumVisi(Alumnovisitante objeAlumVisi) {
        this.objeAlumVisi = objeAlumVisi;
    }

    public List<Alumnovisitante> getListAlumVisi() {
        return listAlumVisi;
    }

    public boolean isGuardar() {
        return guardar;
    }

    public AlumnovisitanteFacadeLocal getFCDEAlumVisi() {
        return FCDEAlumVisi;
    }

    public void setFCDEAlumVisi(AlumnovisitanteFacadeLocal FCDEAlumVisi) {
        this.FCDEAlumVisi = FCDEAlumVisi;
    }

    
    
    @PostConstruct
    public void init()
    {
        this.limpForm();
        this.consTodo();
    }
    
    public void limpForm()
    {
        this.objeAlumVisi = new Alumnovisitante();
        this.guardar = true;        
    }
    
    public void consTodo()
    {
        try
        {
            this.listAlumVisi = FCDEAlumVisi.findAll();
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
            this.objeAlumVisi = FCDEAlumVisi.find(codi);
            this.guardar = false;
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Consultado a " + 
                    String.format("%s %s", this.objeAlumVisi.getPareAlumVisi(), this.objeAlumVisi.getCarnAlum()) + "')");
            //por alguna razón, al consultar con cambia el select... asi que se hace manualmente....
            ctx.execute("selectedItem("+this.objeAlumVisi.getPareAlumVisi()+")");
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
            FCDEAlumVisi.create(this.objeAlumVisi);
            this.listAlumVisi.add(this.objeAlumVisi);
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos guardados')");
            this.limpForm();
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
            this.listAlumVisi.remove(this.objeAlumVisi); //Limpia el objeto viejo
            FCDEAlumVisi.edit(this.objeAlumVisi);
            this.listAlumVisi.add(this.objeAlumVisi); //Agrega el objeto modificado
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Modificados')");
            this.limpForm();
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
            FCDEAlumVisi.remove(this.objeAlumVisi);
            this.listAlumVisi.remove(this.objeAlumVisi);
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Eliminados')");
            this.limpForm();
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al eliminar')");
        }
    }
}
