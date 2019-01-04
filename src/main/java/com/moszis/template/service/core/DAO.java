package com.moszis.template.service.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//TODO: Methods here need to be refactored.

@Stateless
public class DAO<T> {

    protected static Log log = LogFactory.getLog(DAO.class);

    @PersistenceContext(unitName = "testjpa")
    protected EntityManager entityManager;
    

    protected Class<T> type;

    /**
     * Instantiates a new Repository.
     */
    public DAO() {
		super();
	}

    /**
     * Instantiates a new Repository.
     *
     * @param type the type
     */
    public DAO(Class<T> type) {
		this.type = type;
	}

    /**
     * Find t.
     *
     * @param key the key
     * @return the t
     */
    @SuppressWarnings("unchecked")
	public T find(UUID key) {
		Query q = entityManager.createQuery("select t from " + type.getSimpleName() + " t where t.id =:id");
		q.setParameter("id", key);
		List<UUID> list = q.getResultList();
		if (!list.isEmpty()) {
			return (T) list.get(0);
		}
		return null;
	}

    /**
     * Get all list.
     *
     * @return the list
     */
    @SuppressWarnings("unchecked")
	public List<T> getAll() {

	   	try{
		   	
			Query query = entityManager.createQuery("from " + type.getSimpleName());

		   	List<T> results = query.getResultList();
		   	
		   	return results;
	   	
		}catch (NoResultException nre){
			return new ArrayList<T>();
		}catch (Exception e){
		    throw new RuntimeException(e);
		}
	}

    /**
     * Merge t.
     *
     * @param target the target
     * @return the t
     */
    public T merge(T target) {
		return entityManager.merge(target);
	}

    /**
     * Persist.
     *
     * @param target the target
     */
    public void persist(T target) {
		entityManager.persist(target);
	}

    /**
     * Remove.
     *
     * @param target the target
     */
    public void remove(T target) {
		entityManager.remove(target);
	}

    /**
     * Detach.
     *
     * @param target the target
     */
    public void detach(T target) {
		entityManager.detach(target);
	}

    /**
     * Flush.
     */
    public void flush() {
		entityManager.flush();
	}

    /**
     * Exists boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public boolean exists(UUID key) {
		Query q = entityManager.createQuery("select t.id from " + type.getSimpleName() + " t where t.id =:id");
		q.setParameter("id", key);
		@SuppressWarnings("unchecked")
		List<UUID> exists = q.getResultList();

		return !exists.isEmpty();
	}

    /**
     * Delete.
     *
     * @param key the key
     */
    public void delete(UUID key) {
		Query q = entityManager.createQuery("delete from " + type.getSimpleName() + " t where t.id =:id");
		q.setParameter("id", key);
		q.executeUpdate();
	}

    /**
     * Enabled boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public boolean isEnabled(UUID key) {
		try {
			Query q = entityManager.createQuery("select t.id from " + type.getSimpleName() + " t where t.id =:id and t.enabledInd = 1");
			q.setParameter("id", key);
			@SuppressWarnings("unchecked")
			List<UUID> exists = q.getResultList();
			return !exists.isEmpty();
		} catch (Exception e) {
			log.error("The entity " + type.getSimpleName() + " may not have an ENABLED_IND column :" + e.getMessage());
			return true;
		}
	}

}
