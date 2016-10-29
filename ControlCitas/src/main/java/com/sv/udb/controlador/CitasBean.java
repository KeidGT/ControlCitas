/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.ejb.CambiocitaFacadeLocal;
import com.sv.udb.ejb.CitaFacadeLocal;
import com.sv.udb.ejb.HorariodisponibleFacadeLocal;
import com.sv.udb.ejb.VisitanteFacadeLocal;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
   
    //FACADE
    @EJB
    private VisitantecitaFacadeLocal FCDEVisiCita;
    @EJB
    private CambiocitaFacadeLocal FCDECambCita; 
    @EJB
    private HorariodisponibleFacadeLocal FCDEHoraDisp;
    @EJB
    private CitaFacadeLocal FCDECita;
    //OBJETOS
    private Cita objeCita;
    private Cambiocita objeCambCita;
    private Horariodisponible horaSeleCita;
    private Alumnovisitante alumVisiSelec;
    Visitantecita objeVisiCita = new Visitantecita();
    
    //LISTAS
    private List<Cita> listCita;
    private List<Horariodisponible> listHoraDisp;
    private List<Visitantecita> listVisiCitaAlum;
    private List<Visitantecita> listVisiCitaUsua;
    private List<Visitantecita> listVisiCita;
    private List<Visitante> listVisi;
    private List<Horariodisponible> listHoraDispUsua;
    
    //variables de funcionalidad y lógica de negocio
    private boolean guardar;
    private String motivo;
    private Date fechSoliCita;
    private boolean Disable;
    private String carnAlum;
    
    
    //variables para manejar campos de la lógica de negocio
    private boolean confirmar;
    private boolean solicitar;
    private boolean reprogramar;
    
    //Switch para formularios
    private boolean switFormCita=true;

    public boolean getSwitFormCita() {
        return switFormCita;
    }

    public void setSwitFormCita(boolean switFormCita) {
        this.switFormCita = switFormCita;
    }
    
    public void toggSwitFormCita()
    {
        this.switFormCita = !this.switFormCita;
    }
    
    public CitasBean() {
        
    }
    //Encapsulamientos

    public Visitantecita getObjeVisiCita() {
        return objeVisiCita;
    }

    public void setObjeVisiCita(Visitantecita objeVisiCita) {
        this.objeVisiCita = objeVisiCita;
    }
    
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

    public List<Visitantecita> getListVisiCitaUsua() {
        return listVisiCitaUsua;
    }

    public Cambiocita getObjeCambCita() {
        return objeCambCita;
    }

    public void setObjeCambCita(Cambiocita objeCambCita) {
        this.objeCambCita = objeCambCita;
    }

    public List<Visitante> getListVisi() {
        return listVisi;
    }

    public String getCarnAlum() {
        return carnAlum;
    }

    public void setCarnAlum(String carnAlum) {
        this.carnAlum = carnAlum;
    }

    public boolean isConfirmar() {
        return confirmar;
    }

    public void setConfirmar(boolean confirmar) {
        this.confirmar = confirmar;
    }

    public boolean isSolicitar() {
        return solicitar;
    }

    public void setSolicitar(boolean solicitar) {
        this.solicitar = solicitar;
    }

    public boolean isReprogramar() {
        return reprogramar;
    }

    public void setReprogramar(boolean reprogramar) {
        this.reprogramar = reprogramar;
    }

    public List<Horariodisponible> getListHoraDispUsua() {
        return listHoraDispUsua;
    }
    
    
    
    
    @PostConstruct
    public void init()
    {
        this.limpForm();
        this.consTodo();
        this.consHorarios();
        this.consListHoraDispUsua();
    }
    
    public String consFech(Integer codi)
    {
        return "jhola" + codi;
    }
    
    public void limpForm()
    {
        this.objeCita = new Cita();
        this.motivo=null;
        this.fechSoliCita=null;
        this.guardar = true; 
        consCitaPorAlum();
        consListCitaUsua();
        this.Disable = true;
        this.objeVisi = new Visitante();
        this.listVisi = new ArrayList<Visitante>(); 
        this.objeVisiCita = new Visitantecita();
        this.objeCambCita = new Cambiocita();
        listVisiTemp = new ArrayList<Visitante>();
        this.carnAlum = "";
        this.confirmar = false;
        this.reprogramar = false;
        this.solicitar = true;
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
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Cita Consultada')");
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
            //Cambiocita objeCambCita = new Cambiocita();
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
            objeVisiCita.setCodiCita(this.objeCita);
            objeVisiCita.setCodiVisi(alumVisiSelec.getCodiVisi());
            objeVisiCita.setCarnAlum(String.valueOf(LoginBean.getCodiUsuaSesion()));
            FCDEVisiCita.create(objeVisiCita);
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Se ha solicitado la cita, espere por la respuesta.')");
            this.limpForm();
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
                if(this.fechSoliCita.after(new Date()))
                {
                    val = true;
                }
                else
                {
                    val = false;
                    ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Fecha ya ha pasado');");
                }
            }else{
                ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Fecha Inválida para el Horario Seleccionado');");
            }
        return val;
    }
    
    
    
    /*SECCIÓN DESTINADA A LA PROGRAMACIÓN DE REGISTRO DE VISITAS (CITAS DE TIPO 2), PARA LLEVAR CONTROL DE VISITANTES*/
    
    
    @EJB
    private VisitanteFacadeLocal FCDEVisi; 
    private Visitante objeVisi;
    private boolean Disabled;
    private List<Visitante> listVisiTemp;
    public boolean isDisabled() {
        return Disabled;
    }

    public void setDisabled(boolean Disabled) {
        this.Disabled = Disabled;
    }

    public List<Visitante> getListVisiTemp() {
        return listVisiTemp;
    }

    public Visitante getObjeVisi() {
        return objeVisi;
    }

    public void setObjeVisi(Visitante objeVisi) {
        this.objeVisi = objeVisi;
    }
    
    
    private boolean consVisiDui(){
        boolean resp = false;
        RequestContext ctx = RequestContext.getCurrentInstance();
        try
        {   
            Visitante objVis = FCDEVisi.findByDuiVisi(this.objeVisi.getDuiVisi());
            if(objVis != null){
                    if(objVis.getDuiVisi().equals(this.objeVisi.getDuiVisi())){
                        this.objeVisi = objVis;
                        ctx.execute("setMessage('MESS_INFO', 'Atención', 'Visitante Encontrado!')");
                        resp = true;
                }
                
            }
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Datos No Consultados')");
            ex.printStackTrace();
        }
        return resp;
    }
    
    public void regiIngrVisi(){
        RequestContext ctx = RequestContext.getCurrentInstance();
        //Si el visitante no esta registrado, debe registrarse...
        if(!consVisiDui()){
            this.Disabled = false;
            String dui = this.objeVisi.getDuiVisi();
            this.objeVisi = new Visitante();
            this.objeVisi.setDuiVisi(dui);
            ctx.execute("setMessage('MESS_INFO', 'Atención', 'Visitante no encontrado, Registrar Visitante!')");
        }
    }
    
    
    /*SECCIÓN TERMINADA DE VISITAS (CITAS DE TIPO 2), PARA LLEVAR CONTROL DE VISITANTES*/
    
    
                                    /*SECCIÓN PARA CITAS (DOCENTE Y PERSONAL ADMIN.)*/
    
    //consultar el ultimo objeto "cambio cita" a partir de un objeto "cita"
    public Cambiocita consCambCita(Cita cita){
        Cambiocita objecons = FCDECambCita.findByCita(cita);
        return objecons;
    }
    //consultar visitantes de una cita y los guardamos en una lista temporal
    public void consVisiCita(){
        listVisiTemp = FCDEVisi.findByCita(objeCita);
        for(Visitante visi : listVisiTemp){
            for(Visitante visi2 : listVisi){
                if(Objects.equals(visi, visi2)){
                    listVisi.remove(visi2);//quitamos los visitantes que ya estan en la cita, del combobox (lista combobox)
                }
            }
        }
    }
    //consultar las citas del usuario
    public void consListCitaUsua()
    {
        try
        {
            this.listVisiCitaUsua = FCDEVisiCita.findByCodiUsua(String.valueOf(LoginBean.getCodiUsuaSesion()));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    //consultar los encargados de un alumno
    public void consVisiAlum(){
        listVisi = FCDEVisi.findByCarnAlum(carnAlum);
        try
        {
            consVisiCita();
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
    }
    //establecer booleanos
    private void estAcci(){
        switch(this.objeCita.getEstaCita()){
            case 1:
                this.reprogramar = false;
                this.confirmar = true;
                this.solicitar = false;
            break;
            case 2:
                this.reprogramar = true;
                this.confirmar = false;
                this.solicitar = false;
            break;
            case 3:
                this.reprogramar = true;
                this.confirmar = false;
                this.solicitar = false;
            break;
        }
    }
    
    //consultar una cita del usuario (de los que estan en la tabla de datos)
    public void consCitaUsua(){
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        int codi = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codiObjePara"));
        try
        {
            limpForm();
            this.objeCita = FCDECita.find(codi);//consultamos la cita
            this.objeCambCita = consCambCita(this.objeCita);//consultamos el ultimo cambio de cita
            //motivo = objeCambCita.getMotiCambCita();
            consVisiCita();//consultamos todos los visitantes de la cita
            this.guardar = false;
            estAcci();
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Cita Consultada')");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al consultar')");
        }
        
    }
    //consultar los horariso disponibles del usuario 
    private void consListHoraDispUsua(){
        try
        {
            this.listHoraDispUsua = FCDEHoraDisp.findByCodiUsua(LoginBean.getCodiUsuaSesion());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    //"Agregar un visitante a una lista temporal"
    public void guarVisiCita(){
        try
        {
            RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
            if(listVisiTemp == null)listVisiTemp = new ArrayList<Visitante>();
            this.listVisiTemp.add(objeVisi);
            this.listVisi.remove(objeVisi);
            ctx.execute("setMessage('MESS_INFO', 'Atención', 'Visitante Agregado')");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
    
    //confirmar cita
    public void confCita(){
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        
        try
        {
            objeCita.setEstaCita(2);
            objeCambCita.setCodiCita(objeCita);
            objeCambCita.setMotiCambCita(motivo);
            objeCambCita.setEstaCambCita(1);
            objeCambCita.setFechCambCita(new Date());
            DateFormat df = new SimpleDateFormat("HH:mm:a");
            objeCambCita.setHoraCambCita(df.format(new Date()));
            FCDECita.edit(objeCita);
            FCDECambCita.create(objeCambCita);
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Cita Confirmada'); $('#ModaFormRegi').modal('hide');");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al modificar ')");
        }
    }
    
    
    /*TERMINA SECCIÓN PARA CITAS (DOCENTE Y PERSONAL ADMIN.)*/
    
    
    public void prueba(){
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
       
        ctx.execute("setMessage('MESS_SUCC', 'Atención', 'MENSAJE');");
        
    }
}
