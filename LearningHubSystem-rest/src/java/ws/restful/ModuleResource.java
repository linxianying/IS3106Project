/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.ModuleControllerLocal;
import entity.Announcement;
import entity.Module;
import entity.Student;
import entity.Lecturer;
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
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBElement;
import util.exception.ModuleNotFoundException;
import ws.restful.datamodel.ClassAndGroupsRsp;
import ws.restful.datamodel.CreateModuleReq;
import ws.restful.datamodel.CreateModuleRsp;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RetrieveAnnouncementsRsp;
import ws.restful.datamodel.RetrieveLecturersRsp;
import ws.restful.datamodel.RetrieveModulesRsp;
import ws.restful.datamodel.RetrieveSpecificModuleRsp;
import ws.restful.datamodel.RetrieveStudentsRsp;
import ws.restful.datamodel.RetrieveTAsRsp;
import ws.restful.datamodel.UpdateModuleReq;

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
            
            List<Module> modules=moduleController.retrieveModulesByStudentUsername(username);
            System.err.println("module!!!!!!!!!"+modules.size());
            
            for(Module m:modules){
                 m.getStduents().clear();
                 m.getAnnouncements().clear();
                 m.getLecturers().clear();
                 m.getTAs().clear();
                 m.getFiles().clear();
                
            }
            RetrieveModulesRsp retrieveModulesRsp = new RetrieveModulesRsp(modules);

            return Response.status(Response.Status.OK).entity(retrieveModulesRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("retrieveTeachingModules/{username}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveTeachingModules(@PathParam("username") String username) {
        try {
            
            List<Module> modules=moduleController.retrieveModulesByLecturerUsername(username);
            System.err.println("module!!!!!!!!!"+modules.size());
            
            for(Module m:modules){
                 m.getStduents().clear();
                 m.getAnnouncements().clear();
                 m.getLecturers().clear();
                 m.getTAs().clear();
                 m.getFiles().clear();
                
            }
            RetrieveModulesRsp retrieveModulesRsp = new RetrieveModulesRsp(modules);

            return Response.status(Response.Status.OK).entity(retrieveModulesRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("retrieveFacilitatingModules/{username}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveFacilitatingModules(@PathParam("username") String username) {
        try {
            
            List<Module> modules=moduleController.retrieveModulesByTaUsername(username);
            System.err.println("module!!!!!!!!!"+modules.size());
            
            for(Module m:modules){
                 m.getStduents().clear();
                 m.getAnnouncements().clear();
                 m.getLecturers().clear();
                 m.getTAs().clear();
                 m.getFiles().clear();
                
            }
            RetrieveModulesRsp retrieveModulesRsp = new RetrieveModulesRsp(modules);

            return Response.status(Response.Status.OK).entity(retrieveModulesRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("retrieveAllModules")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllModules()
    {
        try
        {   List<Module> modules = moduleController.retrieveAllModules();
            for(Module m:modules){
                 m.getStduents().clear();
                 m.getAnnouncements().clear();
                 m.getLecturers().clear();
                 m.getTAs().clear();
                 m.getFiles().clear();
                
            }
            
            RetrieveModulesRsp retrieveModulesRsp = new RetrieveModulesRsp(modules);
            return Response.status(Response.Status.OK).entity(retrieveModulesRsp).build();
        }
        catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveSpecificModule/{moduleId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveSpecificModule(@PathParam("moduleId") Long moduleId) {
        try {
            Module m=moduleController.retrieveModuleById(moduleId);
            m.getAnnouncements().clear();
            m.getFiles().clear();
            m.getLecturers().clear();
            m.getStduents().clear();
            m.getTAs().clear();
            RetrieveSpecificModuleRsp retrieveSpecificModuleRsp = new RetrieveSpecificModuleRsp(m);

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
            List<Student> students=moduleController.retrieveClassAndGroups(moduleId);
            for(Student student: students){
                student.getModules().clear();
                student.getTimeEntries().clear();
               
            }
            ClassAndGroupsRsp classAndGroupsRsp = new ClassAndGroupsRsp(students);

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
            List<Announcement> announcements=moduleController.retrieveAnnoucements(moduleId);
            for(Announcement a:announcements){
            a.setLecturer(null);
            a.setModule(null);
        }
            RetrieveAnnouncementsRsp retrieveAnnouncementsRsp = new RetrieveAnnouncementsRsp(announcements);

            return Response.status(Response.Status.OK).entity(retrieveAnnouncementsRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveLecturers/{moduleId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveLecturers(@PathParam("moduleId") Long moduleId) {
        try {
            List<Lecturer> lecturers = moduleController.retrieveLecturers(moduleId);
            for(Lecturer each: lecturers){
                each.getAnnouncements().clear();
                each.getModules().clear();
                each.getTimeEntries().clear();
            }
                    
            RetrieveLecturersRsp retrieveLecturersRsp = new RetrieveLecturersRsp(lecturers);

            return Response.status(Response.Status.OK).entity(retrieveLecturersRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveStudents/{moduleId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveStudents(@PathParam("moduleId") Long moduleId) {
        try {
            List<Student> students = moduleController.retrieveStudents(moduleId);
            for(Student each:students){
                each.getModules().clear();
                each.getTimeEntries().clear();
            }
            RetrieveStudentsRsp retrieveStudentsRsp = new RetrieveStudentsRsp(students);

            return Response.status(Response.Status.OK).entity(retrieveStudentsRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveTAs/{moduleId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveTAs(@PathParam("moduleId") Long moduleId) {
        try {
            
            List<TeachingAssistant> tas=moduleController.retrieveTAs(moduleId);
            for (TeachingAssistant ta: tas){
                ta.getModules().clear();
            }

            
            RetrieveTAsRsp retrieveTAsRsp = new RetrieveTAsRsp(tas);

            return Response.status(Response.Status.OK).entity(retrieveTAsRsp).build();
        } catch (ModuleNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createModule(JAXBElement<CreateModuleReq> jaxbCreateModuleReq) {
        if ((jaxbCreateModuleReq != null) && (jaxbCreateModuleReq.getValue() != null)) {
            try {
                CreateModuleReq createModuleReq = jaxbCreateModuleReq.getValue();

                Module module = moduleController.createNewModule(createModuleReq.getModule());
                CreateModuleRsp createModuleRsp = new CreateModuleRsp(module);

                return Response.status(Response.Status.OK).entity(createModuleRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create module request");

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }


    @Path("{moduleId}")
    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteModule(@PathParam("moduleId") Long moduleId)
    {
        try
        {
            moduleController.deleteModule(moduleController.retrieveModuleById(moduleId));
            
            return Response.status(Response.Status.OK).build();
        }
        catch(ModuleNotFoundException ex)
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateModule(JAXBElement<UpdateModuleReq> jaxbUpdateModuleReq)
    {
        if((jaxbUpdateModuleReq != null) && (jaxbUpdateModuleReq.getValue() != null))
        {
            try
            {
                UpdateModuleReq updateModuleReq = jaxbUpdateModuleReq.getValue();
                
                Module mod = updateModuleReq.getModule();
                moduleController.updateModule(mod);
                
                return Response.status(Response.Status.OK).build();
            }
            catch(ModuleNotFoundException ex)
            {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        }
        else
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid update module request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
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
