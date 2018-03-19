/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Student;
import java.util.ArrayList;
import javax.ejb.Local;
import util.exception.StudentExistException;
import util.exception.StudentNotFoundException;

/**
 *
 * @author mango
 */
@Local
public interface studentControllerLocal {

    Student createStudent(Student student) throws StudentExistException;  
    Student findStudent(String username) throws StudentNotFoundException;
    
    boolean updateStudentTelephone(String username, String telephone) throws StudentNotFoundException;
    boolean updateStudentPassword(String username, String password) throws StudentNotFoundException;
    boolean updateStudentEmail(String username, String email) throws StudentNotFoundException;

    public ArrayList<Student> retrieveAllStudents();
}
