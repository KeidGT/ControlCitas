/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.utils.Conexion;
import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import net.sf.jasperreports.engine.JasperRunManager;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Vane
 */
@Named(value = "reportesBeanCitaReprogramada")
@ViewScoped
public class ReportesBeanCitaReprogramada implements Serializable{
    private static final long serialVersionUID = 1L;
    @Inject
    private GlobalAppBean globalAppBean; //Bean de aplicación (Instancia)
    private byte[] docuRepo;
    /**
     * Creates a new instance of ReportesBean
     */
    public ReportesBeanCitaReprogramada() {
    }

    public byte[] getDocuRepo() {
        return docuRepo;
    }
    
    //Procesar reporte
    public void procRepo()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            Connection cn = new Conexion().getCn(); //La conexión
            Map params = new HashMap(); //Mapa de parámetros
//            params.put("Nombre", valor); //Para este ejemplo no es necesario
            String pathRepo = globalAppBean.getResourcePath("reportes/CitaReprogramada.jasper");
            this.docuRepo = JasperRunManager.runReportToPdf(pathRepo, params, cn);
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Reporte cargado correctamente')");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al guardar ')");            
        }
    }
}
