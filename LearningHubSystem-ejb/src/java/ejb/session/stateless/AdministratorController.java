/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import javax.ejb.Stateless;
import entity.Administrator;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.AdminExistException;
import util.exception.AdminNotFoundException;
import util.exception.GeneralException;
import util.exception.InvalidLoginCredentialException;
import util.exception.PasswordChangeException;
/**
 *
 * @author mango
 */
@Stateless
@Local(AdministratorControllerLocal.class)
public class AdministratorController implements AdministratorControllerLocal {

    @PersistenceContext(unitName = "LearningHubSystem-ejbPU")
    private EntityManager em;

    
    @Override
    public Administrator createNewAmin(Administrator admin) throws AdminExistException {
        List<Administrator> admins = retrieveAllAdmins();
        
        for (Administrator each: admins) {
            if (each.getUsername().equals(admin.getUsername()))
            {
                throw new AdminExistException("Admin: " + admin.getUsername() + "Already Exist.\n");
            }
        }
        em.persist(admin);
        em.flush();
        
        return admin;
    }

    @Override
    public List<Administrator> retrieveAllAdmins() {
        Query query = em.createQuery("SELECT a FROM Administrator a");
        List<Administrator> admins = query.getResultList();
        return admins;
    }

    @Override
    public Administrator retrieveAdminByUsername(String username) throws AdminNotFoundException{
        Administrator admin = null;
        try{
            Query query = em.createQuery("SELECT a FROM Administrator a WHERE a.username=:username");
            query.setParameter("username", username);
            admin = (Administrator) query.getSingleResult();
            System.out.println("Admin " + username + " found.");

        }
        catch(NoResultException ex){
            throw new AdminNotFoundException("Admin: " + username + "dose not exist.");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return admin;
    }

    @Override
    public Administrator retrieveAdminById(Long adminId) throws AdminNotFoundException{
        Administrator admin = em.find(Administrator.class, adminId);
        if(admin != null){
            return admin;
        }
        else{
            throw new AdminNotFoundException("Admin with ID " +adminId + " not found.\n");
        }
    }

    @Override
    public Administrator login(String username, String password) throws InvalidLoginCredentialException, AdminNotFoundException {
        try {
            Administrator admin = retrieveAdminByUsername(username);
            
            if (admin.getPassword().equals(password)) {
                return admin;
            } else {
                throw new InvalidLoginCredentialException("Invalid password!");
            }
        } catch (AdminNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist!");
        }
    }
    
    
    @Override
    public void changePassword(String currentPassword, String newPassword, Long adminId) throws AdminNotFoundException, PasswordChangeException {
        if (currentPassword.length() > 16 || currentPassword.length() < 6) {
            throw new PasswordChangeException("Password length must be in range [6.16]!");
        }
        
        try{
            Administrator admin = retrieveAdminById(adminId);
            if(currentPassword.equals(admin.getPassword())){
                admin.setPassword(newPassword);
                em.merge(admin);
            }
            else{
                throw new PasswordChangeException("Password change Failed: Current password is wrong");
            }
        }
        catch(AdminNotFoundException ex){
            throw new AdminNotFoundException("Admin with ID " + adminId + "does not exist.");
        }  
    }
    
    @Override
    public Administrator updateAdmin(Administrator admin) throws AdminNotFoundException{
        if (admin.getId() != null) {
            Administrator adminToUpdate = retrieveAdminById(admin.getId());
            adminToUpdate.setName(admin.getName());
            adminToUpdate.setUsername(admin.getUsername());
            adminToUpdate.setTelephone(admin.getTelephone());
            adminToUpdate.setEmail(admin.getEmail());
            return adminToUpdate;
        } else {
            throw new AdminNotFoundException("Admin ID not provided for profile to be updated");
        }
    }

}
