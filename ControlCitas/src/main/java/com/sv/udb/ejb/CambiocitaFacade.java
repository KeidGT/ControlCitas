/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.ejb;

import com.sv.udb.modelo.Alumnovisitante;
import com.sv.udb.modelo.Cambiocita;
import com.sv.udb.modelo.Cita;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Kevin
 */
@Stateless
public class CambiocitaFacade extends AbstractFacade<Cambiocita> implements CambiocitaFacadeLocal {

    @PersistenceContext(unitName = "PILETPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CambiocitaFacade() {
        super(Cambiocita.class);
    }
    
    @Override
    public Cambiocita findByCodiCita(Object codi) {
        TypedQuery<Cambiocita> q = (TypedQuery<Cambiocita>) getEntityManager().createQuery("SELECT c FROM Cambiocita c WHERE c.codiCita = :codiCita ORDER BY c.fechCambCita desc, c.horaCambCita desc");    
        q.setParameter("codiCita", String.valueOf(codi));
        List resu = q.getResultList();
        return resu.isEmpty() ? null : (Cambiocita)resu.get(0);
    }
    
    @Override
    public Cambiocita findByCita(Cita codi) {
        TypedQuery<Cambiocita> q = (TypedQuery<Cambiocita>) getEntityManager().createQuery("SELECT c FROM Cambiocita c WHERE c.codiCita = :codiCita ORDER BY c.fechCambCita desc, c.horaCambCita desc");     
        q.setParameter("codiCita", codi);
        List resu = q.getResultList();
        return resu.isEmpty() ? null : (Cambiocita)resu.get(0);
    }
    
}
