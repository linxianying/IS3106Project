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
 * @author lxy
 */
@XmlType(name = "studentLoginRsp", propOrder = {
    "student"
})
public class StudentLoginRsp {
    
    private Student student;

    public StudentLoginRsp() {
    }

    public StudentLoginRsp(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    
    
    
}
