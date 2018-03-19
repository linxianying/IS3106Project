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
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.GeneralException;
import util.exception.InvalidLoginCredentialException;
import util.exception.LecturerExistException;
import util.exception.LecturerNotFoundException;
import util.exception.PasswordChangeException;

/**
 *
 * @author mango
 */
@Stateless
@Local(lecturerControllerLocal.class)

public class lecturerController implements lecturerControllerLocal {

    @PersistenceContext(unitName = "LearningHubSystem-ejbPU")
    private EntityManager em;
    Lecturer lecturer;

    
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
        //Query query = em.createQuery("SELECT l FROM Lecturer l WHERE l.username=:username");
        //query.setParameter("username", username);
        //Lecturer lec = (Lecturer) query.getSingleResult();
        
        //if (lec != null) {
        //    return lec;
        //} else {
        //    throw new LecturerNotFoundException("Lecturer with specified username not found");
        //}
        
        lecturer = null;
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
    public void changePassword(String currentPassword, String newPassword, Long lecturerId) throws LecturerNotFoundException, PasswordChangeException {
        if (currentPassword.length() > 16 || currentPassword.length() < 6) {
            throw new PasswordChangeException("Password length must be in range [6.16]!");
        }
        
        try{
            Lecturer lec = retrieveLecturerById(lecturerId);
            if(currentPassword.equals(lec.getPassword())){
                lec.setPassword(newPassword);
                em.merge(lec);
            }
            else{
                throw new PasswordChangeException("Password change Failed: Current password is wrong");
            }
        }
        catch(LecturerNotFoundException ex){
            throw new LecturerNotFoundException("Lecturer with ID " + lecturerId + "does not exist.");
        }  
    }
    
    @Override
    public Lecturer updateLecturer(Lecturer lec) throws LecturerExistException, GeneralException{
        try {
            return em.merge(lec);
        } catch (PersistenceException ex) {
            if (ex.getCause() != null
                    && ex.getCause().getCause() != null
                    && ex.getCause().getCause().getClass().getSimpleName().equals("MySQLIntegrityConstraintViolationException")) {
                throw new LecturerExistException("Lecturer with same username/phone number/email already exist");
            } else {
                throw new GeneralException("An unexpected error has occurred: " + ex.getMessage());
            }
        }
    }

   
}
