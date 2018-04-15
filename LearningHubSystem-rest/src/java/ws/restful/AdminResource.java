/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.AdministratorControllerLocal;
import ejb.session.stateless.ModuleControllerLocal;
import entity.Module;
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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import ws.restful.datamodel.CreateModuleReq;
import ws.restful.datamodel.CreateModuleRsp;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RetrieveAdminRsp;
import ws.restful.datamodel.UpdateAdminReq;
import ws.restful.datamodel.UpdateModuleReq;

/**
 * REST Web Service
 *
 * @author wyh
 */
@Path("admin")
public class AdminResource {

    ModuleControllerLocal moduleController;

    AdministratorControllerLocal adminController;
    
    
    
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AdminResource
     */
    public AdminResource() {
        adminController = lookupAdministratorControllerLocal();
        
        moduleController = lookupModuleControllerLocal1();
    }

    
    @Path("retrieveAdmin/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAdmin(@PathParam("id") Long id)
    {
        try
        {
            RetrieveAdminRsp retrieveAdminRsp = new RetrieveAdminRsp(adminController.retrieveAdminById(id));
            
            return Response.status(Response.Status.OK).entity(retrieveAdminRsp).build();
        }
        
        catch(Exception ex)
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("updateAdmin/{administrator}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAdmin(JAXBElement<UpdateAdminReq> jaxbUpdateAdminReq)
    {
        if((jaxbUpdateAdminReq != null) && (jaxbUpdateAdminReq.getValue() != null))
        {
            try
            {
                UpdateAdminReq updateAdminReq = jaxbUpdateAdminReq.getValue();
                
                adminController.updateAdmin(updateAdminReq.getAdmin());
                
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
    
    @Path("createModule/{module}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createModule(JAXBElement<CreateModuleReq> jaxbCreateModuleReq)
    {
        if((jaxbCreateModuleReq != null) && (jaxbCreateModuleReq.getValue() != null))
        {
            try
            {
                CreateModuleReq createModuleReq = jaxbCreateModuleReq.getValue();
                
                Module module = moduleController.createNewModule(createModuleReq.getModule());
                CreateModuleRsp createModuleRsp = new CreateModuleRsp(module);
                
                return Response.status(Response.Status.OK).entity(createModuleRsp).build();
            }
            catch(Exception ex)
            {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        }
        else
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create module request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    @Path("updateModule/{administrator}")
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
                
                moduleController.updateModule(updateModuleReq.getModule());
                
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
            ErrorRsp errorRsp = new ErrorRsp("Invalid update module request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of AdminResource
     * @param content representation for the resource
     */
    

    private AdministratorControllerLocal lookupAdministratorControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (AdministratorControllerLocal) c.lookup("java:global/LearningHubSystem/LearningHubSystem-ejb/AdministratorController!ejb.session.stateless.AdministratorControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ModuleControllerLocal lookupModuleControllerLocal1() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ModuleControllerLocal) c.lookup("java:global/LearningHubSystem/LearningHubSystem-ejb/ModuleController!ejb.session.stateless.ModuleControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
