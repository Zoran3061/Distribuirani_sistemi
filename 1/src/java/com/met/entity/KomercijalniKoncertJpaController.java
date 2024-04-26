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
public class KomercijalniKoncertJpaController implements Serializable {

    public KomercijalniKoncertJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(KomercijalniKoncert komercijalniKoncert) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Ugovor ugovorId = komercijalniKoncert.getUgovorId();
            if (ugovorId != null) {
                ugovorId = em.getReference(ugovorId.getClass(), ugovorId.getUgovorId());
                komercijalniKoncert.setUgovorId(ugovorId);
            }
            Koncert koncertId = komercijalniKoncert.getKoncertId();
            if (koncertId != null) {
                koncertId = em.getReference(koncertId.getClass(), koncertId.getKoncertId());
                komercijalniKoncert.setKoncertId(koncertId);
            }
            em.persist(komercijalniKoncert);
            if (ugovorId != null) {
                ugovorId.getKomercijalniKoncertCollection().add(komercijalniKoncert);
                ugovorId = em.merge(ugovorId);
            }
            if (koncertId != null) {
                koncertId.getKomercijalniKoncertCollection().add(komercijalniKoncert);
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

    public void edit(KomercijalniKoncert komercijalniKoncert) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            KomercijalniKoncert persistentKomercijalniKoncert = em.find(KomercijalniKoncert.class, komercijalniKoncert.getKomercijalniId());
            Ugovor ugovorIdOld = persistentKomercijalniKoncert.getUgovorId();
            Ugovor ugovorIdNew = komercijalniKoncert.getUgovorId();
            Koncert koncertIdOld = persistentKomercijalniKoncert.getKoncertId();
            Koncert koncertIdNew = komercijalniKoncert.getKoncertId();
            if (ugovorIdNew != null) {
                ugovorIdNew = em.getReference(ugovorIdNew.getClass(), ugovorIdNew.getUgovorId());
                komercijalniKoncert.setUgovorId(ugovorIdNew);
            }
            if (koncertIdNew != null) {
                koncertIdNew = em.getReference(koncertIdNew.getClass(), koncertIdNew.getKoncertId());
                komercijalniKoncert.setKoncertId(koncertIdNew);
            }
            komercijalniKoncert = em.merge(komercijalniKoncert);
            if (ugovorIdOld != null && !ugovorIdOld.equals(ugovorIdNew)) {
                ugovorIdOld.getKomercijalniKoncertCollection().remove(komercijalniKoncert);
                ugovorIdOld = em.merge(ugovorIdOld);
            }
            if (ugovorIdNew != null && !ugovorIdNew.equals(ugovorIdOld)) {
                ugovorIdNew.getKomercijalniKoncertCollection().add(komercijalniKoncert);
                ugovorIdNew = em.merge(ugovorIdNew);
            }
            if (koncertIdOld != null && !koncertIdOld.equals(koncertIdNew)) {
                koncertIdOld.getKomercijalniKoncertCollection().remove(komercijalniKoncert);
                koncertIdOld = em.merge(koncertIdOld);
            }
            if (koncertIdNew != null && !koncertIdNew.equals(koncertIdOld)) {
                koncertIdNew.getKomercijalniKoncertCollection().add(komercijalniKoncert);
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
                Integer id = komercijalniKoncert.getKomercijalniId();
                if (findKomercijalniKoncert(id) == null) {
                    throw new NonexistentEntityException("The komercijalniKoncert with id " + id + " no longer exists.");
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
            KomercijalniKoncert komercijalniKoncert;
            try {
                komercijalniKoncert = em.getReference(KomercijalniKoncert.class, id);
                komercijalniKoncert.getKomercijalniId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The komercijalniKoncert with id " + id + " no longer exists.", enfe);
            }
            Ugovor ugovorId = komercijalniKoncert.getUgovorId();
            if (ugovorId != null) {
                ugovorId.getKomercijalniKoncertCollection().remove(komercijalniKoncert);
                ugovorId = em.merge(ugovorId);
            }
            Koncert koncertId = komercijalniKoncert.getKoncertId();
            if (koncertId != null) {
                koncertId.getKomercijalniKoncertCollection().remove(komercijalniKoncert);
                koncertId = em.merge(koncertId);
            }
            em.remove(komercijalniKoncert);
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

    public List<KomercijalniKoncert> findKomercijalniKoncertEntities() {
        return findKomercijalniKoncertEntities(true, -1, -1);
    }

    public List<KomercijalniKoncert> findKomercijalniKoncertEntities(int maxResults, int firstResult) {
        return findKomercijalniKoncertEntities(false, maxResults, firstResult);
    }

    private List<KomercijalniKoncert> findKomercijalniKoncertEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(KomercijalniKoncert.class));
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

    public KomercijalniKoncert findKomercijalniKoncert(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(KomercijalniKoncert.class, id);
        } finally {
            em.close();
        }
    }

    public int getKomercijalniKoncertCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<KomercijalniKoncert> rt = cq.from(KomercijalniKoncert.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
