/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Lecturer;
import entity.Administrator;
import entity.Announcement;
import entity.Module;
import entity.Student;
import entity.TeachingAssistant;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mango
 */
@Stateless
public class lecturerController implements lecturerControllerLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    EntityManager em;
    
    Lecturer lecturer;
    Student student;
    
    @Override
    public Lecturer createLecturer(String username, String password, String name, String email,
            String faculty, String department, String telephone) {
        lecturer = new Lecturer(username, password, name, email,faculty, department, telephone);
        em.persist(lecturer);
        em.flush();
        return lecturer;
    }
    
    @Override
    public Lecturer findLecturer(String username){
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
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return lecturer;
    }
    
    @Override
    public boolean addNewLecturer(String username, String password, String name, String email,
            String faculty, String department, String telephone){
        lecturer = findLecturer(username);
        if(lecturer==null){
            lecturer = createLecturer(username, password, name, email,faculty, department, telephone);
            System.out.println("Lecturer with id " + lecturer.getId() +" is created successfully.");
            return true;
        }
        return true;
    }
    
    
}
