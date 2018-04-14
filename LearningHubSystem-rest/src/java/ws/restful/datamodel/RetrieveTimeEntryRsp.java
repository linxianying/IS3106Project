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
@XmlType(name = "retrieveTimeEntryRsp", propOrder = {
    "timeEntry"
})
public class RetrieveTimeEntryRsp {
    private TimeEntry timeEntry;

    public RetrieveTimeEntryRsp() {
    }

    public RetrieveTimeEntryRsp(TimeEntry timeEntry) {
        this.timeEntry = timeEntry;
    }

    public TimeEntry getTimeEntry() {
        return timeEntry;
    }

    public void setTimeEntry(TimeEntry timeEntry) {
        this.timeEntry = timeEntry;
    }

    
    
   
    
    
    
}
