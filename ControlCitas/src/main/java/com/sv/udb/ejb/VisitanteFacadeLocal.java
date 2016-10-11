/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.ejb;

import com.sv.udb.modelo.Visitante;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kevin
 */
@Local
public interface VisitanteFacadeLocal {

    void create(Visitante visitante);

    void edit(Visitante visitante);

    void remove(Visitante visitante);

    Visitante find(Object id);

    List<Visitante> findAll();

    List<Visitante> findRange(int[] range);

    List<Visitante> findByDuiVisi(Object dui);
    
    
    int count();
    
}
