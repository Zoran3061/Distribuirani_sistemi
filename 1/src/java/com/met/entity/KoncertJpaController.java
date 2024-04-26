/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.met.entity;

import com.met.entity.exceptions.NonexistentEntityException;
import com.met.entity.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Zoran
 */
public class KoncertJpaController implements Serializable {

    public KoncertJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Koncert koncert) throws RollbackFailureException, Exception {
        if (koncert.getHumanitarniKoncertCollection() == null) {
            koncert.setHumanitarniKoncertCollection(new ArrayList<HumanitarniKoncert>());
        }
        if (koncert.getKomercijalniKoncertCollection() == null) {
            koncert.setKomercijalniKoncertCollection(new ArrayList<KomercijalniKoncert>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<HumanitarniKoncert> attachedHumanitarniKoncertCollection = new ArrayList<HumanitarniKoncert>();
            for (HumanitarniKoncert humanitarniKoncertCollectionHumanitarniKoncertToAttach : koncert.getHumanitarniKoncertCollection()) {
                humanitarniKoncertCollectionHumanitarniKoncertToAttach = em.getReference(humanitarniKoncertCollectionHumanitarniKoncertToAttach.getClass(), humanitarniKoncertCollectionHumanitarniKoncertToAttach.getHumanitarniId());
                attachedHumanitarniKoncertCollection.add(humanitarniKoncertCollectionHumanitarniKoncertToAttach);
            }
            koncert.setHumanitarniKoncertCollection(attachedHumanitarniKoncertCollection);
            Collection<KomercijalniKoncert> attachedKomercijalniKoncertCollection = new ArrayList<KomercijalniKoncert>();
            for (KomercijalniKoncert komercijalniKoncertCollectionKomercijalniKoncertToAttach : koncert.getKomercijalniKoncertCollection()) {
                komercijalniKoncertCollectionKomercijalniKoncertToAttach = em.getReference(komercijalniKoncertCollectionKomercijalniKoncertToAttach.getClass(), komercijalniKoncertCollectionKomercijalniKoncertToAttach.getKomercijalniId());
                attachedKomercijalniKoncertCollection.add(komercijalniKoncertCollectionKomercijalniKoncertToAttach);
            }
            koncert.setKomercijalniKoncertCollection(attachedKomercijalniKoncertCollection);
            em.persist(koncert);
            for (HumanitarniKoncert humanitarniKoncertCollectionHumanitarniKoncert : koncert.getHumanitarniKoncertCollection()) {
                Koncert oldKoncertIdOfHumanitarniKoncertCollectionHumanitarniKoncert = humanitarniKoncertCollectionHumanitarniKoncert.getKoncertId();
                humanitarniKoncertCollectionHumanitarniKoncert.setKoncertId(koncert);
                humanitarniKoncertCollectionHumanitarniKoncert = em.merge(humanitarniKoncertCollectionHumanitarniKoncert);
                if (oldKoncertIdOfHumanitarniKoncertCollectionHumanitarniKoncert != null) {
                    oldKoncertIdOfHumanitarniKoncertCollectionHumanitarniKoncert.getHumanitarniKoncertCollection().remove(humanitarniKoncertCollectionHumanitarniKoncert);
                    oldKoncertIdOfHumanitarniKoncertCollectionHumanitarniKoncert = em.merge(oldKoncertIdOfHumanitarniKoncertCollectionHumanitarniKoncert);
                }
            }
            for (KomercijalniKoncert komercijalniKoncertCollectionKomercijalniKoncert : koncert.getKomercijalniKoncertCollection()) {
                Koncert oldKoncertIdOfKomercijalniKoncertCollectionKomercijalniKoncert = komercijalniKoncertCollectionKomercijalniKoncert.getKoncertId();
                komercijalniKoncertCollectionKomercijalniKoncert.setKoncertId(koncert);
                komercijalniKoncertCollectionKomercijalniKoncert = em.merge(komercijalniKoncertCollectionKomercijalniKoncert);
                if (oldKoncertIdOfKomercijalniKoncertCollectionKomercijalniKoncert != null) {
                    oldKoncertIdOfKomercijalniKoncertCollectionKomercijalniKoncert.getKomercijalniKoncertCollection().remove(komercijalniKoncertCollectionKomercijalniKoncert);
                    oldKoncertIdOfKomercijalniKoncertCollectionKomercijalniKoncert = em.merge(oldKoncertIdOfKomercijalniKoncertCollectionKomercijalniKoncert);
                }
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

    public void edit(Koncert koncert) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Koncert persistentKoncert = em.find(Koncert.class, koncert.getKoncertId());
            Collection<HumanitarniKoncert> humanitarniKoncertCollectionOld = persistentKoncert.getHumanitarniKoncertCollection();
            Collection<HumanitarniKoncert> humanitarniKoncertCollectionNew = koncert.getHumanitarniKoncertCollection();
            Collection<KomercijalniKoncert> komercijalniKoncertCollectionOld = persistentKoncert.getKomercijalniKoncertCollection();
            Collection<KomercijalniKoncert> komercijalniKoncertCollectionNew = koncert.getKomercijalniKoncertCollection();
            Collection<HumanitarniKoncert> attachedHumanitarniKoncertCollectionNew = new ArrayList<HumanitarniKoncert>();
            for (HumanitarniKoncert humanitarniKoncertCollectionNewHumanitarniKoncertToAttach : humanitarniKoncertCollectionNew) {
                humanitarniKoncertCollectionNewHumanitarniKoncertToAttach = em.getReference(humanitarniKoncertCollectionNewHumanitarniKoncertToAttach.getClass(), humanitarniKoncertCollectionNewHumanitarniKoncertToAttach.getHumanitarniId());
                attachedHumanitarniKoncertCollectionNew.add(humanitarniKoncertCollectionNewHumanitarniKoncertToAttach);
            }
            humanitarniKoncertCollectionNew = attachedHumanitarniKoncertCollectionNew;
            koncert.setHumanitarniKoncertCollection(humanitarniKoncertCollectionNew);
            Collection<KomercijalniKoncert> attachedKomercijalniKoncertCollectionNew = new ArrayList<KomercijalniKoncert>();
            for (KomercijalniKoncert komercijalniKoncertCollectionNewKomercijalniKoncertToAttach : komercijalniKoncertCollectionNew) {
                komercijalniKoncertCollectionNewKomercijalniKoncertToAttach = em.getReference(komercijalniKoncertCollectionNewKomercijalniKoncertToAttach.getClass(), komercijalniKoncertCollectionNewKomercijalniKoncertToAttach.getKomercijalniId());
                attachedKomercijalniKoncertCollectionNew.add(komercijalniKoncertCollectionNewKomercijalniKoncertToAttach);
            }
            komercijalniKoncertCollectionNew = attachedKomercijalniKoncertCollectionNew;
            koncert.setKomercijalniKoncertCollection(komercijalniKoncertCollectionNew);
            koncert = em.merge(koncert);
            for (HumanitarniKoncert humanitarniKoncertCollectionOldHumanitarniKoncert : humanitarniKoncertCollectionOld) {
                if (!humanitarniKoncertCollectionNew.contains(humanitarniKoncertCollectionOldHumanitarniKoncert)) {
                    humanitarniKoncertCollectionOldHumanitarniKoncert.setKoncertId(null);
                    humanitarniKoncertCollectionOldHumanitarniKoncert = em.merge(humanitarniKoncertCollectionOldHumanitarniKoncert);
                }
            }
            for (HumanitarniKoncert humanitarniKoncertCollectionNewHumanitarniKoncert : humanitarniKoncertCollectionNew) {
                if (!humanitarniKoncertCollectionOld.contains(humanitarniKoncertCollectionNewHumanitarniKoncert)) {
                    Koncert oldKoncertIdOfHumanitarniKoncertCollectionNewHumanitarniKoncert = humanitarniKoncertCollectionNewHumanitarniKoncert.getKoncertId();
                    humanitarniKoncertCollectionNewHumanitarniKoncert.setKoncertId(koncert);
                    humanitarniKoncertCollectionNewHumanitarniKoncert = em.merge(humanitarniKoncertCollectionNewHumanitarniKoncert);
                    if (oldKoncertIdOfHumanitarniKoncertCollectionNewHumanitarniKoncert != null && !oldKoncertIdOfHumanitarniKoncertCollectionNewHumanitarniKoncert.equals(koncert)) {
                        oldKoncertIdOfHumanitarniKoncertCollectionNewHumanitarniKoncert.getHumanitarniKoncertCollection().remove(humanitarniKoncertCollectionNewHumanitarniKoncert);
                        oldKoncertIdOfHumanitarniKoncertCollectionNewHumanitarniKoncert = em.merge(oldKoncertIdOfHumanitarniKoncertCollectionNewHumanitarniKoncert);
                    }
                }
            }
            for (KomercijalniKoncert komercijalniKoncertCollectionOldKomercijalniKoncert : komercijalniKoncertCollectionOld) {
                if (!komercijalniKoncertCollectionNew.contains(komercijalniKoncertCollectionOldKomercijalniKoncert)) {
                    komercijalniKoncertCollectionOldKomercijalniKoncert.setKoncertId(null);
                    komercijalniKoncertCollectionOldKomercijalniKoncert = em.merge(komercijalniKoncertCollectionOldKomercijalniKoncert);
                }
            }
            for (KomercijalniKoncert komercijalniKoncertCollectionNewKomercijalniKoncert : komercijalniKoncertCollectionNew) {
                if (!komercijalniKoncertCollectionOld.contains(komercijalniKoncertCollectionNewKomercijalniKoncert)) {
                    Koncert oldKoncertIdOfKomercijalniKoncertCollectionNewKomercijalniKoncert = komercijalniKoncertCollectionNewKomercijalniKoncert.getKoncertId();
                    komercijalniKoncertCollectionNewKomercijalniKoncert.setKoncertId(koncert);
                    komercijalniKoncertCollectionNewKomercijalniKoncert = em.merge(komercijalniKoncertCollectionNewKomercijalniKoncert);
                    if (oldKoncertIdOfKomercijalniKoncertCollectionNewKomercijalniKoncert != null && !oldKoncertIdOfKomercijalniKoncertCollectionNewKomercijalniKoncert.equals(koncert)) {
                        oldKoncertIdOfKomercijalniKoncertCollectionNewKomercijalniKoncert.getKomercijalniKoncertCollection().remove(komercijalniKoncertCollectionNewKomercijalniKoncert);
                        oldKoncertIdOfKomercijalniKoncertCollectionNewKomercijalniKoncert = em.merge(oldKoncertIdOfKomercijalniKoncertCollectionNewKomercijalniKoncert);
                    }
                }
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
                Integer id = koncert.getKoncertId();
                if (findKoncert(id) == null) {
                    throw new NonexistentEntityException("The koncert with id " + id + " no longer exists.");
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
            Koncert koncert;
            try {
                koncert = em.getReference(Koncert.class, id);
                koncert.getKoncertId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The koncert with id " + id + " no longer exists.", enfe);
            }
            Collection<HumanitarniKoncert> humanitarniKoncertCollection = koncert.getHumanitarniKoncertCollection();
            for (HumanitarniKoncert humanitarniKoncertCollectionHumanitarniKoncert : humanitarniKoncertCollection) {
                humanitarniKoncertCollectionHumanitarniKoncert.setKoncertId(null);
                humanitarniKoncertCollectionHumanitarniKoncert = em.merge(humanitarniKoncertCollectionHumanitarniKoncert);
            }
            Collection<KomercijalniKoncert> komercijalniKoncertCollection = koncert.getKomercijalniKoncertCollection();
            for (KomercijalniKoncert komercijalniKoncertCollectionKomercijalniKoncert : komercijalniKoncertCollection) {
                komercijalniKoncertCollectionKomercijalniKoncert.setKoncertId(null);
                komercijalniKoncertCollectionKomercijalniKoncert = em.merge(komercijalniKoncertCollectionKomercijalniKoncert);
            }
            em.remove(koncert);
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

    public List<Koncert> findKoncertEntities() {
        return findKoncertEntities(true, -1, -1);
    }

    public List<Koncert> findKoncertEntities(int maxResults, int firstResult) {
        return findKoncertEntities(false, maxResults, firstResult);
    }

    private List<Koncert> findKoncertEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Koncert.class));
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

    public Koncert findKoncert(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Koncert.class, id);
        } finally {
            em.close();
        }
    }

    public int getKoncertCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Koncert> rt = cq.from(Koncert.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
