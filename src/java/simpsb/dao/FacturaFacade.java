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
import simpsb.entidades.Factura;

/**
 *
 * @author Leonardo Lara
 */
@Stateless
public class FacturaFacade extends AbstractFacade<Factura> implements FacturaFacadeLocal {

    @PersistenceContext(unitName = "SIMPSB1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FacturaFacade() {
        super(Factura.class);
    }
    
    @Override
    public List<Factura> validezFactura(Factura ft) {
        List<Factura> listFac = null;
        try {
            Query query = em.createQuery("SELECT c FROM Citas INNER JOIN c.Factura f WHERE c.idCita = f.idCita");
            query.setParameter("idCita", ft);
            listFac = query.getResultList();
            if (!listFac.isEmpty()) {
                listFac.get(0);
            }
        } catch (Exception e) {
            throw e;

        }
        return listFac;
    }
}
