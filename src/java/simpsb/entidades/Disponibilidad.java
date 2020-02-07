/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpsb.entidades;

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
import javax.validation.constraints.Size;

/**
 *
 * @author Sebastián
 */
@Entity
@Table(name = "disponibilidad")
@NamedQueries({
    @NamedQuery(name = "Disponibilidad.findAll", query = "SELECT d FROM Disponibilidad d")
    , @NamedQuery(name = "Disponibilidad.findByIdDisponibilidad", query = "SELECT d FROM Disponibilidad d WHERE d.idDisponibilidad = :idDisponibilidad")
    , @NamedQuery(name = "Disponibilidad.findByFecha", query = "SELECT d FROM Disponibilidad d WHERE d.fecha = :fecha")
    , @NamedQuery(name = "Disponibilidad.findByEstado", query = "SELECT d FROM Disponibilidad d WHERE d.estado = :estado")})
public class Disponibilidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDisponibilidad")
    private Integer idDisponibilidad;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "horaFK", referencedColumnName = "idHoras")
    @ManyToOne
    private Horas horaFK;

    public Disponibilidad() {
    }

    public Disponibilidad(Integer idDisponibilidad) {
        this.idDisponibilidad = idDisponibilidad;
    }

    public Integer getIdDisponibilidad() {
        return idDisponibilidad;
    }

    public void setIdDisponibilidad(Integer idDisponibilidad) {
        this.idDisponibilidad = idDisponibilidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Horas getHoraFK() {
        return horaFK;
    }

    public void setHoraFK(Horas horaFK) {
        this.horaFK = horaFK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDisponibilidad != null ? idDisponibilidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Disponibilidad)) {
            return false;
        }
        Disponibilidad other = (Disponibilidad) object;
        if ((this.idDisponibilidad == null && other.idDisponibilidad != null) || (this.idDisponibilidad != null && !this.idDisponibilidad.equals(other.idDisponibilidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "simpsb.entidades.Disponibilidad[ idDisponibilidad=" + idDisponibilidad + " ]";
    }
    
}
