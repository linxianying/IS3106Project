/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Lecturer;
import java.util.ArrayList;
import javax.ejb.Local;
import util.exception.InvalidLoginCredentialException;
import util.exception.LecturerExistException;
import util.exception.LecturerNotFoundException;

/**
 *
 * @author mango
 */
@Local
public interface lecturerControllerLocal {

    public ArrayList<Lecturer> retrieveAllLecturers();

    public Lecturer createNewLecturer(Lecturer lecturer) throws LecturerExistException;

    public Lecturer retrieveLecturerById(Long lecturerId) throws LecturerNotFoundException;

    public Lecturer retrieveLecturerByUsername(String username) throws LecturerNotFoundException;

    public Lecturer retrieveLecturerByEmail(String email) throws LecturerNotFoundException;

    public Lecturer retrieveLecturerByPhoneNum(String phoneNum) throws LecturerNotFoundException;

    public Lecturer lecturerLogin(String username, String password) throws InvalidLoginCredentialException, LecturerNotFoundException;

    public void updateLecturer(Lecturer lec);

    public void changePassword(String newPassword, Long lecturerId) throws LecturerNotFoundException;
    
}
