/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Lecturer;
import javax.ejb.Local;

/**
 *
 * @author mango
 */
@Local
public interface lecturerControllerLocal {
    Lecturer findLecturer(String username);
    Lecturer createLecturer(String username, String password, String name, String email,
            String faculty, String department, String telephone);
    boolean addNewLecturer(String username, String password, String name, String email,
            String faculty, String department, String telephone);
}
