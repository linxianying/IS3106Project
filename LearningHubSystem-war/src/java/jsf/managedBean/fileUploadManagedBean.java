/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.FileEntityControllerLocal;
import ejb.session.stateless.ModuleControllerLocal;
import entity.FileEntity;
import entity.Module;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import java.io.Serializable;
import static java.lang.System.err;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import util.exception.FileEntityExistException;
import util.exception.ModuleNotFoundException;

/**
 *
 * @author mango
 */
@Named
@ViewScoped
public class fileUploadManagedBean implements Serializable {

    @EJB(name = "FileEntityControllerLocal")
    private FileEntityControllerLocal fileEntityControllerLocal;

    @EJB(name = "ModuleControllerLocal")
    private ModuleControllerLocal moduleControllerLocal;

    private Long moduleIdToView;
    private Module moduleToView;

    public fileUploadManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        moduleIdToView = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("moduleIdToView");
        
        try {
            setModuleToView(moduleControllerLocal.retrieveModuleById(moduleIdToView));
        } catch (ModuleNotFoundException ex) {
            setModuleToView(new Module());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the module details: " + ex.getMessage(), null));
        } catch (Exception ex) {
            setModuleToView(new Module());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
   

    public void handleUpload(FileUploadEvent event) {

        try {
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + moduleToView.getModuleCode() + System.getProperty("file.separator") + event.getFile().getFileName();

            FileEntity newFile = new FileEntity(event.getFile().getFileName());
            
            try{
                newFile = fileEntityControllerLocal.createNewFileEntity(newFile, moduleIdToView);
            }
            catch(Exception ex){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New File Entity is not created successfully!","")); 
            }
            
            System.err.println("********** File name: " + event.getFile().getFileName());
            System.err.println("********** newFilePath: " + newFilePath);

            File file = new File(newFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            InputStream inputStream = event.getFile().getInputstream();

            while (true) {
                a = inputStream.read(buffer);

                if (a < 0) {
                    break;
                }

                fileOutputStream.write(buffer, 0, a);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            inputStream.close();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        }
    }

    /**
     * @return the moduleIdToView
     */
    public Long getModuleIdToView() {
        return moduleIdToView;
    }

    /**
     * @param moduleIdToView the moduleIdToView to set
     */
    public void setModuleIdToView(Long moduleIdToView) {
        this.moduleIdToView = moduleIdToView;
    }

    /**
     * @return the moduleToView
     */
    public Module getModuleToView() {
        return moduleToView;
    }

    /**
     * @param moduleToView the moduleToView to set
     */
    public void setModuleToView(Module moduleToView) {
        this.moduleToView = moduleToView;
    }

}
