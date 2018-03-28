/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 *
 * @author mango
 */
@Entity
public class TeachingAssistant implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
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
    
    @Column(length = 13, nullable = false, unique = true)
    private String telephone;
    
    @Column(length = 32, nullable = false)
    private String department;
    
    @Column(nullable = false)
    private boolean isPremium;
    
    @ManyToMany(cascade={CascadeType.PERSIST})
    @JoinTable(name="TA_Module")
    private List<Module> modules;

    public TeachingAssistant() {
        this.isPremium = false;
        modules = new ArrayList<>();
    }

    public TeachingAssistant(String username, String password, String name, 
            String email, String faculty, String telephone, String department) {
        this();
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.faculty = faculty;
        this.telephone = telephone;
        this.department = department;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(boolean isPremium) {
        this.isPremium = isPremium;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TeachingAssistant)) {
            return false;
        }
        TeachingAssistant other = (TeachingAssistant) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TeachingAssistant[ id=" + id + " ]";
    }
    
}
