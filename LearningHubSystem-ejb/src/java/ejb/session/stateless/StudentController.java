/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import util.exception.StudentExistException;
import entity.Module;
import entity.Student;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
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
import util.exception.StudentNotFoundException;

/**
 *
 * @author mango
 */
@Stateless
@Local(StudentControllerLocal.class)
public class StudentController implements StudentControllerLocal {

    @PersistenceContext(unitName = "LearningHubSystem-ejbPU")
    private EntityManager em;

    Student student;

//    
//    @Override
//    public List<Module> retrieveStudentModules(Long id) throws StudentNotFoundException{
//        student = null;
//        try{
//            Query q = em.createQuery("SELECT s FROM Student s WHERE s.id=:id");
//            q.setParameter("id", id);
//            student = (Student) q.getSingleResult();
//            System.out.println("Student with id:" + id + " found.");
//        }
//        catch(NoResultException e){
//            throw new StudentNotFoundException("Student with specified ID not found");
//        }
//        catch(Exception e) {
//            e.printStackTrace();
//        }
//        return student.getModules();
//    }
//    
    @Override
    public Student createStudent(Student studentEntity) throws StudentExistException, GeneralException {
        try {
            em.persist(studentEntity);
            em.flush();
            em.refresh(studentEntity);

            return studentEntity;
        } catch (PersistenceException ex) {
            if (ex.getCause() != null
                    && ex.getCause().getCause() != null
                    && ex.getCause().getCause().getClass().getSimpleName().equals("MySQLIntegrityConstraintViolationException")) {
                throw new StudentExistException("Student Account Already Exist.\n");
            } else {
                throw new GeneralException("An unexpected error has occurred: " + ex.getMessage());
            }
        }
    }

    @Override
    public Student retrieveStudentById(Long id) throws StudentNotFoundException {
        student = null;
        try {
            Query q = em.createQuery("SELECT s FROM Student s WHERE s.id=:ID");
            q.setParameter("ID", id);
            student = (Student) q.getSingleResult();
        } catch (NoResultException e) {
            throw new StudentNotFoundException("Student with specified ID not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public Student retrieveStudentByUsername(String username) throws StudentNotFoundException {
        student = null;
        try {
            Query q = em.createQuery("SELECT s FROM Student s WHERE s.username=:username");
            q.setParameter("username", username);
            student = (Student) q.getSingleResult();
            System.out.println("Student " + username + " found.");
        } catch (NoResultException e) {
            throw new StudentNotFoundException("Student with specified ID not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public List<Student> retrieveAllStudents() {
        Query query = em.createQuery("SELECT s FROM Student s");
        return (List<Student>) query.getResultList();
    }

    @Override
    public boolean updateStudentEmail(String username, String email) throws StudentNotFoundException {

        student = retrieveStudentByUsername(username);

        if (student == null) {
            System.out.println("Error: No student is found");
            return false;
        }
        student.setEmail(email);
        em.merge(student);
        return true;
    }

    @Override
    public boolean updateStudentPassword(String username, String password) throws StudentNotFoundException {

        student = retrieveStudentByUsername(username);

        if (student == null) {
            System.out.println("Error: No student is found");
            return false;
        }
        student.setPassword(password);
        em.merge(student);
        return true;
    }

    @Override
    public boolean updateStudentTelephone(String username, String telephone) throws StudentNotFoundException {

        student = retrieveStudentByUsername(username);

        if (student == null) {
            System.out.println("Error: No student is found");
            return false;
        }
        student.setTelephone(telephone);
        em.merge(student);
        return true;
    }

    @Override
    public Student login(String username, String password) throws InvalidLoginCredentialException {
        try {
            Student student = retrieveStudentByUsername(username);

            if (student.getPassword().equals(password)) {
                return student;
            } else {
                throw new InvalidLoginCredentialException("Invalid password!");
            }
        } catch (StudentNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist!");
        }
    }

   @Override
    public void deleteStudent (Student stu) throws StudentNotFoundException {
        try {
            Student stuToDelete = retrieveStudentById(stu.getId());
            em.remove(stuToDelete);
        } catch (StudentNotFoundException ex) {
            throw new StudentNotFoundException("Student doesn't exist.");
        }
        
    }

    @Override
    public Module registerModule(Student stu, Module mod) throws ModuleExistException {
        List<Module> modules = stu.getModules();
        for (Module module : modules) {
            if (module.getModuleCode().equals(mod.getModuleCode())) {
                throw new ModuleExistException("Module has already been registered.\n");
            }
        }

        stu.getModules().add(mod);
        mod.getStduents().add(stu);
        em.persist(stu);
        em.persist(mod);
        em.flush();

        return mod;
    }

    @Override
    public void dropModule(Student stu, Module mod) throws ModuleNotFoundException {
        Boolean registered = false;
        List<Module> modules = stu.getModules();
        for (Module module : modules) {
            if (module.getModuleCode().equals(mod.getModuleCode())) {
                registered = true;
            }
        }

        if (registered) {
            stu.getModules().remove(mod);
            mod.getStduents().remove(stu);
            em.persist(stu);
            em.persist(mod);
            em.flush();
        } else {
            throw new ModuleNotFoundException("Module: " + mod.getModuleCode() + " wasn't found in the module list.");
        }
    }

    @Override
    public Student updateStudent(Student stu) throws StudentNotFoundException {
        if (stu.getId() != null) {
            Student studentToUpdate = retrieveStudentById(stu.getId());
            studentToUpdate.setName(stu.getName());
            studentToUpdate.setUsername(stu.getUsername());
            studentToUpdate.setTelephone(stu.getTelephone());
            studentToUpdate.setEmail(stu.getEmail());
            studentToUpdate.setDepartment(stu.getDepartment());
            studentToUpdate.setFaculty(stu.getFaculty());
            return studentToUpdate;
        } else {
            throw new StudentNotFoundException("Student ID not provided for profile to be updated");
        }
    }

    @Override
    public void changePassword(String currentPassword, String newPassword, Long studentId) throws StudentNotFoundException, PasswordChangeException {
        if (currentPassword.length() > 16 || currentPassword.length() < 6||newPassword.length() > 16 || newPassword.length() < 6) {
                throw new PasswordChangeException("Password length must be in range [6.16]!");
        }

        try {
            Student stu = retrieveStudentById(studentId);
            if (currentPassword.equals(stu.getPassword())) {
                stu.setPassword(newPassword);
                em.merge(stu);
            } else {
                throw new PasswordChangeException("Password change Failed: Current password is wrong");
            }
        } catch (StudentNotFoundException ex) {
            throw new StudentNotFoundException("Student with ID " + studentId + "does not exist.");
        }
    }

}
