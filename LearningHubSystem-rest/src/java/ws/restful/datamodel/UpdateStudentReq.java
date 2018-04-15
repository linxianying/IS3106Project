/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Student;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Samango
 */

@XmlRootElement
@XmlType(name = "updateStudentReq", propOrder = {
    "student"
})
public class UpdateStudentReq {

    private Student student;
    
    public UpdateStudentReq() {
    }

    public UpdateStudentReq(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    
    
}
