/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.administratorControllerLocal;
import ejb.session.stateless.lecturerControllerLocal;
import ejb.session.stateless.studentControllerLocal;
import entity.Administrator;
import entity.Lecturer;
import entity.Student;
import java.io.IOException;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import util.exception.AdminNotFoundException;
import util.exception.LecturerNotFoundException;
import util.exception.StudentNotFoundException;

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
    
    private FacesMessage fm;
    private FacesContext fc;
    
    @EJB
    studentControllerLocal scl;
    @EJB
    lecturerControllerLocal lcl;
    @EJB
    administratorControllerLocal acl;
    
    public loginManagedBean() {
    }

    public void login() throws StudentNotFoundException, IOException{
        
        fm = new FacesMessage();
        fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
        student = scl.findStudent(username);
        if(student!=null){
            if(student.getPassword().equals(password)){
                session.setAttribute("user", student);
                session.setAttribute("username", username);
                fc.getExternalContext().redirect("studentDashboard.xhtml");
            }else{
                fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "The password is wrong", "Please check again");
                fc.addMessage(null, fm);
                username = "";
                password = "";
            }
        
        }else{
            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Student '" + username + "' does not exists.", "Please sign up first");
            fc.addMessage(null, fm);
            username = "";
            password = "";
        }
    }
    
    public void loginLecturer() throws IOException, LecturerNotFoundException{
        
        fm = new FacesMessage();
        fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
        if(lcl.retrieveLecturerByUsername(username)!=null){
            lecturer = lcl.retrieveLecturerByUsername(username);
            if(lecturer.getPassword().equals(password)){
                session.setAttribute("user", lecturer);
                session.setAttribute("username", username);
                fc.getExternalContext().redirect("lecturerDashboard.xhtml");
            }else{
                fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "The password is wrong", "Please check again");
                fc.addMessage(null, fm);
                username = "";
                password = "";
            }
        
        }else{
            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Lecturer '" + username + "' does not exists.", "Please appeal first");
            fc.addMessage(null, fm);
            username = "";
            password = "";
        }
    }
    
    public void loginAdmin() throws IOException, AdminNotFoundException{
        
        fm = new FacesMessage();
        fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
        admin = acl.retrieveAdminByUsername(username);
        if(admin!=null){
            if(admin.getPassword().equals(password)){
                session.setAttribute("user", admin);
                session.setAttribute("username", username);
                fc.getExternalContext().redirect("adminDashboard.xhtml");
            }else{
                fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "The password is wrong", "Please check again");
                fc.addMessage(null, fm);
                username = "";
                password = "";
            }
        
        }else{
            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Admin '" + username + "' does not exists.", "Please appeal first");
            fc.addMessage(null, fm);
            username = "";
            password = "";
        }
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

    public FacesMessage getFm() {
        return fm;
    }

    public void setFm(FacesMessage fm) {
        this.fm = fm;
    }

    public FacesContext getFc() {
        return fc;
    }

    public void setFc(FacesContext fc) {
        this.fc = fc;
    }

    public studentControllerLocal getScl() {
        return scl;
    }

    public void setScl(studentControllerLocal scl) {
        this.scl = scl;
    }

    public lecturerControllerLocal getLcl() {
        return lcl;
    }

    public void setLcl(lecturerControllerLocal lcl) {
        this.lcl = lcl;
    }

    public administratorControllerLocal getAcl() {
        return acl;
    }

    public void setAcl(administratorControllerLocal acl) {
        this.acl = acl;
    }


    
}
