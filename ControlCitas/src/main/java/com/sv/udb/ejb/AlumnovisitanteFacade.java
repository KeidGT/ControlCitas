/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.ejb;

import com.sv.udb.modelo.Alumnovisitante;
import com.sv.udb.modelo.Horariodisponible;
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
public class AlumnovisitanteFacade extends AbstractFacade<Alumnovisitante> implements AlumnovisitanteFacadeLocal {

    @PersistenceContext(unitName = "PILETPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AlumnovisitanteFacade() {
        super(Alumnovisitante.class);
    }
    /*
    @Override
    public List<Alumnovisitante> findByCodiVisi(Object codi) {
        TypedQuery<Horariodisponible> q = getEntityManager().createNamedQuery("Horariodisponible.findByCodiVisi", Horariodisponible.class);        
        q.setParameter("codiVisi", codi);
        List resu = q.getResultList();
        return resu.isEmpty() ? null : resu;
    }*/
}
