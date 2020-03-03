/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpsb.dao;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import simpsb.entidades.Porcentajepagos;

/**
 *
 * @author Leonardo Lara
 */
@Stateless
public class PorcentajepagosFacade extends AbstractFacade<Porcentajepagos> implements PorcentajepagosFacadeLocal {

    @PersistenceContext(unitName = "SIMPSB1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PorcentajepagosFacade() {
        super(Porcentajepagos.class);
    }

    @Override
    public List<Porcentajepagos> calcularPago(int idEmp, Date fechaI, Date fechaF) {
        List<Porcentajepagos> listPorc = null;
        try {
            Query q = em.createQuery("SELECT e FROM Empleado e INNER JOIN e.porcentajepagosList p WHERE p.idEmpleadoFK = :idEmp AND p.fecha >= :fechaI AND p.fecha <= :fechaF");
            q.setParameter("fechaI", fechaI);
            q.setParameter("fechaF", fechaF);
            q.setParameter("idEmp", idEmp);
            listPorc = q.getResultList();
            if (!listPorc.isEmpty()) {
                listPorc.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return listPorc;
    }

}
