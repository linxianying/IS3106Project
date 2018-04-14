/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.ModuleControllerLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ws.restful.datamodel.ClassAndGroupsRsp;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RetrieveAnnouncementsRsp;
import ws.restful.datamodel.RetrieveModulesRsp;
import ws.restful.datamodel.RetrieveSpecificModuleRsp;

/**
 * REST Web Service
 *
 * @author wyh
 */
@Path("module")
public class ModuleResource {

    ModuleControllerLocal moduleController;

    @Context
    private UriInfo context;

    public ModuleResource() {
        moduleController = lookupModuleControllerLocal();
    }

    
    @Path("retrieveEnrolledModules/{username}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveEnrolledModules(@PathParam("username") String username) {
        try {
            RetrieveModulesRsp retrieveModulesRsp = new RetrieveModulesRsp(moduleController.retrieveModulesByStudentUsername(username));

            return Response.status(Response.Status.OK).entity(retrieveModulesRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveSpecificModule/{moduleId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveretrieveSpecificModule(@PathParam("moduleId") Long moduleId) {
        try {
            RetrieveSpecificModuleRsp retrieveSpecificModuleRsp = new RetrieveSpecificModuleRsp(moduleController.retrieveModuleById(moduleId));

            return Response.status(Response.Status.OK).entity(retrieveSpecificModuleRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveClassAndGroups/{moduleId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveClassAndGroups(@PathParam("moduleId") Long moduleId) {
        try {
            ClassAndGroupsRsp classAndGroupsRsp = new ClassAndGroupsRsp(moduleController.retrieveClassAndGroups(moduleId));

            return Response.status(Response.Status.OK).entity(classAndGroupsRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveAnnoucements/{moduleId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAnnoucements(@PathParam("moduleId") Long moduleId) {
        try {
            RetrieveAnnouncementsRsp retrieveAnnouncementsRsp = new RetrieveAnnouncementsRsp(moduleController.retrieveAnnoucements(moduleId));

            return Response.status(Response.Status.OK).entity(retrieveAnnouncementsRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    private ModuleControllerLocal lookupModuleControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ModuleControllerLocal) c.lookup("java:global/LearningHubSystem/LearningHubSystem-ejb/ModuleController!ejb.session.stateless.ModuleControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
