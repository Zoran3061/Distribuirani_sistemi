/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.met.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author Zoran
 */
@Entity
@Table(name = "ugovor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ugovor.findAll", query = "SELECT u FROM Ugovor u")
    , @NamedQuery(name = "Ugovor.findByUgovorId", query = "SELECT u FROM Ugovor u WHERE u.ugovorId = :ugovorId")
    , @NamedQuery(name = "Ugovor.findByIzvodjacId", query = "SELECT u FROM Ugovor u WHERE u.izvodjacId = :izvodjacId")
    , @NamedQuery(name = "Ugovor.findByMenadzerId", query = "SELECT u FROM Ugovor u WHERE u.menadzerId = :menadzerId")
    , @NamedQuery(name = "Ugovor.findByBrojUgovora", query = "SELECT u FROM Ugovor u WHERE u.brojUgovora = :brojUgovora")
    , @NamedQuery(name = "Ugovor.findByProcenat", query = "SELECT u FROM Ugovor u WHERE u.procenat = :procenat")})
public class Ugovor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "UGOVOR_ID")
    private Integer ugovorId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IZVODJAC_ID")
    private int izvodjacId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MENADZER_ID")
    private int menadzerId;
    @Size(max = 255)
    @Column(name = "BROJ_UGOVORA")
    private String brojUgovora;
    @Size(max = 16)
    @Column(name = "PROCENAT")
    private String procenat;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ugovorId")
    private Collection<KomercijalniKoncert> komercijalniKoncertCollection;

    public Ugovor() {
    }

    public Ugovor(Integer ugovorId) {
        this.ugovorId = ugovorId;
    }

    public Ugovor(Integer ugovorId, int izvodjacId, int menadzerId) {
        this.ugovorId = ugovorId;
        this.izvodjacId = izvodjacId;
        this.menadzerId = menadzerId;
    }

    public Integer getUgovorId() {
        return ugovorId;
    }

    public void setUgovorId(Integer ugovorId) {
        this.ugovorId = ugovorId;
    }

    public int getIzvodjacId() {
        return izvodjacId;
    }

    public void setIzvodjacId(int izvodjacId) {
        this.izvodjacId = izvodjacId;
    }

    public int getMenadzerId() {
        return menadzerId;
    }

    public void setMenadzerId(int menadzerId) {
        this.menadzerId = menadzerId;
    }

    public String getBrojUgovora() {
        return brojUgovora;
    }

    public void setBrojUgovora(String brojUgovora) {
        this.brojUgovora = brojUgovora;
    }

    public String getProcenat() {
        return procenat;
    }

    public void setProcenat(String procenat) {
        this.procenat = procenat;
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
        hash += (ugovorId != null ? ugovorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ugovor)) {
            return false;
        }
        Ugovor other = (Ugovor) object;
        if ((this.ugovorId == null && other.ugovorId != null) || (this.ugovorId != null && !this.ugovorId.equals(other.ugovorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.met.entity.Ugovor[ ugovorId=" + ugovorId + " ]";
    }
    
}
