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
public class studentController implements studentControllerLocal {

    @PersistenceContext
    EntityManager em;
    
    Student student;
    
    @Override
    public Student createStudent(String name, String password, String email, 
            String faculty, String department, String telephone, String username) {
        student = new Student(name, password, email, faculty, department, telephone, username);
        em.persist(student);
        em.flush();
        return student;
    }

    @Override
    public Student findStudent(String username){
        student = null;
        try{
            Query q = em.createQuery("SELECT s FROM Student s WHERE s.username=:username");
            q.setParameter("username", username);
            student = (Student) q.getSingleResult();
            System.out.println("Student " + username + " found.");
        }
        catch(NoResultException e){
            System.out.println("Student " + username + " does not exist.");
            student = null;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return student;
    }
    
    @Override
    public boolean addNewStudent(String name, String password, String email, 
            String faculty, String department, String telephone, String username){
        student = findStudent(username);
        if(student==null){
            student = createStudent(name, password, email, faculty, department, telephone, username);
            System.out.println("Student with id " + student.getId() +" is created successfully.");
            return true;
        }
        return true;
    }
    
    
}
