package ejb.session.stateless;

import entity.Lecturer;
import javax.ejb.Stateless;
import entity.Module;
import entity.Student;
import entity.TeachingAssistant;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.ModuleExistException;
import util.exception.ModuleNotFoundException;

/**
 *
 * @author mango
 */
@Stateless
@Local(ModuleControllerLocal.class)
public class ModuleController implements ModuleControllerLocal {

    @PersistenceContext(unitName = "LearningHubSystem-ejbPU")
    private EntityManager em;

    @Override
    public List<Module> retrieveAllModules() {
        Query query = em.createQuery("SELECT m FROM Module m");
        List<Module> modules = query.getResultList();
        return modules;
    }

    @Override
    public Module retrieveModuleById(Long id) throws ModuleNotFoundException{
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
    public Module createNewModule(Module newModule) throws ModuleExistException {
        List<Module> moduleList = retrieveAllModules();
        for (Module module : moduleList) {
            if (newModule.getModuleCode().equals(module.getModuleCode())) {
                throw new ModuleExistException("Module Code Already Exists.\n");
            }
        }
        em.persist(newModule);
        em.flush();

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
            throw new ModuleNotFoundException("Moudle: " + moduleCode + "dose not exist.");
        }
    }

}
