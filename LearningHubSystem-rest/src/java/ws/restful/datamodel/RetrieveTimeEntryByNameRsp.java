/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.TimeEntry;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author lxy
 */
@XmlType(name = "retrieveTimeEntryByNameRsp", propOrder = {
    "timeEntrys"
})
public class RetrieveTimeEntryByNameRsp {
    private List<TimeEntry> timeEntrys;

    public RetrieveTimeEntryByNameRsp() {
    }

    public RetrieveTimeEntryByNameRsp(List<TimeEntry> timeEntrys) {
        this.timeEntrys = timeEntrys;
    }

    public List<TimeEntry> getTimeEntrys() {
        return timeEntrys;
    }

    public void setTimeEntrys(List<TimeEntry> timeEntrys) {
        this.timeEntrys = timeEntrys;
    }

    
   

   
    
    
    
}
