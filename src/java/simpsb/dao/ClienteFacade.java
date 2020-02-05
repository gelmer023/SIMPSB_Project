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
import simpsb.entidades.Cliente;

/**
 *
 * @author Sebastián
 */
@Stateless
public class ClienteFacade extends AbstractFacade<Cliente> implements ClienteFacadeLocal {

    @PersistenceContext(unitName = "SIMPSB1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(Cliente.class);
    }
    
    public Cliente getIdCl(Object idUs) {
       Cliente cl = new Cliente();
       try {
           Query query = em.createQuery("SELECT c FROM Cliente c WHERE c.idUsuario.idUsuario = :idUs");
           query.setParameter("idUs", idUs);
           List<Cliente> lista = query.getResultList();
           if (!lista.isEmpty()) {
               cl = lista.get(0);
           }
       } catch (Exception e) {
           throw e;
       }
       return cl;
   }
    
}
