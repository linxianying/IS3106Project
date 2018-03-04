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
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 *
 * @author mango
 */
@Stateless
public class teachingAssistantController implements teachingAssistantControllerLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    EntityManager em;
    
    Student student;
    TeachingAssistant teachingAssistant;
    
    @Override
    public TeachingAssistant createTeachingAssistant(String username, String password, String name, 
            String email, String faculty, String telephone, String department) {
        teachingAssistant = new TeachingAssistant( username, password, name, email, faculty, telephone, department);
        em.persist(teachingAssistant);
        em.flush();
        return teachingAssistant;
    }

    @Override
    public TeachingAssistant findTeachingAssistant(String username){
        teachingAssistant = null;
        try{
            Query q = em.createQuery("SELECT s FROM TeachingAssistant s WHERE s.username=:username");
            q.setParameter("username", username);
            teachingAssistant = (TeachingAssistant) q.getSingleResult();
            System.out.println("TeachingAssistant " + username + " found.");
        }
        catch(NoResultException e){
            System.out.println("TeachingAssistant " + username + " does not exist.");
            teachingAssistant = null;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return teachingAssistant;
    }
    
    @Override
    public boolean addNewTeachingAssistant(String name, String password, String email, 
            String faculty, String department, String telephone, String username){
        teachingAssistant = findTeachingAssistant(username);
        if(teachingAssistant==null){
            teachingAssistant = createTeachingAssistant(name, password, email, faculty, department, telephone, username);
            System.out.println("TeachingAssistant with id " + teachingAssistant.getId() +" is created successfully.");
            return true;
        }
        return true;
    }
    
    @Override
    public boolean updateTeachingAssistantEmail(String username, String email) {
        
        teachingAssistant = findTeachingAssistant(username);
        
        if (teachingAssistant==null) {
            System.out.println("Error: No teaching assistant is found");
            return false;
        }
        teachingAssistant.setEmail(email);
        em.merge(teachingAssistant);
        return true;
    }
    
    @Override
    public boolean updateTeachingAssistantPassword(String username, String password) {
        
        teachingAssistant = findTeachingAssistant(username);
        
        if (teachingAssistant==null) {
            System.out.println("Error: update teaching assistant's password failed");
            return false;
        }
        teachingAssistant.setPassword(password);
        em.merge(teachingAssistant);
        return true;
    }
    
    @Override
    public boolean updateTeachingAssistantTelephone(String username, String telephone) {
        
        teachingAssistant = findTeachingAssistant(username);
        
        if (teachingAssistant==null) {
            System.out.println("Error: update teaching assistant's telephone failed");
            return false;
        }
        teachingAssistant.setTelephone(telephone);
        em.merge(teachingAssistant);
        return true;
    }
}
