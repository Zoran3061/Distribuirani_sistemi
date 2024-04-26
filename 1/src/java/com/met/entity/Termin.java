/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.met.entity;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Zoran
 */
@Entity
@Table(name = "termin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Termin.findAll", query = "SELECT t FROM Termin t")
    , @NamedQuery(name = "Termin.findByTerminId", query = "SELECT t FROM Termin t WHERE t.terminId = :terminId")
    , @NamedQuery(name = "Termin.findByKoncertId", query = "SELECT t FROM Termin t WHERE t.koncertId = :koncertId")
    , @NamedQuery(name = "Termin.findByIzvodjacId", query = "SELECT t FROM Termin t WHERE t.izvodjacId = :izvodjacId")
    , @NamedQuery(name = "Termin.findByVremeIzvodjenja", query = "SELECT t FROM Termin t WHERE t.vremeIzvodjenja = :vremeIzvodjenja")})
public class Termin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TERMIN_ID")
    private Integer terminId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "KONCERT_ID")
    private int koncertId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IZVODJAC_ID")
    private int izvodjacId;
    @Column(name = "VREME_IZVODJENJA")
    @Temporal(TemporalType.TIME)
    private Date vremeIzvodjenja;

    public Termin() {
    }

    public Termin(Integer terminId) {
        this.terminId = terminId;
    }

    public Termin(Integer terminId, int koncertId, int izvodjacId) {
        this.terminId = terminId;
        this.koncertId = koncertId;
        this.izvodjacId = izvodjacId;
    }

    public Integer getTerminId() {
        return terminId;
    }

    public void setTerminId(Integer terminId) {
        this.terminId = terminId;
    }

    public int getKoncertId() {
        return koncertId;
    }

    public void setKoncertId(int koncertId) {
        this.koncertId = koncertId;
    }

    public int getIzvodjacId() {
        return izvodjacId;
    }

    public void setIzvodjacId(int izvodjacId) {
        this.izvodjacId = izvodjacId;
    }

    public Date getVremeIzvodjenja() {
        return vremeIzvodjenja;
    }

    public void setVremeIzvodjenja(Date vremeIzvodjenja) {
        this.vremeIzvodjenja = vremeIzvodjenja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (terminId != null ? terminId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Termin)) {
            return false;
        }
        Termin other = (Termin) object;
        if ((this.terminId == null && other.terminId != null) || (this.terminId != null && !this.terminId.equals(other.terminId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.met.entity.Termin[ terminId=" + terminId + " ]";
    }
    
}
