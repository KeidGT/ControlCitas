/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kevin
 */
@Entity
@Table(name = "Visitante_cita", catalog = "sistemas_pilet", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Visitantecita.findAll", query = "SELECT v FROM Visitantecita v"),
    @NamedQuery(name = "Visitantecita.findByCodiVisiCita", query = "SELECT v FROM Visitantecita v WHERE v.codiVisiCita = :codiVisiCita"),
    @NamedQuery(name = "Visitantecita.findByCodiUsua", query = "SELECT v FROM Visitantecita v WHERE v.codiCita.codiUsua = :codiUsua"),
    @NamedQuery(name = "Visitantecita.findByCarnAlum", query = "SELECT v FROM Visitantecita v WHERE v.carnAlum = :carnAlum")})
public class Visitantecita implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codi_visi_cita")
    private Integer codiVisiCita;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "carn_alum")
    private String carnAlum;
    @JoinColumn(name = "codi_visi", referencedColumnName = "codi_visi")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Visitante codiVisi;
    @JoinColumn(name = "codi_cita", referencedColumnName = "codi_cita")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Cita codiCita;

    public Visitantecita() {
    }

    public Visitantecita(Integer codiVisiCita) {
        this.codiVisiCita = codiVisiCita;
    }

    public Visitantecita(Integer codiVisiCita, String carnAlum) {
        this.codiVisiCita = codiVisiCita;
        this.carnAlum = carnAlum;
    }

    public Integer getCodiVisiCita() {
        return codiVisiCita;
    }

    public void setCodiVisiCita(Integer codiVisiCita) {
        this.codiVisiCita = codiVisiCita;
    }

    public String getCarnAlum() {
        return carnAlum;
    }

    public void setCarnAlum(String carnAlum) {
        this.carnAlum = carnAlum;
    }

    public Visitante getCodiVisi() {
        return codiVisi;
    }

    public void setCodiVisi(Visitante codiVisi) {
        this.codiVisi = codiVisi;
    }

    public Cita getCodiCita() {
        return codiCita;
    }

    public void setCodiCita(Cita codiCita) {
        this.codiCita = codiCita;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiVisiCita != null ? codiVisiCita.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Visitantecita)) {
            return false;
        }
        Visitantecita other = (Visitantecita) object;
        if ((this.codiVisiCita == null && other.codiVisiCita != null) || (this.codiVisiCita != null && !this.codiVisiCita.equals(other.codiVisiCita))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sv.udb.modelo.Visitantecita[ codiVisiCita=" + codiVisiCita + " ]";
    }
    
}
