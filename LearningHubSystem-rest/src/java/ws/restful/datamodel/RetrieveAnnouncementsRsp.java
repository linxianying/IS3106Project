/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Announcement;
import java.util.List;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author wyh
 */

@XmlType(name = "retrieveAnnoucementsRsp", propOrder = {
    "annoucements"
})
public class RetrieveAnnouncementsRsp {
    private List<Announcement> announcements;

    public RetrieveAnnouncementsRsp() {
    }

    public RetrieveAnnouncementsRsp(List<Announcement> announcements) {
        this.announcements = announcements;
    }

    /**
     * @return the announcements
     */
    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    /**
     * @param announcements the announcements to set
     */
    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }
    
}
