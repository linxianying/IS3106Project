/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;
//package org.primefaces.showcase.view.multimedia;
 
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author wyh
 */
@Named(value = "dashboardManagedBean")
@RequestScoped
public class DashboardManagedBean {

    /**
     * Creates a new instance of DashboardManagedBean
     */
    
    private List<String> images;
     
    @PostConstruct
    public void init() {
        images = new ArrayList<String>();
        for (int i = 1; i <= 7; i++) {
            images.add("study" + i + ".jpg");
        }
    }
 
    public List<String> getImages() {
        return images;
    }
    
    public DashboardManagedBean() {
    }
    
    
    
}



