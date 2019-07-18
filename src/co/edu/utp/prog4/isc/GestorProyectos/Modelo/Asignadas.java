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
@Table(name = "asignadas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asignadas.findAll", query = "SELECT a FROM Asignadas a")
    , @NamedQuery(name = "Asignadas.findById", query = "SELECT a FROM Asignadas a WHERE a.id = :id")
    , @NamedQuery(name = "Asignadas.findByTerminado", query = "SELECT a FROM Asignadas a WHERE a.terminado = :terminado")})
public class Asignadas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "terminado")
    private Boolean terminado;
    @JoinColumn(name = "fk_Usu_", referencedColumnName = "usuario")
    @ManyToOne
    private Usuarios fkUsu;
    @JoinColumn(name = "fk_idPro_", referencedColumnName = "idPro")
    @ManyToOne
    private Proyectos fkidPro;
    @JoinColumn(name = "fk_idTarea_", referencedColumnName = "idTarea")
    @ManyToOne
    private Tareas fkidTarea;

    public Asignadas() {
    }

    public Asignadas(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getTerminado() {
        return terminado;
    }

    public void setTerminado(Boolean terminado) {
        this.terminado = terminado;
    }

    public Usuarios getFkUsu() {
        return fkUsu;
    }

    public void setFkUsu(Usuarios fkUsu) {
        this.fkUsu = fkUsu;
    }

    public Proyectos getFkidPro() {
        return fkidPro;
    }

    public void setFkidPro(Proyectos fkidPro) {
        this.fkidPro = fkidPro;
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
        if (!(object instanceof Asignadas)) {
            return false;
        }
        Asignadas other = (Asignadas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.utp.prog4.isc.GestorProyectos.Modelo.Asignadas[ id=" + id + " ]";
    }
    
}
