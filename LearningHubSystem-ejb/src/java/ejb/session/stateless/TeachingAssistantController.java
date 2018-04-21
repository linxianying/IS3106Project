/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import javax.ejb.Stateless;
import entity.Module;
import entity.TeachingAssistant;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
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
@Stateless
@Local(TeachingAssistantControllerLocal.class)
public class TeachingAssistantController implements TeachingAssistantControllerLocal {

    @EJB
    private ModuleControllerLocal moduleController;

    @PersistenceContext
    EntityManager em;
    
    

    TeachingAssistant teachingAssistant;

    @Override
    public TeachingAssistant createTeachingAssistant(TeachingAssistant ta) throws TAExistException, GeneralException {
        try {
            em.persist(ta);
            em.flush();
            em.refresh(ta);

            return ta;
        } catch (PersistenceException ex) {
            if (ex.getCause() != null
                    && ex.getCause().getCause() != null
                    && ex.getCause().getCause().getClass().getSimpleName().equals("MySQLIntegrityConstraintViolationException")) {
                throw new TAExistException("TA Account Already Exist.\n");
            } else {
                throw new GeneralException("An unexpected error has occurred: " + ex.getMessage());
            }
        }
    }
    
    @Override
    public TeachingAssistant updateTAPassword(Long id, String newPassword) {
        try {

            TeachingAssistant taToUpdate = retrieveTAById(id);
            taToUpdate.setPassword(newPassword);

            em.merge(taToUpdate);
            return taToUpdate;
        } catch (TANotFoundException ex) {
        }
        return new TeachingAssistant();
    }

    @Override
    public List<TeachingAssistant> retrieveAllTAs() {
        Query query = em.createQuery("SELECT t FROM TeachingAssistant t");
        return (List<TeachingAssistant>) query.getResultList();
    }
    
    @Override 
    public TeachingAssistant retrieveTAById (Long id) throws TANotFoundException{
        teachingAssistant = null;
        try{
            Query q = em.createQuery("SELECT t FROM TeachingAssistant t WHERE t.id=:ID");
            q.setParameter("ID", id);
            teachingAssistant = (TeachingAssistant) q.getSingleResult();
        }
         catch(NoResultException e){
            throw new TANotFoundException("Student with specified ID not found");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return teachingAssistant;
    }

    @Override
    public TeachingAssistant retrieveTAByUsername(String username) throws TANotFoundException {
        teachingAssistant = null;
        try {
            Query q = em.createQuery("SELECT s FROM TeachingAssistant s WHERE s.username=:username");
            q.setParameter("username", username);
            teachingAssistant = (TeachingAssistant) q.getSingleResult();
            System.out.println("TeachingAssistant " + username + " found.");
        } catch (NoResultException e) {
            throw new TANotFoundException();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teachingAssistant;
    }
//
//    @Override
//    public boolean updateTeachingAssistantEmail(String username, String email) throws TANotFoundException {
//
//        teachingAssistant = retrieveTAByUsername(username);
//
//        if (teachingAssistant == null) {
//            System.out.println("Error: No teaching assistant is found");
//            return false;
//        }
//        teachingAssistant.setEmail(email);
//        em.merge(teachingAssistant);
//        return true;
//    }

    @Override
    public boolean updateTeachingAssistantPassword(String username, String password) throws TANotFoundException {

        teachingAssistant = retrieveTAByUsername(username);

        if (teachingAssistant == null) {
            System.out.println("Error: update teaching assistant's password failed");
            return false;
        }
        teachingAssistant.setPassword(password);
        em.merge(teachingAssistant);
        return true;
    }

    @Override
    public TeachingAssistant updateTA(TeachingAssistant ta) throws TANotFoundException {
        
        if (ta.getId() != null) {
            TeachingAssistant taToUpdate = retrieveTAById(ta.getId());
            taToUpdate.setName(ta.getName());
            taToUpdate.setUsername(ta.getUsername());
            taToUpdate.setTelephone(ta.getTelephone());
            taToUpdate.setEmail(ta.getEmail());
            taToUpdate.setDepartment(ta.getDepartment());
            taToUpdate.setFaculty(ta.getFaculty());
            taToUpdate.setIsPremium(ta.getIsPremium());
            em.merge(taToUpdate);
            return taToUpdate;
            
        } else {
            throw new TANotFoundException("TA ID not provided for profile to be updated");
        }

    }
    
//    @Override
//    public boolean updateTeachingAssistantTelephone(String username, String telephone) throws TANotFoundException {
//
//        teachingAssistant = retrieveTAByUsername(username);
//
//        if (teachingAssistant == null) {
//            System.out.println("Error: update teaching assistant's telephone failed");
//            return false;
//        }
//        teachingAssistant.setTelephone(telephone);
//        em.merge(teachingAssistant);
//        return true;
//    }

    @Override
    public TeachingAssistant login(String username, String password) throws InvalidLoginCredentialException {
        try {
            TeachingAssistant ta = retrieveTAByUsername(username);

            if (ta.getPassword().equals(password)) {
                return ta;
            } else {
                throw new InvalidLoginCredentialException("Invalid password!");
            }
        } catch (TANotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist!");
        }
    }
    
     @Override
    public void deleteTA (TeachingAssistant ta) throws TANotFoundException {
        try {
            TeachingAssistant taToDelete = retrieveTAById(ta.getId());
            em.remove(taToDelete);
        } catch (TANotFoundException ex) {
            throw new TANotFoundException("TA doesn't exist.");
        }
        
    }
    
    @Override 
    public Module registerModule (TeachingAssistant t, Module m) throws ModuleExistException, TANotFoundException, ModuleNotFoundException{
        TeachingAssistant ta = retrieveTAById(t.getId());
        Module mod = moduleController.retrieveModuleById(m.getId());
        
        List<Module> modules = ta.getModules();
        for(Module module:modules)   {
            if (module.getModuleCode().equals(mod.getModuleCode())){
                throw new ModuleExistException("Module has already been registered.\n");
            }
        }
          
            ta.getModules().add(mod);
            mod.getTAs().add(ta);
           
            return mod;
    }
    
    @Override
    public void dropModule(TeachingAssistant t, Module m) throws ModuleNotFoundException, TANotFoundException{
        Boolean registered = false;
        TeachingAssistant ta = retrieveTAById(t.getId());
        Module mod = moduleController.retrieveModuleById(m.getId());
        
        List<Module> modules = ta.getModules();
        for(Module module:modules)   {
            if (module.getModuleCode().equals(mod.getModuleCode())){
                registered = true;
                break;
            }
        }
        
        if(registered){
            ta.getModules().remove(mod);
            mod.getTAs().remove(ta);
            
        }
        
        else throw new ModuleNotFoundException ( "Module: "+mod.getModuleCode()+ " wasn't found in the module list.");
    }
    
    
    
    @Override
    public void changePassword(String currentPassword, String newPassword, Long TAId) throws TANotFoundException, PasswordChangeException {
        if (currentPassword.length() > 16 || currentPassword.length() < 6) {
            throw new PasswordChangeException("Password length must be in range [6.16]!");
        }

        try {
            TeachingAssistant ta = retrieveTAById(TAId);
            if (currentPassword.equals(ta.getPassword())) {
                ta.setPassword(newPassword);
                em.merge(ta);
            } else {
                throw new PasswordChangeException("Password change Failed: Current password is wrong");
            }
        } catch (TANotFoundException ex) {
            throw new TANotFoundException("Teaching Assistant with ID " + TAId + "does not exist.");
        }
    }
}
