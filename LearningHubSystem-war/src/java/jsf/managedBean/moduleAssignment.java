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
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

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
        List<Student> allStudents = studentController.retrieveAllStudents();
        for (Student st: allStudents){
            if(st.getModules().contains(moduleToAssign))
                students.add(st);
        }
        List<Lecturer> allLecturers= lecturerController.retrieveAllLecturers();
        for (Lecturer lc: allLecturers){
            if(lc.getModules().contains(moduleToAssign))
                lecturers.add(lc);
        }
        List<TeachingAssistant> allTAs = taController.retrieveAllTAs();
        for (TeachingAssistant ta: allTAs){
            if(ta.getModules().contains(moduleToAssign))
                TAs.add(ta);
        }
    }
    
    public void enrollStudent(ActionEvent ac){
        
    }
    
   
    
}
