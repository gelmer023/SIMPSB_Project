/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpsb.dao;

import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    @EJB
    HorasFacadeLocal horasFacadeLocal;

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
        List<Horas> listHor = horasFacadeLocal.findAll();
        List<Disponibilidad> lista = disponibilidadFacadeLocal.findAll();
        try {
            for (Disponibilidad disp : lista) {
                Date dfech = disp.getFecha();
                int id = disp.getHoraFK().getIdHoras();
                for (Horas hor : listHor) {
                    int idH = hor.getIdHoras();
                        if (id == idH) {
                            if (dfech.equals(ct.getFecha())) {
                                Query query = em.createQuery("SELECT h from Horas h WHERE h.idHoras != :hora");
                                query.setParameter("hora", idH);
                                listDis = query.getResultList();
                                if (!listDis.isEmpty()) {
                                    listDis.get(0);
                                }
                            }
                    }
                }
            }

        } catch (Exception e) {
            throw e;
        }
        return listDis;
    }
}
