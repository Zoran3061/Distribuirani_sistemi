/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.met.entity;

import com.met.entity.exceptions.NonexistentEntityException;
import com.met.entity.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author Zoran
 */
public class HumanitarniKoncertJpaController implements Serializable {

    public HumanitarniKoncertJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HumanitarniKoncert humanitarniKoncert) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Koncert koncertId = humanitarniKoncert.getKoncertId();
            if (koncertId != null) {
                koncertId = em.getReference(koncertId.getClass(), koncertId.getKoncertId());
                humanitarniKoncert.setKoncertId(koncertId);
            }
            em.persist(humanitarniKoncert);
            if (koncertId != null) {
                koncertId.getHumanitarniKoncertCollection().add(humanitarniKoncert);
                koncertId = em.merge(koncertId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HumanitarniKoncert humanitarniKoncert) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            HumanitarniKoncert persistentHumanitarniKoncert = em.find(HumanitarniKoncert.class, humanitarniKoncert.getHumanitarniId());
            Koncert koncertIdOld = persistentHumanitarniKoncert.getKoncertId();
            Koncert koncertIdNew = humanitarniKoncert.getKoncertId();
            if (koncertIdNew != null) {
                koncertIdNew = em.getReference(koncertIdNew.getClass(), koncertIdNew.getKoncertId());
                humanitarniKoncert.setKoncertId(koncertIdNew);
            }
            humanitarniKoncert = em.merge(humanitarniKoncert);
            if (koncertIdOld != null && !koncertIdOld.equals(koncertIdNew)) {
                koncertIdOld.getHumanitarniKoncertCollection().remove(humanitarniKoncert);
                koncertIdOld = em.merge(koncertIdOld);
            }
            if (koncertIdNew != null && !koncertIdNew.equals(koncertIdOld)) {
                koncertIdNew.getHumanitarniKoncertCollection().add(humanitarniKoncert);
                koncertIdNew = em.merge(koncertIdNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = humanitarniKoncert.getHumanitarniId();
                if (findHumanitarniKoncert(id) == null) {
                    throw new NonexistentEntityException("The humanitarniKoncert with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            HumanitarniKoncert humanitarniKoncert;
            try {
                humanitarniKoncert = em.getReference(HumanitarniKoncert.class, id);
                humanitarniKoncert.getHumanitarniId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The humanitarniKoncert with id " + id + " no longer exists.", enfe);
            }
            Koncert koncertId = humanitarniKoncert.getKoncertId();
            if (koncertId != null) {
                koncertId.getHumanitarniKoncertCollection().remove(humanitarniKoncert);
                koncertId = em.merge(koncertId);
            }
            em.remove(humanitarniKoncert);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HumanitarniKoncert> findHumanitarniKoncertEntities() {
        return findHumanitarniKoncertEntities(true, -1, -1);
    }

    public List<HumanitarniKoncert> findHumanitarniKoncertEntities(int maxResults, int firstResult) {
        return findHumanitarniKoncertEntities(false, maxResults, firstResult);
    }

    private List<HumanitarniKoncert> findHumanitarniKoncertEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HumanitarniKoncert.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public HumanitarniKoncert findHumanitarniKoncert(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HumanitarniKoncert.class, id);
        } finally {
            em.close();
        }
    }

    public int getHumanitarniKoncertCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HumanitarniKoncert> rt = cq.from(HumanitarniKoncert.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
