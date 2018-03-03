/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author mango
 */
@Entity
public class Lecturer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    
    @Column(length = 32, nullable = false, unique = true)
    private String username;
    
    @Column(length = 128, nullable = false)
    private String password;
    
    @Column(length = 32, nullable = false)
    private String name;
    
    @Column(length = 32, nullable = false, unique = true)
    private String email;
    
    @Column(length = 32, nullable = false)
    private String faculty;
    
    @Column(length = 32, nullable = false)
    private String department;
    
    @Column(length = 13, nullable = false, unique = true)
    private String telephone;
    
    @Column(nullable = false)
    private boolean isPremium;
    
    @ManyToMany(cascade={CascadeType.PERSIST})
    @JoinTable(name="Lecturer_Module")
    private Collection<Module> modules;
    
    @OneToMany(cascade={CascadeType.ALL},mappedBy="lecturer")
    private Collection<Announcement> announcements;

    //default constructor
    public Lecturer() {
        this.isPremium = false;
    }

    public Lecturer(String username, String password, String name, String email, String faculty, String department, String telephone) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.faculty = faculty;
        this.department = department;
        this.telephone = telephone;
        
        this.isPremium = false;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(boolean isPremium) {
        this.isPremium = isPremium;
    }

    public Collection<Module> getModules() {
        return modules;
    }

    public void setModules(Collection<Module> modules) {
        this.modules = modules;
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
        if (!(object instanceof Lecturer)) {
            return false;
        }
        Lecturer other = (Lecturer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Lecturer[ id=" + id + " ]";
    }
    
}