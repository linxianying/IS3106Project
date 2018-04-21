/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.TeachingAssistant;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author wyh
 */
@XmlRootElement
@XmlType(name = "updateTaReq", propOrder = {
    "ta"
})
public class UpdateTaReq {
    private TeachingAssistant ta;

    public UpdateTaReq() {
    }

    public UpdateTaReq(TeachingAssistant ta) {
        this.ta = ta;
    }

    /**
     * @return the ta
     */
    public TeachingAssistant getTa() {
        return ta;
    }

    /**
     * @param ta the ta to set
     */
    public void setTa(TeachingAssistant ta) {
        this.ta = ta;
    }
    
    
}
