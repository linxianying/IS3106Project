/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Lecturer;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author lin
 */
@ManagedBean
@RequestScoped
public class lecturerModuleManagedBean {

    /**
     * Creates a new instance of lecturerModuleManagedBean
     */
    private Lecturer lecture;
    private String username;
    
    public lecturerModuleManagedBean() {
    }

    public Lecturer getLecture() {
        return lecture;
    }

    public void setLecture(Lecturer lecture) {
        this.lecture = lecture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
