/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.TeachingAssistant;
import javax.ejb.Local;

/**
 *
 * @author mango
 */
@Local
public interface teachingAssistantControllerLocal {
    
    TeachingAssistant createTeachingAssistant(String username, String password, String name, 
            String email, String faculty, String telephone, String department);
    TeachingAssistant findTeachingAssistant(String username);
    boolean addNewTeachingAssistant(String name, String password, String email, 
            String faculty, String department, String telephone, String username);
    boolean updateTeachingAssistantEmail(String username, String email);
    boolean updateTeachingAssistantPassword(String username, String password);
    boolean updateTeachingAssistantTelephone(String username, String telephone);
}
