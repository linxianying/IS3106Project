/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Module;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Samango
 */
@XmlRootElement
@XmlType(name = "updateModuleReq", propOrder = {
    "module"
})
public class UpdateModuleReq {
    private Module module;

    public UpdateModuleReq() {
    }

    public UpdateModuleReq(Module module) {
        this.module = module;
    }
    
    

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
    
    
}
