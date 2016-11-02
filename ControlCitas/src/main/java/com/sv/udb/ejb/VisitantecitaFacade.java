/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.ejb;

import com.sv.udb.modelo.Cambiocita;
import com.sv.udb.modelo.Cita;
import com.sv.udb.modelo.Visitante;
import com.sv.udb.modelo.Visitantecita;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Kevin
 */
@Stateless
public class VisitantecitaFacade extends AbstractFacade<Visitantecita> implements VisitantecitaFacadeLocal {

    @PersistenceContext(unitName = "PILETPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VisitantecitaFacade() {
        super(Visitantecita.class);
    }
    @Override
    public List<Visitantecita> findByCarnAlum(String codi) {
        TypedQuery<Visitantecita> q = (TypedQuery<Visitantecita>) getEntityManager().createQuery("SELECT v FROM Visitantecita v WHERE v.carnAlum = :carnAlum");
        q.setParameter("carnAlum", codi);
        List resu = q.getResultList();
        return resu.isEmpty() ? null : resu;
    }
    @Override
    public List<Visitantecita> findByCodiUsua(Object codi) {
        TypedQuery<Visitantecita> q = (TypedQuery<Visitantecita>) getEntityManager().createQuery("SELECT v FROM Visitantecita v WHERE v.codiCita.codiUsua = :codiUsua");
        q.setParameter("codiUsua", Integer.parseInt(String.valueOf(codi)));
        List resu = q.getResultList();
        return resu.isEmpty() ? null : resu;
    }
    @Override
    public List<Visitantecita> findByCodiCita(Object codi) {
        TypedQuery<Visitantecita> q = (TypedQuery<Visitantecita>) getEntityManager().createQuery("SELECT v FROM Visitantecita v WHERE v.codiCita = :codiCita");      
        q.setParameter("codiCita", Integer.parseInt(String.valueOf(codi)));
        List resu = q.getResultList();
        return resu.isEmpty() ? null : resu;
    }
    @Override
    public Visitantecita findByCodiCita_Visitante(Cita cita, Visitante visi) {
        TypedQuery<Visitantecita> q = (TypedQuery<Visitantecita>) getEntityManager().createQuery("SELECT v FROM Visitantecita v WHERE v.codiCita = :codiCita and v.codiVisi = :codiVisi");      
        q.setParameter("codiCita", cita);
        q.setParameter("codiVisi", visi);
        Visitantecita resu = q.getSingleResult();
        return (resu == null) ? null : resu;
    }
    
    @EJB
    private CambiocitaFacadeLocal FCDECambCita;
    
    @Override
    public List<Visitantecita> findByFechNow() {
        TypedQuery<Visitantecita> q = (TypedQuery<Visitantecita>) getEntityManager().createQuery("SELECT vc FROM Visitantecita vc, Cambiocita cc WHERE vc.codiCita = cc.codiCita");      
        //q.setParameter("codiCita", Integer.parseInt(String.valueOf(codi)));
        List resu = q.getResultList();
        return resu.isEmpty() ? null : resu;
    }
}
