/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.administratorControllerLocal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mango
 */
@Singleton
@LocalBean
@Startup
public class dataInitialization {

    @EJB(name = "administratorControllerLocal")
    private administratorControllerLocal administratorControllerLocal;

    @PersistenceContext(unitName = "LearningHubSystem-ejbPU")
    private EntityManager em;

    public dataInitialization(){
        
    }
    
    @PostConstruct
    public void postConstruct(){
        //check whther there is admin staff, if not, initialize one 
//        try{
//            administratorControllerLocal.retrieveAdminByUsername("");
//        } catch (adminNotFoundException ex) {
//            initializaData();
//        }
    }
    
    private void initializaData(){
        
    }
}
