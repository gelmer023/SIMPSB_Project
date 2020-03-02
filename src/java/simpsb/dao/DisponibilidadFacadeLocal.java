/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpsb.dao;

import java.util.List;
import java.util.Date;
import javax.ejb.Local;
import simpsb.entidades.Citas;
import simpsb.entidades.Disponibilidad;
import simpsb.entidades.Horas;

/**
 *
 * @author Leonardo Lara
 */
@Local
public interface DisponibilidadFacadeLocal {

    void create(Disponibilidad disponibilidad);

    void edit(Disponibilidad disponibilidad);

    void remove(Disponibilidad disponibilidad);

    Disponibilidad find(Object id);

    List<Disponibilidad> findAll();

    List<Disponibilidad> findRange(int[] range);

    int count();
    
    List<Horas> disponibles(Citas ct);
    
}
