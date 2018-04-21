/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Module;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Samango
 */
@XmlType(name = "assignModuleRsp", propOrder = {
    "module"
})
public class AssignModuleRsp {
    private Module module;

    public AssignModuleRsp(Module module) {
        this.module = module;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
    
}
