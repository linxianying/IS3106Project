/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.ModuleControllerLocal;
import ejb.session.stateless.TimeEntryControllerLocal;
import entity.Administrator;
import entity.Lecturer;
import entity.Module;
import entity.Student;
import entity.TeachingAssistant;
import entity.TimeEntry;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.GeneralException;
import util.exception.TimeEntryExistException;

/**
 *
 * @author mango
 */
@Singleton
@LocalBean
@Startup
public class dataInitialization {

    @EJB(name = "ModuleControllerLocal")
    private ModuleControllerLocal moduleControllerLocal;

    @PersistenceContext(unitName = "LearningHubSystem-ejbPU")
    private EntityManager em;

    @EJB
    TimeEntryControllerLocal tecl;

    public dataInitialization() {

    }

    @PostConstruct
    public void postConstruct() {

        if (em.find(Administrator.class, 1l) == null) {
            loadAdminData();
        }
        if (em.find(Module.class, 1l) == null) {
            loadModuleData();
        }
        if (em.find(Lecturer.class, 1l) == null) {
            loadLecturerData();
        }
        if (em.find(Student.class, 1l) == null) {
            loadStudentData();
        }
        if (em.find(TeachingAssistant.class, 1l) == null) {
            loadTAData();
        }
        if (em.find(TimeEntry.class, 1l) == null) {
            loadTEData();
        }
    }

    private void loadAdminData() {
        Administrator newAdmin = new Administrator("administrator", "admin@soc.nus", "12345678", "admin", "password");
        em.persist(newAdmin);
    }

    private void loadLecturerData() {
        Lecturer newLecturer1 = new Lecturer("lecturer1", "password1", "twk", "twk@soc.nus", "Computing", "IS", "12345678");
//        List<Module> modules = moduleControllerLocal.retrieveAllModules();
//        
//        for(Module each : modules) {
//            each.getLecturers().add(newLecturer1);
//            //List<Module> list = newLecturer1.getModules();
//            newLecturer1.getModules().add(each);
//            em.refresh(each);
//        }
        em.persist(newLecturer1);
        
        Lecturer newLecturer2 = new Lecturer("lecturer2", "password2", "lhh", "lhh@soc.nus", "Computing", "IS", "23456789");
        em.persist(newLecturer2);
        Lecturer newLecturer3 = new Lecturer("lecturer3", "password3", "oh", "oh@soc.nus", "Computing", "IS", "34567890");
        em.persist(newLecturer3);
        Lecturer newLecturer4 = new Lecturer("lecturer4", "password4", "tsc", "tsc@sci.nus", "Science", "Physics", "45678901");
        em.persist(newLecturer4);
        Lecturer newLecturer5 = new Lecturer("lecturer5", "123456", "lecturer", "lec@sci.nus", "Science", "Physics", "45678101");
        em.persist(newLecturer5);

    }

    private void loadModuleData() {
        Timestamp timestamp = new Timestamp(2018, 5, 3, 9, 0, 0, 0);
        Module newModule1 = new Module("Database Systems", "CS2102", 4, 177,
                "The aim of this module is to introduce the fundamental concepts and techniques", timestamp);
        em.persist(newModule1);
        Timestamp timestamp2 = new Timestamp(2018, 4, 30, 13, 0, 0, 0);
        Module newModule2 = new Module("Enterprise Systems Interface Design and Development", "IS3106", 4, 60,
                "This module aims to train students to be conversant in front-end development for Enterprise Systems.", timestamp2);
        em.persist(newModule2);
        Timestamp timestamp3 = new Timestamp(2018, 4, 30, 13, 0, 0, 0);
        Module newModule3 = new Module("Regression Analysis", "ST3131", 4, 232,
                "This module focuses on data analysis using multiple re", timestamp3);
        em.persist(newModule3);

    }

    private void loadStudentData() {
        List<Module> modules = moduleControllerLocal.retrieveAllModules();
        Student student1 = new Student("wyh", "wyhpassword", "wyh@soc.nus", "Computing", "IS", "13579135", "wangyinhan");
        em.persist(student1);
        Student student2 = new Student("gzp", "gzppassword", "gzp@soc.nus", "Computing", "IS", "34464224", "gongzipeng");
        em.persist(student2);
        Student student3 = new Student("lxy", "lxypassword", "lxy@soc.nus", "Computing", "IS", "12345577", "linxianying");
        em.persist(student3);
        Student student4 = new Student("xh", "xhpassword", "xh@soc.nus", "Computing", "IS", "24688424", "xuhong");
        //set relationship between student4 and modules
        student4.setModules(modules);
        for (Module each : modules) {
            each.getStduents().add(student4);
            em.refresh(each);
        }
        em.persist(student4);

    }

    private void loadTAData() {
        TeachingAssistant teachingAssistant1 = new TeachingAssistant("TA111", "password1", "TA1", "TA1@soc.nus", "Computing", "12345672", "IS");
        em.persist(teachingAssistant1);
        TeachingAssistant teachingAssistant2 = new TeachingAssistant("TA222", "password2", "TA2", "TA2@sci.nus", "Science", "12342354", "Data Analytics");
        em.persist(teachingAssistant2);
        TeachingAssistant teachingAssistant3 = new TeachingAssistant("TA333", "password3", "TA3", "TA3@fass.nus", "FASS", "86356252", "Economics");
        em.persist(teachingAssistant3);

    }

    private void loadTEData() {
        Student student1 = new Student("name", "123456", "name@soc.nus", "Computing", "IS", "123456", "namename");
        em.persist(student1);

        TimeEntry t1 = new TimeEntry("go out", "2018-03-29 12:00", "2018-03-29 13:00", "details");
        TimeEntry t2 = new TimeEntry("study", "2018-03-22 12:00", "2018-03-22 13:00", "details");
        TimeEntry t3 = new TimeEntry("work", "2018-03-21 12:00", "2018-03-21 13:00", "details");
        try {
            tecl.createTimeEntry(t1, student1);
            tecl.createTimeEntry(t2, student1);
            tecl.createTimeEntry(t3, student1);
        } catch (TimeEntryExistException ex) {
            Logger.getLogger(dataInitialization.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
            Logger.getLogger(dataInitialization.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
