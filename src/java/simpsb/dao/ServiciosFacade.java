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
            serviciosActivos = query.getResultList();
            if (!serviciosActivos.isEmpty()) {
                serviciosActivos.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return serviciosActivos;
    }
    
     @Override
      public Servicios getValor(){
           Servicios sv = new Servicios();
           List<Servicios> listSer= null;
           try {
               Query query = em.createQuery("SELECT s FROM Servicios s INNER JOIN s.citasList c WHERE  c.idServicio.idServicio = s.idServicio");
               listSer = query.getResultList();
               if (!listSer.isEmpty()) {
                    sv = listSer.get(0);
               }
           } catch (Exception e){
               throw e;
           }
        return sv;
       }
}
