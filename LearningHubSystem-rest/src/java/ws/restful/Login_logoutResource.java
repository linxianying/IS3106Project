/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.AdministratorControllerLocal;
import ejb.session.stateless.LecturerControllerLocal;
import ejb.session.stateless.StudentControllerLocal;
import ejb.session.stateless.TeachingAssistantControllerLocal;
import entity.Lecturer;
import entity.Module;
import entity.Student;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import ws.restful.datamodel.CreateStudentReq;
import ws.restful.datamodel.CreateStudentRsp;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.LecturerLoginRsp;
import ws.restful.datamodel.StudentLoginRsp;
import ws.restful.datamodel.UpdateStudentReq;

/**
 * REST Web Service
 *
 * @author wyh
 */
@Path("login_logout")
public class Login_logoutResource {
    
    StudentControllerLocal studentController;
    AdministratorControllerLocal adminController;
    LecturerControllerLocal lecturerController;
    TeachingAssistantControllerLocal taController;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Login_logoutResource
     */
    public Login_logoutResource() {
        studentController = lookupStudentControllerLocal();
        lecturerController = lookupLecturerControllerLocal();
        adminController = lookupAdminControllerLocal();
        taController = lookupTAControllerLocal();
    }

    
    @Path("studentLogin/{username}/{password}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response studentLogin(@PathParam("username") String username, @PathParam("password") String password ) {
        try {

            Student student = studentController.login(username, password);
            if(student!=null&&student.getIsPremium()==true){
                student.getModules().clear();
//                student.getTimeEntries().clear();
                
                StudentLoginRsp studentLoginRsp = new StudentLoginRsp(student);
                return Response.status(Response.Status.OK).entity(studentLoginRsp).build();
            }else{
                ErrorRsp errorRsp = new ErrorRsp();
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();


            }
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    

    
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createStudent(JAXBElement<CreateStudentReq> jaxbCreateStudentReq)
    {
        if((jaxbCreateStudentReq != null) && (jaxbCreateStudentReq.getValue() != null))
        {
            try
            {
                CreateStudentReq createStudentReq = jaxbCreateStudentReq.getValue();
                
                Student student = studentController.createStudent(createStudentReq.getStudent());
                CreateStudentRsp createStudentRsp = new CreateStudentRsp(student.getId());
                
                return Response.status(Response.Status.OK).entity(createStudentRsp).build();
            }
            
            catch(Exception ex)
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
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStudent(JAXBElement<UpdateStudentReq> jaxbUpdateStudentReq)
    {
        if((jaxbUpdateStudentReq != null) && (jaxbUpdateStudentReq.getValue() != null))
        {
            try
            {
                UpdateStudentReq updateStudentReq = jaxbUpdateStudentReq.getValue();
                
                studentController.updateStudent(updateStudentReq.getStudent());
                
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
            ErrorRsp errorRsp = new ErrorRsp("Invalid update admin request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    @Path("lecturerLogin/{username}/{password}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response lecturerLogin(@PathParam("username") String username, @PathParam("password") String password ) {
        try {
            Lecturer lecturer = lecturerController.login(username, password);
            lecturer.getModules().clear();
            lecturer.getTimeEntries().clear();
            lecturer.getAnnouncements().clear();
            
            LecturerLoginRsp lecturerLoginRsp = new LecturerLoginRsp(lecturer);
            System.out.println(Response.status(Response.Status.OK).entity(lecturerLoginRsp).build());
            return Response.status(Response.Status.OK).entity(lecturerLoginRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
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
    
    private LecturerControllerLocal lookupLecturerControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (LecturerControllerLocal) c.lookup("java:global/LearningHubSystem/LearningHubSystem-ejb/LecturerController!ejb.session.stateless.LecturerControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    private AdministratorControllerLocal lookupAdminControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (AdministratorControllerLocal) c.lookup("java:global/LearningHubSystem/LearningHubSystem-ejb/AdministratorController!ejb.session.stateless.AdministratorControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    private TeachingAssistantControllerLocal lookupTAControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (TeachingAssistantControllerLocal) c.lookup("java:global/LearningHubSystem/LearningHubSystem-ejb/TeachingAssistantController!ejb.session.stateless.TeachingAssistantControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
