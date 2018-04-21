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
@XmlType(name = "dropModuleStudentReq", propOrder = {
    "studentId",
    "moduleId"
})
public class DropModuleStudentReq {
    private Long studentId;
    private Long moduleId;

    public DropModuleStudentReq() {
    }
    
    

    public DropModuleStudentReq(Long studentId, Long moduleId) {
        this.studentId = studentId;
        this.moduleId = moduleId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }
    
    
    
    
}
