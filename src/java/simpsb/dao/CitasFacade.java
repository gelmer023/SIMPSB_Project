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
import static simpsb.entidades.Servicios_.valor;

/**
 *
 * @author Sebastián
 */
@Stateless
public class CitasFacade extends AbstractFacade<Citas> implements CitasFacadeLocal {

    @PersistenceContext(unitName = "SIMPSB1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CitasFacade() {
        super(Citas.class);
    }

    @Override
    public List<Citas> citasCli(Cliente idCli) {
        List<Citas> listaCitas = null;
        try {
            Query query = em.createQuery("SELECT c FROM Citas c WHERE c.idCliente.idUsuario = :user");
            query.setParameter("user", idCli);
            listaCitas = query.getResultList();
            if (!listaCitas.isEmpty()) {
                listaCitas.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return listaCitas;
    }
        @Override
       public Citas getIdCita(Object idCit) {
        Citas cit = new Citas();
        try {
            Query query = em.createQuery("SELECT c FROM citas c WHERE c.idCita = :idCit");
            query.setParameter("idCita", idCit);
            List<Citas> lista = query.getResultList();
            if (!lista.isEmpty()) {
                cit = lista.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return cit;
    }
       
}
