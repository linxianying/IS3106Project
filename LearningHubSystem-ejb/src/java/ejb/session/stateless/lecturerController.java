/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Lecturer;
import java.util.ArrayList;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.InvalidLoginCredentialException;
import util.exception.LecturerExistException;
import util.exception.LecturerNotFoundException;

/**
 *
 * @author mango
 */
@Stateless
@Local(lecturerControllerLocal.class)

public class lecturerController implements lecturerControllerLocal {

    @PersistenceContext(unitName = "LearningHubSystem-ejbPU")
    private EntityManager em;

    
    
    @Override
    public ArrayList<Lecturer> retrieveAllLecturers() {
        Query query = em.createQuery("SELECT l FROM Lecturer l");
        return (ArrayList<Lecturer>) query.getResultList();
        
    }
    
    
    
    @Override
    public Lecturer createNewLecturer(Lecturer lecturer) throws LecturerExistException {

        ArrayList<Lecturer> lecturerList = retrieveAllLecturers();
        for (Lecturer lec : lecturerList) {
            if (lecturer.getUsername().equals(lec.getUsername()))
            {
                throw new LecturerExistException("Lecturer Account Already Exist.\n");
            }
        }
        em.persist(lecturer);
        em.flush();
        
        return lecturer;
    }
    
   
    
    
    @Override
    public Lecturer retrieveLecturerById(Long lecturerId) throws LecturerNotFoundException {
        Lecturer lec = em.find(Lecturer.class, lecturerId);
        
        if (lec != null) {
            return lec;
        } else {
            throw new LecturerNotFoundException("Lecturer with specified ID not found");
        }
    }
    
    
    
    
    @Override
    public Lecturer retrieveLecturerByUsername(String username) throws LecturerNotFoundException {
        Query query = em.createQuery("SELECT l FROM Lecturer l WHERE l.username=:inUsername");
        query.setParameter("inUsername", username);
        Lecturer lec = (Lecturer) query.getSingleResult();
        
        if (lec != null) {
            return lec;
        } else {
            throw new LecturerNotFoundException("Lecturer with specified username not found");
        }
    }
    
    
    
    
    @Override
    public Lecturer retrieveLecturerByEmail(String email) throws LecturerNotFoundException {
        Query query = em.createQuery("SELECT l FROM Lecturer l WHERE l.email=:inEmail");
        query.setParameter("inEmail", email);
        Lecturer lec = (Lecturer) query.getSingleResult();
        
        if (lec != null) {
            return lec;
        } else {
            throw new LecturerNotFoundException("Lecturer with specified email not found");
        }
    }
    
    
    
    @Override
    public Lecturer retrieveLecturerByPhoneNum(String phoneNum) throws LecturerNotFoundException {
        Query query = em.createQuery("SELECT l FROM Lecturer l WHERE l.telephone:inTelephone");
        query.setParameter("inTelephone", phoneNum);
        Lecturer lec = (Lecturer) query.getSingleResult();
        
        if (lec != null) {
            return lec;
        } else {
            throw new LecturerNotFoundException("Lecturer with specified telephone number not found");
        }
    }
    



    @Override
    public Lecturer lecturerLogin(String username, String password) throws InvalidLoginCredentialException, LecturerNotFoundException {
        try {
            Lecturer lecturer = retrieveLecturerByUsername(username);
            
            if (lecturer.getPassword().equals(password)) {
                lecturer.getModules().size();
                lecturer.getAnnouncements().size();
                return lecturer;
            } else {
                throw new InvalidLoginCredentialException("Account does not exist or invalid password!");
            }
        } catch (LecturerNotFoundException ex) {
            throw new InvalidLoginCredentialException("Account does not exist or invalid password!");
        }
    }
    
    
    
    @Override
    public void changePassword(String newPassword, Long lecturerId) throws LecturerNotFoundException {
        Lecturer lec = retrieveLecturerById(lecturerId);
        
        lec.setPassword(newPassword);
        
        
        updateLecturer(lec);
        
    }
    
  
    
    @Override
    public void updateLecturer(Lecturer lec) {
        em.merge(lec);
    }

   
}
