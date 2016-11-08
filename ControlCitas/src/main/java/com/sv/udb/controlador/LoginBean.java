/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.utils.ConsultarCodiEmpleadoLogin;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author REGISTRO
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 5074501358281220977L;

    
    @Inject
    private GlobalAppBean globalAppBean; //Bean de aplicación
    
    private boolean loge;
    private String usua;
    private String cont;
    private String imagPerf;
    private int codiUsua;
    private static int codiUsuaSesion=1;//20130732;

    
    
    public static int getCodiUsuaSesion() {
        return codiUsuaSesion;
    }

    public static void setCodiUsuaSesion(int codiUsuaSesion) {
        LoginBean.codiUsuaSesion = codiUsuaSesion;
    }
        
    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }
    
    @PostConstruct
    public void init()
    {
        
    }

    

    public boolean isLoge() {
        return loge;
    }

    public String getUsua() {
        return usua;
    }

    public void setUsua(String usua) {
        this.usua = usua;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getImagPerf() {
        return imagPerf;
    }

    public int getCodiUsua() {
        this.codiUsua=this.codiUsuaSesion;
        return codiUsua;
    }

    public void setCodiUsua(int codiUsua) {
        this.codiUsua = codiUsua;
    }

    
    
    public void creaSess()
    {
        new ConsultarCodiEmpleadoLogin().consultarCodigo("jirafales@ricaldone.edu.sv", "A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3");
        
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        FacesContext facsCtxt = FacesContext.getCurrentInstance();
        try
        {
            this.codiUsuaSesion=this.codiUsua;
            this.loge = false;
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Bienvenido)"); //No se muestra porque redirecciona
            this.loge = true;
            this.imagPerf = "images/userDemo.png";
            facsCtxt.getExternalContext().redirect("poo/index.xhtml");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al logear')");
        }
    }
    
    public void cerrSess()
    {        
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        FacesContext facsCtxt = FacesContext.getCurrentInstance();
        try
        {
            facsCtxt.getExternalContext().invalidateSession();
            facsCtxt.getExternalContext().redirect(globalAppBean.getUrl("login.xhtml")); 
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al finalizar la sesión')");
        }
        finally
        {
            
        }       
    }

}