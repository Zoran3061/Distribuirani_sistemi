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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Zoran
 */
@Entity
@Table(name = "spisak_pesama")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SpisakPesama.findAll", query = "SELECT s FROM SpisakPesama s")
    , @NamedQuery(name = "SpisakPesama.findBySpisakPesamaId", query = "SELECT s FROM SpisakPesama s WHERE s.spisakPesamaId = :spisakPesamaId")
    , @NamedQuery(name = "SpisakPesama.findByTerminId", query = "SELECT s FROM SpisakPesama s WHERE s.terminId = :terminId")
    , @NamedQuery(name = "SpisakPesama.findByPesmaId", query = "SELECT s FROM SpisakPesama s WHERE s.pesmaId = :pesmaId")})
public class SpisakPesama implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SPISAK_PESAMA_ID")
    private Integer spisakPesamaId;
    @Column(name = "TERMIN_ID")
    private Integer terminId;
    @Column(name = "PESMA_ID")
    private Integer pesmaId;

    public SpisakPesama() {
    }

    public SpisakPesama(Integer spisakPesamaId) {
        this.spisakPesamaId = spisakPesamaId;
    }

    public Integer getSpisakPesamaId() {
        return spisakPesamaId;
    }

    public void setSpisakPesamaId(Integer spisakPesamaId) {
        this.spisakPesamaId = spisakPesamaId;
    }

    public Integer getTerminId() {
        return terminId;
    }

    public void setTerminId(Integer terminId) {
        this.terminId = terminId;
    }

    public Integer getPesmaId() {
        return pesmaId;
    }

    public void setPesmaId(Integer pesmaId) {
        this.pesmaId = pesmaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spisakPesamaId != null ? spisakPesamaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SpisakPesama)) {
            return false;
        }
        SpisakPesama other = (SpisakPesama) object;
        if ((this.spisakPesamaId == null && other.spisakPesamaId != null) || (this.spisakPesamaId != null && !this.spisakPesamaId.equals(other.spisakPesamaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.met.entity.SpisakPesama[ spisakPesamaId=" + spisakPesamaId + " ]";
    }
    
}
