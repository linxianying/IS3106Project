/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.AdministratorControllerLocal;
import ejb.session.stateless.LecturerControllerLocal;
import ejb.session.stateless.StudentControllerLocal;
import ejb.session.stateless.TeachingAssistantControllerLocal;
import entity.Lecturer;
import entity.Module;
import entity.Student;
import entity.TeachingAssistant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import util.exception.GeneralException;
import util.exception.LecturerExistException;
import util.exception.ModuleExistException;


/**
 *
 * @author Samango
 */
@Named(value = "accountManagement")
@SessionScoped
public class adminUsersManagement {

    @EJB
    private LecturerControllerLocal lecturerController;
    private StudentControllerLocal studentController;
    private TeachingAssistantControllerLocal taController;
    
    private List<Lecturer> lecturers;
    private Lecturer newLecturer;
    private Lecturer lecturerToRemove;
    private List<Student> students;
    private Student studentToRemove;
    private List<TeachingAssistant> TAs;
    private TeachingAssistant taToRemove;
    
    
    /**
     * Creates a new instance of accountManagement
     */
    
    public adminUsersManagement() {
        lecturers = new ArrayList();
        students = new ArrayList();
        TAs = new ArrayList();
        newLecturer = new Lecturer();
        lecturerToRemove = new Lecturer();
        studentToRemove = new Student();
        taToRemove = new TeachingAssistant();
        
    }
    
    @PostConstruct
    public void postConstruct(){
        lecturers = lecturerController.retrieveAllLecturers();
        students = studentController.retrieveAllStudents();
        TAs = taController.retrieveAllTAs();
    }
    
    public void createLecturer(ActionEvent event) throws GeneralException {
        
        try{
            Lecturer l = lecturerController.createNewLecturer(newLecturer);
            lecturers.add(l);
            newLecturer = new Lecturer();
             
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New lecturer account created successfully (Lecturer ID: " + l.getId() + ")", null));
            
        }
        catch(LecturerExistException ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while create lecturer account: " + ex.getMessage(), null));
        }
    }
    
    
}
