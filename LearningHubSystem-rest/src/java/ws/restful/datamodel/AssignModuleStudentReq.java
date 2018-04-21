/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Samango
 */
@XmlRootElement
@XmlType(name = "assignModuleStudentReq", propOrder = {
    "moduleId",
    "studentId"
})
public class AssignModuleStudentReq {
    private Long moduleId;
    private Long studentId;

    public AssignModuleStudentReq() {
    }

    public AssignModuleStudentReq(Long moduleId, Long studentId) {
        this.moduleId = moduleId;
        this.studentId = studentId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    
    
}
