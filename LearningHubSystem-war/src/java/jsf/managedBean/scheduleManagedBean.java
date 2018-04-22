/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.TimeEntryControllerLocal;
import entity.Lecturer;
import entity.Student;
import entity.TimeEntry;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
 
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import util.exception.GeneralException;
import util.exception.TimeEntryExistException;

/**
 *
 * @author lin
 */
@ManagedBean(name="scheduleManagedBean")
@SessionScoped
public class scheduleManagedBean {

    /**
     * Creates a new instance of ScheduleManagedBean
     */
    private Student student;
    private Lecturer lecturer;
    private String userType;
    private String username;
    private String details;
    private ScheduleModel eventModel;
    private Collection<TimeEntry> timeEntries;
    FacesContext context;
    HttpSession session;
    
 
    private ScheduleEvent event = new DefaultScheduleEvent();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @EJB
    TimeEntryControllerLocal tecl;
    
    public scheduleManagedBean() {
    }
    
 
    @PostConstruct
    public void init() {
        refresh();
    }
    
    public void refresh() {
        eventModel = new DefaultScheduleModel();
        context = FacesContext.getCurrentInstance();
        //session = (HttpSession) context.getExternalContext().getSession(true);
        Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();
        if(sessionMap.get("role").equals("student")){
            
            userType = "student";
            student = (Student) sessionMap.get("currentStudent");
            username = student.getUsername();
            timeEntries = student.getTimeEntries();
            TimeEntry t;
            if(timeEntries!=null){
                for (TimeEntry timeEntry : timeEntries) {
                    t = (TimeEntry) timeEntry;
                    DefaultScheduleEvent dse = new DefaultScheduleEvent(t.getTitle(), toDate1(t.getFromDate()), toDate1(t.getToDate()), t);
                    dse.setDescription(t.getDetails());
                    //System.out.println(t.getDetails());
                    eventModel.addEvent(dse);
                }
            }else{
                System.out.println("Empty timeEntries");
            }
        }else{
            userType = "lecturer";
            lecturer = (Lecturer) sessionMap.get("currentLecturer");
            username = lecturer.getUsername();
            timeEntries = lecturer.getTimeEntries();
            TimeEntry t;
            if(timeEntries!=null){
                for (TimeEntry timeEntry : timeEntries) {
                    t = (TimeEntry) timeEntry;
                    DefaultScheduleEvent dse = new DefaultScheduleEvent(t.getTitle(), toDate1(t.getFromDate()), toDate1(t.getToDate()), t);
                    dse.setDescription(t.getDetails());
                    //System.out.println(t.getDetails());
                    eventModel.addEvent(dse);
                }
            }else{
                System.out.println("Empty timeEntries");
            }
        }
        
    }
    
    //Turn Date to yyyy-MM-dd HH:mm:ss
    public String formatDF(Date date){
        String result;
        result = df1.format(date);
        String replace = result.replace("T", " ");
        String replace1 = result.replace("Z", "");
        return replace1;
    }
    
    //Turn Date to yyyy-MM-ddTHH:mm:ssZ
    public String formatDF1(Date date){
        String result;
        result = df1.format(date);
        String replace = result.replace(" ","T");
        String replace1 = replace + "Z";
        System.out.println("formatDF1: " + replace1);
        return replace1;
    }
    
    //turn yyyy-MM-dd HH:mm:ss to Date
    public Date toDate(String str){
        Date date = null;
        try {
            System.out.println(str);
            str = str.replace("T", " ").replace("Z", "");
            date = df1.parse(str);
        } catch (ParseException ex) {
            System.err.println(ex);
        }
        return date;
    }
    
    //turn yyyy-MM-dd'T'HH:mm:ss'Z' to Date
    public Date toDate1(String str){
        Date date = null;
        try {
            System.out.println(str);
            str = str.replace("T", " ").replace("Z", "");
            date = df1.parse(str);
        } catch (ParseException ex) {
            System.err.println(ex);
        }
        return date;
    }
     
    public Date getRandomDate(Date base) {
        Calendar date = Calendar.getInstance();
        date.setTime(base);
        date.add(Calendar.DATE, ((int) (Math.random()*30)) + 1);    //set random day of month
         
        return date.getTime();
    }
     
    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);
         
        return calendar.getTime();
    }
     
    public ScheduleModel getEventModel() {
        return eventModel;
    }
 
    private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
 
        return calendar;
    }
     
    public ScheduleEvent getEvent() {
        return event;
    }
 
    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }
     
    
    public void addEvent(ActionEvent actionEvent) throws TimeEntryExistException, GeneralException {
        
        if (event.getId() == null) {
            TimeEntry t = new TimeEntry(event.getTitle(), formatDF1(event.getStartDate()), formatDF1(event.getEndDate()), event.getDescription());
            if(userType.equals("student")){
                tecl.createTimeEntry(t, student);
               
            }else if(userType.equals("lecturer")){
                tecl.createTimeEntry(t, lecturer);
            }
            eventModel.addEvent(new DefaultScheduleEvent(t.getTitle(), toDate(t.getFromDate()), toDate(t.getToDate()), t));
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Time Entry Created", "Time Entry " + event.getTitle() + 
                        " is created successfully");
            addMessage(message);
        } else {
            TimeEntry t = (TimeEntry) event.getData();
            
            System.out.println("t: " + t.getId() + "/ " + t.getTitle());
            
            tecl.updateTimeEntry(t, event.getTitle(), formatDF1(event.getStartDate()), formatDF1(event.getEndDate()), event.getDescription());
            eventModel.updateEvent(new DefaultScheduleEvent(t.getTitle(), toDate1(t.getFromDate()), toDate1(t.getToDate()), t));
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Time Entry Updated", "Time Entry " + event.getTitle() + 
                        " is updated successfully");
            addMessage(message);
        }
        event = new DefaultScheduleEvent();
        refresh();
    }

    public void deleteEvent(ActionEvent actionEvent) {
        TimeEntry t = (TimeEntry) event.getData();
        if(t==null||t.getId()==null){
            System.out.println("the entity cannot be get");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error occurs!", "TimeEntry is null! Error occurs");
            addMessage(message);
            eventModel.deleteEvent(event);
            return;
        }else{
            if(userType.equals("student")){
                tecl.deleteTimeEntry(t.getId(), student); 
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Time Entry Deleted", "Time Entry " + event.getTitle() + 
                        " is deleted successfully");
                addMessage(message);
            }
            else{
                tecl.deleteTimeEntry(t.getId(), lecturer); 
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Time Entry Deleted", "Time Entry " + event.getTitle() + 
                        " is deleted successfully");
                addMessage(message);
            }
            eventModel.deleteEvent(event);
        }
        refresh();
        
    }
     
    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
        TimeEntry t = (TimeEntry) event.getData();
            
        System.out.println("t: " + t.getId() + "/ " + t.getTitle());

        tecl.updateTimeEntry(t, event.getTitle(), formatDF1(event.getStartDate()), formatDF1(event.getEndDate()), event.getDescription());
        eventModel.updateEvent(new DefaultScheduleEvent(t.getTitle(), toDate1(t.getFromDate()), toDate1(t.getToDate()), t));
            
    }
     
    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }
     
    public void onEventMove(ScheduleEntryMoveEvent event) {
        ScheduleEvent tempEvent = event.getScheduleEvent();
        TimeEntry t = (TimeEntry) tempEvent.getData();
            
        System.out.println("t: " + t.getId() + "/ " + t.getTitle());

        tecl.updateTimeEntry(t, tempEvent.getTitle(), formatDF1(tempEvent.getStartDate()), formatDF1(tempEvent.getEndDate()), tempEvent.getDescription());
        eventModel.updateEvent(new DefaultScheduleEvent(t.getTitle(), toDate1(t.getFromDate()), toDate1(t.getToDate()), t));
            
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Time Entry Updated", "Date from:" + event.getScheduleEvent().getStartDate()+ ", Date to:" + event.getScheduleEvent().getEndDate());
         
        addMessage(message);
    }
     
    public void onEventResize(ScheduleEntryResizeEvent scheduleEntryResizeEvent) {
        ScheduleEvent event = scheduleEntryResizeEvent.getScheduleEvent();
        
        TimeEntry t = (TimeEntry) event.getData();
        tecl.updateTimeEntry(t, event.getTitle(), formatDF1(event.getStartDate()), formatDF1(event.getEndDate()), event.getDescription());
        eventModel.updateEvent(new DefaultScheduleEvent(t.getTitle(), toDate1(t.getFromDate()), toDate1(t.getToDate()), t));
         
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Time Entry Updated", "Date from:" +
               t.getFromDate()+ ", Date to:" + t.getToDate());
         
        addMessage(message);
    }
     
    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Collection<TimeEntry> getTimeEntries() {
        return timeEntries;
    }

    public void setTimeEntries(Collection<TimeEntry> timeEntries) {
        this.timeEntries = timeEntries;
    }

    public FacesContext getContext() {
        return context;
    }

    public void setContext(FacesContext context) {
        this.context = context;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public DateFormat getDf() {
        return df;
    }

    public void setDf(DateFormat df) {
        this.df = df;
    }
    
}
