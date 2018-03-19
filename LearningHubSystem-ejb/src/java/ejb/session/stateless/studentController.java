/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import util.exception.StudentExistException;
import entity.Lecturer;
import entity.Administrator;
import entity.Announcement;
import entity.Module;
import entity.Student;
import entity.TeachingAssistant;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.StudentNotFoundException;

/**
 *
 * @author mango
 */
@Stateless
public class studentController implements studentControllerLocal {

    @PersistenceContext(unitName = "LearningHubSystem-ejbPU")
    private EntityManager em;
    
    Student student;
    
    @Override
    public Student createStudent(Student student) throws StudentExistException{
        ArrayList<Student> studentList = retrieveAllStudents();
        for (Student stu : studentList) {
            if (stu.getUsername().equals(student.getUsername()))
            {
                throw new StudentExistException("Student Account Already Exist.\n");
            }
        }
        em.persist(student);
        em.flush();
        
        return student;
    }

    @Override
    public Student findStudent(String username) throws StudentNotFoundException{
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
            throw new StudentNotFoundException("Student with specified ID not found");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return student;
    }
    
    @Override
    public ArrayList<Student> retrieveAllStudents() {
        Query query = em.createQuery("SELECT s FROM Student s");
        return (ArrayList<Student>) query.getResultList();
        
    }
    
    @Override
    public boolean updateStudentEmail(String username, String email) throws StudentNotFoundException {
        
        student = findStudent(username);
        
        if (student==null) {
            System.out.println("Error: No student is found");
            return false;
        }
        student.setEmail(email);
        em.merge(student);
        return true;
    }
    
    @Override
    public boolean updateStudentPassword(String username, String password) throws StudentNotFoundException {
        
        student = findStudent(username);
        
        if (student==null) {
            System.out.println("Error: No student is found");
            return false;
        }
        student.setPassword(password);
        em.merge(student);
        return true;
    }
    
    @Override
    public boolean updateStudentTelephone(String username, String telephone) throws StudentNotFoundException {
        
        student = findStudent(username);
        
        if (student==null) {
            System.out.println("Error: No student is found");
            return false;
        }
        student.setTelephone(telephone);
        em.merge(student);
        return true;
    }
    
    
}
