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
@Table(name = "pesma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pesma.findAll", query = "SELECT p FROM Pesma p")
    , @NamedQuery(name = "Pesma.findByPesmaId", query = "SELECT p FROM Pesma p WHERE p.pesmaId = :pesmaId")
    , @NamedQuery(name = "Pesma.findByNazivPesme", query = "SELECT p FROM Pesma p WHERE p.nazivPesme = :nazivPesme")
    , @NamedQuery(name = "Pesma.findByTrajanjePesme", query = "SELECT p FROM Pesma p WHERE p.trajanjePesme = :trajanjePesme")
    , @NamedQuery(name = "Pesma.findByRedniBrojPesme", query = "SELECT p FROM Pesma p WHERE p.redniBrojPesme = :redniBrojPesme")})
public class Pesma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PESMA_ID")
    private Integer pesmaId;
    @Size(max = 255)
    @Column(name = "NAZIV_PESME")
    private String nazivPesme;
    @Size(max = 16)
    @Column(name = "TRAJANJE_PESME")
    private String trajanjePesme;
    @Column(name = "REDNI_BROJ_PESME")
    private Integer redniBrojPesme;
    @JoinColumn(name = "ALBUM_ID", referencedColumnName = "ALBUM_ID")
    @ManyToOne
    private Album albumId;

    public Pesma() {
    }

    public Pesma(Integer pesmaId) {
        this.pesmaId = pesmaId;
    }

    public Integer getPesmaId() {
        return pesmaId;
    }

    public void setPesmaId(Integer pesmaId) {
        this.pesmaId = pesmaId;
    }

    public String getNazivPesme() {
        return nazivPesme;
    }

    public void setNazivPesme(String nazivPesme) {
        this.nazivPesme = nazivPesme;
    }

    public String getTrajanjePesme() {
        return trajanjePesme;
    }

    public void setTrajanjePesme(String trajanjePesme) {
        this.trajanjePesme = trajanjePesme;
    }

    public Integer getRedniBrojPesme() {
        return redniBrojPesme;
    }

    public void setRedniBrojPesme(Integer redniBrojPesme) {
        this.redniBrojPesme = redniBrojPesme;
    }

    public Album getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Album albumId) {
        this.albumId = albumId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pesmaId != null ? pesmaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pesma)) {
            return false;
        }
        Pesma other = (Pesma) object;
        if ((this.pesmaId == null && other.pesmaId != null) || (this.pesmaId != null && !this.pesmaId.equals(other.pesmaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.met.entity.Pesma[ pesmaId=" + pesmaId + " ]";
    }
    
}
