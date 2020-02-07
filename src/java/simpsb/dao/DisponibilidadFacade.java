/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpsb.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import simpsb.entidades.*;

/**
 *
 * @author Sebastián
 */
@Stateless
public class DisponibilidadFacade extends AbstractFacade<Disponibilidad> implements DisponibilidadFacadeLocal {

    @PersistenceContext(unitName = "SIMPSB1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DisponibilidadFacade() {
        super(Disponibilidad.class);
    }

    @Override
    public List<Horas> disponibles() {
        List<Horas> listDis = null;
        try {
            Query query = em.createQuery("SELECT h from Horas h INNER JOIN h.disponibilidadList d WHERE d.estado = :estado");
            query.setParameter("estado", "Disponible");
            listDis = query.getResultList();
            if (!listDis.isEmpty()) {
                listDis.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return listDis;
    }
    
    
}
