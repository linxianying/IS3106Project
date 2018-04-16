/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.TeachingAssistantControllerLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;

/**
 * REST Web Service
 *
 * @author wyh
 */
@Path("ta")
public class TAResource {

    TeachingAssistantControllerLocal taController;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of FacilitatorResource
     */
    public TAResource() {
        taController = lookupTeachingAssistantControllerLocal();
    }
    
    

    private TeachingAssistantControllerLocal lookupTeachingAssistantControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (TeachingAssistantControllerLocal) c.lookup("java:global/LearningHubSystem/LearningHubSystem-ejb/TeachingAssistantController!ejb.session.stateless.TeachingAssistantControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
