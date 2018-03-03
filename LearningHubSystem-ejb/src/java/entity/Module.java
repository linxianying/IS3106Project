/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author mango
 */
@Entity
public class Module implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    
    @Column(length = 32, nullable = false, unique = true)
    private String moduleName;
    
    @Column(length = 32, nullable = false, unique = true)
    private String moduleCode;
    
    @Column(nullable = false)
    private int modularCredit;
    
    @Column(nullable = false)
    private int classSize;
    
    @Column(length = 128, nullable = false)
    private String description;
    
    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date examDate;
    
    @ManyToMany(cascade={CascadeType.ALL}, mappedBy="modules")
    private Collection<Lecturer> lecturers;
    
    @ManyToMany(cascade={CascadeType.ALL}, mappedBy="modules")
    private Collection<TeachingAssistant> TAs;
    
    @ManyToMany(cascade={CascadeType.ALL}, mappedBy="modules")
    private Collection<Student> stduents;
    
    @OneToMany(cascade={CascadeType.PERSIST},mappedBy="module")
    private Collection<Announcement> announcements;

    public Module() {
    }

    
    public Module(String moduleName, String moduleCode, int modularCredit, int classSize, String description, Date examDate) {
        this.moduleName = moduleName;
        this.moduleCode = moduleCode;
        this.modularCredit = modularCredit;
        this.classSize = classSize;
        this.description = description;
        this.examDate = examDate;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public int getModularCredit() {
        return modularCredit;
    }

    public void setModularCredit(int modularCredit) {
        this.modularCredit = modularCredit;
    }

    public int getClassSize() {
        return classSize;
    }

    public void setClassSize(int classSize) {
        this.classSize = classSize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public Collection<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setLecturers(Collection<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    public Collection<TeachingAssistant> getTAs() {
        return TAs;
    }

    public void setTAs(Collection<TeachingAssistant> TAs) {
        this.TAs = TAs;
    }

    public Collection<Student> getStduents() {
        return stduents;
    }

    public void setStduents(Collection<Student> stduents) {
        this.stduents = stduents;
    }

    public Collection<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(Collection<Announcement> announcements) {
        this.announcements = announcements;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Module)) {
            return false;
        }
        Module other = (Module) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Module[ id=" + id + " ]";
    }
    
}
