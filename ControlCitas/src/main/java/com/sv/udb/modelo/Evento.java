/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kevin
 */
@Entity
@Table(name = "Evento", catalog = "GestionDeCitas", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evento.findAll", query = "SELECT e FROM Evento e"),
    @NamedQuery(name = "Evento.findByCodiEvent", query = "SELECT e FROM Evento e WHERE e.codiEvent = :codiEvent"),
    @NamedQuery(name = "Evento.findByCodiLuga", query = "SELECT e FROM Evento e WHERE e.codiLuga = :codiLuga"),
    @NamedQuery(name = "Evento.findByNombEven", query = "SELECT e FROM Evento e WHERE e.nombEven = :nombEven"),
    @NamedQuery(name = "Evento.findByFechaInicEven", query = "SELECT e FROM Evento e WHERE e.fechaInicEven = :fechaInicEven"),
    @NamedQuery(name = "Evento.findByFechaFinaEven", query = "SELECT e FROM Evento e WHERE e.fechaFinaEven = :fechaFinaEven"),
    @NamedQuery(name = "Evento.findByHoraInicEven", query = "SELECT e FROM Evento e WHERE e.horaInicEven = :horaInicEven"),
    @NamedQuery(name = "Evento.findByHoraFinaEven", query = "SELECT e FROM Evento e WHERE e.horaFinaEven = :horaFinaEven")})
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codi_event")
    private Integer codiEvent;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codi_luga")
    private int codiLuga;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nomb_even")
    private String nombEven;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha__inic_even")
    @Temporal(TemporalType.DATE)
    private Date fechaInicEven;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha__fina_even")
    @Temporal(TemporalType.DATE)
    private Date fechaFinaEven;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "hora_inic_even")
    private String horaInicEven;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "hora_fina_even")
    private String horaFinaEven;
    @OneToMany(mappedBy = "codiEven", fetch = FetchType.EAGER)
    private List<Cita> citaList;

    public Evento() {
    }

    public Evento(Integer codiEvent) {
        this.codiEvent = codiEvent;
    }

    public Evento(Integer codiEvent, int codiLuga, String nombEven, Date fechaInicEven, Date fechaFinaEven, String horaInicEven, String horaFinaEven) {
        this.codiEvent = codiEvent;
        this.codiLuga = codiLuga;
        this.nombEven = nombEven;
        this.fechaInicEven = fechaInicEven;
        this.fechaFinaEven = fechaFinaEven;
        this.horaInicEven = horaInicEven;
        this.horaFinaEven = horaFinaEven;
    }

    public Integer getCodiEvent() {
        return codiEvent;
    }

    public void setCodiEvent(Integer codiEvent) {
        this.codiEvent = codiEvent;
    }

    public int getCodiLuga() {
        return codiLuga;
    }

    public void setCodiLuga(int codiLuga) {
        this.codiLuga = codiLuga;
    }

    public String getNombEven() {
        return nombEven;
    }

    public void setNombEven(String nombEven) {
        this.nombEven = nombEven;
    }

    public Date getFechaInicEven() {
        return fechaInicEven;
    }

    public void setFechaInicEven(Date fechaInicEven) {
        this.fechaInicEven = fechaInicEven;
    }

    public Date getFechaFinaEven() {
        return fechaFinaEven;
    }

    public void setFechaFinaEven(Date fechaFinaEven) {
        this.fechaFinaEven = fechaFinaEven;
    }

    public String getHoraInicEven() {
        return horaInicEven;
    }

    public void setHoraInicEven(String horaInicEven) {
        this.horaInicEven = horaInicEven;
    }

    public String getHoraFinaEven() {
        return horaFinaEven;
    }

    public void setHoraFinaEven(String horaFinaEven) {
        this.horaFinaEven = horaFinaEven;
    }

    @XmlTransient
    public List<Cita> getCitaList() {
        return citaList;
    }

    public void setCitaList(List<Cita> citaList) {
        this.citaList = citaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiEvent != null ? codiEvent.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evento)) {
            return false;
        }
        Evento other = (Evento) object;
        if ((this.codiEvent == null && other.codiEvent != null) || (this.codiEvent != null && !this.codiEvent.equals(other.codiEvent))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sv.udb.modelo.Evento[ codiEvent=" + codiEvent + " ]";
    }
    
}
