/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Announcement;
import entity.Lecturer;
import entity.Module;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import util.exception.AnnouncementExistException;
import util.exception.AnnouncementNotFoundException;

/**
 *
 * @author mango
 */

public interface AnnouncementControllerLocal {
    public List<Announcement> retrieveAllAnnouncement();
    
    public Announcement createNewAnnouncement(Announcement acm, Lecturer lec, Module mod) throws AnnouncementExistException;
    
    public Announcement retrieveAnnoucementById(Long Id) throws AnnouncementNotFoundException;
    
    public Announcement retrieveAnnouncementByName(String name) throws AnnouncementNotFoundException;
    
    public List<Announcement> retrieveAnnouncementsByModule(Module mod) throws AnnouncementNotFoundException;
    
    public List<Announcement> retrieveAnnouncementsByDate(Date date) throws AnnouncementNotFoundException;

    public Announcement createNewAnnouncement(Announcement acm, Lecturer lec, Long moduleId) throws AnnouncementExistException;

}
