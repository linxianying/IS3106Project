/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.TeachingAssistant;
import java.util.List;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Samango
 */
@XmlType(name = "retrieveTAsRsp", propOrder = {
    "tas"
})
public class RetrieveTAsRsp {
    private List<TeachingAssistant> tas;

    public RetrieveTAsRsp() {
    }

    public RetrieveTAsRsp(List<TeachingAssistant> tas) {
        this.tas = tas;
    }

    public List<TeachingAssistant> getTas() {
        return tas;
    }

    public void setTas(List<TeachingAssistant> tas) {
        this.tas = tas;
    }
    
    
}
