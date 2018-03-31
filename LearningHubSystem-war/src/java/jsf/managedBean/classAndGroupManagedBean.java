/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ModuleControllerLocal;
import entity.Module;
import entity.Student;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import util.exception.ModuleNotFoundException;

/**
 *
 * @author wyh
 */
@Named(value = "classAndGroupManagedBean")
@ViewScoped
public class classAndGroupManagedBean implements Serializable {

    @EJB
    private ModuleControllerLocal moduleController;

    private Long moduleId;
    private Module module;
    private List<Student> students;
    private Student selectedStudent;
    FacesContext context;
    HttpSession session;

    @PostConstruct
    public void init() {
        context = FacesContext.getCurrentInstance();
        session = (HttpSession) context.getExternalContext().getSession(true);
        moduleId = (Long) session.getAttribute("moduleIdToView");
        try {
            module = moduleController.retrieveModuleById(moduleId);
            students = module.getStduents();
        } catch (ModuleNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public classAndGroupManagedBean() {
        module = new Module();
        students = new ArrayList<>();
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    /**
     * @return the students
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * @param students the students to set
     */
    public void setStudents(List<Student> students) {
        this.students = students;
    }

    /**
     * @return the selectedStudent
     */
    public Student getSelectedStudent() {
        return selectedStudent;
    }

    /**
     * @param selectedStudent the selectedStudent to set
     */
    public void setSelectedStudent(Student selectedStudent) {
        this.selectedStudent = selectedStudent;
    }

}
