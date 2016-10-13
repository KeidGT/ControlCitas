/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.ejb.AlumnovisitanteFacadeLocal;
import com.sv.udb.ejb.CambiocitaFacadeLocal;
import com.sv.udb.ejb.CitaFacadeLocal;
import com.sv.udb.ejb.HorariodisponibleFacadeLocal;
import com.sv.udb.ejb.VisitantecitaFacadeLocal;
import com.sv.udb.modelo.Alumnovisitante;
import com.sv.udb.modelo.Cambiocita;
import com.sv.udb.modelo.Cita;
import com.sv.udb.modelo.Horariodisponible;
import com.sv.udb.modelo.Visitante;
import com.sv.udb.modelo.Visitantecita;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@Named(value = "citasBean")
@ViewScoped
public class CitasBean implements Serializable{
   
    
    public CitasBean() {
        
    }
    @EJB
    private VisitantecitaFacadeLocal FCDEVisiCita;
    @EJB
    private CambiocitaFacadeLocal FCDECambCita; 
    @EJB
    private HorariodisponibleFacadeLocal FCDEHoraDisp;
    @EJB
    private CitaFacadeLocal FCDECita;    
    private Cita objeCita;
    private List<Cita> listCita;
    private boolean guardar;
    private Horariodisponible horaSeleCita;
    private List<Horariodisponible> listHoraDisp;
    private Alumnovisitante alumVisiSelec;
    private String motivo;
    private Date fechSoliCita;
    private List<Visitantecita> listVisiCitaAlum;
    private boolean Disable;
    
    public List<Visitantecita> getListVisiCitaAlum() {
        return listVisiCitaAlum;
    }

    public void setListVisiCitaVisi(List<Visitantecita> listVisiCitaAlum) {
        this.listVisiCitaAlum = listVisiCitaAlum;
    }

    public Date getFechSoliCita() {
        return fechSoliCita;
    }

    public void setFechSoliCita(Date fechSoliCita) {
        this.fechSoliCita = fechSoliCita;
    }

    
    
    
    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Alumnovisitante getAlumVisiSelec() {
        return alumVisiSelec;
    }

    public void setAlumVisiSelec(Alumnovisitante alumVisiSelec) {
        this.alumVisiSelec = alumVisiSelec;
    }
            
    
    
    public List<Horariodisponible> getListHoraDisp() {
        return listHoraDisp;
    }

    public void setListHoraDisp(List<Horariodisponible> listHoraDisp) {
        this.listHoraDisp = listHoraDisp;
    }

    public Horariodisponible getHoraSeleCita() {
        return horaSeleCita;
    }

    public void setHoraSeleCita(Horariodisponible horaSeleCita) {
        this.horaSeleCita = horaSeleCita;
    }
    
    
    
        
    public Cita getObjeCita() {
        return objeCita;
    }

    public void setObjeCita(Cita objeCita) {
        this.objeCita = objeCita;
    }

    public List<Cita> getListCita() {
        return listCita;
    }

    public boolean isGuardar() {
        return guardar;
    }

    public boolean isDisable() {
        return Disable;
    }

    public void setDisable(boolean Disable) {
        this.Disable = Disable;
    }
    
    
    
    @PostConstruct
    public void init()
    {
        this.limpForm();
        this.consTodo();
        this.consHorarios();
    }
    
    
    public void limpForm()
    {
        this.objeCita = new Cita();
        this.motivo=null;
        this.fechSoliCita=null;
        this.guardar = true; 
        consCitaPorAlum();
       this.Disable = true;
    }
    
    public void consCitaPorAlum()
    {
        try
        {
            this.listVisiCitaAlum = FCDEVisiCita.findByCarnAlum(String.valueOf(LoginBean.getCodiUsuaSesion()));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }    
    
    public void consHorarios()
    {
        try
        {
            this.listHoraDisp = FCDEHoraDisp.findByCodiUsua(this.objeCita.getCodiUsua());
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
            this.listCita = FCDECita.findAll();
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
            this.objeCita = FCDECita.find(codi);
            this.guardar = false;
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Consultado a " + 
                    String.format("%s %s", this.objeCita.getFechLlegCita(), this.objeCita.getFechSaliCita()) + "')");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al consultar')");
        }
    }
    
    public void guar()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        if(valiDato())
        {
            this.objeCita.setTipoCita(1);
            this.objeCita.setTipoVisi(2);
            this.objeCita.setTipoDura(2);
            this.objeCita.setEstaCita(1);
            this.objeCita.setDescCita(this.motivo);
            FCDECita.create(this.objeCita);            
            this.listCita.add(this.objeCita);
            Cambiocita objeCambCita = new Cambiocita();
            objeCambCita.setCodiCita(this.objeCita);
            objeCambCita.setFechCambCita(new Date());
            objeCambCita.setFechInicCitaNuev(fechSoliCita);
            objeCambCita.setFechFinCitaNuev(fechSoliCita);
            DateFormat df = new SimpleDateFormat("HH:mm:a");
            objeCambCita.setHoraCambCita(df.format(new Date()));
            objeCambCita.setHoraInicCitaNuev(this.getHoraSeleCita().getHoraInicHoraDisp());
            objeCambCita.setHoraFinCitaNuev(this.getHoraSeleCita().getHoraFinaHoraDisp());
            objeCambCita.setMotiCambCita(this.motivo);
            objeCambCita.setEstaCambCita(0);
            FCDECambCita.create(objeCambCita);
            Visitantecita objeVisiCita = new Visitantecita();
            objeVisiCita.setCodiCita(this.objeCita);
            objeVisiCita.setCodiVisi(alumVisiSelec.getCodiVisi());
            objeVisiCita.setCarnAlum(String.valueOf(LoginBean.getCodiUsuaSesion()));
            FCDEVisiCita.create(objeVisiCita);
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Guardado con éxito')");
            this.limpForm();
        }
    }
    
    public void confCita(){
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        
        try
        {
            objeCita = FCDECita.find(objeCita.getCodiCita());
            objeCita.setEstaCita(2);
            FCDECita.edit(objeCita);
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Cita Confirmada'); $('#ModaFormRegi').modal('hide');");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al modificar ')");
        }
    }
    
    public void modi()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            this.listCita.remove(this.objeCita); //Limpia el objeto viejo
            FCDECita.edit(this.objeCita);
            this.listCita.add(this.objeCita); //Agrega el objeto modificado
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
            FCDECita.remove(this.objeCita);
            this.listCita.remove(this.objeCita);
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Eliminados')");
            this.limpForm();
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al eliminar')");
        }
    }
    
    private int getDay(String dia){
        int ndia = 0;
        String dias[] = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes"};
        for(int i = 0; i < dias.length; i++){
            if(dia.equals(dias[i])){ndia = i+1; break;}
        }
        return ndia;
    }
    
    private boolean valiDato(){
        boolean val = false;
        RequestContext ctx = RequestContext.getCurrentInstance();
            int diaHoraDisp = getDay(this.horaSeleCita.getDiaHoraDisp());
            int diaExceHoraDisp = this.fechSoliCita.getDay();
            if(diaHoraDisp == diaExceHoraDisp){
                
                val = true;
            }else{
                ctx.execute("setMessage('MESS_INFO', 'Atención', 'Fecha Inválida para el Horario Seleccionado');");
            }
        return val;
    }
    
}
