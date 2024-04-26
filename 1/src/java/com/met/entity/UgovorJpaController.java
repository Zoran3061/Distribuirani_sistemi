/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.met.entity;

import com.met.entity.exceptions.IllegalOrphanException;
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
public class UgovorJpaController implements Serializable {

    public UgovorJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ugovor ugovor) throws RollbackFailureException, Exception {
        if (ugovor.getKomercijalniKoncertCollection() == null) {
            ugovor.setKomercijalniKoncertCollection(new ArrayList<KomercijalniKoncert>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<KomercijalniKoncert> attachedKomercijalniKoncertCollection = new ArrayList<KomercijalniKoncert>();
            for (KomercijalniKoncert komercijalniKoncertCollectionKomercijalniKoncertToAttach : ugovor.getKomercijalniKoncertCollection()) {
                komercijalniKoncertCollectionKomercijalniKoncertToAttach = em.getReference(komercijalniKoncertCollectionKomercijalniKoncertToAttach.getClass(), komercijalniKoncertCollectionKomercijalniKoncertToAttach.getKomercijalniId());
                attachedKomercijalniKoncertCollection.add(komercijalniKoncertCollectionKomercijalniKoncertToAttach);
            }
            ugovor.setKomercijalniKoncertCollection(attachedKomercijalniKoncertCollection);
            em.persist(ugovor);
            for (KomercijalniKoncert komercijalniKoncertCollectionKomercijalniKoncert : ugovor.getKomercijalniKoncertCollection()) {
                Ugovor oldUgovorIdOfKomercijalniKoncertCollectionKomercijalniKoncert = komercijalniKoncertCollectionKomercijalniKoncert.getUgovorId();
                komercijalniKoncertCollectionKomercijalniKoncert.setUgovorId(ugovor);
                komercijalniKoncertCollectionKomercijalniKoncert = em.merge(komercijalniKoncertCollectionKomercijalniKoncert);
                if (oldUgovorIdOfKomercijalniKoncertCollectionKomercijalniKoncert != null) {
                    oldUgovorIdOfKomercijalniKoncertCollectionKomercijalniKoncert.getKomercijalniKoncertCollection().remove(komercijalniKoncertCollectionKomercijalniKoncert);
                    oldUgovorIdOfKomercijalniKoncertCollectionKomercijalniKoncert = em.merge(oldUgovorIdOfKomercijalniKoncertCollectionKomercijalniKoncert);
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

    public void edit(Ugovor ugovor) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Ugovor persistentUgovor = em.find(Ugovor.class, ugovor.getUgovorId());
            Collection<KomercijalniKoncert> komercijalniKoncertCollectionOld = persistentUgovor.getKomercijalniKoncertCollection();
            Collection<KomercijalniKoncert> komercijalniKoncertCollectionNew = ugovor.getKomercijalniKoncertCollection();
            List<String> illegalOrphanMessages = null;
            for (KomercijalniKoncert komercijalniKoncertCollectionOldKomercijalniKoncert : komercijalniKoncertCollectionOld) {
                if (!komercijalniKoncertCollectionNew.contains(komercijalniKoncertCollectionOldKomercijalniKoncert)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain KomercijalniKoncert " + komercijalniKoncertCollectionOldKomercijalniKoncert + " since its ugovorId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<KomercijalniKoncert> attachedKomercijalniKoncertCollectionNew = new ArrayList<KomercijalniKoncert>();
            for (KomercijalniKoncert komercijalniKoncertCollectionNewKomercijalniKoncertToAttach : komercijalniKoncertCollectionNew) {
                komercijalniKoncertCollectionNewKomercijalniKoncertToAttach = em.getReference(komercijalniKoncertCollectionNewKomercijalniKoncertToAttach.getClass(), komercijalniKoncertCollectionNewKomercijalniKoncertToAttach.getKomercijalniId());
                attachedKomercijalniKoncertCollectionNew.add(komercijalniKoncertCollectionNewKomercijalniKoncertToAttach);
            }
            komercijalniKoncertCollectionNew = attachedKomercijalniKoncertCollectionNew;
            ugovor.setKomercijalniKoncertCollection(komercijalniKoncertCollectionNew);
            ugovor = em.merge(ugovor);
            for (KomercijalniKoncert komercijalniKoncertCollectionNewKomercijalniKoncert : komercijalniKoncertCollectionNew) {
                if (!komercijalniKoncertCollectionOld.contains(komercijalniKoncertCollectionNewKomercijalniKoncert)) {
                    Ugovor oldUgovorIdOfKomercijalniKoncertCollectionNewKomercijalniKoncert = komercijalniKoncertCollectionNewKomercijalniKoncert.getUgovorId();
                    komercijalniKoncertCollectionNewKomercijalniKoncert.setUgovorId(ugovor);
                    komercijalniKoncertCollectionNewKomercijalniKoncert = em.merge(komercijalniKoncertCollectionNewKomercijalniKoncert);
                    if (oldUgovorIdOfKomercijalniKoncertCollectionNewKomercijalniKoncert != null && !oldUgovorIdOfKomercijalniKoncertCollectionNewKomercijalniKoncert.equals(ugovor)) {
                        oldUgovorIdOfKomercijalniKoncertCollectionNewKomercijalniKoncert.getKomercijalniKoncertCollection().remove(komercijalniKoncertCollectionNewKomercijalniKoncert);
                        oldUgovorIdOfKomercijalniKoncertCollectionNewKomercijalniKoncert = em.merge(oldUgovorIdOfKomercijalniKoncertCollectionNewKomercijalniKoncert);
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
                Integer id = ugovor.getUgovorId();
                if (findUgovor(id) == null) {
                    throw new NonexistentEntityException("The ugovor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Ugovor ugovor;
            try {
                ugovor = em.getReference(Ugovor.class, id);
                ugovor.getUgovorId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ugovor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<KomercijalniKoncert> komercijalniKoncertCollectionOrphanCheck = ugovor.getKomercijalniKoncertCollection();
            for (KomercijalniKoncert komercijalniKoncertCollectionOrphanCheckKomercijalniKoncert : komercijalniKoncertCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ugovor (" + ugovor + ") cannot be destroyed since the KomercijalniKoncert " + komercijalniKoncertCollectionOrphanCheckKomercijalniKoncert + " in its komercijalniKoncertCollection field has a non-nullable ugovorId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(ugovor);
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

    public List<Ugovor> findUgovorEntities() {
        return findUgovorEntities(true, -1, -1);
    }

    public List<Ugovor> findUgovorEntities(int maxResults, int firstResult) {
        return findUgovorEntities(false, maxResults, firstResult);
    }

    private List<Ugovor> findUgovorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ugovor.class));
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

    public Ugovor findUgovor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ugovor.class, id);
        } finally {
            em.close();
        }
    }

    public int getUgovorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ugovor> rt = cq.from(Ugovor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
