/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.LecturerControllerLocal;
import ejb.session.stateless.StudentControllerLocal;
import ejb.session.stateless.TeachingAssistantControllerLocal;
import entity.Lecturer;
import entity.Student;
import entity.TeachingAssistant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
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
public class adminUsersManagement implements Serializable {

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
    
    private List<Lecturer> filteredLecturers;
    private List<Student> filteredStudents;
    private List<TeachingAssistant> filteredTAs;
            
    private Lecturer lecToView;
    private Student stuToView;
    private TeachingAssistant taToView;
    
    
    
    /**
     * Creates a new instance of accountManagement
     */
    
    public adminUsersManagement() {
        lecturers = new ArrayList();
        students = new ArrayList();
        TAs = new ArrayList();
        
        filteredLecturers = new ArrayList();
        filteredStudents = new ArrayList();
        filteredTAs = new ArrayList();              
        
        newLecturer = new Lecturer();
        newTA = new TeachingAssistant();
        lecToView = new Lecturer();
        stuToView = new Student();
        taToView = new TeachingAssistant();
        
    }
    
    @PostConstruct
    public void postConstruct(){
        lecturers = lecturerController.retrieveAllLecturers();
        students = studentController.retrieveAllStudents();
        TAs = taController.retrieveAllTAs();
        
        for(Lecturer l: lecturers)
            filteredLecturers.add(l);
        for(Student s: students)
            filteredStudents.add(s);
        for(TeachingAssistant t:TAs)
            filteredTAs.add(t);
    }
    
    public void createLecturer(ActionEvent event) throws GeneralException {
        
        try{
            Lecturer l = lecturerController.createNewLecturer(newLecturer);
            lecturers.add(l);
            filteredLecturers.add(l);
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
            filteredTAs.add(ta);
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
            filteredLecturers.remove(lecturerToDelete);

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
            filteredStudents.remove(studentToDelete);

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
            filteredTAs.remove(taToDelete);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "TA account deleted successfully", null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public Lecturer getNewLecturer() {
        return newLecturer;
    }

    public void setNewLecturer(Lecturer newLecturer) {
        this.newLecturer = newLecturer;
    }

    public TeachingAssistant getNewTA() {
        return newTA;
    }

    public void setNewTA(TeachingAssistant newTA) {
        this.newTA = newTA;
    }

    public List<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setLecturers(List<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<TeachingAssistant> getTAs() {
        return TAs;
    }

    public void setTAs(List<TeachingAssistant> TAs) {
        this.TAs = TAs;
    }

    public Lecturer getLecToView() {
        return lecToView;
    }

    public void setLecToView(Lecturer lecToView) {
        this.lecToView = lecToView;
    }

    public Student getStuToView() {
        return stuToView;
    }

    public void setStuToView(Student stuToView) {
        this.stuToView = stuToView;
    }

    public TeachingAssistant getTaToView() {
        return taToView;
    }

    public void setTaToView(TeachingAssistant taToView) {
        this.taToView = taToView;
    }

    public List<Lecturer> getFilteredLecturers() {
        return filteredLecturers;
    }

    public void setFilteredLecturers(List<Lecturer> filteredLecturers) {
        this.filteredLecturers = filteredLecturers;
    }

    public List<Student> getFilteredStudents() {
        return filteredStudents;
    }

    public void setFilteredStudents(List<Student> filteredStudents) {
        this.filteredStudents = filteredStudents;
    }

    public List<TeachingAssistant> getFilteredTAs() {
        return filteredTAs;
    }

    public void setFilteredTAs(List<TeachingAssistant> filteredTAs) {
        this.filteredTAs = filteredTAs;
    }

    
    
    
    
    
    
}
