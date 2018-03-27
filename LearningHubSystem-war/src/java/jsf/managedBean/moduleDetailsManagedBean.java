/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Module;
import javax.inject.Named;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mango
 */
@Named(value = "moduleDetailsManagedBean")
@SessionScoped
public class moduleDetailsManagedBean {

    private Module selectedModuleToView;
    FacesContext context;
    HttpSession session;

    public moduleDetailsManagedBean() {
        session = (HttpSession) context.getExternalContext().getSession(true);
        selectedModuleToView = (Module) session.getAttribute("selectedModuleToView");
        System.err.println("*****************************************");
        System.err.println(selectedModuleToView.getModuleCode());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(selectedModuleToView);
    }

}
