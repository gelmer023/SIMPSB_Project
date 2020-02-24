/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpsb.entidades;

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

/**
 *
 * @author Sebastián
 */
@Entity
@Table(name = "serviciosextra")
@NamedQueries({
    @NamedQuery(name = "Serviciosextra.findAll", query = "SELECT s FROM Serviciosextra s")
    , @NamedQuery(name = "Serviciosextra.findByIdServicioExtra", query = "SELECT s FROM Serviciosextra s WHERE s.idServicioExtra = :idServicioExtra")})
public class Serviciosextra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idServicioExtra")
    private Integer idServicioExtra;
    @JoinColumn(name = "idCita", referencedColumnName = "idCita")
    @ManyToOne
    private Citas idCita;
    @JoinColumn(name = "idServicio", referencedColumnName = "idServicio")
    @ManyToOne
    private Servicios idServicio;

    public Serviciosextra() {
    }

    public Serviciosextra(Integer idServicioExtra) {
        this.idServicioExtra = idServicioExtra;
    }

    public Integer getIdServicioExtra() {
        return idServicioExtra;
    }

    public void setIdServicioExtra(Integer idServicioExtra) {
        this.idServicioExtra = idServicioExtra;
    }

    public Citas getIdCita() {
        return idCita;
    }

    public void setIdCita(Citas idCita) {
        this.idCita = idCita;
    }

    public Servicios getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Servicios idServicio) {
        this.idServicio = idServicio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idServicioExtra != null ? idServicioExtra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Serviciosextra)) {
            return false;
        }
        Serviciosextra other = (Serviciosextra) object;
        if ((this.idServicioExtra == null && other.idServicioExtra != null) || (this.idServicioExtra != null && !this.idServicioExtra.equals(other.idServicioExtra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "simpsb.entidades.Serviciosextra[ idServicioExtra=" + idServicioExtra + " ]";
    }
    
}
