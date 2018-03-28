/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.LecturerControllerLocal;
import ejb.session.stateless.ModuleControllerLocal;
import ejb.session.stateless.TeachingAssistantControllerLocal;
import entity.Lecturer;
import entity.Module;
import entity.TeachingAssistant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import util.exception.ModuleNotFoundException;

/**
 *
 * @author mango
 */
@Named(value = "facilitatorManagedBean")
@ViewScoped
public class facilitatorManagedBean implements Serializable {

    @EJB(name = "ModuleControllerLocal")
    private ModuleControllerLocal moduleControllerLocal;

    private Lecturer selectedLecturer;
    private TeachingAssistant selectedTA;
    private List<Lecturer> lecturers;
    private List<TeachingAssistant> TAs;
    FacesContext context;
    HttpSession session;
    private Long moduleIdToView;
    private Module moduleToView;

    public facilitatorManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        context = FacesContext.getCurrentInstance();
        session = (HttpSession) context.getExternalContext().getSession(true);
        moduleIdToView = (Long) session.getAttribute("moduleIdToView");
        try {
            moduleToView = moduleControllerLocal.retrieveModuleById(moduleIdToView);
            lecturers = moduleToView.getLecturers();
            TAs = moduleToView.getTAs();
        } catch (ModuleNotFoundException ex) {
            moduleToView = new Module();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the module details: " + ex.getMessage(), null));
        } catch (Exception ex) {
            moduleToView = new Module();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public Lecturer getSelectedLecturer() {
        return selectedLecturer;
    }

    public void setSelectedLecturer(Lecturer selectedLecturer) {
        this.selectedLecturer = selectedLecturer;
    }

    public TeachingAssistant getSelectedTA() {
        return selectedTA;
    }

    public void setSelectedTA(TeachingAssistant selectedTa) {
        this.selectedTA = selectedTa;
    }

    public List<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setLecturers(List<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    public List<TeachingAssistant> getTAs() {
        return TAs;
    }

    public void setTAs(List<TeachingAssistant> TAs) {
        this.TAs = TAs;
    }

}
