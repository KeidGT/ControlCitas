/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.ejb;

import com.sv.udb.modelo.Visitantecita;
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
        TypedQuery<Visitantecita> q = getEntityManager().createNamedQuery("Visitantecita.findByCarnAlum", Visitantecita.class);        
        q.setParameter("carnAlum", codi);
        List resu = q.getResultList();
        return resu.isEmpty() ? null : resu;
    }
}
