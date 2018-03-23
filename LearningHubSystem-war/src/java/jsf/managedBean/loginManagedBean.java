/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Administrator;
import entity.Lecturer;
import entity.Student;
import entity.TeachingAssistant;
import java.io.IOException;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import util.exception.AdminNotFoundException;
import util.exception.LecturerNotFoundException;
import util.exception.StudentNotFoundException;
import util.exception.TANotFoundException;
import ejb.session.stateless.AdministratorControllerLocal;
import ejb.session.stateless.StudentControllerLocal;
import ejb.session.stateless.LecturerControllerLocal;
import ejb.session.stateless.TeachingAssistantControllerLocal;
import javax.faces.event.ActionEvent;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author lin
 */
@ManagedBean
@RequestScoped
public class loginManagedBean {

    @EJB
    private TeachingAssistantControllerLocal teachingAssistantControllerLocal;

    @EJB
    private StudentControllerLocal studentControllerLocal;

    @EJB
    private LecturerControllerLocal lecturerControllerLocal;

    @EJB
    private AdministratorControllerLocal administratorControllerLocal;

    private String username;
    private String password;
    
   
    
    public loginManagedBean() {
    }

    public void loginStudent(ActionEvent event) throws StudentNotFoundException, IOException{
        try
        {
            Student currentStudent = studentControllerLocal.login(username, password);
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogin", true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentStudent", currentStudent);
            FacesContext.getCurrentInstance().getExternalContext().redirect("studentDashboard.xhtml");            
        }
        catch(InvalidLoginCredentialException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credential: " + ex.getMessage(), null));
        }
    }
    
    public void loginTA(ActionEvent event) throws TANotFoundException, IOException{ 
        try
        {
            TeachingAssistant currentTA = teachingAssistantControllerLocal.login(username, password);
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogin", true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentTA", currentTA);
            FacesContext.getCurrentInstance().getExternalContext().redirect("TADashboard.xhtml");            
        }
        catch(InvalidLoginCredentialException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credential: " + ex.getMessage(), null));
        }
    }
    
    public void loginLecturer(ActionEvent event) throws IOException, LecturerNotFoundException{
        try
        {
            Lecturer currentLecturer = lecturerControllerLocal.login(username, password);
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogin", true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentLecturer", currentLecturer);
            FacesContext.getCurrentInstance().getExternalContext().redirect("lecturerDashboard.xhtml");            
        }
        catch(InvalidLoginCredentialException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credential: " + ex.getMessage(), null));
        }
    }
    
    public void loginAdmin(ActionEvent event) throws IOException, AdminNotFoundException{
        try
        {
            Administrator currentAdmin = administratorControllerLocal.login(username, password);
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogin", true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentAdmin", currentAdmin);
            FacesContext.getCurrentInstance().getExternalContext().redirect("adminDashboard.xhtml");            
        }
        catch(InvalidLoginCredentialException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credential: " + ex.getMessage(), null));
        }
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
}
