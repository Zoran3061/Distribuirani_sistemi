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
@Table(name = "snimio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Snimio.findAll", query = "SELECT s FROM Snimio s")
    , @NamedQuery(name = "Snimio.findBySnimioId", query = "SELECT s FROM Snimio s WHERE s.snimioId = :snimioId")
    , @NamedQuery(name = "Snimio.findByBrojSati", query = "SELECT s FROM Snimio s WHERE s.brojSati = :brojSati")})
public class Snimio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SNIMIO_ID")
    private Integer snimioId;
    @Column(name = "BROJ_SATI")
    private Integer brojSati;
    @JoinColumn(name = "IZVODJAC_ID", referencedColumnName = "IZVODJAC_ID")
    @ManyToOne(optional = false)
    private Izvodjac izvodjacId;
    @JoinColumn(name = "ALBUM_ID", referencedColumnName = "ALBUM_ID")
    @ManyToOne(optional = false)
    private Album albumId;

    public Snimio() {
    }

    public Snimio(Integer snimioId) {
        this.snimioId = snimioId;
    }

    public Integer getSnimioId() {
        return snimioId;
    }

    public void setSnimioId(Integer snimioId) {
        this.snimioId = snimioId;
    }

    public Integer getBrojSati() {
        return brojSati;
    }

    public void setBrojSati(Integer brojSati) {
        this.brojSati = brojSati;
    }

    public Izvodjac getIzvodjacId() {
        return izvodjacId;
    }

    public void setIzvodjacId(Izvodjac izvodjacId) {
        this.izvodjacId = izvodjacId;
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
        hash += (snimioId != null ? snimioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Snimio)) {
            return false;
        }
        Snimio other = (Snimio) object;
        if ((this.snimioId == null && other.snimioId != null) || (this.snimioId != null && !this.snimioId.equals(other.snimioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.met.entity.Snimio[ snimioId=" + snimioId + " ]";
    }
    
}
