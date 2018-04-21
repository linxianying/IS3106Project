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
@XmlType(name = "dropModuleTAReq", propOrder = {
    "taId",
    "moduleId"
})
public class DropModuleTAReq {
    private Long taId;
    private Long moduleId;

    public DropModuleTAReq() {
    }

    public DropModuleTAReq(Long taId, Long moduleId) {
        this.taId = taId;
        this.moduleId = moduleId;
    }

    public Long getTaId() {
        return taId;
    }

    public void setTaId(Long taId) {
        this.taId = taId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }
    
    
    
}
