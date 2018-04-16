/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Lecturer;
import java.util.List;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author mango
 */

@XmlType(name = "retrieveLecturersRsp", propOrder = {
    "lecturers"
})
public class RetrieveLecturersRsp {
    private List<Lecturer> lecturers;

    public RetrieveLecturersRsp() {
    }

    public RetrieveLecturersRsp(List<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }


    public List<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setLecturers(List<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }
    
}
