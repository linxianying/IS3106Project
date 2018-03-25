/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Module;
import entity.Student;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import util.exception.GeneralException;
import util.exception.InvalidLoginCredentialException;
import util.exception.StudentExistException;
import util.exception.StudentNotFoundException;

/**
 *
 * @author mango
 */
@Local
public interface StudentControllerLocal {

    Student createStudent(Student student) throws StudentExistException, GeneralException;  
    Student retrieveStudentByUsername(String username) throws StudentNotFoundException;
    
    boolean updateStudentTelephone(String username, String telephone) throws StudentNotFoundException;
    boolean updateStudentPassword(String username, String password) throws StudentNotFoundException;
    boolean updateStudentEmail(String username, String email) throws StudentNotFoundException;

    public List<Student> retrieveAllStudents();

    Student login(String username, String password)throws InvalidLoginCredentialException;


    public List<Module> retrieveStudentModules(Long id) throws StudentNotFoundException;
}
