/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.met.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Zoran
 */
@Entity
@Table(name = "koncert")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Koncert.findAll", query = "SELECT k FROM Koncert k")
    , @NamedQuery(name = "Koncert.findByKoncertId", query = "SELECT k FROM Koncert k WHERE k.koncertId = :koncertId")
    , @NamedQuery(name = "Koncert.findByNazivKoncerta", query = "SELECT k FROM Koncert k WHERE k.nazivKoncerta = :nazivKoncerta")
    , @NamedQuery(name = "Koncert.findByDatumKoncerta", query = "SELECT k FROM Koncert k WHERE k.datumKoncerta = :datumKoncerta")
    , @NamedQuery(name = "Koncert.findByLokacijaKoncerta", query = "SELECT k FROM Koncert k WHERE k.lokacijaKoncerta = :lokacijaKoncerta")
    , @NamedQuery(name = "Koncert.findByBrojProdatihKarata", query = "SELECT k FROM Koncert k WHERE k.brojProdatihKarata = :brojProdatihKarata")})
public class Koncert implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "KONCERT_ID")
    private Integer koncertId;
    @Size(max = 255)
    @Column(name = "NAZIV_KONCERTA")
    private String nazivKoncerta;
    @Column(name = "DATUM_KONCERTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumKoncerta;
    @Size(max = 255)
    @Column(name = "LOKACIJA_KONCERTA")
    private String lokacijaKoncerta;
    @Column(name = "BROJ_PRODATIH_KARATA")
    private Integer brojProdatihKarata;
    @OneToMany(mappedBy = "koncertId")
    private Collection<HumanitarniKoncert> humanitarniKoncertCollection;
    @OneToMany(mappedBy = "koncertId")
    private Collection<KomercijalniKoncert> komercijalniKoncertCollection;

    public Koncert() {
    }

    public Koncert(Integer koncertId) {
        this.koncertId = koncertId;
    }

    public Integer getKoncertId() {
        return koncertId;
    }

    public void setKoncertId(Integer koncertId) {
        this.koncertId = koncertId;
    }

    public String getNazivKoncerta() {
        return nazivKoncerta;
    }

    public void setNazivKoncerta(String nazivKoncerta) {
        this.nazivKoncerta = nazivKoncerta;
    }

    public Date getDatumKoncerta() {
        return datumKoncerta;
    }

    public void setDatumKoncerta(Date datumKoncerta) {
        this.datumKoncerta = datumKoncerta;
    }

    public String getLokacijaKoncerta() {
        return lokacijaKoncerta;
    }

    public void setLokacijaKoncerta(String lokacijaKoncerta) {
        this.lokacijaKoncerta = lokacijaKoncerta;
    }

    public Integer getBrojProdatihKarata() {
        return brojProdatihKarata;
    }

    public void setBrojProdatihKarata(Integer brojProdatihKarata) {
        this.brojProdatihKarata = brojProdatihKarata;
    }

    @XmlTransient
    public Collection<HumanitarniKoncert> getHumanitarniKoncertCollection() {
        return humanitarniKoncertCollection;
    }

    public void setHumanitarniKoncertCollection(Collection<HumanitarniKoncert> humanitarniKoncertCollection) {
        this.humanitarniKoncertCollection = humanitarniKoncertCollection;
    }

    @XmlTransient
    public Collection<KomercijalniKoncert> getKomercijalniKoncertCollection() {
        return komercijalniKoncertCollection;
    }

    public void setKomercijalniKoncertCollection(Collection<KomercijalniKoncert> komercijalniKoncertCollection) {
        this.komercijalniKoncertCollection = komercijalniKoncertCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (koncertId != null ? koncertId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Koncert)) {
            return false;
        }
        Koncert other = (Koncert) object;
        if ((this.koncertId == null && other.koncertId != null) || (this.koncertId != null && !this.koncertId.equals(other.koncertId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.met.entity.Koncert[ koncertId=" + koncertId + " ]";
    }
    
}
