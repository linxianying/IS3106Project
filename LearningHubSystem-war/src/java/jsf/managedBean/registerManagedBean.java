/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.studentControllerLocal;
import entity.Administrator;
import entity.Lecturer;
import entity.Student;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.exception.StudentExistException;

/**
 *
 * @author lin
 */
@ManagedBean
@RequestScoped
public class registerManagedBean {

    @EJB(name = "studentControllerLocal")
    private studentControllerLocal studentControllerLocal;

    private Student newStudent;    
         
    private String username;
    private String password;
    private String userType;
    private String telephone;
    private String faculty;
    private String department;
    private String email;
    private boolean isPremium;
    private String name;
    
    public registerManagedBean() {
        newStudent = new Student();
    }

    public Student getStudent() {
        return newStudent;
    }

    public void setStudent(Student student) {
        this.newStudent = student;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isIsPremium() {
        return isPremium;
    }

    public void setIsPremium(boolean isPremium) {
        this.isPremium = isPremium;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    public void doSignUp(ActionEvent event){
        try{
            newStudent = new Student(name, password, email, faculty, department, telephone, username);
            Student stu = studentControllerLocal.createStudent(newStudent);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New student created successfully (Student ID:  " + stu.getId()+ ")", null));
            FacesContext.getCurrentInstance().getExternalContext().redirect("loginStudent.xhtml");
        }
        catch(Exception ex){
            System.err.println(ex);
            System.out.println("Student with the same Username has already exists!");
        }
    }
}
