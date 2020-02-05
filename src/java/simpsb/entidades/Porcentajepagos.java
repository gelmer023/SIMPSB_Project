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

/**
 *
 * @author Sebastián
 */
@Entity
@Table(name = "porcentajepagos")
@NamedQueries({
    @NamedQuery(name = "Porcentajepagos.findAll", query = "SELECT p FROM Porcentajepagos p")
    , @NamedQuery(name = "Porcentajepagos.findByIdPorcentaje", query = "SELECT p FROM Porcentajepagos p WHERE p.idPorcentaje = :idPorcentaje")
    , @NamedQuery(name = "Porcentajepagos.findByFecha", query = "SELECT p FROM Porcentajepagos p WHERE p.fecha = :fecha")
    , @NamedQuery(name = "Porcentajepagos.findByPorcentaje", query = "SELECT p FROM Porcentajepagos p WHERE p.porcentaje = :porcentaje")})
public class Porcentajepagos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPorcentaje")
    private Integer idPorcentaje;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "porcentaje")
    private Integer porcentaje;
    @JoinColumn(name = "idEmpleadoFK", referencedColumnName = "idEmpleado")
    @ManyToOne(optional = false)
    private Empleado idEmpleadoFK;

    public Porcentajepagos() {
    }

    public Porcentajepagos(Integer idPorcentaje) {
        this.idPorcentaje = idPorcentaje;
    }

    public Integer getIdPorcentaje() {
        return idPorcentaje;
    }

    public void setIdPorcentaje(Integer idPorcentaje) {
        this.idPorcentaje = idPorcentaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Integer porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Empleado getIdEmpleadoFK() {
        return idEmpleadoFK;
    }

    public void setIdEmpleadoFK(Empleado idEmpleadoFK) {
        this.idEmpleadoFK = idEmpleadoFK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPorcentaje != null ? idPorcentaje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Porcentajepagos)) {
            return false;
        }
        Porcentajepagos other = (Porcentajepagos) object;
        if ((this.idPorcentaje == null && other.idPorcentaje != null) || (this.idPorcentaje != null && !this.idPorcentaje.equals(other.idPorcentaje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "simpsb.entidades.Porcentajepagos[ idPorcentaje=" + idPorcentaje + " ]";
    }
    
}
