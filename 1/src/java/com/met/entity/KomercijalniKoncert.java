/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.met.entity;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Zoran
 */
@Entity
@Table(name = "komercijalni_koncert")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KomercijalniKoncert.findAll", query = "SELECT k FROM KomercijalniKoncert k")
    , @NamedQuery(name = "KomercijalniKoncert.findByKomercijalniId", query = "SELECT k FROM KomercijalniKoncert k WHERE k.komercijalniId = :komercijalniId")
    , @NamedQuery(name = "KomercijalniKoncert.findByCenaUlaznice", query = "SELECT k FROM KomercijalniKoncert k WHERE k.cenaUlaznice = :cenaUlaznice")})
public class KomercijalniKoncert implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "KOMERCIJALNI_ID")
    private Integer komercijalniId;
    @Column(name = "CENA_ULAZNICE")
    private Long cenaUlaznice;
    @JoinColumn(name = "UGOVOR_ID", referencedColumnName = "UGOVOR_ID")
    @ManyToOne(optional = false)
    private Ugovor ugovorId;
    @JoinColumn(name = "KONCERT_ID", referencedColumnName = "KONCERT_ID")
    @ManyToOne
    private Koncert koncertId;

    public KomercijalniKoncert() {
    }

    public KomercijalniKoncert(Integer komercijalniId) {
        this.komercijalniId = komercijalniId;
    }

    public Integer getKomercijalniId() {
        return komercijalniId;
    }

    public void setKomercijalniId(Integer komercijalniId) {
        this.komercijalniId = komercijalniId;
    }

    public Long getCenaUlaznice() {
        return cenaUlaznice;
    }

    public void setCenaUlaznice(Long cenaUlaznice) {
        this.cenaUlaznice = cenaUlaznice;
    }

    public Ugovor getUgovorId() {
        return ugovorId;
    }

    public void setUgovorId(Ugovor ugovorId) {
        this.ugovorId = ugovorId;
    }

    public Koncert getKoncertId() {
        return koncertId;
    }

    public void setKoncertId(Koncert koncertId) {
        this.koncertId = koncertId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (komercijalniId != null ? komercijalniId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KomercijalniKoncert)) {
            return false;
        }
        KomercijalniKoncert other = (KomercijalniKoncert) object;
        if ((this.komercijalniId == null && other.komercijalniId != null) || (this.komercijalniId != null && !this.komercijalniId.equals(other.komercijalniId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.met.entity.KomercijalniKoncert[ komercijalniId=" + komercijalniId + " ]";
    }
    
}
