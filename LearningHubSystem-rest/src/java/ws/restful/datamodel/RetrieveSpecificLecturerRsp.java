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
 * @author mango
 */
@XmlType(name = "retrieveSpecificLecturerRsp", propOrder = {
    "lecturer"
})
public class RetrieveSpecificLecturerRsp {
    private Lecturer lecturer;

    public RetrieveSpecificLecturerRsp() {
    }

    public RetrieveSpecificLecturerRsp(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    
    public Lecturer getLecturer() {
        return lecturer;
    }

    /**
     * @param module the module to set
     */
    public void setModule(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

   
    
    
    
}
