/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.TeachingAssistant;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author lxy
 */
@XmlType(name = "teachingAssistantLoginRsp", propOrder = {
    "teachingAssistant"
})
public class TeachingAssistantLoginRsp {
    
    private TeachingAssistant teachingAssistant;

    public TeachingAssistantLoginRsp() {
    }

    public TeachingAssistantLoginRsp(TeachingAssistant teachingAssistant) {
        this.teachingAssistant = teachingAssistant;
    }

    public TeachingAssistant getTeachingAssistant() {
        return teachingAssistant;
    }

    public void setTeachingAssistant(TeachingAssistant teachingAssistant) {
        this.teachingAssistant = teachingAssistant;
    }


    
    
    
}
