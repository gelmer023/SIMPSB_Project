/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpsb.dao;

import java.util.List;
import javax.ejb.Local;
import simpsb.entidades.Porcentajepagos;

/**
 *
 * @author Leonardo Lara
 */
@Local
public interface PorcentajepagosFacadeLocal {

    void create(Porcentajepagos porcentajepagos);

    void edit(Porcentajepagos porcentajepagos);

    void remove(Porcentajepagos porcentajepagos);

    Porcentajepagos find(Object id);

    List<Porcentajepagos> findAll();

    List<Porcentajepagos> findRange(int[] range);

    int count();
    
    List<Porcentajepagos> pagoE(Porcentajepagos pg);
}
