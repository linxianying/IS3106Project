/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 128, nullable = false, unique = true)
    private String moduleName;
    
    @Column(length = 32, nullable = false, unique = true)
    private String moduleCode;
    
    @Column(nullable = false)
    private int modularCredit;
    
    @Column(nullable = false)
    private int classSize;
    
    @Column(length = 1024, nullable = false)
    private String description;
    
    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date examDate;
    
    @ManyToMany(cascade={CascadeType.ALL}, mappedBy="modules")
    private List<Lecturer> lecturers;
    
    @ManyToMany(cascade={CascadeType.ALL}, mappedBy="modules")
    private List<TeachingAssistant> TAs;
    
    @ManyToMany(cascade={CascadeType.ALL}, mappedBy="modules")
    private List<Student> stduents;
    
    @OneToMany(cascade={CascadeType.PERSIST},mappedBy="module")
    private List<Announcement> announcements;

    @OneToMany(cascade={CascadeType.PERSIST},mappedBy="module")
    private List<FileEntity> files;
    
    public Module() {
        lecturers = new ArrayList<>();
        TAs = new ArrayList<>();
        stduents = new ArrayList<>();
        announcements = new ArrayList<>();
        files = new ArrayList<>();
    }

    
    public Module(String moduleName, String moduleCode, int modularCredit, int classSize, String description, Date examDate) {
        this();
        this.moduleName = moduleName;
        this.moduleCode = moduleCode;
        this.modularCredit = modularCredit;
        this.classSize = classSize;
        this.description = description;
        this.examDate = examDate;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setLecturers(List<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    public List<TeachingAssistant> getTAs() {
        return TAs;
    }

    public void setTAs(List<TeachingAssistant> TAs) {
        this.TAs = TAs;
    }

    public List<Student> getStduents() {
        return stduents;
    }

    public void setStduents(List<Student> stduents) {
        this.stduents = stduents;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcement> announcements) {
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

    public List<FileEntity> getFiles() {
        return files;
    }

    public void setFiles(List<FileEntity> files) {
        this.files = files;
    }
    
}
