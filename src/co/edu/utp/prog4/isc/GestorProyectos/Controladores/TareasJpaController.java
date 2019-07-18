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
import co.edu.utp.prog4.isc.GestorProyectos.Modelo.Asignadas;
import java.util.ArrayList;
import java.util.List;
import co.edu.utp.prog4.isc.GestorProyectos.Modelo.Tareasdeproyecto;
import co.edu.utp.prog4.isc.GestorProyectos.Modelo.Actividades;
import co.edu.utp.prog4.isc.GestorProyectos.Modelo.Tareas;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ANDRES
 */
public class TareasJpaController implements Serializable {

    public TareasJpaController() {
        this.emf = Persistence.createEntityManagerFactory("GestorProyectosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tareas tareas) {
        if (tareas.getAsignadasList() == null) {
            tareas.setAsignadasList(new ArrayList<Asignadas>());
        }
        if (tareas.getTareasdeproyectoList() == null) {
            tareas.setTareasdeproyectoList(new ArrayList<Tareasdeproyecto>());
        }
        if (tareas.getActividadesList() == null) {
            tareas.setActividadesList(new ArrayList<Actividades>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Asignadas> attachedAsignadasList = new ArrayList<Asignadas>();
            for (Asignadas asignadasListAsignadasToAttach : tareas.getAsignadasList()) {
                asignadasListAsignadasToAttach = em.getReference(asignadasListAsignadasToAttach.getClass(), asignadasListAsignadasToAttach.getId());
                attachedAsignadasList.add(asignadasListAsignadasToAttach);
            }
            tareas.setAsignadasList(attachedAsignadasList);
            List<Tareasdeproyecto> attachedTareasdeproyectoList = new ArrayList<Tareasdeproyecto>();
            for (Tareasdeproyecto tareasdeproyectoListTareasdeproyectoToAttach : tareas.getTareasdeproyectoList()) {
                tareasdeproyectoListTareasdeproyectoToAttach = em.getReference(tareasdeproyectoListTareasdeproyectoToAttach.getClass(), tareasdeproyectoListTareasdeproyectoToAttach.getId());
                attachedTareasdeproyectoList.add(tareasdeproyectoListTareasdeproyectoToAttach);
            }
            tareas.setTareasdeproyectoList(attachedTareasdeproyectoList);
            List<Actividades> attachedActividadesList = new ArrayList<Actividades>();
            for (Actividades actividadesListActividadesToAttach : tareas.getActividadesList()) {
                actividadesListActividadesToAttach = em.getReference(actividadesListActividadesToAttach.getClass(), actividadesListActividadesToAttach.getId());
                attachedActividadesList.add(actividadesListActividadesToAttach);
            }
            tareas.setActividadesList(attachedActividadesList);
            em.persist(tareas);
            for (Asignadas asignadasListAsignadas : tareas.getAsignadasList()) {
                Tareas oldFkidTareaOfAsignadasListAsignadas = asignadasListAsignadas.getFkidTarea();
                asignadasListAsignadas.setFkidTarea(tareas);
                asignadasListAsignadas = em.merge(asignadasListAsignadas);
                if (oldFkidTareaOfAsignadasListAsignadas != null) {
                    oldFkidTareaOfAsignadasListAsignadas.getAsignadasList().remove(asignadasListAsignadas);
                    oldFkidTareaOfAsignadasListAsignadas = em.merge(oldFkidTareaOfAsignadasListAsignadas);
                }
            }
            for (Tareasdeproyecto tareasdeproyectoListTareasdeproyecto : tareas.getTareasdeproyectoList()) {
                Tareas oldFkidTarea2OfTareasdeproyectoListTareasdeproyecto = tareasdeproyectoListTareasdeproyecto.getFkidTarea2();
                tareasdeproyectoListTareasdeproyecto.setFkidTarea2(tareas);
                tareasdeproyectoListTareasdeproyecto = em.merge(tareasdeproyectoListTareasdeproyecto);
                if (oldFkidTarea2OfTareasdeproyectoListTareasdeproyecto != null) {
                    oldFkidTarea2OfTareasdeproyectoListTareasdeproyecto.getTareasdeproyectoList().remove(tareasdeproyectoListTareasdeproyecto);
                    oldFkidTarea2OfTareasdeproyectoListTareasdeproyecto = em.merge(oldFkidTarea2OfTareasdeproyectoListTareasdeproyecto);
                }
            }
            for (Actividades actividadesListActividades : tareas.getActividadesList()) {
                Tareas oldFkidTareaOfActividadesListActividades = actividadesListActividades.getFkidTarea();
                actividadesListActividades.setFkidTarea(tareas);
                actividadesListActividades = em.merge(actividadesListActividades);
                if (oldFkidTareaOfActividadesListActividades != null) {
                    oldFkidTareaOfActividadesListActividades.getActividadesList().remove(actividadesListActividades);
                    oldFkidTareaOfActividadesListActividades = em.merge(oldFkidTareaOfActividadesListActividades);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tareas tareas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tareas persistentTareas = em.find(Tareas.class, tareas.getIdTarea());
            List<Asignadas> asignadasListOld = persistentTareas.getAsignadasList();
            List<Asignadas> asignadasListNew = tareas.getAsignadasList();
            List<Tareasdeproyecto> tareasdeproyectoListOld = persistentTareas.getTareasdeproyectoList();
            List<Tareasdeproyecto> tareasdeproyectoListNew = tareas.getTareasdeproyectoList();
            List<Actividades> actividadesListOld = persistentTareas.getActividadesList();
            List<Actividades> actividadesListNew = tareas.getActividadesList();
            List<Asignadas> attachedAsignadasListNew = new ArrayList<Asignadas>();
            for (Asignadas asignadasListNewAsignadasToAttach : asignadasListNew) {
                asignadasListNewAsignadasToAttach = em.getReference(asignadasListNewAsignadasToAttach.getClass(), asignadasListNewAsignadasToAttach.getId());
                attachedAsignadasListNew.add(asignadasListNewAsignadasToAttach);
            }
            asignadasListNew = attachedAsignadasListNew;
            tareas.setAsignadasList(asignadasListNew);
            List<Tareasdeproyecto> attachedTareasdeproyectoListNew = new ArrayList<Tareasdeproyecto>();
            for (Tareasdeproyecto tareasdeproyectoListNewTareasdeproyectoToAttach : tareasdeproyectoListNew) {
                tareasdeproyectoListNewTareasdeproyectoToAttach = em.getReference(tareasdeproyectoListNewTareasdeproyectoToAttach.getClass(), tareasdeproyectoListNewTareasdeproyectoToAttach.getId());
                attachedTareasdeproyectoListNew.add(tareasdeproyectoListNewTareasdeproyectoToAttach);
            }
            tareasdeproyectoListNew = attachedTareasdeproyectoListNew;
            tareas.setTareasdeproyectoList(tareasdeproyectoListNew);
            List<Actividades> attachedActividadesListNew = new ArrayList<Actividades>();
            for (Actividades actividadesListNewActividadesToAttach : actividadesListNew) {
                actividadesListNewActividadesToAttach = em.getReference(actividadesListNewActividadesToAttach.getClass(), actividadesListNewActividadesToAttach.getId());
                attachedActividadesListNew.add(actividadesListNewActividadesToAttach);
            }
            actividadesListNew = attachedActividadesListNew;
            tareas.setActividadesList(actividadesListNew);
            tareas = em.merge(tareas);
            for (Asignadas asignadasListOldAsignadas : asignadasListOld) {
                if (!asignadasListNew.contains(asignadasListOldAsignadas)) {
                    asignadasListOldAsignadas.setFkidTarea(null);
                    asignadasListOldAsignadas = em.merge(asignadasListOldAsignadas);
                }
            }
            for (Asignadas asignadasListNewAsignadas : asignadasListNew) {
                if (!asignadasListOld.contains(asignadasListNewAsignadas)) {
                    Tareas oldFkidTareaOfAsignadasListNewAsignadas = asignadasListNewAsignadas.getFkidTarea();
                    asignadasListNewAsignadas.setFkidTarea(tareas);
                    asignadasListNewAsignadas = em.merge(asignadasListNewAsignadas);
                    if (oldFkidTareaOfAsignadasListNewAsignadas != null && !oldFkidTareaOfAsignadasListNewAsignadas.equals(tareas)) {
                        oldFkidTareaOfAsignadasListNewAsignadas.getAsignadasList().remove(asignadasListNewAsignadas);
                        oldFkidTareaOfAsignadasListNewAsignadas = em.merge(oldFkidTareaOfAsignadasListNewAsignadas);
                    }
                }
            }
            for (Tareasdeproyecto tareasdeproyectoListOldTareasdeproyecto : tareasdeproyectoListOld) {
                if (!tareasdeproyectoListNew.contains(tareasdeproyectoListOldTareasdeproyecto)) {
                    tareasdeproyectoListOldTareasdeproyecto.setFkidTarea2(null);
                    tareasdeproyectoListOldTareasdeproyecto = em.merge(tareasdeproyectoListOldTareasdeproyecto);
                }
            }
            for (Tareasdeproyecto tareasdeproyectoListNewTareasdeproyecto : tareasdeproyectoListNew) {
                if (!tareasdeproyectoListOld.contains(tareasdeproyectoListNewTareasdeproyecto)) {
                    Tareas oldFkidTarea2OfTareasdeproyectoListNewTareasdeproyecto = tareasdeproyectoListNewTareasdeproyecto.getFkidTarea2();
                    tareasdeproyectoListNewTareasdeproyecto.setFkidTarea2(tareas);
                    tareasdeproyectoListNewTareasdeproyecto = em.merge(tareasdeproyectoListNewTareasdeproyecto);
                    if (oldFkidTarea2OfTareasdeproyectoListNewTareasdeproyecto != null && !oldFkidTarea2OfTareasdeproyectoListNewTareasdeproyecto.equals(tareas)) {
                        oldFkidTarea2OfTareasdeproyectoListNewTareasdeproyecto.getTareasdeproyectoList().remove(tareasdeproyectoListNewTareasdeproyecto);
                        oldFkidTarea2OfTareasdeproyectoListNewTareasdeproyecto = em.merge(oldFkidTarea2OfTareasdeproyectoListNewTareasdeproyecto);
                    }
                }
            }
            for (Actividades actividadesListOldActividades : actividadesListOld) {
                if (!actividadesListNew.contains(actividadesListOldActividades)) {
                    actividadesListOldActividades.setFkidTarea(null);
                    actividadesListOldActividades = em.merge(actividadesListOldActividades);
                }
            }
            for (Actividades actividadesListNewActividades : actividadesListNew) {
                if (!actividadesListOld.contains(actividadesListNewActividades)) {
                    Tareas oldFkidTareaOfActividadesListNewActividades = actividadesListNewActividades.getFkidTarea();
                    actividadesListNewActividades.setFkidTarea(tareas);
                    actividadesListNewActividades = em.merge(actividadesListNewActividades);
                    if (oldFkidTareaOfActividadesListNewActividades != null && !oldFkidTareaOfActividadesListNewActividades.equals(tareas)) {
                        oldFkidTareaOfActividadesListNewActividades.getActividadesList().remove(actividadesListNewActividades);
                        oldFkidTareaOfActividadesListNewActividades = em.merge(oldFkidTareaOfActividadesListNewActividades);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tareas.getIdTarea();
                if (findTareas(id) == null) {
                    throw new NonexistentEntityException("The tareas with id " + id + " no longer exists.");
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
            Tareas tareas;
            try {
                tareas = em.getReference(Tareas.class, id);
                tareas.getIdTarea();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tareas with id " + id + " no longer exists.", enfe);
            }
            List<Asignadas> asignadasList = tareas.getAsignadasList();
            for (Asignadas asignadasListAsignadas : asignadasList) {
                asignadasListAsignadas.setFkidTarea(null);
                asignadasListAsignadas = em.merge(asignadasListAsignadas);
            }
            List<Tareasdeproyecto> tareasdeproyectoList = tareas.getTareasdeproyectoList();
            for (Tareasdeproyecto tareasdeproyectoListTareasdeproyecto : tareasdeproyectoList) {
                tareasdeproyectoListTareasdeproyecto.setFkidTarea2(null);
                tareasdeproyectoListTareasdeproyecto = em.merge(tareasdeproyectoListTareasdeproyecto);
            }
            List<Actividades> actividadesList = tareas.getActividadesList();
            for (Actividades actividadesListActividades : actividadesList) {
                actividadesListActividades.setFkidTarea(null);
                actividadesListActividades = em.merge(actividadesListActividades);
            }
            em.remove(tareas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tareas> findTareasEntities() {
        return findTareasEntities(true, -1, -1);
    }

    public List<Tareas> findTareasEntities(int maxResults, int firstResult) {
        return findTareasEntities(false, maxResults, firstResult);
    }

    private List<Tareas> findTareasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tareas.class));
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

    public Tareas findTareas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tareas.class, id);
        } finally {
            em.close();
        }
    }

    public int getTareasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tareas> rt = cq.from(Tareas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
