/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.ejb;

import com.sv.udb.modelo.Cambiocita;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kevin
 */
@Local
public interface CambiocitaFacadeLocal {

    void create(Cambiocita cambiocita);

    void edit(Cambiocita cambiocita);

    void remove(Cambiocita cambiocita);

    Cambiocita find(Object id);

    List<Cambiocita> findAll();

    List<Cambiocita> findRange(int[] range);

    int count();
    
}