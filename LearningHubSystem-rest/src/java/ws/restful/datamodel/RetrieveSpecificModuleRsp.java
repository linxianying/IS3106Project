/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Module;
import java.util.List;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author wyh
 */
@XmlType(name = "retrieveSpecificModuleRsp", propOrder = {
    "module"
})
public class RetrieveSpecificModuleRsp {
    private Module module;

    public RetrieveSpecificModuleRsp() {
    }

    public RetrieveSpecificModuleRsp(Module module) {
        this.module = module;
    }

    
    public Module getModule() {
        return module;
    }

    /**
     * @param module the module to set
     */
    public void setModule(Module module) {
        this.module = module;
    }

   
    
    
    
}
