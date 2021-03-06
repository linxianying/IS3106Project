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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import java.io.Serializable;
import static java.lang.System.err;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import util.exception.FileEntityExistException;
import util.exception.ModuleNotFoundException;

/**
 *
 * @author mango
 */
@Named
@ViewScoped
public class fileManagedBean implements Serializable {

    @EJB(name = "FileEntityControllerLocal")
    private FileEntityControllerLocal fileEntityControllerLocal;

    @EJB(name = "ModuleControllerLocal")
    private ModuleControllerLocal moduleControllerLocal;

    private Long moduleIdToView;
    private Module moduleToView;
    private StreamedContent file;
    private Long selectedFileId;
    private FileEntity selectedFile;
    private List<FileEntity> relatedFiles;
    FacesContext context;
    HttpSession session;

    public fileManagedBean() {
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
        relatedFiles = moduleToView.getFiles();
    }

    
    //upload
    public void handleUpload(FileUploadEvent event) {
        try {
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + moduleToView.getModuleCode() + System.getProperty("file.separator") + event.getFile().getFileName();

            FileEntity newFile = new FileEntity(event.getFile().getFileName());

            try {
                newFile = fileEntityControllerLocal.createNewFileEntity(newFile, moduleIdToView);
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New File Entity is not created successfully!", ""));
            }

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
            
            setModuleToView(moduleControllerLocal.retrieveModuleById(moduleIdToView));

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));
        } catch (IOException | ModuleNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        }
    }

    //download
    public void handleDownload(ActionEvent event) {
        try {
            selectedFileId = (Long) event.getComponent().getAttributes().get("fileId");
            setSelectedFile(fileEntityControllerLocal.retrieveFileById(selectedFileId));

            String filePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + getSelectedFile().getModule().getModuleCode() + System.getProperty("file.separator") + getSelectedFile().getFileName();

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("downloadFilePath", filePath);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public StreamedContent getFile() {

        String filePath = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("downloadFilePath");

        if (filePath != null && filePath.trim().length() > 0) {
            try {
                FileInputStream stream = new FileInputStream(new File(filePath));
                file = new DefaultStreamedContent(stream, "", getSelectedFile().getFileName());
            } catch (FileNotFoundException ex) {

            }
        }

        return file;
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

    public List<FileEntity> getRelatedFiles() {
        return moduleToView.getFiles();
    }

    /**
     * @param relatedFiles the relatedFiles to set
     */
    public void setRelatedFiles(List<FileEntity> relatedFiles) {
        this.relatedFiles = relatedFiles;
    }

    public Long getSelectedFileId() {
        return selectedFileId;
    }

    /**
     * @param selectedFileId the selectedFileId to set
     */
    public void setSelectedFileId(Long selectedFileId) {
        this.selectedFileId = selectedFileId;
    }

    /**
     * @return the selectedFile
     */
    public FileEntity getSelectedFile() {
        return selectedFile;
    }

    /**
     * @param selectedFile the selectedFile to set
     */
    public void setSelectedFile(FileEntity selectedFile) {
        this.selectedFile = selectedFile;
    }
}
