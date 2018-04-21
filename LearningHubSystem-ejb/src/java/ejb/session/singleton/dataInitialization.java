/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AnnouncementControllerLocal;
import ejb.session.stateless.LecturerControllerLocal;
import ejb.session.stateless.ModuleControllerLocal;
import ejb.session.stateless.StudentControllerLocal;
import ejb.session.stateless.TeachingAssistantControllerLocal;
import ejb.session.stateless.TimeEntryControllerLocal;
import entity.Administrator;
import entity.Announcement;
import entity.Lecturer;
import entity.Module;
import entity.Student;
import entity.TeachingAssistant;
import entity.TimeEntry;
import java.io.File;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.AnnouncementNotFoundException;
import util.exception.GeneralException;
import util.exception.LecturerNotFoundException;
import util.exception.ModuleNotFoundException;
import util.exception.TANotFoundException;
import util.exception.TimeEntryExistException;

/**
 *
 * @author mango
 */
@Singleton
@LocalBean
@Startup
public class dataInitialization {

    @EJB
    private AnnouncementControllerLocal announcementControllerLocal;

    @EJB
    private TeachingAssistantControllerLocal teachingAssistantControllerLocal;

    @EJB
    private StudentControllerLocal studentControllerLocal;

    @EJB
    private LecturerControllerLocal lecturerControllerLocal;

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
        if (em.find(Announcement.class, 1l) == null) {
            loadAnnoucementData();
            setRelationships();
        }
    }

    private void loadAnnoucementData() {
        try {
            Lecturer twk = lecturerControllerLocal.retrieveLecturerByUsername("lecturer1");
            Module is3106 = moduleControllerLocal.retrieveModuleByModuleCode("IS3106");
            Announcement newAnnouncement1 = new Announcement("test1", "testing annoucement functionality", new Timestamp(118, 4, 30, 15, 0, 0, 0), twk, is3106);

            Announcement newAnnouncement2 = new Announcement("test2", "testing annoucement functionality", new Timestamp(118, 4, 30, 13, 0, 0, 0), twk, is3106);
            em.persist(newAnnouncement1);
            em.persist(newAnnouncement2);

        } catch (LecturerNotFoundException | ModuleNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void loadAdminData() {
        Administrator newAdmin = new Administrator("administrator", "admin@soc.nus", "12345678", "admin", "password");
        em.persist(newAdmin);
    }

    private void loadLecturerData() {
        Lecturer newLecturer1 = new Lecturer("lecturer1", "password1", "twk", "twk@soc.nus", "Computing", "IS", "12345678");
        newLecturer1.setPhotoName("twk");
        em.persist(newLecturer1);
        Lecturer newLecturer2 = new Lecturer("lecturer2", "password2", "lhh", "lhh@soc.nus", "Computing", "IS", "23456789");
        newLecturer2.setPhotoName("lhh");
        em.persist(newLecturer2);
        Lecturer newLecturer3 = new Lecturer("lecturer3", "password3", "oh", "oh@soc.nus", "Computing", "IS", "34567890");
        newLecturer3.setPhotoName("oh");
        em.persist(newLecturer3);
        Lecturer newLecturer4 = new Lecturer("lecturer4", "password4", "tsc", "tsc@sci.nus", "Science", "Physics", "45678901");
        newLecturer4.setPhotoName("tsc");
        em.persist(newLecturer4);
        Lecturer newLecturer5 = new Lecturer("lecturer", "123456", "lecturer", "lec@sci.nus", "Science", "Physics", "45678101");
        newLecturer4.setPhotoName("lecturer");
        em.persist(newLecturer5);
    }

    private void loadModuleData() {
        Timestamp timestamp = new Timestamp(118, 5, 3, 9, 0, 0, 0);
        Module newModule1 = new Module("Database Systems", "CS2102", 4, 177,
                "The aim of this module is to introduce the fundamental concepts and techniques necessary for the understanding and practice of design and implementation of database applications and of the management of data with relational database management systems. The module covers practical and theoretical aspects of design with entity-relationship model, theory of functional dependencies and normalisation by decomposition in second, third and Boyce-Codd normal forms. The module covers practical and theoretical aspects of programming with SQL data definition and manipulation sublanguages, relational tuple calculus, relational domain calculus and relational algebra.", timestamp);
        em.persist(newModule1);
        Timestamp timestamp2 = new Timestamp(118, 4, 30, 13, 0, 0, 0);
        Module newModule2 = new Module("Enterprise Systems Interface Design and Development", "IS3106", 4, 60,
                "This module aims to train students to be conversant in front-end development for Enterprise Systems. It complements IS2103 which focuses on backend development aspects for Enterprise Systems. Topics covered include: web development scripting languages, web templating design and component design, integrating with backend application, and basic mobile application development.", timestamp2);
        em.persist(newModule2);
        Timestamp timestamp3 = new Timestamp(118, 4, 30, 13, 0, 0, 0);
        Module newModule3 = new Module("Regression Analysis", "ST3131", 4, 232,
                "This module focuses on data analysis using multiple regression models. Topics include simple linear regression, multiple regression, model building and regression diagnostics. One and two factor analysis of variance, analysis of covariance, linear model as special case of generalized linear model. This module is targeted at students who are interested in Statistics and are able to meet the pre-requisites.", timestamp3);
        em.persist(newModule3);

        //create folder for uploading files
        List<Module> modules = moduleControllerLocal.retrieveAllModules();

        for (Module each : modules) {
            Boolean success = (new File("/Applications/NetBeans/glassfish-4.1.1-uploadedfiles/uploadedFiles/" + each.getModuleCode())).mkdirs();
            if (!success) {
                System.err.println("The new folder is not created successfully!");
            }
        }
    }

    private void loadStudentData() {
        List<Module> modules = moduleControllerLocal.retrieveAllModules();
        Student student1 = new Student("wyh", "wyhpassword", "wyh@soc.nus", "Computing", "IS", "13579135", "wangyinhan");
        student1.setIsPremium(true);
        student1.setPhotoName("wyh");
        em.persist(student1);
        Student student2 = new Student("gzp", "gzppassword", "gzp@soc.nus", "Computing", "IS", "34464224", "gongzipeng");
        student2.setIsPremium(true);
        student2.setPhotoName("gzp");
        em.persist(student2);
        Student student3 = new Student("lxy", "lxypassword", "lxy@soc.nus", "Computing", "IS", "12345577", "linxianying");
        student3.setIsPremium(true);
        student3.setPhotoName("lxy");
        em.persist(student3);
        Student student4 = new Student("xh", "xhpassword", "xh@soc.nus", "Computing", "IS", "24688424", "xuhong");
        student4.setIsPremium(true);
        student4.setPhotoName("xh");
        em.persist(student4);

    }

    private void loadTAData() {
        TeachingAssistant teachingAssistant1 = new TeachingAssistant("TA111", "password1", "TA1", "TA1@soc.nus", "Computing", "12345672", "IS");
        teachingAssistant1.setPhotoName("TA1");
        em.persist(teachingAssistant1);
        TeachingAssistant teachingAssistant2 = new TeachingAssistant("TA222", "password2", "TA2", "TA2@sci.nus", "Science", "12342354", "Data Analytics");
        teachingAssistant2.setPhotoName("TA2");
        em.persist(teachingAssistant2);
        TeachingAssistant teachingAssistant3 = new TeachingAssistant("TA333", "password3", "TA3", "TA3@fass.nus", "FASS", "86356252", "Economics");
        teachingAssistant3.setPhotoName("TA3");
        em.persist(teachingAssistant3);
        
    }

    private void loadTEData() {
        Student student1 = new Student("name", "123456", "name@soc.nus", "Computing", "IS", "123456", "namename");
        student1.setPhotoName("name");
        em.persist(student1);

        TimeEntry t1 = new TimeEntry("go out", "2018-05-29T12:00:22Z", "2018-05-29T13:00:22Z", "details");
        TimeEntry t2 = new TimeEntry("study", "2018-04-11T12:00:22Z", "2018-04-11T13:00:22Z", "details");
        TimeEntry t3 = new TimeEntry("work", "2018-04-29T22:00:22Z", "2018-04-29T22:00:32Z", "details");
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

    private void setRelationships() {
        //each student registers in all module
        List<Module> modules = moduleControllerLocal.retrieveAllModules();
        List<Student> students = studentControllerLocal.retrieveAllStudents();
        for (Student student : students) {
            student.setModules(modules);
        }
        for (Module module : modules) {
            module.setStduents(students);
        }

        //relation between module and lecturer, TA, annoucement
        try {
            Lecturer twk = lecturerControllerLocal.retrieveLecturerByUsername("lecturer1");
            Lecturer lhh = lecturerControllerLocal.retrieveLecturerByUsername("lecturer2");
            Lecturer tsc = lecturerControllerLocal.retrieveLecturerByUsername("lecturer4");

            TeachingAssistant ta1 = teachingAssistantControllerLocal.retrieveTAByUsername("TA111");
            TeachingAssistant ta2 = teachingAssistantControllerLocal.retrieveTAByUsername("TA222");
            TeachingAssistant ta3 = teachingAssistantControllerLocal.retrieveTAByUsername("TA333");

            Module is3106 = moduleControllerLocal.retrieveModuleByModuleCode("IS3106");
            Module cs2102 = moduleControllerLocal.retrieveModuleByModuleCode("CS2102");
            Module st3131 = moduleControllerLocal.retrieveModuleByModuleCode("ST3131");

            Announcement announcement1 = announcementControllerLocal.retrieveAnnouncementByName("test1");
            Announcement announcement2 = announcementControllerLocal.retrieveAnnouncementByName("test2");

            twk.getModules().add(is3106);
            ta1.getModules().add(is3106);
            is3106.getLecturers().add(twk);
            is3106.getTAs().add(ta1);
            is3106.getAnnouncements().add(announcement1);
            is3106.getAnnouncements().add(announcement2);

            lhh.getModules().add(cs2102);
            ta2.getModules().add(cs2102);
            cs2102.getLecturers().add(lhh);
            cs2102.getTAs().add(ta2);

            tsc.getModules().add(st3131);
            ta3.getModules().add(st3131);
            st3131.getLecturers().add(tsc);
            st3131.getTAs().add(ta3);

        } catch (LecturerNotFoundException | ModuleNotFoundException | TANotFoundException | AnnouncementNotFoundException ex) {
            ex.getMessage();

        }

    }
}
