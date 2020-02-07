/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpsb.entidades;

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
import javax.validation.constraints.Size;

/**
 *
 * @author Leonardo Lara
 */
@Entity
@Table(name = "horas")
@NamedQueries({
    @NamedQuery(name = "Horas.findAll", query = "SELECT h FROM Horas h")
    , @NamedQuery(name = "Horas.findByIdHoras", query = "SELECT h FROM Horas h WHERE h.idHoras = :idHoras")
    , @NamedQuery(name = "Horas.findByHora", query = "SELECT h FROM Horas h WHERE h.hora = :hora")})
public class Horas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idHoras")
    private Integer idHoras;
    @Size(max = 45)
    @Column(name = "hora")
    private String hora;
    @OneToMany(mappedBy = "horaFK")
    private List<Disponibilidad> disponibilidadList;

    public Horas() {
    }

    public Horas(Integer idHoras) {
        this.idHoras = idHoras;
    }

    public Integer getIdHoras() {
        return idHoras;
    }

    public void setIdHoras(Integer idHoras) {
        this.idHoras = idHoras;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public List<Disponibilidad> getDisponibilidadList() {
        return disponibilidadList;
    }

    public void setDisponibilidadList(List<Disponibilidad> disponibilidadList) {
        this.disponibilidadList = disponibilidadList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHoras != null ? idHoras.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Horas)) {
            return false;
        }
        Horas other = (Horas) object;
        if ((this.idHoras == null && other.idHoras != null) || (this.idHoras != null && !this.idHoras.equals(other.idHoras))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "simpsb.entidades.Horas[ idHoras=" + idHoras + " ]";
    }
    
}
