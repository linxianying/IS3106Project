/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.TimeEntry;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author lxy
 */

@XmlType(name = "createTimeEntryRsp", propOrder = {
    "id"
})
public class CreateTimeEntryRsp {
    private Long id;

    public CreateTimeEntryRsp() {
    }
    
    public CreateTimeEntryRsp(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    
    
}
