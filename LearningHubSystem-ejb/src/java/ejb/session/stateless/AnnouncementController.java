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
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.AnnouncementExistException;
import util.exception.AnnouncementNotFoundException;
import util.exception.LecturerNotFoundException;
import util.exception.ModuleNotFoundException;

/**
 *
 * @author mango
 */
@Stateless
@Local(AnnouncementControllerLocal.class)

public class AnnouncementController implements AnnouncementControllerLocal {

    @EJB
    private ModuleControllerLocal moduleController;

    @EJB
    private LecturerControllerLocal lecturerController;

    @PersistenceContext(unitName = "LearningHubSystem-ejbPU")
    private EntityManager em;

    @Override
    public List<Announcement> retrieveAllAnnouncement() {
        Query query = em.createQuery("SELECT a FROM Announcement a");
        return (List<Announcement>) query.getResultList();

    }

    @Override
    public Announcement createNewAnnouncement(Announcement acm, Lecturer lec, Module mod) throws AnnouncementExistException {
        Long lecturerId = lec.getId();
        Long moduleId = mod.getId();
        Lecturer lecturer;
        Module module;
        try {
            lecturer = lecturerController.retrieveLecturerById(lecturerId);
            module = moduleController.retrieveModuleById(moduleId);
            List<Announcement> announcements = retrieveAllAnnouncement();
            for (Announcement announcement : announcements) {
                if (announcement.getName().equals(acm.getName())) {
                    throw new AnnouncementExistException("Announcement Already Exist.\n");
                }
            }
            acm.setLecturer(lecturer);
            System.err.println("!!!!!!!!!!!!!!!");
            System.err.println(lecturer);
            acm.setModule(module);
            lecturer.getAnnouncements().add(acm);
            module.getAnnouncements().add(acm);
            em.persist(acm);
            em.flush();
            em.merge(lecturer);
            em.merge(module);

        } catch (LecturerNotFoundException | ModuleNotFoundException ex) {
            ex.getMessage();
        }
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
            throw new AnnouncementNotFoundException("Annoucement " + name + " not found");
        }
    }

    @Override
    public List<Announcement> retrieveAnnouncementsByModule(Module mod) throws AnnouncementNotFoundException {
        Query query = em.createQuery("SELECT a FROM Announcement a WHERE a.module:=inModule");
        query.setParameter("inModule", mod);
        List<Announcement> acms = (List<Announcement>) query.getResultList();

        if (acms != null) {
            return acms;
        } else {
            throw new AnnouncementNotFoundException("No Announcement released under the module");
        }
    }

    @Override
    public List<Announcement> retrieveAnnouncementsByDate(Date date) throws AnnouncementNotFoundException {
        Query query = em.createQuery("SELECT a FROM Announcement a WHERE a.date:=inDate");
        query.setParameter("inDate", date);
        List<Announcement> acms = (List<Announcement>) query.getResultList();

        if (acms != null) {
            return acms;
        } else {
            throw new AnnouncementNotFoundException("No Announcement released on the date");
        }
    }

    public void deleteAnnouncement(Announcement acm) {
        em.remove(acm);
    }

    public void persist(Announcement acm) {
        em.persist(acm);
    }
}
