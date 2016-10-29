/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kevin
 */
@Entity
@Table(name = "ubicaciones", catalog = "sistemas_pilet", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ubicaciones.findAll", query = "SELECT u FROM Ubicaciones u"),
    @NamedQuery(name = "Ubicaciones.findByCodiUbi", query = "SELECT u FROM Ubicaciones u WHERE u.codiUbi = :codiUbi"),
    @NamedQuery(name = "Ubicaciones.findByNombUbi", query = "SELECT u FROM Ubicaciones u WHERE u.nombUbi = :nombUbi"),
    @NamedQuery(name = "Ubicaciones.findByDispEvent", query = "SELECT u FROM Ubicaciones u WHERE u.dispEvent = :dispEvent"),
    @NamedQuery(name = "Ubicaciones.findByDispCita", query = "SELECT u FROM Ubicaciones u WHERE u.dispCita = :dispCita"),
    @NamedQuery(name = "Ubicaciones.findByEstaUbic", query = "SELECT u FROM Ubicaciones u WHERE u.estaUbic = :estaUbic")})
public class Ubicaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codi_ubi")
    private Integer codiUbi;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nomb_ubi")
    private String nombUbi;
    @Basic(optional = false)
    @NotNull
    @Column(name = "disp_event")
    private int dispEvent;
    @Basic(optional = false)
    @NotNull
    @Column(name = "disp_cita")
    private int dispCita;
    @Basic(optional = false)
    @NotNull
    @Column(name = "esta_ubic")
    private boolean estaUbic;
    @OneToMany(mappedBy = "codiUbi", fetch = FetchType.EAGER)
    private List<Cita> citaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codiUbi", fetch = FetchType.EAGER)
    private List<Evento> eventoList;

    public Ubicaciones() {
    }

    public Ubicaciones(Integer codiUbi) {
        this.codiUbi = codiUbi;
    }

    public Ubicaciones(Integer codiUbi, String nombUbi, int dispEvent, int dispCita, boolean estaUbic) {
        this.codiUbi = codiUbi;
        this.nombUbi = nombUbi;
        this.dispEvent = dispEvent;
        this.dispCita = dispCita;
        this.estaUbic = estaUbic;
    }

    public Integer getCodiUbi() {
        return codiUbi;
    }

    public void setCodiUbi(Integer codiUbi) {
        this.codiUbi = codiUbi;
    }

    public String getNombUbi() {
        return nombUbi;
    }

    public void setNombUbi(String nombUbi) {
        this.nombUbi = nombUbi;
    }

    public int getDispEvent() {
        return dispEvent;
    }

    public void setDispEvent(int dispEvent) {
        this.dispEvent = dispEvent;
    }

    public int getDispCita() {
        return dispCita;
    }

    public void setDispCita(int dispCita) {
        this.dispCita = dispCita;
    }

    public boolean getEstaUbic() {
        return estaUbic;
    }

    public void setEstaUbic(boolean estaUbic) {
        this.estaUbic = estaUbic;
    }

    @XmlTransient
    public List<Cita> getCitaList() {
        return citaList;
    }

    public void setCitaList(List<Cita> citaList) {
        this.citaList = citaList;
    }

    @XmlTransient
    public List<Evento> getEventoList() {
        return eventoList;
    }

    public void setEventoList(List<Evento> eventoList) {
        this.eventoList = eventoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiUbi != null ? codiUbi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ubicaciones)) {
            return false;
        }
        Ubicaciones other = (Ubicaciones) object;
        if ((this.codiUbi == null && other.codiUbi != null) || (this.codiUbi != null && !this.codiUbi.equals(other.codiUbi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sv.udb.modelo.Ubicaciones[ codiUbi=" + codiUbi + " ]";
    }
    
}
