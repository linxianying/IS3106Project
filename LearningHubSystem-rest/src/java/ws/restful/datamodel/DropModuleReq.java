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
@XmlType(name = "dropModuleReq", propOrder = {
    "lecturerId",
    "moduleId"
})
public class DropModuleReq {
    private Long lecturerId;
    private Long moduleId;

    public DropModuleReq() {
    }

    public DropModuleReq(Long lecturerId, Long moduleId) {
        this.lecturerId = lecturerId;
        this.moduleId = moduleId;
    }

    public Long getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Long lecturerId) {
        this.lecturerId = lecturerId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }
    
    
}
