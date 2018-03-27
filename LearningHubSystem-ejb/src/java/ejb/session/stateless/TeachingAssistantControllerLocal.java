/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.TeachingAssistant;
import java.util.List;
import javax.ejb.Local;
import util.exception.GeneralException;
import util.exception.InvalidLoginCredentialException;
import util.exception.TAExistException;
import util.exception.TANotFoundException;

/**
 *
 * @author mango
 */
@Local
public interface TeachingAssistantControllerLocal {

    TeachingAssistant createTeachingAssistant(TeachingAssistant ta) throws TAExistException, GeneralException;

    TeachingAssistant retrieveTAByUsername(String username) throws TANotFoundException;

    boolean updateTeachingAssistantEmail(String username, String email) throws TANotFoundException;

    boolean updateTeachingAssistantPassword(String username, String password) throws TANotFoundException;

    boolean updateTeachingAssistantTelephone(String username, String telephone) throws TANotFoundException;

    TeachingAssistant login(String username, String password) throws InvalidLoginCredentialException;

    List<TeachingAssistant> retrieveAllTAs();
    
    public void deleteTA (TeachingAssistant ta);
}
