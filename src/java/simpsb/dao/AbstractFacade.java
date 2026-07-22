package simpsb.dao;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Clase abstracta base para todos los Facades (DAOs).
 * Proporciona operaciones CRUD genéricas.
 * 
 * @author Sistema SIMPSB
 * @version 1.0
 * @param <T> tipo de entidad
 */
public abstract class AbstractFacade<T> {

    private static final Logger LOGGER = Logger.getLogger(AbstractFacade.class.getName());
    private final Class<T> entityClass;

    /**
     * Constructor que inicializa la clase de entidad.
     * 
     * @param entityClass clase de la entidad
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Obtiene el EntityManager de la implementación.
     * 
     * @return EntityManager
     */
    protected abstract EntityManager getEntityManager();

    /**
     * Crea una nueva entidad en la base de datos.
     * 
     * @param entity entidad a crear
     * @throws Exception si ocurre un error
     */
    public void create(T entity) throws Exception {
        try {
            getEntityManager().persist(entity);
            LOGGER.log(Level.FINE, "Entidad creada: " + entityClass.getSimpleName());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al crear entidad", e);
            throw e;
        }
    }

    /**
     * Actualiza una entidad existente.
     * 
     * @param entity entidad a actualizar
     * @return entidad actualizada
     * @throws Exception si ocurre un error
     */
    public T edit(T entity) throws Exception {
        try {
            T merged = getEntityManager().merge(entity);
            LOGGER.log(Level.FINE, "Entidad actualizada: " + entityClass.getSimpleName());
            return merged;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar entidad", e);
            throw e;
        }
    }

    /**
     * Elimina una entidad de la base de datos.
     * 
     * @param entity entidad a eliminar
     * @throws Exception si ocurre un error
     */
    public void remove(T entity) throws Exception {
        try {
            T merged = getEntityManager().merge(entity);
            getEntityManager().remove(merged);
            LOGGER.log(Level.FINE, "Entidad eliminada: " + entityClass.getSimpleName());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar entidad", e);
            throw e;
        }
    }

    /**
     * Busca una entidad por su ID.
     * 
     * @param id ID de la entidad
     * @return la entidad si existe, null en caso contrario
     */
    public T find(Object id) {
        try {
            T result = getEntityManager().find(entityClass, id);
            if (result == null) {
                LOGGER.log(Level.FINE, "Entidad no encontrada con ID: " + id);
            }
            return result;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error al buscar entidad por ID", e);
            return null;
        }
    }

    /**
     * Obtiene todas las entidades de la clase.
     * 
     * @return lista de todas las entidades
     */
    public List<T> findAll() {
        try {
            CriteriaQuery<T> cq = buildCriteriaQuery();
            cq.select(cq.from(entityClass));
            return getEntityManager().createQuery(cq).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error al obtener todas las entidades", e);
            return null;
        }
    }

    /**
     * Obtiene un rango de entidades con paginación.
     * 
     * @param range array con [inicio, fin]
     * @return lista de entidades en el rango
     */
    public List<T> findRange(int[] range) {
        try {
            if (range == null || range.length != 2 || range[0] < 0 || range[1] < range[0]) {
                throw new IllegalArgumentException("Rango inválido");
            }
            
            CriteriaQuery<T> cq = buildCriteriaQuery();
            cq.select(cq.from(entityClass));
            Query q = getEntityManager().createQuery(cq);
            q.setMaxResults(range[1] - range[0] + 1);
            q.setFirstResult(range[0]);
            return q.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error al obtener rango de entidades", e);
            return null;
        }
    }

    /**
     * Cuenta el número total de entidades.
     * 
     * @return cantidad de entidades
     */
    public int count() {
        try {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<T> rt = cq.from(entityClass);
            cq.select(cb.count(rt));
            Query q = getEntityManager().createQuery(cq);
            Long result = (Long) q.getSingleResult();
            return result != null ? result.intValue() : 0;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error al contar entidades", e);
            return 0;
        }
    }

    /**
     * Construye una CriteriaQuery genérica.
     * 
     * @return CriteriaQuery
     */
    private CriteriaQuery<T> buildCriteriaQuery() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        return cb.createQuery(entityClass);
    }
}
