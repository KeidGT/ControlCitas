/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.ejb;

import com.sv.udb.modelo.Horariodisponible;
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
public class HorariodisponibleFacade extends AbstractFacade<Horariodisponible> implements HorariodisponibleFacadeLocal {

    @PersistenceContext(unitName = "PILETPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HorariodisponibleFacade() {
        super(Horariodisponible.class);
    }
    
    @Override
    public List<Horariodisponible> findAll() {
        TypedQuery<Horariodisponible> q = (TypedQuery<Horariodisponible>) getEntityManager().createQuery("SELECT h FROM Horariodisponible h WHERE h.estaHoraDisp = 1"); 
        List resu = q.getResultList();
        return resu.isEmpty() ? null : resu;
    }
    @Override
    public List<Horariodisponible> findByCodiUsua(Object codi) {
        TypedQuery<Horariodisponible> q = (TypedQuery<Horariodisponible>) getEntityManager().createQuery("SELECT h FROM Horariodisponible h WHERE h.codiUsua = :codiUsua and h.estaHoraDisp = 1");       
        q.setParameter("codiUsua", codi);
        List resu = q.getResultList();
        return resu.isEmpty() ? null : resu;
    }
    
}
