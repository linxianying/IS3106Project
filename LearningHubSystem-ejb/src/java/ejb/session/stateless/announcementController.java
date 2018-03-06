/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import javax.ejb.Stateless;
import entity.Announcement;
import entity.Lecturer;
import entity.Module;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.Local;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.AnnouncementExistException;
import util.exception.AnnouncementNotFoundException;
/**
 *
 * @author mango
 */
@Local (announcementController.class)
@Stateless
public class announcementController implements announcementControllerLocal {

    @PersistenceContext(unitName = "LearningHubSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    
    @Override
    public ArrayList<Announcement> retrieveAllAnnouncement() {
        Query query = em.createQuery("SELECT a FROM Announcement a");
        return (ArrayList<Announcement>) query.getResultList();
        
    }
    
    
    
    @Override
    public Announcement createNewAnnouncement(Announcement acm, Lecturer lec, Module mod) throws AnnouncementExistException {

        ArrayList<Announcement> announcements = retrieveAllAnnouncement();
        for (Announcement annoucement: announcements) {
            if (annoucement.getName().equals(acm.getName()))
            {
                throw new AnnouncementExistException ("Announcement Already Exist.\n");
            }
        }
        acm.setLecturer(lec);
        acm.setModule(mod);
        lec.getAnnouncements().add(acm);
        mod.getAnnouncements().add(acm);
        em.persist(acm);
        em.persist(lec);
        em.persist(mod);
        em.flush();
        
        return acm;
    }
    
   
    
    
    @Override
    public Announcement retrieveAnnoucementById(Long Id) throws AnnouncementNotFoundException {
        Announcement acm = em.find(Announcement.class, Id);
        
        if (acm != null) {
            return acm;
        } else {
            throw new AnnouncementNotFoundException("Announcement with specified ID not found");
        }
    }
    
    
    
    
    @Override
    public Announcement retrieveAnnouncementByName(String name) throws AnnouncementNotFoundException {
        Query query = em.createQuery("SELECT a FROM Announcement a WHERE a.name=:inName");
        query.setParameter("inName", name);
        Announcement acm = (Announcement) query.getSingleResult();
        
        if (acm != null) {
            return acm;
        } else {
            throw new AnnouncementNotFoundException("Annoucement " +name+" not found");
        }
    }
    
    
    
    
    @Override
    public ArrayList<Announcement> retrieveAnnouncementsByModule(Module mod) throws AnnouncementNotFoundException {
        Query query = em.createQuery("SELECT a FROM Announcement a WHERE a.module:=inModule");
        query.setParameter("inModule", mod);
        ArrayList<Announcement> acms = (ArrayList<Announcement>) query.getResultList();
        
        if (acms != null) {
            return acms;
        } else {
            throw new AnnouncementNotFoundException("No Announcement released under the module");
        }
    }
    
    @Override
    public ArrayList<Announcement> retrieveAnnouncementsByDate(Date date) throws AnnouncementNotFoundException {
        Query query = em.createQuery("SELECT a FROM Announcement a WHERE a.date:=inDate");
        query.setParameter("inDate", date);
        ArrayList<Announcement> acms = (ArrayList<Announcement>) query.getResultList();
        
        if (acms != null) {
            return acms;
        } else {
            throw new AnnouncementNotFoundException("No Announcement released on the date");
        }
    }
    
    public void deleteAnnouncement(Announcement acm){
        em.remove(acm);
    }

    public void persist(Announcement acm) {
        em.persist(acm);
    }
}
