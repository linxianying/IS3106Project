/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;



import entity.TimeEntry;
import java.util.List;
import entity.Student;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.GeneralException;
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
        try{
            student.getTimeEntries().add(timeEntry);
            em.persist(student);
            em.persist(timeEntry);
            em.flush(); 
            em.refresh(timeEntry); 

            return timeEntry;
        } catch (PersistenceException ex) {
            if (ex.getCause() != null
                    && ex.getCause().getCause() != null
                    && ex.getCause().getCause().getClass().getSimpleName().equals("MySQLIntegrityConstraintViolationException")) {
                throw new TimeEntryExistException("TimeEntry Already Exist.\n");
            } else {
                throw new GeneralException("An unexpected error has occurred: " + ex.getMessage());
            }
        }
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
    
    

    
    
}
