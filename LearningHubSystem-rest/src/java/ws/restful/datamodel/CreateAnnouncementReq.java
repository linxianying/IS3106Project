/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Announcement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author wyh
 */

@XmlRootElement
@XmlType(name = "createAnnouncementReq", propOrder = {
    "announcement",
    "moduleId",
    "username"
})
public class CreateAnnouncementReq {
    
    private Announcement announcement;
    private Long moduleId;
    private String username;

    public CreateAnnouncementReq() {
    }

    public CreateAnnouncementReq(Announcement announcement) {
        this.announcement = announcement;
    }

    /**
     * @return the announcement
     */
    public Announcement getAnnouncement() {
        return announcement;
    }

    /**
     * @param announcement the announcement to set
     */
    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }


    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the moduleId
     */
    public Long getModuleId() {
        return moduleId;
    }

    /**
     * @param moduleId the moduleId to set
     */
    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }
    
    
    
}
