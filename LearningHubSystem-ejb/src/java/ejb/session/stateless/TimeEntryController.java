/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;



import entity.Lecturer;
import entity.TimeEntry;
import java.util.List;
import entity.Student;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.GeneralException;
import util.exception.LecturerNotFoundException;
import util.exception.StudentNotFoundException;
import util.exception.TimeEntryExistException;
import util.exception.TimeEntryNotFoundException;

/**
 *
 * @author mango
 */
@Stateless
@Local(TimeEntryControllerLocal.class)
public class TimeEntryController implements TimeEntryControllerLocal {

    @PersistenceContext(unitName = "LearningHubSystem-ejbPU")
    private EntityManager em;
    

    TimeEntry t;
    
    @Override
    public TimeEntry createTimeEntry(TimeEntry timeEntry, Student student) throws TimeEntryExistException,GeneralException {
        TimeEntry te = new TimeEntry();
        te.setDetails(timeEntry.getDetails());
        te.setFromDate(timeEntry.getFromDate());
        te.setToDate(timeEntry.getToDate());
        te.setTitle(timeEntry.getTitle());
        em.persist(te);
        em.flush(); 
        student.getTimeEntries().add(te);
        em.merge(student);
        em.flush();
        return te;
    }

    @Override
    public TimeEntry createTimeEntry(TimeEntry timeEntry, Lecturer lecturer) throws TimeEntryExistException,GeneralException {
        TimeEntry te = new TimeEntry();
        te.setDetails(timeEntry.getDetails());
        te.setFromDate(timeEntry.getFromDate());
        te.setToDate(timeEntry.getToDate());
        te.setTitle(timeEntry.getTitle());
        em.persist(te);
        em.flush(); 
        lecturer.getTimeEntries().add(te);
        em.merge(lecturer);
        em.flush();
        return te;
    }
    
    @Override
    public TimeEntry retrieveTimeEntryById(Long id) throws TimeEntryNotFoundException{
        t = null;
        try{
            System.out.println(id);
            t = em.find(TimeEntry.class, id);
            
        }
        catch(NoResultException e){
            throw new TimeEntryNotFoundException("TimeEntry with specified ID not found");
        }
        catch(Exception e) {
            e.printStackTrace(); 
        }
        return t;
    }
    
    @Override
    public List<TimeEntry> retrieveAllTimeEntrys() {
        Query query = em.createQuery("SELECT s FROM TimeEntry s");
        return (List<TimeEntry>) query.getResultList();
        
    }
    
    @Override
    //for students
    public List<TimeEntry> retrieveTimeEntrysByName(String username){
        try {
            Student s = retrieveStudentByUsername(username);
            if(s!=null){
                return s.getTimeEntries();
            }
        } catch (StudentNotFoundException ex) {
            Logger.getLogger(TimeEntryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
    
    @Override
    //for students
    public List<TimeEntry> retrieveTimeEntrysByLecturerName(String username){
        try {
            Lecturer s = retrieveLecturerByUsername(username);
            if(s!=null){
                return s.getTimeEntries();
            }
        } catch (LecturerNotFoundException ex) {
            Logger.getLogger(TimeEntryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
    
    public Lecturer retrieveLecturerByUsername(String username) throws LecturerNotFoundException {
        Lecturer lecturer = null;
        try{
            Query q = em.createQuery("SELECT s FROM Lecturer s WHERE s.username=:username");
            q.setParameter("username", username);
            lecturer = (Lecturer) q.getSingleResult();
            System.out.println("Lecturer " + username + " found.");
        }
        catch(NoResultException e){
            System.out.println("Lecturer " + username + " does not exist.");
            lecturer = null;
            throw new LecturerNotFoundException("Lecturer with specified ID not found");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return lecturer;
    }
    
    public Student retrieveStudentByUsername(String username) throws StudentNotFoundException {
        Student student = null;
        try {
            Query q = em.createQuery("SELECT s FROM Student s WHERE s.username=:username");
            q.setParameter("username", username);
            student = (Student) q.getSingleResult();
            System.out.println("Student " + username + " found.");
        } catch (NoResultException e) {
            throw new StudentNotFoundException("Student with specified ID not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }
    
    @Override
    public void updateTimeEntry(TimeEntry timeEntry, String title, String from, String to, String details){
        if(timeEntry!=null){
            if(title!=null){
                timeEntry.setTitle(title);
            }
            if(from!=null){
                timeEntry.setFromDate(from);
            }
            if(to!=null){
                timeEntry.setToDate(to);
            }
            if(details!=null){
                timeEntry.setDetails(details);
            }
            System.out.println("updateTimeEntry: "+title+"(title) " + from + "(from) "
            + to + "(to) "+ details + "(details) ");
        }
        else{
            System.out.println("TimeEntry is null! Updation failed");
        }
    }
    
    @Override
    public void updateTimeEntry(TimeEntry timeEntry) throws TimeEntryNotFoundException{
        t = null;
        t = retrieveTimeEntryById(timeEntry.getId());
        if(t!=null){
            t.setDetails(timeEntry.getDetails());
            t.setTitle(timeEntry.getTitle());
            t.setToDate(timeEntry.getToDate());
            t.setFromDate(timeEntry.getFromDate());
        }
    }
    
    @Override
    public boolean deleteTimeEntry(Long id, Student student){
        t = null;
        try {
            t = retrieveTimeEntryById(id);
            if(t!=null)
                student.getTimeEntries().remove(t);
            em.merge(student);
            em.remove(t);
            em.flush();
            
        } catch (TimeEntryNotFoundException ex) {
            Logger.getLogger(TimeEntryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }


    @Override
    public boolean deleteTimeEntry(Long id, Lecturer lecturer){
        t = null;
        try {
            t = retrieveTimeEntryById(id);
            if(t!=null)
                lecturer.getTimeEntries().remove(t);
            em.merge(lecturer);
            em.remove(t);
            em.flush();
            
        } catch (TimeEntryNotFoundException ex) {
            Logger.getLogger(TimeEntryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
}
