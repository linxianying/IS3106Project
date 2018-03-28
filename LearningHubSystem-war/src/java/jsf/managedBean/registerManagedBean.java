/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Student;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import ejb.session.stateless.StudentControllerLocal;

/**
 *
 * @author lin
 */
@ManagedBean
@RequestScoped
public class registerManagedBean {

    @EJB(name = "studentControllerLocal")
    private StudentControllerLocal studentControllerLocal;

    private Student newStudent;

    public registerManagedBean() {
        newStudent = new Student();
    }

    public Student getNewStudent() {
        return newStudent;
    }

    public void setNewStudent(Student newStudent) {
        this.newStudent = newStudent;
    }
    
    public void doSignUp(ActionEvent event) {
        try {
            Student stu = studentControllerLocal.createStudent(newStudent);
            newStudent = new Student();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New student created successfully (Student ID:  " + stu.getId() + ")", null));

            //if never login, go to login page
            if (!(Boolean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("isLogin")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("loginStudent.xhtml");
            } 
            //if login already, should logout before logoin with new account
            else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "You need to logout before login with new account.", null));
            }
        } catch (Exception ex) {
            System.err.println(ex);
            System.out.println("Student with the same Username has already exists!");
        }
    }
}
