/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.AdministratorControllerLocal;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;


/**
 *
 * @author Samango
 */
@Named(value = "accountManagement")
@SessionScoped
public class adminUsersManagement {

    @EJB
    private AdministratorControllerLocal administratorController;

    /**
     * Creates a new instance of accountManagement
     */
    
    public adminUsersManagement() {
    }
    
}
