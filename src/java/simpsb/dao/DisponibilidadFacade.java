/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpsb.dao;

import java.util.Date;
import java.util.List;
import java.util.Spliterator;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import simpsb.entidades.Citas;
import simpsb.entidades.Disponibilidad;
import simpsb.entidades.Horas;

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
    @EJB
    DisponibilidadFacadeLocal disponibilidadFacadeLocal;
    @EJB
    CitasFacadeLocal citasFacadeLocal;

    public List<Horas> disponibles() {
        List<Horas> lista = null;
        List<Disponibilidad> listDis = null;
        List<Citas> listCitas = null;
        listDis = disponibilidadFacadeLocal.findAll();
        listCitas = citasFacadeLocal.findAll();
        //Hago la validación de que si no hay citas activas le salgan todas las horas 
        for (Citas cita : listCitas) {
            Date fechaCita = cita.getFecha();
            for (Disponibilidad dis : listDis) {
                Date fechaDis = dis.getFecha();
                if (fechaDis == fechaCita) {
                    try {
                        Query query = em.createQuery("SELECT h FROM Horas h INNER JOIN h.disponibilidadList d WHERE d.estado = :estado");
                        query.setParameter("estado", "Disponible");
                        lista = query.getResultList();
                    } catch (Exception e) {
                        throw e;
                    }
                } else {
                    try {
                        Query query = em.createQuery("SELECT h FROM Horas h");
                        lista = query.getResultList();
                    } catch (Exception e) {
                        throw e;
                    }
                }
            }

        }
        return lista;
    }
}
