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
@XmlType(name = "retrieveModulesRsp", propOrder = {
    "modules"
})
public class RetrieveModulesRsp {
    private List<Module> modules;

    public RetrieveModulesRsp() {
    }

    public RetrieveModulesRsp(List<Module> modules) {
        this.modules = modules;
    }

    /**
     * @return the modules
     */
    public List<Module> getModules() {
        return modules;
    }

    /**
     * @param modules the modules to set
     */
    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
    
    
    
}
