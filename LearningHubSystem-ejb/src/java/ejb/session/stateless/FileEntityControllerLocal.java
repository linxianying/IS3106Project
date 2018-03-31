/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FileEntity;
import entity.Module;
import java.util.List;
import javax.ejb.Local;
import util.exception.FileEntityExistException;
import util.exception.FileEntityNotFoundException;

/**
 *
 * @author mango
 */
@Local
public interface FileEntityControllerLocal {

    List<FileEntity> retrieveAllFiles();

    FileEntity createNewFileEntity(FileEntity file, Long moduleId) throws FileEntityExistException;

    FileEntity retrieveFileById(Long fileId);
}
