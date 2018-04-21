/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Student;
import java.util.List;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Samango
 */

@XmlType(name = "retrieveStudentsRsp", propOrder = {
    "students"
})
public class RetrieveStudentsRsp {
    private List<Student> students;

    public RetrieveStudentsRsp() {
    }

    
    public RetrieveStudentsRsp(List<Student> students) {
        this.students = students;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    
    
    
}
