/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.ejb;

import com.sv.udb.modelo.Cita;
import com.sv.udb.modelo.Visitante;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Kevin
 */
@Stateless
public class VisitanteFacade extends AbstractFacade<Visitante> implements VisitanteFacadeLocal {

    @PersistenceContext(unitName = "PILETPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VisitanteFacade() {
        super(Visitante.class);
    }
    @Override
    public Visitante findByDuiVisi(Object dui) {
        TypedQuery<Visitante> q = getEntityManager().createNamedQuery("Visitante.findByDuiVisi", Visitante.class);        
        q.setParameter("duiVisi", dui);
        List resu = q.getResultList();
        return resu.isEmpty() ? null : (Visitante)resu.get(0);
    }
    @Override
    public List<Visitante> findByCita(Cita codiCita) {
        TypedQuery<Visitante> q = getEntityManager().createNamedQuery("Visitante.findByCita", Visitante.class);        
        q.setParameter("codiCita", codiCita.getCodiCita());
        List resu = q.getResultList();
        return resu.isEmpty() ? null : resu;
    }
    @Override
    public List<Visitante> findByCarnAlum(String carnAlum) {
        TypedQuery<Visitante> q = getEntityManager().createNamedQuery("Visitante.findByCarnAlum", Visitante.class);        
        q.setParameter("carnAlum", carnAlum);
        List resu = q.getResultList();
        return resu.isEmpty() ? null : resu;
    }
    
}
