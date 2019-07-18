/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.prog4.isc.GestorProyectos.Modelo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ANDRES
 */
@Entity
@Table(name = "actividades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Actividades.findAll", query = "SELECT a FROM Actividades a")
    , @NamedQuery(name = "Actividades.findById", query = "SELECT a FROM Actividades a WHERE a.id = :id")
    , @NamedQuery(name = "Actividades.findByTiempo", query = "SELECT a FROM Actividades a WHERE a.tiempo = :tiempo")
    , @NamedQuery(name = "Actividades.findByDescripcion", query = "SELECT a FROM Actividades a WHERE a.descripcion = :descripcion")})
public class Actividades implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "tiempo")
    @Temporal(TemporalType.TIME)
    private Date tiempo;
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "fk_Usu", referencedColumnName = "usuario")
    @ManyToOne
    private Usuarios fkUsu;
    @JoinColumn(name = "fk_idPro_2", referencedColumnName = "idPro")
    @ManyToOne
    private Proyectos fkidPro2;
    @JoinColumn(name = "fk_idTarea", referencedColumnName = "idTarea")
    @ManyToOne
    private Tareas fkidTarea;

    public Actividades() {
    }

    public Actividades(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTiempo() {
        return tiempo;
    }

    public void setTiempo(Date tiempo) {
        this.tiempo = tiempo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuarios getFkUsu() {
        return fkUsu;
    }

    public void setFkUsu(Usuarios fkUsu) {
        this.fkUsu = fkUsu;
    }

    public Proyectos getFkidPro2() {
        return fkidPro2;
    }

    public void setFkidPro2(Proyectos fkidPro2) {
        this.fkidPro2 = fkidPro2;
    }

    public Tareas getFkidTarea() {
        return fkidTarea;
    }

    public void setFkidTarea(Tareas fkidTarea) {
        this.fkidTarea = fkidTarea;
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
        if (!(object instanceof Actividades)) {
            return false;
        }
        Actividades other = (Actividades) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.utp.prog4.isc.GestorProyectos.Modelo.Actividades[ id=" + id + " ]";
    }
    
}
