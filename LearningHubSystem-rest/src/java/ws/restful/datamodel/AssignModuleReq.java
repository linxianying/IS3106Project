/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Lecturer;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Samango
 */
@XmlRootElement
@XmlType(name = "assignModuleReq", propOrder = {
    "moduleId",
    "lecturerId"
})
public class AssignModuleReq {
    private Long moduleId;
    private Long lecturerId;

    public AssignModuleReq() {
    }

    public AssignModuleReq(Long moduleId, Long lecturerId) {
        this.moduleId = moduleId;
        this.lecturerId = lecturerId;
    }
    
    
    
    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Long lecturerId) {
        this.lecturerId = lecturerId;
    }
    
    
}
