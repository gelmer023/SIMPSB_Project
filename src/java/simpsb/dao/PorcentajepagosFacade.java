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
     public List<Porcentajepagos> pagoE(Porcentajepagos pg) {
        List<Porcentajepagos> pagosEmp = null;
        try {
            Query query = em.createQuery("SELECT p FROM Porcentajepagos p WHERE p.fecha >= :fechaI");
            query.setParameter("fechaI", (pg.getFecha()));
            pagosEmp = query.getResultList();
            if (!pagosEmp.isEmpty()) {
                pagosEmp.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return pagosEmp;
    }
    
    
}
