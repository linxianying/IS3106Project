/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.AnnouncementControllerLocal;
import ejb.session.stateless.ModuleControllerLocal;
import entity.Announcement;
import entity.Lecturer;
import entity.Module;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import util.exception.AnnouncementExistException;
import util.exception.ModuleNotFoundException;

/**
 *
 * @author lin
 */

@Named
@ViewScoped
public class announcementManagedBean implements Serializable{

    @EJB
    private ModuleControllerLocal moduleController;

    @EJB
    private AnnouncementControllerLocal announcementController;

    private Long moduleId;
    private Module module;
    private Lecturer lecturer;
    private List<Announcement> announcements;
    private Announcement newAnnouncement;
    FacesContext context;
    HttpSession session;

    public announcementManagedBean() {
        lecturer = new Lecturer();
        announcements = new ArrayList<>();
        newAnnouncement = new Announcement();
    }

    @PostConstruct
    public void init() {
        context = FacesContext.getCurrentInstance();
        session = (HttpSession) context.getExternalContext().getSession(true);
        moduleId = (Long) session.getAttribute("moduleIdToView");
        System.err.println(moduleId);
        try {
            Module module = moduleController.retrieveModuleById(moduleId);
            announcements = module.getAnnouncements();
            if(announcements.size()==0) {
                System.err.println("empty!!!!!!!!!!!!");
            }
            if(session.getAttribute("role").equals("currentLecturer")){
            lecturer = (Lecturer) session.getAttribute("currentLecturer");
            }
            
            
        } catch (ModuleNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    public void createNewAnnouncement(ActionEvent event) {
        Date date = new Date();
        newAnnouncement.setDate(new Timestamp(date.getTime()));
        try {
            Announcement a = announcementController.createNewAnnouncement(newAnnouncement, lecturer, module);
            announcements.add(a);
            newAnnouncement = new Announcement();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New announcement created successfully (Product ID: " + a.getId() + ")", null));
        } catch (AnnouncementExistException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new announcement: name of announcement must be unique. " + ex.getMessage(), null));
        }
    }
    

    /**
     * @return the announcements
     */
    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    /**
     * @param announcements the announcements to set
     */
    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }

    /**
     * @return the newAnnouncement
     */
    public Announcement getNewAnnouncement() {
        return newAnnouncement;
    }

    /**
     * @param newAnnouncement the newAnnouncement to set
     */
    public void setNewAnnouncement(Announcement newAnnouncement) {
        this.newAnnouncement = newAnnouncement;
    }

}
