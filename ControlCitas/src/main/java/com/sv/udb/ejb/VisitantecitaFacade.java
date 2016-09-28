/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.ejb;

import com.sv.udb.modelo.Visitantecita;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Kevin
 */
@Stateless
public class VisitantecitaFacade extends AbstractFacade<Visitantecita> implements VisitantecitaFacadeLocal {

    @PersistenceContext(unitName = "POOPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VisitantecitaFacade() {
        super(Visitantecita.class);
    }
    
}
