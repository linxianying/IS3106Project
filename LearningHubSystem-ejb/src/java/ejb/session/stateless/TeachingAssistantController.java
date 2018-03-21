/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import javax.ejb.Stateless;
import entity.Lecturer;
import entity.Administrator;
import entity.Announcement;
import entity.Module;
import entity.Student;
import entity.TeachingAssistant;
import javax.ejb.Local;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.GeneralException;
import util.exception.InvalidLoginCredentialException;
import util.exception.TAExistException;
import util.exception.TANotFoundException;
/**
 *
 * @author mango
 */
@Stateless
@Local(TeachingAssistantControllerLocal.class)
public class TeachingAssistantController implements TeachingAssistantControllerLocal {

    @PersistenceContext
    EntityManager em;
    
    TeachingAssistant teachingAssistant;
    
    @Override
    public TeachingAssistant createTeachingAssistant(TeachingAssistant ta)throws TAExistException,GeneralException {
        try{
            em.persist(ta);
            em.flush();
            em.refresh(ta);

            return ta;
        } catch (PersistenceException ex) {
            if (ex.getCause() != null
                    && ex.getCause().getCause() != null
                    && ex.getCause().getCause().getClass().getSimpleName().equals("MySQLIntegrityConstraintViolationException")) {
                throw new TAExistException("TA Account Already Exist.\n");
            } else {
                throw new GeneralException("An unexpected error has occurred: " + ex.getMessage());
            }
        }
    }

    @Override
    public TeachingAssistant retrieveTAByUsername(String username) throws TANotFoundException{
        teachingAssistant = null;
        try{
            Query q = em.createQuery("SELECT s FROM TeachingAssistant s WHERE s.username=:username");
            q.setParameter("username", username);
            teachingAssistant = (TeachingAssistant) q.getSingleResult();
            System.out.println("TeachingAssistant " + username + " found.");
        }
        catch(NoResultException e){
            throw new TANotFoundException();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return teachingAssistant;
    }
    
    @Override
    public boolean updateTeachingAssistantEmail(String username, String email)throws TANotFoundException {
        
        teachingAssistant = retrieveTAByUsername(username);
        
        if (teachingAssistant==null) {
            System.out.println("Error: No teaching assistant is found");
            return false;
        }
        teachingAssistant.setEmail(email);
        em.merge(teachingAssistant);
        return true;
    }
    
    @Override
    public boolean updateTeachingAssistantPassword(String username, String password)throws TANotFoundException {
        
        teachingAssistant = retrieveTAByUsername(username);
        
        if (teachingAssistant==null) {
            System.out.println("Error: update teaching assistant's password failed");
            return false;
        }
        teachingAssistant.setPassword(password);
        em.merge(teachingAssistant);
        return true;
    }
    
    @Override
    public boolean updateTeachingAssistantTelephone(String username, String telephone) throws TANotFoundException{
        
        teachingAssistant = retrieveTAByUsername(username);
        
        if (teachingAssistant==null) {
            System.out.println("Error: update teaching assistant's telephone failed");
            return false;
        }
        teachingAssistant.setTelephone(telephone);
        em.merge(teachingAssistant);
        return true;
    }
    
    @Override
    public TeachingAssistant login(String username, String password) throws InvalidLoginCredentialException{
        try
        {
            TeachingAssistant ta = retrieveTAByUsername(username);
           
            if(ta.getPassword().equals(password))
            {
                return ta;
            }
            else
            {
                throw new InvalidLoginCredentialException("Invalid password!");
            }
        }
        catch(TANotFoundException ex)
        {
            throw new InvalidLoginCredentialException("Username does not exist!");
        }
    }
}
