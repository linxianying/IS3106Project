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
    private String name;
    private String username;
    private String email;
    private String telephone;
    private String faculty;
    private String department;
    private Boolean isPremium;

    public settingManagedBean() {

    }

    @PostConstruct
    public void postConstruct() {
        context = FacesContext.getCurrentInstance();
        session = (HttpSession) context.getExternalContext().getSession(true);

        setUserType((String) session.getAttribute("role"));
        if (getUserType().equals("student")) {
            setStudent((Student) session.getAttribute("currentStudent"));
        } else if (getUserType().equals("lecturer")) {
            setLecturer((Lecturer) session.getAttribute("currentLecturer"));
        } else if (getUserType().equals("TA")) {
            setTa((TeachingAssistant) session.getAttribute("currentTA"));
        } else if (getUserType().equals("admin")) {
            setAdmin((Administrator) session.getAttribute("currentAdmin"));
        }

    }

    public String getUsername() {
        if (getUserType().equals("student")) {
            return student.getUsername();
        } else if (getUserType().equals("lecturer")) {
            return lecturer.getUsername();
        } else if (getUserType().equals("TA")) {
            return ta.getUsername();
        } else if (getUserType().equals("admin")) {
            return admin.getUsername();
        } else {
            System.err.println("Error");
            return null;
        }
    }

    public void setUsername(String username) {
        if (getUserType().equals("student")) {
            student.setUsername(username);
        } else if (getUserType().equals("lecturer")) {
            lecturer.setUsername(username);
        } else if (getUserType().equals("TA")) {
            ta.setUsername(username);
        } else if (getUserType().equals("admin")) {
            admin.setUsername(username);
        }
    }

    public String getTelephone() {
        if (getUserType().equals("student")) {
            return student.getTelephone();
        } else if (getUserType().equals("lecturer")) {
            return lecturer.getTelephone();
        } else if (getUserType().equals("TA")) {
            return ta.getTelephone();
        } else if (getUserType().equals("admin")) {
            return admin.getTelephone();
        } else {
            System.err.println("Error");
            return null;
        }
    }

    public void setTelephone(String telephone) {
        if (getUserType().equals("student")) {
            student.setTelephone(telephone);
        } else if (getUserType().equals("lecturer")) {
            lecturer.setTelephone(telephone);
        } else if (getUserType().equals("TA")) {
            ta.setTelephone(telephone);
        } else if (getUserType().equals("admin")) {
            admin.setTelephone(telephone);
        }
    }

    public String getName() {
        if (getUserType().equals("student")) {
            return student.getName();
        } else if (getUserType().equals("lecturer")) {
            return lecturer.getName();
        } else if (getUserType().equals("TA")) {
            return ta.getName();
        } else if (getUserType().equals("admin")) {
            return admin.getName();
        } else {
            System.err.println("Error");
            return null;
        }
    }

    public void setName(String name) {
        if (getUserType().equals("student")) {
            student.setName(name);
        } else if (getUserType().equals("lecturer")) {
            lecturer.setName(name);
        } else if (getUserType().equals("TA")) {
            ta.setName(name);
        } else if (getUserType().equals("admin")) {
            admin.setName(name);
        }
    }

    public String getEmail() {
        if (getUserType().equals("student")) {
            return student.getEmail();
        } else if (getUserType().equals("lecturer")) {
            return lecturer.getEmail();
        } else if (getUserType().equals("TA")) {
            return ta.getEmail();
        } else if (getUserType().equals("admin")) {
            return admin.getEmail();
        } else {
            System.err.println("Error");
            return null;
        }
    }

    public void setEmail(String email) {
        if (getUserType().equals("student")) {
            student.setEmail(email);
        } else if (getUserType().equals("lecturer")) {
            lecturer.setEmail(email);
        } else if (getUserType().equals("TA")) {
            ta.setEmail(email);
        } else if (getUserType().equals("admin")) {
            admin.setEmail(email);
        }
    }

    public String getFaculty() {
        if (getUserType().equals("student")) {
            return student.getFaculty();
        } else if (getUserType().equals("lecturer")) {
            return lecturer.getFaculty();
        } else if (getUserType().equals("TA")) {
            return ta.getFaculty();
        } else {
            System.err.println("Error");
            return null;
        }
    }

    public void setFaculty(String faculty) {
        if (getUserType().equals("student")) {
            student.setFaculty(faculty);
        } else if (getUserType().equals("lecturer")) {
            lecturer.setFaculty(faculty);
        } else if (getUserType().equals("TA")) {
            ta.setFaculty(faculty);
        } else {
            System.err.println("Error");
        }
    }

    public String getDepartment() {
        if (getUserType().equals("student")) {
            return student.getDepartment();
        } else if (getUserType().equals("lecturer")) {
            return lecturer.getDepartment();
        } else if (getUserType().equals("TA")) {
            return ta.getDepartment();
        } else {
            System.err.println("Error");
            return null;
        }
    }

    public void setDepartment(String department) {
        if (getUserType().equals("student")) {
            student.setDepartment(department);
        } else if (getUserType().equals("lecturer")) {
            lecturer.setDepartment(department);
        } else if (getUserType().equals("TA")) {
            ta.setDepartment(department);
        } else {
            System.err.println("Error");
        }
    }

    public Boolean getIsPremium() {
        if (getUserType().equals("student")) {
            return student.getIsPremium();
        } else if (getUserType().equals("lecturer")) {
            return lecturer.getIsPremium();
        } else if (getUserType().equals("TA")) {
            return ta.getIsPremium();
        } else if (getUserType().equals("admin")) {
            return admin.getIsPremium();
        } else {
            System.err.println("Error");
            return null;
        }
    }

    public void update(ActionEvent event) {
        if (getUserType().equals("student")) {
            try {
                studentControllerLocal.updateStudent(student);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile updated successfully", null));
            } catch (StudentNotFoundException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
            }
        } else if (getUserType().equals("lecturer")) {
            try {
                lecturerControllerLocal.updateLecturer(lecturer);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile updated successfully", null));
            } catch (LecturerNotFoundException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
            }
        } else if (getUserType().equals("TA")) {
            try {
                teachingAssistantControllerLocal.updateTA(ta);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile updated successfully", null));
            } catch (TANotFoundException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
            }
        } else if (getUserType().equals("admin")) {
            try {
                administratorControllerLocal.updateAdmin(admin);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile updated successfully", null));
            } catch (AdminNotFoundException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
            }
        }

    }

    public void setIsPremium(Boolean isPremium) {
        this.isPremium = isPremium;
    }

    /**
     * @return the userType
     */
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

}
