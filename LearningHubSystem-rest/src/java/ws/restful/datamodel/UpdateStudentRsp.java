/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Student;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author wyh
 */
@XmlType(name = "updateStudentRsp", propOrder = {
    "student"
})
public class UpdateStudentRsp {
    
    private Student student;

    public UpdateStudentRsp() {
    }

    public UpdateStudentRsp(Student student) {
        this.student = student;
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
    
    
    
}
