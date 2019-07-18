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
import co.edu.utp.prog4.isc.GestorProyectos.Modelo.Proyectos;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ANDRES
 */
public class ProyectosJpaController implements Serializable {

    public ProyectosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("GestorProyectosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proyectos proyectos) {
        if (proyectos.getAsignadasList() == null) {
            proyectos.setAsignadasList(new ArrayList<Asignadas>());
        }
        if (proyectos.getTareasdeproyectoList() == null) {
            proyectos.setTareasdeproyectoList(new ArrayList<Tareasdeproyecto>());
        }
        if (proyectos.getActividadesList() == null) {
            proyectos.setActividadesList(new ArrayList<Actividades>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Asignadas> attachedAsignadasList = new ArrayList<Asignadas>();
            for (Asignadas asignadasListAsignadasToAttach : proyectos.getAsignadasList()) {
                asignadasListAsignadasToAttach = em.getReference(asignadasListAsignadasToAttach.getClass(), asignadasListAsignadasToAttach.getId());
                attachedAsignadasList.add(asignadasListAsignadasToAttach);
            }
            proyectos.setAsignadasList(attachedAsignadasList);
            List<Tareasdeproyecto> attachedTareasdeproyectoList = new ArrayList<Tareasdeproyecto>();
            for (Tareasdeproyecto tareasdeproyectoListTareasdeproyectoToAttach : proyectos.getTareasdeproyectoList()) {
                tareasdeproyectoListTareasdeproyectoToAttach = em.getReference(tareasdeproyectoListTareasdeproyectoToAttach.getClass(), tareasdeproyectoListTareasdeproyectoToAttach.getId());
                attachedTareasdeproyectoList.add(tareasdeproyectoListTareasdeproyectoToAttach);
            }
            proyectos.setTareasdeproyectoList(attachedTareasdeproyectoList);
            List<Actividades> attachedActividadesList = new ArrayList<Actividades>();
            for (Actividades actividadesListActividadesToAttach : proyectos.getActividadesList()) {
                actividadesListActividadesToAttach = em.getReference(actividadesListActividadesToAttach.getClass(), actividadesListActividadesToAttach.getId());
                attachedActividadesList.add(actividadesListActividadesToAttach);
            }
            proyectos.setActividadesList(attachedActividadesList);
            em.persist(proyectos);
            for (Asignadas asignadasListAsignadas : proyectos.getAsignadasList()) {
                Proyectos oldFkidProOfAsignadasListAsignadas = asignadasListAsignadas.getFkidPro();
                asignadasListAsignadas.setFkidPro(proyectos);
                asignadasListAsignadas = em.merge(asignadasListAsignadas);
                if (oldFkidProOfAsignadasListAsignadas != null) {
                    oldFkidProOfAsignadasListAsignadas.getAsignadasList().remove(asignadasListAsignadas);
                    oldFkidProOfAsignadasListAsignadas = em.merge(oldFkidProOfAsignadasListAsignadas);
                }
            }
            for (Tareasdeproyecto tareasdeproyectoListTareasdeproyecto : proyectos.getTareasdeproyectoList()) {
                Proyectos oldFkidProOfTareasdeproyectoListTareasdeproyecto = tareasdeproyectoListTareasdeproyecto.getFkidPro();
                tareasdeproyectoListTareasdeproyecto.setFkidPro(proyectos);
                tareasdeproyectoListTareasdeproyecto = em.merge(tareasdeproyectoListTareasdeproyecto);
                if (oldFkidProOfTareasdeproyectoListTareasdeproyecto != null) {
                    oldFkidProOfTareasdeproyectoListTareasdeproyecto.getTareasdeproyectoList().remove(tareasdeproyectoListTareasdeproyecto);
                    oldFkidProOfTareasdeproyectoListTareasdeproyecto = em.merge(oldFkidProOfTareasdeproyectoListTareasdeproyecto);
                }
            }
            for (Actividades actividadesListActividades : proyectos.getActividadesList()) {
                Proyectos oldFkidPro2OfActividadesListActividades = actividadesListActividades.getFkidPro2();
                actividadesListActividades.setFkidPro2(proyectos);
                actividadesListActividades = em.merge(actividadesListActividades);
                if (oldFkidPro2OfActividadesListActividades != null) {
                    oldFkidPro2OfActividadesListActividades.getActividadesList().remove(actividadesListActividades);
                    oldFkidPro2OfActividadesListActividades = em.merge(oldFkidPro2OfActividadesListActividades);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proyectos proyectos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proyectos persistentProyectos = em.find(Proyectos.class, proyectos.getIdPro());
            List<Asignadas> asignadasListOld = persistentProyectos.getAsignadasList();
            List<Asignadas> asignadasListNew = proyectos.getAsignadasList();
            List<Tareasdeproyecto> tareasdeproyectoListOld = persistentProyectos.getTareasdeproyectoList();
            List<Tareasdeproyecto> tareasdeproyectoListNew = proyectos.getTareasdeproyectoList();
            List<Actividades> actividadesListOld = persistentProyectos.getActividadesList();
            List<Actividades> actividadesListNew = proyectos.getActividadesList();
            List<Asignadas> attachedAsignadasListNew = new ArrayList<Asignadas>();
            for (Asignadas asignadasListNewAsignadasToAttach : asignadasListNew) {
                asignadasListNewAsignadasToAttach = em.getReference(asignadasListNewAsignadasToAttach.getClass(), asignadasListNewAsignadasToAttach.getId());
                attachedAsignadasListNew.add(asignadasListNewAsignadasToAttach);
            }
            asignadasListNew = attachedAsignadasListNew;
            proyectos.setAsignadasList(asignadasListNew);
            List<Tareasdeproyecto> attachedTareasdeproyectoListNew = new ArrayList<Tareasdeproyecto>();
            for (Tareasdeproyecto tareasdeproyectoListNewTareasdeproyectoToAttach : tareasdeproyectoListNew) {
                tareasdeproyectoListNewTareasdeproyectoToAttach = em.getReference(tareasdeproyectoListNewTareasdeproyectoToAttach.getClass(), tareasdeproyectoListNewTareasdeproyectoToAttach.getId());
                attachedTareasdeproyectoListNew.add(tareasdeproyectoListNewTareasdeproyectoToAttach);
            }
            tareasdeproyectoListNew = attachedTareasdeproyectoListNew;
            proyectos.setTareasdeproyectoList(tareasdeproyectoListNew);
            List<Actividades> attachedActividadesListNew = new ArrayList<Actividades>();
            for (Actividades actividadesListNewActividadesToAttach : actividadesListNew) {
                actividadesListNewActividadesToAttach = em.getReference(actividadesListNewActividadesToAttach.getClass(), actividadesListNewActividadesToAttach.getId());
                attachedActividadesListNew.add(actividadesListNewActividadesToAttach);
            }
            actividadesListNew = attachedActividadesListNew;
            proyectos.setActividadesList(actividadesListNew);
            proyectos = em.merge(proyectos);
            for (Asignadas asignadasListOldAsignadas : asignadasListOld) {
                if (!asignadasListNew.contains(asignadasListOldAsignadas)) {
                    asignadasListOldAsignadas.setFkidPro(null);
                    asignadasListOldAsignadas = em.merge(asignadasListOldAsignadas);
                }
            }
            for (Asignadas asignadasListNewAsignadas : asignadasListNew) {
                if (!asignadasListOld.contains(asignadasListNewAsignadas)) {
                    Proyectos oldFkidProOfAsignadasListNewAsignadas = asignadasListNewAsignadas.getFkidPro();
                    asignadasListNewAsignadas.setFkidPro(proyectos);
                    asignadasListNewAsignadas = em.merge(asignadasListNewAsignadas);
                    if (oldFkidProOfAsignadasListNewAsignadas != null && !oldFkidProOfAsignadasListNewAsignadas.equals(proyectos)) {
                        oldFkidProOfAsignadasListNewAsignadas.getAsignadasList().remove(asignadasListNewAsignadas);
                        oldFkidProOfAsignadasListNewAsignadas = em.merge(oldFkidProOfAsignadasListNewAsignadas);
                    }
                }
            }
            for (Tareasdeproyecto tareasdeproyectoListOldTareasdeproyecto : tareasdeproyectoListOld) {
                if (!tareasdeproyectoListNew.contains(tareasdeproyectoListOldTareasdeproyecto)) {
                    tareasdeproyectoListOldTareasdeproyecto.setFkidPro(null);
                    tareasdeproyectoListOldTareasdeproyecto = em.merge(tareasdeproyectoListOldTareasdeproyecto);
                }
            }
            for (Tareasdeproyecto tareasdeproyectoListNewTareasdeproyecto : tareasdeproyectoListNew) {
                if (!tareasdeproyectoListOld.contains(tareasdeproyectoListNewTareasdeproyecto)) {
                    Proyectos oldFkidProOfTareasdeproyectoListNewTareasdeproyecto = tareasdeproyectoListNewTareasdeproyecto.getFkidPro();
                    tareasdeproyectoListNewTareasdeproyecto.setFkidPro(proyectos);
                    tareasdeproyectoListNewTareasdeproyecto = em.merge(tareasdeproyectoListNewTareasdeproyecto);
                    if (oldFkidProOfTareasdeproyectoListNewTareasdeproyecto != null && !oldFkidProOfTareasdeproyectoListNewTareasdeproyecto.equals(proyectos)) {
                        oldFkidProOfTareasdeproyectoListNewTareasdeproyecto.getTareasdeproyectoList().remove(tareasdeproyectoListNewTareasdeproyecto);
                        oldFkidProOfTareasdeproyectoListNewTareasdeproyecto = em.merge(oldFkidProOfTareasdeproyectoListNewTareasdeproyecto);
                    }
                }
            }
            for (Actividades actividadesListOldActividades : actividadesListOld) {
                if (!actividadesListNew.contains(actividadesListOldActividades)) {
                    actividadesListOldActividades.setFkidPro2(null);
                    actividadesListOldActividades = em.merge(actividadesListOldActividades);
                }
            }
            for (Actividades actividadesListNewActividades : actividadesListNew) {
                if (!actividadesListOld.contains(actividadesListNewActividades)) {
                    Proyectos oldFkidPro2OfActividadesListNewActividades = actividadesListNewActividades.getFkidPro2();
                    actividadesListNewActividades.setFkidPro2(proyectos);
                    actividadesListNewActividades = em.merge(actividadesListNewActividades);
                    if (oldFkidPro2OfActividadesListNewActividades != null && !oldFkidPro2OfActividadesListNewActividades.equals(proyectos)) {
                        oldFkidPro2OfActividadesListNewActividades.getActividadesList().remove(actividadesListNewActividades);
                        oldFkidPro2OfActividadesListNewActividades = em.merge(oldFkidPro2OfActividadesListNewActividades);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proyectos.getIdPro();
                if (findProyectos(id) == null) {
                    throw new NonexistentEntityException("The proyectos with id " + id + " no longer exists.");
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
            Proyectos proyectos;
            try {
                proyectos = em.getReference(Proyectos.class, id);
                proyectos.getIdPro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proyectos with id " + id + " no longer exists.", enfe);
            }
            List<Asignadas> asignadasList = proyectos.getAsignadasList();
            for (Asignadas asignadasListAsignadas : asignadasList) {
                asignadasListAsignadas.setFkidPro(null);
                asignadasListAsignadas = em.merge(asignadasListAsignadas);
            }
            List<Tareasdeproyecto> tareasdeproyectoList = proyectos.getTareasdeproyectoList();
            for (Tareasdeproyecto tareasdeproyectoListTareasdeproyecto : tareasdeproyectoList) {
                tareasdeproyectoListTareasdeproyecto.setFkidPro(null);
                tareasdeproyectoListTareasdeproyecto = em.merge(tareasdeproyectoListTareasdeproyecto);
            }
            List<Actividades> actividadesList = proyectos.getActividadesList();
            for (Actividades actividadesListActividades : actividadesList) {
                actividadesListActividades.setFkidPro2(null);
                actividadesListActividades = em.merge(actividadesListActividades);
            }
            em.remove(proyectos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proyectos> findProyectosEntities() {
        return findProyectosEntities(true, -1, -1);
    }

    public List<Proyectos> findProyectosEntities(int maxResults, int firstResult) {
        return findProyectosEntities(false, maxResults, firstResult);
    }

    private List<Proyectos> findProyectosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proyectos.class));
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

    public Proyectos findProyectos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proyectos.class, id);
        } finally {
            em.close();
        }
    }

    public int getProyectosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proyectos> rt = cq.from(Proyectos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
