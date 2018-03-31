/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.LecturerControllerLocal;
import ejb.session.stateless.ModuleControllerLocal;
import ejb.session.stateless.StudentControllerLocal;
import ejb.session.stateless.TeachingAssistantControllerLocal;
import entity.Lecturer;
import entity.Module;
import entity.Student;
import entity.TeachingAssistant;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.exception.LecturerNotFoundException;
import util.exception.ModuleExistException;
import util.exception.ModuleNotFoundException;
import util.exception.StudentNotFoundException;
import util.exception.TANotFoundException;

/**
 *
 * @author Samango
 */
@Named(value = "moduleAssignment")
@RequestScoped
public class moduleAssignment implements Serializable {

    @EJB
    private TeachingAssistantControllerLocal taController;
    @EJB
    private StudentControllerLocal studentController;
    @EJB
    private LecturerControllerLocal lecturerController;
    @EJB
    private ModuleControllerLocal moduleController;
    
    private List<Student> students;
    private List<Lecturer> lecturers;
    private List<TeachingAssistant> TAs;
    
    private Module moduleToAssign;
    private Student studentToRg;
    private Lecturer lecturerToRg;
    private TeachingAssistant TAToRg;

    /**
     * Creates a new instance of moduleAssignment
     */
    
    public moduleAssignment() {
        students = new ArrayList<>();
        lecturers = new ArrayList<>();
        TAs = new ArrayList<>();
        
        moduleToAssign = new Module();
        studentToRg = new Student();
        lecturerToRg = new Lecturer();
        TAToRg = new TeachingAssistant();
    }
    
    @PostConstruct
    public void postConstruct(){
        Long moduleToAssignId = (Long)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("moduleToAssignId");
        try {
            moduleToAssign = moduleController.retrieveModuleById(moduleToAssignId);
        } catch (ModuleNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));             
        }
        
        List<Student> allStudents = studentController.retrieveAllStudents();
        allStudents.stream().filter((st) -> (st.getModules().contains(moduleToAssign))).forEachOrdered((st) -> {
            students.add(st);
        });
        
        List<Lecturer> allLecturers= lecturerController.retrieveAllLecturers();
        allLecturers.stream().filter((lc) -> (lc.getModules().contains(moduleToAssign))).forEachOrdered((lc) -> {
            lecturers.add(lc);
        });
        
        List<TeachingAssistant> allTAs = taController.retrieveAllTAs();
        allTAs.stream().filter((ta) -> (ta.getModules().contains(moduleToAssign))).forEachOrdered((ta) -> {
            TAs.add(ta);
        });
    }
    
    public void enrollStudent(ActionEvent event){
        Long studentId = (Long)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("studentId");
        try {
            studentToRg = studentController.retrieveStudentById(studentId);
            studentController.registerModule(studentToRg, moduleToAssign);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Student has been added to module successfully", null));
        } catch (StudentNotFoundException | ModuleExistException ex) {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));             
        }
    }
    
    public void enrollLecturer(ActionEvent event){
        Long lecturerId = (Long)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("lecturerId");
        try {
            lecturerToRg = lecturerController.retrieveLecturerById(lecturerId);
            lecturerController.registerModule(lecturerToRg, moduleToAssign);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Lecturer has been added to module successfully", null));
        } catch (LecturerNotFoundException | ModuleExistException ex) {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));             
        }
    }
    
    public void enrollTA(ActionEvent event){
        Long taId = (Long)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("taId");
        try {
            TAToRg = taController.retrieveTAById(taId); 
            taController.registerModule(TAToRg, moduleToAssign);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "TA has been added to module successfully", null));
        } catch (TANotFoundException | ModuleExistException ex) {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));             
        }
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
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

    public Module getModuleToAssign() {
        return moduleToAssign;
    }

    public void setModuleToAssign(Module moduleToAssign) {
        this.moduleToAssign = moduleToAssign;
    }

    public Student getStudentToRg() {
        return studentToRg;
    }

    public void setStudentToRg(Student studentToRg) {
        this.studentToRg = studentToRg;
    }

    public Lecturer getLecturerToRg() {
        return lecturerToRg;
    }

    public void setLecturerToRg(Lecturer lecturerToRg) {
        this.lecturerToRg = lecturerToRg;
    }

    public TeachingAssistant getTAToRg() {
        return TAToRg;
    }

    public void setTAToRg(TeachingAssistant TAToRg) {
        this.TAToRg = TAToRg;
    }
    
    
   
    
}
