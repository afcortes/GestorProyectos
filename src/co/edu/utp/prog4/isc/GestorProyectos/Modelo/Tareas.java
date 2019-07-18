/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.prog4.isc.GestorProyectos.Modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ANDRES
 */
@Entity
@Table(name = "tareas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tareas.findAll", query = "SELECT t FROM Tareas t")
    , @NamedQuery(name = "Tareas.findByIdTarea", query = "SELECT t FROM Tareas t WHERE t.idTarea = :idTarea")
    , @NamedQuery(name = "Tareas.findByDescripcion", query = "SELECT t FROM Tareas t WHERE t.descripcion = :descripcion")})
public class Tareas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTarea")
    private Integer idTarea;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "fkidTarea")
    private List<Asignadas> asignadasList;
    @OneToMany(mappedBy = "fkidTarea2")
    private List<Tareasdeproyecto> tareasdeproyectoList;
    @OneToMany(mappedBy = "fkidTarea")
    private List<Actividades> actividadesList;

    public Tareas() {
    }

    public Tareas(Integer idTarea) {
        this.idTarea = idTarea;
    }

    public Tareas(Integer idTarea, String descripcion) {
        this.idTarea = idTarea;
        this.descripcion = descripcion;
    }

    public Integer getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Integer idTarea) {
        this.idTarea = idTarea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Asignadas> getAsignadasList() {
        return asignadasList;
    }

    public void setAsignadasList(List<Asignadas> asignadasList) {
        this.asignadasList = asignadasList;
    }

    @XmlTransient
    public List<Tareasdeproyecto> getTareasdeproyectoList() {
        return tareasdeproyectoList;
    }

    public void setTareasdeproyectoList(List<Tareasdeproyecto> tareasdeproyectoList) {
        this.tareasdeproyectoList = tareasdeproyectoList;
    }

    @XmlTransient
    public List<Actividades> getActividadesList() {
        return actividadesList;
    }

    public void setActividadesList(List<Actividades> actividadesList) {
        this.actividadesList = actividadesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTarea != null ? idTarea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tareas)) {
            return false;
        }
        Tareas other = (Tareas) object;
        if ((this.idTarea == null && other.idTarea != null) || (this.idTarea != null && !this.idTarea.equals(other.idTarea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.utp.prog4.isc.GestorProyectos.Modelo.Tareas[ idTarea=" + idTarea + " ]";
    }
    
}
