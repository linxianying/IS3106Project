/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Lecturer;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author wyh
 */
@XmlType(name = "updateLecturerRsp", propOrder = {
    "lecturer"
})
public class UpdateLecturerRsp {
    private Lecturer lecturer;

    public UpdateLecturerRsp() {
    }

    public UpdateLecturerRsp(Lecturer lecturer) {
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
