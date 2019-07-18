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
@Table(name = "proyectos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proyectos.findAll", query = "SELECT p FROM Proyectos p")
    , @NamedQuery(name = "Proyectos.findByIdPro", query = "SELECT p FROM Proyectos p WHERE p.idPro = :idPro")
    , @NamedQuery(name = "Proyectos.findByNombreProyecto", query = "SELECT p FROM Proyectos p WHERE p.nombreProyecto = :nombreProyecto")})
public class Proyectos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPro")
    private Integer idPro;
    @Column(name = "nombreProyecto")
    private String nombreProyecto;
    @OneToMany(mappedBy = "fkidPro")
    private List<Asignadas> asignadasList;
    @OneToMany(mappedBy = "fkidPro")
    private List<Tareasdeproyecto> tareasdeproyectoList;
    @OneToMany(mappedBy = "fkidPro2")
    private List<Actividades> actividadesList;

    public Proyectos() {
    }

    public Proyectos(Integer idPro) {
        this.idPro = idPro;
    }

    public Integer getIdPro() {
        return idPro;
    }

    public void setIdPro(Integer idPro) {
        this.idPro = idPro;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
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
        hash += (idPro != null ? idPro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proyectos)) {
            return false;
        }
        Proyectos other = (Proyectos) object;
        if ((this.idPro == null && other.idPro != null) || (this.idPro != null && !this.idPro.equals(other.idPro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.utp.prog4.isc.GestorProyectos.Modelo.Proyectos[ idPro=" + idPro + " ]";
    }
    
}
