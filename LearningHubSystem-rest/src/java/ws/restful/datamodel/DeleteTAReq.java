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
 * @author Samango
 */
@XmlRootElement
@XmlType(name = "deleteTAReq", propOrder = {
    "ta"
})
public class DeleteTAReq {
    private TeachingAssistant ta;

    public DeleteTAReq(TeachingAssistant ta) {
        this.ta = ta;
    }

    public TeachingAssistant getTa() {
        return ta;
    }

    public void setTa(TeachingAssistant ta) {
        this.ta = ta;
    }
    
    
}
