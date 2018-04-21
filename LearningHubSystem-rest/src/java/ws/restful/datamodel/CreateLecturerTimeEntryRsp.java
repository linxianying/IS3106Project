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
public class CreateLecturerTimeEntryRsp {
    
    private Long id;

    public CreateLecturerTimeEntryRsp() {
    }

    public CreateLecturerTimeEntryRsp(Long id) {
        System.out.println("test CreateTimeEntryRsp:" + id);
        this.id = id;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    


    
    
}
