/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ModuleControllerLocal;
import entity.Module;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import util.exception.ModuleNotFoundException;

/**
 *
 * @author mango
 */
@Named(value = "moduleDetailsManagedBean")
@SessionScoped
public class moduleDetailsManagedBean implements Serializable {

    @EJB(name = "ModuleControllerLocal")
    private ModuleControllerLocal moduleControllerLocal;

    private Long moduleIdToView;
    private Module moduleToView;
    FacesContext context;
    HttpSession session;

    public moduleDetailsManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        moduleIdToView = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("moduleIdToView");
        System.err.println("?????????");
        System.err.println(moduleIdToView);
        try {
            setModuleToView(moduleControllerLocal.retrieveModuleById(moduleIdToView));
        } catch (ModuleNotFoundException ex) {
            setModuleToView(new Module());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the module details: " + ex.getMessage(), null));
        } catch (Exception ex) {
            setModuleToView(new Module());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    /**
     * @return the moduleToView
     */
    public Module getModuleToView() {
        return moduleToView;
    }

    /**
     * @param moduleToView the moduleToView to set
     */
    public void setModuleToView(Module moduleToView) {
        this.moduleToView = moduleToView;
    }

}
