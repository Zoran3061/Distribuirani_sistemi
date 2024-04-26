/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Zoran
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(service.AlbumFacadeREST.class);
        resources.add(service.HumanitarniKoncertFacadeREST.class);
        resources.add(service.IzvodjacFacadeREST.class);
        resources.add(service.KomercijalniKoncertFacadeREST.class);
        resources.add(service.KoncertFacadeREST.class);
        resources.add(service.MenadzerFacadeREST.class);
        resources.add(service.PesmaFacadeREST.class);
        resources.add(service.SnimioFacadeREST.class);
        resources.add(service.SpisakPesamaFacadeREST.class);
        resources.add(service.TerminFacadeREST.class);
        resources.add(service.UgovorFacadeREST.class);
    }
    
}
