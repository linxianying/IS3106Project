package jsf.managedBean;

import ejb.session.stateless.ModuleControllerLocal;
import entity.Module;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import util.exception.ModuleNotFoundException;

/**
 *
 * @author mango
 */
@Named(value = "moduleDetailsManagedBean")
@ViewScoped
public class moduleDetailsManagedBean implements Serializable {

    @EJB(name = "ModuleControllerLocal")
    private ModuleControllerLocal moduleControllerLocal;

    private Long moduleIdToView;
    private Module moduleToView;

    public moduleDetailsManagedBean() {
    }

    public Module getModuleToView() {
        return moduleToView;
    }

    public void setModuleToView(Module moduleToView) {
        this.moduleToView = moduleToView;
    }

    @PostConstruct
    public void postConstruct() {
        moduleIdToView = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("moduleIdToView");
        
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

}
