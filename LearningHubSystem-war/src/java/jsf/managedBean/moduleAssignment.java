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
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
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
@ViewScoped
public class moduleAssignment implements Serializable {

    @EJB
    private TeachingAssistantControllerLocal taController;
    @EJB
    private StudentControllerLocal studentController;
    @EJB
    private LecturerControllerLocal lecturerController;
    @EJB
    private ModuleControllerLocal moduleController;
    
    private List<Lecturer> allLecturers;
    private List<Student> allStudents;
    private List<TeachingAssistant> allTAs;

    private List<Student> students;
    private List<Student> filteredStudents;
    private List<Lecturer> lecturers;
    private List<Lecturer> filteredLecturers;
    private List<TeachingAssistant> TAs;

    private Module moduleToAssign;
    private Student studentToRg;
    private Lecturer lecturerToRg;
    private TeachingAssistant TAToRg;

    private Long studentIdToRg;
    private Long lecturerIdToRg;
    private Long TAIdToRg;

    @PostConstruct
    public void postConstruct() {

        Long moduleToAssignId = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("moduleToAssignId");

        try {
            moduleToAssign = moduleController.retrieveModuleById(moduleToAssignId);
        } catch (ModuleNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }

        allStudents = studentController.retrieveAllStudents();
        allStudents.stream().filter((st) -> (st.getModules().contains(moduleToAssign))).forEachOrdered((st) -> {
            students.add(st);
        });

        allLecturers = lecturerController.retrieveAllLecturers();
        for (Lecturer lc : allLecturers) {
            if (lc.getModules().contains(moduleToAssign)) {
                lecturers.add(lc);
            }
        }

        allTAs = taController.retrieveAllTAs();
        allTAs.stream().filter((ta) -> (ta.getModules().contains(moduleToAssign))).forEachOrdered((ta) -> {
            TAs.add(ta);
        });
    }

    /**
     * Creates a new instance of moduleAssignment
     */
    public moduleAssignment() {
        students = new ArrayList<>();
        filteredStudents = new ArrayList<>();
        lecturers = new ArrayList<>();
        TAs = new ArrayList<>();

        moduleToAssign = new Module();
        studentToRg = new Student();
        lecturerToRg = new Lecturer();
        TAToRg = new TeachingAssistant();

    }

    public void enrollStudent(ActionEvent event) throws ModuleNotFoundException {
        //Long studentId = (Long)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("studentId");
        try {

            studentToRg = studentController.retrieveStudentById(studentIdToRg);
            studentController.registerModule(studentToRg, moduleToAssign);

            students.add(studentToRg);
            filteredStudents.add(studentToRg);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Student has been added to module successfully", null));

        } catch (StudentNotFoundException | ModuleExistException ex) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));

        }
    }

    public void enrollLecturer(ActionEvent event) {
        //Long lecturerId = (Long)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("lecturerId");
        try {

            
            lecturerToRg = lecturerController.retrieveLecturerById(lecturerIdToRg);
            lecturerController.registerModule(lecturerToRg, moduleToAssign);

            lecturers.add(lecturerToRg);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Lecturer has been added to module successfully", null));

        } catch (LecturerNotFoundException | ModuleExistException | ModuleNotFoundException ex) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));

        }
    }

    public void enrollTA(ActionEvent event) throws ModuleNotFoundException {
        //Long taId = (Long)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("taId");
        try {

            TAToRg = taController.retrieveTAById(TAIdToRg);
            taController.registerModule(TAToRg, moduleToAssign);

            TAs.add(TAToRg);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "TA has been added to module successfully", null));

        } catch (TANotFoundException | ModuleExistException ex) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));

        }
    }

    public void removeLecturer(ActionEvent event) throws LecturerNotFoundException {
        try {
            System.out.println("enter method");
           
            Lecturer lecturerToRemove = (Lecturer) event.getComponent().getAttributes().get("lecturerToRemove");
            lecturerController.dropModule(lecturerToRemove, moduleToAssign);

            lecturers.remove(lecturerToRemove);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Lecturer has been removed from module successfully", null));
        } catch (ModuleNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void removeTA(ActionEvent event) throws TANotFoundException {
        try {
            TeachingAssistant taToRemove = (TeachingAssistant) event.getComponent().getAttributes().get("taToRemove");
            taController.dropModule(taToRemove, moduleToAssign);

            TAs.remove(taToRemove);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "TA has been removed from module successfully", null));
        } catch (ModuleNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        } 
    }

    public void removeStudent(ActionEvent event) throws StudentNotFoundException {
        try {
            Student studentToRemove = (Student) event.getComponent().getAttributes().get("studentToRemove");
            studentController.dropModule(studentToRemove, moduleToAssign);

            students.remove(studentToRemove);
          

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Student has been removed from module successfully", null));
        } catch (ModuleNotFoundException ex) {
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

    public List<Student> getFilteredStudents() {
        return filteredStudents;
    }

    public void setFilteredStudents(List<Student> filteredStudents) {
        this.filteredStudents = filteredStudents;
    }

    public Long getStudentIdToRg() {
        return studentIdToRg;
    }

    public void setStudentIdToRg(Long studentIdToRg) {
        this.studentIdToRg = studentIdToRg;
    }

    public Long getLecturerIdToRg() {
        return lecturerIdToRg;
    }

    public void setLecturerIdToRg(Long lecturerIdToRg) {
        this.lecturerIdToRg = lecturerIdToRg;
    }

    public Long getTAIdToRg() {
        return TAIdToRg;
    }

    public void setTAIdToRg(Long TAIdToRg) {
        this.TAIdToRg = TAIdToRg;
    }

    public TeachingAssistantControllerLocal getTaController() {
        return taController;
    }

    public void setTaController(TeachingAssistantControllerLocal taController) {
        this.taController = taController;
    }

    public StudentControllerLocal getStudentController() {
        return studentController;
    }

    public void setStudentController(StudentControllerLocal studentController) {
        this.studentController = studentController;
    }

    public LecturerControllerLocal getLecturerController() {
        return lecturerController;
    }

    public void setLecturerController(LecturerControllerLocal lecturerController) {
        this.lecturerController = lecturerController;
    }

    public ModuleControllerLocal getModuleController() {
        return moduleController;
    }

    public void setModuleController(ModuleControllerLocal moduleController) {
        this.moduleController = moduleController;
    }

    public List<Lecturer> getFilteredLecturers() {
        return filteredLecturers;
    }

    public void setFilteredLecturers(List<Lecturer> filteredLecturers) {
        this.filteredLecturers = filteredLecturers;
    }

    public List<Lecturer> getAllLecturers() {
        return allLecturers;
    }

    public void setAllLecturers(List<Lecturer> allLecturers) {
        this.allLecturers = allLecturers;
    }

    public List<Student> getAllStudents() {
        return allStudents;
    }

    public void setAllStudents(List<Student> allStudents) {
        this.allStudents = allStudents;
    }

    public List<TeachingAssistant> getAllTAs() {
        return allTAs;
    }

    public void setAllTAs(List<TeachingAssistant> allTAs) {
        this.allTAs = allTAs;
    }

    
}
