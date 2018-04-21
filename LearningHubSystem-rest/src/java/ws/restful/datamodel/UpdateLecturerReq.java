/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Lecturer;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author wyh
 */
@XmlRootElement
@XmlType(name = "UpdateLecturerReq", propOrder = {
    "lecturer"
})
public class UpdateLecturerReq {
    private Lecturer lecturer;

    public UpdateLecturerReq() {
    }

    public UpdateLecturerReq(Lecturer lecturer) {
        this.lecturer = lecturer;
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
    
    
}
