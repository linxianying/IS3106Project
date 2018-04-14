package ejb.session.stateless;

import entity.Announcement;
import entity.Lecturer;
import javax.ejb.Stateless;
import entity.Module;
import entity.Student;
import entity.TeachingAssistant;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.ModuleExistException;
import util.exception.ModuleNotFoundException;
import util.exception.StudentNotFoundException;

/**
 *
 * @author mango
 */
@Stateless
@Local(ModuleControllerLocal.class)
public class ModuleController implements ModuleControllerLocal {

    @EJB
    private StudentControllerLocal studentController;

    @PersistenceContext(unitName = "LearningHubSystem-ejbPU")
    private EntityManager em;

    @Override
    public List<Module> retrieveAllModules() {
        Query query = em.createQuery("SELECT m FROM Module m");
        List<Module> modules = query.getResultList();
        return modules;
    }

    @Override
    public Module retrieveModuleById(Long id) throws ModuleNotFoundException {
        Query query = em.createQuery("SELECT m FROM Module m WHERE m.id=:id");
        query.setParameter("id", id);
        try {
            Module module = (Module) query.getSingleResult();
            //lazy fectching
            module.getAnnouncements().size();
            module.getLecturers().size();
            module.getStduents().size();
            module.getTAs();

            return module;
        } catch (NoResultException ex) {
            throw new ModuleNotFoundException("Moudle with id: " + id + "dose not exist.");
        }
    }

    @Override
    public List<Module> retrieveModulesByStudentUsername(String username) {
        try {
            Student currentStudent = studentController.retrieveStudentByUsername(username);
            List<Module> modules = currentStudent.getModules();
            return modules;
        } catch (StudentNotFoundException ex) {
            ex.getMessage();
        }
        System.err.println("modules list is null!!!!!!!!!!!");
        return new ArrayList<Module>();
    }
    
    @Override
    public List<Student> retrieveClassAndGroups (Long moduleId){
        try{
            Module module=retrieveModuleById(moduleId);
            List<Student> classAndGroups=module.getStduents();
            return classAndGroups;
        }
        catch(ModuleNotFoundException ex){
            ex.getMessage();
        }
        return new ArrayList<>();
    }
    
    
    @Override
    public List<Announcement> retrieveAnnoucements (Long moduleId){
        try{
            Module module=retrieveModuleById(moduleId);
            List<Announcement> classAndGroups=module.getAnnouncements();
            return classAndGroups;
        }
        catch(ModuleNotFoundException ex){
            ex.getMessage();
        }
        return new ArrayList<>();
    }
    

    @Override
    public Module createNewModule(Module newModule) throws ModuleExistException {
        List<Module> moduleList = retrieveAllModules();
        for (Module module : moduleList) {
            if (newModule.getModuleCode().equals(module.getModuleCode())) {
                throw new ModuleExistException("Module Code Already Exists.\n");
            }
        }
        em.persist(newModule);
        em.flush();

        //create folder for uploading files for this module
        Boolean success = (new File("/Applications/NetBeans/glassfish-4.1.1-uploadedfiles/uploadedFiles/" + newModule.getModuleCode())).mkdirs();
        if (!success) {
            System.err.println("The new folder is not created successfully!");
        }

        return newModule;
    }

    @Override
    public Module retrieveModuleByModuleCode(String moduleCode) throws ModuleNotFoundException {
        Query query = em.createQuery("SELECT m FROM Module m WHERE m.moduleCode=:ModuleCode");
        query.setParameter("ModuleCode", moduleCode);

        try {
            Module module = (Module) query.getSingleResult();
            //lazy fectching
            module.getAnnouncements().size();
            module.getLecturers().size();
            module.getStduents().size();
            module.getTAs();

            return module;
        } catch (NoResultException ex) {
            throw new ModuleNotFoundException("Moudle: " + moduleCode + "dose not exist.");
        }
    }

    @Override
    public Module addLecturer(String moduleCode, Lecturer lecturer) throws ModuleNotFoundException {
        try {
            Module module = retrieveModuleByModuleCode(moduleCode);
            module.getLecturers().add(lecturer);
            lecturer.getModules().add(module);
            em.merge(module);
            em.merge(lecturer);
            return module;
        } catch (ModuleNotFoundException ex) {
            throw new ModuleNotFoundException("Moudle: " + moduleCode + "dose not exist.");
        }
    }

    @Override
    public Module addTA(TeachingAssistant TA, String moduleCode) throws ModuleNotFoundException {
        try {
            Module module = retrieveModuleByModuleCode(moduleCode);
            module.getTAs().add(TA);
            TA.getModules().add(module);
            em.merge(module);
            em.merge(TA);
            return module;
        } catch (ModuleNotFoundException ex) {
            throw new ModuleNotFoundException("Moudle: " + moduleCode + "dose not exist.");
        }
    }

    @Override
    public Module addStudent(Student student, String moduleCode) throws ModuleNotFoundException {
        try {
            Module module = retrieveModuleByModuleCode(moduleCode);
            module.getStduents().add(student);
            student.getModules().add(module);
            em.merge(module);
            em.merge(student);
            return module;

        } catch (ModuleNotFoundException ex) {
            throw new ModuleNotFoundException("Module: " + moduleCode + "does not exist.");
        }
    }

    @Override
    public void deleteModule(Module module) throws ModuleNotFoundException {
        Module moduleToDelete;
        try {
            moduleToDelete = retrieveModuleById(module.getId());
            em.remove(moduleToDelete);
        } catch (ModuleNotFoundException ex) {
            throw new ModuleNotFoundException("Module does not exist.");
        }

    }

    @Override
    public void updateModule(Module module) throws ModuleNotFoundException {
        if (module.getId() != null) {
            Module moduleToUpdate = retrieveModuleByModuleCode(module.getModuleCode());
            moduleToUpdate.setModuleName(module.getModuleName());
            moduleToUpdate.setModularCredit(module.getModularCredit());
            moduleToUpdate.setClassSize(module.getClassSize());
            moduleToUpdate.setExamDate(module.getExamDate());
            em.merge(moduleToUpdate);
        } else {
            throw new ModuleNotFoundException("Module doesn't exist.");
        }
    }

}
