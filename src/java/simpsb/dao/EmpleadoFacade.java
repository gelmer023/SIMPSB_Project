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
import simpsb.entidades.Empleado;

/**
 *
 * @author Leonardo Lara
 */
@Stateless
public class EmpleadoFacade extends AbstractFacade<Empleado> implements EmpleadoFacadeLocal {

    @PersistenceContext(unitName = "SIMPSB1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpleadoFacade() {
        super(Empleado.class);
    }
    
    @Override
    public Empleado getIdEmp(int idUs) {
        Empleado emp = new Empleado();
        try {
            Query query = em.createQuery("SELECT e FROM Empleado e WHERE e.idUsuario.idUsuario = :idUs");
            query.setParameter("idUs", idUs);
            List<Empleado> lista = query.getResultList();
            if (!lista.isEmpty()) {
                emp = lista.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return emp;
    }

    @Override
    public List<Empleado> dataEmp(int idEmp) {
        List<Empleado> lista = null;
        try {
            Query query = em.createQuery("SELECT e FROM Diadescanso d INNER JOIN d.empleadoList e INNER JOIN e.idHorarioTrabajo h WHERE e.idEmpleado = :idEmp");
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
