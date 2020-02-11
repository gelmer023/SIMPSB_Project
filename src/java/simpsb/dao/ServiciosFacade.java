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
import simpsb.entidades.Servicios;
import static simpsb.entidades.Servicios_.valor;

/**
 *
 * @author Sebastián
 */
@Stateless
public class ServiciosFacade extends AbstractFacade<Servicios> implements ServiciosFacadeLocal {

    @PersistenceContext(unitName = "SIMPSB1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ServiciosFacade() {
        super(Servicios.class);
    }
    
    @Override
     public List<Servicios> servActivos() {
        List<Servicios> serviciosActivos = null;
        try {
            Query query = em.createQuery("SELECT s FROM Servicios s WHERE s.estado = :estado");
            query.setParameter("estado", ("Activo"));
            List<Servicios> listServs = query.getResultList();
            if (!listServs.isEmpty()) {
                serviciosActivos = query.getResultList();
            }
        } catch (Exception e) {
            throw e;
        }
        return serviciosActivos;
    }
    
      public Servicios getValor(Servicios serv){
           Servicios sv = new Servicios();
           try {
               Query query = em.createQuery("SELECT s FROM servicios s INNER JOIN citas c c.idCita = s.idServicio WHERE  AND s.valor = :valor");
               query.setParameter("valor", valor);
               List<Servicios> listServ = query.getResultList();
               if (!listServ.isEmpty()) {
                   sv = listServ.get(0);
               }
           } catch (Exception e){
               throw e;
           }
        return sv;
       }
}
