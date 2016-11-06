/*
 * Controlador eventos
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
import javax.inject.Inject;
import org.primefaces.context.RequestContext;
import org.apache.log4j.Logger;

/**
 *
 * @author Sistema de citas
 * @version 1.0 13 de Octubre de 2016
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
    
    private String cadeText="";

    public String getCadeText() {
        return cadeText;
    }

    public void setCadeText(String cadeText) {
        this.cadeText = cadeText;
    }
    
    
    
    //variables para registro de nuevo visitante
    @Inject
    private GlobalAppBean globalAppBean;
    private AlumnoVisitanteBean alumVisiBean;
    private Logger logger = Logger.getLogger(VisitantesBean.class);
    public Visitante getObjeVisi() {
        return objeVisi;
    }
    

    public void setObjeVisi(Visitante objeVisi) {
        this.objeVisi = objeVisi;
    }

    public List<Visitante> getListVisi() {
        consTodo();
        return listVisi;
    }

    public boolean isGuardar() {
        return guardar;
    }

    public AlumnoVisitanteBean getAlumVisiBean() {
        this.alumVisiBean = new AlumnoVisitanteBean();
        return alumVisiBean;
    }

    public void setAlumVisiBean(AlumnoVisitanteBean alumVisiBean) {
        this.alumVisiBean = alumVisiBean;
    }

    
    
    
    @PostConstruct
    public void init()
    {
        this.limpForm();
        
        
    }
 //Limpiando el formulario   
    public void limpForm()
    {
        this.objeVisi = new Visitante();
        this.guardar = true;   
        
    }
    
    
    public void consTodo()
    {
        try
        {
            this.listVisi = FCDEVisi.findByAllFields(cadeText, 20, 1);
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
            logger.info("Se ha consultado el visitante: " + this.objeVisi.getNombVisi());
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Consultado a " + 
                    String.format("%s %s", this.objeVisi.getNombVisi(), this.objeVisi.getApelVisi()) + "')");
        }
        catch(Exception ex)
        {
            logger.error("Error al consultar registro",ex);
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al consultar')");
        }
        finally
        {
            
        }
    }
     /**
     * Método que guarda un objeto del tipo visitante en la base de datos
     *  Objeto del tipo evento
     * @exception Error al realizar la operacion         
     * @since incluido desde la version 1.0
     */       
    public void guar()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            FCDEVisi.create(this.objeVisi);
            this.listVisi.add(this.objeVisi);
            logger.info("Se ha guardado un visitante: " + this.objeVisi.getNombVisi());
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos guardados')");
            this.limpForm();
        }
        catch(Exception ex)
        {
            logger.error("Error al guardar: ", ex);
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al guardar')");
        }
    }
      /**
     * Método que modifica un objeto del tipo visitante en la base de datos
     * Objeto del tipo evento
     * @exception Error al realizar la operacion         
     * @since incluido desde la version 1.0
     */       
    public void modi()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            this.listVisi.remove(this.objeVisi); //Limpia el objeto viejo
            FCDEVisi.edit(this.objeVisi);
            this.listVisi.add(this.objeVisi); //Agrega el objeto modificado
            logger.info("Se ha modificado un visitante: "+this.objeVisi.getNombVisi());
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Modificados')");
            this.limpForm();
        }
        catch(Exception ex)
        {
            logger.error("Error al modificar: ",ex);
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al modificar ')");
        }
    }
     /**
     * Método que elimina un objeto del tipo visitante en la base de datos
     *  Objeto del tipo evento
     * @exception Error al realizar la operacion         
     * @since incluido desde la version 1.0
     */         
    public void elim()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            FCDEVisi.remove(this.objeVisi);
            this.listVisi.remove(this.objeVisi);
            logger.info("Se ha eliminado un visitante: " + this.objeVisi.getNombVisi());
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Eliminados')");
            this.limpForm();
        }
        catch(Exception ex)
        {
            logger.error("Error al eliminar: ", ex);
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al eliminar')");
        }
    }
}
