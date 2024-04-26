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
public class IzvodjacJpaController implements Serializable {

    public IzvodjacJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Izvodjac izvodjac) throws RollbackFailureException, Exception {
        if (izvodjac.getSnimioCollection() == null) {
            izvodjac.setSnimioCollection(new ArrayList<Snimio>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Snimio> attachedSnimioCollection = new ArrayList<Snimio>();
            for (Snimio snimioCollectionSnimioToAttach : izvodjac.getSnimioCollection()) {
                snimioCollectionSnimioToAttach = em.getReference(snimioCollectionSnimioToAttach.getClass(), snimioCollectionSnimioToAttach.getSnimioId());
                attachedSnimioCollection.add(snimioCollectionSnimioToAttach);
            }
            izvodjac.setSnimioCollection(attachedSnimioCollection);
            em.persist(izvodjac);
            for (Snimio snimioCollectionSnimio : izvodjac.getSnimioCollection()) {
                Izvodjac oldIzvodjacIdOfSnimioCollectionSnimio = snimioCollectionSnimio.getIzvodjacId();
                snimioCollectionSnimio.setIzvodjacId(izvodjac);
                snimioCollectionSnimio = em.merge(snimioCollectionSnimio);
                if (oldIzvodjacIdOfSnimioCollectionSnimio != null) {
                    oldIzvodjacIdOfSnimioCollectionSnimio.getSnimioCollection().remove(snimioCollectionSnimio);
                    oldIzvodjacIdOfSnimioCollectionSnimio = em.merge(oldIzvodjacIdOfSnimioCollectionSnimio);
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

    public void edit(Izvodjac izvodjac) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Izvodjac persistentIzvodjac = em.find(Izvodjac.class, izvodjac.getIzvodjacId());
            Collection<Snimio> snimioCollectionOld = persistentIzvodjac.getSnimioCollection();
            Collection<Snimio> snimioCollectionNew = izvodjac.getSnimioCollection();
            List<String> illegalOrphanMessages = null;
            for (Snimio snimioCollectionOldSnimio : snimioCollectionOld) {
                if (!snimioCollectionNew.contains(snimioCollectionOldSnimio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Snimio " + snimioCollectionOldSnimio + " since its izvodjacId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Snimio> attachedSnimioCollectionNew = new ArrayList<Snimio>();
            for (Snimio snimioCollectionNewSnimioToAttach : snimioCollectionNew) {
                snimioCollectionNewSnimioToAttach = em.getReference(snimioCollectionNewSnimioToAttach.getClass(), snimioCollectionNewSnimioToAttach.getSnimioId());
                attachedSnimioCollectionNew.add(snimioCollectionNewSnimioToAttach);
            }
            snimioCollectionNew = attachedSnimioCollectionNew;
            izvodjac.setSnimioCollection(snimioCollectionNew);
            izvodjac = em.merge(izvodjac);
            for (Snimio snimioCollectionNewSnimio : snimioCollectionNew) {
                if (!snimioCollectionOld.contains(snimioCollectionNewSnimio)) {
                    Izvodjac oldIzvodjacIdOfSnimioCollectionNewSnimio = snimioCollectionNewSnimio.getIzvodjacId();
                    snimioCollectionNewSnimio.setIzvodjacId(izvodjac);
                    snimioCollectionNewSnimio = em.merge(snimioCollectionNewSnimio);
                    if (oldIzvodjacIdOfSnimioCollectionNewSnimio != null && !oldIzvodjacIdOfSnimioCollectionNewSnimio.equals(izvodjac)) {
                        oldIzvodjacIdOfSnimioCollectionNewSnimio.getSnimioCollection().remove(snimioCollectionNewSnimio);
                        oldIzvodjacIdOfSnimioCollectionNewSnimio = em.merge(oldIzvodjacIdOfSnimioCollectionNewSnimio);
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
                Integer id = izvodjac.getIzvodjacId();
                if (findIzvodjac(id) == null) {
                    throw new NonexistentEntityException("The izvodjac with id " + id + " no longer exists.");
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
            Izvodjac izvodjac;
            try {
                izvodjac = em.getReference(Izvodjac.class, id);
                izvodjac.getIzvodjacId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The izvodjac with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Snimio> snimioCollectionOrphanCheck = izvodjac.getSnimioCollection();
            for (Snimio snimioCollectionOrphanCheckSnimio : snimioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Izvodjac (" + izvodjac + ") cannot be destroyed since the Snimio " + snimioCollectionOrphanCheckSnimio + " in its snimioCollection field has a non-nullable izvodjacId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(izvodjac);
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

    public List<Izvodjac> findIzvodjacEntities() {
        return findIzvodjacEntities(true, -1, -1);
    }

    public List<Izvodjac> findIzvodjacEntities(int maxResults, int firstResult) {
        return findIzvodjacEntities(false, maxResults, firstResult);
    }

    private List<Izvodjac> findIzvodjacEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Izvodjac.class));
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

    public Izvodjac findIzvodjac(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Izvodjac.class, id);
        } finally {
            em.close();
        }
    }

    public int getIzvodjacCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Izvodjac> rt = cq.from(Izvodjac.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
