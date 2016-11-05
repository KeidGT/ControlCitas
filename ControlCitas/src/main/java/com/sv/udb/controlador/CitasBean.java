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
import com.sv.udb.ejb.VisitanteFacadeLocal;
import com.sv.udb.ejb.VisitantecitaFacadeLocal;
import com.sv.udb.modelo.Alumno;
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
import java.util.HashSet;
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
    @EJB
    private AlumnovisitanteFacadeLocal FCDEAlumnoVisitante;
     @EJB
    private VisitanteFacadeLocal FCDEVisi; 
    
    //OBJETOS
    private Cita objeCita;
    private Cambiocita objeCambCita;
    private Horariodisponible horaSeleCita;
    private Alumnovisitante alumVisiSelec;
    private Alumnovisitante objeAlumVisi;
    private Visitantecita objeVisiCita = new Visitantecita();
    private Visitante objeVisi;
    private Alumno objeAlumUsuaDepe;
    private Cambiocita objeCambCitaUsuaDepe;
    //LISTAS
    private List<Cita> listCita;
    private List<Horariodisponible> listHoraDisp;
    private List<Visitantecita> listVisiCitaAlum;//--> ambas listas se pueden fusionar en una misma, revisar en el futuro...
    private List<Cita> listCitaUsua;//-->
    private List<Visitantecita> listVisiCita;
    private List<Visitante> listVisi;
    private List<Horariodisponible> listHoraDispUsua;
    private List<Alumno> listAlum;
    private List<Alumnovisitante> listVisiTemp;
    private List<Visitantecita> listVisiCitaUsuaDepe;
    
    //variables de funcionalidad y lógica de negocio
    private boolean guardar;
    private String motivo;
    private Date fechSoliCita;
    private String carnAlum;
    private String FechInic;
    private String FechFina;
    
    //variables para manejar campos de la lógica de negocio
    private boolean confirmar;
    private boolean programar;
    private boolean reprogramar;
    private boolean ignoHoraDisp;
    
    
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
        /*RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        ctx.execute("INIT_OBJE_TABL()");*/
    }
    
    public CitasBean() {
        
    }
    //Encapsulamientos
    
   
    public List<Alumnovisitante> getListVisiTemp() {
        return listVisiTemp;
    }

    public Visitante getObjeVisi() {
        return objeVisi;
    }

    public void setObjeVisi(Visitante objeVisi) {
        this.objeVisi = objeVisi;
    }

    public Visitantecita getObjeVisiCita() {
        return objeVisiCita;
    }

    public void setObjeVisiCita(Visitantecita objeVisiCita) {
        this.objeVisiCita = objeVisiCita;
    }
    
    public List<Visitantecita> getListVisiCitaAlum() {
        this.consCitaPorAlum();
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

    public boolean isProgramar() {
        return programar;
    }

    public void setProgramar(boolean programar) {
        this.programar = programar;
    }

    

    public boolean isReprogramar() {
        return reprogramar;
    }

    public void setReprogramar(boolean reprogramar) {
        this.reprogramar = reprogramar;
    }

    public List<Horariodisponible> getListHoraDispUsua() {
        this.consListHoraDispUsua();
        return listHoraDispUsua;
    }

    public Alumnovisitante getObjeAlumVisi() {
        return objeAlumVisi;
    }

    public void setObjeAlumVisi(Alumnovisitante objeAlumVisi) {
        this.objeAlumVisi = objeAlumVisi;
    }

    public List<Alumno> getListAlum() {
        return listAlum;
    }

    public List<Visitantecita> getListVisiCita() {
        consListCita();
        return listVisiCita;
    }

    public boolean isIgnoHoraDisp() {
        return ignoHoraDisp;
    }

    public void setIgnoHoraDisp(boolean ignoHoraDisp) {
        this.ignoHoraDisp = ignoHoraDisp;
    }

    public String getFechInic() {
        return FechInic;
    }

    public void setFechInic(String FechInic) {
        this.FechInic = FechInic;
    }

    public String getFechFina() {
        return FechFina;
    }

    public void setFechFina(String FechFina) {
        this.FechFina = FechFina;
    }

    public List<Cita> getListCitaUsua() {
        consListCitaUsua();
        return listCitaUsua;
    }

    public Alumno getObjeAlumUsuaDepe() {
        return objeAlumUsuaDepe;
    }

    public void setObjeAlumUsuaDepe(Alumno objeAlumUsuaDepe) {
        this.objeAlumUsuaDepe = objeAlumUsuaDepe;
    }

    public Cambiocita getObjeCambCitaUsuaDepe() {
        return objeCambCitaUsuaDepe;
    }

    public void setObjeCambCitaUsuaDepe(Cambiocita objeCambCitaUsuaDepe) {
        this.objeCambCitaUsuaDepe = objeCambCitaUsuaDepe;
    }

    public List<Visitantecita> getListVisiCitaUsuaDepe() {
        return listVisiCitaUsuaDepe;
    }

    

    
        
    
    
    @PostConstruct
    public void init()
    {
        this.limpForm();
        this.listAlum = new AlumnosBean().consTodoAlum();
        
    }
    
    
    public void limpForm()
    {
        this.objeCita = new Cita();
        this.objeCita.setCodiUbic(null);
        this.fechSoliCita=null;
        this.guardar = true;
        this.objeVisi = new Visitante();
        this.listVisi = new ArrayList<Visitante>();
        listVisi.clear();
        this.objeVisiCita = new Visitantecita();
        listVisiTemp = new ArrayList<Alumnovisitante>();
        listVisiTemp.clear();
        this.carnAlum = "";
        this.confirmar = false;
        this.reprogramar = false;
        this.programar = true;
        this.switFormCita=true;
        this.ignoHoraDisp = false;
        this.horaSeleCita = null;
        this.motivo=null;
        this.objeCambCita  = new Cambiocita();
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
            ex.printStackTrace();
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
    
    
    
    
    public void consListCita()
    {
        try
        {
            this.listCita = FCDECita.findAll();
            List<Cambiocita> listCambCitaTemp = new ArrayList<Cambiocita>();
            for(Cita obj : listCita){
                Cambiocita camb = FCDECambCita.findByCita(obj);
                listCambCitaTemp.add(camb);
            }
            listCita.clear();
            for(Cambiocita objeCambCitaTemp: listCambCitaTemp){
                List<Visitantecita> listVisiCitaTemp = new ArrayList<Visitantecita>();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    objeCambCita.setHoraCambCita(df.format(new Date()));
                if(df.format(objeCambCitaTemp.getFechInicCitaNuev()).equals(df.format(new Date())) || df.format(objeCambCitaTemp.getFechFinCitaNuev()).equals(df.format(new Date()))){
                    listVisiCitaTemp = FCDEVisiCita.findByCodiCita(objeCambCitaTemp.getCodiCita());
                    
                }
                if(listVisiCita == null)listVisiCita = new ArrayList<Visitantecita>();
                for(Visitantecita obje: listVisiCitaTemp){
                    if(!listVisiCita.contains(obje))listVisiCita.add(obje);
                }
                listVisiCitaTemp.clear();
            }
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
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
            String dui = this.objeVisi.getDuiVisi();
            this.objeVisi = new Visitante();
            this.objeVisi.setDuiVisi(dui);
            ctx.execute("setMessage('MESS_INFO', 'Atención', 'Visitante no encontrado, Registrar Visitante!')");
        }
    }
    
    
    /*SECCIÓN TERMINADA DE VISITAS (CITAS DE TIPO 2), PARA LLEVAR CONTROL DE VISITANTES*/
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
                                    /*SECCIÓN PARA CITAS (DOCENTE Y PERSONAL ADMIN.)*/
    
    //consultar el ultimo objeto "cambio cita" a partir de un objeto "cita"
    
     //establecer booleanos
    private void estAcci(){
        switch(this.objeCita.getEstaCita()){
            //cuando una cita está por ser programada
            case 0:
                this.reprogramar = false;
                this.confirmar = true;
                this.programar = false;
                break;
            //cuando una cita está solicitada
            case 1:
                this.reprogramar = false;
                this.confirmar = true;
                this.programar = false;
            break;
            //cuando una cita está confirmada
            case 2:
                this.reprogramar = true;
                this.confirmar = false;
                this.programar = false;
            break;
            //cuando una cita está rechazada
            case 3:
                this.reprogramar = true;
                this.confirmar = false;
                this.programar = false;
            break;
        }
    }
    //método para retornar parentesco, dependiendo del código se le pase
    public String getParen(Visitante visi){
        String parentesco = "";
        Alumnovisitante obje = consObjeAlumVisi(visi);
        switch(obje.getPareAlumVisi()){
            case 1:
                parentesco = "Padre/Madre";
            break;
            case 2:
                parentesco = "Tio/Tia";
            break;
            case 3:
                parentesco = "Abuelo/Abuela";
            break;
            case 4:
                parentesco = "Hermano/Hermana";
            break;
            case 5:
               parentesco = "Primo/Prima";
            break;
            case 6:
                parentesco = obje.getEspeAlumVisi();
            break;
            
        }
        return parentesco;
    }
    //consultar el ultimo cambio de la cita (para mostrar en la tabla)

    
    //consultar el ultimo cambio de la cita (para mostrar en la tabla)
    public Visitante consObjeVisi(int codi){
        Visitante objecons = null; 
        try
        {
             objecons = FCDEVisi.find(codi);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        return objecons;
    }
    
    //consultar un alumno visitante a partir de un visitante y el carné del alumno seleccionado (para mostrar en tabla)
    //se tuvo que haber seleccionado el visitante en combobox primero
    public Alumnovisitante consObjeAlumVisi(Visitante visi){
        try
        {
            this.objeAlumVisi = FCDEAlumnoVisitante.findByAlumVisi(visi, carnAlum);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return objeAlumVisi;
    }
    
    //consultar un onbjeto de tipo alumno de la lista hipotética del web service
    // considerar consultar un solo registro del web service en el futuro....
    public Alumno consObjeAlumno(String carn){
        Alumno objeAlumn = null;
        for(Alumno obje : listAlum){
            if(obje.getCarnAlum().equals(carn)){
                objeAlumn = obje;
                break;
            }
        }
        return objeAlumn;
    }
    
    //consultar las citas del usuario
    public void consListCitaUsua()
    {
        try
        {
            this.listCitaUsua = FCDECita.findByCodiUsua(LoginBean.getCodiUsuaSesion());
            if(this.listCitaUsua == null)this.listCitaUsua = new ArrayList<Cita>();
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
        
    //consultar los encargados de un alumno
    public void consListVisiAlum(){
        try
        {
            if(carnAlum!=null){
                listVisi = FCDEVisi.findByCarnAlum(carnAlum);
            }else{
                listVisi = new ArrayList<Visitante>();
            }
            consVisiCita();
            
        }
        catch(Exception ex)
        {
            System.out.println(ex);
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
    
    //consultar visitantes de una cita y los guardamos en una lista temporal
    public void consVisiCita(){
        try{
            if(objeCita==null)objeCita=new Cita();
            if(listVisiTemp==null){
                listVisiTemp =FCDEAlumnoVisitante.findByCita(objeCita);
                if(listVisiTemp==null)listVisiTemp = new ArrayList<Alumnovisitante>();
            }else{
                //tomamos registros anteriores en una nueva lista
                List<Alumnovisitante> listTransac = listVisiTemp; 
                listVisiTemp=FCDEAlumnoVisitante.findByCita(objeCita);
                if(listVisiTemp==null )listVisiTemp = new ArrayList<Alumnovisitante>();
                //agregamos los anteriores a los nuevos
                for(Alumnovisitante visi : listTransac){
                    listVisiTemp.add(visi);
                }
                //..eliminamos repetidos
                HashSet<Alumnovisitante> hashSet = new HashSet<Alumnovisitante>(listVisiTemp);
                listVisiTemp.clear();
		listVisiTemp.addAll(hashSet);
            }
            if(listVisi==null)listVisi=new ArrayList<Visitante>();
            for(Alumnovisitante visi : listVisiTemp){
                for(Visitante visi2 : listVisi){
                    if(Objects.equals(visi.getCodiVisi(), visi2)){
                        listVisi.remove(visi2);//quitamos los visitantes que ya estan en la cita, del combobox (lista combobox)
                    }
                }
                
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void consDepeListCitaUsua(Cita cita){
        try
        {
            this.listVisiCitaUsuaDepe = FCDEVisiCita.findByCodiCita(cita);
            if(listVisiCitaUsuaDepe == null)listVisiCitaUsuaDepe = new ArrayList<Visitantecita>();
            this.objeCambCitaUsuaDepe = FCDECambCita.findByCita(cita);
            if(objeCambCitaUsuaDepe==null)objeCambCitaUsuaDepe = new Cambiocita();
            this.objeAlumUsuaDepe = consObjeAlumno(listVisiCitaUsuaDepe.get(0).getCarnAlum());
            if(objeAlumUsuaDepe==null)objeAlumUsuaDepe= new Alumno();
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public Visitantecita consObjeVisiCitaDepe(){
        return listVisiCitaUsuaDepe.get(0);
    } 
    
    //usado para consultar los encargados de un alumno, al seleccionar un alumno desde una tabla
    public void setAlumn(){
        String Carn = String.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codiObjeAlum"));
        if(Carn!=null){
            this.carnAlum = Carn;
            consListVisiAlum();
            RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Encargados Consultados')");
        }
    }
    
    //usado para consultar un visitante en una cita, al seleccionar un AlumnoVisitante desde una tabla
    public void setVisi(){
        int codi = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codiObjeVisi"));
        this.objeAlumVisi = consObjeAlumVisi(consObjeVisi(codi));
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Encargados Consultados')");
        
    }
    
    
    
    
    
    //consultar una cita del usuario (de los que estan en la tabla de datos)
    public void consObjeCitaUsua(){
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        int codi = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codiObjePara"));
        try
        {
            limpForm();
            this.objeCita = FCDECita.find(codi);
            this.objeCambCita = FCDECambCita.findByCita(objeCita);//consultamos el ultimo cambio de cita
            if(objeCambCita == null) objeCambCita = new Cambiocita();
            this.fechSoliCita = objeCambCita.getFechInicCitaNuev();
            listVisiTemp = null;
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
   
    
    //"Agregar un visitante a una lista temporal"
    public void addVisiCita(){
        try
        {
            RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
            if(listVisiTemp == null)listVisiTemp = new ArrayList<Alumnovisitante>();
            consVisiCita();//consultamos si ya hay visitantes en la cita
            this.listVisiTemp.add(consObjeAlumVisi(objeVisi));//i agregamos al nuevo
            this.listVisi.remove(objeVisi);
            ctx.execute("setMessage('MESS_INFO', 'Atención', 'Visitante Agregado')");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
    //quitar un visitante de la lista temporal
    public void dropVisiCita(Alumnovisitante obje){
        try
        {
            RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
            this.listVisiTemp.remove(obje);
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Visitante Eliminado de la Cita')");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
    
    //confirmar(1), rechazar(2), Reprogramar(3) cita
    public void confRechReprCita(int acci){
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        
        try
        {
            switch(acci){
                case 1:
                    objeCita.setEstaCita(2);
                break;
                case 2:
                    objeCita.setEstaCita(3);
                break;
                case 3:{
                    objeCambCita.setFechInicCitaNuev(fechSoliCita);
                    objeCambCita.setFechFinCitaNuev(fechSoliCita);
                    DateFormat df = new SimpleDateFormat("HH:mm:a");
                    objeCambCita.setHoraCambCita(df.format(new Date()));
                    if(ignoHoraDisp){
                        objeCambCita.setHoraInicCitaNuev(FechInic);
                        objeCambCita.setHoraFinCitaNuev(FechFina);
                    }else{
                        objeCambCita.setHoraInicCitaNuev(horaSeleCita.getHoraInicHoraDisp());
                        objeCambCita.setHoraFinCitaNuev(horaSeleCita.getHoraFinaHoraDisp());
                    }
                    objeCita.setEstaCita(2);
                }
            }
            listCitaUsua.remove(objeCita);
            objeCambCita.setCodiCita(objeCita);
            objeCambCita.setMotiCambCita(motivo);
            objeCambCita.setEstaCambCita(1);
            objeCambCita.setFechCambCita(new Date());
            DateFormat df = new SimpleDateFormat("HH:mm:a");
            objeCambCita.setHoraCambCita(df.format(new Date()));
            FCDECita.edit(objeCita);
            FCDECambCita.create(objeCambCita);
            objeCambCita.setCodiCita(objeCita);
            listCitaUsua.add(objeCita);
            switch(acci){
                case 1:
                    ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Cita Confirmada'); $('#ModaFormRegi').modal('hide');");
                    this.limpForm();
                break;
                case 2:
                    ctx.execute("setMessage('MESS_INFO', 'Atención', 'Cita Rechazada, Proceda a Reprogramar');");
                break;
                case 3:{
                    ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Cita Reprogramada'); $('#ModaFormRegi').modal('hide');");
                }
            }
            estAcci();
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al realizar la acción')");
            ex.printStackTrace();
        }
    }
    
    private boolean valiDatoProgVisiUsua(){
        boolean vali = false;
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        if((horaSeleCita != null || ignoHoraDisp) && fechSoliCita!= null && objeCita.getCodiUbic() != null){
            int diaHoraDisp = (horaSeleCita == null)? 0 : getDay(this.horaSeleCita.getDiaHoraDisp());
            int diaExceHoraDisp = this.fechSoliCita.getDay();
            if(diaHoraDisp == diaExceHoraDisp || ignoHoraDisp){
                if(listVisiTemp.size() > 0){
                    if(fechSoliCita.after(new Date())){

                        vali = true;
                    }else{
                        ctx.execute("setMessage('MESS_INFO', 'Atención', 'Ningun Visitante Agregado a la Cita');");
                    }
                }else{
                    ctx.execute("setMessage('MESS_INFO', 'Atención', 'Ningun Visitante Agregado a la Cita');");
                }
            }else{
                ctx.execute("setMessage('MESS_INFO', 'Atención', 'El dia de la fecha no coincide con el horario disponible');");
            }
        }else{
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Rellenar Campos');");
        }
        return vali;
    }
    //programar cita como usuario a "x" cantidad de visitantes
    public void progCitaUsua(){
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        
        try
        {
            if(valiDatoProgVisiUsua()){
                objeCita.setEstaCita(2);
                objeCita.setDescCita(motivo);
                objeCita.setTipoCita(1);
                objeCita.setTipoDura(2);
                objeCita.setTipoVisi(2);
                objeCita.setCodiUsua(LoginBean.getCodiUsuaSesion());
                //crear el objeto cita
                FCDECita.create(objeCita);

                objeCambCita.setCodiCita(objeCita);
                objeCambCita.setEstaCambCita(1);
                objeCambCita.setFechCambCita(new Date());

                objeCambCita.setFechInicCitaNuev(fechSoliCita);
                objeCambCita.setFechFinCitaNuev(fechSoliCita);
                DateFormat df = new SimpleDateFormat("HH:mm:a");
                objeCambCita.setHoraCambCita(df.format(new Date()));
                if(ignoHoraDisp){
                    objeCambCita.setHoraInicCitaNuev(FechInic);
                    objeCambCita.setHoraFinCitaNuev(FechFina);
                }else{
                    objeCambCita.setHoraInicCitaNuev(horaSeleCita.getHoraInicHoraDisp());
                    objeCambCita.setHoraFinCitaNuev(horaSeleCita.getHoraFinaHoraDisp());
                }
                objeCambCita.setEstaCambCita(1);

                //crear el objeto cambio Cita
                FCDECambCita.create(objeCambCita);
                
                //registrando visitantes Cita
                for(Alumnovisitante visi : listVisiTemp){
                    objeVisiCita.setCodiCita(objeCita);
                    objeVisiCita.setCarnAlum(visi.getCarnAlum());
                    objeVisiCita.setCodiVisi(visi.getCodiVisi());
                    objeVisiCita.setEstaVisi(0);
                    FCDEVisiCita.create(objeVisiCita);
                    
                }
                if(this.listCitaUsua ==  null)this.listCitaUsua = new ArrayList<Cita>();
                this.listCitaUsua.add(objeCita);
                this.limpForm();
                ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Cita Programada');");
            }
            
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al Programar')");
            ex.printStackTrace();
        }
    }
    
    /*TERMINA SECCIÓN PARA CITAS (DOCENTE Y PERSONAL ADMIN.)*/
    
    
    public void prueba(){
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
       
        ctx.execute("setMessage('MESS_SUCC', 'Atención', 'MENSAJE');");
        
    }
}
