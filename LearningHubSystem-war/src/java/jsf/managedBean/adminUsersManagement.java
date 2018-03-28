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
import util.exception.TAExistException;


/**
 *
 * @author Samango
 */
@Named(value = "accountManagement")
@SessionScoped
public class adminUsersManagement {

    @EJB
    private LecturerControllerLocal lecturerController;
    @EJB
    private StudentControllerLocal studentController;
    @EJB
    private TeachingAssistantControllerLocal taController;
    
    private List<Lecturer> lecturers;
    private Lecturer newLecturer;
    private List<Student> students;
    private List<TeachingAssistant> TAs;
    private TeachingAssistant newTA;
    
    
    /**
     * Creates a new instance of accountManagement
     */
    
    public adminUsersManagement() {
        lecturers = new ArrayList();
        students = new ArrayList();
        TAs = new ArrayList();
        newLecturer = new Lecturer();
        newTA = new TeachingAssistant();
        
        
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
    
    public void createTA(ActionEvent event) throws GeneralException {
        
        try{
            TeachingAssistant ta = taController.createTeachingAssistant(newTA);
            TAs.add(ta);
            newTA = new TeachingAssistant();
             
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Teaching Assistant account created successfully (TA ID: " + ta.getId() + ")", null));
            
        }
        catch(TAExistException ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while create TA account: " + ex.getMessage(), null));
        } 
    }
    
    public void deleteLecturer(ActionEvent event){
        try
        {
            Lecturer lecturerToDelete = (Lecturer)event.getComponent().getAttributes().get("lecturerToDelete");
            lecturerController.deleteLecturer(lecturerToDelete);
            
            lecturers.remove(lecturerToDelete);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Lecturer account deleted successfully", null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public void deleteStudent(ActionEvent event){
        try
        {
            Student studentToDelete = (Student)event.getComponent().getAttributes().get("studentToDelete");
            studentController.deleteStudent(studentToDelete);
            
            students.remove(studentToDelete);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Student account deleted successfully", null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public void deleteTA (ActionEvent event){
        try
        {
            TeachingAssistant taToDelete = (TeachingAssistant)event.getComponent().getAttributes().get("taToDelete");
            taController.deleteTA(taToDelete);
            
            TAs.remove(taToDelete);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "TA account deleted successfully", null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    
    
    
    
    
}
