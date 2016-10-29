/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.ejb;

import com.sv.udb.modelo.Cita;
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
public class CitaFacade extends AbstractFacade<Cita> implements CitaFacadeLocal {

    @PersistenceContext(unitName = "PILETPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CitaFacade() {
        super(Cita.class);
    }
    @Override
    public List<Cita> findByCodiUsua(Object codi) {
        TypedQuery<Cita> q = (TypedQuery<Cita>) getEntityManager().createQuery("SELECT c FROM Cita c WHERE c.codiUsua = :codiUsua");      
        q.setParameter("codiUsua", String.valueOf(codi));
        List resu = q.getResultList();
        return resu.isEmpty() ? null : resu;
    }
}
