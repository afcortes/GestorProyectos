/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.prog4.isc.GestorProyectos.Controladores;

import co.edu.utp.prog4.isc.GestorProyectos.Controlador.exceptions.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.edu.utp.prog4.isc.GestorProyectos.Modelo.Proyectos;
import co.edu.utp.prog4.isc.GestorProyectos.Modelo.Tareas;
import co.edu.utp.prog4.isc.GestorProyectos.Modelo.Tareasdeproyecto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ANDRES
 */
public class TareasdeproyectoJpaController implements Serializable {

    public TareasdeproyectoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("GestorProyectosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tareasdeproyecto tareasdeproyecto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proyectos fkidPro = tareasdeproyecto.getFkidPro();
            if (fkidPro != null) {
                fkidPro = em.getReference(fkidPro.getClass(), fkidPro.getIdPro());
                tareasdeproyecto.setFkidPro(fkidPro);
            }
            Tareas fkidTarea2 = tareasdeproyecto.getFkidTarea2();
            if (fkidTarea2 != null) {
                fkidTarea2 = em.getReference(fkidTarea2.getClass(), fkidTarea2.getIdTarea());
                tareasdeproyecto.setFkidTarea2(fkidTarea2);
            }
            em.persist(tareasdeproyecto);
            if (fkidPro != null) {
                fkidPro.getTareasdeproyectoList().add(tareasdeproyecto);
                fkidPro = em.merge(fkidPro);
            }
            if (fkidTarea2 != null) {
                fkidTarea2.getTareasdeproyectoList().add(tareasdeproyecto);
                fkidTarea2 = em.merge(fkidTarea2);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tareasdeproyecto tareasdeproyecto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tareasdeproyecto persistentTareasdeproyecto = em.find(Tareasdeproyecto.class, tareasdeproyecto.getId());
            Proyectos fkidProOld = persistentTareasdeproyecto.getFkidPro();
            Proyectos fkidProNew = tareasdeproyecto.getFkidPro();
            Tareas fkidTarea2Old = persistentTareasdeproyecto.getFkidTarea2();
            Tareas fkidTarea2New = tareasdeproyecto.getFkidTarea2();
            if (fkidProNew != null) {
                fkidProNew = em.getReference(fkidProNew.getClass(), fkidProNew.getIdPro());
                tareasdeproyecto.setFkidPro(fkidProNew);
            }
            if (fkidTarea2New != null) {
                fkidTarea2New = em.getReference(fkidTarea2New.getClass(), fkidTarea2New.getIdTarea());
                tareasdeproyecto.setFkidTarea2(fkidTarea2New);
            }
            tareasdeproyecto = em.merge(tareasdeproyecto);
            if (fkidProOld != null && !fkidProOld.equals(fkidProNew)) {
                fkidProOld.getTareasdeproyectoList().remove(tareasdeproyecto);
                fkidProOld = em.merge(fkidProOld);
            }
            if (fkidProNew != null && !fkidProNew.equals(fkidProOld)) {
                fkidProNew.getTareasdeproyectoList().add(tareasdeproyecto);
                fkidProNew = em.merge(fkidProNew);
            }
            if (fkidTarea2Old != null && !fkidTarea2Old.equals(fkidTarea2New)) {
                fkidTarea2Old.getTareasdeproyectoList().remove(tareasdeproyecto);
                fkidTarea2Old = em.merge(fkidTarea2Old);
            }
            if (fkidTarea2New != null && !fkidTarea2New.equals(fkidTarea2Old)) {
                fkidTarea2New.getTareasdeproyectoList().add(tareasdeproyecto);
                fkidTarea2New = em.merge(fkidTarea2New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tareasdeproyecto.getId();
                if (findTareasdeproyecto(id) == null) {
                    throw new NonexistentEntityException("The tareasdeproyecto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tareasdeproyecto tareasdeproyecto;
            try {
                tareasdeproyecto = em.getReference(Tareasdeproyecto.class, id);
                tareasdeproyecto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tareasdeproyecto with id " + id + " no longer exists.", enfe);
            }
            Proyectos fkidPro = tareasdeproyecto.getFkidPro();
            if (fkidPro != null) {
                fkidPro.getTareasdeproyectoList().remove(tareasdeproyecto);
                fkidPro = em.merge(fkidPro);
            }
            Tareas fkidTarea2 = tareasdeproyecto.getFkidTarea2();
            if (fkidTarea2 != null) {
                fkidTarea2.getTareasdeproyectoList().remove(tareasdeproyecto);
                fkidTarea2 = em.merge(fkidTarea2);
            }
            em.remove(tareasdeproyecto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tareasdeproyecto> findTareasdeproyectoEntities() {
        return findTareasdeproyectoEntities(true, -1, -1);
    }

    public List<Tareasdeproyecto> findTareasdeproyectoEntities(int maxResults, int firstResult) {
        return findTareasdeproyectoEntities(false, maxResults, firstResult);
    }

    private List<Tareasdeproyecto> findTareasdeproyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tareasdeproyecto.class));
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

    public Tareasdeproyecto findTareasdeproyecto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tareasdeproyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getTareasdeproyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tareasdeproyecto> rt = cq.from(Tareasdeproyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
