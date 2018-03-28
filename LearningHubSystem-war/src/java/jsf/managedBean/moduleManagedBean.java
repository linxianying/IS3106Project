/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ModuleControllerLocal;
import ejb.session.stateless.StudentControllerLocal;
import entity.Lecturer;
import entity.Module;
import entity.Student;
import entity.TeachingAssistant;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

;

/**
 *
 * @author lin
 */
@ManagedBean
@SessionScoped
public class moduleManagedBean implements Serializable {

    private Student student;
    private Lecturer lecturer;
    private TeachingAssistant ta;
    private List<Module> modules;
    private Module moduleToView;
    FacesContext context;
    HttpSession session;

    @PostConstruct
    public void init() {
        context = FacesContext.getCurrentInstance();
        session = (HttpSession) context.getExternalContext().getSession(true);

        if (session.getAttribute("role").equals("student")) {
            student = (Student) session.getAttribute("currentStudent");

            modules = student.getModules();
        } else if (session.getAttribute("role").equals("lecturer")) {
            lecturer = (Lecturer) session.getAttribute("currentLecturer");

            modules = lecturer.getModules();
        } else if (session.getAttribute("role").equals("TA")) {
            ta = (TeachingAssistant) session.getAttribute("currentTA");

            modules = ta.getModules();
        }
    }

    public void viewModuleDetails(ActionEvent event) throws IOException {
        Long moduleIdToView = (Long) event.getComponent().getAttributes().get("ModuleIdToView");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("moduleIdToView", moduleIdToView);

        if (session.getAttribute("role").equals("student")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("studentModuleDetails.xhtml");
        } else if (session.getAttribute("role").equals("lecturer")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("lecturerModuleDetails.xhtml");
        } else if (session.getAttribute("role").equals("TA")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("TAModuleDetails.xhtml");
        }

    }

    public moduleManagedBean() {
        modules = new ArrayList<>();
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * @return the modules
     */
    public List<Module> getModules() {
        return modules;
    }

    /**
     * @param modules the modules to set
     */
    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    /**
     * @return the moduleToView
     */
    public Module getModuleToView() {
        return moduleToView;
    }

    /**
     * @param moduleToView the moduleToView to set
     */
    public void setModuleToView(Module moduleToView) {
        this.moduleToView = moduleToView;
    }

}
