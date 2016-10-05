/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

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
import javax.inject.Inject;
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
    private String pass;
    
    //variables para registro de nuevo visitante
    @Inject
    private GlobalAppBean globalAppBean;
    private String carnAlum;
    private String passAlum;
    private AlumnoVisitanteBean alumVisiBean;
    private Alumnovisitante objeAlumVisi;
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

    public String getCarnAlum() {
        return carnAlum;
    }

    public void setCarnAlum(String carnAlum) {
        this.carnAlum = carnAlum;
    }

    public String getPassAlum() {
        return passAlum;
    }

    public void setPassAlum(String passAlum) {
        this.passAlum = passAlum;
    }

    public AlumnoVisitanteBean getAlumVisiBean() {
        return alumVisiBean;
    }

    public void setAlumVisiBean(AlumnoVisitanteBean alumVisiBean) {
        this.alumVisiBean = alumVisiBean;
    }

    public Alumnovisitante getObjeAlumVisi() {
        return objeAlumVisi;
    }

    public void setObjeAlumVisi(Alumnovisitante objeAlumVisi) {
        this.objeAlumVisi = objeAlumVisi;
    }
    
    
    
    @PostConstruct
    public void init()
    {
        this.limpForm();
        this.consTodo();
        this.alumVisiBean = new AlumnoVisitanteBean();
        this.objeAlumVisi = new Alumnovisitante();
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
            this.pass = objeVisi.getPassVisi();
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
            this.listVisi.remove(this.objeVisi); //Limpia el objeto viejo
            if(objeVisi.getPassVisi().trim().equals(""))objeVisi.setPassVisi(this.pass);//por si la contraseña no se modificó
            FCDEVisi.edit(this.objeVisi);
            this.listVisi.add(this.objeVisi); //Agrega el objeto modificado
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
            FCDEVisi.remove(this.objeVisi);
            this.listVisi.remove(this.objeVisi);
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
            if(true){//aqui deveriamos comparar los datos ingresados, con los que existen en el web service
                //Registramos Visitante
                    this.objeVisi.setTipoVisi(2);
                    FCDEVisi.create(this.objeVisi);
                    this.listVisi.add(this.objeVisi);
            }else{
                ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Información Incorrecta')");
            }
        }catch(Exception e){
                ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al intenar registrarse')");
                System.out.println("ERROR AL REGISTRARSE");
                e.printStackTrace();
            }
        finally
        {
            /*
            try{
                //asignamos alumno que se ingresó, al visitante
                objeAlumVisi.setCarnAlum(carnAlum);
                objeVisi.setCodiVisi(1);
                objeAlumVisi.setCodiVisi(objeVisi);
                objeAlumVisi.setEstaAlumVisi(1);
                alumVisiBean.setObjeAlumVisi(objeAlumVisi);
                alumVisiBean.guar();
                ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Registro Realizado')");
                //redireccionamos
                //facsCtxt.getExternalContext().redirect(globalAppBean.getUrl("index.xhtml"));
            }catch(Exception e){
                System.out.println("ERROR AL ASIGNAR ALUMNO");
                e.printStackTrace();
            }
            */
            
        }
    }
}
