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
        student.getTimeEntries().add(timeEntry);
        em.merge(student);
        em.persist(timeEntry);
        em.flush(); 

        return timeEntry;
    }

    @Override
    public TimeEntry createTimeEntry(TimeEntry timeEntry, Lecturer lecturer) throws TimeEntryExistException,GeneralException {
        lecturer.getTimeEntries().add(timeEntry);
        em.merge(lecturer);
        em.persist(timeEntry);
        em.flush(); 

        return timeEntry;
    }
    
    @Override
    public TimeEntry retrieveTimeEntryById(Long id) throws TimeEntryNotFoundException{
        t = null;
        try{
            Query q = em.createQuery("SELECT s FROM TimeEntry s WHERE s.id=:id");
            q.setParameter("id", id);
            t = (TimeEntry) q.getSingleResult();
            System.out.println("TimeEntry " + id + " found.");
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
    public void updateTimeEntry(TimeEntry timeEntry,String title, String from, String to, String details){
        t.setTitle(title);
        t.setFromDate(from);
        t.setToDate(to);
        t.setDetails(details);
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
