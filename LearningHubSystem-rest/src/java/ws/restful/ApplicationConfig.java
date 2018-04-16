/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author wyh
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ws.restful.AdminResource.class);
        resources.add(ws.restful.AnnouncementResource.class);
        resources.add(ws.restful.FileResource.class);
        resources.add(ws.restful.LecturerResource.class);
        resources.add(ws.restful.Login_logoutResource.class);
        resources.add(ws.restful.ModuleResource.class);
        resources.add(ws.restful.ScheduleResource.class);
        resources.add(ws.restful.TAResource.class);
    }
    
}
