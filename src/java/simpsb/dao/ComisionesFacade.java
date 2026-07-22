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
import simpsb.entidades.Comisiones;
import simpsb.entidades.Empleado;

/**
 *
 * @author Leonardo Lara
 */
@Stateless
public class ComisionesFacade extends AbstractFacade<Comisiones> implements ComisionesFacadeLocal {

    @PersistenceContext(unitName = "SIMPSB1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComisionesFacade() {
        super(Comisiones.class);
    }

    @Override
    public List<Comisiones> listarPagos(int idEmp) {
        List<Comisiones> lista = null;
        try {
            Query query = em.createQuery("SELECT p FROM Comisiones p WHERE p.idEmpleado.idEmpleado = :idEmp");
            query.setParameter("idEmp", idEmp);
            lista = query.getResultList();
            if (!lista.isEmpty()) {
                lista.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return lista;
    }
    
}
