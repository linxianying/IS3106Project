/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.TimeEntryControllerLocal;
import entity.Module;
import entity.TimeEntry;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RetrieveTimeEntryByNameRsp;
import ws.restful.datamodel.RetrieveTimeEntryRsp;

/**
 * REST Web Service
 *
 * @author wyh
 */
@Path("schedule")
public class ScheduleResource {

    @Context
    private UriInfo context;
    
    TimeEntryControllerLocal timeEntryController;

    /**
     * Creates a new instance of ScheduleResource
     */
    public ScheduleResource() {
        timeEntryController = lookupTimeEntryControllerLocal();
    }

    /**
     * Retrieves representation of an instance of ws.restful.ScheduleResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of ScheduleResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
    @Path("retrieveTimeEntryByName/{username}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveTimeEntryByName(@PathParam("username") String username) {
        try {
            List<TimeEntry> timeEntries = timeEntryController.retrieveTimeEntrysByName(username);
            System.err.println("timeEntries!!!!!!!!!"+timeEntries.size());
            for(TimeEntry t:timeEntries){
            }
            RetrieveTimeEntryByNameRsp retrieveTimeEntryByNameRsp = 
                    new RetrieveTimeEntryByNameRsp(timeEntries);
            
            System.out.println(Response.status(Response.Status.OK).entity(retrieveTimeEntryByNameRsp).build());
            return Response.status(Response.Status.OK).entity(retrieveTimeEntryByNameRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("retrieveTimeEntry/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveTimeEntry(@PathParam("id") Long id) {
        try {
            RetrieveTimeEntryRsp retrieveTimeEntryRsp = new RetrieveTimeEntryRsp(timeEntryController.retrieveTimeEntryById(id));

            return Response.status(Response.Status.OK).entity(retrieveTimeEntryRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    private TimeEntryControllerLocal lookupTimeEntryControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (TimeEntryControllerLocal) c.lookup("java:global/LearningHubSystem/LearningHubSystem-ejb/TimeEntryController!ejb.session.stateless.TimeEntryControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
