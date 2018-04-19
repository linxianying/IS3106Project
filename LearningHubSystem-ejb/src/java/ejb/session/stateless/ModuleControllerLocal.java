/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Announcement;
import entity.Lecturer;
import entity.Module;
import entity.Student;
import entity.TeachingAssistant;
import java.util.List;
import javax.ejb.Local;
import util.exception.ModuleExistException;
import util.exception.ModuleNotFoundException;

/**
 *
 * @author mango
 */
@Local
public interface ModuleControllerLocal {
    public List<Module> retrieveAllModules();

    Module createNewModule(Module newModule) throws ModuleExistException;

    Module retrieveModuleByModuleCode(String moduleCode) throws ModuleNotFoundException;

    Module addLecturer(String moduleCode, Lecturer lecturer) throws ModuleNotFoundException;

    Module addTA(TeachingAssistant TA, String moduleCode) throws ModuleNotFoundException;

    Module addStudent(Student student, String moduleCode) throws ModuleNotFoundException;

    public void deleteModule(Module module) throws ModuleNotFoundException;
    
    public void updateModule(Module module) throws ModuleNotFoundException;

    public Module retrieveModuleById(Long id) throws ModuleNotFoundException;

    public List<Module> retrieveModulesByStudentUsername(String username);

    public List<Student> retrieveClassAndGroups(Long moduleId);

    public List<Announcement> retrieveAnnoucements(Long moduleId);

    List<Lecturer> retrieveLecturers(Long moduleId);

    public List<Student> retrieveStudents(Long moduleId) throws ModuleNotFoundException;

    public List<TeachingAssistant> retrieveTAs(Long moduleId) throws ModuleNotFoundException;

    public List<Module> retrieveModulesByLecturerUsername(String username);


}
