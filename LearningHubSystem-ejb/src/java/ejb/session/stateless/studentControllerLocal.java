/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Student;
import javax.ejb.Local;

/**
 *
 * @author mango
 */
@Local
public interface studentControllerLocal {

    Student createStudent(String name, String password, String email, 
            String faculty, String department, String telephone, String username);
    boolean addNewStudent(String name, String password, String email, 
            String faculty, String department, String telephone, String username);
    Student findStudent(String username);
    
    boolean updateStudentTelephone(String username, String telephone);
    boolean updateStudentPassword(String username, String password);
    boolean updateStudentEmail(String username, String email);
}
