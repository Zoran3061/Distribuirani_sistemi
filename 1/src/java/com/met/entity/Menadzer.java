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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Zoran
 */
@Entity
@Table(name = "menadzer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Menadzer.findAll", query = "SELECT m FROM Menadzer m")
    , @NamedQuery(name = "Menadzer.findByMenadzerId", query = "SELECT m FROM Menadzer m WHERE m.menadzerId = :menadzerId")
    , @NamedQuery(name = "Menadzer.findByIme", query = "SELECT m FROM Menadzer m WHERE m.ime = :ime")
    , @NamedQuery(name = "Menadzer.findByPrezime", query = "SELECT m FROM Menadzer m WHERE m.prezime = :prezime")
    , @NamedQuery(name = "Menadzer.findByTelefon", query = "SELECT m FROM Menadzer m WHERE m.telefon = :telefon")})
public class Menadzer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MENADZER_ID")
    private Integer menadzerId;
    @Size(max = 128)
    @Column(name = "IME")
    private String ime;
    @Size(max = 128)
    @Column(name = "PREZIME")
    private String prezime;
    @Size(max = 64)
    @Column(name = "TELEFON")
    private String telefon;

    public Menadzer() {
    }

    public Menadzer(Integer menadzerId) {
        this.menadzerId = menadzerId;
    }

    public Integer getMenadzerId() {
        return menadzerId;
    }

    public void setMenadzerId(Integer menadzerId) {
        this.menadzerId = menadzerId;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (menadzerId != null ? menadzerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Menadzer)) {
            return false;
        }
        Menadzer other = (Menadzer) object;
        if ((this.menadzerId == null && other.menadzerId != null) || (this.menadzerId != null && !this.menadzerId.equals(other.menadzerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.met.entity.Menadzer[ menadzerId=" + menadzerId + " ]";
    }
    
}
