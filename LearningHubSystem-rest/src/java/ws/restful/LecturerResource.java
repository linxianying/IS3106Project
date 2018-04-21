/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.AdministratorControllerLocal;
import ejb.session.stateless.AnnouncementControllerLocal;
import ejb.session.stateless.LecturerControllerLocal;
import ejb.session.stateless.TimeEntryControllerLocal;
import ejb.session.stateless.ModuleControllerLocal;
import ejb.session.stateless.StudentControllerLocal;
import ejb.session.stateless.TeachingAssistantControllerLocal;
import entity.Administrator;
import entity.Module;
import entity.Announcement;
import entity.Lecturer;
import entity.Student;
import entity.TeachingAssistant;
import java.util.List;
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
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import util.exception.AnnouncementExistException;
import util.exception.LecturerNotFoundException;
import util.exception.ModuleExistException;
import util.exception.ModuleNotFoundException;
import ws.restful.datamodel.AdminLoginRsp;
import ws.restful.datamodel.AssignModuleReq;
import ws.restful.datamodel.AssignModuleRsp;
import ws.restful.datamodel.CreateAnnouncementReq;
import ws.restful.datamodel.CreateAnnouncementRsp;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RetrieveLecturersRsp;
import ws.restful.datamodel.RetrieveModulesRsp;
import ws.restful.datamodel.RetrieveSpecificLecturerRsp;
import ws.restful.datamodel.StudentLoginRsp;
import ws.restful.datamodel.TeachingAssistantLoginRsp;
import ws.restful.datamodel.UpdateLecturerReq;
import ws.restful.datamodel.UpdateLecturerRsp;

/**
 * REST Web Service
 *
 * @author wyh
 */
@Path("lecturer")
public class LecturerResource {

    TeachingAssistantControllerLocal teachingAssistantController;

    AdministratorControllerLocal administratorController ;

    StudentControllerLocal studentController ;

    ModuleControllerLocal moduleController;



    AnnouncementControllerLocal announcementController;

    LecturerControllerLocal lecturerControllerLocal;
    


    TimeEntryControllerLocal timeEntryController;



    @Context
    private UriInfo context;

    public LecturerResource() {
        timeEntryController = lookupTimeEntryControllerLocal();
        lecturerControllerLocal = lookupLecturerControllerLocal();
        announcementController = lookupAnnouncementControllerLocal();
        moduleController = lookupModuleControllerLocal();
        teachingAssistantController = lookupTeachingAssistantControllerLocal();
        administratorController = lookupAdministratorControllerLocal();
        studentController = lookupStudentControllerLocal();
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

            Lecturer lec = lecturerControllerLocal.retrieveLecturerById(lecturerId);
            lec.getAnnouncements().clear();
            lec.getModules().clear();
            lec.getTimeEntries().clear();
            RetrieveSpecificLecturerRsp retrieveSpecificLecturerRsp = new RetrieveSpecificLecturerRsp(lec);

            return Response.status(Response.Status.OK).entity(retrieveSpecificLecturerRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    

    @Path("retrieveAllLecturers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllLecturers()
    {
        try
        {   
            List<Lecturer> lecturers = lecturerControllerLocal.retrieveAllLecturers();
            for(Lecturer l:lecturers){
                l.getAnnouncements().clear();
                l.getModules().clear();
                l.getTimeEntries().clear();
            }
            
            RetrieveLecturersRsp retrieveLecturersRsp = new RetrieveLecturersRsp(lecturers);
            return Response.status(Response.Status.OK).entity(retrieveLecturersRsp).build();
        }
        catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
   


    
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
            
            catch(LecturerNotFoundException | ModuleExistException ex){
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

    
    @Path("getLecturer/{username}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLecturer(@PathParam("username") String username) {
        try {
            Lecturer l = lecturerControllerLocal.retrieveLecturerByUsername(username);
            l.getModules().clear();
            l.getTimeEntries().clear();
            l.getAnnouncements().clear();
            UpdateLecturerRsp updateLecturerRsp = new UpdateLecturerRsp(l);
            //RetrieveStudentRsp retrieveStudentRsp = new RetrieveStudentRsp(s);
            return Response.status(Response.Status.OK).entity(updateLecturerRsp).build();
        } catch (LecturerNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("updateLecturerPassword/{id}/{newPassword}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateLecturerPassword(@PathParam("id") Long id,@PathParam("newPassword") String newPassword ) {
        try {
            Lecturer l = lecturerControllerLocal.updateLecturerPassword(id,newPassword);
            l.getModules().clear();
            l.getTimeEntries().clear();
            l.getAnnouncements().clear();
            UpdateLecturerRsp updateLecturerRsp = new UpdateLecturerRsp(l);
            //RetrieveStudentRsp retrieveStudentRsp = new RetrieveStudentRsp(s);
            return Response.status(Response.Status.OK).entity(updateLecturerRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("updateTAPassword/{id}/{newPassword}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTAPassword(@PathParam("id") Long id,@PathParam("newPassword") String newPassword ) {
        try {
            TeachingAssistant ta = teachingAssistantController.updateTAPassword(id,newPassword);
            ta.getModules().clear();
            TeachingAssistantLoginRsp teachingAssistantLoginRsp = new TeachingAssistantLoginRsp(ta);
            //RetrieveStudentRsp retrieveStudentRsp = new RetrieveStudentRsp(s);
            return Response.status(Response.Status.OK).entity(teachingAssistantLoginRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("updateStudentPassword/{id}/{newPassword}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStudentPassword(@PathParam("id") Long id,@PathParam("newPassword") String newPassword ) {
        try {
            Student stu = studentController.updateStudentPassword(id,newPassword);
            stu.getModules().clear();
            stu.getTimeEntries().clear();
            StudentLoginRsp studentLoginRsp = new StudentLoginRsp(stu);
            //RetrieveStudentRsp retrieveStudentRsp = new RetrieveStudentRsp(s);
            return Response.status(Response.Status.OK).entity(studentLoginRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("updateAdminPassword/{id}/{newPassword}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAdminPassword(@PathParam("id") Long id,@PathParam("newPassword") String newPassword ) {
        try {
            Administrator a = administratorController.updateAdminPassword(id,newPassword);
            
            AdminLoginRsp adminLoginRsp = new AdminLoginRsp(a);
            //RetrieveStudentRsp retrieveStudentRsp = new RetrieveStudentRsp(s);
            return Response.status(Response.Status.OK).entity(adminLoginRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateLecturer(JAXBElement<UpdateLecturerReq> jaxbUpdateLecturerReq)
    {
        if((jaxbUpdateLecturerReq != null) && (jaxbUpdateLecturerReq.getValue() != null))
        {
            try
            {
                UpdateLecturerReq updateLecturerReq= jaxbUpdateLecturerReq.getValue();
                
                lecturerControllerLocal.updateLecturer(updateLecturerReq.getLecturer());
                
                return Response.status(Response.Status.OK).build();
            }
            catch(Exception ex)
            {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        }
        else
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid update lecturer request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

    @Path("{lecturerId}")
    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteLecturer(@PathParam("lecturerId") Long lecturerId)
    {
        try
        {
            lecturerControllerLocal.deleteLecturer(lecturerControllerLocal.retrieveLecturerById(lecturerId));
            
            return Response.status(Response.Status.OK).build();
        }
        catch (LecturerNotFoundException ex) {
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

    private AnnouncementControllerLocal lookupAnnouncementControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (AnnouncementControllerLocal) c.lookup("java:global/LearningHubSystem/LearningHubSystem-ejb/AnnouncementController!ejb.session.stateless.AnnouncementControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
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

    private ModuleControllerLocal lookupModuleControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ModuleControllerLocal) c.lookup("java:global/LearningHubSystem/LearningHubSystem-ejb/ModuleController!ejb.session.stateless.ModuleControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private StudentControllerLocal lookupStudentControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (StudentControllerLocal) c.lookup("java:global/LearningHubSystem/LearningHubSystem-ejb/StudentController!ejb.session.stateless.StudentControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private AdministratorControllerLocal lookupAdministratorControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (AdministratorControllerLocal) c.lookup("java:global/LearningHubSystem/LearningHubSystem-ejb/AdministratorController!ejb.session.stateless.AdministratorControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
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
