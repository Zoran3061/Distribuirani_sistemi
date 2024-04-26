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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Zoran
 */
@Entity
@Table(name = "humanitarni_koncert")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HumanitarniKoncert.findAll", query = "SELECT h FROM HumanitarniKoncert h")
    , @NamedQuery(name = "HumanitarniKoncert.findByHumanitarniId", query = "SELECT h FROM HumanitarniKoncert h WHERE h.humanitarniId = :humanitarniId")
    , @NamedQuery(name = "HumanitarniKoncert.findByNazivUstanove", query = "SELECT h FROM HumanitarniKoncert h WHERE h.nazivUstanove = :nazivUstanove")})
public class HumanitarniKoncert implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "HUMANITARNI_ID")
    private Integer humanitarniId;
    @Size(max = 255)
    @Column(name = "NAZIV_USTANOVE")
    private String nazivUstanove;
    @JoinColumn(name = "KONCERT_ID", referencedColumnName = "KONCERT_ID")
    @ManyToOne
    private Koncert koncertId;

    public HumanitarniKoncert() {
    }

    public HumanitarniKoncert(Integer humanitarniId) {
        this.humanitarniId = humanitarniId;
    }

    public Integer getHumanitarniId() {
        return humanitarniId;
    }

    public void setHumanitarniId(Integer humanitarniId) {
        this.humanitarniId = humanitarniId;
    }

    public String getNazivUstanove() {
        return nazivUstanove;
    }

    public void setNazivUstanove(String nazivUstanove) {
        this.nazivUstanove = nazivUstanove;
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
        hash += (humanitarniId != null ? humanitarniId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HumanitarniKoncert)) {
            return false;
        }
        HumanitarniKoncert other = (HumanitarniKoncert) object;
        if ((this.humanitarniId == null && other.humanitarniId != null) || (this.humanitarniId != null && !this.humanitarniId.equals(other.humanitarniId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.met.entity.HumanitarniKoncert[ humanitarniId=" + humanitarniId + " ]";
    }
    
}
