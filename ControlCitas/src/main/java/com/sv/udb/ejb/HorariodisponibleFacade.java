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
    public List<Horariodisponible> findByCodiUsua(Object codi) {
        Query q = getEntityManager().createQuery("SELECT u FROM Usuarios u WHERE u.acceUsua = :acceUsua AND u.contUsua = :contUsua AND u.estaUsua = :estaUsua", Horariodisponible.class);        
        q.setParameter("codiUsua", 1);
        List resu = q.getResultList();
        return resu.isEmpty() ? null : resu;
    }
    
}
