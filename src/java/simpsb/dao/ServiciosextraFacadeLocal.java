/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpsb.dao;

import java.util.List;
import javax.ejb.Local;
import simpsb.entidades.Serviciosextra;

/**
 *
 * @author Leonardo Lara
 */
@Local
public interface ServiciosextraFacadeLocal {

    void create(Serviciosextra serviciosextra);

    void edit(Serviciosextra serviciosextra);

    void remove(Serviciosextra serviciosextra);

    Serviciosextra find(Object id);

    List<Serviciosextra> findAll();

    List<Serviciosextra> findRange(int[] range);

    int count();
    
}
