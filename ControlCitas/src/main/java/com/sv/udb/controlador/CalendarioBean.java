/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;
import com.sv.udb.controlador.VisitantesBean;
import com.sv.udb.ejb.CambiocitaFacadeLocal;
import com.sv.udb.ejb.CitaFacadeLocal;
import com.sv.udb.ejb.PruebaeventoFacade;
import com.sv.udb.ejb.PruebaeventoFacadeLocal;
import com.sv.udb.modelo.Cambiocita;
import com.sv.udb.modelo.Cita;
import com.sv.udb.modelo.Pruebaevento;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author Orlando Vasquez
 */
@Named(value = "calendarioBean")
@ViewScoped
public class CalendarioBean implements Serializable{

    /**
     * Creates a new instance of CalendarioBean
     */
    
    private static final long serialVersionUID = 6527333208194203406L;
    private ScheduleModel objeCale;
    private List<Pruebaevento> listPruebaevento = new ArrayList<Pruebaevento>();
    private List<Cita> listCita;
    @EJB
    private PruebaeventoFacadeLocal FCDEPruEven;
    @EJB
    private CitaFacadeLocal FCDECita;
    @EJB
    private CambiocitaFacadeLocal FCDECambCita;
    
    public CalendarioBean() {
    }
    @PostConstruct
    public void init()
    {
        objeCale = new DefaultScheduleModel();
        listCita = new ArrayList<Cita>();
        //this.consTodo();
        getEvenCale();
    }
    
      public void consTodo() {
        try {
            this.listPruebaevento = FCDEPruEven.findAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public List<Pruebaevento> getListPruebaevento() {
        return listPruebaevento;
    }

    public ScheduleModel getObjeCale() {
        return objeCale;
    }
    
     public void conListCitaProg()
    {
        try
        {
            this.listCita = FCDECita.findByEstaProg();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
     public void getEvenCale(){
        try{
            conListCitaProg();//se consultan las citas programadas
            if(listCita == null) listCita= new ArrayList<Cita>();
            for(Cita obj: listCita){//--> recorrer lista
                Cambiocita  objeCambCita = getCambCita(obj);
                if(objeCambCita == null)objeCambCita = new Cambiocita();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                String FechFina = df.format(objeCambCita.getFechFinCitaNuev())+" "+objeCambCita.getHoraFinCitaNuev();
                String FechInic = df.format(objeCambCita.getFechInicCitaNuev())+" "+objeCambCita.getHoraInicCitaNuev();
                DefaultScheduleEvent evt = new DefaultScheduleEvent();
                evt.setEndDate(sdf.parse(FechFina));
                evt.setStartDate(sdf.parse(FechInic));
                evt.setTitle(obj.getDescCita());
                evt.setData(obj.getCodiCita());
                evt.setDescription(obj.getCodiUbic().getNombUbic());
                objeCale.addEvent(evt);
             }
        }catch(Exception e){
            e.printStackTrace();
        }  
     }
    public Cambiocita getCambCita(Cita cita)
    {
        Cambiocita objeCambCita = null;
        try
        {
            objeCambCita = FCDECambCita.findByCita(cita);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return objeCambCita;
    }
     
    private Date getFecha(String date) 
    {
        Date fecha = null;
        if (date != null){
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                fecha = sdf.parse(date);
            } catch (Exception e) {
                fecha = null;
            }
        }
        return fecha;
    }
    
}
