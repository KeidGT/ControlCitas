/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.ejb;

import com.sv.udb.modelo.Pruebaevento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Orlando Vasquez
 */
@Local
public interface PruebaeventoFacadeLocal {

    void create(Pruebaevento pruebaevento);

    void edit(Pruebaevento pruebaevento);

    void remove(Pruebaevento pruebaevento);

    Pruebaevento find(Object id);

    List<Pruebaevento> findAll();

    List<Pruebaevento> findRange(int[] range);

    int count();
    
}
