/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Orlando Vasquez
 */
@Entity
@Table(name = "pruebaevento", catalog = "sistemas_pilet", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pruebaevento.findAll", query = "SELECT p FROM Pruebaevento p"),
    @NamedQuery(name = "Pruebaevento.findByIdEvento", query = "SELECT p FROM Pruebaevento p WHERE p.idEvento = :idEvento"),
    @NamedQuery(name = "Pruebaevento.findByTituloEvento", query = "SELECT p FROM Pruebaevento p WHERE p.tituloEvento = :tituloEvento"),
    @NamedQuery(name = "Pruebaevento.findByFechInicEven", query = "SELECT p FROM Pruebaevento p WHERE p.fechInicEven = :fechInicEven"),
    @NamedQuery(name = "Pruebaevento.findByFechFinaEven", query = "SELECT p FROM Pruebaevento p WHERE p.fechFinaEven = :fechFinaEven"),
    @NamedQuery(name = "Pruebaevento.findByDescEvento", query = "SELECT p FROM Pruebaevento p WHERE p.descEvento = :descEvento")})
public class Pruebaevento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_evento")
    private Integer idEvento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "titulo_evento")
    private String tituloEvento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fech_inic_even")
    @Temporal(TemporalType.DATE)
    private Date fechInicEven;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fech_fina_even")
    @Temporal(TemporalType.DATE)
    private Date fechFinaEven;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "desc_evento")
    private String descEvento;

    public Pruebaevento() {
    }

    public Pruebaevento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public Pruebaevento(Integer idEvento, String tituloEvento, Date fechInicEven, Date fechFinaEven, String descEvento) {
        this.idEvento = idEvento;
        this.tituloEvento = tituloEvento;
        this.fechInicEven = fechInicEven;
        this.fechFinaEven = fechFinaEven;
        this.descEvento = descEvento;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getTituloEvento() {
        return tituloEvento;
    }

    public void setTituloEvento(String tituloEvento) {
        this.tituloEvento = tituloEvento;
    }

    public Date getFechInicEven() {
        return fechInicEven;
    }

    public void setFechInicEven(Date fechInicEven) {
        this.fechInicEven = fechInicEven;
    }

    public Date getFechFinaEven() {
        return fechFinaEven;
    }

    public void setFechFinaEven(Date fechFinaEven) {
        this.fechFinaEven = fechFinaEven;
    }

    public String getDescEvento() {
        return descEvento;
    }

    public void setDescEvento(String descEvento) {
        this.descEvento = descEvento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvento != null ? idEvento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pruebaevento)) {
            return false;
        }
        Pruebaevento other = (Pruebaevento) object;
        if ((this.idEvento == null && other.idEvento != null) || (this.idEvento != null && !this.idEvento.equals(other.idEvento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sv.udb.modelo.Pruebaevento[ idEvento=" + idEvento + " ]";
    }
    
}
