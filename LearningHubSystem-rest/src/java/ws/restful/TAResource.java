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
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import util.exception.TANotFoundException;
import ws.restful.datamodel.DeleteTAReq;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RetrieveTAsRsp;

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
    
    @Path("retrieveAllTAs")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllTAs()
    {
        try
        {
            return Response.status(Response.Status.OK).entity(new RetrieveTAsRsp(taController.retrieveAllTAs())).build();
        }
        catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTA(JAXBElement<DeleteTAReq> jaxbDeleteTAReq) throws TANotFoundException {
        if ((jaxbDeleteTAReq != null) && (jaxbDeleteTAReq.getValue() != null)) {
            DeleteTAReq deleteTAReq = jaxbDeleteTAReq.getValue();
            taController.deleteTA(deleteTAReq.getTa());
            return Response.status(Response.Status.OK).build();
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid delete lecturer request");

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
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
