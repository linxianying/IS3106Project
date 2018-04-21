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
@XmlType(name = "assignModuleTAReq", propOrder = {
    "moduleId",
    "taId"
})
public class AssignModuleTAReq {
    private Long moduleId;
    private Long taId;

    public AssignModuleTAReq() {
    }
    
    

    public AssignModuleTAReq(Long moduleId, Long taId) {
        this.moduleId = moduleId;
        this.taId = taId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getTaId() {
        return taId;
    }

    public void setTaId(Long taId) {
        this.taId = taId;
    }
    
    
}
