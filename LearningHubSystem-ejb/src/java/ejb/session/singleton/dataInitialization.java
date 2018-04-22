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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.AnnouncementNotFoundException;
import util.exception.GeneralException;
import util.exception.LecturerNotFoundException;
import util.exception.ModuleNotFoundException;
import util.exception.StudentNotFoundException;
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
            //twk 3106
            Lecturer twk = lecturerControllerLocal.retrieveLecturerByUsername("lecturer1");
            Module is3106 = moduleControllerLocal.retrieveModuleByModuleCode("IS3106");
            Announcement newAnnouncement1 = new Announcement("Final Set of Screencast Video Recordings", "Dear Students,The following screencast video recordings are ready for viewing", new Timestamp(118, 3, 28, 15, 0, 0, 0), twk, is3106);
            Announcement newAnnouncement2 = new Announcement("Group Project Written Report Requirements", "Dear Students,Good luck with your project submission :)\n don't miss the ddl!", new Timestamp(118, 4, 3, 13, 0, 0, 0), twk, is3106);
            Announcement newAnnouncement3 = new Announcement("Open Consultation", "I am back in my office !", new Timestamp(118, 3, 20, 16, 56, 0, 0), twk, is3106);
            Announcement newAnnouncement4 = new Announcement("Today’s Open Consultation", "Will commence from 9 am onwards instead of the original 8 am. Pls take note.", new Timestamp(118, 3, 17, 7, 33, 0, 0), twk, is3106);
            Announcement newAnnouncement5 = new Announcement("Gentle Reminder - Good Friday Make-up Lecture", "Today, 29 Mar, 6:30 pm. Venue is Executive Classroom (COM2-04-02)", new Timestamp(118, 2, 29, 8, 56, 0, 0), twk, is3106);

            em.persist(newAnnouncement1);
            em.persist(newAnnouncement2);
            em.persist(newAnnouncement3);
            em.persist(newAnnouncement4);
            em.persist(newAnnouncement5);

            //tsc st3131
            Lecturer tsc = lecturerControllerLocal.retrieveLecturerByUsername("lecturer4");
            Module st3131 = moduleControllerLocal.retrieveModuleByModuleCode("ST3131");
            Announcement newAnnouncement6 = new Announcement("Grade for midterm test", "Students can view their grades for the midterm test in the CA folder in the gradebook of the module website in the IVLE.Legend for the grades. A+ (45-50); A (40-44); B+ (35-39); B (30-34); C (25-29); D (20-24); F (0-19)", new Timestamp(118, 3, 18, 14, 2, 0, 0), tsc, st3131);
            Announcement newAnnouncement7 = new Announcement("Review session", "There will be a review session held in Week 13 on 17 April 2018 (Tuesday) from 8:05 am to 9:40 am at LT32. ", new Timestamp(118, 3, 9, 9, 2, 0, 0), tsc, st3131);
            Announcement newAnnouncement8 = new Announcement("Quiz 3", "Quiz 3 will be open between 12:00 am, 13 April 2018 (Friday) to 11:00 pm, 15 April 2018 (Sunday).You may need a scientific calculator. You are advised to revise the materials before attempting the quiz.", new Timestamp(118, 3, 18, 14, 2, 0, 0), tsc, st3131);
            Announcement newAnnouncement9 = new Announcement("Project guidelines deadline", "The deadline for submission of the project report is 17 April 2018 (Tuesday). An amended version of the project guideline has been uploaded. ", new Timestamp(118, 3, 9, 9, 0, 0, 0), tsc, st3131);
            Announcement newAnnouncement10 = new Announcement("Make-up Test", "For those who were absent from the midterm test on 13/3/18 with a valid reason, the make-up test will be held from 8:25 am to 9:25 am on 3/4/18 (Tuesday) at LT32. The details of the make-up test such as the scope of the test and the number of pieces of help sheet are the same as the midterm test.", new Timestamp(118, 2, 29, 14, 19, 0, 0), tsc, st3131);

            em.persist(newAnnouncement6);
            em.persist(newAnnouncement7);
            em.persist(newAnnouncement8);
            em.persist(newAnnouncement9);
            em.persist(newAnnouncement10);

            //ljy fin2004
            Lecturer ljy = lecturerControllerLocal.retrieveLecturerByUsername("lecturer5");
            Module fin2004 = moduleControllerLocal.retrieveModuleByModuleCode("FIN2004");
            Announcement newAnnouncement11 = new Announcement("Presentation order today", "Hi all,Here's the presentatation order: Groups 3, 5, 2, 4, 1, 6. See you all soon", new Timestamp(118, 3, 16, 8, 32, 0, 0), ljy, fin2004);
            Announcement newAnnouncement12 = new Announcement("exam paper on LHS", "Hi All, exam paper is available to access on the LHS. I have uploaded it in the folder. MCQ questions are not released by the department.", new Timestamp(118, 3, 11, 13, 54, 0, 0), ljy, fin2004);
            Announcement newAnnouncement13 = new Announcement("[Lab 5] Step 2: Data provisioning", "Hi all, The Data Provisioning Agent has been fixed by SAP at QUT. Enjoy the work!", new Timestamp(118, 2, 28, 18, 31, 0, 0), ljy, fin2004);
            Announcement newAnnouncement14 = new Announcement("[Lab 4] Procurement Configuration GBI Case Handbook", "Hi all,The old version of GBI Procurement Configuration (ver 2.23) was mistakenly uploaded on IVLE. The correct version (ver 3.1) is now available. Please use this.", new Timestamp(118, 2, 6, 18, 53, 0, 0), ljy, fin2004);

            em.persist(newAnnouncement11);
            em.persist(newAnnouncement12);
            em.persist(newAnnouncement13);
            em.persist(newAnnouncement14);

            //ljy dsa2102
            Module dsa2102 = moduleControllerLocal.retrieveModuleByModuleCode("DSA2102");
            Announcement newAnnouncement15 = new Announcement("Quiz 2", "Quiz 2 will be open between 12:00 am, 23 March 2018 (Friday) to 11:00 pm, 25 March 2018 (Sunday). The quiz covers the materials in Chapters 4 to 7 of the lecture notes.", new Timestamp(118, 2, 21, 11, 8, 0, 0), ljy, dsa2102);
            Announcement newAnnouncement16 = new Announcement("Quiz 1", "Quiz 1 will be open between 12:00 am, 23 February 2018 (Friday) to 11:00 pm, 25 February 2018 (Sunday). The quiz covers the materials in Chapters 1 to 3 of the lecture notes.", new Timestamp(118, 1, 23, 12, 2, 0, 0), ljy, dsa2102);
            Announcement newAnnouncement17 = new Announcement("Two new tutorial groups are open", "For students who are unable to do the online tutorial registration (including exchange students), the following two new tutorial groups are open for you: Group 6 : Wednesday 5 pm - 6 pm and Group 7: Friday 12 noon - 1 pm", new Timestamp(118, 2, 1, 17, 9, 0, 0), ljy, dsa2102);
            Announcement newAnnouncement18 = new Announcement("Lectures in Week 1", "The first lecture will be held in LT32 on 16/1/2018 (Tuesday) from 8:05 am to 9:35 am.", new Timestamp(118, 2, 21, 11, 8, 0, 0), ljy, dsa2102);

            em.persist(newAnnouncement15);
            em.persist(newAnnouncement16);
            em.persist(newAnnouncement17);
            em.persist(newAnnouncement18);

            //lhh cs2102
            Lecturer lhh = lecturerControllerLocal.retrieveLecturerByUsername("lecturer2");
            Module cs2102 = moduleControllerLocal.retrieveModuleByModuleCode("CS2102");
            Announcement newAnnouncement19 = new Announcement("CS2102: Walk-in Consultation", "Hi,I will be available for walk-in consultation on Tuesday 24 April from 11:00 to 16:00 in my office COM1-03-20. ", new Timestamp(118, 3, 20, 12, 40, 0, 0), lhh, cs2102);
            Announcement newAnnouncement20 = new Announcement("CS2102: Assignment 4 ", "Hi,the deadline for Assignment 4 is extended until 13 April 18:30. ", new Timestamp(118, 3, 6, 14, 7, 0, 0), lhh, cs2102);
            Announcement newAnnouncement21 = new Announcement("New Files in Module CS2102", "Hi,new materials has been uploaded for cs2102, please go download. ", new Timestamp(118, 3, 3, 17, 28, 0, 0), lhh, cs2102);
            Announcement newAnnouncement22 = new Announcement("Collection of midterm test scripts", "If you've not collected your midtest test scripts, you may do so from Prof. Lek Hsiang Hui after today's lecture at LT19.", new Timestamp(118, 2, 21, 12, 27, 0, 0), lhh, cs2102);

            em.persist(newAnnouncement19);
            em.persist(newAnnouncement20);
            em.persist(newAnnouncement21);
            em.persist(newAnnouncement22);

            //oh cn1102
            Lecturer oh = lecturerControllerLocal.retrieveLecturerByUsername("lecturer3");
            Module cn1102 = moduleControllerLocal.retrieveModuleByModuleCode("CN1102");
            Announcement newAnnouncement23 = new Announcement("Reminder of Guest Lecture tomorrow", "As announced, we have Guest Lecture tomorrow at 10am followed by my exam review from 9am to 9:45am. Please be reminded that your participation during Q&A will be marked for your participation portion of grades.", new Timestamp(118, 3, 20, 6, 56, 0, 0), oh, cn1102);
            Announcement newAnnouncement24 = new Announcement("Office hours for project consultation", "As announced in class, please email me to set up an appointment for group project consultation. Please try to meet me at least once. Here are time slots: Wednesday, March 7, 1pm-4pm, Friday, March 9, 2pm-4pm and Monday, March 12, 2pm-4pm.", new Timestamp(118, 2, 5, 18, 6, 0, 0), oh, cn1102);
            Announcement newAnnouncement25 = new Announcement("In-class lab at the end of lecture", "I plan to cover last minute questions on Assignment 3 (FI-CO) and configuration exercises for Assignment 4 tomorrow. Please bring your laptops.", new Timestamp(118, 2, 4, 15, 11, 0, 0), oh, cn1102);

            em.persist(newAnnouncement23);
            em.persist(newAnnouncement25);
            em.persist(newAnnouncement24);
        } catch (LecturerNotFoundException | ModuleNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void loadAdminData() {
        Administrator newAdmin = new Administrator("administrator", "admin@soc.nus", "12345678", "admin", "password");
        newAdmin.setIsPremium(true);
        em.persist(newAdmin);
        
        Administrator newAdmin1 = new Administrator("administrator", "admin2@soc.nus", "23458723", "admin2", "password2");
        newAdmin.setIsPremium(false);
        em.persist(newAdmin1);
    }

    private void loadLecturerData() {
        Lecturer newLecturer1 = new Lecturer("lecturer1", "password1", "Tan Wee Kek", "twk@soc.nus", "Computing", "IS", "12345678");
        newLecturer1.setPhotoName("twk");
        newLecturer1.setIsPremium(true);
        em.persist(newLecturer1);
        Lecturer newLecturer2 = new Lecturer("lecturer2", "password2", "Lek Hsiang Hui", "lhh@soc.nus", "Computing", "IS", "23456789");
        newLecturer2.setPhotoName("lhh");
        newLecturer1.setIsPremium(true);
        em.persist(newLecturer2);
        Lecturer newLecturer3 = new Lecturer("lecturer3", "password3", "Oh Lih Bin", "oh@soc.nus", "Computing", "IS", "34567890");
        newLecturer3.setPhotoName("oh");
        newLecturer1.setIsPremium(true);
        em.persist(newLecturer3);
        Lecturer newLecturer4 = new Lecturer("lecturer4", "password4", "Tay Seng Chuan", "tsc@sci.nus", "Science", "Physics", "45678901");
        newLecturer4.setPhotoName("tsc");
        em.persist(newLecturer4);
        Lecturer newLecturer5 = new Lecturer("lecturer5", "password5", "Lin Jo Yan", "ljy@sci.nus", "Business", "Finance", "45810671");
        newLecturer5.setPhotoName("ljy");
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
        Timestamp timestamp4 = new Timestamp(118, 4, 9, 13, 0, 0, 0);
        Module newModule4 = new Module("Chemical Engineering Principles and Practice II", "CN1102", 4, 30,
                "This module is the second part of a two-part module designed to provide first year Chemical and Biomolecular Engineering students with an experiential exposure to the foundational concepts of Biomolecular/Biochemical/Bioprocess Engineering, including mass and energy balances, biosafety and sterile handling, bioreaction kinetics, bioreactor design, downstream processing and purification, biosystems modelling and optimization, etc., through a series of hands-on experimental laboratories. In the laboratory, they will learn to carry out measurement, data collection, analysis, modelling, interpretation and presentation. The laboratory sessions will be blended with real engineering applications of industrial and societal relevance to Singapore .", timestamp4);
        em.persist(newModule4);
        Timestamp timestamp5 = new Timestamp(118, 4, 5, 13, 0, 0, 0);
        Module newModule5 = new Module("Finance", "FIN2004", 4, 102,
                "This course helps students to understand the key concepts and tools in Finance. It provides a broad overview of the financial environment under which a firm operates. It equips the students with the conceptual and analytical skills necessary to make sound financial decisions for a firm. Topics to be covered include introduction to finance, financial statement analysis, long-term financial planning, time value of money, risk and return analysis, capital budgeting methods and applications, common stock valuation, bond valuation, short term management and financing.", timestamp5);
        em.persist(newModule5);
        Timestamp timestamp6 = new Timestamp(118, 4, 5, 17, 0, 0, 0);
        Module newModule6 = new Module("Essential Data Analytics Tools: Numerical Computation", "DSA2102", 4, 33,
                "This module aims at introducing basic concepts and wellestablished numerical methods that are very related to the computing foundation of data science and analytics. The emphasis is on the tight integration of numerical algorithms, implementation in industrial programming language, and examination on practical examples drawn from various disciplines related to data science. Major topics include: computer arithmetic, matrix multiplication, numerical methods for solving linear systems, least squares method, interpolation, concrete implementations in industrial program language, and sample applications related to data science.", timestamp6);
        em.persist(newModule6);

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
        Student student1 = new Student("Wang Yinhan", "wyhpassword", "wyh@soc.nus", "Computing", "IS", "13579135", "wangyinhan");
        student1.setIsPremium(true);
        student1.setPhotoName("wyh");
        em.persist(student1);
        Student student2 = new Student("Gong Zipeng", "gzppassword", "gzp@soc.nus", "Computing", "IS", "34464224", "gongzipeng");
        student2.setIsPremium(true);
        student2.setPhotoName("gzp");
        em.persist(student2);
        Student student3 = new Student("Lin Xianying", "lxypassword", "lxy@soc.nus", "Computing", "IS", "12345577", "linxianying");
        student3.setIsPremium(true);
        student3.setPhotoName("lxy");
        em.persist(student3);
        Student student4 = new Student("Xu Hong", "xhpassword", "xh@soc.nus", "Computing", "IS", "24688424", "xuhong");
        student4.setIsPremium(true);
        student4.setPhotoName("xh");
        em.persist(student4);
        Student student5 = new Student("Zou Yutong", "zytpassword", "zyt@sci.nus", "Science", "DSA", "14642694", "zouyutong");
        student5.setPhotoName("zyt");
        student5.setIsPremium(true);
        em.persist(student5);
        Student student6 = new Student("Wang Jingheng", "wjhpassword", "wjh@engin.nus", "Engineering", "Chem Engin", "12347765", "wangjingheng");
        student6.setPhotoName("wjh");
        student6.setIsPremium(false);
        em.persist(student6);
        Student student7 = new Student("Peppa pig", "pigpassword", "piggy@com.nus", "Computing", "CS", "13574214", "piggy");
        student7.setPhotoName("pig");
        student7.setIsPremium(false);
        em.persist(student7);
        Student student8 = new Student("Goat Sussie", "goatpassword", "goat@engin.nus", "Engineering", "Civil Engin", "12344515", "sussie");
        student8.setPhotoName("goat");
        student8.setIsPremium(false);
        em.persist(student8);
        Student student9 = new Student("Sponge Bob", "bobpassword", "bob@fass.nus", "FASS", "Economics", "71276345", "spongebob");
        student9.setPhotoName("bob");
        student9.setIsPremium(false);
        em.persist(student9);
        Student student10 = new Student("Garfield", "password", "garfield@biz.nus", "Business", "Accounting", "77612345", "garfield");
        student10.setPhotoName("cat");
        student10.setIsPremium(false);
        em.persist(student10);
        Student student11 = new Student("Snow White", "password", "white@biz.nus", "Business", "Finance", "73457612", "snowwhite");
        student11.setPhotoName("snow");
        student11.setIsPremium(false);
        em.persist(student11);

    }

    private void loadTAData() {
        TeachingAssistant teachingAssistant1 = new TeachingAssistant("TA111", "password1", "TA1", "TA1@soc.nus", "Computing", "12345672", "IS");
        teachingAssistant1.setPhotoName("TA1");
        teachingAssistant1.setIsPremium(true);
        em.persist(teachingAssistant1);
        TeachingAssistant teachingAssistant2 = new TeachingAssistant("TA222", "password2", "TA2", "TA2@sci.nus", "Science", "12342354", "Data Analytics");
        teachingAssistant2.setPhotoName("TA2");
        teachingAssistant2.setIsPremium(true);
        em.persist(teachingAssistant2);
        TeachingAssistant teachingAssistant3 = new TeachingAssistant("TA333", "password3", "TA3", "TA3@fass.nus", "FASS", "86356252", "Economics");
        teachingAssistant3.setPhotoName("TA3");
        teachingAssistant3.setIsPremium(true);
        em.persist(teachingAssistant3);
        TeachingAssistant teachingAssistant4 = new TeachingAssistant("TA444", "password4", "TA4", "TA4@engin.nus", "Engineering", "62863552", "Chemical Engineering");
        teachingAssistant4.setPhotoName("TA4");
        teachingAssistant4.setIsPremium(false);
        em.persist(teachingAssistant4);
        TeachingAssistant teachingAssistant5 = new TeachingAssistant("TA555", "password5", "TA5", "TA5@biz.nus", "Business", "62552863", "Accounting");
        teachingAssistant5.setPhotoName("TA5");
        teachingAssistant5.setIsPremium(false);
        em.persist(teachingAssistant5);
        TeachingAssistant teachingAssistant6 = new TeachingAssistant("TA666", "password6", "TA6", "TA6@biz.nus", "Business", "62862553", "Finance");
        teachingAssistant6.setPhotoName("TA6");
        teachingAssistant6.setIsPremium(false);
        em.persist(teachingAssistant6);

    }

    private void loadTEData() {

        TimeEntry t1 = new TimeEntry("Go out", "2018-05-29T12:00:22Z", "2018-05-29T13:00:22Z", "details");
        TimeEntry t2 = new TimeEntry("Study", "2018-04-11T12:00:22Z", "2018-04-11T13:00:22Z", "details");
        TimeEntry t3 = new TimeEntry("Work", "2018-04-29T22:00:22Z", "2018-04-29T22:00:32Z", "details");
        TimeEntry t4 = new TimeEntry("Sport", "2018-04-29T18:00:22Z", "2018-04-29T20:00:32Z", "sports with friends");
        TimeEntry t5 = new TimeEntry("IS3106 final", "2018-04-30T13:00:00Z", "2018-04-30T15:00:00Z", "IS3106 final examination at COM2, don't forget to bring 2B pencil!");
        TimeEntry t6 = new TimeEntry("IS3106 project due", "2018-04-22T00:00:22Z", "2018-04-22T23:59:59Z", "IS3106 project deadline, don't forget to zip everything in one file!");
        TimeEntry t7 = new TimeEntry("Birthday", "2018-05-22T00:00:22Z", "2018-05-22T23:59:59Z", "it's my firends birthday today!!!don't forget!");
        TimeEntry t8 = new TimeEntry("Family stuff", "2018-05-05T00:00:22Z", "2018-05-05T23:59:59Z", "remember go home by 20:00 today!it's family time!");
        TimeEntry t9 = new TimeEntry("Avenger movie day", "2018-05-01T00:00:22Z", "2018-05-01T23:59:59Z", "it's time to see avengers movie! woo hoo!");
        TimeEntry t10 = new TimeEntry("Final 4103 class", "2018-04-20T14:00:00Z", "2018-04-22T16:00:00Z", "last class of IS4103, you can't imagine how happy i am!");
        TimeEntry t11 = new TimeEntry("Hotpot night", "2018-05-01T00:00:22Z", "2018-05-01T23:59:59Z", "Hotpot night is tonight!!!");
        TimeEntry t12 = new TimeEntry("IIP interview", "2018-05-22T14:00:22Z", "2018-05-22T14:30:59Z", "phone interview with KPMG");
        TimeEntry t13 = new TimeEntry("Shopping with Cao Yu", "2018-05-11T12:00:22Z", "2018-05-11T13:00:22Z", "Orchard");
        TimeEntry t14 = new TimeEntry("Drinking night", "2018-04-21T12:00:22Z", "2018-04-21T13:00:22Z", "Go out and drink with friends");
        TimeEntry t15 = new TimeEntry("IS4103 project", "2018-04-12T13:00:00Z", "2018-04-12T15:00:00Z", "IS4103 final examination at COM2, don't forget to bring 2B pencil!");
        TimeEntry t16 = new TimeEntry("IS4103 project due", "2018-03-22T00:00:22Z", "2018-03-22T23:59:59Z", "IS4103 project deadline, don't forget to zip everything in one file!");
        TimeEntry t17 = new TimeEntry("WZY birthday", "2018-05-11T00:00:22Z", "2018-05-11T23:59:59Z", "it's WZY's firends birthday today!!!don't forget!");
        TimeEntry t18 = new TimeEntry("Family stuff", "2018-05-05T00:00:22Z", "2018-05-05T23:59:59Z", "remember go home by 22:00 today!it's family time!");
        TimeEntry t19 = new TimeEntry("A quite place movie day", "2018-05-01T00:00:22Z", "2018-05-01T23:59:59Z", "it's time to see A quite place movie! woo hoo!");
        TimeEntry t20 = new TimeEntry("Final 3106 class", "2018-04-20T14:00:00Z", "2018-04-22T16:00:00Z", "last class of IS3106, you can't imagine how happy i am!");
        TimeEntry t21 = new TimeEntry("Autodesk interview", "2018-04-01T10:00:00Z", "2018-04-01T12:59:59Z", "Autodesk interview is this morning!!!");
        TimeEntry t22 = new TimeEntry("GIC interview", "2018-04-22T14:00:22Z", "2018-04-22T14:30:59Z", "phone interview with KPMG");
        TimeEntry t23 = new TimeEntry("Mother's birthday", "2018-04-27T14:00:22Z", "2018-04-27T14:30:59Z", "Prepare a surprise for mom.");
        TimeEntry t24 = new TimeEntry("Prepare for new module", "2018-04-25T14:00:22Z", "2018-04-28T14:30:59Z", "Prepare a new module to teach in next SEM!");
        TimeEntry t25 = new TimeEntry("Travelling", "2018-05-10T14:00:22Z", "2018-05-18T14:30:59Z", "Good days in China!");
        TimeEntry t26 = new TimeEntry("Cooking day", "2018-04-30T14:00:22Z", "2018-04-30T14:30:59Z", "Cooing for family members");
        TimeEntry t27 = new TimeEntry("Sleep", "2018-05-02T14:00:22Z", "2018-05-02T14:30:59Z", "Take a good rest! Sleep! Sleep!!!");
        TimeEntry t28 = new TimeEntry("Travel", "2018-04-25T14:00:22Z", "2018-04-27T14:30:59Z", "Travel to HongKong! The most happy day!");
        TimeEntry t29 = new TimeEntry("Coding day", "2018-05-14T14:00:22Z", "2018-05-14T14:30:59Z", "DEADLINE is tommorow! Hurry up! You are the best one!");
        TimeEntry t30 = new TimeEntry("Son's birthday", "2018-04-25T14:00:26Z", "2018-04-26T14:30:59Z", "Prepare a big surprise for my honey!");
        TimeEntry t31 = new TimeEntry("Internship interview", "2018-05-10T14:00:22Z", "2018-05-10T14:30:59Z", "phone interview with KPMG");
        TimeEntry t32 = new TimeEntry("Reading", "2018-04-23T14:00:22Z", "2018-04-23T14:30:59Z", "Finish required reading in IS3106");
        TimeEntry t33 = new TimeEntry("Shopping", "2018-04-25T14:00:22Z", "2018-04-25T18:30:59Z", "Shopping in vivo city with my girlfriend!");
        TimeEntry t34 = new TimeEntry("Go back to China", "2018-05-10T14:00:22Z", "2018-05-11T14:30:59Z", "Happy!!! Finally can go back home!");
        TimeEntry t35 = new TimeEntry("Apply for vacation stay", "2018-04-21T14:00:22Z", "2018-04-21T14:30:59Z", "Dont forget to apply for vacation stay in PGPR! Ver important");
        TimeEntry t36 = new TimeEntry("ST2334 quiz", "2018-05-03T14:00:22Z", "2018-05-03T14:30:59Z", "ST2334 quiz ddl is today!!!");
        TimeEntry t37 = new TimeEntry("GIC", "2018-05-19T14:00:22Z", "2018-05-19T14:30:59Z", "Face-to-face interview with GIC! Very important!");
        TimeEntry t38 = new TimeEntry("Travel to Beijing", "2018-04-23T14:00:22Z", "2018-04-26T14:30:59Z", "Good travel to Beijing with mom! Go to eat everything delicious in Beijing!");

        try {
            Student student1 = studentControllerLocal.retrieveStudentByUsername("xuhong");
            Student student2 = studentControllerLocal.retrieveStudentByUsername("linxianying");
            Student student3 = studentControllerLocal.retrieveStudentByUsername("wangyinhan");
            Student student4 = studentControllerLocal.retrieveStudentByUsername("gongzipeng");
            Student student5 = studentControllerLocal.retrieveStudentByUsername("wangjingheng");
            Student student6 = studentControllerLocal.retrieveStudentByUsername("zouyutong");
            Student student7 = studentControllerLocal.retrieveStudentByUsername("piggy");
            Student student8 = studentControllerLocal.retrieveStudentByUsername("sussie");
            Student student9 = studentControllerLocal.retrieveStudentByUsername("spongebob");
            Student student10 = studentControllerLocal.retrieveStudentByUsername("garfield");
            Student student11 = studentControllerLocal.retrieveStudentByUsername("snowwhite");

            Lecturer twk = lecturerControllerLocal.retrieveLecturerByUsername("lecturer1");
            Lecturer lhh = lecturerControllerLocal.retrieveLecturerByUsername("lecturer2");
            Lecturer oh = lecturerControllerLocal.retrieveLecturerByUsername("lecturer3");
            Lecturer tsc = lecturerControllerLocal.retrieveLecturerByUsername("lecturer4");
            Lecturer ljy = lecturerControllerLocal.retrieveLecturerByUsername("lecturer5");

            tecl.createTimeEntry(t1, student1);
            tecl.createTimeEntry(t2, student1);
            tecl.createTimeEntry(t14, student1);
            tecl.createTimeEntry(t15, student1);
            tecl.createTimeEntry(t16, student1);
            tecl.createTimeEntry(t38, student1);
            tecl.createTimeEntry(t3, student2);
            tecl.createTimeEntry(t4, student2);
            tecl.createTimeEntry(t13, student2);
            tecl.createTimeEntry(t17, student7);
            tecl.createTimeEntry(t18, student8);
            tecl.createTimeEntry(t19, student9);
            tecl.createTimeEntry(t5, student3);
            tecl.createTimeEntry(t21, student10);
            tecl.createTimeEntry(t22, student3);
            tecl.createTimeEntry(t6, student6);
            tecl.createTimeEntry(t20, student4);
            tecl.createTimeEntry(t12, student11);
            tecl.createTimeEntry(t7, student5);
            tecl.createTimeEntry(t8, twk);
            tecl.createTimeEntry(t10, twk);
            tecl.createTimeEntry(t26, twk);
            tecl.createTimeEntry(t27, twk);
            tecl.createTimeEntry(t28, twk);
            tecl.createTimeEntry(t9, lhh);
            tecl.createTimeEntry(t11, lhh);
            tecl.createTimeEntry(t23, oh);
            tecl.createTimeEntry(t24, ljy);
            tecl.createTimeEntry(t29, twk);
            tecl.createTimeEntry(t30, lhh);
            tecl.createTimeEntry(t31, lhh);
            tecl.createTimeEntry(t32, oh);
            tecl.createTimeEntry(t33, oh);
            tecl.createTimeEntry(t34, ljy);
            tecl.createTimeEntry(t35, ljy);
            tecl.createTimeEntry(t36, tsc);
            tecl.createTimeEntry(t37, tsc);

        } catch (TimeEntryExistException | StudentNotFoundException | LecturerNotFoundException ex) {
            Logger.getLogger(dataInitialization.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
            Logger.getLogger(dataInitialization.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setRelationships() {

        try {
            Student wyh = studentControllerLocal.retrieveStudentByUsername("wangyinhan");
            Student xh = studentControllerLocal.retrieveStudentByUsername("xuhong");
            Student gzp = studentControllerLocal.retrieveStudentByUsername("gongzipeng");
            Student lxy = studentControllerLocal.retrieveStudentByUsername("linxianying");
            Student zyt = studentControllerLocal.retrieveStudentByUsername("zouyutong");
            Student wjh = studentControllerLocal.retrieveStudentByUsername("wangjingheng");
            Student pig = studentControllerLocal.retrieveStudentByUsername("piggy");
            Student goat = studentControllerLocal.retrieveStudentByUsername("sussie");
            Student bob = studentControllerLocal.retrieveStudentByUsername("spongebob");
            Student cat = studentControllerLocal.retrieveStudentByUsername("garfield");
            Student snow = studentControllerLocal.retrieveStudentByUsername("snowwhite");

            Module is3106 = moduleControllerLocal.retrieveModuleByModuleCode("IS3106");
            Module CS2102 = moduleControllerLocal.retrieveModuleByModuleCode("CS2102");
            Module st3131 = moduleControllerLocal.retrieveModuleByModuleCode("ST3131");
            Module cn1102 = moduleControllerLocal.retrieveModuleByModuleCode("CN1102");
            Module fin2004 = moduleControllerLocal.retrieveModuleByModuleCode("FIN2004");
            Module dsa2102 = moduleControllerLocal.retrieveModuleByModuleCode("DSA2102");

            //wyh
            wyh.getModules().add(is3106);
            wyh.getModules().add(CS2102);
            wyh.getModules().add(st3131);
            wyh.getModules().add(dsa2102);
            is3106.getStduents().add(wyh);
            CS2102.getStduents().add(wyh);
            st3131.getStduents().add(wyh);
            dsa2102.getStduents().add(wyh);

            //xh
            xh.getModules().add(is3106);
            xh.getModules().add(CS2102);
            xh.getModules().add(st3131);
            is3106.getStduents().add(xh);
            CS2102.getStduents().add(xh);
            st3131.getStduents().add(xh);

            //lxy
            lxy.getModules().add(is3106);
            lxy.getModules().add(CS2102);
            lxy.getModules().add(st3131);
            is3106.getStduents().add(lxy);
            CS2102.getStduents().add(lxy);
            st3131.getStduents().add(lxy);

            //gzp
            gzp.getModules().add(is3106);
            gzp.getModules().add(CS2102);
            gzp.getModules().add(st3131);
            gzp.getModules().add(fin2004);
            is3106.getStduents().add(gzp);
            CS2102.getStduents().add(gzp);
            st3131.getStduents().add(gzp);
            fin2004.getStduents().add(gzp);

            //zyt
            zyt.getModules().add(dsa2102);
            zyt.getModules().add(st3131);
            zyt.getModules().add(CS2102);
            dsa2102.getStduents().add(zyt);
            st3131.getStduents().add(zyt);
            CS2102.getStduents().add(zyt);

            //wjh
            wyh.getModules().add(cn1102);
            wjh.getModules().add(fin2004);
            wjh.getModules().add(dsa2102);

            List<Module> modules = moduleControllerLocal.retrieveAllModules();
            //pig
            pig.setModules(modules);
            for (Module module : modules) {
                module.getStduents().add(pig);
            }

            //goat
            goat.setModules(modules);
            for (Module module : modules) {
                module.getStduents().add(goat);
            }
            //bob
            bob.setModules(modules);
            for (Module module : modules) {
                module.getStduents().add(bob);
            }
            //cat
            cat.setModules(modules);
            for (Module module : modules) {
                module.getStduents().add(cat);
            }
            //snow

            snow.setModules(modules);
            for (Module module : modules) {
                module.getStduents().add(snow);
            }
//        for (Student student : students) {
//            student.setModules(modules);
//        }
//        for (Module module : modules) {
//            module.setStduents(students);
//        }

            //relation between module and lecturer, TA, annoucement
            try {
                Lecturer twk = lecturerControllerLocal.retrieveLecturerByUsername("lecturer1");
                Lecturer lhh = lecturerControllerLocal.retrieveLecturerByUsername("lecturer2");
                Lecturer tsc = lecturerControllerLocal.retrieveLecturerByUsername("lecturer4");
                Lecturer oh = lecturerControllerLocal.retrieveLecturerByUsername("lecturer3");
                Lecturer ljy = lecturerControllerLocal.retrieveLecturerByUsername("lecturer5");

                TeachingAssistant ta1 = teachingAssistantControllerLocal.retrieveTAByUsername("TA111");
                TeachingAssistant ta2 = teachingAssistantControllerLocal.retrieveTAByUsername("TA222");
                TeachingAssistant ta3 = teachingAssistantControllerLocal.retrieveTAByUsername("TA333");
                TeachingAssistant ta4 = teachingAssistantControllerLocal.retrieveTAByUsername("TA444");
                TeachingAssistant ta5 = teachingAssistantControllerLocal.retrieveTAByUsername("TA555");
                TeachingAssistant ta6 = teachingAssistantControllerLocal.retrieveTAByUsername("TA666");

//            Module is3106 = moduleControllerLocal.retrieveModuleByModuleCode("IS3106");
//            Module cs2102 = moduleControllerLocal.retrieveModuleByModuleCode("CS2102");
//            Module st3131 = moduleControllerLocal.retrieveModuleByModuleCode("ST3131");
                Announcement announcement1 = announcementControllerLocal.retrieveAnnouncementByName("Final Set of Screencast Video Recordings");
                Announcement announcement2 = announcementControllerLocal.retrieveAnnouncementByName("Group Project Written Report Requirements");
                Announcement announcement3 = announcementControllerLocal.retrieveAnnouncementByName("Open Consultation");
                Announcement announcement4 = announcementControllerLocal.retrieveAnnouncementByName("Today’s Open Consultation");
                Announcement announcement5 = announcementControllerLocal.retrieveAnnouncementByName("Gentle Reminder - Good Friday Make-up Lecture");

                twk.getModules().add(is3106);
                ta1.getModules().add(is3106);
                ta6.getModules().add(is3106);
                is3106.getLecturers().add(twk);
                is3106.getTAs().add(ta1);
                is3106.getTAs().add(ta6);
                is3106.getAnnouncements().add(announcement1);
                is3106.getAnnouncements().add(announcement2);
                is3106.getAnnouncements().add(announcement3);
                is3106.getAnnouncements().add(announcement4);
                is3106.getAnnouncements().add(announcement5);
//lhh cs2102
                Announcement announcement6 = announcementControllerLocal.retrieveAnnouncementByName("CS2102: Walk-in Consultation");
                Announcement announcement7 = announcementControllerLocal.retrieveAnnouncementByName("CS2102: Assignment 4");
                Announcement announcement8 = announcementControllerLocal.retrieveAnnouncementByName("New Files in Module CS2102");
                Announcement announcement9 = announcementControllerLocal.retrieveAnnouncementByName("Collection of midterm test scripts");

                lhh.getModules().add(CS2102);
                ta2.getModules().add(CS2102);
                CS2102.getLecturers().add(lhh);
                CS2102.getTAs().add(ta2);
                CS2102.getAnnouncements().add(announcement9);
                CS2102.getAnnouncements().add(announcement8);
                CS2102.getAnnouncements().add(announcement7);
                CS2102.getAnnouncements().add(announcement6);

                //tsc st3131
                Announcement announcement10 = announcementControllerLocal.retrieveAnnouncementByName("Grade for midterm test");
                Announcement announcement11 = announcementControllerLocal.retrieveAnnouncementByName("Review session");
                Announcement announcement12 = announcementControllerLocal.retrieveAnnouncementByName("Quiz 3");
                Announcement announcement13 = announcementControllerLocal.retrieveAnnouncementByName("Project guidelines deadline");
                Announcement announcement14 = announcementControllerLocal.retrieveAnnouncementByName("Make-up Test");

                tsc.getModules().add(st3131);
                ta3.getModules().add(st3131);
                st3131.getLecturers().add(tsc);
                st3131.getTAs().add(ta3);
                st3131.getAnnouncements().add(announcement10);
                st3131.getAnnouncements().add(announcement11);
                st3131.getAnnouncements().add(announcement12);
                st3131.getAnnouncements().add(announcement13);
                st3131.getAnnouncements().add(announcement14);

                //oh cn1102
                Announcement announcement15 = announcementControllerLocal.retrieveAnnouncementByName("Reminder of Guest Lecture tomorrow");

                Announcement announcement16 = announcementControllerLocal.retrieveAnnouncementByName("Office hours for project consultation");

                Announcement announcement17 = announcementControllerLocal.retrieveAnnouncementByName("In-class lab at the end of lecture");

                oh.getModules().add(cn1102);
                ta4.getModules().add(cn1102);
                cn1102.getLecturers().add(oh);
                cn1102.getTAs().add(ta4);
                cn1102.getAnnouncements().add(announcement15);
                cn1102.getAnnouncements().add(announcement16);
                cn1102.getAnnouncements().add(announcement17);

                //ljy fin2004
                Announcement announcement18 = announcementControllerLocal.retrieveAnnouncementByName("Presentation order today");
                Announcement announcement19 = announcementControllerLocal.retrieveAnnouncementByName("exam paper on LHS");
                Announcement announcement20 = announcementControllerLocal.retrieveAnnouncementByName("[Lab 5] Step 2: Data provisioning");
                Announcement announcement21 = announcementControllerLocal.retrieveAnnouncementByName("[Lab 4] Procurement Configuration GBI Case Handbook");

                fin2004.getAnnouncements().add(announcement18);
                fin2004.getAnnouncements().add(announcement19);
                fin2004.getAnnouncements().add(announcement20);
                fin2004.getAnnouncements().add(announcement21);
                
                //ljy dsa2102
                Announcement announcement22 = announcementControllerLocal.retrieveAnnouncementByName("Quiz 2");
                Announcement announcement23 = announcementControllerLocal.retrieveAnnouncementByName("Quiz 1");
                Announcement announcement24 = announcementControllerLocal.retrieveAnnouncementByName("Two new tutorial groups are open");
                Announcement announcement25 = announcementControllerLocal.retrieveAnnouncementByName("Lectures in Week 1");

                dsa2102.getAnnouncements().add(announcement22);
                dsa2102.getAnnouncements().add(announcement23);
                dsa2102.getAnnouncements().add(announcement24);
                dsa2102.getAnnouncements().add(announcement25);
                ljy.getModules().add(fin2004);
                ljy.getModules().add(dsa2102);
                ta5.getModules().add(fin2004);
                ta5.getModules().add(dsa2102);
                fin2004.getLecturers().add(ljy);
                dsa2102.getLecturers().add(ljy);
                fin2004.getTAs().add(ta5);
                dsa2102.getTAs().add(ta5);

            } catch (LecturerNotFoundException | TANotFoundException | AnnouncementNotFoundException ex) {
                ex.getMessage();

            }
        } catch (StudentNotFoundException | ModuleNotFoundException ex) {

        }

    }
}
