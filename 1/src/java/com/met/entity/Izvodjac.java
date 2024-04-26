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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Zoran
 */
@Entity
@Table(name = "izvodjac")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Izvodjac.findAll", query = "SELECT i FROM Izvodjac i")
    , @NamedQuery(name = "Izvodjac.findByIzvodjacId", query = "SELECT i FROM Izvodjac i WHERE i.izvodjacId = :izvodjacId")
    , @NamedQuery(name = "Izvodjac.findByNaziv", query = "SELECT i FROM Izvodjac i WHERE i.naziv = :naziv")
    , @NamedQuery(name = "Izvodjac.findByKontaktTelefon", query = "SELECT i FROM Izvodjac i WHERE i.kontaktTelefon = :kontaktTelefon")
    , @NamedQuery(name = "Izvodjac.findByMail", query = "SELECT i FROM Izvodjac i WHERE i.mail = :mail")
    , @NamedQuery(name = "Izvodjac.findByWebAdresa", query = "SELECT i FROM Izvodjac i WHERE i.webAdresa = :webAdresa")})
public class Izvodjac implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IZVODJAC_ID")
    private Integer izvodjacId;
    @Size(max = 128)
    @Column(name = "NAZIV")
    private String naziv;
    @Size(max = 128)
    @Column(name = "KONTAKT_TELEFON")
    private String kontaktTelefon;
    @Size(max = 255)
    @Column(name = "MAIL_")
    private String mail;
    @Size(max = 255)
    @Column(name = "WEB_ADRESA")
    private String webAdresa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "izvodjacId")
    private Collection<Snimio> snimioCollection;

    public Izvodjac() {
    }

    public Izvodjac(Integer izvodjacId) {
        this.izvodjacId = izvodjacId;
    }

    public Integer getIzvodjacId() {
        return izvodjacId;
    }

    public void setIzvodjacId(Integer izvodjacId) {
        this.izvodjacId = izvodjacId;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getKontaktTelefon() {
        return kontaktTelefon;
    }

    public void setKontaktTelefon(String kontaktTelefon) {
        this.kontaktTelefon = kontaktTelefon;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getWebAdresa() {
        return webAdresa;
    }

    public void setWebAdresa(String webAdresa) {
        this.webAdresa = webAdresa;
    }

    @XmlTransient
    public Collection<Snimio> getSnimioCollection() {
        return snimioCollection;
    }

    public void setSnimioCollection(Collection<Snimio> snimioCollection) {
        this.snimioCollection = snimioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (izvodjacId != null ? izvodjacId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Izvodjac)) {
            return false;
        }
        Izvodjac other = (Izvodjac) object;
        if ((this.izvodjacId == null && other.izvodjacId != null) || (this.izvodjacId != null && !this.izvodjacId.equals(other.izvodjacId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.met.entity.Izvodjac[ izvodjacId=" + izvodjacId + " ]";
    }
    
}
