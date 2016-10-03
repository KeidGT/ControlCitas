/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.ejb.AlumnovisitanteFacadeLocal;
import com.sv.udb.modelo.AlumWS;
import com.sv.udb.modelo.Alumnovisitante;
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
    private String Visi;
    private List<AlumWS> listAlum;
    
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

    public String getVisi() {
        return Visi;
    }

    public void setVisi(String Visi) {
        this.Visi = Visi;
    }

    public AlumnovisitanteFacadeLocal getFCDEAlumVisi() {
        return FCDEAlumVisi;
    }

    public void setFCDEAlumVisi(AlumnovisitanteFacadeLocal FCDEAlumVisi) {
        this.FCDEAlumVisi = FCDEAlumVisi;
    }

    public List<AlumWS> getListAlum() {
        return listAlum;
    }

    public void setListAlum(List<AlumWS> listAlum) {
        this.listAlum = listAlum;
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
    
    public void setVisi(){
        try{
            RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
            Visitante visi = new Visitante();
            visi.setCodiVisi(Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codiVisi")));
            this.objeAlumVisi.setCodiVisi(visi);
            this.Visi = String.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nombVisi"));
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Consultado a " + this.Visi + "')");
        }catch(Exception e){
             e.printStackTrace();
        }
    }
    private void geneListAlum(){
        try{
            this.listAlum.add(new AlumWS("Kevin", "Guevara Tolentino", "20130670", "contra123"));
            this.listAlum.add(new AlumWS("Kevin", "Guevara Tolentino", "20130670", "contra123"));
            this.listAlum.add(new AlumWS("Kevin", "Guevara Tolentino", "20130670", "contra123"));
            this.listAlum.add(new AlumWS("Kevin", "Guevara Tolentino", "20130670", "contra123"));
            this.listAlum.add(new AlumWS("Kevin", "Guevara Tolentino", "20130670", "contra123"));
        }catch(Exception e){
             e.printStackTrace();
        }
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
