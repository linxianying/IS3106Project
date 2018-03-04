/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Administrator;
import entity.Lecturer;
import entity.Student;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author lin
 */
@ManagedBean
@RequestScoped
public class loginManagedBean {

    /**
     * Creates a new instance of loginManagedBean
     */
    private Student student;    
    private Lecturer lecturer;
    private Administrator admin;
         
    private String username;
    private String password;
    private String userType;
    
    public loginManagedBean() {
    }

    public void doRedirect() throws IOException {
        FacesMessage fmsg = new FacesMessage();
        fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "redirecting to register page", "");
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, fmsg);
        context.getExternalContext().redirect("register.xhtml");
    }
    
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Administrator getAdmin() {
        return admin;
    }

    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    
}
