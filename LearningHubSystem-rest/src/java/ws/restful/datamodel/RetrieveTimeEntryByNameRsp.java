/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.TimeEntry;
import java.util.Collection;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author lxy
 */
@XmlType(name = "retrieveTimeEntryByNameRsp", propOrder = {
    "timeEntry"
})
public class RetrieveTimeEntryByNameRsp {
    private Collection<TimeEntry> timeEntrys;

    public RetrieveTimeEntryByNameRsp() {
    }

    public RetrieveTimeEntryByNameRsp(Collection<TimeEntry> timeEntrys) {
        this.timeEntrys = timeEntrys;
    }

    public Collection<TimeEntry> getTimeEntrys() {
        return timeEntrys;
    }

    public void setTimeEntrys(Collection<TimeEntry> timeEntrys) {
        this.timeEntrys = timeEntrys;
    }

    
   

   
    
    
    
}
