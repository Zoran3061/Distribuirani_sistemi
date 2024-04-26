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
public class PesmaJpaController implements Serializable {

    public PesmaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pesma pesma) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Album albumId = pesma.getAlbumId();
            if (albumId != null) {
                albumId = em.getReference(albumId.getClass(), albumId.getAlbumId());
                pesma.setAlbumId(albumId);
            }
            em.persist(pesma);
            if (albumId != null) {
                albumId.getPesmaCollection().add(pesma);
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

    public void edit(Pesma pesma) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Pesma persistentPesma = em.find(Pesma.class, pesma.getPesmaId());
            Album albumIdOld = persistentPesma.getAlbumId();
            Album albumIdNew = pesma.getAlbumId();
            if (albumIdNew != null) {
                albumIdNew = em.getReference(albumIdNew.getClass(), albumIdNew.getAlbumId());
                pesma.setAlbumId(albumIdNew);
            }
            pesma = em.merge(pesma);
            if (albumIdOld != null && !albumIdOld.equals(albumIdNew)) {
                albumIdOld.getPesmaCollection().remove(pesma);
                albumIdOld = em.merge(albumIdOld);
            }
            if (albumIdNew != null && !albumIdNew.equals(albumIdOld)) {
                albumIdNew.getPesmaCollection().add(pesma);
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
                Integer id = pesma.getPesmaId();
                if (findPesma(id) == null) {
                    throw new NonexistentEntityException("The pesma with id " + id + " no longer exists.");
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
            Pesma pesma;
            try {
                pesma = em.getReference(Pesma.class, id);
                pesma.getPesmaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pesma with id " + id + " no longer exists.", enfe);
            }
            Album albumId = pesma.getAlbumId();
            if (albumId != null) {
                albumId.getPesmaCollection().remove(pesma);
                albumId = em.merge(albumId);
            }
            em.remove(pesma);
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

    public List<Pesma> findPesmaEntities() {
        return findPesmaEntities(true, -1, -1);
    }

    public List<Pesma> findPesmaEntities(int maxResults, int firstResult) {
        return findPesmaEntities(false, maxResults, firstResult);
    }

    private List<Pesma> findPesmaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pesma.class));
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

    public Pesma findPesma(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pesma.class, id);
        } finally {
            em.close();
        }
    }

    public int getPesmaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pesma> rt = cq.from(Pesma.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
