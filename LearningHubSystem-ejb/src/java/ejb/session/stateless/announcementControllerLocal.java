/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Announcement;
import entity.Lecturer;
import entity.Module;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.Local;
import util.exception.AnnouncementExistException;
import util.exception.AnnouncementNotFoundException;

/**
 *
 * @author mango
 */
@Local
public interface announcementControllerLocal {
    public ArrayList<Announcement> retrieveAllAnnouncement();
    
    public Announcement createNewAnnouncement(Announcement acm, Lecturer lec, Module mod) throws AnnouncementExistException;
    
    public Announcement retrieveAnnoucementById(Long Id) throws AnnouncementNotFoundException;
    
    public Announcement retrieveAnnouncementByName(String name) throws AnnouncementNotFoundException;
    
    public ArrayList<Announcement> retrieveAnnouncementsByModule(Module mod) throws AnnouncementNotFoundException;
    
    public ArrayList<Announcement> retrieveAnnouncementsByDate(Date date) throws AnnouncementNotFoundException;
}
