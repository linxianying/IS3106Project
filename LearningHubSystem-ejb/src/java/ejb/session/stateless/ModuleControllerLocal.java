/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

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
    
    void deleteModule(Module moduleToDelete);
    
    public void updateModule(Module module) throws ModuleNotFoundException;
}
