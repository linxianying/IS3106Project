/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.LecturerControllerLocal;
import ejb.session.stateless.TeachingAssistantControllerLocal;
import entity.Lecturer;
import entity.TeachingAssistant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author mango
 */
@Named(value = "facilitatorManagedBean")
@ViewScoped
public class facilitatorManagedBean implements Serializable {

    @EJB(name = "TeachingAssistantControllerLocal")
    private TeachingAssistantControllerLocal teachingAssistantControllerLocal;

    @EJB(name = "LecturerControllerLocal")
    private LecturerControllerLocal lecturerControllerLocal;

    private Lecturer selectedLecturer;
    private TeachingAssistant selectedTA;
    private List<Lecturer> lecturers;
    private List<TeachingAssistant> TAs;

    public facilitatorManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        lecturers = lecturerControllerLocal.retrieveAllLecturers();
        TAs = teachingAssistantControllerLocal.retrieveAllTAs();
    }

    public Lecturer getSelectedLecturer() {
        return selectedLecturer;
    }

    public void setSelectedLecturer(Lecturer selectedLecturer) {
        this.selectedLecturer = selectedLecturer;
    }

    public TeachingAssistant getSelectedTA() {
        return selectedTA;
    }

    public void setSelectedTA(TeachingAssistant selectedTa) {
        this.selectedTA = selectedTa;
    }

    public List<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setLecturers(List<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    public List<TeachingAssistant> getTAs() {
        return TAs;
    }

    public void setTAs(List<TeachingAssistant> TAs) {
        this.TAs = TAs;
    }

}
