/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.TimeEntry;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author wyh
 */
@XmlRootElement
@XmlType(name = "updateTimeEntryRsp", propOrder = {
    "timeEntry"
})
public class UpdateTimeEntryRsp {
    private TimeEntry timeEntry;

    public UpdateTimeEntryRsp() {
    }

    public UpdateTimeEntryRsp(TimeEntry timeEntry) {
        this.timeEntry = timeEntry;
    }

    /**
     * @return the timeEntry
     */
    public TimeEntry getTimeEntry() {
        return timeEntry;
    }

    /**
     * @param timeEntry the lecturer to set
     */
    public void setTimeEntry(TimeEntry timeEntry) {
        this.timeEntry = timeEntry;
    }
    
    
}
