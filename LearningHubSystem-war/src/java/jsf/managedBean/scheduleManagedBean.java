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
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    
    @EJB
    TimeEntryControllerLocal tecl;
    
    public scheduleManagedBean() {
    }
    
 
    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        context = FacesContext.getCurrentInstance();
        session = (HttpSession) context.getExternalContext().getSession(true);
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
                    DefaultScheduleEvent dse = new DefaultScheduleEvent(t.getTitle(), toDate(t.getFromDate()), toDate(t.getToDate()), t);
                    dse.setDescription(t.getDetails());
                    //System.out.println(t.getDetails());
                    eventModel.addEvent(dse);
                }
            }else{
                System.out.println("Empty timeEntries");
            }
        }else{
            userType = "lecturer";
            lecturer = (Lecturer) session.getAttribute("lecturer");
            username = lecturer.getUsername();
            timeEntries = lecturer.getTimeEntries();
            TimeEntry t;
            if(timeEntries!=null){
                for (TimeEntry timeEntry : timeEntries) {
                    t = (TimeEntry) timeEntry;
                    DefaultScheduleEvent dse = new DefaultScheduleEvent(t.getTitle(), toDate(t.getFromDate()), toDate(t.getToDate()), t);
                    dse.setDescription(t.getDetails());
                    //System.out.println(t.getDetails());
                    eventModel.addEvent(dse);
                }
            }else{
                System.out.println("Empty timeEntries");
            }
        }
        
    }
    
    public Date toDate(String str){
        Date date = null;
        try {
            date = df.parse(str);
        } catch (ParseException ex) {
            
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
            TimeEntry t = new TimeEntry(event.getTitle(), df.format(event.getStartDate()), df.format(event.getEndDate()), "");
            if(userType.equals("student")){
               
                //student.getTimeEntries().add(t);
                tecl.createTimeEntry(t, student);
               
            }else if(userType.equals("lecturer")){
                tecl.createTimeEntry(t, lecturer);
            }
            eventModel.addEvent(new DefaultScheduleEvent(t.getTitle(), toDate(t.getFromDate()), toDate(t.getToDate()), t));
        } else {
            TimeEntry t = (TimeEntry) event.getData();
            tecl.updateTimeEntry(t,event.getTitle(),df.format(event.getStartDate()), df.format(event.getEndDate()), details);
            eventModel.updateEvent(new DefaultScheduleEvent(t.getTitle(), toDate(t.getFromDate()), toDate(t.getToDate()), t));
        }
        event = new DefaultScheduleEvent();
    }

    public void deleteEvent(ActionEvent actionEvent) {
        TimeEntry t = (TimeEntry) event.getData();
        if(userType.equals("student"))
            tecl.deleteTimeEntry(t.getId(), student); 
        else
            tecl.deleteTimeEntry(t.getId(), lecturer); 
        eventModel.deleteEvent(event);
    }
     
    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
    }
     
    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }
     
    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
         
        addMessage(message);
    }
     
    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
         
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
