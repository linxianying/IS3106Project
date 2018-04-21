/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Module;
import entity.TeachingAssistant;
import java.util.List;
import javax.ejb.Local;
import util.exception.GeneralException;
import util.exception.InvalidLoginCredentialException;
import util.exception.ModuleExistException;
import util.exception.ModuleNotFoundException;
import util.exception.PasswordChangeException;
import util.exception.TAExistException;
import util.exception.TANotFoundException;

/**
 *
 * @author mango
 */
@Local
public interface TeachingAssistantControllerLocal {

    TeachingAssistant createTeachingAssistant(TeachingAssistant ta) throws TAExistException, GeneralException;

    TeachingAssistant retrieveTAById(Long id) throws TANotFoundException;

    TeachingAssistant retrieveTAByUsername(String username) throws TANotFoundException;

    //boolean updateTeachingAssistantEmail(String username, String email) throws TANotFoundException;
    boolean updateTeachingAssistantPassword(String username, String password) throws TANotFoundException;

    TeachingAssistant updateTA(TeachingAssistant ta) throws TANotFoundException;

    //boolean updateTeachingAssistantTelephone(String username, String telephone) throws TANotFoundException;
    TeachingAssistant login(String username, String password) throws InvalidLoginCredentialException;

    List<TeachingAssistant> retrieveAllTAs();

    public void deleteTA (TeachingAssistant ta) throws TANotFoundException;

    public Module registerModule (TeachingAssistant t, Module m) throws ModuleExistException, TANotFoundException, ModuleNotFoundException;

    public void dropModule(TeachingAssistant t, Module m) throws ModuleNotFoundException, TANotFoundException;

    public void changePassword(String currentPassword, String newPassword, Long TAId) throws TANotFoundException, PasswordChangeException;

    public TeachingAssistant updateTAPassword(Long id, String newPassword);

}
