/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Administrator;
import java.util.List;
import javax.ejb.Local;
import util.exception.AdminExistException;
import util.exception.AdminNotFoundException;
import util.exception.GeneralException;
import util.exception.InvalidLoginCredentialException;
import util.exception.PasswordChangeException;

/**
 *
 * @author mango
 */
@Local
public interface administratorControllerLocal {

    Administrator createNewAmin(Administrator admin) throws AdminExistException;

    List<Administrator> retrieveAllAdmins();

    Administrator retrieveAdminByUsername(String username) throws AdminNotFoundException;

    Administrator retrieveAdminById(Long id) throws AdminNotFoundException;
    
    Administrator adminLogin(String username, String password) throws InvalidLoginCredentialException, AdminNotFoundException;
    
    void changePassword(String currentPassword, String newPassword, Long adminId) throws AdminNotFoundException, PasswordChangeException;
    
    Administrator updateAdmin(Administrator admin) throws AdminExistException, GeneralException;
}
