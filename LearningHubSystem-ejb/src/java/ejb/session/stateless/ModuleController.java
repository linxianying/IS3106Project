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
import util.exception.LecturerNotFoundException;
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
    private LecturerControllerLocal lecturerController;

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
            System.err.println("module with id: "+module.getId()+"found "+module.getModuleCode()+": "+module.getModuleName());
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
            if(modules.isEmpty()){
                System.err.println("modules list is empty");
            }
            else{
                System.err.println("modules list not empty");
            }
            return modules;
        } catch (StudentNotFoundException ex) {
            ex.getMessage();
        }
        System.err.println("modules list is null!!!!!!!!!!!");
        return new ArrayList<Module>();
    }
    
    
    @Override
    public List<Module> retrieveModulesByLecturerUsername(String username) {
        try {
            Lecturer currentLecturer = lecturerController.retrieveLecturerByUsername(username);
            List<Module> modules = currentLecturer.getModules();
            if(modules.isEmpty()){
                System.err.println("modules list is empty");
            }
            else{
                System.err.println("modules list not empty");
            }
            return modules;
        } catch (LecturerNotFoundException ex) {
            ex.getMessage();
        }
        System.err.println("modules list is null!!!!!!!!!!!");
        return new ArrayList<Module>();
    }

    @Override
    public List<Student> retrieveClassAndGroups(Long moduleId) {
        try {
            Module module = retrieveModuleById(moduleId);
            List<Student> classAndGroups = module.getStduents();
            System.err.println("class and groups for module with id:"+moduleId+"found");
            for(int i=0;i<classAndGroups.size();i++){
                System.err.println(classAndGroups.get(i).getName());
            }
            return classAndGroups;
        } catch (ModuleNotFoundException ex) {
            ex.getMessage();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Announcement> retrieveAnnoucements(Long moduleId) {
        try {
            Module module = retrieveModuleById(moduleId);
            List<Announcement> announcements = module.getAnnouncements();
            System.err.println("announcements with id:"+moduleId+"found");
            for(int i=0;i<announcements.size();i++){
                System.err.println(announcements.get(i).getName());
            }
            return announcements;
        } catch (ModuleNotFoundException ex) {
            ex.getMessage();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Lecturer> retrieveLecturers(Long moduleId) {
        try{
            Module module = retrieveModuleById(moduleId);
            List<Lecturer> lecturers = module.getLecturers();
            return lecturers;
        }catch (ModuleNotFoundException ex) {
            ex.getMessage();
        }
        return new ArrayList<>();
    }
    
    @Override 
    public List<TeachingAssistant> retrieveTAs(Long moduleId) throws ModuleNotFoundException{
        Module module = retrieveModuleById(moduleId);
        List<TeachingAssistant> tas = module.getTAs();
            return tas;
           
    }
    
    @Override
    public List<Student> retrieveStudents(Long moduleId) throws ModuleNotFoundException{
        Module module = retrieveModuleById(moduleId);
        List<Student> students = module.getStduents();
        return students;
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
