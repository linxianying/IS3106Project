/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.AnnouncementControllerLocal;
import ejb.session.stateless.LecturerControllerLocal;
<<<<<<< HEAD
import ejb.session.stateless.ModuleControllerLocal;
import entity.Announcement;
import entity.Lecturer;
import entity.Module;
=======
import ejb.session.stateless.StudentControllerLocal;
import ejb.session.stateless.TimeEntryControllerLocal;
import entity.Announcement;
import entity.Lecturer;
import entity.TimeEntry;
>>>>>>> 5b0352d4f9a5e1e9d459557fa6df2accd51b548a
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import util.exception.LecturerNotFoundException;
import ws.restful.datamodel.DeleteLecturerReq;
import util.exception.AnnouncementExistException;
import util.exception.LecturerNotFoundException;
import util.exception.ModuleExistException;
import util.exception.ModuleNotFoundException;
import ws.restful.datamodel.AssignModuleReq;
import ws.restful.datamodel.AssignModuleRsp;
import ws.restful.datamodel.CreateAnnouncementReq;
import ws.restful.datamodel.CreateAnnouncementRsp;
import ws.restful.datamodel.CreateLecturerTimeEntryReq;
import ws.restful.datamodel.CreateLecturerTimeEntryRsp;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RetrieveLecturersRsp;
import ws.restful.datamodel.RetrieveModulesRsp;
import ws.restful.datamodel.RetrieveSpecificLecturerRsp;

/**
 * REST Web Service
 *
 * @author wyh
 */
@Path("lecturer")
public class LecturerResource {

    ModuleControllerLocal moduleController;

    AnnouncementControllerLocal announcementController;

    LecturerControllerLocal lecturerControllerLocal;
    
<<<<<<< HEAD
    
=======
    TimeEntryControllerLocal timeEntryController;

>>>>>>> 5b0352d4f9a5e1e9d459557fa6df2accd51b548a

    @Context
    private UriInfo context;

    public LecturerResource() {
        timeEntryController = lookupTimeEntryControllerLocal();
        lecturerControllerLocal = lookupLecturerControllerLocal();
        announcementController = lookupAnnouncementControllerLocal();
        moduleController = lookupModuleControllerLocal();
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
    
<<<<<<< HEAD
    @Path("retrieveAllLecturers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllLecturers()
    {
        try
        {
            return Response.status(Response.Status.OK).entity(new RetrieveLecturersRsp(lecturerControllerLocal.retrieveAllLecturers())).build();
        }
        catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteLecturer(JAXBElement<DeleteLecturerReq> jaxbDeleteLecturerReq) throws LecturerNotFoundException {
        if ((jaxbDeleteLecturerReq != null) && (jaxbDeleteLecturerReq.getValue() != null)) {
            DeleteLecturerReq deleteLecturerReq = jaxbDeleteLecturerReq.getValue();
            lecturerControllerLocal.deleteLecturer(deleteLecturerReq.getLecturer());
            return Response.status(Response.Status.OK).build();
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid delete lecturer request");

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
=======
>>>>>>> 5b0352d4f9a5e1e9d459557fa6df2accd51b548a
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAnnouncement(JAXBElement<CreateAnnouncementReq> jaxbCreateAnnouncementReq)
    {
        if((jaxbCreateAnnouncementReq != null) && (jaxbCreateAnnouncementReq.getValue() != null))
        {
            try
            {
                CreateAnnouncementReq createAnnouncementReq = jaxbCreateAnnouncementReq.getValue();
                String lecturerUsername=createAnnouncementReq.getUsername();
                Lecturer lec=lecturerControllerLocal.retrieveLecturerByUsername(lecturerUsername);
                Announcement announcement = announcementController.createNewAnnouncement(createAnnouncementReq.getAnnouncement(),lec,createAnnouncementReq.getModuleId());
                CreateAnnouncementRsp createAnnouncementRsp = new CreateAnnouncementRsp(announcement.getId());
                
                return Response.status(Response.Status.OK).entity(createAnnouncementRsp).build();
            }
            
            catch(LecturerNotFoundException ex)
            {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
            catch(AnnouncementExistException ex)
            {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        }
        else
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create student request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    @Path("assignModule")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response assignModule(JAXBElement<AssignModuleReq> jaxbAssignModuleReq) throws ModuleNotFoundException
    {
        if((jaxbAssignModuleReq != null) && (jaxbAssignModuleReq.getValue() != null))
        {
            try
            {
                AssignModuleReq assignModuleReq = jaxbAssignModuleReq.getValue();
                Module module = moduleController.retrieveModuleById(assignModuleReq.getModuleId());
                Lecturer lec= assignModuleReq.getLecturer();
               
                module = lecturerControllerLocal.registerModule(lec, module);
                
                AssignModuleRsp assignModuleRsp = new AssignModuleRsp(module);
                
                return Response.status(Response.Status.OK).entity(assignModuleRsp).build();
            }
            
            catch(LecturerNotFoundException | ModuleExistException ex)
            {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        }
        else
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid assign module request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
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

    private AnnouncementControllerLocal lookupAnnouncementControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (AnnouncementControllerLocal) c.lookup("java:global/LearningHubSystem/LearningHubSystem-ejb/AnnouncementController!ejb.session.stateless.AnnouncementControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
<<<<<<< HEAD

    private ModuleControllerLocal lookupModuleControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ModuleControllerLocal) c.lookup("java:global/LearningHubSystem/LearningHubSystem-ejb/ModuleController!ejb.session.stateless.ModuleControllerLocal");
=======
    
    private TimeEntryControllerLocal lookupTimeEntryControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (TimeEntryControllerLocal) c.lookup("java:global/LearningHubSystem/LearningHubSystem-ejb/TimeEntryController!ejb.session.stateless.TimeEntryControllerLocal");
>>>>>>> 5b0352d4f9a5e1e9d459557fa6df2accd51b548a
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
