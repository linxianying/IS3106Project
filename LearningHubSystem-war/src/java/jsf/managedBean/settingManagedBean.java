/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.AdministratorControllerLocal;
import ejb.session.stateless.LecturerControllerLocal;
import ejb.session.stateless.StudentControllerLocal;
import ejb.session.stateless.TeachingAssistantControllerLocal;
import entity.Lecturer;
import entity.Student;
import entity.Administrator;
import entity.TeachingAssistant;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import util.exception.AdminNotFoundException;
import util.exception.LecturerNotFoundException;
import util.exception.PasswordChangeException;
import util.exception.StudentNotFoundException;
import util.exception.TANotFoundException;

/**
 *
 * @author mango
 */
@Named
@ViewScoped
public class settingManagedBean implements Serializable {

    @EJB(name = "TeachingAssistantControllerLocal")
    private TeachingAssistantControllerLocal teachingAssistantControllerLocal;

    @EJB(name = "AdministratorControllerLocal")
    private AdministratorControllerLocal administratorControllerLocal;

    @EJB(name = "LecturerControllerLocal")
    private LecturerControllerLocal lecturerControllerLocal;

    @EJB(name = "StudentControllerLocal")
    private StudentControllerLocal studentControllerLocal;

    private String userType;
    FacesContext context;
    HttpSession session;
    private Student student;
    private Administrator admin;
    private Lecturer lecturer;
    private TeachingAssistant ta;
    private String currentPassword;
    private String newPassword;
    private Boolean isPremium;
    private Boolean choosePremium;

    public settingManagedBean() {

    }

    @PostConstruct
    public void postConstruct() {
        context = FacesContext.getCurrentInstance();
        session = (HttpSession) context.getExternalContext().getSession(true);

        setUserType((String) session.getAttribute("role"));
        if (getUserType().equals("student")) {
            setStudent((Student) session.getAttribute("currentStudent"));
            setIsPremium((Boolean) student.getIsPremium());
        } else if (getUserType().equals("lecturer")) {
            setLecturer((Lecturer) session.getAttribute("currentLecturer"));
            setIsPremium((Boolean) lecturer.getIsPremium());
        } else if (getUserType().equals("TA")) {
            setTa((TeachingAssistant) session.getAttribute("currentTA"));
            setIsPremium((Boolean) ta.getIsPremium());
        } else if (getUserType().equals("admin")) {
            setAdmin((Administrator) session.getAttribute("currentAdmin"));
            setIsPremium((Boolean) admin.getIsPremium());
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isPremium", isPremium);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("choosePremium", false);
    }

    public void update(ActionEvent event) {
        if (!isPremium) {
            if (choosePremium) {
                isPremium = true;
            }
        }

        if (getUserType().equals("student")) {
            try {
                student.setIsPremium(isPremium);
                studentControllerLocal.updateStudent(student);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile updated successfully", null));
            } catch (StudentNotFoundException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
            }
        } else if (getUserType().equals("lecturer")) {
            try {
                lecturer.setIsPremium(isPremium);
                lecturerControllerLocal.updateLecturer(lecturer);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile updated successfully", null));
            } catch (LecturerNotFoundException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
            }
        } else if (getUserType().equals("TA")) {
            try {
                ta.setIsPremium(isPremium);
                teachingAssistantControllerLocal.updateTA(ta);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile updated successfully", null));
            } catch (TANotFoundException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
            }
        } else if (getUserType().equals("admin")) {
            try {
                admin.setIsPremium(isPremium);
                administratorControllerLocal.updateAdmin(admin);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile updated successfully", null));
            } catch (AdminNotFoundException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
            }
        }
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isPremium", isPremium);
 
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("choosePremium", false);
    }

    public void updatePassword() {
        if (getUserType().equals("student")) {
            try {
                studentControllerLocal.changePassword(currentPassword, newPassword, student.getId());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Password is changed successfully", null));
            } catch (StudentNotFoundException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
            } catch (PasswordChangeException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
            }
        } else if (getUserType().equals("lecturer")) {
            try {
                lecturerControllerLocal.changePassword(currentPassword, newPassword, lecturer.getId());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Password is changed successfully", null));
            } catch (LecturerNotFoundException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
            } catch (PasswordChangeException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
            }
        } else if (getUserType().equals("TA")) {
            try {
                teachingAssistantControllerLocal.changePassword(currentPassword, newPassword, ta.getId());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Password is changed successfully", null));
            } catch (TANotFoundException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
            } catch (PasswordChangeException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
            }
        } else if (getUserType().equals("admin")) {
            try {
                administratorControllerLocal.changePassword(currentPassword, newPassword, admin.getId());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Password is changed successfully", null));
            } catch (AdminNotFoundException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
            } catch (PasswordChangeException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
            }
        }
    }

    public void setChoosePremium(Boolean choosePremium) {
        this.choosePremium = choosePremium;
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("choosePremium", choosePremium);
    }

    public void checkboxListener() {
        System.err.println("********* choose premium: " + choosePremium);
    }

    public String getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * @return the student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * @param student the student to set
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * @return the admin
     */
    public Administrator getAdmin() {
        return admin;
    }

    /**
     * @param admin the admin to set
     */
    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }

    /**
     * @return the lecturer
     */
    public Lecturer getLecturer() {
        return lecturer;
    }

    /**
     * @param lecturer the lecturer to set
     */
    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    /**
     * @return the ta
     */
    public TeachingAssistant getTa() {
        return ta;
    }

    /**
     * @param ta the ta to set
     */
    public void setTa(TeachingAssistant ta) {
        this.ta = ta;
    }

    /**
     * @return the currentPassword
     */
    public String getCurrentPassword() {
        return currentPassword;
    }

    /**
     * @param currentPassword the currentPassword to set
     */
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    /**
     * @return the newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * @param newPassword the newPassword to set
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * @return the isPremium
     */
    public Boolean getIsPremium() {
        return isPremium;
    }

    /**
     * @param isPremium the isPremium to set
     */
    public void setIsPremium(Boolean isPremium) {
        this.isPremium = isPremium;
    }

    /**
     * @return the choosePremium
     */
    public Boolean getChoosePremium() {
        return choosePremium;
    }
}
