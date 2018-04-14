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
 * @author wyh
 */
@XmlType(name = "classAndGroupsRsp", propOrder = {
    "students"
})
public class ClassAndGroupsRsp {
    private List<Student> students;

    public ClassAndGroupsRsp() {
    }

    public ClassAndGroupsRsp(List<Student> students) {
        this.students = students;
    }

    /**
     * @return the students
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * @param students the students to set
     */
    public void setStudents(List<Student> students) {
        this.students = students;
    }
    
    
    
}
