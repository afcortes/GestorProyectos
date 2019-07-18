/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.prog4.isc.GestorProyectos.Controladores;

import co.edu.utp.prog4.isc.GestorProyectos.Controlador.exceptions.exceptions.NonexistentEntityException;
import co.edu.utp.prog4.isc.GestorProyectos.Controlador.exceptions.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.edu.utp.prog4.isc.GestorProyectos.Modelo.Roles;
import co.edu.utp.prog4.isc.GestorProyectos.Modelo.Asignadas;
import java.util.ArrayList;
import java.util.List;
import co.edu.utp.prog4.isc.GestorProyectos.Modelo.Actividades;
import co.edu.utp.prog4.isc.GestorProyectos.Modelo.Usuarios;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ANDRES
 */
public class UsuariosJpaController implements Serializable {

    public UsuariosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("GestorProyectosPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarios usuarios) throws PreexistingEntityException, Exception {
        if (usuarios.getAsignadasList() == null) {
            usuarios.setAsignadasList(new ArrayList<Asignadas>());
        }
        if (usuarios.getActividadesList() == null) {
            usuarios.setActividadesList(new ArrayList<Actividades>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Roles fkRol = usuarios.getFkRol();
            if (fkRol != null) {
                fkRol = em.getReference(fkRol.getClass(), fkRol.getIdRol());
                usuarios.setFkRol(fkRol);
            }
            List<Asignadas> attachedAsignadasList = new ArrayList<Asignadas>();
            for (Asignadas asignadasListAsignadasToAttach : usuarios.getAsignadasList()) {
                asignadasListAsignadasToAttach = em.getReference(asignadasListAsignadasToAttach.getClass(), asignadasListAsignadasToAttach.getId());
                attachedAsignadasList.add(asignadasListAsignadasToAttach);
            }
            usuarios.setAsignadasList(attachedAsignadasList);
            List<Actividades> attachedActividadesList = new ArrayList<Actividades>();
            for (Actividades actividadesListActividadesToAttach : usuarios.getActividadesList()) {
                actividadesListActividadesToAttach = em.getReference(actividadesListActividadesToAttach.getClass(), actividadesListActividadesToAttach.getId());
                attachedActividadesList.add(actividadesListActividadesToAttach);
            }
            usuarios.setActividadesList(attachedActividadesList);
            em.persist(usuarios);
            if (fkRol != null) {
                fkRol.getUsuariosList().add(usuarios);
                fkRol = em.merge(fkRol);
            }
            for (Asignadas asignadasListAsignadas : usuarios.getAsignadasList()) {
                Usuarios oldFkUsuOfAsignadasListAsignadas = asignadasListAsignadas.getFkUsu();
                asignadasListAsignadas.setFkUsu(usuarios);
                asignadasListAsignadas = em.merge(asignadasListAsignadas);
                if (oldFkUsuOfAsignadasListAsignadas != null) {
                    oldFkUsuOfAsignadasListAsignadas.getAsignadasList().remove(asignadasListAsignadas);
                    oldFkUsuOfAsignadasListAsignadas = em.merge(oldFkUsuOfAsignadasListAsignadas);
                }
            }
            for (Actividades actividadesListActividades : usuarios.getActividadesList()) {
                Usuarios oldFkUsuOfActividadesListActividades = actividadesListActividades.getFkUsu();
                actividadesListActividades.setFkUsu(usuarios);
                actividadesListActividades = em.merge(actividadesListActividades);
                if (oldFkUsuOfActividadesListActividades != null) {
                    oldFkUsuOfActividadesListActividades.getActividadesList().remove(actividadesListActividades);
                    oldFkUsuOfActividadesListActividades = em.merge(oldFkUsuOfActividadesListActividades);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuarios(usuarios.getUsuario()) != null) {
                throw new PreexistingEntityException("Usuarios " + usuarios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getUsuario());
            Roles fkRolOld = persistentUsuarios.getFkRol();
            Roles fkRolNew = usuarios.getFkRol();
            List<Asignadas> asignadasListOld = persistentUsuarios.getAsignadasList();
            List<Asignadas> asignadasListNew = usuarios.getAsignadasList();
            List<Actividades> actividadesListOld = persistentUsuarios.getActividadesList();
            List<Actividades> actividadesListNew = usuarios.getActividadesList();
            if (fkRolNew != null) {
                fkRolNew = em.getReference(fkRolNew.getClass(), fkRolNew.getIdRol());
                usuarios.setFkRol(fkRolNew);
            }
            List<Asignadas> attachedAsignadasListNew = new ArrayList<Asignadas>();
            for (Asignadas asignadasListNewAsignadasToAttach : asignadasListNew) {
                asignadasListNewAsignadasToAttach = em.getReference(asignadasListNewAsignadasToAttach.getClass(), asignadasListNewAsignadasToAttach.getId());
                attachedAsignadasListNew.add(asignadasListNewAsignadasToAttach);
            }
            asignadasListNew = attachedAsignadasListNew;
            usuarios.setAsignadasList(asignadasListNew);
            List<Actividades> attachedActividadesListNew = new ArrayList<Actividades>();
            for (Actividades actividadesListNewActividadesToAttach : actividadesListNew) {
                actividadesListNewActividadesToAttach = em.getReference(actividadesListNewActividadesToAttach.getClass(), actividadesListNewActividadesToAttach.getId());
                attachedActividadesListNew.add(actividadesListNewActividadesToAttach);
            }
            actividadesListNew = attachedActividadesListNew;
            usuarios.setActividadesList(actividadesListNew);
            usuarios = em.merge(usuarios);
            if (fkRolOld != null && !fkRolOld.equals(fkRolNew)) {
                fkRolOld.getUsuariosList().remove(usuarios);
                fkRolOld = em.merge(fkRolOld);
            }
            if (fkRolNew != null && !fkRolNew.equals(fkRolOld)) {
                fkRolNew.getUsuariosList().add(usuarios);
                fkRolNew = em.merge(fkRolNew);
            }
            for (Asignadas asignadasListOldAsignadas : asignadasListOld) {
                if (!asignadasListNew.contains(asignadasListOldAsignadas)) {
                    asignadasListOldAsignadas.setFkUsu(null);
                    asignadasListOldAsignadas = em.merge(asignadasListOldAsignadas);
                }
            }
            for (Asignadas asignadasListNewAsignadas : asignadasListNew) {
                if (!asignadasListOld.contains(asignadasListNewAsignadas)) {
                    Usuarios oldFkUsuOfAsignadasListNewAsignadas = asignadasListNewAsignadas.getFkUsu();
                    asignadasListNewAsignadas.setFkUsu(usuarios);
                    asignadasListNewAsignadas = em.merge(asignadasListNewAsignadas);
                    if (oldFkUsuOfAsignadasListNewAsignadas != null && !oldFkUsuOfAsignadasListNewAsignadas.equals(usuarios)) {
                        oldFkUsuOfAsignadasListNewAsignadas.getAsignadasList().remove(asignadasListNewAsignadas);
                        oldFkUsuOfAsignadasListNewAsignadas = em.merge(oldFkUsuOfAsignadasListNewAsignadas);
                    }
                }
            }
            for (Actividades actividadesListOldActividades : actividadesListOld) {
                if (!actividadesListNew.contains(actividadesListOldActividades)) {
                    actividadesListOldActividades.setFkUsu(null);
                    actividadesListOldActividades = em.merge(actividadesListOldActividades);
                }
            }
            for (Actividades actividadesListNewActividades : actividadesListNew) {
                if (!actividadesListOld.contains(actividadesListNewActividades)) {
                    Usuarios oldFkUsuOfActividadesListNewActividades = actividadesListNewActividades.getFkUsu();
                    actividadesListNewActividades.setFkUsu(usuarios);
                    actividadesListNewActividades = em.merge(actividadesListNewActividades);
                    if (oldFkUsuOfActividadesListNewActividades != null && !oldFkUsuOfActividadesListNewActividades.equals(usuarios)) {
                        oldFkUsuOfActividadesListNewActividades.getActividadesList().remove(actividadesListNewActividades);
                        oldFkUsuOfActividadesListNewActividades = em.merge(oldFkUsuOfActividadesListNewActividades);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuarios.getUsuario();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            Roles fkRol = usuarios.getFkRol();
            if (fkRol != null) {
                fkRol.getUsuariosList().remove(usuarios);
                fkRol = em.merge(fkRol);
            }
            List<Asignadas> asignadasList = usuarios.getAsignadasList();
            for (Asignadas asignadasListAsignadas : asignadasList) {
                asignadasListAsignadas.setFkUsu(null);
                asignadasListAsignadas = em.merge(asignadasListAsignadas);
            }
            List<Actividades> actividadesList = usuarios.getActividadesList();
            for (Actividades actividadesListActividades : actividadesList) {
                actividadesListActividades.setFkUsu(null);
                actividadesListActividades = em.merge(actividadesListActividades);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public Usuarios findUsuarios(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
