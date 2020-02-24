/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpsb.dao;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import simpsb.entidades.Citas;
import simpsb.entidades.Disponibilidad;
import simpsb.entidades.Horas;

/**
 *
 * @author Leonardo Lara
 */
@Stateless
public class DisponibilidadFacade extends AbstractFacade<Disponibilidad> implements DisponibilidadFacadeLocal {
    
    @EJB
    DisponibilidadFacadeLocal disponibilidadFacadeLocal;
    
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
    public List<Horas> disponibles(Citas ct) {
        List<Horas> listDis = null;

        try {
            List<Disponibilidad> lista = disponibilidadFacadeLocal.findAll();
            if (!lista.isEmpty()) {
                Query query = em.createQuery("SELECT h from Horas h INNER JOIN h.disponibilidadList d WHERE d.estado = :estado AND d.fecha = :fecha");
                query.setParameter("estado", "Disponible");
                query.setParameter("fecha", ct.getFecha());
                listDis = query.getResultList();
                if (!listDis.isEmpty()) {
                    listDis.get(0);
                }
            } else {
                Query query = em.createQuery("SELECT h from Horas h");
                listDis = query.getResultList();
                if (!listDis.isEmpty()) {
                    listDis.get(0);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return listDis;
    }
}
