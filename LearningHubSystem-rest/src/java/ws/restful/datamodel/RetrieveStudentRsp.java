/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Administrator;
import entity.Student;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author lxy
 */

@XmlType(name = "retrieveStudentRsp", propOrder = {
    "student"
})
public class RetrieveStudentRsp {
    private Student student;

    public RetrieveStudentRsp() {
    }
    
    

    public RetrieveStudentRsp(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    
    
}
