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
@Table(name = "album")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Album.findAll", query = "SELECT a FROM Album a")
    , @NamedQuery(name = "Album.findByAlbumId", query = "SELECT a FROM Album a WHERE a.albumId = :albumId")
    , @NamedQuery(name = "Album.findByNazivAlbuma", query = "SELECT a FROM Album a WHERE a.nazivAlbuma = :nazivAlbuma")
    , @NamedQuery(name = "Album.findByDatumIzdavanjaAlbima", query = "SELECT a FROM Album a WHERE a.datumIzdavanjaAlbima = :datumIzdavanjaAlbima")})
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ALBUM_ID")
    private Integer albumId;
    @Size(max = 255)
    @Column(name = "NAZIV_ALBUMA")
    private String nazivAlbuma;
    @Column(name = "DATUM_IZDAVANJA_ALBIMA")
    @Temporal(TemporalType.DATE)
    private Date datumIzdavanjaAlbima;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "albumId")
    private Collection<Snimio> snimioCollection;
    @OneToMany(mappedBy = "albumId")
    private Collection<Pesma> pesmaCollection;

    public Album() {
    }

    public Album(Integer albumId) {
        this.albumId = albumId;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getNazivAlbuma() {
        return nazivAlbuma;
    }

    public void setNazivAlbuma(String nazivAlbuma) {
        this.nazivAlbuma = nazivAlbuma;
    }

    public Date getDatumIzdavanjaAlbima() {
        return datumIzdavanjaAlbima;
    }

    public void setDatumIzdavanjaAlbima(Date datumIzdavanjaAlbima) {
        this.datumIzdavanjaAlbima = datumIzdavanjaAlbima;
    }

    @XmlTransient
    public Collection<Snimio> getSnimioCollection() {
        return snimioCollection;
    }

    public void setSnimioCollection(Collection<Snimio> snimioCollection) {
        this.snimioCollection = snimioCollection;
    }

    @XmlTransient
    public Collection<Pesma> getPesmaCollection() {
        return pesmaCollection;
    }

    public void setPesmaCollection(Collection<Pesma> pesmaCollection) {
        this.pesmaCollection = pesmaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (albumId != null ? albumId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Album)) {
            return false;
        }
        Album other = (Album) object;
        if ((this.albumId == null && other.albumId != null) || (this.albumId != null && !this.albumId.equals(other.albumId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.met.entity.Album[ albumId=" + albumId + " ]";
    }
    
}
