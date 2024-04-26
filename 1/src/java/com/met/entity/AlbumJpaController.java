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
public class AlbumJpaController implements Serializable {

    public AlbumJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Album album) throws RollbackFailureException, Exception {
        if (album.getSnimioCollection() == null) {
            album.setSnimioCollection(new ArrayList<Snimio>());
        }
        if (album.getPesmaCollection() == null) {
            album.setPesmaCollection(new ArrayList<Pesma>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Snimio> attachedSnimioCollection = new ArrayList<Snimio>();
            for (Snimio snimioCollectionSnimioToAttach : album.getSnimioCollection()) {
                snimioCollectionSnimioToAttach = em.getReference(snimioCollectionSnimioToAttach.getClass(), snimioCollectionSnimioToAttach.getSnimioId());
                attachedSnimioCollection.add(snimioCollectionSnimioToAttach);
            }
            album.setSnimioCollection(attachedSnimioCollection);
            Collection<Pesma> attachedPesmaCollection = new ArrayList<Pesma>();
            for (Pesma pesmaCollectionPesmaToAttach : album.getPesmaCollection()) {
                pesmaCollectionPesmaToAttach = em.getReference(pesmaCollectionPesmaToAttach.getClass(), pesmaCollectionPesmaToAttach.getPesmaId());
                attachedPesmaCollection.add(pesmaCollectionPesmaToAttach);
            }
            album.setPesmaCollection(attachedPesmaCollection);
            em.persist(album);
            for (Snimio snimioCollectionSnimio : album.getSnimioCollection()) {
                Album oldAlbumIdOfSnimioCollectionSnimio = snimioCollectionSnimio.getAlbumId();
                snimioCollectionSnimio.setAlbumId(album);
                snimioCollectionSnimio = em.merge(snimioCollectionSnimio);
                if (oldAlbumIdOfSnimioCollectionSnimio != null) {
                    oldAlbumIdOfSnimioCollectionSnimio.getSnimioCollection().remove(snimioCollectionSnimio);
                    oldAlbumIdOfSnimioCollectionSnimio = em.merge(oldAlbumIdOfSnimioCollectionSnimio);
                }
            }
            for (Pesma pesmaCollectionPesma : album.getPesmaCollection()) {
                Album oldAlbumIdOfPesmaCollectionPesma = pesmaCollectionPesma.getAlbumId();
                pesmaCollectionPesma.setAlbumId(album);
                pesmaCollectionPesma = em.merge(pesmaCollectionPesma);
                if (oldAlbumIdOfPesmaCollectionPesma != null) {
                    oldAlbumIdOfPesmaCollectionPesma.getPesmaCollection().remove(pesmaCollectionPesma);
                    oldAlbumIdOfPesmaCollectionPesma = em.merge(oldAlbumIdOfPesmaCollectionPesma);
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

    public void edit(Album album) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Album persistentAlbum = em.find(Album.class, album.getAlbumId());
            Collection<Snimio> snimioCollectionOld = persistentAlbum.getSnimioCollection();
            Collection<Snimio> snimioCollectionNew = album.getSnimioCollection();
            Collection<Pesma> pesmaCollectionOld = persistentAlbum.getPesmaCollection();
            Collection<Pesma> pesmaCollectionNew = album.getPesmaCollection();
            List<String> illegalOrphanMessages = null;
            for (Snimio snimioCollectionOldSnimio : snimioCollectionOld) {
                if (!snimioCollectionNew.contains(snimioCollectionOldSnimio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Snimio " + snimioCollectionOldSnimio + " since its albumId field is not nullable.");
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
            album.setSnimioCollection(snimioCollectionNew);
            Collection<Pesma> attachedPesmaCollectionNew = new ArrayList<Pesma>();
            for (Pesma pesmaCollectionNewPesmaToAttach : pesmaCollectionNew) {
                pesmaCollectionNewPesmaToAttach = em.getReference(pesmaCollectionNewPesmaToAttach.getClass(), pesmaCollectionNewPesmaToAttach.getPesmaId());
                attachedPesmaCollectionNew.add(pesmaCollectionNewPesmaToAttach);
            }
            pesmaCollectionNew = attachedPesmaCollectionNew;
            album.setPesmaCollection(pesmaCollectionNew);
            album = em.merge(album);
            for (Snimio snimioCollectionNewSnimio : snimioCollectionNew) {
                if (!snimioCollectionOld.contains(snimioCollectionNewSnimio)) {
                    Album oldAlbumIdOfSnimioCollectionNewSnimio = snimioCollectionNewSnimio.getAlbumId();
                    snimioCollectionNewSnimio.setAlbumId(album);
                    snimioCollectionNewSnimio = em.merge(snimioCollectionNewSnimio);
                    if (oldAlbumIdOfSnimioCollectionNewSnimio != null && !oldAlbumIdOfSnimioCollectionNewSnimio.equals(album)) {
                        oldAlbumIdOfSnimioCollectionNewSnimio.getSnimioCollection().remove(snimioCollectionNewSnimio);
                        oldAlbumIdOfSnimioCollectionNewSnimio = em.merge(oldAlbumIdOfSnimioCollectionNewSnimio);
                    }
                }
            }
            for (Pesma pesmaCollectionOldPesma : pesmaCollectionOld) {
                if (!pesmaCollectionNew.contains(pesmaCollectionOldPesma)) {
                    pesmaCollectionOldPesma.setAlbumId(null);
                    pesmaCollectionOldPesma = em.merge(pesmaCollectionOldPesma);
                }
            }
            for (Pesma pesmaCollectionNewPesma : pesmaCollectionNew) {
                if (!pesmaCollectionOld.contains(pesmaCollectionNewPesma)) {
                    Album oldAlbumIdOfPesmaCollectionNewPesma = pesmaCollectionNewPesma.getAlbumId();
                    pesmaCollectionNewPesma.setAlbumId(album);
                    pesmaCollectionNewPesma = em.merge(pesmaCollectionNewPesma);
                    if (oldAlbumIdOfPesmaCollectionNewPesma != null && !oldAlbumIdOfPesmaCollectionNewPesma.equals(album)) {
                        oldAlbumIdOfPesmaCollectionNewPesma.getPesmaCollection().remove(pesmaCollectionNewPesma);
                        oldAlbumIdOfPesmaCollectionNewPesma = em.merge(oldAlbumIdOfPesmaCollectionNewPesma);
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
                Integer id = album.getAlbumId();
                if (findAlbum(id) == null) {
                    throw new NonexistentEntityException("The album with id " + id + " no longer exists.");
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
            Album album;
            try {
                album = em.getReference(Album.class, id);
                album.getAlbumId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The album with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Snimio> snimioCollectionOrphanCheck = album.getSnimioCollection();
            for (Snimio snimioCollectionOrphanCheckSnimio : snimioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Album (" + album + ") cannot be destroyed since the Snimio " + snimioCollectionOrphanCheckSnimio + " in its snimioCollection field has a non-nullable albumId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Pesma> pesmaCollection = album.getPesmaCollection();
            for (Pesma pesmaCollectionPesma : pesmaCollection) {
                pesmaCollectionPesma.setAlbumId(null);
                pesmaCollectionPesma = em.merge(pesmaCollectionPesma);
            }
            em.remove(album);
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

    public List<Album> findAlbumEntities() {
        return findAlbumEntities(true, -1, -1);
    }

    public List<Album> findAlbumEntities(int maxResults, int firstResult) {
        return findAlbumEntities(false, maxResults, firstResult);
    }

    private List<Album> findAlbumEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Album.class));
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

    public Album findAlbum(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Album.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlbumCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Album> rt = cq.from(Album.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
