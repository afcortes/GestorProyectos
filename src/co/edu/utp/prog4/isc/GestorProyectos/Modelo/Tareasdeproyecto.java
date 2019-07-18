/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.prog4.isc.GestorProyectos.Modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ANDRES
 */
@Entity
@Table(name = "tareasdeproyecto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tareasdeproyecto.findAll", query = "SELECT t FROM Tareasdeproyecto t")
    , @NamedQuery(name = "Tareasdeproyecto.findById", query = "SELECT t FROM Tareasdeproyecto t WHERE t.id = :id")})
public class Tareasdeproyecto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "fk_idPro", referencedColumnName = "idPro")
    @ManyToOne
    private Proyectos fkidPro;
    @JoinColumn(name = "fk_idTarea_2", referencedColumnName = "idTarea")
    @ManyToOne
    private Tareas fkidTarea2;

    public Tareasdeproyecto() {
    }

    public Tareasdeproyecto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Proyectos getFkidPro() {
        return fkidPro;
    }

    public void setFkidPro(Proyectos fkidPro) {
        this.fkidPro = fkidPro;
    }

    public Tareas getFkidTarea2() {
        return fkidTarea2;
    }

    public void setFkidTarea2(Tareas fkidTarea2) {
        this.fkidTarea2 = fkidTarea2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tareasdeproyecto)) {
            return false;
        }
        Tareasdeproyecto other = (Tareasdeproyecto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.utp.prog4.isc.GestorProyectos.Modelo.Tareasdeproyecto[ id=" + id + " ]";
    }
    
}
