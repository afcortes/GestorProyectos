/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.prog4.isc.GestorProyectos.Controladores;

import co.edu.utp.prog4.isc.GestorProyectos.Controlador.exceptions.exceptions.NonexistentEntityException;
import co.edu.utp.prog4.isc.GestorProyectos.Modelo.Asignadas;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.edu.utp.prog4.isc.GestorProyectos.Modelo.Usuarios;
import co.edu.utp.prog4.isc.GestorProyectos.Modelo.Proyectos;
import co.edu.utp.prog4.isc.GestorProyectos.Modelo.Tareas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ANDRES
 */
public class AsignadasJpaController implements Serializable {

    public AsignadasJpaController() {
        this.emf = Persistence.createEntityManagerFactory("GestorProyectosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Asignadas asignadas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios fkUsu = asignadas.getFkUsu();
            if (fkUsu != null) {
                fkUsu = em.getReference(fkUsu.getClass(), fkUsu.getUsuario());
                asignadas.setFkUsu(fkUsu);
            }
            Proyectos fkidPro = asignadas.getFkidPro();
            if (fkidPro != null) {
                fkidPro = em.getReference(fkidPro.getClass(), fkidPro.getIdPro());
                asignadas.setFkidPro(fkidPro);
            }
            Tareas fkidTarea = asignadas.getFkidTarea();
            if (fkidTarea != null) {
                fkidTarea = em.getReference(fkidTarea.getClass(), fkidTarea.getIdTarea());
                asignadas.setFkidTarea(fkidTarea);
            }
            em.persist(asignadas);
            if (fkUsu != null) {
                fkUsu.getAsignadasList().add(asignadas);
                fkUsu = em.merge(fkUsu);
            }
            if (fkidPro != null) {
                fkidPro.getAsignadasList().add(asignadas);
                fkidPro = em.merge(fkidPro);
            }
            if (fkidTarea != null) {
                fkidTarea.getAsignadasList().add(asignadas);
                fkidTarea = em.merge(fkidTarea);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Asignadas asignadas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asignadas persistentAsignadas = em.find(Asignadas.class, asignadas.getId());
            Usuarios fkUsuOld = persistentAsignadas.getFkUsu();
            Usuarios fkUsuNew = asignadas.getFkUsu();
            Proyectos fkidProOld = persistentAsignadas.getFkidPro();
            Proyectos fkidProNew = asignadas.getFkidPro();
            Tareas fkidTareaOld = persistentAsignadas.getFkidTarea();
            Tareas fkidTareaNew = asignadas.getFkidTarea();
            if (fkUsuNew != null) {
                fkUsuNew = em.getReference(fkUsuNew.getClass(), fkUsuNew.getUsuario());
                asignadas.setFkUsu(fkUsuNew);
            }
            if (fkidProNew != null) {
                fkidProNew = em.getReference(fkidProNew.getClass(), fkidProNew.getIdPro());
                asignadas.setFkidPro(fkidProNew);
            }
            if (fkidTareaNew != null) {
                fkidTareaNew = em.getReference(fkidTareaNew.getClass(), fkidTareaNew.getIdTarea());
                asignadas.setFkidTarea(fkidTareaNew);
            }
            asignadas = em.merge(asignadas);
            if (fkUsuOld != null && !fkUsuOld.equals(fkUsuNew)) {
                fkUsuOld.getAsignadasList().remove(asignadas);
                fkUsuOld = em.merge(fkUsuOld);
            }
            if (fkUsuNew != null && !fkUsuNew.equals(fkUsuOld)) {
                fkUsuNew.getAsignadasList().add(asignadas);
                fkUsuNew = em.merge(fkUsuNew);
            }
            if (fkidProOld != null && !fkidProOld.equals(fkidProNew)) {
                fkidProOld.getAsignadasList().remove(asignadas);
                fkidProOld = em.merge(fkidProOld);
            }
            if (fkidProNew != null && !fkidProNew.equals(fkidProOld)) {
                fkidProNew.getAsignadasList().add(asignadas);
                fkidProNew = em.merge(fkidProNew);
            }
            if (fkidTareaOld != null && !fkidTareaOld.equals(fkidTareaNew)) {
                fkidTareaOld.getAsignadasList().remove(asignadas);
                fkidTareaOld = em.merge(fkidTareaOld);
            }
            if (fkidTareaNew != null && !fkidTareaNew.equals(fkidTareaOld)) {
                fkidTareaNew.getAsignadasList().add(asignadas);
                fkidTareaNew = em.merge(fkidTareaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = asignadas.getId();
                if (findAsignadas(id) == null) {
                    throw new NonexistentEntityException("The asignadas with id " + id + " no longer exists.");
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
            Asignadas asignadas;
            try {
                asignadas = em.getReference(Asignadas.class, id);
                asignadas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asignadas with id " + id + " no longer exists.", enfe);
            }
            Usuarios fkUsu = asignadas.getFkUsu();
            if (fkUsu != null) {
                fkUsu.getAsignadasList().remove(asignadas);
                fkUsu = em.merge(fkUsu);
            }
            Proyectos fkidPro = asignadas.getFkidPro();
            if (fkidPro != null) {
                fkidPro.getAsignadasList().remove(asignadas);
                fkidPro = em.merge(fkidPro);
            }
            Tareas fkidTarea = asignadas.getFkidTarea();
            if (fkidTarea != null) {
                fkidTarea.getAsignadasList().remove(asignadas);
                fkidTarea = em.merge(fkidTarea);
            }
            em.remove(asignadas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Asignadas> findAsignadasEntities() {
        return findAsignadasEntities(true, -1, -1);
    }

    public List<Asignadas> findAsignadasEntities(int maxResults, int firstResult) {
        return findAsignadasEntities(false, maxResults, firstResult);
    }

    private List<Asignadas> findAsignadasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Asignadas.class));
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

    public Asignadas findAsignadas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Asignadas.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsignadasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Asignadas> rt = cq.from(Asignadas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
