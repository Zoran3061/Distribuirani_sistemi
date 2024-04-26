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
public class SnimioJpaController implements Serializable {

    public SnimioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Snimio snimio) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Izvodjac izvodjacId = snimio.getIzvodjacId();
            if (izvodjacId != null) {
                izvodjacId = em.getReference(izvodjacId.getClass(), izvodjacId.getIzvodjacId());
                snimio.setIzvodjacId(izvodjacId);
            }
            Album albumId = snimio.getAlbumId();
            if (albumId != null) {
                albumId = em.getReference(albumId.getClass(), albumId.getAlbumId());
                snimio.setAlbumId(albumId);
            }
            em.persist(snimio);
            if (izvodjacId != null) {
                izvodjacId.getSnimioCollection().add(snimio);
                izvodjacId = em.merge(izvodjacId);
            }
            if (albumId != null) {
                albumId.getSnimioCollection().add(snimio);
                albumId = em.merge(albumId);
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

    public void edit(Snimio snimio) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Snimio persistentSnimio = em.find(Snimio.class, snimio.getSnimioId());
            Izvodjac izvodjacIdOld = persistentSnimio.getIzvodjacId();
            Izvodjac izvodjacIdNew = snimio.getIzvodjacId();
            Album albumIdOld = persistentSnimio.getAlbumId();
            Album albumIdNew = snimio.getAlbumId();
            if (izvodjacIdNew != null) {
                izvodjacIdNew = em.getReference(izvodjacIdNew.getClass(), izvodjacIdNew.getIzvodjacId());
                snimio.setIzvodjacId(izvodjacIdNew);
            }
            if (albumIdNew != null) {
                albumIdNew = em.getReference(albumIdNew.getClass(), albumIdNew.getAlbumId());
                snimio.setAlbumId(albumIdNew);
            }
            snimio = em.merge(snimio);
            if (izvodjacIdOld != null && !izvodjacIdOld.equals(izvodjacIdNew)) {
                izvodjacIdOld.getSnimioCollection().remove(snimio);
                izvodjacIdOld = em.merge(izvodjacIdOld);
            }
            if (izvodjacIdNew != null && !izvodjacIdNew.equals(izvodjacIdOld)) {
                izvodjacIdNew.getSnimioCollection().add(snimio);
                izvodjacIdNew = em.merge(izvodjacIdNew);
            }
            if (albumIdOld != null && !albumIdOld.equals(albumIdNew)) {
                albumIdOld.getSnimioCollection().remove(snimio);
                albumIdOld = em.merge(albumIdOld);
            }
            if (albumIdNew != null && !albumIdNew.equals(albumIdOld)) {
                albumIdNew.getSnimioCollection().add(snimio);
                albumIdNew = em.merge(albumIdNew);
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
                Integer id = snimio.getSnimioId();
                if (findSnimio(id) == null) {
                    throw new NonexistentEntityException("The snimio with id " + id + " no longer exists.");
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
            Snimio snimio;
            try {
                snimio = em.getReference(Snimio.class, id);
                snimio.getSnimioId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The snimio with id " + id + " no longer exists.", enfe);
            }
            Izvodjac izvodjacId = snimio.getIzvodjacId();
            if (izvodjacId != null) {
                izvodjacId.getSnimioCollection().remove(snimio);
                izvodjacId = em.merge(izvodjacId);
            }
            Album albumId = snimio.getAlbumId();
            if (albumId != null) {
                albumId.getSnimioCollection().remove(snimio);
                albumId = em.merge(albumId);
            }
            em.remove(snimio);
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

    public List<Snimio> findSnimioEntities() {
        return findSnimioEntities(true, -1, -1);
    }

    public List<Snimio> findSnimioEntities(int maxResults, int firstResult) {
        return findSnimioEntities(false, maxResults, firstResult);
    }

    private List<Snimio> findSnimioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Snimio.class));
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

    public Snimio findSnimio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Snimio.class, id);
        } finally {
            em.close();
        }
    }

    public int getSnimioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Snimio> rt = cq.from(Snimio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
