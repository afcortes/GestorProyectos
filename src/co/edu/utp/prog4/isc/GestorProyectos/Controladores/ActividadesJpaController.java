/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.prog4.isc.GestorProyectos.Controladores;

import co.edu.utp.prog4.isc.GestorProyectos.Controlador.exceptions.exceptions.NonexistentEntityException;
import co.edu.utp.prog4.isc.GestorProyectos.Modelo.Actividades;
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
public class ActividadesJpaController implements Serializable {

    public ActividadesJpaController() {
        this.emf = Persistence.createEntityManagerFactory("GestorProyectosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Actividades actividades) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios fkUsu = actividades.getFkUsu();
            if (fkUsu != null) {
                fkUsu = em.getReference(fkUsu.getClass(), fkUsu.getUsuario());
                actividades.setFkUsu(fkUsu);
            }
            Proyectos fkidPro2 = actividades.getFkidPro2();
            if (fkidPro2 != null) {
                fkidPro2 = em.getReference(fkidPro2.getClass(), fkidPro2.getIdPro());
                actividades.setFkidPro2(fkidPro2);
            }
            Tareas fkidTarea = actividades.getFkidTarea();
            if (fkidTarea != null) {
                fkidTarea = em.getReference(fkidTarea.getClass(), fkidTarea.getIdTarea());
                actividades.setFkidTarea(fkidTarea);
            }
            em.persist(actividades);
            if (fkUsu != null) {
                fkUsu.getActividadesList().add(actividades);
                fkUsu = em.merge(fkUsu);
            }
            if (fkidPro2 != null) {
                fkidPro2.getActividadesList().add(actividades);
                fkidPro2 = em.merge(fkidPro2);
            }
            if (fkidTarea != null) {
                fkidTarea.getActividadesList().add(actividades);
                fkidTarea = em.merge(fkidTarea);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Actividades actividades) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Actividades persistentActividades = em.find(Actividades.class, actividades.getId());
            Usuarios fkUsuOld = persistentActividades.getFkUsu();
            Usuarios fkUsuNew = actividades.getFkUsu();
            Proyectos fkidPro2Old = persistentActividades.getFkidPro2();
            Proyectos fkidPro2New = actividades.getFkidPro2();
            Tareas fkidTareaOld = persistentActividades.getFkidTarea();
            Tareas fkidTareaNew = actividades.getFkidTarea();
            if (fkUsuNew != null) {
                fkUsuNew = em.getReference(fkUsuNew.getClass(), fkUsuNew.getUsuario());
                actividades.setFkUsu(fkUsuNew);
            }
            if (fkidPro2New != null) {
                fkidPro2New = em.getReference(fkidPro2New.getClass(), fkidPro2New.getIdPro());
                actividades.setFkidPro2(fkidPro2New);
            }
            if (fkidTareaNew != null) {
                fkidTareaNew = em.getReference(fkidTareaNew.getClass(), fkidTareaNew.getIdTarea());
                actividades.setFkidTarea(fkidTareaNew);
            }
            actividades = em.merge(actividades);
            if (fkUsuOld != null && !fkUsuOld.equals(fkUsuNew)) {
                fkUsuOld.getActividadesList().remove(actividades);
                fkUsuOld = em.merge(fkUsuOld);
            }
            if (fkUsuNew != null && !fkUsuNew.equals(fkUsuOld)) {
                fkUsuNew.getActividadesList().add(actividades);
                fkUsuNew = em.merge(fkUsuNew);
            }
            if (fkidPro2Old != null && !fkidPro2Old.equals(fkidPro2New)) {
                fkidPro2Old.getActividadesList().remove(actividades);
                fkidPro2Old = em.merge(fkidPro2Old);
            }
            if (fkidPro2New != null && !fkidPro2New.equals(fkidPro2Old)) {
                fkidPro2New.getActividadesList().add(actividades);
                fkidPro2New = em.merge(fkidPro2New);
            }
            if (fkidTareaOld != null && !fkidTareaOld.equals(fkidTareaNew)) {
                fkidTareaOld.getActividadesList().remove(actividades);
                fkidTareaOld = em.merge(fkidTareaOld);
            }
            if (fkidTareaNew != null && !fkidTareaNew.equals(fkidTareaOld)) {
                fkidTareaNew.getActividadesList().add(actividades);
                fkidTareaNew = em.merge(fkidTareaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = actividades.getId();
                if (findActividades(id) == null) {
                    throw new NonexistentEntityException("The actividades with id " + id + " no longer exists.");
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
            Actividades actividades;
            try {
                actividades = em.getReference(Actividades.class, id);
                actividades.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actividades with id " + id + " no longer exists.", enfe);
            }
            Usuarios fkUsu = actividades.getFkUsu();
            if (fkUsu != null) {
                fkUsu.getActividadesList().remove(actividades);
                fkUsu = em.merge(fkUsu);
            }
            Proyectos fkidPro2 = actividades.getFkidPro2();
            if (fkidPro2 != null) {
                fkidPro2.getActividadesList().remove(actividades);
                fkidPro2 = em.merge(fkidPro2);
            }
            Tareas fkidTarea = actividades.getFkidTarea();
            if (fkidTarea != null) {
                fkidTarea.getActividadesList().remove(actividades);
                fkidTarea = em.merge(fkidTarea);
            }
            em.remove(actividades);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Actividades> findActividadesEntities() {
        return findActividadesEntities(true, -1, -1);
    }

    public List<Actividades> findActividadesEntities(int maxResults, int firstResult) {
        return findActividadesEntities(false, maxResults, firstResult);
    }

    private List<Actividades> findActividadesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Actividades.class));
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

    public Actividades findActividades(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Actividades.class, id);
        } finally {
            em.close();
        }
    }

    public int getActividadesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Actividades> rt = cq.from(Actividades.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
