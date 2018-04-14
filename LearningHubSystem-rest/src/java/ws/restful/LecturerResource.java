/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.LecturerControllerLocal;
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
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RetrieveModulesRsp;
import ws.restful.datamodel.RetrieveSpecificLecturerRsp;

/**
 * REST Web Service
 *
 * @author wyh
 */
@Path("lecturer")
public class LecturerResource {

    LecturerControllerLocal lecturerControllerLocal;

    @Context
    private UriInfo context;

    public LecturerResource() {
        lecturerControllerLocal = lookupLecturerControllerLocal();
    }

    @Path("retrieveEnrolledModules/{lecturerId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveEnrolledModules(@PathParam("lecturerId") Long lecturerId) {
        try {
            RetrieveModulesRsp retrieveModulesRsp = new RetrieveModulesRsp(lecturerControllerLocal.retrieveEnrolledModules(lecturerId));

            return Response.status(Response.Status.OK).entity(retrieveModulesRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveSpecificLecturer/{lecturerId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveSpecificLecturer(@PathParam("lecturerId") Long lecturerId) {
        try {
            RetrieveSpecificLecturerRsp retrieveSpecificLecturerRsp = new RetrieveSpecificLecturerRsp(lecturerControllerLocal.retrieveLecturerById(lecturerId));

            return Response.status(Response.Status.OK).entity(retrieveSpecificLecturerRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    private LecturerControllerLocal lookupLecturerControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (LecturerControllerLocal) c.lookup("java:global/LearningHubSystem/LearningHubSystem-ejb/LecturerController!ejb.session.stateless.LecturerControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
