/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.ejb.AlumnovisitanteFacadeLocal;
import com.sv.udb.ejb.VisitanteFacadeLocal;
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
    
    // Variables para registrarse como visitante representante alumno
    @EJB
    private VisitanteFacadeLocal FCDEVisi;    
    private Visitante objeVisi;
    private boolean Disabled;
    private List<Alumnovisitante> listAlumVisiCarne;
    
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

    public List<Alumnovisitante> getListAlumVisiCarne() {
        return listAlumVisiCarne;
    }

    public void setListAlumVisiCarne(List<Alumnovisitante> listAlumVisiCarne) {
        this.listAlumVisiCarne = listAlumVisiCarne;
    }

    public Visitante getObjeVisi() {
        return objeVisi;
    }

    public void setObjeVisi(Visitante objeVisi) {
        this.objeVisi = objeVisi;
    }

    public boolean isDisabled() {
        return Disabled;
    }

    public void setDisabled(boolean Disabled) {
        this.Disabled = Disabled;
    }

    
    
    @PostConstruct
    public void init()
    {
        this.limpForm();
        this.consTodo();
        this.consAlumVisi();
        this.objeAlumVisi = new Alumnovisitante();
    }
    
    public void limpForm()
    {
        this.objeAlumVisi = new Alumnovisitante();
        this.guardar = true;   
        this.Disabled = true; 
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
    public void consAlumVisi(){
         try
        {
            this.listAlumVisiCarne = FCDEAlumVisi.findByCarnAlum(LoginBean.getCodiUsuaSesion());
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
            this.Disabled=false;
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
    public void consPorDui()
     {
        RequestContext ctx = RequestContext.getCurrentInstance();
        try
        {   
            Visitante objVis = FCDEVisi.findByDuiVisi(this.objeVisi.getDuiVisi());
            if(objVis != null){
                    if(objVis.getDuiVisi().equals(this.objeVisi.getDuiVisi())){
                        this.objeVisi = objVis;
                        ctx.execute("setMessage('MESS_INFO', 'Atención', 'Visitante Encontrado!')");
                }
            }
            else{
                    this.Disabled = false;
                    String dui = this.objeVisi.getDuiVisi();
                    this.objeVisi = new Visitante();
                    this.objeVisi.setDuiVisi(dui);
                    ctx.execute("setMessage('MESS_INFO', 'Atención', 'Visitante no encontrado, Registrarse por favor!')");
                }
            
            
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Datos No Consultados')");
            ex.printStackTrace();
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
            ex.printStackTrace();
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
    public void regiVisi(){
        RequestContext ctx = RequestContext.getCurrentInstance();
        FacesContext facsCtxt = FacesContext.getCurrentInstance();
        try{
            if(!Disabled){//si aun no está registrado
                //Registramos Visitante
                    this.objeVisi.setTipoVisi(1);
                    FCDEVisi.create(this.objeVisi);
                    //this.listAlumVisiCarne.add(this.objeVisi);
            }else{
                ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Información Incorrecta')");
            }
        }catch(Exception e){
                ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al intenar registrarse')");
                System.out.println("ERROR AL REGISTRARSE");
                e.printStackTrace();
            }
        //ahora asignamos el alumno a ese visitante
            
        try{
            objeAlumVisi.setCarnAlum(String.valueOf(LoginBean.getCodiUsuaSesion()));
            objeAlumVisi.setCodiVisi(objeVisi);
            objeAlumVisi.setEstaAlumVisi(1);
            //alumVisiBean.setObjeAlumVisi(objeAlumVisi);
            //alumVisiBean.guar();
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Registro Realizado')");
            //redireccionamos
            //facsCtxt.getExternalContext().redirect(globalAppBean.getUrl("index.xhtml"));
        }catch(Exception e){
            System.out.println("ERROR AL ASIGNAR ALUMNO");
            e.printStackTrace();
        }
                        
        
    }
}
