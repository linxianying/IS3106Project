/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import ejb.session.singleton.*;
import ejb.session.stateless.*;
import entity.Lecturer;
import entity.Student;
import entity.TimeEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;
import util.exception.GeneralException;
import util.exception.InvalidLoginCredentialException;
import util.exception.StudentExistException;
import util.exception.StudentNotFoundException;
import util.exception.TimeEntryExistException;
import util.exception.TimeEntryNotFoundException;

/**
 *
 * @author mango
 */
@Local
public interface TimeEntryControllerLocal {

    public TimeEntry createTimeEntry(TimeEntry timeEntry, Student student) 
            throws TimeEntryExistException,GeneralException;
    public TimeEntry createTimeEntry(TimeEntry timeEntry, Lecturer lecturer) 
            throws TimeEntryExistException,GeneralException;
    public TimeEntry retrieveTimeEntryById(Long id) throws TimeEntryNotFoundException;
    public List<TimeEntry> retrieveAllTimeEntrys() ;
    public List<TimeEntry> retrieveTimeEntrysByName(String username) ;
    public void updateTimeEntry(TimeEntry timeEntry,String title, String from, String to, String details);
    public boolean deleteTimeEntry(Long id, Student student);
    public boolean deleteTimeEntry(Long id, Lecturer lecturer);

    public List<TimeEntry> retrieveTimeEntrysByLecturerName(String username);

    public void updateTimeEntry(TimeEntry timeEntry) throws TimeEntryNotFoundException;
}
