/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Lecturer;
import entity.Module;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.GeneralException;
import util.exception.InvalidLoginCredentialException;
import util.exception.LecturerExistException;
import util.exception.LecturerNotFoundException;
import util.exception.ModuleExistException;
import util.exception.ModuleNotFoundException;
import util.exception.PasswordChangeException;

/**
 *
 * @author mango
 */
@Stateless
@Local(LecturerControllerLocal.class)

public class LecturerController implements LecturerControllerLocal {

    @EJB(name = "ModuleControllerLocal")
    private ModuleControllerLocal moduleControllerLocal;

    @PersistenceContext(unitName = "LearningHubSystem-ejbPU")
    private EntityManager em;
    
    
    
    Lecturer lecturer;

    
    @Override
    public List<Lecturer> retrieveAllLecturers() {
        Query query = em.createQuery("SELECT l FROM Lecturer l");
        return (List<Lecturer>) query.getResultList();
        
    }
    
    @Override
    public Lecturer createNewLecturer(Lecturer lecturer) throws LecturerExistException,GeneralException {
        try{
            em.persist(lecturer);
            em.flush();
            em.refresh(lecturer);

            return lecturer;
        } catch (PersistenceException ex) {
            if (ex.getCause() != null
                    && ex.getCause().getCause() != null
                    && ex.getCause().getCause().getClass().getSimpleName().equals("MySQLIntegrityConstraintViolationException")) {
                throw new LecturerExistException("Lecturer Account Already Exist.\n");
            } else {
                throw new GeneralException("An unexpected error has occurred: " + ex.getMessage());
            }
        }
    }

    
    @Override
    public Lecturer retrieveLecturerById(Long lecturerId) throws LecturerNotFoundException {
        Lecturer lec = em.find(Lecturer.class, lecturerId);
        
        if (lec != null) {
            return lec;
        } else {
            throw new LecturerNotFoundException("Lecturer with specified ID not found");
        }
    }
    
    
    
    
    @Override
    public Lecturer retrieveLecturerByUsername(String username) throws LecturerNotFoundException {
        lecturer = null;
        try{
            Query q = em.createQuery("SELECT s FROM Lecturer s WHERE s.username=:username");
            q.setParameter("username", username);
            lecturer = (Lecturer) q.getSingleResult();
            System.out.println("Lecturer " + username + " found.");
        }
        catch(NoResultException e){
            System.out.println("Lecturer " + username + " does not exist.");
            lecturer = null;
            throw new LecturerNotFoundException("Lecturer with specified ID not found");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return lecturer;
    }
    
    
    
    
    @Override
    public Lecturer retrieveLecturerByEmail(String email) throws LecturerNotFoundException {
        Query query = em.createQuery("SELECT l FROM Lecturer l WHERE l.email=:inEmail");
        query.setParameter("inEmail", email);
        Lecturer lec = (Lecturer) query.getSingleResult();
        
        if (lec != null) {
            return lec;
        } else {
            throw new LecturerNotFoundException("Lecturer with specified email not found");
        }
    }
    
    
    
    @Override
    public Lecturer retrieveLecturerByPhoneNum(String phoneNum) throws LecturerNotFoundException {
        Query query = em.createQuery("SELECT l FROM Lecturer l WHERE l.telephone:inTelephone");
        query.setParameter("inTelephone", phoneNum);
        Lecturer lec = (Lecturer) query.getSingleResult();
        
        if (lec != null) {
            return lec;
        } else {
            throw new LecturerNotFoundException("Lecturer with specified telephone number not found");
        }
    }
    



    @Override
    public Lecturer login(String username, String password) throws InvalidLoginCredentialException, LecturerNotFoundException {
        try {
            Lecturer lecturer = retrieveLecturerByUsername(username);
            
            if (lecturer.getPassword().equals(password)) {
                lecturer.getModules().size();
                lecturer.getAnnouncements().size();
                return lecturer;
            } else {
                throw new InvalidLoginCredentialException("Account does not exist or invalid password!");
            }
        } catch (LecturerNotFoundException ex) {
            throw new InvalidLoginCredentialException("Account does not exist or invalid password!");
        }
    }
    
    
    
    @Override
    public void changePassword(String currentPassword, String newPassword, Long lecturerId) throws LecturerNotFoundException, PasswordChangeException {
        if (currentPassword.length() > 16 || currentPassword.length() < 6) {
            throw new PasswordChangeException("Password length must be in range [6.16]!");
        }
        
        try{
            Lecturer lec = retrieveLecturerById(lecturerId);
            if(currentPassword.equals(lec.getPassword())){
                lec.setPassword(newPassword);
                em.merge(lec);
            }
            else{
                throw new PasswordChangeException("Password change Failed: Current password is wrong");
            }
        }
        catch(LecturerNotFoundException ex){
            throw new LecturerNotFoundException("Lecturer with ID " + lecturerId + "does not exist.");
        }  
    }
    
    @Override
    public Lecturer updateLecturer(Lecturer lec) throws LecturerNotFoundException{
        if (lec.getId() != null) {
            Lecturer lecToUpdate = retrieveLecturerById(lec.getId());
            lecToUpdate.setName(lec.getName());
            lecToUpdate.setUsername(lec.getUsername());
            lecToUpdate.setTelephone(lec.getTelephone());
            lecToUpdate.setEmail(lec.getEmail());
            lecToUpdate.setDepartment(lec.getDepartment());
            lecToUpdate.setFaculty(lec.getFaculty());
            em.merge(lecToUpdate);
            return lecToUpdate;
        } else {
            throw new LecturerNotFoundException("Lecturer ID not provided for profile to be updated");
        }
    } 
    
    @Override
    public void deleteLecturer (Lecturer lec) throws LecturerNotFoundException {
        try {
            Lecturer lecToDelete = retrieveLecturerById(lec.getId());
            em.remove(lecToDelete);
        } catch (LecturerNotFoundException ex) {
            throw new LecturerNotFoundException("Lecturer doesn't exist.");
        }
        
    }
    
    @Override 
    public Module registerModule (Lecturer lec, Module mod) throws ModuleExistException, ModuleNotFoundException,LecturerNotFoundException{
        
        Module m = moduleControllerLocal.retrieveModuleById(mod.getId());
        Lecturer l = retrieveLecturerById(lec.getId());
        
        
        List<Module> modules = l.getModules();
        for(Module module:modules)   {
            if (module.getModuleCode().equals(m.getModuleCode())){
                throw new ModuleExistException("Module has already been registered.\n");
            }
        }
          
            l.getModules().add(m);
            m.getLecturers().add(l);
            
            return m;
    }
    
    @Override
    public void dropModule(Lecturer l, Module m) throws ModuleNotFoundException, LecturerNotFoundException{
        System.out.println("enter controller");
        Boolean registered = false;
        
        Module mod = moduleControllerLocal.retrieveModuleById(m.getId());
        Lecturer lec = retrieveLecturerById(l.getId());
        
        List<Module> modules = lec.getModules();
        for(Module module:modules)   {
            if (module.getModuleCode().equals(mod.getModuleCode())){
                registered = true;
                break;
            }
        }
        
        if(registered){
            lec.getModules().remove(mod);
            mod.getLecturers().remove(lec);
            
            System.out.println("drop module");
            
        }
        
        else throw new ModuleNotFoundException ( "Module: "+mod.getModuleCode()+ " wasn't found in the module list.");
    }

    @Override
    public List<Module> retrieveEnrolledModules(Long lecturerId) {
        try{
            Lecturer lecturer = retrieveLecturerById(lecturerId);
            return lecturer.getModules();
        }
        catch(LecturerNotFoundException ex){
            ex.getMessage();
        }
        return new ArrayList<>();
    }

    

}
