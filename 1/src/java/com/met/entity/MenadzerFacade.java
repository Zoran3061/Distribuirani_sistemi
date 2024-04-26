/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.met.entity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Zoran
 */
@Stateless
public class MenadzerFacade extends AbstractFacade<Menadzer> {

    @PersistenceContext(unitName = "1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MenadzerFacade() {
        super(Menadzer.class);
    }
    
}
