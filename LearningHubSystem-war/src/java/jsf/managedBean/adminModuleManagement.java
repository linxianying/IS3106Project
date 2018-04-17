/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ModuleControllerLocal;
import entity.Module;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.exception.ModuleExistException;
import util.exception.ModuleNotFoundException;

/**
 *
 * @author Samango
 */
@Named(value = "moduleManagementManagedBean")
@SessionScoped
public class adminModuleManagement implements Serializable{

    @EJB
    private ModuleControllerLocal moduleControllerLocal;
    
    private List<Module> modules;
    private List<Module> filteredModules;
    private Module newModule;
    private Module moduleToUpdate;
    private Module moduleToAssign;
    private Module moduleToView;
    /**
     * Creates a new instance of moduleManagementManagedBean
     */
    public adminModuleManagement() {
        modules = new ArrayList<>();
        filteredModules = new ArrayList();
        newModule = new Module();
        moduleToUpdate = new Module();
        moduleToAssign = new Module();
        moduleToView = new Module();
    }
    
    @PostConstruct
    public void postConstruct()
    {
        modules = moduleControllerLocal.retrieveAllModules();
        //System.err.println("*********** modules: " + modules.size());
        
        for(Module m:modules)
            filteredModules.add(m);
    }
    
    
    public void createModule(ActionEvent event){
        try{
            
            Module m = moduleControllerLocal.createNewModule(newModule);
            
            modules.add(m);
            filteredModules.add(m);
            newModule = new Module();            
             
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New module created successfully (Module ID: " + m.getId() + ")", null));
            
        }
        catch(ModuleExistException ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while adding module: " + ex.getMessage(), null));
        }
    }
    
    public void removeModule(ActionEvent event){
        try
        {   
            Module moduleToDelete = (Module)event.getComponent().getAttributes().get("moduleToDelete");
            moduleControllerLocal.deleteModule(moduleToDelete);
            
            modules.remove(moduleToDelete);
            filteredModules.remove(moduleToDelete);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Module deleted successfully", null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public void updateModule(ActionEvent event)
    {
        try
        {
            moduleControllerLocal.updateModule(moduleToUpdate);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Module updated successfully", null));
        }
        catch(ModuleNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating module: " + ex.getMessage(), null));
        }
        
    }
    
    
    
    public void assignModule(ActionEvent event) throws IOException{
        moduleToAssign = (Module)event.getComponent().getAttributes().get("moduleToAssign");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("moduleToAssignId",moduleToAssign.getId());
        FacesContext.getCurrentInstance().getExternalContext().redirect("adminModuleAssignment.xhtml");
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public List<Module> getFilteredModules() {
        return filteredModules;
    }

    public void setFilteredModules(List<Module> filteredModules) {
        this.filteredModules = filteredModules;
    }

    public Module getNewModule() {
        return newModule;
    }

    public void setNewModule(Module newModule) {
        this.newModule = newModule;
    }

    public Module getModuleToUpdate() {
        return moduleToUpdate;
    }

    public void setModuleToUpdate(Module moduleToUpdate) {
        this.moduleToUpdate = moduleToUpdate;
    }

    public Module getModuleToAssign() {
        return moduleToAssign;
    }

    public void setModuleToAssign(Module moduleToAssign) {
        this.moduleToAssign = moduleToAssign;
    }

    public Module getModuleToView() {
        return moduleToView;
    }

    public void setModuleToView(Module moduleToView) {
        this.moduleToView = moduleToView;
    }
    
    
    
   
    
}
